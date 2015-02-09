package jp.co.sint.webshop.service.catalog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.co.sint.webshop.configure.WebshopConfig;
import jp.co.sint.webshop.data.AbstractQuery;
import jp.co.sint.webshop.data.domain.CommodityDisplayOrder;
import jp.co.sint.webshop.data.domain.DisplayClientType;
import jp.co.sint.webshop.data.domain.DisplayFlg;
import jp.co.sint.webshop.data.domain.PartsCode;
import jp.co.sint.webshop.data.domain.SaleFlg;
import jp.co.sint.webshop.service.CommodityHeadline;
import jp.co.sint.webshop.service.catalog.CommodityContainerCondition.SearchDetailAttributeList;
import jp.co.sint.webshop.utility.ArrayUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.SqlDialect;
import jp.co.sint.webshop.utility.SqlFragment;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.utility.SqlDialect.LikeClauseOption;

public class CommodityContainerQuery extends AbstractQuery<CommodityHeadline> {

  /**
   */
  private static final long serialVersionUID = 1L;

  private String relatedTableQuery;

  private String relatedTableQueryByB;

  private String relatedWhereQuery;

  private String sortQuery;
  
  private static final String SYSDATE = SqlDialect.getDefault().getCurrentDatetime();
  
  private static final String COMMODITY_LIST_VIEW_SQL = CatalogQuery.COMMODITY_COLUMN
  + " FROM COMMODITY_LIST_VIEW A INNER JOIN SHOP D ON A.SHOP_CODE = D.SHOP_CODE "
//postgreSQL start
  //+ " AND TRUNC(" + SYSDATE + ") "
  + " AND "+SqlDialect.getDefault().getTrunc()+"(" + SYSDATE + ") "
//postgreSQL end  
  + " BETWEEN COALESCE (D.OPEN_DATETIME , TO_DATE('" + DateUtil.toDateString(DateUtil.getMin()) + "', 'yyyy/MM/dd')) "
  + " AND COALESCE (D.CLOSE_DATETIME , TO_DATE('" + DateUtil.toDateString(DateUtil.getMax()) + "', 'yyyy/MM/dd')) "
  + "      LEFT OUTER JOIN STOCK_STATUS E ON A.SHOP_CODE = E.SHOP_CODE AND A.STOCK_STATUS_NO = E.STOCK_STATUS_NO "
  + "      LEFT OUTER JOIN REVIEW_SUMMARY F ON A.SHOP_CODE = F.SHOP_CODE AND A.COMMODITY_CODE  = F.COMMODITY_CODE "
  + "";

  private static final String COMMODITY_SUMMARY_VIEW_SQL = " SELECT A.SHOP_CODE "
  + " , A.COMMODITY_CODE "
  + " , A.SKU_CODE "
  + " , A.REPRESENT_SKU_CODE "
  + " , A.COMMODITY_STANDARD1_NAME "
  + " , A.COMMODITY_STANDARD2_NAME "
  + " , A.STANDARD_DETAIL1_NAME "
  + " , A.STANDARD_DETAIL2_NAME "
  + " , A.COMMODITY_NAME "
  + " , A.COMMODITY_STANDARD1_NAME_EN "
  + " , A.COMMODITY_STANDARD2_NAME_EN "
  + " , A.STANDARD_DETAIL1_NAME_EN "
  + " , A.STANDARD_DETAIL2_NAME_EN "
  + " , A.COMMODITY_NAME_EN "
  + " , A.COMMODITY_STANDARD1_NAME_JP "
  + " , A.COMMODITY_STANDARD2_NAME_JP "
  + " , A.STANDARD_DETAIL1_NAME_JP "
  + " , A.STANDARD_DETAIL2_NAME_JP "
  + " , A.COMMODITY_NAME_JP "
  //20120522 tuxinwei add start
  + " , A.COMMODITY_NAME_EN "
  + " , A.COMMODITY_NAME_JP "
  //20120522 tuxinwei add end
  + " , A.UNIT_PRICE "
  + " , A.DISCOUNT_PRICE "
  + " , A.RESERVATION_PRICE "
  + " , A.SALE_START_DATETIME "
  + " , A.SALE_END_DATETIME "
  + " , A.DISCOUNT_PRICE_START_DATETIME "
  + " , A.DISCOUNT_PRICE_END_DATETIME "
  + " , A.RESERVATION_START_DATETIME "
  + " , A.RESERVATION_END_DATETIME "
  + " , A.COMMODITY_TAX_TYPE "
  + " , A.COMMODITY_DESCRIPTION_PC "
  + " , A.COMMODITY_DESCRIPTION_MOBILE "
  + " , A.COMMODITY_DESCRIPTION_PC_EN "
  + " , A.COMMODITY_DESCRIPTION_MOBILE_EN "
  + " , A.COMMODITY_DESCRIPTION_PC_JP "
  + " , A.COMMODITY_DESCRIPTION_MOBILE_JP "
  + " , A.STOCK_MANAGEMENT_TYPE "
  // add by yyq start 20130415
  + " , A.IMPORT_COMMODITY_TYPE "
  + " , A.CLEAR_COMMODITY_TYPE "
  + " , A.RESERVE_COMMODITY_TYPE1 "
  + " , A.RESERVE_COMMODITY_TYPE2 "
  + " , A.RESERVE_COMMODITY_TYPE3 "
  + " , A.NEW_RESERVE_COMMODITY_TYPE1 "
  + " , A.NEW_RESERVE_COMMODITY_TYPE2 "
  + " , A.NEW_RESERVE_COMMODITY_TYPE3 "
  + " , A.NEW_RESERVE_COMMODITY_TYPE4 "
  + " , A.NEW_RESERVE_COMMODITY_TYPE5 "
  + " , A.INNER_QUANTITY "
  // add by yyq end 20130415
  + " , A.ARRIVAL_GOODS_FLG "
  + " , A.DELIVERY_TYPE_NO "
  + " , A.LINK_URL "
  + " , A.RECOMMEND_COMMODITY_RANK "
  + " , A.JAN_CODE "
  + " , A.COMMODITY_POINT_RATE "
  + " , A.COMMODITY_POINT_START_DATETIME "
  + " , A.COMMODITY_POINT_END_DATETIME "
  + " , A.SALE_FLG "
  + " , A.RETAIL_PRICE "
  + " , AVAILABLE_STOCK_QUANTITY_FUNC(A.SHOP_CODE, A.COMMODITY_CODE) AS AVAILABLE_STOCK_QUANTITY "
  + " , D.SHOP_NAME "
  + " , E.STOCK_STATUS_NO "
  + " , E.STOCK_SUFFICIENT_MESSAGE "
  + " , E.STOCK_LITTLE_MESSAGE "
  + " , E.OUT_OF_STOCK_MESSAGE "
  + " , E.STOCK_SUFFICIENT_THRESHOLD "
  + " , F.REVIEW_SCORE "
  + " , F.REVIEW_COUNT "
  + " , REVIEW_DISPLAY_STATUS(A.SHOP_CODE) AS DISPLAY_FLG "
  + " FROM COMMODITY_LIST_VIEW A "
  + " INNER JOIN SHOP D "
  + " ON A.SHOP_CODE = D.SHOP_CODE "
  + " LEFT OUTER JOIN STOCK_STATUS E "
  + " ON A.SHOP_CODE = E.SHOP_CODE "
  + " AND A.STOCK_STATUS_NO = E.STOCK_STATUS_NO "
  + " LEFT OUTER JOIN REVIEW_SUMMARY F "
  + " ON A.SHOP_CODE = F.SHOP_CODE "
  + " AND A.COMMODITY_CODE = F.COMMODITY_CODE "
  + "";

