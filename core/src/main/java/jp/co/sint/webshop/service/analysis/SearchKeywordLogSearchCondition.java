package jp.co.sint.webshop.service.analysis;

import java.util.Date;

import jp.co.sint.webshop.data.attribute.Datetime;
import jp.co.sint.webshop.service.SearchCondition;
import jp.co.sint.webshop.utility.DateUtil;

public class SearchKeywordLogSearchCondition extends SearchCondition {

  /** serial version UID */
  private static final long serialVersionUID = 1L;

  @Datetime
  private Date searchStartDate;

  @Datetime
  private Date searchEndDate;

  private String searchKey;

  /**
   * searchendDateを取得します。
   * 
   * @return searchendDate
   */
  public Date getSearchEndDate() {
    return DateUtil.immutableCopy(searchEndDate);
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
   * searchStartDateを取得します。
   * 
   * @return searchStartDate
   */
  public Date getSearchStartDate() {
    return DateUtil.immutableCopy(searchStartDate);
  }

  /**
   * searchendDateを設定します。
   * 
   * @param searchEndDate
   *          searchEndDate
   */
  public void setSearchEndDate(Date searchEndDate) {
    this.searchEndDate = DateUtil.immutableCopy(searchEndDate);
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
   * searchStartDateを設定します。
   * 
   * @param searchStartDate
   *          searchStartDate
   */
  public void setSearchStartDate(Date searchStartDate) {
    this.searchStartDate = DateUtil.immutableCopy(searchStartDate);
  }

}
