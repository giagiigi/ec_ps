package jp.co.sint.webshop.service.order;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import jp.co.sint.webshop.data.dto.JdShippingDetail;
import jp.co.sint.webshop.data.dto.JdShippingHeader;
import jp.co.sint.webshop.data.dto.ShippingDetail;
import jp.co.sint.webshop.data.dto.ShippingDetailComposition;
import jp.co.sint.webshop.data.dto.ShippingHeader;
import jp.co.sint.webshop.data.dto.TmallShippingDetail;
import jp.co.sint.webshop.data.dto.TmallShippingHeader;
import jp.co.sint.webshop.utility.BeanCriteria;
import jp.co.sint.webshop.utility.BeanUtil;
import jp.co.sint.webshop.utility.Sku;

/**
 * 出荷データのコンテナです。
 * 
 * @author System Integrator Corp.
 */
public class ShippingContainer implements Serializable {

  /** */
  private static final long serialVersionUID = 1L;

  private ShippingHeader shippingHeader;

  //add by os012 2011230 start 
  private TmallShippingHeader tmallShippingHeader;
  
  private JdShippingHeader jdShippingHeader;
  
  private List<TmallShippingDetail> TmallShippingDetails = new ArrayList<TmallShippingDetail>();
  
  private List<JdShippingDetail> jdShippingDetails = new ArrayList<JdShippingDetail>();
  //2012/11/19 促销活动 ob add start
  private List<ShippingDetailComposition> shippingDetailCommpositionList = new ArrayList<ShippingDetailComposition>();
  
  private HashMap<ShippingDetail, List<ShippingDetailComposition>> shippingDetailContainerMap = new HashMap<ShippingDetail, List<ShippingDetailComposition>>();

  //2012/11/19 促销活动 ob add end
  
  public String getTmallShippingNo() {
    if (getTmallShippingHeader() != null) {
      return getTmallShippingHeader().getShippingNo();
    } else {
      return null;
    }
  }
  
  public String getJdShippingNo() {
    if (getJdShippingHeader() != null) {
      return getJdShippingHeader().getShippingNo();
    } else {
      return null;
    }
  }
  
  private static class TmallSkuFinder implements BeanCriteria<TmallShippingDetail> {

    private Sku sku;

    public TmallSkuFinder(Sku sku) {
      this.sku = sku;
    }

    public boolean match(TmallShippingDetail t) {
      return sku.getShopCode().equals(t.getShopCode()) && sku.getSkuCode().equals(t.getSkuCode());
    }

  }
  
  public TmallShippingDetail getTmallShippingDetail(Sku sku) {
    return BeanUtil.findFirst(getTmallShippingDetails(), new TmallSkuFinder(sku));
  }
  
  public Set<Sku> getTmallSkuSet() {
    Set<Sku> skuSet = new TreeSet<Sku>();
    for (TmallShippingDetail sd : getTmallShippingDetails()) {
      if (sd != null) {
        skuSet.add(new Sku(sd.getShopCode(), sd.getSkuCode()));
      } else {
        skuSet.add(new Sku());
      }
    }
    return skuSet;
  }
//add by os012 2011230 end 
  
  
  private List<ShippingDetail> shippingDetails = new ArrayList<ShippingDetail>();

  public String getShippingNo() {
    if (getShippingHeader() != null) {
      return getShippingHeader().getShippingNo();
    } else {
      return null;
    }
  }
  
  private static class SkuFinder implements BeanCriteria<ShippingDetail> {

    private Sku sku;

    public SkuFinder(Sku sku) {
      this.sku = sku;
    }

    public boolean match(ShippingDetail t) {
      return sku.getShopCode().equals(t.getShopCode()) && sku.getSkuCode().equals(t.getSkuCode());
    }

  }

  public ShippingDetail getShippingDetail(Sku sku) {
    return BeanUtil.findFirst(getShippingDetails(), new SkuFinder(sku));
  }

  public Set<Sku> getSkuSet() {
    Set<Sku> skuSet = new TreeSet<Sku>();
    for (ShippingDetail sd : getShippingDetails()) {
      if (sd != null) {
        skuSet.add(new Sku(sd.getShopCode(), sd.getSkuCode()));
      } else {
        skuSet.add(new Sku());
      }
    }
    return skuSet;
  }

  /**
   * @return the shippingHeader
   */
  public ShippingHeader getShippingHeader() {
    return shippingHeader;
  }

  /**
   * @param shippingHeader
   *          the shippingHeader to set
   */
  public void setShippingHeader(ShippingHeader shippingHeader) {
    this.shippingHeader = shippingHeader;
  }

  /**
   * @return the shippingDetails
   */
  public List<ShippingDetail> getShippingDetails() {
    return shippingDetails;
  }

  /**
   * @param shippingDetails
   *          the shippingDetails to set
   */
  public void setShippingDetails(List<ShippingDetail> shippingDetails) {
    this.shippingDetails = shippingDetails;
  }

  /**
   * @param tmallShippingHeader the tmallShippingHeader to set
   */
  public void setTmallShippingHeader(TmallShippingHeader tmallShippingHeader) {
    this.tmallShippingHeader = tmallShippingHeader;
  }

  /**
   * @return the tmallShippingHeader
   */
  public TmallShippingHeader getTmallShippingHeader() {
    return tmallShippingHeader;
  }

  
  /**
   * @return the jdShippingHeader
   */
  public JdShippingHeader getJdShippingHeader() {
    return jdShippingHeader;
  }

  
  /**
   * @param jdShippingHeader the jdShippingHeader to set
   */
  public void setJdShippingHeader(JdShippingHeader jdShippingHeader) {
    this.jdShippingHeader = jdShippingHeader;
  }

  /**
   * @param tmallShippingDetails the tmallShippingDetails to set
   */
  public void setTmallShippingDetails(List<TmallShippingDetail> tmallShippingDetails) {
    TmallShippingDetails = tmallShippingDetails;
  }

  /**
   * @return the tmallShippingDetails
   */
  public List<TmallShippingDetail> getTmallShippingDetails() {
    return TmallShippingDetails;
  }

  
  
  /**
   * @return the jdShippingDetails
   */
  public List<JdShippingDetail> getJdShippingDetails() {
    return jdShippingDetails;
  }

  
  /**
   * @param jdShippingDetails the jdShippingDetails to set
   */
  public void setJdShippingDetails(List<JdShippingDetail> jdShippingDetails) {
    this.jdShippingDetails = jdShippingDetails;
  }

  public List<ShippingDetailComposition> getShippingDetailCommpositionList() {
    return shippingDetailCommpositionList;
  }

  
  public void setShippingDetailCommpositionList(List<ShippingDetailComposition> shippingDetailCommpositionList) {
    this.shippingDetailCommpositionList = shippingDetailCommpositionList;
  }

public HashMap<ShippingDetail, List<ShippingDetailComposition>> getShippingDetailContainerMap() {
	return shippingDetailContainerMap;
}

public void setShippingDetailContainerMap(
		HashMap<ShippingDetail, List<ShippingDetailComposition>> shippingDetailContainerMap) {
	this.shippingDetailContainerMap = shippingDetailContainerMap;
}

}
