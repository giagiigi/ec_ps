package jp.co.sint.webshop.web.bean.back.shop;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.data.attribute.Currency;
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Precision;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.utility.WebUtil;
import jp.co.sint.webshop.web.bean.UISearchBean;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.webutility.PagerValue;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * @author
 */
public class DeliveryCompanyChargeBean extends UIBackBean implements UISearchBean {

	/** serial version uid */
	private static final long serialVersionUID = 1L;
	
	//运费类集合
	private List<DeliveryCompanyChargeEditBean> list=new ArrayList<DeliveryCompanyChargeEditBean>();
	//运费类
	private DeliveryCompanyChargeEditBean deliveryCompanyChargeEditBean=new DeliveryCompanyChargeEditBean();
	
	private boolean showUpdateInfo = false;
	
	private boolean showCopyInfo = false;
	
	/** 默认配送公司编号 */
  private String defaultCompanyNo;
  
  /** 配送公司一览 */ 
  private List<NameValue> deliveryCompanyList = new ArrayList<NameValue>();
  
	/**
	 * 取得运费类集合
	 * @return List<DeliveryCompanyChargeBean>
	 */
	public List<DeliveryCompanyChargeEditBean> getList() {
		return list;
	}
	/**
	 * 设置运费类集合
	 * @param list
	 */
	public void setList(List<DeliveryCompanyChargeEditBean> list) {
		this.list = list;
	}
	/**
	 * 取得运费类
	 * @return deliveryCompanyChargeBean
	 */
	public DeliveryCompanyChargeEditBean getDeliveryCompanyChargeEditBean() {
		return deliveryCompanyChargeEditBean;
	}
	/**
	 * 设置运费类
	 * @param deliveryCompanyChargeBean deliveryCompanyChargeBean
	 */
	public void setDeliveryCompanyChargeEidtBean(DeliveryCompanyChargeEditBean deliveryCompanyChargeEditBean) {
		this.deliveryCompanyChargeEditBean = (DeliveryCompanyChargeEditBean) deliveryCompanyChargeEditBean.clone();
	}
	/**
	 * 运费类
	 * @author YLP
	 */
	public static class DeliveryCompanyChargeEditBean implements Serializable , Cloneable
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		private String shopCode;

		// 地域名称
		private String prefectureName;
		
		@Metadata(name = "地域编号")
		private String prefectureCode;

		@Required
		@Digit
		@Length(2)
		@Metadata(name = "交货日")
		private String leadTime;

		@Required
		@Currency
		@Length(11)
		@Precision(precision = 10, scale = 2)
		@Metadata(name = "重量上限")
		private String deliveryWeight;

		@Required
		@Length(11)
		@Currency
		@Precision(precision = 10, scale = 2)
		@Metadata(name = "订单金额")
		private String orderAmount;

		@Required
		@Currency
		@Length(11)
		@Precision(precision = 10, scale = 2)
		@Metadata(name = "基本运费(设定金额以下)")
		private String deliveryChargeSmall;

		@Required
		@Currency
		@Length(11)
		@Precision(precision = 10, scale = 2)
		@Metadata(name = "基本运费(设定金额以上)")
		private String deliveryChargeBig;

		@Required
		@Currency
		@Length(11)
		@Precision(precision = 10, scale = 2)
		@Metadata(name = "续重")
		private String addWeight;

		@Required
		@Currency
		@Length(11)
		@Precision(precision = 10, scale = 2)
		@Metadata(name = "续费")
		private String addCharge;

		@Required
		@Currency
		@Precision(precision = 10, scale = 2)
		@Length(11)
		@Metadata(name = "订单金额")
		private String freeOrderAmount;

