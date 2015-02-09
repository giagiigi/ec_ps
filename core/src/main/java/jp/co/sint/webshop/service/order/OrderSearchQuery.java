package jp.co.sint.webshop.service.order;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import jp.co.sint.webshop.data.AbstractQuery;
import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.domain.DataTransportStatus;
import jp.co.sint.webshop.data.domain.FixedSalesStatus;
import jp.co.sint.webshop.data.domain.PaymentStatus;
import jp.co.sint.webshop.data.dto.Customer;
import jp.co.sint.webshop.data.dto.OrderHeader;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.SqlDialect;
import jp.co.sint.webshop.utility.SqlFragment;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.utility.SqlDialect.LikeClauseOption;

import org.apache.log4j.Logger;

public class OrderSearchQuery extends AbstractQuery<OrderHeader> {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;

  // ログインIDで顧客情報を取得する
  public static final String LOAD_CUSTOMER_QUERY_BY_LOGIN_ID = DatabaseUtil.getSelectAllQuery(Customer.class)
      + " WHERE LOGIN_ID = ?";

  public static final String CHECK_EMAIL_QUERY = " SELECT EMAIL FROM CUSTOMER " + "WHERE CUSTOMER_CODE <> ? AND EMAIL = ?";

  public static final String CHECK_LOGINID_QUERY = " SELECT LOGIN_ID FROM CUSTOMER " + "WHERE CUSTOMER_CODE <> ? AND LOGIN_ID = ?";
//2014/06/12 库存更新对应 ob_李 add start
  public static final String GET_TMALL_ORDER_HEADER = " SELECT * FROM TMALL_ORDER_HEADER WHERE TMALL_TID = ?";
  
  public static final String GET_TMALL_SHIPPING_HEADER = " SELECT * FROM TMALL_SHIPPING_HEADER WHERE ORDER_NO = ?";
//2014/06/12 库存更新对应 ob_李 add end
  // add by os012 20111226 start
  // 查询取消订单
  public static final String CHECK_CANCEL_QUERY = " SELECT  ORDER_NO, SHOP_CODE, ORDER_DATETIME, CUSTOMER_CODE, GUEST_FLG, "
      + " LAST_NAME, FIRST_NAME, LAST_NAME_KANA, FIRST_NAME_KANA, EMAIL, "
      + " POSTAL_CODE, PREFECTURE_CODE, ADDRESS1, ADDRESS2, ADDRESS3, ADDRESS4, "
      + "  PHONE_NUMBER, ADVANCE_LATER_FLG, PAYMENT_METHOD_NO, PAYMENT_METHOD_TYPE, "
      + "  PAYMENT_METHOD_NAME, PAYMENT_COMMISSION, PAYMENT_COMMISSION_TAX_RATE, "
      + "  PAYMENT_COMMISSION_TAX, PAYMENT_COMMISSION_TAX_TYPE, USED_POINT, "
      + "   PAYMENT_DATE, PAYMENT_LIMIT_DATE, PAYMENT_STATUS, CUSTOMER_GROUP_CODE, "
      + "  DATA_TRANSPORT_STATUS, ORDER_STATUS, CLIENT_GROUP, CAUTION, MESSAGE, "
      + "  PAYMENT_ORDER_ID, CVS_CODE, PAYMENT_RECEIPT_NO, PAYMENT_RECEIPT_URL, "
      + "  DIGITAL_CASH_TYPE, WARNING_MESSAGE, ORM_ROWID, CREATED_USER, "
      + "  CREATED_DATETIME, UPDATED_USER, UPDATED_DATETIME, CITY_CODE, "
      + "  MOBILE_NUMBER, COUPON_PRICE, BANK_NO, BANK_NAME, OLD_ORDER_NO, "
      + "  INVOICE_FLG, PAID_PRICE  FROM ORDER_HEADER   WHERE   ORDER_NO= ?  AND ORDER_STATUS= ?";

