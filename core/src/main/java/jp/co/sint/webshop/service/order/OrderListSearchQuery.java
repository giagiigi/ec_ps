package jp.co.sint.webshop.service.order;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import jp.co.sint.webshop.data.AbstractQuery;
import jp.co.sint.webshop.data.domain.CustomerCancelableFlg;
import jp.co.sint.webshop.data.domain.FixedSalesStatus;
import jp.co.sint.webshop.data.domain.MailType;
import jp.co.sint.webshop.data.domain.OrderFlg;
import jp.co.sint.webshop.data.domain.OrderStatus;
import jp.co.sint.webshop.data.domain.OrderType;
import jp.co.sint.webshop.data.domain.PaymentMethodType;
import jp.co.sint.webshop.data.domain.PaymentStatus;
import jp.co.sint.webshop.data.domain.PointAmplificationRate;
import jp.co.sint.webshop.data.domain.ReturnItemType;
import jp.co.sint.webshop.data.domain.ShippingStatusSummary;
import jp.co.sint.webshop.utility.ArrayUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.PointUtil;
import jp.co.sint.webshop.utility.SqlDialect;
import jp.co.sint.webshop.utility.SqlFragment;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.utility.SqlDialect.LikeClauseOption;

import org.apache.log4j.Logger;

public class OrderListSearchQuery extends AbstractQuery<OrderHeadline> {

  /**
   * uid
   */
  private static final long serialVersionUID = 1L;

  private String selectCloun = "";

  // 検索条件のステータスが未選択の場合に、固定で0件を返すための条件
  private String[] noSelectStatus = {
    "-1" // 1件も返さないように、存在しない"-1"をセット
  };

  public OrderListSearchQuery() {
    setSqlString(BASE_QUERY);
  }

  public OrderListSearchQuery(OrderListSearchCondition condition) {
    // soukai update 2012/01/14 ob start
    /*
     * if (condition.isCustomerCancelableFlg()) { buildQuery(BASE_QUERY +
     * CANCELABLE_QUERY, condition); } else { buildQuery(BASE_QUERY, condition);
     * }
     */
    if (condition.isTotalPriceFlg()) {
      if (StringUtil.hasValue(condition.getSearchFromTmallTid())) {
        buildQuery(TOTAL_PRICE_QUERY_TMALL, condition);
      } else if (StringUtil.hasValue(condition.getSearchFromJdDid())) {
        buildQuery(TOTAL_PRICE_QUERY_JD, condition);
      } else {
        if (StringUtil.hasValue(condition.getSearchOrderType()) && condition.getSearchOrderType().equals("1")) {
          buildQuery(TOTAL_PRICE_QUERY_TMALL, condition);
        } else if (StringUtil.hasValue(condition.getSearchOrderType()) && condition.getSearchOrderType().equals("3")) {
          buildQuery(TOTAL_PRICE_QUERY_JD, condition);
        } else {
          buildQuery(TOTAL_PRICE_QUERY, condition);
        }
      }

    } else {
      if (condition.isCustomerCancelableFlg()) {
        if (StringUtil.hasValue(condition.getSearchFromTmallTid())) {
          buildQuery(BASE_QUERY_TMALL + CANCELABLE_QUERY, condition);
        } else if (StringUtil.hasValue(condition.getSearchFromJdDid())) {
          buildQuery(BASE_QUERY_JD + CANCELABLE_QUERY, condition);
        } else {
          if (StringUtil.hasValue(condition.getSearchOrderType()) && condition.getSearchOrderType().equals("1")) {
            buildQuery(BASE_QUERY_TMALL + CANCELABLE_QUERY, condition);
          } else if (StringUtil.hasValue(condition.getSearchOrderType()) && condition.getSearchOrderType().equals("3")) {
            buildQuery(BASE_QUERY_JD + CANCELABLE_QUERY, condition);
          } else {
            buildQuery(BASE_QUERY + CANCELABLE_QUERY, condition);
          }
        }
        
      } else {
        if (StringUtil.hasValue(condition.getSearchFromTmallTid())) {
          buildQuery(BASE_QUERY_TMALL, condition);
        } else if (StringUtil.hasValue(condition.getSearchFromJdDid())) {
          buildQuery(BASE_QUERY_JD, condition);
        } else {
          if (StringUtil.hasValue(condition.getSearchOrderType()) && condition.getSearchOrderType().equals("1")) {
            buildQuery(BASE_QUERY_TMALL, condition);
          } else if (StringUtil.hasValue(condition.getSearchOrderType()) && condition.getSearchOrderType().equals("3")) {
            buildQuery(BASE_QUERY_JD, condition);
          } else {
            buildQuery(BASE_QUERY, condition);
          }
        }
        
      }
    }
    // soukai update 2012/01/14 ob end
  }

