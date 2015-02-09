package jp.co.sint.webshop.service.catalog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.co.sint.webshop.data.AbstractQuery;
import jp.co.sint.webshop.data.domain.ArrivalGoodsFlg;
import jp.co.sint.webshop.data.domain.DisplayFlg;
import jp.co.sint.webshop.data.domain.SaleFlg;
import jp.co.sint.webshop.data.domain.SaleStatus;
import jp.co.sint.webshop.data.domain.SkuStatus;
import jp.co.sint.webshop.data.domain.StockManagementType;
import jp.co.sint.webshop.service.CommodityHeadline;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.SqlDialect;
import jp.co.sint.webshop.utility.SqlFragment;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.utility.SqlDialect.LikeClauseOption;

public class CommodityListSearchQuery extends AbstractQuery<CommodityHeadline> {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private String[] defaultStatus = new String[] {
    "-1"
  };

  private static final String SYSDATE = SqlDialect.getDefault().getCurrentDatetime();
  
  // 在庫状況検索のCASE式
  private static final String STOCK_STATUS_CASE_EXPRESSION = ""
    + " (CASE  "
    // 全規格在庫あり (在庫あり規格数 - 規格数 = 0 OR 在庫管理区分 IN (在庫管理しない, 在庫なし販売する))
    + "   WHEN COUNT(COALESCE(CH.AVAILABLE_STOCK_QUANTITY,1))  "
    + "   - SUM(CASE  "
    + "           WHEN (COALESCE(CH.AVAILABLE_STOCK_QUANTITY,1) > 0 OR COALESCE(CH.AVAILABLE_STOCK_QUANTITY,1) = -1) THEN 1 "
    + "           ELSE 0  "
    + "         END) = 0 OR CH.STOCK_MANAGEMENT_TYPE IN (" + StockManagementType.NONE.getValue() 
    + "," + StockManagementType.NOSTOCK.getValue() + ") THEN " + SkuStatus.ALL.getValue()
    // 全規格在庫なし (在庫あり規格数 = 0)
    + "   WHEN SUM(CASE  "
    + "             WHEN COALESCE(CH.AVAILABLE_STOCK_QUANTITY,1) > 0 OR COALESCE(CH.AVAILABLE_STOCK_QUANTITY,1) <> -1 THEN 1  "
    + "             ELSE 0  "
    + "            END) = 0 THEN " + SkuStatus.NONE.getValue()
    // 一部規格在庫あり (0 < 在庫あり規格数 < 規格数)
    + "   ELSE " + SkuStatus.SOME.getValue()
    + "  END) ";
  
  // 適用期間検索のCASE式
  private static final String SALE_STATUS_CASE_EXPRESSION = ""
    + " (CASE  " 
    // 予約期間中 (システム日付 BETWEEN 予約開始日時 AND 予約終了日時)
    + "  WHEN " + SYSDATE 
    + " BETWEEN CH.RESERVATION_START_DATETIME AND CH.RESERVATION_END_DATETIME THEN "
    + SaleStatus.ACCESPTING_RESERVATION.getValue() 
    // 販売中 (システム日付 BETWEEN 販売開始日 AND 販売終了日時)
    + "  WHEN " + SYSDATE
    + " BETWEEN CH.SALE_START_DATETIME AND CH.SALE_END_DATETIME THEN " + SaleStatus.ON_SALE.getValue() 
    // 販売期間外
    + "  ELSE  " + SaleStatus.DISCONTINUED.getValue() 
    + " END) ";
  
  private static final String SALE_TYPE_CASE_EXPRESSION = ""
    + " (CASE  " 
    + "    WHEN CH.SALE_FLG = " + SaleFlg.FOR_SALE.getValue() 
    + "         AND DT.DISPLAY_FLG = " + DisplayFlg.VISIBLE.getValue()
    + "         AND COUNT_CATEGORY_FUNC(CH.SHOP_CODE, CH.COMMODITY_CODE) > 0 THEN 1 " 
    + "    ELSE 0 " 
    + "  END) ";

