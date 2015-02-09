package jp.co.sint.webshop.service.order;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import jp.co.sint.webshop.data.dto.CustomerCoupon;
import jp.co.sint.webshop.data.dto.CustomerVatInvoice;
import jp.co.sint.webshop.data.dto.JdOrderDetail;
import jp.co.sint.webshop.data.dto.OrderCampaign;
import jp.co.sint.webshop.data.dto.OrderDetail;
import jp.co.sint.webshop.data.dto.OrderHeader;
import jp.co.sint.webshop.data.dto.OrderInvoice;
import jp.co.sint.webshop.data.dto.ShippingDetail;
import jp.co.sint.webshop.data.dto.ShippingDetailComposition;
import jp.co.sint.webshop.data.dto.ShippingHeader;
import jp.co.sint.webshop.data.dto.TmallOrderDetail;
import jp.co.sint.webshop.data.dto.TmallOrderHeader;
import jp.co.sint.webshop.data.dto.TmallShippingDetail;
import jp.co.sint.webshop.data.dto.TmallShippingHeader;
import jp.co.sint.webshop.data.dto.JdOrderHeader;
import jp.co.sint.webshop.utility.BeanCriteria;
import jp.co.sint.webshop.utility.BeanUtil;
import jp.co.sint.webshop.utility.BigDecimalUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.Sku;

import org.apache.log4j.Logger;

/**
 * @author System Integrator Corp.
 */
