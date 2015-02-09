package jp.co.sint.webshop.service.analysis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.co.sint.webshop.data.AbstractQuery;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.SqlDialect;
import jp.co.sint.webshop.utility.SqlFragment;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.utility.SqlDialect.DateSearchAccuracy;
import jp.co.sint.webshop.utility.SqlDialect.LikeClauseOption;

public class GiftCardUseLogSearchQuery extends AbstractQuery<GiftCardUseLogSummary> {

  /** serial version UID */
  private static final long serialVersionUID = 1L;

  /** default constructor */
  public GiftCardUseLogSearchQuery() {
    
  }

  public GiftCardUseLogSearchQuery(GiftCardUseLogListSearchCondition condition) {
    StringBuilder builder = new StringBuilder();
    List<Object> params = new ArrayList<Object>();

    
    builder.append("SELECT ''''||A.CUSTOMER_CODE  , A.CARD_ID, A.RECHARGE_DATE,A.DENOMINATION , A.UNIT_PRICE ,''||A.DISCOUNT_RATE||'%' , A.USE_AMOUNT, A.LEFT_AMOUNT, A.LINK_ORDER ");
    builder.append("FROM (SELECT  CCI.CUSTOMER_CODE , ");
    builder.append("CCI.CARD_ID,CCI.RECHARGE_DATE,CCI.DENOMINATION ,CT.MOBILE_NUMBER ,CT.EMAIL,CT.LAST_NAME ,");
    builder.append("(SELECT UNIT_PRICE FROM GIFT_CARD_ISSUE_DETAIL WHERE CARD_ID = CCI.CARD_ID LIMIT 1) AS UNIT_PRICE ,");
    builder.append("((SELECT CASE WHEN UNIT_PRICE IS NULL THEN 0 ELSE UNIT_PRICE END FROM GIFT_CARD_ISSUE_DETAIL WHERE CARD_ID = CCI.CARD_ID LIMIT 1)*100/CCI.DENOMINATION)::NUMERIC(10,2)  AS DISCOUNT_RATE ,");
    builder.append(" (SELECT CASE WHEN SUM(CCUI.USE_AMOUNT) IS NULL THEN 0 ELSE SUM(CCUI.USE_AMOUNT) END FROM CUSTOMER_CARD_USE_INFO CCUI INNER JOIN SHIPPING_HEADER SH  ON SH.ORDER_NO = CCUI.ORDER_NO  AND SH.RETURN_ITEM_TYPE <> 1 AND CCUI.USE_STATUS = 0 ");
    builder.append(" WHERE CARD_ID = CCI.CARD_ID ) - (SELECT CASE WHEN SUM(GCRC.RETURN_AMOUNT) IS NULL THEN 0 ELSE SUM(GCRC.RETURN_AMOUNT)  END  FROM GIFT_CARD_RETURN_CONFIRM GCRC WHERE GCRC.CARD_ID = CCI.CARD_ID ) AS USE_AMOUNT, CCI.DENOMINATION - ( SELECT CASE WHEN SUM(CCUI.USE_AMOUNT) IS NULL THEN 0 ELSE SUM(CCUI.USE_AMOUNT) END ");
    builder.append(" FROM CUSTOMER_CARD_USE_INFO CCUI  INNER JOIN SHIPPING_HEADER SH  ON SH.ORDER_NO = CCUI.ORDER_NO AND SH.RETURN_ITEM_TYPE <> 1 AND CCUI.USE_STATUS = 0 ");
    if (StringUtil.hasValue(condition.getSearchShippingStartDatetimeFrom()) && StringUtil.hasValue(condition.getSearchShippingStartDatetimeTo())){
      builder.append(" WHERE CARD_ID = CCI.CARD_ID) + (SELECT CASE WHEN SUM(GCRC.RETURN_AMOUNT) IS NULL THEN 0 ELSE SUM(GCRC.RETURN_AMOUNT)  END  FROM GIFT_CARD_RETURN_CONFIRM GCRC WHERE GCRC.CARD_ID = CCI.CARD_ID ) AS LEFT_AMOUNT, " +
          "GIFT_CARD_UNION_ORDERS(CCI.CARD_ID,'"+condition.getSearchShippingStartDatetimeFrom()+"','"+condition.getSearchShippingStartDatetimeTo()+"') AS LINK_ORDER,");
    } else if (!StringUtil.hasValue(condition.getSearchShippingStartDatetimeFrom()) && StringUtil.hasValue(condition.getSearchShippingStartDatetimeTo())){
      builder.append(" WHERE CARD_ID = CCI.CARD_ID) + (SELECT CASE WHEN SUM(GCRC.RETURN_AMOUNT) IS NULL THEN 0 ELSE SUM(GCRC.RETURN_AMOUNT)  END  FROM GIFT_CARD_RETURN_CONFIRM GCRC WHERE GCRC.CARD_ID = CCI.CARD_ID ) AS LEFT_AMOUNT, " +
          "GIFT_CARD_UNION_ORDERS(CCI.CARD_ID,null,'"+condition.getSearchShippingStartDatetimeTo()+"') AS LINK_ORDER,");
    } else if (StringUtil.hasValue(condition.getSearchShippingStartDatetimeFrom()) && !StringUtil.hasValue(condition.getSearchShippingStartDatetimeTo())){
      builder.append(" WHERE CARD_ID = CCI.CARD_ID) + (SELECT CASE WHEN SUM(GCRC.RETURN_AMOUNT) IS NULL THEN 0 ELSE SUM(GCRC.RETURN_AMOUNT)  END  FROM GIFT_CARD_RETURN_CONFIRM GCRC WHERE GCRC.CARD_ID = CCI.CARD_ID ) AS LEFT_AMOUNT, " +
          "GIFT_CARD_UNION_ORDERS(CCI.CARD_ID,'"+condition.getSearchShippingStartDatetimeFrom()+"',null) AS LINK_ORDER,");
    } else {
      builder.append(" WHERE CARD_ID = CCI.CARD_ID) + (SELECT CASE WHEN SUM(GCRC.RETURN_AMOUNT) IS NULL THEN 0 ELSE SUM(GCRC.RETURN_AMOUNT)  END  FROM GIFT_CARD_RETURN_CONFIRM GCRC WHERE GCRC.CARD_ID = CCI.CARD_ID ) AS LEFT_AMOUNT, " +
          "GIFT_CARD_UNION_ORDERS(CCI.CARD_ID,null,null) AS LINK_ORDER,");
    }

    builder.append(" SH.shipping_date FROM CUSTOMER_CARD_INFO CCI INNER JOIN CUSTOMER CT ON CCI.CUSTOMER_CODE = CT.CUSTOMER_CODE  ");
    builder.append(" INNER JOIN CUSTOMER_CARD_USE_INFO CCUI  ON CCUI.CARD_ID = CCI.CARD_ID ");
    builder.append(" INNER JOIN SHIPPING_HEADER SH  ON SH.ORDER_NO = CCUI.ORDER_NO  AND SH.RETURN_ITEM_TYPE <> 1 ");
    builder.append("  ) A  ");
    builder.append("WHERE 1 = 1 ");

    if (StringUtil.hasValue(condition.getSearchTelephoneNum())) {
      builder.append("AND A.mobile_number = ? ");
      params.add(condition.getSearchTelephoneNum());
    }

    if (StringUtil.hasValue(condition.getSearchCardId())) {
      builder.append("AND A.CARD_ID = ? ");
      params.add(condition.getSearchCardId());
    }

    if (StringUtil.hasValue(condition.getSearchOrderNo())) {
      SqlFragment skuNameFragmentOrder = SqlDialect.getDefault().createLikeClause("A.LINK_ORDER", condition.getSearchOrderNo(),
          LikeClauseOption.PARTIAL_MATCH);

      builder.append(" AND " + skuNameFragmentOrder.getFragment());
      for (Object o : skuNameFragmentOrder.getParameters()) {
        params.add(o);
      }
    }

    SqlFragment commodityCodeFragment = SqlDialect.getDefault().createRangeClause("A.customer_code",
        condition.getSearchCustomerCodeStart(), condition.getSearchCustomerCodeEnd());
    
    if (StringUtil.hasValue(condition.getSearchEmail())) {
      SqlFragment skuEmailFragment = SqlDialect.getDefault().createLikeClause("A.email", condition.getSearchEmail(),
          LikeClauseOption.PARTIAL_MATCH);

      builder.append(" AND " + skuEmailFragment.getFragment());
      for (Object o : skuEmailFragment.getParameters()) {
        params.add(o);
      }
    }

    if (StringUtil.hasValue(commodityCodeFragment.getFragment())) {
      builder.append(" AND " + commodityCodeFragment.getFragment());
      for (Object o : commodityCodeFragment.getParameters()) {
        params.add(o);
      }
    }

    if (StringUtil.hasValue(condition.getSearchCustomerName())) {
      SqlFragment skuNameFragment = SqlDialect.getDefault().createLikeClause("A.last_name", condition.getSearchCustomerName(),
          LikeClauseOption.PARTIAL_MATCH);

      builder.append(" AND " + skuNameFragment.getFragment());
      for (Object o : skuNameFragment.getParameters()) {
        params.add(o);
      }

    }

    // 检索条件：发货时间
    if (StringUtil.hasValueAnyOf(condition.getSearchShippingStartDatetimeFrom(), condition.getSearchShippingStartDatetimeTo())) {
      SqlDialect dialect = SqlDialect.getDefault();
      builder.append(" AND ");
      SqlFragment fragment = dialect.createDateRangeClause("A.shipping_date", DateUtil.fromString(condition
          .getSearchShippingStartDatetimeFrom()), DateUtil.fromString(condition.getSearchShippingStartDatetimeTo()),
          DateSearchAccuracy.DATE);
      builder.append(fragment.getFragment());
      params.addAll(Arrays.asList(fragment.getParameters()));
    }

    builder.append(" GROUP BY CUSTOMER_CODE , CARD_ID, RECHARGE_DATE,DENOMINATION , UNIT_PRICE ,DISCOUNT_RATE , USE_AMOUNT, LEFT_AMOUNT, LINK_ORDER ");
    builder.append(" ORDER BY CUSTOMER_CODE ");

    setSqlString(builder.toString());
    setParameters(params.toArray());
    setPageNumber(condition.getCurrentPage());
  }

  public Class<GiftCardUseLogSummary> getRowType() {
    return GiftCardUseLogSummary.class;
  }

}
