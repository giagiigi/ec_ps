package jp.co.sint.webshop.service.order;

import java.util.List;

import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.SimpleQuery;
import jp.co.sint.webshop.data.domain.AdvanceLaterFlg;
import jp.co.sint.webshop.data.domain.CouponStatus;
import jp.co.sint.webshop.data.domain.DataTransportStatus;
import jp.co.sint.webshop.data.domain.LanguageCode;
import jp.co.sint.webshop.data.domain.LanguageType;
import jp.co.sint.webshop.data.domain.OrderStatus;
import jp.co.sint.webshop.data.domain.OrderType;
import jp.co.sint.webshop.data.domain.PaymentMethodType;
import jp.co.sint.webshop.data.domain.PaymentStatus;
import jp.co.sint.webshop.data.domain.PointAmplificationRate;
import jp.co.sint.webshop.data.domain.ShippingStatusSummary;
import jp.co.sint.webshop.data.domain.UseStatus;
import jp.co.sint.webshop.data.dto.City;
import jp.co.sint.webshop.data.dto.CustomerVatInvoice;
import jp.co.sint.webshop.data.dto.DeliveryCompany;
import jp.co.sint.webshop.data.dto.JdCity;
import jp.co.sint.webshop.data.dto.NewCouponRule;
import jp.co.sint.webshop.data.dto.OrderHeader;
import jp.co.sint.webshop.data.dto.OrderInvoice;
import jp.co.sint.webshop.data.dto.PaymentMethod;
import jp.co.sint.webshop.utility.PointUtil;
import jp.co.sint.webshop.utility.SqlDialect;
import jp.co.sint.webshop.utility.SqlFragment;
import jp.co.sint.webshop.utility.StringUtil;

public final class OrderServiceQuery {

  /**
   * ソート順定数 出荷指示日 降順
   */
  private static final String ORDER_BY_SHIPPING_DIRECT_DATE_DESC = "shippingDirectDateDesc";

  /**
   * ソート順定数 配送希望日 降順
   */
  private static final String ORDER_BY_DELIVERY_APPOINTED_DATE_DESC = "deliveryAppointedDateDesc";

  /**
   * ソート順定数 出荷日 降順
   */
  private static final String ORDER_BY_DELIVERY_SHIPPING_DATE_DESC = "shippingDateDesc";

  private static final long serialVersionUID = 1L;

  private static String outputOrderDataQuery;

  private OrderServiceQuery() {

  }

  static {
    StringBuilder builder = new StringBuilder();
    builder.append("SELECT  A.ORDER_NO, ");
    builder.append("        A.SHOP_CODE, ");
    builder.append("        A.ORDER_DATETIME, ");
    builder.append("        A.CUSTOMER_CODE, ");
    builder.append("        A.GUEST_FLG, ");
    builder.append("        A.LAST_NAME, ");
    // delete by V10-CH start
    // builder.append(" A.FIRST_NAME, ");
    // builder.append(" A.LAST_NAME_KANA, ");
    // builder.append(" A.FIRST_NAME_KANA, ");
    // delete by V10-CH end
    builder.append("        A.EMAIL, ");
    builder.append("        A.POSTAL_CODE, ");
    builder.append("        A.PREFECTURE_CODE, ");
    // add by V10-CH start
    builder.append("        A.CITY_CODE, ");
    // add by V10-CH end
    builder.append("        A.ADDRESS1, ");
    builder.append("        A.ADDRESS2, ");
    builder.append("        A.ADDRESS3, ");
    // delete by V10-CH start
    // builder.append(" A.ADDRESS4, ");
    // delete by V10-CH start
    builder.append("        A.PHONE_NUMBER, ");
    // Add by V10-CH start
    builder.append("        A.MOBILE_NUMBER, ");
    // Add by V10-CH end
    builder.append("        A.ADVANCE_LATER_FLG, ");
    builder.append("        A.PAYMENT_METHOD_NO, ");
    builder.append("        A.PAYMENT_METHOD_TYPE, ");
    builder.append("        A.PAYMENT_METHOD_NAME, ");
    builder.append("        A.PAYMENT_COMMISSION, ");
    // delete by V10-CH start
    // builder.append(" A.PAYMENT_COMMISSION_TAX_RATE, ");
    // builder.append(" A.PAYMENT_COMMISSION_TAX, ");
    // builder.append(" A.PAYMENT_COMMISSION_TAX_TYPE, ");
    // delete by V10-CH END
    // modify by V10-CH start
    // builder.append(" A.USED_POINT, ");
    builder.append("trim(to_char(trunc(A.USED_POINT").append(",").append(PointUtil.getAcquiredPointScale()).append("), '").append(
        PointAmplificationRate.fromValue(Integer.toString(PointUtil.getAcquiredPointScale())).getName()).append(
        "')) AS USED_POINT,");
    builder.append("        A.COUPON_PRICE, ");
    // modify by V10-CH end
    builder.append("        A.PAYMENT_DATE, ");
    builder.append("        A.PAYMENT_LIMIT_DATE, ");
    builder.append("        A.PAYMENT_STATUS, ");
    builder.append("        A.CUSTOMER_GROUP_CODE, ");
    builder.append("        A.DATA_TRANSPORT_STATUS, ");
    builder.append("        A.ORDER_STATUS, ");
    builder.append("        A.CLIENT_GROUP, ");
    builder.append("        A.CAUTION, ");
    builder.append("        A.MESSAGE, ");
    builder.append("        A.PAYMENT_ORDER_ID, ");
    // delete by V10-CH start
    // builder.append(" A.CVS_CODE, ");
    // builder.append(" A.PAYMENT_RECEIPT_NO, ");
    // builder.append(" A.PAYMENT_RECEIPT_URL, ");
    // builder.append(" A.DIGITAL_CASH_TYPE, ");
    // delete by V10-CH END
    builder.append("        A.WARNING_MESSAGE, ");
    builder.append("        A.ORM_ROWID, ");
    builder.append("        A.CREATED_USER, ");
    builder.append("        A.CREATED_DATETIME, ");
    builder.append("        A.UPDATED_USER, ");
    builder.append("        A.UPDATED_DATETIME, ");
    builder.append("        B.ORDER_NO, ");
    builder.append("        B.SHOP_CODE, ");
    builder.append("        B.SKU_CODE, ");
    builder.append("        B.COMMODITY_CODE, ");
    builder.append("        B.COMMODITY_NAME, ");
    builder.append("        B.STANDARD_DETAIL1_NAME, ");
    builder.append("        B.STANDARD_DETAIL2_NAME, ");
    builder.append("        B.PURCHASING_AMOUNT, ");
    builder.append("        B.UNIT_PRICE, ");
    builder.append("        B.RETAIL_PRICE, ");
    // delete by V10-CH start
    // builder.append(" B.RETAIL_TAX, ");
    // builder.append(" B.COMMODITY_TAX_RATE, ");
    // builder.append(" B.COMMODITY_TAX, ");
    // builder.append(" B.COMMODITY_TAX_TYPE, ");
    // delete by V10-CH end
    builder.append("        B.CAMPAIGN_CODE, ");
    builder.append("        B.CAMPAIGN_NAME, ");
    builder.append("        B.CAMPAIGN_DISCOUNT_RATE, ");
    builder.append("        B.APPLIED_POINT_RATE, ");
    builder.append("        B.ORM_ROWID, ");
    builder.append("        B.CREATED_USER, ");
    builder.append("        B.CREATED_DATETIME, ");
    builder.append("        B.UPDATED_USER, ");
    builder.append("        B.UPDATED_DATETIME, ");
    builder.append("        C.SHIPPING_NO, ");
    builder.append("        C.ORDER_NO, ");
    builder.append("        C.SHOP_CODE, ");
    builder.append("        C.CUSTOMER_CODE, ");
    builder.append("        C.ADDRESS_NO, ");
    builder.append("        C.ADDRESS_LAST_NAME, ");
    // delete by V10-CH start
    // builder.append(" C.ADDRESS_FIRST_NAME, ");
    // builder.append(" C.ADDRESS_LAST_NAME_KANA, ");
    // builder.append(" C.ADDRESS_FIRST_NAME_KANA, ");
    // delete by V10-CH end
    builder.append("        C.POSTAL_CODE, ");
    builder.append("        C.PREFECTURE_CODE, ");
    // add by V10-CH start
    builder.append("        C.CITY_CODE, ");
    // add by V10-CH end
    builder.append("        C.ADDRESS1, ");
    builder.append("        C.ADDRESS2, ");
    builder.append("        C.ADDRESS3, ");
    // delete by V10-CH start
    // builder.append(" C.ADDRESS4, ");
    // delete by V10-CH END
    builder.append("        C.PHONE_NUMBER, ");
    // Add by V10-CH start
    builder.append("        C.MOBILE_NUMBER, ");
    // Add by V10-CH end
    builder.append("        C.DELIVERY_REMARK, ");
    // modify by V10-CH start
    // builder.append(" C.ACQUIRED_POINT, ");
    builder.append("trim(to_char(trunc(C.ACQUIRED_POINT").append(",").append(PointUtil.getAcquiredPointScale()).append("), '")
        .append(PointAmplificationRate.fromValue(Integer.toString(PointUtil.getAcquiredPointScale())).getName()).append(
            "')) AS ACQUIRED_POINT,");
    // modify by V10-CH end
    builder.append("        C.DELIVERY_SLIP_NO, ");
    builder.append("        C.SHIPPING_CHARGE, ");
    // delete by V10-CH start
    // builder.append(" C.SHIPPING_CHARGE_TAX_TYPE, ");
    // builder.append(" C.SHIPPING_CHARGE_TAX_RATE, ");
    // builder.append(" C.SHIPPING_CHARGE_TAX, ");
    // delete by V10-CH END
    builder.append("        C.DELIVERY_TYPE_NO, ");
    builder.append("        C.DELIVERY_TYPE_NAME, ");
    builder.append("        C.DELIVERY_APPOINTED_DATE, ");
    builder.append("        C.DELIVERY_APPOINTED_TIME_START, ");
    builder.append("        C.DELIVERY_APPOINTED_TIME_END, ");
    builder.append("        C.ARRIVAL_DATE, ");
    builder.append("        C.ARRIVAL_TIME_START, ");
    builder.append("        C.ARRIVAL_TIME_END, ");
    builder.append("        C.FIXED_SALES_STATUS, ");
    builder.append("        C.SHIPPING_STATUS, ");
    builder.append("        C.SHIPPING_DIRECT_DATE, ");
    builder.append("        C.SHIPPING_DATE, ");
    builder.append("        C.ORIGINAL_SHIPPING_NO, ");
    builder.append("        C.RETURN_ITEM_DATE, ");
    builder.append("        C.RETURN_ITEM_LOSS_MONEY, ");
    builder.append("        C.RETURN_ITEM_TYPE, ");
    builder.append("        C.ORM_ROWID, ");
    builder.append("        C.CREATED_USER, ");
    builder.append("        C.CREATED_DATETIME, ");
    builder.append("        C.UPDATED_USER, ");
    builder.append("        C.UPDATED_DATETIME, ");
    builder.append("        D.SHIPPING_NO, ");
    builder.append("        D.SHIPPING_DETAIL_NO, ");
    builder.append("        D.SHOP_CODE, ");
    builder.append("        D.SKU_CODE, ");
    builder.append("        D.UNIT_PRICE, ");
    builder.append("        D.DISCOUNT_PRICE, ");
    builder.append("        D.DISCOUNT_AMOUNT, ");
    builder.append("        D.RETAIL_PRICE, ");
    // delete by V10-CH start
    // builder.append(" D.RETAIL_TAX, ");
    // delete by V10-CH END
    builder.append("        D.PURCHASING_AMOUNT, ");
    builder.append("        D.GIFT_CODE, ");
    builder.append("        D.GIFT_NAME, ");
    builder.append("        D.GIFT_PRICE, ");
    // delete by V10-CH start
    // builder.append(" D.GIFT_TAX_RATE, ");
    // builder.append(" D.GIFT_TAX, ");
    // builder.append(" D.GIFT_TAX_TYPE, ");
    // delete by V10-CH END
    builder.append("        D.ORM_ROWID, ");
    builder.append("        D.CREATED_USER, ");
    builder.append("        D.CREATED_DATETIME, ");
    builder.append("        D.UPDATED_USER, ");
    builder.append("        D.UPDATED_DATETIME ");
    builder.append(" FROM   ORDER_HEADER A ");
    builder.append("          INNER JOIN ORDER_DETAIL B ");
    builder.append("            ON A.ORDER_NO    = B.ORDER_NO ");
    builder.append("          INNER JOIN SHIPPING_HEADER C ");
    builder.append("            ON A.ORDER_NO    = C.ORDER_NO ");
    builder.append("          INNER JOIN SHIPPING_DETAIL D ");
    builder.append("            ON C.SHIPPING_NO = D.SHIPPING_NO AND ");
    builder.append("               D.SHOP_CODE   = B.SHOP_CODE   AND ");
    builder.append("               D.SKU_CODE    = B.SKU_CODE ");
    builder.append(" WHERE A.ORDER_STATUS        = " + OrderStatus.ORDERED.getValue());
    builder.append("  AND (A.ADVANCE_LATER_FLG  = " + AdvanceLaterFlg.ADVANCE.getValue());
    builder.append("   OR (A.ADVANCE_LATER_FLG  = " + AdvanceLaterFlg.LATER.getValue());
    builder.append("  AND  A.PAYMENT_STATUS = " + PaymentStatus.NOT_PAID.getValue() + ")) AND A.ORDER_NO IN ( ");
    builder.append("                             SELECT OSV.ORDER_NO ");
    builder.append("                             FROM  ORDER_SUMMARY_VIEW OSV ");
    builder.append("                             WHERE SHIPPING_STATUS_SUMMARY = ");
    builder.append("                             " + ShippingStatusSummary.NOT_SHIPPED.getValue() + ") AND ");
    builder.append("       A.DATA_TRANSPORT_STATUS = " + DataTransportStatus.NOT_TRANSPORTED.getValue());
    builder.append("       ORDER BY A.ORDER_NO, ");
    builder.append("                C.SHIPPING_NO, ");
    builder.append("                D.SHIPPING_DETAIL_NO ");
    builder.append("  FOR UPDATE NOWAIT ");

    outputOrderDataQuery = builder.toString();
  }

