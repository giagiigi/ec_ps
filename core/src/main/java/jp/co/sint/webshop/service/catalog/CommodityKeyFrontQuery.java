package jp.co.sint.webshop.service.catalog;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.AbstractQuery;
import jp.co.sint.webshop.data.dao.CategoryDao;
import jp.co.sint.webshop.data.domain.CommodityDisplayOrder;
import jp.co.sint.webshop.data.domain.DisplayClientType; 
import jp.co.sint.webshop.data.dto.Category;
import jp.co.sint.webshop.service.catalog.CommodityContainerCondition.SearchDetailAttributeList;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.SqlDialect;
import jp.co.sint.webshop.utility.SqlFragment;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.utility.SqlDialect.LikeClauseOption;

import org.apache.log4j.Logger;

public class CommodityKeyFrontQuery extends AbstractQuery<CommodityKey> {

  private static final long serialVersionUID = 1L;

  public Class<CommodityKey> getRowType() {
    return CommodityKey.class;
  }

  public CommodityKeyFrontQuery() {
    this(new CommodityContainerCondition());
  }

  private StringBuilder getInitializedBuffer() {

    StringBuilder b = new StringBuilder();

    // select clause
    b.append("SELECT /*+ INDEX(CD COMMODITY_DETAIL_IX2) */ CH.SHOP_CODE, CH.COMMODITY_CODE ");

    // from clause and joinees
    b.append("FROM COMMODITY_HEADER CH INNER JOIN COMMODITY_DETAIL CD ");
    b.append("ON CD.SHOP_CODE = CH.SHOP_CODE AND CD.SKU_CODE = CH.REPRESENT_SKU_CODE ");
    b.append("LEFT JOIN REVIEW_SUMMARY RS ON RS.COMMODITY_CODE = CH.COMMODITY_CODE ");
    b.append("INNER JOIN C_COMMODITY_DETAIL CCD ON CCD.SKU_CODE = CH.COMMODITY_CODE ");
    // where clause: required conditions
    b.append("WHERE ");
    // postgreSQL start
    //b.append("((SYSDATE BETWEEN CH.SALE_START_DATETIME AND CH.SALE_END_DATETIME)  OR ");
    //b.append(" (SYSDATE BETWEEN CH.RESERVATION_START_DATETIME AND CH.RESERVATION_END_DATETIME)) ");
    b.append("((" + SqlDialect.getDefault().getCurrentDatetime() + " BETWEEN CH.SALE_START_DATETIME AND CH.SALE_END_DATETIME)  OR ");
    b.append(" (" + SqlDialect.getDefault().getCurrentDatetime() + " BETWEEN CH.RESERVATION_START_DATETIME AND CH.RESERVATION_END_DATETIME)) ");
    // postgreSQL end
    // 没有库存的商品筛选掉 20120914 yyq update 
    b.append(" AND CH.SALE_FLG = 1 AND CH.CATEGORY_PATH IS NOT NULL ");
    b.append("AND (((SELECT STOCK_QUANTITY - ALLOCATED_QUANTITY AS ACT_STOCK FROM STOCK WHERE CD.SHOP_CODE = SHOP_CODE AND ( CASE WHEN CH.ORIGINAL_COMMODITY_CODE IS NULL THEN CD.SKU_CODE ELSE CH.ORIGINAL_COMMODITY_CODE END) = SKU_CODE) >=  CASE WHEN CH.COMBINATION_AMOUNT IS NULL THEN 1 ELSE CH.COMBINATION_AMOUNT END OR CH.STOCK_MANAGEMENT_TYPE = 1) " + 
       "  or   ((SELECT STOCK_QUANTITY - ALLOCATED_QUANTITY AS ACT_STOCK FROM STOCK WHERE CD.SHOP_CODE = SHOP_CODE AND ( CASE WHEN CH.ORIGINAL_COMMODITY_CODE IS NULL THEN CD.SKU_CODE ELSE CH.ORIGINAL_COMMODITY_CODE END) = SKU_CODE) <  CASE WHEN CH.COMBINATION_AMOUNT IS NULL THEN 1 ELSE CH.COMBINATION_AMOUNT END and ccd.use_flg = 1 and CH.STOCK_MANAGEMENT_TYPE <> 1) )" );

    // check: shop is on sale
    b.append("AND EXISTS (SELECT /*+ INDEX(SP SHOP_IX1) */ 'OK' FROM SHOP SP WHERE SP.SHOP_CODE = CH.SHOP_CODE ");
    // postgreSQL start
    //b.append("AND SYSDATE BETWEEN SP.OPEN_DATETIME AND SP.CLOSE_DATETIME) ");
    b.append("AND " + SqlDialect.getDefault().getCurrentDatetime() + " BETWEEN SP.OPEN_DATETIME AND SP.CLOSE_DATETIME) ");
    // postgreSQL end
    
    // check: delivery type is displayable
    b.append("AND EXISTS ( SELECT /*+ INDEX(DJ DELIVERY_TYPE_IX1) */ 'OK' FROM DELIVERY_TYPE DJ ");
    b.append("WHERE DJ.DELIVERY_TYPE_NO = CH.DELIVERY_TYPE_NO AND DJ.SHOP_CODE = CH.SHOP_CODE AND DJ.DISPLAY_FLG = 1) ");

    return b;
  }

