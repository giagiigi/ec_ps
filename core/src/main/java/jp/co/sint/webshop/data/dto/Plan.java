package jp.co.sint.webshop.data.dto;

/**
 * 企划表
 * 
 * @author ob.
 * @version 1.0.0
 * 
 */
import java.io.Serializable;
import java.util.Date;

import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.WebshopEntity;
import jp.co.sint.webshop.data.attribute.Datetime;
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Required;

/**
 * AbstractPlan entity provides the base persistence definition of the Plan
 * 
 * @author OB
 */

public class Plan implements Serializable, WebshopEntity {

	private static final long serialVersionUID = 1L;

	/** 企划编号 */
	@Required
	@Length(16)
	@Metadata(name = "企划编号", order = 1)
	private String planCode;

	/** 企划名称 */
	@Required
	@Length(40)
	@Metadata(name = "企划名称", order = 2)
	private String planName;

	/** 企划类别（0:sale企划;1:特集企划） */
	@Required
	@Digit
	@Metadata(name = "企划类别（0:sale企划;1:特集企划）", order = 3)
	private Long planType;

	/** 企划说明 */
	@Length(1000)
	@Metadata(name = "企划说明", order = 4)
	private String planDescription;

	/** 企划开始日时 */
	@Required
	@Datetime
	@Metadata(name = "企划开始日时", order = 5)
	private Date planStartDatetime;

	/** 企划结束日时 */
	@Required
	@Datetime
	@Metadata(name = "企划结束日时", order = 6)
	private Date planEndDatetime;

	/** 企划明细类别 */
	@Required
	@Metadata(name = "企划明细类别", order = 7)
	private String planDetailType;

	/** 备注 */
	@Length(200)
	@Metadata(name = "备注", order = 8)
	private String memo;

	/** データ行ID */
	@Required
	@Digit
	@Metadata(name = "データ行ID", order = 9)
	private Long ormRowid = DatabaseUtil.DEFAULT_ORM_ROWID;

	/** 作成ユーザ */
	@Required
	@Length(100)
	@Metadata(name = "作成ユーザ", order = 10)
	private String createdUser;

	/** 作成日時 */
	@Required
	@Datetime
	@Metadata(name = "作成日時", order = 11)
	private Date createdDatetime;

	/** 更新ユーザ */
	@Required
	@Length(100)
	@Metadata(name = "更新ユーザ", order = 12)
	private String updatedUser;

	/** 更新日時 */
	@Required
	@Datetime
	@Metadata(name = "更新日時", order = 13)
	private Date updatedDatetime;

	// add by cs_yuli 20120515 start
	/** 企划英文名称 ***/
	@Length(40)
	@Metadata(name = "企划英文名称", order = 14)
	private String planNameEn;
	/** 企划日文名称 ***/
	@Length(40)
	@Metadata(name = "企划日文名称", order = 15)
	private String planNameJp;
	/*** 企划英文说明 ***/
	@Length(1000)
	@Metadata(name = "企划英文说明", order = 16)
	private String planDescriptionEn;
	/*** 企划日文说明 ***/
	@Length(1000)
	@Metadata(name = "企划日文说明", order = 17)
	private String planDescriptionJp;

	// add by cs_yuli 20120515 end
	// 20130703 txw add start
	 /*** TITLE ***/
  @Length(60)
  @Metadata(name = "TITLE", order = 18)
	private String title;
  
  /*** TITLE(英文) ***/
  @Length(60)
  @Metadata(name = "TITLE(英文)", order = 19)
  private String titleEn;
  
  /*** TITLE(日文) ***/
  @Length(60)
  @Metadata(name = "TITLE(日文)", order = 20)
  private String titleJp;
  
  /*** DESCRIPTION ***/
  @Length(100)
  @Metadata(name = "DESCRIPTION", order = 21)
  private String description;
  
  /*** DESCRIPTION(英文) ***/
  @Length(100)
  @Metadata(name = "DESCRIPTION(英文)", order = 22)
  private String descriptionEn;
  
  /*** DESCRIPTION(日文) ***/
  @Length(100)
  @Metadata(name = "DESCRIPTION(日文)", order = 23)
  private String descriptionJp;
  
  /*** KEYWORD ***/
  @Length(100)
  @Metadata(name = "KEYWORD", order = 24)
  private String keyword;
  
  /*** KEYWORD(英文) ***/
  @Length(100)
  @Metadata(name = "KEYWORD(英文)", order = 25)
  private String keywordEn;
  
  /*** KEYWORD(日文) ***/
  @Length(100)
  @Metadata(name = "KEYWORD(日文)", order = 26)
  private String keywordJp;
	// 20130703 txw add end

