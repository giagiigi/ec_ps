package jp.co.sint.webshop.web.bean.back.communication;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.data.attribute.AlphaNum2;
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
public class PublicCouponListBean extends UIBackBean implements UISearchBean {

	/** serial version uid */
	private static final long serialVersionUID = 1L;

	private PagerValue pagerValue = new PagerValue();

	/** soukai add 2012/01/13 ob start */
	/** 优惠券规则编号 */
	@AlphaNum2
	@Length(16)
	@Metadata(name = "优惠券规则编号", order = 1)
	private String searchCouponCode;
	/** soukai add 2012/01/13 ob end */

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
		
		/** 优惠数值 */
		private String couponAmount;
		
		/** SITE最大利用回数 */
		private String siteUseLimit;
		
		/** 个人最大利用回数 */
		private String personalUseLimit;

		/** 优惠券规则名称 */
		private String couponName;

		/** 优惠券类别 */
		private String couponType;

		/** 优惠券发行类别 */
		private String couponIssueType;

		/** 优惠券利用开始日时 */
		private String minUseStartDatetime;

		/** 优惠券利用结束日时 */
		private String minUseEndDatetime;

		/** 优惠券利用最小购买金额 */
		private String minUseOrderAmount;

		// soukai add 2012/01/14 ob start
		/** 优惠比例 */
		private String couponProportion;
		
		/** 发行类别 */
		private boolean proportion;
		// soukai add 2012/01/14 ob end

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
		 *            couponCode
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
		 *            couponName
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
		 *            couponType
		 */
		public void setCouponType(String couponType) {
			this.couponType = couponType;
		}

		/**
		 * couponIssueTypeを取得します。
		 * 
		 * @return couponIssueType
		 */
		public String getCouponIssueType() {
			return couponIssueType;
		}

		/**
		 * couponIssueTypeを設定します。
		 * 
		 * @param couponIssueType
		 *            couponIssueType
		 */
		public void setCouponIssueType(String couponIssueType) {
			this.couponIssueType = couponIssueType;
		}


		/**
		 * minUseStartDatetimeを取得します。
		 * 
		 * @return minUseStartDatetime
		 */
		public String getMinUseStartDatetime() {
			return minUseStartDatetime;
		}

		/**
		 * minUseStartDatetimeを設定します。
		 * 
		 * @param minUseStartDatetime
		 *            minUseStartDatetime
		 */
		public void setMinUseStartDatetime(String minUseStartDatetime) {
			this.minUseStartDatetime = minUseStartDatetime;
		}

		/**
		 * minUseEndDatetimeを取得します。
		 * 
		 * @return minUseEndDatetime
		 */
		public String getMinUseEndDatetime() {
			return minUseEndDatetime;
		}

		/**
		 * minUseEndDatetimeを設定します。
		 * 
		 * @param minUseEndDatetime
		 *            minUseEndDatetime
		 */
		public void setMinUseEndDatetime(String minUseEndDatetime) {
			this.minUseEndDatetime = minUseEndDatetime;
		}

		/**
		 * minUseOrderAmountを取得します。
		 * 
		 * @return minUseOrderAmount
		 */
		public String getMinUseOrderAmount() {
			return minUseOrderAmount;
		}

		/**
		 * minUseOrderAmountを設定します。
		 * 
		 * @param minUseOrderAmount
		 *            minUseOrderAmount
		 */
		public void setMinUseOrderAmount(String minUseOrderAmount) {
			this.minUseOrderAmount = minUseOrderAmount;
		}

		public String getCouponAmount() {
			return couponAmount;
		}

		public void setCouponAmount(String couponAmount) {
			this.couponAmount = couponAmount;
		}

		public String getSiteUseLimit() {
			return siteUseLimit;
		}

		public void setSiteUseLimit(String siteUseLimit) {
			this.siteUseLimit = siteUseLimit;
		}

		public String getPersonalUseLimit() {
			return personalUseLimit;
		}

		public void setPersonalUseLimit(String personalUseLimit) {
			this.personalUseLimit = personalUseLimit;
		}

		/**
		 * @return the couponProportion
		 */
		public String getCouponProportion() {
			return couponProportion;
		}

		/**
		 * @param couponProportion the couponProportion to set
		 */
		public void setCouponProportion(String couponProportion) {
			this.couponProportion = couponProportion;
		}

		/**
		 * @return the proportion
		 */
		public boolean isProportion() {
			return proportion;
		}

		/**
		 * @param proportion the proportion to set
		 */
		public void setProportion(boolean proportion) {
			this.proportion = proportion;
		}

	}

	/**
	 * @return the searchCouponCode
	 */
	public String getSearchCouponCode() {
		return searchCouponCode;
	}

	/**
	 * @param searchCouponCode the searchCouponCode to set
	 */
	public void setSearchCouponCode(String searchCouponCode) {
		this.searchCouponCode = searchCouponCode;
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

		//soukai add 2012/01/13 ob start
		setSearchCouponCode(reqparam.get("searchCouponCode"));
		//soukai add 2012/01/13 ob end
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
		return "U1060710";
	}


	/**
	 * モジュール名を取得します。
	 * 
	 * @return モジュール名
	 */
	public String getModuleName() {
		return Messages.getString("web.bean.back.communication.PublicCouponListBean.0");
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
