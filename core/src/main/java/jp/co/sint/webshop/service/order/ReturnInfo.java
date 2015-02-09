package jp.co.sint.webshop.service.order;

import java.io.Serializable;

import jp.co.sint.webshop.data.dto.ReturnHeader;

/**
 * 退换货データのコンテナです。
 * 
 * @author System Integrator Corp.
 */
public class ReturnInfo implements Serializable {
  
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private ReturnHeader returnHeader;
  
  private ShippingContainer shippingContainer;

  
  public ReturnHeader getReturnHeader() {
    return returnHeader;
  }

  
  public void setReturnheader(ReturnHeader returnHeader) {
    this.returnHeader = returnHeader;
  }

  
  public ShippingContainer getShippingContainer() {
    return shippingContainer;
  }

  
  public void setShippingContainer(ShippingContainer shippingContainer) {
    this.shippingContainer = shippingContainer;
  }
  
  
  
}
