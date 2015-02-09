package jp.co.sint.webshop.web.bean.back.analysis;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
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
import jp.co.sint.webshop.web.text.back.Messages;;
/**
 * U1071010:顾客组别优惠分析のデータモデルです。
 * 
 * @author cxw
 */
public class CustomerGroupCampaignBean extends UIBackBean implements UISearchBean {

	/** serial version uid */
	private static final long serialVersionUID = 1L;

	/** 顾客组别优惠分析集合 */
	private List<CustomerGroupCampaignDetail> list = new ArrayList<CustomerGroupCampaignDetail>();

	@AlphaNum2
	@Length(16)
	@Metadata(name = "活动编号")
	private String searchCampaignCode;

	@Length(40)
	@Metadata(name = "活动名称")
	private String searchCampaignName;

	/** 顾客组编号 */
	private String searchCustomerCode;
	
	/** 优惠种别 */
	private String searchCampaignType;

	/** 活动状态 */
	private String searchCampaignStatus;

	/** 优惠开始时间From */
	@Datetime(format = "yyyy/MM/dd HH:mm:ss")
	@Metadata(name = "活动开始时间From")
	private String searchStartDateFrom;

	/** 优惠开始时间To */
	@Datetime(format = "yyyy/MM/dd HH:mm:ss")
	@Metadata(name = "活动开始时间To")
	private String searchStartDateTo;

	/** 优惠结束时间From */
	@Datetime(format = "yyyy/MM/dd HH:mm:ss")
	@Metadata(name = "活动结束时间From")
	private String searchEndDateFrom;

	/** 优惠结束时间To */
	@Datetime(format = "yyyy/MM/dd HH:mm:ss")
	@Metadata(name = "活动结束时间To")
	private String searchEndDateTo;

	/** 顾客组别列表 */
	private List<CodeAttribute> customerGroupList = new ArrayList<CodeAttribute>();
	
	/** 优惠种别列表 */
	private List<CodeAttribute> campaignTypeList = new ArrayList<CodeAttribute>();

	/** 页面分页 */
	private PagerValue pagerValue = new PagerValue();

	public List<CustomerGroupCampaignDetail> getList() {
		return list;
	}

	public void setList(List<CustomerGroupCampaignDetail> list) {
		this.list = list;
	}
	
	public List<CodeAttribute> getCampaignTypeList() {
		return campaignTypeList;
	}

	public void setCampaignTypeList(List<CodeAttribute> campaignTypeList) {
		this.campaignTypeList = campaignTypeList;
	}

	public String getSearchCustomerCode() {
		return searchCustomerCode;
	}

	public void setSearchCustomerCode(String searchCustomerCode) {
		this.searchCustomerCode = searchCustomerCode;
	}

	public String getSearchCampaignType() {
		return searchCampaignType;
	}

