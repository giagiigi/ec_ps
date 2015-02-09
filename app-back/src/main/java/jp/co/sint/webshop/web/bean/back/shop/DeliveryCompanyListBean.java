package jp.co.sint.webshop.web.bean.back.shop;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.web.bean.UISearchBean;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.webutility.PagerValue;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U1051410:配送公司一览画面				
 * 
 * @author cxw
 */
public class DeliveryCompanyListBean extends UIBackBean implements UISearchBean {

	/** serial version uid */
	private static final long serialVersionUID = 1L;

	/** 配送公司明细 */
	private DeliveryCompanyDetail deliveryDetail = new DeliveryCompanyDetail();

	/** 配送公司明细集合 */
	private List<DeliveryCompanyDetail> deleveryDetailList = new ArrayList<DeliveryCompanyDetail>();

	/** 选择按钮显示 */
	private boolean displaySelectButtonFlg;

	/** 删除按钮显示 */
	private boolean displayDeleteButtonFlg;

	/** 新建按钮显示 */
	private boolean displayRegisterButtonFlg;

	/** 默认配送公司编号 */
	private String defaultCompanyNo;
	
	/** 配送公司一览 */	
	private List<NameValue> deliveryCompanyList = new ArrayList<NameValue>();
	
	private PagerValue pagerValue;
	
	/**
	 * 创建属性取得配送公司参数值
	 * 
	 * @param reqparam 参数
	 */
	public void createAttributes(RequestParameter reqparam) {

		// 默认配送公司信息设定
		this.setDefaultCompanyNo(reqparam.get("defaultCompanyNo"));
	}

	/**
	 * 配送公司明细
	 */
	public static class DeliveryCompanyDetail implements Serializable {

		/** serial version uid */
		private static final long serialVersionUID = 1L;

		/** 店铺编号 */
		@AlphaNum2
		@Metadata(name = "店铺编号")
		private String shopCode;

		/** 配送公司编号 */
		@Required
		@Metadata(name = "配送公司编号")
		private String deliveryCompanyNo;

		private BigDecimal deliveryDatetimeCommission;
		
		/** 配送公司名称 */
		private String deliveryCompanyName;

		/** 默认公司Flg */
		private String defaultFlg;
		
		/** 关联URL */
		private String deliveryCompanyUrl;
		
		/** 行内删除按钮显示 */
	    private boolean deletableFlg;

		/** 数据库行ID */
		private Long ormRowid;

		/** 作成用户 */
		private String createdUser;

		/** 作成日期 */
		private Date createdDatetime;

		/** 更新用户 */
		private String updatedUser;

		/** 更新日期 */
		private Date updatedDatetime;

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
		 * @return deletableFlg 删除按钮
		 */
		public boolean isDeletableFlg() {
			return deletableFlg;
		}

		/**
		 * @param deletableFlg 设置删除按钮
		 */
		public void setDeletableFlg(boolean deletableFlg) {
			this.deletableFlg = deletableFlg;
		}

		/**
		 * @return deliveryCompanyName 配送公司名称
		 */
		public String getDeliveryCompanyName() {
			return deliveryCompanyName;
		}

		/**
		 * @param deliveryCompanyNo 设置配送公司名称
		 */
		public void setDeliveryCompanyName(String deliveryCompanyName) {
			this.deliveryCompanyName = deliveryCompanyName;
		}

		/**
		 * @return defaultFlg 是否支持货到付款
		 */
		public String getDefaultFlg() {
			return defaultFlg;
		}

		/**
		 * @param deliveryPaymentAdvanceFlg 设置是否支持货到付款
		 */
		public void setDefaultFlg(String defaultFlg) {
			this.defaultFlg = defaultFlg;
		}
	

		/**
		 * @return deliveryCompanyUrl 关联URL
		 */
		public String getDeliveryCompanyUrl() {
			return deliveryCompanyUrl;
		}

		/**
		 * @param deliveryCompanyUrl 设置关联URL
		 */
		public void setDeliveryCompanyUrl(String deliveryCompanyUrl) {
			this.deliveryCompanyUrl = deliveryCompanyUrl;
		}

		/**
		 * @return ormRowid 数据库行ID
		 */
		public Long getOrmRowid() {
			return ormRowid;
		}

		/**
		 * @param ormRowid 设置数据库行ID
		 */	
		public void setOrmRowid(Long ormRowid) {
			this.ormRowid = ormRowid;
		}

		/**
		 * @return createdUser 作成用户
		 */
		public String getCreatedUser() {
			return createdUser;
		}

		/**
		 * @param createdUser 设置作成用户
		 */	
		public void setCreatedUser(String createdUser) {
			this.createdUser = createdUser;
		}

