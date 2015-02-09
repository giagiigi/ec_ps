package jp.co.sint.webshop.service.catalog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.attribute.Datetime;
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.data.dto.CommodityMaster;
import jp.co.sint.webshop.utility.DateUtil;

/**
 * @author System Integrator Corp.
 */
public class CommodityMasterInfo extends CommodityMaster {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;

  private List<CommodityMasterInfos> commoditymasterinfolist = new ArrayList<CommodityMasterInfos>();

  public static class CommodityMasterInfos implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    @Datetime(format = DateUtil.DEFAULT_DATETIME_FORMAT)
    @Length(16)
    @Metadata(name = "主商品编号", order = 1)
    private String commodityCode;
  
    @Datetime(format = DateUtil.DEFAULT_DATETIME_FORMAT)
    @Length(50)
    @Metadata(name = "主商品名", order = 2)
    private String commodityName;

    @Datetime(format = DateUtil.DEFAULT_DATETIME_FORMAT)
    @Length(16)
    @Metadata(name = "JD商品编号", order = 5)
    private String jdCommodityCode;
    
    @Datetime(format = DateUtil.DEFAULT_DATETIME_FORMAT)
    @Length(16)
    @Metadata(name = "TMALL商品编号", order = 6)
    private String tmallCommodityCode;

    
    /**
     * @return the commodityCode
     */
    public String getCommodityCode() {
      return commodityCode;
    }

    
    /**
     * @param commodityCode the commodityCode to set
     */
    public void setCommodityCode(String commodityCode) {
      this.commodityCode = commodityCode;
    }

    
    /**
     * @return the commodityName
     */
    public String getCommodityName() {
      return commodityName;
    }

    
    /**
     * @param commodityName the commodityName to set
     */
    public void setCommodityName(String commodityName) {
      this.commodityName = commodityName;
    }

    
    /**
     * @return the jdCommodityCode
     */
    public String getJdCommodityCode() {
      return jdCommodityCode;
    }

    
    /**
     * @param jdCommodityCode the jdCommodityCode to set
     */
    public void setJdCommodityCode(String jdCommodityCode) {
      this.jdCommodityCode = jdCommodityCode;
    }

    
    /**
     * @return the tmallCommodityCode
     */
    public String getTmallCommodityCode() {
      return tmallCommodityCode;
    }

    
    /**
     * @param tmallCommodityCode the tmallCommodityCode to set
     */
    public void setTmallCommodityCode(String tmallCommodityCode) {
      this.tmallCommodityCode = tmallCommodityCode;
    }
  
}

  
  /**
   * @return the commoditymasterinfolist
   */
  public List<CommodityMasterInfos> getCommoditymasterinfolist() {
    return commoditymasterinfolist;
  }

  
  /**
   * @param commoditymasterinfolist the commoditymasterinfolist to set
   */
  public void setCommoditymasterinfolist(List<CommodityMasterInfos> commoditymasterinfolist) {
    this.commoditymasterinfolist = commoditymasterinfolist;
  }
  }