  private static final String LIST_SELECT_CLAUSE = ""
    + " SELECT CH.SHOP_CODE " 
    + "   ," + STOCK_STATUS_CASE_EXPRESSION + " AS STOCK_STATUS " 
    + "   ,SH.SHORT_SHOP_NAME AS SHOP_NAME " 
    + "   ,CH.COMMODITY_CODE " 
    + "   ,CH.COMMODITY_NAME "
    + "   ,CH.COMMODITY_SEARCH_WORDS " 
    + "   ,MIN " + "   (CH.UNIT_PRICE " + "   ) UNIT_PRICE " 
    + "   ,MIN " + "   (CH.RETAIL_PRICE " + "   ) RETAIL_PRICE " 
    + "   ,CH.COMMODITY_TAX_TYPE " 
    + "   ,CH.STOCK_MANAGEMENT_TYPE "
    + "   ," + SALE_STATUS_CASE_EXPRESSION + " AS SALE_STATUS " 
    + "   ," + SALE_TYPE_CASE_EXPRESSION  + " AS SALE_TYPE "
    + "   ,COUNT_RELATED_A_FUNC(CH.SHOP_CODE, CH.COMMODITY_CODE) AS RELATED_COMMODITY_A_COUNT"
    + "   ,COUNT_RELATED_B_FUNC(CH.SHOP_CODE, CH.COMMODITY_CODE) AS RELATED_COMMODITY_B_COUNT"
    + "   ,COUNT_CATEGORY_FUNC(CH.SHOP_CODE, CH.COMMODITY_CODE) AS RELATED_CATEGORY_COUNT"
    + "   ,COUNT_TAG_FUNC(CH.SHOP_CODE, CH.COMMODITY_CODE) AS RELATED_TAG_COUNT"
    + "   ,COUNT_CAMPAIGN_FUNC(CH.SHOP_CODE, CH.COMMODITY_CODE) AS RELATED_CAMPAIGN_COUNT"
    + "   ,COUNT_GIFT_FUNC(CH.SHOP_CODE, CH.COMMODITY_CODE) AS RELATED_GIFT_COUNT";

  private static String getQueryPartClause(boolean isNotSafetyStock) {
    String query = " FROM ";
    if (isNotSafetyStock) {
      query = query + "(SELECT * FROM COMMODITY_LIST_VIEW CV WHERE ((CV.AVAILABLE_STOCK_QUANTITY - CV.STOCK_THRESHOLD < 0)"
      + " AND CV.STOCK_MANAGEMENT_TYPE IN(2,3)) OR CV.STOCK_MANAGEMENT_TYPE IN(0,1)) ";
    } else {
      query = query + " COMMODITY_LIST_VIEW ";
    }

    query = query + "CH LEFT OUTER JOIN DELIVERY_TYPE DT ON DT.SHOP_CODE = CH.SHOP_CODE " 
    + " AND DT.DELIVERY_TYPE_NO = CH.DELIVERY_TYPE_NO LEFT OUTER "
    + " JOIN SHOP SH ON SH.SHOP_CODE = CH.SHOP_CODE "
    + " WHERE 1 = 1 ";
    return query;
  }