  private static final String REPRESENT_SKU_SQL = " AND A.REPRESENT_SKU_CODE = A.SKU_CODE ";

  private static final String FAVORITE_COMMODITY_SQL = " INNER JOIN FAVORITE_COMMODITY G ON A.SHOP_CODE = G.SHOP_CODE AND "
      + " A.SKU_CODE = G.SKU_CODE "
      + " INNER JOIN DELIVERY_TYPE H ON A.DELIVERY_TYPE_NO = H.DELIVERY_TYPE_NO AND A.SHOP_CODE = H.SHOP_CODE "
      // 20120914 update by yyq
      + " AND H.DISPLAY_FLG = " + DisplayFlg.VISIBLE.getValue() + " WHERE  A.SALE_FLG = 1  AND G.CUSTOMER_CODE = ? ";

  private static final String RECOMMEND_COMMODITY_SQL = " INNER JOIN RECOMMENDED_COMMODITY G ON A.SHOP_CODE = G.SHOP_CODE AND "
      + " A.COMMODITY_CODE = G.COMMODITY_CODE "
      + " INNER JOIN DELIVERY_TYPE H ON A.DELIVERY_TYPE_NO = H.DELIVERY_TYPE_NO AND A.SHOP_CODE = H.SHOP_CODE "
      // 20120914 update by yyq start
      + " AND H.DISPLAY_FLG = " + DisplayFlg.VISIBLE.getValue() + " WHERE G.CUSTOMER_CODE = ? AND A.ACT_STOCK > 0 AND A.SALE_FLG = "
      // 20120914 update by yyq end
      + SaleFlg.FOR_SALE.getValue() + " AND ((A.SALE_START_DATETIME <= " + SYSDATE 
      + " AND " + SYSDATE
      + " <= A.SALE_END_DATETIME) OR (A.RESERVATION_START_DATETIME <= "
      + SYSDATE + " AND "
//    postgreSQL start
      //+ SYSDATE + " <= A.RESERVATION_END_DATETIME)) AND TRUNC(SYSDATE) BETWEEN D.OPEN_DATETIME AND D.CLOSE_DATETIME ";
      + SYSDATE + " <= A.RESERVATION_END_DATETIME)) AND "+SqlDialect.getDefault().getTruncSysdate()+" BETWEEN D.OPEN_DATETIME AND D.CLOSE_DATETIME ";
//postgreSQL end

  // add by wjw 20120103 start
  private static final String RELATED_COMMODITY_FROM_SQL = "  , A.IMPORT_COMMODITY_TYPE , A.CLEAR_COMMODITY_TYPE , A.RESERVE_COMMODITY_TYPE1 , " +
      "A.RESERVE_COMMODITY_TYPE2 , A.RESERVE_COMMODITY_TYPE3 , A.NEW_RESERVE_COMMODITY_TYPE1, A.NEW_RESERVE_COMMODITY_TYPE2, A.NEW_RESERVE_COMMODITY_TYPE3" +
      ", A.NEW_RESERVE_COMMODITY_TYPE4, A.NEW_RESERVE_COMMODITY_TYPE5, A.INNER_QUANTITY, A.ACT_STOCK,A.DISCOUNTMODE,A.COMMODITY_POPULAR_RANK FROM COMMODITY_LIST_VIEW A "
      + " INNER JOIN SHOP D ON A.SHOP_CODE = D.SHOP_CODE "
      + " AND "+SqlDialect.getDefault().getTrunc()+"(" + SYSDATE + ") "
      + " BETWEEN COALESCE (D.OPEN_DATETIME , TO_DATE('" + DateUtil.toDateString(DateUtil.getMin()) + "', 'yyyy/MM/dd')) "
      + " AND COALESCE (D.CLOSE_DATETIME , TO_DATE('" + DateUtil.toDateString(DateUtil.getMax()) + "', 'yyyy/MM/dd')) "
      + " LEFT OUTER JOIN STOCK_STATUS E  ON A.SHOP_CODE = E.SHOP_CODE AND A.STOCK_STATUS_NO = E.STOCK_STATUS_NO "
      + " LEFT OUTER JOIN REVIEW_SUMMARY F ON A.SHOP_CODE = F.SHOP_CODE AND A.COMMODITY_CODE = F.COMMODITY_CODE "
      + " INNER JOIN DELIVERY_TYPE H ON A.DELIVERY_TYPE_NO = H.DELIVERY_TYPE_NO AND A.SHOP_CODE = H.SHOP_CODE "
      + " AND H.DISPLAY_FLG = " + DisplayFlg.VISIBLE.getValue()
      + " INNER JOIN COMMODITY_LAYOUT K ON A.SHOP_CODE = K.SHOP_CODE AND K.PARTS_CODE = '" + PartsCode.REVIEWS.getValue() + "' "
      + " INNER JOIN  (SELECT SHOP_CODE, LINK_COMMODITY_CODE, ";

