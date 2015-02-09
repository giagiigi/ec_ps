package jp.co.sint.webshop.service.catalog;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.data.AbstractQuery;
import jp.co.sint.webshop.data.dao.CategoryDao; 
import jp.co.sint.webshop.data.domain.PriceList;
import jp.co.sint.webshop.data.dto.Category;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.SqlDialect;
import jp.co.sint.webshop.utility.SqlFragment;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.utility.SqlDialect.LikeClauseOption;

import org.apache.log4j.Logger;

public class LeftMenuInfoQuery extends AbstractQuery<CommodityKey> {

  private static final long serialVersionUID = 1L;
  
  private String currentLanguageCode = DIContainer.getLocaleContext().getCurrentLanguageCode();

  public Class<CommodityKey> getRowType() {
    return CommodityKey.class;
  }

  public LeftMenuInfoQuery() {
    this("","", "", "", "", "", "", "", "", "", "");
  }

  private StringBuilder getInitializedBuffer() {

    StringBuilder b = new StringBuilder();

    // select clause
    b.append("SELECT CH.COMMODITY_CODE,  ");
    b.append("CH.CATEGORY_PATH ,  ");
    b.append("CH.CATEGORY_ATTRIBUTE1 ,  ");
    b.append("CH.CATEGORY_ATTRIBUTE1_EN ,  ");
    b.append("CH.CATEGORY_ATTRIBUTE1_JP ,  ");
    b.append("CH.COMMODITY_POPULAR_RANK,  ");
    b.append("CH.BRAND_CODE,  ");
    b.append("B.BRAND_NAME,  ");
    b.append("B.BRAND_ENGLISH_NAME AS BRAND_NAME_EN,  ");
    b.append("B.BRAND_JAPANESE_NAME AS BRAND_NAME_JP,  ");
    b.append("CASE WHEN RS.REVIEW_SCORE = 100 THEN 80 ELSE RS.REVIEW_SCORE END,  ");
//    b.append("CASE WHEN CH.REPRESENT_SKU_UNIT_PRICE BETWEEN 0 AND 200 THEN 1  ");
//    b.append("WHEN CH.REPRESENT_SKU_UNIT_PRICE BETWEEN 200 AND 500 THEN 2  ");
//    b.append("WHEN CH.REPRESENT_SKU_UNIT_PRICE BETWEEN 500 AND 1000 THEN 3  ");
//    b.append("WHEN CH.REPRESENT_SKU_UNIT_PRICE BETWEEN 1000 AND 2000 THEN 4  ");
//    b.append("ELSE 5 END AS PRICE  ");
    b.append("CH.REPRESENT_SKU_UNIT_PRICE AS PRICE ");
    // from clause and joinees
    b.append("FROM COMMODITY_HEADER CH INNER JOIN COMMODITY_DETAIL CD ");
    b.append("ON CD.SHOP_CODE = CH.SHOP_CODE AND CD.SKU_CODE = CH.REPRESENT_SKU_CODE ");
    b.append("LEFT JOIN BRAND B ON CH.BRAND_CODE = B.BRAND_CODE ");
    b.append("LEFT JOIN REVIEW_SUMMARY RS ON CH.COMMODITY_CODE = RS.COMMODITY_CODE ");
    b.append("INNER JOIN C_COMMODITY_DETAIL CCD ON CCD.SKU_CODE = CH.COMMODITY_CODE ");
    // where clause: required conditions
    b.append("WHERE ");
    // postgreSQL start
    //b.append("((SYSDATE BETWEEN CH.SALE_START_DATETIME AND CH.SALE_END_DATETIME)  OR ");
    //b.append(" (SYSDATE BETWEEN CH.RESERVATION_START_DATETIME AND CH.RESERVATION_END_DATETIME)) ");
    b.append("(" + SqlDialect.getDefault().getCurrentDatetime() + " BETWEEN CH.SALE_START_DATETIME AND CH.SALE_END_DATETIME) ");
    // postgreSQL end 
    // 20130528 shen update start
    // b.append("AND (SELECT STOCK_QUANTITY - ALLOCATED_QUANTITY AS ACT_STOCK FROM STOCK WHERE CD.SHOP_CODE = SHOP_CODE AND CD.SKU_CODE = SKU_CODE) > 0 ");
//    b.append("AND ((SELECT STOCK_QUANTITY - ALLOCATED_QUANTITY AS ACT_STOCK FROM STOCK WHERE CD.SHOP_CODE = SHOP_CODE AND CD.SKU_CODE = SKU_CODE) > 0 OR CH.STOCK_MANAGEMENT_TYPE = 1) ");
    // 20130528 shen update end
//    b.append("AND CH.SALE_FLG = 1 AND CH.CATEGORY_PATH IS NOT NULL ");

    b.append(" AND CH.SALE_FLG = 1 AND CH.CATEGORY_PATH IS NOT NULL ");
    b.append(" AND ( ((SELECT STOCK_QUANTITY - ALLOCATED_QUANTITY AS ACT_STOCK FROM STOCK WHERE CD.SHOP_CODE = SHOP_CODE AND ( CASE WHEN CH.ORIGINAL_COMMODITY_CODE IS NULL THEN CD.SKU_CODE ELSE CH.ORIGINAL_COMMODITY_CODE END) = SKU_CODE) >= CASE WHEN CH.COMBINATION_AMOUNT IS NULL THEN 1 ELSE CH.COMBINATION_AMOUNT END OR CH.STOCK_MANAGEMENT_TYPE = 1) " +
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

  public LeftMenuInfoQuery(String selected , String categoryCode, String brandCode, String reviewScore, String priceType,
      String priceStart, String priceEnd, String categoryAttribute1, String categoryAttribute2, String categoryAttribute3, String searchWord) {
    Logger logger = Logger.getLogger(this.getClass());
    List<Object> params = new ArrayList<Object>();

    // select - from - where (必須条件) まで
    StringBuilder b = getInitializedBuffer();
    b.append(" AND CH.RESERVE_COMMODITY_TYPE1 <> 1 ");


    // キーワード指定
    if (StringUtil.hasValue(searchWord)) {
      setKeyWordCondition(searchWord, b, params);
    }

    // 価格帯指定
    if (StringUtil.hasValue(priceType)) {
      CodeAttribute price = PriceList.fromValue(priceType);
      String[] prices = price.getName().split(",");
      BigDecimal start = null;
      BigDecimal end = null;
      if (prices.length == 2) {
        start = NumUtil.parse(prices[0], BigDecimal.ZERO);
        end = NumUtil.parse(prices[1], BigDecimal.ZERO);
      } else {
        start = NumUtil.parse(prices[0], BigDecimal.ZERO);
        end = new BigDecimal("9999999999");
      }
      SqlFragment frag = SqlDialect.getDefault().createRangeClause("CH.REPRESENT_SKU_UNIT_PRICE", start, end);
      logger.debug(frag);
      b.append(" AND ");
      b.append(frag.getFragment());
      for (Object o : frag.getParameters()) {
        params.add(o);
      }
    } else {
      if (StringUtil.hasValueAnyOf(priceStart, priceEnd)) {
        BigDecimal start = NumUtil.parse(priceStart, BigDecimal.ZERO);
        BigDecimal end = NumUtil.parse(priceEnd, BigDecimal.ZERO);
        SqlFragment frag = SqlDialect.getDefault().createRangeClause("CH.REPRESENT_SKU_UNIT_PRICE", start, end);
        logger.debug(frag);
        b.append(" AND ");
        b.append(frag.getFragment());
        for (Object o : frag.getParameters()) {
          params.add(o);
        }
      }
    }

    // カテゴリ指定
    if (StringUtil.hasValue(categoryCode) && !categoryCode.equals("0")) {
      CategoryDao dao = DIContainer.getDao(CategoryDao.class);
      Category cat = dao.load(categoryCode);
//      b.append("AND EXISTS (SELECT /*+ INDEX(CC CATEGORY_COMMODITY_IX1) */ 'OK' FROM CATEGORY_COMMODITY CC ");
//      b.append("WHERE CC.SHOP_CODE = CH.SHOP_CODE AND CC.COMMODITY_CODE = CH.COMMODITY_CODE AND ");
      // 10.1.3 10173 修正 ここから
      // String searchCat = cat.getPath() + "~" + condition.getSearchCategoryCode();
      // 検索条件のカテゴリコードが存在しない場合は、結果件数が必ず0件になるようにする。
      // ⇒確実にこのSQL文の条件をFALSEにするため、1 = 0を検索条件に追加する。
      b.append(" AND ");
      String searchCat = "";
      if (cat != null) {
        searchCat = cat.getPath() + "~" + categoryCode;
      } else {
        b.append("1 = 0 AND ");
      }
      // 10.1.3 10173 修正 ここまで
      
      String searchFormat = "CH.CATEGORY_PATH";
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
    // 
    if (StringUtil.hasValue(brandCode)) {
      b.append("AND CH.BRAND_CODE = ?  ");
      params.add(brandCode);
    }
    
    if (StringUtil.hasValue(selected)) {
      b.append(" AND CH.NEW_RESERVE_COMMODITY_TYPE1 = 1 ");
    }

    // 
    if (StringUtil.hasValue(reviewScore)) {
      b.append("AND EXISTS (SELECT /*+ INDEX(RS REVIEW_SUMMARY_PK)*/ 'OK' FROM REVIEW_SUMMARY RS ");
      b.append("WHERE RS.SHOP_CODE = CH.SHOP_CODE AND RS.COMMODITY_CODE = CH.COMMODITY_CODE AND RS.REVIEW_SCORE >= ?) ");
      params.add(reviewScore);
    }
    // 
    if (StringUtil.hasValue(categoryAttribute1)) {
      b.append(" AND ");
      String searchFormat="";
      if(currentLanguageCode.equals("zh-cn")){
        searchFormat = " CH.CATEGORY_ATTRIBUTE1 ";
      }else if (currentLanguageCode.equals("ja-jp")){
        searchFormat = " CH.CATEGORY_ATTRIBUTE1_JP ";
      }else{
        searchFormat = " CH.CATEGORY_ATTRIBUTE1_EN ";
      }
      String searchCatTld = categoryAttribute1;
      SqlFragment frag = SqlDialect.getDefault().createLikeClause(searchFormat, searchCatTld, LikeClauseOption.PARTIAL_MATCH, false);
      b.append(frag.getFragment());
      for (Object o : frag.getParameters()) {
        params.add(o);
      }
    }
    // 
    if (StringUtil.hasValue(categoryAttribute2)) {
      b.append(" AND ");
      String searchFormat = " CH.CATEGORY_ATTRIBUTE1 ";
      String searchCatTld = categoryAttribute2;
      SqlFragment frag = SqlDialect.getDefault().createLikeClause(searchFormat, searchCatTld, LikeClauseOption.PARTIAL_MATCH, false);
      b.append(frag.getFragment());
      for (Object o : frag.getParameters()) {
        params.add(o);
      }
    }
    // 
    if (StringUtil.hasValue(categoryAttribute3)) {
      b.append(" AND ");
      String searchFormat = " CH.CATEGORY_ATTRIBUTE1 ";
      String searchCatTld = categoryAttribute3;
      SqlFragment frag = SqlDialect.getDefault().createLikeClause(searchFormat, searchCatTld, LikeClauseOption.PARTIAL_MATCH, false);
      b.append(frag.getFragment());
      for (Object o : frag.getParameters()) {
        params.add(o);
      }
    }

    setSqlString(b.toString());
    setParameters(params.toArray());
  }

  private void setKeyWordCondition(String searchWord, StringBuilder builder, List<Object> params) {
//    List<String> searchWordList = StringUtil.getSearchWordStringList(StringUtil.toSearchKeywords(searchWord), 
//        DIContainer.getWebshopConfig().getSearchWordMaxLength());
//    //edit by cs_yuli 20120601 start 
//    String[] defaultSearchColumns = new String[] {};   
//	defaultSearchColumns = new String[] {
//		      "CH.COMMODITY_CODE", "CH.COMMODITY_NAME||CH.COMMODITY_NAME_EN||CH.COMMODITY_NAME_JP", "CH.SHADOW_SEARCH_WORDS",
//		      "CH.COMMODITY_DESCRIPTION_PC||CH.COMMODITY_DESCRIPTION_PC_EN||CH.COMMODITY_DESCRIPTION_PC_JP", 
//		      "CH.COMMODITY_DESCRIPTION_MOBILE||CH.COMMODITY_DESCRIPTION_MOBILE_EN||CH.COMMODITY_DESCRIPTION_MOBILE_JP",
//		    }; 
//    //add by cs_yuli 20120601 end
//    // modify by wjh 20120104 start
////    String[] defaultSearchColumns = new String[] {
////    "CH.COMMODITY_CODE", "CH.COMMODITY_NAME", "CH.SHADOW_SEARCH_WORDS",
////    "CH.COMMODITY_DESCRIPTION_PC", "CH.COMMODITY_DESCRIPTION_MOBILE",
////  };
//    // modify by wjh 20120104 end
//    // 検索対象カラム
//    List<String> searchColumns = new ArrayList<String>();
//    for (String s : defaultSearchColumns) {
//      searchColumns.add(s);
//    } 
//    if (!searchWordList.isEmpty()) {
//      builder.append(" AND (");
//      for (int i = 0; i < searchWordList.size(); i++) {
//    	if(i>=1){
//    		  builder.append(" OR ");
//    	 }
//        if (StringUtil.hasValue(searchWordList.get(i).trim())) {
//          appendKeywordConditionParts(builder, params, searchWordList.get(i).trim(), searchColumns.toArray(new String[searchColumns
//              .size()]));
//          if (i != searchWordList.size() - 1 && searchWordList.size() != 1) {
//            String searchMethod = StringUtil.coalesce(searchWord, "0");
//            if (searchMethod.equals("0")) {
//              builder.append(" AND ");
//            } else {
//              builder.append(" OR ");
//            }
//          } else {
//              builder.append(" OR ");
//          }
//        } else {
//            builder.append(" OR ");
//        }
//        //edit by cs_yuli 20120601 start  
//        String brandNanme="";
//	    String catagoryName=""; 
//    	brandNanme="    (SELECT BRAND_NAME||BRAND_ENGLISH_NAME||BRAND_JAPANESE_NAME FROM BRAND WHERE BRAND_CODE = CH.BRAND_CODE) LIKE ?";
//    	catagoryName=" OR (SELECT CATEGORY_NAME_PC||CATEGORY_NAME_PC_EN||CATEGORY_NAME_PC_JP FROM CATEGORY WHERE PATH || '~' || CATEGORY_CODE = CH.CATEGORY_PATH) LIKE ? ";
//        builder.append(brandNanme); 
//        params.add("%" + SqlDialect.getDefault().escape(searchWordList.get(i).trim()) + "%"); 
//        builder.append(catagoryName);
//        params.add("%" + SqlDialect.getDefault().escape(searchWordList.get(i).trim()) + "%");
////        builder.append(" OR (SELECT ? FROM BRAND WHERE BRAND_CODE = CH.BRAND_CODE) LIKE ? "); 
////        params.add("%" + SqlDialect.getDefault().escape(searchWordList.get(i).trim()) + "%");
////        builder.append(" OR (SELECT ? FROM CATEGORY WHERE PATH || '~' || CATEGORY_CODE = CH.CATEGORY_PATH) LIKE ? "); 
////        params.add("%" + SqlDialect.getDefault().escape(searchWordList.get(i).trim()) + "%"); 
//        //edit by cs_yuli 20120601 end
//      }
//      builder.append(")");
//    }
    // add by twh 2013-6-3 start
    if(!StringUtil.isNullOrEmpty(searchWord)){
      searchWord = StringUtil.getWordAndWordOnlyOneSpace(searchWord);
    }
      List<String> searchWordList = StringUtil.getSearchWordStringList(searchWord, 
        DIContainer.getWebshopConfig().getSearchWordMaxLength());
      String searchSql = "";
      if (currentLanguageCode.equals("ja-jp")){
        searchSql = "( CH.KEYWORD_JP1 LIKE ? OR CH.KEYWORD_JP2 LIKE ? )";
      }else if (currentLanguageCode.equals("zh-cn")){
        searchSql = "(CH.KEYWORD_CN1 LIKE ? OR CH.KEYWORD_CN2 LIKE ?)";
      }else{
        searchSql = "(CH.KEYWORD_EN1 LIKE ? OR CH.KEYWORD_EN2 LIKE ?)";
      }
    if (!searchWordList.isEmpty()) {
        builder.append(" AND (");
        for (int i = 0; i < searchWordList.size(); i++) {
            if(i>=1 && i!=searchWordList.size() ){
                builder.append(" AND ");
            }
            builder.append(searchSql);
            params.add("%" + SqlDialect.getDefault().escape(searchWordList.get(i).trim()) + "%"); 
            params.add("%" + SqlDialect.getDefault().escape(searchWordList.get(i).trim()) + "%"); 
        }
        builder.append(")");
    
    }
    // add by twh 2013-6-3 end
    
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