		/**
		 * @return createdDatetime 作成日期
		 */
		public Date getCreatedDatetime() {
			return createdDatetime;
		}

		/**
		 * @param createdDatetime 设置作成日期
		 */	
		public void setCreatedDatetime(Date createdDatetime) {
			this.createdDatetime = createdDatetime;
		}

		/**
		 * @return updatedUser 更新用户
		 */
		public String getUpdatedUser() {
			return updatedUser;
		}

		/**
		 * @param updatedUser 设置更新用户
		 */	
		public void setUpdatedUser(String updatedUser) {
			this.updatedUser = updatedUser;
		}

		/**
		 * @return updatedDatetime 更新日期
		 */
		public Date getUpdatedDatetime() {
			return updatedDatetime;
		}

		/**
		 * @param updatedDatetime 设置更新日期
		 */	
		public void setUpdatedDatetime(Date updatedDatetime) {
			this.updatedDatetime = updatedDatetime;
		}

		public BigDecimal getDeliveryDatetimeCommission() {
			return deliveryDatetimeCommission;
		}

		public void setDeliveryDatetimeCommission(BigDecimal deliveryDatetimeCommission) {
			this.deliveryDatetimeCommission = deliveryDatetimeCommission;
		}

	}

	/**
	 * @return deliveryDetail 配送公司明细
	 */
	public DeliveryCompanyDetail getDeliveryDetail() {
		return deliveryDetail;
	}

	/**
	 * @param deliveryDetail 设置配送公司明细
	 */
	public void setDeliveryDetail(DeliveryCompanyDetail deliveryDetail) {
		this.deliveryDetail = deliveryDetail;
	}

	/**
	 * @return deleveryDetailList 配送公司明细集合
	 */
	public List<DeliveryCompanyDetail> getDeleveryDetailList() {
		return deleveryDetailList;
	}

	/**
	 * @param deleveryDetailList 设置配送公司明细集合
	 */
	public void setDeleveryDetailList(
			List<DeliveryCompanyDetail> deleveryDetailList) {
		this.deleveryDetailList = deleveryDetailList;
	}

	/**
	 * @return pagerValue
	 */
	public PagerValue getPagerValue() {
		return pagerValue;
	}

	/**
	 * @param pagerValue
	 */
	public void setPagerValue(PagerValue pagerValue) {
		this.pagerValue = pagerValue;
	}

	/**
	 * @return displaySelectButtonFlg 选择按钮显示
	 */
	public boolean isDisplaySelectButtonFlg() {
		return displaySelectButtonFlg;
	}

	/**
	 * @param displaySelectButtonFlg 设置选择按钮显示
	 */
	public void setDisplaySelectButtonFlg(boolean displaySelectButtonFlg) {
		this.displaySelectButtonFlg = displaySelectButtonFlg;
	}

	/**
	 * @return displayDeleteButtonFlg 删除按钮显示
	 */
	public boolean isDisplayDeleteButtonFlg() {
		return displayDeleteButtonFlg;
	}

	/**
	 * @param displayDeleteButtonFlg 设置删除按钮显示
	 */
	public void setDisplayDeleteButtonFlg(boolean displayDeleteButtonFlg) {
		this.displayDeleteButtonFlg = displayDeleteButtonFlg;
	}

	/**
	 * @return displayRegisterButtonFlg 新建按钮显示
	 */
	public boolean isDisplayRegisterButtonFlg() {
		return displayRegisterButtonFlg;
	}

	/**
	 * @param displayRegisterButtonFlg 设置新建按钮显示
	 */
	public void setDisplayRegisterButtonFlg(boolean displayRegisterButtonFlg) {
		this.displayRegisterButtonFlg = displayRegisterButtonFlg;
	}

	/**
	 * サブJSPを設定します。
	 */
	@Override
	public void setSubJspId() {

	}

	/**
	 * モジュールIDを取得します。
	 * 
	 * @return モジュールID
	 */
	public String getModuleId() {
		return "U1051410";
	}

	/**
	 * モジュール名を取得します。
	 * 
	 * @return モジュール名
	 */
	public String getModuleName() {
		return "配送公司设定";
	}

	public List<NameValue> getDeliveryCompanyList() {
		return deliveryCompanyList;
	}

	public void setDeliveryCompanyList(List<NameValue> deliveryCompanyList) {
		this.deliveryCompanyList = deliveryCompanyList;
	}

	public String getDefaultCompanyNo() {
		return defaultCompanyNo;
	}

	public void setDefaultCompanyNo(String defaultCompanyNo) {
		this.defaultCompanyNo = defaultCompanyNo;
	}

}
