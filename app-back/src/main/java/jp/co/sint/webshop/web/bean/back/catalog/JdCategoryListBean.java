package jp.co.sint.webshop.web.bean.back.catalog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.web.bean.UISearchBean;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.webutility.PagerValue;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * 2014/04/25 京东WBS对应 ob_卢 add 
 */
public class JdCategoryListBean extends UIBackBean implements UISearchBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private List<JdCategoryDetail> list = new ArrayList<JdCategoryDetail>();

  /**
   * U1030110:京东商品品牌表
   * 
   * @author System Integrator Corp.
   */
  public static class JdCategoryDetail implements Serializable {

    /** serial version uid */
    private static final long serialVersionUID = 1L;

    private String categoryCode;

    private String categoryName;
    
    private String parentName;

    
    /**
     * @return the categoryCode
     */
    public String getCategoryCode() {
      return categoryCode;
    }

    
    /**
     * @param categoryCode the categoryCode to set
     */
    public void setCategoryCode(String categoryCode) {
      this.categoryCode = categoryCode;
    }

    
    /**
     * @return the categoryName
     */
    public String getCategoryName() {
      return categoryName;
    }

    
    /**
     * @param categoryName the categoryName to set
     */
    public void setCategoryName(String categoryName) {
      this.categoryName = categoryName;
    }

    
    /**
     * @return the parentName
     */
    public String getParentName() {
      return parentName;
    }

    
    /**
     * @param parentName the parentName to set
     */
    public void setParentName(String parentName) {
      this.parentName = parentName;
    }

  }

  @Override
  public void setSubJspId() {
    
  }

  @Override
  public PagerValue getPagerValue() {
    return null;
  }

  @Override
  public void setPagerValue(PagerValue pagerValue) {
    
  }

  @Override
  public void createAttributes(RequestParameter reqparam) {
    
  }

  @Override
  public String getModuleId() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String getModuleName() {
    return null;
  }

  
  /**
   * @return the list
   */
  public List<JdCategoryDetail> getList() {
    return list;
  }

  
  /**
   * @param list the list to set
   */
  public void setList(List<JdCategoryDetail> list) {
    this.list = list;
  }


}
