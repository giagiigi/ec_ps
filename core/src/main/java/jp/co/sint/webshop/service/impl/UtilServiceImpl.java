package jp.co.sint.webshop.service.impl;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.configure.WebshopConfig;
import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.SimpleQuery;
import jp.co.sint.webshop.data.SqlUtil;
import jp.co.sint.webshop.data.dao.AreaDao;
import jp.co.sint.webshop.data.dao.CityDao;
import jp.co.sint.webshop.data.dao.DeliveryCompanyDao;
import jp.co.sint.webshop.data.dao.JdCityDao;
import jp.co.sint.webshop.data.dao.JdPrefectureDao;
import jp.co.sint.webshop.data.dao.PrefectureDao;
import jp.co.sint.webshop.data.dao.ShopDao;
import jp.co.sint.webshop.data.domain.AllowDeliveryTimeFlg;
import jp.co.sint.webshop.data.domain.AppointedTimeType;
import jp.co.sint.webshop.data.domain.CodType;
import jp.co.sint.webshop.data.domain.DefaultFlg;
import jp.co.sint.webshop.data.domain.DeliveryDateType;
import jp.co.sint.webshop.data.domain.LanguageCode;
import jp.co.sint.webshop.data.domain.OperatingMode;
import jp.co.sint.webshop.data.domain.PrefectureCode;
import jp.co.sint.webshop.data.domain.StockManagementType;
import jp.co.sint.webshop.data.dto.Area;
import jp.co.sint.webshop.data.dto.City;
import jp.co.sint.webshop.data.dto.DeliveryCompany;
import jp.co.sint.webshop.data.dto.DeliveryRegionCharge;
import jp.co.sint.webshop.data.dto.DeliveryRelatedInfo;
import jp.co.sint.webshop.data.dto.DeliveryType;
import jp.co.sint.webshop.data.dto.Gift;
import jp.co.sint.webshop.data.dto.JdArea;
import jp.co.sint.webshop.data.dto.JdCity;
import jp.co.sint.webshop.data.dto.JdDeliveryRelatedInfo;
import jp.co.sint.webshop.data.dto.JdPrefecture;
import jp.co.sint.webshop.data.dto.OriginalPlace;
import jp.co.sint.webshop.data.dto.Prefecture;
import jp.co.sint.webshop.data.dto.ShippingCharge;
import jp.co.sint.webshop.data.dto.Shop;
import jp.co.sint.webshop.data.dto.StockHoliday;
import jp.co.sint.webshop.data.dto.TmallDeliveryRelatedInfo;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.Price;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ShopManagementService;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.service.cart.CartCommodityInfo;
import jp.co.sint.webshop.service.catalog.CatalogQuery;
import jp.co.sint.webshop.service.catalog.TaxUtil;
import jp.co.sint.webshop.service.shop.AddressInfo;
import jp.co.sint.webshop.text.Messages;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.InquiryConfig;
import jp.co.sint.webshop.utility.MailMagazineConfig;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.SqlDialect;
import jp.co.sint.webshop.utility.StringUtil;


import org.apache.log4j.Logger;

public class UtilServiceImpl extends AbstractServiceImpl implements UtilService {

  private static String QUERY = "";

  private static final String CUSTOMER_GROUP_QUERY = "SELECT  CUSTOMER_GROUP_NAME AS NAME, "
      + "CUSTOMER_GROUP_CODE AS VALUE FROM CUSTOMER_GROUP ORDER BY CUSTOMER_GROUP_CODE";

  private static final String CUSTOMER_GROUP_QUERY_EN = "SELECT  CUSTOMER_GROUP_NAME_EN AS NAME, "
      + "CUSTOMER_GROUP_CODE AS VALUE FROM CUSTOMER_GROUP ORDER BY CUSTOMER_GROUP_CODE";

  private static final String CUSTOMER_GROUP_QUERY_JP = "SELECT  CUSTOMER_GROUP_NAME_JP AS NAME, "
      + "CUSTOMER_GROUP_CODE AS VALUE FROM CUSTOMER_GROUP ORDER BY CUSTOMER_GROUP_CODE";

  private static final String STOCK_STATUS_QUERY = "SELECT  STOCK_STATUS_NAME AS NAME, STOCK_STATUS_NO AS VALUE "
      + "FROM STOCK_STATUS WHERE SHOP_CODE = ? ORDER BY STOCK_STATUS_NO";

  private static final String ENQUETE_QUESTIONS_QUERY = "SELECT ENQUETE_QUESTION_CONTENT AS NAME, "
      + "ENQUETE_QUESTION_NO AS VALUE FROM ENQUETE_QUESTION "
      + "WHERE ENQUETE_CODE = ? AND ENQUETE_QUESTION_TYPE != '2' ORDER BY DISPLAY_ORDER";

  private static final String ENQUETE_QUESTIONS_QUERY_EN = "SELECT ENQUETE_QUESTION_CONTENT_EN AS NAME, "
      + "ENQUETE_QUESTION_NO AS VALUE FROM ENQUETE_QUESTION "
      + "WHERE ENQUETE_CODE = ? AND ENQUETE_QUESTION_TYPE != '2' ORDER BY DISPLAY_ORDER";

  private static final String ENQUETE_QUESTIONS_QUERY_JP = "SELECT ENQUETE_QUESTION_CONTENT_JP AS NAME, "
      + "ENQUETE_QUESTION_NO AS VALUE FROM ENQUETE_QUESTION "
      + "WHERE ENQUETE_CODE = ? AND ENQUETE_QUESTION_TYPE != '2' ORDER BY DISPLAY_ORDER";

  private static final String DELIVERY_TYPE_QUERY = "SELECT  DELIVERY_TYPE_NAME AS NAME, DELIVERY_TYPE_NO AS VALUE "
      + "FROM DELIVERY_TYPE WHERE SHOP_CODE = ? ORDER BY DELIVERY_TYPE_NO";

  // 20120116 ysy add start
  private static final String DELIVERY_COMPANY_QUERY = "SELECT  DELIVERY_COMPANY_NAME AS NAME, DELIVERY_COMPANY_NO AS VALUE "
      + "FROM DELIVERY_COMPANY WHERE SHOP_CODE = ? ORDER BY DELIVERY_COMPANY_NO";

  // 20120116 ysy add end

  private static final String TAX_QUERY = "SELECT TAX_RATE FROM TAX WHERE APPLIED_START_DATE <= SYSDATE "
      + "ORDER BY APPLIED_START_DATE Desc";

  private static final String CHECK_HOLIDAY_QUERY = "SELECT COUNT(HOLIDAY_ID) FROM HOLIDAY WHERE SHOP_CODE = ? "
      + " AND HOLIDAY = TO_DATE(?, 'YYYY/MM/DD')";

  private static final String AVAILABLE_DELIVERY_TYPES = "SELECT DELIVERY_TYPE_NAME AS NAME, DELIVERY_TYPE_NO AS VALUE "
      + "FROM DELIVERY_TYPE WHERE SHOP_CODE = ? ORDER BY VALUE";

  private static final String DELIVERY_APPOINTED_TIMES = "SELECT DELIVERY_APPOINTED_TIME_START || '時 - '"
      + " || DELIVERY_APPOINTED_TIME_END || '時' AS NAME, " + "DELIVERY_APPOINTED_TIME_START || '/' || DELIVERY_APPOINTED_TIME_END "
      + " AS VALUE FROM DELIVERY_APPOINTED_TIME WHERE SHOP_CODE = ? AND DELIVERY_TYPE_NO = ? "
      + "ORDER BY DELIVERY_APPOINTED_TIME_START ASC";

  private static final String CATEGORY_NAMES = "SELECT  CATEGORY_NAME AS NAME, CATEGORY_CODE AS VALUE FROM CATEGORY "
      + "ORDER BY SHOP_CODE";

  private static final String SUB_CATEGORY_NAMES = "SELECT  CATEGORY_NAME_PC AS NAME, CATEGORY_CODE AS VALUE "
      + "FROM CATEGORY WHERE PARENT_CATEGORY_CODE = ? " + "ORDER BY DISPLAY_ORDER";

  private static final String LEAD_TIME_QUERY = "SELECT " + SqlUtil.getColumnNamesCsv(ShippingCharge.class, "SC")
      + " FROM SHIPPING_CHARGE SC INNER JOIN REGION_BLOCK_LOCATION RBL "
      + "ON RBL.SHOP_CODE = SC.SHOP_CODE AND RBL.REGION_BLOCK_ID = SC.REGION_BLOCK_ID "
      + "WHERE SC.SHOP_CODE = ? AND SC.DELIVERY_TYPE_NO = ? AND RBL.PREFECTURE_CODE = ? ";

  // 20111227 shen add start
  private static final String LOAD_DELIVERY_DATE_QUERY = DatabaseUtil.getSelectAllQuery(DeliveryRelatedInfo.class)
      + " WHERE SHOP_CODE = ? AND PREFECTURE_CODE = ? AND COD_TYPE <> ?"
      + " AND CASE WHEN MIN_WEIGHT IS NULL THEN 0 ELSE  MIN_WEIGHT END <= ? "
      + " AND CASE WHEN MAX_WEIGHT IS NULL THEN 9999999999 ELSE  MAX_WEIGHT END >= ? " + " AND DELIVERY_COMPANY_NO = ?"
      + " ORDER BY DELIVERY_DATE_TYPE";

  private static final String LOAD_DELIVERY_TIME_QUERY = DatabaseUtil.getSelectAllQuery(DeliveryRelatedInfo.class)
      + " WHERE SHOP_CODE = ? AND PREFECTURE_CODE = ? AND COD_TYPE <> ? AND DELIVERY_DATE_TYPE IN (?,?)"
      + " AND CASE WHEN MIN_WEIGHT IS NULL THEN 0 ELSE  MIN_WEIGHT END <= ? "
      + " AND CASE WHEN MAX_WEIGHT IS NULL THEN 9999999999 ELSE  MAX_WEIGHT END >= ? " + " AND DELIVERY_COMPANY_NO = ?"
      + " ORDER BY DELIVERY_APPOINTED_TIME_TYPE, DELIVERY_APPOINTED_START_TIME, DELIVERY_APPOINTED_END_TIME";

  private static final String LOAD_DELIVERY_REGION_CHARGE_QUERY = DatabaseUtil.getSelectAllQuery(DeliveryRegionCharge.class)
      + " WHERE SHOP_CODE = ? AND PREFECTURE_CODE = ? AND DELIVERY_COMPANY_NO = ?";

  private static final String TMALL_LOAD_DELIVERY_REGION_CHARGE_QUERY = DatabaseUtil.getSelectAllQuery(DeliveryRegionCharge.class)
      + " WHERE SHOP_CODE = ? AND PREFECTURE_CODE = ? ";

  private static final String JD_LOAD_DELIVERY_REGION_CHARGE_QUERY = DatabaseUtil.getSelectAllQuery(DeliveryRegionCharge.class)
  + " WHERE SHOP_CODE = ? AND PREFECTURE_CODE = ? ";

