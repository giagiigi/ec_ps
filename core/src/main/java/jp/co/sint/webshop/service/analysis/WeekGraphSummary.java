package jp.co.sint.webshop.service.analysis;

import java.io.Serializable;

public class WeekGraphSummary implements Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;

  /** 集計日 */
  private String countDate;

  /** クライアントグループ */
  private String clientGroup;

  /** 集計結果 */
  private Long countResult;

  /**
   * countDateを返します。
   * 
   * @return the countDate
   */
  public String getCountDate() {
    return countDate;
  }

  /**
   * clientGroupを返します。
   * 
   * @return the clientGroup
   */
  public String getClientGroup() {
    return clientGroup;
  }

  /**
   * countResultを返します。
   * 
   * @return the countResult
   */
  public Long getCountResult() {
    return countResult;
  }

  /**
   * countDateを設定します。
   * 
   * @param countDate
   *          設定する countDate
   */
  public void setCountDate(String countDate) {
    this.countDate = countDate;
  }

  /**
   * clientGroupを設定します。
   * 
   * @param clientGroup
   *          設定する clientGroup
   */
  public void setClientGroup(String clientGroup) {
    this.clientGroup = clientGroup;
  }

  /**
   * countResultを設定します。
   * 
   * @param countResult
   *          設定する countResult
   */
  public void setCountResult(Long countResult) {
    this.countResult = countResult;
  }
}
