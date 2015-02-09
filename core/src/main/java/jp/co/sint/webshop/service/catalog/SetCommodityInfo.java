package jp.co.sint.webshop.service.catalog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.service.CommodityContainer;

public class SetCommodityInfo implements Serializable {

  /**
	 * 
	 */
  private static final long serialVersionUID = 1L;

  private CommodityContainer setCommodity;

  private boolean isSingleSku = false;

  private List<CommodityContainer> childCommodity = new ArrayList<CommodityContainer>();

  public CommodityContainer getSetCommodity() {
    return setCommodity;
  }

  public void setSetCommodity(CommodityContainer setCommodity) {
    this.setCommodity = setCommodity;
  }

  public List<CommodityContainer> getChildCommodity() {
    return childCommodity;
  }

  public void setChildCommodity(List<CommodityContainer> childCommodity) {
    this.childCommodity = childCommodity;
  }

  public boolean isSingleSku() {
    return isSingleSku;
  }

  public void setSingleSku(boolean isSingleSku) {
    this.isSingleSku = isSingleSku;
  }
	
	
}
