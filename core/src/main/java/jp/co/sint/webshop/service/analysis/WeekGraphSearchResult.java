package jp.co.sint.webshop.service.analysis;

public class WeekGraphSearchResult {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;

  /** 集計日 */
  private String countDate;

  /** クライアントグループ */
  private String clientGroup;

  /** 集計結果 */
  private String countResult;

  /**
   * clientGroupを取得します。
   * 
   * @return clientGroup
   */
  public String getClientGroup() {
    return clientGroup;
  }

  /**
   * clientGroupを設定します。
   * 
   * @param clientGroup
   *          clientGroup
   */
  public void setClientGroup(String clientGroup) {
    this.clientGroup = clientGroup;
  }

  /**
   * countDateを取得します。
   * 
   * @return countDate
   */
  public String getCountDate() {
    return countDate;
  }

  /**
   * countDateを設定します。
   * 
   * @param countDate
   *          countDate
   */
  public void setCountDate(String countDate) {
    this.countDate = countDate;
  }

  /**
   * countResultを取得します。
   * 
   * @return countResult
   */
  public String getCountResult() {
    return countResult;
  }

  /**
   * countResultを設定します。
   * 
   * @param countResult
   *          countResult
   */
  public void setCountResult(String countResult) {
    this.countResult = countResult;
  }

}
