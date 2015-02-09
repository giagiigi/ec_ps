package jp.co.sint.webshop.service.customer;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.SimpleQuery;
import jp.co.sint.webshop.utility.SqlDialect;
import jp.co.sint.webshop.utility.SqlFragment;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.utility.SqlDialect.LikeClauseOption;

public class CustomerExportQuery extends SimpleQuery {

  /** serial version UID */
  private static final long serialVersionUID = 1L;

  public CustomerExportQuery(CustomerSearchCondition condition) {
    List<Object> params = new ArrayList<Object>();
    StringBuilder builder = new StringBuilder();
    builder.append(" SELECT ");
    builder.append("   CUSTOMER_GROUP.CUSTOMER_GROUP_NAME, ");
    builder.append("   CUSTOMER.CUSTOMER_CODE, ");
//  delete by lc 2012-03-12 start
//    builder.append("   CUSTOMER.CUSTOMER_GROUP_CODE, ");
    builder.append("   CUSTOMER.LAST_NAME, ");
//    // delete by V10-CH 170 start
//    // builder.append(" CUSTOMER.FIRST_NAME, ");
//    // builder.append(" CUSTOMER.LAST_NAME_KANA, ");
//    // builder.append(" CUSTOMER.FIRST_NAME_KANA, ");
//    // delete by V10-CH 170 end
//    builder.append("   CUSTOMER.LOGIN_ID, ");
    builder.append("   CUSTOMER.EMAIL, ");
    builder.append("   CUSTOMER_ADDRESS.PHONE_NUMBER, ");
    builder.append("   CUSTOMER_ADDRESS.MOBILE_NUMBER, ");
//    builder.append("   CUSTOMER.PASSWORD, ");
      builder.append("   CUSTOMER.BIRTH_DATE, ");
//      builder.append("   CUSTOMER.SEX, ");
      builder.append("   CASE WHEN CUSTOMER.SEX = 1 THEN '男'  ELSE '女' END SEX, ");
      builder.append("   CUSTOMER_ADDRESS.ADDRESS1 || ' ' || CUSTOMER_ADDRESS.ADDRESS2 || ' ' || CUSTOMER_ADDRESS.ADDRESS3 || ' ' || CUSTOMER_ADDRESS.ADDRESS4 AS ADDRESS, ");
      builder.append("   CUSTOMER.LANGUAGE_CODE, ");
      builder.append("   CASE WHEN CUSTOMER.REQUEST_MAIL_TYPE = 0 THEN '否'  ELSE '是' END AS REQUEST_MAIL_TYPE  ");
//    builder.append("   CUSTOMER.CLIENT_MAIL_TYPE, ");
//    builder.append("   CUSTOMER.CAUTION, ");
//    builder.append("   CUSTOMER.LOGIN_DATETIME, ");
//    builder.append("   CUSTOMER.LOGIN_ERROR_COUNT, ");
//    builder.append("   CUSTOMER.LOGIN_LOCKED_FLG, ");
//    builder.append("   CUSTOMER.CUSTOMER_STATUS, ");
//    builder.append("   CUSTOMER.CUSTOMER_ATTRIBUTE_REPLY_DATE, ");
//    builder.append("   CUSTOMER.LATEST_POINT_ACQUIRED_DATE, ");
//    // modify by V10-CH start
//    // modified by zhanghaibin start 2010-05-24
//    // builder.append("
//    // CUSTOMER.REST_POINT/").append(DIContainer.getWebshopConfig().getPointMultiple()).append(",
//    // ");
//    // builder.append("
//    // CUSTOMER.TEMPORARY_POINT/").append(DIContainer.getWebshopConfig().getPointMultiple()).append(",
//    // ");
//    // modified by zhanghaibin end 2010-05-24
//    builder.append("trim(to_char(trunc(CUSTOMER.REST_POINT,").append(PointUtil.getAcquiredPointScale()).append("),'").append(
//        PointAmplificationRate.fromValue(String.valueOf(PointUtil.getAcquiredPointScale())).getName()).append("')) AS REST_POINT,");
//    builder.append("trim(to_char(trunc(CUSTOMER.TEMPORARY_POINT,").append(PointUtil.getAcquiredPointScale()).append("),'").append(
//        PointAmplificationRate.fromValue(String.valueOf(PointUtil.getAcquiredPointScale())).getName()).append(
//        "')) AS TEMPORARY_POINT,");
//    // modify by V10-CH end
//    builder.append("   CUSTOMER.WITHDRAWAL_REQUEST_DATE, ");
//    builder.append("   CUSTOMER.WITHDRAWAL_DATE, ");
//    builder.append("   CUSTOMER_ADDRESS.ADDRESS_ALIAS, ");
//    builder.append("   CUSTOMER_ADDRESS.POSTAL_CODE, ");
//    builder.append("   CUSTOMER_ADDRESS.PREFECTURE_CODE, ");
//    // add by V10-CH 170 start
//    builder.append("   CUSTOMER_ADDRESS.CITY_CODE, ");
//    // add by V10-CH 170 end
//    builder.append("   CUSTOMER_ADDRESS.ADDRESS1, ");
//    builder.append("   CUSTOMER_ADDRESS.ADDRESS2, ");
//    builder.append("   CUSTOMER_ADDRESS.ADDRESS3, ");
//    // delete by V10-CH 170 start
//    // builder.append(" CUSTOMER_ADDRESS.ADDRESS4, ");
//    // delete by V10-CH 170 end
//    builder.append("   CUSTOMER.ORM_ROWID, ");
//    builder.append("   CUSTOMER.CREATED_USER, ");
//    builder.append("   CUSTOMER.CREATED_DATETIME, ");
//    builder.append("   CUSTOMER.UPDATED_USER, ");
//    builder.append("   CUSTOMER.UPDATED_DATETIME ");
//  delete by lc 2012-03-12 end
    builder.append(" FROM ");
    builder.append("   CUSTOMER ");
    builder.append(" LEFT JOIN ");
    builder.append("   CUSTOMER_ADDRESS ");
    builder.append(" ON ");
    builder.append("   CUSTOMER.CUSTOMER_CODE = CUSTOMER_ADDRESS.CUSTOMER_CODE ");
    builder.append(" AND CUSTOMER_ADDRESS.ADDRESS_NO = ? ");
    builder.append(" LEFT JOIN ");
    builder.append("   CUSTOMER_GROUP ");
    builder.append(" ON ");
    builder.append("   CUSTOMER.CUSTOMER_GROUP_CODE = CUSTOMER_GROUP.CUSTOMER_GROUP_CODE ");
    builder.append(" WHERE 1=1 ");

    // 本人アドレス
    // 10.1.3 10150 修正 ここから
    // params.add(CustomerConstant.SELFE_ADDRESS_NO);
    params.add(CustomerConstant.SELF_ADDRESS_NO);
    // 10.1.3 10150 修正 ここまで

    // 顧客コード
    SqlFragment customerCodeFragment = SqlDialect.getDefault().createRangeClause("CUSTOMER.CUSTOMER_CODE",
        condition.getSearchCustomerFrom(), condition.getSearchCustomerTo());
    if (StringUtil.hasValue(customerCodeFragment.getFragment())) {
      builder.append(" AND ");
      builder.append(customerCodeFragment.getFragment());

      for (Object o : customerCodeFragment.getParameters()) {
        params.add(o);
      }
    }

    // 顧客グループ
    if (StringUtil.hasValue(condition.getSearchCustomerGroupCode())) {
      builder.append(" AND CUSTOMER.CUSTOMER_GROUP_CODE = ? ");
      params.add(condition.getSearchCustomerGroupCode());
    }

    // 電話番号
    if (StringUtil.hasValue(condition.getSearchTel())) {
      builder.append(" AND REPLACE(CUSTOMER_ADDRESS.PHONE_NUMBER,'-','') = ? ");
      params.add(condition.getSearchTel());
    }
    
    // 電話番号
    if (StringUtil.hasValue(condition.getSearchMobile())) {
      builder.append(" AND CUSTOMER_ADDRESS.MOBILE_NUMBER = ? ");
      params.add(condition.getSearchMobile());
    }

    // 会員状態
    if (StringUtil.hasValue(condition.getSearchCustomerStatus())) {
      builder.append(" AND CUSTOMER.CUSTOMER_STATUS = ? ");
      params.add(condition.getSearchCustomerStatus());
    }

    // 顧客名
    if (StringUtil.hasValue(condition.getSearchCustomerName())) {
      SqlFragment customerNameFragment = SqlDialect.getDefault().createLikeClause(
          // postgreSQL start
          // "CONCAT(CUSTOMER.LAST_NAME, CUSTOMER.FIRST_NAME)",
          // condition.getSearchCustomerName(), LikeClauseOption.PARTIAL_MATCH);
          SqlDialect.getDefault().getConcat("CUSTOMER.LAST_NAME", "CUSTOMER.FIRST_NAME"), condition.getSearchCustomerName(),
          LikeClauseOption.PARTIAL_MATCH);
      // postgreSQL end
      builder.append(" AND ");
      builder.append(customerNameFragment.getFragment());
      for (Object o : customerNameFragment.getParameters()) {
        params.add(o);
      }
    }

    // 顧客名カナ
    if (StringUtil.hasValue(condition.getSearchCustomerNameKana())) {
      SqlFragment customerNameKanaFragment = SqlDialect.getDefault().createLikeClause(
          // postgreSQL start
          // "CONCAT(CUSTOMER.LAST_NAME_KANA, CUSTOMER.FIRST_NAME_KANA)",
          // condition.getSearchCustomerNameKana(),
          SqlDialect.getDefault().getConcat("CUSTOMER.LAST_NAME_KANA", "CUSTOMER.FIRST_NAME_KANA"),
          condition.getSearchCustomerNameKana(),
          // postgreSQL end
          LikeClauseOption.PARTIAL_MATCH);
      builder.append(" AND ");
      builder.append(customerNameKanaFragment.getFragment());
      for (Object o : customerNameKanaFragment.getParameters()) {
        params.add(o);
      }
    }

    // メールアドレス
    if (StringUtil.hasValue(condition.getSearchEmail())) {
      SqlFragment emailFragment = SqlDialect.getDefault().createLikeClause("CUSTOMER.EMAIL", condition.getSearchEmail(),
          LikeClauseOption.PARTIAL_MATCH);
      builder.append(" AND ");
      builder.append(emailFragment.getFragment());
      for (Object o : emailFragment.getParameters()) {
        params.add(o);
      }
    }

    // 情報メール
    if (StringUtil.hasValue(condition.getSearchRequestMailType())) {
      builder.append(" AND CUSTOMER.REQUEST_MAIL_TYPE = ? ");
      params.add(condition.getSearchRequestMailType());
    }

    // ログインロック
    if (StringUtil.hasValue(condition.getSearchLoginLockedFlg())) {
      builder.append(" AND CUSTOMER.LOGIN_LOCKED_FLG = ? ");
      params.add(condition.getSearchLoginLockedFlg());
    }

    // メール区分
    if (StringUtil.hasValue(condition.getSearchClientMailType())) {
      builder.append(" AND CUSTOMER.CLIENT_MAIL_TYPE = ? ");
      params.add(condition.getSearchClientMailType());
    }
    //会员区分
    if (StringUtil.hasValue(condition.getCustomerKbn())) {
      builder.append(" AND CUSTOMER.CUSTOMER_KBN = ?");
      params.add(condition.getCustomerKbn());
    }
    // 並び順
    builder.append(" ORDER BY CUSTOMER.CUSTOMER_CODE");

    setSqlString(builder.toString());
    setParameters(params.toArray());
  }

}
