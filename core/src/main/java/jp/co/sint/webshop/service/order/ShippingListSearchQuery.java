package jp.co.sint.webshop.service.order;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.co.sint.webshop.data.AbstractQuery;
import jp.co.sint.webshop.data.domain.DataTransportStatus;
import jp.co.sint.webshop.data.domain.FixedSalesStatus;
import jp.co.sint.webshop.data.domain.OrderStatus;
import jp.co.sint.webshop.data.domain.PaymentMethodType;
import jp.co.sint.webshop.data.domain.PointAmplificationRate;
import jp.co.sint.webshop.data.domain.ReturnItemType;
import jp.co.sint.webshop.data.domain.ShippingStatus;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.PointUtil;
import jp.co.sint.webshop.utility.SqlDialect;
import jp.co.sint.webshop.utility.SqlFragment;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.utility.SqlDialect.DateSearchAccuracy;

import org.apache.log4j.Logger;

public class ShippingListSearchQuery extends AbstractQuery<ShippingList> {

  public ShippingListSearchQuery() {

  }

  private static final long serialVersionUID = 1L;

  public static final String SORT_EXPORT = "export";

  public static final String SORT_ASC = "asc";

  public static final String SORT_DESC = "desc";

  // 出荷ステータス検索条件に存在しなかった時に使うデフォルト受注ステータス検索条件

  private String[] defaultShippingStatus = {
    "-1" // 1件も返さないように、存在しない"-1"をセット
  };

  // 20120118 ysy add start
  private static final String SELECT_CLAUSE = "SELECT * FROM EC_TMALL_ORDER_VIEW ET ";

  // 20120118 ysy add end

  private static final String EXPORT_CLAUSE = "SELECT  SH.SHIPPING_NO, " + "        SD.SHIPPING_DETAIL_NO, "
      + "        SD.SKU_CODE, " + "        OD.COMMODITY_CODE, " + "        OD.COMMODITY_NAME, " + "        STANDARD_DETAIL1_NAME, "
      + "        STANDARD_DETAIL2_NAME, " + "        SD.UNIT_PRICE, " + "        SD.DISCOUNT_PRICE, "
      + "        SD.DISCOUNT_AMOUNT, "
      + "        SD.RETAIL_PRICE, "
      // + " SD.RETAIL_TAX, "
      + "        SD.PURCHASING_AMOUNT, "
      + "        SD.GIFT_CODE, "
      + "        SD.GIFT_NAME, "
      + "        SD.GIFT_PRICE, "
      // delete by V10-CH 170 start
      // + " SD.GIFT_TAX_RATE, "
      // + " SD.GIFT_TAX, "
      // + " SD.GIFT_TAX_TYPE, "
      // delete by V10-CH 170 end
      + "        SH.ORDER_NO, " + "        OH.ORDER_DATETIME, " + "        SH.SHOP_CODE, " + "        SH.CUSTOMER_CODE, "
      + "        OH.LAST_NAME, "
      // delete by V10-CH 170 start
      // + " OH.FIRST_NAME, "
      // + " OH.LAST_NAME_KANA, "
      // + " OH.FIRST_NAME_KANA, "
      // delete by V10-CH 170 end
      + "        OH.PAYMENT_METHOD_TYPE, " + "        OH.PAYMENT_METHOD_NAME, " + "        OH.PAYMENT_COMMISSION, "
      // delete by V10-CH 170 start
      // + " OH.PAYMENT_COMMISSION_TAX_RATE, "
      // + " OH.PAYMENT_COMMISSION_TAX, "
      // + " OH.PAYMENT_COMMISSION_TAX_TYPE, "
      // delete by V10-CH 170 start
      + "        SH.ADDRESS_NO, " + "        SH.ADDRESS_LAST_NAME, "
      // delete by V10-CH 170 start
      // + " SH.ADDRESS_FIRST_NAME, "
      // + " SH.ADDRESS_LAST_NAME_KANA, "
      // + " SH.ADDRESS_FIRST_NAME_KANA, "
      // delete by V10-CH 170 end
      + "        SH.POSTAL_CODE, " + "        SH.PREFECTURE_CODE, "
      // add by V10-CH 170 start
      + "        SH.CITY_CODE, "
      // add by V10-CH 170 end
      + "        SH.ADDRESS1, " + "        SH.ADDRESS2, " + "        SH.ADDRESS3, "
      // delete by V10-CH 170 start
      // + " SH.ADDRESS4, "
      // delete by V10-CH 170 end
      + "        SH.PHONE_NUMBER, " + "        SH.MOBILE_NUMBER, " + "        SH.DELIVERY_REMARK, "
      // + " SH.ACQUIRED_POINT/" +
      // DIContainer.getWebshopConfig().getPointMultiple() + ", "

