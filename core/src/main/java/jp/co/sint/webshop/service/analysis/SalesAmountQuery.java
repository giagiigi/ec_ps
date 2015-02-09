package jp.co.sint.webshop.service.analysis;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.configure.WebshopConfig;
import jp.co.sint.webshop.data.SimpleQuery;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateRange;
import jp.co.sint.webshop.utility.SqlDialect;
import jp.co.sint.webshop.utility.SqlFragment;
import jp.co.sint.webshop.utility.StringUtil;

public class SalesAmountQuery extends SimpleQuery {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;

  private int getShopType(boolean isSite) {
    int result = 1;

    if (isSite) {
      result = 0;
    }

    return result;
  }

  public SalesAmountQuery() {
  }

  public SalesAmountQuery(DateRange range, boolean isSite) {

    StringBuilder builder = new StringBuilder();
    List<Object> params = new ArrayList<Object>();

    builder.append("SELECT ");
    builder.append("SHOP.SHOP_CODE, ");
    builder.append("SHOP.SHOP_NAME, ");
    builder.append("SUM(COALESCE(SALES_AMOUNT_BY_SHOP.TOTAL_SALES_PRICE,0)) AS TOTAL_SALES_PRICE,");
    builder.append("SUM(COALESCE(SALES_AMOUNT_BY_SHOP.TOTAL_SALES_PRICE_TAX,0)) AS TOTAL_SALES_PRICE_TAX,");
    builder.append("SUM(COALESCE(SALES_AMOUNT_BY_SHOP.TOTAL_GIFT_PRICE,0)) AS TOTAL_GIFT_PRICE,");
    builder.append("SUM(COALESCE(SALES_AMOUNT_BY_SHOP.TOTAL_GIFT_TAX,0)) AS TOTAL_GIFT_TAX,");
    builder.append("SUM(COALESCE(SALES_AMOUNT_BY_SHOP.TOTAL_SHIPPING_CHARGE,0)) AS TOTAL_SHIPPING_CHARGE,");
    builder.append("SUM(COALESCE(SALES_AMOUNT_BY_SHOP.TOTAL_SHIPPING_CHARGE_TAX,0)) AS TOTAL_SHIPPING_CHARGE_TAX,");
    builder.append("SUM(COALESCE(SALES_AMOUNT_BY_SHOP.TOTAL_DISCOUNT_AMOUNT,0)) AS TOTAL_DISCOUNT_AMOUNT,");
    builder.append("SUM(COALESCE(SALES_AMOUNT_BY_SHOP.TOTAL_REFUND,0)) AS TOTAL_REFUND,");
    builder.append("SUM(COALESCE(SALES_AMOUNT_BY_SHOP.TOTAL_RETURN_ITEM_LOSS_MONEY,0)) AS TOTAL_RETURN_ITEM_LOSS_MONEY ");
    builder.append("FROM SHOP ");
    builder.append("LEFT OUTER JOIN ( ");

    if (isSite) {
      builder.append("SELECT ");
      builder.append("( SELECT SHOP_CODE FROM SHOP WHERE SHOP_CODE = ? ) AS SHOP_CODE, ");
      WebshopConfig config = DIContainer.getWebshopConfig();
      params.add(config.getSiteShopCode());
      builder.append("TOTAL_SALES_PRICE,");
      builder.append("TOTAL_SALES_PRICE_TAX,");
      builder.append("TOTAL_REFUND,");
      builder.append("TOTAL_RETURN_ITEM_LOSS_MONEY,");
      builder.append("TOTAL_DISCOUNT_AMOUNT,");
      builder.append("TOTAL_SHIPPING_CHARGE,");
      builder.append("TOTAL_SHIPPING_CHARGE_TAX,");
      builder.append("TOTAL_GIFT_PRICE,");
      builder.append("TOTAL_GIFT_TAX ");
      builder.append("FROM ( ");
      builder.append("SELECT ");
      builder.append("SUM(COALESCE(TOTAL_SALES_PRICE , 0)) AS TOTAL_SALES_PRICE,");
      builder.append("SUM(COALESCE(TOTAL_SALES_PRICE_TAX , 0)) AS TOTAL_SALES_PRICE_TAX,");
      builder.append("SUM(COALESCE(TOTAL_REFUND , 0)) AS TOTAL_REFUND,");
      builder.append("SUM(COALESCE(TOTAL_RETURN_ITEM_LOSS_MONEY , 0)) AS TOTAL_RETURN_ITEM_LOSS_MONEY,");
      builder.append("SUM(COALESCE(TOTAL_DISCOUNT_AMOUNT , 0)) AS TOTAL_DISCOUNT_AMOUNT,");
      builder.append("SUM(COALESCE(TOTAL_SHIPPING_CHARGE , 0)) AS TOTAL_SHIPPING_CHARGE,");
      builder.append("SUM(COALESCE(TOTAL_SHIPPING_CHARGE_TAX , 0)) AS TOTAL_SHIPPING_CHARGE_TAX,");
      builder.append("SUM(COALESCE(TOTAL_GIFT_PRICE , 0)) AS TOTAL_GIFT_PRICE,");
      builder.append("SUM(COALESCE(TOTAL_GIFT_TAX , 0)) AS TOTAL_GIFT_TAX ");
      builder.append("FROM SALES_AMOUNT_BY_SHOP ");
      builder.append("WHERE 1 = 1 ");

      SqlFragment dateFragment = SqlDialect.getDefault().createDateRangeClause("COUNTED_DATE", range.getStart(), range.getEnd());
      if (StringUtil.hasValue(dateFragment.getFragment())) {
        builder.append(" AND " + dateFragment.getFragment());
        for (Object o : dateFragment.getParameters()) {
          params.add(o);
        }
      }
      builder.append(")");
//    Add by V10-CH start
      builder.append(" AS BB )  SALES_AMOUNT_BY_SHOP ");
//    Add by V10-CH end

    } else {
      builder.append("SELECT ");
      builder.append("SHOP_CODE, ");
      builder.append("SUM(COALESCE(TOTAL_SALES_PRICE , 0)) AS TOTAL_SALES_PRICE,");
      builder.append("SUM(COALESCE(TOTAL_SALES_PRICE_TAX , 0)) AS TOTAL_SALES_PRICE_TAX,");
      builder.append("SUM(COALESCE(TOTAL_REFUND , 0)) AS TOTAL_REFUND,");
      builder.append("SUM(COALESCE(TOTAL_RETURN_ITEM_LOSS_MONEY , 0)) AS TOTAL_RETURN_ITEM_LOSS_MONEY,");
      builder.append("SUM(COALESCE(TOTAL_DISCOUNT_AMOUNT , 0)) AS TOTAL_DISCOUNT_AMOUNT,");
      builder.append("SUM(COALESCE(TOTAL_SHIPPING_CHARGE , 0)) AS TOTAL_SHIPPING_CHARGE,");
      builder.append("SUM(COALESCE(TOTAL_SHIPPING_CHARGE_TAX , 0)) AS TOTAL_SHIPPING_CHARGE_TAX,");
      builder.append("SUM(COALESCE(TOTAL_GIFT_PRICE , 0)) AS TOTAL_GIFT_PRICE,");
      builder.append("SUM(COALESCE(TOTAL_GIFT_TAX , 0)) AS TOTAL_GIFT_TAX ");
      builder.append("FROM SALES_AMOUNT_BY_SHOP ");
      builder.append("WHERE 1 = 1 ");

      SqlFragment dateFragment = SqlDialect.getDefault().createDateRangeClause("COUNTED_DATE", range.getStart(), range.getEnd());
      if (StringUtil.hasValue(dateFragment.getFragment())) {
        builder.append(" AND " + dateFragment.getFragment());
        for (Object o : dateFragment.getParameters()) {
          params.add(o);
        }
      }
      builder.append(" GROUP BY SHOP_CODE ");
//    Add by V10-CH start
      builder.append(" )  SALES_AMOUNT_BY_SHOP ");
//    Add by V10-CH end
    }
//delete by V10-CH start
    //builder.append(" )  SALES_AMOUNT_BY_SHOP ");
//delete by V10-CH end
    builder.append("ON SHOP.SHOP_CODE = SALES_AMOUNT_BY_SHOP.SHOP_CODE ");
    builder.append("WHERE SHOP.SHOP_TYPE = ? ");
    params.add(getShopType(isSite));
    builder.append("GROUP BY SHOP.SHOP_CODE, SHOP.SHOP_NAME ");

    if (!isSite) {
      builder.append("ORDER BY ");
      builder.append("  TOTAL_SALES_PRICE DESC,");
      builder.append("  SHOP_CODE ASC");
    }

    setSqlString(builder.toString());
    setParameters(params.toArray());

  }

}
