package jp.co.sint.webshop.service.catalog;

import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.domain.SaleFlg;
import jp.co.sint.webshop.data.dto.Plan;
import jp.co.sint.webshop.utility.SqlDialect;


public final class PlanQuery {
  
  private static final String SYSDATE = SqlDialect.getDefault().getCurrentDatetime();
  
  private PlanQuery() {
  }

  public static final String GET_PLAN_QUERY = DatabaseUtil.getSelectAllQuery(Plan.class) 
    + " WHERE PLAN_TYPE = ? AND PLAN_DETAIL_TYPE = ? AND " + SqlDialect.getDefault().getCurrentDatetime() 
    + " BETWEEN PLAN_START_DATETIME AND PLAN_END_DATETIME ";
  
  public static final String GET_PLAN_INFO_LIST_QUERY_B = ""
    + " SELECT PD.PLAN_CODE,"
    + " PD.DETAIL_TYPE,"
    + " PD.DETAIL_CODE,"
    + " PD.DETAIL_NAME,"
    + " PD.DETAIL_URL,"
    + " PD.SHOW_COMMODITY_COUNT,"
    + " PD.DETAIL_NAME_EN,"
    + " PD.DETAIL_URL_EN,"
    + " PD.DETAIL_NAME_JP,"
    + " PD.DETAIL_URL_JP,"
    + " PC.COMMODITY_CODE,"
    + " CH.SHOP_CODE,"
    + " CH.COMMODITY_CODE,"
    + " CH.COMMODITY_NAME,"
    + " CH.COMMODITY_NAME_EN,"
    + " CH.COMMODITY_NAME_JP,"
    + " CD.UNIT_PRICE,"
    + " CD.DISCOUNT_PRICE,"
    + " CD.RESERVATION_PRICE,"
    + " CH.SALE_START_DATETIME,"
    + " CH.SALE_END_DATETIME,"
    // add by yyq start 20130414
    + " CH.IMPORT_COMMODITY_TYPE,"
    + " CH.CLEAR_COMMODITY_TYPE,"
    + " CH.RESERVE_COMMODITY_TYPE1,"
    + " CH.RESERVE_COMMODITY_TYPE2,"
    + " CH.RESERVE_COMMODITY_TYPE3,"
    + " CH.NEW_RESERVE_COMMODITY_TYPE1,"
    + " CH.NEW_RESERVE_COMMODITY_TYPE2,"
    + " CH.NEW_RESERVE_COMMODITY_TYPE3,"
    + " CH.NEW_RESERVE_COMMODITY_TYPE4,"
    + " CH.NEW_RESERVE_COMMODITY_TYPE5,"
    + " CD.INNER_QUANTITY,"
    + " CH.ORIGINAL_COMMODITY_CODE,CH.COMBINATION_AMOUNT,"
    // add by yyq end 20130414
    + " CASE"
    + " WHEN CH.DISCOUNT_PRICE_START_DATETIME IS NULL AND CH.DISCOUNT_PRICE_END_DATETIME IS NOT NULL AND CH.SALE_START_DATETIME IS NULL THEN TO_TIMESTAMP('1970/01/01 00:00:00'::TEXT, 'YYYY/MM/DD HH24:MI:SS'::TEXT)"
    + " WHEN CH.DISCOUNT_PRICE_START_DATETIME IS NULL AND CH.DISCOUNT_PRICE_END_DATETIME IS NOT NULL AND CH.SALE_START_DATETIME IS NOT NULL THEN CH.SALE_START_DATETIME::TIMESTAMP WITH TIME ZONE"
    + " ELSE CH.DISCOUNT_PRICE_START_DATETIME::TIMESTAMP WITH TIME ZONE"
    + " END DISCOUNT_PRICE_START_DATETIME,"
    + " CASE"
    + " WHEN CH.DISCOUNT_PRICE_START_DATETIME IS NOT NULL AND CH.DISCOUNT_PRICE_END_DATETIME IS NULL AND CH.SALE_END_DATETIME IS NULL THEN TO_TIMESTAMP('2100/12/31 23:59:59'::TEXT, 'YYYY/MM/DD HH24:MI:SS'::TEXT)"
    + " WHEN CH.DISCOUNT_PRICE_START_DATETIME IS NOT NULL AND CH.DISCOUNT_PRICE_END_DATETIME IS NULL AND CH.SALE_END_DATETIME IS NOT NULL THEN CH.SALE_END_DATETIME::TIMESTAMP WITH TIME ZONE"
    + " ELSE CH.DISCOUNT_PRICE_END_DATETIME::TIMESTAMP WITH TIME ZONE"
    + " END DISCOUNT_PRICE_END_DATETIME,"
    + " CH.RESERVATION_START_DATETIME,"
    + " CH.RESERVATION_END_DATETIME,"
    + " CH.COMMODITY_TAX_TYPE,"
    + " CH.COMMODITY_DESCRIPTION_PC,"
    + " CH.COMMODITY_DESCRIPTION_MOBILE,"
    + " CH.COMMODITY_DESCRIPTION_MOBILE_EN,"
    + " CH.COMMODITY_DESCRIPTION_MOBILE_JP,"
    + " CH.STOCK_MANAGEMENT_TYPE,"
    + " CH.SALE_FLG,"
    + " CASE"
    + " WHEN CH.RESERVATION_START_DATETIME IS NOT NULL AND CH.RESERVATION_END_DATETIME IS NOT NULL AND TO_CHAR(NOW(), 'YYYY/MM/DD HH24:MI:SS'::TEXT) >= TO_CHAR(CH.RESERVATION_START_DATETIME, 'YYYY/MM/DD HH24:MI:SS'::TEXT) AND TO_CHAR(NOW(), 'YYYY/MM/DD HH24:MI:SS'::TEXT) <= TO_CHAR(CH.RESERVATION_END_DATETIME, 'YYYY/MM/DD HH24:MI:SS'::TEXT) THEN "
    + " CASE"
    + " WHEN CH.COMMODITY_TAX_TYPE = 1::NUMERIC THEN CD.RESERVATION_PRICE * (D.TAX_RATE + 100::NUMERIC) / 100::NUMERIC"
    + " ELSE CD.RESERVATION_PRICE"
    + " END"
    + " WHEN CH.RESERVATION_START_DATETIME IS NULL AND CH.RESERVATION_END_DATETIME IS NOT NULL AND TO_CHAR(NOW(), 'YYYY/MM/DD HH24:MI:SS'::TEXT) <= TO_CHAR(CH.RESERVATION_END_DATETIME, 'YYYY/MM/DD HH24:MI:SS'::TEXT) THEN "
    + " CASE"
    + " WHEN CH.COMMODITY_TAX_TYPE = 1::NUMERIC THEN CD.RESERVATION_PRICE * (D.TAX_RATE + 100::NUMERIC) / 100::NUMERIC"
    + " ELSE CD.RESERVATION_PRICE"
    + " END"
    + " WHEN CH.DISCOUNT_PRICE_START_DATETIME IS NOT NULL AND CH.DISCOUNT_PRICE_END_DATETIME IS NOT NULL AND TO_CHAR(NOW(), 'YYYY/MM/DD HH24:MI:SS'::TEXT) >= TO_CHAR(CH.DISCOUNT_PRICE_START_DATETIME, 'YYYY/MM/DD HH24:MI:SS'::TEXT) AND TO_CHAR(NOW(), 'YYYY/MM/DD HH24:MI:SS'::TEXT) <= TO_CHAR(CH.DISCOUNT_PRICE_END_DATETIME, 'YYYY/MM/DD HH24:MI:SS'::TEXT) THEN" 
    + " CASE"
    + " WHEN CH.COMMODITY_TAX_TYPE = 1::NUMERIC THEN CD.DISCOUNT_PRICE * (D.TAX_RATE + 100::NUMERIC) / 100::NUMERIC"
    + " ELSE CD.DISCOUNT_PRICE"
    + " END"
    + " WHEN CH.DISCOUNT_PRICE_START_DATETIME IS NOT NULL AND CH.DISCOUNT_PRICE_END_DATETIME IS NULL AND TO_CHAR(NOW(), 'YYYY/MM/DD HH24:MI:SS'::TEXT) >= TO_CHAR(CH.DISCOUNT_PRICE_START_DATETIME, 'YYYY/MM/DD HH24:MI:SS'::TEXT) AND TO_CHAR(NOW(), 'YYYY/MM/DD HH24:MI:SS'::TEXT) <= TO_CHAR(CH.SALE_END_DATETIME, 'YYYY/MM/DD HH24:MI:SS'::TEXT) THEN "
    + " CASE"
    + " WHEN CH.COMMODITY_TAX_TYPE = 1::NUMERIC THEN CD.DISCOUNT_PRICE * (D.TAX_RATE + 100::NUMERIC) / 100::NUMERIC"
    + " ELSE CD.DISCOUNT_PRICE"
    + " END"
    + " WHEN CH.DISCOUNT_PRICE_START_DATETIME IS NULL AND CH.DISCOUNT_PRICE_END_DATETIME IS NOT NULL AND TO_CHAR(NOW(), 'YYYY/MM/DD HH24:MI:SS'::TEXT) >= TO_CHAR(CH.SALE_START_DATETIME, 'YYYY/MM/DD HH24:MI:SS'::TEXT) AND TO_CHAR(NOW(), 'YYYY/MM/DD HH24:MI:SS'::TEXT) <= TO_CHAR(CH.DISCOUNT_PRICE_END_DATETIME, 'YYYY/MM/DD HH24:MI:SS'::TEXT) THEN "
    + " CASE"
    + " WHEN CH.COMMODITY_TAX_TYPE = 1::NUMERIC THEN CD.DISCOUNT_PRICE * (D.TAX_RATE + 100::NUMERIC) / 100::NUMERIC"
    + " ELSE CD.DISCOUNT_PRICE"
    + " END"
    + " ELSE "
    + " CASE"
    + " WHEN CH.COMMODITY_TAX_TYPE = 1::NUMERIC THEN CD.UNIT_PRICE * (D.TAX_RATE + 100::NUMERIC) / 100::NUMERIC"
    + " ELSE CD.UNIT_PRICE"
    + " END"
    + " END AS RETAIL_PRICE,"
    + " F.REVIEW_SCORE,"
    + " F.REVIEW_COUNT"
    + " FROM PLAN_DETAIL PD"
    + " LEFT OUTER JOIN PLAN PL ON PL.PLAN_CODE = PD.PLAN_CODE"
    + " LEFT OUTER JOIN PLAN_COMMODITY PC ON PD.PLAN_CODE = PC.PLAN_CODE AND PD.DETAIL_CODE = PC.DETAIL_CODE AND PD.DETAIL_TYPE = PC.DETAIL_TYPE"
    + " LEFT OUTER JOIN COMMODITY_HEADER CH ON PC.COMMODITY_CODE = CH.COMMODITY_CODE"
    + " LEFT OUTER JOIN COMMODITY_DETAIL CD ON CH.COMMODITY_CODE = CD.COMMODITY_CODE"
    + " LEFT OUTER JOIN STOCK ST ON CD.SKU_CODE= ST.SKU_CODE"
    + " LEFT OUTER JOIN REVIEW_SUMMARY F ON CH.SHOP_CODE = F.SHOP_CODE AND CH.COMMODITY_CODE = F.COMMODITY_CODE , "
    + " ( SELECT TAX.TAX_RATE FROM TAX"
    + " WHERE TAX.APPLIED_START_DATE = (( SELECT MAX(TAX.APPLIED_START_DATE) AS MAX FROM TAX WHERE TAX.APPLIED_START_DATE <= NOW()))) D"
    + " WHERE PD.PLAN_CODE = ?" 
    + " AND CH.SALE_FLAG = PL.PLAN_DETAIL_TYPE"
    + " AND ((CH.SALE_START_DATETIME <= NOW() AND NOW() <= CH.SALE_END_DATETIME) OR"
    + " (CH.RESERVATION_START_DATETIME <= NOW() AND NOW() <= CH.RESERVATION_END_DATETIME))"
    + " AND (CASE"
    + " WHEN CH.DISCOUNT_PRICE_START_DATETIME IS NULL AND CH.DISCOUNT_PRICE_END_DATETIME IS NOT NULL AND CH.SALE_START_DATETIME IS NULL THEN TO_TIMESTAMP('1970/01/01 00:00:00'::TEXT, 'YYYY/MM/DD HH24:MI:SS'::TEXT)"
    + " WHEN CH.DISCOUNT_PRICE_START_DATETIME IS NULL AND CH.DISCOUNT_PRICE_END_DATETIME IS NOT NULL AND CH.SALE_START_DATETIME IS NOT NULL THEN CH.SALE_START_DATETIME::TIMESTAMP WITH TIME ZONE"
    + " ELSE CH.DISCOUNT_PRICE_START_DATETIME::TIMESTAMP WITH TIME ZONE"
    + " END <= NOW() AND NOW() <= CASE"
    + " WHEN CH.DISCOUNT_PRICE_START_DATETIME IS NOT NULL AND CH.DISCOUNT_PRICE_END_DATETIME IS NULL AND CH.SALE_END_DATETIME IS NULL THEN TO_TIMESTAMP('2100/12/31 23:59:59'::TEXT, 'YYYY/MM/DD HH24:MI:SS'::TEXT)"
    + " WHEN CH.DISCOUNT_PRICE_START_DATETIME IS NOT NULL AND CH.DISCOUNT_PRICE_END_DATETIME IS NULL AND CH.SALE_END_DATETIME IS NOT NULL THEN CH.SALE_END_DATETIME::TIMESTAMP WITH TIME ZONE"
    + " ELSE CH.DISCOUNT_PRICE_END_DATETIME::TIMESTAMP WITH TIME ZONE"
    + " END)"
    + " AND ((SELECT STOCK_QUANTITY - ALLOCATED_QUANTITY AS ACT_STOCK FROM STOCK WHERE  SKU_CODE = ( CASE WHEN CH.ORIGINAL_COMMODITY_CODE IS NULL THEN CD.SKU_CODE ELSE CH.ORIGINAL_COMMODITY_CODE END)) > 0 OR CH.STOCK_MANAGEMENT_TYPE = 1)  "
    + " AND ((SELECT STOCK_QUANTITY - ALLOCATED_QUANTITY AS ACT_STOCK FROM STOCK WHERE  SKU_CODE = ( CASE WHEN CH.ORIGINAL_COMMODITY_CODE IS NULL THEN CD.SKU_CODE ELSE CH.ORIGINAL_COMMODITY_CODE END)) >=  CASE WHEN CH.COMBINATION_AMOUNT IS NULL THEN 0 ELSE CH.COMBINATION_AMOUNT END OR CH.STOCK_MANAGEMENT_TYPE = 1)"
    + " AND CH.SALE_FLG = 1 AND CH.RESERVE_COMMODITY_TYPE1 <> 1"
    + " ORDER BY PD.DISPLAY_ORDER, PD.DETAIL_CODE, PC.DISPLAY_ORDER,"
    + " CASE WHEN ST.STOCK_QUANTITY - ST.ALLOCATED_QUANTITY > 0 OR CH.STOCK_MANAGEMENT_TYPE = 1 THEN 1 ELSE 0 END DESC ";

