package jp.co.sint.webshop.service.catalog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.data.AbstractQuery;
import jp.co.sint.webshop.data.domain.ArrivalGoodsFlg;
import jp.co.sint.webshop.data.domain.CommodityType;
import jp.co.sint.webshop.data.domain.SaleStatus;
import jp.co.sint.webshop.data.domain.SetCommodityFlg;
import jp.co.sint.webshop.data.domain.StockManagementType;
import jp.co.sint.webshop.data.dto.Stock;
import jp.co.sint.webshop.utility.ArrayUtil;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.SqlDialect;
import jp.co.sint.webshop.utility.SqlFragment;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.utility.WebUtil;
import jp.co.sint.webshop.utility.SqlDialect.LikeClauseOption;

public class CommodityKeyBackQuery extends AbstractQuery<CommodityKey> {

  private static final long serialVersionUID = 1L;

  private static final String[] DEFAULT_STATUS = new String[] {
    "-1"
  };

  public Class<CommodityKey> getRowType() {
    return CommodityKey.class;
  }

  public CommodityKeyBackQuery() {
    this(new CommodityListSearchCondition());
  }

  private StringBuilder getInitilizedBuffer() {
    StringBuilder b = new StringBuilder();
    b.append("SELECT /*+ INDEX(CH COMMODITY_HEADER_IX_BACK) */ ");
    b.append("CH.SHOP_CODE, CH.COMMODITY_CODE FROM COMMODITY_HEADER CH WHERE 1 = 1 ");
    return b;
  }

