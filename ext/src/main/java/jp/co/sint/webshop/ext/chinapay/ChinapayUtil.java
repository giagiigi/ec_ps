package jp.co.sint.webshop.ext.chinapay;

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
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;

import chinapay.PrivateKey;
import chinapay.SecureLink;


import jp.co.sint.webshop.data.dao.PaymentMethodDao;
import jp.co.sint.webshop.data.domain.PaymentMethodType;
import jp.co.sint.webshop.data.dto.PaymentMethod;
import jp.co.sint.webshop.service.chinapay.PaymentChinapayParameter;
import jp.co.sint.webshop.service.chinapay.PaymentChinapayResult;
import jp.co.sint.webshop.service.chinapay.PaymentChinapayResultBean;
import jp.co.sint.webshop.utility.BigDecimalUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
//import jp.co.sint.webshop.utility.PaymentConfig;
import jp.co.sint.webshop.utility.StringUtil;

public final class ChinapayUtil {

  private ChinapayUtil() {
  } 

  /**
   * 银联支付接口初期参数设定
   * @param parameter 支付
   * @param config
   * @return
   */
  public static PaymentChinapayResultBean initBean(PaymentChinapayParameter parameter,ChinapayConfig config,String type) {
    
    PaymentChinapayResultBean bean = new PaymentChinapayResultBean();
    
    //运行环境信息取得
     Locale locale = DIContainer.getLocaleContext().getCurrentLocale();
    
    //银联商户信息设置
    initMerchant(config,type);
    
    try {
      
      if (type.equals(PaymentMethodType.OUTER_CARD.getValue())) {
        //外卡支付网关号
        bean.setGateId(config.getOutGateId());
        //外卡支付请求URL
        bean.setPurchaseRequestUrl(config.getOutPurchaseRequestUrl());
      } else {
        //支付网关号
        bean.setGateId(config.getGateId());
        //支付请求URL
        bean.setPurchaseRequestUrl(config.getPurchaseRequestUrl());
      }

      //页面交易接入URL
      String returnUrl = config.getPageReturnUrl();
      if(locale != null){
        if (locale.getLanguage().equals("en")) {
          returnUrl = returnUrl.replace("zh-cn", "en-us");
        } else if (locale.getLanguage().equals("ja")) {
          returnUrl = returnUrl.replace("zh-cn", "ja-jp");
        } else if (locale.getLanguage().equals("zh")) {
          //returnUrl = returnUrl.replace("zh-cn", "");
        }
      }
      bean.setPageReturnUrl(returnUrl);
      
  
      //后台交易接入URL
      bean.setBackReturnUrl(config.getBackReturnUrl());
      //订单交易币种
      bean.setCurrencyId(config.getCurrencyId());
      //支付接入版本号
      bean.setVersion(config.getVersion());
      //ChinaPay商户编号
      bean.setMerchantId(config.getMerchantId());
      //交易订单号
      bean.setOrderId(convertOrderId(parameter.getOrderId(),config.getMerchantId()));
      //订单交易金额
      bean.setTransactionAmount(convertTransactionAmount(parameter.getAmount()));
      //订单交易日期
      // 20120416 wjh modify start
      //Date now = DateUtil.getSysdate();
      //bean.setTransactionDate(DateUtil.toDateTimeString(now, "yyyyMMdd"));
      bean.setTransactionDate(parameter.getTransDate());
      // 20120416 wjh modify end

      //订单交易类型
      bean.setTransactionType(config.getTransType());
      //商户私有域
      bean.setPrivate1(parameter.getOrderId());
    } catch (RuntimeException e) {
      Logger.getLogger(ChinapayUtil.class).error(e);
    }
    return bean;
  }
  
