package jp.co.sint.webshop.web.bean.back.communication;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.Datetime;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.domain.PlanType;
import jp.co.sint.webshop.web.bean.UISearchBean;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerValue;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U1030740:企划设定一览のデータモデルです。
 * 
 * @author OB.
 */
public class PlanListBean extends UIBackBean implements UISearchBean {

	/** serial version uid */
	private static final long serialVersionUID = 1L;

	private PagerValue pagerValue = new PagerValue();

	/** 企划类型模式 */
	private String planTypeMode;

	@AlphaNum2
	@Length(16)
	@Metadata(name = "企划编号")
	private String planCode;

	@Length(40)
	@Metadata(name = "企划名称")
	private String planName;

	// add by cs_yuli 20120515 start
	/** 企划英文名称 ***/
	@Length(40)
	@Metadata(name = "企划英文名称")
	private String planNameEn;
	/** 企划日文名称 ***/
	@Length(40)
	@Metadata(name = "企划日文名称")
	private String planNameJp;

	// add by cs_yuli 20120515 end

	@Metadata(name = "企划类型")
	private String planType;

	@Datetime(format = "yyyy/MM/dd HH:mm:ss")
	@Metadata(name = "企划开始时间(From)")
	private String searchStartDateFrom;

	@Datetime(format = "yyyy/MM/dd HH:mm:ss")
	@Metadata(name = "企划开始时间(To)")
	private String searchStartDateTo;

	@Datetime(format = "yyyy/MM/dd HH:mm:ss")
	@Metadata(name = "企划结束时间(From)")
	private String searchEndDateFrom;

	@Datetime(format = "yyyy/MM/dd HH:mm:ss")
	@Metadata(name = "企划结束时间(To)")
	private String searchEndDateTo;

	/** 更新权限持有区分 */
	private Boolean updateAuthorizeFlg;

	/** 删除权限持有区分 */
	private Boolean deleteAuthorizeFlg;

	/** 企划类型集合 */
	private List<NameValue> planDetailTypeList = new ArrayList<NameValue>();

	private List<PlanDetailBean> list = new ArrayList<PlanDetailBean>();

	private String searchCampaignStatus;
	
	// 20130702 txw add start
	/** 特辑区分 */
	private Boolean tjFlg;
	// 20130702 txw add end

	public String getPlanTypeMode() {
		return planTypeMode;
	}

	public void setPlanTypeMode(String planTypeMode) {
		this.planTypeMode = planTypeMode;
	}

	public String getPlanCode() {
		return planCode;
	}

	public void setPlanCode(String planCode) {
		this.planCode = planCode;
	}

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public String getPlanType() {
		return planType;
	}

	public void setPlanType(String planType) {
		this.planType = planType;
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

	public List<NameValue> getPlanDetailTypeList() {
		return planDetailTypeList;
	}

	public void setPlanDetailTypeList(List<NameValue> planDetailTypeList) {
		this.planDetailTypeList = planDetailTypeList;
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

	public List<PlanDetailBean> getList() {
		return list;
	}

	public void setList(List<PlanDetailBean> list) {
		this.list = list;
	}

	public String getSearchCampaignStatus() {
		return searchCampaignStatus;
	}

	public void setSearchCampaignStatus(String searchCampaignStatus) {
		this.searchCampaignStatus = searchCampaignStatus;
	}
	
  /**
   * @return the tjFlg
   */
  public Boolean getTjFlg() {
    return tjFlg;
  }

  
  /**
   * @param tjFlg the tjFlg to set
   */
  public void setTjFlg(Boolean tjFlg) {
    this.tjFlg = tjFlg;
  }



  /**
	 * U1030740:企划设定一览のサブモデルです。
	 * 
	 * @author System Integrator Corp.
	 */
	public static class PlanDetailBean implements Serializable {

		private static final long serialVersionUID = 1L;

		private String planCode;

		private String planName;

		private String planType;

		private String startDate;

		private String endDate;

		public String getPlanCode() {
			return planCode;
		}

		public void setPlanCode(String planCode) {
			this.planCode = planCode;
		}

		public String getPlanName() {
			return planName;
		}

		public void setPlanName(String planName) {
			this.planName = planName;
		}

		public String getPlanType() {
			return planType;
		}

		public void setPlanType(String planType) {
			this.planType = planType;
		}

		public String getStartDate() {
			return startDate;
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
	 *            設定する pagerValue
	 */
	public void setPagerValue(PagerValue pagerValue) {
		this.pagerValue = pagerValue;
	}

	@Override
	public void createAttributes(RequestParameter reqparam) {
		setPlanCode(reqparam.get("planCode"));
		setPlanName(reqparam.get("planName"));
		setPlanNameEn(reqparam.get("planNameEn"));
		setPlanNameJp(reqparam.get("planNameJp"));
		setPlanType(reqparam.get("planType"));
		setSearchStartDateFrom(reqparam
				.getDateTimeString("searchStartDateFrom"));
		setSearchStartDateTo(reqparam.getDateTimeString("searchStartDateTo"));
		setSearchEndDateFrom(reqparam.getDateTimeString("searchEndDateFrom"));
		setSearchEndDateTo(reqparam.getDateTimeString("searchEndDateTo"));
		setSearchCampaignStatus(reqparam.get("searchCampaignStatus"));
	}

	@Override
	public String getModuleId() {
		if (PlanType.PROMOTION.getValue().equals(getPlanTypeMode())) {
			return "U1060810";
		} else if (PlanType.FEATURE.getValue().equals(getPlanTypeMode())) {
			return "U1060910";
		} else {
			return "";
		}
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
		if (PlanType.PROMOTION.getValue().equals(getPlanTypeMode())) {
			return Messages.getString("web.bean.back.communication.PlanListBean.0");
		} else if (PlanType.FEATURE.getValue().equals(getPlanTypeMode())) {
			return Messages.getString("web.bean.back.communication.PlanListBean.1");
		} else {
			return "";
		}
	}

	/**
	 * @param planNameEn
	 *            the planNameEn to set
	 */
	public void setPlanNameEn(String planNameEn) {
		this.planNameEn = planNameEn;
	}

	/**
	 * @return the planNameEn
	 */
	public String getPlanNameEn() {
		return planNameEn;
	}

	/**
	 * @param planNameJp
	 *            the planNameJp to set
	 */
	public void setPlanNameJp(String planNameJp) {
		this.planNameJp = planNameJp;
	}

	/**
	 * @return the planNameJp
	 */
	public String getPlanNameJp() {
		return planNameJp;
	}

}
