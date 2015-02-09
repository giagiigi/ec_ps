package jp.co.sint.webshop.service.catalog;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.AbstractQuery;
import jp.co.sint.webshop.data.dao.CategoryDao;
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

public class PriceFrontQuery extends AbstractQuery<CommodityKey> {

  private static final long serialVersionUID = 1L;
  
  public Class<CommodityKey> getRowType() {
    return CommodityKey.class;
  }

  public PriceFrontQuery() {
    this(new CommodityContainerCondition());
  }

  private StringBuilder getInitializedBuffer() {

    StringBuilder b = new StringBuilder();

    // select clause
    b.append("SELECT COUNT(COMMODITY_CODE) FROM COMMODITY_LIST_VIEW CH ");

    // where clause: required conditions
    b.append("WHERE ");
    b.append("(" + SqlDialect.getDefault().getCurrentDatetime() + " BETWEEN CH.SALE_START_DATETIME AND CH.SALE_END_DATETIME) ");
    b.append("AND CH.SALE_FLG = 1 AND CH.REPRESENT_SKU_CODE = CH.SKU_CODE ");

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

  public PriceFrontQuery(CommodityContainerCondition condition) {
    Logger logger = Logger.getLogger(this.getClass());
    List<Object> params = new ArrayList<Object>();

    // select - from - where (必須条件) まで
    StringBuilder b = getInitializedBuffer();

    // キーワード指定
    if (StringUtil.hasValue(condition.getSearchWord())) {
      setKeyWordCondition(condition, b, params);
    }

    // 価格帯指定
    if (StringUtil.hasValueAnyOf(condition.getSearchPriceStart(), condition.getSearchPriceEnd())) {
      BigDecimal start = NumUtil.parse(condition.getSearchPriceStart(), BigDecimal.ZERO);
      BigDecimal end = NumUtil.parse(condition.getSearchPriceEnd(), BigDecimal.ZERO);
      SqlFragment frag = SqlDialect.getDefault().createRangeClause("CH.UNIT_PRICE", start, end);
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
      // 10.1.3 10173 修正 ここから
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
      b.append("(" + SqlDialect.getDefault().toChar(searchFormat) + " = ? OR ");
      // postgreSQL end
      params.add(searchCat);
      // 前方一致(LIKE検索)
      String searchCatTld = searchCat + "~";
      SqlFragment frag = SqlDialect.getDefault().createLikeClause(searchFormat, searchCatTld, LikeClauseOption.STARTS_WITH, false);
      b.append(frag.getFragment());
      b.append(") ");
      // 10.1.2 10093 修正 ここまで
      for (Object o : frag.getParameters()) {
        params.add(o);
      }
    } else {
      // 条件がなくても、カテゴリに関連付けられた商品のみ表示する(商品公開仕様)
      b.append("AND EXISTS (SELECT /*+ INDEX(CC CATEGORY_COMMODITY_IX1) */ 'OK' FROM CATEGORY_COMMODITY CC ");
      b.append("WHERE CC.SHOP_CODE = CH.SHOP_CODE AND CC.COMMODITY_CODE = CH.COMMODITY_CODE) ");
    }

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
      b.append("AND SUBSTRING(CH.CATEGORY_ATTRIBUTE1, POSITION('|' IN CH.CATEGORY_ATTRIBUTE1) + 1) = ?  ");
      params.add(condition.getSearchCategoryAttribute1());
    }

    setSqlString(b.toString());
    setParameters(params.toArray());
    setMaxFetchSize(condition.getMaxFetchSize());
    setPageNumber(condition.getCurrentPage());
    setPageSize(condition.getPageSize());
  }

  private void setKeyWordCondition(CommodityContainerCondition condition, StringBuilder builder, List<Object> params) {
    List<String> searchWordList = StringUtil.getSearchWordStringList(StringUtil.toSearchKeywords(condition.getSearchWord()), 
        DIContainer.getWebshopConfig().getSearchWordMaxLength());
    // modify by wjh 20120104 start
    String[] defaultSearchColumns = new String[] {
      "CH.COMMODITY_CODE", "CH.COMMODITY_NAME", "CH.SHADOW_SEARCH_WORDS",
      "CH.COMMODITY_DESCRIPTION_PC", "CH.COMMODITY_DESCRIPTION_MOBILE",
    };
    // modify by wjh 20120104 end
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
        builder.append(" OR (SELECT BRAND_NAME FROM BRAND WHERE BRAND_CODE = CH.BRAND_CODE) LIKE ? ");
        params.add("%" + SqlDialect.getDefault().escape(searchWordList.get(i).trim()) + "%");
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
