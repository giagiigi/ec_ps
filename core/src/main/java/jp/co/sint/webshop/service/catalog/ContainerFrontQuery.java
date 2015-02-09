package jp.co.sint.webshop.service.catalog;

import java.util.List;

import jp.co.sint.webshop.data.AbstractQuery; 
import jp.co.sint.webshop.service.CommodityHeadline;
import jp.co.sint.webshop.utility.SqlDialect;
import jp.co.sint.webshop.utility.SqlFragment;
import jp.co.sint.webshop.utility.StringUtil;

public class ContainerFrontQuery extends AbstractQuery<CommodityHeadline> {

  private static final long serialVersionUID = 1L;
  private static final String SYSDATE = SqlDialect.getDefault().getCurrentDatetime();
  
  public Class<CommodityHeadline> getRowType() {
    return CommodityHeadline.class;
  }

//  public ContainerFrontQuery(List<CommodityKey> keyList, String alignmentSequence, boolean planDetailFlag) {
//  新增加参数planDetailFlag。当为true时，SQL语句增加特价期间条件查询
//  add by lc 2012-02-21 
    public ContainerFrontQuery(List<CommodityKey> keyList, String alignmentSequence, boolean planDetailFlag,CommodityContainerCondition condition) {
    StringBuilder b = new StringBuilder();
    b.append("SELECT CH.SHOP_CODE ");
    b.append("      ,CH.COMMODITY_CODE ");
    b.append("      ,CH.SKU_CODE ");
    b.append("      ,CH.REPRESENT_SKU_CODE ");
    b.append("      ,CH.COMMODITY_STANDARD1_NAME ");
    b.append("      ,CH.COMMODITY_STANDARD2_NAME ");
    b.append("      ,CH.COMMODITY_STANDARD1_NAME_EN ");
    b.append("      ,CH.COMMODITY_STANDARD2_NAME_EN ");
    b.append("      ,CH.COMMODITY_STANDARD1_NAME_JP ");
    b.append("      ,CH.COMMODITY_STANDARD2_NAME_JP ");
    b.append("      ,CH.STANDARD_DETAIL1_NAME ");
    b.append("      ,CH.STANDARD_DETAIL2_NAME ");
    b.append("      ,CH.STANDARD_DETAIL1_NAME_EN ");
    b.append("      ,CH.STANDARD_DETAIL2_NAME_EN ");
    b.append("      ,CH.STANDARD_DETAIL1_NAME_JP ");
    b.append("      ,CH.STANDARD_DETAIL2_NAME_JP ");
    b.append("      ,CH.COMMODITY_NAME ");
    // 20141014 hdh add start
    b.append("      ,CH.COMMODITY_NAME AS  COMMODITY_NAME_CN ");
    // 20141014 hdh add start
    b.append("      ,CH.COMMODITY_NAME_EN ");
    b.append("      ,CH.COMMODITY_NAME_JP ");
    
    //b.append("      ,CH.UNIT_PRICE ");
    //zzy 2015-1-7 add start  商品金额
    b.append("      ,(CASE WHEN (SELECT SET_COMMODITY_FLG FROM C_COMMODITY_HEADER WHERE COMMODITY_CODE = CH.COMMODITY_CODE)=1 THEN (SELECT SUM(SCC.RETAIL_PRICE)AS UNIT_PRICES FROM SET_COMMODITY_COMPOSITION SCC,STOCK S WHERE SCC.CHILD_COMMODITY_CODE=S.COMMODITY_CODE AND SCC.COMMODITY_CODE=CH.COMMODITY_CODE GROUP BY SCC.COMMODITY_CODE)  ELSE CH.UNIT_PRICE END) AS UNIT_PRICE");
    //zzy 2015-1-7 add end
    
    b.append("      ,CH.DISCOUNT_PRICE ");
    b.append("      ,CH.RESERVATION_PRICE ");
    b.append("      ,CH.SALE_START_DATETIME ");
    b.append("      ,CH.SALE_END_DATETIME ");
    b.append("      ,CH.DISCOUNT_PRICE_START_DATETIME ");
    b.append("      ,CH.DISCOUNT_PRICE_END_DATETIME ");
    b.append("      ,CH.RESERVATION_START_DATETIME ");
    b.append("      ,CH.RESERVATION_END_DATETIME ");
    b.append("      ,CH.COMMODITY_TAX_TYPE ");
    b.append("      ,CH.COMMODITY_DESCRIPTION_PC ");
    b.append("      ,CH.COMMODITY_DESCRIPTION_MOBILE ");
    b.append("      ,CH.COMMODITY_DESCRIPTION_PC_EN ");
    b.append("      ,CH.COMMODITY_DESCRIPTION_MOBILE_EN ");
    b.append("      ,CH.COMMODITY_DESCRIPTION_PC_JP ");
    b.append("      ,CH.COMMODITY_DESCRIPTION_MOBILE_JP ");
    b.append("      ,CH.STOCK_MANAGEMENT_TYPE ");
    b.append("      ,CH.ARRIVAL_GOODS_FLG ");
    b.append("      ,CH.DELIVERY_TYPE_NO ");
    b.append("      ,CH.LINK_URL ");
    b.append("      ,CH.RECOMMEND_COMMODITY_RANK ");
    b.append("      ,CH.JAN_CODE ");
    b.append("      ,CH.COMMODITY_POINT_RATE ");
    b.append("      ,CH.COMMODITY_POINT_START_DATETIME ");
    b.append("      ,CH.COMMODITY_POINT_END_DATETIME ");
    // add by yyq start 20130313 
    b.append("      ,CH.IMPORT_COMMODITY_TYPE ");
    b.append("      ,CH.CLEAR_COMMODITY_TYPE ");
    b.append("      ,CH.RESERVE_COMMODITY_TYPE1 ");
    b.append("      ,CH.RESERVE_COMMODITY_TYPE2 ");
    b.append("      ,CH.RESERVE_COMMODITY_TYPE3 ");
    b.append("      ,CH.NEW_RESERVE_COMMODITY_TYPE1 ");
    b.append("      ,CH.NEW_RESERVE_COMMODITY_TYPE2 ");
    b.append("      ,CH.NEW_RESERVE_COMMODITY_TYPE3 ");
    b.append("      ,CH.NEW_RESERVE_COMMODITY_TYPE4 ");
    b.append("      ,CH.NEW_RESERVE_COMMODITY_TYPE5 ");
    b.append("      ,CH.INNER_QUANTITY ");
    b.append("      ,CH.SALE_FLG ");
    // add by yyq end 20130313 
    b.append("      ,CH.SALE_FLG ");
    
    //b.append("      ,CH.RETAIL_PRICE ");
    //zzy 2015-1-7 add start
    b.append("      ,(CASE WHEN (SELECT SET_COMMODITY_FLG FROM C_COMMODITY_HEADER WHERE COMMODITY_CODE = CH.COMMODITY_CODE)=1 THEN (SELECT SUM(SCC.RETAIL_PRICE)AS UNIT_PRICES FROM SET_COMMODITY_COMPOSITION SCC,STOCK S WHERE SCC.CHILD_COMMODITY_CODE=S.COMMODITY_CODE AND SCC.COMMODITY_CODE=CH.COMMODITY_CODE GROUP BY SCC.COMMODITY_CODE)  ELSE CH.UNIT_PRICE END) AS RETAIL_PRICE ");
    //zzy 2015-1-7 add end
    b.append("      ,CH.BRAND_CODE ");
    b.append("      ,CH.CATEGORY_PATH ");
    b.append("      ,CH.CATEGORY_ATTRIBUTE1 ");
    b.append("      ,CH.ORIGINAL_COMMODITY_CODE ");
    b.append("      ,CH.COMBINATION_AMOUNT ");
    b.append("      ,CCD.USE_FLG ");
    b.append("      ,AVAILABLE_STOCK_QUANTITY_FUNC(CH.SHOP_CODE, CH.COMMODITY_CODE) AS AVAILABLE_STOCK_QUANTITY ");
    b.append("      ,D.SHOP_NAME ");
    b.append("      ,E.STOCK_STATUS_NO ");
    b.append("      ,E.STOCK_SUFFICIENT_MESSAGE ");
    b.append("      ,E.STOCK_LITTLE_MESSAGE ");
    b.append("      ,E.OUT_OF_STOCK_MESSAGE ");
    b.append("      ,E.STOCK_SUFFICIENT_THRESHOLD ");
    b.append("      ,F.REVIEW_SCORE ");
    b.append("      ,F.REVIEW_COUNT ");
    b.append("      ,REVIEW_DISPLAY_STATUS(CH.SHOP_CODE) AS DISPLAY_FLG,");
    
    // 20141023 hdh add start
    b.append(" (SELECT COALESCE(");
    b.append("   (SELECT CAMPAIGN_NAME||'@'||CAMPAIGN_NAME_EN||'@'||CAMPAIGN_NAME_JP FROM OPTIONAL_CAMPAIGN ");
    b.append("     WHERE NOW() BETWEEN CAMPAIGN_START_DATE AND CAMPAIGN_END_DATE AND CAMPAIGN_STATUS = 1  AND  RELATED_COMMODITY LIKE '%,'||CH.COMMODITY_CODE||',%' )");
    b.append("    ,(SELECT CAMPAIGN_NAME||'@'||CAMPAIGN_NAME_EN||'@'||CAMPAIGN_NAME_JP FROM CAMPAIGN_MAIN C ");
    b.append("   INNER JOIN CAMPAIGN_CONDITION CC ON CC.CAMPAIGN_CODE=C.CAMPAIGN_CODE");
    b.append("    WHERE  CASE WHEN C.CAMPAIGN_TYPE= 1 THEN CAMPAIGN_CONDITION_TYPE='1' WHEN  C.CAMPAIGN_TYPE= 3 THEN 1=1  END AND    NOW()  ");
    b.append("    BETWEEN CAMPAIGN_START_DATE AND CAMPAIGN_END_DATE AND ");
    b.append("    (ATTRIBUTR_VALUE = CH.COMMODITY_CODE OR ATTRIBUTR_VALUE LIKE CH.COMMODITY_CODE||',%' OR ATTRIBUTR_VALUE LIKE '%,'||CH.COMMODITY_CODE OR ");
    b.append("    ATTRIBUTR_VALUE LIKE '%,'||CH.COMMODITY_CODE||',%')");
    b.append("     ORDER BY C.CAMPAIGN_TYPE ASC LIMIT 1)");
    b.append("  ) AS NAME  FROM DUAL) AS CAMPAIGN_NAME");
    // 20141023 hdh add end
    
    b.append("  FROM COMMODITY_LIST_VIEW CH ");
    b.append(" INNER JOIN SHOP D ON D.SHOP_CODE = CH.SHOP_CODE ");
    
    if (StringUtil.hasValue(condition.getSearchSpecFlag())) {
      b.append(" LEFT JOIN PLAN_COMMODITY PC ON PC.COMMODITY_CODE = CH.REPRESENT_SKU_CODE "); 
    }
    b.append(" LEFT JOIN C_COMMODITY_DETAIL CCD ON CCD.COMMODITY_CODE = CH.COMMODITY_CODE "); 
    b.append(" LEFT OUTER JOIN STOCK_STATUS E ON CH.SHOP_CODE = E.SHOP_CODE ");
    b.append(" AND CH.STOCK_STATUS_NO = E.STOCK_STATUS_NO  ");
    b.append(" LEFT OUTER JOIN REVIEW_SUMMARY F ON CH.SHOP_CODE = F.SHOP_CODE ");
    b.append(" AND CH.COMMODITY_CODE = F.COMMODITY_CODE ");
    if (StringUtil.hasValue(condition.getSearchSpecFlag())) {
      b.append("   WHERE CH.REPRESENT_SKU_CODE = CH.SKU_CODE AND ((SELECT STOCK_QUANTITY - ALLOCATED_QUANTITY AS ACT_STOCK FROM STOCK WHERE  ( CASE WHEN CH.ORIGINAL_COMMODITY_CODE IS NULL THEN ch.commodity_code ELSE CH.ORIGINAL_COMMODITY_CODE END) = SKU_CODE) >=  CASE WHEN CH.COMBINATION_AMOUNT IS NULL THEN 1 ELSE CH.COMBINATION_AMOUNT END OR CH.STOCK_MANAGEMENT_TYPE = 1 OR (select set_commodity_flg from c_commodity_header where commodity_code = ch.commodity_code)=1) ");
    } else {
      b.append("   WHERE CH.REPRESENT_SKU_CODE = CH.SKU_CODE ");
    }
    
    
    //add by lc 2012-02-20 start
    if (planDetailFlag) {
      b.append("    AND (CH.DISCOUNT_PRICE_START_DATETIME <= ");
      b.append(SYSDATE + " AND ");
      b.append(SYSDATE+ " <= CH.DISCOUNT_PRICE_END_DATETIME) ");
    }
    //add by lc 2012-02-20 end

    if (keyList != null && keyList.size() > 0) {
      SqlFragment fragment = CommodityKey.buildInClause("CH.SHOP_CODE", "CH.COMMODITY_CODE", keyList);
      b.append("AND ");
      b.append(fragment.getFragment());
      this.setParameters(fragment.getParameters());
    }
    if (StringUtil.hasValue(condition.getSearchSpecFlag())) {
      b.append(" AND PC.PLAN_CODE = '"+condition.getSearchPlanDetail().getPlanCode()+"' AND DETAIL_TYPE="+condition.getSearchPlanDetail().getDetailType()+" AND DETAIL_CODE= '"+condition.getSearchPlanDetail().getDetailCode()+"'  ");
     
      // 20140902 hdh add start
      if(StringUtil.hasValue(condition.getForHotSale())){
        b.append(" ORDER BY CH.COMMODITY_POPULAR_RANK ASC ");
        this.setSqlString(b.toString());
      }
      // 20140902 hdh add end
      
      b.append(" ORDER BY PC.DISPLAY_ORDER ASC,");
      b.append("CASE WHEN EXISTS (SELECT DC.* FROM DISCOUNT_COMMODITY DC INNER JOIN DISCOUNT_HEADER DH ON DC.DISCOUNT_CODE = DH.DISCOUNT_CODE WHERE DC.COMMODITY_CODE = CH.COMMODITY_CODE AND DC.USE_FLG = 1 AND DH.DISCOUNT_START_DATETIME <= NOW() AND DH.DISCOUNT_END_DATETIME >= NOW()) THEN 1 ELSE 2 END ASC , ");
      b.append("CASE WHEN CH.NEW_RESERVE_COMMODITY_TYPE3 = 9 THEN 9 ELSE CH.NEW_RESERVE_COMMODITY_TYPE3 END ASC , ");
      b.append("CASE WHEN CH.RESERVE_COMMODITY_TYPE2 = 9 THEN 9 ELSE CH.RESERVE_COMMODITY_TYPE2 END ASC , ");
      b.append("CASE WHEN CH.NEW_RESERVE_COMMODITY_TYPE1 = 9 THEN 9 ELSE CH.NEW_RESERVE_COMMODITY_TYPE1 END ASC , ");
      b.append("CASE WHEN CH.IMPORT_COMMODITY_TYPE = 9 THEN 9 ELSE CH.IMPORT_COMMODITY_TYPE END ASC , ");
      b.append("CASE WHEN CH.CLEAR_COMMODITY_TYPE = 9 THEN 9 ELSE CH.CLEAR_COMMODITY_TYPE END DESC  ");
    }else{
      // 20140902 hdh add start
      if(StringUtil.hasValue(condition.getForHotSale())){
        b.append(" ORDER BY CH.COMMODITY_POPULAR_RANK ASC ");
      }
      // 20140902 hdh add end
    }

    // 並び順設定
//    CommodityDisplayOrder cdo = CommodityDisplayOrder.fromValue(alignmentSequence);
//    if (cdo == null) {
//      cdo = CommodityDisplayOrder.BY_POPULAR_RANKING;
//    }
//
//    b.append("ORDER BY ");
//    switch (cdo) {
//      case BY_POPULAR_RANKING:
//        b.append("CASE WHEN CH.ACT_STOCK > 0 OR CH.STOCK_MANAGEMENT_TYPE = 1 THEN 1 ELSE 0 END DESC, CH.DISCOUNTMODE DESC,CH.COMMODITY_POPULAR_RANK ASC");
//        break;
//      case BY_PRICE_ASCENDING:
//        b.append("CASE WHEN CH.ACT_STOCK > 0 OR CH.STOCK_MANAGEMENT_TYPE = 1 THEN 1 ELSE 0 END DESC,CH.COMMODITY_POPULAR_RANK ASC, CH.DISCOUNTMODE DESC");
//        break;
//      case BY_PRICE_DESCENDING:
//        b.append("CASE WHEN CH.ACT_STOCK > 0 OR CH.STOCK_MANAGEMENT_TYPE = 1 THEN 1 ELSE 0 END DESC, CH.RETAIL_PRICE ASC, CH.DISCOUNTMODE DESC,CH.COMMODITY_POPULAR_RANK ASC");
//        break;
//      case BY_COMMODITY_NAME:
//        b.append("CASE WHEN CH.ACT_STOCK > 0 OR CH.STOCK_MANAGEMENT_TYPE = 1 THEN 1 ELSE 0 END DESC, CASE WHEN F.REVIEW_COUNT IS NULL THEN 0 ELSE F.REVIEW_COUNT END DESC, CH.DISCOUNTMODE DESC,CH.COMMODITY_POPULAR_RANK ASC");
//        break;
//      case BY_RECOMMEND_SCORE:
//        b.append("CASE WHEN CH.ACT_STOCK > 0 OR CH.STOCK_MANAGEMENT_TYPE = 1 THEN 1 ELSE 0 END DESC, CASE WHEN F.REVIEW_SCORE IS NULL THEN 0 ELSE F.REVIEW_SCORE END DESC, CH.DISCOUNTMODE DESC,CH.COMMODITY_POPULAR_RANK ASC");
//        break;
//      default:
//        break;
//    }
    this.setSqlString(b.toString());
  }

}
