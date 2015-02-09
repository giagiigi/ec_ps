package jp.co.sint.webshop.data.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.Currency;
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Precision;

/**
 * 「顾客组别优惠分析(CUSTOMER_GROUP_CAMPAIGN_SUMMARY_VIEW)」テーブルの1行分のレコードを表すDTO(Data Transfer Object)です。
 * 
 * @author cxw
 * 
 */
public class CustomerGroupCampaignSummaryView implements Serializable {

	/** Serial Version UID */
	private static final long serialVersionUID = -1;

	/** 优惠活动编号 */
	@Length(16)
	@AlphaNum2
	@Metadata(name = "优惠活动编号", order = 1)
	private String campaignCode;

	/** 订单金额 */
	@Currency
	@Precision(precision = 16, scale = 2)
	@Metadata(name = "订单金额", order = 2)
	private BigDecimal orderTotalPrice;

	/** 订单件数 */
	@Length(8)
	@Digit
	@Metadata(name = "订单件数", order = 3)
	private Long orderTotalCount;

	/** 订单单价 */
	@Currency
	@Precision(precision = 10, scale = 2)
	@Metadata(name = "订单单价", order = 4)
	private BigDecimal orderUnitPrice;

	/** 优惠金额 */
	@Currency
	@Precision(precision = 16, scale = 2)
	@Metadata(name = "优惠金额", order = 5)
	private BigDecimal campaignTotalPrice;

	/** 取消订单金额 */
	@Currency
	@Precision(precision = 16, scale = 2)
	@Metadata(name = "取消订单金额", order = 6)
	private BigDecimal cancelTotalPrice;

	/** 取消订单件数 */
	@Length(8)
	@Digit
	@Metadata(name = "取消订单件数", order = 7)
	private Long cancelTotalCount;

	/** 取消订单单价 */
	@Currency
	@Precision(precision = 10, scale = 2)
	@Metadata(name = "取消订单单价", order = 8)
	private BigDecimal cancelUnitPrice;

	/** 取消优惠金额 */
	@Currency
	@Precision(precision = 16, scale = 2)
	@Metadata(name = "取消优惠金额", order = 9)
	private BigDecimal cancelCampaignPrice;

	public String getCampaignCode() {
		return campaignCode;
	}

	public void setCampaignCode(String campaignCode) {
		this.campaignCode = campaignCode;
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

}
