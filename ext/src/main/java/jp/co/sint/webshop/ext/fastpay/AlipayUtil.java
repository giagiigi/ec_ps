package jp.co.sint.webshop.ext.fastpay;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import jp.co.sint.webshop.service.fastpay.FastpayAlipayParameter;
import jp.co.sint.webshop.service.fastpay.FastpayAlipayResultBean;
import jp.co.sint.webshop.utility.StringUtil;

public class AlipayUtil {

  @SuppressWarnings("unused")
  private static final String PAYMENT_TYPE = "1";

  public static FastpayAlipayResultBean initBean(FastpayAlipayParameter parameter, FastpayAlipayConfig config) {
    FastpayAlipayResultBean bean = new FastpayAlipayResultBean();
    // 支付宝请求URL 
    bean.setRequestUrl(config.getRequestUrl());
    // 接口名称
    bean.setService(parameter.getServiceId());
    // 合作伙伴ID
    bean.setPartner(parameter.getMerchantId());
    // 参数编码字符集
    bean.setInputCharset(config.getInputCharset());
    // 返回URL
    bean.setReturnUrl(config.getReturnUrl());
  
    // 通知URL
    bean.setNotifyUrl(config.getNotifyUrl());
    String mySign = AlipayUtil.Sign(bean, parameter.getSecretKey());
    // 签名
    bean.setSign(mySign);
    // 签名方式
    bean.setSignType(config.getSignType());

    return bean;
  }

  private static String Sign(FastpayAlipayResultBean bean, String key) {
    Map<String, String> sPara = new HashMap<String, String>();
    sPara.put("service", "alipay.auth.authorize");
    sPara.put("target_service", "user.auth.quick.login");
    sPara.put("partner", bean.getPartner());
    sPara.put("return_url", bean.getReturnUrl());
    sPara.put("_input_charset", bean.getInputCharset());
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
    String mysign = AlipayMd5Encrypt.md5(prestr);
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