  private static final String RELATED_COMMODITY_FROM_SQL_BYBRAND = " , a.act_stock,a.discountMode,a.commodity_popular_rank FROM COMMODITY_LIST_VIEW A "
      + " INNER JOIN SHOP D ON A.SHOP_CODE = D.SHOP_CODE "
      + " AND "+SqlDialect.getDefault().getTrunc()+"(" + SYSDATE + ") "
      + " BETWEEN COALESCE (D.OPEN_DATETIME , TO_DATE('" + DateUtil.toDateString(DateUtil.getMin()) + "', 'yyyy/MM/dd')) "
      + " AND COALESCE (D.CLOSE_DATETIME , TO_DATE('" + DateUtil.toDateString(DateUtil.getMax()) + "', 'yyyy/MM/dd')) "
      + " LEFT OUTER JOIN STOCK_STATUS E  ON A.SHOP_CODE = E.SHOP_CODE AND A.STOCK_STATUS_NO = E.STOCK_STATUS_NO "
      + " LEFT OUTER JOIN REVIEW_SUMMARY F ON A.SHOP_CODE = F.SHOP_CODE AND A.COMMODITY_CODE = F.COMMODITY_CODE "
      + " INNER JOIN DELIVERY_TYPE H ON A.DELIVERY_TYPE_NO = H.DELIVERY_TYPE_NO AND A.SHOP_CODE = H.SHOP_CODE "
      + " AND H.DISPLAY_FLG = " + DisplayFlg.VISIBLE.getValue()
      + " INNER JOIN COMMODITY_LAYOUT K ON A.SHOP_CODE = K.SHOP_CODE AND K.PARTS_CODE = '" + PartsCode.REVIEWS.getValue() + "' ";

  private static final String RELATED_COMMODITY_WHERE_SQL_BYBRAND = " "
      + "WHERE ((A.SALE_START_DATETIME <= "
      + SYSDATE + " AND "
      + SYSDATE
      + " <= A.SALE_END_DATETIME) OR (A.RESERVATION_START_DATETIME <= "
      + SYSDATE + " AND "
      + SYSDATE + " <= A.RESERVATION_END_DATETIME)) "
      // 20120914 update by yyq
      + " AND A.ACT_STOCK > 0 AND A.SALE_FLG = " + SaleFlg.FOR_SALE.getValue();
  // add by wjw 20120103 end
  
  private static final String RELATED_COMMODITY_WHERE_SQL = " A.COMMODITY_CODE = G.LINK_COMMODITY_CODE "
      + "WHERE ((A.SALE_START_DATETIME <= "
      + SYSDATE + " AND "
      + SYSDATE
      + " <= A.SALE_END_DATETIME) OR (A.RESERVATION_START_DATETIME <= "
      + SYSDATE + " AND "
      // 添加必须是有库存的商品的限制条件 20120914 update by yyq
      + SYSDATE + " <= A.RESERVATION_END_DATETIME)) AND (A.ACT_STOCK > 0 OR A.STOCK_MANAGEMENT_TYPE = 1) "
      + "AND A.SALE_FLG = " + SaleFlg.FOR_SALE.getValue() ;
  
//  private static final String CATEGORY_COMMODITY_VIEW = "" 
//    + " CATEGORY_COMMODITY_VIEW AS ( "
//    + "   SELECT SHOP_CODE, COMMODITY_CODE FROM CATEGORY_COMMODITY CC "
//    + "   INNER JOIN (SELECT CATEGORY_CODE FROM CATEGORY "
//    + "               START WITH CATEGORY_CODE = ? "
//    + "               CONNECT BY PRIOR CATEGORY_CODE = PARENT_CATEGORY_CODE) C "
//    + "   ON C.CATEGORY_CODE = CC.CATEGORY_CODE "
//    + "   GROUP BY SHOP_CODE, COMMODITY_CODE "
//    + " ) ";
  
  // postgreSQL start
  private static final String CATEGORY_COMMODITY_VIEW = "" //$NON-NLS-1$
      + " CATEGORY_COMMODITY_VIEW AS ( " //$NON-NLS-1$
      + "   SELECT SHOP_CODE, COMMODITY_CODE FROM CATEGORY_COMMODITY CC " //$NON-NLS-1$
      + "   INNER JOIN (" + categoryCodeListSQL() + ") C " //$NON-NLS-1$
      + "   ON C.CATEGORY_CODE = CC.CATEGORY_CODE " //$NON-NLS-1$
      + "   GROUP BY SHOP_CODE, COMMODITY_CODE " //$NON-NLS-1$
      + " ) "; //$NON-NLS-1$

  private static String categoryCodeListSQL() {
    StringBuffer sql = new StringBuffer();
  if (DIContainer.getWebshopConfig().isPostgreSQL() == true) {
    sql.append(" WITH RECURSIVE R AS ( ");
    sql.append("   SELECT CATEGORY_CODE FROM CATEGORY WHERE CATEGORY_CODE = ? ");
    sql.append("   UNION ALL ");
    sql.append("   SELECT CATEGORY.CATEGORY_CODE FROM CATEGORY, R WHERE CATEGORY.PARENT_CATEGORY_CODE = R.CATEGORY_CODE ");
    sql.append(" )");
    sql.append(" SELECT CATEGORY_CODE FROM R ");
  } else {
    sql.append(" SELECT CATEGORY_CODE FROM CATEGORY ");
    sql.append(" START WITH CATEGORY_CODE = ? ");
    sql.append(" CONNECT BY PRIOR CATEGORY_CODE = PARENT_CATEGORY_CODE ");
  }
  return sql.toString();
  }
  // postgreSQL end
  private static final String CAMPAIGN_COMMODITY_VIEW = ""
    + " CAMPAIGN_COMMODITY_VIEW AS ( "
    + "    SELECT SHOP_CODE, "
    + "           CAMPAIGN_CODE, "
    + "           COMMODITY_CODE "
    + "    FROM (SELECT CC.SHOP_CODE, "
    + "                 CC.CAMPAIGN_CODE, "
    + "                 CC.COMMODITY_CODE,  "
    + "                 RANK() OVER (PARTITION BY CC.SHOP_CODE, "
    + "                                           CC.COMMODITY_CODE "
    + "                              ORDER BY C.CAMPAIGN_DISCOUNT_RATE DESC, " 
    + "                                       C.CAMPAIGN_START_DATE, "
    + "                                       C.CAMPAIGN_CODE) AS RANK "
    + "          FROM CAMPAIGN_COMMODITY CC "
    + "          INNER JOIN CAMPAIGN C "
    + "          ON C.SHOP_CODE = CC.SHOP_CODE "
    + "          AND C.CAMPAIGN_CODE = CC.CAMPAIGN_CODE "
    + "          WHERE " + SYSDATE + " BETWEEN C.CAMPAIGN_START_DATE AND C.CAMPAIGN_END_DATE) "
    + "    WHERE RANK = 1 "
    + "    AND CAMPAIGN_CODE = ? "
    + " ) ";
  