  /**
   * 出荷実績取消のクエリSHIPPING_HEADER用<br>
   * [0]出荷ステータス<br>
   * [1]更新ユーザー<br>
   * [2]現在日時<br>
   * [3]出荷番号<br>
   * [4]受注番号<br>
   * [5]ORM_ROWID 楽観同時実行制御<br>
   * [6]UPDATED_DATETIME 楽観同時実行制御<br>
   */
  public static final String CLEAR_SHIPPING_REPORT_SHIPPING_HEADER_QUERY = "update SHIPPING_HEADER "
      + "set DELIVERY_SLIP_NO = null , ARRIVAL_DATE = null , ARRIVAL_TIME_START = null , ARRIVAL_TIME_END = null , "
      + "SHIPPING_STATUS = ? , SHIPPING_DATE = null , UPDATED_USER = ? , UPDATED_DATETIME = ? where SHIPPING_NO = ? "
      + "and ORDER_NO = ? and ORM_ROWID = ? and UPDATED_DATETIME = ?";

  /**
   * 出荷実績取消のクエリORDER_HEADER用<br>
   * [0]出荷ステータス<br>
   * [1]更新ユーザー<br>
   * [2]現在日時<br>
   * [3]受注番号<br>
   * [4]ORM_ROWID 楽観同時実行制御<br>
   * [5]UPDATED_DATETIME 楽観同時実行制御<br>
   */
  public static final String CLEAR_SHIPPING_REPORT_ORDER_HEADER_QUERY = "update ORDER_HEADER set SHIPPING_STATUS = ? , "
      + "UPDATED_USER = ? , UPDATED_DATETIME = ? where ORDER_NO = ? and ORM_ROWID = ? and UPDATED_DATETIME = ?";

  /**
   * 出荷指示登録のクエリSHIPPING_HEADER用<br>
   * [0]出荷ステータス<br>
   * [1]出荷指示日<br>
   * [2]更新ユーザー<br>
   * [3]現在日時<br>
   * [4]出荷番号<br>
   * [5]受注番号<br>
   * [6]ORM_ROWID 楽観同時実行制御<br>
   * [7]UPDATED_DATETIME 楽観同時実行制御<br>
   */
  public static final String REGISTER_SHIPPING_DIRECT_SHIPPING_HEADER_QUERY = "UPDATE SHIPPING_HEADER SET SHIPPING_STATUS = ? , "
      + "SHIPPING_DIRECT_DATE = ? , UPDATED_USER = ? , UPDATED_DATETIME = ? WHERE SHIPPING_NO = ? AND ORDER_NO = ? "
      + "AND ORM_ROWID = ? AND UPDATED_DATETIME = ?";

  /**
   * 出荷実績登録のクエリORDER_HEADER用<br>
   * [0]出荷ステータス<br>
   * [1]更新ユーザー<br>
   * [2]現在日時<br>
   * [3]受注番号<br>
   * [4]ORM_ROWID 楽観同時実行制御<br>
   * [5]UPDATED_DATETIME 楽観同時実行制御<br>
   */
  public static final String REGISTER_SHIPPING_DIRECT_ORDER_HEADER_QUERY = "update ORDER_HEADER set SHIPPING_STATUS = ? , "
      + "UPDATED_USER = ? , UPDATED_DATETIME = ? where ORDER_NO = ? and ORM_ROWID = ? and UPDATED_DATETIME = ?";

  /**
   * 納品兼請求データ出力のCSV用クエリ
   * 
   * @param shippingNos
   * @return 納品兼請求データ出力CSV
   */
  public static String billAndStatmentOfDeliveryCsvQuery(List<String> shippingNos, String sort) {
    StringBuilder builder = new StringBuilder();
    builder.append("SELECT OH.ORDER_NO ,SH.SHIPPING_NO ,SD.SHIPPING_DETAIL_NO ,"
        + "OH.ORDER_DATETIME ,OH.CUSTOMER_CODE ,OH.LAST_NAME ,OH.FIRST_NAME ,"
        + "OH.LAST_NAME_KANA ,OH.FIRST_NAME_KANA ,OH.EMAIL ,OH.POSTAL_CODE ,"
        + "OH.PREFECTURE_CODE ,OH.ADDRESS1 ,OH.ADDRESS2 ,"
        // modify by V10-CH start
        + "OH.ADDRESS3 ,OH.ADDRESS4 ,OH.PHONE_NUMBER ,OH.MOBILE_NUMBER ,OH.SHOP_CODE ,"
        // modify by V10-CH end
        + "OH.ADVANCE_LATER_FLG ,OH.PAYMENT_METHOD_CODE ,OH.PAYMENT_METHOD_NAME ,"
        + "OH.PAYMENT_COMMISSION ,OH.PEYMENT_COMMISSION_TAX_RATE ,OH.PAYMENT_COMMISSION_TAX_PRICE ,"
        + "OH.PAYMENT_COMMISSION_TAX_TYPE ,OH.PAYMENT_DATE ,OH.PAYMENT_LIMIT_DATE ,"
        + "OH.PAYMENT_STATUS ,OH.CUSTOMER_GROUP_CODE ,OH.CAUTION ,OH.MESSAGE ,'' ,"
        + "OH.CVS_CODE ,OH.CVS_RECEIPT_NO ,OH.CVS_RECEIPT_URL ,OH.DIGITAL_CASH_TYPE ,"
        + "OH.WARNING_MESSAGE ,'' ,'' ,'' ,'' ,'' ,'' ,SH.ADDRESS_NO ,SH.ADDRESS_LAST_NAME ,"
        + "SH.ADDRESS_FIRST_NAME ,SH.ADDRESS_LAST_NAME_KANA ,SH.ADDRESS_FIRST_NAME_KANA ,"
        + "SH.POSTAL_CODE ,SH.PREFECTURE_CODE ,SH.ADDRESS1 ,"
        + "SH.ADDRESS2 ,SH.ADDRESS3 ,SH.ADDRESS4 ,SH.PHONE_NUMBER ,"
        // modify by V10-CH start
        + "SH.MOBILE_NUMBER ,SH.DELIVERY_REMARK ,SH.DELIVERY_SLIP_NO ,SH.SHIPPING_CHARGE ,"
        // modify by V10-CH end
        + "SH.SHIPPING_CHARGE_TAX_TYPE ,SH.SHIPPING_CHARGE_TAX_RATE ,"
        + "SH.SHIPPING_CHARGE_TAX_PRICE ,SH.DELIVERY_TYPE_CODE ,SH.DELIVERY_TYPE_NAME ,"
        + "SH.DELIVERY_APPOINTED_DATE ,SH.DELIVERY_APPOINTED_START_TIME ,SH.DELIVERY_APPOINTED_TIME_END ,"
        + "SH.ARRIVAL_DATE ,SH.ARRIVAL_TIME_START ,SH.ARRIVAL_TIME_END ,SH.SHIPPING_STATUS ,"
        + "SH.SHIPPING_DATE ,SH.ORIGINAL_SHIPPING_NO ,SD.SHOP_CODE ,SD.SKU_CODE ,"
        + "OD.COMMODITY_CODE ,OD.COMMODITY_NAME ,OD.STANDARD_DETAIL1_NAME ,OD.STANDARD_DETAIL2_NAME ,"
        + "SD.UNIT_PRICE ,SD.DISCOUNT_PRICE ,SD.DISCOUNT_AMOUNT ,SD.RETAIL_PRICE ,"
        + "SD.PURCHASING_AMOUNT ,SD.GIFT_CODE ,SD.GIFT_PRICE ,SD.GIFT_TAX_RATE ,"
        + "SD.GIFT_TAX_PRICE ,SD.GIFT_TAX_TYPE FROM ORDER_HEADER OH,SHIPPING_HEADER SH,SHIPPING_DETAIL SD,"
        + "ORDER_DETAIL OD WHERE 1 = 1 AND OH.ORDER_NO = SH.ORDER_NO AND SH.SHIPPING_NO = SD.SHIPPING_NO "
        + "AND SH.ORDER_NO = OD.ORDER_NO AND SD.SKU_CODE = OD.SKU_CODE ");

    SqlFragment f = SqlDialect.getDefault().createInClause("SHIPPING_HEADER.SHIPPING_NO", shippingNos.toArray());
    builder.append(" AND " + f.getFragment());

    // 並べ替え条件
    String sortCondition = "";
    if (StringUtil.isNullOrEmpty(sort)) {
      sortCondition = " ORDER BY SHIPPING_HEADER.SHIPPING_NO DESC ";
      // } else if
      // (OrderConstantCode.ORDER_BY_SHIPPING_DIRECT_DATE_DESC.equals(sort)) {
    } else if (ORDER_BY_SHIPPING_DIRECT_DATE_DESC.equals(sort)) {
      sortCondition = " ORDER BY SHIPPING_HEADER.SHIPPING_DIRECT_DATE DESC ";
      // } else if
      // (OrderConstantCode.ORDER_BY_DELIVERY_APPOINTED_DATE_DESC.equals(sort))
      // {
    } else if (ORDER_BY_DELIVERY_APPOINTED_DATE_DESC.equals(sort)) {
      sortCondition = " ORDER BY SHIPPING_HEADER.DELIVERY_APPOINTED_DATE DESC ";
      // } else if
      // (OrderConstantCode.ORDER_BY_DELIVERY_SHIPPING_DATE_DESC.equals(sort)) {
    } else if (ORDER_BY_DELIVERY_SHIPPING_DATE_DESC.equals(sort)) {
      sortCondition = " ORDER BY SHIPPING_HEADER.SHIPPING_DATE DESC ";
    } else {
      sortCondition = " ORDER BY SHIPPING_HEADER.SHIPPING_NO DESC ";
    }

    builder.append(sortCondition);

    return builder.toString();
  }

