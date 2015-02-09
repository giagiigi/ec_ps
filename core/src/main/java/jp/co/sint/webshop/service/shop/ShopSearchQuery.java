package jp.co.sint.webshop.service.shop;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.SearchQuery;
import jp.co.sint.webshop.data.SimpleQuery;
import jp.co.sint.webshop.data.dto.Shop;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.SqlDialect;
import jp.co.sint.webshop.utility.SqlFragment;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.utility.SqlDialect.LikeClauseOption;

/**
 * ショップを検索を行うクエリ
 * 
 * @author System Integrator Corp.
 */
public class ShopSearchQuery extends SimpleQuery implements SearchQuery<Shop> {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private int maxFetchSize = 1000;

  private int pageSize = 10;

  private int pageNumber = 1;

  public ShopSearchQuery() {
    super();
  }

  public ShopSearchQuery(ShopListSearchCondition condition) {

    StringBuilder builder = new StringBuilder();
    List<Object> params = new ArrayList<Object>();
    builder.append("SELECT SHOP_CODE, OPEN_DATETIME, " + "CLOSE_DATETIME, SHOP_NAME, SHOP_INTRODUCED_URL, "
        //Add by V10-CH start
        + "EMAIL, POSTAL_CODE, ADDRESS1, " + "ADDRESS2, ADDRESS3, ADDRESS4, PHONE_NUMBER, MOBILE_NUMBER, PERSON_IN_CHARGE, "
        //Add by V10-CH end
        + "SSL_PAGE, SHOP_TYPE, ORM_ROWID, CREATED_USER, " + "CREATED_DATETIME, UPDATED_USER, UPDATED_DATETIME "
        + " FROM SHOP WHERE 1 = 1");
    if (StringUtil.hasValue(condition.getShopCode())) {
      builder.append(" AND SHOP_CODE = ?");
      params.add(condition.getShopCode());
    }
    if (StringUtil.hasValue(condition.getShopName())) {
      SqlFragment fragment = SqlDialect.getDefault().createLikeClause("SHOP_NAME", condition.getShopName(),
          LikeClauseOption.PARTIAL_MATCH);

      builder.append(" AND " + fragment.getFragment());
      for (Object o : fragment.getParameters()) {
        params.add(o);
      }
    }
    if (StringUtil.hasValue(condition.getTel())) {
      builder.append(" AND REPLACE(PHONE_NUMBER,'-','') = ? ");
      params.add(condition.getTel());
    }
    //Add by V10-CH start
    if (StringUtil.hasValue(condition.getMobileTel())) {
      builder.append(" AND MOBILE_NUMBER = ? ");
      params.add(condition.getMobileTel());
    }
    //Add by V10-CH end
    if (StringUtil.hasValue(condition.getEmail())) {
      SqlFragment fragment = SqlDialect.getDefault()
          .createLikeClause("EMAIL", condition.getEmail(), LikeClauseOption.PARTIAL_MATCH);

      builder.append(" AND " + fragment.getFragment());
      for (Object o : fragment.getParameters()) {
        params.add(o);
      }
    }
    if (StringUtil.hasValue(condition.getShopStatus())) {
      // システム時間が開店日時～閉店日時の間かどうか
      // 開店日時と閉店日時は存在しなかったらシステム最小値と最大値に修正
//    	postgreSQL start
      //String shopOpenCondition = "TRUNC(" + SqlDialect.getDefault().getCurrentDatetime() + ")"
      String shopOpenCondition = SqlDialect.getDefault().getTrunc() + "(" + SqlDialect.getDefault().getCurrentDatetime() + ")"
//  	postgreSQL end      
          + " BETWEEN COALESCE (OPEN_DATETIME , ?) AND COALESCE (CLOSE_DATETIME , ?)";
      if (condition.getShopStatus().equals("0")) {
        // ショップ状態が開店
        builder.append(" AND " + shopOpenCondition);
        params.add(DateUtil.getMin());
        params.add(DateUtil.getMax());
      } else if (condition.getShopStatus().equals("1")) {
        // ショップ状態が閉店
        builder.append(" AND NOT " + shopOpenCondition);
        params.add(DateUtil.getMin());
        params.add(DateUtil.getMax());
      }
    }

    builder.append(" AND SHOP_TYPE = 1");
    builder.append(" ORDER BY SHOP_CODE ");
    this.setSqlString(builder.toString());
    this.setParameters(params.toArray());

    this.pageNumber = condition.getCurrentPage();
    this.pageSize = condition.getPageSize();

  }

  public int getMaxFetchSize() {
    return maxFetchSize;
  }

  public int getPageNumber() {
    return pageNumber;
  }

  public int getPageSize() {
    return pageSize;
  }

  public Class<Shop> getRowType() {
    return Shop.class;
  }

}
