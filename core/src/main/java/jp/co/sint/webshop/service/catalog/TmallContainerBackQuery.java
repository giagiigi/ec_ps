package jp.co.sint.webshop.service.catalog;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.AbstractQuery;
import jp.co.sint.webshop.data.dto.Stock;
import jp.co.sint.webshop.service.CommodityHeadline;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.SqlDialect;
import jp.co.sint.webshop.utility.SqlFragment;
import jp.co.sint.webshop.utility.WebUtil;

public class TmallContainerBackQuery extends AbstractQuery<CommodityHeadline> {

  private static final long serialVersionUID = 1L;

  public Class<CommodityHeadline> getRowType() {
    return CommodityHeadline.class;
  }

  public TmallContainerBackQuery(List<CommodityKey> keyList) {
    String length = WebUtil.buildMaxlength(Stock.class, "stockQuantity", null);
    String stockQuantity = "";
    for (Long i = 0L; i < NumUtil.toLong(length); i++) {
      stockQuantity += "9";
    }

    StringBuilder b = new StringBuilder();
    List<Object> params = new ArrayList<Object>();
    b.append(" SELECT CH.SHOP_CODE, ");
    //查询是否有库存
    b.append("( CASE WHEN MIN(ST.STOCK_TOTAL - ST.ALLOCATED_QUANTITY - ST.ALLOCATED_TMALL) > 0 THEN 0  " +
    		"WHEN MAX(ST.STOCK_TOTAL - ST.ALLOCATED_QUANTITY - ST.ALLOCATED_TMALL)>0 THEN 1 " +
    		"ELSE 2 END ) " +
    		"AS STOCK_STATUS, ");
    
    b.append(" SH.SHORT_SHOP_NAME AS SHOP_NAME, ");
    b.append(" CH.COMMODITY_CODE, ");
    b.append(" CH.COMMODITY_NAME, ");
    b.append(" CH.COMMODITY_TYPE, ");
    b.append(" CH.ORIGINAL_COMMODITY_CODE, ");
    b.append(" CH.COMBINATION_AMOUNT, ");
    b.append(" CH.COMMODITY_SEARCH_WORDS, ");
    //2014/4/28 京东WBS对应 ob_李 add start
    b.append(" CH.represent_sku_code, ");
    //2014/4/28 京东WBS对应 ob_李 add end
    b.append(" MIN(CH.UNIT_PRICE) UNIT_PRICE, ");
    b.append(" MIN (CH.RETAIL_PRICE ) RETAIL_PRICE, ");
//  postgreSQL start
//    b.append(" (CASE WHEN SYSDATE BETWEEN CH.RESERVATION_START_DATETIME AND CH.RESERVATION_END_DATETIME THEN 0 ");
//    b.append("       WHEN SYSDATE BETWEEN CH.SALE_START_DATETIME AND CH.SALE_END_DATETIME THEN 1 ");
    b.append(" (CASE WHEN "+SqlDialect.getDefault().getCurrentDatetime()+" BETWEEN CH.SALE_START_DATETIME AND CH.SALE_END_DATETIME THEN 1 ");
//  postgreSQL end    
    b.append("       ELSE 2 END) AS SALE_STATUS, ");
    b.append(" (CASE WHEN CH.SALE_FLG_EC = 1 ");
    b.append(" AND COUNT_CATEGORY_FUNC(CH.SHOP_CODE, CH.COMMODITY_CODE) > 0 THEN 1 ");
    b.append("       ELSE 0 END) AS SALE_TYPE, ");
    b.append(" COUNT_RELATED_A_FUNC(CH.SHOP_CODE, CH.COMMODITY_CODE) AS RELATED_COMMODITY_A_COUNT, ");
    b.append(" COUNT_RELATED_B_FUNC(CH.SHOP_CODE, CH.COMMODITY_CODE) AS RELATED_COMMODITY_B_COUNT, ");
    b.append(" COUNT_CATEGORY_FUNC( CH.SHOP_CODE, CH.COMMODITY_CODE) AS RELATED_CATEGORY_COUNT, ");
    b.append(" COUNT_TAG_FUNC(      CH.SHOP_CODE, CH.COMMODITY_CODE) AS RELATED_TAG_COUNT, ");
    b.append(" COUNT_CAMPAIGN_FUNC( CH.SHOP_CODE, CH.COMMODITY_CODE) AS RELATED_CAMPAIGN_COUNT, ");
    b.append(" COUNT_GIFT_FUNC(     CH.SHOP_CODE, CH.COMMODITY_CODE) AS RELATED_GIFT_COUNT ");
    b.append(" ");
    b.append(" FROM C_COMMODITY_LIST_VIEW CH ");
    b.append(" INNER JOIN SHOP SH ON SH.SHOP_CODE = CH.SHOP_CODE ");
    b.append(" INNER JOIN STOCK ST ON  ST.SHOP_CODE = CH.SHOP_CODE AND ST.COMMODITY_CODE = CH.COMMODITY_CODE ");

    if (keyList != null && keyList.size() > 0) {
      SqlFragment fragment = CommodityKey.buildInClause("CH.SHOP_CODE", "CH.COMMODITY_CODE", keyList);
      b.append("WHERE ");
      b.append(fragment.getFragment());
      b.append(" GROUP BY CH.SHOP_CODE, SH.SHOP_NAME, SH.SHORT_SHOP_NAME, CH.COMMODITY_CODE,CH.COMMODITY_TYPE, ");
      b.append("CH.COMMODITY_NAME, CH.REPRESENT_SKU_CODE, ");
      b.append("          CH.COMMODITY_DESCRIPTION1, ");
      b.append("          CH.COMMODITY_DESCRIPTION_SHORT, CH.COMMODITY_SEARCH_WORDS, ");
      b.append("CH.SALE_START_DATETIME, CH.SALE_END_DATETIME, ");
      b.append("          CH.DISCOUNT_PRICE_START_DATETIME, CH.DISCOUNT_PRICE_END_DATETIME, ");
      b.append("          CH.STANDARD1_NAME, CH.STANDARD2_NAME, ");
      b.append("          CH.SALE_FLG_EC, ");
      b.append("          ST.SHOP_CODE, ST.COMMODITY_CODE, ");
      b.append("          CH.ORIGINAL_COMMODITY_CODE,CH.COMBINATION_AMOUNT ");
      b.append(" ");
      b.append(" HAVING 1 = 1  ");

      for (Object value : fragment.getParameters()) {
        params.add(value);
      }
      fragment.setParameters(params.toArray());
      this.setParameters(fragment.getParameters());
    }
    this.setSqlString(b.toString());
  }

}
