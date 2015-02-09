package jp.co.sint.webshop.service.catalog;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.AbstractQuery;
import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.dto.Gift;
import jp.co.sint.webshop.data.dto.GiftCommodity;
import jp.co.sint.webshop.utility.SqlDialect;
import jp.co.sint.webshop.utility.StringUtil;

public class RelatedSearchQueryGift extends AbstractQuery <RelatedGift> {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /**
   * 
   */
  public RelatedSearchQueryGift() {

  }
  /**
   * 該当の商品コードに関連付いているギフトの一覧を取得する
   */
  public RelatedSearchQueryGift createRelatedGiftSearchQuery(RelatedSearchConditionBaseCommodity condition) {

    StringBuilder builder = new StringBuilder();
    List<Object> params = new ArrayList<Object>();

    builder.append(" SELECT   A.SHOP_CODE, "
        + "                   A.GIFT_CODE, "
        + "                   A.GIFT_NAME, "
        + "                   A.GIFT_PRICE, "
        + "                   A.GIFT_DESCRIPTION, "
        + "                   A.GIFT_TAX_TYPE, "
        + "                   B.COMMODITY_CODE "
        + "          FROM     (" + DatabaseUtil.getSelectAllQuery(Gift.class)
        + "                    WHERE SHOP_CODE = ?) A "
        + "                      LEFT OUTER JOIN (" + DatabaseUtil.getSelectAllQuery(GiftCommodity.class)
        + "                                       WHERE SHOP_CODE = ? AND  "
        + "                                             COMMODITY_CODE = ?) B "
        + "                      ON A.SHOP_CODE = B.SHOP_CODE AND "
        + "                         A.GIFT_CODE  = B.GIFT_CODE ");
    
    params.add(condition.getShopCode());
    params.add(condition.getShopCode());
    params.add(condition.getCommodityCode());
    
    builder.append(" WHERE 1 = 1 ");
    if (StringUtil.hasValue(condition.getSearchEffectualCodeStart())
        && StringUtil.hasValue(condition.getSearchEffectualCodeEnd())) {
      builder.append(" AND A.GIFT_CODE BETWEEN ? AND ?");
      params.add(condition.getSearchEffectualCodeStart());
      params.add(condition.getSearchEffectualCodeEnd());
    } else if (StringUtil.hasValue(condition.getSearchEffectualCodeStart())) {
      builder.append(" AND A.GIFT_CODE >= ?");
      params.add(condition.getSearchEffectualCodeStart());
    } else if (StringUtil.hasValue(condition.getSearchEffectualCodeEnd())) {
      builder.append(" AND A.GIFT_CODE <= ?");
      params.add(condition.getSearchEffectualCodeEnd());
    }
    if (StringUtil.hasValue(condition.getSearchEffectualName())) {
      builder.append(" AND ");
      builder.append(SqlDialect.getDefault().getLikeClausePattern("A.GIFT_NAME"));
      String commodityName = SqlDialect.getDefault().escape(condition.getSearchEffectualName());
      commodityName = "%" + commodityName + "%";
      params.add(commodityName);
    }
    
    builder.append(" ORDER BY A.GIFT_CODE");

    setSqlString(builder.toString());
    setParameters(params.toArray());
    setPageNumber(condition.getCurrentPage());
    setPageSize(condition.getPageSize());

    return this;
  }
  
  /**
   * 
   */
  public Class <RelatedGift> getRowType() {
    return RelatedGift.class;
  }
}
