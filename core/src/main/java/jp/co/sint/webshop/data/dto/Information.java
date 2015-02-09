//
//Copyright(C) 2007-2008 System Integrator Corp.
//All rights reserved.
//
// このファイルはEDMファイルから自動生成されます。
// 直接編集しないで下さい。
//
package jp.co.sint.webshop.data.dto;

import java.io.Serializable;
import java.util.Date;

import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.WebshopEntity;
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Domain;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.PrimaryKey;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.data.attribute.Url;
import jp.co.sint.webshop.data.domain.InformationType;
import jp.co.sint.webshop.utility.DateUtil;

/** 
 * 「お知らせ(INFORMATION)」テーブルの1行分のレコードを表すDTO(Data Transfer Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public class Information implements Serializable, WebshopEntity {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** お知らせ番号 */
  @PrimaryKey(1)
  @Required
  @Length(8)
  @Digit
  @Metadata(name = "お知らせ番号", order = 1)
  private Long informationNo;

  /** お知らせ区分 */
  @Required
  @Length(1)
  @Domain(InformationType.class)
  @Metadata(name = "お知らせ区分", order = 2)
  private Long informationType;

  /** お知らせ開始日時 */
  @Metadata(name = "お知らせ開始日時", order = 3)
  private Date informationStartDatetime;

  /** お知らせ終了日時 */
  @Metadata(name = "お知らせ終了日時", order = 4)
  private Date informationEndDatetime;

  /** お知らせ内容 */
  @Required
  @Length(500)
  @Metadata(name = "お知らせ内容", order = 5)
  private String informationContent;

  /** 表示クライアント区分 */
  @Required
  @Length(1)
  @Metadata(name = "表示クライアント区分", order = 6)
  private Long displayClientType;
  
  //soukai add ob 2011/12/19 start
  /** 重要度区分 */
  @Required
  @Length(1)
  @Metadata(name = "重要度区分", order = 7)
  private Long primaryFlg;

  /** 关联URL */
  @Length(256)
  @Url
  @Metadata(name = "关联URL", order = 8)
  private String informationUrl;
  
  /** 关联URL */
  @Length(256)
  @Url
  @Metadata(name = "关联URL(En)", order = 9)
  private String informationUrlEn;
  
  /** 关联URL */
  @Length(256)
  @Url
  @Metadata(name = "关联URL(Jp)", order = 10)
  private String informationUrlJp;
  
  //soukai add ob 2011/12/19 end
  
  /** データ行ID */
  @Required
  @Length(38)
  @Metadata(name = "データ行ID", order = 11)
  private Long ormRowid = DatabaseUtil.DEFAULT_ORM_ROWID;

  /** 作成ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "作成ユーザ", order = 12)
  private String createdUser;

  /** 作成日時 */
  @Required
  @Metadata(name = "作成日時", order = 13)
  private Date createdDatetime;

  /** 更新ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "更新ユーザ", order = 14)
  private String updatedUser;

  /** 更新日時 */
  @Required
  @Metadata(name = "更新日時", order = 15)
  private Date updatedDatetime;
  
 //20120514 tuxinwei add start
  /** お知らせ内容(英文) */
  @Required
  @Length(500)
  @Metadata(name = "お知らせ内容(英文)", order = 16)
  private String informationContentEn;
  
  /** お知らせ内容(日本语) */
  @Required
  @Length(500)
  @Metadata(name = "お知らせ内容(日本语)", order = 17)
  private String informationContentJp;
  //20120514 tuxinwei add end

  /**
   * お知らせ番号を取得します
   *
   * @return お知らせ番号
   */
  public Long getInformationNo() {
    return this.informationNo;
  }

  /**
   * お知らせ区分を取得します
   *
   * @return お知らせ区分
   */
  public Long getInformationType() {
    return this.informationType;
  }

  /**
   * お知らせ開始日時を取得します
   *
   * @return お知らせ開始日時
   */
  public Date getInformationStartDatetime() {
    return DateUtil.immutableCopy(this.informationStartDatetime);
  }

  /**
   * お知らせ終了日時を取得します
   *
   * @return お知らせ終了日時
   */
  public Date getInformationEndDatetime() {
    return DateUtil.immutableCopy(this.informationEndDatetime);
  }

  /**
   * お知らせ内容を取得します
   *
   * @return お知らせ内容
   */
  public String getInformationContent() {
    return this.informationContent;
  }

  /**
   * 表示クライアント区分を取得します
   *
   * @return 表示クライアント区分
   */
  public Long getDisplayClientType() {
    return this.displayClientType;
  }

  /**
   * データ行IDを取得します
   *
   * @return データ行ID
   */
  public Long getOrmRowid() {
    return this.ormRowid;
  }

  /**
   * 作成ユーザを取得します
   *
   * @return 作成ユーザ
   */
  public String getCreatedUser() {
    return this.createdUser;
  }

  /**
   * 作成日時を取得します
   *
   * @return 作成日時
   */
  public Date getCreatedDatetime() {
    return DateUtil.immutableCopy(this.createdDatetime);
  }

  /**
   * 更新ユーザを取得します
   *
   * @return 更新ユーザ
   */
  public String getUpdatedUser() {
    return this.updatedUser;
  }

  /**
   * 更新日時を取得します
   *
   * @return 更新日時
   */
  public Date getUpdatedDatetime() {
    return DateUtil.immutableCopy(this.updatedDatetime);
  }

  /**
   * お知らせ番号を設定します
   *
   * @param  val お知らせ番号
   */
  public void setInformationNo(Long val) {
    this.informationNo = val;
  }

  /**
   * お知らせ区分を設定します
   *
   * @param  val お知らせ区分
   */
  public void setInformationType(Long val) {
    this.informationType = val;
  }

  /**
   * お知らせ開始日時を設定します
   *
   * @param  val お知らせ開始日時
   */
  public void setInformationStartDatetime(Date val) {
    this.informationStartDatetime = DateUtil.immutableCopy(val);
  }

  /**
   * お知らせ終了日時を設定します
   *
   * @param  val お知らせ終了日時
   */
  public void setInformationEndDatetime(Date val) {
    this.informationEndDatetime = DateUtil.immutableCopy(val);
  }

  /**
   * お知らせ内容を設定します
   *
   * @param  val お知らせ内容
   */
  public void setInformationContent(String val) {
    this.informationContent = val;
  }

  /**
   * 表示クライアント区分を設定します
   *
   * @param  val 表示クライアント区分
   */
  public void setDisplayClientType(Long val) {
    this.displayClientType = val;
  }

  /**
   * データ行IDを設定します
   *
   * @param  val データ行ID
   */
  public void setOrmRowid(Long val) {
    this.ormRowid = val;
  }

  /**
   * 作成ユーザを設定します
   *
   * @param  val 作成ユーザ
   */
  public void setCreatedUser(String val) {
    this.createdUser = val;
  }

  /**
   * 作成日時を設定します
   *
   * @param  val 作成日時
   */
  public void setCreatedDatetime(Date val) {
    this.createdDatetime = DateUtil.immutableCopy(val);
  }

  /**
   * 更新ユーザを設定します
   *
   * @param  val 更新ユーザ
   */
  public void setUpdatedUser(String val) {
    this.updatedUser = val;
  }

  /**
   * 更新日時を設定します
   *
   * @param  val 更新日時
   */
  public void setUpdatedDatetime(Date val) {
    this.updatedDatetime = DateUtil.immutableCopy(val);
  }

