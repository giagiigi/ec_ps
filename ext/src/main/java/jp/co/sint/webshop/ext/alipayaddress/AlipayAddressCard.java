package jp.co.sint.webshop.ext.alipayaddress;

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

 
import jp.co.sint.webshop.service.alipayaddress.AlipayAddressParameter;
import jp.co.sint.webshop.service.alipayaddress.AlipayAddressProvider;
import jp.co.sint.webshop.service.alipayaddress.AlipayAddressResult;
import jp.co.sint.webshop.service.alipayaddress.AlipayAddressResultBean;
import jp.co.sint.webshop.service.alipayaddress.AlipayAddressResultImpl;
import jp.co.sint.webshop.service.order.AliPaymentResult;
import jp.co.sint.webshop.utility.DIContainer;

public class AlipayAddressCard implements AlipayAddressProvider {

  /**
   * 支付宝通知
   */ 
  public AlipayAddressResult advice(AlipayAddressParameter parameter) {
    // TODO Auto-generated method stub
    return null;
  }

  /**
   * 支付宝支付
   */
  public AlipayAddressResult payment(AlipayAddressParameter parameter) {
    AlipayAddressResultImpl impl = new AlipayAddressResultImpl();
    AlipayAddressConfig config = DIContainer.get(AlipayAddressConfig.class.getSimpleName());
    // 支付宝参数初期化
    AlipayAddressResultBean bean = AlipayUtil.initBean(parameter, config);
    impl.setResultBean(bean);
    return impl;
  }

  /**
   * 支付宝查询
   */
  public AlipayAddressResult find(AlipayAddressParameter parameter) {
    @SuppressWarnings("unused")
    Logger logger = Logger.getLogger(this.getClass());
    AlipayAddressResultImpl impl = new AlipayAddressResultImpl();
    AlipayAddressConfig config = DIContainer.get(AlipayAddressConfig.class.getSimpleName());
    StringBuffer result = new StringBuffer();
    @SuppressWarnings("unused")
    Map<String, Object> mapRusult = new HashMap<String, Object>();

    // 支付宝参数初期化
    Map<String, String> sPara = new HashMap<String, String>();
    sPara.put("service", "single_trade_query");
    sPara.put("partner", parameter.getMerchantId());
    sPara.put("_input_charset", config.getInputCharset());
    sPara.put("sign_type", config.getSignType());
    sPara.put("out_trade_no", parameter.getOrderId());
    sPara.put("receive_address", parameter.getAddress());
    sPara.put("receive_name", config.getReceiveName());
    sPara.put("receive_zip", config.getReceiveZip());
    sPara.put("receive_phone", parameter.getReceivePhone());
    sPara.put("receive_mobile", parameter.getReceiveMobile());    
    Map<String, String> sParaNew = paraFilter(sPara); // 除去数组中的空值和签名参数

    StringBuilder sb = new StringBuilder();
    sb.append("service=").append("single_trade_query");
    sb.append("&partner=").append(parameter.getMerchantId());
    sb.append("&_input_charset=").append(config.getInputCharset());
    sb.append("&sign_type=").append(config.getSignType());
    sb.append("&sign=").append(AlipayUtil.BuildMysign(sParaNew, parameter.getSecretKey()));
    sb.append("&out_trade_no=" + parameter.getOrderId());
    sb.append("&receive_address=").append(parameter.getAddress());
    sb.append("&receive_name=").append(config.getReceiveName());
    sb.append("&receive_zip=").append(config.getReceiveZip());
    sb.append("&receive_phone=").append(config.getReceivePhone() );
    sb.append("&receive_mobile=" + parameter.getReceiveMobile());
    result = connectURL(sb, config.getRequestUrl());

    mapRusult = setAlipayResult("<alipay>" + result.toString().split("<alipay>")[1]);

    AlipayAddressResultBean bean = new AlipayAddressResultBean();

    impl.setResultBean(bean);

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
  public AlipayAddressResult close(AlipayAddressParameter parameter) {
    AlipayAddressResultImpl impl = new AlipayAddressResultImpl();
    AlipayAddressConfig config = DIContainer.get(AlipayAddressConfig.class.getSimpleName());
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

    @SuppressWarnings("unused")
    AliPaymentResult aliPaymentResult = setAlipayCloseResult("<alipay>" + result.toString().split("<alipay>")[1]);

    AlipayAddressResultBean bean = new AlipayAddressResultBean();


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

  public AlipayAddressParameter createParameterInstance() {
    return new AlipayAddressParameter();
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
