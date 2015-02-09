package jp.co.sint.webshop.service.catalog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.co.sint.webshop.data.AbstractQuery;
import jp.co.sint.webshop.data.domain.CommodityType;
import jp.co.sint.webshop.data.domain.SetCommodityFlg;
import jp.co.sint.webshop.data.domain.StockManagementType;
import jp.co.sint.webshop.data.domain.TmallSaleStatus;
import jp.co.sint.webshop.utility.ArrayUtil;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.SqlDialect;
import jp.co.sint.webshop.utility.SqlFragment;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.utility.SqlDialect.LikeClauseOption;

public class TmallCommodityKeyBackQuery extends AbstractQuery<CommodityKey> {

  private static final long serialVersionUID = 1L;

  private static final String[] DEFAULT_STATUS = new String[] {
    "-1"
  };

  public Class<CommodityKey> getRowType() {
    return CommodityKey.class;
  }

  public TmallCommodityKeyBackQuery() {
    this(new CommodityListSearchCondition());
  }

  private StringBuilder getInitilizedBuffer() {
    StringBuilder b = new StringBuilder();
    b.append("SELECT /*+ INDEX(CH C_COMMODITY_HEADER_IX_BACK) */ ");
    b.append("CH.SHOP_CODE, CH.COMMODITY_CODE FROM C_COMMODITY_HEADER CH WHERE 1 = 1 ");
    return b;
  }

