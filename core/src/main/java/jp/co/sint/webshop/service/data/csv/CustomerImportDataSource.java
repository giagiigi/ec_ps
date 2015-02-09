package jp.co.sint.webshop.service.data.csv;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.co.sint.webshop.code.SequenceType;
import jp.co.sint.webshop.data.DataAccessException;
import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.SimpleQuery;
import jp.co.sint.webshop.data.csv.CsvImportException;
import jp.co.sint.webshop.data.csv.CsvUtil;
import jp.co.sint.webshop.data.csv.sql.SqlImportDataSource;
//import jp.co.sint.webshop.data.dao.PointRuleDao; // 10.1.7 10299 削除
import jp.co.sint.webshop.data.domain.CustomerStatus;
//import jp.co.sint.webshop.data.domain.PointFunctionEnabledFlg; // 10.1.7 10299 削除
import jp.co.sint.webshop.data.domain.PrefectureCode;
//import jp.co.sint.webshop.data.dto.PointRule; // 10.1.7 10299 削除
import jp.co.sint.webshop.message.CsvMessage;
import jp.co.sint.webshop.message.Message;
import jp.co.sint.webshop.service.customer.CustomerConstant;
import jp.co.sint.webshop.service.customer.InputCustomer;
import jp.co.sint.webshop.text.Messages;
import jp.co.sint.webshop.utility.ArrayUtil;
import jp.co.sint.webshop.utility.BigDecimalUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.PasswordPolicy;
import jp.co.sint.webshop.utility.PasswordUtil;
import jp.co.sint.webshop.utility.SqlDialect;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.validation.BeanValidator;
import jp.co.sint.webshop.validation.ValidationResult;
import jp.co.sint.webshop.validation.ValidationSummary;

import org.apache.log4j.Logger;

public class CustomerImportDataSource extends SqlImportDataSource<CustomerCsvSchema, CustomerImportCondition> {

  private PreparedStatement customerSelectStatement = null;

  private PreparedStatement customerInsertStatement = null;

  private PreparedStatement customerAddressInsertStatement = null;

  private PreparedStatement customerUpdateStatement = null;

  private PreparedStatement customerAddressUpdateStatement = null;

  private InputCustomer customer = null;