  public CommodityKeyFrontQuery(CommodityContainerCondition condition) {
    Logger logger = Logger.getLogger(this.getClass());
    List<Object> params = new ArrayList<Object>();
    
    // select - from - where (必須条件) まで
    StringBuilder b = getInitializedBuffer();
    b.append(" AND CH.COMMODITY_TYPE = 0 ");
    if(StringUtil.isNullOrEmpty(condition.getSearchSpecFlag())){
      b.append(" AND CH.RESERVE_COMMODITY_TYPE1 <> 1 ");
    }
    
    //品店精选 1是 9不是
    if (StringUtil.hasValue(condition.getSearchSelected())) {
      b.append(" AND CH.NEW_RESERVE_COMMODITY_TYPE1 = 1 ");
    }
    
    // ショップ指定
    if (StringUtil.hasValue(condition.getSearchShopCode())) {
      b.append(" AND CH.SHOP_CODE = ? ");
      params.add(condition.getSearchShopCode());
    }
    //add by lc 2012-02-07 start
    // セール区分
    if (StringUtil.hasValue(condition.getSearchSaleFlag())) {
      b.append(" AND CH.SALE_FLAG = ? ");
      params.add(condition.getSearchSaleFlag());
    }
    
    if (StringUtil.hasValue(condition.getImportCommodityType())) {
      b.append(" AND CH.IMPORT_COMMODITY_TYPE = ? ");
      params.add(condition.getImportCommodityType());
    }
    
    if (StringUtil.hasValue(condition.getClearCommodityType())) {
      b.append(" AND CH.CLEAR_COMMODITY_TYPE = ? ");
      params.add(condition.getClearCommodityType());
    }
    
    if (StringUtil.hasValue(condition.getReserveCommodityType1())) {
      b.append(" AND CH.RESERVE_COMMODITY_TYPE1 = ? ");
      params.add(condition.getReserveCommodityType1());
    }
    
    if (StringUtil.hasValue(condition.getReserveCommodityType2())) {
      b.append(" AND CH.RESERVE_COMMODITY_TYPE2 = ? ");
      params.add(condition.getReserveCommodityType2());
    }
    
    if (StringUtil.hasValue(condition.getReserveCommodityType3())) {
      b.append(" AND CH.RESERVE_COMMODITY_TYPE3 = ? ");
      params.add(condition.getReserveCommodityType3());
    }
    
    if (StringUtil.hasValue(condition.getNewReserveCommodityType1())) {
      b.append(" AND CH.NEW_RESERVE_COMMODITY_TYPE1 = ? ");
      params.add(condition.getNewReserveCommodityType1());
    }
    
    if (StringUtil.hasValue(condition.getNewReserveCommodityType2())) {
      b.append(" AND CH.NEW_RESERVE_COMMODITY_TYPE2 = ? ");
      params.add(condition.getNewReserveCommodityType2());
    }
    
    if (StringUtil.hasValue(condition.getNewReserveCommodityType3())) {
      b.append(" AND CH.NEW_RESERVE_COMMODITY_TYPE3 = ? ");
      params.add(condition.getNewReserveCommodityType3());
    }
    
    if (StringUtil.hasValue(condition.getNewReserveCommodityType4())) {
      b.append(" AND CH.NEW_RESERVE_COMMODITY_TYPE4 = ? ");
      params.add(condition.getNewReserveCommodityType4());
    }
    
    if (StringUtil.hasValue(condition.getNewReserveCommodityType5())) {
      b.append(" AND CH.NEW_RESERVE_COMMODITY_TYPE5 = ? ");
      params.add(condition.getNewReserveCommodityType5());
    }
    // 特集区分
    if (StringUtil.hasValue(condition.getSearchSpecFlag())) {
    //b.append(" AND CH.SPEC_FLAG = ? ");
    //params.add(condition.getSearchSpecFlag());
      b.append(" AND EXISTS (SELECT 'OK' FROM PLAN_COMMODITY PC WHERE PC.COMMODITY_CODE = CH.REPRESENT_SKU_CODE AND PC.PLAN_CODE = ? AND DETAIL_TYPE= ? AND DETAIL_CODE= ? )");
      params.add(condition.getSearchPlanDetail().getPlanCode());
      params.add(condition.getSearchPlanDetail().getDetailType());
      params.add(condition.getSearchPlanDetail().getDetailCode());
    }
    //add by lc 2012-02-07 end
    // 商品コード指定
    if (StringUtil.hasValue(condition.getSearchCommodityCode())) {
      b.append(" AND CH.COMMODITY_CODE = ? ");
      params.add(condition.getSearchCommodityCode());
    }

    // SKUコード指定
    if (StringUtil.hasValue(condition.getSearchSkuCode())) {
      b.append("AND EXISTS (SELECT /*+ INDEX(CD COMMODITY_DETAIL_PK)*/ 'OK' FROM COMMODITY_DETAIL CD ");
      b.append("WHERE CD.SHOP_CODE = CH.SHOP_CODE AND CD.COMMODITY_CODE = CH.COMMODITY_CODE AND CD.SKU_CODE = ?) ");
      params.add(condition.getSearchSkuCode());
    }

    // キーワード指定
    if (StringUtil.hasValue(condition.getSearchWord())) {
      setKeyWordCondition(condition, b, params);
    }

    // 価格帯指定
    if (StringUtil.hasValueAnyOf(condition.getSearchPriceStart(), condition.getSearchPriceEnd())) {
      BigDecimal start = NumUtil.parse(condition.getSearchPriceStart(), BigDecimal.ZERO);
      BigDecimal end = NumUtil.parse(condition.getSearchPriceEnd(), BigDecimal.ZERO);
      String tmpFrg = "CASE WHEN CH.DISCOUNT_PRICE_START_DATETIME IS NOT NULL AND CH.DISCOUNT_PRICE_END_DATETIME IS NOT NULL " +
          "AND TO_CHAR(NOW(), 'YYYY/MM/DD HH24:MI:SS') >= TO_CHAR(CH.DISCOUNT_PRICE_START_DATETIME, 'YYYY/MM/DD HH24:MI:SS') " +
          "AND TO_CHAR(NOW(), 'YYYY/MM/DD HH24:MI:SS' ::TEXT) <= TO_CHAR(CH.DISCOUNT_PRICE_END_DATETIME, 'YYYY/MM/DD HH24:MI:SS' ::TEXT) " +
          "AND CD.DISCOUNT_PRICE > 0 THEN CD.DISCOUNT_PRICE ELSE CH.REPRESENT_SKU_UNIT_PRICE END";
      SqlFragment frag = SqlDialect.getDefault().createRangeClause(tmpFrg, start, end);
      logger.debug(frag);
      b.append(" AND ");
      b.append(frag.getFragment());
      for (Object o : frag.getParameters()) {
        params.add(o);
      }
    }

    // カテゴリ指定
    if (StringUtil.hasValue(condition.getSearchCategoryCode()) && !condition.getSearchCategoryCode().equals("0")) {
      CategoryDao dao = DIContainer.getDao(CategoryDao.class);
      Category cat = dao.load(condition.getSearchCategoryCode());
//      b.append("AND EXISTS (SELECT /*+ INDEX(CC CATEGORY_COMMODITY_IX1) */ 'OK' FROM CATEGORY_COMMODITY CC ");
//      b.append("WHERE CC.SHOP_CODE = CH.SHOP_CODE AND CC.COMMODITY_CODE = CH.COMMODITY_CODE AND ");
      // 10.1.3 10173 修正 ここから
      // String searchCat = cat.getPath() + "~" + condition.getSearchCategoryCode();
      // 検索条件のカテゴリコードが存在しない場合は、結果件数が必ず0件になるようにする。
      // ⇒確実にこのSQL文の条件をFALSEにするため、1 = 0を検索条件に追加する。
      b.append(" AND ");
      String searchCat = "";
      if (cat != null) {
        searchCat = cat.getPath() + "~" + condition.getSearchCategoryCode();
      } else {
        b.append("1 = 0 AND ");
      }
      // 10.1.3 10173 修正 ここまで
      
      String searchFormat = "CH.CATEGORY_PATH";
      // 10.1.2 10093 修正 ここから
      // SqlFragment frag = SqlDialect.getDefault().createLikeClause(searchFormat, searchCat, LikeClauseOption.STARTS_WITH, false);
      // b.append(frag.getFragment());
      // b.append(") ");
      // CATEGORY_SEARCH_PATHが完全一致か、もしくは前方一致の商品(サブカテゴリ商品)を抽出する
      // 完全一致
      // postgreSQL start
      //b.append("(TO_CHAR(CC.CATEGORY_SEARCH_PATH) = ? OR ");
      b.append("(" + SqlDialect.getDefault().toChar(searchFormat) + " = ? OR ");
      // postgreSQL end
      params.add(searchCat);
      // 前方一致(LIKE検索)
      String searchCatTld = searchCat;
      SqlFragment frag = SqlDialect.getDefault().createLikeClause(searchFormat, searchCatTld, LikeClauseOption.PARTIAL_MATCH, false);
      b.append(frag.getFragment());
      b.append(") ");
      // 10.1.2 10093 修正 ここまで
      for (Object o : frag.getParameters()) {
        params.add(o);
      }
    }
    // 条件がなくても、カテゴリに関連付けられた商品のみ表示する(商品公開仕様)
    b.append("AND EXISTS (SELECT /*+ INDEX(CC CATEGORY_COMMODITY_IX1) */ 'OK' FROM CATEGORY_COMMODITY CC ");
    b.append("WHERE CC.SHOP_CODE = CH.SHOP_CODE AND CC.COMMODITY_CODE = CH.COMMODITY_CODE) ");

    // カテゴリ属性値検索
    for (SearchDetailAttributeList da : condition.getSearchDetailAttributeList()) {
      if (StringUtil.hasValue(da.getCategoryAttributeValue())) {
        b.append("AND EXISTS (SELECT /*+ INDEX(CAV CATEGORY_ATTRIBUTE_VALUE_PK)*/ 'OK' FROM CATEGORY_ATTRIBUTE_VALUE CAV ");
        b.append("WHERE CAV.SHOP_CODE = CH.SHOP_CODE AND CAV.COMMODITY_CODE = CH.COMMODITY_CODE AND CAV.CATEGORY_CODE = ? ");
        b.append("AND (CAV.CATEGORY_ATTRIBUTE_NO = ? AND ");
        SqlDialect dialect = SqlDialect.getDefault();
        SqlFragment frag = dialect.createLikeClause("CAV.CATEGORY_ATTRIBUTE_VALUE", da.getCategoryAttributeValue(),
            LikeClauseOption.PARTIAL_MATCH, false);
        b.append(frag.getFragment());
        b.append(")) ");
        params.add(condition.getSearchCategoryCode());
        params.add(da.getCategoryAttributeNo());
        for (Object o : frag.getParameters()) {
          params.add(o);
        }
      }
    }

    // 表示クライアント指定
    if (StringUtil.hasValue(condition.getDisplayClientType())) {
      DisplayClientType dct = DisplayClientType.fromValue(condition.getDisplayClientType());
      if (dct != null && (dct == DisplayClientType.PC || dct == DisplayClientType.MOBILE)) {
        b.append("AND CH.DISPLAY_CLIENT_TYPE IN (");
        b.append(DisplayClientType.ALL.getValue());
        b.append(",");
        b.append(dct.getValue());
        b.append(") ");
      }
    }
    

    // キャンペーン指定
    if (StringUtil.hasValue(condition.getSearchCampaignCode())) {
      b.append("AND EXISTS (SELECT /*+ INDEX(MC CAMPAIGN_COMMODITY_PK)*/ 'OK' FROM CAMPAIGN_COMMODITY MC ");
      b.append("WHERE MC.SHOP_CODE = CH.SHOP_CODE AND MC.COMMODITY_CODE = CH.COMMODITY_CODE AND MC.CAMPAIGN_CODE= ?) ");
      params.add(condition.getSearchCampaignCode());
    }

    // タグ指定
    if (StringUtil.hasValue(condition.getSearchTagCode())) {
      b.append("AND EXISTS (SELECT /*+ INDEX(TC TAG_COMMODITY_PK)*/ 'OK' FROM TAG_COMMODITY TC ");
      b.append("WHERE TC.SHOP_CODE = CH.SHOP_CODE AND TC.COMMODITY_CODE = CH.COMMODITY_CODE AND TC.TAG_CODE = ?) ");
      params.add(condition.getSearchTagCode());
    }
    // 
    if (StringUtil.hasValue(condition.getSearchBrandCode())) {
      b.append("AND CH.BRAND_CODE = ?  ");
      params.add(condition.getSearchBrandCode());
    }

    // 
    if (StringUtil.hasValue(condition.getReviewScore())) {
      b.append("AND EXISTS (SELECT /*+ INDEX(RS REVIEW_SUMMARY_PK)*/ 'OK' FROM REVIEW_SUMMARY RS ");
      b.append("WHERE RS.SHOP_CODE = CH.SHOP_CODE AND RS.COMMODITY_CODE = CH.COMMODITY_CODE AND RS.REVIEW_SCORE >= ?) ");
      params.add(condition.getReviewScore());
    }
    // 
    if (StringUtil.hasValue(condition.getSearchCategoryAttribute1())) {
      b.append(" AND ");
      String searchFormat = " CH.CATEGORY_ATTRIBUTE1||CH.CATEGORY_ATTRIBUTE1_EN||CH.CATEGORY_ATTRIBUTE1_JP ";
      String searchCatTld = condition.getSearchCategoryAttribute1();
      SqlFragment frag = SqlDialect.getDefault().createLikeClause(searchFormat, searchCatTld, LikeClauseOption.PARTIAL_MATCH, false);
      b.append(frag.getFragment());
      for (Object o : frag.getParameters()) {
        params.add(o);
      }
    }
    // 
    if (StringUtil.hasValue(condition.getSearchCategoryAttribute2())) {
      b.append(" AND ");
      String searchFormat = " CH.CATEGORY_ATTRIBUTE1 ";
      String searchCatTld = condition.getSearchCategoryAttribute2();
      SqlFragment frag = SqlDialect.getDefault().createLikeClause(searchFormat, searchCatTld, LikeClauseOption.PARTIAL_MATCH, false);
      b.append(frag.getFragment());
      for (Object o : frag.getParameters()) {
        params.add(o);
      }
    }
    // 
    if (StringUtil.hasValue(condition.getSearchCategoryAttribute3())) {
      b.append(" AND ");
      String searchFormat = " CH.CATEGORY_ATTRIBUTE1 ";
      String searchCatTld = condition.getSearchCategoryAttribute3();
      SqlFragment frag = SqlDialect.getDefault().createLikeClause(searchFormat, searchCatTld, LikeClauseOption.PARTIAL_MATCH, false);
      b.append(frag.getFragment());
      for (Object o : frag.getParameters()) {
        params.add(o);
      }
    }
    // 並び順設定
    CommodityDisplayOrder cdo = CommodityDisplayOrder.fromValue(condition.getAlignmentSequence());
    if (cdo == null) {
      cdo = CommodityDisplayOrder.BY_POPULAR_RANKING;
    }
    String currentLanguageCode = DIContainer.getLocaleContext().getCurrentLanguageCode();
    b.append("ORDER BY ");
    
    // 20140902 hdh add start品店热卖
    if(StringUtil.hasValue(condition.getForHotSale())){
      b.append("CH.COMMODITY_POPULAR_RANK ASC ");
      setSqlString(b.toString());
      setParameters(params.toArray());
      setMaxFetchSize(condition.getMaxFetchSize());
      setPageNumber(condition.getCurrentPage());
      setPageSize(condition.getPageSize());
      return;
    }
    // 20140902 hdh add  end品店热卖
    
    switch (cdo) {
      case BY_POPULAR_RANKING:
        
        // add by zhangfeng 2014/6/10
        b.append("CASE WHEN EXISTS (SELECT DC.* FROM DISCOUNT_COMMODITY DC INNER JOIN DISCOUNT_HEADER DH ON DC.DISCOUNT_CODE = DH.DISCOUNT_CODE WHERE DC.COMMODITY_CODE = CH.COMMODITY_CODE AND DC.USE_FLG = 1 AND DH.DISCOUNT_START_DATETIME <= NOW() AND DH.DISCOUNT_END_DATETIME >= NOW()) THEN 1 ELSE 2 END ASC , ");
        b.append("CASE WHEN CH.NEW_RESERVE_COMMODITY_TYPE3 = 9 THEN 9 ELSE CH.NEW_RESERVE_COMMODITY_TYPE3 END ASC , ");
        b.append("CASE WHEN CH.RESERVE_COMMODITY_TYPE2 = 9 THEN 9 ELSE CH.RESERVE_COMMODITY_TYPE2 END ASC , ");
        b.append("CASE WHEN CH.NEW_RESERVE_COMMODITY_TYPE1 = 9 THEN 9 ELSE CH.NEW_RESERVE_COMMODITY_TYPE1 END ASC , ");
        b.append("CASE WHEN CH.IMPORT_COMMODITY_TYPE = 9 THEN 9 ELSE CH.IMPORT_COMMODITY_TYPE END ASC , ");
        b.append("CASE WHEN CH.CLEAR_COMMODITY_TYPE = 9 THEN 9 ELSE CH.CLEAR_COMMODITY_TYPE END DESC , ");
        // end 
        
        if (currentLanguageCode.equals("ja-jp")){
          b.append("CASE WHEN CH.HOT_FLG_JP IS NULL THEN 0 ELSE CH.HOT_FLG_JP END DESC , ");
        } else if (currentLanguageCode.equals("en-us")){
          b.append("CASE WHEN CH.HOT_FLG_EN IS NULL THEN 0 ELSE CH.HOT_FLG_EN END DESC , ");
        }
        // 20130414 update by yyq start 
        b.append("CASE WHEN CH.RESERVE_COMMODITY_TYPE2 = 9 THEN 9 ELSE CH.RESERVE_COMMODITY_TYPE2 END ASC , ");
        b.append("CASE WHEN CH.IMPORT_COMMODITY_TYPE = 9 THEN 9 ELSE CH.IMPORT_COMMODITY_TYPE END ASC , ");
        b.append("CASE WHEN  ");
        b.append(" (SELECT STOCK_QUANTITY - ALLOCATED_QUANTITY AS ACT_STOCK FROM STOCK WHERE CD.SHOP_CODE = SHOP_CODE AND CD.SKU_CODE = SKU_CODE) > 0 OR CH.STOCK_MANAGEMENT_TYPE = 1 THEN 1 ELSE 0 END DESC , ");
        b.append("CASE WHEN CH.DISCOUNT_PRICE_START_DATETIME IS NOT NULL AND CH.DISCOUNT_PRICE_END_DATETIME IS NOT NULL AND TO_CHAR(NOW(), 'YYYY/MM/DD HH24:MI:SS') >= TO_CHAR(CH.DISCOUNT_PRICE_START_DATETIME, 'YYYY/MM/DD HH24:MI:SS') AND TO_CHAR(NOW(), 'YYYY/MM/DD HH24:MI:SS'::TEXT) <= TO_CHAR(CH.DISCOUNT_PRICE_END_DATETIME, 'YYYY/MM/DD HH24:MI:SS'::TEXT) THEN 1 ");
        b.append("WHEN CH.DISCOUNT_PRICE_START_DATETIME IS NOT NULL AND CH.DISCOUNT_PRICE_END_DATETIME IS NULL AND TO_CHAR(NOW(), 'YYYY/MM/DD HH24:MI:SS'::TEXT) >= TO_CHAR(CH.DISCOUNT_PRICE_START_DATETIME, 'YYYY/MM/DD HH24:MI:SS'::TEXT) AND TO_CHAR(NOW(), 'YYYY/MM/DD HH24:MI:SS'::TEXT) <= TO_CHAR(CH.SALE_END_DATETIME, 'YYYY/MM/DD HH24:MI:SS'::TEXT) THEN 1 ");
        b.append("WHEN CH.DISCOUNT_PRICE_START_DATETIME IS NULL AND CH.DISCOUNT_PRICE_END_DATETIME IS NOT NULL AND TO_CHAR(NOW(), 'YYYY/MM/DD HH24:MI:SS'::TEXT) >= TO_CHAR(CH.SALE_START_DATETIME, 'YYYY/MM/DD HH24:MI:SS'::TEXT) AND TO_CHAR(NOW(), 'YYYY/MM/DD HH24:MI:SS'::TEXT) <= TO_CHAR(CH.DISCOUNT_PRICE_END_DATETIME, 'YYYY/MM/DD HH24:MI:SS'::TEXT) THEN 1 ELSE 0 END DESC ,");
        b.append("CASE WHEN CH.CLEAR_COMMODITY_TYPE = 9 THEN 9 ELSE CH.CLEAR_COMMODITY_TYPE END DESC , ");
        // 20130414 update by yyq end 
        b.append("CH.COMMODITY_POPULAR_RANK ASC ");


        break;
      case BY_PRICE_ASCENDING:
        // 20120914 update by yyq start 
        b.append("CASE WHEN  ");
        b.append(" (SELECT STOCK_QUANTITY - ALLOCATED_QUANTITY AS ACT_STOCK FROM STOCK WHERE CD.SHOP_CODE = SHOP_CODE AND CD.SKU_CODE = SKU_CODE) > 0 OR CH.STOCK_MANAGEMENT_TYPE = 1 THEN 1 ELSE 0 END DESC , CH.COMMODITY_POPULAR_RANK ASC, ");
        // 20120914 update by yyq end 
        b.append("CASE WHEN CH.DISCOUNT_PRICE_START_DATETIME IS NOT NULL AND CH.DISCOUNT_PRICE_END_DATETIME IS NOT NULL AND TO_CHAR(NOW(), 'YYYY/MM/DD HH24:MI:SS') >= TO_CHAR(CH.DISCOUNT_PRICE_START_DATETIME, 'YYYY/MM/DD HH24:MI:SS') AND TO_CHAR(NOW(), 'YYYY/MM/DD HH24:MI:SS'::TEXT) <= TO_CHAR(CH.DISCOUNT_PRICE_END_DATETIME, 'YYYY/MM/DD HH24:MI:SS'::TEXT) THEN 1 ");
        b.append("WHEN CH.DISCOUNT_PRICE_START_DATETIME IS NOT NULL AND CH.DISCOUNT_PRICE_END_DATETIME IS NULL AND TO_CHAR(NOW(), 'YYYY/MM/DD HH24:MI:SS'::TEXT) >= TO_CHAR(CH.DISCOUNT_PRICE_START_DATETIME, 'YYYY/MM/DD HH24:MI:SS'::TEXT) AND TO_CHAR(NOW(), 'YYYY/MM/DD HH24:MI:SS'::TEXT) <= TO_CHAR(CH.SALE_END_DATETIME, 'YYYY/MM/DD HH24:MI:SS'::TEXT) THEN 1 ");
        b.append("WHEN CH.DISCOUNT_PRICE_START_DATETIME IS NULL AND CH.DISCOUNT_PRICE_END_DATETIME IS NOT NULL AND TO_CHAR(NOW(), 'YYYY/MM/DD HH24:MI:SS'::TEXT) >= TO_CHAR(CH.SALE_START_DATETIME, 'YYYY/MM/DD HH24:MI:SS'::TEXT) AND TO_CHAR(NOW(), 'YYYY/MM/DD HH24:MI:SS'::TEXT) <= TO_CHAR(CH.DISCOUNT_PRICE_END_DATETIME, 'YYYY/MM/DD HH24:MI:SS'::TEXT) THEN 1 ");
        b.append("ELSE 0 END DESC ");
        break;
      case BY_PRICE_DESCENDING:
        // 20120914 update by yyq start 
        b.append("CASE WHEN  ");
        b.append(" (SELECT STOCK_QUANTITY - ALLOCATED_QUANTITY AS ACT_STOCK FROM STOCK WHERE CD.SHOP_CODE = SHOP_CODE AND CD.SKU_CODE = SKU_CODE) > 0 OR CH.STOCK_MANAGEMENT_TYPE = 1 THEN 1 ELSE 0 END DESC ,");
        // 20120914 update by yyq end 
        b.append("CASE WHEN CH.DISCOUNT_PRICE_START_DATETIME IS NOT NULL AND CH.DISCOUNT_PRICE_END_DATETIME IS NOT NULL AND TO_CHAR(NOW(), 'YYYY/MM/DD HH24:MI:SS') >= TO_CHAR(CH.DISCOUNT_PRICE_START_DATETIME, 'YYYY/MM/DD HH24:MI:SS') AND TO_CHAR(NOW(), 'YYYY/MM/DD HH24:MI:SS' ::TEXT) <= TO_CHAR(CH.DISCOUNT_PRICE_END_DATETIME, 'YYYY/MM/DD HH24:MI:SS' ::TEXT) THEN CD.DISCOUNT_PRICE ELSE CD.UNIT_PRICE END ASC,");
        b.append("CASE WHEN CH.DISCOUNT_PRICE_START_DATETIME IS NOT NULL AND CH.DISCOUNT_PRICE_END_DATETIME IS NOT NULL AND TO_CHAR(NOW(), 'YYYY/MM/DD HH24:MI:SS') >= TO_CHAR(CH.DISCOUNT_PRICE_START_DATETIME, 'YYYY/MM/DD HH24:MI:SS') AND TO_CHAR(NOW(), 'YYYY/MM/DD HH24:MI:SS'::TEXT) <= TO_CHAR(CH.DISCOUNT_PRICE_END_DATETIME, 'YYYY/MM/DD HH24:MI:SS'::TEXT) THEN 1 ");
        b.append("WHEN CH.DISCOUNT_PRICE_START_DATETIME IS NOT NULL AND CH.DISCOUNT_PRICE_END_DATETIME IS NULL AND TO_CHAR(NOW(), 'YYYY/MM/DD HH24:MI:SS'::TEXT) >= TO_CHAR(CH.DISCOUNT_PRICE_START_DATETIME, 'YYYY/MM/DD HH24:MI:SS'::TEXT) AND TO_CHAR(NOW(), 'YYYY/MM/DD HH24:MI:SS'::TEXT) <= TO_CHAR(CH.SALE_END_DATETIME, 'YYYY/MM/DD HH24:MI:SS'::TEXT) THEN 1 ");
        b.append("WHEN CH.DISCOUNT_PRICE_START_DATETIME IS NULL AND CH.DISCOUNT_PRICE_END_DATETIME IS NOT NULL AND TO_CHAR(NOW(), 'YYYY/MM/DD HH24:MI:SS'::TEXT) >= TO_CHAR(CH.SALE_START_DATETIME, 'YYYY/MM/DD HH24:MI:SS'::TEXT) AND TO_CHAR(NOW(), 'YYYY/MM/DD HH24:MI:SS'::TEXT) <= TO_CHAR(CH.DISCOUNT_PRICE_END_DATETIME, 'YYYY/MM/DD HH24:MI:SS'::TEXT) THEN 1 ");
        b.append("ELSE 0 END DESC ,CH.COMMODITY_POPULAR_RANK ASC");
        break;
      case BY_COMMODITY_NAME:
        // 20120914 update by yyq start 
        b.append("CASE WHEN  ");
        b.append(" (SELECT STOCK_QUANTITY - ALLOCATED_QUANTITY AS ACT_STOCK FROM STOCK WHERE CD.SHOP_CODE = SHOP_CODE AND CD.SKU_CODE = SKU_CODE) > 0 OR CH.STOCK_MANAGEMENT_TYPE = 1 THEN 1 ELSE 0 END DESC , ");
        // 20120914 update by yyq end 
        b.append("CASE WHEN RS.REVIEW_COUNT IS NULL THEN 0 ELSE RS.REVIEW_COUNT END DESC, ");
        b.append("CASE WHEN CH.DISCOUNT_PRICE_START_DATETIME IS NOT NULL AND CH.DISCOUNT_PRICE_END_DATETIME IS NOT NULL AND TO_CHAR(NOW(), 'YYYY/MM/DD HH24:MI:SS') >= TO_CHAR(CH.DISCOUNT_PRICE_START_DATETIME, 'YYYY/MM/DD HH24:MI:SS') AND TO_CHAR(NOW(), 'YYYY/MM/DD HH24:MI:SS'::TEXT) <= TO_CHAR(CH.DISCOUNT_PRICE_END_DATETIME, 'YYYY/MM/DD HH24:MI:SS'::TEXT) THEN 1 ");
        b.append("WHEN CH.DISCOUNT_PRICE_START_DATETIME IS NOT NULL AND CH.DISCOUNT_PRICE_END_DATETIME IS NULL AND TO_CHAR(NOW(), 'YYYY/MM/DD HH24:MI:SS'::TEXT) >= TO_CHAR(CH.DISCOUNT_PRICE_START_DATETIME, 'YYYY/MM/DD HH24:MI:SS'::TEXT) AND TO_CHAR(NOW(), 'YYYY/MM/DD HH24:MI:SS'::TEXT) <= TO_CHAR(CH.SALE_END_DATETIME, 'YYYY/MM/DD HH24:MI:SS'::TEXT) THEN 1 ");
        b.append("WHEN CH.DISCOUNT_PRICE_START_DATETIME IS NULL AND CH.DISCOUNT_PRICE_END_DATETIME IS NOT NULL AND TO_CHAR(NOW(), 'YYYY/MM/DD HH24:MI:SS'::TEXT) >= TO_CHAR(CH.SALE_START_DATETIME, 'YYYY/MM/DD HH24:MI:SS'::TEXT) AND TO_CHAR(NOW(), 'YYYY/MM/DD HH24:MI:SS'::TEXT) <= TO_CHAR(CH.DISCOUNT_PRICE_END_DATETIME, 'YYYY/MM/DD HH24:MI:SS'::TEXT) THEN 1 ");
        b.append("ELSE 0 END DESC ,CH.COMMODITY_POPULAR_RANK ASC");
        break;
      case BY_RECOMMEND_SCORE:
        // 20120914 update by yyq start 
        b.append("CASE WHEN  ");
        b.append(" (SELECT STOCK_QUANTITY - ALLOCATED_QUANTITY AS ACT_STOCK FROM STOCK WHERE CD.SHOP_CODE = SHOP_CODE AND CD.SKU_CODE = SKU_CODE) > 0 OR CH.STOCK_MANAGEMENT_TYPE = 1 THEN 1 ELSE 0 END DESC , ");
        // 20120914 update by yyq end 
        b.append("CASE WHEN RS.REVIEW_SCORE IS NULL THEN 0 ELSE RS.REVIEW_SCORE END DESC, ");
        b.append("CASE WHEN CH.DISCOUNT_PRICE_START_DATETIME IS NOT NULL AND CH.DISCOUNT_PRICE_END_DATETIME IS NOT NULL AND TO_CHAR(NOW(), 'YYYY/MM/DD HH24:MI:SS') >= TO_CHAR(CH.DISCOUNT_PRICE_START_DATETIME, 'YYYY/MM/DD HH24:MI:SS') AND TO_CHAR(NOW(), 'YYYY/MM/DD HH24:MI:SS'::TEXT) <= TO_CHAR(CH.DISCOUNT_PRICE_END_DATETIME, 'YYYY/MM/DD HH24:MI:SS'::TEXT) THEN 1 ");
        b.append("WHEN CH.DISCOUNT_PRICE_START_DATETIME IS NOT NULL AND CH.DISCOUNT_PRICE_END_DATETIME IS NULL AND TO_CHAR(NOW(), 'YYYY/MM/DD HH24:MI:SS'::TEXT) >= TO_CHAR(CH.DISCOUNT_PRICE_START_DATETIME, 'YYYY/MM/DD HH24:MI:SS'::TEXT) AND TO_CHAR(NOW(), 'YYYY/MM/DD HH24:MI:SS'::TEXT) <= TO_CHAR(CH.SALE_END_DATETIME, 'YYYY/MM/DD HH24:MI:SS'::TEXT) THEN 1 ");
        b.append("WHEN CH.DISCOUNT_PRICE_START_DATETIME IS NULL AND CH.DISCOUNT_PRICE_END_DATETIME IS NOT NULL AND TO_CHAR(NOW(), 'YYYY/MM/DD HH24:MI:SS'::TEXT) >= TO_CHAR(CH.SALE_START_DATETIME, 'YYYY/MM/DD HH24:MI:SS'::TEXT) AND TO_CHAR(NOW(), 'YYYY/MM/DD HH24:MI:SS'::TEXT) <= TO_CHAR(CH.DISCOUNT_PRICE_END_DATETIME, 'YYYY/MM/DD HH24:MI:SS'::TEXT) THEN 1 ");
        b.append("ELSE 0 END DESC ,CH.COMMODITY_POPULAR_RANK ASC");
        break;
      case BY_NEW_COMMODITY_SCORE:
        // 20120914 update by yyq start 
        b.append("CASE WHEN  ");
        b.append(" (SELECT STOCK_QUANTITY - ALLOCATED_QUANTITY AS ACT_STOCK FROM STOCK WHERE CD.SHOP_CODE = SHOP_CODE AND CD.SKU_CODE = SKU_CODE) > 0 OR CH.STOCK_MANAGEMENT_TYPE = 1 THEN 1 ELSE 0 END DESC , ");
        // 20120914 update by yyq end 
        b.append(" CH.SALE_START_DATETIME DESC, ");
        b.append("CASE WHEN CH.DISCOUNT_PRICE_START_DATETIME IS NOT NULL AND CH.DISCOUNT_PRICE_END_DATETIME IS NOT NULL AND TO_CHAR(NOW(), 'YYYY/MM/DD HH24:MI:SS') >= TO_CHAR(CH.DISCOUNT_PRICE_START_DATETIME, 'YYYY/MM/DD HH24:MI:SS') AND TO_CHAR(NOW(), 'YYYY/MM/DD HH24:MI:SS'::TEXT) <= TO_CHAR(CH.DISCOUNT_PRICE_END_DATETIME, 'YYYY/MM/DD HH24:MI:SS'::TEXT) THEN 1 ");
        b.append("WHEN CH.DISCOUNT_PRICE_START_DATETIME IS NOT NULL AND CH.DISCOUNT_PRICE_END_DATETIME IS NULL AND TO_CHAR(NOW(), 'YYYY/MM/DD HH24:MI:SS'::TEXT) >= TO_CHAR(CH.DISCOUNT_PRICE_START_DATETIME, 'YYYY/MM/DD HH24:MI:SS'::TEXT) AND TO_CHAR(NOW(), 'YYYY/MM/DD HH24:MI:SS'::TEXT) <= TO_CHAR(CH.SALE_END_DATETIME, 'YYYY/MM/DD HH24:MI:SS'::TEXT) THEN 1 ");
        b.append("WHEN CH.DISCOUNT_PRICE_START_DATETIME IS NULL AND CH.DISCOUNT_PRICE_END_DATETIME IS NOT NULL AND TO_CHAR(NOW(), 'YYYY/MM/DD HH24:MI:SS'::TEXT) >= TO_CHAR(CH.SALE_START_DATETIME, 'YYYY/MM/DD HH24:MI:SS'::TEXT) AND TO_CHAR(NOW(), 'YYYY/MM/DD HH24:MI:SS'::TEXT) <= TO_CHAR(CH.DISCOUNT_PRICE_END_DATETIME, 'YYYY/MM/DD HH24:MI:SS'::TEXT) THEN 1 ");
        b.append("ELSE 0 END DESC ,CH.COMMODITY_POPULAR_RANK ASC");
        break;
      default:
        break;
    }
//  b.append(" CH.COMMODITY_CODE ");

    setSqlString(b.toString());
    setParameters(params.toArray());
    setMaxFetchSize(condition.getMaxFetchSize());
    setPageNumber(condition.getCurrentPage());
    setPageSize(condition.getPageSize());
  }

