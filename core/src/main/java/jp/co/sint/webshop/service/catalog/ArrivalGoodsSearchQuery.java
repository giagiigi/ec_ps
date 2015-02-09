package jp.co.sint.webshop.service.catalog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.co.sint.webshop.data.AbstractQuery;
import jp.co.sint.webshop.data.domain.ArrivalGoodsFlg;
import jp.co.sint.webshop.utility.SqlDialect;
import jp.co.sint.webshop.utility.SqlFragment;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.utility.SqlDialect.LikeClauseOption;

public class ArrivalGoodsSearchQuery extends AbstractQuery<ArrivalGoodsSubscritionCount> {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private static final String LOAD_ARRIVAL_GOODS = "" 
    + " SELECT a.SHOP_CODE,  " 
    + "        a.COMMODITY_NAME, " 
    + "        b.SKU_CODE, "
    + "        b.STANDARD_DETAIL1_NAME, " 
    + "        b.STANDARD_DETAIL2_NAME, " 
    + "        c.SUBSCRIPTION_COUNT  "
    + " FROM COMMODITY_HEADER a " 
    + " INNER JOIN (" 
    + "   SELECT SHOP_CODE,  " 
    + "          SKU_CODE,  " 
    + "          COMMODITY_CODE,  "
    + "          STANDARD_DETAIL1_NAME, " 
    + "          STANDARD_DETAIL2_NAME " 
    + "   FROM COMMODITY_DETAIL) b "
    + " ON  b.SHOP_CODE      = a.SHOP_CODE " 
    + " AND b.COMMODITY_CODE = a.COMMODITY_CODE " 
    + " INNER JOIN (" 
    + "   SELECT SHOP_CODE, "
    + "          SKU_CODE, " 
    + "          COUNT(EMAIL) SUBSCRIPTION_COUNT " 
    + "   FROM ARRIVAL_GOODS " 
    + "   GROUP BY SHOP_CODE, SKU_CODE) c "
    + " ON  c.SHOP_CODE = a.SHOP_CODE " 
    + " AND c.SKU_CODE  = b.SKU_CODE ";

  public ArrivalGoodsSearchQuery() {
  }

  public ArrivalGoodsSearchQuery createArrivalGoodsSearchQuery(ArrivalGoodsSearchCondition condition) {
    return createArrivalGoodsSearchQuery(condition, LOAD_ARRIVAL_GOODS);
  }

  public ArrivalGoodsSearchQuery createArrivalGoodsSearchQuery(ArrivalGoodsSearchCondition condition, String query) {
    // ショップコード、商品名、SKUコードでの検索クエリを生成
    StringBuilder builder = new StringBuilder();
    List<Object> params = new ArrayList<Object>();

    builder.append(query);

    // 検索条件開始
    builder.append(" WHERE a.ARRIVAL_GOODS_FLG = " + ArrivalGoodsFlg.ACCEPTABLE.longValue());
    
    // ショップコード検索
    builder.append(" AND   a.SHOP_CODE = ?  ");
    params.add(condition.getSearchShopCode());

    // 商品名の前方一致検索
    if (StringUtil.hasValue(condition.getSearchCommodityName())) {
      SqlFragment fragment = SqlDialect.getDefault().createLikeClause("a.COMMODITY_NAME", condition.getSearchCommodityName(), 
          LikeClauseOption.STARTS_WITH);
      builder.append(" AND " + fragment.getFragment());
      params.addAll(Arrays.asList(fragment.getParameters()));
    }

    // SKUコード検索
    if (StringUtil.hasValue(condition.getSearchSkuCode())) {
      builder.append(" AND   b.SKU_CODE = ? ");
      params.add(condition.getSearchSkuCode());
    }

    builder.append(" ORDER BY c.SUBSCRIPTION_COUNT DESC, a.SHOP_CODE, b.SKU_CODE");

    setSqlString(builder.toString());
    setParameters(params.toArray());
    setPageNumber(condition.getCurrentPage());
    setPageSize(condition.getPageSize());

    return this;
  }

  public Class<ArrivalGoodsSubscritionCount> getRowType() {
    return ArrivalGoodsSubscritionCount.class;
  }

}
