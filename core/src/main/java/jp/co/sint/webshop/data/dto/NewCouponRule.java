package jp.co.sint.webshop.data.dto;

/**
 * 优惠券规则表
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
 * AbstractNewCouponRule entity provides the base persistence definition of the
 * NewCouponRule
 * 
 * @author OB
 */

public class NewCouponRule implements Serializable, WebshopEntity {

	private static final long serialVersionUID = 1L;

	/** 优惠券规则编号 */
	@Required
	@Length(16)
	@Metadata(name = "优惠券规则编号", order = 1)
	private String couponCode;

	/** 优惠券规则名称 */
	@Required
	@Length(40)
	@Metadata(name = "优惠券规则名称", order = 2)
	private String couponName;
	
	/** 优惠券类别（0:购买发行；1:特别会员发行；2:公共发行） */
	@Required
	@Digit
	@Metadata(name = "优惠券类别", order = 3)
	private Long couponType;
	
	 /** 优惠券发行方式适用类别（0:无限制；1:仅限初次购买使用） */
  @Required
  @Digit
  @Metadata(name = "优惠券发行方式适用类别", order = 4)
  private Long applicableObjects;

	/** 优惠券发行类别（0:比例；1:固定金额） */
	@Required
	@Digit
	@Metadata(name = "优惠券发行类别", order = 5)
	private Long couponIssueType;

	/** 优惠券发行最小购买金额 */
	@Precision(precision = 10, scale = 2)
	@Currency
	@Metadata(name = "优惠券发行最小购买金额", order = 6)
	private BigDecimal minIssueOrderAmount;

	/** 优惠券发行开始日时 */
	@Datetime
	@Metadata(name = "优惠券发行开始日时", order = 7)
	private Date minIssueStartDatetime;

	/** 优惠券发行结束日时 */
	@Datetime
	@Metadata(name = "优惠券发行结束日时", order = 8)
	private Date minIssueEndDatetime;

	/** 优惠金额 */
	@Precision(precision = 10, scale = 2)
	@Currency
	@Metadata(name = "优惠金额", order = 9)
	private BigDecimal couponAmount;

	/** 优惠券比例 */
	@Digit
	@Range(min = 0, max = 100)
	@Metadata(name = "优惠券比例", order = 10)
	private Long couponProportion;

	/** 优惠券利用最小购买金额 */
	@Required
	@Precision(precision = 10, scale = 2)
	@Currency
	@Metadata(name = "优惠券利用最小购买金额", order = 11)
	private BigDecimal minUseOrderAmount;

	/** 优惠券利用开始日时 */
	@Required
	@Datetime
	@Metadata(name = "优惠券利用开始日时", order = 12)
	private Date minUseStartDatetime;

	/** 优惠券利用结束日时 */
	@Required
	@Datetime
	@Metadata(name = "优惠券利用结束日时", order = 13)
	private Date minUseEndDatetime;

	/** 个人最大利用回数（0:无限制） */
	@Required
	@Precision(precision = 10, scale = 2)
	@Currency
	@Metadata(name = "个人最大利用回数（0:无限制）", order = 14)
	private BigDecimal personalUseLimit;

	/** SITE最大利用回数（0:无限制） */
	@Required
	@Precision(precision = 10, scale = 2)
	@Currency
	@Metadata(name = "SITE最大利用回数（0:无限制）", order = 15)
	private BigDecimal siteUseLimit;

	/** 发行理由 */
	@Length(200)
	@Metadata(name = "发行理由", order = 16)
	private String issueReason;

	/** 备注 */
	@Length(200)
	@Metadata(name = "备注", order = 17)
	private String memo;
	
	/** 积分数换取优惠券 */
	@Digit
  @Length(8)
  @Metadata(name = "积分数换取优惠券", order = 18)
  private Long exchangePointAmount;

	/** データ行ID */
	@Required
	@Digit
	@Metadata(name = "データ行ID", order = 19)
	private Long ormRowid = DatabaseUtil.DEFAULT_ORM_ROWID;

	/** 作成ユーザ */
	@Required
	@Length(100)
	@Metadata(name = "作成ユーザ", order = 20)
	private String createdUser;

