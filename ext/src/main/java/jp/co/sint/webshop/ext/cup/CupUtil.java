package jp.co.sint.webshop.ext.cup;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;

import jp.co.sint.webshop.data.dao.PaymentMethodDao;
import jp.co.sint.webshop.data.dto.OrderHeader;
import jp.co.sint.webshop.data.dto.PaymentMethod;
import jp.co.sint.webshop.ext.sps.SpsUtil;
import jp.co.sint.webshop.service.order.OrderContainer;
import jp.co.sint.webshop.utility.BigDecimalUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.LocaleUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.utility.WebUtil;

import chinapay.PrivateKey;
import chinapay.SecureLink;

/**
 * @author System Integrator Corp.
 */
public final class CupUtil {

  private static final String TRANSACTION_TYPE_PAYMENT = "0001";

  // private static final String TRANSACTION_TYPE_REFUND = "0002";

  private CupUtil() {
  }

  public static CupConfig getCupConfig() {
    return DIContainer.get(CupConfig.class.getSimpleName());

  }

  public static CupMerchant getChinapayMerchant() {
    CupConfig config = getCupConfig();
    return config.getChinapayMerchant();
  }

  private static String get(String name, Map<String, String[]> src) {
    return get(name, src, 0);
  }

  private static String get(String name, Map<String, String[]> src, int index) {
    String result = null;
    if (src != null) {
      String[] results = src.get(name);
      if (results != null && results.length > index) {
        result = results[index];
      }
    }
    return result;
  }

  public static Map<String, String[]> convertMap(String queryString) {
    return WebUtil.parseQueryString(queryString);
  }

  public static CupRefundResponse createRefundResponse(Map<String, String[]> requestMap) {
    CupRefundResponse response = new CupRefundResponse();
    response.setRefundAmount(get("RefundAmout", requestMap));
    response.setOrderId(get("OrderId", requestMap));
    response.setResponseCode(get("ResponseCode", requestMap));
    response.setMerchantId(get("MerID", requestMap));
    response.setProcessDate(get("ProcessDate", requestMap));
    response.setCheckValue(get("CheckValue", requestMap));
    response.setStatus(get("Status", requestMap));
    response.setSendTime(get("SendTime", requestMap));
    response.setTransactionType(get("TransType", requestMap));
    response.setPrivate2(get("Priv1", requestMap));
    return response;
  }

  public static CupPaymentResponse createPaymentResponse(Map<String, String[]> requestMap) {
    CupPaymentResponse response = new CupPaymentResponse();

    // add start date 2011-01-27 by yyq desc：添加读取参数赋值
    response.setMerchantId(get("merid", requestMap));// 商户号
    response.setOrderId(get("orderno", requestMap));// 交易流水号
    response.setAmount(get("amount", requestMap));// 交易金额
    response.setTransactionStatus(get("status", requestMap));// 是否成功
    response.setTransactionType(get("transtype", requestMap)); // 交易类型
    response.setTransactionDate(get("transdate", requestMap));// 日期
    response.setCurrencyId(get("currencycode", requestMap));// 货币类型
    response.setCheckValue(get("checkvalue", requestMap));// 数字签名
    response.setPrivate1(get("Priv1", requestMap));//订单号
    // add end date 2011-01-27 by yyq desc：添加读取参数赋值
    
    // del start date 2011-01-27 by yyq desc： 注释无用参数赋值
    // response.setGateId(get("GateId", requestMap));
    // response.setClock(get("clock", requestMap));
    // del end date 2011-01-27 by yyq desc：注释无用参数赋值
    
    return response;
  }