  public CommodityKeyBackQuery(CommodityListSearchCondition condition) {

    List<Object> params = new ArrayList<Object>();
    StringBuilder b = getInitilizedBuffer();
    SqlDialect dialect = SqlDialect.getDefault();
    
    if(StringUtil.hasValue(condition.getCombination())){
      if(condition.getCombination().equals("1")){
        b.append("AND CH.ORIGINAL_COMMODITY_CODE != ? ");
        params.add("");
      }else if(condition.getCombination().equals("0")){
        b.append("AND CH.ORIGINAL_COMMODITY_CODE is null ");
      }else{
        b.append("AND 1=1 ");
      }
    }

    // ショップコード指定
    if (StringUtil.hasValue(condition.getSearchShopCode())) {
      b.append("AND CH.SHOP_CODE = ? ");
      params.add(condition.getSearchShopCode());
    }

    // SKUコード指定
    if (StringUtil.hasValue(condition.getSearchSkuCode())) {
      b.append("AND EXISTS (SELECT /*+ INDEX(CD COMMODITY_DETAIL_PK)*/ 'OK' FROM COMMODITY_DETAIL CD ");
      b.append("WHERE CH.SHOP_CODE = CD.SHOP_CODE AND CH.COMMODITY_CODE = CD.COMMODITY_CODE AND CD.SKU_CODE = ?) ");
      params.add(condition.getSearchSkuCode());
    }

    // 入荷お知らせ
    if (ArrivalGoodsFlg.isValid(condition.getSearchArrivalGoods())) {
      b.append(" AND CH.ARRIVAL_GOODS_FLG = ? ");
      params.add(condition.getSearchArrivalGoods());
    }
    // add by tangweihui 2012-11-16 start
    //增加COMMODITY_TYPE和SET_COMMODITY_FLG查询条件 
    if (CommodityType.isValid(condition.getSearchCommodityType()) && !condition.getSearchCommodityType().equals(CommodityType.ALL.getValue())) {
      b.append(" AND CH.COMMODITY_TYPE = ? ");
      params.add(condition.getSearchCommodityType());
    }
    
    if (SetCommodityFlg.isValid(condition.getSearchSetcommodityflg()) && !condition.getSearchSetcommodityflg().equals(SetCommodityFlg.ALLOBJECTS.getValue())) {
      if (condition.getSearchSetcommodityflg().equals(SetCommodityFlg.OBJECTIN.getValue())) {
        b.append(" AND CH.SET_COMMODITY_FLG = ? ");
      }else{
        b.append(" AND (CH.SET_COMMODITY_FLG = ? OR CH.SET_COMMODITY_FLG IS NULL) ");
      }
      params.add(condition.getSearchSetcommodityflg());
    }
    // add by tangweihui 2012-11-16 end
    // 表示クライアント指定
    String[] displayClientType = DEFAULT_STATUS;
    if (StringUtil.hasValueAnyOf(condition.getSearchDisplayClientType())) {
      displayClientType = ArrayUtil.immutableCopy(condition.getSearchDisplayClientType());
    }
    Object[] values = displayClientType;
    Arrays.sort(values);
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
    
    // 品店精选 
    // 20140526 hdh add start
    if (StringUtil.hasValue(condition.getChoseType()) && "1".equals(condition.getChoseType())) {
      b.append("AND CH. NEW_RESERVE_COMMODITY_TYPE1 = 1  ");
    }
    // 20140526 hdh add end

    // 商品キーワード
    if (StringUtil.hasValue(condition.getSearchCommoditySearchWords())) {
      // 10.1.3 10112 修正 ここから
      // SqlFragment frag = dialect.createLikeClause("CH.SHADOW_SEARCH_WORDS", condition.getSearchCommoditySearchWords(),
      //     LikeClauseOption.PARTIAL_MATCH, false);
      SqlFragment frag = dialect.createLikeClause("CH.SHADOW_SEARCH_WORDS",
          StringUtil.toSearchKeywords(condition.getSearchCommoditySearchWords()), LikeClauseOption.PARTIAL_MATCH, false);
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
    b.append(" ORDER BY ");
    if (StringUtil.hasValue(condition.getChoseType()) && "1".equals(condition.getChoseType())) {
      b.append(" ch.chosen_sort_rank, ");
    }
    b.append(" CH.COMMODITY_CODE ");
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
        SaleStatus.ACCESPTING_RESERVATION.getValue(), SaleStatus.ON_SALE.getValue(), SaleStatus.DISCONTINUED.getValue(),
    })) {
      SqlFragment frag = dialect.createInClause(
//    		postgreSQL start    		  
//              "(CASE WHEN SYSDATE BETWEEN CH.RESERVATION_START_DATETIME AND CH.RESERVATION_END_DATETIME THEN 0 "
//              + "WHEN SYSDATE BETWEEN CH.SALE_START_DATETIME AND CH.SALE_END_DATETIME THEN 1 ELSE 2 END)", values);
              "(CASE WHEN "+SqlDialect.getDefault().getCurrentDatetime()+" BETWEEN CH.RESERVATION_START_DATETIME AND CH.RESERVATION_END_DATETIME THEN 0 "
              + "WHEN "+SqlDialect.getDefault().getCurrentDatetime()+" BETWEEN CH.SALE_START_DATETIME AND CH.SALE_END_DATETIME THEN 1 ELSE 2 END)", values);
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
        b.append(" AND (CASE WHEN CH.SALE_FLG = 1 ");
        b.append(" AND EXISTS ( SELECT /*+ INDEX(DJ DELIVERY_TYPE_IX1) */ 'OK' ");
        b.append(" FROM DELIVERY_TYPE DJ WHERE DJ.DELIVERY_TYPE_NO = CH.DELIVERY_TYPE_NO ");
        b.append(" AND DJ.SHOP_CODE = CH.SHOP_CODE AND DJ.DISPLAY_FLG = 1) ");
        b.append(" AND EXISTS ( SELECT /*+ INDEX(CC CATEGORY_COMMODITY_IX1) */ 'OK' ");
        b.append(" FROM CATEGORY_COMMODITY CC WHERE CC.SHOP_CODE = CH.SHOP_CODE ");
        b.append(" AND CC.COMMODITY_CODE = CH.COMMODITY_CODE) THEN 1 ELSE 0 END) = ? ");
        params.add(condition.getSearchSaleType());
      }
    }

    // // 適用期間
    // if (StringUtil.hasValueAnyOf(condition.getSearchSaleStatus())) {
    // if (!ArrayUtil.areEquals(condition.getSearchSaleStatus(), new String[] {
    // SaleStatus.ACCESPTING_RESERVATION.getValue(),
    // SaleStatus.ON_SALE.getValue(), SaleStatus.DISCONTINUED.getValue()
    // })) {
    // b.append(" AND ((CASE WHEN SYSDATE ");
    // b.append(" BETWEEN CH.RESERVATION_START_DATETIME AND
    // CH.RESERVATION_END_DATETIME THEN ");
    // b.append(SaleStatus.ACCESPTING_RESERVATION.getValue() + " ");
    // b.append(" WHEN SYSDATE BETWEEN CH.SALE_START_DATETIME AND
    // CH.SALE_END_DATETIME THEN ");
    // b.append(SaleStatus.ON_SALE.getValue() + " ");
    // b.append(" ELSE " + SaleStatus.DISCONTINUED.getValue() + " END) ");
    // SqlFragment frag = dialect.createInClause("", (Object[])
    // condition.getSearchSaleStatus());
    // b.append(frag.getFragment());
    // for (Object o : frag.getParameters()) {
    // params.add(o);
    // }
    // b.append(" ) ");
    // }
    // }
  }

  private static void buildStockConditions(CommodityListSearchCondition condition, StringBuilder b, List<Object> params) {

    SqlDialect dialect = SqlDialect.getDefault();

    // 在庫状況
    String[] stockStatus = DEFAULT_STATUS;
    if (StringUtil.hasValueAnyOf(condition.getSearchStockStatus())) {
      stockStatus = ArrayUtil.immutableCopy(condition.getSearchStockStatus());
    }
    String length = WebUtil.buildMaxlength(Stock.class, "stockQuantity", null);
    String stockQuantity = "";
    for (Long i = 0L; i < NumUtil.toLong(length); i++) {
      stockQuantity += "9";
    }

    String[] ss = new String[] {
        StockAvailability.FULL.getValue(), StockAvailability.SOME.getValue(), StockAvailability.NONE.getValue()
    };

    if (!ArrayUtil.areEquals(stockStatus, ss)) {
      b.append(" AND ( CASE WHEN CH.STOCK_MANAGEMENT_TYPE IN (?, ?) THEN 0 ");
      params.add(StockManagementType.NONE.getValue());
      params.add(StockManagementType.NOSTOCK.getValue());

      // 10.1.1 10004 修正 ここから
//      b.append(" ELSE (SELECT CASE WHEN ROW_CNT = AKQ_CNT THEN 0 ");
//      b.append(" WHEN AKQ_CNT = 0 THEN 2 ELSE 1 END ");
//      b.append(" FROM (SELECT COUNT(*) ROW_CNT, COUNT( CASE WHEN ( ");
//      b.append(" CASE WHEN SYSDATE BETWEEN CH.RESERVATION_START_DATETIME AND CH.RESERVATION_END_DATETIME THEN "); // 予約受付中
//      b.append("  CASE WHEN ST.RESERVATION_LIMIT IS NOT NULL THEN ");
//      b.append("   COALESCE(ST.RESERVATION_LIMIT - ST.RESERVED_QUANTITY, 0) ");
//      b.append("   ELSE " + stockQuantity + " END ");
//      b.append(" WHEN SYSDATE BETWEEN CH.SALE_START_DATETIME AND CH.SALE_END_DATETIME "); // 販売中
//      b.append("   AND (ST.STOCK_QUANTITY >= 0 AND ST.ALLOCATED_QUANTITY >= 0 AND ST.RESERVED_QUANTITY >= 0) THEN"); // 販売中
//      b.append("   ST.STOCK_QUANTITY - ST.ALLOCATED_QUANTITY - ST.RESERVED_QUANTITY END");
//      b.append(" ) > 0 THEN 0 ELSE NULL END ) AKQ_CNT FROM STOCK ST ");
//      b.append(" WHERE ST.SHOP_CODE = CH.SHOP_CODE AND ST.COMMODITY_CODE = CH.COMMODITY_CODE  ");
//      b.append("  GROUP BY ST.SHOP_CODE, COMMODITY_CODE)) END) ");
      b.append(" ELSE STOCK_CONDITIONS(CH.SHOP_CODE, CH.COMMODITY_CODE) ");
      b.append(" END) ");
      // 10.1.1 10004 修正 ここまで

      SqlFragment frag = dialect.createInClause("", (Object[]) stockStatus);
      b.append(frag.getFragment());
      for (Object o : frag.getParameters()) {
        params.add(o);
      }
    }

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

  private static enum StockAvailability implements CodeAttribute {

    /** 「全規格在庫あり」を表す値です。 */
    FULL("全規格在庫あり", "0"),

    /** 「一部規格在庫あり」を表す値です。 */
    SOME("一部規格在庫あり", "1"),

    /** 「全規格在庫なし」を表す値です。 */
    NONE("全規格在庫なし", "2");

    private String name;

    private String value;

    private StockAvailability(String name, String value) {
      this.name = name;
      this.value = value;
    }

    public String getName() {
      return this.name;
    }

    public String getValue() {
      return this.value;
    }

    public Long longValue() {
      return Long.valueOf(this.value);
    }

  }
}
