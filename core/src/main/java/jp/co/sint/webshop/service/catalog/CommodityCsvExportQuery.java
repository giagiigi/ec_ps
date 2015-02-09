package jp.co.sint.webshop.service.catalog;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.AbstractQuery;
import jp.co.sint.webshop.service.CCommodityHeadline;
import jp.co.sint.webshop.utility.StringUtil;


public class CommodityCsvExportQuery extends AbstractQuery<CCommodityHeadline> {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private static final String COMMODITY_HEAD_COLUMNS = ""
    + " SELECT    CH.SHOP_CODE " 
    + "   ,CH.COMMODITY_CODE " 
    + "   ,CH.COMMODITY_NAME "
    + "   ,CH.COMMODITY_NAME_EN " 
    + "   ,CH.REPRESENT_SKU_CODE "
    + "   ,CH.REPRESENT_SKU_UNIT_PRICE "
    + "   ,CH.COMMODITY_DESCRIPTION_PC "
    + "   ,CH.COMMODITY_DESCRIPTION_MOBILE"
    + "   ,CH.COMMODITY_SEARCH_WORDS " 
    + "   ,CH.SALE_START_DATETIME " 
    + "   ,CH.SALE_END_DATETIME "
    + "   ,CH.DISCOUNT_PRICE_START_DATETIME " 
    + "   ,CH.DISCOUNT_PRICE_END_DATETIME " 
    + "   ,CH.STANDARD1_ID " 
    + "   ,CH.STANDARD1_NAME " 
    + "   ,CH.STANDARD2_ID "
    + "   ,CH.STANDARD2_NAME " 
    + "   ,CH.SALE_FLG "
    + "   ,CH.RETURN_FLG "
    + "   ,CH.DISPLAY_CLIENT_TYPE "
    + "   ,CH.WARNING_FLAG "
    + "   ,CH.LEAD_TIME " 
    + "   ,CH.SALE_FLAG "
    + "   ,CH.SPEC_FLAG "
    + "   ,CH.BRAND_CODE " 
    + "   ,CH.T_COMMODITY_ID " 
    + "   ,CH.T_REPRESENT_SKU_PRICE  "
    + "   ,CH.T_CATEGORY_ID  "
    + "   ,CH.SUPPLIER_CODE  "
    + "   ,CH.BUYER_CODE  "
    + "   ,CH.TAX_CODE  "
    + "   ,CH.ORIGINAL_PLACE  "
    + "   ,CH.INGREDIENT  "
    + "   ,CH.MATERIAL  "
    + "   ,CH.BIG_FLAG  ";

  private static final String COMMODITY_DETAIL_COLUMNS = ""
      + "SELECT    CD.SHOP_CODE"
      + "   ,CD.SKU_CODE"
      + "   ,CD.SKU_NAME"
      + "   ,CD.COMMODITY_CODE"
      + "   ,CD.PURCHASE_PRICE"
      + "   ,CD.UNIT_PRICE"
      + "   ,CD.DISCOUNT_PRICE"
      + "   ,CD.DISPLAY_ORDER"
      + "   ,CD.STANDARD_DETAIL1_ID"
      + "   ,CD.STANDARD_DETAIL1_NAME"
      + "   ,CD.STANDARD_DETAIL2_ID"
      + "   ,CD.STANDARD_DETAIL2_NAME"
      + "   ,CD.WEIGHT"
      + "   ,CD.USE_FLG"
      + "   ,CD.MINIMUM_ORDER"
      + "   ,CD.MAXIMUM_ORDER"
      + "   ,CD.ORDER_MULTIPLE"
      + "   ,CD.STOCK_WARNING"
      + "   ,CD.TMALL_SKU_ID"
      + "   ,CD.TMALL_UNIT_PRICE"
      + "   ,CD.TMALL_DISCOUNT_PRICE";
      