  private static final String SALESCHARTS_COMMODITY_FROM_SQL = " , a.act_stock,a.discountMode,a.commodity_popular_rank FROM COMMODITY_LIST_VIEW A "
    + " INNER JOIN SHOP D ON A.SHOP_CODE = D.SHOP_CODE "
    + " AND "+SqlDialect.getDefault().getTrunc()+"(" + SYSDATE + ") "
    + " BETWEEN COALESCE (D.OPEN_DATETIME , TO_DATE('" + DateUtil.toDateString(DateUtil.getMin()) + "', 'yyyy/MM/dd')) "
    + " AND COALESCE (D.CLOSE_DATETIME , TO_DATE('" + DateUtil.toDateString(DateUtil.getMax()) + "', 'yyyy/MM/dd')) "
    + " LEFT OUTER JOIN STOCK_STATUS E  ON A.SHOP_CODE = E.SHOP_CODE AND A.STOCK_STATUS_NO = E.STOCK_STATUS_NO "
    + " LEFT OUTER JOIN REVIEW_SUMMARY F ON A.SHOP_CODE = F.SHOP_CODE AND A.COMMODITY_CODE = F.COMMODITY_CODE "
    + " INNER JOIN DELIVERY_TYPE H ON A.DELIVERY_TYPE_NO = H.DELIVERY_TYPE_NO AND A.SHOP_CODE = H.SHOP_CODE "
    + " AND H.DISPLAY_FLG = " + DisplayFlg.VISIBLE.getValue()
    + " INNER JOIN COMMODITY_LAYOUT K ON A.SHOP_CODE = K.SHOP_CODE AND K.PARTS_CODE = '" + PartsCode.REVIEWS.getValue() + "' ";

  private static final String SALESCHARTS_COMMODITY_WHERE_SQL = " WHERE ((A.SALE_START_DATETIME <= "
    + SYSDATE + " AND "
    + SYSDATE
    + " <= A.SALE_END_DATETIME) OR (A.RESERVATION_START_DATETIME <= "
    + SYSDATE + " AND "
    + SYSDATE + " <= A.RESERVATION_END_DATETIME)) AND A.CATEGORY_PATH IS NOT NULL "
    + " AND A.SALE_FLG = " + SaleFlg.FOR_SALE.getValue()
    + " AND A.ACT_STOCK > 0";

  public CommodityContainerQuery createFavoriteCommodityQuery(CommodityContainerCondition condition) {
    StringBuilder builder = new StringBuilder();

    // 商品一覧取得時の共通SQL
    builder.append(COMMODITY_LIST_VIEW_SQL);

    // 取得する商品一覧が商品コード単位かSKU単位かを設定
    if (condition.isByRepresent()) {
      builder.append(REPRESENT_SKU_SQL);
    }

    // お気に入り一覧の場合の個別SQL
    builder.append(FAVORITE_COMMODITY_SQL);

    // 表示クライアントタイプの指定
    setDisplayClientType(condition, builder);

    List<Object> params = new ArrayList<Object>();
    params.add(condition.getSearchCustomerCode());

    // 表示順の設定
    //builder.append(" ORDER BY G.FAVORITE_REGISTER_DATE DESC");

    // 並び順
  if (StringUtil.hasValue(condition.getSearchListSort())) {
    if (condition.getSearchListSort().equals("orderNo0")) {
      builder.append(" ORDER BY G.FAVORITE_REGISTER_DATE ");
    } else if (condition.getSearchListSort().equals("orderNo1")) {
      builder.append(" ORDER BY G.FAVORITE_REGISTER_DATE DESC ");
    }
  } else {
    builder.append(" ORDER BY G.FAVORITE_REGISTER_DATE DESC");
  }
  
    
    setSqlString(builder.toString());
    setParameters(params.toArray());
    setPageNumber(condition.getCurrentPage());
    setPageSize(condition.getPageSize());

    return this;
  }

  public CommodityContainerQuery createRecomendCommodityQuery(CommodityContainerCondition condition) {
    StringBuilder builder = new StringBuilder();

    // 商品一覧取得時の共通SQL
    builder.append(COMMODITY_SUMMARY_VIEW_SQL);

    // おすすめ商品の場合の個別SQL
    builder.append(RECOMMEND_COMMODITY_SQL);

    // 取得する商品一覧が商品コード単位かSKU単位かを設定
    if (condition.isByRepresent()) {
      builder.append(REPRESENT_SKU_SQL);
    }
    
    builder.append(" AND A.CATEGORY_PATH IS NOT NULL ");
    // 表示クライアントタイプの指定
    setDisplayClientType(condition, builder);

    List<Object> params = new ArrayList<Object>();
    params.add(condition.getSearchCustomerCode());

    // 表示順の設定
    builder.append(" ORDER BY G.DISPLAY_ORDER");

    setSqlString(builder.toString());
    setParameters(params.toArray());
    setPageNumber(condition.getCurrentPage());
    setPageSize(condition.getPageSize());  
    setMaxFetchSize(condition.getMaxFetchSize());

    return this;
  }

  /**
   * @param condition
   * @param builder
   */
  private void setDisplayClientType(CommodityContainerCondition condition, StringBuilder builder) {
    if (StringUtil.hasValue(condition.getDisplayClientType())
        && (condition.getDisplayClientType().equals(DisplayClientType.ALL.getValue()) || condition.getDisplayClientType().equals(
            DisplayClientType.PC.getValue()))) {
      builder.append(CatalogQuery.DISPLAY_PC_SQL);
    } else if (StringUtil.hasValue(condition.getDisplayClientType())
        && (condition.getDisplayClientType().equals(DisplayClientType.ALL.getValue()) || condition.getDisplayClientType().equals(
            DisplayClientType.MOBILE.getValue()))) {
      builder.append(CatalogQuery.DISPLAY_MOBILE_SQL);
    }
  }

