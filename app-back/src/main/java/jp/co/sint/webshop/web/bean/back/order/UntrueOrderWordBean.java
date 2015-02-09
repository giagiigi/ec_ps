package jp.co.sint.webshop.web.bean.back.order;


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
 *虚假订单关键词bean
 */
public class UntrueOrderWordBean extends UIBackBean implements UISearchBean  {

  private static final long serialVersionUID = 1L;
  
  
  
  @Datetime(format = DateUtil.DEFAULT_DATETIME_FORMAT)
  @Metadata(name = "虚假订单关键词编号", order = 1)
  private String orderWordCode;
  
  @Datetime(format = DateUtil.DEFAULT_DATETIME_FORMAT)
  @Metadata(name = "虚假订单关键词名称名称", order = 2)
  private String orderWordName;
  
  @Datetime(format = DateUtil.DEFAULT_DATETIME_FORMAT)
  @Length(100)
  @Metadata(name = "虚假关键词检索名称", order = 3)
  private String searchWord;
  //权限验证
  private boolean authorityIO = false;
  private String advancedSearchMode;
  @Override
  public void createAttributes(RequestParameter reqparam) {
    this.setOrderWordCode(reqparam.get("orderWordCode"));
    this.setSearchWord(reqparam.get("searchWord"));
  }
  
  /**
   * 
   */
  private List<String> uowlist =new ArrayList<String>();
  /**
   * 虚假订单关键此页面显示集合
   */
  private List<UntrueOrderWordList> untrueorderwordlist = new ArrayList<UntrueOrderWordList>();
  
  private PagerValue pagerValue;
  /**
   * 虚假订单关键词页面显示集合
   */
  public static class UntrueOrderWordList implements Serializable {
    private static final long serialVersionUID = 1L;
    private String orderWordCode;            //虚假订单关键词编号
    private String orderWordName;         //虚假订单关键词名称
    
    /**
     * @return the orderWordCode
     */
    public String getOrderWordCode() {
      return orderWordCode;
    }
    
    /**
     * @param orderWordCode the orderWordCode to set
     */
    public void setOrderWordCode(String orderWordCode) {
      this.orderWordCode = orderWordCode;
    }
    
    /**
     * @return the orderWordName
     */
    public String getOrderWordName() {
      return orderWordName;
    }
    
    /**
     * @param orderWordName the orderWordName to set
     */
    public void setOrderWordName(String orderWordName) {
      this.orderWordName = orderWordName;
    }
  };
/**
 * 模块用户名的ID（无规范）
 */
 public String getModuleId() {
   return "U1071711";
 }

@Override
public void setSubJspId() {
  // TODO Auto-generated method stub
  
}

@Override
public PagerValue getPagerValue() {
  return pagerValue;
}

@Override
public void setPagerValue(PagerValue pagerValue) {
  this.pagerValue = pagerValue;
  
}


/**
 * @return the orderWordCode
 */
public String getOrderWordCode() {
  return orderWordCode;
}


/**
 * @param orderWordCode the orderWordCode to set
 */
public void setOrderWordCode(String orderWordCode) {
  this.orderWordCode = orderWordCode;
}


/**
 * @return the orderWordName
 */
public String getOrderWordName() {
  return orderWordName;
}


/**
 * @param orderWordName the orderWordName to set
 */
public void setOrderWordName(String orderWordName) {
  this.orderWordName = orderWordName;
}


/**
 * @return the uowlist
 */
public List<String> getUowlist() {
  return uowlist;
}


/**
 * @param uowlist the uowlist to set
 */
public void setUowlist(List<String> uowlist) {
  this.uowlist = uowlist;
}


/**
 * @return the untrueorderwordlist
 */
public List<UntrueOrderWordList> getUntrueorderwordlist() {
  return untrueorderwordlist;
}


/**
 * @param untrueorderwordlist the untrueorderwordlist to set
 */
public void setUntrueorderwordlist(List<UntrueOrderWordList> untrueorderwordlist) {
  this.untrueorderwordlist = untrueorderwordlist;
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
 * @return the searchWord
 */
public String getSearchWord() {
  return searchWord;
}


/**
 * @param searchWord the searchWord to set
 */
public void setSearchWord(String searchWord) {
  this.searchWord = searchWord;
}

@Override
public String getModuleName() {
  return Messages.getString("web.bean.back.order.UntrueOrderWordBean.0");
}


}