  private static final String GROUPING_PART_CLAUSE = ""
    + " GROUP BY CH.SHOP_CODE " 
    + "   ,SH.SHOP_NAME " 
    + "   ,SH.SHORT_SHOP_NAME"
    + "   ,CH.COMMODITY_CODE "
    + "   ,CH.COMMODITY_NAME "
    + "   ,CH.REPRESENT_SKU_CODE "
    + "   ,CH.STOCK_STATUS_NO "
    + "   ,CH.STOCK_MANAGEMENT_TYPE "
    + "   ,CH.COMMODITY_TAX_TYPE "
    + "   ,CH.COMMODITY_DESCRIPTION_PC "
    + "   ,CH.COMMODITY_DESCRIPTION_MOBILE "
    + "   ,CH.COMMODITY_SEARCH_WORDS "
    + "   ,CH.SALE_START_DATETIME "
    + "   ,CH.SALE_END_DATETIME "
    + "   ,CH.CHANGE_UNIT_PRICE_DATETIME "
    + "   ,CH.DISCOUNT_PRICE_START_DATETIME "
    + "   ,CH.DISCOUNT_PRICE_END_DATETIME "
    + "   ,CH.RESERVATION_START_DATETIME "
    + "   ,CH.RESERVATION_END_DATETIME "
    + "   ,CH.DELIVERY_TYPE_NO "
    + "   ,CH.LINK_URL "
    + "   ,CH.RECOMMEND_COMMODITY_RANK "
    + "   ,CH.COMMODITY_STANDARD1_NAME "
    + "   ,CH.COMMODITY_STANDARD2_NAME "
    + "   ,CH.COMMODITY_POINT_RATE "
    + "   ,CH.COMMODITY_POINT_START_DATETIME "
    + "   ,CH.COMMODITY_POINT_END_DATETIME "
    + "   ,CH.SALE_FLG "
    + "   ,CH.DISPLAY_CLIENT_TYPE "
    + "   ,CH.ARRIVAL_GOODS_FLG "
    + "   ,DT.DISPLAY_FLG  " 
    + " HAVING 1 = 1 ";
  
  public CommodityListSearchQuery(CommodityListSearchCondition condition) {
    this(condition, LIST_SELECT_CLAUSE);
  }

  public CommodityListSearchQuery(CommodityListSearchCondition condition, String query) {
    this(condition, query, GROUPING_PART_CLAUSE);
  }

  public CommodityListSearchQuery(CommodityListSearchCondition condition, String query, String groupingPart) {

    StringBuilder builder = new StringBuilder();
    List<Object> params = new ArrayList<Object>();

    builder.append(query);
    builder.append(getQueryPartClause(condition.isNotSafetyStock()));      

    if (StringUtil.hasValue(condition.getSearchShopCode())) {
      builder.append(" AND CH.SHOP_CODE = ? ");
      params.add(condition.getSearchShopCode());
    }

    // 商品コードの範囲検索
    // SKUコードの完全一致検索
    // 商品コードの部分一致検索
    // 検索キーワードの部分一致検索
    // 販売期間開始日の範囲検索
    // 販売期間終了日の範囲検索
    // 適用期間の検索
    // 表示クライアントの検索
    // 入荷お知らせの検索
    buildConditions1(builder, params, condition);

    // 在庫状況の検索
    // 在庫数の範囲検索
    // 全ての規格の在庫が範囲内であるものを検索
    // 販売状態の検索
    builder.append(groupingPart);
    buildConditions2(builder, params, condition);

    builder.append(" ORDER BY CH.SHOP_CODE, CH.COMMODITY_CODE, CH.COMMODITY_NAME ");

    setSqlString(builder.toString());
    setParameters(params.toArray());
    setPageNumber(condition.getCurrentPage());
    setPageSize(condition.getPageSize());

  }

