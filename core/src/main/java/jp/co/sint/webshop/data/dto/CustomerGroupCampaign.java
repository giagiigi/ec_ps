package jp.co.sint.webshop.data.dto;

/**
 * 会员组别优惠表
 * 
 * @author ob.
 * @version 1.0.0
 * 
 */
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.WebshopEntity;
import jp.co.sint.webshop.data.attribute.Currency;
import jp.co.sint.webshop.data.attribute.Datetime;
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Precision;
import jp.co.sint.webshop.data.attribute.Range;
import jp.co.sint.webshop.data.attribute.Required;

/**
 * AbstractCustomerGroupCampaign entity provides the base persistence definition
 * of the CustomerGroupCampaign
 * 
 * @author OB
 */

public class CustomerGroupCampaign implements Serializable, WebshopEntity {

	private static final long serialVersionUID = 1L;

	/** 店铺编号 */
	@Required
	@Length(16)
	@Metadata(name = "店铺编号", order = 1)
	private String shopCode;

	/** 优惠活动编号 */
	@Required
	@Length(16)
	@Metadata(name = "优惠活动编号", order = 2)
	private String campaignCode;

	/** 优惠活动名称 */
	@Required
	@Length(40)
	@Metadata(name = "优惠活动名称", order = 3)
	private String campaignName;

	/** 顾客组别编号 */
	@Length(16)
	@Metadata(name = "顾客组别编号", order = 4)
	private String customerGroupCode;

	/** 优惠类型 */
	@Required
	@Digit
	@Metadata(name = "优惠类型", order = 5)
	private Long campaignType;

	/** 优惠比例 */
	@Digit
	@Range(min = 0, max = 100)
	@Metadata(name = "优惠比例", order = 6)
	private Long campaignProportion;

	/** 优惠金额 */
	@Currency
	@Precision(precision = 10, scale = 2)
	@Metadata(name = "优惠金额", order = 7)
	private BigDecimal campaignAmount;

	/** 优惠活动开始日时 */
	@Required
	@Datetime
	@Metadata(name = "优惠活动开始日时", order = 8)
	private Date campaignStartDatetime;

	/** 优惠活动结束日时 */
	@Required
	@Datetime
	@Metadata(name = "优惠活动结束日时", order = 9)
	private Date campaignEndDatetime;

	/** 活动适用最小购买金额 */
	@Required
	@Currency
	@Metadata(name = "活动适用最小购买金额", order = 10)
	private BigDecimal minOrderAmount;

	/** 备注 */
	@Length(200)
	@Metadata(name = "备注", order = 11)
	private String memo;

	/** データ行ID */
	@Required
	@Digit
	@Metadata(name = "データ行ID", order = 12)
	private Long ormRowid = DatabaseUtil.DEFAULT_ORM_ROWID;

	/** 作成ユーザ */
	@Required
	@Length(100)
	@Metadata(name = "作成ユーザ", order = 13)
	private String createdUser;

	/** 作成日時 */
	@Required
	@Datetime
	@Metadata(name = "作成日時", order = 14)
	private Date createdDatetime;

	/** 更新ユーザ */
	@Required
	@Length(100)
	@Metadata(name = "更新ユーザ", order = 15)
	private String updatedUser;

	/** 更新日時 */
	@Required
	@Datetime
	@Metadata(name = "更新日時", order = 16)
	private Date updatedDatetime;
	// add by cs_yuli 20120521 start
	/** 优惠活动名称(英文) */
	@Required
	@Length(40)
	@Metadata(name = "优惠活动名称(英文)", order = 17)
	private String campaignNameEn;

	/** 优惠活动名称(日文) */
	@Required
	@Length(40)
	@Metadata(name = "优惠活动名称(日文)", order = 18)
	private String campaignNameJp;
	
	@Required
	@Precision(precision = 8, scale = 0)
	@Currency
	@Metadata(name = "个人最大利用次数", order = 19)
	private BigDecimal personalUseLimit;

	// add by cs_yuli 20120521 end
	/**
	 * @return 店铺编号
	 */
	public String getShopCode() {
		return shopCode;
	}

