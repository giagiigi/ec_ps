package jp.co.sint.webshop.web.bean.back.order;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import jp.co.sint.webshop.data.attribute.Datetime;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.web.bean.UISearchBean;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerValue;
import jp.co.sint.webshop.web.webutility.RequestParameter;
/**
 * 京东拆分订单显示
 */
public class JdOrderListBean extends UIBackBean implements UISearchBean  {

  private static final long serialVersionUID = 1L;
  private PagerValue pagerValue;
  
  
  @Datetime(format = DateUtil.DEFAULT_DATETIME_FORMAT)
  @Metadata(name = "子订单号", order = 1)
  private String subOrderNo;
 
  @Datetime(format = DateUtil.DEFAULT_DATETIME_FORMAT)
  @Metadata(name = "子订单金额", order = 2)
  private String subOrderMoney;
 
  @Datetime(format = DateUtil.DEFAULT_DATETIME_FORMAT)
  @Metadata(name = "子发货状态", order = 3)
  private String jdSubStatus;
 
  @Datetime(format = DateUtil.DEFAULT_DATETIME_FORMAT)
  @Metadata(name = "母订单号", order = 4)
  private String theOrderNo;
 
  @Datetime(format = DateUtil.DEFAULT_DATETIME_FORMAT)
  @Metadata(name = "母订单金额", order = 5)
  private String theOrderMoney;
 
  @Datetime(format = DateUtil.DEFAULT_DATETIME_FORMAT)
  @Metadata(name = "母发货状态", order = 6)
  private String jdTheStatus;
  
  @Datetime
  @Metadata(name = "发货开始日期(From)", order = 7)
  private String searchFromPaymentDatetime;

  @Datetime
  @Metadata(name = "发货终止日(From)", order = 8)
  private String searchToPaymentDatetime;
  
  @Datetime(format = DateUtil.DEFAULT_DATETIME_FORMAT)
  @Metadata(name = "京东交易订单号", order = 9)
  private String customerCode;

  //20141225 zzy start
  //订单开始日期
  @Datetime
  @Metadata(name = "订单开始日期(From)", order = 10)
  private String orderFromPaymentDatetime;
  //订单结束日期
  @Datetime
  @Metadata(name = "订单结束日期(From)", order = 11)
  private String orderToPaymentDatetime;
  
  private String advancedSearchMode;
  private boolean authorityIO = false;
  
  //20141225 zzy end
  
  @Override
  public void createAttributes(RequestParameter reqparam) {
    this.setSubOrderNo(reqparam.getDateString("subOrderNo"));
    this.setSubOrderMoney(reqparam.getDateString("subOrderMone"));
    this.setJdSubStatus(reqparam.getDateString("jdSubStatus"));
    this.setTheOrderNo(reqparam.getDateString("theOrderNo"));
    this.setTheOrderMoney(reqparam.getDateString("theOrderMoney"));
    this.setJdTheStatus(reqparam.getDateString("jdTheStatus"));
    this.setAdvancedSearchMode(reqparam.getDateString("advancedSearchMode"));
    this.setSearchFromPaymentDatetime(reqparam.getDateString("searchFromPaymentDatetime"));
    this.setSearchToPaymentDatetime(reqparam.getDateString("searchToPaymentDatetime"));
    this.setCustomerCode(reqparam.getDateString("customerCode"));
    this.setOrderFromPaymentDatetime(reqparam.getDateString("orderFromPaymentDatetime"));
    this.setOrderToPaymentDatetime(reqparam.getDateString("orderToPaymentDatetime"));
    
  }
  
  /**
   * 
   */
  private List<String> jdList =new ArrayList<String>();
  /**
   * jd拆分订单页面现实集合
   */
  private List<JdOrderListList> jdlistlist = new ArrayList<JdOrderListList>();
  /**
   * jdorderlist页面显示集合
   */
  public static class JdOrderListList implements Serializable {
    private static final long serialVersionUID = 1L;
    private String subOrderNo;            //子订单编号
    private String subOrderMoney;         //子订单金额
    private String jdSubStatus;           //自订单发货状态
    private String theOrderNo;            //母订单编号
    private String theOrderMoney;         //母订单金额
    private String jdTheStatus;           //母订单发货状态
    private String customerCode;          //京东交易订单号
    
    /**
     * @return the subOrderNo
     */
    public String getSubOrderNo() {
      return subOrderNo;
    }
    
    /**
     * @param subOrderNo the subOrderNo to set
     */
    public void setSubOrderNo(String subOrderNo) {
      this.subOrderNo = subOrderNo;
    }
    
    /**
     * @return the subOrderMoney
     */
    public String getSubOrderMoney() {
      return subOrderMoney;
    }
    
    /**
     * @param subOrderMoney the subOrderMoney to set
     */
    public void setSubOrderMoney(String subOrderMoney) {
      this.subOrderMoney = subOrderMoney;
    }
    
    /**
     * @return the jdSubStatus
     */
    public String getJdSubStatus() {
      return jdSubStatus;
    }
    
    /**
     * @param jdSubStatus the jdSubStatus to set
     */
    public void setJdSubStatus(String jdSubStatus) {
      this.jdSubStatus = jdSubStatus;
    }
    
    /**
     * @return the theOrderNo
     */
    public String getTheOrderNo() {
      return theOrderNo;
    }
    
    /**
     * @param theOrderNo the theOrderNo to set
     */
    public void setTheOrderNo(String theOrderNo) {
      this.theOrderNo = theOrderNo;
    }
    