  public TmallCommodityKeyBackQuery(CommodityListSearchCondition condition) {

    List<Object> params = new ArrayList<Object>();
    StringBuilder b = getInitilizedBuffer();
    SqlDialect dialect = SqlDialect.getDefault();
    if(StringUtil.hasValue(condition.getCombination())){
      if(condition.getCombination().equals("1")){
        b.append("AND CH.ORIGINAL_COMMODITY_CODE != ?");
        params.add("");
      }else if(condition.getCombination().equals("0")){
        b.append("AND CH.ORIGINAL_COMMODITY_CODE is null ");
      }else{
        b.append("AND 1=1");
      }
    }
    
    // ショップコード指定
    if (StringUtil.hasValue(condition.getSearchShopCode())) {
      b.append("AND CH.SHOP_CODE = ? ");
      params.add(condition.getSearchShopCode());
    }
    
    // add by tangweihui 2012-11-22 start
    //增加COMMODITY_TYPE
    if (CommodityType.isValid(condition.getSearchCommodityType()) && !condition.getSearchCommodityType().equals(CommodityType.ALL.getValue())) {
      b.append(" AND CH.COMMODITY_TYPE = ? ");
      params.add(condition.getSearchCommodityType());
    }
    // add by tangweihui 2012-11-22 end

    // add by yyq 2012-12-17 start
    if (!StringUtil.isNullOrEmpty(condition.getSearchTmallMjsCommodityflg()) && condition.getSearchTmallMjsCommodityflg().equals("1")) {
      b.append(" AND CH.TMALL_MJS_FLG = ? ");
      params.add(1L);
    }
    if (!StringUtil.isNullOrEmpty(condition.getSearchTmallMjsCommodityflg()) && !condition.getSearchTmallMjsCommodityflg().equals("1") &&!condition.getSearchTmallMjsCommodityflg().equals("2")) {
      b.append(" AND (CH.TMALL_MJS_FLG = ? or CH.TMALL_MJS_FLG is null)");
      params.add(0L);
    }
    // add by yyq 2012-12-17 end
    
    // SKUコード指定
    if (StringUtil.hasValue(condition.getSearchSkuCode())) {
      b.append("AND EXISTS (SELECT /*+ INDEX(CD C_COMMODITY_DETAIL_PK)*/ 'OK' FROM C_COMMODITY_DETAIL CD ");
      b.append("WHERE CH.SHOP_CODE = CD.SHOP_CODE AND CH.COMMODITY_CODE = CD.COMMODITY_CODE AND CD.SKU_CODE = ?) ");
      params.add(condition.getSearchSkuCode());
    }

    // 表示クライアント指定
    String[] displayClientType = DEFAULT_STATUS;
    if (StringUtil.hasValueAnyOf(condition.getSearchDisplayClientType())) {
      displayClientType = ArrayUtil.immutableCopy(condition.getSearchDisplayClientType());
    }
    Object[] values = displayClientType;
    Arrays.sort(values);
    //delete by os014 2012-02-10
//    if (!Arrays.equals(values, new Object[] {
//        DisplayClientType.ALL.getValue(), DisplayClientType.PC.getValue(), DisplayClientType.MOBILE.getValue(),
//    })) {
//      SqlFragment frag = dialect.createInClause("CH.DISPLAY_CLIENT_TYPE", values);
//      b.append("AND ");
//      b.append(frag.getFragment());
//      for (Object o : frag.getParameters()) {
//        params.add(o);
//      }
//    }

    // 商品コード
    if (StringUtil.hasValueAnyOf(condition.getSearchCommodityCodeStart(), condition.getSearchCommodityCodeEnd())) {
      SqlFragment frag = dialect.createRangeClause("CH.COMMODITY_CODE", condition.getSearchCommodityCodeStart(), condition
          .getSearchCommodityCodeEnd());
      b.append("AND ");
      b.append(frag.getFragment());
      for (Object o : frag.getParameters()) {
        params.add(o);
      }
    }

    // 商品名称
    if (StringUtil.hasValue(condition.getSearchCommodityName())) {
      SqlFragment frag = dialect.createLikeClause("CH.COMMODITY_NAME", condition.getSearchCommodityName(),
          LikeClauseOption.PARTIAL_MATCH, false);
      b.append("AND ");
      b.append(frag.getFragment());
      for (Object o : frag.getParameters()) {
        params.add(o);
      }
    }

    // 商品キーワード
    if (StringUtil.hasValue(condition.getSearchCommoditySearchWords())) {
      // 10.1.3 10112 修正 ここから
      //update by os014 2012-02-10 begin
//      SqlFragment frag = dialect.createLikeClause("CH.COMMODITY_SEARCH_WORDS",
//          StringUtil.toSearchKeywords(condition.getSearchCommoditySearchWords()), LikeClauseOption.PARTIAL_MATCH, false);
      SqlFragment frag = dialect.createLikeClause("CH.COMMODITY_SEARCH_WORDS", condition.getSearchCommoditySearchWords(),
          LikeClauseOption.PARTIAL_MATCH, false);
    //update by os014 2012-02-10 end
      // 10.1.3 10112 修正 ここまで
      b.append("AND ");
      b.append(frag.getFragment());
      for (Object o : frag.getParameters()) {
        params.add(o);
      }
    }
    

    
    buildSalesConditions(condition, b, params);

    buildStockConditions(condition, b, params);
    
    //20120323 os013 add start
    b.append(" ORDER BY CH.COMMODITY_CODE");
    //20120323 os013 add end
    
    setSqlString(b.toString());
    setParameters(params.toArray());
    setMaxFetchSize(condition.getMaxFetchSize());
    setPageNumber(condition.getCurrentPage());
    setPageSize(condition.getPageSize());
  }

