package jp.co.sint.webshop.service.order;

//10.1.6 10267 追加 ここから
import jp.co.sint.webshop.data.domain.MailSendStatus;
//10.1.6 10267 追加 ここまで
public final class OrderPaymentInfoQuery {

  private static final long serialVersionUID = 1L;

  private OrderPaymentInfoQuery() {

  }

  public static final String GET_MAIL_SENT_DATE_LIST_FROMTYPE = "SELECT MAIL_SENT_DATETIME FROM RESPECTIVE_MAILQUEUE RM "
      + "INNER JOIN ORDER_MAIL_HISTORY OMH ON RM.MAIL_QUEUE_ID = OMH.MAIL_QUEUE_ID "
      // 10.1.6 10267 修正 ここから
      // + "WHERE RM.MAIL_TYPE = ? AND ORDER_NO = ? ORDER BY MAIL_SENT_DATETIME ";
      + "WHERE RM.MAIL_TYPE = ? AND ORDER_NO = ? AND RM.MAIL_SEND_STATUS = "
      + MailSendStatus.SENT_ALL.longValue() + " ORDER BY MAIL_SENT_DATETIME ";
      // 10.1.6 10267 修正 ここまで
}