	public void setSearchCampaignType(String searchCampaignType) {
		this.searchCampaignType = searchCampaignType;
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

	public String getSearchCampaignStatus() {
		return searchCampaignStatus;
	}

	public void setSearchCampaignStatus(String searchCampaignStatus) {
		this.searchCampaignStatus = searchCampaignStatus;
	}

	/**
	 * U1050910:顾客组别优惠分析側アクセスログのサブモデルです。
	 * 
	 * @author cxw
	 */
	public static class CustomerGroupCampaignDetail implements Serializable {

		/** serial version uid */
		private static final long serialVersionUID = 1L;

		/** 优惠活动编号 */
		private String campaignCode;

		/** 优惠活动名称 */
		private String campaignName;
		/** 优惠活动名称 */
		private String campaignNameEn;
		/** 优惠活动名称 */
		private String campaignNameJp;

		/** 顾客组别编号 */
		private String customerGroupCode;

		/** 顾客组别编号 */
		private String customerGroupName;
		private String customerGroupNameEn;
		private String customerGroupNameJp;

		/** 订单金额 */
		private BigDecimal orderTotalPrice;

		/** 订单件数 */
		private String orderTotalCount;

		/** 订单单价 */
		private BigDecimal orderUnitPrice;

		/** 优惠金额 */
		private BigDecimal campaignTotalPrice;

		/** 取消订单金额 */
		private BigDecimal cancelTotalPrice;

		/** 取消订单件数 */
		private String cancelTotalCount;

		/** 取消订单单价 */
		private BigDecimal cancelUnitPrice;

		/** 取消优惠金额 */
		private BigDecimal cancelCampaignPrice;

		/** 优惠活动开始日时 */
		private Date campaignStartDatetime;

		/** 优惠活动结束日时 */
		private Date campaignEndDatetime;

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

		public String getCustomerGroupCode() {
			return customerGroupCode;
		}

		public void setCustomerGroupCode(String customerGroupCode) {
			this.customerGroupCode = customerGroupCode;
		}

		public String getCustomerGroupName() {
			return customerGroupName;
		}

		public void setCustomerGroupName(String customerGroupName) {
			this.customerGroupName = customerGroupName;
		}

		public BigDecimal getOrderTotalPrice() {
			return orderTotalPrice;
		}

		public void setOrderTotalPrice(BigDecimal orderTotalPrice) {
			this.orderTotalPrice = orderTotalPrice;
		}

		public String getOrderTotalCount() {
			return orderTotalCount;
		}

		public void setOrderTotalCount(String orderTotalCount) {
			this.orderTotalCount = orderTotalCount;
		}

		public BigDecimal getOrderUnitPrice() {
			return orderUnitPrice;
		}

		public void setOrderUnitPrice(BigDecimal orderUnitPrice) {
			this.orderUnitPrice = orderUnitPrice;
		}

		public BigDecimal getCampaignTotalPrice() {
			return campaignTotalPrice;
		}

		public void setCampaignTotalPrice(BigDecimal campaignTotalPrice) {
			this.campaignTotalPrice = campaignTotalPrice;
		}

		public BigDecimal getCancelTotalPrice() {
			return cancelTotalPrice;
		}

		public void setCancelTotalPrice(BigDecimal cancelTotalPrice) {
			this.cancelTotalPrice = cancelTotalPrice;
		}

		public String getCancelTotalCount() {
			return cancelTotalCount;
		}

		public void setCancelTotalCount(String cancelTotalCount) {
			this.cancelTotalCount = cancelTotalCount;
		}

		public BigDecimal getCancelUnitPrice() {
			return cancelUnitPrice;
		}

		public void setCancelUnitPrice(BigDecimal cancelUnitPrice) {
			this.cancelUnitPrice = cancelUnitPrice;
		}

		public BigDecimal getCancelCampaignPrice() {
			return cancelCampaignPrice;
		}

		public void setCancelCampaignPrice(BigDecimal cancelCampaignPrice) {
			this.cancelCampaignPrice = cancelCampaignPrice;
		}

		public Date getCampaignStartDatetime() {
			return campaignStartDatetime;
		}

		public void setCampaignStartDatetime(Date campaignStartDatetime) {
			this.campaignStartDatetime = campaignStartDatetime;
		}

		public Date getCampaignEndDatetime() {
			return campaignEndDatetime;
		}

		public void setCampaignEndDatetime(Date campaignEndDatetime) {
			this.campaignEndDatetime = campaignEndDatetime;
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

	/**
	 * リクエストパラメータから値を取得します。
	 * 
	 * @param reqparam
	 *            リクエストパラメータ
	 */
	public void createAttributes(RequestParameter reqparam) {
		setSearchCampaignCode(reqparam.get("searchCampaignCode"));
		setSearchCampaignName(reqparam.get("searchCampaignName"));
		setSearchCampaignType(reqparam.get("searchCampaignType"));
		setSearchCustomerCode(reqparam.get("searchCustomerGroupCode"));
		setSearchStartDateFrom(reqparam.getDateTimeString("searchStartDateFrom"));
		setSearchStartDateTo(reqparam.getDateTimeString("searchStartDateTo"));
		setSearchEndDateFrom(reqparam.getDateTimeString("searchEndDateFrom"));
		setSearchEndDateTo(reqparam.getDateTimeString("searchEndDateTo"));
		setSearchCampaignStatus(reqparam.get("searchCampaignStatus"));
	}

	@Override
	public String getModuleId() {
		return "U1071010";
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
		return  Messages.getString("web.bean.back.analysis.CustomerGroupCampaignBean.0");
	}

}
