package jp.co.sint.webshop.service.communication;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.sint.webshop.data.AbstractQuery;
import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.Query; // 10.1.3 10140 追加
import jp.co.sint.webshop.data.SimpleQuery; // 10.1.3 10140 追加
import jp.co.sint.webshop.data.dto.CommodityHeader;
import jp.co.sint.webshop.data.dto.ReviewPost;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.SqlDialect;
import jp.co.sint.webshop.utility.SqlFragment;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.utility.SqlDialect.DateSearchAccuracy;
import jp.co.sint.webshop.utility.SqlDialect.LikeClauseOption;

public class ReviewSearchQuery extends AbstractQuery<ReviewList> {

  private static final long serialVersionUID = 1L;

  private static final String DISPLAYTYPE_ALL = "3"; // "3"=すべて表示

  public ReviewSearchQuery() {

  }

  public ReviewSearchQuery(ReviewListSearchCondition condition) {

    StringBuilder builder = new StringBuilder();
    List<Object> params = new ArrayList<Object>();

    builder.append("SELECT ");
    for (String column : DatabaseUtil.getColumnNames(ReviewPost.class)) {
      // 10.1.3 10140 修正 ここから
      // builder.append(" R." + column + ", ");
      if (!column.equals("REVIEW_DESCRIPTION")) {
        builder.append(" R." + column + ", ");  
      }
      // 10.1.3 10140 修正 ここまで
    }
    builder.append("C.COMMODITY_NAME,C.COMMODITY_NAME_EN,C.COMMODITY_NAME_JP, CASE WHEN C.DISCOUNT_PRICE_START_DATETIME <= NOW() AND NOW() <= C.DISCOUNT_PRICE_END_DATETIME THEN 1 ELSE 0 END AS DISCOUNT_MODE");
    builder.append(" FROM " + DatabaseUtil.getTableName(ReviewPost.class) + " R");
    builder.append(" INNER JOIN " + DatabaseUtil.getTableName(CommodityHeader.class) + " C");
    builder.append(" ON R.COMMODITY_CODE = C.COMMODITY_CODE AND R.SHOP_CODE = C.SHOP_CODE WHERE 1 = 1");

    // ショップコード
    if (StringUtil.hasValue(condition.getSearchShopCode())) {
      builder.append(" AND R.SHOP_CODE = ?");
      params.add(condition.getSearchShopCode());
    }
    //20111219 os013 add start
    // 受注履歴ID
    if (StringUtil.hasValue(condition.getSearchOrderNo())) {
      builder.append(" AND R.ORDER_NO = ?");
      params.add(condition.getSearchOrderNo());
    }
    //20111219 os013 add end
    // 投稿日
    Date startDate = DateUtil.fromString(condition.getSearchReviewContributedDatetimeFrom());
    Date endDate = DateUtil.fromString(condition.getSearchReviewContributedDatetimeTo());
    SqlDialect dialect = SqlDialect.getDefault();
    SqlFragment dateFragment = dialect.createDateRangeClause("R.REVIEW_CONTRIBUTED_DATETIME", startDate, endDate,
        DateSearchAccuracy.DATE);
    if (StringUtil.hasValue(dateFragment.getFragment())) {
      builder.append(" AND " + dateFragment.getFragment());
      for (Object o : dateFragment.getParameters()) {
        params.add(o);
      }
    }

    // 商品コード
    SqlFragment commodityCodeFragment = SqlDialect.getDefault().createRangeClause("R.COMMODITY_CODE",
        condition.getSearchCommodityCodeFrom(), condition.getSearchCommodityCodeTo());
    if (StringUtil.hasValue(commodityCodeFragment.getFragment())) {
      builder.append(" AND " + commodityCodeFragment.getFragment());
      for (Object o : commodityCodeFragment.getParameters()) {
        params.add(o);
      }
    }

    // 商品名
    if (StringUtil.hasValue(condition.getSearchCommodityName())) {
      SqlFragment commodityNameFragment = SqlDialect.getDefault().createLikeClause("C.COMMODITY_NAME",
          condition.getSearchCommodityName(), LikeClauseOption.PARTIAL_MATCH);
      builder.append(" AND " + commodityNameFragment.getFragment());
      for (Object o : commodityNameFragment.getParameters()) {
        params.add(o);
      }
    }

    // タイトル・内容
    if (StringUtil.hasValue(condition.getSearchReviewContent())) {
      String content = condition.getSearchReviewContent();
      SqlFragment titleFragment = SqlDialect.getDefault().createLikeClause("R.REVIEW_TITLE", content,
          LikeClauseOption.PARTIAL_MATCH);
      SqlFragment descriptionFragment = SqlDialect.getDefault().createLikeClause("R.REVIEW_DESCRIPTION", content,
          LikeClauseOption.PARTIAL_MATCH);

      builder.append(" AND (");
      builder.append(titleFragment.getFragment());
      builder.append(" OR ");
      builder.append(descriptionFragment.getFragment());
      builder.append(" )");

      for (Object o : titleFragment.getParameters()) {
        params.add(o);
      }

      for (Object o : descriptionFragment.getParameters()) {
        params.add(o);
      }
    }

    // 表示状態
    if (StringUtil.hasValue(condition.getSearchReviewDisplayType())
        && !condition.getSearchReviewDisplayType().equals(DISPLAYTYPE_ALL)) {
      builder.append(" AND R.REVIEW_DISPLAY_TYPE = ?");
      params.add(condition.getSearchReviewDisplayType());
    }

    // 顧客コード
    if (StringUtil.hasValue(condition.getSearchCustomerCode())) {
      builder.append(" AND R.CUSTOMER_CODE = ? ");
      params.add(condition.getSearchCustomerCode());
    }

    // 商品コード
    if (StringUtil.hasValue(condition.getSearchCommodityCode())) {
      builder.append(" AND R.COMMODITY_CODE = ? ");
      params.add(condition.getSearchCommodityCode());
    }

    // 並べ替え条件(投稿日、商品名の昇順降順で切り替え可能)
    if (StringUtil.hasValue(condition.getSearchListSort())) {
      builder.append(" ORDER BY");
      if (condition.getSearchListSort().equals("contributedDatetimeAsc")) {
        builder.append(" R.REVIEW_CONTRIBUTED_DATETIME ASC");
      } else if (condition.getSearchListSort().equals("contributedDatetimeDesc")) {
        builder.append(" R.REVIEW_CONTRIBUTED_DATETIME DESC");
      } else if (condition.getSearchListSort().equals("commodityNameAsc")) {
        builder.append(" C.COMMODITY_NAME ASC");
      } else if (condition.getSearchListSort().equals("commodityNameDesc")) {
        builder.append(" C.COMMODITY_NAME DESC");
      }
    } else {
      builder.append(" ORDER BY R.REVIEW_CONTRIBUTED_DATETIME DESC");
    }
		// add by lc 2012-03-28 start
    // 限定查询数量
    if (StringUtil.hasValue(condition.getSearchDisplayCount())) {
      builder.append(" LIMIT ").append(condition.getSearchDisplayCount());
    }
		// add by lc 2012-03-28 end  
    setSqlString(builder.toString());
    setParameters(params.toArray());
    setPageNumber(condition.getCurrentPage());
    setPageSize(condition.getPageSize());

  }

  // 10.1.3 10140 追加 ここから
  public Query getReviewDiscriptionQuery(String reviewId) {
    String query = "SELECT REVIEW_DESCRIPTION FROM REVIEW_POST WHERE REVIEW_ID = ?";
    return new SimpleQuery(query, reviewId);
  }
  // 10.1.3 10140 追加 ここまで
  
  public Class<ReviewList> getRowType() {
    return ReviewList.class;
  }

}