  // 2013/04/21 优惠券对应 ob update start
//  private static final String LOAD_DELIVERY_RELATED_INFO_QUERY = DatabaseUtil.getSelectAllQuery(DeliveryRelatedInfo.class)
//  // +
//  // " WHERE SHOP_CODE = ? AND PREFECTURE_CODE = ? AND COD_TYPE <> ? AND DELIVERY_DATE_TYPE IN (?,?)"
//  + " WHERE SHOP_CODE = ? AND PREFECTURE_CODE = ? AND COD_TYPE <> ? "
//  + " AND CASE WHEN MIN_WEIGHT IS NULL THEN 0 ELSE  MIN_WEIGHT END <= ? "
//  + " AND CASE WHEN MAX_WEIGHT IS NULL THEN 9999999999 ELSE  MAX_WEIGHT END >= ? ORDER BY DELIVERY_COMPANY_NO DESC ";
  private static final String LOAD_DELIVERY_RELATED_INFO_QUERY = "SELECT DR.* FROM delivery_related_info DR INNER JOIN delivery_location DL "
    + " ON DR.SHOP_CODE = DL.SHOP_CODE AND DR.DELIVERY_COMPANY_NO = DL.DELIVERY_COMPANY_NO AND DR.PREFECTURE_CODE = DL.PREFECTURE_CODE "
    + " WHERE DR.SHOP_CODE = ? AND DR.PREFECTURE_CODE = ? AND DR.COD_TYPE <> ? "
    + " AND DL.city_code = ? AND DL.area_code = ? "
    + " AND CASE WHEN DR.MIN_WEIGHT IS NULL THEN 0 ELSE DR.MIN_WEIGHT END <= ? "
    + " AND CASE WHEN DR.MAX_WEIGHT IS NULL THEN 9999999999 ELSE DR.MAX_WEIGHT END >= ? " 
    + " AND CASE WHEN DL.MIN_WEIGHT IS NULL THEN 0 ELSE DL.MIN_WEIGHT END <= ? "
    + " AND CASE WHEN DL.MAX_WEIGHT IS NULL THEN 9999999999 ELSE DL.MAX_WEIGHT END >= ? " 
    + "	ORDER BY DR.DELIVERY_COMPANY_NO DESC ";
  // 2013/04/21 优惠券对应 ob update end
  

  // 20111227 shen add end

  // soukai add 2012/03/27 ob start
  private static final String LOAD_TMALL_DELIVERY_TIME_QUERY = DatabaseUtil.getSelectAllQuery(TmallDeliveryRelatedInfo.class)
      + " WHERE SHOP_CODE = ? AND PREFECTURE_CODE = ? AND COD_TYPE <> ? AND DELIVERY_DATE_TYPE IN (?,?)"
      + " ORDER BY DELIVERY_APPOINTED_TIME_TYPE, DELIVERY_APPOINTED_START_TIME, DELIVERY_APPOINTED_END_TIME";

  private static final String LOAD_JD_DELIVERY_TIME_QUERY = DatabaseUtil.getSelectAllQuery(JdDeliveryRelatedInfo.class)
  + " WHERE SHOP_CODE = ? AND PREFECTURE_CODE = ? AND COD_TYPE <> ? AND DELIVERY_DATE_TYPE IN (?,?)"
  + " ORDER BY DELIVERY_APPOINTED_TIME_TYPE, DELIVERY_APPOINTED_START_TIME, DELIVERY_APPOINTED_END_TIME";
  
  private static final String LOAD_TMALL_DELIVERY_DATE_QUERY = DatabaseUtil.getSelectAllQuery(TmallDeliveryRelatedInfo.class)
      + " WHERE SHOP_CODE = ? AND PREFECTURE_CODE = ? AND COD_TYPE <> ?" + " ORDER BY DELIVERY_DATE_TYPE";

  private static final String LOAD_JD_DELIVERY_DATE_QUERY = DatabaseUtil.getSelectAllQuery(JdDeliveryRelatedInfo.class)
  + " WHERE SHOP_CODE = ? AND PREFECTURE_CODE = ? AND COD_TYPE <> ?" + " ORDER BY DELIVERY_DATE_TYPE";
  
  private static final String LOAD_TMALL_DELIVERY_RELATED_INFO_QUERY = DatabaseUtil
      .getSelectAllQuery(TmallDeliveryRelatedInfo.class)
      + " WHERE SHOP_CODE = ? AND PREFECTURE_CODE = ? AND COD_TYPE <> ? AND DELIVERY_DATE_TYPE IN (?,?)";

  // soukai add 2012/03/27 ob end

  // 20120105 shen add start
  private static final String LOAD_CITY_QUERY = DatabaseUtil.getSelectAllQuery(City.class) + " WHERE REGION_CODE = ?"
      + " ORDER BY DISPLAY_ORDER";

  private static final String LOAD_JD_CITY_QUERY = DatabaseUtil.getSelectAllQuery(JdCity.class) + " WHERE REGION_CODE = ?"
  + " ORDER BY DISPLAY_ORDER";
  
  private static final String LOAD_AREA_QUERY = DatabaseUtil.getSelectAllQuery(Area.class)
      + " WHERE PREFECTURE_CODE = ? AND CITY_CODE = ?" + " ORDER BY AREA_CODE";

  private static final String LOAD_JD_AREA_QUERY = DatabaseUtil.getSelectAllQuery(JdArea.class)
  + " WHERE PREFECTURE_CODE = ? AND CITY_CODE = ?" + " ORDER BY AREA_CODE";
  
  private static final String LOAD_ADDRESS_QUERY = "SELECT P.PREFECTURE_CODE, P.PREFECTURE_NAME,"
      + " C.CITY_CODE, C.CITY_NAME, A.AREA_CODE, A.AREA_NAME" + " FROM PREFECTURE P"
      + " LEFT JOIN CITY C ON P.PREFECTURE_CODE = C.REGION_CODE"
      + " LEFT JOIN AREA A ON P.PREFECTURE_CODE = A.PREFECTURE_CODE AND C.CITY_CODE = A.CITY_CODE"
      + " ORDER BY P.PREFECTURE_CODE, C.CITY_CODE, A.AREA_CODE";

  private static final String LOAD_ADDRESS_EN_QUERY = "SELECT P.PREFECTURE_CODE, P.PREFECTURE_NAME_EN AS PREFECTURE_NAME,"
      + " C.CITY_CODE, C.CITY_NAME_EN AS CITY_NAME, A.AREA_CODE, A.AREA_NAME_EN AS AREA_NAME" + " FROM PREFECTURE P"
      + " LEFT JOIN CITY C ON P.PREFECTURE_CODE = C.REGION_CODE"
      + " LEFT JOIN AREA A ON P.PREFECTURE_CODE = A.PREFECTURE_CODE AND C.CITY_CODE = A.CITY_CODE"
      + " ORDER BY P.PREFECTURE_CODE, C.CITY_CODE, A.AREA_CODE";

  private static final String LOAD_ADDRESS_JP_QUERY = "SELECT P.PREFECTURE_CODE, P.PREFECTURE_NAME_JP AS PREFECTURE_NAME,"
      + " C.CITY_CODE, C.CITY_NAME_JP AS CITY_NAME, A.AREA_CODE, A.AREA_NAME_JP AS AREA_NAME" + " FROM PREFECTURE P"
      + " LEFT JOIN CITY C ON P.PREFECTURE_CODE = C.REGION_CODE"
      + " LEFT JOIN AREA A ON P.PREFECTURE_CODE = A.PREFECTURE_CODE AND C.CITY_CODE = A.CITY_CODE"
      + " ORDER BY P.PREFECTURE_CODE, C.CITY_CODE, A.AREA_CODE";

  private static final String LOAD_JD_ADDRESS_QUERY = "SELECT P.PREFECTURE_CODE, P.PREFECTURE_NAME,"
    + " C.CITY_CODE, C.CITY_NAME, A.AREA_CODE, A.AREA_NAME" + " FROM JD_PREFECTURE P"
    + " LEFT JOIN CITY C ON P.PREFECTURE_CODE = C.REGION_CODE"
    + " LEFT JOIN AREA A ON P.PREFECTURE_CODE = A.PREFECTURE_CODE AND C.CITY_CODE = A.CITY_CODE"
    + " ORDER BY P.PREFECTURE_CODE, C.CITY_CODE, A.AREA_CODE";

  private static final String LOAD_JD_ADDRESS_EN_QUERY = "SELECT P.PREFECTURE_CODE, P.PREFECTURE_NAME_EN AS PREFECTURE_NAME,"
    + " C.CITY_CODE, C.CITY_NAME_EN AS CITY_NAME, A.AREA_CODE, A.AREA_NAME_EN AS AREA_NAME" + " FROM JD_PREFECTURE P"
    + " LEFT JOIN CITY C ON P.PREFECTURE_CODE = C.REGION_CODE"
    + " LEFT JOIN AREA A ON P.PREFECTURE_CODE = A.PREFECTURE_CODE AND C.CITY_CODE = A.CITY_CODE"
    + " ORDER BY P.PREFECTURE_CODE, C.CITY_CODE, A.AREA_CODE";

  private static final String LOAD_JD_ADDRESS_JP_QUERY = "SELECT P.PREFECTURE_CODE, P.PREFECTURE_NAME_JP AS PREFECTURE_NAME,"
    + " C.CITY_CODE, C.CITY_NAME_JP AS CITY_NAME, A.AREA_CODE, A.AREA_NAME_JP AS AREA_NAME" + " FROM JD_PREFECTURE P"
    + " LEFT JOIN CITY C ON P.PREFECTURE_CODE = C.REGION_CODE"
    + " LEFT JOIN AREA A ON P.PREFECTURE_CODE = A.PREFECTURE_CODE AND C.CITY_CODE = A.CITY_CODE"
    + " ORDER BY P.PREFECTURE_CODE, C.CITY_CODE, A.AREA_CODE";

  
  // 20120105 shen add end

  // 20120112 shen add start
  private static final String LOAD_PREFECTURE_QUERY = DatabaseUtil.getSelectAllQuery(Prefecture.class)
      + " WHERE PREFECTURE_NAME = ?";

  // 20120112 shen add end

  public String getShopName(String shopCode) {
    ShopDao dao = DIContainer.getDao(ShopDao.class);
    Shop shop = dao.load(shopCode);
    if (shop == null) {
      return StringUtil.EMPTY;
    }
    return shop.getShopName();
  }

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  public List<CodeAttribute> getAvailableDeliveryType(String shopCode) {
    List<NameValue> result = DatabaseUtil.loadAsBeanList(new SimpleQuery(AVAILABLE_DELIVERY_TYPES, shopCode), NameValue.class);
    return new ArrayList<CodeAttribute>(result);
  }

  public List<CodeAttribute> getDeliveryAppointedDays(DeliveryType deliveryType, String prefectureCode) {
    Logger logger = Logger.getLogger(UtilServiceImpl.class);
    if (PrefectureCode.fromValue(prefectureCode) == null) {
      logger.warn("invalid Prefecture Code :" + prefectureCode);
      return Collections.emptyList();
    }

    Date startDate = getShortestDeliveryDate(deliveryType, prefectureCode);
    if (startDate == null) {
      return Collections.emptyList();
    }

    List<CodeAttribute> result = new ArrayList<CodeAttribute>();
    WebshopConfig config = DIContainer.getWebshopConfig();
    DateFormat df = new SimpleDateFormat("yyyy/MM/dd(EE)");
    result.add(new NameValue(Messages.getString("service.impl.UtilServiceImpl.0"), ""));
    for (int i = 0; i < config.getDeliveryAppointedPeriod(); i++) {
      Date addDate = DateUtil.addDate(startDate, i);
      result.add(new NameValue(df.format(addDate), DateUtil.toDateString(addDate)));
    }

    return result;
  }

  public Date createShippingDirectDate(Date deliveryAppointedDate, DeliveryType deliveryType, String prefectureCode) {
    Logger logger = Logger.getLogger(UtilServiceImpl.class);
    // Date shippingDirectDate =
    // DateUtil.fromString(DateUtil.getSysdateString());
    Date shortestDeliveryDate = getShortestDeliveryDate(deliveryType, prefectureCode);

    // 最短お届け可能日と配送指定日の日数を取得する
    long distance = (deliveryAppointedDate.getTime() - shortestDeliveryDate.getTime()) / (1000 * 60 * 60 * 24);

    int leadTime = getDeliveryLeadTime(deliveryType, prefectureCode);
    if (leadTime == 0) {
      if (distance == 0L) {
        // 配送指定日が最短お届け可能日（翌営業日）の場合、配送指定日＝出荷指示日とする
        return deliveryAppointedDate;
      } else {
        // リードタイムが０日かつ翌営業日でない場合 、リードタイムを1日とする
        // (前営業日が配送指定日となる）
        leadTime = 1;
      }
    } else if (leadTime < 0) {
      logger.warn("lead time not found.");
      return null;
    }
    // 配送指定日からリードタイムの日数分さかのぼった日を出荷指示日として使う
    return subDateWithoutHoliday(deliveryAppointedDate, leadTime - 1, deliveryType.getShopCode());
  }

