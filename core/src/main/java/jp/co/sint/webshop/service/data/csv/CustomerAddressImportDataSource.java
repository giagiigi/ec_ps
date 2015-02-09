package jp.co.sint.webshop.service.data.csv;

import java.sql.PreparedStatement;
import java.sql.SQLException;
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
import jp.co.sint.webshop.data.domain.CustomerStatus;
import jp.co.sint.webshop.data.domain.PrefectureCode;
import jp.co.sint.webshop.data.dto.CustomerAddress;
import jp.co.sint.webshop.message.CsvMessage;
import jp.co.sint.webshop.message.Message;
import jp.co.sint.webshop.service.customer.CustomerConstant;
import jp.co.sint.webshop.text.Messages;
import jp.co.sint.webshop.utility.ArrayUtil;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.SqlDialect;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.validation.BeanValidator;
import jp.co.sint.webshop.validation.ValidationResult;
import jp.co.sint.webshop.validation.ValidationSummary;

import org.apache.log4j.Logger;

public class CustomerAddressImportDataSource extends SqlImportDataSource<CustomerAddressCsvSchema, CustomerAddressImportCondition> {

  private PreparedStatement insertStatement = null;

  private PreparedStatement updateStatement = null;

  private PreparedStatement updateCustomerStatement = null;

  private PreparedStatement selectStatement = null;

  private CustomerAddress customerAddress = null;

  @Override
  protected void initializeResources() {
    Logger logger = Logger.getLogger(this.getClass());
    //  modify by V10-CH 170 start
    String insertQuery = //CsvUtil.buildInsertQuery(getSchema());
    " INSERT INTO customer_address( "
     + " customer_code, address_no, address_alias, address_last_name,  "
     + "    address_first_name, address_last_name_kana, address_first_name_kana, " 
     + "   postal_code, prefecture_code, city_code, address1, address2, address3, address4, " 
     + " phone_number, mobile_number, orm_rowid, created_user, created_datetime, updated_user,  "
     + " updated_datetime) "
     + " VALUES (?, ?, ?, ?,  "
     + " ?, ?, ?, ?, "
     + " ?, ?, ?, ?, ?, ?,  "
     + " ?, ?, "+SqlDialect.getDefault().getNextvalNOprm("CUSTOMER_ADDRESS_SEQ")+", ?, ?,  "
     + " ?, ?) ";

    String updateQuery = //CsvUtil.buildUpdateQuery(getSchema());
      " UPDATE customer_address "
      + " SET address_alias=?, address_last_name=?, "
      + " address_first_name=?, address_last_name_kana=?, address_first_name_kana=?, " 
      + " postal_code=?, prefecture_code=?,city_code=?,  address1=?, address2=?, address3=?,  "
      + " address4=?, phone_number=?, mobile_number=?,"
      + " updated_user=?, updated_datetime=?"
      + " WHERE CUSTOMER_CODE = ? AND ADDRESS_NO = ? ";
    //  modify by V10-CH 170 end

    String updateCustomerQuery = "UPDATE CUSTOMER SET LAST_NAME = ?, FIRST_NAME = ?, LAST_NAME_KANA = ?, FIRST_NAME_KANA = ?, "
        + " UPDATED_USER = ?, UPDATED_DATETIME = ? WHERE CUSTOMER_CODE = ? ";
    String selectQuery = CsvUtil.buildCheckExistsQuery(getSchema());
    logger.debug("INSERT statement: " + insertQuery);
    logger.debug("UPDATE statement: " + updateQuery);
    logger.debug("UPDATE statement: " + updateCustomerQuery);
    logger.debug("SELECT statement: " + selectQuery);

    try {
      insertStatement = createPreparedStatement(insertQuery);
      updateStatement = createPreparedStatement(updateQuery);
      updateCustomerStatement = createPreparedStatement(updateCustomerQuery);
      selectStatement = createPreparedStatement(selectQuery);
    } catch (Exception e) {
      throw new DataAccessException(e);
    }
  }

