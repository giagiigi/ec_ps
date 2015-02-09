package jp.co.sint.webshop.web.bean.back.analysis;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.data.attribute.Datetime;
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata; 
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.web.bean.UISearchBean;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerValue;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U1060310:キャンペーン管理のデータモデルです。
 * 
 * @author OB.
 */
public class PublicCouponBean extends UIBackBean implements UISearchBean {

	/** serial version uid */
	private static final long serialVersionUID = 1L;

	private PagerValue pagerValue = new PagerValue();


	/** 优惠券规则名称 */
	@Length(40)
	@Metadata(name = "优惠券规则名称", order = 2)
	private String searchCouponName;

	/** 优惠券类别 */
	@Digit
	@Metadata(name = "优惠券类别", order = 3)
	private String searchCouponType;

	/** 优惠券利用开始日时(From) */
	@Datetime(format = DateUtil.DEFAULT_DATETIME_FORMAT)
	@Metadata(name = "利用开始期间(From)", order = 9)
	private String searchMinUseStartDatetimeFrom;

	/** 优惠券利用开始日时(To) */
	@Datetime(format = DateUtil.DEFAULT_DATETIME_FORMAT)
	@Metadata(name = "利用开始期间(To)", order = 10)
	private String searchMinUseStartDatetimeTo;

	/** 优惠券利用结束日时(From) */
	@Datetime(format = DateUtil.DEFAULT_DATETIME_FORMAT)
	@Metadata(name = "利用结束期间(From)", order = 11)
	private String searchMinUseEndDatetimeFrom;

	/** 优惠券利用结束日时(To) */
	@Datetime(format = DateUtil.DEFAULT_DATETIME_FORMAT)
	@Metadata(name = "利用结束期间(To)", order = 12)
	private String searchMinUseEndDatetimeTo;
	
	/** 发行状态 */
	@Digit
	@Metadata(name = "发行状态", order = 13)
	private String searchCouponActivityStatus;

	private List<PrivateCouponListBeanDetail> privateCouponList = new ArrayList<PrivateCouponListBeanDetail>();
	
	private List<CodeAttribute> couponActivityStatusList = new ArrayList<CodeAttribute>();
	
	private List<String> checkedCouponCodeList = new ArrayList<String>();

	/** 削除ボタン表示有無 */
	private boolean deleteButtonDisplayFlg;

	/** 新規登録ボタン表示有無 */
	private boolean registerNewDisplayFlg;
	
	private boolean linkNewDisplayFlg;
	
	private List<CodeAttribute> couponTypes = new ArrayList<CodeAttribute>();
	
	private List<CodeAttribute> couponIssueTypes = new ArrayList<CodeAttribute>();

	  /**
	   * U1060310:キャンペーン管理のサブモデルです。
	   * 
	   * @author OB.
	   */
	  public static class PrivateCouponListBeanDetail implements Serializable {

	    /**
	     * 
	     */
	    private static final long serialVersionUID = 1L;

	    /** 优惠券规则编号 */
	    private String couponCode;

	    /** 优惠券规则名称 */
	    private String couponName;
	    
	    /** 优惠券类别 */
	    private String couponType;

	    /** 发行件数 */
	    private String wholesaleCount;

	    // 订单金额
	    private String orderAmount;

	    // 订单件数
	    private Long orderCount;

	    // 订单单价
	    private String orderPrice;

	    // 优惠金额
	    private String campaignAmount;
	    
	    // 新订单件数
	    private Long newOrderCount;
	    
	    // 旧订单件数
	    private Long oldOrderCount;

	    // 已取消订单金额
	    private String abrogateOrderAmount;

	    // 已取消订单件数
	    private Long abrogateOrderCount;

	    // 已取消订单单价
	    private String abrogateOrderPrice;

	    // 已取消优惠金额
	    private String abrogateCampaignAmount;

	    /**
	     * couponCodeを取得します。
	     * 
	     * @return couponCode
	     */
	    public String getCouponCode() {
	      return couponCode;
	    }

	    /**
	     * couponCodeを設定します。
	     * 
	     * @param couponCode
	     *          couponCode
	     */
	    public void setCouponCode(String couponCode) {
	      this.couponCode = couponCode;
	    }