  public static final String GET_PLAN_INFO_LIST_QUERY = "SELECT PD.PLAN_CODE,"
    + " PD.DETAIL_TYPE, PD.DETAIL_CODE, PD.DETAIL_NAME, PD.DETAIL_URL, PD.SHOW_COMMODITY_COUNT,"
    + " PD.DETAIL_NAME_EN, PD.DETAIL_URL_EN,"
    + " PD.DETAIL_NAME_JP, PD.DETAIL_URL_JP,"
    + " PC.COMMODITY_CODE,"
    + " CH.SHOP_CODE,"
    + " CH.COMMODITY_CODE,"
    + " CH.COMMODITY_NAME,"
    + " CH.COMMODITY_NAME_EN,"
    + " CH.COMMODITY_NAME_JP,"
    + " CH.UNIT_PRICE,"
    + " CH.DISCOUNT_PRICE,"
    + " CH.RESERVATION_PRICE,"
    + " CH.SALE_START_DATETIME,"
    + " CH.SALE_END_DATETIME,"
    + " CH.DISCOUNT_PRICE_START_DATETIME,"
    + " CH.DISCOUNT_PRICE_END_DATETIME,"
    + " CH.RESERVATION_START_DATETIME,"
    + " CH.RESERVATION_END_DATETIME,"
    + " CH.COMMODITY_TAX_TYPE,"
    + " CH.COMMODITY_DESCRIPTION_PC,"
    + " CH.COMMODITY_DESCRIPTION_MOBILE,"
    + " CH.COMMODITY_DESCRIPTION_MOBILE_EN,"
    + " CH.COMMODITY_DESCRIPTION_MOBILE_JP,"
    + " CH.STOCK_MANAGEMENT_TYPE,"
    + " CH.SALE_FLG,"
    + " CH.RETAIL_PRICE,"
    + " F.REVIEW_SCORE,"
    + " F.REVIEW_COUNT"
    + " FROM PLAN_DETAIL PD"
    + " LEFT OUTER JOIN PLAN PL ON PL.PLAN_CODE = PD.PLAN_CODE "
    + " LEFT OUTER JOIN PLAN_COMMODITY PC ON PD.PLAN_CODE = PC.PLAN_CODE AND PD.DETAIL_CODE = PC.DETAIL_CODE AND PD.DETAIL_TYPE = PC.DETAIL_TYPE"
    + " LEFT OUTER JOIN COMMODITY_HEADER CCH ON PC.COMMODITY_CODE = CCH.COMMODITY_CODE"
    + " LEFT OUTER JOIN COMMODITY_LIST_VIEW CH ON PC.COMMODITY_CODE = CH.COMMODITY_CODE"
    + " LEFT OUTER JOIN REVIEW_SUMMARY F ON CH.SHOP_CODE = F.SHOP_CODE AND CH.COMMODITY_CODE = F.COMMODITY_CODE"
    + " WHERE PD.PLAN_CODE = ?"   
    + " AND CCH.SALE_FLAG = PL.PLAN_DETAIL_TYPE"
    + " AND ((CH.SALE_START_DATETIME <= " + SYSDATE + " AND " + SYSDATE
    + " <= CH.SALE_END_DATETIME) OR (CH.RESERVATION_START_DATETIME <= "
    + SYSDATE + " AND "
    + SYSDATE + " <= CH.RESERVATION_END_DATETIME)) "
    //add by lc 2012-02-20 start
    + " AND (CH.DISCOUNT_PRICE_START_DATETIME <= " 
    + SYSDATE + " AND " 
    + SYSDATE+ " <= CH.DISCOUNT_PRICE_END_DATETIME)"
    //add by lc 2012-02-20 end
    // update by yyq 2012-09-14 start
    + " AND CH.ACT_STOCK > 0 AND CH.SALE_FLG = " + SaleFlg.FOR_SALE.getValue()
    // update by yyq 2012-09-14 end
    + " AND CH.REPRESENT_SKU_CODE = CH.SKU_CODE"
    + " ORDER BY PD.DISPLAY_ORDER, PD.DETAIL_CODE, PC.DISPLAY_ORDER"
    + "";
  
