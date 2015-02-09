package jp.co.sint.webshop.web.bean.back.catalog;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import jp.co.sint.webshop.data.attribute.Datetime;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.web.bean.UISearchBean;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerValue;
import jp.co.sint.webshop.web.webutility.RequestParameter;
/**
 *TM/JD sku商品关联
 */
public class CommodityMasterListBean extends UIBackBean implements UISearchBean  {

    private static final long serialVersionUID = 1L;
  
    @Datetime(format = DateUtil.DEFAULT_DATETIME_FORMAT)
    @Length(16)
    @Metadata(name = "主商品编号", order = 1)
    private String commodityCode;
  
    @Datetime(format = DateUtil.DEFAULT_DATETIME_FORMAT)
    @Length(50)
    @Metadata(name = "主商品名称", order = 2)
    private String commodityName;
  
    @Datetime(format = DateUtil.DEFAULT_DATETIME_FORMAT)
    @Metadata(name = "JD商品ID", order = 3)
    private String jdCommodityCode;
  
    @Datetime(format = DateUtil.DEFAULT_DATETIME_FORMAT)
    @Metadata(name = "TM商品ID", order = 4)
    private String tmallCommodityCode;
  
    //权限验证
    private boolean authorityIO = false;
    private String advancedSearchMode;
    
    //分页显示
    private PagerValue pagerValue;
    
    /**
     *  tm/jd多商品关联 画面一览集合
     */
    private List<CommodityMasterList> commoditymasterlist = new ArrayList<CommodityMasterList>();

      @Override
      public void createAttributes(RequestParameter reqparam) {
        this.setCommodityCode(reqparam.get("commodityCode"));
        this.setCommodityName(reqparam.get("commodityName"));
        this.setJdCommodityCode(reqparam.get("jdCommodityCode"));
        this.setTmallCommodityCode(reqparam.get("tmallCommodityCode"));
        }
  /**
   * tm/jd多商品关联 画面一览结合
   */
  public static class CommodityMasterList implements Serializable {
    private static final long serialVersionUID = 1L;
    private String commodityCode;     //主 商品名称
    private String commodityName;     //主商品编号
    private String jdCommodityCode;   //JD商品编号
    private String tmallCommodityCode;//天猫商品编号
    
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
    
  };
/**
 * 模块用户名的ID（无规范）
 */
 public String getModuleId() {
   return "U1040180";
 }



@Override
public String getModuleName() {
  return Messages.getString("web.bean.back.catalog.CommodityMasterListBean.0");
 
}




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




/**
 * @return the authorityIO
 */
public boolean isAuthorityIO() {
  return authorityIO;
}




/**
 * @param authorityIO the authorityIO to set
 */
public void setAuthorityIO(boolean authorityIO) {
  this.authorityIO = authorityIO;
}




/**
 * @return the advancedSearchMode
 */
public String getAdvancedSearchMode() {
  return advancedSearchMode;
}




/**
 * @param advancedSearchMode the advancedSearchMode to set
 */
public void setAdvancedSearchMode(String advancedSearchMode) {
  this.advancedSearchMode = advancedSearchMode;
}




/**
 * @return the pagerValue
 */
public PagerValue getPagerValue() {
  return pagerValue;
}




/**
 * @param pagerValue the pagerValue to set
 */
public void setPagerValue(PagerValue pagerValue) {
  this.pagerValue = pagerValue;
}




/**
 * @return the commoditymasterlist
 */
public List<CommodityMasterList> getCommoditymasterlist() {
  return commoditymasterlist;
}




/**
 * @param commoditymasterlist the commoditymasterlist to set
 */
public void setCommoditymasterlist(List<CommodityMasterList> commoditymasterlist) {
  this.commoditymasterlist = commoditymasterlist;
}

@Override
public void setSubJspId() {
  // TODO Auto-generated method stub
  
}




}