  public ValidationSummary validate(List<String> csvLine) {
    ValidationSummary summary = new ValidationSummary();

    try {
      customerAddress = CsvUtil.createBeanFromCsvLine(csvLine, getSchema(), CustomerAddress.class);
      //  add by V10-CH 170 start
      customerAddress.setAddressFirstName("姓");
      customerAddress.setAddressFirstNameKana("カナ");
      customerAddress.setAddressLastNameKana("カナ");
      //  add by V10-CH 170 end
    } catch (Exception e) {
      summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.WRONG_VALUE)));
      return summary;
    }
    customerAddress.setOrmRowid(DatabaseUtil.DEFAULT_ORM_ROWID);
    DatabaseUtil.setUserStatus(customerAddress, getCondition().getLoginInfo());

    // 10.1.3 10150 修正 ここから
    // if (customerAddress.getAddressNo() != null && customerAddress.getAddressNo().equals(CustomerConstant.SELFE_ADDRESS_NO)) {
    if (customerAddress.getAddressNo() != null && customerAddress.getAddressNo().equals(CustomerConstant.SELF_ADDRESS_NO)) {
    // 10.1.3 10150 修正 ここまで
      customerAddress.setAddressAlias(Messages.getString("service.data.csv.CustomerAddressImportDataSource.0"));
    }
    // 10.1.7 10312 追加 ここから
    if (NumUtil.isNegative(customerAddress.getAddressNo())) {
      summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.MINUS_NUMBER_ERROR, Messages.getString("service.data.csv.CustomerAddressImportDataSource.2"))));
    }
    // 10.1.7 10312 追加 ここまで
    
    if (NumUtil.isNull(customerAddress.getAddressNo())) {
      // アドレスの新規登録のため暫定的なアドレス帳番号をセット


      customerAddress.setAddressNo(-1L);
    }

    // 都道府県名


    if (StringUtil.isNullOrEmpty(customerAddress.getAddress1()) && StringUtil.hasValue(customerAddress.getPrefectureCode())) {
      PrefectureCode prefecture = PrefectureCode.fromValue(customerAddress.getPrefectureCode());
      customerAddress.setAddress1(prefecture.getName());
    }

    // 単項目バリデーションチェック
    summary.getErrors().addAll(BeanValidator.validate(customerAddress).getErrors());
    // 10.1.1 10019 追加 ここから
    // 10.1.7 10312 削除 ここから
    // if (NumUtil.isNegative(customerAddress.getAddressNo())) {
    //   summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.MINUS_NUMBER_ERROR, Messages.getString("service.data.csv.CustomerAddressImportDataSource.2"))));
    // }
    // 10.1.7 10312 削除 ここまで
    // 10.1.1 10019 追加 ここまで
    if (summary.hasError()) {
      return summary;
    }

    // 顧客データ存在チェック


    SimpleQuery customerCountQuery = new SimpleQuery("SELECT COUNT(*) FROM CUSTOMER WHERE CUSTOMER_CODE = ? ");
    customerCountQuery.setParameters(customerAddress.getCustomerCode());
    Long customerCount = Long.valueOf(executeScalar(customerCountQuery).toString());
    if (customerCount == 0L) {
      summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.NOT_EXIST,
          Messages.getString("service.data.csv.CustomerAddressImportDataSource.1"))));
      return summary;
    }

    // 退会済み顧客チェック


    SimpleQuery withdrawedQuery = new SimpleQuery("SELECT CUSTOMER_STATUS FROM CUSTOMER WHERE CUSTOMER_CODE = ? ");
    withdrawedQuery.setParameters(customerAddress.getCustomerCode());
    Long withdrawed = Long.valueOf(executeScalar(withdrawedQuery).toString());
    if (!withdrawed.equals(CustomerStatus.MEMBER.longValue())) {
      summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.WITHDRAWED_ADDRESS)));
    }

    // 都道府県コード・住所1整合性チェック


    PrefectureCode prefecture = PrefectureCode.fromValue(customerAddress.getPrefectureCode());
    if (prefecture == null || !customerAddress.getAddress1().equals(prefecture.getName())) {
      summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.PREFECTURE_MISMATCH)));
    }

    // 新規登録時・本人アドレスだった場合はエラー


    if (customerAddress.getAddressNo() != null
        && !exists(selectStatement, customerAddress.getCustomerCode(), customerAddress.getAddressNo())) {
      // 10.1.3 10150 修正 ここから
      // if (customerAddress.getAddressNo().equals(CustomerConstant.SELFE_ADDRESS_NO)) {
      if (customerAddress.getAddressNo().equals(CustomerConstant.SELF_ADDRESS_NO)) {
      // 10.1.3 10150 修正 ここまで
        summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.SELF_ADDRESS)));
      }
    }
    // 10.1.3 10150 修正 ここから
    // if (customerAddress.getAddressNo().equals(CustomerConstant.SELFE_ADDRESS_NO)