  /**
   * 受注Noがもつ受注明細(ORDER_DETAIL)を全件取得するSQL <BR>
   * [0]受注No ORDER_NO
   */
  public static final String ORDER_DETAIL_LIST_QUERY = "SELECT * FROM ORDER_DETAIL "
      + "WHERE ORDER_NO = ? ORDER BY SHOP_CODE ASC, SKU_CODE ASC ";

  /**
   * 受注Noがもつ受注明細(TMALL_ORDER_DETAIL)を全件取得するSQL <BR>
   * [0]受注No ORDER_NO
   */
  public static final String TMALL_ORDER_DETAIL_LIST_QUERY = "SELECT * FROM TMALL_ORDER_DETAIL "
      + "WHERE ORDER_NO = ? ORDER BY SHOP_CODE ASC, SKU_CODE ASC ";

  public static final String JD_ORDER_DETAIL_LIST_QUERY = "SELECT * FROM JD_ORDER_DETAIL "
    + "WHERE ORDER_NO = ? ORDER BY SHOP_CODE ASC, SKU_CODE ASC ";
  
  /**
   * 受注Noがもつ出荷ヘッダ(SHIPPING_HEADER)を全件取得するSQL <BR>
   * [0]受注No ORDER_NO
   */
  public static final String SHIPPING_HEADER_LIST_QUERY = "SELECT * FROM SHIPPING_HEADER "
      + "WHERE ORDER_NO = ? ORDER BY SHIPPING_NO DESC ";
  
  public static final String SHIPPING_STATUS_BY_ORDER_NO = "SELECT SHIPPING_STATUS FROM SHIPPING_HEADER "
    + "WHERE ORDER_NO = ? AND RETURN_ITEM_TYPE = 0 ";

  public static final String TMALL_SHIPPING_HEADER_LIST_QUERY = "SELECT * FROM TMALL_SHIPPING_HEADER "
      + "WHERE ORDER_NO = ? ORDER BY SHIPPING_NO DESC ";

  public static final String JD_SHIPPING_HEADER_LIST_QUERY = "SELECT * FROM JD_SHIPPING_HEADER "
    + "WHERE ORDER_NO = ? ORDER BY SHIPPING_NO DESC ";
  /**
   * 出荷Noがもつ出荷明細(SHIPPING_DETAIL)を全件取得するSQL <BR>
   * [0]出荷No SHIPPING_NO
   */
  public static final String SHIPPING_DETAIL_LIST_QUERY = "SELECT * FROM SHIPPING_DETAIL "
      + "WHERE SHIPPING_NO = ? ORDER BY SHIPPING_DETAIL_NO ASC ";

  // 2012/11/19 促销活动 ob add start
 
  public static final String SHIPPING_COMPOSITION_ALL_LIST_QUERY = "SELECT * FROM SHIPPING_DETAIL_COMPOSITION "
      + "WHERE SHIPPING_NO = ? ORDER BY SHIPPING_DETAIL_NO ASC ,COMPOSITION_NO ASC";
  
  public static final String SHIPPING_COMPOSITION_DETAIL_LIST_QUERY = "SELECT * FROM SHIPPING_DETAIL_COMPOSITION "
    + "WHERE SHIPPING_NO = ? AND SHIPPING_DETAIL_NO = ? ORDER BY SHIPPING_DETAIL_NO ASC ,COMPOSITION_NO ASC";
  
  public static final String TMALL_SHIPPING_COMPOSITION_DETAIL_LIST_QUERY = "SELECT * FROM TMALL_SHIPPING_DETAIL_COMPOSITION "
    + "WHERE SHIPPING_NO = ? AND SHIPPING_DETAIL_NO = ? ORDER BY SHIPPING_DETAIL_NO ASC ,COMPOSITION_NO ASC";
  
  // 套装商品库存更新（TMALL）
  public static final String UPDATE_TMALL_SUIT_COMMODITY_SQL = "UPDATE tmall_suit_commodity SET ALLOCATED_QUANTITY = ALLOCATED_QUANTITY - ?"
      + ",UPDATED_USER = ?, UPDATED_DATETIME = ? WHERE COMMODITY_CODE = ? ";
  
  // 组合商品库存更新（TMALL）
  public static final String UPDATE_TMALL_STOCK_ALLOCATION_SQL = "UPDATE TMALL_STOCK_ALLOCATION SET ALLOCATED_QUANTITY = ALLOCATED_QUANTITY - ?"
      + ",UPDATED_USER = ?, UPDATED_DATETIME = ? WHERE SHOP_CODE=? AND  SKU_CODE = ? ";
  
  public static final String JD_SHIPPING_COMPOSITION_DETAIL_LIST_QUERY = "SELECT * JD_FROM SHIPPING_DETAIL_COMPOSITION "
    + "WHERE SHIPPING_NO = ? AND SHIPPING_DETAIL_NO = ? ORDER BY SHIPPING_DETAIL_NO ASC ,COMPOSITION_NO ASC";
  
  // 套装商品库存更新（JD）
  public static final String UPDATE_JD_STOCK_ALLOCATION_SQL = "UPDATE JD_STOCK_ALLOCATION SET ALLOCATED_QUANTITY = ALLOCATED_QUANTITY - ?"
      + ",UPDATED_USER = ?, UPDATED_DATETIME = ? WHERE COMMODITY_CODE = ?";
  
  // 组合商品库存更新（JD）
  public static final String UPDATE_JD_SUIT_COMMODITY_SQL = "UPDATE JD_SUIT_COMMODITY SET ALLOCATED_QUANTITY = ALLOCATED_QUANTITY - ?"
      + ",UPDATED_USER = ?, UPDATED_DATETIME = ? WHERE SHOP_CODE=? AND  SKU_CODE = ? ";
  
  public static final String ORDER_CAMAPIGN_LIST_QUERY = "SELECT * FROM ORDER_CAMPAIGN "
	    + "WHERE ORDER_NO = ? ORDER BY CAMPAIGN_CODE";
  // 2012/11/19 促销活动 ob add end
  /**
   * 出荷Noがもつ出荷明細(TMALL_SHIPPING_DETAIL)を全件取得するSQL <BR>
   * [0]出荷No SHIPPING_NO
   */
  public static final String TMALL_SHIPPING_DETAIL_LIST_QUERY = "SELECT * FROM TMALL_SHIPPING_DETAIL "
      + "WHERE SHIPPING_NO = ? ORDER BY SHIPPING_DETAIL_NO ASC ";

  public static final String JD_SHIPPING_DETAIL_LIST_QUERY = "SELECT * FROM JD_SHIPPING_DETAIL "
    + "WHERE SHIPPING_NO = ? ORDER BY SHIPPING_DETAIL_NO ASC ";

  
  // 20120224 yyq add start
  /**
   * 淘宝api未发货成功的shippingHeader集合 <BR>
   * [0]支付区分
   */
  public static final String TMALL_SHIPPING_HEADER_LIST_GET = "SELECT * FROM TMALL_SHIPPING_HEADER TSH "
  + " INNER JOIN TMALL_ORDER_HEADER TOH ON TOH.ORDER_NO = TSH.ORDER_NO "
  + " WHERE (TMALL_SHIPPING_FLG <> 1 OR TMALL_SHIPPING_FLG IS NULL) " + " AND (RETURN_ITEM_TYPE <> 1 OR RETURN_ITEM_TYPE IS NULL) "
  + " AND SHIPPING_STATUS = 3 "
  + "ORDER BY  TSH.ORDER_NO ASC ";
  // 20120224 yyq add end

