//
// Copyright(C) 2007-2008 System Integrator Corp.
// All rights reserved.
//
// このファイルはEDMファイルから自動生成されます。
// 直接編集しないで下さい。
//
package jp.co.sint.webshop.service.catalog;

import java.io.Serializable;
import java.util.Date;

import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.WebshopEntity;
import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.CategoryCode;
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.PrimaryKey;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.utility.DateUtil;

/**
 * 「カテゴリ属性値(CATEGORY_ATTRIBUTE_VALUE)」テーブルの1行分のレコードを表すDTO(Data Transfer
 * Object)です。
 * 
 * @author System Integrator Corp.
 */
public class CategoryAttributeValueImport implements Serializable, WebshopEntity {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** ショップコード */
  @PrimaryKey(1)
  @Required
  @Length(16)
  @AlphaNum2
  @Metadata(name = "ショップコード", order = 1)
  private String shopCode;

  /** カテゴリコード */
  @PrimaryKey(2)
  @Required
  @Length(16)
  @CategoryCode
  @Metadata(name = "カテゴリコード", order = 2)
  private String categoryCode;

  /** カテゴリ属性番号 */
  @PrimaryKey(3)
  @Required
  @Length(8)
  @Digit
  @Metadata(name = "カテゴリ属性番号", order = 3)
  private Long categoryAttributeNo;

  /** 商品コード */
  @PrimaryKey(4)
  @Required
  @Length(16)
  @AlphaNum2
  @Metadata(name = "商品コード", order = 4)
  private String commodityCode;

  /** カテゴリ属性値 */
  @Length(30)
  @Metadata(name = "カテゴリ属性値", order = 5)
  private String categoryAttributeValue;
// add by cs_yuli 20120608 start
  /** カテゴリ属性英文値 */
  @Length(30)
  @Metadata(name = "カテゴリ属性英文値", order = 6)
  private String categoryAttributeValueEn;
  /** カテゴリ属性日文値 */
  @Length(30)
  @Metadata(name = "カテゴリ属性日文値", order = 7)
  private String categoryAttributeValueJp;

//add by cs_yuli 20120608 end
  /** データ行ID */
  @Required
  @Length(38)
  @Metadata(name = "データ行ID", order = 8)
  private Long ormRowid = DatabaseUtil.DEFAULT_ORM_ROWID;

  /** 作成ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "作成ユーザ", order = 9)
  private String createdUser;

  /** 作成日時 */
  @Required
  @Metadata(name = "作成日時", order = 10)
  private Date createdDatetime;

  /** 更新ユーザ */
  @Required
  @Length(100)
  @Metadata(name = "更新ユーザ", order = 11)
  private String updatedUser;

  /** 更新日時 */
  @Required
  @Metadata(name = "更新日時", order = 12)
  private Date updatedDatetime;

  // 20120209 os013 add start
  @Length(30)
  @Metadata(name = "カテゴリ属性値", order = 13)
  private String category1Attribute1;

  @Length(30)
  @Metadata(name = "カテゴリ属性値", order = 14)
  private String category1Attribute2;

  @Length(30)
  @Metadata(name = "カテゴリ属性値", order = 15)
  private String category1Attribute3;

  @Length(30)
  @Metadata(name = "カテゴリ属性値", order = 16)
  private String category2Attribute1;

  @Length(30)
  @Metadata(name = "カテゴリ属性値", order = 17)
  private String category2Attribute2;

  @Length(30)
  @Metadata(name = "カテゴリ属性値", order = 18)
  private String category2Attribute3;

  @Length(30)
  @Metadata(name = "カテゴリ属性値", order = 19)
  private String category3Attribute1;

  @Length(30)
  @Metadata(name = "カテゴリ属性値", order = 20)
  private String category3Attribute2;

  @Length(30)
  @Metadata(name = "カテゴリ属性値", order = 21)
  private String category3Attribute3;

  @Length(30)
  @Metadata(name = "カテゴリ属性値", order = 22)
  private String category4Attribute1;

  @Length(30)
  @Metadata(name = "カテゴリ属性値", order = 23)
  private String category4Attribute2;

  @Length(30)
  @Metadata(name = "カテゴリ属性値", order = 24)
  private String category4Attribute3;

  @Length(30)
  @Metadata(name = "カテゴリ属性値", order = 25)
  private String category5Attribute1;

