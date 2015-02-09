package jp.co.sint.webshop.service.tmall;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TmallTradeInfoList implements Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;

  // 订单header信息集合
  private List<TmallTradeHeader> orderHeaderList = new ArrayList<TmallTradeHeader>();

  // 发货header信息集合
  private List<TmallShippingDelivery> shippingHeaderList = new ArrayList<TmallShippingDelivery>();

  public List<TmallTradeHeader> getOrderHeaderList() {
    return orderHeaderList;
  }

  public void setOrderHeaderList(List<TmallTradeHeader> orderHeaderList) {
    this.orderHeaderList = orderHeaderList;
  }

  public List<TmallShippingDelivery> getShippingHeaderList() {
    return shippingHeaderList;
  }

  public void setShippingHeaderList(List<TmallShippingDelivery> shippingHeaderList) {
    this.shippingHeaderList = shippingHeaderList;
  }

}
