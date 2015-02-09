package jp.co.sint.webshop.service;

import java.io.Serializable;
import java.util.Map;

/**
 * 検索条件全般の規定クラス
 * 
 * @author System Integrator Corp.
 */
public abstract class SearchCondition implements Serializable {

  private static final long serialVersionUID = 1L; // 10.1.4 K00149 追加

  private int currentPage;

  private int pageSize;

  private int maxFetchSize;

  private Map<String, String> keywords;

  /**
   * currentPageを取得します。
   * 
   * @return currentPage
   */
  public int getCurrentPage() {
    return currentPage;
  }

  /**
   * currentPageを設定します。
   * 
   * @param currentPage
   *          currentPage
   */
  public void setCurrentPage(int currentPage) {
    this.currentPage = currentPage;
  }

  /**
   * keywordsを取得します。
   * 
   * @return keywords
   */
  public Map<String, String> getKeywords() {
    return keywords;
  }

  /**
   * keywordsを設定します。
   * 
   * @param keywords
   *          keywords
   */
  public void setKeywords(Map<String, String> keywords) {
    this.keywords = keywords;
  }

  /**
   * maxFetchSizeを取得します。
   * 
   * @return maxFetchSize
   */
  public int getMaxFetchSize() {
    return maxFetchSize;
  }

  /**
   * maxFetchSizeを設定します。
   * 
   * @param maxFetchSize
   *          maxFetchSize
   */
  public void setMaxFetchSize(int maxFetchSize) {
    this.maxFetchSize = maxFetchSize;
  }

  /**
   * pageSizeを取得します。
   * 
   * @return pageSize
   */
  public int getPageSize() {
    return pageSize;
  }

  /**
   * pageSizeを設定します。
   * 
   * @param pageSize
   *          pageSize
   */
  public void setPageSize(int pageSize) {
    this.pageSize = pageSize;
  }

}
