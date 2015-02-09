package jp.co.sint.webshop.service.analysis;

import java.io.Serializable;

public class PageView implements Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;

  /** 集計単位(時間帯，日付，月，曜日) */
  private String label;

  /** クライアントグループ */
  private String clientGroup;

  /** アクセス回数 */
  private Long accessCount;

  /**
   * labelを返します。
   * 
   * @return the label
   */
  public String getLabel() {
    return label;
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
   * accessCountを返します。
   * 
   * @return the accessCount
   */
  public Long getAccessCount() {
    return accessCount;
  }

  /**
   * labelを設定します。
   * 
   * @param label
   *          設定する label
   */
  public void setLabel(String label) {
    this.label = label;
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
   * accessCountを設定します。
   * 
   * @param accessCount
   *          設定する accessCount
   */
  public void setAccessCount(Long accessCount) {
    this.accessCount = accessCount;
  }
}