//    if (customerAddress.getAddressNo().equals(CustomerConstant.SELF_ADDRESS_NO)
//    // 10.1.3 10150 修正 ここまで
//        && StringUtil.isNullOrEmpty(customerAddress.getPhoneNumber())) {
//      summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.SELF_PHONE_NO_REQUIRED)));
//    }

    if(StringUtil.isNullOrEmpty(customerAddress.getPhoneNumber()) && StringUtil.isNullOrEmpty(customerAddress.getMobileNumber())){
      summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.NO_NUMBER)));
    }else{
      String  phone []={};
      if(StringUtil.hasValue(customerAddress.getPhoneNumber())){
        phone = customerAddress.getPhoneNumber().split("-");
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
        if(!(StringUtil.hasValueAllOf(phone1,phone2) && phone1.length()>1 &&phone1.length()<5 && phone2.length()>5 && phone2.length()<9 && phone3.length()<7)){
          summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.FALSE_PHONE)));
        }
      }
      if((StringUtil.hasValue(customerAddress.getMobileNumber()) && (customerAddress.getMobileNumber().length() != 11))){
        summary.getErrors().add(new ValidationResult(null, null, Message.get(CsvMessage.FALSE_MOBILE)));
      }
    }
    return summary;
  }

  @Override
  public void executeUpdate(List<String> csvLine) throws SQLException {
    Logger logger = Logger.getLogger(this.getClass());
    try {
      PreparedStatement pstmt = null;
      List<Object> params = new ArrayList<Object>();
      if (exists(selectStatement, customerAddress.getCustomerCode(), customerAddress.getAddressNo())) {

        params.add(customerAddress.getAddressAlias());
        params.add(customerAddress.getAddressLastName());
        params.add(customerAddress.getAddressFirstName());
        params.add(customerAddress.getAddressLastNameKana());
        params.add(customerAddress.getAddressFirstNameKana());
        params.add(customerAddress.getPostalCode());
        params.add(customerAddress.getPrefectureCode());
        params.add(customerAddress.getCityCode());
        params.add(customerAddress.getAddress1());
        params.add(customerAddress.getAddress2());
        params.add(customerAddress.getAddress3());
        params.add(customerAddress.getAddress4());
        //modify by V10-CH start
        if(StringUtil.hasValue(customerAddress.getPhoneNumber())){
          params.add(customerAddress.getPhoneNumber());
        }else{
          params.add("");
        }
        //modify by V10-CH end
        //Add by V10-CH start
        params.add(customerAddress.getMobileNumber());
        //Add by V10-CH end
        params.add(getCondition().getLoginInfo().getRecordingFormat());
        params.add(DateUtil.getSysdate());

        params.add(customerAddress.getCustomerCode());
        params.add(customerAddress.getAddressNo());

        pstmt = updateStatement;

        // 更新時、本人アドレスだった場合は顧客マスタの姓/名とカナ姓/名を更新する
        // 10.1.3 10150 修正 ここから
        // if (customerAddress.getAddressNo().equals(CustomerConstant.SELFE_ADDRESS_NO)) {
        if (customerAddress.getAddressNo().equals(CustomerConstant.SELF_ADDRESS_NO)) {
        // 10.1.3 10150 修正 ここまで
          List<Object> customerParams = new ArrayList<Object>();

          customerParams.add(customerAddress.getAddressLastName());
          customerParams.add(customerAddress.getAddressFirstName());
          customerParams.add(customerAddress.getAddressLastNameKana());
          customerParams.add(customerAddress.getAddressFirstNameKana());

          customerParams.add(getCondition().getLoginInfo().getRecordingFormat());
          customerParams.add(DateUtil.getSysdate());

          customerParams.add(customerAddress.getCustomerCode());

          logger.debug("UPDATE Parameters: " + Arrays.toString(ArrayUtil.toArray(customerParams, Object.class)));
          DatabaseUtil.bindParameters(updateCustomerStatement, ArrayUtil.toArray(customerParams, Object.class));

          int updCustomerCount = updateCustomerStatement.executeUpdate();
          if (updCustomerCount != 1) {
            throw new CsvImportException();
          }
        }

        logger.debug("UPDATE Parameters: " + Arrays.toString(ArrayUtil.toArray(params, Object.class)));
      } else {

        params.add(customerAddress.getCustomerCode());
        params.add(DatabaseUtil.generateSequence(SequenceType.ADDRESS_NO));
        params.add(customerAddress.getAddressAlias());
        params.add(customerAddress.getAddressLastName());
        params.add(customerAddress.getAddressFirstName());
        params.add(customerAddress.getAddressLastNameKana());
        params.add(customerAddress.getAddressFirstNameKana());
        params.add(customerAddress.getPostalCode());
        params.add(customerAddress.getPrefectureCode());
        params.add(customerAddress.getCityCode());
        params.add(customerAddress.getAddress1());
        params.add(customerAddress.getAddress2());
        params.add(customerAddress.getAddress3());
        params.add(customerAddress.getAddress4());
        //modify by V10-CH start
        if(StringUtil.hasValue(customerAddress.getPhoneNumber())){
          params.add(customerAddress.getPhoneNumber());
        }else{
          params.add("");
        }
        //modify by V10-CH end
        //Add by V10-CH start
        params.add(customerAddress.getMobileNumber());
        //Add by V10-CH end
        params.add(getCondition().getLoginInfo().getRecordingFormat());
        params.add(DateUtil.getSysdate());

        params.add(getCondition().getLoginInfo().getRecordingFormat());
        params.add(DateUtil.getSysdate());

        pstmt = insertStatement;
        logger.debug("INSERT Parameters: " + Arrays.toString(ArrayUtil.toArray(params, Object.class)));
      }

      DatabaseUtil.bindParameters(pstmt, ArrayUtil.toArray(params, Object.class));

      int updCount = pstmt.executeUpdate();
      if (updCount != 1) {
        throw new CsvImportException();
      }
    } catch (SQLException e) {
      throw new CsvImportException(e);
    } catch (CsvImportException e) {
      throw e;
    } catch (RuntimeException e) {
      throw new CsvImportException(e);
    }

  }

}
