package jp.co.sint.webshop.service.shop;

import java.io.Serializable;
import java.math.BigDecimal;
/**
 * 配送希望日一览bean
 * @author 
 */
public class DeliveryRegionDateInfo implements Serializable{

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;
  
  private String shopCode;
	//配送公司编号
	private String deliveryCompanyNo;
	// 配送公司名
	private String deliveryCompanyName;
	//地域编号
	private Long regionBlockId;
	// 地域名
	private String regionBlockName;
	//配送时间段Id
	private Long deliveryAppointedTimeCode;

	//配送时间段指定时的手续费
	private BigDecimal deliveryDatetimeCommission;

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
	 * 取得配送公司名
	 * @return deliveryCompanyName
	 */
	public String getDeliveryCompanyName() {
		return deliveryCompanyName;
	}
	/**
	 * 设置配送公司名
	 * @param deliveryCompanyName deliveryCompanyName
	 */
	public void setDeliveryCompanyName(String deliveryCompanyName) {
		this.deliveryCompanyName = deliveryCompanyName;
	}
	/**
	 * 取得地域编号
	 * @return  regionBlockId
	 */
	public Long getRegionBlockId() {
		return regionBlockId;
	}
	/**
	 * 设置地域编号
	 * @param regionBlockId regionBlockId
	 */
	public void setRegionBlockId(Long regionBlockId) {
		this.regionBlockId = regionBlockId;
	}
    /**
     * 取得地域名
     * @return regionBlockName
     */
	public String getRegionBlockName() {
		return regionBlockName;
	}
    /**
     * 设置地域名
     * @param regionBlockName regionBlockName
     */
	public void setRegionBlockName(String regionBlockName) {
		this.regionBlockName = regionBlockName;
	}
  
    /**
     * 取得配送时间段指定时的手续费
     * @return deliveryDatetimeCommission
     */
	public BigDecimal getDeliveryDatetimeCommission() {
		return deliveryDatetimeCommission;
	}
    /**
     * 设置配送时间段指定时的手续费
     * @param deliveryDatetimeCommission deliveryDatetimeCommission
     */
	public void setDeliveryDatetimeCommission(BigDecimal deliveryDatetimeCommission) {
		this.deliveryDatetimeCommission = deliveryDatetimeCommission;
	}
	/**
	 * 取得配送时间段Id
	 * @return deliveryAppointedTimeCode
	 */
	public Long getDeliveryAppointedTimeCode() {
		return deliveryAppointedTimeCode;
	}
	/**
	 * 设置配送时间段Id
	 * @param deliveryAppointedTimeCode deliveryAppointedTimeCode
	 */
	public void setDeliveryAppointedTimeCode(Long deliveryAppointedTimeCode) {
		this.deliveryAppointedTimeCode = deliveryAppointedTimeCode;
	}
	
}