      // modify by V10-CH start
      + " trim(to_char(trunc(ACQUIRED_POINT , "
      + PointUtil.getAcquiredPointScale()
      + "), '"
      + PointAmplificationRate.fromValue(Integer.toString(PointUtil.getAcquiredPointScale())).getName()
      + "' "
      + ")) AS ACQUIRED_POINT,"
      // modify by V10-CH end

      + "        SH.DELIVERY_SLIP_NO, "
      + "        SH.SHIPPING_CHARGE, "
      // delete by V10-CH 170 start
      // + " SH.SHIPPING_CHARGE_TAX_TYPE, "
      // + " SH.SHIPPING_CHARGE_TAX_RATE, "
      // + " SH.SHIPPING_CHARGE_TAX, "
      // delete by V10-CH 170 end
      + "        SH.DELIVERY_TYPE_NO, "
      + "        SH.DELIVERY_TYPE_NAME, "
      + "        SH.DELIVERY_APPOINTED_DATE, "
      + "        SH.DELIVERY_APPOINTED_TIME_START, "
      + "        SH.DELIVERY_APPOINTED_TIME_END, "
      + "        SH.ARRIVAL_DATE, "
      + "        SH.ARRIVAL_TIME_START, "
      + "        SH.ARRIVAL_TIME_END, "
      + "        SH.SHIPPING_STATUS, "
      + "        CASE "
      + "          WHEN SH.SHIPPING_STATUS="
      + ShippingStatus.NOT_READY.getValue()
      + " THEN '"
      + ShippingStatus.NOT_READY.getName()
      + "'"
      + "          WHEN SH.SHIPPING_STATUS="
      + ShippingStatus.READY.getValue()
      + " THEN '"
      + ShippingStatus.READY.getName()
      + "'"
      + "          WHEN SH.SHIPPING_STATUS="
      + ShippingStatus.IN_PROCESSING.getValue()
      + " THEN '"
      + ShippingStatus.IN_PROCESSING.getName()
      + "'"
      + "          WHEN SH.SHIPPING_STATUS="
      + ShippingStatus.SHIPPED.getValue()
      + " THEN '"
      + ShippingStatus.SHIPPED.getName()
      + "'"
      + "          WHEN SH.SHIPPING_STATUS="
      + ShippingStatus.CANCELLED.getValue()
      + " THEN '"
      + ShippingStatus.CANCELLED.getName()
      + "'"
      + "        END SHIPPING_STATUS_NAME, "
      + "        SH.SHIPPING_DIRECT_DATE, "
      + "        SH.SHIPPING_DATE, "
      + "        SH.ORIGINAL_SHIPPING_NO, "
      + "        SH.RETURN_ITEM_DATE, "
      + "        SH.RETURN_ITEM_LOSS_MONEY, "
      + "        SH.RETURN_ITEM_TYPE, "
      + "        CASE "
      + " WHEN OH.PAYMENT_METHOD_TYPE='"
      + PaymentMethodType.CASH_ON_DELIVERY.getValue()
      + "'"
      + " THEN ("
      + "            SELECT SUM((DETAIL.RETAIL_PRICE + DETAIL.GIFT_PRICE) * DETAIL.PURCHASING_AMOUNT) "
      + "                   + SH.SHIPPING_CHARGE - trunc(OH.USED_POINT /  "
      + DIContainer.getWebshopConfig().getRmbPointRate()
      + ",2) FROM SHIPPING_DETAIL DETAIL "
      + "            WHERE DETAIL.SHIPPING_NO = SH.SHIPPING_NO "
      + " )"
      + "          ELSE null "
      + "        END ALL_PAYMENT_PRICE, "
      + "        CASE "
      + "          WHEN OH.PAYMENT_METHOD_TYPE='"
      + PaymentMethodType.CASH_ON_DELIVERY.getValue()
      + "'"
      + "          THEN OH.PAYMENT_COMMISSION "
      + "          ELSE null "
      + "        END CASH_ON_DELIVERY_COMMISSION, "
      + "        SH.ORM_ROWID, "
      + "        SH.CREATED_USER, "
      + "        SH.CREATED_DATETIME, "
      + "        SH.UPDATED_USER, "
      + "        SH.UPDATED_DATETIME "
      + " FROM SHIPPING_HEADER SH "
      + "        INNER JOIN SHIPPING_DETAIL SD ON SH.SHIPPING_NO = SD.SHIPPING_NO "
      + "        LEFT OUTER JOIN ORDER_HEADER OH ON SH.ORDER_NO = OH.ORDER_NO "
      + "        INNER JOIN ORDER_DETAIL OD ON OH.ORDER_NO = OD.ORDER_NO "
      + "             AND OD.SHOP_CODE = SD.SHOP_CODE AND OD.SKU_CODE = SD.SKU_CODE ";

