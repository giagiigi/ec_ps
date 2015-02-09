package jp.co.sint.webshop.service.jd;

import java.util.List;

import jp.co.sint.webshop.data.dto.JdShippingDetail;
import jp.co.sint.webshop.data.dto.JdShippingDetailComposition;

/**
 * author : huangdahui date : 2014-6-18
 */
public class JdShippingDetailContainer {

  private JdShippingDetail shippingDetail;

  private List<JdShippingDetailComposition> compositionList;

  /**
   * @return the compositionList
   */
  public List<JdShippingDetailComposition> getCompositionList() {
    return compositionList;
  }

  /**
   * @param compositionList
   *          the compositionList to set
   */
  public void setCompositionList(List<JdShippingDetailComposition> compositionList) {
    this.compositionList = compositionList;
  }

  /**
   * @return the shippingDetail
   */
  public JdShippingDetail getShippingDetail() {
    return shippingDetail;
  }

  /**
   * @param shippingDetail
   *          the shippingDetail to set
   */
  public void setShippingDetail(JdShippingDetail shippingDetail) {
    this.shippingDetail = shippingDetail;
  }

}
