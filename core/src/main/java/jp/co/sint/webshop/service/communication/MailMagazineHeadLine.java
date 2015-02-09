package jp.co.sint.webshop.service.communication;

import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.data.dto.MailMagazine;

public class MailMagazineHeadLine extends MailMagazine {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** 購読人数 */
  @Required
  private String subscriberAmount;

  /**
   * subscriberAmountを取得します。
   * 
   * @return subscriberAmount
   */
  public String getSubscriberAmount() {
    return subscriberAmount;
  }

  /**
   * subscriberAmountを設定します。
   * 
   * @param subscriberAmount
   *          subscriberAmount
   */
  public void setSubscriberAmount(String subscriberAmount) {
    this.subscriberAmount = subscriberAmount;
  }

}