  /**
   * 出荷明細画面の更新クエリ ORDER_HEADER用<br>
   * [0]注意事項<br>
   * [1]連絡事項<br>
   * [2]更新ユーザ<br>
   * [3]更新日付<br>
   * [4]受注番号<br>
   * [5]ORM_ROWID 楽観同時実行制御<br>
   * [6]UPDATED_DATETIME 楽観同時実行制御<br>
   */
  public static final String UPDATE_SHIPPING_DETAIL_ORDER_HEADER_QUERY = "UPDATE ORDER_HEADER "
      + "SET CAUTION = ? ,MESSAGE = ? ,UPDATED_USER = ? ,UPDATED_DATETIME = ? "
      + "WHERE ORDER_NO = ? AND ORM_ROWID = ? AND UPDATED_DATETIME = ?";

  /**
   * 出荷明細画面の更新クエリ SHIPPING_HEADER用<br>
   * [0]出荷ステータス<br>
   * [1]出荷日<br>
   * [2]出荷伝票番号<br>
   * [3]到着日時<br>
   * [4]到着時間Start<br>
   * [5]到着時間To<br>
   * [6]更新ユーザ<br>
   * [7]更新日付<br>
   * [8]出荷番号<br>
   * [9]ORM_ROWID 楽観同時実行制御<br>
   * [10]UPDATED_DATETIME 楽観同時実行制御<br>
   */
  public static final String UPDATE_SHIPPING_DETAIL_SHIPPING_HEADER_QUERY = "UPDATE SHIPPING_HEADER "
      + "SET SHIPPING_STATUS = ? ,SHIPPING_DATE = ? ,DELIVERY_SLIP_NO = ? ,ARRIVAL_DATE = ? ,"
      + "ARRIVAL_TIME_START = ? ,ARRIVAL_TIME_END = ? ,UPDATED_USER = ? ,UPDATED_DATETIME = ? "
      + "WHERE SHIPPING_NO = ? AND ORM_ROWID = ? AND UPDATED_DATETIME = ?";

  /**
   * 宅配便伝票番号取込の更新クエリ SHIPPING_HEADER用<br>
   * [0]出荷番号<br>
   * [1]宅配便伝票番号<br>
   * [2]更新ユーザ<br>
   * [3]更新日付<br>
   */
  public static final String UPDATE_SHIPPING_HEADER_DELIVERY_SLIP_NO_QUERY = "UPDATE SHIPPING_HEADER "
      + "SET DELIVERY_SLIP_NO = ? ,UPDATED_USER = ? ,UPDATED_DATETIME = ? WHERE SHIPPING_NO = ?";

  /**
   * 支払の最終受付番号を取得する。 <br>
   * [0]ショップコード<br>
   * [1]支払方法タイプ<br>
   * [2]顧客コード<br>
   * [3]有効期限日数<br>
   */
  public static final String GET_LAST_PAYMENT_RECEIPT_NO_QUERY = "SELECT OH.PAYMENT_RECEIPT_NO "
      + "AS LAST_PAYMENT_RECEIPT_NO FROM ORDER_HEADER OH ,PAYMENT_METHOD PM "
      + "WHERE OH.SHOP_CODE = PM.SHOP_CODE AND OH.PAYMENT_METHOD_CODE = PM.PAYMENT_METHOD_CODE "
      + "AND OH.SHOP_CODE = ?  AND PM.PAYMENT_METHOD_TYPE = ? AND OH.CUSTOMER_CODE = ? "
      + "AND TO_CHAR(OH.CREATED_DATETIME, 'yyyy/mm/dd') >=  TO_CHAR(?,'yyyy/mm/dd') ORDER BY OH.ORDER_NO DESC";

  /**
   * 受注ステータスの更新クエリ SHIPPING_HEADER用<br>
   * [0]受注番号<br>
   * [1]受注ステータス<br>
   * [2]更新ユーザ<br>
   * [3]更新日付<br>
   * [4]UPDATED_DATETIME 楽観同時実行制御<br>
   */
  public static final String UPDATE_ORDER_HEADER_ORDER_STATUS_QUERY = "UPDATE ORDER_HEADER SET"
      + " ORDER_STATUS = ? ,UPDATED_USER = ? ,UPDATED_DATETIME = ? WHERE ORDER_NO = ? AND UPDATED_DATETIME = ?";
  
  // 2013/04/10 优惠券对应 ob add start
  public static final String UPDATE_NEW_COUPON_HISTORY_QUERY = "UPDATE NEW_COUPON_HISTORY SET"
      + " use_status = ? , use_order_no = ?, UPDATED_USER = ? ,UPDATED_DATETIME = ? WHERE coupon_issue_no = ?";
  // 2013/04/10 优惠券对应 ob add end

  /**
   * データ連携ステータスの更新クエリ SHIPPING_HEADER用<br>
   * [0]受注番号<br>
   * [1]データ連携ステータス<br>
   * [2]更新ユーザ<br>
   * [3]更新日付<br>
   * [4]UPDATED_DATETIME 楽観同時実行制御<br>
   */
  public static final String UPDATE_ORDER_HEADER_DATA_TRANSPORT_STATUS_QUERY = "UPDATE ORDER_HEADER SET"
      + " DATA_TRANSPORT_STATUS = ? ,UPDATED_USER = ? ,UPDATED_DATETIME = ? WHERE ORDER_NO = ?";

  /**
   * ショップ単位で受注ヘッダを取得する。 ORDER_HEADER用<br>
   * [0]ショップコード<br>
   */
  public static final String GET_ORDER_HEADER_LIST_OF_SHOP_QUERY = DatabaseUtil.getSelectAllQuery(OrderHeader.class)
      + " WHERE SHOP_CODE = ?";

  public static String getOutputOrderDataQuery() {
    return outputOrderDataQuery;
  }

  /**
   * 受注番号に関連付いているポイント履歴を取得するクエリ
   */
  public static final String GET_POINT_HISTORY_BY_ORDER_NO = "SELECT * FROM POINT_HISTORY WHERE ORDER_NO = ?";

  /**
   * 出荷ヘッダ更新用クエリ
   */
  public static final String UPDATE_SHIPPING_STATUS = "UPDATE SHIPPING_HEADER "
      + "SET SHIPPING_STATUS = ?, UPDATED_USER = ?, UPDATED_DATETIME = ? " + "WHERE SHIPPING_NO = ? AND UPDATED_DATETIME = ?";

  /**
   * 予約注文取得用クエリ
   */
  public static final String GET_RESERVED_ORDER_QUERY = "SELECT B.ORDER_NO, B.SHOP_CODE, B.SKU_CODE "
      + " FROM ORDER_HEADER A INNER JOIN ORDER_DETAIL B ON A.ORDER_NO = B.ORDER_NO "
      + " WHERE B.SHOP_CODE = ? AND B.SKU_CODE = ? AND A.ORDER_STATUS = " + OrderStatus.RESERVED.getValue() + " AND ("
      + SqlDialect.getDefault().getCurrentDateString() + " <= A.PAYMENT_LIMIT_DATE OR A.PAYMENT_LIMIT_DATE IS NULL OR "
      + " A.PAYMENT_STATUS = " + PaymentStatus.PAID.getValue() + ") ORDER BY B.ORDER_NO";

  public static final String SALES_TRANSPORT = "" + " SELECT  A.ORDER_NO, A.SHOP_CODE, "
      + "         A.PAYMENT_METHOD_TYPE, B.TOTAL_AMOUNT  FROM  ORDER_HEADER A "
      + "           INNER JOIN ORDER_SUMMARY_VIEW B ON A.ORDER_NO = B.ORDER_NO "
      + " WHERE   A.PAYMENT_METHOD_TYPE     = ? AND A.PAYMENT_STATUS      = ? AND "
      + "         B.SHIPPING_STATUS_SUMMARY = ? AND A.ORDER_DATETIME BETWEEN ? AND ?";

  /**
   * 決済サービス取引IDから受注ヘッダ情報を取得するクエリ
   */
  public static final String GET_ORDER_HEADER_BY_PAYMENT_ORDER_ID = " SELECT * FROM ORDER_HEADER OH "
      + "WHERE OH.SHOP_CODE = ? AND OH.PAYMENT_ORDER_ID = ? ";

  public static final String UPDATE_POINT_HISTORY = "UPDATE POINT_HISTORY SET POINT_ISSUE_STATUS = ? , DESCRIPTION = ?, "
      + "UPDATED_USER = ?, UPDATED_DATETIME = ? WHERE ORDER_NO = ? ";

  /**
   * 受注メール送信履歴テーブルから、出荷番号を元に件数を返すｸｴﾘ
   */
  public static final String COUNT_SHIPPING = "SELECT COUNT(*) FROM ORDER_MAIL_HISTORY WHERE SHIPPING_NO = ? ";

  // v10-ch-pg added start
  public static Query getUpdatePhantomOrderQuery(String orderNo, OrderStatus status) {
    String sql = "UPDATE ORDER_HEADER SET ORDER_STATUS = ? " + "WHERE ORDER_NO = ? AND ORDER_STATUS = ?";
    Object[] args = {
        orderNo, status.longValue(), OrderStatus.PHANTOM_ORDER.longValue()
    };
    return new SimpleQuery(sql, args);
  }

  public static final String CANCEL_PHANTOM_ORDER_QUERY = "UPDATE ORDER_HEADER OH "
      + "SET OH.ORDER_STATUS = "
      + OrderStatus.CANCELLED.longValue()
      + ", OH.UPDATED_USER = ?, OH.UPDATED_DATETIME = ?  "
      // M17N 10361 修正 ここから
      // + "WHERE OH.ORDER_STATUS = " + OrderStatus.PHANTOM_ORDER.longValue() +
      // " AND OH.ORDER_DATETIME < (SYSDATE - ( ? /24))";
      + "WHERE OH.ORDER_STATUS IN ( " + OrderStatus.PHANTOM_ORDER.longValue() + " , " + OrderStatus.PHANTOM_RESERVATION.longValue()
      + " ) AND OH.ORDER_DATETIME < (SYSDATE - ( ? /24))";

  // M17N 10361 修正 ここまで

