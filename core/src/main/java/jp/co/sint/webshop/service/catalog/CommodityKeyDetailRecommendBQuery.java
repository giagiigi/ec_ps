package jp.co.sint.webshop.service.catalog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import jp.co.sint.webshop.data.AbstractQuery;
import jp.co.sint.webshop.data.domain.DisplayClientType;
import jp.co.sint.webshop.utility.ArrayUtil;
import jp.co.sint.webshop.utility.SqlDialect;
import jp.co.sint.webshop.utility.SqlFragment;
import jp.co.sint.webshop.utility.StringUtil;

public class CommodityKeyDetailRecommendBQuery extends AbstractQuery<CommodityKey> {

  private static final long serialVersionUID = 1L;

  public Class<CommodityKey> getRowType() {
    return CommodityKey.class;
  }

  public CommodityKeyDetailRecommendBQuery() {
    this(new CommodityContainerCondition());
  }

  public CommodityKeyDetailRecommendBQuery(CommodityContainerCondition condition) {
    List<Object> params = new ArrayList<Object>();

    // select - from - where (必須条件) まで
    StringBuilder b = new StringBuilder();

    b.append(" SELECT RCB.SHOP_CODE,RCB.LINK_COMMODITY_CODE AS COMMODITY_CODE FROM RELATED_COMMODITY_B RCB "
            + " INNER JOIN COMMODITY_HEADER CH ON RCB.LINK_COMMODITY_CODE = CH.COMMODITY_CODE " 
            + " INNER JOIN STOCK S ON CH.COMMODITY_CODE = S.COMMODITY_CODE " 
            + " WHERE  CH.SALE_FLG = 1 AND CH.COMMODITY_TYPE = 0 AND RCB.SHOP_CODE = ?  " 
            + " AND ((SELECT STOCK_QUANTITY - ALLOCATED_QUANTITY AS ACT_STOCK FROM STOCK WHERE  SKU_CODE = ( CASE WHEN CH.ORIGINAL_COMMODITY_CODE IS NULL THEN CH.COMMODITY_CODE ELSE CH.ORIGINAL_COMMODITY_CODE END)) > 0 OR CH.STOCK_MANAGEMENT_TYPE = 1)   "
            + " AND ((SELECT STOCK_QUANTITY - ALLOCATED_QUANTITY AS ACT_STOCK FROM STOCK WHERE  SKU_CODE = ( CASE WHEN CH.ORIGINAL_COMMODITY_CODE IS NULL THEN CH.COMMODITY_CODE ELSE CH.ORIGINAL_COMMODITY_CODE END)) >=  CASE WHEN CH.COMBINATION_AMOUNT IS NULL THEN 0 ELSE CH.COMBINATION_AMOUNT END OR CH.STOCK_MANAGEMENT_TYPE = 1)  ");
    b.append(" AND (" + SqlDialect.getDefault().getCurrentDatetime() + " BETWEEN CH.SALE_START_DATETIME AND CH.SALE_END_DATETIME) ");
    
    if (StringUtil.hasValueAnyOf(condition.getSearchCartCommodityCode())) {
      String[] cartCommodityCode = ArrayUtil.immutableCopy(condition.getSearchCartCommodityCode());
      SqlFragment fragment = SqlDialect.getDefault().createInClause("RCB.COMMODITY_CODE", (Object[]) cartCommodityCode);
      b.append(" AND "+ fragment.getFragment());
      params.add(condition.getSearchShopCode());
      params.addAll(Arrays.asList(condition.getSearchCartCommodityCode()));
    }else{
      b.append(" AND RCB.COMMODITY_CODE = ? ");
      params.add(condition.getSearchShopCode());
      params.add(condition.getSearchCommodityCode());
    }

    
    // 表示クライアント指定
    if (StringUtil.hasValue(condition.getDisplayClientType())) {
      DisplayClientType dct = DisplayClientType.fromValue(condition.getDisplayClientType());
      if (dct != null && (dct == DisplayClientType.PC || dct == DisplayClientType.MOBILE)) {
        b.append(" AND CH.DISPLAY_CLIENT_TYPE IN (");
        b.append(DisplayClientType.ALL.getValue());
        b.append(",");
        b.append(dct.getValue());
        b.append(") ");
      }
    }
    if (StringUtil.hasValueAnyOf(condition.getSearchCartCommodityCode())) {
      b.append(" GROUP BY RCB.SHOP_CODE,CH.COMMODITY_POPULAR_RANK,RCB.LINK_COMMODITY_CODE ");
      b.append(" ORDER BY CH.COMMODITY_POPULAR_RANK,SUM(RCB.RANKING_SCORE) DESC,RCB.LINK_COMMODITY_CODE LIMIT ?");
    }else{
      b.append(" ORDER BY CH.COMMODITY_POPULAR_RANK,RCB.RANKING_SCORE DESC,RCB.LINK_COMMODITY_CODE LIMIT ?");
    }

    params.add(condition.getMaxFetchSize());
    setSqlString(b.toString());
    setParameters(params.toArray());
    setMaxFetchSize(condition.getMaxFetchSize());
    setPageNumber(condition.getCurrentPage());
    setPageSize(condition.getPageSize());
  }

}
