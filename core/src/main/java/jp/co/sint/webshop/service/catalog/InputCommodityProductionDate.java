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

import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.PrimaryKey;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.utility.DateUtil;

/**
 * 商品生产日期
 * 
 * @author System Integrator Corp.
 */
public class InputCommodityProductionDate implements Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = -1;

  /** SKU编号 */
  @PrimaryKey(1)
  @Required
  @Length(24)
  @AlphaNum2
  @Metadata(name = "SKU编号", order = 1)
  private String skuCode;

  /** 生产日期 */
  @Required
  @Metadata(name = "生产日期", order = 2)
  private Date earlistDate;

  /**
   * @return the skuCode
   */
  public String getSkuCode() {
    return skuCode;
  }

  /**
   * @return the earlistDate
   */
  public Date getEarlistDate() {
    return DateUtil.immutableCopy(earlistDate);
  }

  /**
   * @param skuCode
   *          the skuCode to set
   */
  public void setSkuCode(String skuCode) {
    this.skuCode = skuCode;
  }

  /**
   * @param earlistDate
   *          the earlistDate to set
   */
  public void setEarlistDate(Date earlistDate) {
    this.earlistDate = DateUtil.immutableCopy(earlistDate);
  }

}