  private static final String COMMODITY_COLUMNS = ""
    + "SELECT    CH.SHOP_CODE"
    +	"   ,CD.SKU_CODE"
    + "   ,CD.SKU_NAME"
    + "   ,CD.COMMODITY_CODE"
    + "   ,CD.PURCHASE_PRICE"
    + "   ,CD.UNIT_PRICE"
    + "   ,CD.DISCOUNT_PRICE"
    + "   ,CD.DISPLAY_ORDER"
    + "   ,CD.STANDARD_DETAIL1_ID"
    + "   ,CD.STANDARD_DETAIL1_NAME"
    + "   ,CD.STANDARD_DETAIL2_ID"
    + "   ,CD.STANDARD_DETAIL2_NAME"
    + "   ,CD.WEIGHT"
    + "   ,CD.USE_FLG"
    + "   ,CD.MINIMUM_ORDER"
    + "   ,CD.MAXIMUM_ORDER"
    + "   ,CD.ORDER_MULTIPLE"
    + "   ,CD.STOCK_WARNING"
    + "   ,CD.TMALL_SKU_ID"
    + "   ,CD.TMALL_UNIT_PRICE"
    + "   ,CD.TMALL_DISCOUNT_PRICE"
    + "   ,CH.COMMODITY_NAME "
    + "   ,CH.COMMODITY_NAME_EN " 
    + "   ,CH.REPRESENT_SKU_CODE "
    + "   ,CH.REPRESENT_SKU_UNIT_PRICE "
    + "   ,CH.COMMODITY_DESCRIPTION_PC "
    + "   ,CH.COMMODITY_DESCRIPTION_MOBILE"
    + "   ,CH.COMMODITY_SEARCH_WORDS " 
    + "   ,CH.SALE_START_DATETIME " 
    + "   ,CH.SALE_END_DATETIME "
    + "   ,CH.DISCOUNT_PRICE_START_DATETIME " 
    + "   ,CH.DISCOUNT_PRICE_END_DATETIME " 
    + "   ,CH.STANDARD1_ID " 
    + "   ,CH.STANDARD1_NAME " 
    + "   ,CH.STANDARD2_ID "
    + "   ,CH.STANDARD2_NAME " 
    + "   ,CH.SALE_FLG "
    + "   ,CH.RETURN_FLG "
    + "   ,CH.DISPLAY_CLIENT_TYPE "
    + "   ,CH.WARNING_FLAG "
    + "   ,CH.LEAD_TIME " 
    + "   ,CH.SALE_FLAG "
    + "   ,CH.SPEC_FLAG "
    + "   ,CH.BRAND_CODE " 
    + "   ,CH.T_COMMODITY_ID " 
    + "   ,CH.T_REPRESENT_SKU_PRICE  "
    + "   ,CH.T_CATEGORY_ID  "
    + "   ,CH.SUPPLIER_CODE  "
    + "   ,CH.BUYER_CODE  "
    + "   ,CH.TAX_CODE  "
    + "   ,CH.ORIGINAL_PLACE  "
    + "   ,CH.INGREDIENT  "
    + "   ,CH.MATERIAL  "
    + "   ,CH.BIG_FLAG  "
    + "   FROM C_COMMODITY_HEADER CH "
    + " INNER JOIN C_COMMODITY_DETAIL CD "
    + " ON CD.SHOP_CODE = CH.SHOP_CODE "
    + " AND CD.COMMODITY_CODE = CH.COMMODITY_CODE ";
  
  private static final String COMMODITY_HEADER_BASE_COLUMNS = ""
    + "SELECT  '''' || CH.COMMODITY_CODE AS COMMODITY_CODE ";
  
