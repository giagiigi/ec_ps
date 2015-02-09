package jp.co.sint.webshop.service.customer;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.SimpleQuery;
import jp.co.sint.webshop.utility.SqlDialect;
import jp.co.sint.webshop.utility.SqlFragment;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.utility.SqlDialect.LikeClauseOption;

public class CustomerDMExportQuery extends SimpleQuery {

  private static final long serialVersionUID = 1L;

  public CustomerDMExportQuery(CustomerSearchCondition condition) {
    List<Object> params = new ArrayList<Object>();

    StringBuilder builder = new StringBuilder();
    builder.append(" SELECT ");
    builder.append("   CUSTOMER_ADDRESS.ADDRESS_LAST_NAME, ");
    //delete by V10-CH 170 start
    //builder.append("   CUSTOMER_ADDRESS.ADDRESS_FIRST_NAME, ");
    //delete by V10-CH 170 end
    builder.append("   CUSTOMER_ADDRESS.POSTAL_CODE, ");
    builder.append("   CUSTOMER_ADDRESS.ADDRESS1, ");
    builder.append("   CUSTOMER_ADDRESS.ADDRESS2, ");
    builder.append("   CUSTOMER_ADDRESS.ADDRESS3, ");
    //  delete by V10-CH 170 start
    builder.append("   CUSTOMER_ADDRESS.ADDRESS4, ");
    //  delete by V10-CH 170 end
    builder.append("   CUSTOMER_ADDRESS.PHONE_NUMBER, ");
    builder.append("   CUSTOMER_ADDRESS.MOBILE_NUMBER, ");
    builder.append("   CUSTOMER_ADDRESS.ORM_ROWID, ");
    builder.append("   CUSTOMER_ADDRESS.CREATED_USER, ");
    builder.append("   CUSTOMER_ADDRESS.CREATED_DATETIME, ");
    builder.append("   CUSTOMER_ADDRESS.UPDATED_USER, ");
    builder.append("   CUSTOMER_ADDRESS.UPDATED_DATETIME ");
    builder.append(" FROM ");
    builder.append("   CUSTOMER ");
    builder.append(" INNER JOIN ");
    builder.append("   CUSTOMER_ADDRESS ");
    builder.append(" ON ");
    builder.append("   CUSTOMER.CUSTOMER_CODE = CUSTOMER_ADDRESS.CUSTOMER_CODE ");
    builder.append(" WHERE ");
    builder.append("   CUSTOMER_ADDRESS.ADDRESS_NO = ? ");

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
    // 携帯番号
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
//    		postgreSQL start
    		  //"CONCAT(CUSTOMER.LAST_NAME, CUSTOMER.FIRST_NAME)", condition.getSearchCustomerName(), LikeClauseOption.PARTIAL_MATCH);
    		  SqlDialect.getDefault().getConcat("CUSTOMER.LAST_NAME", "CUSTOMER.FIRST_NAME"), condition.getSearchCustomerName(), LikeClauseOption.PARTIAL_MATCH);
//		postgreSQL end      
      builder.append(" AND ");
      builder.append(customerNameFragment.getFragment());
      for (Object o : customerNameFragment.getParameters()) {
        params.add(o);
      }
    }

    // 顧客名カナ
    if (StringUtil.hasValue(condition.getSearchCustomerNameKana())) {
      SqlFragment customerNameKanaFragment = SqlDialect.getDefault().createLikeClause(
//    		postgreSQL start    		  
    		  //"CONCAT(CUSTOMER.LAST_NAME_KANA, CUSTOMER.FIRST_NAME_KANA)", condition.getSearchCustomerNameKana(),
    		  SqlDialect.getDefault().getConcat("CUSTOMER.LAST_NAME_KANA", "CUSTOMER.FIRST_NAME_KANA"), condition.getSearchCustomerNameKana(),
//      		postgreSQL end    		  
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

    // 並び順
    builder.append(" ORDER BY CUSTOMER.CUSTOMER_CODE");

    setSqlString(builder.toString());
    setParameters(params.toArray());
  }

}
