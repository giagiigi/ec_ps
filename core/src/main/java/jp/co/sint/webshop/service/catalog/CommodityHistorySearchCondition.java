package jp.co.sint.webshop.service.catalog;

import jp.co.sint.webshop.data.attribute.Datetime;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.service.SearchCondition;
import jp.co.sint.webshop.utility.DateUtil;

public class CommodityHistorySearchCondition extends SearchCondition {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  @Datetime(format = DateUtil.DEFAULT_DATETIME_FORMAT)
  @Metadata(name = "同期開始日時(From)")
  private String searchCynchroStartTime;
  
  @Datetime(format = DateUtil.DEFAULT_DATETIME_FORMAT)
  @Metadata(name = "同期结束日時(To)")
  private String searchCynchroEndTime;
  
  private String queryType;

  
  /**
   * @return the searchCynchroStartTime
   */
  public String getSearchCynchroStartTime() {
    return searchCynchroStartTime;
  }

  
  /**
   * @param searchCynchroStartTime the searchCynchroStartTime to set
   */
  public void setSearchCynchroStartTime(String searchCynchroStartTime) {
    this.searchCynchroStartTime = searchCynchroStartTime;
  }

  
  /**
   * @return the searchCynchroEndTime
   */
  public String getSearchCynchroEndTime() {
    return searchCynchroEndTime;
  }

  
  /**
   * @param searchCynchroEndTime the searchCynchroEndTime to set
   */
  public void setSearchCynchroEndTime(String searchCynchroEndTime) {
    this.searchCynchroEndTime = searchCynchroEndTime;
  }

  
  /**
   * @return the queryType
   */
  public String getQueryType() {
    return queryType;
  }

  
  /**
   * @param queryType the queryType to set
   */
  public void setQueryType(String queryType) {
    this.queryType = queryType;
  }
  
  


}