  private void setKeyWordCondition(CommodityContainerCondition condition, StringBuilder builder, List<Object> params) {
    String searchWord = condition.getSearchWord();
    if(!StringUtil.isNullOrEmpty(condition.getSearchWord())){
      searchWord = StringUtil.getWordAndWordOnlyOneSpace(searchWord);
    }
      List<String> searchWordList = StringUtil.getSearchWordStringList(searchWord, 
        DIContainer.getWebshopConfig().getSearchWordMaxLength());
      //String currentLanguageCode = DIContainer.getLocaleContext().getCurrentLanguageCode();
      String searchSql = "";
      searchSql = "(lower(CH.KEYWORD_JP1) LIKE ? OR lower(CH.KEYWORD_JP2) LIKE ?) OR (lower(CH.KEYWORD_CN1) LIKE ? OR lower(CH.KEYWORD_CN2) LIKE ?) or (lower(CH.KEYWORD_EN1) LIKE ? OR lower(CH.KEYWORD_EN2) LIKE ?)";
//      if (currentLanguageCode.equals("ja-jp")){
//        searchSql = "(lower(CH.KEYWORD_JP1) LIKE ? OR lower(CH.KEYWORD_JP2) LIKE ?)";
//      }else if (currentLanguageCode.equals("zh-cn")){
//        searchSql = "(lower(CH.KEYWORD_CN1) LIKE ? OR lower(CH.KEYWORD_CN2) LIKE ?)";
//      }else{
//        searchSql = "(lower(CH.KEYWORD_EN1) LIKE ? OR lower(CH.KEYWORD_EN2) LIKE ?)";
//      }
    if (!searchWordList.isEmpty()) {
        builder.append(" AND (");
        for (int i = 0; i < searchWordList.size(); i++) {
            if(i>=1 && i!=searchWordList.size() ){
                builder.append(" AND ");
            }
            builder.append(searchSql);
            params.add("%" + SqlDialect.getDefault().escape(searchWordList.get(i).toLowerCase().trim()) + "%"); 
            params.add("%" + SqlDialect.getDefault().escape(searchWordList.get(i).toLowerCase().trim()) + "%"); 
            params.add("%" + SqlDialect.getDefault().escape(searchWordList.get(i).toLowerCase().trim()) + "%"); 
            params.add("%" + SqlDialect.getDefault().escape(searchWordList.get(i).toLowerCase().trim()) + "%"); 
            params.add("%" + SqlDialect.getDefault().escape(searchWordList.get(i).toLowerCase().trim()) + "%"); 
            params.add("%" + SqlDialect.getDefault().escape(searchWordList.get(i).toLowerCase().trim()) + "%"); 
        }
        builder.append(")");
        
    }
      
  }

  private void appendKeywordConditionParts(StringBuilder builder, List<Object> params, String value, String... columns) {
    StringBuilder subCondition = new StringBuilder();
    subCondition.append("(");
    String joint = "";
    for (String column : columns) {
      SqlFragment fragment = SqlDialect.getDefault().createLikeClause(column, value, LikeClauseOption.PARTIAL_MATCH, false);
      if (StringUtil.hasValue(fragment.getFragment())) {
        subCondition.append(joint + fragment.getFragment());
        joint = " OR ";
        for (Object o : fragment.getParameters()) {
          params.add(o);
        }
      }
    }
    subCondition.append(")");
    builder.append(subCondition);
  }
}