  @Length(30)
  @Metadata(name = "カテゴリ属性値", order = 26)
  private String category5Attribute2;

  @Length(30)
  @Metadata(name = "カテゴリ属性値", order = 27)
  private String category5Attribute3;

  @Length(30)
  @Metadata(name = "カテゴリ属性値", order = 28)
  private String categoryAttributeValue1;

  @Length(30)
  @Metadata(name = "カテゴリ属性値", order = 29)
  private String categoryAttributeValue2;

  @Length(30)
  @Metadata(name = "カテゴリ属性値", order = 30)
  private String categoryAttributeValue3;

  @Length(30)
  @Metadata(name = "更新区分", order = 31)
  private String importFlag;
  
  @Length(16)
  @Metadata(name = "産地CODE", order = 32)
  private String originalCode;

  // 20120209 os013 add end 

  /**
 * @return the originalCode
 */
public String getOriginalCode() {
	return originalCode;
}

/**
 * @param originalCode the originalCode to set
 */
public void setOriginalCode(String originalCode) {
	this.originalCode = originalCode;
}

@Length(30)
  @Metadata(name = "カテゴリ属性値", order = 32)
  private String categoryAttributeValue1En;

  @Length(30)
  @Metadata(name = "カテゴリ属性値", order = 33)
  private String categoryAttributeValue2En;

  @Length(30)
  @Metadata(name = "カテゴリ属性値", order = 34)
  private String categoryAttributeValue3En; 
  @Length(30)
  @Metadata(name = "カテゴリ属性値", order = 35)
  private String categoryAttributeValue1Jp;

  @Length(30)
  @Metadata(name = "カテゴリ属性値", order = 36)
  private String categoryAttributeValue2Jp;

  @Length(30)
  @Metadata(name = "カテゴリ属性値", order = 37)
  private String categoryAttributeValue3Jp;
  
  @Length(30)
  @Metadata(name = "カテゴリ属性値", order = 38)
  private String category1Attribute1En;

  @Length(30)
  @Metadata(name = "カテゴリ属性値", order = 39)
  private String category1Attribute2En;

  @Length(30)
  @Metadata(name = "カテゴリ属性値", order = 40)
  private String category1Attribute3En;

  @Length(30)
  @Metadata(name = "カテゴリ属性値", order = 41)
  private String category2Attribute1En;

  @Length(30)
  @Metadata(name = "カテゴリ属性値", order = 42)
  private String category2Attribute2En;

  @Length(30)
  @Metadata(name = "カテゴリ属性値", order = 43)
  private String category2Attribute3En;

  @Length(30)
  @Metadata(name = "カテゴリ属性値", order = 44)
  private String category3Attribute1En;

  @Length(30)
  @Metadata(name = "カテゴリ属性値", order = 45)
  private String category3Attribute2En;

  @Length(30)
  @Metadata(name = "カテゴリ属性値", order = 46)
  private String category3Attribute3En;

  @Length(30)
  @Metadata(name = "カテゴリ属性値", order = 47)
  private String category4Attribute1En;

  @Length(30)
  @Metadata(name = "カテゴリ属性値", order = 48)
  private String category4Attribute2En;

  @Length(30)
  @Metadata(name = "カテゴリ属性値", order = 49)
  private String category4Attribute3En;

  @Length(30)
  @Metadata(name = "カテゴリ属性値", order = 50)
  private String category5Attribute1En;

  @Length(30)
  @Metadata(name = "カテゴリ属性値", order = 51)
  private String category5Attribute2En;

  @Length(30)
  @Metadata(name = "カテゴリ属性値", order = 52)
  private String category5Attribute3En;


  @Length(30)
  @Metadata(name = "カテゴリ属性値", order = 53)
  private String category1Attribute1Jp;

  @Length(30)
  @Metadata(name = "カテゴリ属性値", order = 54)
  private String category1Attribute2Jp;

  @Length(30)
  @Metadata(name = "カテゴリ属性値", order = 55)
  private String category1Attribute3Jp;

  @Length(30)
  @Metadata(name = "カテゴリ属性値", order = 56)
  private String category2Attribute1Jp;

  @Length(30)
  @Metadata(name = "カテゴリ属性値", order = 57)
  private String category2Attribute2Jp;

