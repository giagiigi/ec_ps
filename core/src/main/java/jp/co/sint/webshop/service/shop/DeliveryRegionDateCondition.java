package jp.co.sint.webshop.service.shop;

import jp.co.sint.webshop.service.SearchCondition;

public class DeliveryRegionDateCondition extends SearchCondition {
	/** Serial Version UID */
	private static final long serialVersionUID = 1L;
	//配送公司编号
	private String deliveryCompanyNo;
	//地域编号
	private String regionBlockId;
	private String shopCode;
	
	/**
	 * 取得配送公司编号
	 * @return deliveryCompanyNo
	 */
	public String getDeliveryCompanyNo() {
		return deliveryCompanyNo;
	}
	/**
	 * 设置配送公司编号
	 * @param deliveryCompanyNo deliveryCompanyNo
	 */
	public void setDeliveryCompanyNo(String deliveryCompanyNo) {
		this.deliveryCompanyNo = deliveryCompanyNo;
	}
	/**
	 * 取得地域编号
	 * @return 地域编号
	 */
	public String getRegionBlockId() {
		return regionBlockId;
	}
	/**
	 * 设置地域编号
	 * @param regionBlockId regionBlockId
	 */
	public void setRegionBlockId(String regionBlockId) {
		this.regionBlockId = regionBlockId;
	}
	/**
	 * 取得shopCode
	 * @return shopCode
	 */
	public String getShopCode() {
		return shopCode;
	}
	/**
	 * 设置shopCode
	 * @param shopCode shopCode
	 */
	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}
	
}