  // soukai add 2012/01/14 ob end
  public OrderListSearchQuery(OrderListSearchCondition condition, boolean exportFlg) {
    if (exportFlg) {

      if (StringUtil.hasValue(condition.getSearchFromTmallTid())) {
        selectCloun = " SELECT  " + "   OSV.ORDER_NO, " + "   CASE WHEN  OSV.ORDER_TYPE = 1 THEN 'TMALL' "
        + "        WHEN  OSV.ORDER_TYPE = 3 THEN 'JD' "
        + "        WHEN  OSV.ORDER_TYPE = 0 AND OSV.MOBILE_COMPUTER_TYPE = 1 THEN 'EC[PC]' "
        + "   ELSE  'EC[MOBILE]' END AS MOBILE_COMPUTER_TYPE , " + "   OSV.EC_CODE AS CUSTOMER_CODE, " + "   OSV.CUSTOMER_LE, "
        + "   OSV.USE_LANGUAGE, " + "   OSV.PAYMENT_METHOD, " + "   OSV.ADDRESSSF AS ADDRESS1, " + "   OSV.RETAIL_PRICE, "
        + "   OSV.SHIPPING_CHARGE, " + "   OSV.PAYMENT_COMMISSION," + "   OSV.DISCOUNT_PRICE, "  
        + "   OSV.TOTAL_AMOUNT + OSV.PAYMENT_COMMISSION - OSV.DISCOUNT_PRICE + GIFT_CARD_USE_PRICE AS TOTAL_AMOUNT, " + "   OSV.DISCOUNT_CODE, "
        + "   OC.CAMPAIGN_CODE ,OSV.USE_AGENT,OSV.ORDER_CLIENT_TYPE,CU.LANGUAGE_CODE, " 
        + "   OSV.GIFT_CARD_USE_PRICE ,  "
        + "    0.00 as OUTER_CARD_USE_PRICE ,  "
        + "    '' as addresssf2,  "
        + "    '' as addresssf3,  "
        + "    '' as addresssf4,  "
        + "    '' as address_last_name,  "
        + "    '' as shipping_mobile_number,    "
        + "    OSV.LAST_NAME,    "
        + "    OSV.EMAIL,     "
        + "    TSH.DELIVERY_COMPANY_NAME     "
        + "   FROM TMALL_ORDER_SUMMARY OSV "
        + "   INNER JOIN TMALL_SHIPPING_HEADER TSH ON OSV.ORDER_NO = TSH.ORDER_NO AND TSH.RETURN_ITEM_TYPE <> 1 "
        + "   LEFT OUTER JOIN CUSTOMER CU ON CU.CUSTOMER_CODE = OSV.CUSTOMER_CODE"
        + "   LEFT OUTER JOIN CUSTOMER_ADDRESS CA ON CA.CUSTOMER_CODE = OSV.CUSTOMER_CODE AND CA.ADDRESS_NO = 0"
        + "   LEFT JOIN NEW_COUPON_RULE NCR ON OSV.DISCOUNT_CODE = NCR.COUPON_CODE "
        + "   LEFT JOIN ORDER_CAMPAIGN OC ON OC.ORDER_NO = OSV.ORDER_NO ";
      } else if (StringUtil.hasValue(condition.getSearchFromJdDid())) {
        selectCloun = " SELECT  " + "   OSV.ORDER_NO, " + "   CASE WHEN  OSV.ORDER_TYPE = 1 THEN 'TMALL' "
        + "        WHEN  OSV.ORDER_TYPE = 3 THEN 'JD' "
        + "        WHEN  OSV.ORDER_TYPE = 0 AND OSV.MOBILE_COMPUTER_TYPE = 1 THEN 'EC[PC]' "
        + "   ELSE  'EC[MOBILE]' END AS MOBILE_COMPUTER_TYPE , " + "   OSV.EC_CODE AS CUSTOMER_CODE, " + "   OSV.CUSTOMER_LE, "
        + "   OSV.USE_LANGUAGE, " + "   OSV.PAYMENT_METHOD, " + "   OSV.ADDRESSSF AS ADDRESS1, " + "   OSV.RETAIL_PRICE, "
        + "   OSV.SHIPPING_CHARGE, " + "   OSV.PAYMENT_COMMISSION," + "   OSV.DISCOUNT_PRICE, "  
        + "   OSV.TOTAL_AMOUNT + OSV.PAYMENT_COMMISSION - OSV.DISCOUNT_PRICE + GIFT_CARD_USE_PRICE AS TOTAL_AMOUNT, " + "   OSV.DISCOUNT_CODE, "
        + "   OC.CAMPAIGN_CODE ,OSV.USE_AGENT,OSV.ORDER_CLIENT_TYPE,CU.LANGUAGE_CODE, " 
        + "   OSV.GIFT_CARD_USE_PRICE ,  "
        + "    0.00 as OUTER_CARD_USE_PRICE ,  "
        + "    '' as addresssf2,  "
        + "    '' as addresssf3,  "
        + "    '' as addresssf4,  "
        + "    '' as address_last_name,  "
        + "    '' as shipping_mobile_number,    "
        + "    OSV.LAST_NAME,    "
        + "    OSV.EMAIL,     "
        + "    JSH.DELIVERY_COMPANY_NAME     "
        + "   FROM JD_ORDER_SUMMARY OSV "
        + "   INNER JOIN JD_SHIPPING_HEADER JSH ON OSV.ORDER_NO = JSH.ORDER_NO AND JSH.RETURN_ITEM_TYPE <> 1 "
        + "   LEFT OUTER JOIN CUSTOMER CU ON CU.CUSTOMER_CODE = OSV.CUSTOMER_CODE"
        + "   LEFT OUTER JOIN CUSTOMER_ADDRESS CA ON CA.CUSTOMER_CODE = OSV.CUSTOMER_CODE AND CA.ADDRESS_NO = 0"
        + "   LEFT JOIN NEW_COUPON_RULE NCR ON OSV.DISCOUNT_CODE = NCR.COUPON_CODE "
        + "   LEFT JOIN ORDER_CAMPAIGN OC ON OC.ORDER_NO = OSV.ORDER_NO ";
      } else {
        if (StringUtil.hasValue(condition.getSearchOrderType()) && condition.getSearchOrderType().equals("1")) {
          selectCloun = " SELECT  " + "   OSV.ORDER_NO, " + "   CASE WHEN  OSV.ORDER_TYPE = 1 THEN 'TMALL' "
          + "        WHEN  OSV.ORDER_TYPE = 3 THEN 'JD' "
          + "        WHEN  OSV.ORDER_TYPE = 0 AND OSV.MOBILE_COMPUTER_TYPE = 1 THEN 'EC[PC]' "
          + "   ELSE  'EC[MOBILE]' END AS MOBILE_COMPUTER_TYPE , " + "   OSV.EC_CODE AS CUSTOMER_CODE, " + "   OSV.CUSTOMER_LE, "
          + "   OSV.USE_LANGUAGE, " + "   OSV.PAYMENT_METHOD, " + "   OSV.ADDRESSSF AS ADDRESS1, " + "   OSV.RETAIL_PRICE, "
          + "   OSV.SHIPPING_CHARGE, " + "   OSV.PAYMENT_COMMISSION," + "   OSV.DISCOUNT_PRICE, "  
          + "   OSV.TOTAL_AMOUNT + OSV.PAYMENT_COMMISSION - OSV.DISCOUNT_PRICE + GIFT_CARD_USE_PRICE AS TOTAL_AMOUNT, " + "   OSV.DISCOUNT_CODE, "
          + "   OC.CAMPAIGN_CODE ,OSV.USE_AGENT,OSV.ORDER_CLIENT_TYPE,CU.LANGUAGE_CODE, " 
          + "   OSV.GIFT_CARD_USE_PRICE ,  "
          + "    0.00 as OUTER_CARD_USE_PRICE ,  "
          + "    '' as addresssf2,  "
          + "    '' as addresssf3,  "
          + "    '' as addresssf4,  "
          + "    '' as address_last_name,  "
          + "    '' as shipping_mobile_number,    "
          + "    OSV.LAST_NAME,    "
          + "    OSV.EMAIL,     "
          + "    TSH.DELIVERY_COMPANY_NAME     "
          + "   FROM TMALL_ORDER_SUMMARY OSV "
          + "   INNER JOIN TMALL_SHIPPING_HEADER TSH ON OSV.ORDER_NO = TSH.ORDER_NO AND TSH.RETURN_ITEM_TYPE <> 1 "
          + "   LEFT OUTER JOIN CUSTOMER CU ON CU.CUSTOMER_CODE = OSV.CUSTOMER_CODE"
          + "   LEFT OUTER JOIN CUSTOMER_ADDRESS CA ON CA.CUSTOMER_CODE = OSV.CUSTOMER_CODE AND CA.ADDRESS_NO = 0"
          + "   LEFT JOIN NEW_COUPON_RULE NCR ON OSV.DISCOUNT_CODE = NCR.COUPON_CODE "
          + "   LEFT JOIN ORDER_CAMPAIGN OC ON OC.ORDER_NO = OSV.ORDER_NO ";
        } else if (StringUtil.hasValue(condition.getSearchOrderType()) && condition.getSearchOrderType().equals("3")) {
          selectCloun = " SELECT  " + "   OSV.ORDER_NO, " + "   CASE WHEN  OSV.ORDER_TYPE = 1 THEN 'TMALL' "
          + "        WHEN  OSV.ORDER_TYPE = 3 THEN 'JD' "
          + "        WHEN  OSV.ORDER_TYPE = 0 AND OSV.MOBILE_COMPUTER_TYPE = 1 THEN 'EC[PC]' "
          + "   ELSE  'EC[MOBILE]' END AS MOBILE_COMPUTER_TYPE , " + "   OSV.EC_CODE AS CUSTOMER_CODE, " + "   OSV.CUSTOMER_LE, "
          + "   OSV.USE_LANGUAGE, " + "   OSV.PAYMENT_METHOD, " + "   OSV.ADDRESSSF AS ADDRESS1, " + "   OSV.RETAIL_PRICE, "
          + "   OSV.SHIPPING_CHARGE, " + "   OSV.PAYMENT_COMMISSION," + "   OSV.DISCOUNT_PRICE, "  
          + "   OSV.TOTAL_AMOUNT + OSV.PAYMENT_COMMISSION - OSV.DISCOUNT_PRICE + GIFT_CARD_USE_PRICE AS TOTAL_AMOUNT, " + "   OSV.DISCOUNT_CODE, "
          + "   OC.CAMPAIGN_CODE ,OSV.USE_AGENT,OSV.ORDER_CLIENT_TYPE,CU.LANGUAGE_CODE, " 
          + "   OSV.GIFT_CARD_USE_PRICE ,  "
          + "    0.00 as OUTER_CARD_USE_PRICE ,  "
          + "    '' as addresssf2,  "
          + "    '' as addresssf3,  "
          + "    '' as addresssf4,  "
          + "    '' as address_last_name,  "
          + "    '' as shipping_mobile_number,    "
          + "    OSV.LAST_NAME,    "
          + "    OSV.EMAIL,     "
          + "    JSH.DELIVERY_COMPANY_NAME     "
          + "   FROM JD_ORDER_SUMMARY OSV "
          + "   INNER JOIN JD_SHIPPING_HEADER JSH ON OSV.ORDER_NO = JSH.ORDER_NO AND JSH.RETURN_ITEM_TYPE <> 1 " 
          + "   LEFT OUTER JOIN CUSTOMER CU ON CU.CUSTOMER_CODE = OSV.CUSTOMER_CODE"
          + "   LEFT OUTER JOIN CUSTOMER_ADDRESS CA ON CA.CUSTOMER_CODE = OSV.CUSTOMER_CODE AND CA.ADDRESS_NO = 0"
          + "   LEFT JOIN NEW_COUPON_RULE NCR ON OSV.DISCOUNT_CODE = NCR.COUPON_CODE "
          + "   LEFT JOIN ORDER_CAMPAIGN OC ON OC.ORDER_NO = OSV.ORDER_NO ";
        } else {
          selectCloun = " SELECT  " + "   OSV.ORDER_NO, " + "   CASE WHEN  OSV.ORDER_TYPE = 1 THEN 'TMALL' "
          + "        WHEN  OSV.ORDER_TYPE = 3 THEN 'JD' "
          + "        WHEN  OSV.ORDER_TYPE = 0 AND OSV.MOBILE_COMPUTER_TYPE = 1 THEN 'EC[PC]' "
          + "   ELSE  'EC[MOBILE]' END AS MOBILE_COMPUTER_TYPE , " + "   OSV.EC_CODE AS CUSTOMER_CODE, " + "   OSV.CUSTOMER_LE, "
          + "   OSV.USE_LANGUAGE, " + "   OSV.PAYMENT_METHOD, " + "   OSV.ADDRESSSF AS ADDRESS1, " + "   OSV.RETAIL_PRICE,"
          + "   OSV.SHIPPING_CHARGE, " + "   OSV.PAYMENT_COMMISSION," + "   OSV.DISCOUNT_PRICE,"  
          + "   OSV.TOTAL_AMOUNT + OSV.PAYMENT_COMMISSION - OSV.DISCOUNT_PRICE + OSV.GIFT_CARD_USE_PRICE + OSV.OUTER_CARD_USE_PRICE AS TOTAL_AMOUNT, " + "   OSV.DISCOUNT_CODE, "
          + "   OC.CAMPAIGN_CODE ,OSV.USE_AGENT,OSV.ORDER_CLIENT_TYPE,CU.LANGUAGE_CODE , OSV.GIFT_CARD_USE_PRICE , OSV.OUTER_CARD_USE_PRICE,OSV.addresssf2,OSV.addresssf3,OSV.addresssf4,osv.address_last_name,osv.shipping_mobile_number, "
          // 20140424 why add start
          + "   OSV.LAST_NAME, " + "   OSV.EMAIL,OSV.DELIVERY_COMPANY_NAME "
          // 20140424 why add end
          + "   FROM ORDER_SUMMARY_LANGUAGE_VIEW6 OSV "
          + "   LEFT OUTER JOIN CUSTOMER CU ON CU.CUSTOMER_CODE = OSV.CUSTOMER_CODE"
          + "   LEFT OUTER JOIN CUSTOMER_ADDRESS CA ON CA.CUSTOMER_CODE = OSV.CUSTOMER_CODE AND CA.ADDRESS_NO = 0"
          + "   LEFT JOIN NEW_COUPON_RULE NCR ON OSV.DISCOUNT_CODE = NCR.COUPON_CODE "
          + "   LEFT JOIN ORDER_CAMPAIGN OC ON OC.ORDER_NO = OSV.ORDER_NO ";
        }
      }

      buildQuery(selectCloun, condition);

    } else {
      if (StringUtil.hasValue(condition.getSearchFromTmallTid())) {
        buildQuery(BASE_QUERY_TMALL, condition);
      } else if (StringUtil.hasValue(condition.getSearchFromJdDid())) {
        buildQuery(BASE_QUERY_JD, condition);
      } else {
        if (StringUtil.hasValue(condition.getSearchOrderType()) && condition.getSearchOrderType().equals("1")) {
          buildQuery(BASE_QUERY_TMALL, condition);
        } else if (StringUtil.hasValue(condition.getSearchOrderType()) && condition.getSearchOrderType().equals("3")) {
          buildQuery(BASE_QUERY_JD, condition);
        } else {
          buildQuery(BASE_QUERY, condition);
        }
      }
    }
  }

