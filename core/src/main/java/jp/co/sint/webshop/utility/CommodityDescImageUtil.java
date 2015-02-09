package jp.co.sint.webshop.utility;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import sun.misc.BASE64Decoder;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SCPClient;

public final class CommodityDescImageUtil {

  // downloap图片的上传
  public static void netImgUpload(String urlAddress, String newUrl) throws Exception {
    // 创建输出流
    FileOutputStream outStream = null;
    InputStream inStream = null;
    HttpURLConnection conn = null;
    try {
      // new一个URL对象
      URL url = new URL(urlAddress);
      // 打开链接
      conn = (HttpURLConnection) url.openConnection();
      // 设置请求方式为"GET"
      conn.setRequestMethod("GET");
      // 超时响应时间为5秒
      conn.setConnectTimeout(5 * 1000);
      // 通过输入流获取图片数据
      inStream = conn.getInputStream();
      // 得到图片的二进制数据，以二进制封装得到数据，具有通用性
      byte[] data = readInputStream(inStream);
      // new一个文件对象用来保存图片，默认保存当前工程根目录
      File imageFile = new File(newUrl);
      outStream = new FileOutputStream(imageFile);
      // 写入数据
      outStream.write(data);
    } catch (Exception e) {
      throw new IOException(e.getMessage());
    } finally {
      // 关闭输入流
      inStream.close();
      // 关闭输出流
      outStream.flush();
      outStream.close();
      // 关闭连接
      conn.disconnect();
    }
  }

  public static byte[] readInputStream(InputStream inStream) throws Exception {
    ByteArrayOutputStream outStream = null;
    byte[] data = null;
    try {
      outStream = new ByteArrayOutputStream();
      // 创建一个Buffer字符串
      byte[] buffer = new byte[1024];
      // 每次读取的字符串长度，如果为-1，代表全部读取完毕
      int len = 0;
      // 使用一个输入流从buffer里把数据读取出来
      while ((len = inStream.read(buffer)) != -1) {
        // 用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
        outStream.write(buffer, 0, len);
      }
      data = outStream.toByteArray();

    } catch (Exception e) {
      throw new IOException(e.getMessage());
    } finally {
      outStream.flush();
      outStream.close();
    }
    // 把outStream里的数据写入内存
    return data;
  }

  // Base64图片的解码以及上传
  public static boolean GenerateImage(String imgStr, String imgFilePath) {// 对字节数组字符串进行Base64解码并生成图片
    boolean flag = false;
    if (imgStr == null) {
      // 图像数据为空
      return false;
    }

    BASE64Decoder decoder = new BASE64Decoder();
    OutputStream out = null;
    try {
      // Base64解码
      byte[] bytes = decoder.decodeBuffer(imgStr);
      for (int i = 0; i < bytes.length; ++i) {
        if (bytes[i] < 0) {// 调整异常数据
          bytes[i] += 256;
        }
      }
      // 生成jpeg图片
      out = new FileOutputStream(imgFilePath);
      out.write(bytes);
      out.flush();
      out.close();
      flag = true;
    } catch (Exception e) {
      flag = false;
    } finally {
      IOUtil.close(out);
    }
    return flag;
  }

  // 获得描述中src属性字符串
  public static List<String> getImg(String s) {
    String regex;
    List<String> list = new ArrayList<String>();
    regex = "src=\"(.*?)\"";
    Pattern pa = Pattern.compile(regex, Pattern.DOTALL);
    Matcher ma = pa.matcher(s);
    while (ma.find()) {
      list.add(ma.group());
    }
    return list;
  }

  /**
   * 参数说明 ipStr:服务器IP sourceUrl：源目录图片 例：/usr/local/1.jpg target:目标目录
   * 例:/usr/local/soukai/ 目标服务器用户&密码:user&pwd
   **/
  public static boolean scpSendImg(String ipStr, String sourceUrl, String target, String user, String pwd) {
    boolean flag = false;
    Connection conn = null;
    try {
      conn = new Connection(ipStr);
      conn.connect();
      boolean isAuthenticated = conn.authenticateWithPassword(user, pwd);
      if (isAuthenticated == false) {
        return flag;
      }
      SCPClient client = new SCPClient(conn);
      client.put(sourceUrl, target, "0777");
      flag = true;
    } catch (Exception e) {
      e.printStackTrace();
      return flag;
    } finally {
      conn.close();
    }
    return flag;
  }

  // 判断当前系统是windows还是linux
  public static boolean isOSType() {
    Properties prop = System.getProperties();
    String os = prop.getProperty("os.name");
    String checkOs = os.toUpperCase();

    if (checkOs.substring(0, 3).equals("win") || checkOs.substring(0, 3).equals("WIN")) {
      return true;
    } else {
      return false;
    }
  }
}