  // 内卡对应
  public static PaymentChinapayResultBean initInnerBean(PaymentChinapayParameter parameter,ChinapayInnerConfig config) {
    
    PaymentChinapayResultBean bean = new PaymentChinapayResultBean();
    
    //运行环境信息取得
     Locale locale = DIContainer.getLocaleContext().getCurrentLocale();
    
    //银联商户信息设置
     initInnerMerchant(config);
    
    try {
      //支付网关号
      bean.setGateId(config.getGateId());
      //支付请求URL
      bean.setPurchaseRequestUrl(config.getPurchaseRequestUrl());
      //页面交易接入URL
      String returnUrl = config.getPageReturnUrl();
      if(locale != null){
        if (locale.getLanguage().equals("en")) {
          returnUrl = returnUrl.replace("zh-cn", "en-us");
        } else if (locale.getLanguage().equals("ja")) {
          returnUrl = returnUrl.replace("zh-cn", "ja-jp");
        } else if (locale.getLanguage().equals("zh")) {
        }
      }
      bean.setPageReturnUrl(returnUrl);
      //后台交易接入URL
      bean.setBackReturnUrl(config.getBackReturnUrl());
      //订单交易币种
      bean.setCurrencyId(config.getCurrencyId());
      //支付接入版本号
      bean.setVersion(config.getVersion());
      //ChinaPay商户编号
      bean.setMerchantId(config.getMerchantId());
      //交易订单号
      bean.setOrderId(convertOrderId(parameter.getOrderId(),config.getMerchantId()));
      //订单交易金额
      bean.setTransactionAmount(convertTransactionAmount(parameter.getAmount()));
      //订单交易日期
      bean.setTransactionDate(parameter.getTransDate());
      //订单交易类型
      bean.setTransactionType(config.getTransType());
      //商户私有域
      bean.setPrivate1(parameter.getOrderId());
    } catch (RuntimeException e) {
      Logger.getLogger(ChinapayUtil.class).error(e);
    }
    return bean;
  }
  
  /**
   * 银联商户信息初始化
   * @param config
   */
  public static void initMerchant(ChinapayConfig config,String type) {
    try {
      //支付方式信息取得
      PaymentMethodDao dao = DIContainer.getDao(PaymentMethodDao.class);
      PaymentMethod pm = dao.load(type);
      
      //店主ID取得
      String merchantId = pm.getMerchantId();
      config.setMerchantId(merchantId);
      
      File file = null;
      
      //密钥文件取得
      file = DIContainer.getResourceFile("classpath:" + pm.getSecretKey());
      if (file == null || !file.exists()) {
        throw new FileNotFoundException(pm.getSecretKey());
      }
      config.setSecretKey(file);
      
      //公钥文件取得
      File publicKeyFile = null;
      String publicKeyName = "";
      if (type.equals(PaymentMethodType.OUTER_CARD.getValue())) {
        //外卡
        publicKeyName = config.getOutPublicKeyName();
      } else {
        //普通银联
        publicKeyName = config.getPublicKeyName();
      }
      publicKeyFile = DIContainer.getResourceFile(publicKeyName);
      if (publicKeyFile == null || !publicKeyFile.exists()) {
        throw new FileNotFoundException(pm.getSecretKey());
      }
      config.setPublicKey(publicKeyFile);
      
    } catch (Exception e) {
      Logger.getLogger(PaymentChinapayResult.class).error(e);
    }
  }
  
  /**
   * 银联商户信息初始化
   * @param config
   */
  //内卡对应
  public static void initInnerMerchant(ChinapayInnerConfig config) {
    try {
      //支付方式信息取得
      PaymentMethodDao dao = DIContainer.getDao(PaymentMethodDao.class);
      
      PaymentMethod pm = dao.load(PaymentMethodType.INNER_CARD.getValue());
      
      //店主ID取得
      String merchantId = pm.getMerchantId();
      config.setMerchantId(merchantId);
      
      File file = null;
      
      //密钥文件取得
      file = DIContainer.getResourceFile("classpath:" + pm.getSecretKey());
      if (file == null || !file.exists()) {
        throw new FileNotFoundException(pm.getSecretKey());
      }
      config.setSecretKey(file);
      
      //公钥文件取得
      File publicKeyFile = null;
      publicKeyFile = DIContainer.getResourceFile(config.getPublicKeyName());
      if (publicKeyFile == null || !publicKeyFile.exists()) {
        throw new FileNotFoundException(pm.getSecretKey());
      }
      config.setPublicKey(publicKeyFile);
      
    } catch (Exception e) {
      Logger.getLogger(PaymentChinapayResult.class).error(e);
    }
  }
  