  private static final String BASE_QUERY = "SELECT OSV.MOBILE_COMPUTER_TYPE,OSV.GIFT_CARD_USE_PRICE,OSV.ORDER_TYPE, OSV.ORDER_FLG, OSV.LANGUAGE_CODE,OSV.ORDER_NO, OSV.SHOP_CODE, OSV.PAYMENT_METHOD_NO, OSV.ORDER_STATUS, "
      // 20120120 ysy add start
      + "   OSV.DISCOUNT_PRICE, "
      // 20120120 ysy add end
      + "   OSV.LAST_NAME CUSTOMER_NAME,OSV.ADDRESS_LAST_NAME, OSV.GUEST_FLG, OSV.PHONE_NUMBER PHONE_NUMBER, OSV.MOBILE_NUMBER MOBILE_NUMBER, "
      + "   OSV.CAUTION, OSV.MESSAGE,  "
      + "   TO_CHAR(OSV.ORDER_DATETIME, 'YYYY/MM/DD HH24:MI:SS') ORDER_DATETIME,  "
      + "   (OSV.TOTAL_AMOUNT + OSV.GIFT_CARD_USE_PRICE + OSV.OUTER_CARD_USE_PRICE) AS TOTAL_AMOUNT,  "
      + "   TO_CHAR(OSV.PAYMENT_DATE, 'YYYY/MM/DD') PAYMENT_DATE,  "
      + "   OSV.PAYMENT_METHOD_NAME PAYMENT_METHOD_NAME,  "
      + "   OSV.PAYMENT_COMMISSION, "
      // 20120129 wjh add start
      + "   OSV.BUYER_MESSAGE, "
      // 20120129 wjh add end
      + "   OSV.SHIPPING_STATUS_SUMMARY, OSV.RETURN_STATUS_SUMMARY, OSV.PAYMENT_LIMIT_DATE, OSV.UPDATED_DATETIME,"
      + "   OSV.USED_POINT FROM order_summary_language_view5 OSV "
      + "   LEFT OUTER JOIN CUSTOMER CU ON CU.CUSTOMER_CODE = OSV.CUSTOMER_CODE  "
      + "LEFT OUTER JOIN CUSTOMER_ADDRESS CA "
      + "   ON CA.CUSTOMER_CODE = OSV.CUSTOMER_CODE AND CA.ADDRESS_NO = 0  ";
  