  public CommodityContainerQuery createCommodityListQuery(CommodityContainerCondition condition) {

    StringBuilder builder = new StringBuilder();
    List<Object> params = new ArrayList<Object>();

    // WITH句開始
    builder.append(" WITH ");
    
    // カテゴリ検索
    builder.append(CATEGORY_COMMODITY_VIEW);
    params.add(condition.getSearchCategoryCode());
    
    // キャンペーン検索
    if (StringUtil.hasValue(condition.getSearchCampaignCode())) {
      builder.append(" , " + CAMPAIGN_COMMODITY_VIEW);
      params.add(condition.getSearchCampaignCode());
    }
    
    // 商品一覧取得時の共通SQL
    if (condition.isByRepresent()) {
      builder.append(COMMODITY_SUMMARY_VIEW_SQL);
    } else {
      builder.append(COMMODITY_LIST_VIEW_SQL);
    }

    builder.append("INNER JOIN CATEGORY_COMMODITY_VIEW G ON A.SHOP_CODE = G.SHOP_CODE AND A.COMMODITY_CODE = G.COMMODITY_CODE ");
    
    if (StringUtil.hasValue(condition.getSearchCampaignCode())) {
      builder.append(" INNER JOIN CAMPAIGN_COMMODITY_VIEW H ON A.SHOP_CODE = H.SHOP_CODE AND A.COMMODITY_CODE = H.COMMODITY_CODE ");
    }
    
    if (StringUtil.hasValue(condition.getSearchTagCode())) {
      builder.append(" INNER JOIN TAG_COMMODITY M ON A.SHOP_CODE = M.SHOP_CODE AND A.COMMODITY_CODE = M.COMMODITY_CODE ");
    }

    builder.append("INNER JOIN DELIVERY_TYPE J ON A.DELIVERY_TYPE_NO = J.DELIVERY_TYPE_NO AND A.SHOP_CODE = J.SHOP_CODE ");
    if (!condition.isIgnoreDeliveryDisplsayType()) {
      builder.append("AND J.DISPLAY_FLG = " + DisplayFlg.VISIBLE.getValue());
    }

    // カテゴリ属性検索
    setCategoryAttributeCondition(condition, builder, params);

    // 商品検索SQLのWhere句開始
    builder.append(" WHERE (( " + SYSDATE + " BETWEEN A.SALE_START_DATETIME AND A.SALE_END_DATETIME) "
                      + " OR ( " + SYSDATE + " BETWEEN COALESCE(A.RESERVATION_START_DATETIME, ?) AND A.RESERVATION_END_DATETIME)) "
                                + " AND A.SALE_FLG = " + SaleFlg.FOR_SALE.getValue());
    params.add(DateUtil.getMin());

    builder.append(" AND " + SYSDATE + " BETWEEN D.OPEN_DATETIME AND D.CLOSE_DATETIME ");
  
    // 表示クライアントタイプの指定
    setDisplayClientType(condition, builder);

    // キーワード検索
    setKeyWordCondition(condition, builder, params);

    // 商品コード検索
    if (StringUtil.hasValue(condition.getSearchCommodityCode())) {
      builder.append(" AND A.COMMODITY_CODE = ? ");
      params.add(condition.getSearchCommodityCode());
    }

    // SKUコード検索(前方一致)
    if (StringUtil.hasValue(condition.getSearchSkuCode())) {
      SqlFragment skuCodeFragment = SqlDialect.getDefault().createLikeClause("A.SKU_CODE", condition.getSearchSkuCode(),
          LikeClauseOption.STARTS_WITH);
      builder.append(" AND " + skuCodeFragment.getFragment());
      params.addAll(Arrays.asList(skuCodeFragment.getParameters()));
    }

    // 商品名検索(部分一致)
    if (StringUtil.hasValue(condition.getSearchCommodityName())) {
      SqlFragment commodityNameFragment = SqlDialect.getDefault().createLikeClause("A.COMMODITY_NAME",
          condition.getSearchCommodityName(), LikeClauseOption.PARTIAL_MATCH);
      builder.append(" AND " + commodityNameFragment.getFragment());
      params.addAll(Arrays.asList(commodityNameFragment.getParameters()));
    }

    // ショップ検索
    if (StringUtil.hasValue(condition.getSearchShopCode())) {
      builder.append(" AND A.SHOP_CODE = ? ");
      params.add(condition.getSearchShopCode());
    }

    // 取得する商品一覧が商品コード単位かSKU単位かを設定
    if (condition.isByRepresent()) {
      builder.append(REPRESENT_SKU_SQL);
    }

    // レビュースコア検索
    if (StringUtil.hasValue(condition.getReviewScore())) {
      builder.append(" AND F.REVIEW_SCORE = ? ");
      params.add(condition.getReviewScore());
    }
    
    // タグ検索
    if (StringUtil.hasValue(condition.getSearchTagCode())) {
      builder.append(" AND M.TAG_CODE = ? ");
      params.add(condition.getSearchTagCode());
    }

    // 商品価格の範囲検索
    setPriceRangeCondition(condition, builder, params);
    
    // 表示順の設定
    setDisplayOrderCondition(condition, builder);

    setSqlString(builder.toString());
    setParameters(params.toArray());
    setMaxFetchSize(condition.getMaxFetchSize());
    setPageNumber(condition.getCurrentPage());
    setPageSize(condition.getPageSize());

    return this;
  }
  
  /**
   * @param condition
   * @param builder
   * @param params
   */
  private void setCategoryAttributeCondition(CommodityContainerCondition condition, StringBuilder builder, List<Object> params) {
    int attributeColumnSize = 0;
    for (SearchDetailAttributeList da : condition.getSearchDetailAttributeList()) {
      if (StringUtil.hasValue(da.getCategoryAttributeValue())) {
        attributeColumnSize++;
      }
    }

    if (attributeColumnSize > 0) {
      builder.append(" INNER JOIN (SELECT SHOP_CODE, COMMODITY_CODE FROM CATEGORY_ATTRIBUTE_VALUE ");
      builder.append(" WHERE CATEGORY_CODE = ? AND ( ");
      params.add(condition.getSearchCategoryCode());
      for (int i = 0; i < condition.getSearchDetailAttributeList().size(); i++) {
        if (StringUtil.hasValue(condition.getSearchDetailAttributeList().get(i).getCategoryAttributeValue())) {
          builder.append("  ( CATEGORY_ATTRIBUTE_NO = ? AND ");
          params.add(condition.getSearchDetailAttributeList().get(i).getCategoryAttributeNo());
          SqlFragment fragment = SqlDialect.getDefault().createLikeClause("CATEGORY_ATTRIBUTE_VALUE",
              condition.getSearchDetailAttributeList().get(i).getCategoryAttributeValue(), LikeClauseOption.PARTIAL_MATCH);
          builder.append(fragment.getFragment() + " ");
          for (Object o : fragment.getParameters()) {
            params.add(o);
          }
          builder.append(" ) ");

          if (attributeColumnSize > 1) {
            builder.append(" OR ");
          }
          attributeColumnSize--;
        }
      }
      builder.append(" ) ");
      builder.append(" GROUP BY SHOP_CODE, COMMODITY_CODE) L ON A.SHOP_CODE = L.SHOP_CODE AND A.COMMODITY_CODE = L.COMMODITY_CODE");
    }
  }

