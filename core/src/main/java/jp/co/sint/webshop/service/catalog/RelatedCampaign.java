package jp.co.sint.webshop.service.catalog;

import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.data.dto.Campaign;

public class RelatedCampaign extends Campaign {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private String checkCode;

  /** 商品コード */
  @Required
  @Length(16)
  @Metadata(name = "商品コード", order = 3)
  private String commodityCode;

  /**
   * @return the checkCode
   */
  public String getCheckCode() {
    return checkCode;
  }

  /**
   * @param checkCode
   *          the checkCode to set
   */
  public void setCheckCode(String checkCode) {
    this.checkCode = checkCode;
  }

  /**
   * @return the commodityCode
   */
  public String getCommodityCode() {
    return commodityCode;
  }

  /**
   * @param commodityCode
   *          the commodityCode to set
   */
  public void setCommodityCode(String commodityCode) {
    this.commodityCode = commodityCode;
  }

}
