package jp.co.sint.webshop.service.analysis;

import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.Datetime;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.service.SearchCondition;
import jp.co.sint.webshop.utility.DateUtil;


public class GiftCardLogSearchCondition extends SearchCondition{
  /** Serial Version UID */
  private static final long serialVersionUID = 1L;
  
  /** 礼品卡编号 */
  @AlphaNum2
  @Length(16)
  @Metadata(name = "礼品卡编号", order = 1)
  private String searchGiftCardCode;

  /** 礼品卡名称 */
  @Length(40)
  @Metadata(name = "礼品卡名称", order = 2)
  private String searchGiftCardName;

  /** 礼品卡发行开始日时(From) */
  @Datetime(format = DateUtil.DEFAULT_DATETIME_FORMAT)
  @Metadata(name = "礼品卡发行开始日时(From)", order = 9)
  private String searchMinIssueStartDatetimeFrom;

  /** 礼品卡发行开始日时(To) */
  @Datetime(format = DateUtil.DEFAULT_DATETIME_FORMAT)
  @Metadata(name = "礼品卡发行开始日时(To)", order = 10)
  private String searchMinIssueStartDatetimeTo;

  
  /**
   * @return the searchGiftCardCode
   */
  public String getSearchGiftCardCode() {
    return searchGiftCardCode;
  }

  
  /**
   * @param searchGiftCardCode the searchGiftCardCode to set
   */
  public void setSearchGiftCardCode(String searchGiftCardCode) {
    this.searchGiftCardCode = searchGiftCardCode;
  }

  
  /**
   * @return the searchGiftCardName
   */
  public String getSearchGiftCardName() {
    return searchGiftCardName;
  }

  
  /**
   * @param searchGiftCardName the searchGiftCardName to set
   */
  public void setSearchGiftCardName(String searchGiftCardName) {
    this.searchGiftCardName = searchGiftCardName;
  }

  
  /**
   * @return the searchMinIssueStartDatetimeFrom
   */
  public String getSearchMinIssueStartDatetimeFrom() {
    return searchMinIssueStartDatetimeFrom;
  }

  
  /**
   * @param searchMinIssueStartDatetimeFrom the searchMinIssueStartDatetimeFrom to set
   */
  public void setSearchMinIssueStartDatetimeFrom(String searchMinIssueStartDatetimeFrom) {
    this.searchMinIssueStartDatetimeFrom = searchMinIssueStartDatetimeFrom;
  }

  
  /**
   * @return the searchMinIssueStartDatetimeTo
   */
  public String getSearchMinIssueStartDatetimeTo() {
    return searchMinIssueStartDatetimeTo;
  }

  
  /**
   * @param searchMinIssueStartDatetimeTo the searchMinIssueStartDatetimeTo to set
   */
  public void setSearchMinIssueStartDatetimeTo(String searchMinIssueStartDatetimeTo) {
    this.searchMinIssueStartDatetimeTo = searchMinIssueStartDatetimeTo;
  }
  
  
  
  
}