  public static final String PLSQL_CANCEL_PHANTOM_ORDER_QUERY = "UPDATE ORDER_HEADER "
      + "SET ORDER_STATUS = "
      + OrderStatus.CANCELLED.longValue()
      + ", UPDATED_USER = ?,  UPDATED_DATETIME = ?  "
      // M17N 10361 修正 ここから
      // + "WHERE ORDER_STATUS = " + OrderStatus.PHANTOM_ORDER.longValue() +
      // " AND ORDER_DATETIME < (CURRENT_TIMESTAMP - interval ? hour)";
      + "WHERE ORDER_STATUS IN ( " + OrderStatus.PHANTOM_ORDER.longValue() + " , " + OrderStatus.PHANTOM_RESERVATION.longValue()
      + " ) AND ORDER_DATETIME < (CURRENT_TIMESTAMP - interval ? hour)";

  // M17N 10361 修正 ここまで

  public static final String GET_COUPON_ISSUE = "SELECT * FROM coupon_issue a " + " where shop_code = ? and get_coupon_price <= ? "
      + " and bonus_coupon_start_date < " + SqlDialect.getDefault().getCurrentDatetime() + " and bonus_coupon_end_date >  "
      + SqlDialect.getDefault().getCurrentDatetime() + "   order by coupon_price  desc";

  public static final String GET_PAYMENT_COUPON_LIST = "SELECT "
      + " CC.customer_coupon_id, CC.coupon_issue_no,CC.use_flg, CC.coupon_name, "
      + " CC.use_coupon_start_date,CC.use_coupon_end_date, " + " CC.coupon_price FROM CUSTOMER_COUPON CC  "
      + " WHERE CUSTOMER_CODE = ? AND CC.use_coupon_end_date >=  " + SqlDialect.getDefault().getCurrentDatetime()
      + "  AND CC.use_coupon_start_date <=  " + SqlDialect.getDefault().getCurrentDatetime() + " "
      + " AND CC.use_flg = ? ORDER BY CC.customer_coupon_id";

  public static final String GET_ORDER_COUPON_LIST = " SELECT * FROM CUSTOMER_COUPON CC " + "WHERE ORDER_NO = ? ";

  public static final String ORDER_MODIFY_ENABLE_COUPON = "SELECT CC.customer_coupon_id, CC.coupon_issue_no,CC.use_flg, CC.coupon_name, "
      + " CC.use_coupon_start_date,CC.use_coupon_end_date, "
      + " CC.coupon_price,CC.use_date,CC.order_no FROM CUSTOMER_COUPON CC "
      + " WHERE (CUSTOMER_CODE = ? AND USE_FLG = ? AND CC.use_coupon_end_date >  "
      + SqlDialect.getDefault().getCurrentDatetime()
      + " ) or (order_no = ? AND CC.use_coupon_end_date >  "
      + SqlDialect.getDefault().getCurrentDatetime()
      + " ) ORDER BY customer_coupon_id desc";

  public static final String GET_OLD_GET_COUPON_LIST = " SELECT * FROM CUSTOMER_COUPON CC "
      + "WHERE GET_COUPON_ORDER_NO = ? AND USE_FLG = 0 ";

  // v10-ch-pg added end

  // M17N 10361 追加 ここから
  public static final String FIND_CANCEL_PHANTOM_ORDER_QUERY = "SELECT OH.ORDER_NO FROM ORDER_HEADER OH "
      + "WHERE OH.ORDER_STATUS IN( " + OrderStatus.PHANTOM_ORDER.longValue() + "," + OrderStatus.PHANTOM_RESERVATION.longValue()
      + ") AND OH.ORDER_DATETIME < (SYSDATE - ( ? /24))";

  public static final String FIND_CANCEL_PHANTOM_ORDER_QUERY_FOR_PLSQL = "SELECT ORDER_NO FROM ORDER_HEADER "
      + "WHERE ORDER_STATUS IN( " + OrderStatus.PHANTOM_ORDER.longValue() + "," + OrderStatus.PHANTOM_RESERVATION.longValue()
      + " ) AND ORDER_DATETIME < (CURRENT_TIMESTAMP - interval ? hour)";

  public static final String CANCEL_PHANTOM_ORDER_QUERY_BY_ORDER_NO = "UPDATE ORDER_HEADER  SET ORDER_STATUS = "
      + OrderStatus.CANCELLED.longValue() + ", UPDATED_USER = ?,  UPDATED_DATETIME = ?  " + "WHERE ORDER_STATUS IN( "
      + OrderStatus.PHANTOM_ORDER.longValue() + "," + OrderStatus.PHANTOM_RESERVATION.longValue() + ") AND ORDER_NO = ?";

  public static final String CANCEL_CUP_SPS_ALL = DatabaseUtil.getSelectAllQuery(PaymentMethod.class)
      + " WHERE PAYMENT_LIMIT_DAYS IS NOT NULL AND PAYMENT_METHOD_TYPE IN ('" + PaymentMethodType.ALIPAY.longValue() + "', '"
      + PaymentMethodType.CHINA_UNIONPAY.longValue() + "')";

  public static final String FIND_CANCEL_PHANTOM_ORDER_QUERY_FOR_PLSQL1 = "SELECT ORDER_NO FROM ORDER_HEADER "
      + "WHERE PAYMENT_METHOD_NO =? AND ORDER_STATUS IN( " + OrderStatus.PHANTOM_ORDER.longValue() + ","
      + OrderStatus.PHANTOM_RESERVATION.longValue() + " ) AND ORDER_DATETIME < (CURRENT_TIMESTAMP - interval ? hour)";

  public static final String FIND_CANCEL_PHANTOM_ORDER_QUERY1 = "SELECT OH.ORDER_NO FROM ORDER_HEADER OH "
      + "WHERE OH.PAYMENT_METHOD_NO =? AND OH.ORDER_STATUS IN( " + OrderStatus.PHANTOM_ORDER.longValue() + ","
      + OrderStatus.PHANTOM_RESERVATION.longValue() + ") AND OH.ORDER_DATETIME < (SYSDATE - ( ? /24))";

  // M17N 10361 追加 ここまで
  // 20111209 lirong add start
  public static final String SHIPPING_DETAIL_LIST_QUERY_ASC = "SELECT * FROM SHIPPING_DETAIL "
      + "WHERE SHIPPING_NO = ? ORDER BY COMMODITY_TYPE ASC, SKU_CODE ASC";

  // 20111209 lirong add start

  // 20111223 shen add start
  public static final String LOAD_CUSTOMER_VAT_INVOICE_BY_CUSTOMER_CODE = DatabaseUtil.getSelectAllQuery(CustomerVatInvoice.class)
      + " WHERE CUSTOMER_CODE = ?";

  public static final String DELETE_CUSTOMER_VAT_INVOICE_BY_CUSTOMER_CODE = "DELETE FROM CUSTOMER_VAT_INVOICE WHERE CUSTOMER_CODE = ?";

  // 20111223 shen add end
  // soukai add 2011/12/28 ob start
  // 取得订单发票情报
  public static final String LOAD_ORDER_INVOICE_BY_ORDER_NO = DatabaseUtil.getSelectAllQuery(OrderInvoice.class)
      + " WHERE ORDER_NO = ?";

  // 取得订单发票的商品规格（取得订单详细的商品情报）
  public static final String ORDER_DETAIL_COMMODITY_LIST_QUERY = "SELECT COMMODITY_CODE,COMMODITY_NAME FROM ORDER_DETAIL "
      + "WHERE ORDER_NO = ? AND SHOP_CODE = ?";

  // 取得订单商品的code, name和重量
  public static final String ORDER_DETAIL_COMMODITY_LIST_WEIGHT_QUERY = "SELECT COMMODITY_CODE, COMMODITY_NAME, COMMODITY_WEIGHT FROM ORDER_DETAIL "
    + "WHERE ORDER_NO = ? AND SHOP_CODE = ?";
  
  // 取得淘宝订单发票的商品规格（取得订单详细的商品情报）
  public static final String ORDER_TMALL_DETAIL_COMMODITY_LIST_QUERY = "SELECT COMMODITY_CODE,COMMODITY_NAME FROM TMALL_ORDER_DETAIL "
      + "WHERE ORDER_NO = ? AND SHOP_CODE = ?";

  // soukai add 2011/12/28 ob end
  // 城市、省份情报查询
  public static final String LOAD_CITY_BY_NAME = DatabaseUtil.getSelectAllQuery(City.class)
      + " WHERE city_name = ?  or city_name = ?  ";
  
  public static final String LOAD_JD_CITY_BY_NAME = DatabaseUtil.getSelectAllQuery(JdCity.class)
  + " WHERE city_name = ?  or city_name = ?  ";
  
  public static final String LOAD_SPECIAL_JD_CITY_BY_NAME = DatabaseUtil.getSelectAllQuery(JdCity.class)
  + " WHERE city_name like ? limit 1";
  
  public static final String LOAD_DELIVERY_COMPANY = DatabaseUtil.getSelectAllQuery(DeliveryCompany.class)
  + " WHERE DEFAULT_FLG='1' ";
  // 运送情报查询
  public static final String LOAD_DELIVERY_RELATED_INFO = "SELECT A.DELIVERY_RELATED_INFO_NO, A.SHOP_CODE, A.DELIVERY_COMPANY_NO, B.DELIVERY_COMPANY_NAME,A.PREFECTURE_CODE,"
      + "A.COD_TYPE, A.DELIVERY_DATE_TYPE, A.DELIVERY_APPOINTED_TIME_TYPE, A.DELIVERY_APPOINTED_START_TIME, "
      + "A.DELIVERY_APPOINTED_END_TIME, A.ORM_ROWID, A.CREATED_USER, A.CREATED_DATETIME, "
      + "A.UPDATED_USER, A.UPDATED_DATETIME "
      + "FROM TMALL_DELIVERY_RELATED_INFO A "
      +	"inner join DELIVERY_COMPANY B on B.DELIVERY_COMPANY_NO=A.DELIVERY_COMPANY_NO " 
      +	"INNER JOIN tmall_delivery_location DL ON A.DELIVERY_COMPANY_NO = DL.DELIVERY_COMPANY_NO AND A.PREFECTURE_CODE = DL.PREFECTURE_CODE "
      + "WHERE (COD_TYPE=? OR COD_TYPE='2') AND  A.PREFECTURE_CODE=  ?  AND A.DELIVERY_DATE_TYPE=0  "
      + "AND DL.CITY_CODE = ? AND DL.AREA_CODE = ? "
      + "AND A.DELIVERY_APPOINTED_TIME_TYPE=0 "
      + "AND CASE WHEN A.MIN_WEIGHT IS NULL THEN 0 ELSE A.MIN_WEIGHT END < ? "
      + "AND CASE WHEN A.MAX_WEIGHT IS NULL THEN 9999999999 ELSE A.MAX_WEIGHT END >= ? " 
      +	"AND CASE WHEN DL.MIN_WEIGHT IS NULL THEN 0 ELSE DL.MIN_WEIGHT END <= ? " 
      +	"AND CASE WHEN DL.MAX_WEIGHT IS NULL THEN 9999999999 ELSE DL.MAX_WEIGHT END >= ? ";
  