	/** 作成日時 */
	@Required
	@Datetime
	@Metadata(name = "作成日時", order = 21)
	private Date createdDatetime;

	/** 更新ユーザ */
	@Required
	@Length(100)
	@Metadata(name = "更新ユーザ", order = 22)
	private String updatedUser;

	/** 更新日時 */
	@Required
	@Datetime
	@Metadata(name = "更新日時", order = 23)
	private Date updatedDatetime;

	// add by cs_yuli 20120515 start
	/** 优惠券规则英文名称 */
	@Length(40)
	@Metadata(name = "优惠券规则英文名称", order = 24)
	private String couponNameEn;

	/** 优惠券规则日文名称 */
	@Length(40)
	@Metadata(name = "优惠券规则日文名称", order = 25)
	private String couponNameJp;
	
	 /** 适用地域 */
  @Length(2000)
  @Metadata(name = "适用地域", order = 26)
  private String applicableArea;
	// add by cs_yuli 20120515 end
  
  /** 对象商品集合 */
  @Metadata(name = "对象商品集合", order = 27)
  private String objectCommodities;
  
  /** 优惠券利用最大购买金额 */
  @Precision(precision = 10, scale = 2)
  @Currency
  @Metadata(name = "优惠券利用最大购买金额", order = 28)
  private BigDecimal maxUseOrderAmount;
  
  /** 优惠券发行金额类别（0:折扣前金额 1:折扣后金额） */
  @Required
  @Digit
  @Length(1)
  @Metadata(name = "优惠券发行金额类别", order = 29)
  private Long beforeAfterDiscountType;
	
  // 2013/04/07 优惠券对应 ob add start
  @Digit
  @Length(2)
  @Metadata(name = "优惠券利用开始日期", order = 30)
  private Long minUseStartNum;
  
  @Digit
  @Length(8)
  @Metadata(name = "优惠券利用结束日期", order = 31)
  private Long minUseEndNum;
  
  @Digit
  @Length(1)
  @Metadata(name = "指定关联商品区分", order = 32)
  private Long relatedCommodityFlg;
  // 2013/04/07 优惠券对应 ob add end
  
  /** 优惠关联对象使用类型类别（0：可使用   1：不可使用） */
  @Digit
  @Metadata(name = "关联对象使用类型", order = 33)
  private Long useType;
  
  /** 对象品牌集合字段集合 */
  @Metadata(name = "对象品牌集合字段", order = 34)
  private String objectBrand;
  
  // 20130927 txw add start
  /** 对象分类集合字段集合 */
  @Metadata(name = "对象分类集合字段", order = 35)
  private String objectCategory;
  // 20130927 txw add end
  
  public String getObjectCommodities() {
    return objectCommodities;
  }
  
  public void setObjectCommodities(String objectCommodities) {
    this.objectCommodities = objectCommodities;
  }

  /**
	 * @return 优惠券规则编号
	 */
	public String getCouponCode() {
		return couponCode;
	}

