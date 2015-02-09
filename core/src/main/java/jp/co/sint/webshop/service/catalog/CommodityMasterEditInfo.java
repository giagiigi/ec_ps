package jp.co.sint.webshop.service.catalog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import jp.co.sint.webshop.data.attribute.Datetime;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.dto.CommoditySku;
import jp.co.sint.webshop.utility.DateUtil;

/**
 * @author System Integrator Corp.
 */
public class CommodityMasterEditInfo extends CommoditySku {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;

  private CommoditySku cs;
  private List<CommodityMasterEditInfos> CommodityMasterEditlist = new ArrayList<CommodityMasterEditInfos>();

  public static class CommodityMasterEditInfos implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    @Datetime(format = DateUtil.DEFAULT_DATETIME_FORMAT)
    @Length(16)
    @Metadata(name = "商品编号", order = 1)
    private String commodityCode;
  
    @Datetime(format = DateUtil.DEFAULT_DATETIME_FORMAT)
    @Length(50)
    @Metadata(name = "商品名", order = 2)
    private String commodityName;

    @Datetime(format = DateUtil.DEFAULT_DATETIME_FORMAT)
    @Length(16)
    @Metadata(name = "jd使用标志", order = 5)
    private String jdUseFlag;
    
    @Datetime(format = DateUtil.DEFAULT_DATETIME_FORMAT)
    @Length(16)
    @Metadata(name = "TMALL使用标志", order = 6)
    private String tmallUseFlag;

    
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
     * @return the jdUseFlag
     */
    public String getJdUseFlag() {
      return jdUseFlag;
    }

    
    /**
     * @param jdUseFlag the jdUseFlag to set
     */
    public void setJdUseFlag(String jdUseFlag) {
      this.jdUseFlag = jdUseFlag;
    }

    
    /**
     * @return the tmallUseFlag
     */
    public String getTmallUseFlag() {
      return tmallUseFlag;
    }

    
    /**
     * @param tmallUseFlag the tmallUseFlag to set
     */
    public void setTmallUseFlag(String tmallUseFlag) {
      this.tmallUseFlag = tmallUseFlag;
    }

  
  
}

  
  /**
   * @return the commodityMasterEditlist
   */
  public List<CommodityMasterEditInfos> getCommodityMasterEditlist() {
    return CommodityMasterEditlist;
  }

  
  /**
   * @param commodityMasterEditlist the commodityMasterEditlist to set
   */
  public void setCommodityMasterEditlist(List<CommodityMasterEditInfos> commodityMasterEditlist) {
    CommodityMasterEditlist = commodityMasterEditlist;
  }


  
  /**
   * @return the cs
   */
  public CommoditySku getCs() {
    return cs;
  }


  
  /**
   * @param cs the cs to set
   */
  public void setCs(CommoditySku cs) {
    this.cs = cs;
  }


  }