  private static final String COMMODITY_DETAIL_BASE_COLUMNS = ""
    + "SELECT  '''' || CD.SKU_CODE AS SKU_CODE ";
//    + "   ,CD.SKU_NAME"
//    + "   ,CD.COMMODITY_CODE";
  //20120119 os013 add start
  private static final String COMMODITY_HEADER_AND_DETAIL_BASE_COLUMNS = ""
    + "SELECT '''' || CH.COMMODITY_CODE AS COMMODITY_CODE ";
  @SuppressWarnings("unused")
  private static final String COMMODITY_STOCK_BASE_COLUMNS = ""
    + "SELECT SK.SKU_CODE";
  //20120119 os013 add end
  //20120206 os013 add start
  private static final String CATEGORY_ATTRIBUTE_VALUE=""
    + " SELECT '''' || CC.COMMODITY_CODE AS COMMODITY_CODE "
    + " ,CC.CATEGORY_CODE";
  //20120206 os013 add end
  public CommodityCsvExportQuery(String code, String searchExportObject) {
    StringBuilder builder = new StringBuilder();
    List<Object> params = new ArrayList<Object>();
    if (searchExportObject.equals("0")) {
      builder.append(COMMODITY_HEAD_COLUMNS);
      builder.append(" FROM C_COMMODITY_HEADER CH ");
      builder.append(" WHERE 1 = 1 ");
      if (StringUtil.hasValue(code)) {
        String[] element = code.split("\r\n");
        if (element.length > 0) {
          builder.append(" AND CH.COMMODITY_CODE IN ( ");
          if (element.length == 0) {
            builder.append(" ? ");
            params.add(code);
          } else {
            for (int i = 0; i < element.length; i++) {
              builder.append(" ? ");
              if (i != element.length - 1) {
                builder.append(", ");
              }
              params.add(element[i]);
            }
          }
          builder.append(") ");
        }
      }
    } else if (searchExportObject.equals("1")) {
      builder.append(COMMODITY_DETAIL_COLUMNS);
      builder.append(" FROM C_COMMODITY_DETAIL CD ");
      builder.append(" WHERE 1 = 1 ");
      if (StringUtil.hasValue(code)) {
        String[] element = code.split("\r\n");
        if (element.length > 0) {
          builder.append(" AND CD.SKU_CODE IN ( ");
          if (element.length == 0) {
            builder.append(" ? ");
            params.add(code);
          } else {
            for (int i = 0; i < element.length; i++) {
              builder.append(" ? ");
              if (i != element.length - 1) {
                builder.append(", ");
              }
              params.add(element[i]);
            }
          }
          builder.append(") ");
        }
      }
    } else {
      builder.append(COMMODITY_COLUMNS);
      builder.append(" WHERE 1 = 1 ");
      if (StringUtil.hasValue(code)) {
        String[] element = code.split("\r\n");
        if (element.length > 0) {
          builder.append(" AND CD.SKU_CODE IN ( ");
          if (element.length == 0) {
            builder.append(" ? ");
            params.add(code);
          } else {
            for (int i = 0; i < element.length; i++) {
              builder.append(" ? ");
              if (i != element.length - 1) {
                builder.append(", ");
              }
              params.add(element[i]);
            }
          }
          builder.append(") ");
        }
      }
    }
    this.setParameters(params.toArray());
    this.setSqlString(builder.toString());
  }
  
