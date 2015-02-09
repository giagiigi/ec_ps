package jp.co.sint.webshop.service.analysis;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class CustomerGroupCampaignSummaryViewList implements Serializable {

	/** uid */
	private static final long serialVersionUID = 1L;

	/** 店铺编号 */
	private String shopCode;

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

	/** 顾客组别编号 */
	private String customerGroupNameEn;

	/** 顾客组别编号 */
	private String customerGroupNameJp;

	/** 订单金额 */
	private BigDecimal orderTotalPrice;

	/** 订单件数 */
	private Long orderTotalCount;

	/** 订单单价 */
	private BigDecimal orderUnitPrice;

	/** 优惠金额 */
	private BigDecimal campaignTotalPrice;

	/** 取消订单金额 */
	private BigDecimal cancelTotalPrice;

	/** 取消订单件数 */
	private Long cancelTotalCount;

	/** 取消订单单价 */
	private BigDecimal cancelUnitPrice;

	/** 优惠活动开始日时 */
	private Date campaignStartDatetime;

	/** 优惠活动结束日时 */
	private Date campaignEndDatetime;

	/** 取消优惠金额 */
	private BigDecimal cancelCampaignPrice;

	public String getShopCode() {
		return shopCode;
	}

	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
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

	public Long getOrderTotalCount() {
		return orderTotalCount;
	}

	public void setOrderTotalCount(Long orderTotalCount) {
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

	public Long getCancelTotalCount() {
		return cancelTotalCount;
	}

	public void setCancelTotalCount(Long cancelTotalCount) {
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
