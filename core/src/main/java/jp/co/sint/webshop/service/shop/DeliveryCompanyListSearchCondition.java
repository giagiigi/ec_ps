package jp.co.sint.webshop.service.shop;

import java.math.BigDecimal;

import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.Currency;
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.service.SearchCondition;

public class DeliveryCompanyListSearchCondition extends SearchCondition {

	/** serial version uid */
	private static final long serialVersionUID = 1L;

	/** 店铺编号 */
	@AlphaNum2
	@Length(16)
	@Metadata(name = "ショップコード", order = 1)
	private String shopCode;

	/** 配送公司编号 */
	@Required
	@Length(16)
	@Metadata(name = "配送公司编号", order = 2)
	private String deliveryCompanyNo;

	/** 配送公司名称 */
	@Required
	@Length(40)
	@Metadata(name = "配送公司编号", order = 3)
	private String deliveryCompanyName;

	/** 支持配送指定日时区分(0:无指定；1:日期指定；2:时间指定；3:日时指定) */
	@Required
	@Digit
	@Length(1)
	@Metadata(name = "支持配送指定日时区分", order = 4)
	private Long deliverySpecificationType;

	/** 是否支持先支付区分(0:不支持;1;支持) */
	@Required
	@Length(1)
	@Digit
	@Metadata(name = "是否支持先支付区分", order = 5)
	private Long deliveryPaymentAdvanceFlg;

	/** 是否支持后支付区分(0:不支持;1;支持) */
	@Required
	@Length(1)
	@Digit
	@Metadata(name = "是否支持后支付区分", order = 6)
	private Long deliveryPaymentLaterFlg;
	
	/** 配送时间指定时的手续费 */
	@Required
	@Length(10)
	@Currency
	@Metadata(name = "配送时间指定时的手续费", order = 7)
	private BigDecimal deliveryDatetimeCommission;

	/**
	 * @return shopCode 店铺编号
	 */
	public String getShopCode() {
		return shopCode;
	}

	/**
	 * @param shopCode 设置店铺编号
	 */
	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}

	/**
	 * @return deliveryCompanyNo 配送公司编号
	 */
	public String getDeliveryCompanyNo() {
		return deliveryCompanyNo;
	}

	/**
	 * @param deliveryCompanyNo 设置配送公司编号
	 */
	public void setDeliveryCompanyNo(String deliveryCompanyNo) {
		this.deliveryCompanyNo = deliveryCompanyNo;
	}

	/**
	 * @return deliveryCompanyName 配送公司名称
	 */
	public String getDeliveryCompanyName() {
		return deliveryCompanyName;
	}

	/**
	 * @param deliveryCompanyName 设置配送公司名称
	 */
	public void setDeliveryCompanyName(String deliveryCompanyName) {
		this.deliveryCompanyName = deliveryCompanyName;
	}

	/**
	 * @return deliverySpecificationType 支持配送指定日时区分
	 */
	public Long getDeliverySpecificationType() {
		return deliverySpecificationType;
	}

	/**
	 * @param deliverySpecificationType 设置支持配送指定日时区分
	 */
	public void setDeliverySpecificationType(Long deliverySpecificationType) {
		this.deliverySpecificationType = deliverySpecificationType;
	}

	/**
	 * @return deliveryPaymentAdvanceFlg 是否支持先支付区分
	 */
	public Long getDeliveryPaymentAdvanceFlg() {
		return deliveryPaymentAdvanceFlg;
	}

	/**
	 * @param deliveryPaymentAdvanceFlg 设置是否支持先支付区分
	 */
	public void setDeliveryPaymentAdvanceFlg(Long deliveryPaymentAdvanceFlg) {
		this.deliveryPaymentAdvanceFlg = deliveryPaymentAdvanceFlg;
	}

	/**
	 * @return deliveryPaymentLaterFlg 是否支持后支付区分
	 */
	public Long getDeliveryPaymentLaterFlg() {
		return deliveryPaymentLaterFlg;
	}

	/**
	 * @param deliveryPaymentLaterFlg 设置是否支持后支付区分
	 */
	public void setDeliveryPaymentLaterFlg(Long deliveryPaymentLaterFlg) {
		this.deliveryPaymentLaterFlg = deliveryPaymentLaterFlg;
	}

	/**
	 * @return deliveryDatetimeCommission 配送时间指定时的手续费
	 */
	public BigDecimal getDeliveryDatetimeCommission() {
		return deliveryDatetimeCommission;
	}

	/**
	 * @param deliveryDatetimeCommission 配送时间指定时的手续费
	 */
	public void setDeliveryDatetimeCommission(BigDecimal deliveryDatetimeCommission) {
		this.deliveryDatetimeCommission = deliveryDatetimeCommission;
	}
	
	

}
