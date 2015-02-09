package jp.co.sint.webshop.configure;

import java.io.Serializable;

import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.SimpleQuery;
import jp.co.sint.webshop.utility.StringUtil;

public class MutableStatus implements Serializable {

  private static final long serialVersionUID = 1L;

  private static final String POINT_ENABLE_QUERY = "SELECT POINT_FUNCTION_ENABLED_FLG FROM POINT_RULE";

  private static final String SITE_NAME_QUERY = "SELECT SHOP_NAME FROM SHOP WHERE SHOP_TYPE = 0";

  private static final String ICP_CODE_QUERY = "SELECT ICP_CODE FROM SHOP WHERE SHOP_TYPE = 0";

  private static final String ONLINE_SERVICE_QUERY = "SELECT enabled_flg FROM online_service";

  // Add start
  private static String ONLINE_SCRIPT1_QUERY = "SELECT SCRIPT_TEXT1 FROM ONLINE_SERVICE ";

  private static String ONLINE_SCRIPT2_QUERY = "SELECT SCRIPT_TEXT2 FROM ONLINE_SERVICE ";

  // Add by V10-CH start
  private static String ONLINE_SCRIPT3_QUERY = "SELECT SCRIPT_TEXT3 FROM ONLINE_SERVICE ";

  private static final String ONLINE_SHOPCODE_QUERY = "SELECT SHOP_CODE FROM ONLINE_SERVICE";

  private static final String COUPON_ENABLE_QUERY = "SELECT COUPON_FUNCTION_ENABLED_FLG FROM COUPON_RULE";

  // Add by V10-CH end

  // Add by V10-CH start

  private static final String GOOGLE_ANALYSIS_QUERY = "SELECT enabled_flg FROM GOOGLE_ANALYSIS";

  private static final String GOOGLE_SCRIPT_QUERY = "SELECT SCRIPT_TEXT FROM GOOGLE_ANALYSIS";
  
  private static final String BAIDU_ANALYSIS_QUERY = "SELECT enabled_flg FROM BAIDU_ANALYSIS";

  private static final String BAIDU_SCRIPT_QUERY = "SELECT SCRIPT_TEXT FROM BAIDU_ANALYSIS";
  
  // Add by V10-CH end
  /**
   * 在线客服规则 script1
   * 
   * @return the script1
   */
  public String getScript1() {
    Object obj1 = DatabaseUtil.executeScalar(new SimpleQuery(ONLINE_SCRIPT1_QUERY));
    if (obj1 != null) {
      return obj1.toString();
    } else {
      return StringUtil.EMPTY;
    }
  }

  /**
   * 在线客服规则 script2
   * 
   * @return the script2
   */
  public String getScript2() {
    Object obj2 = DatabaseUtil.executeScalar(new SimpleQuery(ONLINE_SCRIPT2_QUERY));
    if (obj2 != null) {
      return obj2.toString();
    } else {
      return StringUtil.EMPTY;
    }
  }

  // Add end

  // Add by V10-CH start
  /**
   * 在线客服规则 script3
   * 
   * @return the script3
   */
  public String getScript3() {
    Object obj3 = DatabaseUtil.executeScalar(new SimpleQuery(ONLINE_SCRIPT3_QUERY));
    if (obj3 != null) {
      return obj3.toString();
    } else {
      return StringUtil.EMPTY;
    }
  }

  /**
   * 在线客服规则 shopCode
   * 
   * @return the shopCode
   */
  public String getShopCode() {
    Object obj4 = DatabaseUtil.executeScalar(new SimpleQuery(ONLINE_SHOPCODE_QUERY));
    if (obj4 != null) {
      return obj4.toString();
    } else {
      return StringUtil.EMPTY;
    }
  }

  /**
   * Google Analysis规则 script
   * 
   * @return the script
   */
  public String getScript() {
    Object obj = DatabaseUtil.executeScalar(new SimpleQuery(GOOGLE_SCRIPT_QUERY));
    if (obj != null) {
      return obj.toString();
    } else {
      return StringUtil.EMPTY;
    }
  }

  
  public String getBaiduScript() {
    Object obj = DatabaseUtil.executeScalar(new SimpleQuery(BAIDU_SCRIPT_QUERY));
    if (obj != null) {
      return obj.toString();
    } else {
      return StringUtil.EMPTY;
    }
  }
  /**
   * enablePointSystemを返します。
   * 
   * @return the enablePointSystem
   */
  public boolean isEnableCouponSystem() {
    Object obj = DatabaseUtil.executeScalar(new SimpleQuery(COUPON_ENABLE_QUERY));
    return Long.valueOf(obj.toString()).equals(1L);
  }

  // Add by V10-CH end

  /**
   * enablePointSystemを返します。
   * 
   * @return the enablePointSystem
   */
  public boolean isEnablePointSystem() {
    Object obj = DatabaseUtil.executeScalar(new SimpleQuery(POINT_ENABLE_QUERY));
    return Long.valueOf(obj.toString()).equals(1L);
  }

  /**
   * サイト名を返します。
   * 
   * @return the siteName
   */
  public String getSiteName() {
    Object obj = DatabaseUtil.executeScalar(new SimpleQuery(SITE_NAME_QUERY));
    if (obj != null) {
      return obj.toString();
    } else {
      return StringUtil.EMPTY;
    }
  }

  /**
   * ICP登録コードを返します。
   * 
   * @return the IcpCode
   */
  public String getIcpCode() {
    Object obj = DatabaseUtil.executeScalar(new SimpleQuery(ICP_CODE_QUERY));
    if (obj != null) {
      return obj.toString();
    } else {
      return StringUtil.EMPTY;
    }
  }

  /**
   * EnableOnlineServiceコードを返します。
   * 
   * @return the EnableOnlineService
   */
  public boolean getEnableOnlineService() {
    Object obj = DatabaseUtil.executeScalar(new SimpleQuery(ONLINE_SERVICE_QUERY));
    if (obj != null) {
      return obj.toString().equals("1");
    } else {
      return false;
    }
  }

  public boolean getEnableGoogleAnalysis() {
    Object obj = DatabaseUtil.executeScalar(new SimpleQuery(GOOGLE_ANALYSIS_QUERY));
    if (obj != null) {
      return obj.toString().equals("1");
    } else {
      return false;
    }
  }
  
  public boolean getEnableBaiduAnalysis() {
    Object obj = DatabaseUtil.executeScalar(new SimpleQuery(BAIDU_ANALYSIS_QUERY));
    if (obj != null) {
      return obj.toString().equals("1");
    } else {
      return false;
    }
  }

}