  // TMALL查询取消订单
  public static final String TMALL_CHECK_CANCEL_QUERY = "SELECT ORDER_NO, SHOP_CODE, ORDER_DATETIME, CUSTOMER_CODE, GUEST_FLG, "
      + "  LAST_NAME, FIRST_NAME, LAST_NAME_KANA, FIRST_NAME_KANA, EMAIL, "
      + "  POSTAL_CODE, PREFECTURE_CODE, ADDRESS1, ADDRESS2, ADDRESS3, ADDRESS4, "
      + "  PHONE_NUMBER, ADVANCE_LATER_FLG, PAYMENT_METHOD_NO, PAYMENT_METHOD_TYPE, "
      + "  PAYMENT_METHOD_NAME, PAYMENT_COMMISSION, PAYMENT_COMMISSION_TAX_RATE, "
      + "  PAYMENT_COMMISSION_TAX, PAYMENT_COMMISSION_TAX_TYPE, USED_POINT, "
      + "   PAYMENT_DATE, PAYMENT_LIMIT_DATE, PAYMENT_STATUS, CUSTOMER_GROUP_CODE, "
      + "  DATA_TRANSPORT_STATUS, ORDER_STATUS, CLIENT_GROUP, CAUTION, MESSAGE, "
      + "  PAYMENT_ORDER_ID, CVS_CODE, PAYMENT_RECEIPT_NO, PAYMENT_RECEIPT_URL, "
      + "  DIGITAL_CASH_TYPE, WARNING_MESSAGE, ORM_ROWID, CREATED_USER, "
      + "   CREATED_DATETIME, UPDATED_USER, UPDATED_DATETIME, CITY_CODE, "
      + "   MOBILE_NUMBER, COUPON_PRICE, INVOICE_FLG, DISCOUNT_TYPE, DISCOUNT_MODE, "
      + "   DISCOUNT_RATE, DISCOUNT_PRICE, DISCOUNT_CODE, DISCOUNT_NAME, "
      + "   DISCOUNT_DETAIL_CODE, ORDER_FLG, TMALL_END_TIME, TMALL_BUYER_MESSAGE, "
      + "   TMALL_STATUS, TMALL_MODIFIED_TIME, TMALL_COD_STATUS, TMALL_TYPE, "
      + "   TMALL_TID, TMALL_REAL_POINT_FEE, TMALL_INVOICE_NAME, AREA_CODE FROM TMALL_ORDER_HEADER   WHERE   TMALL_TID= ?  AND ORDER_STATUS= ?";

  // EC订单查询
  public static final String ORDER_HEADER_QUERY = " SELECT  ORDER_NO, SHOP_CODE, ORDER_DATETIME, CUSTOMER_CODE, GUEST_FLG, "
      + " LAST_NAME, FIRST_NAME, LAST_NAME_KANA, FIRST_NAME_KANA, EMAIL, "
      + " POSTAL_CODE, PREFECTURE_CODE, ADDRESS1, ADDRESS2, ADDRESS3, ADDRESS4, "
      + "  PHONE_NUMBER, ADVANCE_LATER_FLG, PAYMENT_METHOD_NO, PAYMENT_METHOD_TYPE, "
      + "  PAYMENT_METHOD_NAME, PAYMENT_COMMISSION, PAYMENT_COMMISSION_TAX_RATE, "
      + "  PAYMENT_COMMISSION_TAX, PAYMENT_COMMISSION_TAX_TYPE, USED_POINT, "
      + "   PAYMENT_DATE, PAYMENT_LIMIT_DATE, PAYMENT_STATUS, CUSTOMER_GROUP_CODE, "
      + "  DATA_TRANSPORT_STATUS, ORDER_STATUS, CLIENT_GROUP, CAUTION, MESSAGE, "
      + "  PAYMENT_ORDER_ID, CVS_CODE, PAYMENT_RECEIPT_NO, PAYMENT_RECEIPT_URL, "
      + "  DIGITAL_CASH_TYPE, WARNING_MESSAGE, ORM_ROWID, CREATED_USER, "
      + "  CREATED_DATETIME, UPDATED_USER, UPDATED_DATETIME, CITY_CODE, "
      + "  MOBILE_NUMBER, COUPON_PRICE, BANK_NO, BANK_NAME, OLD_ORDER_NO, "
      + "  INVOICE_FLG, PAID_PRICE  FROM ORDER_HEADER   WHERE   ORDER_NO= ?  ";

