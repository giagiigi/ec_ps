package jp.co.sint.webshop.ext.tmall;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import sun.misc.BASE64Decoder;

public class TmallUtil {

  /*
   * 商品API
   */
  // 添加商品header
  public static final String ADD_COMMODITY_HEADER = "taobao.item.add";

  // 更新商品header
  public static final String UPDATE_COMMODITY_HEADER = "taobao.item.update";

  // 添加商品SKU
  public static final String ADD_SKU = "taobao.item.sku.add";

  // 更新商品SKU
  public static final String UPDATE_SKU = "taobao.item.sku.update";

  // 商品下架
  public static final String COMMODITY_DELISTING = "taobao.item.update.delisting";

  // 获取单个商品详细信息
  public static final String SEARCH_COMMODITY = "taobao.item.get";
  
  // 上传图片到tmall
  public static final String IMG_UPLOAD = "taobao.item.img.upload";
  
  // SKU库存修改 
  public static final String UPDATE_SKU_STOCK = "taobao.item.quantity.update";
  
  // SKU获取
  public static final String GET_SKU = "taobao.item.sku.get";
  
  // SKU删除
  public static final String DELETE_SKU = "taobao.item.sku.delete";

  /*
   * 交易API
   */
  // 批量获取交易数据
  public static final String ORDER_LIST_INFO = "taobao.trades.sold.increment.get";

  // 获取单笔交易的详细信息
  public static final String ORDER_DETAIL_NO = "taobao.trade.fullinfo.get";

  // 关闭单笔交易
  public static final String CLOSE_ORDER = "taobao.trade.close";

  // 修改订单收获地址信息
  public static final String SHIPPING_INFO = "taobao.trade.shippingaddress.update";

  /*
   * 类目API
   */
  // 查询B商家被授权品牌列表和类目列表
  public static final String CATEGORY_BRAND_LIST = "taobao.itemcats.authorize.get";

  // 获取后台供卖家发布商品的标准商品类目
  public static final String COMMODITY_CATEGORY_LIST = "taobao.itemcats.get";

  // 获取标准商品类目属性
  public static final String CATEGORY_PROPRTIES = "taobao.itemprops.get";

  // 获取标准类目属性值
  public static final String CATEGORY_PROPRTIES_VALUES = "taobao.itempropvalues.get";

  /*
   * 店铺API
   */
  // 获取前台展示的店铺内卖家自定义商品类目
  public static final String SELLERCATS_LIST = "taobao.sellercats.list.get";

  /*
   * 退货退款API
   */
  // 获取单笔退款信息详细
  public static final String REFUND_INFO = "taobao.refund.get";

  /*
   * 发货物流API
   */
  // 批量发货
  public static final String SHIP_DELIVERY_SEND = "taobao.topats.delivery.send";

  // 获取淘宝地址薄
  public static final String GET_AREAS = "taobao.areas.get";

  /*
   * 系统API
   */
  // 获取淘宝系统时间
  public static final String GET_TMALL_TIME = "taobao.time.get";

  private TmallUtil() {
  }

  /*
   * 二行制转字符串
   */
  private static String byte2hex(byte[] b) {

    StringBuffer hs = new StringBuffer();
    String stmp = "";
    for (int n = 0; n < b.length; n++) {
      stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
      if (stmp.length() == 1)
        hs.append("0").append(stmp);
      else
        hs.append(stmp);
    }
    return hs.toString().toUpperCase();
  }

  /*
   * 把经过BASE64编码的字符串转换为Map对象
   */
  public static Map<String, String> convertBase64StringtoMap(String str) {

    if (str == null)
      return null;
    BASE64Decoder decoder = new BASE64Decoder();
    String keyvalues = null;
    try {
      keyvalues = new String(decoder.decodeBuffer(str));
    } catch (IOException e) {
      return null;
    }
    if (keyvalues == null || keyvalues.length() == 0)
      return null;
    String[] keyvalueArray = keyvalues.split("&");
    Map<String, String> map = new HashMap<String, String>();
    for (String keyvalue : keyvalueArray) {
      String[] s = keyvalue.split("=");
      if (s == null || s.length != 2)
        return null;
      map.put(s[0], s[1]);
    }
    return map;
  }

  /*
   * 签名方法，用于生成签名。
   * @param params 传给服务器的参数
   * @param secret APP_SECRET
   */
  public static String sign(TreeMap<String, String> params, String secret) {

    String result = null;
    if (params == null)
      return result;
    Iterator<String> iter = params.keySet().iterator();
    StringBuffer orgin = new StringBuffer(secret);
    while (iter.hasNext()) {
      String name = (String) iter.next();
      orgin.append(name).append(params.get(name));
    }
    try {
      MessageDigest md = MessageDigest.getInstance("MD5");
      result = byte2hex(md.digest(orgin.toString().getBytes("utf-8")));
    } catch (Exception ex) {
      throw new java.lang.RuntimeException("sign error !");
    }
    return result;
  }

  /**
   * 模拟post请求，调用api，传递参数，获得结果
   * 
   * @param urlStr
   *          请求的url
   * @param content
   *          传递的参数
   * @return 调用api获得返回结果
   */
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

  /**
   * 根据回调url中的top_parameters获得nick
   * 
   * @param top_parameters
   *          回调url中的参数
   * @return nick
   */
  @SuppressWarnings("unchecked")
  public static String ParametersName(String top_parameters) {

    String nick = null;
    Map<String, String> map = convertBase64StringtoMap(top_parameters);
    Iterator keyValuePairs = map.entrySet().iterator();
    for (int i = 0; i < map.size(); i++) {
      Map.Entry entry = (Map.Entry) keyValuePairs.next();
      Object key = entry.getKey();
      Object value = entry.getValue();
      if (key.equals("visitor_nick")) {

        nick = (String) value;
      }
    }
    return nick;
  }
}