  public static String getCheckValue(CupRefundRequest cupRequest, CupMerchant merchant) {
    String checkValue = "";
    try {
      Object[] arguments = {
          adjustValue(cupRequest.getMerchantId()), adjustValue(cupRequest.getTransactionDate()),
          adjustValue(cupRequest.getTransactionType()), adjustValue(cupRequest.getOrderId()),
          adjustValue(cupRequest.getRefundAmount()), adjustValue(cupRequest.getPrivate1()),
      };
      String signData = MessageFormat.format("{0}{1}{2}{3}{4}{5}", arguments);
      PrivateKey privateKey = new PrivateKey();
      privateKey.buildKey(cupRequest.getMerchantId(), 0, merchant.getPrivateKeyFile().getAbsolutePath());
      SecureLink link = new SecureLink(privateKey);
      checkValue = link.Sign(signData);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    return checkValue;
  }

  public static String getPlainData(CupPaymentRequest cupRequest) {
//    Object[] arguments = {
//        adjustValue(cupRequest.getMerchantId()), adjustValue(cupRequest.getOrderId()),
//        adjustValue(cupRequest.getTransactionAmount()), adjustValue(cupRequest.getCurrencyId()),
//        adjustValue(cupRequest.getTransactionDate()), adjustValue(cupRequest.getTransactionTime()),
//        adjustValue(cupRequest.getTransactionType()), adjustValue(cupRequest.getCountryId()),
//        adjustValue(cupRequest.getTimeZone()), adjustValue(cupRequest.getDstFlag()), adjustValue(cupRequest.getExtFlag()),
//        adjustValue(cupRequest.getPrivate1())
//    };
//    return MessageFormat.format("{0}{1}{2}{3}{4}{5}{6}{7}{8}{9}{10}{11}", arguments);
    Object[] arguments = {
            adjustValue(cupRequest.getMerchantId()), adjustValue(cupRequest.getOrderId()),
            adjustValue(cupRequest.getTransactionAmount()), adjustValue(cupRequest.getCurrencyId()),
            adjustValue(cupRequest.getTransactionDate()), adjustValue(cupRequest.getTransactionType()),
            adjustValue(cupRequest.getPrivate1())
        };
      return MessageFormat.format("{0}{1}{2}{3}{4}{5}{6}", arguments);
  }

  public static String getCheckValue(CupPaymentRequest cupRequest, CupMerchant merchant) {
    String checkValue = "";
    try {
      // update start date 2011-01-27 by yyq desc： 更新加密参数
      Object[] arguments = {
          adjustValue(cupRequest.getMerchantId()), adjustValue(cupRequest.getOrderId()),
          adjustValue(cupRequest.getTransactionAmount()), adjustValue(cupRequest.getCurrencyId()),
          adjustValue(cupRequest.getTransactionDate()), adjustValue(cupRequest.getTransactionType()),
          adjustValue(cupRequest.getPrivate1())
      };
      String signData = MessageFormat.format("{0}{1}{2}{3}{4}{5}{6}", arguments);
      // update end date 2011-01-27 by yyq desc： 更新加密参数
      PrivateKey privateKey = new PrivateKey();
      // update start date 2011-01-27 by yyq desc： 初始化key
      boolean flag = privateKey.buildKey(cupRequest.getMerchantId(), 0, merchant.getPrivateKeyFile().getAbsolutePath());
      if (flag == false) {
        System.out.println("build key error");// 输出KEY错误
        return "";
      } else {
        SecureLink link = new SecureLink(privateKey);
        checkValue = link.Sign(signData);
      }
      // update end date 2011-01-27 by yyq desc： 初始化key
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    return checkValue;
  }

  private static String adjustValue(String s) {
    String v = "";
    if (s != null) {
      v = s.trim();
    }
    return v;
  }

  public static String getCupOrderId(int originalOrderId, String merchantId) {
    String cupOrderId = "";
    if (merchantId != null && merchantId.length() == 15 && originalOrderId > 0 && originalOrderId < 100000000) {
      String strOrderId = (new DecimalFormat("0000000")).format(originalOrderId);
      cupOrderId = MessageFormat.format("0000{0}{1}", merchantId.subSequence(10, 15), strOrderId);
    }
    return cupOrderId;
  }

  public static SecureLink getSecureLink(CupMerchant merchant) {
    PrivateKey pkey = new PrivateKey();
    pkey.buildKey(merchant.getMerchantId(), 0, merchant.getPrivateKeyFile().getAbsolutePath());
    SecureLink link = new SecureLink(pkey);
    return link;
  }

  public static boolean verifyCupPaymentResponse(CupPaymentResponse response) {
    CupMerchant chinapay = getChinapayMerchant();
    SecureLink link = getSecureLink(chinapay);
    return link.verifyTransResponse(adjustValue(response.getMerchantId()), adjustValue(response.getOrderId()), adjustValue(response
        .getAmount()), adjustValue(response.getCurrencyId()), adjustValue(response.getTransactionDate()), adjustValue(response
        .getTransactionType()), adjustValue(response.getTransactionStatus()), adjustValue(response.getCheckValue()));
  }

  public static boolean verifyCupRefundResponse(CupRefundResponse response) {

    String plainData = MessageFormat.format("{0}{1}{2}{3}{4}{5}{6}", adjustValue(response.getMerchantId()), adjustValue(response
        .getTransactionDate()), adjustValue(response.getTransactionType()), adjustValue(response.getOrderId()),
        adjustValue(response.getRefundAmount()), adjustValue(response.getResponseCode()), adjustValue(response.getPrivate1()));

    CupMerchant chinapay = getChinapayMerchant();
    SecureLink link = getSecureLink(chinapay);

    return link.verifyAuthToken(plainData, adjustValue(response.getCheckValue()));
  }

  public static CupMerchant getMerchant(String shopCode, Long paymentMethodNo) {
    CupMerchant merchant = new CupMerchant();
    File file = null;
    try {
      PaymentMethodDao dao = DIContainer.getDao(PaymentMethodDao.class);
      PaymentMethod pm = dao.load(shopCode, paymentMethodNo);
      String merchantId = pm.getMerchantId();
      merchant.setMerchantId(merchantId);
      file = DIContainer.getResourceFile("classpath:" + pm.getSecretKey());
      if (file == null || !file.exists()) {
        throw new FileNotFoundException(pm.getSecretKey());
      }
      merchant.setPrivateKeyFile(file);
    } catch (Exception e) {
      Logger.getLogger(SpsUtil.class).error(e);
    }
    return merchant;
  }

  public static CupPaymentRequest createRefundRequest(OrderContainer container) {
    throw new UnsupportedOperationException("Not Implemented Yet.");
  }

  public static CupPaymentRequest createPaymentRequest(OrderContainer container, Locale locale) {

    CupPaymentRequest req = new CupPaymentRequest();
    try {
      CupConfig config = DIContainer.get(CupConfig.class.getSimpleName());
      OrderHeader header = container.getOrderHeader();
      CupMerchant merchant = getMerchant(header.getShopCode(), header.getPaymentMethodNo());
      
      req.setPurchaseRequestUrl(config.getPurchaseRequestUrl());
      req.setPageReturnUrl(config.getPageReturnUrl() + "?lang=" + LocaleUtil.getLanguageCode(locale));
      req.setBackReturnUrl(config.getBackReturnUrl());
      req.setCurrencyId(config.getCurrencyId());
      // req.setCountryId(config.getCountryId());
      req.setVersion(config.getVersion());
      req.setGateId(config.getGateId());
      // req.setTimeZone(config.getTimeZone());
      // req.setDstFlag(config.getDstFlag());
      // req.setExtFlag(config.getExtFlag());

      req.setMerchantId(merchant.getMerchantId());
      req.setOrderId(convertOrderId(container.getOrderNo(), merchant.getMerchantId()));

      // 50132 added start
      // modify by V10-CH start
      // BigDecimal totalAmount =
      // BigDecimalUtil.tempFormatLong(container.getTotalAmount());
      BigDecimal totalAmount = container.getTotalAmount();
      // modify by V10-CH end
      BigDecimal usedPoint = NumUtil.coalesce(container.getOrderHeader().getUsedPoint(), BigDecimal.ZERO);
      BigDecimal paymentTotalPrice = BigDecimalUtil.subtract(totalAmount, usedPoint);
      // 50132 added end
      // 20120202 shen add start
      BigDecimal discountPrice = NumUtil.coalesce(container.getOrderHeader().getDiscountPrice(), BigDecimal.ZERO);
      paymentTotalPrice = BigDecimalUtil.subtract(paymentTotalPrice, discountPrice);
      // 20120202 shen add end

      // 50132 modified start
      // req.setTransactionAmount(convertTransactionAmount(container.getTotalAmount()));
      req.setTransactionAmount(convertTransactionAmount(paymentTotalPrice));
      // 50132 modified start

      Date now = DateUtil.getSysdate();
      req.setTransactionDate(DateUtil.toDateTimeString(now, "yyyyMMdd"));
      // req.setTransactionTime(DateUtil.toDateTimeString(now, "HHmmss"));
      req.setTransactionType(TRANSACTION_TYPE_PAYMENT);

      req.setPrivate1(container.getOrderNo());
      req.setPrivate2("");
      req.setCheckValue(CupUtil.getCheckValue(req, merchant));
      req.setPlainData(CupUtil.getPlainData(req));

    } catch (RuntimeException e) {
      Logger.getLogger(CupUtil.class).error(e);
    }
    return req;
  }

  private static String convertOrderId(String wsOrderNo, String merchantId) {
    String orderId = "";
    try {
      long lnOrderNo = Long.valueOf(wsOrderNo);
      if (lnOrderNo >= 10000000) {
        throw new IllegalArgumentException("order no overflow: " + wsOrderNo);
      }
      String strOrderNo = (new DecimalFormat("0000000")).format(lnOrderNo);
      String merchantSuffix = merchantId.substring(10);
      orderId = MessageFormat.format("{0}{1}{2}", "0000", merchantSuffix, strOrderNo);
    } catch (RuntimeException e) {
      throw new IllegalArgumentException(e);
    }
    return orderId;
  }

  private static String convertTransactionAmount(BigDecimal amount) {
    DecimalFormat df = new DecimalFormat("000000000000");
    return df.format(BigDecimalUtil.multiply(amount, new BigDecimal(100)).longValue());
  }
  //20111206 lirong add start
  /**
   * 银联支付状态查询
   */
  public static Map<String, Object> unionpayInfoRequest(OrderHeader orderInfo) {
    
    CupPaymentRequest req = new CupPaymentRequest();
    CupConfig config = DIContainer.get(CupConfig.class.getSimpleName());
    
    Map<String, Object> map = new HashMap<String, Object>();
    
    try {
      CupMerchant merchant = getMerchant(orderInfo.getShopCode(), orderInfo.getPaymentMethodNo());
      
      req.setMerchantId(merchant.getMerchantId());
      req.setTransactionType(TRANSACTION_TYPE_PAYMENT);
      req.setOrderId(convertOrderId(orderInfo.getOrderNo(), merchant.getMerchantId()));
      Date now = DateUtil.getSysdate();
      req.setTransactionDate(DateUtil.toDateTimeString(now, "yyyyMMdd"));
      req.setVersion(config.getVersion());
      req.setResv(orderInfo.getOrderNo());
      req.setCheckValue(CupUtil.getCheckValue(req, merchant));
      
      StringBuffer result = new StringBuffer();
      StringBuilder sb = new StringBuilder();
      sb.append("MerId=").append(req.getMerchantId());
      sb.append("&TransType=").append(TRANSACTION_TYPE_PAYMENT);
      sb.append("&OrdId=").append(req.getOrderId());
      sb.append("&TransDate=").append(req.getTransactionDate());
      sb.append("&Version=").append(config.getVersion());
      sb.append("&Resv=").append(req.getResv());
      sb.append("&ChkValue=" + CupUtil.getCheckValue(req, merchant));
      sb.append("&ChkValue=" + req.getCheckValue());
        
      result = connectURL(sb , config.getRequestURL());

      String[] codeValue = result.toString().split("<body>")[1].split("</body>")[0].trim().split("&");
      String sKey = StringUtil.EMPTY;
      String sValue = StringUtil.EMPTY;
    
      for (int i = 0; i < codeValue.length; i++) {
        sKey = codeValue[i].split("=")[0];
        sValue = codeValue[i].split("=")[1];
        map.put(sKey, sValue);
      }

    } catch (RuntimeException e) {
      Logger.getLogger(CupUtil.class).error(e);
    }
    
    return map;
  }
  
  /**
   *  连接URL发送请求
   */
  private static StringBuffer connectURL(StringBuilder sb, String requestURL) {

    StringBuffer result = new StringBuffer();

    try {
      // 请求内容
      String httpContent = sb.toString();
      URL url = new URL(requestURL);
      HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection();
      httpUrlConnection.setConnectTimeout(30000); // 设置连接主机超时（单位：毫秒）
      httpUrlConnection.setReadTimeout(30000); // 设置从主机读取数据超时
      httpUrlConnection.setDoInput(true);
      httpUrlConnection.setDoOutput(true);
      httpUrlConnection.setUseCaches(false);
      httpUrlConnection.setRequestProperty("Content-type", "application/x-www-form-urlencoded;text/html;charset=GBK");
      httpUrlConnection.setRequestProperty("Content-Length", String.valueOf(httpContent.length()));
      httpUrlConnection.setRequestMethod("POST");

      OutputStream outStrm = httpUrlConnection.getOutputStream();

      byte[] data = httpContent.getBytes();
      outStrm.write(data);
      outStrm.flush();
      outStrm.close();

      // 实际发送请求
      InputStream is = httpUrlConnection.getInputStream();
      BufferedReader rd = new BufferedReader(new InputStreamReader(is, "gb2312"));

      String line;
      while ((line = rd.readLine()) != null) {
        result.append(line);
      }

      rd.close();
      is.close();
    } catch (MalformedURLException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

    return result;
  }
//20111206 lirong add end
}
