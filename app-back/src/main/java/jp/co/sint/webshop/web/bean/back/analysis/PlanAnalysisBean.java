package jp.co.sint.webshop.web.bean.back.analysis;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.Datetime;
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.domain.PlanType; 
import jp.co.sint.webshop.web.bean.UISearchBean;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerValue;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U1060820:企划分析のデータモデルです。
 * 
 * @author OB
 */
public class PlanAnalysisBean extends UIBackBean implements UISearchBean {

	/** serial version uid */
	private static final long serialVersionUID = 1L;

	private PagerValue pagerValue = new PagerValue();
	/** 查询结果 */
	private List<PlanAnalysisDetail> list=new ArrayList<PlanAnalysisDetail>();
	
	@AlphaNum2
	@Length(16)
	@Metadata(name = "企划编号")
	private String searchPlanCode;

	@Length(40)
	@Metadata(name = "企划名称")
	private String searchPlanName;
	
	@Metadata(name = "企划分类")
	private String searchPlanDetailType;
	
	@Digit()
	@Length(1)
	@Metadata(name = "订单类别")
	private String searchOrderType;

	@Digit()
	@Length(1)
	@Metadata(name = "统计类别")
	private String searchStatisticsType;


	@Datetime(format = "yyyy/MM/dd HH:mm:ss")
	@Metadata(name = "企划开始日时From")
	private String searchStartDateFrom;

	@Datetime(format = "yyyy/MM/dd HH:mm:ss")
	@Metadata(name = "企划开始日时To")
	private String searchStartDateTo;

	@Datetime(format = "yyyy/MM/dd HH:mm:ss")
	@Metadata(name = "企划结束日时From")
	private String searchEndDateFrom;

	@Datetime(format = "yyyy/MM/dd HH:mm:ss")
	@Metadata(name = "企划结束日时To")
	private String searchEndDateTo;
	 
	  /** 企划类型模式 */
	private String planTypeMode;
	/**设置查询按钮显示值*/
	private boolean displaySearchButton=Boolean.TRUE;
	/***
	 * 取得企划类型模式
	 * @return planTypeMode
	 */
	public String getPlanTypeMode() {
		return planTypeMode;
	}
	/**
	 * 设定企划类型模式
	 * @param planTypeMode planTypeMode
	 */
	public void setPlanTypeMode(String planTypeMode) {
		this.planTypeMode = planTypeMode;
	}
	
	/**
	 * 取得企划编号
	 * @return searchPlanCode
	 */
	public String getSearchPlanCode() {
		return searchPlanCode;
	}
	/**
	 * 设定企划编号
	 * @param searchPlanCode searchPlanCode
	 */
	public void setSearchPlanCode(String searchPlanCode) {
		this.searchPlanCode = searchPlanCode;
	}
	/**
	 * 取得企划名称
	 * @return searchPlanCode
	 */
	public String getSearchPlanName() {
		return searchPlanName;
	}
	/**
	 * 设定企划名称
	 * @param searchPlanName searchPlanName
	 */
	public void setSearchPlanName(String searchPlanName) {
		this.searchPlanName = searchPlanName;
	}
	/**
	 * 取得企划分类
	 * @return String
	 */
	public String getSearchPlanDetailType() {
		return searchPlanDetailType;
	}
	/**
	 * 设定企划分类
	 * @param searchPlanDetailType String[]
	 */
	public void setSearchPlanDetailType(String searchPlanDetailType) {
		this.searchPlanDetailType = searchPlanDetailType;
	}
	/**
	 * 取得订单类别
	 * @return searchPlanType
	 */
	public String getSearchOrderType() {
		return searchOrderType;
	}
	/**
	 * 设定订单类别
	 * @param searchOrderType searchOrderType
	 */
	public void setSearchOrderType(String searchOrderType) {
		this.searchOrderType = searchOrderType;
	}
	/**
	 * 取得统计类别
	 * @return searchStatisticsType
	 */
	public String getSearchStatisticsType() {
		return searchStatisticsType;
	}
	/**
	 * 设定统计类别
	 * @param searchStatisticsType searchStatisticsType
	 */
	public void setSearchStatisticsType(String searchStatisticsType) {
		this.searchStatisticsType = searchStatisticsType;
	}
	/**
	 * 取得企划开始时间From
	 * @return searchStartDateFrom
	 */
	public String getSearchStartDateFrom() {
		return searchStartDateFrom;
	}
	/**
	 * 设定企划开始时间From
	 * @param searchStartDateFrom searchStartDateFrom
	 */
	public void setSearchStartDateFrom(String searchStartDateFrom) {
		this.searchStartDateFrom = searchStartDateFrom;
	}
	/**
	 * 取得企划开始时间To
	 * @return searchStartDateTo
	 */
	public String getSearchStartDateTo() {
		return searchStartDateTo;
	}
	/**
	 * 设定企划开始时间To
	 * @param searchStartDateTo searchStartDateTo
	 */
	public void setSearchStartDateTo(String searchStartDateTo) {
		this.searchStartDateTo = searchStartDateTo;
	}
	/**
	 * 取得企划结束时间From
	 * @return searchEndDateFrom
	 */
	public String getSearchEndDateFrom() {
		return searchEndDateFrom;
	}
	/**
	 * 设定企划结束时间From
	 * @param searchEndDateFrom searchEndDateFrom
	 */
	public void setSearchEndDateFrom(String searchEndDateFrom) {
		this.searchEndDateFrom = searchEndDateFrom;
	}
	/**
	 * 取得企划结束时间To
	 * @return searchEndDateTo
	 */
	public String getSearchEndDateTo() {
		return searchEndDateTo;
	}
	/**
	 * 设定企划结束时间To
	 * @param searchEndDateTo searchEndDateTo
	 */
	public void setSearchEndDateTo(String searchEndDateTo) {
		this.searchEndDateTo = searchEndDateTo;
	}
	/**
	 * 取得查询结果
	 * @return List<PlanAnalysisDetail>
	 */
	public List<PlanAnalysisDetail> getList() {
		return list;
	}
	/**
	 * 设定查询结果
	 * @param list List<PlanAnalysisDetail>
	 */
	public void setList(List<PlanAnalysisDetail> list) {
		this.list = list;
	}
	/**
	 * 取得 查询按钮显示值
	 * @return displaySearchButton
	 */
	public boolean isDisplaySearchButton() {
		return displaySearchButton;
	}
	/**
	 * 设定 查询按钮显示值
	 * @param displaySearchButton displaySearchButton
	 */
	public void setDisplaySearchButton(boolean displaySearchButton) {
		this.displaySearchButton = displaySearchButton;
	}


