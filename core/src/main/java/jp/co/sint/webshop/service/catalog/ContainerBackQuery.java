package jp.co.sint.webshop.service.catalog;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.AbstractQuery;
import jp.co.sint.webshop.data.domain.StockManagementType;
import jp.co.sint.webshop.data.dto.Stock;

import jp.co.sint.webshop.service.CommodityHeadline;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.SqlDialect;
import jp.co.sint.webshop.utility.SqlFragment;
import jp.co.sint.webshop.utility.WebUtil;

public class ContainerBackQuery extends AbstractQuery<CommodityHeadline> {

  private static final long serialVersionUID = 1L;

  public Class<CommodityHeadline> getRowType() {
    return CommodityHeadline.class;
  }

  public ContainerBackQuery(List<CommodityKey> keyList) {
    String length = WebUtil.buildMaxlength(Stock.class, "stockQuantity", null);
    String stockQuantity = "";
    for (Long i = 0L; i < NumUtil.toLong(length); i++) {
      stockQuantity += "9";
    }

    StringBuilder b = new StringBuilder();
    List<Object> params = new ArrayList<Object>();
    b.append(" SELECT CH.SHOP_CODE, ");
    b.append(" CH.ORIGINAL_COMMODITY_CODE, ");
    b.append(" CH.COMBINATION_AMOUNT, ");
    b.append(" (CASE WHEN MIN( ");
    // 有効在庫算出式
//  postgreSQL start    
    //b.append(" CASE WHEN SYSDATE BETWEEN CH.RESERVATION_START_DATETIME AND CH.RESERVATION_END_DATETIME THEN "); // 予約受付中
    b.append(" CASE WHEN "+SqlDialect.getDefault().getCurrentDatetime()+" BETWEEN CH.RESERVATION_START_DATETIME AND CH.RESERVATION_END_DATETIME THEN "); // 予約受付中
//  postgreSQL end    
    // 予約上限なし かつ 在庫管理する場合：(予約上限 - 予約数)
    b.append("    CASE WHEN ST.RESERVATION_LIMIT IS NOT NULL AND CH.STOCK_MANAGEMENT_TYPE IN (?, ?) THEN ");
    params.add(StockManagementType.WITH_QUANTITY.getValue());
    params.add(StockManagementType.WITH_STATUS.getValue());
    b.append("          COALESCE(ST.RESERVATION_LIMIT - ST.RESERVED_QUANTITY, 0) ");
    // そうでなければ：論理的無限(NUMBER(8,0)だから99999999)
    b.append("    ELSE " + stockQuantity + " END ");
//  postgreSQL start    
    //b.append("  WHEN SYSDATE BETWEEN CH.SALE_START_DATETIME AND CH.SALE_END_DATETIME THEN"); // 販売中
    b.append("  WHEN "+SqlDialect.getDefault().getCurrentDatetime()+" BETWEEN CH.SALE_START_DATETIME AND CH.SALE_END_DATETIME THEN"); // 販売中
//  postgreSQL end    
    // 在庫管理しない場合：論理的無限(NUMBER(8,0)だから99999999)
    b.append("    CASE WHEN CH.STOCK_MANAGEMENT_TYPE IN (?, ?) THEN " + stockQuantity);
    params.add(StockManagementType.NONE.getValue());
    params.add(StockManagementType.NOSTOCK.getValue());
    // 在庫管理する場合：(在庫数 - 引当数 - 予約数)
    b.append("    WHEN CH.STOCK_MANAGEMENT_TYPE IN (?, ?) ");
    params.add(StockManagementType.WITH_QUANTITY.getValue());
    params.add(StockManagementType.WITH_STATUS.getValue());
    b.append(" AND (ST.STOCK_QUANTITY >= 0 AND ST.ALLOCATED_QUANTITY >= 0 AND ST.RESERVED_QUANTITY >= 0)");
    b.append("         THEN ST.STOCK_QUANTITY - ST.ALLOCATED_QUANTITY - ST.RESERVED_QUANTITY ");
    b.append("    ELSE 0 END ");
    b.append("  ELSE 0 END "); // 販売期間外
    b.append(" ) > 0 THEN 0 ");
    b.append(" WHEN MAX ( ");
    // 有効在庫算出式(上と同じ)
//  postgreSQL start    
    //b.append(" CASE WHEN SYSDATE BETWEEN CH.RESERVATION_START_DATETIME AND CH.RESERVATION_END_DATETIME THEN ");
    b.append(" CASE WHEN "+SqlDialect.getDefault().getCurrentDatetime()+" BETWEEN CH.RESERVATION_START_DATETIME AND CH.RESERVATION_END_DATETIME THEN ");
//  postgreSQL end    
    b.append("    CASE WHEN ST.RESERVATION_LIMIT IS NOT NULL AND CH.STOCK_MANAGEMENT_TYPE IN (?, ?) THEN ");
    params.add(StockManagementType.WITH_QUANTITY.getValue());
    params.add(StockManagementType.WITH_STATUS.getValue());
    b.append("          COALESCE(ST.RESERVATION_LIMIT - ST.RESERVED_QUANTITY, 0) ");
    b.append("    ELSE " + stockQuantity + " END ");
//  postgreSQL start    
    //b.append("  WHEN SYSDATE BETWEEN CH.SALE_START_DATETIME AND CH.SALE_END_DATETIME THEN ");
    b.append("  WHEN "+SqlDialect.getDefault().getCurrentDatetime()+" BETWEEN CH.SALE_START_DATETIME AND CH.SALE_END_DATETIME THEN ");   
//  postgreSQL end    
    b.append("    CASE WHEN CH.STOCK_MANAGEMENT_TYPE IN (?, ?) THEN " + stockQuantity);
    b.append("    WHEN CH.STOCK_MANAGEMENT_TYPE IN (?, ?) ");
    params.add(StockManagementType.NONE.getValue());
    params.add(StockManagementType.NOSTOCK.getValue());
    params.add(StockManagementType.WITH_QUANTITY.getValue());
    params.add(StockManagementType.WITH_STATUS.getValue());
    b.append(" AND (ST.STOCK_QUANTITY >= 0 AND ST.ALLOCATED_QUANTITY >= 0 AND ST.RESERVED_QUANTITY >= 0)");
    b.append("         THEN ST.STOCK_QUANTITY - ST.ALLOCATED_QUANTITY - ST.RESERVED_QUANTITY ");
    b.append("    ELSE 0 END ");
    b.append("  ELSE 0 END ");
    // 10.1.1 10014 修正 ここから
    // b.append(" ) = 0 THEN 2 ELSE 1 END) AS STOCK_STATUS, ");
    b.append(" ) <= 0 THEN 2 ELSE 1 END) AS STOCK_STATUS, ");
    // 10.1.1 10014 修正 ここまで
    b.append(" SH.SHORT_SHOP_NAME AS SHOP_NAME, ");
    b.append(" CH.COMMODITY_CODE, ");
    b.append(" CH.COMMODITY_NAME, ");
    // 2012/11/17 促销对应 ob add start
    b.append(" CH.SET_COMMODITY_FLG, ");
    b.append(" COUNT_SET_COMPOSITION_FUNC(CH.SHOP_CODE, CH.COMMODITY_CODE) AS RELATED_COMPOSITION_COUNT,");
	// 2012/11/17 促销对应 ob add end
    b.append(" CH.COMMODITY_SEARCH_WORDS, ");
    b.append(" MIN(CH.UNIT_PRICE) UNIT_PRICE, ");
    b.append(" MIN (CH.RETAIL_PRICE ) RETAIL_PRICE, ");
    b.append(" CH.COMMODITY_TAX_TYPE, ");
    b.append(" CH.COMMODITY_TYPE, ");
    b.append(" CH.STOCK_MANAGEMENT_TYPE, ");
    // 20140526 hdh add start 品店精选排序
    b.append(" CH.CHOSEN_SORT_RANK, ");
    // 20140526 hdh add end
//  postgreSQL start
//    b.append(" (CASE WHEN SYSDATE BETWEEN CH.RESERVATION_START_DATETIME AND CH.RESERVATION_END_DATETIME THEN 0 ");
//    b.append("       WHEN SYSDATE BETWEEN CH.SALE_START_DATETIME AND CH.SALE_END_DATETIME THEN 1 ");
    b.append(" (CASE WHEN "+SqlDialect.getDefault().getCurrentDatetime()+" BETWEEN CH.RESERVATION_START_DATETIME AND CH.RESERVATION_END_DATETIME THEN 0 ");
    b.append("       WHEN "+SqlDialect.getDefault().getCurrentDatetime()+" BETWEEN CH.SALE_START_DATETIME AND CH.SALE_END_DATETIME THEN 1 ");
//  postgreSQL end    
    b.append("       ELSE 2 END) AS SALE_STATUS, ");
    b.append(" (CASE WHEN CH.SALE_FLG = 1 AND DT.DISPLAY_FLG = 1 ");
    b.append(" AND COUNT_CATEGORY_FUNC(CH.SHOP_CODE, CH.COMMODITY_CODE) > 0 THEN 1 ");
    b.append("       ELSE 0 END) AS SALE_TYPE, ");
    b.append(" COUNT_RELATED_A_FUNC(CH.SHOP_CODE, CH.COMMODITY_CODE) AS RELATED_COMMODITY_A_COUNT, ");
    b.append(" COUNT_RELATED_B_FUNC(CH.SHOP_CODE, CH.COMMODITY_CODE) AS RELATED_COMMODITY_B_COUNT, ");
    b.append(" COUNT_CATEGORY_FUNC( CH.SHOP_CODE, CH.COMMODITY_CODE) AS RELATED_CATEGORY_COUNT, ");
    b.append(" COUNT_TAG_FUNC(      CH.SHOP_CODE, CH.COMMODITY_CODE) AS RELATED_TAG_COUNT, ");
    b.append(" COUNT_CAMPAIGN_FUNC( CH.SHOP_CODE, CH.COMMODITY_CODE) AS RELATED_CAMPAIGN_COUNT, ");
    b.append(" COUNT_GIFT_FUNC(     CH.SHOP_CODE, CH.COMMODITY_CODE) AS RELATED_GIFT_COUNT ");
    b.append(" ");
    b.append(" FROM COMMODITY_LIST_VIEW CH ");
    b.append(" INNER JOIN DELIVERY_TYPE DT ON DT.SHOP_CODE = CH.SHOP_CODE AND DT.DELIVERY_TYPE_NO = CH.DELIVERY_TYPE_NO ");
    b.append(" INNER JOIN SHOP SH ON SH.SHOP_CODE = CH.SHOP_CODE ");
    b.append(" INNER JOIN STOCK ST ON  ST.SHOP_CODE = CH.SHOP_CODE AND ST.COMMODITY_CODE = CH.COMMODITY_CODE ");

    if (keyList != null && keyList.size() > 0) {
      SqlFragment fragment = CommodityKey.buildInClause("CH.SHOP_CODE", "CH.COMMODITY_CODE", keyList);
      b.append("WHERE ");
      b.append(fragment.getFragment());
      b.append(" GROUP BY CH.SHOP_CODE, SH.SHOP_NAME, SH.SHORT_SHOP_NAME, CH.COMMODITY_CODE,CH.COMMODITY_TYPE, ");
      b.append("CH.COMMODITY_NAME, CH.REPRESENT_SKU_CODE, ");
      b.append("          CH.STOCK_STATUS_NO, CH.STOCK_MANAGEMENT_TYPE, CH.COMMODITY_TAX_TYPE, CH.COMMODITY_DESCRIPTION_PC, ");
      b.append("          CH.COMMODITY_DESCRIPTION_MOBILE, CH.COMMODITY_SEARCH_WORDS, ");
      b.append("CH.SALE_START_DATETIME, CH.SALE_END_DATETIME, ");
      b.append("          CH.CHANGE_UNIT_PRICE_DATETIME, CH.DISCOUNT_PRICE_START_DATETIME, CH.DISCOUNT_PRICE_END_DATETIME, ");
      b.append("          CH.RESERVATION_START_DATETIME, CH.RESERVATION_END_DATETIME, CH.DELIVERY_TYPE_NO, CH.LINK_URL, ");
      b.append("          CH.RECOMMEND_COMMODITY_RANK, CH.COMMODITY_STANDARD1_NAME, CH.COMMODITY_STANDARD2_NAME, ");
      b.append("          CH.COMMODITY_POINT_RATE, CH.COMMODITY_POINT_START_DATETIME, CH.COMMODITY_POINT_END_DATETIME, ");
      b.append("          CH.SALE_FLG, CH.DISPLAY_CLIENT_TYPE, CH.ARRIVAL_GOODS_FLG, DT.DISPLAY_FLG, ");
      b.append("          ST.SHOP_CODE, ST.COMMODITY_CODE, ");
      b.append("          CH.ORIGINAL_COMMODITY_CODE,CH.COMBINATION_AMOUNT ");
      // 20140526 hdh add start 品店精选排序
      b.append("          ,CH.CHOSEN_SORT_RANK ");
      // 20140526 hdh add end
      b.append(" ");
      // 2012/11/17 促销对应 ob add start
      b.append(", CH.SET_COMMODITY_FLG");
	  // 2012/11/17 促销对应 ob add end
      b.append(" HAVING 1 = 1  ");
      b.append(" AND ((CASE WHEN COUNT(COALESCE(CH.AVAILABLE_STOCK_QUANTITY, 1)) ");
      b.append("- SUM(CASE WHEN (COALESCE(CH.AVAILABLE_STOCK_QUANTITY,1 ) > 0 ");
      b.append(" OR COALESCE(CH.AVAILABLE_STOCK_QUANTITY,1) = -1) THEN 1 ELSE 0 END) = 0 ");
      b.append(" OR CH.STOCK_MANAGEMENT_TYPE IN (0,1) THEN 0 WHEN SUM(CASE WHEN COALESCE(CH.AVAILABLE_STOCK_QUANTITY,1) > 0 ");
      b.append(" OR COALESCE(CH.AVAILABLE_STOCK_QUANTITY,1) = -1 THEN 1 ELSE 0 END) = 0 THEN 2 ELSE 1 END) IN (0,1,2)) ");

      for (Object value : fragment.getParameters()) {
        params.add(value);
      }
      fragment.setParameters(params.toArray());
      this.setParameters(fragment.getParameters());
    }
    this.setSqlString(b.toString());
  }

}