  public static final String LOAD_JD_DELIVERY_RELATED_INFO = "SELECT A.DELIVERY_RELATED_INFO_NO, A.SHOP_CODE, A.DELIVERY_COMPANY_NO, B.DELIVERY_COMPANY_NAME,A.PREFECTURE_CODE,"
    + "A.COD_TYPE, A.DELIVERY_DATE_TYPE, A.DELIVERY_APPOINTED_TIME_TYPE, A.DELIVERY_APPOINTED_START_TIME, "
    + "A.DELIVERY_APPOINTED_END_TIME, A.ORM_ROWID, A.CREATED_USER, A.CREATED_DATETIME, "
    + "A.UPDATED_USER, A.UPDATED_DATETIME "
    + "FROM JD_DELIVERY_RELATED_INFO A "
    + "inner join DELIVERY_COMPANY B on B.DELIVERY_COMPANY_NO=A.DELIVERY_COMPANY_NO " 
    + "INNER JOIN jd_delivery_location DL ON A.DELIVERY_COMPANY_NO = DL.DELIVERY_COMPANY_NO AND A.PREFECTURE_CODE = DL.PREFECTURE_CODE "
    + "WHERE (COD_TYPE=? OR COD_TYPE='2') AND  A.PREFECTURE_CODE=  ?  AND A.DELIVERY_DATE_TYPE=0  "
    + "AND DL.CITY_CODE = ? AND DL.AREA_CODE = ? "
    + "AND A.DELIVERY_APPOINTED_TIME_TYPE=0 "
    + "AND CASE WHEN A.MIN_WEIGHT IS NULL THEN 0 ELSE A.MIN_WEIGHT END < ? "
    + "AND CASE WHEN A.MAX_WEIGHT IS NULL THEN 9999999999 ELSE A.MAX_WEIGHT END >= ? " 
    + "AND CASE WHEN DL.MIN_WEIGHT IS NULL THEN 0 ELSE DL.MIN_WEIGHT END <= ? " 
    + "AND CASE WHEN DL.MAX_WEIGHT IS NULL THEN 9999999999 ELSE DL.MAX_WEIGHT END >= ? ";

  
  
  public static String loadJdDeliveryRelatedInfo(String egionCode,String cityCode,String areaCode) {
    StringBuilder builder = new StringBuilder();
    builder.append("SELECT A.DELIVERY_RELATED_INFO_NO, A.SHOP_CODE, A.DELIVERY_COMPANY_NO, B.DELIVERY_COMPANY_NAME,A.PREFECTURE_CODE,"
        + "A.COD_TYPE, A.DELIVERY_DATE_TYPE, A.DELIVERY_APPOINTED_TIME_TYPE, A.DELIVERY_APPOINTED_START_TIME, "
        + "A.DELIVERY_APPOINTED_END_TIME, A.ORM_ROWID, A.CREATED_USER, A.CREATED_DATETIME, "
        + "A.UPDATED_USER, A.UPDATED_DATETIME "
        + "FROM JD_DELIVERY_RELATED_INFO A "
        + "inner join DELIVERY_COMPANY B on B.DELIVERY_COMPANY_NO=A.DELIVERY_COMPANY_NO " 
        + "INNER JOIN jd_delivery_location DL ON A.DELIVERY_COMPANY_NO = DL.DELIVERY_COMPANY_NO AND A.PREFECTURE_CODE = DL.PREFECTURE_CODE WHERE (COD_TYPE=? OR COD_TYPE='2') AND A.DELIVERY_DATE_TYPE=0  ");
    if (StringUtil.hasValue(egionCode)) {
      builder.append(" AND A.PREFECTURE_CODE = '"+egionCode+"' ");
    }
    if (StringUtil.hasValue(cityCode)) {
      builder.append(" AND DL.CITY_CODE = '"+cityCode+"' ");
    }
    if (StringUtil.hasValue(areaCode)) {
      builder.append(" AND DL.AREA_CODE = '"+areaCode+"' ");
    }
    builder.append("AND A.DELIVERY_APPOINTED_TIME_TYPE=0 "
        + " AND CASE WHEN A.MIN_WEIGHT IS NULL THEN 0 ELSE A.MIN_WEIGHT END < ? "
        + " AND CASE WHEN A.MAX_WEIGHT IS NULL THEN 9999999999 ELSE A.MAX_WEIGHT END >= ? " 
        + " AND CASE WHEN DL.MIN_WEIGHT IS NULL THEN 0 ELSE DL.MIN_WEIGHT END <= ? " 
        + " AND CASE WHEN DL.MAX_WEIGHT IS NULL THEN 9999999999 ELSE DL.MAX_WEIGHT END >= ? ");


    return builder.toString();
  }
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  // 支付信息查询
  public static final String PAYINFO_BY_METHOD = DatabaseUtil.getSelectAllQuery(PaymentMethod.class)
      + " WHERE payment_method_type = ?   AND advance_later_flg=?  ";

  // 企划信息
  public static final String PLAN_NAME_BY_TYPE = " SELECT   A.PLAN_CODE,   A.PLAN_NAME ,  A.PLAN_TYPE FROM    PLAN A,    PLAN_COMMODITY B WHERE "
      + "A.PLAN_CODE = B.PLAN_CODE AND B.COMMODITY_CODE=? AND ? BETWEEN " + "A.PLAN_START_DATETIME AND  A.PLAN_END_DATETIME";

  // 品牌信息
  // add by os012 20120201 strat
  public static final String BRAND_INFO_BY_CODE = " SELECT    B.SHOP_CODE,    B.BRAND_CODE,  B.BRAND_NAME,  B.BRAND_NAME_ABBR,   B.BRAND_DESCRIPTION,   B.ORM_ROWID,   B.CREATED_USER, "
      + " B.CREATED_DATETIME,   B.UPDATED_USER,   B.UPDATED_DATETIME,   B.BRAND_ENGLISH_NAME,   B.TMALL_BRAND_CODE,   B.TMALL_BRAND_NAME "
      + "FROM    COMMODITY_HEADER A,   BRAND B WHERE A.BRAND_CODE=B.BRAND_CODE AND A. COMMODITY_CODE=? ";

  // add by os012 20120109 strat
  // 区县信息
  public static final String AREA_INFO_BY_CODE = " SELECT PREFECTURE_CODE, CITY_CODE, AREA_CODE, AREA_NAME, ORM_ROWID,"
      + "   CREATED_USER, CREATED_DATETIME, UPDATED_USER, UPDATED_DATETIME"
      + "   FROM AREA  WHERE PREFECTURE_CODE=? AND CITY_CODE= ? AND AREA_NAME=? ";
  
  public static final String JD_AREA_INFO_BY_CODE = " SELECT PREFECTURE_CODE, CITY_CODE, AREA_CODE, AREA_NAME, ORM_ROWID,"
    + "   CREATED_USER, CREATED_DATETIME, UPDATED_USER, UPDATED_DATETIME"
    + "   FROM JD_AREA  WHERE PREFECTURE_CODE=? AND CITY_CODE= ? AND AREA_NAME like ? limit 1";

  // batch时间信息
  public static final String BATCH_INFO_QUERY = " SELECT * FROM BATCH_TIME";

  public static final String BATCH_INFO_UPDATE = " UPDATE  BATCH_TIME SET FROM_TIME= ? , UPDATED_DATETIME=?,UPDATED_USER= ? ,BATCH_STATUS=?";

  // add by os012 20120109 end
  // add by os012 20111226 start
  // 修改库存TMALL引当数=引当数+订单商品数量差额
  public static final String UPDATE_STOCK_ALLOCATED_TMALL2 = "UPDATE TMALL_STOCK_ALLOCATION SET UPDATED_USER=?, UPDATED_DATETIME=?, "
    + "  ALLOCATED_QUANTITY = ALLOCATED_QUANTITY + ?  WHERE  SHOP_CODE = ?  AND SKU_CODE = ? AND ORIGINAL_COMMODITY_CODE = ?";
  
  public static final String UPDATE_STOCK_ALLOCATED_TMALL = "UPDATE STOCK  SET  UPDATED_USER=?, UPDATED_DATETIME=?, "
      + "  ALLOCATED_TMALL=ALLOCATED_TMALL+  ?  WHERE  SHOP_CODE=?   AND  SKU_CODE=? ";


  // 组合商品库存引单
  public static final String UPDATE_TMALL_STOCK_ALLOCATED = "UPDATE TMALL_STOCK_ALLOCATION SET UPDATED_USER=?, UPDATED_DATETIME=?, "
      + "ALLOCATED_QUANTITY = ALLOCATED_QUANTITY + ? WHERE  SHOP_CODE = ? AND SKU_CODE = ? AND ORIGINAL_COMMODITY_CODE = ?";
  
  
  public static final String UPDATE_STOCKTEMP_ALLOCATED = "UPDATE STOCKTEMP  SET  UPDATED_USER=?, UPDATED_DATETIME=?, "
      + "  ALLOCATED_TMALL=ALLOCATED_TMALL+  ?  WHERE  SHOP_CODE=?   AND  SKU_CODE=? ";

  // Tmall订单取消后修改库存TMALL引当数=引当数-EC侧此订单商品数量

  public static final String UPDATE_STOCK_ALLOCATED_AFTER_ORDER_CANCEL2 = "UPDATE TMALL_STOCK_ALLOCATION SET UPDATED_USER=?, UPDATED_DATETIME=?, "
      + "ALLOCATED_QUANTITY = ALLOCATED_QUANTITY - ?  WHERE  SHOP_CODE = ? AND SKU_CODE = ? AND ORIGINAL_COMMODITY_CODE = ?";
  
  public static final String UPDATE_STOCK_ALLOCATED_AFTER_ORDER_CANCEL = "UPDATE STOCK SET   UPDATED_USER=?, UPDATED_DATETIME=?, "
      + "  ALLOCATED_TMALL=ALLOCATED_TMALL-  ?  WHERE  SHOP_CODE=?   AND  SKU_CODE=? ";
  