	    /**
	     * couponNameを取得します。
	     * 
	     * @return couponName
	     */
	    public String getCouponName() {
	      return couponName;
	    }

	    /**
	     * couponNameを設定します。
	     * 
	     * @param couponName
	     *          couponName
	     */
	    public void setCouponName(String couponName) {
	      this.couponName = couponName;
	    }

	    /**
	     * couponTypeを取得します。
	     * 
	     * @return couponType
	     */
	    public String getCouponType() {
	      return couponType;
	    }

	    /**
	     * couponTypeを設定します。
	     * 
	     * @param couponType
	     *          couponType
	     */
	    public void setCouponType(String couponType) {
	      this.couponType = couponType;
	    }

	    public Long getNewOrderCount() {
	      return newOrderCount;
	    }

	    public void setNewOrderCount(Long newOrderCount) {
	      this.newOrderCount = newOrderCount;
	    }

	    public Long getOldOrderCount() {
	      return oldOrderCount;
	    }

	    public void setOldOrderCount(Long oldOrderCount) {
	      this.oldOrderCount = oldOrderCount;
	    }

	    public String getOrderAmount() {
	      return orderAmount;
	    }

	    public void setOrderAmount(String orderAmount) {
	      this.orderAmount = orderAmount;
	    }

	    public Long getOrderCount() {
	      return orderCount;
	    }

	    public void setOrderCount(Long orderCount) {
	      this.orderCount = orderCount;
	    }

	    public String getOrderPrice() {
	      return orderPrice;
	    }

	    public void setOrderPrice(String orderPrice) {
	      this.orderPrice = orderPrice;
	    }

	    public String getCampaignAmount() {
	      return campaignAmount;
	    }

	    public void setCampaignAmount(String campaignAmount) {
	      this.campaignAmount = campaignAmount;
	    }

	    public String getAbrogateOrderAmount() {
	      return abrogateOrderAmount;
	    }

	    public void setAbrogateOrderAmount(String abrogateOrderAmount) {
	      this.abrogateOrderAmount = abrogateOrderAmount;
	    }

	    public Long getAbrogateOrderCount() {
	      return abrogateOrderCount;
	    }

	    public void setAbrogateOrderCount(Long abrogateOrderCount) {
	      this.abrogateOrderCount = abrogateOrderCount;
	    }

	    public String getAbrogateOrderPrice() {
	      return abrogateOrderPrice;
	    }

	    public void setAbrogateOrderPrice(String abrogateOrderPrice) {
	      this.abrogateOrderPrice = abrogateOrderPrice;
	    }

	    public String getAbrogateCampaignAmount() {
	      return abrogateCampaignAmount;
	    }

	    public void setAbrogateCampaignAmount(String abrogateCampaignAmount) {
	      this.abrogateCampaignAmount = abrogateCampaignAmount;
	    }

		public String getWholesaleCount() {
			return wholesaleCount;
		}

		public void setWholesaleCount(String wholesaleCount) {
			this.wholesaleCount = wholesaleCount;
		}
	  }


	/**
	 * searchCouponNameを取得します。
	 * 
	 * @return searchCouponName
	 */
	public String getSearchCouponName() {
		return searchCouponName;
	}

	/**
	 * searchCouponNameを設定します。
	 * 
	 * @param searchCouponName
	 *            searchCouponName
	 */
	public void setSearchCouponName(String searchCouponName) {
		this.searchCouponName = searchCouponName;
	}

	/**
	 * searchCouponTypeを取得します。
	 * 
	 * @return searchCouponType
	 */
	public String getSearchCouponType() {
		return searchCouponType;
	}

	/**
	 * searchCouponTypeを設定します。
	 * 
	 * @param searchCouponType
	 *            searchCouponType
	 */
	public void setSearchCouponType(String searchCouponType) {
		this.searchCouponType = searchCouponType;
	}

	/**
	 * searchMinUseStartDatetimeFromを取得します。
	 * 
	 * @return searchMinUseStartDatetimeFrom
	 */
	public String getSearchMinUseStartDatetimeFrom() {
		return searchMinUseStartDatetimeFrom;
	}