  public ShippingListSearchQuery(ShippingListSearchCondition condition) {
    buildQuery(SELECT_CLAUSE, condition);
  }

  public ShippingListSearchQuery(ShippingListSearchCondition condition, boolean exportFlg) {
    if (exportFlg) {
      // buildQuery(EXPORT_CLAUSE, condition);
    } else {
      buildQuery(SELECT_CLAUSE, condition);
    }
  }

  private void buildQuery(String query, ShippingListSearchCondition condition) {
    Logger logger = Logger.getLogger(this.getClass());
    StringBuilder builder = new StringBuilder();
    List<Object> params = new ArrayList<Object>();
    // if search JD things.
    if(condition.isSearchOnlyJDOrder()) {
      builder
      .append("SELECT ET.*, (SELECT DELIVERY_SLIP_NO FROM JD_SHIPPING_REALITY_DETAIL WHERE SHIPPING_NO = ET.SHIPPING_NO LIMIT 1) AS DELIVERY_SLIP_NO1 FROM EC_TMALL_JD_ORDER_VIEW ET ");
      builder
          .append(" LEFT JOIN ( SELECT SHIPPING_NO, ARRAY_TO_STRING(ARRAY_AGG(DELIVERY_SLIP_NO),',') AS DELIVERY_SLIP_NO1 FROM JD_SHIPPING_REALITY_DETAIL GROUP BY SHIPPING_NO) SRD ");
      builder.append(" ON ET.SHIPPING_NO = SRD.SHIPPING_NO ");
      // if search EC things.
    } else if(condition.isSearchOnlyEcOrder()){
      builder
        .append("SELECT ET.*, (SELECT DELIVERY_SLIP_NO FROM SHIPPING_REALITY_DETAIL WHERE SHIPPING_NO = ET.SHIPPING_NO LIMIT 1) AS DELIVERY_SLIP_NO1 FROM EC_TMALL_JD_ORDER_VIEW ET ");
      builder
        .append(" LEFT JOIN ( SELECT SHIPPING_NO, ARRAY_TO_STRING(ARRAY_AGG(DELIVERY_SLIP_NO),',') AS DELIVERY_SLIP_NO1 FROM SHIPPING_REALITY_DETAIL GROUP BY SHIPPING_NO) SRD ");
      builder.append(" ON ET.SHIPPING_NO = SRD.SHIPPING_NO ");
    } else {
      builder
      .append("SELECT ET.*, (SELECT DELIVERY_SLIP_NO FROM TMALL_SHIPPING_REALITY_DETAIL WHERE SHIPPING_NO = ET.SHIPPING_NO LIMIT 1) AS DELIVERY_SLIP_NO1 FROM EC_TMALL_JD_ORDER_VIEW ET ");
    builder
      .append(" LEFT JOIN ( SELECT SHIPPING_NO, ARRAY_TO_STRING(ARRAY_AGG(DELIVERY_SLIP_NO),',') AS DELIVERY_SLIP_NO1 FROM TMALL_SHIPPING_REALITY_DETAIL GROUP BY SHIPPING_NO) SRD ");
    builder.append(" ON ET.SHIPPING_NO = SRD.SHIPPING_NO ");
    }
    // Add by V10-CH start
    // builder.append(query);
    if (condition.getScale() != 0) {
      params.add(NumUtil.parseLong(NumUtil.getScale(condition.getScale())));
      params.add(PointAmplificationRate.fromValue(String.valueOf(NumUtil.getScale(condition.getScale()))).getName());
    }
    // Add by V10-CH end
    // M17N 10361 修正 ここから
    // builder.append("WHERE 1 = 1 AND OH.ORDER_STATUS <> ? ");
    // params.add(OrderStatus.RESERVED.getValue());
    builder.append("WHERE 1 = 1 AND ET.ORDER_STATUS = ? ");
    params.add(OrderStatus.ORDERED.getValue());
    // M17N 10361 修正 ここまで
    // 出荷番号
    if (StringUtil.hasValue(condition.getSearchShippingNo())) {
      builder.append(" AND ET.SHIPPING_NO = ? ");
      params.add(condition.getSearchShippingNo());
    }
    // add by lc 2012-04-25 start
    // 订单检查状态
    if (StringUtil.hasValue(condition.getSearchOrderFlg())) {
      builder.append(" AND ET.ORDER_FLG = ? ");
      params.add(condition.getSearchOrderFlg());
    }
    // add by lc 2012-04-25 end
    // 受注番号
    if (StringUtil.hasValue(condition.getSearchOrderNo())) {
      builder.append(" AND ET.ORDER_NO = ? ");
      params.add(condition.getSearchOrderNo());
    }
    //add by lc 2012-04-09 start
    // 交易编号
    if (StringUtil.hasValue(condition.getSearchFromTmallTid())) {
      builder.append(" AND ET.TMALL_TID = ? ");
      params.add(condition.getSearchFromTmallTid());
    }
    //add by lc 2012-04-09 end
    // 顧客名

    if (StringUtil.hasValue(condition.getSearchCustomerName())) {
      List<String> searchWordList = StringUtil.getSearchWordStringList(condition.getSearchCustomerName());
      for (String s : searchWordList) {
        builder.append(" AND ");
        builder.append(SqlDialect.getDefault().getLikeClausePattern("CONCAT(ET.LAST_NAME, ET.FIRST_NAME)"));
        params.add("%" + SqlDialect.getDefault().escape(s) + "%");
      }
    }
    // 顧客名カナ

    if (StringUtil.hasValue(condition.getSearchCustomerNameKana())) {
      List<String> searchWordList = StringUtil.getSearchWordStringList(condition.getSearchCustomerNameKana());
      for (String s : searchWordList) {
        builder.append(" AND ");
        builder.append(SqlDialect.getDefault().getLikeClausePattern("CONCAT(ET.LAST_NAME_KANA, ET.FIRST_NAME_KANA)"));
        params.add("%" + SqlDialect.getDefault().escape(s) + "%");
      }
    }
    // 宛名
    if (StringUtil.hasValue(condition.getSearchAddressName())) {
      List<String> searchWordList = StringUtil.getSearchWordStringList(condition.getSearchAddressName());
      for (String s : searchWordList) {
        builder.append(" AND ");
        builder.append(SqlDialect.getDefault().getLikeClausePattern("CONCAT(ET.ADDRESS_LAST_NAME, ET.ADDRESS_FIRST_NAME)"));
        params.add("%" + SqlDialect.getDefault().escape(s) + "%");
      }
    }
    // 電話番号
    if (StringUtil.hasValue(condition.getSearchCustomerPhoneNumber())) {
      builder.append(" AND REPLACE(ET.PHONE_NUMBER,'-','') = ? ");
      params.add(condition.getSearchCustomerPhoneNumber());
    }
    // 電話番号
    if (StringUtil.hasValue(condition.getSearchCustomerMobileNumber())) {
      builder.append(" AND ET.MOBILE_NUMBER = ? ");
      params.add(condition.getSearchCustomerMobileNumber());
    }
    // Add by V10-CH start
    // 手机号码
    if (StringUtil.hasValue(condition.getSearchCustomerMobileNumber())) {
      builder.append("AND ET.MOBILE_NUMBER = ? ");
      params.add(condition.getSearchCustomerMobileNumber());
    }
    // Add by V10-CH end

    // ショップ
    if (StringUtil.hasValue(condition.getSearchShopCode())) {
      builder.append(" AND ET.SHOP_CODE = ? ");
      params.add(condition.getSearchShopCode());
    }
    // 配送種別

    if (StringUtil.hasValue(condition.getSearchShopCode()) && StringUtil.hasValue(condition.getSearchDeliveryCompanyNo())) {
      builder.append(" AND ET.DELIVERY_COMPANY_NO = ? ");
      params.add(condition.getSearchDeliveryCompanyNo());
    }
    // 宅配便伝票番号

    if (StringUtil.hasValue(condition.getSearchDeliverySlipNo1())) {
      builder.append(" AND ");
      builder.append(SqlDialect.getDefault().getLikeClausePattern("SRD.DELIVERY_SLIP_NO1 "));
      params.add("%" + SqlDialect.getDefault().escape(condition.getSearchDeliverySlipNo1()) + "%");
    }
    // 受注日

    if (StringUtil.hasValueAnyOf(condition.getSearchFromOrderDatetime(), condition.getSearchToOrderDatetime())) {
      SqlDialect dialect = SqlDialect.getDefault();
      builder.append(" AND ");
      SqlFragment fragment = dialect.createDateRangeClause("ET.ORDER_DATETIME", DateUtil.fromString(condition
          .getSearchFromOrderDatetime()), DateUtil.fromString(condition.getSearchToOrderDatetime()), DateSearchAccuracy.DATE);
      builder.append(fragment.getFragment());
      params.addAll(Arrays.asList(fragment.getParameters()));
    }
    // 出荷日

    if (StringUtil.hasValueAnyOf(condition.getSearchFromShippingDatetime(), condition.getSearchToShippingDatetime())) {
      SqlDialect dialect = SqlDialect.getDefault();
      builder.append(" AND ");
      SqlFragment fragment = dialect.createDateRangeClause("ET.SHIPPING_DATE", DateUtil.fromString(condition
          .getSearchFromShippingDatetime()), DateUtil.fromString(condition.getSearchToShippingDatetime()), DateSearchAccuracy.DATE);
      builder.append(fragment.getFragment());
      params.addAll(Arrays.asList(fragment.getParameters()));
    }
    // 出荷指示日

    // 20120118 ysy add start
    if (condition.isSearchOnlyEcOrder()) {
      builder.append(" AND EC_TMALL_FLG = '0' ");
    } else if (condition.isSearchOnlyTmallOrder()) {
      builder.append(" AND EC_TMALL_FLG = '1' ");
    } else if (condition.isSearchOnlyJDOrder()) {
      builder.append(" AND EC_TMALL_FLG = '2' ");
    }

    // 20120118 ysy add end

    createShippingDirectCondition(condition, builder, params);

    Object[] shippingSearchStatus;
    // if (condition.getSearchShippingStatus().size() > 0 &&
    // condition.getSearchShippingStatus().size() < 6) {
    String[] shippingSearchStatusArray = condition.getSearchShippingStatus().toArray(
        new String[condition.getSearchShippingStatus().size()]);
    if (StringUtil.hasValueAnyOf(shippingSearchStatusArray) && condition.getSearchShippingStatus().size() < 6) {
      shippingSearchStatus = condition.getSearchShippingStatus().toArray();
    } else {
      shippingSearchStatus = (Object[]) this.defaultShippingStatus;
    }
    SqlFragment f = SqlDialect.getDefault().createInClause("ET.SHIPPING_STATUS", shippingSearchStatus);
    builder.append(" AND " + f.getFragment());
    params.addAll(Arrays.asList(f.getParameters()));
    // 2012-01-31 yyq del start
    // データ連携済みデータは含まない
    // if (condition.isRemoveDataTransportFlg()) {
    // builder.append(" AND ET.DATA_TRANSPORT_STATUS = " +
    // DataTransportStatus.NOT_TRANSPORTED.getValue());
    // }
    // 2012-01-31 yyq del end
    // 売上確定データは含まない
    if (condition.isRemoveFixedSalesDataFlg()) {
      builder.append(" AND ET.FIXED_SALES_STATUS = " + FixedSalesStatus.NOT_FIXED.getValue());
    }
    // 返品データは含まない
    builder.append(" AND ET.RETURN_ITEM_TYPE <> ? ");
    params.add(ReturnItemType.RETURNED.getValue());
    // 並べ替え条件
    if (StringUtil.hasValue(condition.getSortItem())) {
      if (condition.getSortItem().equals(SORT_EXPORT)) {
        builder.append(" ORDER BY ET.SHIPPING_NO, SD.SHIPPING_DETAIL_NO ");
      } else if (condition.getSortItem().equals(SORT_ASC)) {
        builder.append(" ORDER BY ET.SHIPPING_NO ASC ");
      } else if (condition.getSortItem().equals(SORT_DESC)) {
        builder.append(" ORDER BY ET.SHIPPING_NO DESC ");
      }
    } else {
      builder.append(" ORDER BY ET.SHIPPING_NO DESC ");
    }
    setSqlString(builder.toString());
    setParameters(params.toArray());
    setPageNumber(condition.getCurrentPage());
    setPageSize(condition.getPageSize());
    setMaxFetchSize(condition.getMaxFetchSize());
    logger.debug(builder.toString());
  }