  @Override
  protected void initializeResources() {

    // 10.1.7 10299 削除 ここから
    // PointRuleDao dao = DIContainer.getDao(PointRuleDao.class);
    // PointRule pointRule = dao.loadAll().get(0);
    // String firstPoint = "0";
    // if (pointRule.getCustomerRegisterPoint() != null) {
    //   firstPoint = NumUtil.toString(pointRule.getCustomerRegisterPoint());
    // }
    // 10.1.7 10299 削除 ここまで

    final String customerSelectQuery = "SELECT COUNT(*) FROM CUSTOMER WHERE CUSTOMER_CODE = ? ";
    // 10.1.7 10299 削除 ここから
    // final String customerInsertWithPointQuery = "INSERT INTO CUSTOMER(CUSTOMER_CODE, CUSTOMER_GROUP_CODE, LAST_NAME, FIRST_NAME, "
    //     + "LAST_NAME_KANA, FIRST_NAME_KANA, LOGIN_ID, EMAIL, PASSWORD, BIRTH_DATE, SEX, REQUEST_MAIL_TYPE, "
    //     + "CLIENT_MAIL_TYPE, CAUTION, LOGIN_ERROR_COUNT, LOGIN_LOCKED_FLG, CUSTOMER_STATUS, "
    //     + "REST_POINT, WITHDRAWAL_REQUEST_DATE, WITHDRAWAL_DATE, "
    //     + "ORM_ROWID, CREATED_USER, CREATED_DATETIME, UPDATED_USER, UPDATED_DATETIME) "
    //     + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?," + firstPoint + ", ?, ?, CUSTOMER_SEQ.NEXTVAL, ?, ?, ? ,?)";
    // 10.1.7 10299 削除 ここまで
    final String customerInsertQuery = "INSERT INTO CUSTOMER(CUSTOMER_CODE, CUSTOMER_GROUP_CODE, LAST_NAME, FIRST_NAME, "
        + "LAST_NAME_KANA, FIRST_NAME_KANA, LOGIN_ID, EMAIL, PASSWORD, BIRTH_DATE, SEX, REQUEST_MAIL_TYPE, "
        + "CLIENT_MAIL_TYPE, CAUTION, LOGIN_ERROR_COUNT, LOGIN_LOCKED_FLG, CUSTOMER_STATUS, "
        + "REST_POINT, WITHDRAWAL_REQUEST_DATE, WITHDRAWAL_DATE, "
        + "ORM_ROWID, CREATED_USER, CREATED_DATETIME, UPDATED_USER, UPDATED_DATETIME) "
//      postgreSQL start        
        //+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 0, ?, ?, CUSTOMER_SEQ.NEXTVAL, ?, ?, ? ,?)";
        + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 0, ?, ?, "+SqlDialect.getDefault().getNextvalNOprm("CUSTOMER_SEQ")+", ?, ?, ? ,?)";
//  postgreSQL end   
    final String customerAddressInsertQuery = "INSERT INTO CUSTOMER_ADDRESS(CUSTOMER_CODE, ADDRESS_NO, ADDRESS_ALIAS, "
        + "ADDRESS_LAST_NAME, ADDRESS_FIRST_NAME, ADDRESS_LAST_NAME_KANA, ADDRESS_FIRST_NAME_KANA, POSTAL_CODE, "
        //modify by V10-CH 170 start
//        + "PREFECTURE_CODE, ADDRESS1, ADDRESS2, ADDRESS3, ADDRESS4, PHONE_NUMBER, "
        + "PREFECTURE_CODE, CITY_CODE, ADDRESS1, ADDRESS2, ADDRESS3, ADDRESS4, PHONE_NUMBER, MOBILE_NUMBER, "
        //modify by V10-CH 170 end
        + "ORM_ROWID, CREATED_USER, CREATED_DATETIME, UPDATED_USER, UPDATED_DATETIME) "
//      postgreSQL start        
        //+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, CUSTOMER_ADDRESS_SEQ.NEXTVAL, ?, ?, ? ,?)";
        + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "+SqlDialect.getDefault().getNextvalNOprm("CUSTOMER_ADDRESS_SEQ")+", ?, ?, ? ,?)";
