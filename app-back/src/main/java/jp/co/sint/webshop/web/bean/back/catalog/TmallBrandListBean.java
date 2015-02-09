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
public class TmallBrandListBean extends UIBackBean implements UISearchBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private List<TmallBrandDetail> list = new ArrayList<TmallBrandDetail>();

  /**
   * U1030110:淘宝商品品牌表
   * 
   * @author System Integrator Corp.
   */
  public static class TmallBrandDetail implements Serializable {

    /** serial version uid */
    private static final long serialVersionUID = 1L;

    private String tmallBrandCode;

    private String tmallBrandName;

    
    /**
     * @return the tmallBrandCode
     */
    public String getTmallBrandCode() {
      return tmallBrandCode;
    }

    
    /**
     * @param tmallBrandCode the tmallBrandCode to set
     */
    public void setTmallBrandCode(String tmallBrandCode) {
      this.tmallBrandCode = tmallBrandCode;
    }

    
    /**
     * @return the tmallBrandName
     */
    public String getTmallBrandName() {
      return tmallBrandName;
    }

    
    /**
     * @param tmallBrandName the tmallBrandName to set
     */
    public void setTmallBrandName(String tmallBrandName) {
      this.tmallBrandName = tmallBrandName;
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
  public List<TmallBrandDetail> getList() {
    return list;
  }

  
  /**
   * @param list the list to set
   */
  public void setList(List<TmallBrandDetail> list) {
    this.list = list;
  }


}