  /**
   * 数字签名做成
   * @param arguments 需要数字签名的参数数组
   * @param parameter 数字签名的密钥参数
   * @return
   */
  public static String getCheckValue(Object[] arguments, ChinapayConfig config) {
    String checkValue = "";
    try {
      StringBuilder strFormat = new StringBuilder("");
      for (int i =0; i<arguments.length;i++ ) {
        arguments[i] = adjustValue(String.valueOf(arguments[i]));
        strFormat.append("{" + String.valueOf(i) + "}");
      }
      String signData = MessageFormat.format(strFormat.toString(), arguments);
      PrivateKey privateKey = new PrivateKey();
      privateKey.buildKey(config.getMerchantId(), 0, config.getSecretKey().getAbsolutePath());
      SecureLink link = new SecureLink(privateKey);
      checkValue = link.Sign(signData);
    } catch (Exception e) {
      //throw new RuntimeException(e);
    }
    return checkValue;
  }
  // 内卡对应
  public static String getInnerCheckValue(Object[] arguments, ChinapayInnerConfig config) {
    String checkValue = "";
    try {
      StringBuilder strFormat = new StringBuilder("");
      for (int i =0; i<arguments.length;i++ ) {
        arguments[i] = adjustValue(String.valueOf(arguments[i]));
        strFormat.append("{" + String.valueOf(i) + "}");
      }
      String signData = MessageFormat.format(strFormat.toString(), arguments);
      PrivateKey privateKey = new PrivateKey();
      privateKey.buildKey(config.getMerchantId(), 0, config.getSecretKey().getAbsolutePath());
      SecureLink link = new SecureLink(privateKey);
      checkValue = link.Sign(signData);
    } catch (Exception e) {
      //throw new RuntimeException(e);
    }
    return checkValue;
  }
  
  
  /**
   * 从请求URL取得银联通知参数
   * @param requestMap
   * @return
   */
  public static PaymentChinapayResultBean createPaymentResponse(Map<String, String[]> requestMap) {
    PaymentChinapayResultBean bean = new PaymentChinapayResultBean();
    bean.setMerchantId(get("merid", requestMap));// 商户号
    bean.setOrderId(get("orderno", requestMap));// 交易流水号
    bean.setTransactionAmount(get("amount", requestMap));// 交易金额
    bean.setTransactionStatus(get("status", requestMap));// 是否成功 
    bean.setTransactionType(get("transtype", requestMap)); // 交易类型  
    bean.setTransactionDate(get("transdate", requestMap));// 日期 
    bean.setCurrencyId(get("currencycode", requestMap));// 货币类型
    bean.setCheckValue(get("checkvalue", requestMap));// 数字签名
    bean.setPrivate1(get("Priv1", requestMap));// 订单号
    return bean;
  }
 
 /**
  * 银联通知数字签名验证
  * @param bean 银联通知参数列表
  * @param config 银联接口参数
  * @return 验证结果
  */
  public static boolean verifyPaymentResponse(PaymentChinapayResultBean bean, ChinapayConfig config) {
    PrivateKey privateKey = new PrivateKey();
    privateKey.buildKey(config.getChinaPayMerchantId(), 0, config.getPublicKey().getAbsolutePath());
    SecureLink link = new SecureLink(privateKey);
    return link.verifyTransResponse(adjustValue(bean.getMerchantId()), adjustValue(bean.getOrderId()), adjustValue(bean.getTransactionAmount()),
        adjustValue(bean.getCurrencyId()), adjustValue(bean.getTransactionDate()), adjustValue(bean
        .getTransactionType()), adjustValue(bean.getTransactionStatus()), adjustValue(bean.getCheckValue()));
  }
  // 内卡对应
  public static boolean verifyInnerPaymentResponse(PaymentChinapayResultBean bean, ChinapayInnerConfig config) {
    PrivateKey privateKey = new PrivateKey();
    privateKey.buildKey(config.getChinaPayMerchantId(), 0, config.getPublicKey().getAbsolutePath());
    SecureLink link = new SecureLink(privateKey);
    return link.verifyTransResponse(adjustValue(bean.getMerchantId()), adjustValue(bean.getOrderId()), adjustValue(bean.getTransactionAmount()),
        adjustValue(bean.getCurrencyId()), adjustValue(bean.getTransactionDate()), adjustValue(bean
        .getTransactionType()), adjustValue(bean.getTransactionStatus()), adjustValue(bean.getCheckValue()));
  }
 
