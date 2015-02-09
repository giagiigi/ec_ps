package jp.co.sint.webshop.utility;

import org.bouncycastle.util.encoders.Base64;

public class DecryptUtil {

  //生成新字符
  private static String unescape(String src) {
    StringBuffer tmp = new StringBuffer();
    tmp.ensureCapacity(src.length());
    int lastPos = 0, pos = 0;
    char ch;
    while (lastPos < src.length()) {
      pos = src.indexOf("%", lastPos);
      if (pos == lastPos) {
        if (src.charAt(pos + 1) == 'u') {
          ch = (char) Integer.parseInt(src.substring(pos + 2, pos + 6), 16);
          tmp.append(ch);
          lastPos = pos + 6;
        } else {
          ch = (char) Integer.parseInt(src.substring(pos + 1, pos + 3), 16);
          tmp.append(ch);
          lastPos = pos + 3;
        }
      } else {
        if (pos == -1) {
          tmp.append(src.substring(lastPos));
          lastPos = src.length();
        } else {
          tmp.append(src.substring(lastPos, pos));
          lastPos = pos;
        }
      }
    }
    return tmp.toString();
  }

  /********
   * 解密程序
   * 
   * @param encode
   * @return
   */
  public static String decode64(String encode) {
    // 调用org.apache.commons.codec.binary.Base64包，在commons-codec-1.3.jar中
    byte[] byteOfEncode = encode.getBytes();
    byte[] byteOfDecode = Base64.decode(byteOfEncode);// 调用decodeBase64方法
    String decode = new String(byteOfDecode);
    return unescape(decode);// 调用unescape（String src）方法
  }

  /********
   * 加密程序
   * 
   * @param encode
   * @return
   */
  public static String encode64(String encode) {
    // 调用org.apache.commons.codec.binary.Base64包，在commons-codec-1.3.jar中
    byte[] byteOfEncode = encode.getBytes();
    // 加密
    byte[] byteOfDecode = Base64.encode(byteOfEncode);// 调用decodeBase64方法
    String decode = new String(byteOfDecode);
    return unescape(decode);// 调用unescape（String src）方法
  }
}