  // EC详细订单查询
  public static final String ORDER_DETAIL_QUERY = "SELECT ORDER_NO, SHOP_CODE, SKU_CODE, COMMODITY_CODE, COMMODITY_NAME,"
      + "  STANDARD_DETAIL1_NAME, STANDARD_DETAIL2_NAME, PURCHASING_AMOUNT, "
      + " UNIT_PRICE, RETAIL_PRICE, RETAIL_TAX, COMMODITY_TAX_RATE, COMMODITY_TAX, "
      + " COMMODITY_TAX_TYPE, CAMPAIGN_CODE, CAMPAIGN_NAME, CAMPAIGN_DISCOUNT_RATE, "
      + " APPLIED_POINT_RATE, ORM_ROWID, CREATED_USER, CREATED_DATETIME, " + " UPDATED_USER, UPDATED_DATETIME"
      + " FROM ORDER_DETAIL WHERE ORDER_NO= ?    ";

  // TMALL详细订单查询
  public static final String TMALL_ORDER_DETAIL_LIST_QUERY = "SELECT ORDER_NO, SHOP_CODE, SKU_CODE, COMMODITY_CODE, COMMODITY_NAME, "
      + "STANDARD_DETAIL1_NAME, STANDARD_DETAIL2_NAME, PURCHASING_AMOUNT, "
      + " UNIT_PRICE, RETAIL_PRICE, RETAIL_TAX, COMMODITY_TAX_RATE, COMMODITY_TAX, "
      + " COMMODITY_TAX_TYPE, CAMPAIGN_CODE, CAMPAIGN_NAME, CAMPAIGN_DISCOUNT_RATE, "
      + "   APPLIED_POINT_RATE, SALE_PLAN_CODE, SALE_PLAN_NAME, "
      + "  FEATURED_PLAN_CODE, FEATURED_PLAN_NAME, BRAND_CODE, BRAND_NAME, "
      + "   TMALL_SKU_CODE, TMALL_COMMODITY_CODE, TMALL_REFUND_STATUS, TMALL_MODIFIED_TIME, "
      + "   TMALL_REFUND_PAID_PRICE, TMALL_REFUND_ID, ORM_ROWID, CREATED_USER, "
      + "  CREATED_DATETIME, UPDATED_USER, UPDATED_DATETIME" + " FROM TMALL_ORDER_DETAIL WHERE ORDER_NO= ?    ";

  public static final String TMALL_ORDER_DETAIL_QUERY = "SELECT A.ORDER_NO, A.SHOP_CODE, A.SKU_CODE, A.COMMODITY_CODE, A.COMMODITY_NAME, "
      + " A.STANDARD_DETAIL1_NAME, A.STANDARD_DETAIL2_NAME, A.PURCHASING_AMOUNT, "
      + "  A.UNIT_PRICE, A.RETAIL_PRICE, A.RETAIL_TAX, A.COMMODITY_TAX_RATE, A.COMMODITY_TAX, "
      + " A.COMMODITY_TAX_TYPE, A.CAMPAIGN_CODE, A.CAMPAIGN_NAME, A.CAMPAIGN_DISCOUNT_RATE, "
      + "   A.APPLIED_POINT_RATE, A.SALE_PLAN_CODE, A.SALE_PLAN_NAME, "
      + "  A.FEATURED_PLAN_CODE, A.FEATURED_PLAN_NAME, A.BRAND_CODE, A.BRAND_NAME, "
      + "   A.TMALL_SKU_CODE, A.TMALL_COMMODITY_CODE, A.TMALL_REFUND_STATUS, A.TMALL_MODIFIED_TIME, "
      + "   A.TMALL_REFUND_PAID_PRICE, A.TMALL_REFUND_ID, A.ORM_ROWID, A.CREATED_USER, "
      + "   A.CREATED_DATETIME, A.UPDATED_USER, A.UPDATED_DATETIME"
      + "   FROM   TMALL_ORDER_DETAIL  A, "
      + " (SELECT ORDER_NO,TMALL_TID FROM  TMALL_ORDER_HEADER   GROUP BY ORDER_NO,TMALL_TID )B"
      + " WHERE   A.ORDER_NO = B.ORDER_NO AND A.SKU_CODE= ?   AND B.TMALL_TID= ? ";
  