  private Date getShortestDeliveryDate(DeliveryType deliveryType, String prefectureCode) {
    return getShortestDeliveryDate(deliveryType, prefectureCode, DateUtil.getSysdate());
  }

  private Date getShortestDeliveryDate(DeliveryType deliveryType, String prefectureCode, Date orderDate) {
    Logger logger = Logger.getLogger(UtilServiceImpl.class);

    // リードタイムを取得
    int leadTime = getDeliveryLeadTime(deliveryType, prefectureCode);
    if (leadTime < 0) {
      logger.warn("lead time not found.");
      return null;
    }

    // 時分秒を消去
    Date startDate = DateUtil.truncateDate(orderDate);

    return addDateWithoutHoliday(startDate, leadTime, deliveryType.getShopCode());
  }

  private int getDeliveryLeadTime(DeliveryType deliveryType, String prefectureCode) {
    Logger logger = Logger.getLogger(UtilServiceImpl.class);
    Object[] args = new Object[] {
        deliveryType.getShopCode(), deliveryType.getDeliveryTypeNo(), prefectureCode
    };
    ShippingCharge sc = DatabaseUtil.loadAsBean(new SimpleQuery(LEAD_TIME_QUERY, args), ShippingCharge.class);
    if (sc == null) {
      logger.warn("lead time not found.");
      return -1;
    }
    return sc.getLeadTime().intValue();

  }

  /**
   * 第一引数の日付にcount分の営業日を足した日付を取得する
   * 
   * @param startDate
   *          対象日
   * @param count
   *          日数
   * @param shopCode
   *          休日を取得するショップ
   * @return
   */
  private Date addDateWithoutHoliday(Date startDate, int count, String shopCode) {
    for (int i = 0; i < count + 1;) {
      // 配送指定日時の開始日を加算
      startDate = DateUtil.addDate(startDate, 1);

      // 休日でなければ(営業日なら)カウントを加算
      if (isBusinessDay(shopCode, startDate)) {
        i++;
      }
    }
    return startDate;
  }

  /**
   * 第一引数の日付からcount分の営業日を遡った日付を取得する
   * 
   * @param startDate
   *          対象日
   * @param count
   *          日数
   * @param shopCode
   *          休日を取得するショップ
   * @return 日付
   */
  public Date subDateWithoutHoliday(Date startDate, int count, String shopCode) {
    startDate = DateUtil.addDate(startDate, -1);
    count++;
    while (count > 0) {
      // 休日でなければ(営業日なら)カウントを加算
      if (isBusinessDay(shopCode, startDate)) {
        count--;
      }
      if (count > 0) {
        startDate = DateUtil.addDate(startDate, -1);
      }
    }
    return startDate;
  }

  /**
   * ショップと日付を指定して、その日が休日かどうかを調べます。
   * 
   * @param shopCode
   *          営業日/休日を判断するショップ
   * @param day
   *          対象日
   * @return 休日であればtrue
   */
  private boolean isHoliday(String shopCode, Date day) {
    Query query = new SimpleQuery(CHECK_HOLIDAY_QUERY, shopCode, DateUtil.toDateString(day));
    Long result = NumUtil.parseLong(DatabaseUtil.executeScalar(query));
    return result > 0L;
  }

  /**
   * ショップと日付を指定して、その日が営業日かどうかを調べます。
   * 
   * @param shopCode
   *          営業日/休日を判断するショップ
   * @param day
   *          対象日
   * @return 営業日であればtrue
   */
  public boolean isBusinessDay(String shopCode, Date day) {
    return !isHoliday(shopCode, day);
  }

  public List<CodeAttribute> getDeliveryAppointedTimes(DeliveryType deliveryType) {

    List<NameValue> nameValue = DatabaseUtil.loadAsBeanList(new SimpleQuery(DELIVERY_APPOINTED_TIMES, deliveryType.getShopCode(),
        deliveryType.getDeliveryTypeNo()), NameValue.class);
    List<CodeAttribute> result = new ArrayList<CodeAttribute>();
    if (nameValue.size() > 0) {
      result.add(new NameValue(Messages.getString("service.impl.UtilServiceImpl.1"), ""));
    } else {
      result.add(new NameValue("", ""));
    }
    result.addAll(nameValue);

    return result;
  }

  public List<CodeAttribute> getCategoryNames() {
    return getNameValue(CATEGORY_NAMES);
  }

  public List<CodeAttribute> getSubCategoryNames(String categoryCode) {

    List<NameValue> nameValue = DatabaseUtil.loadAsBeanList(new SimpleQuery(SUB_CATEGORY_NAMES, categoryCode), NameValue.class);
    List<CodeAttribute> result = new ArrayList<CodeAttribute>();
    result.add(new NameValue(Messages.getString("service.impl.UtilServiceImpl.2"), ""));
    result.addAll(nameValue);

    return result;

  }

  public List<CodeAttribute> getShopNames() {
    return getShopNames(false);
  }

  public List<CodeAttribute> getShopNames(boolean blank) {
    return getShopNames(blank, false);
  }

  public List<CodeAttribute> getShopNames(boolean blank, boolean site) {
    return getShopNames(blank, site, false);
  }

  public List<CodeAttribute> getShopNames(boolean blank, boolean site, boolean justOpen) {
    return getShopNames(blank, site, justOpen, false);
  }

  public List<CodeAttribute> getShopNamesDefaultAllShop(boolean site, boolean justOpen) {
    return getShopNames(true, site, justOpen, true);
  }

  public List<CodeAttribute> getShopNames(boolean blank, boolean site, boolean justOpen, boolean defaultAllShop) {

    String query = "SELECT SHORT_SHOP_NAME AS NAME, SHOP_CODE AS VALUE FROM SHOP WHERE ";

    WebshopConfig config = DIContainer.getWebshopConfig();
    List<CodeAttribute> result = null;
    if (config.getOperatingMode() == OperatingMode.ONE) {
      query += "SHOP_CODE = " + SqlDialect.getDefault().quote(config.getSiteShopCode());
      result = getNameValue(query);
    } else {

      if (site) {
        query += "1 = 1";
      } else {
        query += "SHOP_TYPE = '1' ";
      }

      if (justOpen) {
        query += " AND ((OPEN_DATETIME <= " + SqlDialect.getDefault().getCurrentDatetime() + " AND "
            + SqlDialect.getDefault().getTruncSysdate() + " <= CLOSE_DATETIME) OR " + " (OPEN_DATETIME <= "
            + SqlDialect.getDefault().getCurrentDatetime() + " AND CLOSE_DATETIME IS NULL)) ";
      }

      query += " ORDER BY SHOP_CODE";

      if (blank) {
        if (defaultAllShop) {
          result = getNameValueDefault(query, Messages.getString("service.impl.UtilServiceImpl.3"));
        } else {
          result = getNameValueDefault(query);
        }
      } else {
        result = getNameValue(query);
      }
    }
    return result;

  }

  public List<CodeAttribute> getCustomerGroupNames() {
    if (getCurrentLanguageCode().equals(LanguageCode.Zh_Cn.getValue())) {
      QUERY = CUSTOMER_GROUP_QUERY;
    } else if (getCurrentLanguageCode().equals(LanguageCode.En_Us.getValue())) {
      QUERY = CUSTOMER_GROUP_QUERY_EN;
    } else if (getCurrentLanguageCode().equals(LanguageCode.Ja_Jp.getValue())) {
      QUERY = CUSTOMER_GROUP_QUERY_JP;
    }
    return getNameValueDefault(QUERY);
  }

  private List<CodeAttribute> getNameValue(String query) {
    List<NameValue> result = DatabaseUtil.loadAsBeanList(new SimpleQuery(query), NameValue.class);
    return new ArrayList<CodeAttribute>(result);
  }

  private List<CodeAttribute> getNameValueDefault(String query) {
    return getNameValueDefault(query, Messages.getString("service.impl.UtilServiceImpl.17"));
  }

  private List<CodeAttribute> getNameValueDefault(String query, String defaultName) {

    List<NameValue> nameValue = DatabaseUtil.loadAsBeanList(new SimpleQuery(query), NameValue.class);
    List<CodeAttribute> result = new ArrayList<CodeAttribute>();
    result.add(new NameValue(defaultName, ""));
    result.addAll(nameValue);

    return result;
  }

  public List<CodeAttribute> getStockStatusNames(String shopCode) {

    List<NameValue> nameValue = DatabaseUtil.loadAsBeanList(new SimpleQuery(STOCK_STATUS_QUERY, shopCode), NameValue.class);
    List<CodeAttribute> result = new ArrayList<CodeAttribute>();
    result.add(new NameValue(Messages.getString("service.impl.UtilServiceImpl.5"), ""));
    result.addAll(nameValue);

    return result;
  }

  public List<CodeAttribute> getEnqueteQuestions(String enqueteCode) {

    List<NameValue> nameValue = DatabaseUtil.loadAsBeanList(new SimpleQuery(ENQUETE_QUESTIONS_QUERY, enqueteCode), NameValue.class);
    List<NameValue> nameValue2 = DatabaseUtil.loadAsBeanList(new SimpleQuery(ENQUETE_QUESTIONS_QUERY_EN, enqueteCode),
        NameValue.class);
    List<NameValue> nameValue3 = DatabaseUtil.loadAsBeanList(new SimpleQuery(ENQUETE_QUESTIONS_QUERY_JP, enqueteCode),
        NameValue.class);
    List<CodeAttribute> result = new ArrayList<CodeAttribute>();
    result.add(new NameValue(Messages.getString("service.impl.UtilServiceImpl.6"), ""));
    result.addAll(nameValue);
    result.addAll(nameValue2);
    result.addAll(nameValue3);
    return result;
  }

  public List<CodeAttribute> getDeliveryTypes(String shopCode, boolean blank) {

    List<NameValue> nameValue = DatabaseUtil.loadAsBeanList(new SimpleQuery(DELIVERY_TYPE_QUERY, shopCode), NameValue.class);
    List<CodeAttribute> result = new ArrayList<CodeAttribute>();
    if (blank) {
      result.add(new NameValue(Messages.getString("service.impl.UtilServiceImpl.7"), ""));
    }
    result.addAll(nameValue);

    return result;
  }

  // 20120116 ysy add start
  public List<CodeAttribute> getDeliveryCompany(String shopCode, boolean blank) {

    List<NameValue> nameValue = DatabaseUtil.loadAsBeanList(new SimpleQuery(DELIVERY_COMPANY_QUERY, shopCode), NameValue.class);
    List<CodeAttribute> result = new ArrayList<CodeAttribute>();
    if (blank) {
      result.add(new NameValue(Messages.getString("service.impl.UtilServiceImpl.10"), ""));
    }
    result.addAll(nameValue);

    return result;
  }

  // 20120116 ysy add end

  public Long getAppliedTaxRateNow() {
    return getAppliedTaxRateWhen(DateUtil.getSysdate());
  }

  public Long getAppliedTaxRateWhen(Date when) {
    BigDecimal taxRate = (BigDecimal) DatabaseUtil.executeScalar(new SimpleQuery(TAX_QUERY));
    return taxRate.longValue();
  }

  public InquiryConfig getInquiryConfig() {
    return DIContainer.getInquiryConfig();
  }