		@Required
		@Currency
		@Precision(precision = 10, scale = 2)
		@Length(11)
		@Metadata(name = "商品总重量")
		private String freeWeight;

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
	     * 取得交换日
	     * @return leadTime
	     */
		public String getLeadTime() {
			return leadTime;
		}
	    /**
	     * 设置交换日
	     * @param leadTime leadTime
	     */
		public void setLeadTime(String leadTime) {
			this.leadTime = leadTime;
		}
	    /**
	     * 取得指定金额以下时运费
	     * @return deliveryChargeSmall
	     */
		public String getDeliveryChargeSmall() {
			return deliveryChargeSmall;
		}
	    /**
	     * 设置指定金额以下时运费
	     * @param deliveryChargeSmall deliveryChargeSmall
	     */
		public void setDeliveryChargeSmall(String deliveryChargeSmall) {
			this.deliveryChargeSmall = deliveryChargeSmall;
		}
	    /**
	     * 取得指定金额以上时运费
	     * @return deliveryChargeBig
	     */
		public String getDeliveryChargeBig() {
			return deliveryChargeBig;
		}
	    /**
	     * 设置指定金额以上时运费
	     * @param deliveryChargeBig deliveryChargeBig
	     */
		public void setDeliveryChargeBig(String deliveryChargeBig) {
			this.deliveryChargeBig = deliveryChargeBig;
		}
	    /**
	     * 取得重量上限
	     * @return deliveryWeight
	     */
		public String getDeliveryWeight() {
			return deliveryWeight;
		}
	    /**
	     * 设置重量上限
	     * @param deliveryWeight deliveryWeight
	     */
		public void setDeliveryWeight(String deliveryWeight) {
			this.deliveryWeight = deliveryWeight;
		}
	    /**
	     * 取得订单金额
	     * @return orderAmount
	     */
		public String getOrderAmount() {
			return orderAmount;
		}
		/**
		 * 设置订单金额
		 * @param orderAmount orderAmount
		 */
		public void setOrderAmount(String orderAmount) {
			this.orderAmount = orderAmount;
		}
		/**
		 * 取得续重
		 * @return addWeight
		 */
		public String getAddWeight() {
			return addWeight;
		}
		/**
		 * 设置续重
		 * @param addWeight addWeight
		 */
		public void setAddWeight(String addWeight) {
			this.addWeight = addWeight;
		}
		/**
		 * 取得续费
		 * @return addCharge
		 */
		public String getAddCharge() {
			return addCharge;
		}
		/**
		 * 设置续费
		 * @param addCharge addCharge
		 */
		public void setAddCharge(String addCharge) {
			this.addCharge = addCharge;
		}
		/**
		 * 取得免运费_订单金额
		 * @return freeOrderAmount freeOrderAmount
		 */
		public String getFreeOrderAmount() {
			return freeOrderAmount;
		}
		/**
		 * 设置免运费_订单金额
		 * @param freeOrderAmount freeOrderAmount
		 */
		public void setFreeOrderAmount(String freeOrderAmount) {
			this.freeOrderAmount = freeOrderAmount;
		}
		/**
		 * 取得免运费_商品重量
		 * @return freeWeight
		 */
		public String getFreeWeight() {
			return freeWeight;
		}
		/**
		 * 设置免运费_商品重量
		 * @param freeWeight freeWeight
		 */
		public void setFreeWeight(String freeWeight) {
			this.freeWeight = freeWeight;
		}
		public String getPrefectureName() {
			return prefectureName;
		}
		public void setPrefectureName(String prefectureName) {
			this.prefectureName = prefectureName;
		}
		public String getPrefectureCode() {
			return prefectureCode;
		}
		public void setPrefectureCode(String prefectureCode) {
			this.prefectureCode = prefectureCode;
		}
		 public Object clone() {
			    try {
			      return super.clone();
			    } catch (CloneNotSupportedException e) {
			      return null;
			    }
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
		this.getDeliveryCompanyChargeEditBean().setLeadTime(reqparam.get("leadTime"));
		if (StringUtil.hasValue(reqparam.get("addCharge"))) {
			this.getDeliveryCompanyChargeEditBean().setAddCharge(reqparam.get("addCharge"));
		} else {
			this.getDeliveryCompanyChargeEditBean().setAddCharge(null);
		}
		if (StringUtil.hasValue(reqparam.get("addWeight"))) {
			this.getDeliveryCompanyChargeEditBean().setAddWeight(reqparam.get("addWeight"));
		} else {
			this.getDeliveryCompanyChargeEditBean().setAddWeight(null);
		}
		if (StringUtil.hasValue(reqparam.get("deliveryChargeBig"))) {
			this.getDeliveryCompanyChargeEditBean().setDeliveryChargeBig(reqparam.get("deliveryChargeBig"));
		} else {
			this.getDeliveryCompanyChargeEditBean().setDeliveryChargeBig(null);
		}
		if (StringUtil.hasValue(reqparam.get("deliveryChargeSmall"))) {
			this.getDeliveryCompanyChargeEditBean().setDeliveryChargeSmall(reqparam.get("deliveryChargeSmall"));
		} else {
			this.getDeliveryCompanyChargeEditBean().setDeliveryChargeSmall(null);
		}
		if (StringUtil.hasValue(reqparam.get("deliveryWeight"))) {
			this.getDeliveryCompanyChargeEditBean().setDeliveryWeight(reqparam.get("deliveryWeight"));
		}else {
			this.getDeliveryCompanyChargeEditBean().setDeliveryWeight(null);
		}
		if (StringUtil.hasValue(reqparam.get("freeOrderAmount"))) {
			this.getDeliveryCompanyChargeEditBean().setFreeOrderAmount(reqparam.get("freeOrderAmount"));
		}else {
			this.getDeliveryCompanyChargeEditBean().setFreeOrderAmount(null);
		}
		if (StringUtil.hasValue(reqparam.get("freeWeight"))) {
			this.getDeliveryCompanyChargeEditBean().setFreeWeight(reqparam.get("freeWeight"));
		}else {
			this.getDeliveryCompanyChargeEditBean().setFreeWeight(null);
		}
		if (StringUtil.hasValue(reqparam.get("orderAmount"))) {
			this.getDeliveryCompanyChargeEditBean().setOrderAmount(reqparam.get("orderAmount"));
		}else {
			this.getDeliveryCompanyChargeEditBean().setOrderAmount(null);
		}
		boolean update = true;
    for (String str : reqparam.getPathArgs()) {
      if (str.equals("updated") || str.equals("copy")) {
        update = false;
      }
    }
	// 默认配送公司信息设定
    if(update){
      this.setDefaultCompanyNo(reqparam.get("defaultCompanyNo"));
    }
	}
	
