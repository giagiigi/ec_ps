package jp.co.sint.webshop.service.jd.order;

import jp.co.sint.webshop.service.SearchCondition;
import jp.co.sint.webshop.utility.StringUtil;

public class JdOrderSearchCondition extends SearchCondition {

  /**
   * 京东拆单 检索条件
   * */
  private static final long serialVersionUID = 1L;
  
  //发货开始结束日
  private String searchFromPaymentDatetime;
  private String searchToPaymentDatetime;
  //订单开始结束日
  private String orderFromPaymentDatetime;
  private String orderToPaymentDatetime;
  

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
  // 期间检查
  public boolean isValid() {

    boolean result = true;

    String startDateFrom = getSearchFromPaymentDatetime();
    String endDateTo = getSearchToPaymentDatetime();
    
    String orderDateFrom =getOrderFromPaymentDatetime();
    String orderDateTo = getOrderToPaymentDatetime();
    if (StringUtil.hasValueAllOf(startDateFrom, endDateTo)) {
      result &= StringUtil.isCorrectRange(startDateFrom, endDateTo);
    }
    if(StringUtil.hasValueAllOf(orderDateFrom,orderDateTo)){
      result &= StringUtil.isCorrectRange(orderDateFrom, orderDateTo);      
    }
    return result;
  }


}
