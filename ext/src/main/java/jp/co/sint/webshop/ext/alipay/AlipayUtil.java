package jp.co.sint.webshop.ext.alipay;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import jp.co.sint.webshop.service.alipay.PaymentAlipayParameter;
import jp.co.sint.webshop.service.alipay.PaymentAlipayResultBean;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.NumUtil;
//import jp.co.sint.webshop.utility.PaymentConfig;
import jp.co.sint.webshop.utility.StringUtil;

public class AlipayUtil {

  private static final String PAYMENT_TYPE = "1";

  public static PaymentAlipayResultBean initBean(PaymentAlipayParameter parameter, AlipayConfig config) {
    PaymentAlipayResultBean bean = new PaymentAlipayResultBean();
    
    //运行环境信息取得
    Locale locale = DIContainer.getLocaleContext().getCurrentLocale();
    // PaymentConfig pc = DIContainer.get(PaymentConfig.class.getSimpleName());
    
    // 支付宝请求URL 
    bean.setRequestUrl(config.getRequestUrl());
    // 接口名称
    bean.setService(parameter.getServiceId());
    // 合作伙伴ID
    bean.setPartner(parameter.getMerchantId());
    // 卖家ID
    bean.setSellerId(parameter.getMerchantId());
    // 参数编码字符集
    bean.setInputCharset(config.getInputCharset());
    // 通知URL
    bean.setNotifyUrl(config.getNotifyUrl());
    // 返回URL
    // UPDATE FROMT去除对应 START
    //if(locale != null){
    //  if(locale.getLanguage().equals("en")){
    //    bean.setReturnUrl(pc.getNetUrl()+"/front/en-us/alipay/return"); 
    //  }else if(locale.getLanguage().equals("ja")){
    //    bean.setReturnUrl(pc.getNetUrl()+"/front/ja-jp/alipay/return"); 
    //  }else if(locale.getLanguage().equals("zh")){
    //    bean.setReturnUrl(pc.getNetUrl()+"/front/zh-cn/alipay/return"); 
    //  }else{
    //    bean.setReturnUrl(config.getReturnUrl());
    //  }
    //}else{
    //  bean.setReturnUrl(config.getReturnUrl());
    //}
    String returnUrl = config.getReturnUrl();
    if(locale != null){
      if (locale.getLanguage().equals("en")) {
        returnUrl = returnUrl.replace("zh-cn", "en-us");
      } else if (locale.getLanguage().equals("ja")) {
        returnUrl = returnUrl.replace("zh-cn", "ja-jp");
      } else if (locale.getLanguage().equals("zh")) {
        //returnUrl = returnUrl.replace("zh-cn", "");
      }
    }
    bean.setReturnUrl(returnUrl);
    // UPDATE FROMT去除对应 END

    // 外部交易号
    bean.setOutTradeNo(parameter.getOrderId());
    // 支付类型
    bean.setPaymentType(PAYMENT_TYPE);
    // 商品展示URL
    bean.setShowUrl(config.getShowUrl());
    // 商品描述
    bean.setBody(config.getBody());
    // 交易金额
    bean.setTotalFee(NumUtil.toString(parameter.getAmount()));
    // 商品名称
    bean.setSubject(parameter.getCommodityNames());
    // 超时时间
    bean.setItBPay(config.getItBPay());

    String mySign = AlipayUtil.Sign(bean, parameter.getSecretKey());
    // 签名
    bean.setSign(mySign);
    // 签名方式
    bean.setSignType(config.getSignType());

    return bean;
  }

  private static String Sign(PaymentAlipayResultBean bean, String key) {
    Map<String, String> sPara = new HashMap<String, String>();
    sPara.put("service", bean.getService());
    sPara.put("payment_type", bean.getPaymentType());
    sPara.put("partner", bean.getPartner());
    sPara.put("seller_id", bean.getSellerId());
    sPara.put("return_url", bean.getReturnUrl());
    sPara.put("notify_url", bean.getNotifyUrl());
    sPara.put("_input_charset", bean.getInputCharset());
    sPara.put("show_url", bean.getShowUrl());
    sPara.put("out_trade_no", bean.getOutTradeNo());
    sPara.put("subject", bean.getSubject());
    sPara.put("body", bean.getBody());
    sPara.put("total_fee", bean.getTotalFee());
    sPara.put("it_b_pay", bean.getItBPay());
    return GetMysign(sPara, key);
  }

  public static String GetMysign(Map<String, String> Params, String key) {
    Map<String, String> sParaNew = AlipayUtil.ParaFilter(Params);// 过滤空值、sign与sign_type参数
    String mysign = AlipayUtil.BuildMysign(sParaNew, key);// 获得签名结果
    return mysign;
  }

  /**
   * 功能：除去数组中的空值和签名参数
   * 
   * @param sArray
   *          加密参数组
   * @return 去掉空值与签名参数后的新加密参数组
   */
  public static Map<String, String> ParaFilter(Map<String, String> sArray) {
    List<String> keys = new ArrayList<String>(sArray.keySet());
    Map<String, String> sArrayNew = new HashMap<String, String>();
    for (int i = 0; i < keys.size(); i++) {
      String key = (String) keys.get(i);
      String value = (String) sArray.get(key);
      if (StringUtil.isNullOrEmpty(value) || key.equalsIgnoreCase("sign") || key.equalsIgnoreCase("sign_type")) {
        continue;
      }
      sArrayNew.put(key, value);
    }
    return sArrayNew;
  }

  /**
   * 功能：生成签名结果
   * 
   * @param sArray
   *          要加密的数组
   * @param key
   *          安全校验码
   * @return 签名结果字符串
   */
  public static String BuildMysign(Map<String, String> sArray, String key) {
    String prestr = CreateLinkString(sArray); // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
    prestr = prestr + key; // 把拼接后的字符串再与安全校验码直接连接起来
    String mysign = Md5Encrypt.md5(prestr);
    return mysign;
  }

  /**
   * 功能：把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
   * 
   * @param params
   *          需要排序并参与字符拼接的参数组
   * @return 拼接后字符串
   */
  public static String CreateLinkString(Map<String, String> params) {
    List<String> keys = new ArrayList<String>(params.keySet());
    Collections.sort(keys);
    String prestr = "";
    for (int i = 0; i < keys.size(); i++) {
      String key = (String) keys.get(i);
      String value = (String) params.get(key);

      if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符
        prestr = prestr + key + "=" + value;
      } else {
        prestr = prestr + key + "=" + value + "&";
      }
    }
    return prestr;
  }

}