  public static final String GET_FEATURE_INFO_LIST_QUERY = 
                    " SELECT "
                    + " PD.PLAN_CODE, "
                    + " PD.DETAIL_TYPE, "
                    + " PD.DETAIL_CODE,"
                    + " PD.DETAIL_NAME, "
                    + " PD.DETAIL_URL, "
                    + " PD.SHOW_COMMODITY_COUNT, "
                    + " PD.DETAIL_NAME_EN, "
                    + " PD.DETAIL_URL_EN, "
                    + " PD.DETAIL_NAME_JP, "
                    + " PD.DETAIL_URL_JP, "
                    + " PC.COMMODITY_CODE, "
                    + " CH.SHOP_CODE, "
                    + " CH.COMMODITY_CODE, "
                    + " CCH.COMMODITY_NAME, "
                    + " CCH.COMMODITY_NAME_JP, "
                    + " CCH.COMMODITY_NAME_EN, "
                   // + " CH.UNIT_PRICE, "
                    + " (case when cch.set_commodity_flg=1 then (SELECT SUM(SCC.RETAIL_PRICE)AS unit_prices FROM SET_COMMODITY_COMPOSITION SCC,STOCK S WHERE SCC.CHILD_COMMODITY_CODE=S.COMMODITY_CODE AND SCC.COMMODITY_CODE=CH.COMMODITY_CODE GROUP BY SCC.COMMODITY_CODE)  else ch.unit_price end), "
                    
