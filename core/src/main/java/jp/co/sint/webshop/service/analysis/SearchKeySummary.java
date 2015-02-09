package jp.co.sint.webshop.service.analysis;

import java.io.Serializable;

public class SearchKeySummary implements Serializable {

  /**
   * serial version UID
   */
  private static final long serialVersionUID = 1L;

  private String searchKey;

  /**
   * searchKeyを返します。
   * 
   * @return the searchKey
   */
  public String getSearchKey() {
    return searchKey;
  }

  /**
   * searchKeyを設定します。
   * 
   * @param searchKey
   *          設定する searchKey
   */
  public void setSearchKey(String searchKey) {
    this.searchKey = searchKey;
  }
}