  public List<CodeAttribute> getGiftList(String shopCode, String commodityCode) {
    List<CodeAttribute> giftCodeList = new ArrayList<CodeAttribute>();
    giftCodeList.add(new NameValue(Messages.getString("service.impl.UtilServiceImpl.8"), ""));
    TaxUtil u = DIContainer.get("TaxUtil");
    Long taxRate = u.getTaxRate();

    if (StringUtil.isNullOrEmpty(shopCode) || StringUtil.isNullOrEmpty(commodityCode)) {
      return giftCodeList;
    }

    Query query = new SimpleQuery(CatalogQuery.GET_GIFT_COMMODITY_LIST, shopCode, commodityCode);
    List<Gift> giftList = DatabaseUtil.loadAsBeanList(query, Gift.class);

    for (Gift gift : giftList) {
      BigDecimal giftPrice = Price.getPriceIncludingTax(gift.getGiftPrice(), taxRate, String.valueOf(gift.getGiftTaxType()));
      NameValue value = new NameValue(gift.getGiftName() + "(" + Price.getFormatPrice(giftPrice) + ")", gift.getGiftCode());
      giftCodeList.add(value);
    }

    return giftCodeList;
  }

  public MailMagazineConfig getMailMagazineConfig() {
    return DIContainer.getMailMagazineConfig();
  }

  // 20120516 tuxinwei update start
  public List<CodeAttribute> getMajorCategories() {
    String currentLanguageCode = DIContainer.getLocaleContext().getCurrentLanguageCode();
    StringBuilder builder = new StringBuilder();
    builder.append("SELECT ");
    if (currentLanguageCode.equals(LanguageCode.Zh_Cn.getValue())) {
      builder.append("A.CATEGORY_NAME_PC AS NAME");
    } else if (currentLanguageCode.equals(LanguageCode.Ja_Jp.getValue())) {
      builder.append("A.CATEGORY_NAME_PC_JP AS NAME");
    } else if (currentLanguageCode.equals(LanguageCode.En_Us.getValue())) {
      builder.append("A.CATEGORY_NAME_PC_EN AS NAME");
    }
    builder.append(", A.CATEGORY_CODE AS VALUE "
        + "FROM CATEGORY A WHERE DEPTH <= 1 ORDER BY A.PATH, A.DISPLAY_ORDER, A.CATEGORY_CODE");

    Query query = new SimpleQuery(builder.toString());
    List<NameValue> list = DatabaseUtil.loadAsBeanList(query, NameValue.class);
    return new ArrayList<CodeAttribute>(list);
  }

  // 20120516 tuxinwei update end

  // add by V10-CH 170 start
  public List<CodeAttribute> getCityNames(String regionCode) {
    String regionCodeDo = "";
    List<CodeAttribute> codeList = new ArrayList<CodeAttribute>();
    codeList.add(new NameValue("--------", ""));
    if (StringUtil.hasValue(regionCode)) {
      regionCodeDo = regionCode;
      String query = "SELECT * FROM CITY WHERE REGION_CODE = '" + regionCodeDo + "' ORDER BY COUNTRY_CODE,REGION_CODE,CITY_CODE ";
      CityDao dao = DIContainer.getDao(CityDao.class);
      List<City> myCity = dao.findByQuery(query);
      for (City city : myCity) {
        codeList.add(new NameValue(city.getCityName(), city.getCityCode()));
      }
    }
    return codeList;
  }
  
  public List<CodeAttribute> getJdCityNames(String regionCode) {
    String regionCodeDo = "";
    List<CodeAttribute> codeList = new ArrayList<CodeAttribute>();
    codeList.add(new NameValue("--------", ""));
    if (StringUtil.hasValue(regionCode)) {
      regionCodeDo = regionCode;
      String query = "SELECT * FROM JD_CITY WHERE REGION_CODE = '" + regionCodeDo + "' ORDER BY COUNTRY_CODE,REGION_CODE,CITY_CODE ";
      JdCityDao dao = DIContainer.getDao(JdCityDao.class);
      List<JdCity> myCity = dao.findByQuery(query);
      for (JdCity city : myCity) {
        codeList.add(new NameValue(city.getCityName(), city.getCityCode()));
      }
    }
    return codeList;
  }

  public String getCityName(String regionCode, String cityCode) {
    String cityName = "";
    String countryCode = "cn";
    CityDao dao = DIContainer.getDao(CityDao.class);
    City myCity = dao.load(countryCode, regionCode, cityCode);
    if (myCity != null) {
      cityName = getNameByLanguage(myCity.getCityName(), myCity.getCityNameEn(), myCity.getCityNameJp());
    }
    return cityName;
  }

  // add by V10-CH 170 end

  // 20111227 shen add start
  public List<CodeAttribute> getDeliveryDateList(String shopCode, String prefectureCode, boolean codFlg,
      List<CartCommodityInfo> commodityList, String weight, String deliveryCompanyNo) {
    List<CodeAttribute> deliveryDateList = new ArrayList<CodeAttribute>();
    String codType = "";
    if (codFlg) {
      codType = CodType.OHTER.getValue();
    } else {
      codType = CodType.COD.getValue();
    }
    Query query = new SimpleQuery(LOAD_DELIVERY_DATE_QUERY, shopCode, prefectureCode, codType, weight, weight, deliveryCompanyNo);
    List<DeliveryRelatedInfo> deliveryRelatedInfoList = DatabaseUtil.loadAsBeanList(query, DeliveryRelatedInfo.class);
    boolean unusable = false;
    boolean assignable = false;
    boolean ferial = false;
    boolean holiday = false;
    for (DeliveryRelatedInfo deliveryRelatedInfo : deliveryRelatedInfoList) {
      if (deliveryRelatedInfo.getDeliveryDateType().equals(DeliveryDateType.UNUSABLE.longValue())) {
        unusable = true;
      } else if (deliveryRelatedInfo.getDeliveryDateType().equals(DeliveryDateType.ASSIGNABLE.longValue())) {
        assignable = true;
      } else if (deliveryRelatedInfo.getDeliveryDateType().equals(DeliveryDateType.FERIAL.longValue())) {
        ferial = true;
      } else if (deliveryRelatedInfo.getDeliveryDateType().equals(DeliveryDateType.HOLIDAY.longValue())) {
        holiday = true;
      }
    }

    // 无库存管理的商品存在的场合，指定具体交货日不可设定
    for (CartCommodityInfo commodity : commodityList) {
      if (StringUtil.hasValue(commodity.getStockManagementType())
          && commodity.getStockManagementType().equals(StockManagementType.NOSTOCK.getValue())) {
        assignable = false;
        ferial = false;
        holiday = false;
        break;
      }
    }

    if (unusable) {
      deliveryDateList.add(new NameValue(DeliveryDateType.UNUSABLE.getName(), DeliveryDateType.UNUSABLE.getValue()));
    }

    if (assignable || ferial || holiday) {
      String sysDateStr = DateUtil.getDateTimeString().replace("/", "-");// 系统日期时间字符串
      String sysConfString = sysDateStr.substring(0, 11) + DIContainer.getWebshopConfig().getFinalSysTime();// 根据配置拼接成的时间字符串
      Date sysDate = DateUtil.fromTimestampString(sysDateStr);// 系统时间
      Date confDate = DateUtil.fromTimestampString(sysConfString);// 配置文件时间

      int leadTime = 0;
      // 固定出仓日
      if (sysDate.after(confDate)) {
        leadTime += DIContainer.getWebshopConfig().getCommodityLeadTimeAfter().intValue();
      } else {
        leadTime += DIContainer.getWebshopConfig().getCommodityLeadTimeBefore().intValue();
      }

      // add by yyq 20130328 start
      // 判断配送指定特殊休息日
    //List<String> HoliList = DIContainer.getWebshopConfig().getDeliveryDateDesignHoliDay();
      
      CommunicationService shService = ServiceLocator.getCommunicationService(getLoginInfo());
      List<StockHoliday> shList =shService.getStockHolidayList();
        List<String> HoliList =new ArrayList<String>();
      for(StockHoliday sh : shList){
          Date hl = sh.getHolidayDay();
          SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
          String str = format.format(hl);
          HoliList.add(str);
      }
      if (HoliList != null && HoliList.size() > 0) {
        for (int i = 0; i < HoliList.size(); i++) {
          String sysHoliDayStr = DateUtil.toDateString(DateUtil.addDate(DateUtil.getSysdate(), leadTime)).replace("/", "-");
          for (int j = 0; j < HoliList.size(); j++) {
            if (sysHoliDayStr.equals(HoliList.get(j))) {
              leadTime++;
            }
          }
        }
      }
      // 判断配送指定休息日（星期几）
      List<Integer> weekList = DIContainer.getWebshopConfig().getDeliveryDateWeekDay();
      if (weekList != null && weekList.size() > 0) {
        for (int i = 0; i < weekList.size(); i++) {
          Date sysWeekDay = DateUtil.addDate(DateUtil.getSysdate(), leadTime);
          for (int j = 0; j < weekList.size(); j++) {
            if (DateUtil.getDayForWeek(sysWeekDay) == weekList.get(i)) {
              leadTime++;
            }
          }
        }
      }
      // 配送必要日
      query = new SimpleQuery(LOAD_DELIVERY_REGION_CHARGE_QUERY, shopCode, prefectureCode, deliveryCompanyNo);
      DeliveryRegionCharge deliveryRegionCharge = DatabaseUtil.loadAsBean(query, DeliveryRegionCharge.class);
      if (deliveryRegionCharge != null) {
        leadTime += deliveryRegionCharge.getLeadTime().intValue();
      }
      // // 出仓休息日
      // int extension = 0;
      // for (int i = 1; i < leadTime; i++) {
      // Date targetDate = DateUtil.addDate(DateUtil.getSysdate(), i);
      // int dayForWeek = DateUtil.getDayForWeek(targetDate);
      // for (int restDay :
      // DIContainer.getWebshopConfig().getWarehouseRestDay()) {
      // if (dayForWeek == restDay) {
      // extension++;
      // break;
      // }
      // }
      // }
      // leadTime += extension;

      // add by yyq 20130328 end

      Date startDate = DateUtil.addDate(DateUtil.getSysdate(), leadTime);
      int range = DIContainer.getWebshopConfig().getDeliveryDayRange();
      if (assignable || (ferial && holiday)) {
        // 指定可
        for (int i = 0; i < range; i++) {
          String dateStr = DateUtil.toDateString(DateUtil.addDate(startDate, i));
          deliveryDateList.add(new NameValue(dateStr, dateStr));
        }
      } else if (ferial) {
        // 平日
        for (int i = 0; i < range; i++) {
          if (isBusinessDay(shopCode, DateUtil.addDate(startDate, i))) {
            String dateStr = DateUtil.toDateString(DateUtil.addDate(startDate, i));
            deliveryDateList.add(new NameValue(dateStr, dateStr));
          }
        }
      } else if (holiday) {
        // 休息日
        for (int i = 0; i < range; i++) {
          if (isHoliday(shopCode, DateUtil.addDate(startDate, i))) {
            String dateStr = DateUtil.toDateString(DateUtil.addDate(startDate, i));
            deliveryDateList.add(new NameValue(dateStr, dateStr));
          }
        }
      }
    }
    return deliveryDateList;
  }

  public List<CodeAttribute> getDeliveryTimeList(String shopCode, String prefectureCode, boolean codFlg, String deliveryDate,
      String weight, String deliveryCompanyNo) {
    return getDeliveryTimeList(shopCode, prefectureCode, codFlg, deliveryDate, "", weight, deliveryCompanyNo);
  }

