package jp.co.sint.webshop.web.bean.back.communication;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import jp.co.sint.webshop.data.attribute.Datetime;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.webutility.PagerValue;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U1060310:キャンペーン管理のデータモデルです。
 * 
 * @author OB.
 */
public class StockHolidayListBean extends UIBackBean  {

  
  private static final long serialVersionUID = 1L;

  private PagerValue pagerValue = new PagerValue();
 
  
  /**仓库休息日时间*/
  @Datetime(format = DateUtil.DEFAULT_DATETIME_FORMAT)
  @Metadata(name = "仓库休息日时间", order = 1)
  private String stockHoliDay;
  
  /**休息日行号*/
  @Datetime(format = DateUtil.DEFAULT_DATETIME_FORMAT)
  @Metadata(name = "休息日行号", order = 2)
  private String ormRowid;
  
  
  /**
   * 休息日集合
   */
  private List<StockHolidayDetail> stockHolidayList = new ArrayList<StockHolidayDetail>();

  private List<String> stockPageList = new ArrayList<String>();
  
  
  public void createAttributes(RequestParameter reqparam) {
    this.setStockHoliDay(reqparam.getDateString("stockHoliDay"));
  }
  
  
  public static class StockHolidayDetail implements Serializable {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    
    private String stockHoliDay;

    /** 数据库行ID */
    private Long ormRowid;

    /** 作成用户 */
    private String createdUser;

    /** 作成日期 */
    private Date createdDatetime;

    /** 更新用户 */
    private String updatedUser;

    /** 更新日期 */
    private Date updatedDatetime;

    

    
    /**
     * @return the ormRowid
     */
    public Long getOrmRowid() {
      return ormRowid;
    }

    
    /**
     * @param ormRowid the ormRowid to set
     */
    public void setOrmRowid(Long ormRowid) {
      this.ormRowid = ormRowid;
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
     * @return the createdDatetime
     */
    public Date getCreatedDatetime() {
      return createdDatetime;
    }

    
    /**
     * @param createdDatetime the createdDatetime to set
     */
    public void setCreatedDatetime(Date createdDatetime) {
      this.createdDatetime = createdDatetime;
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

    
    /**
     * @return the updatedDatetime
     */
    public Date getUpdatedDatetime() {
      return updatedDatetime;
    }

    
    /**
     * @param updatedDatetime the updatedDatetime to set
     */
    public void setUpdatedDatetime(Date updatedDatetime) {
      this.updatedDatetime = updatedDatetime;
    }


    
    /**
     * @return the stockHoliDay
     */
    public String getStockHoliDay() {
      return stockHoliDay;
    }


    
    /**
     * @param stockHoliDay the stockHoliDay to set
     */
    public void setStockHoliDay(String stockHoliDay) {
      this.stockHoliDay = stockHoliDay;
    }
  }
  
 /**
  * 模块用户名的id;
  */
  public String getModuleId() {
    return "U1061611";
  }

 /**
  * 模块名
  */
  public String getModuleName() {
    // return
    // Messages.getString("web.bean.back.communication.PublicCouponListBean.0");
    return "仓库休息日管理";
  }

  
  /**
   * @return the pagerValue
   */
  public PagerValue getPagerValue() {
    return pagerValue;
  }

  
  /**
   * @param pagerValue the pagerValue to set
   */
  public void setPagerValue(PagerValue pagerValue) {
    this.pagerValue = pagerValue;
  }
  


  /**
   * @return the stockPageList
   */
  public List<String> getStockPageList() {
    return stockPageList;
  }

  
  /**
   * @param stockPageList the stockPageList to set
   */
  public void setStockPageList(List<String> stockPageList) {
    this.stockPageList = stockPageList;
  }

  


  
  /**
   * @return the stockHolidayList
   */
  public List<StockHolidayDetail> getStockHolidayList() {
    return stockHolidayList;
  }

  
  /**
   * @param stockHolidayList the stockHolidayList to set
   */
  public void setStockHolidayList(List<StockHolidayDetail> stockHolidayList) {
    this.stockHolidayList = stockHolidayList;
  }

  
  /**
   * @return the stockHoliDay
   */
  public String getStockHoliDay() {
    return stockHoliDay;
  }

  
  /**
   * @param stockHoliDay the stockHoliDay to set
   */
  public void setStockHoliDay(String stockHoliDay) {
    this.stockHoliDay = stockHoliDay;
  }

  
  /**
   * @return the ormRowid
   */
  public String getOrmRowid() {
    return ormRowid;
  }

  
  /**
   * @param ormRowid the ormRowid to set
   */
  public void setOrmRowid(String ormRowid) {
    this.ormRowid = ormRowid;
  }

  @Override
  public void setSubJspId() {
    
  }
}
