package jp.co.sint.webshop.service.analysis;

import java.util.Date;

import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.service.SearchCondition;
import jp.co.sint.webshop.utility.DateUtil;

public class RefererSearchCondition extends SearchCondition {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;

  /** 集計開始日 */
  @Required
  @Metadata(name = "検索期間(開始日)", order = 1)
  private Date searchStartDate;

  /** 集計終了日 */
  @Required
  @Metadata(name = "検索期間(終了日)", order = 2)
  private Date searchEndDate;

  /** クライアントグループ */
  @Metadata(name = "クライアントグループ", order = 4)
  private String clientGroup;

  /**
   * clientGroupを返します。
   * 
   * @return the clientGroup
   */
  public String getClientGroup() {
    return clientGroup;
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
   * searchStartDateを返します。
   * 
   * @return the searchStartDate
   */
  public Date getSearchStartDate() {
    return DateUtil.immutableCopy(searchStartDate);
  }

  /**
   * searchEndDateを返します。
   * 
   * @return the searchEndDate
   */
  public Date getSearchEndDate() {
    return DateUtil.immutableCopy(searchEndDate);
  }

  /**
   * searchStartDateを設定します。
   * 
   * @param searchStartDate
   *          設定する searchStartDate
   */
  public void setSearchStartDate(Date searchStartDate) {
    this.searchStartDate = DateUtil.immutableCopy(searchStartDate);
  }

  /**
   * searchEndDateを設定します。
   * 
   * @param searchEndDate
   *          設定する searchEndDate
   */
  public void setSearchEndDate(Date searchEndDate) {
    this.searchEndDate = DateUtil.immutableCopy(searchEndDate);
  }

}