  public List<CodeAttribute> getDeliveryTimeList(String shopCode, String prefectureCode, boolean codFlg, String deliveryDate,
      String areaCode, String weight, String deliveryCompanyNo) {
    List<CodeAttribute> deliveryTimeList = new ArrayList<CodeAttribute>();
    if (StringUtil.isNullOrEmptyAnyOf(shopCode, prefectureCode, deliveryDate)) {
      return deliveryTimeList;
    }

    if (StringUtil.hasValue(areaCode)) {
      ShopManagementService shop = ServiceLocator.getShopManagementService(getLoginInfo());
      Area area = shop.getArea(areaCode);
      if (area == null || area.getAllowDeliveryTimeFlg().equals(AllowDeliveryTimeFlg.NO_ALLOW.longValue())) {
        deliveryTimeList.add(new NameValue(AppointedTimeType.NOT.getName(), AppointedTimeType.NOT.getValue()));
        return deliveryTimeList;
      }
    }

    String codType = "";
    if (codFlg) {
      codType = CodType.OHTER.getValue();
    } else {
      codType = CodType.COD.getValue();
    }

    String deliveryDateType1 = "";
    String deliveryDateType2 = "";
    DeliveryDateType type = DeliveryDateType.fromValue(deliveryDate);
    if (type != null) {
      deliveryDateType1 = type.getValue();
      deliveryDateType2 = type.getValue();
    } else {
      Date targetDate = DateUtil.fromString(deliveryDate);
      if (targetDate == null) {
        return deliveryTimeList;
      }

      if (isBusinessDay(shopCode, targetDate)) {
        deliveryDateType1 = DeliveryDateType.FERIAL.getValue();
      } else {
        deliveryDateType1 = DeliveryDateType.HOLIDAY.getValue();
      }
      deliveryDateType2 = DeliveryDateType.ASSIGNABLE.getValue();
    }

    Query query = new SimpleQuery(LOAD_DELIVERY_TIME_QUERY, shopCode, prefectureCode, codType, deliveryDateType1,
        deliveryDateType2, weight, weight, deliveryCompanyNo);
    List<DeliveryRelatedInfo> deliveryRelatedInfoList = DatabaseUtil.loadAsBeanList(query, DeliveryRelatedInfo.class);
    for (DeliveryRelatedInfo deliveryRelatedInfo : deliveryRelatedInfoList) {
      if (deliveryRelatedInfo.getDeliveryAppointedTimeType().equals(AppointedTimeType.NOT.longValue())) {
        deliveryTimeList.add(new NameValue(AppointedTimeType.NOT.getName(), AppointedTimeType.NOT.getValue()));
      } else {
        String deliveryDateCommssion = "";
        String name = "";
        String value = "";

        // ShopManagementService shopService =
        // ServiceLocator.getShopManagementService(getLoginInfo());
        // DeliveryRegion deliveryRegion =
        // shopService.getDeliveryRegion(deliveryRelatedInfo.getShopCode(),
        // deliveryRelatedInfo.getDeliveryCompanyNo(),
        // deliveryRelatedInfo.getPrefectureCode());
        // if (deliveryRegion != null) {
        // deliveryDateCommssion = "(￥" +
        // deliveryRegion.getDeliveryDatetimeCommission().toString() + ")";
        // }
        if (deliveryRelatedInfo.getDeliveryAppointedStartTime() != null
            && deliveryRelatedInfo.getDeliveryAppointedEndTime() != null) {
          name = MessageFormat.format(Messages.getString("service.impl.UtilServiceImpl.14"), deliveryRelatedInfo
              .getDeliveryAppointedStartTime().toString(), deliveryRelatedInfo.getDeliveryAppointedEndTime().toString());
          value = deliveryRelatedInfo.getDeliveryAppointedStartTime().toString() + "-"
              + deliveryRelatedInfo.getDeliveryAppointedEndTime().toString();
          deliveryTimeList.add(new NameValue(name + deliveryDateCommssion, value));
        } else if (deliveryRelatedInfo.getDeliveryAppointedStartTime() != null) {
          name = MessageFormat.format(Messages.getString("service.impl.UtilServiceImpl.15"), deliveryRelatedInfo
              .getDeliveryAppointedStartTime().toString());
          value = deliveryRelatedInfo.getDeliveryAppointedStartTime().toString() + "-";
          deliveryTimeList.add(new NameValue(name + deliveryDateCommssion, value));
        } else {
          name = MessageFormat.format(Messages.getString("service.impl.UtilServiceImpl.16"), deliveryRelatedInfo
              .getDeliveryAppointedEndTime().toString());
          value = "-" + deliveryRelatedInfo.getDeliveryAppointedEndTime().toString();
          deliveryTimeList.add(new NameValue(name + deliveryDateCommssion, value));
        }
      }
    }
    // add by cs_yuli 20120605 start
    deliveryTimeList = getDeliveryDateListAfterRemoveRepeat(deliveryTimeList);
    // add by cs_yuli 20120605 end
    return deliveryTimeList;
  }

  // add by cs_yuli 20120605 start
  public List<CodeAttribute> getDeliveryDateListAfterRemoveRepeat(List<CodeAttribute> deliveryTimeList) {
    List<CodeAttribute> tempDeliveryTimeList = new ArrayList<CodeAttribute>();
    for (int i = 0; i < deliveryTimeList.size(); i++) {
      CodeAttribute codeAttribute = deliveryTimeList.get(i);
      if (i == 0) {
        tempDeliveryTimeList.add(codeAttribute);
      }
      for (int j = i + 1; j < deliveryTimeList.size(); j++) {
        CodeAttribute codeAttributej = deliveryTimeList.get(j);
        if (!codeAttribute.getName().equals(codeAttributej.getName())) {
          tempDeliveryTimeList.add(codeAttributej);
          break;
        }
      }
    }
    return tempDeliveryTimeList;
  }

  // add by cs_yuli 20120605 end
  // soukai add 2012/03/27 ob start
  public List<CodeAttribute> getTmallDeliveryDateList(String shopCode, String prefectureCode, boolean codFlg,
      List<CartCommodityInfo> commodityList) {
    List<CodeAttribute> deliveryDateList = new ArrayList<CodeAttribute>();
    String codType = "";
    if (codFlg) {
      codType = CodType.OHTER.getValue();
    } else {
      codType = CodType.COD.getValue();
    }
    Query query = new SimpleQuery(LOAD_TMALL_DELIVERY_DATE_QUERY, shopCode, prefectureCode, codType);
    List<DeliveryRelatedInfo> deliveryRelatedInfoList = DatabaseUtil.loadAsBeanList(query, DeliveryRelatedInfo.class);
    boolean unusable = false;
    boolean assignable = false;
    boolean ferial = false;
    boolean holiday = false;
    for (DeliveryRelatedInfo deliveryRelatedInfo : deliveryRelatedInfoList) {
      if (deliveryRelatedInfo.getDeliveryDateType().equals(DeliveryDateType.UNUSABLE.longValue())) {
        unusable = true;
      } else if (deliveryRelatedInfo.getDeliveryDateType().equals(DeliveryDateType.ASSIGNABLE.longValue())) {
        assignable = true;
      } else if (deliveryRelatedInfo.getDeliveryDateType().equals(DeliveryDateType.FERIAL.longValue())) {
        ferial = true;
      } else if (deliveryRelatedInfo.getDeliveryDateType().equals(DeliveryDateType.HOLIDAY.longValue())) {
        holiday = true;
      }
    }

    // 无库存管理的商品存在的场合，指定具体交货日不可设定
    for (CartCommodityInfo commodity : commodityList) {
      if (StringUtil.hasValue(commodity.getStockManagementType())
          && commodity.getStockManagementType().equals(StockManagementType.NOSTOCK.getValue())) {
        assignable = false;
        ferial = false;
        holiday = false;
        break;
      }
    }

    if (unusable) {
      deliveryDateList.add(new NameValue(DeliveryDateType.UNUSABLE.getName(), DeliveryDateType.UNUSABLE.getValue()));
    }

    if (assignable || ferial || holiday) {
      int leadTime = 1;
      // 固定出仓日
      leadTime += DIContainer.getWebshopConfig().getCommodityLeadTime().intValue();
      // 配送必要日
      query = new SimpleQuery(TMALL_LOAD_DELIVERY_REGION_CHARGE_QUERY, shopCode, prefectureCode);
      DeliveryRegionCharge deliveryRegionCharge = DatabaseUtil.loadAsBean(query, DeliveryRegionCharge.class);
      if (deliveryRegionCharge != null) {
        leadTime += deliveryRegionCharge.getLeadTime().intValue();
      }
      // 出仓休息日
      int extension = 0;
      for (int i = 1; i < leadTime; i++) {
        Date targetDate = DateUtil.addDate(DateUtil.getSysdate(), i);
        int dayForWeek = DateUtil.getDayForWeek(targetDate);
        for (int restDay : DIContainer.getWebshopConfig().getWarehouseRestDay()) {
          if (dayForWeek == restDay) {
            extension++;
            break;
          }
        }
      }
      leadTime += extension;

      Date startDate = DateUtil.addDate(DateUtil.getSysdate(), leadTime);
      int range = DIContainer.getWebshopConfig().getDeliveryDayRange();
      if (assignable || (ferial && holiday)) {
        // 指定可
        for (int i = 0; i < range; i++) {
          String dateStr = DateUtil.toDateString(DateUtil.addDate(startDate, i));
          deliveryDateList.add(new NameValue(dateStr, dateStr));
        }
      } else if (ferial) {
        // 平日
        for (int i = 0; i < range; i++) {
          if (isBusinessDay(shopCode, DateUtil.addDate(startDate, i))) {
            String dateStr = DateUtil.toDateString(DateUtil.addDate(startDate, i));
            deliveryDateList.add(new NameValue(dateStr, dateStr));
          }
        }
      } else if (holiday) {
        // 休息日
        for (int i = 0; i < range; i++) {
          if (isHoliday(shopCode, DateUtil.addDate(startDate, i))) {
            String dateStr = DateUtil.toDateString(DateUtil.addDate(startDate, i));
            deliveryDateList.add(new NameValue(dateStr, dateStr));
          }
        }
      }
    }
    return deliveryDateList;
  }

  public List<CodeAttribute> getJdDeliveryDateList(String shopCode, String prefectureCode, boolean codFlg,
      List<CartCommodityInfo> commodityList) {
    List<CodeAttribute> deliveryDateList = new ArrayList<CodeAttribute>();
    String codType = "";
    if (codFlg) {
      codType = CodType.OHTER.getValue();
    } else {
      codType = CodType.COD.getValue();
    }
    Query query = new SimpleQuery(LOAD_JD_DELIVERY_DATE_QUERY, shopCode, prefectureCode, codType);
    List<DeliveryRelatedInfo> deliveryRelatedInfoList = DatabaseUtil.loadAsBeanList(query, DeliveryRelatedInfo.class);
    boolean unusable = false;
    boolean assignable = false;
    boolean ferial = false;
    boolean holiday = false;
    for (DeliveryRelatedInfo deliveryRelatedInfo : deliveryRelatedInfoList) {
      if (deliveryRelatedInfo.getDeliveryDateType().equals(DeliveryDateType.UNUSABLE.longValue())) {
        unusable = true;
      } else if (deliveryRelatedInfo.getDeliveryDateType().equals(DeliveryDateType.ASSIGNABLE.longValue())) {
        assignable = true;
      } else if (deliveryRelatedInfo.getDeliveryDateType().equals(DeliveryDateType.FERIAL.longValue())) {
        ferial = true;
      } else if (deliveryRelatedInfo.getDeliveryDateType().equals(DeliveryDateType.HOLIDAY.longValue())) {
        holiday = true;
      }
    }

    // 无库存管理的商品存在的场合，指定具体交货日不可设定
    for (CartCommodityInfo commodity : commodityList) {
      if (StringUtil.hasValue(commodity.getStockManagementType())
          && commodity.getStockManagementType().equals(StockManagementType.NOSTOCK.getValue())) {
        assignable = false;
        ferial = false;
        holiday = false;
        break;
      }
    }

    if (unusable) {
      deliveryDateList.add(new NameValue(DeliveryDateType.UNUSABLE.getName(), DeliveryDateType.UNUSABLE.getValue()));
    }

    if (assignable || ferial || holiday) {
      int leadTime = 1;
      // 固定出仓日
      leadTime += DIContainer.getWebshopConfig().getCommodityLeadTime().intValue();
      // 配送必要日
      query = new SimpleQuery(JD_LOAD_DELIVERY_REGION_CHARGE_QUERY, shopCode, prefectureCode);
      DeliveryRegionCharge deliveryRegionCharge = DatabaseUtil.loadAsBean(query, DeliveryRegionCharge.class);
      if (deliveryRegionCharge != null) {
        leadTime += deliveryRegionCharge.getLeadTime().intValue();
      }
      // 出仓休息日
      int extension = 0;
      for (int i = 1; i < leadTime; i++) {
        Date targetDate = DateUtil.addDate(DateUtil.getSysdate(), i);
        int dayForWeek = DateUtil.getDayForWeek(targetDate);
        for (int restDay : DIContainer.getWebshopConfig().getWarehouseRestDay()) {
          if (dayForWeek == restDay) {
            extension++;
            break;
          }
        }
      }
      leadTime += extension;

      Date startDate = DateUtil.addDate(DateUtil.getSysdate(), leadTime);
      int range = DIContainer.getWebshopConfig().getDeliveryDayRange();
      if (assignable || (ferial && holiday)) {
        // 指定可
        for (int i = 0; i < range; i++) {
          String dateStr = DateUtil.toDateString(DateUtil.addDate(startDate, i));
          deliveryDateList.add(new NameValue(dateStr, dateStr));
        }
      } else if (ferial) {
        // 平日
        for (int i = 0; i < range; i++) {
          if (isBusinessDay(shopCode, DateUtil.addDate(startDate, i))) {
            String dateStr = DateUtil.toDateString(DateUtil.addDate(startDate, i));
            deliveryDateList.add(new NameValue(dateStr, dateStr));
          }
        }
      } else if (holiday) {
        // 休息日
        for (int i = 0; i < range; i++) {
          if (isHoliday(shopCode, DateUtil.addDate(startDate, i))) {
            String dateStr = DateUtil.toDateString(DateUtil.addDate(startDate, i));
            deliveryDateList.add(new NameValue(dateStr, dateStr));
          }
        }
      }
    }
    return deliveryDateList;
  }
  
