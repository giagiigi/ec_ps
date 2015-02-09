package jp.co.sint.webshop.service.communication;

import jp.co.sint.webshop.data.domain.DisplayFlg;

/**
 * 
 * @author System Integrator Corp.
 */
public final class ReviewSummaryQuery {
  
  /** serial version uid */
  private static final long serialVersionUID = 1L;
  
  /** default constructor */
  private ReviewSummaryQuery() {
  }

  /** レビュー集計用クエリ */
  public static final String GET_REVIEW_SUMMARY_QUERY = "" 
    + " SELECT SHOP_CODE, "
    + "        COMMODITY_CODE, "
    + "        ROUND(AVG(REVIEW_SCORE)) REVIEW_SCORE,"
    + "        COUNT(*) REVIEW_COUNT "
    + " FROM   REVIEW_POST "
    + " WHERE  REVIEW_DISPLAY_TYPE = " + DisplayFlg.VISIBLE.getValue()                                
    + " GROUP  BY SHOP_CODE, COMMODITY_CODE "
    + " HAVING SHOP_CODE = ? AND COMMODITY_CODE = ? ";

}
