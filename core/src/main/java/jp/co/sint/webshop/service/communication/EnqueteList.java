package jp.co.sint.webshop.service.communication;

import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.data.dto.Enquete;

public class EnqueteList extends Enquete {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** 回答者数 */
  @Required
  private String enqueteAnswerCount;

  /**
   * enqueteAnswerCountを取得します。
   * 
   * @return enqueteAnswerCount
   */
  public String getEnqueteAnswerCount() {
    return enqueteAnswerCount;
  }

  /**
   * enqueteAnswerCountを設定します。
   * 
   * @param enqueteAnswerCount
   *          enqueteAnswerCount
   */
  public void setEnqueteAnswerCount(String enqueteAnswerCount) {
    this.enqueteAnswerCount = enqueteAnswerCount;
  }

}
