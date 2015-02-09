package jp.co.sint.webshop.ext.alipay;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import jp.co.sint.webshop.service.alipay.PaymentAlipayParameter;
import jp.co.sint.webshop.service.alipay.PaymentAlipayProvider;
import jp.co.sint.webshop.service.alipay.PaymentAlipayResult;
import jp.co.sint.webshop.service.alipay.PaymentAlipayResultBean;
import jp.co.sint.webshop.service.alipay.PaymentAlipayResultImpl;
import jp.co.sint.webshop.service.order.AliPaymentResult;
import jp.co.sint.webshop.utility.DIContainer;

public class AlipayCard implements PaymentAlipayProvider {

  /**
   * 支付宝通知
   */
  public PaymentAlipayResult advice(PaymentAlipayParameter parameter) {
    // TODO Auto-generated method stub
    return null;
  }

  /**
   * 支付宝支付
   */
  public PaymentAlipayResult payment(PaymentAlipayParameter parameter) {
    PaymentAlipayResultImpl impl = new PaymentAlipayResultImpl();
    AlipayConfig config = DIContainer.get(AlipayConfig.class.getSimpleName());
    // 支付宝参数初期化
    PaymentAlipayResultBean bean = AlipayUtil.initBean(parameter, config);
    impl.setResultBean(bean);
    return impl;
  }