  /**
   * 银联支付状态查询
   */
  public static PaymentChinapayResultBean queryRequest(PaymentChinapayResultBean bean,ChinapayConfig config) {
    Logger logger = Logger.getLogger(ChinapayUtil.class);
    Map<String, Object> map = new HashMap<String, Object>();
    PaymentChinapayResultBean resultBean = new PaymentChinapayResultBean();
    try {
      
      StringBuffer result = new StringBuffer();
      StringBuilder sb = new StringBuilder();
      //银联商户ID
      sb.append("MerId=").append(bean.getMerchantId());
      //交易类型
      sb.append("&TransType=").append(bean.getTransactionType());
      //交易编号
      sb.append("&OrdId=").append(bean.getOrderId());
      //交易日期
      sb.append("&TransDate=").append(bean.getTransactionDate());
      //查询接口版本号
      sb.append("&Version=").append(bean.getVersion());
      //私有域
      sb.append("&Resv=").append(bean.getPrivate1());
      //关键数据数字签名
      String checkValue = getCheckValue(new Object[]{bean.getMerchantId(),bean.getTransactionDate()
          ,bean.getOrderId(),bean.getTransactionType()},config);
      sb.append("&ChkValue=" + checkValue);
      logger.info("FIND START");
      result = connectURL(sb , config.getRequestUrl());
      logger.info("FIND END");
      
      String[] codeValue = result.toString().split("<body>")[1].split("</body>")[0].trim().split("&");
      String sKey = StringUtil.EMPTY;
      String sValue = StringUtil.EMPTY;
    
      for (int i = 0; i < codeValue.length; i++) {
        sKey = codeValue[i].split("=")[0];
        sValue = codeValue[i].split("=")[1];
        map.put(sKey, sValue);
      }
      resultBean.setResponseCode(String.valueOf(map.get("ResponeseCode"))); // 应答编号
      resultBean.setMerchantId(String.valueOf(map.get("merid")));           // 银联商户ID
      resultBean.setOrderId(String.valueOf(map.get("orderno")));            // 交易订单编号 
      //delete by cs_yuli 20120409 start
      //resultBean.setTransactionAmount(String.valueOf(map.get("amount")));   // 交易金额
      // resultBean.setTransactionDate(String.valueOf(map.get("transdate")));  // 交易日期
      //delete by cs_yuli 20120409 end
      // 20120416 wjh modify start
//      resultBean.setTransactionDate(String.format(String.valueOf(map.get("transdate")),"yyyyMMdd"));  // 交易日期 
      resultBean.setTransactionDate(DateUtil.toDateTimeString(DateUtil.parseString(String.valueOf(map.get("transdate")),"yyyyMMdd"), DateUtil.ALIPAY_DATE_FORMAT));   // 交易日期
      // 20120416 wjh modify end 
      resultBean.setTransactionAmount(BigDecimal.valueOf(Double.parseDouble(String.valueOf(map.get("amount")))).divide(new BigDecimal(100)).toString());   // 交易金额
      resultBean.setTransactionType(String.valueOf(map.get("transtype")));  // 交易类型
      resultBean.setTransactionStatus(String.valueOf(map.get("status")));   // 交易状态
      resultBean.setCheckValue(String.valueOf(map.get("checkvalue")));      // 数字签名
      resultBean.setGateId(String.valueOf(map.get("GateId")));              // 网关ID
      resultBean.setPrivate1(String.valueOf(map.get("Priv1")));             // 用户私有域
    } catch (RuntimeException e) {
      logger.error(e);
    }
    return resultBean;
  }
  