  public CommodityCsvExportQuery(String sqlString, String code, String searchExportObject,String combineType) {
    StringBuilder builder = new StringBuilder();
    List<Object> params = new ArrayList<Object>();
    if (searchExportObject.equals("0")) {
      builder.append(COMMODITY_HEADER_BASE_COLUMNS);
      builder.append(sqlString);
      builder.append(" FROM C_COMMODITY_HEADER CH ");
      builder.append(" WHERE 1 = 1 ");
      if (StringUtil.hasValue(code)) {
        String[] element = code.split("\r\n");
        if (element.length > 0) {
          builder.append(" AND CH.COMMODITY_CODE IN ( ");
          if (element.length == 0) {
            builder.append(" ? ");
            params.add(code);
          } else {
            for (int i = 0; i < element.length; i++) {
              builder.append(" ? ");
              if (i != element.length - 1) {
                builder.append(", ");
              }
              params.add(element[i]);
            }
          }
          builder.append(") ");
        }
      }
      if (combineType.equals("2")){
        builder.append(" AND ( ORIGINAL_COMMODITY_CODE IS NOT NULL OR CH.COMMODITY_CODE IN (SELECT ORIGINAL_COMMODITY_CODE FROM C_COMMODITY_HEADER WHERE ORIGINAL_COMMODITY_CODE IS NOT NULL " );
        String[] element = code.split("\r\n");
        if (element.length > 0) {
          builder.append(" AND COMMODITY_CODE IN ( ");
          if (element.length == 0) {
            builder.append(" ? ");
            params.add(code);
          } else {
            for (int i = 0; i < element.length; i++) {
              builder.append(" ? ");
              if (i != element.length - 1) {
                builder.append(", ");
              }
              params.add(element[i]);
            }
          }
          builder.append(") ");
        }
        builder.append(" )) ");
      } else if (combineType.equals("1")){
        builder.append(" AND  ORIGINAL_COMMODITY_CODE IS  NULL  ");
      }
      builder.append(" ORDER BY ");
      builder.append(" CH.COMMODITY_CODE");
    } else if (searchExportObject.equals("1")) {
      builder.append(COMMODITY_DETAIL_BASE_COLUMNS);
      builder.append(sqlString);
      builder.append(" FROM C_COMMODITY_HEADER CH ");
      builder.append(" INNER JOIN C_COMMODITY_DETAIL CD ");
      builder.append(" ON CD.SHOP_CODE = CH.SHOP_CODE ");
      builder.append(" AND CD.COMMODITY_CODE = CH.COMMODITY_CODE ");
      builder.append(" INNER JOIN STOCK SK ");
      builder.append(" ON CD.SKU_CODE = SK.SKU_CODE ");
      builder.append(" WHERE 1 = 1 ");
      if (StringUtil.hasValue(code)) {
        String[] element = code.split("\r\n");
        if (element.length > 0) {
          builder.append(" AND CD.SKU_CODE IN ( ");
          if (element.length == 0) {
            builder.append(" ? ");
            params.add(code);
          } else {
            for (int i = 0; i < element.length; i++) {
              builder.append(" ? ");
              if (i != element.length - 1) {
                builder.append(", ");
              }
              params.add(element[i]);
            }
          }
          builder.append(") ");
        }
      }
      if (combineType.equals("2")){
        builder.append(" AND ( ORIGINAL_COMMODITY_CODE IS NOT NULL OR CH.COMMODITY_CODE IN (SELECT ORIGINAL_COMMODITY_CODE FROM C_COMMODITY_HEADER WHERE ORIGINAL_COMMODITY_CODE IS NOT NULL " );
        String[] element = code.split("\r\n");
        if (element.length > 0) {
          builder.append(" AND COMMODITY_CODE IN ( ");
          if (element.length == 0) {
            builder.append(" ? ");
            params.add(code);
          } else {
            for (int i = 0; i < element.length; i++) {
              builder.append(" ? ");
              if (i != element.length - 1) {
                builder.append(", ");
              }
              params.add(element[i]);
            }
          }
          builder.append(") ");
        }
        builder.append(" )) ");
      } else if (combineType.equals("1")){
        builder.append(" AND  ORIGINAL_COMMODITY_CODE IS  NULL  ");
      }
      builder.append(" ORDER BY ");
      builder.append(" CD.SKU_CODE");
    } else if (searchExportObject.equals("2")){
      builder.append(COMMODITY_HEADER_AND_DETAIL_BASE_COLUMNS);
      builder.append(sqlString);
      builder.append("   FROM C_COMMODITY_HEADER CH ");
      builder.append(" INNER JOIN C_COMMODITY_DETAIL CD ");
      builder.append(" ON CD.SHOP_CODE = CH.SHOP_CODE ");
      builder.append(" AND CD.COMMODITY_CODE = CH.COMMODITY_CODE ");
      builder.append(" INNER JOIN STOCK SK ");
      builder.append(" ON CD.SKU_CODE = SK.SKU_CODE ");
      builder.append(" WHERE 1 = 1 ");
      if (StringUtil.hasValue(code)) {
        String[] element = code.split("\r\n");
        if (element.length > 0) {
          builder.append(" AND CD.SKU_CODE IN ( ");
          if (element.length == 0) {
            builder.append(" ? ");
            params.add(code);
          } else {
            for (int i = 0; i < element.length; i++) {
              builder.append(" ? ");
              if (i != element.length - 1) {
                builder.append(", ");
              }
              params.add(element[i]);
            }
          }
          builder.append(") ");
        }
      }
      if (combineType.equals("2")){
        builder.append(" AND ( ORIGINAL_COMMODITY_CODE IS NOT NULL OR CH.COMMODITY_CODE IN (SELECT ORIGINAL_COMMODITY_CODE FROM C_COMMODITY_HEADER WHERE ORIGINAL_COMMODITY_CODE IS NOT NULL " );
        String[] element = code.split("\r\n");
        if (element.length > 0) {
          builder.append(" AND COMMODITY_CODE IN ( ");
          if (element.length == 0) {
            builder.append(" ? ");
            params.add(code);
          } else {
            for (int i = 0; i < element.length; i++) {
              builder.append(" ? ");
              if (i != element.length - 1) {
                builder.append(", ");
              }
              params.add(element[i]);
            }
          }
          builder.append(") ");
        }
        builder.append(" )) ");
      } else if (combineType.equals("1")){
        builder.append(" AND  ORIGINAL_COMMODITY_CODE IS  NULL  ");
      }
      builder.append(" ORDER BY ");
      builder.append(" CH.COMMODITY_CODE , CD.SKU_CODE");
    } else if (searchExportObject.equals("3")){
      builder.append(CATEGORY_ATTRIBUTE_VALUE);
      builder.append(sqlString);
      builder.append(" FROM (SELECT CA.COMMODITY_CODE,CA.CATEGORY_CODE,");
      builder.append(" CASE CA.CATEGORY_ATTRIBUTE_NO WHEN 0 THEN CH.ORIGINAL_CODE ELSE NULL END ");
      builder.append(" AS ORIGINAL_CODE ,");
      builder.append(" CASE CA.CATEGORY_ATTRIBUTE_NO WHEN 0 THEN CA.CATEGORY_ATTRIBUTE_VALUE ELSE NULL END ");
      builder.append(" AS CATEGORY_ATTRIBUTE_VALUE1 ,");
      builder.append(" CASE CA.CATEGORY_ATTRIBUTE_NO WHEN 0 THEN CA.CATEGORY_ATTRIBUTE_VALUE_EN ELSE NULL END ");
      builder.append(" AS CATEGORY_ATTRIBUTE_VALUE1_EN ,");
      builder.append(" CASE CA.CATEGORY_ATTRIBUTE_NO WHEN 0 THEN CA.CATEGORY_ATTRIBUTE_VALUE_JP ELSE NULL END ");
      builder.append(" AS CATEGORY_ATTRIBUTE_VALUE1_JP ,");
      builder.append(" CASE CA.CATEGORY_ATTRIBUTE_NO WHEN 1 THEN CA.CATEGORY_ATTRIBUTE_VALUE ELSE NULL END ");
      builder.append(" AS CATEGORY_ATTRIBUTE_VALUE2 ,");
      builder.append(" CASE CA.CATEGORY_ATTRIBUTE_NO WHEN 1 THEN CA.CATEGORY_ATTRIBUTE_VALUE_EN ELSE NULL END ");
      builder.append(" AS CATEGORY_ATTRIBUTE_VALUE2_EN ,");
      builder.append(" CASE CA.CATEGORY_ATTRIBUTE_NO WHEN 1 THEN CA.CATEGORY_ATTRIBUTE_VALUE_JP ELSE NULL END ");
      builder.append(" AS CATEGORY_ATTRIBUTE_VALUE2_JP ,");
      builder.append(" CASE CA.CATEGORY_ATTRIBUTE_NO WHEN 2 THEN CA.CATEGORY_ATTRIBUTE_VALUE ELSE NULL END ");
      builder.append(" AS CATEGORY_ATTRIBUTE_VALUE3,");
      builder.append(" CASE CA.CATEGORY_ATTRIBUTE_NO WHEN 2 THEN CA.CATEGORY_ATTRIBUTE_VALUE_EN ELSE NULL END ");
      builder.append(" AS CATEGORY_ATTRIBUTE_VALUE3_EN,");
      builder.append(" CASE CA.CATEGORY_ATTRIBUTE_NO WHEN 2 THEN CA.CATEGORY_ATTRIBUTE_VALUE_JP ELSE NULL END ");
      builder.append(" AS CATEGORY_ATTRIBUTE_VALUE3_JP");
      builder.append(" FROM CATEGORY_ATTRIBUTE_VALUE  CA INNER JOIN C_COMMODITY_HEADER CH ON CH.COMMODITY_CODE = CA.COMMODITY_CODE ) AS CAV ");
      builder.append(" RIGHT JOIN CATEGORY_COMMODITY CC ");
      builder.append(" ON CC.COMMODITY_CODE = CAV.COMMODITY_CODE ");
      builder.append(" AND CC.CATEGORY_CODE = CAV.CATEGORY_CODE ");
      builder.append(" LEFT JOIN C_COMMODITY_DETAIL CD ");
      builder.append(" ON CD.COMMODITY_CODE = CC.COMMODITY_CODE ");
      builder.append(" WHERE 1 = 1 ");
      if (StringUtil.hasValue(code)) {
        String[] element = code.split("\r\n");
        if (element.length > 0) {
          builder.append(" AND CC.COMMODITY_CODE IN ( ");
          if (element.length == 0) {
            builder.append(" ? ");
            params.add(code);
          } else {
            for (int i = 0; i < element.length; i++) {
              builder.append(" ? ");
              if (i != element.length - 1) {
                builder.append(", ");
              }
              params.add(element[i]);
            }
          }
          builder.append(") ");
        }
      }
      builder.append(" GROUP BY ");
      builder.append(" CC.COMMODITY_CODE,CC.CATEGORY_CODE,CAV.ORIGINAL_CODE");
      builder.append(" ORDER BY ");
      builder.append(" CC.COMMODITY_CODE,CC.CATEGORY_CODE");
    }
    this.setParameters(params.toArray());
    this.setSqlString(builder.toString());
  }
  //20120201 os013 add start
  //根据用户输入的是商品编号或SKU编号拼sql条件
  public CommodityCsvExportQuery(String sqlString, String code, String searchExportObject,String commodityOrSku,String combineType) {
    StringBuilder builder = new StringBuilder();
    List<Object> params = new ArrayList<Object>();
    if (searchExportObject.equals("0")){
      builder.append(COMMODITY_HEADER_BASE_COLUMNS);
      builder.append(sqlString);
      builder.append(" FROM C_COMMODITY_HEADER CH ");
      if(commodityOrSku.equals("2")){
      builder.append(" INNER JOIN C_COMMODITY_DETAIL CD ");
      builder.append(" ON CD.SHOP_CODE = CH.SHOP_CODE ");
      builder.append(" AND CD.COMMODITY_CODE = CH.COMMODITY_CODE ");
      }
      builder.append(" WHERE 1 = 1 ");
      if (StringUtil.hasValue(code)) {
        String[] element = code.split("\r\n");
        if (element.length > 0) {
          if(commodityOrSku.equals("1")){
            builder.append(" AND CH.COMMODITY_CODE IN ( ");
          }else if(commodityOrSku.equals("2")){
            builder.append(" AND CD.SKU_CODE IN ( ");
          } 
          if (element.length == 0) {
            builder.append(" ? ");
            params.add(code);
          } else {
            for (int i = 0; i < element.length; i++) {
              builder.append(" ? ");
              if (i != element.length - 1) {
                builder.append(", ");
              }
              params.add(element[i]);
            }
          }
          builder.append(") ");
        }
      }
      if (combineType.equals("2")){
        builder.append(" AND ( ORIGINAL_COMMODITY_CODE IS NOT NULL OR CH.COMMODITY_CODE IN (SELECT ORIGINAL_COMMODITY_CODE FROM C_COMMODITY_HEADER WHERE ORIGINAL_COMMODITY_CODE IS NOT NULL " );
        String[] element = code.split("\r\n");
        if (element.length > 0) {
          builder.append(" AND COMMODITY_CODE IN ( ");
          if (element.length == 0) {
            builder.append(" ? ");
            params.add(code);
          } else {
            for (int i = 0; i < element.length; i++) {
              builder.append(" ? ");
              if (i != element.length - 1) {
                builder.append(", ");
              }
              params.add(element[i]);
            }
          }
          builder.append(") ");
        }
        builder.append(" )) ");
      } else if (combineType.equals("1")){
        builder.append(" AND  ORIGINAL_COMMODITY_CODE IS  NULL  ");
      }
      builder.append(" ORDER BY ");
      builder.append(" CH.COMMODITY_CODE");
    }else if(searchExportObject.equals("1")){
      builder.append(COMMODITY_DETAIL_BASE_COLUMNS);
      builder.append(sqlString);
      builder.append(" FROM C_COMMODITY_HEADER CH ");
      builder.append(" INNER JOIN C_COMMODITY_DETAIL CD ");
      builder.append(" ON CD.SHOP_CODE = CH.SHOP_CODE ");
      builder.append(" AND CD.COMMODITY_CODE = CH.COMMODITY_CODE ");
      builder.append(" INNER JOIN STOCK SK ");
      builder.append(" ON CD.SKU_CODE = SK.SKU_CODE ");
      builder.append(" WHERE 1 = 1 ");
      if (StringUtil.hasValue(code)) {
        String[] element = code.split("\r\n");
        if (element.length > 0) {
          if(commodityOrSku.equals("1")){
            builder.append(" AND CD.COMMODITY_CODE IN ( ");
          }else if(commodityOrSku.equals("2")){
            builder.append(" AND CD.SKU_CODE IN ( ");
          } 
          if (element.length == 0) {
            builder.append(" ? ");
            params.add(code);
          } else {
            for (int i = 0; i < element.length; i++) {
              builder.append(" ? ");
              if (i != element.length - 1) {
                builder.append(", ");
              }
              params.add(element[i]);
            }
          }
          builder.append(") ");
        }
      }
      if (combineType.equals("2")){
        builder.append(" AND ( ORIGINAL_COMMODITY_CODE IS NOT NULL OR CH.COMMODITY_CODE IN (SELECT ORIGINAL_COMMODITY_CODE FROM C_COMMODITY_HEADER WHERE ORIGINAL_COMMODITY_CODE IS NOT NULL " );
        String[] element = code.split("\r\n");
        if (element.length > 0) {
          builder.append(" AND COMMODITY_CODE IN ( ");
          if (element.length == 0) {
            builder.append(" ? ");
            params.add(code);
          } else {
            for (int i = 0; i < element.length; i++) {
              builder.append(" ? ");
              if (i != element.length - 1) {
                builder.append(", ");
              }
              params.add(element[i]);
            }
          }
          builder.append(") ");
        }
        builder.append(" )) ");
      } else if (combineType.equals("1")){
        builder.append(" AND  ORIGINAL_COMMODITY_CODE IS  NULL  ");
      }
      builder.append(" ORDER BY ");
      builder.append(" CD.SKU_CODE");
    }else if (searchExportObject.equals("2")){
      builder.append(COMMODITY_HEADER_AND_DETAIL_BASE_COLUMNS);
      builder.append(sqlString);
      builder.append("   FROM C_COMMODITY_HEADER CH ");
      builder.append(" INNER JOIN C_COMMODITY_DETAIL CD ");
      builder.append(" ON CD.SHOP_CODE = CH.SHOP_CODE ");
      builder.append(" AND CD.COMMODITY_CODE = CH.COMMODITY_CODE ");
      builder.append(" INNER JOIN STOCK SK ");
      builder.append(" ON CD.SKU_CODE = SK.SKU_CODE ");
      builder.append(" WHERE 1 = 1 ");
      if (StringUtil.hasValue(code)) {
        String[] element = code.split("\r\n");
        if (element.length > 0) {
          if(commodityOrSku.equals("1")){
            builder.append(" AND CH.COMMODITY_CODE IN ( ");
          }else if(commodityOrSku.equals("2")){
            builder.append(" AND CD.SKU_CODE IN ( ");
          } 
          if (element.length == 0) {
            builder.append(" ? ");
            params.add(code);
          } else {
            for (int i = 0; i < element.length; i++) {
              builder.append(" ? ");
              if (i != element.length - 1) {
                builder.append(", ");
              }
              params.add(element[i]);
            }
          }
          builder.append(") ");
        }
      }
      if (combineType.equals("2")){
        builder.append(" AND ( ORIGINAL_COMMODITY_CODE IS NOT NULL OR CH.COMMODITY_CODE IN (SELECT ORIGINAL_COMMODITY_CODE FROM C_COMMODITY_HEADER WHERE ORIGINAL_COMMODITY_CODE IS NOT NULL " );
        String[] element = code.split("\r\n");
        if (element.length > 0) {
          builder.append(" AND COMMODITY_CODE IN ( ");
          if (element.length == 0) {
            builder.append(" ? ");
            params.add(code);
          } else {
            for (int i = 0; i < element.length; i++) {
              builder.append(" ? ");
              if (i != element.length - 1) {
                builder.append(", ");
              }
              params.add(element[i]);
            }
          }
          builder.append(") ");
        }
        builder.append(" )) ");
      } else if (combineType.equals("1")){
        builder.append(" AND  ORIGINAL_COMMODITY_CODE IS  NULL  ");
      }
      builder.append(" ORDER BY ");
      builder.append(" CH.COMMODITY_CODE ,CD.SKU_CODE");
    }else if(searchExportObject.equals("3")){
      builder.append(CATEGORY_ATTRIBUTE_VALUE);
      builder.append(sqlString);
      builder.append(" FROM (SELECT CA.COMMODITY_CODE,CA.CATEGORY_CODE,");
      builder.append(" CASE CA.CATEGORY_ATTRIBUTE_NO WHEN 0 THEN CH.ORIGINAL_CODE ELSE NULL END ");
      builder.append(" AS ORIGINAL_CODE ,");
//      builder.append(" CASE CATEGORY_ATTRIBUTE_NO WHEN 0 THEN CATEGORY_ATTRIBUTE_VALUE_EN ELSE NULL END ");
//      builder.append(" AS CATEGORY_ATTRIBUTE_VALUE1_EN ,");
//      builder.append(" CASE CATEGORY_ATTRIBUTE_NO WHEN 0 THEN CATEGORY_ATTRIBUTE_VALUE_JP ELSE NULL END ");
//      builder.append(" AS CATEGORY_ATTRIBUTE_VALUE1_JP ,");
      builder.append(" CASE CA.CATEGORY_ATTRIBUTE_NO WHEN 1 THEN CA.CATEGORY_ATTRIBUTE_VALUE ELSE NULL END ");
      builder.append(" AS CATEGORY_ATTRIBUTE_VALUE2 ,");
      builder.append(" CASE CA.CATEGORY_ATTRIBUTE_NO WHEN 1 THEN CA.CATEGORY_ATTRIBUTE_VALUE_EN ELSE NULL END ");
      builder.append(" AS CATEGORY_ATTRIBUTE_VALUE2_EN ,");
      builder.append(" CASE CA.CATEGORY_ATTRIBUTE_NO WHEN 1 THEN CA.CATEGORY_ATTRIBUTE_VALUE_JP ELSE NULL END ");
      builder.append(" AS CATEGORY_ATTRIBUTE_VALUE2_JP ,");
      builder.append(" CASE CA.CATEGORY_ATTRIBUTE_NO WHEN 2 THEN CA.CATEGORY_ATTRIBUTE_VALUE ELSE NULL END ");
      builder.append(" AS CATEGORY_ATTRIBUTE_VALUE3,");
      builder.append(" CASE CA.CATEGORY_ATTRIBUTE_NO WHEN 2 THEN CA.CATEGORY_ATTRIBUTE_VALUE_EN ELSE NULL END ");
      builder.append(" AS CATEGORY_ATTRIBUTE_VALUE3_EN,");
      builder.append(" CASE CA.CATEGORY_ATTRIBUTE_NO WHEN 2 THEN CA.CATEGORY_ATTRIBUTE_VALUE_JP ELSE NULL END ");
      builder.append(" AS CATEGORY_ATTRIBUTE_VALUE3_JP");
      builder.append(" FROM CATEGORY_ATTRIBUTE_VALUE CA INNER JOIN C_COMMODITY_HEADER CH ON CH.COMMODITY_CODE = CA.COMMODITY_CODE ) AS CAV ");
      builder.append(" RIGHT JOIN CATEGORY_COMMODITY CC ");
      builder.append(" ON CC.COMMODITY_CODE = CAV.COMMODITY_CODE ");
      builder.append(" AND CC.CATEGORY_CODE = CAV.CATEGORY_CODE ");
      builder.append(" LEFT JOIN C_COMMODITY_DETAIL CD ");
      builder.append(" ON CD.COMMODITY_CODE = CC.COMMODITY_CODE ");
      builder.append(" WHERE 1 = 1 ");
      if (StringUtil.hasValue(code)) {
        String[] element = code.split("\r\n");
        if (element.length > 0) {
          if(commodityOrSku.equals("1")){
            builder.append(" AND CC.COMMODITY_CODE IN ( ");
          }else if(commodityOrSku.equals("2")){
            builder.append(" AND CD.SKU_CODE IN ( ");
          } 
          if (element.length == 0) {
            builder.append(" ? ");
            params.add(code);
          } else {
            for (int i = 0; i < element.length; i++) {
              builder.append(" ? ");
              if (i != element.length - 1) {
                builder.append(", ");
              }
              params.add(element[i]);
            }
          }
          builder.append(") ");
        }
      }
      builder.append(" GROUP BY ");
      builder.append(" CC.COMMODITY_CODE,CC.CATEGORY_CODE,CAV.ORIGINAL_CODE");
      builder.append(" ORDER BY ");
      builder.append(" CC.COMMODITY_CODE,CC.CATEGORY_CODE");
    }
    this.setParameters(params.toArray());
    this.setSqlString(builder.toString());
  }
  //20120201 os013 add end
  public Class<CCommodityHeadline> getRowType() {
    return CCommodityHeadline.class;
  }
}