  private static final String BASE_QUERY_TMALL = "SELECT OSV.MOBILE_COMPUTER_TYPE,OSV.GIFT_CARD_USE_PRICE,OSV.ORDER_TYPE, OSV.ORDER_FLG, OSV.LANGUAGE_CODE,OSV.ORDER_NO, OSV.SHOP_CODE, OSV.PAYMENT_METHOD_NO, OSV.ORDER_STATUS, "
    // 20120120 ysy add start
    + "   OSV.DISCOUNT_PRICE, "
    // 20120120 ysy add end
    + "   OSV.LAST_NAME CUSTOMER_NAME, OSV.GUEST_FLG, OSV.PHONE_NUMBER PHONE_NUMBER, OSV.MOBILE_NUMBER MOBILE_NUMBER, "
    + "   OSV.CAUTION, OSV.MESSAGE,  "
    + "   TO_CHAR(OSV.ORDER_DATETIME, 'YYYY/MM/DD HH24:MI:SS') ORDER_DATETIME,  "
    + "   (OSV.TOTAL_AMOUNT + OSV.GIFT_CARD_USE_PRICE) as TOTAL_AMOUNT,  "
    + "   TO_CHAR(OSV.PAYMENT_DATE, 'YYYY/MM/DD') PAYMENT_DATE,  "
    + "   OSV.PAYMENT_METHOD_NAME PAYMENT_METHOD_NAME,  "
    + "   OSV.PAYMENT_COMMISSION, "
    // 20120129 wjh add start
    + "   OSV.TMALL_BUYER_MESSAGE, "
    // 20120129 wjh add end
    + "   OSV.SHIPPING_STATUS_SUMMARY, OSV.RETURN_STATUS_SUMMARY, OSV.PAYMENT_LIMIT_DATE, OSV.UPDATED_DATETIME,"
    + "   OSV.USED_POINT FROM TMALL_ORDER_SUMMARY OSV "
    + "   LEFT OUTER JOIN CUSTOMER CU ON CU.CUSTOMER_CODE = OSV.CUSTOMER_CODE  "
    + "LEFT OUTER JOIN CUSTOMER_ADDRESS CA "
    + "   ON CA.CUSTOMER_CODE = OSV.CUSTOMER_CODE AND CA.ADDRESS_NO = 0  ";
  
  private static final String BASE_QUERY_JD = "SELECT OSV.MOBILE_COMPUTER_TYPE,OSV.GIFT_CARD_USE_PRICE,OSV.ORDER_TYPE, OSV.ORDER_FLG, OSV.LANGUAGE_CODE,OSV.ORDER_NO, OSV.SHOP_CODE, OSV.PAYMENT_METHOD_NO, OSV.ORDER_STATUS, "
    // 20120120 ysy add start
    + "   OSV.DISCOUNT_PRICE, "
    // 20120120 ysy add end
    + "   OSV.LAST_NAME CUSTOMER_NAME, OSV.GUEST_FLG, OSV.PHONE_NUMBER PHONE_NUMBER, OSV.MOBILE_NUMBER MOBILE_NUMBER, "
    + "   OSV.CAUTION, OSV.MESSAGE,  "
    + "   TO_CHAR(OSV.ORDER_DATETIME, 'YYYY/MM/DD HH24:MI:SS') ORDER_DATETIME,  "
    + "   (OSV.TOTAL_AMOUNT + OSV.GIFT_CARD_USE_PRICE) as TOTAL_AMOUNT,  "
    + "   TO_CHAR(OSV.PAYMENT_DATE, 'YYYY/MM/DD') PAYMENT_DATE,  "
    + "   OSV.PAYMENT_METHOD_NAME PAYMENT_METHOD_NAME,  "
    + "   OSV.PAYMENT_COMMISSION, "
    // 20120129 wjh add start
    + "   OSV.JD_BUYER_MESSAGE, "
    // 20120129 wjh add end
    + "   OSV.SHIPPING_STATUS_SUMMARY, OSV.RETURN_STATUS_SUMMARY, OSV.PAYMENT_LIMIT_DATE, OSV.UPDATED_DATETIME,"
    + "   OSV.USED_POINT FROM JD_ORDER_SUMMARY OSV "
    + "   LEFT OUTER JOIN CUSTOMER CU ON CU.CUSTOMER_CODE = OSV.CUSTOMER_CODE  "
    + "LEFT OUTER JOIN CUSTOMER_ADDRESS CA "
    + "   ON CA.CUSTOMER_CODE = OSV.CUSTOMER_CODE AND CA.ADDRESS_NO = 0  ";

  // soukai add 2012/01/14 ob start
  private static final String TOTAL_PRICE_QUERY = "select sum (osv.total_amount +osv.payment_commission-osv.discount_price +  OSV.GIFT_CARD_USE_PRICE )"
      + "   from order_summary_language_view4 osv " + "   left outer join customer cu on cu.customer_code = osv.customer_code  "
      + "left outer join customer_address ca " + "   on ca.customer_code = osv.customer_code and ca.address_no = 0  ";
  
  private static final String TOTAL_PRICE_QUERY_TMALL = "select sum (osv.total_amount +osv.payment_commission-osv.discount_price +  OSV.GIFT_CARD_USE_PRICE )"
    + "   from TMALL_ORDER_SUMMARY osv " + "   left outer join customer cu on cu.customer_code = osv.customer_code  "
    + "left outer join customer_address ca " + "   on ca.customer_code = osv.customer_code and ca.address_no = 0  ";
  
  private static final String TOTAL_PRICE_QUERY_JD = "select sum (osv.total_amount +osv.payment_commission-osv.discount_price +  OSV.GIFT_CARD_USE_PRICE )"
    + "   from JD_ORDER_SUMMARY osv " + "   left outer join customer cu on cu.customer_code = osv.customer_code  "
    + "left outer join customer_address ca " + "   on ca.customer_code = osv.customer_code and ca.address_no = 0  ";

  // soukai add 2012/01/14 ob end
  private String EXPORT_QUERY = "SELECT  OH.ORDER_NO, OH.SHOP_CODE, OD.SKU_CODE, OD.COMMODITY_CODE, "
      + "OD.COMMODITY_NAME, OD.STANDARD_DETAIL1_NAME, OD.STANDARD_DETAIL2_NAME, OD.PURCHASING_AMOUNT, OD.UNIT_PRICE, "
      // modify by V10-CH 170 start
      // +
      // "OD.RETAIL_PRICE, OD.RETAIL_TAX, OD.COMMODITY_TAX_RATE, OD.COMMODITY_TAX, OD.COMMODITY_TAX_TYPE, OD.CAMPAIGN_CODE, "
      + "OD.RETAIL_PRICE, OD.CAMPAIGN_CODE, "
      // modify by V10-CH 170 end
      + "OD.CAMPAIGN_NAME, OD.CAMPAIGN_DISCOUNT_RATE, OD.APPLIED_POINT_RATE, OH.ORDER_DATETIME, OH.CUSTOMER_CODE, OH.GUEST_FLG, "
      // modify by V10-CH 170 start
      // +
      // "OH.LAST_NAME, OH.FIRST_NAME, OH.LAST_NAME_KANA, OH.FIRST_NAME_KANA, OH.EMAIL, OH.POSTAL_CODE, OH.PREFECTURE_CODE, "
      // +
      // "OH.ADDRESS1, OH.ADDRESS2, OH.ADDRESS3, OH.ADDRESS4, OH.PHONE_NUMBER, OH.ADVANCE_LATER_FLG, OH.PAYMENT_METHOD_NO, "
      + "OH.LAST_NAME, OH.EMAIL, OH.POSTAL_CODE, OH.PREFECTURE_CODE, OH.CITY_CODE,"
      + "OH.ADDRESS1, OH.ADDRESS2, OH.ADDRESS3, OH.PHONE_NUMBER, OH.MOBILE_NUMBER, OH.ADVANCE_LATER_FLG, OH.PAYMENT_METHOD_NO, "
      // modify by V10-CH 170 end
      // modify by V10-CH 170 start
      // +
      // "OH.PAYMENT_METHOD_TYPE, OH.PAYMENT_METHOD_NAME, OH.PAYMENT_COMMISSION, OH.PAYMENT_COMMISSION_TAX_RATE, "
      // +
      // "OH.PAYMENT_COMMISSION_TAX, OH.PAYMENT_COMMISSION_TAX_TYPE, OH.USED_POINT, OH.PAYMENT_DATE, OH.PAYMENT_LIMIT_DATE, "
      + "OH.PAYMENT_METHOD_TYPE, OH.PAYMENT_METHOD_NAME, OH.PAYMENT_COMMISSION, "
      // + "OH.USED_POINT, "
      + "trim(to_char(trunc(OH.USED_POINT, "
      + PointUtil.getAcquiredPointScale()
      + "),'"
      + PointAmplificationRate.fromValue(Integer.toString(PointUtil.getAcquiredPointScale())).getName()
      + "'))AS USED_POINT,"
      + "OH.COUPON_PRICE, OH.PAYMENT_DATE, OH.PAYMENT_LIMIT_DATE, "
      // modify by V10-CH 170 end
      + "OH.PAYMENT_STATUS, OH.CUSTOMER_GROUP_CODE, OH.DATA_TRANSPORT_STATUS, OH.ORDER_STATUS, OH.CLIENT_GROUP, "
      // modify by V10-CH 170 start
      // +
      // "OH.CAUTION, OH.MESSAGE, OH.PAYMENT_ORDER_ID, OH.CVS_CODE, OH.PAYMENT_RECEIPT_NO, OH.PAYMENT_RECEIPT_URL, "
      // +
      // "OH.DIGITAL_CASH_TYPE, OH.WARNING_MESSAGE, OH.ORM_ROWID, OH.CREATED_USER, OH.CREATED_DATETIME, OH.UPDATED_USER, "
      + "OH.CAUTION, OH.MESSAGE, OH.PAYMENT_ORDER_ID, "
      + "OH.WARNING_MESSAGE, "
      // soukai add 2012/01/05 ob start
      + "  OH.invoice_flg, "
      + "  OH.discount_type,"
      + "  OH.discount_mode,"
      + "  OH.discount_rate,"
      + "  OH.discount_price,"
      + "  OH.discount_code, "
      + "  OH.discount_name,"
      + "  OH.discount_detail_code,"
      + "  OH.order_flg,"
      + "  OD.commodity_weight, "
      + "  OD.sale_plan_code, "
      + "  OD.sale_plan_name, "
      + "  OD.featured_plan_code, "
      + "  OD.featured_plan_name, " + "  OD.brand_code, " + "  OD.brand_name, ";

