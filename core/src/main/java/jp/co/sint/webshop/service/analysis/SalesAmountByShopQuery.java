package jp.co.sint.webshop.service.analysis;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.sint.webshop.configure.WebshopConfig;
import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.SimpleQuery;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.SqlDialect;
import jp.co.sint.webshop.utility.SqlFragment;
import jp.co.sint.webshop.utility.StringUtil;

public class SalesAmountByShopQuery extends SimpleQuery {

  /** serial version UID */
  private static final long serialVersionUID = 1L;

  /** default constructor */
  public SalesAmountByShopQuery() {
  }

  private String createAmountUnit(CountType type) {
    String unit = "";
    switch (type) {
      case MONTHLY:
        unit = SqlDialect.getDefault().getMonthFromDate("COUNTED_DATE");
        break;
      case DAILY:
        unit = SqlDialect.getDefault().getDayFromDate("COUNTED_DATE");
        break;
      default:
        break;
    }
    return unit;
  }

  /** 歯抜けデータを埋める為、日付一覧テーブルを作成 */
  public static Query createDateTable(CountType type, Date start, Date end) {
    List<Object> params = new ArrayList<Object>();
    StringBuilder builder = new StringBuilder();

    switch (type) {
      case MONTHLY:
//        builder.append(" SELECT ");
//        builder.append("   DISTINCT " + SqlDialect.getDefault().getMonthFromDate("? + LEVEL - 1") + " AS COUNTED_DATE ");
//        builder.append(" FROM ");
//        builder.append("   DUAL ");
//        builder.append(" CONNECT BY ");
//        builder.append("   ? + LEVEL - 1 <= ? ");
//        params.add(start);
//        params.add(start);
//        params.add(end);
    	// postgreSQL start
    	if (DIContainer.getWebshopConfig().isPostgreSQL()) {
    	  builder.append(" WITH RECURSIVE R(CAL_DATE) AS ( ");
    	  builder.append("   VALUES(" + SqlDialect.getDefault().toDate() + ") ");
    	  builder.append(" 	 UNION ALL ");
          builder.append("   SELECT CAL_DATE + 1 FROM R WHERE CAL_DATE < " + SqlDialect.getDefault().toDate());
          builder.append(" ) ");
          builder.append(" SELECT DISTINCT " + SqlDialect.getDefault().getMonthFromDate("CAL_DATE") + " AS COUNTED_DATE FROM R ");
    	} else {
          builder.append(" SELECT "); //$NON-NLS-1$
          builder.append("   DISTINCT " + SqlDialect.getDefault().getMonthFromDate("? + LEVEL - 1") //$NON-NLS-1$ //$NON-NLS-2$
              + " AS COUNTED_DATE "); //$NON-NLS-1$
          builder.append(" FROM "); //$NON-NLS-1$
          builder.append("   DUAL "); //$NON-NLS-1$
          builder.append(" CONNECT BY "); //$NON-NLS-1$
          builder.append("   ? + LEVEL - 1 <= ? "); //$NON-NLS-1$
          params.add(start);
    	}
        params.add(start);
        params.add(end);
        // postgreSQL end        
        break;
      case DAILY:
//        builder.append(" SELECT ");
//        builder.append("   DISTINCT " + SqlDialect.getDefault().getDayFromDate("? + LEVEL - 1") + " AS COUNTED_DATE ");
//        builder.append(" FROM ");
//        builder.append("   DUAL ");
//        builder.append(" CONNECT BY ");
//        builder.append("   ? + LEVEL - 1 <= ? ");
//        params.add(start);
//        params.add(start);
//        params.add(end);
      	// postgreSQL start
      	if (DIContainer.getWebshopConfig().isPostgreSQL()) {
          builder.append(" WITH RECURSIVE R(CAL_DATE) AS ( ");
      	  builder.append("   VALUES(" + SqlDialect.getDefault().toDate() + ") ");
      	  builder.append(" 	 UNION ALL ");
          builder.append("   SELECT CAL_DATE + 1 FROM R WHERE CAL_DATE < " + SqlDialect.getDefault().toDate());
          builder.append(" ) ");
          builder.append(" SELECT DISTINCT " + SqlDialect.getDefault().getDayFromDate("CAL_DATE") + " AS COUNTED_DATE FROM R ");
      	} else {
          builder.append(" SELECT "); //$NON-NLS-1$
          builder.append("   DISTINCT " //$NON-NLS-1$
              + SqlDialect.getDefault().getDayFromDate("? + LEVEL - 1") + " AS COUNTED_DATE "); //$NON-NLS-1$ //$NON-NLS-2$
          builder.append(" FROM "); //$NON-NLS-1$
          builder.append("   DUAL "); //$NON-NLS-1$
          builder.append(" CONNECT BY "); //$NON-NLS-1$
          builder.append("   ? + LEVEL - 1 <= ? "); //$NON-NLS-1$
          params.add(start);
      	}
        params.add(start);
        params.add(end);
        // postgreSQL end        
        break;
      default:
        break;
    }
    return new SimpleQuery(builder.toString(), params.toArray());
  }