  @Length(30)
  @Metadata(name = "カテゴリ属性値", order = 58)
  private String category2Attribute3Jp;

  @Length(30)
  @Metadata(name = "カテゴリ属性値", order = 59)
  private String category3Attribute1Jp;

  @Length(30)
  @Metadata(name = "カテゴリ属性値", order = 60)
  private String category3Attribute2Jp;

  @Length(30)
  @Metadata(name = "カテゴリ属性値", order = 61)
  private String category3Attribute3Jp;

  @Length(30)
  @Metadata(name = "カテゴリ属性値", order = 62)
  private String category4Attribute1Jp;

  @Length(30)
  @Metadata(name = "カテゴリ属性値", order = 63)
  private String category4Attribute2Jp;

  @Length(30)
  @Metadata(name = "カテゴリ属性値", order = 64)
  private String category4Attribute3Jp;

  @Length(30)
  @Metadata(name = "カテゴリ属性値", order = 65)
  private String category5Attribute1Jp;

  @Length(30)
  @Metadata(name = "カテゴリ属性値", order = 66)
  private String category5Attribute2Jp;

  @Length(30)
  @Metadata(name = "カテゴリ属性値", order = 67)
  private String category5Attribute3Jp;
  /**
   * ショップコードを取得します
   * 
   * @return ショップコード
   */
  public String getShopCode() {
    return this.shopCode;
  }

  /**
   * カテゴリコードを取得します
   * 
   * @return カテゴリコード
   */
  public String getCategoryCode() {
    return this.categoryCode;
  }

  /**
   * カテゴリ属性番号を取得します
   * 
   * @return カテゴリ属性番号
   */
  public Long getCategoryAttributeNo() {
    return this.categoryAttributeNo;
  }

  /**
   * 商品コードを取得します
   * 
   * @return 商品コード
   */
  public String getCommodityCode() {
    return this.commodityCode;
  }