  /**
   * @param condition
   * @param builder
   */
  private void setDisplayOrderCondition(CommodityContainerCondition condition, StringBuilder builder) {
    WebshopConfig config = DIContainer.getWebshopConfig();

    if (StringUtil.isNullOrEmpty(condition.getAlignmentSequence())) {
      builder.append(" ORDER BY A.RECOMMEND_COMMODITY_RANK ASC, A.COMMODITY_CODE ");
    } else if (condition.getAlignmentSequence().equals(CommodityDisplayOrder.BY_PRICE_ASCENDING.getValue())) {
      // 価格の安い順
      builder.append(" ORDER BY A.RETAIL_PRICE ASC, A.COMMODITY_CODE ");
    } else if (condition.getAlignmentSequence().equals(CommodityDisplayOrder.BY_PRICE_DESCENDING.getValue())) {
      // 価格の高い順
      builder.append(" ORDER BY A.RETAIL_PRICE DESC, A.COMMODITY_CODE ");
    } else if (condition.getAlignmentSequence().equals(CommodityDisplayOrder.BY_POPULAR_RANKING.getValue())) {
      // 人気順
      if (config.isSortedOrderAmount()) {
        builder.append(" ORDER BY LAST_ORDER_RANKING(A.SHOP_CODE, A.COMMODITY_CODE) ASC, A.COMMODITY_CODE ");
      } else {
        builder.append(" ORDER BY LAST_COUNT_RANKING(A.SHOP_CODE, A.COMMODITY_CODE) ASC, A.COMMODITY_CODE ");
      }
    } else if (condition.getAlignmentSequence().equals(CommodityDisplayOrder.BY_COMMODITY_NAME.getValue())) {
      // 商品名順
      builder.append(" ORDER BY A.COMMODITY_NAME ASC, A.COMMODITY_CODE ");
    } else if (condition.getAlignmentSequence().equals(CommodityDisplayOrder.BY_RECOMMEND_SCORE.getValue())) {
      // おすすめ順
      builder.append(" ORDER BY A.RECOMMEND_COMMODITY_RANK ASC, A.COMMODITY_CODE ");
    } else if (condition.getAlignmentSequence().equals("back_neworder")) {
      // 管理側新規受注商品検索用
      builder.append(" ORDER BY A.SHOP_CODE, A.SKU_CODE ASC ");
    }
  }

  /**
   * @param condition
   * @param builder
   * @param params
   */
  private void setPriceRangeCondition(CommodityContainerCondition condition, StringBuilder builder, List<Object> params) {
    if (StringUtil.hasValue(condition.getSearchPriceStart()) && StringUtil.hasValue(condition.getSearchPriceEnd())) {
      builder.append(" AND A.RETAIL_PRICE BETWEEN ? AND ? ");
      params.add(condition.getSearchPriceStart());
      params.add(condition.getSearchPriceEnd());
    } else if (StringUtil.hasValue(condition.getSearchPriceStart())) {
      builder.append(" AND A.RETAIL_PRICE >= ? ");
      params.add(condition.getSearchPriceStart());
    } else if (StringUtil.hasValue(condition.getSearchPriceEnd())) {
      builder.append(" AND A.RETAIL_PRICE <= ? ");
      params.add(condition.getSearchPriceEnd());
    }
  }

  /**
   * @param condition
   * @param builder
   * @param params
   */
  private void setKeyWordCondition(CommodityContainerCondition condition, StringBuilder builder, List<Object> params) {
    List<String> searchWordList = StringUtil.getSearchWordStringList(StringUtil.toSearchKeywords(condition.getSearchWord()),
        DIContainer.getWebshopConfig().getSearchWordMaxLength());

    String[] defaultSearchColumns = new String[] {
      "A.SHADOW_SEARCH_WORDS",
    };
    // 検索対象カラム
    List<String> searchColumns = new ArrayList<String>();
    for (String s : defaultSearchColumns) {
      searchColumns.add(s);
    }

   if (!searchWordList.isEmpty()) {
      builder.append(" AND (");
      for (int i = 0; i < searchWordList.size(); i++) {
        if (StringUtil.hasValue(searchWordList.get(i).trim())) {
          appendKeywordConditionParts(builder, params, searchWordList.get(i).trim(), searchColumns.toArray(new String[searchColumns
              .size()]));
          if (i != searchWordList.size() - 1 && searchWordList.size() != 1) {
            String searchMethod = StringUtil.coalesce(condition.getSearchMethod(), "0");
            if (searchMethod.equals("0")) {
              builder.append(" AND ");
            } else {
              builder.append(" OR ");
            }
          }
        }
      }
      builder.append(")");
    }
  }

  /** キーワードと、検索対象となるカラムの一覧を受け取って検索条件を作成します */
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

  private CommodityContainerQuery createRelatedCommodityList(CommodityContainerCondition condition) {

    StringBuilder builder = new StringBuilder();
    builder.append(relatedTableQueryByB);
    builder.append(CatalogQuery.COMMODITY_COLUMN);
    builder.append(RELATED_COMMODITY_FROM_SQL);
    builder.append(relatedTableQuery);
    builder.append(relatedWhereQuery);
    builder.append(RELATED_COMMODITY_WHERE_SQL);
    builder.append(REPRESENT_SKU_SQL);
    builder.append("AND A.RESERVE_COMMODITY_TYPE1 <> 1 ");

    if (DisplayClientType.PC.getValue().equals(condition.getDisplayClientType())) {
      builder.append(CatalogQuery.DISPLAY_PC_SQL);
    } else if (DisplayClientType.MOBILE.getValue().equals(condition.getDisplayClientType())) {
      builder.append(CatalogQuery.DISPLAY_MOBILE_SQL);
    } else {
      throw new RuntimeException();
    }

    // 表示クライアントタイプの指定
    setDisplayClientType(condition, builder);

    builder.append(sortQuery);

    List<Object> params = new ArrayList<Object>();
    params.add(condition.getSearchShopCode());
    // 20120217 shen update start
    // params.add(condition.getSearchCommodityCode());
    if (StringUtil.hasValueAnyOf(condition.getSearchCartCommodityCode())) {
      params.addAll(Arrays.asList(condition.getSearchCartCommodityCode()));
    } else {
      params.add(condition.getSearchCommodityCode());
    }
    // 20120217 shen update end
    setSqlString(builder.toString());
    setParameters(params.toArray());
    setPageNumber(condition.getCurrentPage());
    setMaxFetchSize(condition.getMaxFetchSize());
    setPageSize(getMaxFetchSize());

    return this;
  }