  // 2014/06/12 库存更新对应 ob_李 add start
  public static final String UPDATE_TMALL_STOCK = "UPDATE TMALL_STOCK  SET  UPDATED_USER=?, UPDATED_DATETIME=?, "
    + "  ALLOCATED_QUANTITY = ALLOCATED_QUANTITY +  ?  WHERE  SHOP_CODE=?   AND  SKU_CODE=? ";
  
  public static final String UPDATE_TMALL_STOCK_AFTER_ORDER_CANCEL = "UPDATE TMALL_STOCK SET   UPDATED_USER=?, UPDATED_DATETIME=?, "
    + "  ALLOCATED_QUANTITY = ALLOCATED_QUANTITY-  ?  WHERE  SHOP_CODE=?   AND  SKU_CODE=? ";
  
  // 2014/06/12 库存更新对应 ob_李 add end

  // add by os012 20111226 end
  
  public static final String UPDATE_TMALL_SUIT_COMMODITY_CANCEL = "UPDATE TMALL_SUIT_COMMODITY SET UPDATED_USER=?, UPDATED_DATETIME=?, "
    + "ALLOCATED_QUANTITY = ALLOCATED_QUANTITY - ?  WHERE  COMMODITY_CODE = ? ";
  
  public static final String UPDATE_TMALL_SUIT_COMMODITY = "UPDATE TMALL_SUIT_COMMODITY SET UPDATED_USER=?, UPDATED_DATETIME=?, "
    + "ALLOCATED_QUANTITY = ALLOCATED_QUANTITY + ?  WHERE  COMMODITY_CODE = ? ";
  
  public static final String UPDATE_SUIT_STOCK_CANCEL = "UPDATE STOCK SET UPDATED_USER=?, UPDATED_DATETIME=?, "
    + "ALLOCATED_TMALL = ALLOCATED_TMALL - ?  WHERE  SHOP_CODE = ? AND COMMODITY_CODE = ? ";
  
  public static final String UPDATE_SUIT_STOCK = "UPDATE STOCK SET UPDATED_USER=?, UPDATED_DATETIME=?, "
    + "ALLOCATED_TMALL = ALLOCATED_TMALL + ?  WHERE  SHOP_CODE = ? AND COMMODITY_CODE = ? ";

  public static final String COUNT_USED_COUPON_ORDER_QUERY = "SELECT COUNT(*) FROM ORDER_HEADER"
      + " WHERE DISCOUNT_CODE = ? AND ORDER_STATUS <> ?";

  public static final String COUNT_USED_COUPON_ORDER_BY_CUSTOMER_CODE_QUERY = "SELECT COUNT(*) FROM ORDER_HEADER"
      + " WHERE DISCOUNT_CODE = ? AND CUSTOMER_CODE = ? AND ORDER_STATUS <> ?";

  public static final String COUNT_USED_COUPON_FIRST_ORDER_QUERY = "SELECT COUNT(*) "
    + " FROM ORDER_HEADER OH  "
    + " INNER JOIN SHIPPING_HEADER SH ON OH.ORDER_NO = SH.ORDER_NO "
    + " WHERE OH.CUSTOMER_CODE = ?  "
    + " AND (SH.SHIPPING_STATUS <> 4 OR SHIPPING_DIRECT_DATE IS NOT NULL) ";
  /**
   * 出荷Noがもつ出荷明細(SHIPPING_DETAIL)を購入商品数总数するSQL <BR>
   */
  public static final String SHIPPING_DETAIL_PURCHASING_AMOUNT_QUERY = "SELECT SUM(PURCHASING_AMOUNT) FROM SHIPPING_DETAIL "
      + "WHERE SHIPPING_NO = ? AND SKU_CODE=?";

  /**
   * 出荷Noがもつ出荷明細(SHIPPING_DETAIL_LIST)を发货总数するSQL <BR>
   */
  public static final String SHIPPING_REALITY_DETAIL_SHIPPING_AMOUNT_QUERY = "SELECT SUM(SHIPPING_AMOUNT) FROM SHIPPING_REALITY_DETAIL "
      + "WHERE SHIPPING_NO = ? AND SKU_CODE=?";

  /**
   *EC订单在库修改
   */
  public static final String UPDATE_STOCK_EC_QUERY = "UPDATE STOCK SET STOCK_QUANTITY = ? , ALLOCATED_QUANTITY = ? ,STOCK_TOTAL = ? "
      + "WHERE SHOP_CODE = ? AND SKU_CODE = ? ";

  /**
   *淘宝订单在库修改
   */
  public static final String UPDATE_STOCK_T_MALL_QUERY = "UPDATE STOCK SET STOCK_TMALL = ? , ALLOCATED_TMALL = ? ,STOCK_TOTAL = ? "
      + "WHERE SHOP_CODE = ? AND SKU_CODE = ? ";

  /**
   *出荷ヘッダ修改
   */
  public static final String UPDATE_SHIPPING_HEADER_QUERY = "UPDATE SHIPPING_HEADER SET SHIPPING_STATUS = ? , SHIPPING_DATE = ? "
      + "WHERE SHIPPING_NO = ? AND ORDER_NO = ? ";

  public static final String GET_CUSTOMER_GOURP_CAMPAIGN = " SELECT * FROM CUSTOMER_GROUP_CAMPAIGN WHERE CAMPAIGN_CODE = ?";

  public static final String LOAD_PUBLIC_COUPON_QUERY = DatabaseUtil.getSelectAllQuery(NewCouponRule.class)
      + " WHERE  COUPON_TYPE = ? AND COUPON_CODE = ?";

  /**
   * 获取同一订单运单号
   */
  public static final String GET_DILIVERY_SLIP_NO_SUM = "SELECT SKU_CODE AS NAME , ARRAY_TO_STRING(ARRAY_AGG(DELIVERY_SLIP_NO),',') "
      + " AS VALUE  FROM SHIPPING_REALITY_DETAIL WHERE SHIPPING_NO = ? GROUP BY SKU_CODE ";

  /**
   * 获取同一订单运单号
   */
  public static final String GET_TMALL_DILIVERY_SLIP_NO_SUM = "SELECT SKU_CODE AS NAME , ARRAY_TO_STRING(ARRAY_AGG(DELIVERY_SLIP_NO),',') "
      + " AS VALUE  FROM TMALL_SHIPPING_REALITY_DETAIL WHERE SHIPPING_NO = ? GROUP BY SKU_CODE ";

  public static final String GET_JD_DILIVERY_SLIP_NO_SUM = "SELECT SKU_CODE AS NAME , ARRAY_TO_STRING(ARRAY_AGG(DELIVERY_SLIP_NO),',') "
    + " AS VALUE  FROM JD_SHIPPING_REALITY_DETAIL WHERE SHIPPING_NO = ? GROUP BY SKU_CODE ";
  
  public static final String GET_DILIVERY_SLIP_NO_ARRAY = " SELECT ARRAY_TO_STRING(ARRAY_AGG(DISTINCT DELIVERY_SLIP_NO),',') "
      + " FROM SHIPPING_REALITY_DETAIL WHERE SHIPPING_NO = ? GROUP BY SHIPPING_NO  ";

  public static final String GET_RETURN_ORDER_DETAIL = " SELECT * FROM ORDER_DETAIL WHERE ORDER_NO = "
      + " ( SELECT ORDER_NO FROM SHIPPING_HEADER WHERE SHIPPING_NO = "
      + " ( SELECT SHIPPING_NO FROM SHIPPING_HEADER  WHERE ORIGINAL_SHIPPING_NO = ? AND RETURN_ITEM_TYPE = 1 )) "
      + "AND SHOP_CODE = ?  AND SKU_CODE = ? ";

  /**
   * まだ支払っていない受注ヘッダ情報を取得するクエリ
   */
  public static final String GET_ORDER_HEADER_BY_PAYMENT_STATUS = " SELECT * FROM ORDER_HEADER OH "
      + " WHERE OH.PAYMENT_STATUS = '0' " + " AND OH.ORDER_STATUS IN ('0','1') " + " AND OH.PAYMENT_METHOD_TYPE IN ('"
      + PaymentMethodType.CHINA_UNIONPAY.getValue() + "','" + PaymentMethodType.ALIPAY.getValue() + "','" + PaymentMethodType.OUTER_CARD.getValue() + "','" + PaymentMethodType.INNER_CARD.getValue() + "')";

  // 2013/04/13 优惠券对应 ob update start
  public static final String CANCEL_COUPON_USED = "UPDATE NEW_COUPON_HISTORY SET USE_STATUS = " + UseStatus.UNUSED.getValue()
    + " , coupon_status = " + CouponStatus.USED.getValue()
    + " , USE_ORDER_NO = NULL WHERE USE_ORDER_NO = ?";
  
  public static final String UPDATE_FRIEND_COUPON_USE_HISTORY = "UPDATE FRIEND_COUPON_USE_HISTORY SET POINT_STATUS = ? WHERE ORDER_NO = ? ";
  // 2013/04/13 优惠券对应 ob update end
  
  public static final String GET_ALIPAY_PAYMETHOD_INFO = "SELECT * FROM PAYMENT_METHOD WHERE PAYMENT_METHOD_TYPE='" 
    + PaymentMethodType.ALIPAY.longValue() + "' limit 1 ";
  
  public static final String GET_CUSTOMER_USE_PAYMENT_METHOD_QUERY = "SELECT"
      + " MAX(ORDER_DATETIME)AS ORDER_DATETIME,"
      + " PAYMENT_METHOD_NO"
      + " FROM ORDER_HEADER"
      + " WHERE CUSTOMER_CODE = ?"
      + " AND ORDER_STATUS = ?"
      + " GROUP BY PAYMENT_METHOD_NO"
      + " ORDER BY ORDER_DATETIME DESC";
  
  public static final String GET_COUPON_LIMIT_ORDER_CHECK = "SELECT"
      + " NCR.PERSONAL_USE_LIMIT"
      + " FROM NEW_COUPON_RULE NCR "
      + " INNER JOIN ORDER_HEADER OH ON OH.DISCOUNT_CODE = ? "
      + " INNER JOIN SHIPPING_HEADER SH ON SH.ORDER_NO = OH.ORDER_NO "
      + " WHERE NCR.COUPON_TYPE = 2 AND "
      + " SH.SHIPPING_STATUS <> 4 AND "
      + " SH.RETURN_ITEM_TYPE <> 1 AND "
      + " NCR.COUPON_CODE = ? AND " 
      + "(SH.MOBILE_NUMBER = ? OR REPLACE(SH.ADDRESS_LAST_NAME,' ','') = ? OR " 
      + " REPLACE(SH.ADDRESS1 || SH.ADDRESS2 ||  SH.ADDRESS3 ||  SH.ADDRESS4,' ','') = ? ) " ;
  
