package jp.co.sint.webshop.web.bean.back.catalog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.web.bean.UISearchBean;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.webutility.PagerValue;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U1030110:顧客マスタのデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class CategoryListBean extends UIBackBean implements UISearchBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private List<CategoryDetail> list = new ArrayList<CategoryDetail>();

  /**
   * U1030110:淘宝商品品牌表
   * 
   * @author System Integrator Corp.
   */
  public static class CategoryDetail implements Serializable {

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
    // TODO Auto-generated method stub
    
  }

  @Override
  public PagerValue getPagerValue() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void setPagerValue(PagerValue pagerValue) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void createAttributes(RequestParameter reqparam) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public String getModuleId() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String getModuleName() {
    // TODO Auto-generated method stub
    return null;
  }

  
  /**
   * @return the list
   */
  public List<CategoryDetail> getList() {
    return list;
  }

  
  /**
   * @param list the list to set
   */
  public void setList(List<CategoryDetail> list) {
    this.list = list;
  }


}