  // soukai add 2012/01/05 ob end

  private String EXPORT_QUERY_2 = " OH.ORM_ROWID, OH.CREATED_USER, OH.CREATED_DATETIME, OH.UPDATED_USER, ";

  // modify by V10-CH 170 end

  // soukai edit 2012/01/05 ob start
  // +
  // "OH.UPDATED_DATETIME FROM ORDER_HEADER OH INNER JOIN ORDER_DETAIL OD ON OH.ORDER_NO = OD.ORDER_NO "

  // soukai edit 2012/01/05 ob end

  private String EXPORT_QUERY_3 = "" + "LEFT OUTER JOIN order_summary_language_view4 OSV ON OH.ORDER_NO = OSV.ORDER_NO "
      + "LEFT OUTER JOIN CUSTOMER CU ON CU.CUSTOMER_CODE = OH.CUSTOMER_CODE  LEFT OUTER JOIN CUSTOMER_ADDRESS CA "
      + "ON CA.CUSTOMER_CODE = OH.CUSTOMER_CODE AND CA.ADDRESS_NO = 0  ";

  private static final String CANCELABLE_QUERY = "INNER JOIN SHOP SP ON SP.SHOP_CODE = OSV.SHOP_CODE "
      + "AND SP.CUSTOMER_CANCELABLE_FLG = " + CustomerCancelableFlg.ENABLED.getValue();

  private static final String TMALL_WEIGHT_QUERY = "INNER JOIN ("
      + " SELECT ORDER_NO FROM ("
      + " SELECT OSV2.ORDER_NO, SUM(OD.PURCHASING_AMOUNT * CD.WEIGHT) AS TOTAL_WEIGHT" // 根据订单编号，计算订单重量
      + " FROM order_summary_language_view4 OSV2" + " LEFT OUTER JOIN TMALL_ORDER_DETAIL OD ON OSV2.ORDER_NO = OD.ORDER_NO"
      + " LEFT OUTER JOIN C_COMMODITY_DETAIL CD ON OD.COMMODITY_CODE = CD.COMMODITY_CODE" + " WHERE OSV2.ORDER_NO LIKE 'T%'"
      + " GROUP BY OSV2.ORDER_NO" + " ) T WHERE T.TOTAL_WEIGHT > 1"
      + " ) TV ON OSV.ORDER_NO = TV.ORDER_NO AND OSV.SHIPPING_CHARGE=0 AND OSV.RETAIL_PRICE > 200";
  
  private static final String JD_WEIGHT_QUERY = "INNER JOIN ("
    + " SELECT ORDER_NO FROM ("
    + " SELECT OSV2.ORDER_NO, SUM(OD.PURCHASING_AMOUNT * CD.WEIGHT) AS TOTAL_WEIGHT" // 根据订单编号，计算订单重量
    + " FROM order_summary_language_view4 OSV2" + " LEFT OUTER JOIN JD_ORDER_DETAIL OD ON OSV2.ORDER_NO = OD.ORDER_NO"
    + " LEFT OUTER JOIN C_COMMODITY_DETAIL CD ON OD.COMMODITY_CODE = CD.COMMODITY_CODE" + " WHERE OSV2.ORDER_NO LIKE 'T%'"
    + " GROUP BY OSV2.ORDER_NO" + " ) T WHERE T.TOTAL_WEIGHT > 1"
    + " ) TV ON OSV.ORDER_NO = TV.ORDER_NO AND OSV.SHIPPING_CHARGE=0 AND OSV.RETAIL_PRICE > 200";
  
  private static final String TMALL_WEIGHT_QUERY_TMALL = "INNER JOIN ("
    + " SELECT ORDER_NO FROM ("
    + " SELECT OSV2.ORDER_NO, SUM(OD.PURCHASING_AMOUNT * CD.WEIGHT) AS TOTAL_WEIGHT" // 根据订单编号，计算订单重量
    + " FROM order_summary_language_view4 OSV2" + " LEFT OUTER JOIN TMALL_ORDER_DETAIL OD ON OSV2.ORDER_NO = OD.ORDER_NO"
    + " LEFT OUTER JOIN C_COMMODITY_DETAIL CD ON OD.COMMODITY_CODE = CD.COMMODITY_CODE" + " WHERE OSV2.ORDER_NO LIKE 'T%'"
    + " GROUP BY OSV2.ORDER_NO" + " ) T WHERE T.TOTAL_WEIGHT > 1"
    + " ) TV ON OSV.ORDER_NO = TV.ORDER_NO AND OSV.SHIPPING_CHARGE=0 AND OSV.RETAIL_PRICE > 200";
  
  private static final String JD_WEIGHT_QUERY_JD = "INNER JOIN ("
    + " SELECT ORDER_NO FROM ("
    + " SELECT OSV2.ORDER_NO, SUM(OD.PURCHASING_AMOUNT * CD.WEIGHT) AS TOTAL_WEIGHT" // 根据订单编号，计算订单重量
    + " FROM order_summary_language_view4 OSV2" + " LEFT OUTER JOIN JD_ORDER_DETAIL OD ON OSV2.ORDER_NO = OD.ORDER_NO"
    + " LEFT OUTER JOIN C_COMMODITY_DETAIL CD ON OD.COMMODITY_CODE = CD.COMMODITY_CODE" + " WHERE OSV2.ORDER_NO LIKE 'T%'"
    + " GROUP BY OSV2.ORDER_NO" + " ) T WHERE T.TOTAL_WEIGHT > 1"
    + " ) TV ON OSV.ORDER_NO = TV.ORDER_NO AND OSV.SHIPPING_CHARGE=0 AND OSV.RETAIL_PRICE > 200";