  private void buildConditions1(StringBuilder builder, List<Object> params, CommodityListSearchCondition condition) {
    SqlDialect dialect = SqlDialect.getDefault();

    // 商品コードの範囲検索
    SqlFragment commodityCodeFragment = SqlDialect.getDefault().createRangeClause("CH.COMMODITY_CODE",
        condition.getSearchCommodityCodeStart(), condition.getSearchCommodityCodeEnd());
    if (StringUtil.hasValue(commodityCodeFragment.getFragment())) {
      builder.append(" AND " + commodityCodeFragment.getFragment());
      for (Object o : commodityCodeFragment.getParameters()) {
        params.add(o);
      }
    }

    // SKUコードの検索
    if (StringUtil.hasValue(condition.getSearchSkuCode())) {
      builder.append(" AND CH.COMMODITY_CODE IN ( SELECT COMMODITY_CODE FROM COMMODITY_DETAIL WHERE SKU_CODE = ?)  ");
      params.add(condition.getSearchSkuCode());
    }

    // 商品名の部分一致検索
    if (StringUtil.hasValue(condition.getSearchCommodityName())) {
      SqlFragment fragment = SqlDialect.getDefault().createLikeClause("CH.COMMODITY_NAME", condition.getSearchCommodityName(),
          LikeClauseOption.PARTIAL_MATCH);
      if (StringUtil.hasValue(fragment.getFragment())) {
        builder.append(" AND " + fragment.getFragment());
        for (Object o : fragment.getParameters()) {
          params.add(o);
        }
      }
    }

    // 検索キーワードの部分一致検索
    if (StringUtil.hasValue(condition.getSearchCommoditySearchWords())) {
      // 10.1.6 10252 修正 ここから
      //SqlFragment fragment = SqlDialect.getDefault().createLikeClause("CH.COMMODITY_SEARCH_WORDS",
      //    condition.getSearchCommoditySearchWords(), LikeClauseOption.PARTIAL_MATCH);
      SqlFragment fragment = SqlDialect.getDefault().createLikeClause("CH.SHADOW_SEARCH_WORDS",
          StringUtil.toSearchKeywords(condition.getSearchCommoditySearchWords()), LikeClauseOption.PARTIAL_MATCH, false);
      // 10.1.6 10252 修正 ここまで
      if (StringUtil.hasValue(fragment.getFragment())) {
        builder.append(" AND " + fragment.getFragment());
        for (Object o : fragment.getParameters()) {
          params.add(o);
        }
      }
    }

    // 販売期間開始日の範囲検索
    SqlFragment saleStartDatetimeFragment = SqlDialect.getDefault().createDateRangeClause("CH.SALE_START_DATETIME",
        DateUtil.fromString(condition.getSearchSaleStartDateRangeFrom(), true),
        DateUtil.fromString(condition.getSearchSaleStartDateRangeTo(), true));
    if (StringUtil.hasValue(saleStartDatetimeFragment.getFragment())) {
      builder.append(" AND " + saleStartDatetimeFragment.getFragment());
      for (Object o : saleStartDatetimeFragment.getParameters()) {
        params.add(o);
      }
    }

    // 販売期間終了日の範囲検索
    SqlFragment saleEndDatetimeFragment = SqlDialect.getDefault().createDateRangeClause("CH.SALE_END_DATETIME",
        DateUtil.fromString(condition.getSearchSaleEndDateRangeFrom(), true),
        DateUtil.fromString(condition.getSearchSaleEndDateRangeTo(), true));
    if (StringUtil.hasValue(saleEndDatetimeFragment.getFragment())) {
      builder.append(" AND " + saleEndDatetimeFragment.getFragment());
      for (Object o : saleEndDatetimeFragment.getParameters()) {
        params.add(o);
      }
    }

    // 適用期間の検索
    String[] saleStatus;
    if (StringUtil.hasValueAnyOf(condition.getSearchSaleStatus())) {
      saleStatus = condition.getSearchSaleStatus();
    } else {
      saleStatus = defaultStatus;
    }
    SqlFragment searchSaleStatusFragment = dialect.createInClause(SALE_STATUS_CASE_EXPRESSION, (Object[]) saleStatus);
    builder.append(" AND ( " + searchSaleStatusFragment.getFragment() + " ) ");
    params.addAll(Arrays.asList(searchSaleStatusFragment.getParameters()));

    // 表示クライアントの検索
    String[] displayClientType;
    if (StringUtil.hasValueAnyOf(condition.getSearchDisplayClientType())) {
      displayClientType = condition.getSearchDisplayClientType();
    } else {
      displayClientType = defaultStatus;
    }
    SqlFragment searchDisplayClientFrag = dialect.createInClause("CH.DISPLAY_CLIENT_TYPE", (Object[]) displayClientType);
    builder.append(" AND " + searchDisplayClientFrag.getFragment());
    params.addAll(Arrays.asList(searchDisplayClientFrag.getParameters()));

    // 入荷お知らせの検索
    if (ArrivalGoodsFlg.isValid(condition.getSearchArrivalGoods())) {
      builder.append(" AND CH.ARRIVAL_GOODS_FLG = ? ");
      params.add(condition.getSearchArrivalGoods());
    }

  }