	public String toQueryString() {
	  List<String> params = new ArrayList<String>();
	  if (getPagerValue() != null) {
      appendParameter(params, "currentPage", "" + getPagerValue().getCurrentPage());
      appendParameter(params, "pageSize", "" + getPagerValue().getPageSize());
    }
	  
	  StringBuilder builder = new StringBuilder();
    String delimiter = "?";
    for (String p : params) {
      builder.append(delimiter + p);
      delimiter = "&";
    }
    return builder.toString();
	}
	
	private void appendParameter(List<String> params, String name, String value) {
    if (StringUtil.hasValue(value)) {
      params.add(name + "=" + WebUtil.urlEncode(value));
    }
    return;
  }

	/**
	 * モジュールIDを取得します。
	 * 
	 * @return モジュールID
	 */
	public String getModuleId() {
		return "U1051310";
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
	public boolean isShowUpdateInfo() {
		return showUpdateInfo;
	}
	public void setShowUpdateInfo(boolean showUpdateInfo) {
		this.showUpdateInfo = showUpdateInfo;
	}
  
  /**
   * @return the defaultCompanyNo
   */
  public String getDefaultCompanyNo() {
    return defaultCompanyNo;
  }
  
  /**
   * @param defaultCompanyNo the defaultCompanyNo to set
   */
  public void setDefaultCompanyNo(String defaultCompanyNo) {
    this.defaultCompanyNo = defaultCompanyNo;
  }
  
  /**
   * @return the deliveryCompanyList
   */
  public List<NameValue> getDeliveryCompanyList() {
    return deliveryCompanyList;
  }
  
  /**
   * @param deliveryCompanyList the deliveryCompanyList to set
   */
  public void setDeliveryCompanyList(List<NameValue> deliveryCompanyList) {
    this.deliveryCompanyList = deliveryCompanyList;
  }
  
  /**
   * @return the showCopyInfo
   */
  public boolean isShowCopyInfo() {
    return showCopyInfo;
  }
  
  /**
   * @param showCopyInfo the showCopyInfo to set
   */
  public void setShowCopyInfo(boolean showCopyInfo) {
    this.showCopyInfo = showCopyInfo;
  }
	

}