  private static void buildSalesConditions(CommodityListSearchCondition condition, StringBuilder b, List<Object> params) {

    SqlDialect dialect = SqlDialect.getDefault();

    // 販売開始日
    if (StringUtil.hasValueAnyOf(condition.getSearchSaleStartDateRangeFrom(), condition.getSearchSaleStartDateRangeTo())) {
      SqlFragment frag = dialect.createRangeClause("CH.SALE_START_DATETIME", DateUtil.fromString(condition
          .getSearchSaleStartDateRangeFrom(), true), DateUtil.fromString(condition.getSearchSaleStartDateRangeTo(), true));
      b.append("AND ");
      b.append(frag.getFragment());
      for (Object o : frag.getParameters()) {
        params.add(o);
      }
    }

    // 販売終了日
    if (StringUtil.hasValueAnyOf(condition.getSearchSaleEndDateRangeFrom(), condition.getSearchSaleEndDateRangeTo())) {
      SqlFragment frag = dialect.createRangeClause("CH.SALE_END_DATETIME", DateUtil.fromString(condition
          .getSearchSaleEndDateRangeFrom(), true), DateUtil.fromString(condition.getSearchSaleEndDateRangeTo(), true));
      b.append("AND ");
      b.append(frag.getFragment());
      for (Object o : frag.getParameters()) {
        params.add(o);
      }
    }

    // 適用期間
    String[] searchSaleStatus = DEFAULT_STATUS;
    if (StringUtil.hasValueAnyOf(condition.getSearchSaleStatus())) {
      searchSaleStatus = ArrayUtil.immutableCopy(condition.getSearchSaleStatus());
    }
    Object[] values = searchSaleStatus;
    Arrays.sort(values);
    if (!Arrays.equals(values, new Object[] {
        TmallSaleStatus.ON_SALE.getValue(), TmallSaleStatus.DISCONTINUED.getValue()
    })) {
      SqlFragment frag = dialect.createInClause(
//    		postgreSQL start    		  
//              "(CASE WHEN SYSDATE BETWEEN CH.RESERVATION_START_DATETIME AND CH.RESERVATION_END_DATETIME THEN 0 "
//              + "WHEN SYSDATE BETWEEN CH.SALE_START_DATETIME AND CH.SALE_END_DATETIME THEN 1 ELSE 2 END)", values);
              "(CASE WHEN "+SqlDialect.getDefault().getCurrentDatetime()+" BETWEEN CH.SALE_START_DATETIME AND CH.SALE_END_DATETIME THEN 1 ELSE 2 END)", values);
      System.out.println(frag.getFragment());
      System.out.println(frag.getParameters().length);
      System.out.println(values.length);
      System.out.println(values[0]);
      System.out.println(frag.getParameters()[0]);
//		postgreSQL end
      b.append("AND ");
      b.append(frag.getFragment());
      for (Object o : frag.getParameters()) {
        params.add(o);
      }
    }

    // 販売状態
    if (StringUtil.hasValue(condition.getSearchSaleType())) {
      if (condition.getSearchSaleType().equals("0") || condition.getSearchSaleType().equals("1")) {
        b.append(" AND (CASE WHEN CH.SALE_FLG_EC = 1 ");
        b.append(" AND EXISTS ( SELECT /*+ INDEX(DJ DELIVERY_TYPE_IX1) */ 'OK' ");
        b.append(" FROM DELIVERY_TYPE DJ WHERE  ");
        b.append(" DJ.SHOP_CODE = CH.SHOP_CODE AND DJ.DISPLAY_FLG = 1) ");
        b.append(" AND EXISTS ( SELECT /*+ INDEX(CC CATEGORY_COMMODITY_IX1) */ 'OK' ");
        b.append(" FROM CATEGORY_COMMODITY CC WHERE CC.SHOP_CODE = CH.SHOP_CODE ");
        b.append(" AND CC.COMMODITY_CODE = CH.COMMODITY_CODE) THEN 1 ELSE 0 END) = ? ");
        params.add(condition.getSearchSaleType());
      }
    }
  }

