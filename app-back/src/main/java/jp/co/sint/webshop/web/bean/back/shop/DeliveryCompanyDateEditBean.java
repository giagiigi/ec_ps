package jp.co.sint.webshop.web.bean.back.shop;

import java.io.Serializable;
import java.math.BigDecimal;
import jp.co.sint.webshop.data.attribute.Currency;
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.web.bean.UISearchBean;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.webutility.PagerValue;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * @author
 */
public class DeliveryCompanyDateEditBean extends UIBackBean implements UISearchBean {
	/** serial version uid */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 配送公司类
	 * @author YLP
	 */
	public static class DeliveryCompanyBean implements Serializable 
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		private String shopCode;
		
		//配送公司编号
		@Length(16)
		@Metadata(name="配送公司编号")
		private String diliveryCompanyNo;
		
		@Length(40)
		@Metadata(name="配送公司名称")
		private String deliveryCompanyName;
		
		@Length(1)
		@Digit
		@Metadata(name="是否可指定配送希望日")
		private Long deliverySpecificationType;
		@Length(1)
		@Digit
		@Metadata(name="是否支持先支付")
		private Long deliveryPaymentAdvanceFlg;
		
		@Length(1)
		@Digit
		@Metadata(name="是否支持后支付")
		private Long deliveryPaymentLaterFlg;
		
		@Length(10)
		@Digit
		@Currency()
		@Metadata(name="指定配送希望时间段时的手续费")
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
		 * @return diliveryCompanyNo
		 */
		public String getDiliveryCompanyNo() {
			return diliveryCompanyNo;
		}
		/**
		 * 设置diliveryCompanyNo
		 * @param diliveryCompanyNo diliveryCompanyNo
		 */
		public void setDiliveryCompanyNo(String diliveryCompanyNo) {
			this.diliveryCompanyNo = diliveryCompanyNo;
		}
		/**
		 * 取得配送公司名称
		 * @return deliveryCompanyName
		 */
		public String getDeliveryCompanyName() {
			return deliveryCompanyName;
		}
		/**
		 * 设置配送公司名称
		 * @param deliveryCompanyName
		 */
		public void setDeliveryCompanyName(String deliveryCompanyName) {
			this.deliveryCompanyName = deliveryCompanyName;
		}
		/**
		 * 取得是否可指定配送希望日
		 * @return deliverySpecificationType
		 */
		public Long getDeliverySpecificationType() {
			return deliverySpecificationType;
		}
		/**
		 * 设置取得是否可指定配送希望日
		 * @param deliverySpecificationType deliverySpecificationType
		 */
		public void setDeliverySpecificationType(Long deliverySpecificationType) {
			this.deliverySpecificationType = deliverySpecificationType;
		}
		/**
		 * 取得是否支持先支付
		 * @return deliveryPaymentAdvanceFlg
		 */
		public Long getDeliveryPaymentAdvanceFlg() {
			return deliveryPaymentAdvanceFlg;
		}
		/**
		 * 设置是否支持先支付
		 * @param deliveryPaymentAdvanceFlg deliveryPaymentAdvanceFlg
		 */
		public void setDeliveryPaymentAdvanceFlg(Long deliveryPaymentAdvanceFlg) {
			this.deliveryPaymentAdvanceFlg = deliveryPaymentAdvanceFlg;
		}
		/**
		 * 取得是否支持后支付
		 * @return deliveryPaymentLaterFlg
		 */
		public Long getDeliveryPaymentLaterFlg() {
			return deliveryPaymentLaterFlg;
		}
		/**
		 * 设置是否支持后支付
		 * @param deliveryPaymentLaterFlg deliveryPaymentLaterFlg
		 */
		public void setDeliveryPaymentLaterFlg(Long deliveryPaymentLaterFlg) {
			this.deliveryPaymentLaterFlg = deliveryPaymentLaterFlg;
		}
		/**
		 * 取得指定配送希望时间段时的手续费
		 * @return deliveryDatetimeCommission
		 */
		public BigDecimal getDeliveryDatetimeCommission() {
			return deliveryDatetimeCommission;
		}
		/**
		 * 设置指定配送希望时间段时的手续费
		 * @param deliveryDatetimeCommission deliveryDatetimeCommission
		 */
		public void setDeliveryDatetimeCommission(BigDecimal deliveryDatetimeCommission) {
			this.deliveryDatetimeCommission = deliveryDatetimeCommission;
		}
		
	}
	/**
	 * 配送公司地域配送指定时间类
	 * @author YLP
	 *
	 */
	public static class DeliveryRegionAppointedTime implements Serializable
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		private String shopCode;
		@Length(16)
		@Metadata(name="配送公司编号")
		private Long deliveryCompanyNo;
		
		@Digit
		@Length(2)
		@Metadata(name="地域编号")
		private String prefectureCode;
		
		@Digit
		@Length(2)
		@Metadata(name="配送时间段")
		private Long deliveryAppointedTimeCode;
		/**
		 * 取得店铺号
		 * @return shopCode
		 */
		public String getShopCode() {
			return shopCode;
		}
		/**
		 * 设置店铺号
		 * @param shopCode shopCode
		 */
		public void setShopCode(String shopCode) {
			this.shopCode = shopCode;
		}
		/**
		 * 取得配送公司编号
		 * @return deliveryCompanyNo
		 */
		public Long getDeliveryCompanyNo() {
			return deliveryCompanyNo;
		}
		/**
		 * 设置配送公司编号
		 * @param deliveryCompanyNo deliveryCompanyNo
		 */
		public void setDeliveryCompanyNo(Long deliveryCompanyNo) {
			this.deliveryCompanyNo = deliveryCompanyNo;
		}
		/**
		 * 取得地域编号
		 * @return regionBlockId
		 */
		public String getPrefectureCode() {
			return prefectureCode;
		}
		/**
		 * 设置地域编号
		 * @param regionBlockId regionBlockId
		 */
		public void setPrefectureCode(String prefectureCode) {
			this.prefectureCode = prefectureCode;
		}
		/**
		 * 取得配送时间段
		 * @return deliveryAppointedTimeCode
		 */
		public Long getDeliveryAppointedTimeCode() {
			return deliveryAppointedTimeCode;
		}
		/**
		 * 设置配送时间段
		 * @param deliveryAppointedTimeCode deliveryAppointedTimeCode
		 */
		public void setDeliveryAppointedTimeCode(Long deliveryAppointedTimeCode) {
			this.deliveryAppointedTimeCode = deliveryAppointedTimeCode;
		}
		
	}
	private PagerValue pagerValue = new PagerValue();

	/**
	 * サブJSPを設定します。
	 */
	@Override
	public void setSubJspId() {
	}

	/**
	 * リクエストパラメータから値を取得します。
	 * 
	 * @param reqparam
	 *            リクエストパラメータ
	 */
	public void createAttributes(RequestParameter reqparam) {

	}

	/**
	 * モジュールIDを取得します。
	 * 
	 * @return モジュールID
	 */
	public String getModuleId() {
		return "U1050940";
	}

	/**
	 * モジュール名を取得します。
	 * 
	 * @return モジュール名
	 */
	public String getModuleName() {
		return "运费设定";
	}

	/**
	 * pagerValueを取得します。
	 * 
	 * @return pagerValue pagerValue
	 */
	public PagerValue getPagerValue() {
		return pagerValue;
	}

	/**
	 * pagerValueを設定します。
	 * 
	 * @param pagerValue
	 *            pagerValue
	 */
	public void setPagerValue(PagerValue pagerValue) {
		this.pagerValue = pagerValue;
	}
}