	/**
	 * 管理側アクセスログのサブモデルです。
	 * 
	 * @author System Integrator Corp.
	 */
	public static class PlanAnalysisDetail implements Serializable {
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
		/**企划分类*/
		private String planType;
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
		/**取消件数*/
		private Long cancleTotalCount;
		/**企划类别*/
		private String planDetailType;
		/**订单类型*/
		private Long orderType;
		private String summaryType;
		/**明细*/
		private String detailName;
		/**类别*/
		private String summaryName;
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
		 * 取得取消件数
		 * @return cancleTotalCount
		 */
		public Long getCancleTotalCount() {
			return cancleTotalCount;
		}
		/**
		 * 设定取消件数
		 * @param cancleTotalCount cancleTotalCount
		 */
		public void setCancleTotalCount(Long cancleTotalCount) {
			this.cancleTotalCount = cancleTotalCount;
		}
		/**
		 * 取得订单类别
		 * @return orderType
		 */
		public Long getOrderType() {
			return orderType;
		}
		/**
		 * 设定订单类别
		 * @param orderType orderType
		 */
		public void setOrderType(Long orderType) {
			this.orderType = orderType;
		}
		/**
		 * 取得企划类别
		 * @return planDetailType
		 */
		public String getPlanDetailType() {
			return planDetailType;
		}
		/**
		 * 设定企划类别
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
		 * 取得类别
		 * @return summaryName
		 */
		public String getSummaryName() {
			return summaryName;
		}
		/**
		 * 设定类别
		 * @param summaryName summaryName
		 */
		public void setSummaryName(String summaryName) {
			this.summaryName = summaryName;
		}
		/**
		 * 取得类别
		 * @return summaryType
		 */
		public String getSummaryType() {
			return summaryType;
		}
		/**
		 * 设定类别
		 * @param summaryType summaryType
		 */
		public void setSummaryType(String summaryType) {
			this.summaryType = summaryType;
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

	@Override
	public void createAttributes(RequestParameter reqparam) {
		setSearchStartDateFrom(reqparam.getDateTimeString("searchStartDateFrom"));
		setSearchStartDateTo(reqparam.getDateTimeString("searchStartDateTo"));
		setSearchEndDateFrom(reqparam.getDateTimeString("searchEndDateFrom"));
		setSearchEndDateTo(reqparam.getDateTimeString("searchEndDateTo"));
		setSearchPlanDetailType(reqparam.get("searchPlanDetailType"));
		reqparam.copy(this);
	}

	@Override
	public String getModuleId() {

	    if (PlanType.PROMOTION.getValue().equals(getPlanTypeMode())) {
		  return "U1071210";
		} else if(PlanType.FEATURE.getValue().equals(getPlanTypeMode())) {
	      return "U1071310";
		}
	    return "";
	}

	@Override
	public void setSubJspId() {
	}

	/**
	 * モジュール名を取得します。
	 * 
	 * @return モジュール名
	 */
	public String getModuleName() {

    if (PlanType.PROMOTION.getValue().equals(getPlanTypeMode())) {
      return Messages.getString("web.bean.back.analysis.PlanAnalysisBean.0");
    } else if (PlanType.FEATURE.getValue().equals(getPlanTypeMode())) {
      return Messages.getString("web.bean.back.analysis.PlanAnalysisBean.1");
    }
    return "";
  }
}
