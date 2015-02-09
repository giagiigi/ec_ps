package jp.co.sint.webshop.ext.sms;

// import
// jp.co.sint.webshop.service.sms.SmsProvider;
// import jp.co.sint.webshop.service.sms.SmsResult;
// import jp.co.sint.webshop.service.sms.SmsResult.PhoneBean;
// public class SmsUtil implements SmsProvider {
public class SmsUtil {

  // private final static String[] hexDigits = {
  // "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e",
  // "f"
  // };

  public SmsUtil() {
  }

  // public static SmsConfig getSmsConfig() {
  // return DIContainer.get(SmsConfig.class.getSimpleName());
  // }
  //  
  // /**
  // * 短信发送
  // * @param phone
  // * 手机号码
  // * @param content
  // * 短信内容
  // * @return
  // */
  // public SmsResult sendSms(String phone, String content){
  // return sendSms(phone, content, "");
  // }
  //  
  // /**
  // * 短信发送
  // * @param phone
  // * 手机号码
  // * @param content
  // * 短信内容
  // * @param sendTime
  // * 发送时间
  // * @return
  // */
  // public SmsResult sendSms(String phone, String content, String sendTime) {
  //    
  // StringBuffer result = new StringBuffer();
  //    
  // SmsConfig cfg = getSmsConfig();
  // StringBuilder sb = new StringBuilder();
  // sb.append("Account=").append(cfg.getAccount().toString());
  // sb.append("&Password=").append(MD5Encode(cfg.getPassword().toString()));
  // sb.append("&SubCode=").append("");
  // sb.append("&Phone=").append(phone);
  //    
  // sb.append("&SendTime=" + sendTime);
  //    
  // try {
  // //内容编码的设置
  // sb.append("&Content=").append(java.net.URLEncoder.encode(content,
  // "UTF-8"));
  // // 请求内容
  // String httpContent = sb.toString();
  // URL url = new URL(cfg.getSendUrl());
  // HttpURLConnection httpUrlConnection = (HttpURLConnection)
  // url.openConnection();
  // httpUrlConnection.setConnectTimeout(30000); // 设置连接主机超时（单位：毫秒）
  // httpUrlConnection.setReadTimeout(30000); // 设置从主机读取数据超时
  // httpUrlConnection.setDoInput(true);
  // httpUrlConnection.setDoOutput(true);
  // httpUrlConnection.setUseCaches(false);
  // httpUrlConnection.setRequestProperty("Content-type",
  // "application/x-www-form-urlencoded;text/html;charset=GBK");
  // httpUrlConnection.setRequestProperty("Content-Length",
  // String.valueOf(httpContent.length()));
  // httpUrlConnection.setRequestMethod("POST");
  //  
  // OutputStream outStrm = httpUrlConnection.getOutputStream();
  //  
  // byte[] data = httpContent.getBytes();
  // outStrm.write(data);
  // outStrm.flush();
  // outStrm.close();
  //  
  // // 实际发送请求
  // InputStream is = httpUrlConnection.getInputStream();
  // BufferedReader rd = new BufferedReader(new InputStreamReader(is));
  //      
  // String line;
  // while ((line = rd.readLine()) != null) {
  // result.append(line);
  // }
  //      
  // rd.close();
  // is.close();
  // } catch (MalformedURLException e) {
  // e.printStackTrace();
  // } catch (IOException e) {
  // e.printStackTrace();
  // }
  //    
  // return setSmsResult(result.toString());
  // }
  //  
  // private SmsResult setSmsResult(String strResult) {
  // SmsResult smsResult = new SmsResult();
  //
  // try {
  // Document doc =
  // transferStringToDocument1(strResult.replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?>",
  // "").trim().toUpperCase());
  // NodeList list = doc.getChildNodes();
  // List<PhoneBean> phoneResult = new ArrayList<PhoneBean>();
  //      
  // for (int i=0;i< list.getLength();i++) {
  // Node sublist = list.item(i);
  // NodeList listInfo = sublist.getChildNodes();
  //
  // for (int j= 0; j<listInfo.getLength();j++) {
  // Node node = listInfo.item(j);
  // NodeList listMeta = node.getChildNodes();
  //         
  // if (j == 0) {
  // smsResult.setResponse(listMeta.item(0).getTextContent());
  // } else {
  // PhoneBean phoneInfo = new PhoneBean();
  // String a[] = new String[2];
  // for (int k= 0; k < listMeta.getLength();k++) {
  // a[k]=listMeta.item(k).getTextContent();
  // }
  // phoneInfo.setPhone(a[0]);
  // phoneInfo.setSmsID(a[1].toLowerCase());
  // phoneResult.add(phoneInfo);
  // }
  // }
  // }
  //      
  // smsResult.setResult(phoneResult);
  //      
  // } catch (ParserConfigurationException e) {
  // e.printStackTrace();
  // } catch (SAXException e) {
  // e.printStackTrace();
  // } catch (IOException e) {
  // e.printStackTrace();
  // }
  //    
  // return smsResult;
  // }
  //  
  // //短信状态报告
  // public SmsResult smsReport(String smsid) {
  //    
  // StringBuffer result = new StringBuffer();
  //    
  // SmsConfig cfg = getSmsConfig();
  // StringBuilder sb = new StringBuilder();
  // sb.append("Account=").append(cfg.getAccount().toString());
  // sb.append("&Password=").append(MD5Encode(cfg.getPassword().toString()));
  // sb.append("&smsid=" + smsid);
  //    
  // try {
  // // 请求内容
  // String httpContent = sb.toString();
  // URL url = new URL(cfg.getReportUrl());
  //      
  // HttpURLConnection httpUrlConnection = (HttpURLConnection)
  // url.openConnection();
  // httpUrlConnection.setConnectTimeout(30000); // 设置连接主机超时（单位：毫秒）
  // httpUrlConnection.setReadTimeout(30000); // 设置从主机读取数据超时
  // httpUrlConnection.setDoInput(true);
  // httpUrlConnection.setDoOutput(true);
  // httpUrlConnection.setUseCaches(false);
  // httpUrlConnection.setRequestProperty("Content-type",
  // "application/x-www-form-urlencoded;text/html;charset=GBK");
  // httpUrlConnection.setRequestProperty("Content-Length",
  // String.valueOf(httpContent.length()));
  // httpUrlConnection.setRequestMethod("POST");
  //
  // OutputStream outStrm = httpUrlConnection.getOutputStream();
  //
  // byte[] data = httpContent.getBytes();
  // outStrm.write(data);
  // outStrm.flush();
  // outStrm.close();
  //
  // //实际发送请求
  // InputStream is = httpUrlConnection.getInputStream();
  // BufferedReader rd = new BufferedReader(new InputStreamReader(is));
  // String line;
  // while ((line = rd.readLine()) != null) {
  // result.append(line);
  // }
  // rd.close();
  // is.close();
  // } catch (MalformedURLException e) {
  // e.printStackTrace();
  // } catch (IOException e) {
  // e.printStackTrace();
  // }
  // return setSmsResult(result.toString());
  //   
  // }
  //  
  // //用来对明文密码进行MD5加密
  // public static String MD5Encode(String origin) {
  // String resultString = null;
  //
  // try {
  // resultString=new String(origin);
  // MessageDigest md = MessageDigest.getInstance("MD5");
  // resultString=byte2hexString(md.digest(resultString.getBytes()));
  // }
  // catch (Exception ex) {
  //
  // }
  // return resultString;
  // }
  //  
  // public static String byte2hexString(byte[] bytes) {
  // StringBuffer resultSb = new StringBuffer();
  // for (int i = 0; i < bytes.length; i++) {
  // resultSb.append(byteToHexString(bytes[i]));
  // }
  // return resultSb.toString();
  // }
  //
  // private static String byteToHexString(byte b) {
  // int n = b;
  // if (n < 0)
  // n = 256 + n;
  // int d1 = n / 16;
  // int d2 = n % 16;
  // return hexDigits[d1] + hexDigits[d2];
  // }
  //
  // // 解析xml格式字符串
  // private Document transferStringToDocument1(String s) throws
  // ParserConfigurationException, SAXException, IOException {
  // StringReader reader = new StringReader(s);
  // InputSource is = new InputSource(reader);
  // DocumentBuilder builder =
  // DocumentBuilderFactory.newInstance().newDocumentBuilder();
  // Document doc = builder.parse(is);
  //
  // return doc;
  // }

}
