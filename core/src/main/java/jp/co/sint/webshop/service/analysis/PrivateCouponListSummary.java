package jp.co.sint.webshop.service.analysis;

import java.io.Serializable;
import java.math.BigDecimal;

public class PrivateCouponListSummary implements Serializable {

  /** serial version UID */
  private static final long serialVersionUID = 1L;

  // 优惠券规则编号
  private String couponIssueNo;
  // 规则编号
	private String couponCode;
	

	// 规则名称
	private String couponName;

	// 优惠券种别
	private Long couponType;

	// 订单金额
	private BigDecimal orderTotalPrice;

	// 订单个数
	private Long orderTotalCount;

	// 订单单价
	private BigDecimal orderUnitPrice;
	// 新订单
	private Long firstOrderCount;
	// 发行件数
	private Long issueTotalCount;
	// 优惠金额
	private BigDecimal campaignTotalPrice;
	// 取消订单个数
	private Long cancelTotalCount;
	// 取消订单金额
	private BigDecimal cancelTotalPrice;
	// 取消订单单价
	private BigDecimal cancelUnitPrice;
	// 取消优惠金额
	private BigDecimal cancelCampaignPrice;
	
	/**
	 *取得 规则编号
	 * 
	 * */
	public String getCouponCode() {
		return couponCode;
	}
	
	/**
	 *设置规则编号
	 * 
	 * */
	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}
	
	/**
	 *取得 规则名称
	 * 
	 * */
	public String getCouponName() {
		return couponName;
	}
	
	/**
	 * 
	 *设置规则名称
	 * 
	 * */
	public void setCouponName(String couponName) {
		this.couponName = couponName;
	}
	
	/**
	 *取得优惠券种别
	 * 
	 * */
	public Long getCouponType() {
		return couponType;
	}
	
	/**
	 *设置优惠券种别
	 * 
	 * */
	public void setCouponType(Long couponIssueType) {
		this.couponType = couponIssueType;
	}
	
	/**
	 *取得 订单金额
	 * 
	 * */
	public BigDecimal getOrderTotalPrice() {
		return orderTotalPrice;
	}
	/**
	 *设置订单金额
	 * 
	 * */
	public void setOrderTotalPrice(BigDecimal orderTotalPrice) {
		this.orderTotalPrice = orderTotalPrice;
	}
	
	/**
	 *取得 订单个数
	 * 
	 * */
	public Long getOrderTotalCount() {
		return orderTotalCount;
	}
	/**
	 *设置订单个数
	 * 
	 * */
	public void setOrderTotalCount(Long orderTotalCount) {
		this.orderTotalCount = orderTotalCount;
	}
	
	/**
	 *取得 订单单价
	 * 
	 * */
	public BigDecimal getOrderUnitPrice() {
		return orderUnitPrice;
	}
	/**
	 *设置订单单价
	 * 
	 * */
	public void setOrderUnitPrice(BigDecimal orderUnitPrice) {
		this.orderUnitPrice = orderUnitPrice;
	}
	
	/**
	 *取得 新订单
	 * 
	 * */
	public Long getFirstOrderCount() {
		return firstOrderCount;
	}
	/**
	 *设置新订单
	 * 
	 * */
	public void setFirstOrderCount(Long firstOrderCount) {
		this.firstOrderCount = firstOrderCount;
	}
		
	/**
	 *取得 发行件数
	 * 
	 * */
	public Long getIssueTotalCount() {
		return issueTotalCount;
	}
	
	/**
	 *设置发行件数
	 * 
	 * */
	public void setIssueTotalCount(Long issueTotalCount) {
		this.issueTotalCount = issueTotalCount;
	}
	
	/**
	 *取得 优惠金额
	 * 
	 * */
	public BigDecimal getCampaignTotalPrice() {
		return campaignTotalPrice;
	}
	
	/**
	 *设置优惠金额
	 * 
	 * */
	public void setCampaignTotalPrice(BigDecimal campaignTotalPrice) {
		this.campaignTotalPrice = campaignTotalPrice;
	}
	
	/**
	 *取得 取消订单个数
	 * 
	 * */
	public Long getCancelTotalCount() {
		return cancelTotalCount;
	}
	
	/**
	 *设置取消订单个数
	 * 
	 * */
	public void setCancelTotalCount(Long cancelTotalCount) {
		this.cancelTotalCount = cancelTotalCount;
	}
	
	/**
	 *取得 取消订单金额
	 * 
	 * */
	public BigDecimal getCancelTotalPrice() {
		return cancelTotalPrice;
	}
	
	/**
	 *设置取消订单金额
	 * 
	 * */
	public void setCancelTotalPrice(BigDecimal cancelTotalPrice) {
		this.cancelTotalPrice = cancelTotalPrice;
	}
	
	/**
	 *取得 取消订单单价
	 * 
	 * */
	public BigDecimal getCancelUnitPrice() {
		return cancelUnitPrice;
	}
	
	/**
	 *设置取消订单单价
	 * 
	 * */
	public void setCancelUnitPrice(BigDecimal cancelUnitPrice) {
		this.cancelUnitPrice = cancelUnitPrice;
	}
	
	/**
	 *取得 取消优惠金额
	 * 
	 * */
	public BigDecimal getCancelCampaignPrice() {
		return cancelCampaignPrice;
	}
	
	/**
	 *设置取消优惠金额
	 * 
	 * */
	public void setCancelCampaignPrice(BigDecimal cancelCampaign) {
		this.cancelCampaignPrice = cancelCampaign;
	}

	public String getCouponIssueNo() {
		return couponIssueNo;
	}

	public void setCouponIssueNo(String couponIssueNo) {
		this.couponIssueNo = couponIssueNo;
	}

}