  private void buildConditions2(StringBuilder builder, List<Object> params, CommodityListSearchCondition condition) {
    SqlDialect dialect = SqlDialect.getDefault();
    
    // 在庫状況の検索
    String[] stockStatus;
    if (StringUtil.hasValueAnyOf(condition.getSearchStockStatus())) {
      stockStatus = condition.getSearchStockStatus();
    } else {
      stockStatus = defaultStatus;
    }
    SqlFragment searchStockStatusFragment = dialect.createInClause(STOCK_STATUS_CASE_EXPRESSION, (Object[]) stockStatus);
    builder.append(" AND ( " + searchStockStatusFragment.getFragment() + " ) ");
    params.addAll(Arrays.asList(searchStockStatusFragment.getParameters()));

    // 在庫数の範囲検索
    // 全ての規格の在庫が範囲内であるものを検索
    // searchStockQuantityEndが値を持つ場合、「在庫管理する」商品のみを検索する
    // searchStockQuantityEndが値を持たない場合、「在庫管理しない」商品は有効在庫数に関わらず検索する
    if (StringUtil.hasValueAnyOf(condition.getSearchStockQuantityStart(), condition.getSearchStockQuantityEnd())) {
      String managementStockType = StockManagementType.WITH_QUANTITY.getValue() + ", " + StockManagementType.WITH_STATUS.getValue();
      String notManagementStockType  = StockManagementType.NONE.getValue() + ", " + StockManagementType.NOSTOCK.getValue();
      if (StringUtil.hasValue(condition.getSearchStockQuantityStart())
          && StringUtil.hasValue(condition.getSearchStockQuantityEnd())) {
        builder.append(" AND SUM(CASE WHEN (COALESCE(CH.AVAILABLE_STOCK_QUANTITY, 1) BETWEEN ? AND ?"
            + " AND CH.STOCK_MANAGEMENT_TYPE IN (" + managementStockType + ")) THEN 1 ELSE 0 END) > 0 ");
        params.add(condition.getSearchStockQuantityStart());
        params.add(condition.getSearchStockQuantityEnd());
      } else if (StringUtil.hasValue(condition.getSearchStockQuantityStart())) {
        builder.append(" AND SUM(CASE WHEN ((COALESCE(CH.AVAILABLE_STOCK_QUANTITY, 1) >= ?"
            + " AND CH.STOCK_MANAGEMENT_TYPE IN (" + managementStockType + "))"
            + " OR CH.STOCK_MANAGEMENT_TYPE IN (" + notManagementStockType + ")) THEN 1 ELSE 0 END) > 0 ");
        params.add(condition.getSearchStockQuantityStart());
      } else if (StringUtil.hasValue(condition.getSearchStockQuantityEnd())) {
        builder.append(" AND SUM(CASE WHEN (COALESCE(CH.AVAILABLE_STOCK_QUANTITY, 1) <= ?"
            + " AND CH.STOCK_MANAGEMENT_TYPE IN (" + managementStockType + ")) THEN 1 ELSE 0 END) > 0 ");
        params.add(condition.getSearchStockQuantityEnd());
      }
    }
    
    // 販売状態の検索
    if (StringUtil.hasValue(condition.getSearchSaleType()) && !condition.getSearchSaleType().equals("2")) {
      builder.append(" AND " + SALE_TYPE_CASE_EXPRESSION + " = ? ");
      params.add(condition.getSearchSaleType());
    }

  }

  public Class<CommodityHeadline> getRowType() {
    return CommodityHeadline.class;
  }

}