  public static final String TMALL_ORDER_DETAIL_LIST_BYTID_QUERY = "SELECT A.ORDER_NO, A.SHOP_CODE, A.SKU_CODE, A.COMMODITY_CODE, A.COMMODITY_NAME, "
    + " A.STANDARD_DETAIL1_NAME, A.STANDARD_DETAIL2_NAME, A.PURCHASING_AMOUNT, "
    + "  A.UNIT_PRICE, A.RETAIL_PRICE, A.RETAIL_TAX, A.COMMODITY_TAX_RATE, A.COMMODITY_TAX, "
    + " A.COMMODITY_TAX_TYPE, A.CAMPAIGN_CODE, A.CAMPAIGN_NAME, A.CAMPAIGN_DISCOUNT_RATE, "
    + "   A.APPLIED_POINT_RATE, A.SALE_PLAN_CODE, A.SALE_PLAN_NAME, "
    + "  A.FEATURED_PLAN_CODE, A.FEATURED_PLAN_NAME, A.BRAND_CODE, A.BRAND_NAME, "
    + "   A.TMALL_SKU_CODE, A.TMALL_COMMODITY_CODE, A.TMALL_REFUND_STATUS, A.TMALL_MODIFIED_TIME, "
    + "   A.TMALL_REFUND_PAID_PRICE, A.TMALL_REFUND_ID, A.ORM_ROWID, A.CREATED_USER, "
    + "   A.CREATED_DATETIME, A.UPDATED_USER, A.UPDATED_DATETIME"
    + "   FROM   TMALL_ORDER_DETAIL  A, "
    + " (SELECT ORDER_NO,TMALL_TID FROM  TMALL_ORDER_HEADER   GROUP BY ORDER_NO,TMALL_TID )B"
    + " WHERE   A.ORDER_NO = B.ORDER_NO  AND B.TMALL_TID= ? ";

  // EC详细订单查询
  public static final String ORDER_DETAIL_QUERY_BY_PK = "SELECT ORDER_NO, SHOP_CODE, SKU_CODE, COMMODITY_CODE, COMMODITY_NAME,"
      + "  STANDARD_DETAIL1_NAME, STANDARD_DETAIL2_NAME, PURCHASING_AMOUNT, "
      + " UNIT_PRICE, RETAIL_PRICE, RETAIL_TAX, COMMODITY_TAX_RATE, COMMODITY_TAX, "
      + " COMMODITY_TAX_TYPE, CAMPAIGN_CODE, CAMPAIGN_NAME, CAMPAIGN_DISCOUNT_RATE, "
      + " APPLIED_POINT_RATE, ORM_ROWID, CREATED_USER, CREATED_DATETIME, " + " UPDATED_USER, UPDATED_DATETIME"
      + " FROM ORDER_DETAIL WHERE ORDER_NO= ?  AND  SHOP_CODE=? AND SKU_CODE=?    ";

  // 取消EC订单
  public static final String UPDATE_ORDER_HEADER_ORDER_STATUS = "UPDATE ORDER_HEADER SET"
      + " ORDER_STATUS = ? ,UPDATED_USER = ? ,UPDATED_DATETIME = ? WHERE ORDER_NO = ? ";

  // 取消EC系统TMALL订单
  public static final String UPDATE_TMALL_ORDER_HEADER_ORDER_STATUS = "UPDATE TMALL_ORDER_HEADER SET"
      + " ORDER_STATUS = ? ,TMALL_STATUS=?, UPDATED_USER = ? ,UPDATED_DATETIME = ? WHERE ORDER_NO = ? ";

  // 取消EC系统TMALL发货单
  public static final String UPDATE_TMALL_SHIPPING_HEADER_STATUS = "UPDATE TMALL_SHIPPING_HEADER SET"
      + " SHIPPING_STATUS = ? ,UPDATED_USER = ? ,UPDATED_DATETIME = ?  WHERE ORDER_NO = ? ";

  // 取消EC系统TMALL订单付款状态
  public static final String UPDATE_TMALL_ORDER_HEADER_PAID_STATUS = "UPDATE TMALL_ORDER_HEADER SET"
      + " PAYMENT_STATUS = ? ,PAYMENT_DATE = ? , TMALL_COD_STATUS=?,TMALL_STATUS=?,TMALL_END_TIME=?,"
      + " TMALL_MODIFIED_TIME=?,TMALL_REAL_POINT_FEE=?,TMALL_INVOICE_NAME=?, PAID_PRICE=?,UPDATED_USER = ? ," 
      +	" ADJUST_FEE = ?, USED_POINT = ?, COMMISSION_FEE = ?, DISCOUNT_PRICE = ?, POINT_CONVERT_PRICE = ?, "
      +	" TMALL_DISCOUNT_PRICE= ?,MJS_DISCOUNT = ?, UPDATED_DATETIME = ? WHERE ORDER_NO = ? ";

