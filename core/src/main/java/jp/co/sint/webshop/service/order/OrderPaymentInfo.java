package jp.co.sint.webshop.service.order;

import java.io.Serializable;
import java.util.List;

public class OrderPaymentInfo implements Serializable {

  private static final long serialVersionUID = 1L;

  private String orderNo;

  private List<String> receivedMailSentDateList;

  private List<String> reminderMailSentDateList;

  /**
   * orderNoを返します。
   * 
   * @return the orderNo
   */
  public String getOrderNo() {
    return orderNo;
  }

  /**
   * orderNoを設定します。
   * 
   * @param orderNo
   *          設定する orderNo
   */
  public void setOrderNo(String orderNo) {
    this.orderNo = orderNo;
  }

  /**
   * @return the receivedMailSentDateList
   */
  public List<String> getReceivedMailSentDateList() {
    return receivedMailSentDateList;
  }

  /**
   * @param receivedMailSentDateList
   *          the receivedMailSentDateList to set
   */
  public void setReceivedMailSentDateList(List<String> receivedMailSentDateList) {
    this.receivedMailSentDateList = receivedMailSentDateList;
  }

  /**
   * @return the reminderMailSentDateList
   */
  public List<String> getReminderMailSentDateList() {
    return reminderMailSentDateList;
  }

  /**
   * @param reminderMailSentDateList
   *          the reminderMailSentDateList to set
   */
  public void setReminderMailSentDateList(List<String> reminderMailSentDateList) {
    this.reminderMailSentDateList = reminderMailSentDateList;
  }

}
