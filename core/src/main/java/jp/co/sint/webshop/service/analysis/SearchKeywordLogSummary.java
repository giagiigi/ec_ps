package jp.co.sint.webshop.service.analysis;

import java.io.Serializable;

/**
 * 検索キーワードログ集計結果を格納するクラス。
 * 
 * @author System Integrator Corp.
 */
public class SearchKeywordLogSummary implements Serializable {

  /** serial version UID */
  private static final long serialVersionUID = 1L;

  /** 検索キー */
  private String searchKey;

  /** 検索ワード */
  private String searchWord;

  /** 検索回数 */
  private Long searchCount;

  /**
   * searchCountを取得します。
   * 
   * @return searchCount
   */
  public Long getSearchCount() {
    return searchCount;
  }

  /**
   * searchKeyを取得します。
   * 
   * @return searchKey
   */
  public String getSearchKey() {
    return searchKey;
  }

  /**
   * searchWordを取得します。
   * 
   * @return searchWord
   */
  public String getSearchWord() {
    return searchWord;
  }

  /**
   * searchCountを設定します。
   * 
   * @param searchCount
   *          searchCount
   */
  public void setSearchCount(Long searchCount) {
    this.searchCount = searchCount;
  }

  /**
   * searchKeyを設定します。
   * 
   * @param searchKey
   *          searchKey
   */
  public void setSearchKey(String searchKey) {
    this.searchKey = searchKey;
  }

  /**
   * searchWordを設定します。
   * 
   * @param searchWord
   *          searchWord
   */
  public void setSearchWord(String searchWord) {
    this.searchWord = searchWord;
  }
}