  // 修改详细订单
  public static final String UPDATE_ORDER_DETAIL_ORDER = "UPDATE ORDER_DETAIL SET"
      + "  UPDATED_USER = ? ,UPDATED_DATETIME = ? ,PURCHASING_AMOUNT=? WHERE ORDER_NO = ? " + "  AND  SHOP_CODE=? AND SKU_CODE=? ";

  // 修改EC系统TMALL发货单
  public static final String UPDATE_TMALL_SHIPPING_HEADER_INFO = "UPDATE TMALL_SHIPPING_HEADER SET"
      + " ADDRESS_LAST_NAME = ? ,POSTAL_CODE=?,PREFECTURE_CODE=?,CITY_CODE=?,AREA_CODE=?,ADDRESS1=?,ADDRESS2=?,ADDRESS3=?,ADDRESS4=? ,ADDRESS_NO=?,"
      + "PHONE_NUMBER=?,MOBILE_NUMBER=?,SHIPPING_STATUS=?,UPDATED_USER = ? ,UPDATED_DATETIME = ?, " 
      + "ADDRESS_FIRST_NAME=?, "
      + " ADDRESS_LAST_NAME_KANA=?, "
      + "ADDRESS_FIRST_NAME_KANA=?, "
      + "   DELIVERY_REMARK=?, "
      + "   ACQUIRED_POINT=?, "
      + "   DELIVERY_SLIP_NO=?, "
      + " SHIPPING_CHARGE=?,"
      + "  SHIPPING_CHARGE_TAX_TYPE=?,"
      + "  SHIPPING_CHARGE_TAX_RATE=?, "
      + " SHIPPING_CHARGE_TAX=?, "
      + " DELIVERY_TYPE_NO=?,"
      + "  DELIVERY_TYPE_NAME=?, "
      + " DELIVERY_APPOINTED_TIME_START=?, "
      + " DELIVERY_APPOINTED_TIME_END=?," 
      + " ARRIVAL_DATE=?, "
      + " ARRIVAL_TIME_START=?, "
      + " ARRIVAL_TIME_END=?,  "
      + " SHIPPING_DIRECT_DATE=?,"
      + "  SHIPPING_DATE=?, "
      + " ORIGINAL_SHIPPING_NO=?, "
      + "RETURN_ITEM_DATE=?, "
      + " RETURN_ITEM_LOSS_MONEY=?,"
      + " RETURN_ITEM_TYPE=?, "
      + " DELIVERY_COMPANY_NO=?, "
      + "DELIVERY_COMPANY_NAME=?,    "
      + "DELIVERY_APPOINTED_DATE=?    WHERE ORDER_NO = ? ";

  // 修改EC系统TMALL详细订单
  public static final String UPDATE_TMALL_ORDER_DETAIL_ORDER = "UPDATE TMALL_ORDER_DETAIL SET"
      + "  UPDATED_USER = ? ,UPDATED_DATETIME = ? ,PURCHASING_AMOUNT=? WHERE ORDER_NO = ? " + "  AND  SHOP_CODE=? AND SKU_CODE=? ";
  public static final String UPDATE_EC_ORDER_DETAIL_ORDER = "UPDATE TMALL_ORDER_DETAIL SET"
    + "  UPDATED_USER = ? ,UPDATED_DATETIME = ? ,PURCHASING_AMOUNT=?,RETAIL_PRICE=?,UNIT_PRICE=? WHERE ORDER_NO = ? " + "  AND  SHOP_CODE=? AND SKU_CODE=? ";
  public static final String UPDATE_TMALL_ORDER_TARDE_FINISH = "UPDATE TMALL_ORDER_HEADER SET TMALL_STATUS=?, PAID_PRICE=?,TMALL_END_TIME=?,TMALL_MODIFIED_TIME=?,UPDATED_USER=?,UPDATED_DATETIME=? WHERE ORDER_NO=?";
  public static final String UPDATE_TMALL_ORDER_TARDE_PAID = "UPDATE TMALL_ORDER_HEADER SET PAYMENT_STATUS=?,PAYMENT_DATE=?,TMALL_MODIFIED_TIME=?,UPDATED_USER=?,UPDATED_DATETIME=? WHERE ORDER_NO=?";
  // add by os012 20111226 end
  public OrderSearchQuery() {

  }