  private void buildQuery(String query, OrderListSearchCondition condition) {
    StringBuilder builder = new StringBuilder(query);
    
    if (StringUtil.hasValue(condition.getSearchFromTmallTid())) {
      if (!StringUtil.isNullOrEmpty(condition.getContentFlg())) {
        if (condition.getContentFlg().equals("3")) {
          builder.append(TMALL_WEIGHT_QUERY_TMALL);
        }
      }
    } else if (StringUtil.hasValue(condition.getSearchFromJdDid())) {
      if (!StringUtil.isNullOrEmpty(condition.getContentFlg())) {
        if (condition.getContentFlg().equals("3")) {
          builder.append(JD_WEIGHT_QUERY_JD);
        }
      }
    } else {
      if (StringUtil.hasValue(condition.getSearchOrderType()) && condition.getSearchOrderType().equals("1")) {
        if (!StringUtil.isNullOrEmpty(condition.getContentFlg())) {
          if (condition.getContentFlg().equals("3")) {
            builder.append(TMALL_WEIGHT_QUERY_TMALL);
          }
        }
      } else if (StringUtil.hasValue(condition.getSearchOrderType()) && condition.getSearchOrderType().equals("3")) {
        if (!StringUtil.isNullOrEmpty(condition.getContentFlg())) {
          if (condition.getContentFlg().equals("3")) {
            builder.append(JD_WEIGHT_QUERY_JD);
          }
        }
      } else {
        if (!StringUtil.isNullOrEmpty(condition.getContentFlg())) {
          if (condition.getContentFlg().equals("3")) {
            builder.append(TMALL_WEIGHT_QUERY);
          }
        }
      }
    }

    builder.append(" WHERE 1 = 1 ");
    List<Object> params = new ArrayList<Object>();
    // 検索条件:顧客名
    if (StringUtil.hasValue(condition.getCustomerName())) {
      SqlDialect dialect = SqlDialect.getDefault();
      SqlFragment fragment = dialect.createLikeClause("OSV.LAST_NAME", condition.getCustomerName(), LikeClauseOption.PARTIAL_MATCH);
      builder.append(" AND " + fragment.getFragment());
      params.addAll(Arrays.asList(fragment.getParameters()));
    }
    // 検索条件:顧客名カナ
    if (StringUtil.hasValue(condition.getCustomerNameKana())) {
      SqlDialect dialect = SqlDialect.getDefault();
      SqlFragment fragment = dialect.createLikeClause("CONCAT(OSV.LAST_NAME_KANA, OSV.FIRST_NAME_KANA)", condition
          .getCustomerNameKana(), LikeClauseOption.PARTIAL_MATCH);
      builder.append(" AND " + fragment.getFragment());
      params.addAll(Arrays.asList(fragment.getParameters()));
    }
    // 検索条件:顧客コード
    if (StringUtil.hasValue(condition.getCustomerCode())) {
      builder.append(" AND OSV.CUSTOMER_CODE = ? ");
      params.add(condition.getCustomerCode());
    }
    // 検索条件:電話番号
    if (StringUtil.hasValue(condition.getTel())) {
      SqlDialect dialect = SqlDialect.getDefault();
      builder.append(" AND (REPLACE(CA.PHONE_NUMBER, '-','') = " + dialect.quote(condition.getTel())
          + " OR (REPLACE(OSV.PHONE_NUMBER, '-','') = " + dialect.quote(condition.getTel()) + " AND CA.PHONE_NUMBER IS NULL))");
    }
    // 検索条件:携帯番号
    if (StringUtil.hasValue(condition.getMobileTel())) {
      builder.append(" AND (CA.MOBILE_NUMBER = ? OR OSV.MOBILE_NUMBER = ?) ");
      params.add(condition.getMobileTel());
      params.add(condition.getMobileTel());
    }
    // 検索条件:メール
    if (StringUtil.hasValue(condition.getEmail())) {
      SqlDialect dialect = SqlDialect.getDefault();
      SqlFragment fragment = dialect.createLikeClause("CU.EMAIL", condition.getEmail(), LikeClauseOption.PARTIAL_MATCH);
      SqlFragment guestFragment = dialect.createLikeClause("OSV.EMAIL", condition.getEmail(), LikeClauseOption.PARTIAL_MATCH);
      builder.append(" AND (" + fragment.getFragment() + " OR ( CU.EMAIL IS NULL AND " + guestFragment.getFragment() + " ))");
      params.addAll(Arrays.asList(fragment.getParameters()));
      params.addAll(Arrays.asList(guestFragment.getParameters()));
    }
    // 検索条件:受注日
    if (StringUtil.hasValueAnyOf(condition.getOrderDatetimeStart(), condition.getOrderDatetimeEnd())) {
      SqlDialect dialect = SqlDialect.getDefault();
      SqlFragment fragment = dialect.createRangeClause("OSV.ORDER_DATETIME", DateUtil.fromString(condition.getOrderDatetimeStart()
          + " 00:00:00", true), DateUtil.fromString(condition.getOrderDatetimeEnd() + " 23:59:59", true));
      builder.append(" AND ");
      builder.append(fragment.getFragment());
      params.addAll(Arrays.asList(fragment.getParameters()));
    }
    // 検索条件:入金日
    if (StringUtil.hasValueAnyOf(condition.getPaymentDatetimeStart(), condition.getPaymentDatetimeEnd())) {
      SqlDialect dialect = SqlDialect.getDefault();
      SqlFragment fragment = dialect.createRangeClause("(OSV.PAYMENT_DATE", DateUtil.fromString(condition.getPaymentDatetimeStart()
          + " 00:00:00", true), DateUtil.fromString(condition.getPaymentDatetimeEnd() + " 23:59:59", true));
      builder.append(" AND ");
      builder.append(fragment.getFragment());
      builder.append(" ) ");
      params.addAll(Arrays.asList(fragment.getParameters()));
    }
    // 検索条件:受注番号
    if (StringUtil.hasValueAnyOf(condition.getOrderNoStart(), condition.getOrderNoEnd())) {
      SqlDialect dialect = SqlDialect.getDefault();
      SqlFragment fragment = dialect.createRangeClause("OSV.ORDER_NO", condition.getOrderNoStart(), condition.getOrderNoEnd());
      builder.append(" AND ");
      builder.append(fragment.getFragment());
      params.addAll(Arrays.asList(fragment.getParameters()));
    }
    // add by lc 2012-04-09 start
    // 検索条件:交易编号
    if (StringUtil.hasValue(condition.getSearchFromTmallTid())) {
      SqlDialect dialect = SqlDialect.getDefault();
      SqlFragment fragment = dialect.createLikeClause("OSV.TMALL_TID", condition.getSearchFromTmallTid(),
          LikeClauseOption.PARTIAL_MATCH);
      builder.append(" AND " + fragment.getFragment());
      params.addAll(Arrays.asList(fragment.getParameters()));
    }
    // add by lc 2012-04-09 end
    // add by why 2014-05-13 start
    // 検索条件:京东交易编号
    if (StringUtil.hasValue(condition.getSearchFromJdDid())) {
      SqlDialect dialect = SqlDialect.getDefault();
      SqlFragment fragment = dialect.createLikeClause("OSV.JD_ORDER_NO", condition.getSearchFromJdDid(),
          LikeClauseOption.PARTIAL_MATCH);
      builder.append(" AND " + fragment.getFragment());
      params.addAll(Arrays.asList(fragment.getParameters()));
    }
    // add by why 2014-05-13 end
    // 検索条件:受注ステータス
    String[] orderSearchStatus;
    if (StringUtil.hasValueAnyOf(condition.getOrderStatus())) {
      orderSearchStatus = ArrayUtil.immutableCopy(condition.getOrderStatus());
    } else {
      orderSearchStatus = this.noSelectStatus;
    }
    SqlFragment orderFragment = SqlDialect.getDefault().createInClause("OSV.ORDER_STATUS", (Object[]) orderSearchStatus);
    builder.append(" AND ");
    builder.append(orderFragment.getFragment());
    params.addAll(Arrays.asList(orderFragment.getParameters()));
    setCancelCondition(condition);
    // 検索条件:出荷ステータス
    String[] shippingSearchStatus;
    if (StringUtil.hasValueAnyOf(condition.getShippingStatusSummary())) {
      shippingSearchStatus = ArrayUtil.immutableCopy(condition.getShippingStatusSummary());
    } else {
      shippingSearchStatus = this.noSelectStatus;
    }
    SqlFragment shippingFragment = SqlDialect.getDefault().createInClause("OSV.SHIPPING_STATUS_SUMMARY",
        (Object[]) shippingSearchStatus);
    builder.append(" AND ");
    builder.append(shippingFragment.getFragment());
    params.addAll(Arrays.asList(shippingFragment.getParameters()));
    // 検索条件:返品ステータス
    String[] returnSearchStatus;
    if (StringUtil.hasValueAnyOf(condition.getReturnStatusSummary())) {
      returnSearchStatus = ArrayUtil.immutableCopy(condition.getReturnStatusSummary());
    } else {
      returnSearchStatus = this.noSelectStatus;
    }
    SqlFragment returnFragment = SqlDialect.getDefault().createInClause("OSV.RETURN_STATUS_SUMMARY", (Object[]) returnSearchStatus);
    builder.append(" AND ");
    builder.append(returnFragment.getFragment());
    params.addAll(Arrays.asList(returnFragment.getParameters()));
    // 検索条件:売上確定ステータス
    if (!condition.isSearchFixedSalesDataFlg()) {
      // soukai edit 2011/12/29 ob start
      // TMALLの場合
      if (OrderType.TMALL.getValue().equals(condition.getSearchOrderType())) {
        builder.append(" AND " + FixedSalesStatus.NOT_FIXED.getValue()
            + " = ANY(SELECT SH.FIXED_SALES_STATUS FROM TMALL_SHIPPING_HEADER SH WHERE OSV.ORDER_NO = SH.ORDER_NO ");
        builder.append(" AND SH.RETURN_ITEM_TYPE = " + ReturnItemType.ORDERED.getValue() + ") ");
      } else if (OrderType.EC.getValue().equals(condition.getSearchOrderType())) {
        builder.append(" AND " + FixedSalesStatus.NOT_FIXED.getValue()
            + " = ANY(SELECT SH.FIXED_SALES_STATUS FROM SHIPPING_HEADER SH WHERE OSV.ORDER_NO = SH.ORDER_NO ");
        builder.append(" AND SH.RETURN_ITEM_TYPE = " + ReturnItemType.ORDERED.getValue() + ") ");
      } else if (OrderType.JD.getValue().equals(condition.getSearchOrderType())) {
        builder.append(" AND " + FixedSalesStatus.NOT_FIXED.getValue()
            + " = ANY(SELECT SH.FIXED_SALES_STATUS FROM JD_SHIPPING_HEADER SH WHERE OSV.ORDER_NO = SH.ORDER_NO ");
        builder.append(" AND SH.RETURN_ITEM_TYPE = " + ReturnItemType.ORDERED.getValue() + ") ");
      } else {
        builder.append(" AND (" + FixedSalesStatus.NOT_FIXED.getValue()
            + " = ANY(SELECT SH.FIXED_SALES_STATUS FROM TMALL_SHIPPING_HEADER SH WHERE OSV.ORDER_NO = SH.ORDER_NO ");
        builder.append(" AND SH.RETURN_ITEM_TYPE = " + ReturnItemType.ORDERED.getValue() + ") ");
        builder.append(" OR " + FixedSalesStatus.NOT_FIXED.getValue()
            + " = ANY(SELECT SH.FIXED_SALES_STATUS FROM SHIPPING_HEADER SH WHERE OSV.ORDER_NO = SH.ORDER_NO ");
        builder.append(" AND SH.RETURN_ITEM_TYPE = " + ReturnItemType.ORDERED.getValue() + ") ");
     
        builder.append(" OR " + FixedSalesStatus.NOT_FIXED.getValue()
            + " = ANY(SELECT SH.FIXED_SALES_STATUS FROM JD_SHIPPING_HEADER SH WHERE OSV.ORDER_NO = SH.ORDER_NO ");
        builder.append(" AND SH.RETURN_ITEM_TYPE = " + ReturnItemType.ORDERED.getValue() + ")) ");

      }
      // soukai edit 2011/12/29 ob end
    }
    // 支払方法
    if (StringUtil.hasValue(condition.getPaymentMethod())) {
      builder.append(" AND OSV.SHOP_CODE = ? AND OSV.PAYMENT_METHOD_NO = ? ");
      params.add(condition.getShopCode());
      params.add(condition.getPaymentMethod());
    }
    // 支払ステータス
    if (StringUtil.hasValue(condition.getPaymentStatus())) {
      builder.append(" AND OSV.PAYMENT_STATUS = ? ");
      params.add(condition.getPaymentStatus());
    }
    // 受注タイプ
    if (!StringUtil.isNullOrEmpty(condition.getSearchOrderType())) {
      builder.append(" AND OSV.ORDER_TYPE = ? ");
      params.add(condition.getSearchOrderType());
    }
    if (StringUtil.hasValue(condition.getSearchOrderClientType())) {
      builder.append(" AND OSV.ORDER_CLIENT_TYPE = ? ");
      params.add(condition.getSearchOrderClientType());
    }

    // 判断是PC来源还是手机来源
    if (!StringUtil.isNullOrEmpty(condition.getSearchMobileComputerType())) {
      builder.append(" AND OSV.MOBILE_COMPUTER_TYPE = ? ");
      params.add(condition.getSearchMobileComputerType());
    }

    // 検査フラグ
    if (!StringUtil.isNullOrEmpty(condition.getSearchOrderFlg())) {
      if (!StringUtil.isNullOrEmpty(condition.getConfirmFlg())) {
        if (condition.getConfirmFlg().equals("1")) {
          builder.append(" AND OSV.ORDER_FLG = ? ");
          params.add(OrderFlg.NOT_CHECKED.getValue());
        } else if (condition.getConfirmFlg().equals("2")) {
          builder.append(" AND OSV.ORDER_FLG = ? ");
          params.add(OrderFlg.GROUPCHECKED.getValue());
        } else if (condition.getConfirmFlg().equals("0")) {
          builder.append(" AND ( OSV.ORDER_FLG = ? OR OSV.ORDER_FLG = ?) ");
          params.add(OrderFlg.NOT_CHECKED.getValue());
          params.add(OrderFlg.GROUPCHECKED.getValue());
        } else if (condition.getConfirmFlg().equals("3")) {
          builder.append(" AND OSV.ORDER_FLG = ? ");
          params.add(OrderFlg.JDCHECKED.getValue());
        }
       } else {
         builder.append(" AND OSV.ORDER_FLG = ? ");
         params.add(condition.getSearchOrderFlg());
       }
    }
    // 判断订单使用语言区分
    if (!StringUtil.isNullOrEmpty(condition.getSearchLanguageCode())) {
      builder.append(" AND OSV.LANGUAGE_CODE = ? ");
      params.add(condition.getSearchLanguageCode());
    }
    // add by lc 2012-04-13 start
    int orderHeadOff = DIContainer.getWebshopConfig().getOrderHeadOff();
    if (condition.isSearchUnpaymentFlg()) {
      if (!StringUtil.isNullOrEmpty(condition.getContentFlg()) && condition.getContentFlg().equals("2")) {
        builder.append("  AND ((OSV.PAYMENT_METHOD_TYPE = '" + PaymentMethodType.CASH_ON_DELIVERY.getValue() + "'");
      } else {
        builder.append(" AND ((OSV.PAYMENT_METHOD_TYPE = '" + PaymentMethodType.CASH_ON_DELIVERY.getValue() + "'");
      }
      builder
          .append(" AND ((OSV.RETAIL_PRICE - OSV.COUPON_PRICE + OSV.SHIPPING_CHARGE + OSV.PAYMENT_COMMISSION >= 0) OR (OSV.CAUTION IS NOT NULL AND OSV.CAUTION <> ''))) ");
      builder.append(" OR (OSV.PAYMENT_METHOD_TYPE <> '" + PaymentMethodType.CASH_ON_DELIVERY.getValue()
          + "' AND OSV.PAYMENT_STATUS ='" + PaymentStatus.PAID.getValue() + "' ))");
      // params.add(orderHeadOff);

    }
    // add by lc 2012-04-13 end
    // 支払期限条件設定
    setConditionPaymentLimit(condition, builder, params);
    // ショップコード
    if (condition.isSiteAdmin() || condition.isCustomer()) {
      Logger.getLogger(this.getClass()).debug("Query Mode = site admin");
    } else {
      Logger.getLogger(this.getClass()).debug("Query Mode = shop");
      builder.append(" AND OSV.SHOP_CODE = ? ");
      params.add(condition.getShopCode());
    }
    if (!StringUtil.isNullOrEmpty(condition.getConfirmFlg())) {
      if (condition.getConfirmFlg().equals("0")) {
        builder.append(" AND ( OSV.ORDER_FLG = ? OR OSV.ORDER_FLG = ? ) ");
        params.add(OrderFlg.NOT_CHECKED.getValue());
        params.add(OrderFlg.GROUPCHECKED.getValue());
      } else if (condition.getConfirmFlg().equals("1")) {
        builder.append(" AND OSV.ORDER_FLG = ? ");
        params.add(OrderFlg.NOT_CHECKED.getValue());
      } else if (condition.getConfirmFlg().equals("2")) {
        builder.append(" AND OSV.ORDER_FLG = ? ");
        params.add(OrderFlg.GROUPCHECKED.getValue());
      } else if (condition.getConfirmFlg().equals("3")) {
        builder.append(" AND OSV.ORDER_FLG = ? ");
        params.add(OrderFlg.JDCHECKED.getValue());
      }
    }
    if (!StringUtil.isNullOrEmpty(condition.getContentFlg())) {
      if (condition.getContentFlg().equals("0")) {
        builder.append(" AND OSV.RETAIL_PRICE - OSV.COUPON_PRICE + OSV.SHIPPING_CHARGE + OSV.PAYMENT_COMMISSION >= ?");
        params.add(orderHeadOff);
      }
      if (condition.getContentFlg().equals("1")) {
        builder.append(" AND OSV.CAUTION IS NOT NULL AND OSV.CAUTION <> ''");
      }
      if (condition.getContentFlg().equals("2")) {
        if (!StringUtil.isNullOrEmpty(condition.getSearchOrderType())) {
          builder.append(" AND   OSV.ORDER_TYPE = ? ");
          params.add(condition.getSearchOrderType());
        }
      }

    }

    if (!condition.isTotalPriceFlg()) {
      createConditionOrder(condition, builder);
    }
    
    if(condition.getLimitNum() != null && condition.getLimitNum() > 0L) {
      builder.append(" LIMIT ? ");
      params.add(condition.getLimitNum());
    }
    setSqlString(builder.toString());
    setParameters(params.toArray());
    setPageNumber(condition.getCurrentPage());
    setPageSize(condition.getPageSize());
  }

