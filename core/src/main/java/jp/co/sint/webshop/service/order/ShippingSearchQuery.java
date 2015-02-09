package jp.co.sint.webshop.service.order;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.co.sint.webshop.data.AbstractQuery;
import jp.co.sint.webshop.data.domain.ReturnItemType;
import jp.co.sint.webshop.data.domain.ShippingStatus;
import jp.co.sint.webshop.data.dto.ShippingHeader;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.SqlDialect;
import jp.co.sint.webshop.utility.SqlFragment;
import jp.co.sint.webshop.utility.StringUtil;

public class ShippingSearchQuery extends AbstractQuery<ShippingHeader> {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;

  // 出荷ステータス検索条件に存在しなかった時に使うデフォルト受注ステータス検索条件
  private String[] defaultShippingStatus = {
    "-1" // 1件も返さないように、存在しない"-1"をセット
  };

  public ShippingSearchQuery() {

  }

  public ShippingSearchQuery(ShippingListSearchCondition condition, String query) {

    StringBuilder builder = new StringBuilder();
    builder.append(query);
    builder.append(" WHERE 1 = 1 ");

    List<Object> params = new ArrayList<Object>();

    // 出荷番号
    if (StringUtil.hasValue(condition.getSearchShippingNo())) {
      builder.append(" AND SH.SHIPPING_NO = ? ");
      params.add(condition.getSearchShippingNo());
    }

    // 受注番号
    if (StringUtil.hasValue(condition.getSearchOrderNo())) {
      builder.append(" AND SH.ORDER_NO = ? ");
      params.add(condition.getSearchOrderNo());
    }

    // 顧客名
    if (StringUtil.hasValue(condition.getSearchCustomerName())) {
      List<String> searchWordList = StringUtil.getSearchWordStringList(condition.getSearchCustomerName());
      if (searchWordList != null) {
        for (String s : searchWordList) {
          builder.append(" AND ");
//        postgreSQL start
          //builder.append(SqlDialect.getDefault().getLikeClausePattern("CONCAT(OH.LAST_NAME, OH.FIRST_NAME)"));
          builder.append(SqlDialect.getDefault().getLikeClausePattern(SqlDialect.getDefault().getConcat("OH.LAST_NAME", "OH.FIRST_NAME")));
//        postgreSQL end
          params.add("%" + SqlDialect.getDefault().escape(s) + "%");
        }
      }
    }

    // 顧客名カナ
    if (StringUtil.hasValue(condition.getSearchCustomerNameKana())) {
      List<String> searchWordList = StringUtil.getSearchWordStringList(condition.getSearchCustomerNameKana());
      if (searchWordList != null) {
        for (String s : searchWordList) {
          builder.append(" AND ");
//        postgreSQL start
          //builder.append(SqlDialect.getDefault().getLikeClausePattern("CONCAT(OH.LAST_NAME_KANA, OH.FIRST_NAME_KANA)"));
          builder.append(SqlDialect.getDefault().getLikeClausePattern(SqlDialect.getDefault().getConcat("OH.LAST_NAME_KANA", "OH.FIRST_NAME_KANA")));
//        postgreSQL end
          params.add("%" + SqlDialect.getDefault().escape(s) + "%");
        }
      }
    }

    // 宛名
    if (StringUtil.hasValue(condition.getSearchAddressName())) {
      List<String> searchWordList = StringUtil.getSearchWordStringList(condition.getSearchAddressName());
      if (searchWordList != null) {
        for (String s : searchWordList) {
          builder.append(" AND ");
//        postgreSQL start
          //builder.append(SqlDialect.getDefault().getLikeClausePattern("CONCAT(SH.ADDRESS_LAST_NAME, SH.ADDRESS_FIRST_NAME)"));
          builder.append(SqlDialect.getDefault().getLikeClausePattern(SqlDialect.getDefault().getConcat("SH.ADDRESS_LAST_NAME", "SH.ADDRESS_FIRST_NAME")));
//        postgreSQL end
          params.add("%" + SqlDialect.getDefault().escape(s) + "%");
        }
      }
    }

    // 電話番号
    if (StringUtil.hasValue(condition.getSearchCustomerPhoneNumber())) {
      builder.append(" AND REPLACE(OH.PHONE_NUMBER,'-','') = ? ");
      params.add(condition.getSearchCustomerPhoneNumber());
    }

    //Add by V10-CH start
    if (StringUtil.hasValue(condition.getSearchCustomerMobileNumber())) {
      builder.append(" AND OH.MOBILE_NUMBER = ? ");
      params.add(condition.getSearchCustomerMobileNumber());
    }
    //Add by V10-CH end
    // ショップ
    if (StringUtil.hasValue(condition.getSearchShopCode())) {
      builder.append(" AND SH.SHOP_CODE = ? ");
      params.add(condition.getSearchShopCode());
    }

    // 配送種別
    if (StringUtil.hasValue(condition.getSearchShopCode()) && StringUtil.hasValue(condition.getSearchDeliveryTypeNo())) {
      builder.append(" AND SH.DELIVERY_TYPE_NO = ? ");
      params.add(condition.getSearchDeliveryTypeNo());
    }

    // 宅配便伝票番号
    if (StringUtil.hasValue(condition.getSearchDeliverySlipNo())) {
      builder.append(" AND SH.DELIVERY_SLIP_NO = ? ");
      params.add(condition.getSearchDeliverySlipNo());
    }

    // 出荷日
    if (DateUtil.isCorrect(condition.getSearchFromShippingDatetime())
        && DateUtil.isCorrect(condition.getSearchToShippingDatetime())) {
      builder.append(" AND SH.SHIPPING_DATE BETWEEN ? AND ?");
      params.add(condition.getSearchFromShippingDatetime());
      params.add(condition.getSearchToShippingDatetime());
    } else if (DateUtil.isCorrect(condition.getSearchFromShippingDatetime())) {
      builder.append(" AND SH.SHIPPING_DATE >= ?");
      params.add(condition.getSearchFromShippingDatetime());
    } else if (DateUtil.isCorrect(condition.getSearchToShippingDatetime())) {
      builder.append(" AND SH.SHIPPING_DATE <= ?");
      params.add(condition.getSearchToShippingDatetime());
    }

    // 出荷指示日
    if (DateUtil.isCorrect(condition.getSearchFromShippingDirectDate())
        && DateUtil.isCorrect(condition.getSearchToShippingDirectDate())) {
      builder.append(" AND SH.SHIPPING_DIRECT_DATE BETWEEN ? AND ?");
      params.add(condition.getSearchFromShippingDirectDate());
      params.add(condition.getSearchToShippingDirectDate());
    } else if (DateUtil.isCorrect(condition.getSearchFromShippingDirectDate())) {
      builder.append(" AND SH.SHIPPING_DIRECT_DATE >= ?");
      params.add(condition.getSearchFromShippingDirectDate());
    } else if (DateUtil.isCorrect(condition.getSearchToShippingDirectDate())) {
      builder.append(" AND SH.SHIPPING_DIRECT_DATE <= ?");
      params.add(condition.getSearchToShippingDirectDate());
    }

    Object[] shippingSearchStatus;
    if (condition.getSearchShippingStatus().size() > 0 && condition.getSearchShippingStatus().size() < 5) {
      shippingSearchStatus = condition.getSearchShippingStatus().toArray();
    } else {
      shippingSearchStatus = (Object[]) this.defaultShippingStatus;
    }
    SqlFragment f = SqlDialect.getDefault().createInClause("SH.SHIPPING_STATUS", shippingSearchStatus);
    builder.append(" AND " + f.getFragment());
    params.addAll(Arrays.asList(f.getParameters()));

    if (!condition.getSearchReturnItemType().equals(ReturnItemType.RETURNED.getValue())) {
      // 1の場合は返品データも含む
      builder.append(" AND SH.RETURN_ITEM_TYPE <> ? AND SH.SHIPPING_STATUS <> ? ");
      params.add(ReturnItemType.RETURNED.getValue());
      params.add(ShippingStatus.CANCELLED.getValue());
    }

    setSqlString(builder.toString());
    setParameters(params.toArray());
    setPageNumber(condition.getCurrentPage());
    setPageSize(condition.getPageSize());
    setMaxFetchSize(condition.getMaxFetchSize());
  }

  public Class<ShippingHeader> getRowType() {
    return ShippingHeader.class;
  }
}
