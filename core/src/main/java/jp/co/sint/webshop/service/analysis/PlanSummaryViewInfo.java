package jp.co.sint.webshop.service.analysis;

import java.io.Serializable;
import java.math.BigDecimal;

public class PlanSummaryViewInfo implements Serializable {

	/** uid */
	private static final long serialVersionUID = 1L;

	/** 店铺编号 */
	private String shopCode;
	/**企划编号*/
	private String planCode;
	/**企划名称*/
	private String planName;
	/**企划名称*/
	private String planNameEn;
	/**企划名称*/
	private String planNameJp;
	/**企划类型*/
	private String planType;
	/**企划分类*/
	private String planDetailType;
	/**明细*/
	private String planDescription;
	/**明细*/
	private String planDescriptionEn;
	/**明细*/
	private String planDescriptionJp;
	/** 订单金额 */
	private BigDecimal orderTotalPrice;

	/** 订单件数 */
	private Long orderTotalCount;

	/** 优惠金额 */
	private BigDecimal campaignTotalPrice;

	/** 取消订单金额 */
	private BigDecimal cancelTotalPrice;
	
	/** 取消订单单价 */
	private BigDecimal cancelUnitPrice;
	/**已取消优惠金额*/
	private BigDecimal cancleCampaignPrice;

	/** 取消优惠金额 */
	private BigDecimal cancelCampaignPrice;
	private Long cancelTotalCount;
	/**订单类型*/
	private Long orderType;
	/**统计类别*/
	private Long summaryType;
	/**明细*/
	private String detailName;
	/**
	 * 取得shopCode
	 * @return shopCode
	 */
	public String getShopCode() {
		return shopCode;
	}
	/**
	 * 设定shopCode
	 * @param shopCode shopCode
	 */
	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}
	/**
	 * 取得企划编号
	 * @return  planCode
	 */
	public String getPlanCode() {
		return planCode;
	}
	/**
	 * 设定企划编号
	 * @param planCode planCode
	 */
	public void setPlanCode(String planCode) {
		this.planCode = planCode;
	}
	/**
	 * 取得企划名称
	 * @return planName
	 */
	public String getPlanName() {
		return planName;
	}
	/**
	 * 设定企划名称
	 * @param planName planName
	 */
	public void setPlanName(String planName) {
		this.planName = planName;
	}
	/**
	 * 取得企划分类
	 * @return planType
	 */
	public String getPlanType() {
		return planType;
	}
	/**
	 * 设定企划分类
	 * @param planType planType
	 */
	public void setPlanType(String planType) {
		this.planType = planType;
	}
	/**
	 * 取得明细
	 * @return planDescription
	 */
	public String getPlanDescription() {
		return planDescription;
	}
	/**
	 * 设定明细
	 * @param planDescription planDescription
	 */
	public void setPlanDescription(String planDescription) {
		this.planDescription = planDescription;
	}
	/**
	 * 取得已取消优惠金额
	 * @return cancleCampaignPrice
	 */
	public BigDecimal getCancleCampaignPrice() {
		return cancleCampaignPrice;
	}
	/**
	 * 设定已取消优惠金额
	 * @param cancleCampaignPrice cancleCampaignPrice
	 */
	public void setCancleCampaignPrice(BigDecimal cancleCampaignPrice) {
		this.cancleCampaignPrice = cancleCampaignPrice;
	}
	/**
	 * 取得订单金额 
	 * @return orderTotalPrice
	 */
	public BigDecimal getOrderTotalPrice() {
		return orderTotalPrice;
	}
	/**
	 * 设定订单金额 
	 * @param orderTotalPrice orderTotalPrice
	 */
	public void setOrderTotalPrice(BigDecimal orderTotalPrice) {
		this.orderTotalPrice = orderTotalPrice;
	}
	/**
	 * 取得订单件数 
	 * @return orderTotalCount
	 */ 
	public Long getOrderTotalCount() {
		return orderTotalCount;
	}
	/**
	 * 设定订单件数 
	 * @param orderTotalCount orderTotalCount
	 */
	public void setOrderTotalCount(Long orderTotalCount) {
		this.orderTotalCount = orderTotalCount;
	}
	/**
	 * 取得优惠金额 
	 * @return campaignTotalPrice
	 */
	public BigDecimal getCampaignTotalPrice() {
		return campaignTotalPrice;
	}
	/**
	 * 设定优惠金额 
	 * @param campaignTotalPrice campaignTotalPrice
	 */
	public void setCampaignTotalPrice(BigDecimal campaignTotalPrice) {
		this.campaignTotalPrice = campaignTotalPrice;
	}
	/**
	 * 取得取消订单金额 
	 * @return cancelTotalPrice
	 */
	public BigDecimal getCancelTotalPrice() {
		return cancelTotalPrice;
	}
	/**
	 * 设定取消订单金额 
	 * @param cancelTotalPrice cancelTotalPrice
	 */
	public void setCancelTotalPrice(BigDecimal cancelTotalPrice) {
		this.cancelTotalPrice = cancelTotalPrice;
	}
	/**
	 * 取得取消优惠金额 
	 * @return cancelUnitPrice
	 */
	public BigDecimal getCancelUnitPrice() {
		return cancelUnitPrice;
	}
	/**
	 * 设定取消优惠金额 
	 * @param cancelUnitPrice cancelUnitPrice
	 */
	public void setCancelUnitPrice(BigDecimal cancelUnitPrice) {
		this.cancelUnitPrice = cancelUnitPrice;
	}
	/**
	 * 取得取消优惠金额 
	 * @return  cancelCampaignPrice
	 */
	public BigDecimal getCancelCampaignPrice() {
		return cancelCampaignPrice;
	}
	/**
	 * 设定取消优惠金额 
	 * @param cancelCampaignPrice cancelCampaignPrice
	 */
	public void setCancelCampaignPrice(BigDecimal cancelCampaignPrice) {
		this.cancelCampaignPrice = cancelCampaignPrice;
	}
	/**
	 * 取得取消商品件数
	 * @return cancleTotalCount
	 */
	public Long getCancelTotalCount() {
		return cancelTotalCount;
	}
	/**
	 * 设定取消商品件数
	 * @param cancleTotalCount cancleTotalCount
	 */
	public void setCancelTotalCount(Long cancelTotalCount) {
		this.cancelTotalCount = cancelTotalCount;
	}
	/**
	 * 取得orderType
	 * @return orderType
	 */
	public Long getOrderType() {
		return orderType;
	}
	/**
	 * 设定orderType
	 * @param orderType orderType
	 */
	public void setOrderType(Long orderType) {
		this.orderType = orderType;
	}
	/**
	 * 取得summaryType
	 * @return summaryType
	 */
	public Long getSummaryType() {
		return summaryType;
	}
	/**
	 * 设定summaryType
	 * @param summaryType summaryType
	 */
	public void setSummaryType(Long summaryType) {
		this.summaryType = summaryType;
	}
	/**
	 * 取得企划分类
	 * @return planDetailType
	 */
	public String getPlanDetailType() {
		return planDetailType;
	}	
	/**
	 * 设定企划分类
	 * @param planDetailType planDetailType
	 */
	public void setPlanDetailType(String planDetailType) {
		this.planDetailType = planDetailType;
	}
	/**
	 * 取得明细
	 * @return detailName
	 */
	public String getDetailName() {
		return detailName;
	}
	/**
	 * 设定明细
	 * @param detailName detailName
	 */
	public void setDetailName(String detailName) {
		this.detailName = detailName;
	}
	/**
	 * @param planNameEn the planNameEn to set
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
	 * @param planNameJp the planNameJp to set
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
	/**
	 * @param planDescriptionEn the planDescriptionEn to set
	 */
	public void setPlanDescriptionEn(String planDescriptionEn) {
		this.planDescriptionEn = planDescriptionEn;
	}
	/**
	 * @return the planDescriptionEn
	 */
	public String getPlanDescriptionEn() {
		return planDescriptionEn;
	}
	/**
	 * @param planDescriptionJp the planDescriptionJp to set
	 */
	public void setPlanDescriptionJp(String planDescriptionJp) {
		this.planDescriptionJp = planDescriptionJp;
	}
	/**
	 * @return the planDescriptionJp
	 */
	public String getPlanDescriptionJp() {
		return planDescriptionJp;
	}

}
