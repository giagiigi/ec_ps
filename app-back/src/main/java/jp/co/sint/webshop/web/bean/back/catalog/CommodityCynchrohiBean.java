package jp.co.sint.webshop.web.bean.back.catalog;

import java.util.ArrayList;
import java.util.List;


import jp.co.sint.webshop.data.attribute.Datetime;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.dto.CCommodityHeader;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.web.bean.UISearchBean;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerValue;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U1040230:商品マスタのデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class CommodityCynchrohiBean extends UIBackBean implements UISearchBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  
  
  @Datetime(format = DateUtil.DEFAULT_DATETIME_FORMAT)
  @Metadata(name = "同期開始日時(From)")
  private String searchCynchroStartTime;
  
  @Datetime(format = DateUtil.DEFAULT_DATETIME_FORMAT)
  @Metadata(name = "同期结束日時(To)")
  private String searchCynchroEndTime;
  
  private String searchHistoryType;
  
  /**
   * @return the searchHistoryType
   */
  public String getSearchHistoryType() {
    return searchHistoryType;
  }

  /**
   * @param searchHistoryType the searchHistoryType to set
   */
  public void setSearchHistoryType(String searchHistoryType) {
    this.searchHistoryType = searchHistoryType;
  }

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

  private List<CCommodityHeader> resultList ;
  
  
  public List<CCommodityHeader> getResultList() {
    return resultList;
  }
  
  public void setResultList(List<CCommodityHeader> resultList) {
    this.resultList = resultList;
  }


  private PagerValue pagerValue;

  private List<ResultHistoryInfo> list = new ArrayList<ResultHistoryInfo>();

  /**
   * 商品一覧を取得します。
   * 
   * @return list
   */
  public List<ResultHistoryInfo> getList() {
    return list;
  }

  /**
   * 商品一覧を設定します。
   * 
   * @param list
   *          list
   */
  public void setList(List<ResultHistoryInfo> list) {
    this.list = list;
  }

  

  /**
   * pagerValueを取得します。
   * 
   * @return pagerValue
   */
  public PagerValue getPagerValue() {
    return pagerValue;
  }

  /**
   * pagerValueを設定します。
   * 
   * @param pagerValue
   *          pagerValue
   */
  public void setPagerValue(PagerValue pagerValue) {
    this.pagerValue = pagerValue;
  }

  /**
   * サブJSPを設定します。
   */
  @Override
  public void setSubJspId() {
  }

  /**
   * リクエストパラメータから値を取得します。
   * 
   * @param reqparam
   *          リクエストパラメータ
   */
  public void createAttributes(RequestParameter reqparam) {
    this.setSearchCynchroStartTime(reqparam.getDateTimeString("searchCynchroStartTime"));
    this.setSearchCynchroEndTime(reqparam.getDateTimeString("searchCynchroEndTime"));
  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U1040750";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.back.catalog.CommodityCychroBean.0");
  }
  public class ResultHistoryInfo{
    private String syncCode;

    private Long totalCount;
    
    private Long successCount;

    private Long failureCount;

    private String syncUser;

    private String useTime;

    private String syncTime;
    
    private String createdUser;

    private String updatedUser;

    
    /**
     * @return the syncCode
     */
    public String getSyncCode() {
      return syncCode;
    }

    
    /**
     * @param syncCode the syncCode to set
     */
    public void setSyncCode(String syncCode) {
      this.syncCode = syncCode;
    }

    
    /**
     * @return the totalCount
     */
    public Long getTotalCount() {
      return totalCount;
    }

    
    /**
     * @param totalCount the totalCount to set
     */
    public void setTotalCount(Long totalCount) {
      this.totalCount = totalCount;
    }

    
    /**
     * @return the successCount
     */
    public Long getSuccessCount() {
      return successCount;
    }

    
    /**
     * @param successCount the successCount to set
     */
    public void setSuccessCount(Long successCount) {
      this.successCount = successCount;
    }

    
    /**
     * @return the failureCount
     */
    public Long getFailureCount() {
      return failureCount;
    }

    
    /**
     * @param failureCount the failureCount to set
     */
    public void setFailureCount(Long failureCount) {
      this.failureCount = failureCount;
    }

    
    /**
     * @return the syncUser
     */
    public String getSyncUser() {
      return syncUser;
    }

    
    /**
     * @param syncUser the syncUser to set
     */
    public void setSyncUser(String syncUser) {
      this.syncUser = syncUser;
    }

    
    /**
     * @return the useTime
     */
    public String getUseTime() {
      return useTime;
    }

    
    /**
     * @param useTime the useTime to set
     */
    public void setUseTime(String useTime) {
      this.useTime = useTime;
    }

    

    
    /**
     * @return the syncTime
     */
    public String getSyncTime() {
      return syncTime;
    }


    
    /**
     * @param syncTime the syncTime to set
     */
    public void setSyncTime(String syncTime) {
      this.syncTime = syncTime;
    }


    /**
     * @return the createdUser
     */
    public String getCreatedUser() {
      return createdUser;
    }

    
    /**
     * @param createdUser the createdUser to set
     */
    public void setCreatedUser(String createdUser) {
      this.createdUser = createdUser;
    }

    
    /**
     * @return the updatedUser
     */
    public String getUpdatedUser() {
      return updatedUser;
    }

    
    /**
     * @param updatedUser the updatedUser to set
     */
    public void setUpdatedUser(String updatedUser) {
      this.updatedUser = updatedUser;
    }
  }

}
