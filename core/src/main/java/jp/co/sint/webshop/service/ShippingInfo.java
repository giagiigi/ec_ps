package jp.co.sint.webshop.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.dto.ShippingDetail;
import jp.co.sint.webshop.data.dto.ShippingHeader;

public class ShippingInfo implements Serializable {

  /** UID */
  private static final long serialVersionUID = 1L;

  private ShippingHeader header;

  private List<ShippingDetail> details = new ArrayList<ShippingDetail>();

  /**
   * @return the header
   */
  public ShippingHeader getHeader() {
    return header;
  }

  /**
   * @param header
   *          the header to set
   */
  public void setHeader(ShippingHeader header) {
    this.header = header;
  }

  /**
   * @return the details
   */
  public List<ShippingDetail> getDetails() {
    return details;
  }

  /**
   * @param details
   *          the details to set
   */
  public void setDetails(List<ShippingDetail> details) {
    this.details = details;
  }

}