  public List<CodeAttribute> getTmallDeliveryTimeList(String shopCode, String prefectureCode, boolean codFlg, String deliveryDate) {
    return getTmallDeliveryTimeList(shopCode, prefectureCode, codFlg, deliveryDate, "");
  }

  public List<CodeAttribute> getTmallDeliveryTimeList(String shopCode, String prefectureCode, boolean codFlg, String deliveryDate,
      String areaCode) {
    List<CodeAttribute> deliveryTimeList = new ArrayList<CodeAttribute>();

    if (StringUtil.isNullOrEmptyAnyOf(shopCode, prefectureCode, deliveryDate)) {
      return deliveryTimeList;
    }

    if (StringUtil.hasValue(areaCode)) {
      ShopManagementService shop = ServiceLocator.getShopManagementService(getLoginInfo());
      Area area = shop.getArea(areaCode);
      if (area == null || area.getAllowDeliveryTimeFlg().equals(AllowDeliveryTimeFlg.NO_ALLOW.longValue())) {
        deliveryTimeList.add(new NameValue(AppointedTimeType.NOT.getName(), AppointedTimeType.NOT.getValue()));
        return deliveryTimeList;
      }
    }

    String codType = "";
    if (codFlg) {
      codType = CodType.OHTER.getValue();
    } else {
      codType = CodType.COD.getValue();
    }

    String deliveryDateType1 = "";
    String deliveryDateType2 = "";
    DeliveryDateType type = DeliveryDateType.fromValue(deliveryDate);
    if (type != null) {
      deliveryDateType1 = type.getValue();
      deliveryDateType2 = type.getValue();
    } else {
      Date targetDate = DateUtil.fromString(deliveryDate);
      if (targetDate == null) {
        return deliveryTimeList;
      }

      if (isBusinessDay(shopCode, targetDate)) {
        deliveryDateType1 = DeliveryDateType.FERIAL.getValue();
      } else {
        deliveryDateType1 = DeliveryDateType.HOLIDAY.getValue();
      }
      deliveryDateType2 = DeliveryDateType.ASSIGNABLE.getValue();
    }

    Query query = new SimpleQuery(LOAD_TMALL_DELIVERY_TIME_QUERY, shopCode, prefectureCode, codType, deliveryDateType1,
        deliveryDateType2);
    List<DeliveryRelatedInfo> deliveryRelatedInfoList = DatabaseUtil.loadAsBeanList(query, DeliveryRelatedInfo.class);
    for (DeliveryRelatedInfo deliveryRelatedInfo : deliveryRelatedInfoList) {
      if (deliveryRelatedInfo.getDeliveryAppointedTimeType().equals(AppointedTimeType.NOT.longValue())) {
        deliveryTimeList.add(new NameValue(AppointedTimeType.NOT.getName(), AppointedTimeType.NOT.getValue()));
      } else {
        String name = "";
        String value = "";

        if (deliveryRelatedInfo.getDeliveryAppointedStartTime() != null
            && deliveryRelatedInfo.getDeliveryAppointedEndTime() != null) {
          name = deliveryRelatedInfo.getDeliveryAppointedStartTime().toString()
              + Messages.getString("service.impl.UtilServiceImpl.18") + "~"
              + deliveryRelatedInfo.getDeliveryAppointedEndTime().toString()
              + Messages.getString("service.impl.UtilServiceImpl.18");
          value = deliveryRelatedInfo.getDeliveryAppointedStartTime().toString() + "-"
              + deliveryRelatedInfo.getDeliveryAppointedEndTime().toString();
          deliveryTimeList.add(new NameValue(name, value));
        } else if (deliveryRelatedInfo.getDeliveryAppointedStartTime() != null) {
          name = deliveryRelatedInfo.getDeliveryAppointedStartTime().toString()
              + Messages.getString("service.impl.UtilServiceImpl.19");
          value = deliveryRelatedInfo.getDeliveryAppointedStartTime().toString() + "-";
          deliveryTimeList.add(new NameValue(name, value));
        } else {
          name = deliveryRelatedInfo.getDeliveryAppointedEndTime().toString()
              + Messages.getString("service.impl.UtilServiceImpl.20");
          value = "-" + deliveryRelatedInfo.getDeliveryAppointedEndTime().toString();
          deliveryTimeList.add(new NameValue(name, value));
        }
      }
    }

    return deliveryTimeList;
  }

  public List<CodeAttribute> getJdDeliveryTimeList(String shopCode, String prefectureCode, boolean codFlg, String deliveryDate,
      String areaCode) {
    List<CodeAttribute> deliveryTimeList = new ArrayList<CodeAttribute>();

    if (StringUtil.isNullOrEmptyAnyOf(shopCode, prefectureCode, deliveryDate)) {
      return deliveryTimeList;
    }

    if (StringUtil.hasValue(areaCode)) {
      ShopManagementService shop = ServiceLocator.getShopManagementService(getLoginInfo());
      Area area = shop.getArea(areaCode);
      if (area == null || area.getAllowDeliveryTimeFlg().equals(AllowDeliveryTimeFlg.NO_ALLOW.longValue())) {
        deliveryTimeList.add(new NameValue(AppointedTimeType.NOT.getName(), AppointedTimeType.NOT.getValue()));
        return deliveryTimeList;
      }
    }

    String codType = "";
    if (codFlg) {
      codType = CodType.OHTER.getValue();
    } else {
      codType = CodType.COD.getValue();
    }

    String deliveryDateType1 = "";
    String deliveryDateType2 = "";
    DeliveryDateType type = DeliveryDateType.fromValue(deliveryDate);
    if (type != null) {
      deliveryDateType1 = type.getValue();
      deliveryDateType2 = type.getValue();
    } else {
      Date targetDate = DateUtil.fromString(deliveryDate);
      if (targetDate == null) {
        return deliveryTimeList;
      }

      if (isBusinessDay(shopCode, targetDate)) {
        deliveryDateType1 = DeliveryDateType.FERIAL.getValue();
      } else {
        deliveryDateType1 = DeliveryDateType.HOLIDAY.getValue();
      }
      deliveryDateType2 = DeliveryDateType.ASSIGNABLE.getValue();
    }

    Query query = new SimpleQuery(LOAD_JD_DELIVERY_TIME_QUERY, shopCode, prefectureCode, codType, deliveryDateType1,
        deliveryDateType2);
    List<DeliveryRelatedInfo> deliveryRelatedInfoList = DatabaseUtil.loadAsBeanList(query, DeliveryRelatedInfo.class);
    for (DeliveryRelatedInfo deliveryRelatedInfo : deliveryRelatedInfoList) {
      if (deliveryRelatedInfo.getDeliveryAppointedTimeType().equals(AppointedTimeType.NOT.longValue())) {
        deliveryTimeList.add(new NameValue(AppointedTimeType.NOT.getName(), AppointedTimeType.NOT.getValue()));
      } else {
        String name = "";
        String value = "";

        if (deliveryRelatedInfo.getDeliveryAppointedStartTime() != null
            && deliveryRelatedInfo.getDeliveryAppointedEndTime() != null) {
          name = deliveryRelatedInfo.getDeliveryAppointedStartTime().toString()
              + Messages.getString("service.impl.UtilServiceImpl.18") + "~"
              + deliveryRelatedInfo.getDeliveryAppointedEndTime().toString()
              + Messages.getString("service.impl.UtilServiceImpl.18");
          value = deliveryRelatedInfo.getDeliveryAppointedStartTime().toString() + "-"
              + deliveryRelatedInfo.getDeliveryAppointedEndTime().toString();
          deliveryTimeList.add(new NameValue(name, value));
        } else if (deliveryRelatedInfo.getDeliveryAppointedStartTime() != null) {
          name = deliveryRelatedInfo.getDeliveryAppointedStartTime().toString()
              + Messages.getString("service.impl.UtilServiceImpl.19");
          value = deliveryRelatedInfo.getDeliveryAppointedStartTime().toString() + "-";
          deliveryTimeList.add(new NameValue(name, value));
        } else {
          name = deliveryRelatedInfo.getDeliveryAppointedEndTime().toString()
              + Messages.getString("service.impl.UtilServiceImpl.20");
          value = "-" + deliveryRelatedInfo.getDeliveryAppointedEndTime().toString();
          deliveryTimeList.add(new NameValue(name, value));
        }
      }
    }

    return deliveryTimeList;
  }
  