	/**
	 * @param ShopCode
	 *            设置店铺编号
	 */
	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}

	/**
	 * @return 优惠活动编号
	 */
	public String getCampaignCode() {
		return campaignCode;
	}

	/**
	 * @param CampainCode
	 *            设置优惠活动编号
	 */
	public void setCampaignCode(String campaignCode) {
		this.campaignCode = campaignCode;
	}

	/**
	 * @return 优惠活动名称
	 */
	public String getCampaignName() {
		return campaignName;
	}

	/**
	 * @param CampaignName
	 *            设置优惠活动名称
	 */
	public void setCampaignName(String campaignName) {
		this.campaignName = campaignName;
	}

	/**
	 * @return 会员组别编号
	 */
	public String getCustomerGroupCode() {
		return customerGroupCode;
	}

	/**
	 * @param CustomerGroupCode
	 *            设置会员组别编号
	 */
	public void setCustomerGroupCode(String customerGroupCode) {
		this.customerGroupCode = customerGroupCode;
	}

	/**
	 * @return 优惠类型
	 */
	public Long getCampaignType() {
		return campaignType;
	}

	/**
	 * @param CampaignType
	 *            设置优惠类型（0
	 */
	public void setCampaignType(Long campaignType) {
		this.campaignType = campaignType;
	}

	/**
	 * @return 优惠比率
	 */
	public Long getCampaignProportion() {
		return campaignProportion;
	}

	/**
	 * @param 设置优惠比率
	 */
	public void setCampaignProportion(Long campaignProportion) {
		this.campaignProportion = campaignProportion;
	}

	/**
	 * @return 优惠金额
	 */
	public BigDecimal getCampaignAmount() {
		return campaignAmount;
	}

	/**
	 * @param 设置优惠金额
	 */
	public void setCampaignAmount(BigDecimal campaignAmount) {
		this.campaignAmount = campaignAmount;
	}

	/**
	 * @return 优惠活动开始日时
	 */
	public Date getCampaignStartDatetime() {
		return campaignStartDatetime;
	}

	/**
	 * @param CampaignStartDatetime
	 *            设置优惠活动开始日时
	 */
	public void setCampaignStartDatetime(Date campaignStartDatetime) {
		this.campaignStartDatetime = campaignStartDatetime;
	}

	/**
	 * @return 优惠活动结束日时
	 */
	public Date getCampaignEndDatetime() {
		return campaignEndDatetime;
	}

	/**
	 * @param CampaignEndDatetime
	 *            设置优惠活动结束日时
	 */
	public void setCampaignEndDatetime(Date campaignEndDatetime) {
		this.campaignEndDatetime = campaignEndDatetime;
	}

	/**
	 * @return 活动适用最小购买金额
	 */
	public BigDecimal getMinOrderAmount() {
		return minOrderAmount;
	}

	/**
	 * @param MinOrderAmount
	 *            设置活动适用最小购买金额
	 */
	public void setMinOrderAmount(BigDecimal minOrderAmount) {
		this.minOrderAmount = minOrderAmount;
	}

	/**
	 * @return 备注
	 */
	public String getMemo() {
		return memo;
	}

	/**
	 * @param memo
	 *            备注
	 */
	public void setMemo(String memo) {
		this.memo = memo;
	}

	/**
	 * @return データ行ID
	 */
	public Long getOrmRowid() {
		return ormRowid;
	}

	/**
	 * @param OrmRowid
	 *            设置データ行ID
	 */
	public void setOrmRowid(Long ormRowid) {
		this.ormRowid = ormRowid;
	}

	/**
	 * @return 作成ユーザ
	 */
	public String getCreatedUser() {
		return createdUser;
	}

	/**
	 * @param CreatedUser
	 *            设置作成ユーザ
	 */
	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}

	/**
	 * @return 作成日時
	 */
	public Date getCreatedDatetime() {
		return createdDatetime;
	}

	/**
	 * @param CreatedDatetime
	 *            设置作成日時
	 */
	public void setCreatedDatetime(Date createdDatetime) {
		this.createdDatetime = createdDatetime;
	}

	/**
	 * @return 更新ユーザ
	 */
	public String getUpdatedUser() {
		return updatedUser;
	}

	/**
	 * @param UpdatedUser
	 *            设置更新ユーザ
	 */
	public void setUpdatedUser(String updatedUser) {
		this.updatedUser = updatedUser;
	}

	/**
	 * @return 更新日時
	 */
	public Date getUpdatedDatetime() {
		return updatedDatetime;
	}

	/**
	 * @param UpdatedDatetime
	 *            设置更新日時
	 */
	public void setUpdatedDatetime(Date updatedDatetime) {
		this.updatedDatetime = updatedDatetime;
	}

	/**
	 * @param campaignNameEn
	 *            the campaignNameEn to set
	 */
	public void setCampaignNameEn(String campaignNameEn) {
		this.campaignNameEn = campaignNameEn;
	}

	/**
	 * @return the campaignNameEn
	 */
	public String getCampaignNameEn() {
		return campaignNameEn;
	}

	/**
	 * @param campaignNameJp
	 *            the campaignNameJp to set
	 */
	public void setCampaignNameJp(String campaignNameJp) {
		this.campaignNameJp = campaignNameJp;
	}

	/**
	 * @return the campaignNameJp
	 */
	public String getCampaignNameJp() {
		return campaignNameJp;
	}

  
  /**
   * @return the personalUseLimit
   */
  public BigDecimal getPersonalUseLimit() {
    return personalUseLimit;
  }

  
  /**
   * @param personalUseLimit the personalUseLimit to set
   */
  public void setPersonalUseLimit(BigDecimal personalUseLimit) {
    this.personalUseLimit = personalUseLimit;
  }

}