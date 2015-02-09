package jp.co.sint.webshop.web.bean.back.catalog;

import java.util.ArrayList;
import java.util.List;


import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.dto.CCommodityCynchro;
import jp.co.sint.webshop.data.dto.CCommodityHeader;
import jp.co.sint.webshop.data.dto.CSynchistory;
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
public class CommodityCynchroBean extends UIBackBean implements UISearchBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  /*
   * 最后同期化时间
   */
  private String lastCynchroTime;
  /*
   * EC最后同期化时间
   */
  private String lastCynchroTimeEc;
  /*
   * Tmall最后同期化时间
   */
  private String lastCynchroTimeTmall;
  
  // 2014/05/02 京东WBS对应 ob_姚 add start
  /*
   * jd最后同期化时间
   */
  private String lastCynchroTimeJd;
  // 2014/05/02 京东WBS对应 ob_姚 add end
  
  /*
   * 是否有ec系统未同步数据
   */
  private boolean ecIsNull;
  
  /*
   * 是否有tmall系统未同步数据
   */
  private boolean tmallIsNull;
  
  // 2014/05/02 京东WBS对应 ob_姚 add start
  /*
   * 是否有tmall系统未同步数据
   */
  private boolean jdIsNull;
  // 2014/05/02 京东WBS对应 ob_姚 add end
  
  private String[] checkCommodityCodes;
  
  /**
   * 权限判断：是否可以执行同步操作
   */
  private boolean updateButtonDisplayFlg;
  /**
   * @return the ecIsNull
   */
  public boolean getEcIsNull() {
    return ecIsNull;
  }
  
  /**
   * @param ecIsNull the ecIsNull to set
   */
  public void setEcIsNull(boolean ecIsNull) {
    this.ecIsNull = ecIsNull;
  }
  
  /**
   * @return the tmallIsNull
   */
  public boolean getTmallIsNull() {
    return tmallIsNull;
  }
  
  /**
   * @param tmallIsNull the tmallIsNull to set
   */
  public void setTmallIsNull(boolean tmallIsNull) {
    this.tmallIsNull = tmallIsNull;
  }

  /**
   * @return the jdIsNull
   */
  public boolean getJdIsNull() {
    return jdIsNull;
  }

  /**
   * @param jdIsNull the jdIsNull to set
   */
  public void setJdIsNull(boolean jdIsNull) {
    this.jdIsNull = jdIsNull;
  }

  @Metadata(name="查询EC信息的多选框")
  private String queryEc;
  
  @Metadata(name="查询TMALL信息的多选框")
  private String queryTmall;
  
  private List<CCommodityHeader> resultList ;
  //add by os012 20120213 start
  private List<CCommodityCynchro> ccList ;
  //add by os012 20120213 end  
  public List<CCommodityHeader> getResultList() {
    return resultList;
  }
  
  public void setResultList(List<CCommodityHeader> resultList) {
    this.resultList = resultList;
  }

  public String getQueryEc() {
    return queryEc;
  }

  public void setQueryEc(String queryEc) {
    this.queryEc = queryEc;
  }


  
  public String getQueryTmall() {
    return queryTmall;
  }


  
  public void setQueryTmall(String queryTmall) {
    this.queryTmall = queryTmall;
  }


  public String getLastCynchroTime() {
    return lastCynchroTime;
  }

  
  public void setLastCynchroTime(String lastCynchroTime) {
    this.lastCynchroTime = lastCynchroTime;
  }

  
  public String getLastCynchroTimeEc() {
    return lastCynchroTimeEc;
  }

  
  public void setLastCynchroTimeEc(String lastCynchroTimeEc) {
    this.lastCynchroTimeEc = lastCynchroTimeEc;
  }

  
  public String getLastCynchroTimeTmall() {
    return lastCynchroTimeTmall;
  }

  
  public void setLastCynchroTimeTmall(String lastCynchroTimeTmall) {
    this.lastCynchroTimeTmall = lastCynchroTimeTmall;
  }

  /**
   * @return the lastCynchroTimeJd
   */
  public String getLastCynchroTimeJd() {
    return lastCynchroTimeJd;
  }

  /**
   * @param lastCynchroTimeJd the lastCynchroTimeJd to set
   */
  public void setLastCynchroTimeJd(String lastCynchroTimeJd) {
    this.lastCynchroTimeJd = lastCynchroTimeJd;
  }

  private PagerValue pagerValue;

  private List<CSynchistory> list = new ArrayList<CSynchistory>();

  /**
   * 商品一覧を取得します。
   * 
   * @return list
   */
  public List<CSynchistory> getList() {
    return list;
  }

  /**
   * 商品一覧を設定します。
   * 
   * @param list
   *          list
   */
  public void setList(List<CSynchistory> list) {
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
  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U1040230";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.back.catalog.CommodityCychroBean.0");
  }
  /**
   * @param ccList the ccList to set
   */
  public void setCcList(List<CCommodityCynchro> ccList) {
    this.ccList = ccList;
  }

  /**
   * @return the ccList
   */
  public List<CCommodityCynchro> getCcList() {
    return ccList;
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
  
  /**
   * @return the updateButtonDisplayFlg
   */
  public boolean isUpdateButtonDisplayFlg() {
    return updateButtonDisplayFlg;
  }

  
  /**
   * @param updateButtonDisplayFlg the updateButtonDisplayFlg to set
   */
  public void setUpdateButtonDisplayFlg(boolean updateButtonDisplayFlg) {
    this.updateButtonDisplayFlg = updateButtonDisplayFlg;
  }

  
  /**
   * @return the checkCommodityCodes
   */
  public String[] getCheckCommodityCodes() {
    return checkCommodityCodes;
  }

  
  /**
   * @param checkCommodityCodes the checkCommodityCodes to set
   */
  public void setCheckCommodityCodes(String[] checkCommodityCodes) {
    this.checkCommodityCodes = checkCommodityCodes;
  }

}