  public DeliveryCompany getTmallDeliveryCompany(String shopCode, String prefectureCode, boolean codFlg, String deliveryDate,
      String deliveryStartTime, String deliveryEndTime) {
    DeliveryCompany deliveryCompany = new DeliveryCompany();
    StringBuilder builder = new StringBuilder();
    List<Object> params = new ArrayList<Object>();
    String codType = "";

    if (codFlg) {
      codType = CodType.OHTER.getValue();
    } else {
      codType = CodType.COD.getValue();
    }

    String deliveryDateType1 = DeliveryDateType.UNUSABLE.getValue();
    String deliveryDateType2 = DeliveryDateType.UNUSABLE.getValue();
    DeliveryDateType type = DeliveryDateType.fromValue(deliveryDate);
    if (type != null) {
      deliveryDateType1 = type.getValue();
      deliveryDateType2 = type.getValue();
    } else {
      Date targetDate = DateUtil.fromString(deliveryDate);
      if (targetDate != null) {
        if (isBusinessDay(shopCode, targetDate)) {
          deliveryDateType1 = DeliveryDateType.FERIAL.getValue();
        } else {
          deliveryDateType1 = DeliveryDateType.HOLIDAY.getValue();
        }
        deliveryDateType2 = DeliveryDateType.ASSIGNABLE.getValue();
      }
    }

    builder.append(LOAD_TMALL_DELIVERY_RELATED_INFO_QUERY);
    params.add(shopCode);
    params.add(prefectureCode);
    params.add(codType);
    params.add(deliveryDateType1);
    params.add(deliveryDateType2);

    builder.append(" AND DELIVERY_APPOINTED_TIME_TYPE = ?");
    if (StringUtil.isNullOrEmpty(deliveryStartTime) && StringUtil.isNullOrEmpty(deliveryEndTime)) {
      params.add(AppointedTimeType.NOT.getValue());
    } else {
      params.add(AppointedTimeType.DO.getValue());
      if (StringUtil.hasValue(deliveryStartTime)) {
        builder.append(" AND DELIVERY_APPOINTED_START_TIME = ?");
        params.add(deliveryStartTime);
      } else {
        builder.append(" AND DELIVERY_APPOINTED_START_TIME IS NULL");
      }
      if (StringUtil.hasValue(deliveryEndTime)) {
        builder.append(" AND DELIVERY_APPOINTED_END_TIME = ?");
        params.add(deliveryEndTime);
      } else {
        builder.append(" AND DELIVERY_APPOINTED_END_TIME IS NULL");
      }
    }

    Query query = new SimpleQuery(builder.toString(), params.toArray());
    DeliveryRelatedInfo deliveryRelatedInfo = DatabaseUtil.loadAsBean(query, DeliveryRelatedInfo.class);
    if (deliveryRelatedInfo != null) {
      DeliveryCompanyDao dao = DIContainer.getDao(DeliveryCompanyDao.class);
      deliveryCompany = dao.load(deliveryRelatedInfo.getShopCode(), deliveryRelatedInfo.getDeliveryCompanyNo());
    }

    return deliveryCompany;
  }

  // soukai add 2012/03/27 ob end

  public DeliveryCompany getDeliveryCompany(String shopCode, String prefectureCode, boolean codFlg, String weight) {
    DeliveryCompany deliveryCompany = new DeliveryCompany();
    StringBuilder builder = new StringBuilder();
    List<Object> params = new ArrayList<Object>();
    String codType = "";

    if (codFlg) {
      codType = CodType.OHTER.getValue();
    } else {
      codType = CodType.COD.getValue();
    }
    /*
     * String deliveryDateType1 = DeliveryDateType.UNUSABLE.getValue(); String
     * deliveryDateType2 = DeliveryDateType.UNUSABLE.getValue();
     * DeliveryDateType type = DeliveryDateType.fromValue(deliveryDate); if
     * (type != null) { deliveryDateType1 = type.getValue(); deliveryDateType2 =
     * type.getValue(); } else { Date targetDate =
     * DateUtil.fromString(deliveryDate); if (targetDate != null) { if
     * (isBusinessDay(shopCode, targetDate)) { deliveryDateType1 =
     * DeliveryDateType.FERIAL.getValue(); } else { deliveryDateType1 =
     * DeliveryDateType.HOLIDAY.getValue(); } deliveryDateType2 =
     * DeliveryDateType.ASSIGNABLE.getValue(); } }
     */
    builder.append(LOAD_DELIVERY_RELATED_INFO_QUERY);
    params.add(shopCode);
    params.add(prefectureCode);
    params.add(codType);
    // params.add(deliveryDateType1);
    // params.add(deliveryDateType2);
    params.add(weight);
    params.add(weight);
    params.add(weight);
    params.add(weight);
    /*
     * builder.append(" AND DELIVERY_APPOINTED_TIME_TYPE = ?"); if
     * (StringUtil.isNullOrEmpty(deliveryStartTime) &&
     * StringUtil.isNullOrEmpty(deliveryEndTime)) {
     * params.add(AppointedTimeType.NOT.getValue()); } else {
     * params.add(AppointedTimeType.DO.getValue()); if
     * (StringUtil.hasValue(deliveryStartTime)) {
     * builder.append(" AND DELIVERY_APPOINTED_START_TIME = ?");
     * params.add(deliveryStartTime); } else {
     * builder.append(" AND DELIVERY_APPOINTED_START_TIME IS NULL"); } if
     * (StringUtil.hasValue(deliveryEndTime)) {
     * builder.append(" AND DELIVERY_APPOINTED_END_TIME = ?");
     * params.add(deliveryEndTime); } else {
     * builder.append(" AND DELIVERY_APPOINTED_END_TIME IS NULL"); } }
     */
    Query query = new SimpleQuery(builder.toString(), params.toArray());
    DeliveryRelatedInfo deliveryRelatedInfo = DatabaseUtil.loadAsBean(query, DeliveryRelatedInfo.class);
    if (deliveryRelatedInfo != null) {
      DeliveryCompanyDao dao = DIContainer.getDao(DeliveryCompanyDao.class);
      deliveryCompany = dao.load(deliveryRelatedInfo.getShopCode(), deliveryRelatedInfo.getDeliveryCompanyNo());
    }

    return deliveryCompany;
  }

  // 20111227 shen add end
  // add by lc 2012-08-23 start 取得符合条件的全部配送公司
  // 2013/04/21 优惠券对应 ob update start
  //public List<DeliveryCompany> getDeliveryCompanys(String shopCode, String prefectureCode, boolean codFlg, String weight) {
  public List<DeliveryCompany> getDeliveryCompanys(String shopCode, String prefectureCode, String cityCode, String areaCode, boolean codFlg, String weight) {
  // 2013/04/21 优惠券对应 ob update end
  
    List<DeliveryCompany> deliveryCompany = new ArrayList<DeliveryCompany>();
    StringBuilder builder = new StringBuilder();
    List<Object> params = new ArrayList<Object>();
    String codType = "";

    if (codFlg) {
      codType = CodType.OHTER.getValue();
    } else {
      codType = CodType.COD.getValue();
    }
    /*
     * String deliveryDateType1 = DeliveryDateType.UNUSABLE.getValue(); String
     * deliveryDateType2 = DeliveryDateType.UNUSABLE.getValue();
     * DeliveryDateType type = DeliveryDateType.fromValue(deliveryDate); if
     * (type != null) { deliveryDateType1 = type.getValue(); deliveryDateType2 =
     * type.getValue(); } else { Date targetDate =
     * DateUtil.fromString(deliveryDate); if (targetDate != null) { if
     * (isBusinessDay(shopCode, targetDate)) { deliveryDateType1 =
     * DeliveryDateType.FERIAL.getValue(); } else { deliveryDateType1 =
     * DeliveryDateType.HOLIDAY.getValue(); } deliveryDateType2 =
     * DeliveryDateType.ASSIGNABLE.getValue(); } }
     */
    builder.append(LOAD_DELIVERY_RELATED_INFO_QUERY);
    params.add(shopCode);
    params.add(prefectureCode);
    params.add(codType);
    // params.add(deliveryDateType1);
    // params.add(deliveryDateType2);
    // 2013/04/22 优惠券对应 ob add start
    params.add(cityCode);
    params.add(areaCode);
    // 2013/04/22 优惠券对应 ob add end
    params.add(weight);
    params.add(weight);
    params.add(weight);
    params.add(weight);
    /*
     * builder.append(" AND DELIVERY_APPOINTED_TIME_TYPE = ?"); if
     * (StringUtil.isNullOrEmpty(deliveryStartTime) &&
     * StringUtil.isNullOrEmpty(deliveryEndTime)) {
     * params.add(AppointedTimeType.NOT.getValue()); } else {
     * params.add(AppointedTimeType.DO.getValue()); if
     * (StringUtil.hasValue(deliveryStartTime)) {
     * builder.append(" AND DELIVERY_APPOINTED_START_TIME = ?");
     * params.add(deliveryStartTime); } else {
     * builder.append(" AND DELIVERY_APPOINTED_START_TIME IS NULL"); } if
     * (StringUtil.hasValue(deliveryEndTime)) {
     * builder.append(" AND DELIVERY_APPOINTED_END_TIME = ?");
     * params.add(deliveryEndTime); } else {
     * builder.append(" AND DELIVERY_APPOINTED_END_TIME IS NULL"); } }
     */
    Query query = new SimpleQuery(builder.toString(), params.toArray());
    List<DeliveryRelatedInfo> deliveryRelatedInfo = DatabaseUtil.loadAsBeanList(query, DeliveryRelatedInfo.class);
    for (DeliveryRelatedInfo dri : deliveryRelatedInfo) {
      DeliveryCompanyDao dao = DIContainer.getDao(DeliveryCompanyDao.class);
      DeliveryCompany tempDeliveryCompany = dao.load(dri.getShopCode(), dri.getDeliveryCompanyNo());
      deliveryCompany.add(tempDeliveryCompany);
    }
    return deliveryCompany;
  }

  // add by lc 2012-08-23 end
  // 20120105 shen add start
  public List<CodeAttribute> createPrefectureList(boolean hasTitle) {
    String currentLanguageCode = getCurrentLanguageCode();
    List<CodeAttribute> prefectureList = new ArrayList<CodeAttribute>();
    if (hasTitle) {
      prefectureList.add(new NameValue(Messages.getString("service.impl.UtilServiceImpl.11"), ""));
    }
    PrefectureDao dao = DIContainer.getDao(PrefectureDao.class);
    List<Prefecture> result = dao.loadAllByOrmRowid();
    for (Prefecture p : result) {
      prefectureList.add(new NameValue(getNameByLanguage(p.getPrefectureName(), p.getPrefectureNameEn(), p.getPrefectureNameJp(),
          currentLanguageCode), p.getPrefectureCode()));
    }

    return prefectureList;
  }

  public List<CodeAttribute> createJdPrefectureList(boolean hasTitle) {
    List<CodeAttribute> prefectureList = new ArrayList<CodeAttribute>();
    if (hasTitle) {
      prefectureList.add(new NameValue(Messages.getString("service.impl.UtilServiceImpl.11"), ""));
    }
    JdPrefectureDao dao = DIContainer.getDao(JdPrefectureDao.class);
    List<JdPrefecture> result = dao.loadAllByOrmRowid();
    for (JdPrefecture p : result) {
      prefectureList.add(new NameValue(p.getPrefectureName(), p.getPrefectureCode()));
    }
    return prefectureList;
  }
  
  public List<CodeAttribute> createPrefectureList() {
    return createPrefectureList(true);
  }
  
  public List<CodeAttribute> createJdPrefectureList() {
    return createJdPrefectureList(true);
  }

  public String getPrefectureName(String prefectureCode) {
    String prefectureName = "";
    PrefectureDao dao = DIContainer.getDao(PrefectureDao.class);
    Prefecture prefecture = dao.load(prefectureCode);
    if (prefecture != null) {
      prefectureName = getNameByLanguage(prefecture.getPrefectureName(), prefecture.getPrefectureNameEn(), prefecture
          .getPrefectureNameJp());
    }

    return prefectureName;
  }

  public List<CodeAttribute> createJdCityList(String prefectureCode) {
    List<CodeAttribute> cityList = new ArrayList<CodeAttribute>();
    cityList.add(new NameValue(Messages.getString("service.impl.UtilServiceImpl.12"), ""));
    Query query = new SimpleQuery(LOAD_JD_CITY_QUERY, prefectureCode);
    List<JdCity> result = DatabaseUtil.loadAsBeanList(query, JdCity.class);
    for (JdCity c : result) {
      cityList.add(new NameValue(c.getCityName(), c.getCityCode()));
    }
    return cityList;
  }
  
  public List<CodeAttribute> createCityList(String prefectureCode) {
    String currentLanguageCode = getCurrentLanguageCode();
    List<CodeAttribute> cityList = new ArrayList<CodeAttribute>();
    cityList.add(new NameValue(Messages.getString("service.impl.UtilServiceImpl.12"), ""));
    Query query = new SimpleQuery(LOAD_CITY_QUERY, prefectureCode);
    List<City> result = DatabaseUtil.loadAsBeanList(query, City.class);
    for (City c : result) {
      cityList.add(new NameValue(getNameByLanguage(c.getCityName(), c.getCityNameEn(), c.getCityNameJp(), currentLanguageCode), c
          .getCityCode()));
    }

    return cityList;
  }