  // add by wjw 20120103 start
  private CommodityContainerQuery createRelatedCommodityListByBrand(CommodityContainerCondition condition) {

    StringBuilder builder = new StringBuilder();
    builder.append(relatedTableQuery);
    builder.append(CatalogQuery.COMMODITY_COLUMN);
    builder.append(RELATED_COMMODITY_FROM_SQL_BYBRAND);
    builder.append(RELATED_COMMODITY_WHERE_SQL_BYBRAND);
    builder.append(relatedWhereQuery);
    builder.append(REPRESENT_SKU_SQL);

    if (DisplayClientType.PC.getValue().equals(condition.getDisplayClientType())) {
      builder.append(CatalogQuery.DISPLAY_PC_SQL);
    } else if (DisplayClientType.MOBILE.getValue().equals(condition.getDisplayClientType())) {
      builder.append(CatalogQuery.DISPLAY_MOBILE_SQL);
    } else {
      throw new RuntimeException();
    }

    // 表示クライアントタイプの指定
    setDisplayClientType(condition, builder);

    builder.append(sortQuery);

    List<Object> params = new ArrayList<Object>();
    params.add(condition.getSearchShopCode());
    params.add(condition.getSearchCommodityCode());
    params.add(condition.getSearchBrandCode());

    setSqlString(builder.toString());
    setParameters(params.toArray());
    setPageNumber(condition.getCurrentPage());
    setMaxFetchSize(condition.getMaxFetchSize());
    setPageSize(getMaxFetchSize());

    return this;
  }
  // add by wjw 20120103 end

  public CommodityContainerQuery createRelatedCommodityAList(CommodityContainerCondition condition) {
    relatedTableQueryByB = "";
    relatedTableQuery = "DISPLAY_ORDER FROM RELATED_COMMODITY_A";
    relatedWhereQuery = " WHERE  SHOP_CODE = ? AND COMMODITY_CODE = ? " 
      + " ) G ON A.SHOP_CODE = G.SHOP_CODE AND ";
    sortQuery = " ORDER BY G.DISPLAY_ORDER, A.COMMODITY_CODE ";
    return createRelatedCommodityList(condition);
  }

  // add by wjw 20120103 start
  public CommodityContainerQuery createRelatedCommodityCList(CommodityContainerCondition condition) {
    StringBuilder builder = new StringBuilder();
    builder.append(" SELECT * FROM ( "); 
    builder.append(" SELECT A.COMMODITY_CODE, "); 
    builder.append(" A.SHOP_CODE, ");
    builder.append(" A.SKU_CODE, ");
    builder.append(" A.REPRESENT_SKU_CODE, ");
    builder.append(" A.COMMODITY_NAME,  ");
    builder.append(" A.UNIT_PRICE, ");
    builder.append(" A.DISCOUNT_PRICE, ");
    builder.append(" A.RESERVATION_PRICE,  ");
    builder.append(" A.SALE_START_DATETIME, ");
    builder.append(" A.SALE_END_DATETIME, ");
    builder.append(" A.DISCOUNT_PRICE_START_DATETIME, ");
    builder.append(" A.DISCOUNT_PRICE_END_DATETIME,  ");
    builder.append(" A.RESERVATION_START_DATETIME, ");
    builder.append(" A.RESERVATION_END_DATETIME, ");
    builder.append(" A.COMMODITY_TAX_TYPE, ");
    builder.append(" A.STOCK_MANAGEMENT_TYPE,  ");
    builder.append(" A.COMMODITY_POINT_RATE, ");
    builder.append(" A.COMMODITY_POINT_START_DATETIME, "); 
    builder.append(" A.COMMODITY_POINT_END_DATETIME, ");
    builder.append(" A.RETAIL_PRICE,  ");
    builder.append(" A.COMMODITY_NAME_EN, ");
    builder.append(" A.COMMODITY_NAME_JP, ");
    builder.append(" A.ACT_STOCK, ");
    builder.append(" A.DISCOUNTMODE, ");
    //add by yyq start 20130313
    builder.append(" A.IMPORT_COMMODITY_TYPE, ");
    builder.append(" A.CLEAR_COMMODITY_TYPE, ");
    builder.append(" A.RESERVE_COMMODITY_TYPE1, ");
    builder.append(" A.RESERVE_COMMODITY_TYPE2, ");
    builder.append(" A.RESERVE_COMMODITY_TYPE3, ");
    builder.append(" A.NEW_RESERVE_COMMODITY_TYPE1, ");
    builder.append(" A.NEW_RESERVE_COMMODITY_TYPE2, ");
    builder.append(" A.NEW_RESERVE_COMMODITY_TYPE3, ");
    builder.append(" A.NEW_RESERVE_COMMODITY_TYPE4, ");
    builder.append(" A.NEW_RESERVE_COMMODITY_TYPE5, ");
    builder.append(" A.INNER_QUANTITY, ");
    builder.append(" A.COMBINATION_AMOUNT, ");
    builder.append(" A.ORIGINAL_COMMODITY_CODE, ");
    //add by yyq end 20130313
    builder.append(" A.COMMODITY_POPULAR_RANK ");
    builder.append(" FROM COMMODITY_LIST_VIEW A ");
    builder.append(" WHERE (A.SALE_START_DATETIME <= NOW() AND NOW() <= A.SALE_END_DATETIME) ");
    builder.append(" AND ((SELECT STOCK_QUANTITY - ALLOCATED_QUANTITY AS ACT_STOCK FROM STOCK WHERE  SKU_CODE = ( CASE WHEN A.ORIGINAL_COMMODITY_CODE IS NULL THEN A.COMMODITY_CODE ELSE A.ORIGINAL_COMMODITY_CODE END)) > 0 OR A.STOCK_MANAGEMENT_TYPE = 1)  " );
    builder.append(" AND ((SELECT STOCK_QUANTITY - ALLOCATED_QUANTITY AS ACT_STOCK FROM STOCK WHERE  SKU_CODE = ( CASE WHEN A.ORIGINAL_COMMODITY_CODE IS NULL THEN A.COMMODITY_CODE ELSE A.ORIGINAL_COMMODITY_CODE END)) >=  CASE WHEN A.COMBINATION_AMOUNT IS NULL THEN 0 ELSE A.COMBINATION_AMOUNT END OR A.STOCK_MANAGEMENT_TYPE = 1)" );
    builder.append(" AND A.SALE_FLG = 1 AND A.COMMODITY_TYPE = 0 AND A.REPRESENT_SKU_CODE = A.SKU_CODE ");
    builder.append(" AND A.SHOP_CODE = ? AND A.COMMODITY_CODE <> ?  AND A.BRAND_CODE = ?  AND A.RESERVE_COMMODITY_TYPE1 <> 1) AS BND  ");
    builder.append(" ORDER BY CASE WHEN BND.ACT_STOCK > 0 OR BND.STOCK_MANAGEMENT_TYPE = 1 THEN 1 ELSE 0 END DESC,   ");
    builder.append(" BND.DISCOUNTMODE DESC,BND.COMMODITY_POPULAR_RANK DESC,BND.COMMODITY_CODE  ");

    List<Object> params = new ArrayList<Object>();
    params.add(condition.getSearchShopCode());
    params.add(condition.getSearchCommodityCode());
    params.add(condition.getSearchBrandCode());

    setSqlString(builder.toString());
    setParameters(params.toArray());
    setPageNumber(condition.getCurrentPage());
    setMaxFetchSize(condition.getMaxFetchSize());
    setPageSize(getMaxFetchSize());

    return this;
  }