  private void createShippingDirectCondition(ShippingListSearchCondition condition, StringBuilder builder, List<Object> params) {
    if (condition.isSearchOnlyNotSetDirectDate()) {
      builder.append(" AND ET.SHIPPING_DIRECT_DATE IS NULL");
    } else {
      boolean searchShippingDirectDate = false;
      if (StringUtil.hasValueAnyOf(condition.getSearchFromShippingDirectDate(), condition.getSearchToShippingDirectDate())) {
        SqlDialect dialect = SqlDialect.getDefault();
        builder.append(" AND ");
        SqlFragment fragment = dialect.createDateRangeClause("ET.SHIPPING_DIRECT_DATE", DateUtil.fromString(condition
            .getSearchFromShippingDirectDate()), DateUtil.fromString(condition.getSearchToShippingDirectDate()),
            DateSearchAccuracy.DATE);
        builder.append(fragment.getFragment().replace(")", ""));
        params.addAll(Arrays.asList(fragment.getParameters()));
        searchShippingDirectDate = true;
      }
      if (condition.isSearchOnlySetDirectDate()) {
        // 出荷指示日が設定されているもののみの場合
        if (searchShippingDirectDate) {
          // 日付のレンジがあれば条件で日付があるので括弧を閉じるだけ
          builder.append(" )");
        } else {
          // 日付のレンジがなければ出荷指示日設定済みのみ
          builder.append(" AND ET.SHIPPING_DIRECT_DATE IS NOT NULL");
        }
      } else if (searchShippingDirectDate) {
        // 出荷指示日の有無を考えない場合は、条件に OR IS NULL を追加

        builder.append(" OR ET.SHIPPING_DIRECT_DATE IS NULL )");
      }
    }
  }

  public Class<ShippingList> getRowType() {
    return ShippingList.class;
  }

}
