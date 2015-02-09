package jp.co.sint.webshop.service.stock;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 商品库存增量情報を保持するクラス
 * 
 * @author System Integrator Corp.
 */
public class StockTempInfo implements Serializable, Cloneable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private List<String> commodityCodeList = new ArrayList<String>();
  
  private Map<String,Long> tempMap = new HashMap<String,Long>();

  /**
   * @return the commodityCodeList
   */
  public List<String> getCommodityCodeList() {
    return commodityCodeList;
  }

  /**
   * @param commodityCodeList the commodityCodeList to set
   */
  public void setCommodityCodeList(List<String> commodityCodeList) {
    this.commodityCodeList = commodityCodeList;
  }

  /**
   * @return the tempMap
   */
  public Map<String, Long> getTempMap() {
    return tempMap;
  }

  /**
   * @param tempMap the tempMap to set
   */
  public void setTempMap(Map<String, Long> tempMap) {
    this.tempMap = tempMap;
  } 
}
