package jp.co.sint.webshop.service.analysis;

import java.io.Serializable;

public class RefererSummary implements Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;

  /** リファラー名称 */
  private String refererUrl;

  /** リファラー集計件数 */
  private Long refererCount;

  /**
   * refererCountを取得します。
   * 
   * @return refererCount
   */
  public Long getRefererCount() {
    return refererCount;
  }

  /**
   * refererCountを設定します。
   * 
   * @param refererCount
   *          refererCount
   */
  public void setRefererCount(Long refererCount) {
    this.refererCount = refererCount;
  }

  /**
   * refererUrlを取得します。
   * 
   * @return refererUrl
   */
  public String getRefererUrl() {
    return refererUrl;
  }

  /**
   * refererUrlを設定します。
   * 
   * @param refererUrl
   *          refererUrl
   */
  public void setRefererUrl(String refererUrl) {
    this.refererUrl = refererUrl;
  }

}