  public OrderSearchQuery(OrderListSearchCondition condition, String query) {

    StringBuilder builder = new StringBuilder();
    builder.append(query);
    builder.append(" WHERE 1 = 1 ");

    List<Object> params = new ArrayList<Object>();

    // 検索条件:顧客名
    if (StringUtil.hasValue(condition.getCustomerName())) {
      SqlDialect dialect = SqlDialect.getDefault();
      SqlFragment fragment = dialect.createLikeClause("CONCAT(OH.LAST_NAME, OH.FIRST_NAME)", condition.getCustomerName(),
          LikeClauseOption.PARTIAL_MATCH);

      builder.append(" AND ");
      builder.append(fragment.getFragment());
      params.addAll(Arrays.asList(fragment.getParameters()));
    }
    // 検索条件:顧客名カナ
    if (StringUtil.hasValue(condition.getCustomerNameKana())) {
      SqlDialect dialect = SqlDialect.getDefault();
      SqlFragment fragment = dialect.createLikeClause("CONCAT(OH.LAST_NAME_KANA, OH.FIRST_NAME_KANA)", condition
          .getCustomerNameKana(), LikeClauseOption.PARTIAL_MATCH);

      builder.append(" AND ");
      builder.append(fragment.getFragment());
      params.addAll(Arrays.asList(fragment.getParameters()));
    }
    // 検索条件:顧客コード
    if (StringUtil.hasValue(condition.getCustomerCode())) {
      builder.append(" AND ");
      builder.append(" OH.CUSTOMER_CODE = ? ");
      params.add(condition.getCustomerCode());
    }
    // 検索条件:電話番号
    if (StringUtil.hasValue(condition.getTel())) {
      SqlDialect dialect = SqlDialect.getDefault();
      builder.append(" AND REPLACE(CA.PHONE_NUMBER, '-','') = " + dialect.quote(condition.getTel()));
    }
    // Add by V10-CH start
    // 検索条件:手機番号
    if (StringUtil.hasValue(condition.getMobileTel())) {
      SqlDialect dialect = SqlDialect.getDefault();
      builder.append(" AND CA.MOBILE_NUMBER = " + dialect.quote(condition.getMobileTel()));
    }
    // Add by V10-CH end
    // 検索条件:メール
    if (StringUtil.hasValue(condition.getEmail())) {
      SqlDialect dialect = SqlDialect.getDefault();
      SqlFragment fragment = dialect.createLikeClause("CU.EMAIL", condition.getEmail(), LikeClauseOption.PARTIAL_MATCH);
      builder.append(" AND ");
      builder.append(fragment.getFragment());
      params.addAll(Arrays.asList(fragment.getParameters()));
    }

    // 検索条件:受注日
    if (StringUtil.hasValueAnyOf(condition.getOrderDatetimeStart(), condition.getOrderDatetimeEnd())) {
      SqlDialect dialect = SqlDialect.getDefault();
      SqlFragment fragment = dialect.createRangeClause("OH.ORDER_DATETIME", DateUtil.fromString(condition.getOrderDatetimeStart()
          + " 00:00:00", true), DateUtil.fromString(condition.getOrderDatetimeEnd() + " 23:59:59", true));
      builder.append(" AND ");
      builder.append(fragment.getFragment());
      params.addAll(Arrays.asList(fragment.getParameters()));
    }

    // 検索条件:入金日
    if (StringUtil.hasValueAnyOf(condition.getPaymentDatetimeStart(), condition.getPaymentDatetimeEnd())
        && condition.getPaymentStatus().equals(PaymentStatus.PAID.getValue())) {
      SqlDialect dialect = SqlDialect.getDefault();
      SqlFragment fragment = dialect.createRangeClause("OH.PAYMENT_DATE", DateUtil.fromString(condition.getPaymentDatetimeStart()
          + " 00:00:00", true), DateUtil.fromString(condition.getPaymentDatetimeEnd() + " 23:59:59", true));
      builder.append(" AND ");
      builder.append(fragment.getFragment());
      params.addAll(Arrays.asList(fragment.getParameters()));
    }

    // 検索条件:受注番号
    if (StringUtil.hasValueAnyOf(condition.getOrderNoStart(), condition.getOrderNoEnd())) {

      SqlDialect dialect = SqlDialect.getDefault();
      SqlFragment fragment = dialect.createRangeClause("OH.ORDER_NO", condition.getOrderNoStart(), condition.getOrderNoEnd());

      builder.append(" AND ");
      builder.append(fragment.getFragment());
      params.addAll(Arrays.asList(fragment.getParameters()));
    }

    // 検索条件:受注ステータス
    if (StringUtil.hasValueAnyOf(condition.getOrderStatus())) {
      SqlDialect dialect = SqlDialect.getDefault();
      SqlFragment fragment = dialect.createInClause("OH.ORDER_STATUS", (Object[]) condition.getOrderStatus());

      builder.append(" AND ");
      builder.append(fragment.getFragment());
      params.addAll(Arrays.asList(fragment.getParameters()));
    }

    // 検索条件:出荷ステータス
    if (StringUtil.hasValueAnyOf(condition.getShippingStatusSummary())) {
      SqlDialect dialect = SqlDialect.getDefault();
      SqlFragment fragment = dialect.createInClause("OSV.SHIPPING_STATUS_SUMMARY", (Object[]) condition.getShippingStatusSummary());

      builder.append(" AND ");
      builder.append(fragment.getFragment());
      params.addAll(Arrays.asList(fragment.getParameters()));
    }

    // 検索条件:返品ステータス
    if (StringUtil.hasValueAnyOf(condition.getReturnStatusSummary())) {
      SqlDialect dialect = SqlDialect.getDefault();
      SqlFragment fragment = dialect.createInClause("OSV.RETURN_STATUS_SUMMARY", (Object[]) condition.getReturnStatusSummary());

      builder.append(" AND ");
      builder.append(fragment.getFragment());
      params.addAll(Arrays.asList(fragment.getParameters()));
    }

    // 検索条件:売上確定ステータス
    if (!condition.isSearchFixedSalesDataFlg()) {
      builder.append("AND OSV.FIXED_SALES_STATUS <> " + FixedSalesStatus.FIXED.getValue());
    }
    // 検索条件:データ連携ステータス
    if (!condition.isSearchDataTransportFlg()) {
      builder.append(" AND OSV.DATA_TRANSPORT_STATUS = " + DataTransportStatus.NOT_TRANSPORTED.getValue());
    }
    // 支払方法
    if (StringUtil.hasValue(condition.getPaymentMethod())) {
      builder.append(" AND PM.PAYMENT_METHOD_TYPE = ? ");
      params.add(condition.getPaymentMethod());
    }

    // 支払ステータス
    if (StringUtil.hasValue(condition.getPaymentStatus())) {
      builder.append(" AND OH.PAYMENT_STATUS = ? ");
      params.add(condition.getPaymentStatus());
    }
    // 支払期限条件設定
    setConditionPaymentLimit(condition, builder, params);
    // ショップコード
    if (condition.isSiteAdmin() || condition.isCustomer()) {
      Logger.getLogger(this.getClass()).debug("Query Mode = site admin");
    } else {
      Logger.getLogger(this.getClass()).debug("Query Mode = shop");
      builder.append(" AND OH.SHOP_CODE = ? ");
      params.add(condition.getShopCode());
    }

    setSqlString(builder.toString());
    setParameters(params.toArray());
    setPageNumber(condition.getCurrentPage());
    setPageSize(condition.getPageSize());
    setMaxFetchSize(condition.getMaxFetchSize());
  }

  private void setConditionPaymentLimit(OrderListSearchCondition condition, StringBuilder builder, List<Object> params) {
    // 支払期限切れ
    if (condition.isSearchPaymentLimitOver()) {
      builder.append(" AND OH.PAYMENT_LIMIT_DATE < SYSDATE");
    }
    // 支払期限間近
    if (StringUtil.hasValue(condition.getPaymentLimitDays())) {
      Date searchLimitDate = DateUtil.getSysdate();
      searchLimitDate = DateUtil.addDate(searchLimitDate, NumUtil.toLong(condition.getPaymentLimitDays()).intValue());
      builder.append(" AND OH.PAYMENT_LIMIT_DATE < ? ");
      params.add(searchLimitDate);
      // 入金督促メール送信済みを含む
      if (!condition.isSearchWithSentPaymentReminderMail()) {
        builder.append(" AND NOT EXISTS ( SELECT * FROM ORDER_MAIL_HISTORY OHM WHERE OHM.ORDER_NO = OH.ORDER_NO )");
      }
    }

  }

  public Class<OrderHeader> getRowType() {
    return OrderHeader.class;
  }
}
