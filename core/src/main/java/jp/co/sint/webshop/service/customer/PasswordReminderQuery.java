package jp.co.sint.webshop.service.customer;

import jp.co.sint.webshop.utility.SqlDialect;

public final class PasswordReminderQuery {

  /**
   * default constructor
   */
  private PasswordReminderQuery() {
  }

  public static final String LOAD_REMINDER_QUERY = "SELECT CUSTOMER_CODE, REISSUED_DATETIME, MAIL_SEND_STATUS "
//	postgreSQL start	  
      //+ "FROM REMINDER WHERE REISSUANCE_KEY = ? AND REISSUED_DATETIME + ?/1440 >= SYSDATE  ";
  + "FROM REMINDER WHERE REISSUANCE_KEY = ? AND REISSUED_DATETIME + "+SqlDialect.getDefault().getAddMinute()+" >= "+SqlDialect.getDefault().getCurrentDatetime()+"  ";
//	postgreSQL end
}
