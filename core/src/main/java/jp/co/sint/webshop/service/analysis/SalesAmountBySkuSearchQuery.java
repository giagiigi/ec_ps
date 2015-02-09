package jp.co.sint.webshop.service.analysis;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.AbstractQuery;
import jp.co.sint.webshop.utility.SqlDialect;
import jp.co.sint.webshop.utility.SqlFragment;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.utility.SqlDialect.LikeClauseOption;

public class SalesAmountBySkuSearchQuery extends AbstractQuery<SalesAmountBySkuSummary> {

  /** serial version UID */
  private static final long serialVersionUID = 1L;

  /** default constructor */
  public SalesAmountBySkuSearchQuery() {
  }

  public SalesAmountBySkuSearchQuery(SalesAmountBySkuSearchCondition condition) {
    StringBuilder builder = new StringBuilder();
    List<Object> params = new ArrayList<Object>();

    builder.append("SELECT ");
    builder.append("SHOP_NAME, ");
    builder.append("SHOP_CODE, ");
    builder.append("COMMODITY_CODE, ");
    builder.append("SKU_CODE, ");
    builder.append("COMMODITY_SKU_NAME,");
    builder.append("SUM(COALESCE(TOTAL_SALES_PRICE , 0)) AS TOTAL_SALES_PRICE,");
//  delete by V10-CH 170 start
//    builder.append("SUM(COALESCE(TOTAL_SALES_PRICE_TAX , 0)) AS TOTAL_SALES_PRICE_TAX,");
//  delete by V10-CH 170 start
    builder.append("SUM(COALESCE(TOTAL_GIFT_PRICE , 0)) AS TOTAL_GIFT_PRICE,");
//  delete by V10-CH 170 start
//    builder.append("SUM(COALESCE(TOTAL_GIFT_TAX , 0)) AS TOTAL_GIFT_TAX,");
//  delete by V10-CH 170 start
    builder.append("SUM(COALESCE(TOTAL_ORDER_QUANTITY , 0)) AS TOTAL_ORDER_QUANTITY,");
    builder.append("SUM(COALESCE(TOTAL_RETURN_ITEM_QUANTITY , 0)) AS TOTAL_RETURN_ITEM_QUANTITY, ");
    builder.append("SUM(COALESCE(TOTAL_DISCOUNT_AMOUNT , 0)) AS TOTAL_DISCOUNT_AMOUNT,");
    builder.append("SUM(COALESCE(TOTAL_REFUND , 0)) AS TOTAL_REFUND ");
    builder.append("FROM SALES_AMOUNT_BY_SKU ");
    builder.append("WHERE 1 = 1 ");

    SqlFragment dateFragment = SqlDialect.getDefault().createDateRangeClause("COUNTED_DATE", condition.getSearchStartDate(),
        condition.getSearchEndDate());

    if (StringUtil.hasValue(dateFragment.getFragment())) {
      builder.append(" AND " + dateFragment.getFragment());
      for (Object o : dateFragment.getParameters()) {
        params.add(o);
      }
    }

    if (StringUtil.hasValue(condition.getShopCode())) {
      builder.append("AND SHOP_CODE = ? ");
      params.add(condition.getShopCode());
    }

    SqlFragment commodityCodeFragment = SqlDialect.getDefault().createRangeClause("COMMODITY_CODE",
        condition.getCommodityCodeStart(), condition.getCommodityCodeEnd());

    if (StringUtil.hasValue(commodityCodeFragment.getFragment())) {
      builder.append(" AND " + commodityCodeFragment.getFragment());
      for (Object o : commodityCodeFragment.getParameters()) {
        params.add(o);
      }
    }

    if (StringUtil.hasValue(condition.getCommoditySkuName())) {
      SqlFragment skuNameFragment = SqlDialect.getDefault().createLikeClause("COMMODITY_SKU_NAME", condition.getCommoditySkuName(),
          LikeClauseOption.PARTIAL_MATCH);

      builder.append(" AND " + skuNameFragment.getFragment());
      for (Object o : skuNameFragment.getParameters()) {
        params.add(o);
      }

    }

    builder.append("GROUP BY SHOP_NAME, SHOP_CODE, COMMODITY_CODE, SKU_CODE, COMMODITY_SKU_NAME ");
    builder.append("ORDER BY TOTAL_SALES_PRICE DESC ");

    setSqlString(builder.toString());
    setParameters(params.toArray());
    setPageNumber(condition.getCurrentPage());
    setPageSize(condition.getPageSize());
    setMaxFetchSize(condition.getMaxFetchSize());
  }

  public Class<SalesAmountBySkuSummary> getRowType() {
    return SalesAmountBySkuSummary.class;
  }

}