	/**
	 * searchMinUseStartDatetimeFromを設定します。
	 * 
	 * @param searchMinUseStartDatetimeFrom
	 *            searchMinUseStartDatetimeFrom
	 */
	public void setSearchMinUseStartDatetimeFrom(
			String searchMinUseStartDatetimeFrom) {
		this.searchMinUseStartDatetimeFrom = searchMinUseStartDatetimeFrom;
	}

	/**
	 * searchMinUseStartDatetimeToを取得します。
	 * 
	 * @return searchMinUseStartDatetimeTo
	 */
	public String getSearchMinUseStartDatetimeTo() {
		return searchMinUseStartDatetimeTo;
	}

	/**
	 * searchMinUseStartDatetimeToを設定します。
	 * 
	 * @param searchMinUseStartDatetimeTo
	 *            searchMinUseStartDatetimeTo
	 */
	public void setSearchMinUseStartDatetimeTo(
			String searchMinUseStartDatetimeTo) {
		this.searchMinUseStartDatetimeTo = searchMinUseStartDatetimeTo;
	}

	/**
	 * searchMinUseEndDatetimeFromを取得します。
	 * 
	 * @return searchMinUseEndDatetimeFrom
	 */
	public String getSearchMinUseEndDatetimeFrom() {
		return searchMinUseEndDatetimeFrom;
	}

	/**
	 * searchMinUseEndDatetimeFromを設定します。
	 * 
	 * @param searchMinUseEndDatetimeFrom
	 *            searchMinUseEndDatetimeFrom
	 */
	public void setSearchMinUseEndDatetimeFrom(
			String searchMinUseEndDatetimeFrom) {
		this.searchMinUseEndDatetimeFrom = searchMinUseEndDatetimeFrom;
	}

	/**
	 * searchMinUseEndDatetimeToを取得します。
	 * 
	 * @return searchMinUseEndDatetimeTo
	 */
	public String getSearchMinUseEndDatetimeTo() {
		return searchMinUseEndDatetimeTo;
	}

	/**
	 * searchMinUseEndDatetimeToを設定します。
	 * 
	 * @param searchMinUseEndDatetimeTo
	 *            searchMinUseEndDatetimeTo
	 */
	public void setSearchMinUseEndDatetimeTo(String searchMinUseEndDatetimeTo) {
		this.searchMinUseEndDatetimeTo = searchMinUseEndDatetimeTo;
	}

	/**
	 * privateCouponListを取得します。
	 * 
	 * @return privateCouponList
	 */
	public List<PrivateCouponListBeanDetail> getPrivateCouponList() {
		return privateCouponList;
	}

	/**
	 * privateCouponListを設定します。
	 * 
	 * @param privateCouponList
	 *            privateCouponList
	 */
	public void setPrivateCouponList(
			List<PrivateCouponListBeanDetail> privateCouponList) {
		this.privateCouponList = privateCouponList;
	}
	
	/**
	 * checkedCouponCodeListを取得します。
	 * 
	 * @return checkedCouponCodeList
	 */
	public List<String> getCheckedCouponCodeList() {
		return checkedCouponCodeList;
	}

	/**
	 * checkedCouponCodeListを設定します。
	 * 
	 * @param checkedCouponCodeList 
	 * 		checkedCouponCodeList
	 */
	public void setCheckedCouponCodeList(List<String> checkedCouponCodeList) {
		this.checkedCouponCodeList = checkedCouponCodeList;
	}

	/**
	 * deleteButtonDisplayFlgを取得します。
	 * 
	 * @return deleteButtonDisplayFlg
	 */
	public boolean getDeleteButtonDisplayFlg() {
		return deleteButtonDisplayFlg;
	}

	/**
	 * deleteButtonDisplayFlgを設定します。
	 * 
	 * @param deleteButtonDisplayFlg
	 *            deleteButtonDisplayFlg
	 */
	public void setDeleteButtonDisplayFlg(boolean deleteButtonDisplayFlg) {
		this.deleteButtonDisplayFlg = deleteButtonDisplayFlg;
	}