  /**
   * カテゴリ属性値を取得します
   * 
   * @return カテゴリ属性値
   */
  public String getCategoryAttributeValue() {
    return this.categoryAttributeValue;
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
   * ショップコードを設定します
   * 
   * @param val
   *          ショップコード
   */
  public void setShopCode(String val) {
    this.shopCode = val;
  }

  /**
   * カテゴリコードを設定します
   * 
   * @param val
   *          カテゴリコード
   */
  public void setCategoryCode(String val) {
    this.categoryCode = val;
  }

  /**
   * カテゴリ属性番号を設定します
   * 
   * @param val
   *          カテゴリ属性番号
   */
  public void setCategoryAttributeNo(Long val) {
    this.categoryAttributeNo = val;
  }

  /**
   * 商品コードを設定します
   * 
   * @param val
   *          商品コード
   */
  public void setCommodityCode(String val) {
    this.commodityCode = val;
  }

  /**
   * カテゴリ属性値を設定します
   * 
   * @param val
   *          カテゴリ属性値
   */
  public void setCategoryAttributeValue(String val) {
    this.categoryAttributeValue = val;
  }

  /**
   * データ行IDを設定します
   * 
   * @param val
   *          データ行ID
   */
  public void setOrmRowid(Long val) {
    this.ormRowid = val;
  }

  /**
   * 作成ユーザを設定します
   * 
   * @param val
   *          作成ユーザ
   */
  public void setCreatedUser(String val) {
    this.createdUser = val;
  }

  /**
   * 作成日時を設定します
   * 
   * @param val
   *          作成日時
   */
  public void setCreatedDatetime(Date val) {
    this.createdDatetime = DateUtil.immutableCopy(val);
  }

  /**
   * 更新ユーザを設定します
   * 
   * @param val
   *          更新ユーザ
   */
  public void setUpdatedUser(String val) {
    this.updatedUser = val;
  }

  /**
   * 更新日時を設定します
   * 
   * @param val
   *          更新日時
   */
  public void setUpdatedDatetime(Date val) {
    this.updatedDatetime = DateUtil.immutableCopy(val);
  }

  public String getCategory1Attribute1() {
    return category1Attribute1;
  }

  public void setCategory1Attribute1(String category1Attribute1) {
    this.category1Attribute1 = category1Attribute1;
  }

  public String getCategory1Attribute2() {
    return category1Attribute2;
  }

  public void setCategory1Attribute2(String category1Attribute2) {
    this.category1Attribute2 = category1Attribute2;
  }

  public String getCategory1Attribute3() {
    return category1Attribute3;
  }

  public void setCategory1Attribute3(String category1Attribute3) {
    this.category1Attribute3 = category1Attribute3;
  }

  public String getCategory2Attribute1() {
    return category2Attribute1;
  }

  public void setCategory2Attribute1(String category2Attribute1) {
    this.category2Attribute1 = category2Attribute1;
  }

  public String getCategory2Attribute2() {
    return category2Attribute2;
  }

  public void setCategory2Attribute2(String category2Attribute2) {
    this.category2Attribute2 = category2Attribute2;
  }

  public String getCategory2Attribute3() {
    return category2Attribute3;
  }

  public void setCategory2Attribute3(String category2Attribute3) {
    this.category2Attribute3 = category2Attribute3;
  }

  public String getCategory3Attribute1() {
    return category3Attribute1;
  }

  public void setCategory3Attribute1(String category3Attribute1) {
    this.category3Attribute1 = category3Attribute1;
  }

  public String getCategory3Attribute2() {
    return category3Attribute2;
  }

  public void setCategory3Attribute2(String category3Attribute2) {
    this.category3Attribute2 = category3Attribute2;
  }

  public String getCategory3Attribute3() {
    return category3Attribute3;
  }

  public void setCategory3Attribute3(String category3Attribute3) {
    this.category3Attribute3 = category3Attribute3;
  }

  public String getCategory4Attribute1() {
    return category4Attribute1;
  }

  public void setCategory4Attribute1(String category4Attribute1) {
    this.category4Attribute1 = category4Attribute1;
  }

  public String getCategory4Attribute2() {
    return category4Attribute2;
  }

  public void setCategory4Attribute2(String category4Attribute2) {
    this.category4Attribute2 = category4Attribute2;
  }

  public String getCategory4Attribute3() {
    return category4Attribute3;
  }

  public void setCategory4Attribute3(String category4Attribute3) {
    this.category4Attribute3 = category4Attribute3;
  }

  public String getCategory5Attribute1() {
    return category5Attribute1;
  }

  public void setCategory5Attribute1(String category5Attribute1) {
    this.category5Attribute1 = category5Attribute1;
  }

  public String getCategory5Attribute2() {
    return category5Attribute2;
  }

  public void setCategory5Attribute2(String category5Attribute2) {
    this.category5Attribute2 = category5Attribute2;
  }

  public String getCategory5Attribute3() {
    return category5Attribute3;
  }

  public void setCategory5Attribute3(String category5Attribute3) {
    this.category5Attribute3 = category5Attribute3;
  }

  public String getCategoryAttributeValue1() {
    return categoryAttributeValue1;
  }

  public void setCategoryAttributeValue1(String categoryAttributeValue1) {
    this.categoryAttributeValue1 = categoryAttributeValue1;
  }

  public String getCategoryAttributeValue2() {
    return categoryAttributeValue2;
  }

  public void setCategoryAttributeValue2(String categoryAttributeValue2) {
    this.categoryAttributeValue2 = categoryAttributeValue2;
  }

  public String getCategoryAttributeValue3() {
    return categoryAttributeValue3;
  }

  public void setCategoryAttributeValue3(String categoryAttributeValue3) {
    this.categoryAttributeValue3 = categoryAttributeValue3;
  }

  public String getImportFlag() {
    return importFlag;
  }

  public void setImportFlag(String importFlag) {
    this.importFlag = importFlag;
  }

/**
 * @param categoryAttributeValueEn the categoryAttributeValueEn to set
 */
public void setCategoryAttributeValueEn(String categoryAttributeValueEn) {
	this.categoryAttributeValueEn = categoryAttributeValueEn;
}

/**
 * @return the categoryAttributeValueEn
 */
public String getCategoryAttributeValueEn() {
	return categoryAttributeValueEn;
}

/**
 * @param categoryAttributeValueJp the categoryAttributeValueJp to set
 */
public void setCategoryAttributeValueJp(String categoryAttributeValueJp) {
	this.categoryAttributeValueJp = categoryAttributeValueJp;
}

/**
 * @return the categoryAttributeValueJp
 */
public String getCategoryAttributeValueJp() {
	return categoryAttributeValueJp;
}

/**
 * @param categoryAttributeValue1En the categoryAttributeValue1En to set
 */
public void setCategoryAttributeValue1En(String categoryAttributeValue1En) {
	this.categoryAttributeValue1En = categoryAttributeValue1En;
}

/**
 * @return the categoryAttributeValue1En
 */
public String getCategoryAttributeValue1En() {
	return categoryAttributeValue1En;
}

/**
 * @param categoryAttributeValue2En the categoryAttributeValue2En to set
 */
public void setCategoryAttributeValue2En(String categoryAttributeValue2En) {
	this.categoryAttributeValue2En = categoryAttributeValue2En;
}

/**
 * @return the categoryAttributeValue2En
 */
public String getCategoryAttributeValue2En() {
	return categoryAttributeValue2En;
}

/**
 * @param categoryAttributeValue3En the categoryAttributeValue3En to set
 */
public void setCategoryAttributeValue3En(String categoryAttributeValue3En) {
	this.categoryAttributeValue3En = categoryAttributeValue3En;
}

/**
 * @return the categoryAttributeValue3En
 */
public String getCategoryAttributeValue3En() {
	return categoryAttributeValue3En;
}

/**
 * @param categoryAttributeValue1Jp the categoryAttributeValue1Jp to set
 */
public void setCategoryAttributeValue1Jp(String categoryAttributeValue1Jp) {
	this.categoryAttributeValue1Jp = categoryAttributeValue1Jp;
}

/**
 * @return the categoryAttributeValue1Jp
 */
public String getCategoryAttributeValue1Jp() {
	return categoryAttributeValue1Jp;
}

/**
 * @param categoryAttributeValue2Jp the categoryAttributeValue2Jp to set
 */
public void setCategoryAttributeValue2Jp(String categoryAttributeValue2Jp) {
	this.categoryAttributeValue2Jp = categoryAttributeValue2Jp;
}

/**
 * @return the categoryAttributeValue2Jp
 */
public String getCategoryAttributeValue2Jp() {
	return categoryAttributeValue2Jp;
}

/**
 * @param categoryAttributeValue3Jp the categoryAttributeValue3Jp to set
 */
public void setCategoryAttributeValue3Jp(String categoryAttributeValue3Jp) {
	this.categoryAttributeValue3Jp = categoryAttributeValue3Jp;
}

/**
 * @return the categoryAttributeValue3Jp
 */
public String getCategoryAttributeValue3Jp() {
	return categoryAttributeValue3Jp;
}

/**
 * @param category5Attribute3En the category5Attribute3En to set
 */
public void setCategory5Attribute3En(String category5Attribute3En) {
	this.category5Attribute3En = category5Attribute3En;
}

/**
 * @return the category5Attribute3En
 */
public String getCategory5Attribute3En() {
	return category5Attribute3En;
}

/**
 * @param category5Attribute2En the category5Attribute2En to set
 */
public void setCategory5Attribute2En(String category5Attribute2En) {
	this.category5Attribute2En = category5Attribute2En;
}

/**
 * @return the category5Attribute2En
 */
public String getCategory5Attribute2En() {
	return category5Attribute2En;
}

/**
 * @param category5Attribute1En the category5Attribute1En to set
 */
public void setCategory5Attribute1En(String category5Attribute1En) {
	this.category5Attribute1En = category5Attribute1En;
}

/**
 * @return the category5Attribute1En
 */
public String getCategory5Attribute1En() {
	return category5Attribute1En;
}

/**
 * @param category4Attribute3En the category4Attribute3En to set
 */
public void setCategory4Attribute3En(String category4Attribute3En) {
	this.category4Attribute3En = category4Attribute3En;
}

/**
 * @return the category4Attribute3En
 */
public String getCategory4Attribute3En() {
	return category4Attribute3En;
}

/**
 * @param category4Attribute2En the category4Attribute2En to set
 */
public void setCategory4Attribute2En(String category4Attribute2En) {
	this.category4Attribute2En = category4Attribute2En;
}

/**
 * @return the category4Attribute2En
 */
public String getCategory4Attribute2En() {
	return category4Attribute2En;
}

/**
 * @param category4Attribute1En the category4Attribute1En to set
 */
public void setCategory4Attribute1En(String category4Attribute1En) {
	this.category4Attribute1En = category4Attribute1En;
}

/**
 * @return the category4Attribute1En
 */
public String getCategory4Attribute1En() {
	return category4Attribute1En;
}

/**
 * @param category3Attribute3En the category3Attribute3En to set
 */
public void setCategory3Attribute3En(String category3Attribute3En) {
	this.category3Attribute3En = category3Attribute3En;
}

/**
 * @return the category3Attribute3En
 */
public String getCategory3Attribute3En() {
	return category3Attribute3En;
}

/**
 * @param category3Attribute2En the category3Attribute2En to set
 */
public void setCategory3Attribute2En(String category3Attribute2En) {
	this.category3Attribute2En = category3Attribute2En;
}

/**
 * @return the category3Attribute2En
 */
public String getCategory3Attribute2En() {
	return category3Attribute2En;
}

/**
 * @param category3Attribute1En the category3Attribute1En to set
 */
public void setCategory3Attribute1En(String category3Attribute1En) {
	this.category3Attribute1En = category3Attribute1En;
}

/**
 * @return the category3Attribute1En
 */
public String getCategory3Attribute1En() {
	return category3Attribute1En;
}

/**
 * @param category2Attribute3En the category2Attribute3En to set
 */
public void setCategory2Attribute3En(String category2Attribute3En) {
	this.category2Attribute3En = category2Attribute3En;
}

/**
 * @return the category2Attribute3En
 */
public String getCategory2Attribute3En() {
	return category2Attribute3En;
}

/**
 * @param category2Attribute2En the category2Attribute2En to set
 */
public void setCategory2Attribute2En(String category2Attribute2En) {
	this.category2Attribute2En = category2Attribute2En;
}

/**
 * @return the category2Attribute2En
 */
public String getCategory2Attribute2En() {
	return category2Attribute2En;
}

/**
 * @param category2Attribute1En the category2Attribute1En to set
 */
public void setCategory2Attribute1En(String category2Attribute1En) {
	this.category2Attribute1En = category2Attribute1En;
}

/**
 * @return the category2Attribute1En
 */
public String getCategory2Attribute1En() {
	return category2Attribute1En;
}

/**
 * @param category1Attribute3En the category1Attribute3En to set
 */
public void setCategory1Attribute3En(String category1Attribute3En) {
	this.category1Attribute3En = category1Attribute3En;
}

/**
 * @return the category1Attribute3En
 */
public String getCategory1Attribute3En() {
	return category1Attribute3En;
}

/**
 * @param category1Attribute2En the category1Attribute2En to set
 */
public void setCategory1Attribute2En(String category1Attribute2En) {
	this.category1Attribute2En = category1Attribute2En;
}

/**
 * @return the category1Attribute2En
 */
public String getCategory1Attribute2En() {
	return category1Attribute2En;
}

/**
 * @param category1Attribute1En the category1Attribute1En to set
 */
public void setCategory1Attribute1En(String category1Attribute1En) {
	this.category1Attribute1En = category1Attribute1En;
}

/**
 * @return the category1Attribute1En
 */
public String getCategory1Attribute1En() {
	return category1Attribute1En;
}

/**
 * @param category5Attribute3Jp the category5Attribute3Jp to set
 */
public void setCategory5Attribute3Jp(String category5Attribute3Jp) {
	this.category5Attribute3Jp = category5Attribute3Jp;
}

/**
 * @return the category5Attribute3Jp
 */
public String getCategory5Attribute3Jp() {
	return category5Attribute3Jp;
}

/**
 * @param category5Attribute2Jp the category5Attribute2Jp to set
 */
public void setCategory5Attribute2Jp(String category5Attribute2Jp) {
	this.category5Attribute2Jp = category5Attribute2Jp;
}

/**
 * @return the category5Attribute2Jp
 */
public String getCategory5Attribute2Jp() {
	return category5Attribute2Jp;
}

/**
 * @param category5Attribute1Jp the category5Attribute1Jp to set
 */
public void setCategory5Attribute1Jp(String category5Attribute1Jp) {
	this.category5Attribute1Jp = category5Attribute1Jp;
}

/**
 * @return the category5Attribute1Jp
 */
public String getCategory5Attribute1Jp() {
	return category5Attribute1Jp;
}

/**
 * @param category4Attribute3Jp the category4Attribute3Jp to set
 */
public void setCategory4Attribute3Jp(String category4Attribute3Jp) {
	this.category4Attribute3Jp = category4Attribute3Jp;
}

/**
 * @return the category4Attribute3Jp
 */
public String getCategory4Attribute3Jp() {
	return category4Attribute3Jp;
}

/**
 * @param category4Attribute2Jp the category4Attribute2Jp to set
 */
public void setCategory4Attribute2Jp(String category4Attribute2Jp) {
	this.category4Attribute2Jp = category4Attribute2Jp;
}

/**
 * @return the category4Attribute2Jp
 */
public String getCategory4Attribute2Jp() {
	return category4Attribute2Jp;
}

/**
 * @param category4Attribute1Jp the category4Attribute1Jp to set
 */
public void setCategory4Attribute1Jp(String category4Attribute1Jp) {
	this.category4Attribute1Jp = category4Attribute1Jp;
}

/**
 * @return the category4Attribute1Jp
 */
public String getCategory4Attribute1Jp() {
	return category4Attribute1Jp;
}

/**
 * @param category3Attribute3Jp the category3Attribute3Jp to set
 */
public void setCategory3Attribute3Jp(String category3Attribute3Jp) {
	this.category3Attribute3Jp = category3Attribute3Jp;
}

/**
 * @return the category3Attribute3Jp
 */
public String getCategory3Attribute3Jp() {
	return category3Attribute3Jp;
}

/**
 * @param category3Attribute2Jp the category3Attribute2Jp to set
 */
public void setCategory3Attribute2Jp(String category3Attribute2Jp) {
	this.category3Attribute2Jp = category3Attribute2Jp;
}

/**
 * @return the category3Attribute2Jp
 */
public String getCategory3Attribute2Jp() {
	return category3Attribute2Jp;
}

/**
 * @param category3Attribute1Jp the category3Attribute1Jp to set
 */
public void setCategory3Attribute1Jp(String category3Attribute1Jp) {
	this.category3Attribute1Jp = category3Attribute1Jp;
}

/**
 * @return the category3Attribute1Jp
 */
public String getCategory3Attribute1Jp() {
	return category3Attribute1Jp;
}

/**
 * @param category2Attribute3Jp the category2Attribute3Jp to set
 */
public void setCategory2Attribute3Jp(String category2Attribute3Jp) {
	this.category2Attribute3Jp = category2Attribute3Jp;
}

/**
 * @return the category2Attribute3Jp
 */
public String getCategory2Attribute3Jp() {
	return category2Attribute3Jp;
}

/**
 * @param category2Attribute2Jp the category2Attribute2Jp to set
 */
public void setCategory2Attribute2Jp(String category2Attribute2Jp) {
	this.category2Attribute2Jp = category2Attribute2Jp;
}

/**
 * @return the category2Attribute2Jp
 */
public String getCategory2Attribute2Jp() {
	return category2Attribute2Jp;
}

/**
 * @param category2Attribute1Jp the category2Attribute1Jp to set
 */
public void setCategory2Attribute1Jp(String category2Attribute1Jp) {
	this.category2Attribute1Jp = category2Attribute1Jp;
}

/**
 * @return the category2Attribute1Jp
 */
public String getCategory2Attribute1Jp() {
	return category2Attribute1Jp;
}

/**
 * @param category1Attribute3Jp the category1Attribute3Jp to set
 */
public void setCategory1Attribute3Jp(String category1Attribute3Jp) {
	this.category1Attribute3Jp = category1Attribute3Jp;
}

/**
 * @return the category1Attribute3Jp
 */
public String getCategory1Attribute3Jp() {
	return category1Attribute3Jp;
}

/**
 * @param category1Attribute2Jp the category1Attribute2Jp to set
 */
public void setCategory1Attribute2Jp(String category1Attribute2Jp) {
	this.category1Attribute2Jp = category1Attribute2Jp;
}

/**
 * @return the category1Attribute2Jp
 */
public String getCategory1Attribute2Jp() {
	return category1Attribute2Jp;
}

/**
 * @param category1Attribute1Jp the category1Attribute1Jp to set
 */
public void setCategory1Attribute1Jp(String category1Attribute1Jp) {
	this.category1Attribute1Jp = category1Attribute1Jp;
}

/**
 * @return the category1Attribute1Jp
 */
public String getCategory1Attribute1Jp() {
	return category1Attribute1Jp;
}

}
