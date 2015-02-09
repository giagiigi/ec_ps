package jp.co.sint.webshop.service.customer;

import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.data.dto.PointHistory;
import jp.co.sint.webshop.text.Messages;

public class PointHistoryList extends PointHistory {

  /**
   * 「ポイント履歴(POINT_HISTORY)」の拡張Bean
   */
  /** Serial Version UID */
  private static final long serialVersionUID = 1L;

  /** 発行元 */
  @Required
  private String publisher;
  
  /** 発行元ID */
  private String publisherId;

  
  /**
   * @return publisher
   */
  public String getPublisher() {
    if (getOrderNo() != null) {
      publisher = Messages.getString("service.customer.PointHistoryList.0");
    } else if (getEnqueteCode() != null) {
      publisher = Messages.getString("service.customer.PointHistoryList.1");
    } else if (getReviewId() != null) {
      publisher = Messages.getString("service.customer.PointHistoryList.2");
    } else {
      publisher = Messages.getString("service.customer.PointHistoryList.3");
    }
    return publisher;
  }

  
  /**
   * @param publisher 設定する publisher
   */
  public void setPublisher(String publisher) {
    this.publisher = publisher;
  }


  
  /**
   * @return publisherId
   */
  public String getPublisherId() {
    if (getOrderNo() != null) {
      publisherId = getOrderNo();
    } else if (getEnqueteCode() != null) {
      publisherId = getEnqueteCode();
    } else if (getReviewId() != null) {
      publisherId = Long.toString(getReviewId());
    } else {
      publisherId = "";
    }
    return publisherId;
  }


  
  /**
   * @param publisherId 設定する publisherId
   */
  public void setPublisherId(String publisherId) {
    this.publisherId = publisherId;
  }

}
