package jp.co.sint.webshop.configure;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DeliveryCompanyNoMapping implements Serializable {

  private static final long serialVersionUID = 1L;

  // 配送公司mapping
  private List<String> deliveryCompanyNoTmall = new ArrayList<String>();
  
  private List<String> deliveryCompanyNoJd = new ArrayList<String>();

  public List<String> getDeliveryCompanyNoTmall() {
    return deliveryCompanyNoTmall;
  }

  public void setDeliveryCompanyNoTmall(List<String> deliveryCompanyNoTmall) {
    this.deliveryCompanyNoTmall = deliveryCompanyNoTmall;
  }

  /**
   * @return the deliveryCompanyNoJd
   */
  public List<String> getDeliveryCompanyNoJd() {
    return deliveryCompanyNoJd;
  }

  
  /**
   * @param deliveryCompanyNoJd the deliveryCompanyNoJd to set
   */
  public void setDeliveryCompanyNoJd(List<String> deliveryCompanyNoJd) {
    this.deliveryCompanyNoJd = deliveryCompanyNoJd;
  }

}