  private static void buildStockConditions(CommodityListSearchCondition condition, StringBuilder b, List<Object> params) {

    SqlDialect dialect = SqlDialect.getDefault();

    // 在庫数検索
    if (StringUtil.hasValueAnyOf(condition.getSearchStockQuantityStart(), condition.getSearchStockQuantityEnd())) {
      String managementStockType = StockManagementType.WITH_QUANTITY.getValue() + ", " + StockManagementType.WITH_STATUS.getValue();
      b.append(" AND CH.STOCK_MANAGEMENT_TYPE IN (" + managementStockType + ") "); // 在庫管理対象商品のみ抽出
      b.append(" AND EXISTS( SELECT /*+ INDEX (ST STOCK_IX1)*/ 'OK' FROM STOCK ST ");
      b.append("     WHERE ST.SHOP_CODE = CH.SHOP_CODE AND ST.COMMODITY_CODE = CH.COMMODITY_CODE ");
      b.append(" AND ST.STOCK_QUANTITY >= 0 AND ST.ALLOCATED_QUANTITY >= 0 AND ST.RESERVED_QUANTITY >= 0 ");
//    postgreSQL start      
      //b.append(" AND (CASE WHEN SYSDATE BETWEEN CH.RESERVATION_START_DATETIME AND CH.RESERVATION_END_DATETIME ");
      b.append(" AND (CASE WHEN "+SqlDialect.getDefault().getCurrentDatetime()+" BETWEEN CH.RESERVATION_START_DATETIME AND CH.RESERVATION_END_DATETIME ");
//    postgreSQL end      
      // 10.1.3 10033 修正 ここから
      // b.append("  THEN LEAST(COALESCE(ST.RESERVATION_LIMIT, -1) - ST.RESERVED_QUANTITY, ");
      // b.append("             COALESCE(ST.ONESHOT_RESERVATION_LIMIT, 99999999)) ");
      b.append("  THEN CASE WHEN ST.RESERVATION_LIMIT >= 0"); // 予約上限数が設定されているもののみ検索対象に含める
      b.append("   THEN ST.RESERVATION_LIMIT - ST.RESERVED_QUANTITY ELSE NULL END");
      // 10.1.3 10033 修正 ここまで
      b.append("  ELSE ST.STOCK_QUANTITY - ST.ALLOCATED_QUANTITY - ST.RESERVED_QUANTITY END)  ");

      Long stockStart = NumUtil.toLong(condition.getSearchStockQuantityStart(), null);
      Long stockEnd = NumUtil.toLong(condition.getSearchStockQuantityEnd(), null);
      SqlFragment frag = dialect.createRangeClause("", stockStart, stockEnd, false);
      b.append(frag.getFragment());
      b.append(") ");
      for (Object o : frag.getParameters()) {
        params.add(o);
      }
    }

    // 在庫閾値検索（予約可能数は有効在庫とみなさない）
    if (condition.isNotSafetyStock()) {
      String managementStockType = StockManagementType.WITH_QUANTITY.getValue() + ", " + StockManagementType.WITH_STATUS.getValue();
      b.append(" AND CH.STOCK_MANAGEMENT_TYPE IN (" + managementStockType + ") "); // 在庫管理対象商品のみ抽出
      b.append(" AND EXISTS( SELECT /*+ INDEX (ST STOCK_IX1)*/ 'OK' FROM STOCK ST ");
      b.append("     WHERE ST.SHOP_CODE = CH.SHOP_CODE AND ST.COMMODITY_CODE = CH.COMMODITY_CODE ");
      b.append(" AND ST.STOCK_THRESHOLD > 0 "); // 閾値ゼロ＝設定値なしとみなして検索対象外
      b.append(" AND (((ST.STOCK_QUANTITY - ST.ALLOCATED_QUANTITY - ST.RESERVED_QUANTITY) < ST.STOCK_THRESHOLD ");
      b.append(" AND  ST.STOCK_QUANTITY >= 0 AND ST.ALLOCATED_QUANTITY >= 0 AND ST.RESERVED_QUANTITY >= 0) ");
      b.append(" OR ST.STOCK_QUANTITY < 0 OR ST.ALLOCATED_QUANTITY < 0 OR ST.RESERVED_QUANTITY < 0)) ");
    }
  }
}
