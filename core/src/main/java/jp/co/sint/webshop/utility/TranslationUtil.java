package jp.co.sint.webshop.utility;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

public final class TranslationUtil {

  // 百度翻译主方法
  // 请求URL样例：http://openapi.baidu.com/public/2.0/bmt/translate?client_id=TsaPkNa4ocV9EMdNhK34yVE3&q=%E4%BB%8A%E5%A4%A9&from=zh&to=jp
  public static String baiduTranslation(String fromBody) {

    String toBody = "";
    try {
      // 获得请求参数
      String paramStr = createRequestParam(fromBody);
      // 调用api获得结果
      String resBybaidu = getResult("http://openapi.baidu.com/public/2.0/bmt/translate", paramStr);
      // 读取返回JSON内容
      JSONObject json = new JSONObject(resBybaidu);
      String resStr = json.getString("trans_result");
      // 判断是否返回翻译结果
      if (StringUtil.hasValue(resStr)) {
        // 正确返回
        JSONArray jsonarr = new JSONArray(resStr);
        String str = jsonarr.get(0).toString();
        json = new JSONObject(str);
        toBody = json.getString("dst");
      } else {
        // 错误返回
        toBody = "error";
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return toBody;
  }

  // http请求百度翻译post方式
  public static String getResult(String urlStr, String content) {
    URL url = null;
    HttpURLConnection connection = null;

    try {
      // 建立连接
      url = new URL(urlStr);
      connection = (HttpURLConnection) url.openConnection();
      connection.setDoOutput(true);
      connection.setDoInput(true);
      connection.setRequestMethod("POST");
      connection.setUseCaches(false);
      connection.connect();

      // 传递参数
      DataOutputStream out = new DataOutputStream(connection.getOutputStream());
      // 用utf-8的编码方式传递参数，否则中文会出现乱码
      out.write(content.getBytes("utf-8"));
      out.flush();
      out.close();

      // 获得返回结果
      BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
      StringBuffer buffer = new StringBuffer();
      String line = "";
      while ((line = reader.readLine()) != null) {
        buffer.append(line);
      }
      reader.close();
      return buffer.toString();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (connection != null) {
        connection.disconnect();
      }
    }
    return null;
  }

  // 组装百度翻译请求参数
  private static String createRequestParam(String fromBody) {
    TreeMap<String, String> apiparamsMap = new TreeMap<String, String>();

    // 组装协议参数。
    apiparamsMap.put("client_id", "TsaPkNa4ocV9EMdNhK34yVE3");
    apiparamsMap.put("q", fromBody);
    apiparamsMap.put("from", "zh");
    apiparamsMap.put("to", "jp");

    StringBuilder param = new StringBuilder();
    for (Iterator<Map.Entry<String, String>> it = apiparamsMap.entrySet().iterator(); it.hasNext();) {
      Map.Entry<String, String> e = it.next();
      param.append("&").append(e.getKey()).append("=").append(e.getValue());
    }
    return param.toString().substring(1);
  }
}