  private SqlFragment createRange(CountType type, int year, int month) {
    Date startDate = null;
    Date endDate = null;

    switch (type) {
      case MONTHLY:
        // 当年の初日～当年の末日
        startDate = DateUtil.fromYear("" + year);
        endDate = DateUtil.addDate(DateUtil.addMonth(startDate, 12), -1);
        break;
      case DAILY:
        // 当月の初日～当月の末日
        startDate = DateUtil.fromYearMonth("" + year, "" + month);
        endDate = DateUtil.addDate(DateUtil.addMonth(startDate, 1), -1);
        break;
      default:
        break;
    }
    return SqlDialect.getDefault().createDateRangeClause("COUNTED_DATE", startDate, endDate);
  }

  public SalesAmountByShopQuery(SalesAmountByShopSearchCondition condition) {
    StringBuilder builder = new StringBuilder();
    List<Object> params = new ArrayList<Object>();

    int year = condition.getSearchYear();
    int month = condition.getSearchMonth();
    Date startDate = null;
    Date endDate = null;
    switch (condition.getType()) {
      case MONTHLY:
        // 当年の初日～当年の末日
        startDate = DateUtil.fromYear("" + year);
        endDate = DateUtil.addDate(DateUtil.addMonth(startDate, 12), -1);
        break;
      case DAILY:
        // 当月の初日～当月の末日
        startDate = DateUtil.fromYearMonth("" + year, "" + month);
        endDate = DateUtil.addDate(DateUtil.addMonth(startDate, 1), -1);
        break;
      default:
        break;
    }

    Query calendarQuery = createDateTable(condition.getType(), startDate, endDate);

    builder.append(" SELECT ");
    builder.append("   CALENDAR.COUNTED_DATE AS COUNTED_DATE, ");
    builder.append("   COALESCE(SALES_AMOUNT.SALES_AMOUNT,0) AS TOTAL_SALES_PRICE, ");
//  delete by V10-CH 170 start
//    builder.append("   COALESCE(SALES_AMOUNT.TOTAL_SALES_PRICE_TAX,0) AS TOTAL_SALES_PRICE_TAX, ");
//  delete by V10-CH 170 end
    builder.append("   COALESCE(SALES_AMOUNT.TOTAL_GIFT_PRICE,0) AS TOTAL_GIFT_PRICE, ");
//  delete by V10-CH 170 start
//    builder.append("   COALESCE(SALES_AMOUNT.TOTAL_GIFT_TAX,0) AS TOTAL_GIFT_TAX, ");
//  delete by V10-CH 170 end
    builder.append("   COALESCE(SALES_AMOUNT.TOTAL_SHIPPING_CHARGE,0) AS TOTAL_SHIPPING_CHARGE, ");
//  delete by V10-CH 170 start
//    builder.append("   COALESCE(SALES_AMOUNT.TOTAL_SHIPPING_CHARGE_TAX,0) AS TOTAL_SHIPPING_CHARGE_TAX, ");
//  delete by V10-CH 170 end
    builder.append("   COALESCE(SALES_AMOUNT.TOTAL_DISCOUNT_AMOUNT,0) AS TOTAL_DISCOUNT_AMOUNT, ");
    builder.append("   COALESCE(SALES_AMOUNT.TOTAL_REFUND,0) AS TOTAL_REFUND, ");
    builder.append("   COALESCE(SALES_AMOUNT.TOTAL_RETURN_ITEM_LOSS_MONEY,0) AS TOTAL_RETURN_ITEM_LOSS_MONEY ");
    builder.append(" FROM ");
    builder.append("   ( ");
    builder.append("  " + calendarQuery.getSqlString());
    for (Object o : calendarQuery.getParameters()) {
      params.add(o);
    }
    builder.append("   ) CALENDAR ");
    builder.append(" LEFT OUTER JOIN ");
    builder.append("   ( ");
    builder.append("   SELECT ");
    builder.append(createAmountUnit(condition.getType()) + " AS COUNTED_DATE,");
    builder.append("     SUM(COALESCE(TOTAL_SALES_PRICE,0)) AS SALES_AMOUNT, ");
    builder.append("     SUM(COALESCE(TOTAL_SALES_PRICE_TAX,0)) AS TOTAL_SALES_PRICE_TAX, ");
    builder.append("     SUM(COALESCE(TOTAL_REFUND,0)) AS TOTAL_REFUND, ");
    builder.append("     SUM(COALESCE(TOTAL_RETURN_ITEM_LOSS_MONEY,0)) AS TOTAL_RETURN_ITEM_LOSS_MONEY, ");
    builder.append("     SUM(COALESCE(TOTAL_DISCOUNT_AMOUNT,0)) AS TOTAL_DISCOUNT_AMOUNT, ");
    builder.append("     SUM(COALESCE(TOTAL_SHIPPING_CHARGE,0)) AS TOTAL_SHIPPING_CHARGE, ");
    builder.append("     SUM(COALESCE(TOTAL_SHIPPING_CHARGE_TAX,0)) AS TOTAL_SHIPPING_CHARGE_TAX, ");
    builder.append("     SUM(COALESCE(TOTAL_GIFT_PRICE,0)) AS TOTAL_GIFT_PRICE,");
    builder.append("     SUM(COALESCE(TOTAL_GIFT_TAX,0)) AS TOTAL_GIFT_TAX ");
    builder.append("   FROM ");
    builder.append("     SALES_AMOUNT_BY_SHOP ");
    builder.append("   WHERE ");
    builder.append("     1 = 1 ");

    SqlFragment fragment = createRange(condition.getType(), condition.getSearchYear(), condition.getSearchMonth());
    if (StringUtil.hasValue(fragment.getFragment())) {
      builder.append(" AND " + fragment.getFragment());
      for (Object o : fragment.getParameters()) {
        params.add(o);
      }
    }

    if (StringUtil.hasValue(condition.getClientGroup())) {
      builder.append(" AND CLIENT_GROUP = ? ");
      params.add(condition.getClientGroup());
    }

    WebshopConfig config = DIContainer.getWebshopConfig();
    if (config.isShop() && condition.getShopCode().equals(config.getSiteShopCode())) {
      if (StringUtil.hasValue(condition.getPaymentMethodType())) {
        // ショップ個別決済かつサイト管理者のときは支払い方法区分が一致するものを検索
        builder.append(" AND PAYMENT_METHOD_NO IN (");
        builder.append("  SELECT PAYMENT_METHOD_NO FROM PAYMENT_METHOD");
        builder.append("   WHERE PAYMENT_METHOD_TYPE = ?");
        builder.append(" )");
        params.add(condition.getPaymentMethodType());
      }
    } else {
      // それ以外のときはショップコードと支払い方法番号で検索
      // サイト管理者のときはショップコードを条件に含めない(全てのショップを検索)
      if (!condition.getShopCode().equals(config.getSiteShopCode())) {
        builder.append(" AND SHOP_CODE = ?");
        params.add(condition.getShopCode());
      }
      if (!NumUtil.isNull(condition.getPaymentMethodNo())) {
        builder.append(" AND PAYMENT_METHOD_NO = ?");
        params.add(condition.getPaymentMethodNo());
      }
    }

    builder.append("   GROUP BY " + createAmountUnit(condition.getType()));
    builder.append("   ) SALES_AMOUNT ");
    builder.append(" ON ");
    builder.append("   CALENDAR.COUNTED_DATE = SALES_AMOUNT.COUNTED_DATE");
    builder.append(" ORDER BY CALENDAR.COUNTED_DATE ");

    setSqlString(builder.toString());
    setParameters(params.toArray());

  }
}