	/**
	 * @return 企划编号
	 */
	public String getPlanCode() {
		return planCode;
	}

	/**
	 * @param PlanCode
	 *            设置企划编号
	 */
	public void setPlanCode(String planCode) {
		this.planCode = planCode;
	}

	/**
	 * @return 企划名称
	 */
	public String getPlanName() {
		return planName;
	}

	/**
	 * @param PlanName
	 *            设置企划名称
	 */
	public void setPlanName(String planName) {
		this.planName = planName;
	}

	/**
	 * @return 企划类别（0:sale企划;1:特集企划）
	 */
	public Long getPlanType() {
		return planType;
	}

	/**
	 * @param PlanType
	 *            设置企划类别（0:sale企划;1:特集企划）
	 */
	public void setPlanType(Long planType) {
		this.planType = planType;
	}

	/**
	 * @return 企划说明
	 */
	public String getPlanDescription() {
		return planDescription;
	}

	/**
	 * @param PlanDescription
	 *            设置企划说明
	 */
	public void setPlanDescription(String planDescription) {
		this.planDescription = planDescription;
	}

	/**
	 * @return 企划开始日时
	 */
	public Date getPlanStartDatetime() {
		return planStartDatetime;
	}

	/**
	 * @param PlanStartDatetime
	 *            设置企划开始日时
	 */
	public void setPlanStartDatetime(Date planStartDatetime) {
		this.planStartDatetime = planStartDatetime;
	}

	/**
	 * @return 企划结束日时
	 */
	public Date getPlanEndDatetime() {
		return planEndDatetime;
	}

	/**
	 * @param PlanEndDatetime
	 *            设置企划结束日时
	 */
	public void setPlanEndDatetime(Date planEndDatetime) {
		this.planEndDatetime = planEndDatetime;
	}

	/**
	 * @return 企划明细类别
	 */
	public String getPlanDetailType() {
		return planDetailType;
	}

	/**
	 * @param PlanDetailType
	 *            设置企划明细类别
	 */
	public void setPlanDetailType(String planDetailType) {
		this.planDetailType = planDetailType;
	}

	public String getMemo() {
		return memo;
	}

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
	 * @param planNameEn
	 *            the planNameEn to set
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
	 * @param planNameJp
	 *            the planNameJp to set
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
	 * @param planDescriptionEn
	 *            the planDescriptionEn to set
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
	 * @param planDescriptionJp
	 *            the planDescriptionJp to set
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

  
  /**
   * @return the title
   */
  public String getTitle() {
    return title;
  }

  
  /**
   * @return the titleEn
   */
  public String getTitleEn() {
    return titleEn;
  }

  
  /**
   * @return the titleJp
   */
  public String getTitleJp() {
    return titleJp;
  }

  
  /**
   * @return the description
   */
  public String getDescription() {
    return description;
  }

  
  /**
   * @return the descriptionEn
   */
  public String getDescriptionEn() {
    return descriptionEn;
  }

  
  /**
   * @return the descriptionJp
   */
  public String getDescriptionJp() {
    return descriptionJp;
  }

  
  /**
   * @return the keyword
   */
  public String getKeyword() {
    return keyword;
  }

  
  /**
   * @return the keywordEn
   */
  public String getKeywordEn() {
    return keywordEn;
  }

  
  /**
   * @return the keywordJp
   */
  public String getKeywordJp() {
    return keywordJp;
  }

  
  /**
   * @param title the title to set
   */
  public void setTitle(String title) {
    this.title = title;
  }

  
  /**
   * @param titleEn the titleEn to set
   */
  public void setTitleEn(String titleEn) {
    this.titleEn = titleEn;
  }

  
  /**
   * @param titleJp the titleJp to set
   */
  public void setTitleJp(String titleJp) {
    this.titleJp = titleJp;
  }

  
  /**
   * @param description the description to set
   */
  public void setDescription(String description) {
    this.description = description;
  }

  
  /**
   * @param descriptionEn the descriptionEn to set
   */
  public void setDescriptionEn(String descriptionEn) {
    this.descriptionEn = descriptionEn;
  }

  
  /**
   * @param descriptionJp the descriptionJp to set
   */
  public void setDescriptionJp(String descriptionJp) {
    this.descriptionJp = descriptionJp;
  }

  
  /**
   * @param keyword the keyword to set
   */
  public void setKeyword(String keyword) {
    this.keyword = keyword;
  }

  
  /**
   * @param keywordEn the keywordEn to set
   */
  public void setKeywordEn(String keywordEn) {
    this.keywordEn = keywordEn;
  }

  
  /**
   * @param keywordJp the keywordJp to set
   */
  public void setKeywordJp(String keywordJp) {
    this.keywordJp = keywordJp;
  }

}