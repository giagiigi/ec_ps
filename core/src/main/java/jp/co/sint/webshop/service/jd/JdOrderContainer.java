package jp.co.sint.webshop.service.jd;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.dto.JdCouponDetail;
import jp.co.sint.webshop.data.dto.JdOrderDetail;
import jp.co.sint.webshop.data.dto.JdOrderHeader;
import jp.co.sint.webshop.data.dto.JdShippingHeader;
import jp.co.sint.webshop.data.dto.OrderInvoice;

public class JdOrderContainer {

  private JdOrderHeader orderHeader;

  private JdShippingHeader shippingHeader;

  private List<JdOrderDetail> orderDetailList = new ArrayList<JdOrderDetail>();

  private List<JdShippingDetailContainer> shippingDetailList = new ArrayList<JdShippingDetailContainer>();

  private List<JdCouponDetail> couponDetailList = new ArrayList<JdCouponDetail>();

  private OrderInvoice orderInvoice;

  // 总重量
  private BigDecimal totalWeight;
  
  //卫星仓发贷一览
  private JdShippingHeader partShippingHeader;
  
  //卫星仓发货详细
  private List<JdShippingDetailContainer> partShippingDetailList = new ArrayList<JdShippingDetailContainer>();
  
  //折单信息
  private JdOrderExtraInfo extraInfo;
  

  /**
   * @return the orderHeader
   */
  public JdOrderHeader getOrderHeader() {
    return orderHeader;
  }

  /**
   * @param orderHeader
   *          the orderHeader to set
   */
  public void setOrderHeader(JdOrderHeader orderHeader) {
    this.orderHeader = orderHeader;
  }

  /**
   * @return the shippingHeader
   */
  public JdShippingHeader getShippingHeader() {
    return shippingHeader;
  }

  /**
   * @param shippingHeader
   *          the shippingHeader to set
   */
  public void setShippingHeader(JdShippingHeader shippingHeader) {
    this.shippingHeader = shippingHeader;
  }

  /**
   * @return the orderDetailList
   */
  public List<JdOrderDetail> getOrderDetailList() {
    return orderDetailList;
  }

  /**
   * @param orderDetailList2
   *          the orderDetailList to set
   */
  public void setOrderDetailList(List<JdOrderDetail> orderDetailList2) {
    this.orderDetailList = orderDetailList2;
  }


  /**
   * @return the couponDetailList
   */
  public List<JdCouponDetail> getCouponDetailList() {
    return couponDetailList;
  }

  /**
   * @param couponDetailList
   *          the couponDetailList to set
   */
  public void setCouponDetailList(List<JdCouponDetail> couponDetailList) {
    this.couponDetailList = couponDetailList;
  }

  /**
   * @return the totalWeight
   */
  public BigDecimal getTotalWeight() {
    return totalWeight;
  }

  /**
   * @param totalWeight
   *          the totalWeight to set
   */
  public void setTotalWeight(BigDecimal totalWeight) {
    this.totalWeight = totalWeight;
  }

  /**
   * @return the orderInvoice
   */
  public OrderInvoice getOrderInvoice() {
    return orderInvoice;
  }

  /**
   * @param orderInvoice
   *          the orderInvoice to set
   */
  public void setOrderInvoice(OrderInvoice orderInvoice) {
    this.orderInvoice = orderInvoice;
  }

  
  /**
   * @return the shippingDetailList
   */
  public List<JdShippingDetailContainer> getShippingDetailList() {
    return shippingDetailList;
  }

  
  /**
   * @param shippingDetailList the shippingDetailList to set
   */
  public void setShippingDetailList(List<JdShippingDetailContainer> shippingDetailList) {
    this.shippingDetailList = shippingDetailList;
  }

  
  /**
   * @return the partShippingHeader
   */
  public JdShippingHeader getPartShippingHeader() {
    return partShippingHeader;
  }

  
  /**
   * @param partShippingHeader the partShippingHeader to set
   */
  public void setPartShippingHeader(JdShippingHeader partShippingHeader) {
    this.partShippingHeader = partShippingHeader;
  }

  
  /**
   * @return the partShippingDetailList
   */
  public List<JdShippingDetailContainer> getPartShippingDetailList() {
    return partShippingDetailList;
  }

  
  /**
   * @param partShippingDetailList the partShippingDetailList to set
   */
  public void setPartShippingDetailList(List<JdShippingDetailContainer> partShippingDetailList) {
    this.partShippingDetailList = partShippingDetailList;
  }

  
  /**
   * @return the extraInfo
   */
  public JdOrderExtraInfo getExtraInfo() {
    return extraInfo;
  }

  
  /**
   * @param extraInfo the extraInfo to set
   */
  public void setExtraInfo(JdOrderExtraInfo extraInfo) {
    this.extraInfo = extraInfo;
  }

}
