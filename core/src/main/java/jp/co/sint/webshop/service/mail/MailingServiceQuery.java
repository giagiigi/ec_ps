package jp.co.sint.webshop.service.mail;

import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.domain.MailSendStatus; // 10.1.7 10302 追加
import jp.co.sint.webshop.data.domain.RequestMailType;
import jp.co.sint.webshop.data.dto.BroadcastMailqueueDetail;
import jp.co.sint.webshop.data.dto.BroadcastMailqueueHeader;
import jp.co.sint.webshop.data.dto.Customer;
import jp.co.sint.webshop.utility.SqlDialect;

/**
 * メールサービスで使用するクエリを集約したクラス
 * 
 * @author System Integrator Corp.
 */
public final class MailingServiceQuery {

  /** default constructor */
  private MailingServiceQuery() {
  }

  public static final String LOAD_NO_SENT_BROADCAST_MAILQUEUE_HEADER = DatabaseUtil
      .getSelectAllQuery(BroadcastMailqueueHeader.class)
      // 10.1.7 10302 修正 ここから
      // + " WHERE MAIL_SEND_STATUS != '1'";
      + " WHERE MAIL_SEND_STATUS = " + MailSendStatus.NOT_SENT.getValue();
      // 10.1.7 10302 修正 ここまで

  public static final String LOAD_NO_SENT_BROADCAST_MAILQUEUE_DETAIL = DatabaseUtil
      .getSelectAllQuery(BroadcastMailqueueDetail.class)
      + " WHERE MAIL_QUEUE_ID = ? AND MAIL_SEND_STATUS != '1'";

  public static final String DELETE_BROADCAST_MAILQUE_HEADER = "DELETE FROM BROADCAST_MAILQUEUE_HEADER"
      + " WHERE MAIL_QUEUE_ID NOT IN (SELECT MAIL_QUEUE_ID FROM BROADCAST_MAILQUEUE_DETAIL)";

  public static final String DELETE_BROADCAST_MAILQUEUE_DETAIL = "DELETE FROM BROADCAST_MAILQUEUE_DETAIL"
      + " WHERE MAIL_SENT_DATETIME <= ?";

  public static final String DELETE_RESPECTIVE_MAILQUEUE = "DELETE FROM RESPECTIVE_MAILQUEUE WHERE MAIL_SENT_DATETIME <= ?";

  public static final String LOAD_CUSTOMER_BY_BIRTH_DATE = DatabaseUtil.getSelectAllQuery(Customer.class) + " WHERE "
      + SqlDialect.getDefault().getMonthDayFromDate("BIRTH_DATE") + " = ? AND CUSTOMER_STATUS = 0 AND REQUEST_MAIL_TYPE <> "
      + RequestMailType.UNWANTED.getValue();

  public static final String LOAD_CUSTOMER_BY_LATEST_POINT_ACQUIRED_DATE = DatabaseUtil.getSelectAllQuery(Customer.class)
      + " WHERE LATEST_POINT_ACQUIRED_DATE = ? AND CUSTOMER_STATUS = 0 AND REST_POINT > 0 AND REQUEST_MAIL_TYPE <> "
      + RequestMailType.UNWANTED.getValue();

  // 10.1.1 10015 追加 ここから
  public static final String UPDATE_MAIL_SEND_STATUS = "UPDATE BROADCAST_MAILQUEUE_DETAIL "
      + " SET MAIL_SEND_STATUS = ?, UPDATED_USER = ?, UPDATED_DATETIME = ? "
      + " , MAIL_SENT_DATETIME = ? " // 10.1.7 10302 追加
      + " WHERE MAIL_QUEUE_ID = ? AND CUSTOMER_CODE = ?";
  // 10.1.1 10015 追加 ここまで

}