                    + " CH.DISCOUNT_PRICE, "
                    + " CCH.SALE_START_DATETIME, "
                    + " CCH.SALE_END_DATETIME, "
                    + " CCH.DISCOUNT_PRICE_START_DATETIME, "
                    + " CCH.DISCOUNT_PRICE_END_DATETIME, "
                    + " CCH.COMMODITY_TAX_TYPE, "
                    + " CCH.COMMODITY_DESCRIPTION_PC, "
                    + " CCH.COMMODITY_DESCRIPTION_MOBILE, "
                    + " CCH.COMMODITY_DESCRIPTION_MOBILE_JP, "
                    + " CCH.COMMODITY_DESCRIPTION_MOBILE_EN, "
                    + " CCH.STOCK_MANAGEMENT_TYPE, "
                    + " CCH.IMPORT_COMMODITY_TYPE, "
                    + " CCH.CLEAR_COMMODITY_TYPE, "
                    + " CCH.RESERVE_COMMODITY_TYPE1, "
                    + " CCH.RESERVE_COMMODITY_TYPE2, "
                    + " CCH.RESERVE_COMMODITY_TYPE3, "
                    + " CCH.NEW_RESERVE_COMMODITY_TYPE1, "
                    + " CCH.NEW_RESERVE_COMMODITY_TYPE2, "
                    + " CCH.NEW_RESERVE_COMMODITY_TYPE3, "
                    + " CCH.NEW_RESERVE_COMMODITY_TYPE4, "
                    + " CCH.NEW_RESERVE_COMMODITY_TYPE5, "
                    // 20141024 hdh add staert
                    +" (SELECT COALESCE( "
                    +"   (SELECT CAMPAIGN_NAME||'@'||CAMPAIGN_NAME_EN||'@'||CAMPAIGN_NAME_JP FROM OPTIONAL_CAMPAIGN  "
                    +"     WHERE NOW() BETWEEN CAMPAIGN_START_DATE AND CAMPAIGN_END_DATE AND CAMPAIGN_STATUS = 1  AND  RELATED_COMMODITY LIKE '%,'||CH.COMMODITY_CODE||',%' )" 
                    +"    ,(SELECT CAMPAIGN_NAME||'@'||CAMPAIGN_NAME_EN||'@'||CAMPAIGN_NAME_JP FROM CAMPAIGN_MAIN C " 
                    +"   INNER JOIN CAMPAIGN_CONDITION CC ON CC.CAMPAIGN_CODE=C.CAMPAIGN_CODE" 
                    +"    WHERE  CASE WHEN C.CAMPAIGN_TYPE= 1 THEN CAMPAIGN_CONDITION_TYPE='1' WHEN  C.CAMPAIGN_TYPE= 3 THEN 1=1  END AND    NOW()  "
                    +"    BETWEEN CAMPAIGN_START_DATE AND CAMPAIGN_END_DATE AND  "
                    +"    (ATTRIBUTR_VALUE = CH.COMMODITY_CODE OR ATTRIBUTR_VALUE LIKE CH.COMMODITY_CODE||',%' OR ATTRIBUTR_VALUE LIKE '%,'||CH.COMMODITY_CODE OR  "
                    +"    ATTRIBUTR_VALUE LIKE '%,'||CH.COMMODITY_CODE||',%') "
                    +"     ORDER BY C.CAMPAIGN_TYPE ASC LIMIT 1)"
                    +"  ) AS NAME  FROM DUAL) AS CAMPAIGN_NAME, "
                    // 20141024 hdh add end
                    + " CH.INNER_QUANTITY, "
                    + " CCH.ORIGINAL_COMMODITY_CODE,CCH.COMBINATION_AMOUNT,"
                    + " CCH.SALE_FLG, "
                    
                    
                    //+ " (CASE WHEN S.STOCK_QUANTITY - S.ALLOCATED_QUANTITY   >= (CASE WHEN CCH.COMBINATION_AMOUNT IS NULL THEN 1 ELSE CCH.COMBINATION_AMOUNT END ) THEN 1 ELSE 0 END ) AS AVA_QUANTITY "
                    + " CASE WHEN cch.set_commodity_flg=1 then (case when (SELECT MIN(S.STOCK_QUANTITY-S.ALLOCATED_QUANTITY) FROM SET_COMMODITY_COMPOSITION SCC,STOCK S WHERE SCC.CHILD_COMMODITY_CODE=S.COMMODITY_CODE AND SCC.COMMODITY_CODE=CH.COMMODITY_CODE GROUP BY SCC.COMMODITY_CODE)>=1 then 1 else 0 end) "
                    + " ELSE (CASE WHEN S.STOCK_QUANTITY - S.ALLOCATED_QUANTITY   >= (CASE WHEN CCH.COMBINATION_AMOUNT IS NULL  THEN 1 ELSE CCH.COMBINATION_AMOUNT END ) THEN 1 ELSE 0 END ) END AS AVA_QUANTITY "
                    