	/**
	 * @param CouponCode
	 *            设置优惠券规则编号
	 */
	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}

	/**
	 * @return 优惠券规则名称
	 */
	public String getCouponName() {
		return couponName;
	}

	/**
	 * @param CouponName
	 *            设置优惠券规则名称
	 */
	public void setCouponName(String couponName) {
		this.couponName = couponName;
	}

	/**
	 * @return 优惠券类别（0:购买发行；1:特别会员发行；2:公共发行）
	 */
	public Long getCouponType() {
		return couponType;
	}

	/**
	 * @param CouponType
	 *            设置优惠券类别（0:购买发行；1:特别会员发行；2:公共发行）
	 */
	public void setCouponType(Long couponType) {
		this.couponType = couponType;
	}

  
  public Long getApplicableObjects() {
    return applicableObjects;
  }

  /**
   * @return   优惠券发行方式适用类别（0:无限制；1:仅限初次购买使用） 
   */
  public void setApplicableObjects(Long applicableObjects) {
    this.applicableObjects = applicableObjects;
  }

  
  
  
  
  public String getApplicableArea() {
    return applicableArea;
  }

  /**
   * @return   适用地域 
   */
  public void setApplicableArea(String applicableArea) {
    this.applicableArea = applicableArea;
  }

  /**
	 * @return 优惠券发行类别（0:比例；1:固定金额）
	 */
	public Long getCouponIssueType() {
		return couponIssueType;
	}

	/**
	 * @param CouponIssueType
	 *            设置优惠券发行类别（0:比例；1:固定金额）
	 */
	public void setCouponIssueType(Long couponIssueType) {
		this.couponIssueType = couponIssueType;
	}

	/**
	 * @return 优惠券发行最小购买金额
	 */
	public BigDecimal getMinIssueOrderAmount() {
		return minIssueOrderAmount;
	}

	/**
	 * @param MinIssueOrderAmount
	 *            设置优惠券发行最小购买金额
	 */
	public void setMinIssueOrderAmount(BigDecimal minIssueOrderAmount) {
		this.minIssueOrderAmount = minIssueOrderAmount;
	}

	/**
	 * @return 优惠券发行开始日时
	 */
	public Date getMinIssueStartDatetime() {
		return minIssueStartDatetime;
	}

	/**
	 * @param MinIssueStartDatetime
	 *            设置优惠券发行开始日时
	 */
	public void setMinIssueStartDatetime(Date minIssueStartDatetime) {
		this.minIssueStartDatetime = minIssueStartDatetime;
	}

	/**
	 * @return 优惠券发行结束日时
	 */
	public Date getMinIssueEndDatetime() {
		return minIssueEndDatetime;
	}

	/**
	 * @param MinIssueEndDatetime
	 *            设置优惠券发行结束日时
	 */
	public void setMinIssueEndDatetime(Date minIssueEndDatetime) {
		this.minIssueEndDatetime = minIssueEndDatetime;
	}

	/**
	 * @return 优惠券数值
	 */
	public BigDecimal getCouponAmount() {
		return couponAmount;
	}

	/**
	 * @param CouponAmount
	 *            设置优惠券数值
	 */
	public void setCouponAmount(BigDecimal couponAmount) {
		this.couponAmount = couponAmount;
	}

	/**
	 * @return 优惠券比例
	 */
	public Long getCouponProportion() {
		return couponProportion;
	}

	/**
	 * @param couponProportion
	 *            优惠券比例
	 */
	public void setCouponProportion(Long couponProportion) {
		this.couponProportion = couponProportion;
	}

	/**
	 * @return 优惠券利用最小购买金额
	 */
	public BigDecimal getMinUseOrderAmount() {
		return minUseOrderAmount;
	}

	/**
	 * @param MinUseOrderAmount
	 *            设置优惠券利用最小购买金额
	 */
	public void setMinUseOrderAmount(BigDecimal minUseOrderAmount) {
		this.minUseOrderAmount = minUseOrderAmount;
	}

	/**
	 * @return 优惠券利用开始日时
	 */
	public Date getMinUseStartDatetime() {
		return minUseStartDatetime;
	}

	/**
	 * @param MinUseStartDatetime
	 *            设置优惠券利用开始日时
	 */
	public void setMinUseStartDatetime(Date minUseStartDatetime) {
		this.minUseStartDatetime = minUseStartDatetime;
	}

	/**
	 * @return 优惠券利用结束日时
	 */
	public Date getMinUseEndDatetime() {
		return minUseEndDatetime;
	}

	/**
	 * @param MinUseEndDatetime
	 *            设置优惠券利用结束日时
	 */
	public void setMinUseEndDatetime(Date minUseEndDatetime) {
		this.minUseEndDatetime = minUseEndDatetime;
	}

	/**
	 * @return 个人最大利用回数（0:无限制）
	 */
	public BigDecimal getPersonalUseLimit() {
		return personalUseLimit;
	}

	/**
	 * @param PersonalUseLimit
	 *            设置个人最大利用回数（0:无限制）
	 */
	public void setPersonalUseLimit(BigDecimal personalUseLimit) {
		this.personalUseLimit = personalUseLimit;
	}

	/**
	 * @return SITE最大利用回数（0:无限制）
	 */
	public BigDecimal getSiteUseLimit() {
		return siteUseLimit;
	}

	/**
	 * @param SiteUseLimit
	 *            设置SITE最大利用回数（0:无限制）
	 */
	public void setSiteUseLimit(BigDecimal siteUseLimit) {
		this.siteUseLimit = siteUseLimit;
	}

	/**
	 * @return 发行理由
	 */
	public String getIssueReason() {
		return issueReason;
	}

	/**
	 * @param issueReason
	 *            发行理由
	 */
	public void setIssueReason(String issueReason) {
		this.issueReason = issueReason;
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
   * @return the exchangePointAmount
   */
  public Long getExchangePointAmount() {
    return exchangePointAmount;
  }

  /**
   * @param exchangePointAmount the exchangePointAmount to set
   */
  public void setExchangePointAmount(Long exchangePointAmount) {
    this.exchangePointAmount = exchangePointAmount;
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
	 * @param couponNameEn the couponNameEn to set
	 */
	public void setCouponNameEn(String couponNameEn) {
		this.couponNameEn = couponNameEn;
	}

	/**
	 * @return the couponNameEn
	 */
	public String getCouponNameEn() {
		return couponNameEn;
	}

	/**
	 * @param couponNameJp the couponNameJp to set
	 */
	public void setCouponNameJp(String couponNameJp) {
		this.couponNameJp = couponNameJp;
	}

	/**
	 * @return the couponNameJp
	 */
	public String getCouponNameJp() {
		return couponNameJp;
	}

  
  /**
   * @return the maxUseOrderAmount
   */
  public BigDecimal getMaxUseOrderAmount() {
    return maxUseOrderAmount;
  }

  
  /**
   * @param maxUseOrderAmount the maxUseOrderAmount to set
   */
  public void setMaxUseOrderAmount(BigDecimal maxUseOrderAmount) {
    this.maxUseOrderAmount = maxUseOrderAmount;
  }

  /**
   * @return the beforeAfterDiscountType
   */
  public Long getBeforeAfterDiscountType() {
    return beforeAfterDiscountType;
  }

  /**
   * @param beforeAfterDiscountType the beforeAfterDiscountType to set
   */
  public void setBeforeAfterDiscountType(Long beforeAfterDiscountType) {
    this.beforeAfterDiscountType = beforeAfterDiscountType;
  }

  /**
   * @return the minUseStartNum
   */
  public Long getMinUseStartNum() {
    return minUseStartNum;
  }

  /**
   * @param minUseStartNum the minUseStartNum to set
   */
  public void setMinUseStartNum(Long minUseStartNum) {
    this.minUseStartNum = minUseStartNum;
  }

  /**
   * @return the minUseEndNum
   */
  public Long getMinUseEndNum() {
    return minUseEndNum;
  }

  /**
   * @param minUseEndNum the minUseEndNum to set
   */
  public void setMinUseEndNum(Long minUseEndNum) {
    this.minUseEndNum = minUseEndNum;
  }

  /**
   * @return the relatedCommodityFlg
   */
  public Long getRelatedCommodityFlg() {
    return relatedCommodityFlg;
  }

  /**
   * @param relatedCommodityFlg the relatedCommodityFlg to set
   */
  public void setRelatedCommodityFlg(Long relatedCommodityFlg) {
    this.relatedCommodityFlg= relatedCommodityFlg;
  }

  
  /**
   * @return the useType
   */
  public Long getUseType() {
    return useType;
  }

  
  /**
   * @param useType the useType to set
   */
  public void setUseType(Long useType) {
    this.useType = useType;
  }

  
  /**
   * @return the objectBrand
   */
  public String getObjectBrand() {
    return objectBrand;
  }

  
  /**
   * @param objectBrand the objectBrand to set
   */
  public void setObjectBrand(String objectBrand) {
    this.objectBrand = objectBrand;
  }

  
  /**
   * @return the objectCategory
   */
  public String getObjectCategory() {
    return objectCategory;
  }

  
  /**
   * @param objectCategory the objectCategory to set
   */
  public void setObjectCategory(String objectCategory) {
    this.objectCategory = objectCategory;
  }
  
}