  /**
   * 银联支付状态查询
   */
  // 内卡对应
  public static PaymentChinapayResultBean queryInnerRequest(PaymentChinapayResultBean bean,ChinapayInnerConfig config) {
    Logger logger = Logger.getLogger(ChinapayUtil.class);
    Map<String, Object> map = new HashMap<String, Object>();
    PaymentChinapayResultBean resultBean = new PaymentChinapayResultBean();
    try {
      
      StringBuffer result = new StringBuffer();
      StringBuilder sb = new StringBuilder();
      //银联商户ID
      sb.append("MerId=").append(bean.getMerchantId());
      //交易类型
      sb.append("&TransType=").append(bean.getTransactionType());
      //交易编号
      sb.append("&OrdId=").append(bean.getOrderId());
      //交易日期
      sb.append("&TransDate=").append(bean.getTransactionDate());
      //查询接口版本号
      sb.append("&Version=").append(bean.getVersion());
      //私有域
      sb.append("&Resv=").append(bean.getPrivate1());
      //关键数据数字签名
      String checkValue = getInnerCheckValue(new Object[]{bean.getMerchantId(),bean.getTransactionDate()
          ,bean.getOrderId(),bean.getTransactionType()},config);
      sb.append("&ChkValue=" + checkValue);
      logger.info("FIND START");
      result = connectURL(sb , config.getRequestUrl());
      logger.info("FIND END");
      
      String[] codeValue = result.toString().split("<body>")[1].split("</body>")[0].trim().split("&");
      String sKey = StringUtil.EMPTY;
      String sValue = StringUtil.EMPTY;
    
      for (int i = 0; i < codeValue.length; i++) {
        sKey = codeValue[i].split("=")[0];
        sValue = codeValue[i].split("=")[1];
        map.put(sKey, sValue);
      }
      resultBean.setResponseCode(String.valueOf(map.get("ResponeseCode"))); // 应答编号
      resultBean.setMerchantId(String.valueOf(map.get("merid")));           // 银联商户ID
      resultBean.setOrderId(String.valueOf(map.get("orderno")));            // 交易订单编号 
      resultBean.setTransactionDate(DateUtil.toDateTimeString(DateUtil.parseString(String.valueOf(map.get("transdate")),"yyyyMMdd"), DateUtil.ALIPAY_DATE_FORMAT));   // 交易日期
      resultBean.setTransactionAmount(BigDecimal.valueOf(Double.parseDouble(String.valueOf(map.get("amount")))).divide(new BigDecimal(100)).toString());   // 交易金额
      resultBean.setTransactionType(String.valueOf(map.get("transtype")));  // 交易类型
      resultBean.setTransactionStatus(String.valueOf(map.get("status")));   // 交易状态
      resultBean.setCheckValue(String.valueOf(map.get("checkvalue")));      // 数字签名
      resultBean.setGateId(String.valueOf(map.get("GateId")));              // 网关ID
      resultBean.setPrivate1(String.valueOf(map.get("Priv1")));             // 用户私有域
    } catch (RuntimeException e) {
      logger.error(e);
    }
    return resultBean;
  }
  
  
  /**
   * 银联查询请求信息做成
   * @param sb
   * @param requestURL
   * @return
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
  
  /**
   * 银联交易订单号做成
   * @param wsOrderNo EC侧订单编号
   * @param merchantId  Chinapay商户编号
   * @return
   */
  private static String convertOrderId(String wsOrderNo, String merchantId) {
    /*String orderId = "";
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
    }*/
    return StringUtil.addZero(wsOrderNo, 16);
    //return orderId;
  }
  
  /**
   * 交易金额Format化
   * @param amount  交易金额
   * @return
   */
  private static String convertTransactionAmount(BigDecimal amount) {
    DecimalFormat df = new DecimalFormat("000000000000");
    return df.format(BigDecimalUtil.multiply(amount, new BigDecimal(100)).longValue());
  }
  
  
  
  /**
   * 从MAP取得指定key的属性
   * @param name
   * @param src
   * @return
   */
  private static String get(String name, Map<String, String[]> src) {
    return get(name, src, 0);
  }
  
  /**
   * 从MAP取得指定key的属性
   * @param name
   * @param src
   * @param index
   * @return
   */
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
  
  /**
   * 剔除参数空格
   * @param s 
   * @return
   */
  private static String adjustValue(String s) {
    String v = "";
    if (s != null) {
      v = s.trim();
    }
    return v;
  }
}
