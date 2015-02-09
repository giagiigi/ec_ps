package jp.co.sint.webshop.data.dto;

/**
 * 配送会社表
 * 
 * @author ob.
 * @version 1.0.0
 * 
 */
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import jp.co.sint.webshop.data.attribute.Bool;
import jp.co.sint.webshop.data.attribute.Currency;
import jp.co.sint.webshop.data.attribute.Datetime;
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Precision;
import jp.co.sint.webshop.data.attribute.PrimaryKey;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.data.attribute.Url;
import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.WebshopEntity;

/**
 * AbstractDeliveryCompany entity provides the base persistence definition of
 * the DeliveryCompany
 * 
 * @author OB
 */

public class DeliveryCompany implements Serializable, WebshopEntity {

	private static final long serialVersionUID = 1L;

	/** 店铺编号 */
	@PrimaryKey(1)
	@Required
	@Length(16)
	@Metadata(name = "店铺编号", order = 1)
	private String shopCode;

	/** 配送公司编号 */
	@PrimaryKey(2)
	@Required
	@Length(16)
	@Metadata(name = "配送公司编号", order = 2)
	private String deliveryCompanyNo;

	/** 配送公司名称 */
	@Required
	@Length(40)
	@Metadata(name = "配送公司名称", order = 3)
	private String deliveryCompanyName;

	/** 是否支持货到付款（0:不支持;1;支持） */
	@Required
	@Digit
	@Metadata(name = "默认公司Flg", order = 4)
	private Long defaultFlg;

	/** 配送时间指定时的手续费 */
	@Required
	@Currency
	@Precision(precision = 10, scale = 2)
	@Metadata(name = "配送时间指定时的手续费", order = 5)
	private BigDecimal deliveryDatetimeCommission;

	/** 关联URL */
	@Length(256)
	@Url
	@Metadata(name = "配送公司URL", order = 6)
	private String deliveryCompanyUrl;

	/** データ行ID */
	@Required
	@Digit
	@Metadata(name = "データ行ID", order = 7)
	private Long ormRowid = DatabaseUtil.DEFAULT_ORM_ROWID;

	/** 作成ユーザ */
	@Required
	@Length(100)
	@Metadata(name = "作成ユーザ", order = 8)
	private String createdUser;

	/** 作成日時 */
	@Required
	@Datetime
	@Metadata(name = "作成日時", order = 9)
	private Date createdDatetime;

	/** 更新ユーザ */
	@Required
	@Length(100)
	@Metadata(name = "更新ユーザ", order = 10)
	private String updatedUser;

	/** 更新日時 */
	@Required
	@Datetime
	@Metadata(name = "更新日時", order = 11)
	private Date updatedDatetime;
	
	/** 使用区分 */
  @Required
  @Length(1)
  @Bool
  @Metadata(name = "使用区分", order = 12)
  private Long useFlg;

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
	 * @return 配送公司编号
	 */
	public String getDeliveryCompanyNo() {
		return deliveryCompanyNo;
	}

	/**
	 * @param DeliveryCompanyNo
	 *            设置配送公司编号
	 */
	public void setDeliveryCompanyNo(String deliveryCompanyNo) {
		this.deliveryCompanyNo = deliveryCompanyNo;
	}

	/**
	 * @return 配送公司名称
	 */
	public String getDeliveryCompanyName() {
		return deliveryCompanyName;
	}

	/**
	 * @param DeliveryCompanyName
	 *            设置配送公司名称
	 */
	public void setDeliveryCompanyName(String deliveryCompanyName) {
		this.deliveryCompanyName = deliveryCompanyName;
	}

	/**
	 * @return 支持货到付款（0:不支持;1;支持）
	 */
	public Long getDefaultFlg() {
		return defaultFlg;
	}

	/**
	 * @param defaultFlg
	 *            设置支持货到付款（0:不支持;1;支持）
	 */
	public void setDefaultFlg(Long defaultFlg) {
		this.defaultFlg = defaultFlg;
	}

	/**
	 * @return 配送时间指定时的手续费
	 */
	public BigDecimal getDeliveryDatetimeCommission() {
		return deliveryDatetimeCommission;
	}

	/**
	 * @param DeliveryDatetimeCommission
	 *            设置配送时间指定时的手续费
	 */
	public void setDeliveryDatetimeCommission(
			BigDecimal deliveryDatetimeCommission) {
		this.deliveryDatetimeCommission = deliveryDatetimeCommission;
	}

	public String getDeliveryCompanyUrl() {
		return deliveryCompanyUrl;
	}

	public void setDeliveryCompanyUrl(String deliveryCompanyUrl) {
		this.deliveryCompanyUrl = deliveryCompanyUrl;
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

  public Long getUseFlg() {
    return useFlg;
  }

  public void setUseFlg(Long useFlg) {
    this.useFlg = useFlg;
  }
}