package jp.co.sint.webshop.web.bean.back.communication;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.Datetime;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.web.bean.UISearchBean;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.webutility.PagerValue;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U1061010:促销活动管理。
 * 
 * @author KS.
 */
public class NewCampaignListBean extends UIBackBean implements UISearchBean {

	/** serial version uid */
	private static final long serialVersionUID = 1L;

	private PagerValue pagerValue = new PagerValue();

	@AlphaNum2
	@Length(16)
	@Metadata(name = "活动编号")
	private String campaignCode;

	@Length(50)
	@Metadata(name = "活动名称")
	private String campaignName;

	/** 英文名称 ***/
	@Length(100)
	@Metadata(name = "活动英文名称")
	private String campaignNameEn;
	
	/** 日文名称 ***/
	@Length(100)
	@Metadata(name = "活动日文名称")
	private String campaignNameJp;

	@Metadata(name = "活动类型")
	private String campaignType;

	@Datetime(format = "yyyy/MM/dd HH:mm:ss")
	@Metadata(name = "活动开始时间(From)")
	private String campaignStartDateFrom;

	@Datetime(format = "yyyy/MM/dd HH:mm:ss")
	@Metadata(name = "活动结束时间(To)")
	private String campaignEndDateTo;

	/** 更新权限持有区分 */
	private Boolean updateAuthorizeFlg;

	/** 删除权限持有区分 */
	private Boolean deleteAuthorizeFlg;

	/** 活动类型集合 */
	private List<CodeAttribute> campaignDetailTypeList = new ArrayList<CodeAttribute>();

	private List<CampaignDetailBean> list = new ArrayList<CampaignDetailBean>();

	private String searchCampaignStatus;

	public String getCampaignStartDateFrom() {
		return campaignStartDateFrom;
	}

	public void setCampaignStartDateFrom(String campaignStartDateFrom) {
		this.campaignStartDateFrom = campaignStartDateFrom;
	}

	/**
   * @return the campaignEndDateTo
   */
  public String getCampaignEndDateTo() {
    return campaignEndDateTo;
  }

  /**
   * @param campaignEndDateTo the campaignEndDateTo to set
   */
  public void setCampaignEndDateTo(String campaignEndDateTo) {
    this.campaignEndDateTo = campaignEndDateTo;
  }

  public List<CampaignDetailBean> getList() {
		return list;
	}

	public void setList(List<CampaignDetailBean> list) {
		this.list = list;
	}

	public List<CodeAttribute> getCampaignDetailTypeList() {
		return campaignDetailTypeList;
	}

	public void setCampaignDetailTypeList(List<CodeAttribute> campaignDetailTypeList) {
		this.campaignDetailTypeList = campaignDetailTypeList;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getCampaignNameEn() {
		return campaignNameEn;
	}

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

	public String getCampaignType() {
		return campaignType;
	}

	public void setCampaignType(String campaignType) {
		this.campaignType = campaignType;
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


	public String getSearchCampaignStatus() {
		return searchCampaignStatus;
	}

	public void setSearchCampaignStatus(String searchCampaignStatus) {
		this.searchCampaignStatus = searchCampaignStatus;
	}

	/**
	 * U1061010:促销活动のサブモデルです。
	 * 
	 * @author System Integrator Corp.
	 */
	public static class CampaignDetailBean implements Serializable {

		private static final long serialVersionUID = 1L;
    //活动编号
		private String campaignCode;
    //活动名称
		private String campaignName;
		//活动类型 
		private String campaignType;
		//活动开始时间    
		private String campaignStartDate;
		//活动结束时间
		private String campaignEndDate;
 
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

		public String getCampaignType() {
			return campaignType;
		}

		public void setCampaignType(String campaignType) {
			this.campaignType = campaignType;
		}

		public String getCampaignStartDate() {
			return campaignStartDate;
		}

		public void setCampaignStartDate(String campaignStartDate) {
			this.campaignStartDate = campaignStartDate;
		}
		public String getCampaignEndDate() {
			return campaignEndDate;
		}

		public void setCampaignEndDate(String campaignEndDate) {
			this.campaignEndDate = campaignEndDate;
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
		setCampaignCode(reqparam.get("campaignCode"));
		setCampaignName(reqparam.get("campaignName"));
		setCampaignNameEn(reqparam.get("campaignNameEn"));
		setCampaignNameJp(reqparam.get("campaignNameJp"));
		setCampaignType(reqparam.get("campaignType"));
		setCampaignStartDateFrom(reqparam.getDateTimeString("campaignStartDateFrom"));
		setCampaignEndDateTo(reqparam.getDateTimeString("campaignEndDateTo"));
		setSearchCampaignStatus(reqparam.get("searchCampaignStatus"));
	}

	@Override
	public String getModuleId() {
	  return "U1061010";
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
      return "促销活动管理";
	}

	/**
	 * @param planNameEn
	 *            the planNameEn to set
	 */
	public void setCampaignNameEn(String campaignNameEn) {
		this.campaignNameEn = campaignNameEn;
	}

	/**
	 * @return the planNameEn
	 */
	public String getcampaignNameEn() {
		return campaignNameEn;
	}

	/**
	 * @param planNameJp
	 *            the planNameJp to set
	 */
	public void setCampaignNameJp(String campaignNameJp) {
		this.campaignNameJp = campaignNameJp;
	}

	/**
	 * @return the planNameJp
	 */
	public String getCampaignNameJp() {
		return campaignNameJp;
	}

}
