package jp.co.sint.webshop.service.jd.order;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.jd.open.api.sdk.domain.order.OrderSearchInfo;

public class JdOrderResultBean implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private List<OrderSearchInfo> orderList = new ArrayList<OrderSearchInfo>();

  private boolean errorFlg = false;

  /**
   * @return the orderList
   */
  public List<OrderSearchInfo> getOrderList() {
    return orderList;
  }

  /**
   * @param orderList
   *          the orderList to set
   */
  public void setOrderList(List<OrderSearchInfo> orderList) {
    this.orderList = orderList;
  }

  /**
   * @return the errorFlg
   */
  public boolean isErrorFlg() {
    return errorFlg;
  }

  /**
   * @param errorFlg
   *          the errorFlg to set
   */
  public void setErrorFlg(boolean errorFlg) {
    this.errorFlg = errorFlg;
  }

}