public class OrderContainer implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private OrderHeader orderHeader;

  // add by os012 20111230 start
  private TmallOrderHeader tmallOrderHeader;

  private JdOrderHeader jdOrderHeader;
  
  private List<TmallOrderDetail> tmallIOrderDetails = new ArrayList<TmallOrderDetail>();

  private List<JdOrderDetail> JdOrderDetails = new ArrayList<JdOrderDetail>();
  
  // add by os012 20111230 end
  private List<OrderDetail> orderDetails = new ArrayList<OrderDetail>();

  private List<ShippingContainer> shippings = new ArrayList<ShippingContainer>();

  private OrderMailType orderMailType;

  private OrderSmsType orderSmsType;

  private List<CustomerCoupon> customerCouponlist;

  // 20111222 shen add start
  private OrderInvoice orderInvoice;

  private CustomerVatInvoice customerVatInvoice;

  // 20111222 shen add end
  // 2012-11-27 促销对应 ob add start
  // 发货套餐明细
  private List<ShippingDetailComposition> shippingDtailCompositions = new ArrayList<ShippingDetailComposition>();

  // 促销订单
  private List<OrderCampaign> orderCampaigns = new ArrayList<OrderCampaign>();

  // 20131018 txw add start
  private String mediaCode;

  // 20131018 txw add end

  /**
   * @return the shippingDtailCompositions
   */
  public List<ShippingDetailComposition> getShippingDtailCompositions() {
    return shippingDtailCompositions;
  }

  /**
   * @param shippingDtailCompositions
   *          the shippingDtailCompositions to set
   */
  public void setShippingDtailCompositions(List<ShippingDetailComposition> shippingDtailCompositions) {
    this.shippingDtailCompositions = shippingDtailCompositions;
  }

  /**
   * @return the orderCampaigns
   */
  public List<OrderCampaign> getOrderCampaigns() {
    return orderCampaigns;
  }

  /**
   * @param orderCampaigns
   *          the orderCampaigns to set
   */
  public void setOrderCampaigns(List<OrderCampaign> orderCampaigns) {
    this.orderCampaigns = orderCampaigns;
  }

  // 2012-11-27 促销对应 ob add end
  public String getOrderNo() {
    if (getOrderHeader() != null) {
      return getOrderHeader().getOrderNo();
    } else {
      return null;
    }
  }

  // add by os012 20111230 start
  /**
   * 获取淘宝订单号
   */
  public String getTmallOrderNo() {
    if (getTmallOrderHeader() != null) {
      return getTmallOrderHeader().getOrderNo();
    } else {
      return null;
    }
  }
  
  /**
   * 获取JD订单号
   */
  public String getJdOrderNo() {
    if (getJdOrderHeader() != null) {
      return getJdOrderHeader().getOrderNo();
    } else {
      return null;
    }
  }

  public Set<String> getTmallShippingNoSet() {
    Set<String> shippingNumbers = new TreeSet<String>();
    for (ShippingContainer sc : getShippings()) {
      TmallShippingHeader sh = sc.getTmallShippingHeader();
      if (sh != null && sh.getShippingNo() != null) {
        shippingNumbers.add(sc.getTmallShippingHeader().getShippingNo());
      } else {
        shippingNumbers.add("");
      }
    }
    Logger logger = Logger.getLogger(this.getClass());
    logger.debug(shippingNumbers);
    return shippingNumbers;
  }

  /**
   * TmallCommodityTotalAmount
   * 
   * @return
   */
  public BigDecimal getTmallCommodityTotalAmount() {
    BigDecimal totalAmount = BigDecimal.ZERO;

    for (ShippingContainer sc : shippings) {
      for (TmallShippingDetail sd : sc.getTmallShippingDetails()) {
        BigDecimal retailPrice = NumUtil.coalesce(sd.getRetailPrice(), BigDecimal.ZERO);
        long purchasingAmount = NumUtil.coalesce(sd.getPurchasingAmount(), 0L);
        totalAmount = totalAmount.add(BigDecimalUtil.multiply(retailPrice, purchasingAmount));
      }
    }
    if (getCustomerCouponlist() != null) {
      for (CustomerCoupon cc : getCustomerCouponlist()) {
        totalAmount = totalAmount.subtract(cc.getCouponPrice());
      }
    }
    return totalAmount;
  }

  /**
   * TmallSkuFinder
   * 
   * @author kousen-pa641
   */
  @SuppressWarnings("unused")
  private static class TmallSkuFinder implements BeanCriteria<TmallOrderDetail> {

    private Sku sku;

    public TmallSkuFinder(Sku sku) {
      this.sku = sku;
    }

    public boolean match(TmallOrderDetail od) {
      return sku.getShopCode().equals(od.getShopCode()) && sku.getSkuCode().equals(od.getSkuCode());
    }

  }

  /***
   * TmallTotalAmount
   * 
   * @return
   */
  public BigDecimal getTmallTotalAmount() {
    BigDecimal totalAmount = BigDecimal.ZERO;

    for (ShippingContainer sc : shippings) {
      for (TmallShippingDetail sd : sc.getTmallShippingDetails()) {
        BigDecimal retailPrice = NumUtil.coalesce(sd.getRetailPrice(), BigDecimal.ZERO);
        BigDecimal giftPrice = NumUtil.coalesce(sd.getGiftPrice(), BigDecimal.ZERO);
        long purchasingAmount = NumUtil.coalesce(sd.getPurchasingAmount(), 0L);
        totalAmount = totalAmount.add(BigDecimalUtil.multiply(retailPrice.add(giftPrice), purchasingAmount));
      }
      totalAmount = totalAmount.add(NumUtil.coalesce(sc.getTmallShippingHeader().getShippingCharge(), BigDecimal.ZERO));
    }

    return totalAmount;
  }

  // add by os012 20111230 end

  public Set<String> getShippingNoSet() {
    Set<String> shippingNumbers = new TreeSet<String>();
    for (ShippingContainer sc : getShippings()) {
      ShippingHeader sh = sc.getShippingHeader();
      if (sh != null && sh.getShippingNo() != null) {
        shippingNumbers.add(sc.getShippingHeader().getShippingNo());
      } else {
        shippingNumbers.add("");
      }
    }
    Logger logger = Logger.getLogger(this.getClass());
    logger.debug(shippingNumbers);
    return shippingNumbers;
  }

  public ShippingContainer getShipping(String shippingNo) {
    return BeanUtil.findFirst(getShippings(), new ShippingContainerFinder(shippingNo));
  }

  public OrderDetail getOrderDetail(Sku sku) {
    return BeanUtil.findFirst(getOrderDetails(), new SkuFinder(sku));
  }

  /**
   * @return the orderHeader
   */
  public OrderHeader getOrderHeader() {
    return orderHeader;
  }

  /**
   * @param orderHeader
   *          the orderHeader to set
   */
  public void setOrderHeader(OrderHeader orderHeader) {
    this.orderHeader = orderHeader;
  }

  /**
   * @return the orderDetails
   */
  public List<OrderDetail> getOrderDetails() {
    return orderDetails;
  }

  /**
   * @param orderDetails
   *          the orderDetails to set
   */
  public void setOrderDetails(List<OrderDetail> orderDetails) {
    this.orderDetails = orderDetails;
  }

  /**
   * @return the shippings
   */
  public List<ShippingContainer> getShippings() {
    return shippings;
  }

  /**
   * @param shippings
   *          the shippings to set
   */
  public void setShippings(List<ShippingContainer> shippings) {
    this.shippings = shippings;
  }

  /**
   * 受注情報より、決済金額を取得します。<BR>
   * 決済金額 = ( ((販売金額 + ギフト価格) × 数量) + 送料 ) × 出荷ヘッダ件数
   * 
   * @return 算出した決済金額。算出できない場合はnullを返す。
   */
  // modify by V10-CH start
  // public Long getTotalAmount() {
  // Long totalAmount = 0L;
  //
  // for (ShippingContainer sc : shippings) {
  // for (ShippingDetail sd : sc.getShippingDetails()) {
  // totalAmount += (NumUtil.toPrimitiveLong(sd.getRetailPrice(), 0L) +
  // NumUtil.toPrimitiveLong(sd.getGiftPrice(), 0L))
  // * NumUtil.toPrimitiveLong(sd.getPurchasingAmount(), 0L);
  // }
  // totalAmount +=
  // NumUtil.toPrimitiveLong(sc.getShippingHeader().getShippingCharge(), 0L);
  // }
  //
  // return totalAmount;
  // }
  public BigDecimal getTotalAmount() {
    BigDecimal totalAmount = BigDecimal.ZERO;

    for (ShippingContainer sc : shippings) {
      for (ShippingDetail sd : sc.getShippingDetails()) {
        BigDecimal retailPrice = NumUtil.coalesce(sd.getRetailPrice(), BigDecimal.ZERO);
        BigDecimal giftPrice = NumUtil.coalesce(sd.getGiftPrice(), BigDecimal.ZERO);
        long purchasingAmount = NumUtil.coalesce(sd.getPurchasingAmount(), 0L);
        totalAmount = totalAmount.add(BigDecimalUtil.multiply(retailPrice.add(giftPrice), purchasingAmount));
      }
      totalAmount = totalAmount.add(NumUtil.coalesce(sc.getShippingHeader().getShippingCharge(), BigDecimal.ZERO));
    }

    return totalAmount;
  }

  public BigDecimal getCommodityTotalAmount() {
    BigDecimal totalAmount = BigDecimal.ZERO;

    for (ShippingContainer sc : shippings) {
      for (ShippingDetail sd : sc.getShippingDetails()) {
        BigDecimal retailPrice = NumUtil.coalesce(sd.getRetailPrice(), BigDecimal.ZERO);
        long purchasingAmount = NumUtil.coalesce(sd.getPurchasingAmount(), 0L);
        totalAmount = totalAmount.add(BigDecimalUtil.multiply(retailPrice, purchasingAmount));
      }
    }
    if (getCustomerCouponlist() != null) {
      for (CustomerCoupon cc : getCustomerCouponlist()) {
        totalAmount = totalAmount.subtract(cc.getCouponPrice());
      }
    }
    return totalAmount;
  }

  // modify by V10-CH end

  /**
   * orderMailTypeを取得します。
   * 
   * @return orderMailType
   */

  public OrderMailType getOrderMailType() {
    return orderMailType;
  }

  /**
   * orderMailTypeを設定します。
   * 
   * @param orderMailType
   *          orderMailType
   */
  public void setOrderMailType(OrderMailType orderMailType) {
    this.orderMailType = orderMailType;
  }

  private static class ShippingContainerFinder implements BeanCriteria<ShippingContainer> {

    private String shippingNo;

    public ShippingContainerFinder(String shippingNo) {
      this.shippingNo = shippingNo;
    }

    public boolean match(ShippingContainer sc) {
      return shippingNo.equals(sc.getShippingHeader().getShippingNo());
    }

    public boolean tmallMatch(ShippingContainer sc) {
      return shippingNo.equals(sc.getTmallShippingHeader().getShippingNo());
    }
  }

  private static class SkuFinder implements BeanCriteria<OrderDetail> {

    private Sku sku;

    public SkuFinder(Sku sku) {
      this.sku = sku;
    }

    public boolean match(OrderDetail od) {
      return sku.getShopCode().equals(od.getShopCode()) && sku.getSkuCode().equals(od.getSkuCode());
    }

  }

  public List<CustomerCoupon> getCustomerCouponlist() {
    return customerCouponlist;
  }

  public void setCustomerCouponlist(List<CustomerCoupon> customerCouponlist) {
    this.customerCouponlist = customerCouponlist;
  }

  /**
   * orderSmsTypeを取得します。
   * 
   * @return orderSmsType orderSmsType
   */
  public OrderSmsType getOrderSmsType() {
    return orderSmsType;
  }

  /**
   * orderSmsTypeを設定します。
   * 
   * @param orderSmsType
   *          orderSmsType
   */
  public void setOrderSmsType(OrderSmsType orderSmsType) {
    this.orderSmsType = orderSmsType;
  }

  public OrderInvoice getOrderInvoice() {
    return orderInvoice;
  }

  public void setOrderInvoice(OrderInvoice orderInvoice) {
    this.orderInvoice = orderInvoice;
  }

  public CustomerVatInvoice getCustomerVatInvoice() {
    return customerVatInvoice;
  }

  public void setCustomerVatInvoice(CustomerVatInvoice customerVatInvoice) {
    this.customerVatInvoice = customerVatInvoice;
  }

  /**
   * @param tmallOrderHeader
   *          the tmallOrderHeader to set
   */
  public void setTmallOrderHeader(TmallOrderHeader tmallOrderHeader) {
    this.tmallOrderHeader = tmallOrderHeader;
  }

  /**
   * @return the tmallOrderHeader
   */
  public TmallOrderHeader getTmallOrderHeader() {
    return tmallOrderHeader;
  }

  
  /**
   * @return the jdOrderHeader
   */
  public JdOrderHeader getJdOrderHeader() {
    return jdOrderHeader;
  }

  
  /**
   * @param jdOrderHeader the jdOrderHeader to set
   */
  public void setJdOrderHeader(JdOrderHeader jdOrderHeader) {
    this.jdOrderHeader = jdOrderHeader;
  }

  /**
   * @param tmallIOrderDetails
   *          the tmallIOrderDetails to set
   */
  public void setTmallIOrderDetails(List<TmallOrderDetail> tmallIOrderDetails) {
    this.tmallIOrderDetails = tmallIOrderDetails;
  }

  /**
   * @return the tmallIOrderDetails
   */
  public List<TmallOrderDetail> getTmallIOrderDetails() {
    return tmallIOrderDetails;
  }

  
  /**
   * @return the jdOrderDetails
   */
  public List<JdOrderDetail> getJdOrderDetails() {
    return JdOrderDetails;
  }

  
  /**
   * @param jdOrderDetails the jdOrderDetails to set
   */
  public void setJdOrderDetails(List<JdOrderDetail> jdOrderDetails) {
    JdOrderDetails = jdOrderDetails;
  }

  /**
   * @return the mediaCode
   */
  public String getMediaCode() {
    return mediaCode;
  }

  /**
   * @param mediaCode
   *          the mediaCode to set
   */
  public void setMediaCode(String mediaCode) {
    this.mediaCode = mediaCode;
  }

}