public Long getPrimaryFlg() {
	return primaryFlg;
}

public void setPrimaryFlg(Long primary_flg) {
	this.primaryFlg = primary_flg;
}

public String getInformationUrl() {
	return informationUrl;
}

public void setInformationUrl(String information_url) {
	this.informationUrl = information_url;
}


/**
 * @return the informationContentEn
 */
public String getInformationContentEn() {
  return informationContentEn;
}


/**
 * @param informationContentEn the informationContentEn to set
 */
public void setInformationContentEn(String informationContentEn) {
  this.informationContentEn = informationContentEn;
}


/**
 * @return the informationContentJp
 */
public String getInformationContentJp() {
  return informationContentJp;
}


/**
 * @param informationContentJp the informationContentJp to set
 */
public void setInformationContentJp(String informationContentJp) {
  this.informationContentJp = informationContentJp;
}


/**
 * @return the informationUrlEn
 */
public String getInformationUrlEn() {
  return informationUrlEn;
}


/**
 * @param informationUrlEn the informationUrlEn to set
 */
public void setInformationUrlEn(String informationUrlEn) {
  this.informationUrlEn = informationUrlEn;
}


/**
 * @return the informationUrlJp
 */
public String getInformationUrlJp() {
  return informationUrlJp;
}


/**
 * @param informationUrlJp the informationUrlJp to set
 */
public void setInformationUrlJp(String informationUrlJp) {
  this.informationUrlJp = informationUrlJp;
}

}
