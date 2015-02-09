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
 * @author System Integrator Corp.
 */
public class JdBrandListBean extends UIBackBean implements UISearchBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private List<JdBrandDetail> list = new ArrayList<JdBrandDetail>();

  /**
   * U1030110:京东商品品牌表
   * 
   * @author System Integrator Corp.
   */
  public static class JdBrandDetail implements Serializable {

    /** serial version uid */
    private static final long serialVersionUID = 1L;

    private String JdBrandCode;

    private String JdBrandName;

    
    /**
     * @return the JdBrandCode
     */
    public String getJdBrandCode() {
      return JdBrandCode;
    }

    
    /**
     * @param JdBrandCode the JdBrandCode to set
     */
    public void setJdBrandCode(String JdBrandCode) {
      this.JdBrandCode = JdBrandCode;
    }

    
    /**
     * @return the JdBrandName
     */
    public String getJdBrandName() {
      return JdBrandName;
    }

    
    /**
     * @param JdBrandName the JdBrandName to set
     */
    public void setJdBrandName(String JdBrandName) {
      this.JdBrandName = JdBrandName;
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
    return null;
  }

  @Override
  public String getModuleName() {
    return null;
  }

  
  /**
   * @return the list
   */
  public List<JdBrandDetail> getList() {
    return list;
  }

  
  /**
   * @param list the list to set
   */
  public void setList(List<JdBrandDetail> list) {
    this.list = list;
  }


}