  public List<CodeAttribute> createAreaList(String prefectureCode, String cityCode) {
    String currentLanguageCode = getCurrentLanguageCode();
    List<CodeAttribute> areaList = new ArrayList<CodeAttribute>();
    areaList.add(new NameValue(Messages.getString("service.impl.UtilServiceImpl.13"), ""));
    Query query = new SimpleQuery(LOAD_AREA_QUERY, prefectureCode, cityCode);
    List<Area> result = DatabaseUtil.loadAsBeanList(query, Area.class);
    for (Area a : result) {
      areaList.add(new NameValue(getNameByLanguage(a.getAreaName(), a.getAreaNameEn(), a.getAreaNameJp(), currentLanguageCode), a.getAreaCode()));
    }
    return areaList;
  }

  public List<CodeAttribute> createJdAreaList(String prefectureCode, String cityCode) {
    List<CodeAttribute> areaList = new ArrayList<CodeAttribute>();
    areaList.add(new NameValue(Messages.getString("service.impl.UtilServiceImpl.13"), ""));
    Query query = new SimpleQuery(LOAD_JD_AREA_QUERY, prefectureCode, cityCode);
    List<JdArea> result = DatabaseUtil.loadAsBeanList(query, JdArea.class);
    for (JdArea a : result) {
      areaList.add(new NameValue(a.getAreaName(), a.getAreaCode()));
    }
    return areaList;
  }
  
  public String getAreaName(String areaCode) {
    String areaName = "";
    AreaDao dao = DIContainer.getDao(AreaDao.class);
    Area area = dao.load(areaCode);
    if (area != null) {
      areaName = getNameByLanguage(area.getAreaName(), area.getAreaNameEn(), area.getAreaNameJp());
    }

    return areaName;
  }

  public String createAddressScript() {
    String currentLanguageCode = getCurrentLanguageCode();
    StringBuffer script = new StringBuffer();
    script.append("<script type=\"text/javascript\">");
    script.append("var addressArray = {");

    Query query = null;
    if (currentLanguageCode.equals(LanguageCode.Zh_Cn.getValue())) {
      query = new SimpleQuery(LOAD_ADDRESS_QUERY);
    } else if (currentLanguageCode.equals(LanguageCode.En_Us.getValue())) {
      query = new SimpleQuery(LOAD_ADDRESS_EN_QUERY);
    } else if (currentLanguageCode.equals(LanguageCode.Ja_Jp.getValue())) {
      query = new SimpleQuery(LOAD_ADDRESS_JP_QUERY);
    }
    if (query == null) {
      return "";
    }
    List<AddressInfo> result = DatabaseUtil.loadAsBeanList(query, AddressInfo.class);
    String lastPrefectureCode = "";
    String lastCityCode = "";
    for (AddressInfo address : result) {
      if (StringUtil.isNullOrEmpty(lastPrefectureCode)) {
        lastPrefectureCode = address.getPrefectureCode();
        script.append("'" + address.getPrefectureCode() + "':{");
      } else if (!address.getPrefectureCode().equals(lastPrefectureCode)) {
        lastPrefectureCode = address.getPrefectureCode();
        lastCityCode = "";
        script.append("                                                                        }");
        script.append("                                         }");
        script.append("                                     }");
        script.append(" },");
        script.append("'" + address.getPrefectureCode() + "':{");
      }

      if (StringUtil.isNullOrEmpty(lastCityCode)) {
        lastCityCode = address.getCityCode();
        script.append("                                     city:{");
        script.append("                                         '" + address.getCityCode() + "':{");
        script.append("                                                                         name:'" + address.getCityName()
            + "',");
        script.append("                                                                         area:{");
        if (StringUtil.hasValue(address.getAreaCode())) {
          script.append("                                                                               '" + address.getAreaCode()
              + "':{name:'" + address.getAreaName() + "'}");
        }
      } else if (!address.getCityCode().equals(lastCityCode)) {
        lastCityCode = address.getCityCode();
        script.append("                                                                         }");
        script.append("                                         },'" + address.getCityCode() + "':{");
        script.append("                                                                         name:'" + address.getCityName()
            + "',");
        script.append("                                                                         area:{");
        if (StringUtil.hasValue(address.getAreaCode())) {
          script.append("                                                                               '" + address.getAreaCode()
              + "':{name:'" + address.getAreaName() + "'}");
        }
      } else {
        if (StringUtil.hasValue(address.getAreaCode())) {
          script.append("                                                                               ,'" + address.getAreaCode()
              + "':{name:'" + address.getAreaName() + "'}");
        }
      }
    }
    script.append("                                                                        }");
    script.append("                                         }");
    script.append("                                     }");
    script.append(" }");

    script.append("}");
    script.append("</script>");

    return script.toString();
  }


  public String createJdAddressScript() {
    String currentLanguageCode = getCurrentLanguageCode();
    StringBuffer script = new StringBuffer();
    script.append("<script type=\"text/javascript\">");
    script.append("var addressArray = {");

    Query query = null;
    if (currentLanguageCode.equals(LanguageCode.Zh_Cn.getValue())) {
      query = new SimpleQuery(LOAD_JD_ADDRESS_QUERY);
    } else if (currentLanguageCode.equals(LanguageCode.En_Us.getValue())) {
      query = new SimpleQuery(LOAD_JD_ADDRESS_EN_QUERY);
    } else if (currentLanguageCode.equals(LanguageCode.Ja_Jp.getValue())) {
      query = new SimpleQuery(LOAD_JD_ADDRESS_JP_QUERY);
    }
    if (query == null) {
      return "";
    }
    List<AddressInfo> result = DatabaseUtil.loadAsBeanList(query, AddressInfo.class);
    String lastPrefectureCode = "";
    String lastCityCode = "";
    for (AddressInfo address : result) {
      if (StringUtil.isNullOrEmpty(lastPrefectureCode)) {
        lastPrefectureCode = address.getPrefectureCode();
        script.append("'" + address.getPrefectureCode() + "':{");
      } else if (!address.getPrefectureCode().equals(lastPrefectureCode)) {
        lastPrefectureCode = address.getPrefectureCode();
        lastCityCode = "";
        script.append("                                                                        }");
        script.append("                                         }");
        script.append("                                     }");
        script.append(" },");
        script.append("'" + address.getPrefectureCode() + "':{");
      }

      if (StringUtil.isNullOrEmpty(lastCityCode)) {
        lastCityCode = address.getCityCode();
        script.append("                                     city:{");
        script.append("                                         '" + address.getCityCode() + "':{");
        script.append("                                                                         name:'" + address.getCityName()
            + "',");
        script.append("                                                                         area:{");
        if (StringUtil.hasValue(address.getAreaCode())) {
          script.append("                                                                               '" + address.getAreaCode()
              + "':{name:'" + address.getAreaName() + "'}");
        }
      } else if (!address.getCityCode().equals(lastCityCode)) {
        lastCityCode = address.getCityCode();
        script.append("                                                                         }");
        script.append("                                         },'" + address.getCityCode() + "':{");
        script.append("                                                                         name:'" + address.getCityName()
            + "',");
        script.append("                                                                         area:{");
        if (StringUtil.hasValue(address.getAreaCode())) {
          script.append("                                                                               '" + address.getAreaCode()
              + "':{name:'" + address.getAreaName() + "'}");
        }
      } else {
        if (StringUtil.hasValue(address.getAreaCode())) {
          script.append("                                                                               ,'" + address.getAreaCode()
              + "':{name:'" + address.getAreaName() + "'}");
        }
      }
    }
    script.append("                                                                        }");
    script.append("                                         }");
    script.append("                                     }");
    script.append(" }");

    script.append("}");
    script.append("</script>");

    return script.toString();
  }
  
  // 20120105 shen add end

  // 20120112 shen add start
  public String getPrefectureCode(String prefectureName) {
    String prefectureCode = "";

    Query query = new SimpleQuery(LOAD_PREFECTURE_QUERY, prefectureName);
    Prefecture prefecture = DatabaseUtil.loadAsBean(query, Prefecture.class);
    if (prefecture != null) {
      prefectureCode = prefecture.getPrefectureCode();
    }

    return prefectureCode;
  }

  // 20120112 shen add end

  // @Override
  // soukai add 20120107 ob start
  public DeliveryCompany getDefaultDeliveryCompany() {
    DeliveryCompany deliveryCompany = null;
    DeliveryCompanyDao dao = DIContainer.getDao(DeliveryCompanyDao.class);
    List<DeliveryCompany> list = dao.findByQuery(" SELECT * FROM DELIVERY_COMPANY WHERE DEFAULT_FLG = "
        + DefaultFlg.DEFAULT.getValue());
    if (list != null && list.size() > 0) {
      return list.get(0);
    }
    return deliveryCompany;
  }

  // soukai add 20120107 ob end

  // 20120511 shen add start
  private String getCurrentLanguageCode() {
    String currentLanguageCode = DIContainer.getLocaleContext().getCurrentLanguageCode();
    if (StringUtil.isNullOrEmpty(currentLanguageCode)) {
      currentLanguageCode = DIContainer.getLocaleContext().getSystemLanguageCode();
    }

    return currentLanguageCode;
  }

  public String getNameByLanguage(String nameCn, String nameEn, String nameJp) {
    return getNameByLanguage(nameCn, nameEn, nameJp, getCurrentLanguageCode());
  }

  public String getNameByLanguage(String nameCn, String nameEn, String nameJp, String languageCode) {
    String name = "";
    if (languageCode.equals(LanguageCode.Zh_Cn.getValue())) {
      name = nameCn;
    } else if (languageCode.equals(LanguageCode.En_Us.getValue())) {
      name = nameEn;
    } else if (languageCode.equals(LanguageCode.Ja_Jp.getValue())) {
      name = nameJp;
    }
    return name;
  }
  // 20120511 shen add end

	@Override
	public List<CodeAttribute> getOriginalPlace() {
		List<CodeAttribute> originalPlaceList = new ArrayList<CodeAttribute>();
		List<OriginalPlace> result = DatabaseUtil.loadAsBeanList(new SimpleQuery("SELECT * FROM ORIGINAL_PLACE ORDER BY ORIGINAL_CODE"), OriginalPlace.class);
		if(result != null && result.size() > 0){
			for(int i = 0;i < result.size();i++){
				OriginalPlace op = (OriginalPlace)result.get(i);
				originalPlaceList.add(new NameValue(op.getOriginalCode()+":"+op.getOriginalPlaceNameCn(), op.getOriginalCode()));
			}
		}
	    return originalPlaceList;
	}

	@Override
	public OriginalPlace getOriginal(String originalplace) {
		Query query = new SimpleQuery("select * from original_place where original_place_name_cn=?",originalplace);
		OriginalPlace op = DatabaseUtil.loadAsBean(query, OriginalPlace.class);
	    return op;
	}
	
	// 根据产地code查询产地信息 20130416 add by yyq start
	@Override
	public OriginalPlace getOriginalPlaceByCode(String originalCode) {
		Query query = new SimpleQuery("SELECT * FROM ORIGINAL_PLACE WHERE ORIGINAL_CODE= ?",originalCode);
		OriginalPlace op = DatabaseUtil.loadAsBean(query, OriginalPlace.class);
	    return op;
	}
	// 根据产地code查询产地信息 20130416 add by yyq end

	@Override
	public OriginalPlace getOriginal(String originalplace,
			String originalplaceEn, String originalplaceJp) {
		Query query = new SimpleQuery("SELECT * FROM ORIGINAL_PLACE WHERE original_place_name_cn=? and original_place_name_en=? and original_place_name_jp=?", 
				originalplace,originalplaceEn,originalplaceJp);
	    OriginalPlace op = DatabaseUtil.loadAsBean(query, OriginalPlace.class);
	    return op;
	}
}
