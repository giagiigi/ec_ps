//
// Copyright(C) 2007-2008 System Integrator Corp.
// All rights reserved.
//
// このファイルはEDMファイルから自動生成されます。
// 直接編集しないで下さい。
//
package jp.co.sint.webshop.data.dto;

import java.io.Serializable;
import java.util.Date;

import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.WebshopEntity;
import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.PrimaryKey;
import jp.co.sint.webshop.data.attribute.Required;

/**
 * 「增值税发票(CUSTOMER_VAT_INVOICE)」テーブルの1行分のレコードを表すDTO(Data Transfer Object)です。
 * 
 * @author Kousen.
 */
public class CustomerVatInvoice implements Serializable, WebshopEntity {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** 顾客编号 */
  @PrimaryKey(1)
  @Required
  @Length(16)
  @AlphaNum2
  @Metadata(name = "顾客编号", order = 1)
  private String customerCode;

  @Required
  @Length(60)
  @Metadata(name = "公司名称", order = 2)
  private String companyName;

  @Required
  @Length(20)
  @AlphaNum2
  @Metadata(name = "纳税人识别号", order = 3)
  private String taxpayerCode;

  @Required
  @Length(100)
  @Metadata(name = "地址", order = 4)
  private String address;

  @Required
  @Length(20)
  @Digit(allowNegative = false)
  @Metadata(name = "电话号码", order = 5)
  private String tel;

  @Required
  @Length(50)
  @Metadata(name = "银行名称", order = 6)
  private String bankName;

  @Required
  @Length(25)
  @AlphaNum2
  @Metadata(name = "银行编号", order = 7)
  private String bankNo;

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

  public String getCustomerCode() {
    return customerCode;
  }

  public void setCustomerCode(String customerCode) {
    this.customerCode = customerCode;
  }

  public String getCompanyName() {
    return companyName;
  }

  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }

  public String getTaxpayerCode() {
    return taxpayerCode;
  }

  public void setTaxpayerCode(String taxpayerCode) {
    this.taxpayerCode = taxpayerCode;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getTel() {
    return tel;
  }

  public void setTel(String tel) {
    this.tel = tel;
  }

  public String getBankName() {
    return bankName;
  }

  public void setBankName(String bankName) {
    this.bankName = bankName;
  }

  public String getBankNo() {
    return bankNo;
  }

  public void setBankNo(String bankNo) {
    this.bankNo = bankNo;
  }

  public Long getOrmRowid() {
    return ormRowid;
  }

  public void setOrmRowid(Long ormRowid) {
    this.ormRowid = ormRowid;
  }

  public String getCreatedUser() {
    return createdUser;
  }

  public void setCreatedUser(String createdUser) {
    this.createdUser = createdUser;
  }

  public Date getCreatedDatetime() {
    return createdDatetime;
  }

  public void setCreatedDatetime(Date createdDatetime) {
    this.createdDatetime = createdDatetime;
  }

  public String getUpdatedUser() {
    return updatedUser;
  }

  public void setUpdatedUser(String updatedUser) {
    this.updatedUser = updatedUser;
  }

  public Date getUpdatedDatetime() {
    return updatedDatetime;
  }

  public void setUpdatedDatetime(Date updatedDatetime) {
    this.updatedDatetime = updatedDatetime;
  }

}