  public CommodityContainerQuery createRelatedCommodityBList(CommodityContainerCondition condition) {
    relatedTableQueryByB = "SELECT * FROM (";
    relatedTableQuery = "LINK_SHOP_CODE, RANKING_SCORE FROM RELATED_COMMODITY_B";
    // 20120217 shen update start
    // relatedWhereQuery = " WHERE  SHOP_CODE = ? AND COMMODITY_CODE = ? "
    //   + " ) G ON A.SHOP_CODE = G.LINK_SHOP_CODE AND ";
    if (StringUtil.hasValueAnyOf(condition.getSearchCartCommodityCode())) {
      String[] cartCommodityCode = ArrayUtil.immutableCopy(condition.getSearchCartCommodityCode());
      SqlFragment fragment = SqlDialect.getDefault().createInClause("COMMODITY_CODE", (Object[]) cartCommodityCode);
      relatedWhereQuery = " WHERE  SHOP_CODE = ? AND "
        + fragment.getFragment()
        + " ) G ON A.SHOP_CODE = G.LINK_SHOP_CODE AND ";
    } else {
      relatedWhereQuery = " WHERE  SHOP_CODE = ? AND COMMODITY_CODE = ? "
        + " ) G ON A.SHOP_CODE = G.LINK_SHOP_CODE AND ";
    }
    // 20120217 shen update end
    
    sortQuery = " ) AS BND ORDER BY CASE WHEN BND.ACT_STOCK > 0 OR BND.STOCK_MANAGEMENT_TYPE = 1 THEN 1 ELSE 0 END DESC, BND.discountMode DESC,BND.commodity_popular_rank DESC, BND.COMMODITY_CODE";
    return createRelatedCommodityList(condition);
  }
  

  public CommodityContainerQuery createSalesChartsCommodityList(CommodityContainerCondition condition) {


    StringBuilder builder = new StringBuilder();
    builder.append(" SELECT * FROM ( "); 
    builder.append(" SELECT A.COMMODITY_NAME, "); 
    builder.append(" A.COMMODITY_NAME_EN, ");
    builder.append(" A.COMMODITY_NAME_JP, ");
    builder.append(" A.COMMODITY_CODE, ");
    builder.append(" A.SHOP_CODE,  ");
    builder.append(" A.UNIT_PRICE, ");
    builder.append(" A.DISCOUNT_PRICE, ");
    builder.append(" A.RESERVATION_PRICE,  ");
    builder.append(" A.COMMODITY_TAX_TYPE, ");
    builder.append(" A.STOCK_MANAGEMENT_TYPE,  ");
    builder.append(" A.ACT_STOCK, ");
    builder.append(" A.DISCOUNTMODE, ");
    //add by yyq start 20130313
    builder.append(" A.IMPORT_COMMODITY_TYPE, ");
    builder.append(" A.CLEAR_COMMODITY_TYPE, ");
    builder.append(" A.RESERVE_COMMODITY_TYPE1, ");
    builder.append(" A.RESERVE_COMMODITY_TYPE2, ");
    builder.append(" A.RESERVE_COMMODITY_TYPE3, ");
    builder.append(" A.NEW_RESERVE_COMMODITY_TYPE1, ");
    builder.append(" A.NEW_RESERVE_COMMODITY_TYPE2, ");
    builder.append(" A.NEW_RESERVE_COMMODITY_TYPE3, ");
    builder.append(" A.NEW_RESERVE_COMMODITY_TYPE4, ");
    builder.append(" A.NEW_RESERVE_COMMODITY_TYPE5, ");
    builder.append(" A.INNER_QUANTITY, ");
    builder.append(" A.DISCOUNT_PRICE_START_DATETIME, ");
    builder.append(" A.DISCOUNT_PRICE_END_DATETIME, ");
    builder.append(" A.ORIGINAL_COMMODITY_CODE, ");
    builder.append(" A.COMBINATION_AMOUNT, ");
    //add by yyq end 20130313
    builder.append(" A.COMMODITY_POPULAR_RANK ");
    builder.append(" FROM COMMODITY_LIST_VIEW A ");
    builder.append(" WHERE (A.SALE_START_DATETIME <= NOW() AND NOW() <= A.SALE_END_DATETIME) AND ");
    builder.append(" A.CATEGORY_PATH IS NOT NULL  AND A.SALE_FLG = 1  ");
    builder.append(" AND ((SELECT STOCK_QUANTITY - ALLOCATED_QUANTITY AS ACT_STOCK FROM STOCK WHERE  SKU_CODE = ( CASE WHEN A.ORIGINAL_COMMODITY_CODE IS NULL THEN A.COMMODITY_CODE ELSE A.ORIGINAL_COMMODITY_CODE END)) > 0 OR A.STOCK_MANAGEMENT_TYPE = 1)  " );
    builder.append(" AND ((SELECT STOCK_QUANTITY - ALLOCATED_QUANTITY AS ACT_STOCK FROM STOCK WHERE  SKU_CODE = ( CASE WHEN A.ORIGINAL_COMMODITY_CODE IS NULL THEN A.COMMODITY_CODE ELSE A.ORIGINAL_COMMODITY_CODE END)) >=  CASE WHEN A.COMBINATION_AMOUNT IS NULL THEN 0 ELSE A.COMBINATION_AMOUNT END OR A.STOCK_MANAGEMENT_TYPE = 1)" );
    //add by yyq start 20130321 详细页右侧只显示进口品
    builder.append(" AND  A.RESERVE_COMMODITY_TYPE1 <> 1 AND (A.RESERVE_COMMODITY_TYPE2 = 1 OR A.IMPORT_COMMODITY_TYPE =1) AND");
    //add by yyq end 20130321 详细页右侧只显示进口品
    builder.append(" A.REPRESENT_SKU_CODE = A.SKU_CODE AND A.COMMODITY_POPULAR_RANK < 50 LIMIT 10 ) AS BND   ");
    builder.append(" ORDER BY CASE WHEN BND.ACT_STOCK > 0 OR BND.STOCK_MANAGEMENT_TYPE = 1 THEN 1 ELSE 0 END DESC,BND.COMMODITY_POPULAR_RANK   ");
    
    setSqlString(builder.toString());
    setPageNumber(condition.getCurrentPage());
    setMaxFetchSize(condition.getMaxFetchSize());
    setPageSize(getMaxFetchSize());

    return this;
  }

  public Class<CommodityHeadline> getRowType() {
    return CommodityHeadline.class;
  }
}
