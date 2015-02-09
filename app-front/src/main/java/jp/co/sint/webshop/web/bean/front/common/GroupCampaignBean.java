package jp.co.sint.webshop.web.bean.front.common;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.web.bean.UISubBean;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * インフォメーションのデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class GroupCampaignBean extends UISubBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private String campaignName;

  private String customerGroupCode;

  private Long campaignType;

  private Long campaignProportion;

  private BigDecimal campaignAmount;

  private Date campaignStartDatetime;

  private Date campaignEndDatetime;

  private BigDecimal minOrderAmount;

  private String customerGroupName;

  private List<CampaignDetailBean> campaignList = new ArrayList<CampaignDetailBean>();

  /**
   * インフォメーションのサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class CampaignDetailBean implements Serializable {

    /** serial version uid */
    private static final long serialVersionUID = 1L;

    private String campaignName;

    private String customerGroupCode;

    private Long campaignType;

    private Long campaignProportion;

    private BigDecimal campaignAmount;

    private Date campaignStartDatetime;

    private Date campaignEndDatetime;

    private BigDecimal minOrderAmount;

    private String customerGroupName;

    
    /**
     * @return the campaignName
     */
    public String getCampaignName() {
      return campaignName;
    }

    
    /**
     * @param campaignName the campaignName to set
     */
    public void setCampaignName(String campaignName) {
      this.campaignName = campaignName;
    }

    
    /**
     * @return the customerGroupCode
     */
    public String getCustomerGroupCode() {
      return customerGroupCode;
    }

    
    /**
     * @param customerGroupCode the customerGroupCode to set
     */
    public void setCustomerGroupCode(String customerGroupCode) {
      this.customerGroupCode = customerGroupCode;
    }

    
    /**
     * @return the campaignType
     */
    public Long getCampaignType() {
      return campaignType;
    }

    
    /**
     * @param campaignType the campaignType to set
     */
    public void setCampaignType(Long campaignType) {
      this.campaignType = campaignType;
    }

    
    /**
     * @return the campaignProportion
     */
    public Long getCampaignProportion() {
      return campaignProportion;
    }

    
    /**
     * @param campaignProportion the campaignProportion to set
     */
    public void setCampaignProportion(Long campaignProportion) {
      this.campaignProportion = campaignProportion;
    }

    
    /**
     * @return the campaignAmount
     */
    public BigDecimal getCampaignAmount() {
      return campaignAmount;
    }

    
    /**
     * @param campaignAmount the campaignAmount to set
     */
    public void setCampaignAmount(BigDecimal campaignAmount) {
      this.campaignAmount = campaignAmount;
    }

    
    /**
     * @return the campaignStartDatetime
     */
    public Date getCampaignStartDatetime() {
      return DateUtil.immutableCopy(campaignStartDatetime);
    }

    
    /**
     * @param campaignStartDatetime the campaignStartDatetime to set
     */
    public void setCampaignStartDatetime(Date campaignStartDatetime) {
      this.campaignStartDatetime = campaignStartDatetime;
    }

    
    /**
     * @return the campaignEndDatetime
     */
    public Date getCampaignEndDatetime() {
      return DateUtil.immutableCopy(campaignEndDatetime);
    }

    
    /**
     * @param campaignEndDatetime the campaignEndDatetime to set
     */
    public void setCampaignEndDatetime(Date campaignEndDatetime) {
      this.campaignEndDatetime = campaignEndDatetime;
    }

    
    /**
     * @return the minOrderAmount
     */
    public BigDecimal getMinOrderAmount() {
      return minOrderAmount;
    }

    
    /**
     * @param minOrderAmount the minOrderAmount to set
     */
    public void setMinOrderAmount(BigDecimal minOrderAmount) {
      this.minOrderAmount = minOrderAmount;
    }

    
    /**
     * @return the customerGroupName
     */
    public String getCustomerGroupName() {
      return customerGroupName;
    }

    
    /**
     * @param customerGroupName the customerGroupName to set
     */
    public void setCustomerGroupName(String customerGroupName) {
      this.customerGroupName = customerGroupName;
    }
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
   * @return the campaignName
   */
  public String getCampaignName() {
    return campaignName;
  }

  
  /**
   * @param campaignName the campaignName to set
   */
  public void setCampaignName(String campaignName) {
    this.campaignName = campaignName;
  }

  
  /**
   * @return the customerGroupCode
   */
  public String getCustomerGroupCode() {
    return customerGroupCode;
  }

  
  /**
   * @param customerGroupCode the customerGroupCode to set
   */
  public void setCustomerGroupCode(String customerGroupCode) {
    this.customerGroupCode = customerGroupCode;
  }

  
  /**
   * @return the campaignType
   */
  public Long getCampaignType() {
    return campaignType;
  }

  
  /**
   * @param campaignType the campaignType to set
   */
  public void setCampaignType(Long campaignType) {
    this.campaignType = campaignType;
  }

  
  /**
   * @return the campaignProportion
   */
  public Long getCampaignProportion() {
    return campaignProportion;
  }

  
  /**
   * @param campaignProportion the campaignProportion to set
   */
  public void setCampaignProportion(Long campaignProportion) {
    this.campaignProportion = campaignProportion;
  }

  
  /**
   * @return the campaignAmount
   */
  public BigDecimal getCampaignAmount() {
    return campaignAmount;
  }

  
  /**
   * @param campaignAmount the campaignAmount to set
   */
  public void setCampaignAmount(BigDecimal campaignAmount) {
    this.campaignAmount = campaignAmount;
  }

  
  /**
   * @return the campaignStartDatetime
   */
  public Date getCampaignStartDatetime() {
    return DateUtil.immutableCopy(campaignStartDatetime);
  }

  
  /**
   * @param campaignStartDatetime the campaignStartDatetime to set
   */
  public void setCampaignStartDatetime(Date campaignStartDatetime) {
    this.campaignStartDatetime = campaignStartDatetime;
  }

  
  /**
   * @return the campaignEndDatetime
   */
  public Date getCampaignEndDatetime() {
    return DateUtil.immutableCopy(campaignEndDatetime);
  }

  
  /**
   * @param campaignEndDatetime the campaignEndDatetime to set
   */
  public void setCampaignEndDatetime(Date campaignEndDatetime) {
    this.campaignEndDatetime = campaignEndDatetime;
  }

  
  /**
   * @return the minOrderAmount
   */
  public BigDecimal getMinOrderAmount() {
    return minOrderAmount;
  }

  
  /**
   * @param minOrderAmount the minOrderAmount to set
   */
  public void setMinOrderAmount(BigDecimal minOrderAmount) {
    this.minOrderAmount = minOrderAmount;
  }

  
  /**
   * @return the customerGroupName
   */
  public String getCustomerGroupName() {
    return customerGroupName;
  }

  
  /**
   * @param customerGroupName the customerGroupName to set
   */
  public void setCustomerGroupName(String customerGroupName) {
    this.customerGroupName = customerGroupName;
  }

  
  /**
   * @return the campaignList
   */
  public List<CampaignDetailBean> getCampaignList() {
    return campaignList;
  }

  
  /**
   * @param campaignList the campaignList to set
   */
  public void setCampaignList(List<CampaignDetailBean> campaignList) {
    this.campaignList = campaignList;
  }
}