  public static final String GET_COUPON_LIMIT_NEW_ORDER_CHECK = "SELECT"
      + " NCR.PERSONAL_USE_LIMIT"
      + " FROM NEW_COUPON_RULE NCR "
      + " INNER JOIN ORDER_HEADER OH ON OH.DISCOUNT_CODE = ? "
      + " INNER JOIN SHIPPING_HEADER SH ON SH.ORDER_NO = OH.ORDER_NO "
      + " WHERE NCR.COUPON_TYPE = 2 AND "
      + " SH.SHIPPING_STATUS <> 4 AND "
      + " SH.RETURN_ITEM_TYPE <> 1 AND "
      + " NCR.COUPON_CODE = ? AND " 
      + " (SH.MOBILE_NUMBER = ? OR SH.PHONE_NUMBER = ? " 
      + " OR REPLACE(SH.ADDRESS_LAST_NAME || SH.ADDRESS1 || SH.ADDRESS2 ||  SH.ADDRESS3 ||  SH.ADDRESS4,' ','') = ?) " ;
  
  public static final String CAMPAIGN_DISCOUNT_USED_QUERY= "SELECT COUNT(SD.SHIPPING_NO) "
    + " FROM ORDER_HEADER OH  "
    + " INNER JOIN SHIPPING_HEADER SH ON OH.ORDER_NO = SH.ORDER_NO " 
    + " INNER JOIN SHIPPING_DETAIL SD ON SH.SHIPPING_NO = SD.SHIPPING_NO"
    + " WHERE SD.CAMPAIGN_CODE = ?  "
    + " AND SH.SHIPPING_STATUS <> 4 " 
    + " AND SH.ORDER_NO <> ?";
  
  public static final String GET_TMALL_ORDER_DETAIL= "SELECT TOD.ORDER_NO,TOD.PURCHASING_AMOUNT "
    + " FROM TMALL_ORDER_DETAIL TOD  "
    + " WHERE TOD.ORDER_NO = ?  "
    + " AND TOD.SKU_CODE = ?";
  
  public static final String GET_SHIPPING_DETAIL_COMPOSITION = "SELECT * FROM SHIPPING_DETAIL_COMPOSITION WHERE SHIPPING_NO = ? AND CHILD_SKU_CODE = ?";
  
  public static final String GET_TMALL_SHIPPING_DETAIL_COMPOSITION = "SELECT * FROM TMALL_SHIPPING_DETAIL_COMPOSITION WHERE SHIPPING_NO = ? AND CHILD_SKU_CODE = ?";
  
  public static final String GET_JD_SHIPPING_DETAIL_COMPOSITION = "SELECT * FROM JD_SHIPPING_DETAIL_COMPOSITION WHERE SHIPPING_NO = ? AND CHILD_SKU_CODE = ?";
  
  // 20140306 txw add start
  public static final String GET_PROPAGANDA_ACTIVITY_COMMODITY_QUERY = "SELECT"
    + " CH.COMMODITY_CODE,"
    + " CH.COMMODITY_NAME,"
    + " PAC.PURCHASING_AMOUNT,"
    + " CD.WEIGHT AS COMMODITY_WEIGHT"
    + " FROM PROPAGANDA_ACTIVITY_RULE PAR"
    + " INNER JOIN PROPAGANDA_ACTIVITY_COMMODITY PAC ON PAR.ACTIVITY_CODE = PAC.ACTIVITY_CODE"
    + " INNER JOIN COMMODITY_HEADER CH ON PAC.COMMODITY_CODE = CH.COMMODITY_CODE"
    + " INNER JOIN COMMODITY_DETAIL CD ON CH.COMMODITY_CODE = CD.COMMODITY_CODE"
    + " WHERE PAR.ORDER_TYPE = " + OrderType.EC.getValue()
    + " AND NOW() BETWEEN PAR.ACTIVITY_START_DATETIME AND PAR.ACTIVITY_END_DATETIME"
    + " AND(PAR.DELIVERY_AREA IS NULL OR PAR.DELIVERY_AREA = ''"
    + "   OR ? IN (SELECT REGEXP_SPLIT_TO_TABLE(PAR.DELIVERY_AREA, ';')))"
    + " AND CASE WHEN PAR.LANGUAGE_CODE = " + LanguageType.Zh_Cn.getValue() + " THEN '" + LanguageCode.Zh_Cn.getValue() + "' IN (SELECT LANGUAGE_CODE FROM CUSTOMER WHERE CUSTOMER_CODE = ?)"
    + "          WHEN PAR.LANGUAGE_CODE = " + LanguageType.Ja_Jp.getValue() + " THEN '" + LanguageCode.Ja_Jp.getValue() + "' IN (SELECT LANGUAGE_CODE FROM CUSTOMER WHERE CUSTOMER_CODE = ?)"
    + "          WHEN PAR.LANGUAGE_CODE = " + LanguageType.En_Us.getValue() + " THEN '" + LanguageCode.En_Us.getValue() + "' IN (SELECT LANGUAGE_CODE FROM CUSTOMER WHERE CUSTOMER_CODE = ?)"
    + "     END"
    + " AND (SELECT COUNT(*) FROM STOCK WHERE COMMODITY_CODE = PAC.COMMODITY_CODE AND PAC.PURCHASING_AMOUNT <= (STOCK_QUANTITY - ALLOCATED_QUANTITY)) > 0";
  
  public static final String GET_TMALL_PROPAGANDA_ACTIVITY_COMMODITY_QUERY = "SELECT"
    + " CH.COMMODITY_CODE,"
    + " CH.COMMODITY_NAME,"
    + " PAC.PURCHASING_AMOUNT,"
    + " CD.WEIGHT AS COMMODITY_WEIGHT"
    + " FROM PROPAGANDA_ACTIVITY_RULE PAR"
    + " INNER JOIN PROPAGANDA_ACTIVITY_COMMODITY PAC ON PAR.ACTIVITY_CODE = PAC.ACTIVITY_CODE"
    + " INNER JOIN COMMODITY_HEADER CH ON PAC.COMMODITY_CODE = CH.COMMODITY_CODE"
    + " INNER JOIN COMMODITY_DETAIL CD ON CH.COMMODITY_CODE = CD.COMMODITY_CODE"
    + " WHERE PAR.ORDER_TYPE = " + OrderType.TMALL.getValue()
    + " AND NOW() BETWEEN PAR.ACTIVITY_START_DATETIME AND PAR.ACTIVITY_END_DATETIME"
    + " AND(PAR.DELIVERY_AREA IS NULL OR PAR.DELIVERY_AREA = ''"
    + "   OR ? IN (SELECT REGEXP_SPLIT_TO_TABLE(PAR.DELIVERY_AREA, ';')))"
//    + " AND CASE WHEN PAR.LANGUAGE_CODE = " + LanguageType.Zh_Cn.getValue() + " THEN '" + LanguageCode.Zh_Cn.getValue() + "' IN (SELECT LANGUAGE_CODE FROM CUSTOMER WHERE CUSTOMER_CODE = ?)"
//    + "     END"
    + " AND (SELECT COUNT(*) FROM STOCK WHERE COMMODITY_CODE = PAC.COMMODITY_CODE AND PAC.PURCHASING_AMOUNT <= (STOCK_QUANTITY - ALLOCATED_QUANTITY)) > 0";
  // 20140306 txw add end
 
  // 20140512 hdh add start
  
  //通过京东商品编号获取商品详细信息
  public static final String GET_C_COMMDITY_DETAIL_BY_JD_COMMODITY_CODE = "SELECT CCD.* FROM C_COMMODITY_DETAIL CCD "+
    " INNER JOIN C_COMMODITY_HEADER CCH ON CCD.COMMODITY_CODE = CCH.COMMODITY_CODE"+
    " WHERE CCH.JD_COMMODITY_ID = ? ";
 
  
  public static final String GET_C_COMMDITY_DETAIL_BY_SKU_CODE = "SELECT * FROM C_COMMODITY_DETAIL  "+
  " WHERE SKU_CODE= ? ";
  
  
  
  //通过京东订单编号获取订单信息
  public static final String GET_JD_ORDER_HEADER_BY_JD_ORDERNO = "SELECT * FROM JD_ORDER_HEADER WHERE "+
    "CUSTOMER_CODE= ?";
  
  //通过京东订单编号获取发货信息
  public static final String GET_JD_SHIPPING_HEADER_BY_JD_ORDERNO = "SELECT * FROM JD_SHIPPING_HEADER WHERE "+
  "CUSTOMER_CODE= ?";
  
  // 20140512 hdh add start
  

  // 京东batch时间信息
  public static final String JD_BATCH_INFO_QUERY = " SELECT * FROM JD_BATCH_TIME";
  
  //更新batch时间信息
  public static final String JD_BATCH_INFO_UPDATE = " UPDATE  JD_BATCH_TIME SET FROM_TIME= ? , UPDATED_DATETIME=?,UPDATED_USER= ? ,BATCH_STATUS=?";

  public static final String GET_COMMODITY_DETAIL = "SELECT COMMODITY_CODE, WEIGHT FROM COMMODITY_DETAIL "
    + "WHERE COMMODITY_CODE = ? AND SHOP_CODE = ?";
  
  // 取得天猫订单商品的code, name
  public static final String TMALL_ORDER_DETAIL_COMMODITY_LIST = "SELECT COMMODITY_CODE, COMMODITY_NAME FROM TMALL_ORDER_DETAIL "
    + "WHERE ORDER_NO = ? AND SHOP_CODE = ?";
  
  //通过子订单号取得京东交易号
  public static final String GET_CHILD_ORDER_NO = "SELECT CUSTOMER_CODE FROM JD_SHIPPING_HEADER WHERE CHILD_ORDER_NO=?";
  
  //通过用户收获详细地址 检索虚假订单关键词
  public static final String GET_COUNT_UNTRUE_ORDER_WORD="SELECT COUNT(1) FROM UNTRUE_ORDER_WORD WHERE ORDER_WORD_NAME=?";
  
  //查询关键词列表
  public static final String GET_UNTRUE_ORDER_WORD_LIST="SELECT ORDER_WORD_NAME FROM UNTRUE_ORDER_WORD";
  
}
