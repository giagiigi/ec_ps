package jp.co.sint.webshop.data.dto;

/**
 
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
import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.Datetime;
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Required;

/**
 * AbstractDeliveryCompany entity provides the base persistence definition of
 * the DeliveryCompany
 * 
 * @author OB
 */

public class DeliveryLocation implements Serializable, WebshopEntity {

	private static final long serialVersionUID = 1L;

  /**ショップコード*/
	@Required
	@Length(16)
	@Metadata(name = "ショップコード", order = 1)
  private String shopCode;
  
  /**配送会社コード*/
	@Required
  @Length(16)
  @Metadata(name = "配送会社コード", order = 2)
  private String deliveryCompanyNo;
  
  /**地域コード*/
	@Required
	@Length(2)
	@Metadata(name = "地域コード", order = 3)
  private String prefectureCode;
  
  /**都市コード*/
	@Required
	@Length(3)
	@Metadata(name = "地域コード", order = 4)
  private String cityCode;
  
  /**県コード*/
	@Required
	@Length(4)
	@Metadata(name = "地域コード", order = 5)
  private String areaCode;
	
  @Length(12)
  @AlphaNum2
  @Metadata(name = "重量下限", order = 11)
  private BigDecimal minWeight;
  
  @Length(12)
  @AlphaNum2
  @Metadata(name = "重量上限", order = 12)
  private BigDecimal maxWeight;
  
  
	/** データ行ID */
	@Required
	@Digit
	@Metadata(name = "データ行ID", order = 6)
	private Long ormRowid = DatabaseUtil.DEFAULT_ORM_ROWID;

	/** 作成ユーザ */
	@Required
	@Length(100)
	@Metadata(name = "作成ユーザ", order = 7)
	private String createdUser;

	/** 作成日時 */
	@Required
	@Datetime
	@Metadata(name = "作成日時", order = 8)
	private Date createdDatetime;

	/** 更新ユーザ */
	@Required
	@Length(100)
	@Metadata(name = "更新ユーザ", order = 9)
	private String updatedUser;

	/** 更新日時 */
	@Required
	@Datetime
	@Metadata(name = "更新日時", order = 10)
	private Date updatedDatetime;


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

  
  public String getShopCode() {
    return shopCode;
  }

  
  public void setShopCode(String shopCode) {
    this.shopCode = shopCode;
  }

  
  public String getDeliveryCompanyNo() {
    return deliveryCompanyNo;
  }

  
  public void setDeliveryCompanyNo(String deliveryCompanyNo) {
    this.deliveryCompanyNo = deliveryCompanyNo;
  }

  
  public String getPrefectureCode() {
    return prefectureCode;
  }

  
  public void setPrefectureCode(String prefectureCode) {
    this.prefectureCode = prefectureCode;
  }

  
  public String getCityCode() {
    return cityCode;
  }

  
  public void setCityCode(String cityCode) {
    this.cityCode = cityCode;
  }

  
  public String getAreaCode() {
    return areaCode;
  }

  
  public void setAreaCode(String areaCode) {
    this.areaCode = areaCode;
  }

  
  /**
   * @return the minWeight
   */
  public BigDecimal getMinWeight() {
    return minWeight;
  }

  
  /**
   * @param minWeight the minWeight to set
   */
  public void setMinWeight(BigDecimal minWeight) {
    this.minWeight = minWeight;
  }

  
  /**
   * @return the maxWeight
   */
  public BigDecimal getMaxWeight() {
    return maxWeight;
  }

  
  /**
   * @param maxWeight the maxWeight to set
   */
  public void setMaxWeight(BigDecimal maxWeight) {
    this.maxWeight = maxWeight;
  }
}