  /**
   * 支付宝查询
   */
  public PaymentAlipayResult find(PaymentAlipayParameter parameter) {
    Logger logger = Logger.getLogger(this.getClass());
    PaymentAlipayResultImpl impl = new PaymentAlipayResultImpl();
    AlipayConfig config = DIContainer.get(AlipayConfig.class.getSimpleName());
    StringBuffer result = new StringBuffer();
    Map<String, Object> mapRusult = new HashMap<String, Object>();

    // 支付宝参数初期化
    Map<String, String> sPara = new HashMap<String, String>();
    sPara.put("service", "single_trade_query");
    sPara.put("partner", parameter.getMerchantId());
    sPara.put("_input_charset", config.getInputCharset());
    sPara.put("sign_type", config.getSignType());
    sPara.put("out_trade_no", parameter.getOrderId());
    Map<String, String> sParaNew = paraFilter(sPara); // 除去数组中的空值和签名参数

    StringBuilder sb = new StringBuilder();
    sb.append("service=").append("single_trade_query");
    sb.append("&partner=").append(parameter.getMerchantId());
    sb.append("&_input_charset=").append(config.getInputCharset());
    sb.append("&sign_type=").append(config.getSignType());
    sb.append("&sign=").append(AlipayUtil.BuildMysign(sParaNew, parameter.getSecretKey()));
    sb.append("&out_trade_no=" + parameter.getOrderId());

    result = connectURL(sb, config.getRequestUrl());

    String resultStr = result.toString();
    try {
      if (result != null) {
        if (result.indexOf("<subject>") != -1 && result.indexOf("</subject>") != -1) {
          resultStr = result.substring(0, result.indexOf("<subject>")) + result.substring(result.indexOf("</subject>") + 10);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    mapRusult = setAlipayResult("<alipay>" + resultStr.split("<alipay>")[1]);

    PaymentAlipayResultBean bean = new PaymentAlipayResultBean();
    bean.setIsSuccess(mapRusult.get("IS_SUCCESS").toString());
    if (mapRusult.get("IS_SUCCESS").toString().equals("T")) {
      bean.setTradeStatus(mapRusult.get("TRADE_STATUS").toString());
      if (mapRusult.get("GMT_PAYMENT") != null) {
        bean.setGmtPayment(mapRusult.get("GMT_PAYMENT").toString());
      }
      bean.setTotalFee(mapRusult.get("TOTAL_FEE").toString());
      bean.setOutTradeNo(mapRusult.get("TRADE_NO").toString());
    } else {
      bean.setError(mapRusult.get("ERROR").toString());
    }
    impl.setResultBean(bean);
    logger.info("支付宝查询: " + parameter.getOrderId() + "：" + bean.getIsSuccess() + "：" + bean.getError());
    return impl;
  }

  /**
   * 支付宝查询返回結果
   */
  private static Map<String, Object> setAlipayResult(String strResult) {

    Map<String, Object> alipayResultMap = new HashMap<String, Object>();

    try {
      Document doc = transferStringToDocument1(strResult.toUpperCase());
      NodeList list = doc.getChildNodes();

      for (int i = 0; i < list.getLength(); i++) {
        Node sublist = list.item(i);
        NodeList listInfo = sublist.getChildNodes();

        if (listInfo.getLength() == 2) {
          alipayResultMap.put("IS_SUCCESS", listInfo.item(0).getTextContent().toUpperCase());
          alipayResultMap.put("ERROR", listInfo.item(1).getTextContent().toUpperCase());

        } else {
          setNodeMap(listInfo, alipayResultMap);
          // for (int j = 0; j < listInfo.getLength(); j++) {
          // Node node = listInfo.item(j);
          // NodeList listMeta = node.getChildNodes();
          // if (j == 2) {
          // alipayResultMap = getTradeNodeMap(node);
          // }
          //
          // for (int k = 0; k < listMeta.getLength(); k++) {
          //
          // if (j == 0) {
          // is_success = listMeta.item(0).getTextContent();
          // } else if (j == 1) {
          // out_trade_no = listMeta.item(2).getTextContent();
          // } else {
          // alipayResultMap.put("IS_SUCCESS", is_success.toUpperCase());
          // alipayResultMap.put("OUT_TRADE_NO", out_trade_no.toUpperCase());
          // }
          // }
          // }
        }
      }

    } catch (ParserConfigurationException e) {
      e.printStackTrace();
    } catch (SAXException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

    return alipayResultMap;
  }

  /**
   * 支付宝关闭
   */
  public PaymentAlipayResult close(PaymentAlipayParameter parameter) {
    PaymentAlipayResultImpl impl = new PaymentAlipayResultImpl();
    AlipayConfig config = DIContainer.get(AlipayConfig.class.getSimpleName());
    StringBuffer result = new StringBuffer();

    // 支付宝参数初期化
    Map<String, String> sPara = new HashMap<String, String>();
    sPara.put("service", "close_trade");
    sPara.put("partner", parameter.getMerchantId());
    sPara.put("sign_type", config.getSignType());
    sPara.put("out_order_no", parameter.getOrderId());
    Map<String, String> sParaNew = paraFilter(sPara); // 除去数组中的空值和签名参数

    StringBuilder sb = new StringBuilder();
    sb.append("service=").append("close_trade");
    sb.append("&partner=").append(parameter.getMerchantId());
    sb.append("&sign_type=").append(config.getSignType());
    sb.append("&sign=").append(AlipayUtil.BuildMysign(sParaNew, parameter.getSecretKey()));
    sb.append("&out_order_no=" + parameter.getOrderId());

    result = connectURL(sb, config.getRequestUrl());

    AliPaymentResult aliPaymentResult = setAlipayCloseResult("<alipay>" + result.toString().split("<alipay>")[1]);

    PaymentAlipayResultBean bean = new PaymentAlipayResultBean();
    bean.setIsSuccess(aliPaymentResult.getIs_success());
    bean.setError(aliPaymentResult.getError());

    impl.setResultBean(bean);

    return impl;
  }

  /**
   * 关闭支付宝交易返回结果
   */
  private static AliPaymentResult setAlipayCloseResult(String strResult) {
    AliPaymentResult alipayResult = new AliPaymentResult();

    try {
      Document doc = transferStringToDocument1(strResult.toUpperCase());
      NodeList list = doc.getChildNodes();

      for (int i = 0; i < list.getLength(); i++) {
        Node sublist = list.item(i);
        NodeList listInfo = sublist.getChildNodes();

        alipayResult.setIs_success(listInfo.item(0).getTextContent());
        if (listInfo.getLength() > 1) {
          alipayResult.setError(listInfo.item(1).getTextContent());
        }
      }

    } catch (ParserConfigurationException e) {
      e.printStackTrace();
    } catch (SAXException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

    return alipayResult;
  }

  /**
   * 连接URL发送请求
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
      BufferedReader rd = new BufferedReader(new InputStreamReader(is, "utf-8"));

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
   * 解析xml格式字符串
   * 
   * @param s
   * @return
   */
  private static Document transferStringToDocument1(String s) throws ParserConfigurationException, SAXException, IOException {
    StringReader reader = new StringReader(s);
    InputSource is = new InputSource(reader);
    DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
    Document doc = builder.parse(is);

    return doc;
  }

  public PaymentAlipayParameter createParameterInstance() {
    return new PaymentAlipayParameter();
  }

  /**
   * 功能：除去数组中的空值和签名参数
   * 
   * @param sArray
   *          加密参数组
   * @return 去掉空值与签名参数后的新加密参数组
   */
  public static Map<String, String> paraFilter(Map<String, String> sArray) {
    List<String> keys = new ArrayList<String>(sArray.keySet());
    Map<String, String> sArrayNew = new HashMap<String, String>();

    for (int i = 0; i < keys.size(); i++) {
      String key = (String) keys.get(i);
      String value = (String) sArray.get(key);

      if (value == null) {
        continue;
      } else {
        if (value.equals("") || key.equalsIgnoreCase("sign") || key.equalsIgnoreCase("sign_type")) {
          continue;
        }
      }
      sArrayNew.put(key, value);
    }

    return sArrayNew;
  }

  /**
   * 把Node解析成Map对象，键值对
   * 
   * @param element
   * @return
   */
  public static Map<String, Object> getTradeNodeMap(Node element) {

    Map<String, Object> map = new HashMap<String, Object>();
    NodeList nodeList = element.getFirstChild().getChildNodes();
    if (nodeList != null) {
      for (int i = 0; i < nodeList.getLength(); i++) {
        Node node = nodeList.item(i);
        if (node.getNodeName() != null && node.getFirstChild() != null) {
          map.put(node.getNodeName(), node.getFirstChild().getNodeValue() == null ? "" : node.getFirstChild().getNodeValue());
        } else if (node.getNodeName() != null && node.getFirstChild() == null) {
          map.put(node.getNodeName(), "");
        }

      }
    }
    return map;
  }

  private static void setNodeMap(NodeList nodeList, Map<String, Object> alipayResultMap) {
    if (nodeList.getLength() > 0) {
      for (int i = 0; i < nodeList.getLength(); i++) {
        Node node = nodeList.item(i);
        if (node.getFirstChild() != null && node.getFirstChild().getNodeValue() != null) {
          alipayResultMap.put(node.getNodeName(), node.getTextContent());
        }
        NodeList listInfo = node.getChildNodes();
        if (listInfo.getLength() > 0) {
          setNodeMap(listInfo, alipayResultMap);
        }
      }
    }
  }
}
