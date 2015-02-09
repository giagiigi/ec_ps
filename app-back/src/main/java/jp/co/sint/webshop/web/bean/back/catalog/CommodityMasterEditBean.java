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
public class CommodityMasterEditBean extends UIBackBean implements UISearchBean  {

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
    @Metadata(name = "JD商品使用标志", order = 3)
    private String jdUseFlag;
  
    @Datetime(format = DateUtil.DEFAULT_DATETIME_FORMAT)
    @Metadata(name = "TM商品使用标志", order = 4)
    private String tmallUseFlag;
  
    @Datetime(format = DateUtil.DEFAULT_DATETIME_FORMAT)
    @Length(16)
    @Metadata(name = "JD商品编号", order = 5)
    private String jdCommodityCode;
    
    @Datetime(format = DateUtil.DEFAULT_DATETIME_FORMAT)
    @Length(16)
    @Metadata(name = "TMALL商品编号", order = 6)
    private String tmallCommodityCode;
    
    @Datetime(format = DateUtil.DEFAULT_DATETIME_FORMAT)
    @Length(16)
    @Metadata(name = "子商品编号", order = 6)
    private String skuCode;
   
    //子商品名称
    private String skuName;
    //单选框
    private String tmallUseFlg;
    private String jdUseFlg;
    //权限验证
    private boolean authorityIO = false;
    private String advancedSearchMode;
    //商品关联编号
    private String relatedComdtyCode;
    // 区分更新或增加
    private String displayMode;

    private String editMode;

    // 登录按钮
    private boolean displayLoginButtonFlg;

    // 更新按钮
    private boolean displayUpdateButtonFlg;

    // 删除按钮
    private boolean displayDeleteFlg;
    
    
    private PagerValue pagerValue;
    
    /**
     *  tm/jd多商品关联 画面一览集合
     */
    private List<CommodityMasterEditList> commoditymastereditlist = new ArrayList<CommodityMasterEditList>();
      
      @Override
      public void createAttributes(RequestParameter reqparam) {
        this.setCommodityCode(reqparam.get("commodityCode"));
        this.setCommodityName(reqparam.get("commodityName"));
        this.setJdUseFlag(reqparam.get("jdUseFlag"));
        this.setTmallUseFlag(reqparam.get("tmallUseFlag"));
        this.setJdCommodityCode(reqparam.get("jdCommodityCode"));
        this.setTmallCommodityCode(reqparam.get("tmallCommodityCode"));
        this.setSkuCode(reqparam.get("skuCode"));
        this.setSkuName(reqparam.get("skuName"));
        this.setTmallUseFlg(reqparam.get("tmallUseFlg"));
        this.setJdUseFlg(reqparam.get("jdUseFlg"));
        this.setRelatedComdtyCode(reqparam.get("relatedComdtyCode"));
        }
  
  public static class CommodityMasterEditList implements Serializable {
    private static final long serialVersionUID = 1L;
    private String commodityCode;
    private String commodityName;
    private String jdUseFlag;
    private String tmallUseFlag;
    private String skuCode;
    private String skuName;
    
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
     * @return the skuCode
     */
    public String getSkuCode() {
      return skuCode;
    }

    
    /**
     * @param skuCode the skuCode to set
     */
    public void setSkuCode(String skuCode) {
      this.skuCode = skuCode;
    }

    
    /**
     * @return the skuName
     */
    public String getSkuName() {
      return skuName;
    }

    
    /**
     * @param skuName the skuName to set
     */
    public void setSkuName(String skuName) {
      this.skuName = skuName;
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
  return Messages.getString("web.bean.back.catalog.CommodityMasterEditBean.0");
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
@Override
public void setSubJspId() {
  // TODO Auto-generated method stub
  
}
/**
 * @return the displayMode
 */
public String getDisplayMode() {
  return displayMode;
}
/**
 * @param displayMode the displayMode to set
 */
public void setDisplayMode(String displayMode) {
  this.displayMode = displayMode;
}




/**
 * @return the editMode
 */
public String getEditMode() {
  return editMode;
}




/**
 * @param editMode the editMode to set
 */
public void setEditMode(String editMode) {
  this.editMode = editMode;
}




/**
 * @return the displayLoginButtonFlg
 */
public boolean isDisplayLoginButtonFlg() {
  return displayLoginButtonFlg;
}




/**
 * @param displayLoginButtonFlg the displayLoginButtonFlg to set
 */
public void setDisplayLoginButtonFlg(boolean displayLoginButtonFlg) {
  this.displayLoginButtonFlg = displayLoginButtonFlg;
}




/**
 * @return the displayUpdateButtonFlg
 */
public boolean isDisplayUpdateButtonFlg() {
  return displayUpdateButtonFlg;
}




/**
 * @param displayUpdateButtonFlg the displayUpdateButtonFlg to set
 */
public void setDisplayUpdateButtonFlg(boolean displayUpdateButtonFlg) {
  this.displayUpdateButtonFlg = displayUpdateButtonFlg;
}




/**
 * @return the displayDeleteFlg
 */
public boolean isDisplayDeleteFlg() {
  return displayDeleteFlg;
}




/**
 * @param displayDeleteFlg the displayDeleteFlg to set
 */
public void setDisplayDeleteFlg(boolean displayDeleteFlg) {
  this.displayDeleteFlg = displayDeleteFlg;
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
 * @return the commoditymastereditlist
 */
public List<CommodityMasterEditList> getCommoditymastereditlist() {
  return commoditymastereditlist;
}




/**
 * @param commoditymastereditlist the commoditymastereditlist to set
 */
public void setCommoditymastereditlist(List<CommodityMasterEditList> commoditymastereditlist) {
  this.commoditymastereditlist = commoditymastereditlist;
}




/**
 * @return the skuCode
 */
public String getSkuCode() {
  return skuCode;
}




/**
 * @param skuCode the skuCode to set
 */
public void setSkuCode(String skuCode) {
  this.skuCode = skuCode;
}




/**
 * @return the skuName
 */
public String getSkuName() {
  return skuName;
}




/**
 * @param skuName the skuName to set
 */
public void setSkuName(String skuName) {
  this.skuName = skuName;
}




/**
 * @return the tmallUseFlg
 */
public String getTmallUseFlg() {
  return tmallUseFlg;
}




/**
 * @param tmallUseFlg the tmallUseFlg to set
 */
public void setTmallUseFlg(String tmallUseFlg) {
  this.tmallUseFlg = tmallUseFlg;
}




/**
 * @return the jdUseFlg
 */
public String getJdUseFlg() {
  return jdUseFlg;
}




/**
 * @param jdUseFlg the jdUseFlg to set
 */
public void setJdUseFlg(String jdUseFlg) {
  this.jdUseFlg = jdUseFlg;
}




/**
 * @return the relatedComdtyCode
 */
public String getRelatedComdtyCode() {
  return relatedComdtyCode;
}




/**
 * @param relatedComdtyCode the relatedComdtyCode to set
 */
public void setRelatedComdtyCode(String relatedComdtyCode) {
  this.relatedComdtyCode = relatedComdtyCode;
}


}