                    + " FROM PLAN_DETAIL PD "
                    + " INNER JOIN PLAN PL ON PL.PLAN_CODE = PD.PLAN_CODE  "
                    + " INNER JOIN PLAN_COMMODITY PC ON PD.PLAN_CODE = PC.PLAN_CODE "
                    + " AND PD.DETAIL_CODE = PC.DETAIL_CODE AND PD.DETAIL_TYPE = PC.DETAIL_TYPE "
                    + " INNER JOIN COMMODITY_HEADER CCH ON PC.COMMODITY_CODE = CCH.COMMODITY_CODE "
                    + " INNER JOIN COMMODITY_DETAIL CH ON CCH.COMMODITY_CODE = CH.COMMODITY_CODE "
                    + " INNER JOIN STOCK S ON S.SKU_CODE = CASE WHEN CCH.ORIGINAL_COMMODITY_CODE IS NULL THEN CCH.COMMODITY_CODE ELSE CCH.ORIGINAL_COMMODITY_CODE END "
                    + " INNER JOIN C_COMMODITY_DETAIL CCD ON CCD.SKU_CODE = CH.COMMODITY_CODE "
                    + " WHERE PD.PLAN_CODE = ? "
                    + " AND CCH.COMMODITY_TYPE = 0 AND (CCH.SALE_START_DATETIME <= " + SYSDATE + " AND " + SYSDATE + " <= CCH.SALE_END_DATETIME)"
                    + " AND CCH.SALE_FLG =  " +SaleFlg.FOR_SALE.getValue() + " AND CCH.REPRESENT_SKU_CODE = CH.SKU_CODE "
                    + " AND  ((SELECT STOCK_QUANTITY - ALLOCATED_QUANTITY AS ACT_STOCK FROM STOCK WHERE  SKU_CODE = ( CASE WHEN CCH.ORIGINAL_COMMODITY_CODE IS NULL THEN CH.SKU_CODE ELSE CCH.ORIGINAL_COMMODITY_CODE END)) >=  CASE WHEN CCH.COMBINATION_AMOUNT IS NULL THEN 1 ELSE CCH.COMBINATION_AMOUNT END OR CCH.STOCK_MANAGEMENT_TYPE = 1 OR CCH.SET_COMMODITY_FLG=1) "
                    //+	"      OR ((SELECT STOCK_QUANTITY - ALLOCATED_QUANTITY AS ACT_STOCK FROM STOCK WHERE  SKU_CODE = ( CASE WHEN CCH.ORIGINAL_COMMODITY_CODE IS NULL THEN CH.SKU_CODE ELSE CCH.ORIGINAL_COMMODITY_CODE END)) < CASE WHEN CCH.COMBINATION_AMOUNT IS NULL THEN 1 ELSE CCH.COMBINATION_AMOUNT END and ccd.use_flg = 1 and CCH.STOCK_MANAGEMENT_TYPE <> 1)) "
                    + " ORDER BY PD.DISPLAY_ORDER ASC ,PC.DISPLAY_ORDER ASC, "
                    + " CASE WHEN S.STOCK_QUANTITY - S.ALLOCATED_QUANTITY > 0 OR CCH.STOCK_MANAGEMENT_TYPE = 1 THEN 1 ELSE 0 END DESC, "
                    + " CASE WHEN EXISTS (SELECT DC.* FROM DISCOUNT_COMMODITY DC INNER JOIN DISCOUNT_HEADER DH ON DC.DISCOUNT_CODE = DH.DISCOUNT_CODE WHERE DC.COMMODITY_CODE = CH.COMMODITY_CODE AND DC.USE_FLG = 1 AND DH.DISCOUNT_START_DATETIME <= NOW() AND DH.DISCOUNT_END_DATETIME >= NOW()) THEN 1 ELSE 2 END ASC , "
                    + " CASE WHEN CCH.NEW_RESERVE_COMMODITY_TYPE3 = 9 THEN 9 ELSE CCH.NEW_RESERVE_COMMODITY_TYPE3 END ASC , "
                    + " CASE WHEN CCH.RESERVE_COMMODITY_TYPE2 = 9 THEN 9 ELSE CCH.RESERVE_COMMODITY_TYPE2 END ASC , "
                    + " CASE WHEN CCH.NEW_RESERVE_COMMODITY_TYPE1 = 9 THEN 9 ELSE CCH.NEW_RESERVE_COMMODITY_TYPE1 END ASC , "
                    + " CASE WHEN CCH.IMPORT_COMMODITY_TYPE = 9 THEN 9 ELSE CCH.IMPORT_COMMODITY_TYPE END ASC , "
                    + " CASE WHEN CCH.CLEAR_COMMODITY_TYPE = 9 THEN 9 ELSE CCH.CLEAR_COMMODITY_TYPE END DESC ";
                    //+ " PC.DISPLAY_ORDER,PD.DETAIL_NAME,CH.DISCOUNT_PRICE,PD.DISPLAY_ORDER, PD.DETAIL_CODE DESC ";
  

  
  public static final String GET_PLAN_INFO_LIST_QUERY_FOR_INDEX =  " SELECT * FROM ("
    + " SELECT /*+ INDEX(CD COMMODITY_DETAIL_IX2) */"
    + " CH.SHOP_CODE, CH.COMMODITY_CODE,CH.COMMODITY_NAME,CH.ORIGINAL_COMMODITY_CODE,CH.COMBINATION_AMOUNT, "
    + " CH.COMMODITY_NAME_JP,"
    + " CH.COMMODITY_NAME_EN,CD.UNIT_PRICE,CD.DISCOUNT_PRICE,"
    + " CH.SALE_START_DATETIME,"
    + " CH.SALE_END_DATETIME,"
    + " CH.DISCOUNT_PRICE_START_DATETIME,"
    + " CH.DISCOUNT_PRICE_END_DATETIME,"
    + " CH.RESERVATION_START_DATETIME,"
    + " CH.RESERVATION_END_DATETIME,"
    + " CH.COMMODITY_TAX_TYPE,"
    + " CH.COMMODITY_DESCRIPTION_PC,"
    + " CH.STOCK_MANAGEMENT_TYPE,"
    // add by yyq start 20130415
    + " CH.IMPORT_COMMODITY_TYPE,"
    + " CH.CLEAR_COMMODITY_TYPE,"
    + " CH.RESERVE_COMMODITY_TYPE1,"
    + " CH.RESERVE_COMMODITY_TYPE2,"    
    + " CH.RESERVE_COMMODITY_TYPE3,"    
    + " CH.NEW_RESERVE_COMMODITY_TYPE1,"  
    + " CH.NEW_RESERVE_COMMODITY_TYPE2,"  
    + " CH.NEW_RESERVE_COMMODITY_TYPE3,"  
    + " CH.NEW_RESERVE_COMMODITY_TYPE4," 
    + " CH.NEW_RESERVE_COMMODITY_TYPE5," 
    + " CD.INNER_QUANTITY," 
    // add by yyq end 20130415
    + " CH.SALE_FLG"
    + " FROM COMMODITY_HEADER CH"
    + " INNER JOIN COMMODITY_DETAIL CD ON CD.SHOP_CODE = CH.SHOP_CODE"
    + " AND CD.SKU_CODE = CH.REPRESENT_SKU_CODE"
    + " LEFT JOIN REVIEW_SUMMARY RS ON RS.COMMODITY_CODE = CH.COMMODITY_CODE"
    + " WHERE ((NOW() BETWEEN CH.SALE_START_DATETIME AND CH.SALE_END_DATETIME) OR"
    + " (NOW() BETWEEN CH.RESERVATION_START_DATETIME AND"
    + " CH.RESERVATION_END_DATETIME))"
    + " AND CH.SALE_FLG = 1"
    + " AND CH.CATEGORY_PATH IS NOT NULL"
    + " AND EXISTS"
    + " (SELECT /*+ INDEX(SP SHOP_IX1) */"
    + " 'OK'"
    + " FROM SHOP SP"
    + " WHERE SP.SHOP_CODE = CH.SHOP_CODE"
    + " AND NOW() BETWEEN SP.OPEN_DATETIME AND SP.CLOSE_DATETIME)"
    + " AND EXISTS (SELECT /*+ INDEX(DJ DELIVERY_TYPE_IX1) */"
    + " 'OK'"
    + " FROM DELIVERY_TYPE DJ"
    + " WHERE DJ.DELIVERY_TYPE_NO = CH.DELIVERY_TYPE_NO"
    + " AND DJ.SHOP_CODE = CH.SHOP_CODE"
    + " AND DJ.DISPLAY_FLG = 1)"
    + " AND EXISTS (SELECT /*+ INDEX(CC CATEGORY_COMMODITY_IX1) */"
    + " 'OK'"
    + " FROM CATEGORY_COMMODITY CC"
    + " WHERE CC.SHOP_CODE = CH.SHOP_CODE"
    + " AND CC.COMMODITY_CODE = CH.COMMODITY_CODE)"
    + " AND CH.DISPLAY_CLIENT_TYPE IN (0, 1)"
    // 如果当前没有库存则不查出来20120914 add by yyq
    + " AND ((SELECT STOCK_QUANTITY - ALLOCATED_QUANTITY AS ACT_STOCK FROM STOCK WHERE  SKU_CODE = ( CASE WHEN CH.ORIGINAL_COMMODITY_CODE IS NULL THEN CD.SKU_CODE ELSE CH.ORIGINAL_COMMODITY_CODE END)) > 0 OR CH.STOCK_MANAGEMENT_TYPE = 1)  "
    + " AND ((SELECT STOCK_QUANTITY - ALLOCATED_QUANTITY AS ACT_STOCK FROM STOCK WHERE  SKU_CODE = ( CASE WHEN CH.ORIGINAL_COMMODITY_CODE IS NULL THEN CD.SKU_CODE ELSE CH.ORIGINAL_COMMODITY_CODE END)) >=  CASE WHEN CH.COMBINATION_AMOUNT IS NULL THEN 0 ELSE CH.COMBINATION_AMOUNT END OR CH.STOCK_MANAGEMENT_TYPE = 1)"
    + " AND CH.RESERVE_COMMODITY_TYPE1 <> 1"
    + " ORDER BY "
    + " CASE WHEN (SELECT STOCK_QUANTITY - ALLOCATED_QUANTITY AS ACT_STOCK"
    + " FROM STOCK WHERE CD.SHOP_CODE = SHOP_CODE AND CD.SKU_CODE = SKU_CODE) > 0 OR"
    + " CH.STOCK_MANAGEMENT_TYPE = 1 THEN"
    + " 1 ELSE 0 END DESC,"
    + " CASE WHEN CH.DISCOUNT_PRICE_START_DATETIME IS NOT NULL AND"
    + " CH.DISCOUNT_PRICE_END_DATETIME IS NOT NULL AND"
    + " TO_CHAR(NOW(), 'YYYY/MM/DD HH24:MI:SS') >="
    + " TO_CHAR(CH.DISCOUNT_PRICE_START_DATETIME,"
    + " 'YYYY/MM/DD HH24:MI:SS') AND"
    + " TO_CHAR(NOW(), 'YYYY/MM/DD HH24:MI:SS' ::TEXT) <="
    + " TO_CHAR(CH.DISCOUNT_PRICE_END_DATETIME,"
    + " 'YYYY/MM/DD HH24:MI:SS' ::TEXT) THEN 1"
    + " WHEN CH.DISCOUNT_PRICE_START_DATETIME IS NOT NULL AND"
    + " CH.DISCOUNT_PRICE_END_DATETIME IS NULL AND"
    + " TO_CHAR(NOW(), 'YYYY/MM/DD HH24:MI:SS' ::TEXT) >="
    + " TO_CHAR(CH.DISCOUNT_PRICE_START_DATETIME,"
    + " 'YYYY/MM/DD HH24:MI:SS' ::TEXT) AND"
    + " TO_CHAR(NOW(), 'YYYY/MM/DD HH24:MI:SS' ::TEXT) <="
    + " TO_CHAR(CH.SALE_END_DATETIME,"
    + " 'YYYY/MM/DD HH24:MI:SS' ::TEXT) THEN 1"
    + " WHEN CH.DISCOUNT_PRICE_START_DATETIME IS NULL AND"
    + " CH.DISCOUNT_PRICE_END_DATETIME IS NOT NULL AND"
    + " TO_CHAR(NOW(), 'YYYY/MM/DD HH24:MI:SS' ::TEXT) >="
    + " TO_CHAR(CH.SALE_START_DATETIME,"
    + " 'YYYY/MM/DD HH24:MI:SS' ::TEXT) AND"
    + " TO_CHAR(NOW(), 'YYYY/MM/DD HH24:MI:SS' ::TEXT) <="
    + " TO_CHAR(CH.DISCOUNT_PRICE_END_DATETIME,"
    + " 'YYYY/MM/DD HH24:MI:SS' ::TEXT) THEN 1 ELSE 0 END DESC,"
    + " CH.COMMODITY_POPULAR_RANK ASC) T LIMIT 5";

}