	/**
	 * registerNewDisplayFlgを取得します。
	 * 
	 * @return registerNewDisplayFlg
	 */
	public boolean getRegisterNewDisplayFlg() {
		return registerNewDisplayFlg;
	}

	/**
	 * registerNewDisplayFlgを設定します。
	 * 
	 * @param registerNewDisplayFlg
	 *            registerNewDisplayFlg
	 */
	public void setRegisterNewDisplayFlg(boolean registerNewDisplayFlg) {
		this.registerNewDisplayFlg = registerNewDisplayFlg;
	}
	
	/**
	 * couponTypesを取得します。
	 * 
	 * @return couponTypes
	 */
	public List<CodeAttribute> getCouponTypes() {
		return couponTypes;
	}

	/**
	 * couponTypesを設定します。
	 * 
	 * @param couponTypes 
	 * 		couponTypes
	 */
	public void setCouponTypes(List<CodeAttribute> couponTypes) {
		this.couponTypes = couponTypes;
	}

	/**
	 * couponIssueTypesを取得します。
	 * 
	 * @return couponIssueTypes
	 */
	public List<CodeAttribute> getCouponIssueTypes() {
		return couponIssueTypes;
	}

	/**
	 * couponIssueTypesを設定します。
	 * 
	 * @param couponIssueTypes 
	 * 		couponIssueTypes
	 */
	public void setCouponIssueTypes(List<CodeAttribute> couponIssueTypes) {
		this.couponIssueTypes = couponIssueTypes;
	}

	/**
	 * サブJSPを設定します。
	 */
	@Override
	public void setSubJspId() {
		
	}

	/**
	 * pagerValueを取得します。
	 * 
	 * @return pagerValue
	 */
	public PagerValue getPagerValue() {
		return pagerValue;
	}

	/**
	 * pagerValueを設定します。
	 * 
	 * @param pagerValue
	 *            設定する pagerValue
	 */
	public void setPagerValue(PagerValue pagerValue) {
		this.pagerValue = pagerValue;
	}

	/**
	 * リクエストパラメータから値を取得します。
	 * 
	 * @param reqparam
	 *            リクエストパラメータ
	 */
	public void createAttributes(RequestParameter reqparam) {

		setSearchCouponName(reqparam.get("searchCouponName"));
		setSearchCouponType(reqparam.get("searchCouponType"));
		setSearchMinUseStartDatetimeFrom(reqparam.getDateTimeString("searchMinUseStartDatetimeFrom"));
		setSearchMinUseStartDatetimeTo(reqparam.getDateTimeString("searchMinUseStartDatetimeTo"));
		setSearchMinUseEndDatetimeFrom(reqparam.getDateTimeString("searchMinUseEndDatetimeFrom"));
		setSearchMinUseEndDatetimeTo(reqparam.getDateTimeString("searchMinUseEndDatetimeTo"));
		this.setCheckedCouponCodeList(Arrays.asList(reqparam.getAll("couponCode")));
		this.setSearchCouponActivityStatus(reqparam.get("searchCouponActivityStatus"));
	}

	/**
	 * モジュールIDを取得します。
	 * 
	 * @return モジュールID
	 */
	public String getModuleId() {
		return "U1071410";
	}


	/**
	 * モジュール名を取得します。
	 * 
	 * @return モジュール名
	 */
	public String getModuleName() {
		return Messages.getString("web.bean.back.analysis.PublicCouponBean.0");
	}

	public boolean isLinkNewDisplayFlg() {
		return linkNewDisplayFlg;
	}

	public void setLinkNewDisplayFlg(boolean linkNewDisplayFlg) {
		this.linkNewDisplayFlg = linkNewDisplayFlg;
	}

	public String getSearchCouponActivityStatus() {
		return searchCouponActivityStatus;
	}

	public void setSearchCouponActivityStatus(String searchCouponActivityStatus) {
		this.searchCouponActivityStatus = searchCouponActivityStatus;
	}

	public List<CodeAttribute> getCouponActivityStatusList() {
		return couponActivityStatusList;
	}

	public void setCouponActivityStatusList(
			List<CodeAttribute> couponActivityStatusList) {
		this.couponActivityStatusList = couponActivityStatusList;
	}

}
