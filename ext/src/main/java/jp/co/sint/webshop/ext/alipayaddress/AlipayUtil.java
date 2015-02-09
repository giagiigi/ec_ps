package jp.co.sint.webshop.ext.alipayaddress;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


import jp.co.sint.webshop.service.alipayaddress.AlipayAddressParameter;
import jp.co.sint.webshop.service.alipayaddress.AlipayAddressResultBean;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.PaymentConfig;
import jp.co.sint.webshop.utility.StringUtil;

public class AlipayUtil {

  @SuppressWarnings("unused")
  private static final String PAYMENT_TYPE = "1";

  public static AlipayAddressResultBean initBean(AlipayAddressParameter parameter, AlipayAddressConfig config) {
    AlipayAddressResultBean bean = new AlipayAddressResultBean();
    //运行环境信息取得
    Locale locale = DIContainer.getLocaleContext().getCurrentLocale();
    PaymentConfig pc = DIContainer.get(PaymentConfig.class.getSimpleName());
    
    // 支付宝请求URL 
    bean.setRequestUrl(config.getRequestUrl());
    // 接口名称
    bean.setService(parameter.getServiceId());
    // 合作伙伴ID
    bean.setPartner(parameter.getMerchantId());
    // 参数编码字符集
    bean.setInputCharset(config.getInputCharset());
    // 返回URL
    if(locale != null){
      if(locale.getLanguage().equals("en")){
        bean.setReturnUrl(pc.getNetUrl()+"/front/en-us/fastpay/return"); 
      }else if(locale.getLanguage().equals("ja")){
        bean.setReturnUrl(pc.getNetUrl()+"/front/ja-jp/fastpay/return"); 
      }else if(locale.getLanguage().equals("zh")){
        bean.setReturnUrl(pc.getNetUrl()+"/front/zh-cn/fastpay/return"); 
      }else{
        bean.setReturnUrl(config.getReturnUrl());
      }
    }else{
      bean.setReturnUrl(config.getReturnUrl());
    }
  
    // 通知URL
    bean.setNotifyUrl(config.getNotifyUrl());
    String mySign = AlipayUtil.Sign(bean, parameter.getSecretKey());
    // 签名
    bean.setSign(mySign);
    // 签名方式
    bean.setSignType(config.getSignType());

    bean.setAddress(config.getAddress());
    
    
    bean.setReceiveZip(config.getReceiveZip());
    
    bean.setReceiveName(config.getReceiveName());
    
    bean.setReceivePhone(config.getReceivePhone());
    
    bean.setReceiveMobile(config.getReceiveMobile());
    
    return bean;
  }
 
  private static String Sign(AlipayAddressResultBean bean, String key) {
    Map<String, String> sPara = new HashMap<String, String>();
    sPara.put("service", "alipay.auth.authorize");
    sPara.put("target_service", "user.auth.quick.login");
    sPara.put("partner", bean.getPartner());
    sPara.put("return_url", bean.getReturnUrl());
    sPara.put("_input_charset", bean.getInputCharset());
    sPara.put("receive_address", bean.getAddress());
    sPara.put("receive_name", bean.getReceiveName());
    sPara.put("receive_zip", bean.getReceiveZip());
    sPara.put("receive_phone", bean.getReceivePhone());
    sPara.put("receive_mobile", bean.getReceiveMobile());
    
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
    Map<String, String> sArrayNew = new HashMap<String, String>();
    if (sArray == null || sArray.size() <= 0) {
      return sArrayNew;
  }
    for (String key : sArray.keySet()) {
      String value = sArray.get(key);
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
    String mysign = AlipayAddressMd5Encrypt.md5(prestr);
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