    /**
     * @return the theOrderMoney
     */
    public String getTheOrderMoney() {
      return theOrderMoney;
    }
    
    /**
     * @param theOrderMoney the theOrderMoney to set
     */
    public void setTheOrderMoney(String theOrderMoney) {
      this.theOrderMoney = theOrderMoney;
    }

    
    /**
     * @return the jdTheStatus
     */
    public String getJdTheStatus() {
      return jdTheStatus;
    }

    
    /**
     * @param jdTheStatus the jdTheStatus to set
     */
    public void setJdTheStatus(String jdTheStatus) {
      this.jdTheStatus = jdTheStatus;
    }

    
    /**
     * @return the customerCode
     */
    public String getCustomerCode() {
      return customerCode;
    }

    
    /**
     * @param customerCode the customerCode to set
     */
    public void setCustomerCode(String customerCode) {
      this.customerCode = customerCode;
    }
  };
  

  
/**
 * @return the subOrderNo
 */
public String getSubOrderNo() {
  return subOrderNo;
}


/**
 * @param subOrderNo the subOrderNo to set
 */
public void setSubOrderNo(String subOrderNo) {
  this.subOrderNo = subOrderNo;
}



/**
 * @return the jdSubStatus
 */
public String getJdSubStatus() {
  return jdSubStatus;
}


/**
 * @param jdSubStatus the jdSubStatus to set
 */
public void setJdSubStatus(String jdSubStatus) {
  this.jdSubStatus = jdSubStatus;
}


/**
 * @return the theOrderNo
 */
public String getTheOrderNo() {
  return theOrderNo;
}


/**
 * @param theOrderNo the theOrderNo to set
 */
public void setTheOrderNo(String theOrderNo) {
  this.theOrderNo = theOrderNo;
}



/**
 * @return the jdTheStatus
 */
public String getJdTheStatus() {
  return jdTheStatus;
}


/**
 * @param jdTheStatus the jdTheStatus to set
 */
public void setJdTheStatus(String jdTheStatus) {
  this.jdTheStatus = jdTheStatus;
}


/**
 * @return the jdList
 */
public List<String> getJdList() {
  return jdList;
}


/**
 * @param jdList the jdList to set
 */
public void setJdList(List<String> jdList) {
  this.jdList = jdList;
}

/**
 * 模块用户名的ID（无规范）
 */
 public String getModuleId() {
   return "U1071611";
 }

/**
 * 模块名(对应后台页面的显示)
 */
 public String getModuleName() {
   return Messages.getString("web.bean.back.order.JdOrderListBean.0");
  
 }




@Override
public void setSubJspId() {
}



/**
 * @return the theOrderMoney
 */
public String getTheOrderMoney() {
  return theOrderMoney;
}



/**
 * @param theOrderMoney the theOrderMoney to set
 */
public void setTheOrderMoney(String theOrderMoney) {
  this.theOrderMoney = theOrderMoney;
}



/**
 * @return the subOrderMoney
 */
public String getSubOrderMoney() {
  return subOrderMoney;
}



/**
 * @param subOrderMoney the subOrderMoney to set
 */
public void setSubOrderMoney(String subOrderMoney) {
  this.subOrderMoney = subOrderMoney;
}


/**
 * @return the jdlistlist
 */
public List<JdOrderListList> getJdlistlist() {
  return jdlistlist;
}


/**
 * @param jdlistlist the jdlistlist to set
 */
public void setJdlistlist(List<JdOrderListList> jdlistlist) {
  this.jdlistlist = jdlistlist;
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
 * @return the searchFromPaymentDatetime
 */
public String getSearchFromPaymentDatetime() {
  return searchFromPaymentDatetime;
}


/**
 * @param searchFromPaymentDatetime the searchFromPaymentDatetime to set
 */
public void setSearchFromPaymentDatetime(String searchFromPaymentDatetime) {
  this.searchFromPaymentDatetime = searchFromPaymentDatetime;
}


/**
 * @return the searchToPaymentDatetime
 */
public String getSearchToPaymentDatetime() {
  return searchToPaymentDatetime;
}


/**
 * @param searchToPaymentDatetime the searchToPaymentDatetime to set
 */
public void setSearchToPaymentDatetime(String searchToPaymentDatetime) {
  this.searchToPaymentDatetime = searchToPaymentDatetime;
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
 * @return the customerCode
 */
public String getCustomerCode() {
  return customerCode;
}



/**
 * @param customerCode the customerCode to set
 */
public void setCustomerCode(String customerCode) {
  this.customerCode = customerCode;
}



/**
 * @return the orderFromPaymentDatetime
 */
public String getOrderFromPaymentDatetime() {
  return orderFromPaymentDatetime;
}



/**
 * @param orderFromPaymentDatetime the orderFromPaymentDatetime to set
 */
public void setOrderFromPaymentDatetime(String orderFromPaymentDatetime) {
  this.orderFromPaymentDatetime = orderFromPaymentDatetime;
}



/**
 * @return the orderToPaymentDatetime
 */
public String getOrderToPaymentDatetime() {
  return orderToPaymentDatetime;
}



/**
 * @param orderToPaymentDatetime the orderToPaymentDatetime to set
 */
public void setOrderToPaymentDatetime(String orderToPaymentDatetime) {
  this.orderToPaymentDatetime = orderToPaymentDatetime;
}

}
