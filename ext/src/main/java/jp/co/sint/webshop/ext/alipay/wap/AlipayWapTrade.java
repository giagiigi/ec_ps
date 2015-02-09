/**
 * Alipay.com Inc.
 * Copyright (c) 2005-2008 All Rights Reserved.
 */
package jp.co.sint.webshop.ext.alipay.wap;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sint.webshop.ext.alipay.wap.util.DirectTradeCreateRes;
import jp.co.sint.webshop.ext.alipay.wap.util.ErrorCode;
import jp.co.sint.webshop.ext.alipay.wap.util.MD5Signature;
import jp.co.sint.webshop.ext.alipay.wap.util.ParameterUtil;
import jp.co.sint.webshop.ext.alipay.wap.util.ResponseResult;
import jp.co.sint.webshop.ext.alipay.wap.util.StringUtil;
import jp.co.sint.webshop.ext.alipay.wap.util.XMapUtil;
import jp.co.sint.webshop.service.OrderService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceLoginInfo;
import jp.co.sint.webshop.service.order.OrderContainer;
import jp.co.sint.webshop.utility.BigDecimalUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.NumUtil;

/**
 * 
 * 调用支付宝的开放平台创建、支付交易步骤
 * 
 * 1.将业务参数：外部交易号、商品名称、商品总价、卖家帐户、卖家帐户、notify_url这些东西按照xml 的格式放入<req_data></req_data>中
 * 2.将通用参数也放入请求参数中 3.对以上的参数进行签名，签名结果也放入请求参数中
 * 4.请求支付宝开放平台的alipay.wap.trade.create.direct服务
 * 5.从开放平台返回的内容中取出request_token（对返回的内容要先用私钥解密，再用支付宝的公钥验签名）
 * 6.使用拿到的request_token组装alipay.wap.auth.authAndExecute服务的跳转url
 * 7.根据组装出来的url跳转到支付宝的开放平台页面，交易创建和支付在支付宝的页面上完成
 * 
 * 
 * @author 3y
 * @version $Id: Trade.java, v 0.1 2011-08-22 下午17:52:02 3y Exp $
 */
public class AlipayWapTrade extends HttpServlet {

  private static final long serialVersionUID = -3035307235076650766L;
  private static final String SEC_ID="MD5";
  String basePath="";
  public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException {
      doPost(request,response);
  }
  
  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
  	PartnerConfig config = DIContainer.get(PartnerConfig.class.getSimpleName());

    Map<String, String> reqParams = prepareTradeRequestParamsMap(request);
    //签名类型 
    String signAlgo = SEC_ID;
    String reqUrl = config.getReqUrl();
    
    //获取商户MD5 key
    String key = config.getKey();
    String sign = sign(reqParams,signAlgo,key);
    reqParams.put("sign", sign);
    
