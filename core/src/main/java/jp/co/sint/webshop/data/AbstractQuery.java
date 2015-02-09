package jp.co.sint.webshop.data;

import jp.co.sint.webshop.utility.ArrayUtil;
import jp.co.sint.webshop.utility.StringUtil;

/**
 * 検索クエリのデフォルト実装基底クラス
 * 
 * @author System Integrator Corp.
 * @param <T>
 */
public abstract class AbstractQuery<T> implements SearchQuery<T> {

  private static final long serialVersionUID = 1L; // 10.1.4 K00149 追加

  public static final int DEFAULT_MAX_FETCH_SIZE = 1000;

  private static final int DEFAULT_PAGE_NUMBER = 1;

  private static final int DEFAULT_PAGE_SIZE = 10;

  private int maxFetchSize = DEFAULT_MAX_FETCH_SIZE;

  private int pageNumber = DEFAULT_PAGE_NUMBER;

  private int pageSize = DEFAULT_PAGE_SIZE;

  private String sqlString = StringUtil.EMPTY;

  private Object[] parameters = new Object[0];

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
   * pageNumberを取得します。
   * 
   * @return pageNumber
   */
  public int getPageNumber() {
    return pageNumber;
  }

  /**
   * pageNumberを設定します。
   * 
   * @param pageNumber
   *          pageNumber
   */
  public void setPageNumber(int pageNumber) {
    this.pageNumber = pageNumber;
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

  /**
   * parametersを取得します。
   * 
   * @return parameters
   */
  public Object[] getParameters() {
    return ArrayUtil.immutableCopy(parameters);
  }

  /**
   * parametersを設定します。
   * 
   * @param parameters
   *          parameters
   */
  public void setParameters(Object[] parameters) {
    this.parameters = ArrayUtil.immutableCopy(parameters);
  }

  /**
   * sqlStringを取得します。
   * 
   * @return sqlString
   */
  public String getSqlString() {
    return sqlString;
  }

  /**
   * sqlStringを設定します。
   * 
   * @param sqlString
   *          sqlString
   */
  public void setSqlString(String sqlString) {
    this.sqlString = sqlString;
  }

}