  private void createConditionOrder(OrderListSearchCondition condition, StringBuilder builder) {
    // 並び順
    if (StringUtil.hasValue(condition.getSearchListSort())) {
      if (condition.getSearchListSort().equals("export")) {
        builder.append(" ORDER BY ORDER_NO");
      } else if (condition.getSearchListSort().equals("orderNo")) {
        builder.append(" ORDER BY OSV.ORDER_NO DESC ");
      } else if (condition.getSearchListSort().equals("customerName")) {
        builder.append(" ORDER BY CUSTOMER_NAME DESC ");
      } else if (condition.getSearchListSort().equals("orderDatetime")) {
        builder.append(" ORDER BY OSV.ORDER_DATETIME DESC ");
      } else if (condition.getSearchListSort().equals("paymentDatetime")) {
        builder.append(" ORDER BY OSV.PAYMENT_DATE DESC ");
      } else if (condition.getSearchListSort().equals("orderNo0")) {
        builder.append(" ORDER BY OSV.ORDER_NO ");
      } else if (condition.getSearchListSort().equals("orderNo1")) {
        builder.append(" ORDER BY OSV.ORDER_NO DESC ");
      }
    } else {
      builder.append(" ORDER BY OSV.ORDER_NO DESC ");
    }

  }

  private void setConditionPaymentLimit(OrderListSearchCondition condition, StringBuilder builder, List<Object> params) {
    // 支払期限切れ
    if (condition.isSearchPaymentLimitOver()) {
      builder.append(" AND ( OSV.PAYMENT_LIMIT_DATE < ?");
      params.add(DateUtil.fromString(DateUtil.getSysdateString()));
    }

    // 支払期限間近
    if (StringUtil.hasValue(condition.getPaymentLimitDays())) {
      if (condition.isSearchPaymentLimitOver()) {
        builder.append(" OR ");
      } else {
        builder.append(" AND ");
      }

      Date searchLimitDate = DateUtil.getSysdate();
      searchLimitDate = DateUtil.addDate(searchLimitDate, NumUtil.toLong(condition.getPaymentLimitDays()).intValue());
      searchLimitDate = DateUtil.truncateDate(searchLimitDate);
      builder.append(" ( OSV.PAYMENT_LIMIT_DATE <= ? AND OSV.PAYMENT_LIMIT_DATE >= ? )");
      params.add(searchLimitDate);
      params.add(DateUtil.fromString(DateUtil.getSysdateString()));
      // 入金督促メール送信済みを含む
      if (!condition.isSearchWithSentPaymentReminderMail()) {
        builder.append(" AND NOT EXISTS ( ");
        builder.append(" SELECT * FROM RESPECTIVE_MAILQUEUE RM ");
        builder.append(" INNER JOIN ORDER_MAIL_HISTORY OMH ON RM.MAIL_QUEUE_ID = OMH.MAIL_QUEUE_ID ");
        builder.append(" WHERE RM.MAIL_TYPE = ? AND ORDER_NO = OSV.ORDER_NO )");
        params.add(MailType.PAYMENT_REMINDER.getValue());
      }
    }

    if (condition.isSearchPaymentLimitOver()) {
      builder.append(" ) ");
    }

  }

  private void setCancelCondition(OrderListSearchCondition condition) {
    List<String> orderStatus = Arrays.asList(condition.getOrderStatus());
    for (String order : orderStatus) {
      if (order.equals(OrderStatus.CANCELLED.getValue()) && condition.getShippingStatusSummary().length > 0) {
        List<String> shippingStatus = new ArrayList<String>();
        shippingStatus.addAll(Arrays.asList(condition.getShippingStatusSummary()));
        shippingStatus.add(ShippingStatusSummary.CANCELLED.getValue());
        condition.setShippingStatusSummary(shippingStatus.toArray(new String[shippingStatus.size()]));
      }
    }

  }

  public Class<OrderHeadline> getRowType() {
    return OrderHeadline.class;
  }

}