    ResponseResult resResult = new ResponseResult();
    String businessResult = "";
    try {
      resResult = send(reqParams,reqUrl,signAlgo);
    } catch (Exception e1) {
      e1.printStackTrace();
    }
    if (resResult.isSuccess()) {
      businessResult = resResult.getBusinessResult();
    } else {
      return;
    }
    DirectTradeCreateRes directTradeCreateRes = null;
    XMapUtil.register(DirectTradeCreateRes.class);
    try {
      directTradeCreateRes = (DirectTradeCreateRes) XMapUtil
          .load(new ByteArrayInputStream(businessResult.getBytes("UTF-8")));
    } catch (UnsupportedEncodingException e) {
    } catch (Exception e) {
    }
    // 开放平台返回的内容中取出request_token
    String requestToken = directTradeCreateRes.getRequestToken();
    Map<String, String> authParams = prepareAuthParamsMap(request, requestToken);
    //对调用授权请求数据签名
    String authSign = sign(authParams,signAlgo,key);
    authParams.put("sign", authSign);
    String redirectURL = "";
    try {
      redirectURL = getRedirectUrl(authParams,reqUrl);
    } catch (Exception e) {
      e.printStackTrace();
    }
    if (StringUtil.isNotBlank(redirectURL)) {
      response.sendRedirect(redirectURL);
      return;
    }
  }
  
  /**
   * 准备alipay.wap.trade.create.direct服务的参数
   * 
   * @param request
   * @return
   * @throws UnsupportedEncodingException
   */
  private Map<String, String> prepareTradeRequestParamsMap(
      HttpServletRequest request) throws UnsupportedEncodingException {
	  
  	PartnerConfig config = DIContainer.get(PartnerConfig.class.getSimpleName());

    Map<String, String> requestParams = new HashMap<String, String>();
    request.setCharacterEncoding("utf-8");
    // 商品名称
    String subject = new String(request.getParameter("commodityName").getBytes("ISO-8859-1"), "UTF-8");
//    // 商品总价
//    String totalFee = request.getParameter("totalFee");
    
    // 外部交易号 保证唯一性
    String outTradeNo = request.getParameter("orderNo");
    
    //订单金额重新查询  20141112 add start
    OrderService orderService = ServiceLocator.getOrderService(ServiceLoginInfo.getInstance());
    OrderContainer orderContainer = orderService.getOrder(outTradeNo);
    if(orderContainer ==null){
      return null;
    }
    // 订单金额计算方式参考AlipayManager
    BigDecimal totalAmount = BigDecimalUtil.add(orderContainer.getTotalAmount(), orderContainer.getOrderHeader().getPaymentCommission());
    BigDecimal discountPrice = NumUtil.coalesce(orderContainer.getOrderHeader().getDiscountPrice(), BigDecimal.ZERO);
    BigDecimal giftCardUsePrice = NumUtil.coalesce(orderContainer.getOrderHeader().getGiftCardUsePrice(), BigDecimal.ZERO);
    totalAmount = BigDecimalUtil.subtract(totalAmount, discountPrice);
    totalAmount = BigDecimalUtil.subtract(totalAmount, giftCardUsePrice);
    String totalFee = totalAmount.toString();
    //订单金额重新查询  20141112 add end
    
    // 卖家帐号
    String sellerAccountName =config.getSeller();
    // 接收支付宝发送的通知的url
    String notifyUrl = config.getWapNotify();
    // 未完成支付，用户点击链接返回商户url
    String merchantUrl = config.getWapCallback();
    
    // req_data的内容
    String reqData = ""
    	+ "<direct_trade_create_req>" 
        + "<subject>" + subject + "</subject>" 
        + "<out_trade_no>" + outTradeNo + "</out_trade_no>"
        + "<total_fee>" + totalFee + "</total_fee>" 
        + "<seller_account_name>" + sellerAccountName + "</seller_account_name>"
        + "<notify_url>" + notifyUrl + "</notify_url>"
        + "<call_back_url>" + merchantUrl+ "</call_back_url>"
        + "</direct_trade_create_req>";
    
    requestParams.put("req_data", reqData);
    requestParams.put("req_id", outTradeNo + "");
    requestParams.putAll(prepareCommonParams(request));
    return requestParams;
  }

  /**
   * 准备alipay.wap.auth.authAndExecute服务的参数
   * 
   * @param request
   * @param requestToken
   * @return
   */
  private Map<String, String> prepareAuthParamsMap(HttpServletRequest request, String requestToken) {
	
    PartnerConfig config = DIContainer.get(PartnerConfig.class.getSimpleName());

    Map<String, String> requestParams = new HashMap<String, String>();
    String reqData = "<auth_and_execute_req><request_token>" + requestToken
        + "</request_token></auth_and_execute_req>";
    requestParams.put("req_data", reqData);
    requestParams.putAll(prepareCommonParams(request));
    
    //支付成功跳转链接
    String callbackUrl = config.getWapCallback();
    requestParams.put("call_back_url", callbackUrl);
    requestParams.put("service", "alipay.wap.auth.authAndExecute");
    return requestParams;
  }

  /**
   * 准备通用参数
   * 
   * @param request
   * @return
   */
  private Map<String, String> prepareCommonParams(HttpServletRequest request) {
  	PartnerConfig config = DIContainer.get(PartnerConfig.class.getSimpleName());

    Map<String, String> commonParams = new HashMap<String, String>();
    commonParams.put("service", "alipay.wap.trade.create.direct");
    commonParams.put("sec_id", SEC_ID);
    commonParams.put("partner", config.getPartner());
    String callBackUrl = config.getWapCallback();
    commonParams.put("call_back_url", callBackUrl);
    commonParams.put("format", "xml");
    commonParams.put("v", "2.0");
    return commonParams;
  }

  /**
   * 对参数进行签名
   * 
   * @param reqParams
   * @return
   */
  private String sign(Map<String, String> reqParams,String signAlgo,String key) {

    String signData = ParameterUtil.getSignData(reqParams);
    
    String sign = "";
    try {
      sign = MD5Signature.sign(signData, key);
    } catch (Exception e1) {
      e1.printStackTrace();
    }
    return sign;
  }

  /**
   * 调用alipay.wap.auth.authAndExecute服务的时候需要跳转到支付宝的页面，组装跳转url
   * 
   * @param reqParams
   * @return
   * @throws Exception
   */
  private String getRedirectUrl(Map<String, String> reqParams,String reqUrl)
      throws Exception {
    String redirectUrl = reqUrl + "?";
    redirectUrl = redirectUrl + ParameterUtil.mapToUrl(reqParams);
    return redirectUrl;
  }

  /**
   * 调用支付宝开放平台的服务
   * 
   * @param reqParams
   *            请求参数
   * @return
   * @throws Exception
   */
  private ResponseResult send(Map<String, String> reqParams,String reqUrl,String secId) throws Exception {
    String response = "";
    String invokeUrl = reqUrl  + "?";
    URL serverUrl = new URL(invokeUrl);
    HttpURLConnection conn = (HttpURLConnection) serverUrl.openConnection();

    conn.setRequestMethod("POST");
    conn.setDoOutput(true);
    conn.connect();
    String params = ParameterUtil.mapToUrl(reqParams);
    conn.getOutputStream().write(params.getBytes());

    InputStream is = conn.getInputStream();

    BufferedReader in = new BufferedReader(new InputStreamReader(is));
    StringBuffer buffer = new StringBuffer();
    String line = "";
    while ((line = in.readLine()) != null) {
      buffer.append(line);
    }
    response = URLDecoder.decode(buffer.toString(), "utf-8");
    conn.disconnect();
    return praseResult(response,secId);
  }

  /**
   * 解析支付宝返回的结果
   * 
   * @param response
   * @return
   * @throws Exception
   */
  private ResponseResult praseResult(String response,String secId) throws Exception {
    PartnerConfig config = DIContainer.get(PartnerConfig.class.getSimpleName());

    // 调用成功
    HashMap<String, String> resMap = new HashMap<String, String>();
    String v = ParameterUtil.getParameter(response, "v");
    String service = ParameterUtil.getParameter(response, "service");
    String partner = ParameterUtil.getParameter(response, "partner");
    String sign = ParameterUtil.getParameter(response, "sign");
    String reqId = ParameterUtil.getParameter(response, "req_id");
    resMap.put("v", v);
    resMap.put("service", service);
    resMap.put("partner", partner);
    resMap.put("sec_id", secId);
    resMap.put("req_id", reqId);
    String businessResult = "";
    ResponseResult result = new ResponseResult();
    // System.out.println("Token Result:"+response);
    if (response.contains("<err>")) {
      
      result.setSuccess(false);
      businessResult = ParameterUtil.getParameter(response, "res_error");

      // 转换错误信息
      XMapUtil.register(ErrorCode.class);
      ErrorCode errorCode = (ErrorCode) XMapUtil.load(new ByteArrayInputStream(businessResult.getBytes("UTF-8")));
      result.setErrorMessage(errorCode);
      resMap.put("res_error", ParameterUtil.getParameter(response, "res_error"));
    } else {
        businessResult = ParameterUtil.getParameter(response, "res_data");
        result.setSuccess(true);
        result.setBusinessResult(businessResult);
        resMap.put("res_data", businessResult);
    }
    //获取待签名数据
    String verifyData = ParameterUtil.getSignData(resMap);
    //对待签名数据使用支付宝公钥验签名
    boolean verified = MD5Signature.verify(verifyData,sign,config.getKey());
    
    if (!verified) {
      throw new Exception("验证签名失败");
    }
    return result;
  }
}