//  postgreSQL end   
    final String customerUpdateQuery = "UPDATE CUSTOMER SET CUSTOMER_GROUP_CODE = ?, LAST_NAME = ?, FIRST_NAME = ?, "
        + "LAST_NAME_KANA = ?, FIRST_NAME_KANA = ?, LOGIN_ID = ?, EMAIL = ?, BIRTH_DATE = ?, SEX = ?, "
        + "REQUEST_MAIL_TYPE = ?, CLIENT_MAIL_TYPE = ?, CAUTION = ?, LOGIN_ERROR_COUNT = ?, LOGIN_LOCKED_FLG = ?, "
        + "CUSTOMER_STATUS = ?, WITHDRAWAL_REQUEST_DATE = ?, WITHDRAWAL_DATE = ?, UPDATED_USER = ?, UPDATED_DATETIME = ? "
        + "WHERE CUSTOMER_CODE = ?";
    final String customerAddressUpdateQuery = "UPDATE CUSTOMER_ADDRESS SET ADDRESS_ALIAS = ?, ADDRESS_LAST_NAME = ?, "
        + "ADDRESS_FIRST_NAME = ?, ADDRESS_LAST_NAME_KANA = ?, ADDRESS_FIRST_NAME_KANA = ?, POSTAL_CODE = ?, "
        //modify by V10-CH 170 start
        //+ "PREFECTURE_CODE = ?, ADDRESS1 = ?, ADDRESS2 = ?, ADDRESS3 = ?, ADDRESS4 = ?, PHONE_NUMBER = ?, "
        + "PREFECTURE_CODE = ?,CITY_CODE = ?, ADDRESS1 = ?, ADDRESS2 = ?, ADDRESS3 = ?, ADDRESS4 = ?, PHONE_NUMBER = ?, MOBILE_NUMBER = ?, "
        //modify by V10-CH 170 end
        + "UPDATED_USER = ?, UPDATED_DATETIME = ? WHERE CUSTOMER_CODE = ? AND ADDRESS_NO = ? ";

    try {
      customerSelectStatement = createPreparedStatement(customerSelectQuery);
      // 10.1.7 10299 修正 ここから
      // if (pointRule.getPointFunctionEnabledFlg().equals(PointFunctionEnabledFlg.ENABLED.longValue())) {
      //   customerInsertStatement = createPreparedStatement(customerInsertWithPointQuery);
      // } else {
      //   customerInsertStatement = createPreparedStatement(customerInsertQuery);
      // }
      customerInsertStatement = createPreparedStatement(customerInsertQuery);
      // 10.1.7 10299 修正 ここまで
      customerAddressInsertStatement = createPreparedStatement(customerAddressInsertQuery);
      customerUpdateStatement = createPreparedStatement(customerUpdateQuery);
      customerAddressUpdateStatement = createPreparedStatement(customerAddressUpdateQuery);
    } catch (Exception e) {
      throw new DataAccessException(e);
    }
  }

  public ValidationSummary validate(List<String> csvLine) {
    ValidationSummary summary = new ValidationSummary();

    try {
      customer = CsvUtil.createBeanFromCsvLine(csvLine, getSchema(), InputCustomer.class);
    } catch (Exception e) {
      summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.WRONG_VALUE)));
      return summary;
    }
    customer.setOrmRowid(DatabaseUtil.DEFAULT_ORM_ROWID);

    // 配送先呼称
    customer.setAddressAlias(Messages.getString("service.data.csv.CustomerImportDataSource.0"));

    // ログインID
    customer.setLoginId(customer.getEmail());

    // 都道府県名

    if (StringUtil.isNullOrEmpty(customer.getAddress1()) && StringUtil.hasValue(customer.getPrefectureCode())) {
      PrefectureCode prefecture = PrefectureCode.fromValue(customer.getPrefectureCode());
      customer.setAddress1(prefecture.getName());
    }

    // 単項目バリデーションチェック
    summary.getErrors().addAll(BeanValidator.validate(customer).getErrors());
    // 10.1.1 10019 追加 ここから
    checkMinusNumber(summary);
    // 10.1.1 10019 追加 ここまで
    if (summary.hasError()) {
      return summary;
    }

    // 顧客グループデータ存在チェック

    SimpleQuery customerGroupCountQuery = new SimpleQuery("SELECT COUNT(*) FROM CUSTOMER_GROUP WHERE CUSTOMER_GROUP_CODE = ? ");
    customerGroupCountQuery.setParameters(customer.getCustomerGroupCode());
    Long customerGroupCount = Long.valueOf(executeScalar(customerGroupCountQuery).toString());
    if (customerGroupCount == 0L) {
      summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.NOT_EXIST,
          Messages.getString("service.data.csv.CustomerImportDataSource.1"))));
    }

    // メールアドレス重複チェック

    SimpleQuery customerMailCountQuery = null;
    if (customer.getCustomerCode() == null) {
      customerMailCountQuery = new SimpleQuery("SELECT COUNT(*) FROM CUSTOMER WHERE EMAIL = ? ");
      customerMailCountQuery.setParameters(customer.getEmail());
    } else {
      customerMailCountQuery = new SimpleQuery("SELECT COUNT(*) FROM CUSTOMER WHERE EMAIL = ? AND CUSTOMER_CODE <> ? ");
      customerMailCountQuery.setParameters(customer.getEmail(), customer.getCustomerCode());
    }
    Long customerMailCount = Long.valueOf(executeScalar(customerMailCountQuery).toString());
    if (customerMailCount > 0) {
      summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.DUPLICATED_MAIL)));
    }

    // ログインID重複チェック
    // 将来的にメールアドレスとログインIDを別々に使用する可能性もあるので、ログインID重複チェックを別に実行

    SimpleQuery customerLoginIdCountQuery = null;
    if (customer.getCustomerCode() == null) {
      customerLoginIdCountQuery = new SimpleQuery("SELECT COUNT(*) FROM CUSTOMER WHERE LOGIN_ID = ? ");
      customerLoginIdCountQuery.setParameters(customer.getEmail());
    } else {
      customerLoginIdCountQuery = new SimpleQuery("SELECT COUNT(*) FROM CUSTOMER WHERE LOGIN_ID = ? AND CUSTOMER_CODE <> ? ");
      customerLoginIdCountQuery.setParameters(customer.getEmail(), customer.getCustomerCode());
    }
    Long customerLoginIdCount = Long.valueOf(executeScalar(customerLoginIdCountQuery).toString());
    if (customerLoginIdCount > 0) {
      summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.DUPLICATED_LOGINID)));
    }

    // 生年月日正当性チェック(年が現在日付より100年以内かどうかのチェック)
    Long birthYear = Long.valueOf(DateUtil.getYYYY(customer.getBirthDate()));
    Long sysYear = Long.valueOf(DateUtil.getYYYY(DateUtil.getSysdate())) - 100L;
    if (sysYear > birthYear) {
      summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.BIRTHDAY)));
    }

    // 取込時、パスワードポリシーチェック
    if (!exists(customerSelectStatement, customer.getCustomerCode())) {
      PasswordPolicy policy = DIContainer.get("FrontPasswordPolicy");
      if (!policy.isValidPassword(customer.getPassword())) {
        summary.getErrors().add(new ValidationResult(null, null, policy.getMessage()));
      }
    }

    // 顧客ステータスチェック(通常のみ可)
    if (!customer.getCustomerStatus().equals(CustomerStatus.MEMBER.longValue())) {
      summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.WITHDRAWED)));
    }

    // 都道府県コード・住所1整合性チェック

    PrefectureCode prefecture = PrefectureCode.fromValue(customer.getPrefectureCode());
    if (prefecture == null || !customer.getAddress1().equals(prefecture.getName())) {
      summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.PREFECTURE_MISMATCH)));
    }
    if(StringUtil.isNullOrEmpty(customer.getPhoneNumber()) && StringUtil.isNullOrEmpty(customer.getMobileNumber())){
      summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.NO_NUMBER)));
    }else{
      String  phone []={};
      if(StringUtil.hasValue(customer.getPhoneNumber())){
        phone = customer.getPhoneNumber().split("-");
      }
      String phone1 = "";
      String phone2 = "";
      String phone3 = "";
      if(phone.length == 1){
        summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.FALSE_PHONE)));
      }else if(phone.length == 2){
        phone1 = phone[0];
        phone2 = phone[1];
      }else if(phone.length == 3){
        phone1 = phone[0];
        phone2 = phone[1];
        phone3 = phone[2];
      }
      if(StringUtil.hasValue(phone1) || StringUtil.hasValue(phone2) || StringUtil.hasValue(phone3)){
        if(!(StringUtil.hasValueAllOf(phone1,phone2) && phone1.length()>1 && phone1.length()<5 && phone2.length()<9 && phone2.length()>5 && phone3.length()<7)){
          summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.FALSE_PHONE)));
        }
      }
      if((StringUtil.hasValue(customer.getMobileNumber()) && (customer.getMobileNumber().length() != 11))){
        summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.FALSE_MOBILE)));
      }
    }
    

    return summary;
  }

  @Override
  public void executeUpdate(List<String> csvLine) throws SQLException {
    Logger logger = Logger.getLogger("tracelog");
    try {
      if (exists(customerSelectStatement, customer.getCustomerCode())) {
        List<Object> customerParams = new ArrayList<Object>();

        customerParams.add(customer.getCustomerGroupCode());
        customerParams.add(customer.getLastName());
        customerParams.add(customer.getFirstName());
        customerParams.add(customer.getLastNameKana());
        customerParams.add(customer.getFirstNameKana());
        customerParams.add(customer.getEmail());
        customerParams.add(customer.getEmail());
        customerParams.add(customer.getBirthDate());
        customerParams.add(customer.getSex());
        customerParams.add(customer.getRequestMailType());
        customerParams.add(customer.getClientMailType());
        customerParams.add(customer.getCaution());
        customerParams.add(customer.getLoginErrorCount());
        customerParams.add(customer.getLoginLockedFlg());
        customerParams.add(customer.getCustomerStatus());
        customerParams.add(customer.getWithdrawalRequestDate());
        customerParams.add(customer.getWithdrawalDate());
        customerParams.add(getCondition().getLoginInfo().getRecordingFormat());
        customerParams.add(DateUtil.getSysdate());
        customerParams.add(customer.getCustomerCode());

        logger.debug("UPDATE Parameters: " + Arrays.toString(ArrayUtil.toArray(customerParams, Object.class)));

        DatabaseUtil.bindParameters(customerUpdateStatement, ArrayUtil.toArray(customerParams, Object.class));

        int updateCustomerCount = customerUpdateStatement.executeUpdate();
        if (updateCustomerCount != 1) {
          throw new CsvImportException();
        }

        List<Object> customerAddressParams = new ArrayList<Object>();

        customerAddressParams.add(customer.getAddressAlias());
        customerAddressParams.add(customer.getLastName());
        customerAddressParams.add(customer.getFirstName());
        customerAddressParams.add(customer.getLastNameKana());
        customerAddressParams.add(customer.getFirstNameKana());
        customerAddressParams.add(customer.getPostalCode());
        customerAddressParams.add(customer.getPrefectureCode());
        //add by V10-CH 170 start
        customerAddressParams.add(customer.getCityCode());
        //add by V10-CH 170 end
        customerAddressParams.add(customer.getAddress1());
        customerAddressParams.add(customer.getAddress2());
        customerAddressParams.add(customer.getAddress3());
        customerAddressParams.add(customer.getAddress4());
        if(StringUtil.isNullOrEmpty(customer.getPhoneNumber())){
          customerAddressParams.add("");
        }else{
          customerAddressParams.add(customer.getPhoneNumber());
        }
        //Add by V10-CH start
        customerAddressParams.add(customer.getMobileNumber());
        //Add by V10-CH end
        customerAddressParams.add(getCondition().getLoginInfo().getRecordingFormat());
        customerAddressParams.add(DateUtil.getSysdate());

        customerAddressParams.add(customer.getCustomerCode());
        // 10.1.3 10150 修正 ここから
        // customerAddressParams.add(CustomerConstant.SELFE_ADDRESS_NO);
        customerAddressParams.add(CustomerConstant.SELF_ADDRESS_NO);
        // 10.1.3 10150 修正 ここまで

        logger.debug("UPDATE Parameters: " + Arrays.toString(ArrayUtil.toArray(customerAddressParams, Object.class)));

        DatabaseUtil.bindParameters(customerAddressUpdateStatement, ArrayUtil.toArray(customerAddressParams, Object.class));

        int updateCustomerAddressCount = customerAddressUpdateStatement.executeUpdate();
        if (updateCustomerAddressCount != 1) {
          throw new CsvImportException();
        }
      } else {
        List<Object> customerParams = new ArrayList<Object>();

        NumberFormat nf = new DecimalFormat("0000000000000000");
        String customerCode = nf.format(generateSequence(SequenceType.CUSTOMER_CODE));

        customerParams.add(customerCode);
        customerParams.add(customer.getCustomerGroupCode());
        customerParams.add(customer.getLastName());
        customerParams.add(customer.getFirstName());
        customerParams.add(customer.getLastNameKana());
        customerParams.add(customer.getFirstNameKana());
        customerParams.add(customer.getEmail());
        customerParams.add(customer.getEmail());
        if (DIContainer.getWebshopConfig().isUseCustomerPassEncrypt()) {
          customerParams.add(PasswordUtil.getDigest(customer.getPassword()));
        } else {
          customerParams.add(customer.getPassword());
        }
        customerParams.add(customer.getBirthDate());
        customerParams.add(customer.getSex());
        customerParams.add(customer.getRequestMailType());
        customerParams.add(customer.getClientMailType());
        customerParams.add(customer.getCaution());
        customerParams.add(customer.getLoginErrorCount());
        customerParams.add(customer.getLoginLockedFlg());
        customerParams.add(customer.getCustomerStatus());
        customerParams.add(customer.getWithdrawalRequestDate());
        customerParams.add(customer.getWithdrawalDate());

        customerParams.add(getCondition().getLoginInfo().getRecordingFormat());
        customerParams.add(DateUtil.getSysdate());
        customerParams.add(getCondition().getLoginInfo().getRecordingFormat());
        customerParams.add(DateUtil.getSysdate());

        logger.debug("INSERT Parameters: " + Arrays.toString(ArrayUtil.toArray(customerParams, Object.class)));

        DatabaseUtil.bindParameters(customerInsertStatement, ArrayUtil.toArray(customerParams, Object.class));

        int insertCustomerCount = customerInsertStatement.executeUpdate();
        if (insertCustomerCount != 1) {
          throw new CsvImportException();
        }

        List<Object> customerAddressParams = new ArrayList<Object>();

        customerAddressParams.add(customerCode);
        // 10.1.3 10150 修正 ここから
        // customerAddressParams.add(CustomerConstant.SELFE_ADDRESS_NO);
        customerAddressParams.add(CustomerConstant.SELF_ADDRESS_NO);
        // 10.1.3 10150 修正 ここまで
        customerAddressParams.add(customer.getAddressAlias());
        customerAddressParams.add(customer.getLastName());
        customerAddressParams.add(customer.getFirstName());
        customerAddressParams.add(customer.getLastNameKana());
        customerAddressParams.add(customer.getFirstNameKana());
        customerAddressParams.add(customer.getPostalCode());
        customerAddressParams.add(customer.getPrefectureCode());
        //add by V10-CH 170 start
        customerAddressParams.add(customer.getCityCode());
        //add by V10-CH 170 end
        customerAddressParams.add(customer.getAddress1());
        customerAddressParams.add(customer.getAddress2());
        customerAddressParams.add(customer.getAddress3());
        customerAddressParams.add(customer.getAddress4());
        if(StringUtil.isNullOrEmpty(customer.getPhoneNumber())){
          customerAddressParams.add("");
        }else{
          customerAddressParams.add(customer.getPhoneNumber());
        }
        //Add by V10-CH start
        customerAddressParams.add(customer.getMobileNumber());
        //Add by V10-CH end
        customerAddressParams.add(getCondition().getLoginInfo().getRecordingFormat());
        customerAddressParams.add(DateUtil.getSysdate());
        customerAddressParams.add(getCondition().getLoginInfo().getRecordingFormat());
        customerAddressParams.add(DateUtil.getSysdate());

        logger.debug("INSERT Parameters: " + Arrays.toString(ArrayUtil.toArray(customerAddressParams, Object.class)));

        DatabaseUtil.bindParameters(customerAddressInsertStatement, ArrayUtil.toArray(customerAddressParams, Object.class));

        int insertCustomerAddressCount = customerAddressInsertStatement.executeUpdate();
        if (insertCustomerAddressCount != 1) {
          throw new CsvImportException();
        }

      }

    } catch (SQLException e) {
      throw new CsvImportException(e);
    } catch (CsvImportException e) {
      throw e;
    } catch (RuntimeException e) {
      throw new CsvImportException(e);
    }

  }

  // 10.1.1 10019 追加 ここから
  private void checkMinusNumber(ValidationSummary summary) {
    if (NumUtil.isNegative(customer.getLoginErrorCount())) {
      summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.MINUS_NUMBER_ERROR,
          Messages.getString("service.data.csv.CustomerImportDataSource.2"))));
    }
    // 10.1.7 10299 削除 ここから
    // if (NumUtil.isNegative(customer.getRestPoint())) {
    //   summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.MINUS_NUMBER_ERROR, "ポイント残高")));
    // }
    // if (NumUtil.isNegative(customer.getTemporaryPoint())) {
    //   summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.MINUS_NUMBER_ERROR, "仮発行ポイント")));
    // }
    // 10.1.7 10299 削除 ここまで
  }
  // 10.1.1 10019 追加 ここまで

}
