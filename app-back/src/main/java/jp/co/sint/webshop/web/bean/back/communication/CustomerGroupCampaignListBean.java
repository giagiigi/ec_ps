package jp.co.sint.webshop.web.bean.back.communication;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.Datetime;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata; 
import jp.co.sint.webshop.web.bean.UISearchBean;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerValue;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U1050910:顾客组别优惠设定列表のデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class CustomerGroupCampaignListBean extends UIBackBean implements UISearchBean {
 
  /** serial version uid */
  private static final long serialVersionUID = 1L;
  
  private PagerValue pagerValue = new PagerValue();
  
  private List<CustomerGroupCampaignDetail> list = new ArrayList<CustomerGroupCampaignDetail>();
  
  @AlphaNum2
  @Length(16)
  @Metadata(name = "活动编号")
  private String searchCampaignCode;

  @Length(40)
  @Metadata(name = "活动名称")
  private String searchCampaignName;
  @Length(40)
  @Metadata(name = "活动名称(英文)")
  private String searchCampaignNameEn;
  @Length(40)
  @Metadata(name = "活动名称(日文)")
  private String searchCampaignNameJp;
  
  private String searchCustomerGroupCode;
  
  private String searchCampaignType;
  
  private String searchCampaignStatus;

  @Datetime(format = "yyyy/MM/dd HH:mm:ss")
  @Metadata(name = "活动开始时间(From)")
  private String searchStartDateFrom;
  
  @Datetime(format = "yyyy/MM/dd HH:mm:ss")
  @Metadata(name = "活动开始时间(To)")
  private String searchStartDateTo;
  
  @Datetime(format = "yyyy/MM/dd HH:mm:ss")
  @Metadata(name = "活动结束时间(From)")
  private String searchEndDateFrom;
  
  @Datetime(format = "yyyy/MM/dd HH:mm:ss")
  @Metadata(name = "活动结束时间(To)")
  private String searchEndDateTo;
  
  /** 更新权限持有区分 */
  private Boolean updateAuthorizeFlg;
  
  /** 删除权限持有区分 */
  private Boolean deleteAuthorizeFlg;
  
  /** 顾客组别列表 */
  private List<CodeAttribute> customerGroupList = new ArrayList<CodeAttribute>();

  public List<CustomerGroupCampaignDetail> getList() {
	return list;
  }

  public void setList(List<CustomerGroupCampaignDetail> list) {
	this.list = list;
  }

  public String getSearchCustomerGroupCode() {
	return searchCustomerGroupCode;
  }

  public void setSearchCustomerGroupCode(String searchCustomerGroupCode) {
	this.searchCustomerGroupCode = searchCustomerGroupCode;
  }

  public String getSearchCampaignType() {
	return searchCampaignType;
  }

  public void setSearchCampaignType(String searchCampaignType) {
	this.searchCampaignType = searchCampaignType;
  }

  public String getSearchCampaignStatus() {
	return searchCampaignStatus;
  }

  public void setSearchCampaignStatus(String searchCampaignStatus) {
	this.searchCampaignStatus = searchCampaignStatus;
  }

public String getSearchCampaignName() {
	return searchCampaignName;
  }

  public void setSearchCampaignName(String searchCampaignName) {
	this.searchCampaignName = searchCampaignName;
  }

  public String getSearchCampaignCode() {
	return searchCampaignCode;
  }

  public void setSearchCampaignCode(String searchCampaignCode) {
	this.searchCampaignCode = searchCampaignCode;
  }

  public String getSearchStartDateFrom() {
	return searchStartDateFrom;
  }

  public void setSearchStartDateFrom(String searchStartDateFrom) {
	this.searchStartDateFrom = searchStartDateFrom;
  }

  public String getSearchStartDateTo() {
	return searchStartDateTo;
  }

  public void setSearchStartDateTo(String searchStartDateTo) {
	this.searchStartDateTo = searchStartDateTo;
  }

  public String getSearchEndDateFrom() {
	return searchEndDateFrom;
  }

  public void setSearchEndDateFrom(String searchEndDateFrom) {
	this.searchEndDateFrom = searchEndDateFrom;
  }

  public String getSearchEndDateTo() {
	return searchEndDateTo;
  }

  public void setSearchEndDateTo(String searchEndDateTo) {
	this.searchEndDateTo = searchEndDateTo;
  }

  public List<CodeAttribute> getCustomerGroupList() {
	return customerGroupList;
  }

  public void setCustomerGroupList(List<CodeAttribute> customerGroupList) {
	this.customerGroupList = customerGroupList;
  }
  
  public Boolean getUpdateAuthorizeFlg() {
	return updateAuthorizeFlg;
  }

  public void setUpdateAuthorizeFlg(Boolean updateAuthorizeFlg) {
	this.updateAuthorizeFlg = updateAuthorizeFlg;
  }

  public Boolean getDeleteAuthorizeFlg() {
	return deleteAuthorizeFlg;
  }

  public void setDeleteAuthorizeFlg(Boolean deleteAuthorizeFlg) {
	this.deleteAuthorizeFlg = deleteAuthorizeFlg;
  }


/**
   * U1050910:顾客组别优惠设定列表のサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class CustomerGroupCampaignDetail implements Serializable {
	  
	private static final long serialVersionUID = 1L;

	private String campaignCode;
	  
	private String campaignName;
	
	private String customerGroupName;
	  
	private String campaignNameEn;
	
	private String customerGroupNameEn;
	  
	private String campaignNameJp;
	
	private String customerGroupNameJp;
	
	private String campaignType;
	
	private Boolean Fixed;
	
	private Long rate;
	
	private String campaignAmount;
	
	private String startDate;
	
	private String endDate;
	
	private BigDecimal minOrderAmount;
	
	private String personalUseLimit;

	public String getCampaignCode() {
		return campaignCode;
	}

	public void setCampaignCode(String campaignCode) {
		this.campaignCode = campaignCode;
	}

	public String getCampaignName() {
		return campaignName;
	}

	public void setCampaignName(String campaignName) {
		this.campaignName = campaignName;
	}

	public String getCustomerGroupName() {
		return customerGroupName;
	}

	public void setCustomerGroupName(String customerGroupName) {
		this.customerGroupName = customerGroupName;
	}

	public String getCampaignType() {
		return campaignType;
	}

	public void setCampaignType(String campaignType) {
		this.campaignType = campaignType;
	}

	public Boolean getFixed() {
		return Fixed;
	}

	public void setFixed(Boolean fixed) {
		Fixed = fixed;
	}

	public Long getRate() {
		return rate;
	}

	public void setRate(Long rate) {
		this.rate = rate;
	}

	public String getStartDate() {
		return startDate;
	}

	public String getCampaignAmount() {
		return campaignAmount;
	}

	public void setCampaignAmount(String campaignAmount) {
		this.campaignAmount = campaignAmount;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public BigDecimal getMinOrderAmount() {
		return minOrderAmount;
	}

	public void setMinOrderAmount(BigDecimal minOrderAmount) {
		this.minOrderAmount = minOrderAmount;
	}

	/**
	 * @param campaignNameEn the campaignNameEn to set
	 */
	public void setCampaignNameEn(String campaignNameEn) {
		this.campaignNameEn = campaignNameEn;
	}

	/**
	 * @return the campaignNameEn
	 */
	public String getCampaignNameEn() {
		return campaignNameEn;
	}

	/**
	 * @param customerGroupNameEn the customerGroupNameEn to set
	 */
	public void setCustomerGroupNameEn(String customerGroupNameEn) {
		this.customerGroupNameEn = customerGroupNameEn;
	}

	/**
	 * @return the customerGroupNameEn
	 */
	public String getCustomerGroupNameEn() {
		return customerGroupNameEn;
	}

	/**
	 * @param campaignNameJp the campaignNameJp to set
	 */
	public void setCampaignNameJp(String campaignNameJp) {
		this.campaignNameJp = campaignNameJp;
	}

	/**
	 * @return the campaignNameJp
	 */
	public String getCampaignNameJp() {
		return campaignNameJp;
	}

	/**
	 * @param customerGroupNameJp the customerGroupNameJp to set
	 */
	public void setCustomerGroupNameJp(String customerGroupNameJp) {
		this.customerGroupNameJp = customerGroupNameJp;
	}

	/**
	 * @return the customerGroupNameJp
	 */
	public String getCustomerGroupNameJp() {
		return customerGroupNameJp;
	}

  
  /**
   * @return the personalUseLimit
   */
  public String getPersonalUseLimit() {
    return personalUseLimit;
  }

  
  /**
   * @param personalUseLimit the personalUseLimit to set
   */
  public void setPersonalUseLimit(String personalUseLimit) {
    this.personalUseLimit = personalUseLimit;
  }
	
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
   *          設定する pagerValue
   */
  public void setPagerValue(PagerValue pagerValue) {
    this.pagerValue = pagerValue;
  }


  @Override
  public void createAttributes(RequestParameter reqparam) {
	setSearchCampaignCode(reqparam.get("searchCampaignCode"));
	setSearchCampaignName(reqparam.get("searchCampaignName"));
	setSearchCampaignNameEn(reqparam.get("searchCampaignNameEn"));
	setSearchCampaignNameJp(reqparam.get("searchCampaignNameJp"));
	setSearchCampaignType(reqparam.get("searchCampaignType"));
	setSearchCustomerGroupCode(reqparam.get("searchCustomerGroupCode"));
	setSearchStartDateFrom(reqparam.getDateTimeString("searchStartDateFrom"));
	setSearchStartDateTo(reqparam.getDateTimeString("searchStartDateTo"));
	setSearchEndDateFrom(reqparam.getDateTimeString("searchEndDateFrom"));
	setSearchEndDateTo(reqparam.getDateTimeString("searchEndDateTo"));
	setSearchCampaignStatus(reqparam.get("searchCampaignStatus"));
  }

  @Override
  public String getModuleId() {
	return "U1060510";
  }

  @Override
  public void setSubJspId() {
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return  Messages.getString("web.bean.back.communication.CustomerGroupCampaignListBean.0");
  }

/**
 * @param searchCampaignNameEn the searchCampaignNameEn to set
 */
public void setSearchCampaignNameEn(String searchCampaignNameEn) {
	this.searchCampaignNameEn = searchCampaignNameEn;
}

/**
 * @return the searchCampaignNameEn
 */
public String getSearchCampaignNameEn() {
	return searchCampaignNameEn;
}

/**
 * @param searchCampaignNameJp the searchCampaignNameJp to set
 */
public void setSearchCampaignNameJp(String searchCampaignNameJp) {
	this.searchCampaignNameJp = searchCampaignNameJp;
}

/**
 * @return the searchCampaignNameJp
 */
public String getSearchCampaignNameJp() {
	return searchCampaignNameJp;
}

    

}
