package jp.co.sint.webshop.service.catalog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.dto.CCommodityDetail;
import jp.co.sint.webshop.data.dto.CCommodityHeader;
import jp.co.sint.webshop.data.dto.CommodityDetail;
import jp.co.sint.webshop.data.dto.CommodityHeader;
import jp.co.sint.webshop.data.dto.SetCommodityComposition;
import jp.co.sint.webshop.data.dto.Stock;
import jp.co.sint.webshop.data.dto.StockStatus;

public class CommodityInfo implements Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;

  private CommodityHeader header;

  private CommodityDetail detail;
  
  private Stock stock;

  private StockStatus stockStatus;

  /** ショップ名 */
  private String shopName;

  /** レビュー点数 */
  private Long reviewScore;

  /** 有効在庫 */
  private Long availableStockQuantity;

  private CCommodityHeader cheader;
  
  private SetCommodityComposition setComposition;
  
  private List<SetCommodityComposition> setCompositionList = new ArrayList<SetCommodityComposition>();

  private CCommodityDetail cdetail;
  private List<CCommodityDetail> cdetailList = new ArrayList<CCommodityDetail>();
  

  
  /**
   * @return the cdetailList
   */
  public List<CCommodityDetail> getCdetailList() {
    return cdetailList;
  }

  
  /**
   * @param cdetailList the cdetailList to set
   */
  public void setCdetailList(List<CCommodityDetail> cdetailList) {
    this.cdetailList = cdetailList;
  }

  /**
   * @return the availableStockQuantity
   */
  public Long getAvailableStockQuantity() {
    return availableStockQuantity;
  }

  /**
   * @param availableStockQuantity
   *          the availableStockQuantity to set
   */
  public void setAvailableStockQuantity(Long availableStockQuantity) {
    this.availableStockQuantity = availableStockQuantity;
  }

  /**
   * detailを取得します。
   * 
   * @return detail
   */
  public CommodityDetail getDetail() {
    return detail;
  }

  /**
   * headerを取得します。
   * 
   * @return header
   */
  public CommodityHeader getHeader() {
    return header;
  }

  /**
   * detailを設定します。
   * 
   * @param detail
   *          detail
   */
  public void setDetail(CommodityDetail detail) {
    this.detail = detail;
  }

  /**
   * headerを設定します。
   * 
   * @param header
   *          header
   */
  public void setHeader(CommodityHeader header) {
    this.header = header;
  }

  /**
   * @return the reviewScore
   */
  public Long getReviewScore() {
    return reviewScore;
  }

  /**
   * @param reviewScore
   *          the reviewScore to set
   */
  public void setReviewScore(Long reviewScore) {
    this.reviewScore = reviewScore;
  }

  /**
   * @return the shopName
   */
  public String getShopName() {
    return shopName;
  }

  /**
   * @param shopName
   *          the shopName to set
   */
  public void setShopName(String shopName) {
    this.shopName = shopName;
  }

  /**
   * @return the stockStatus
   */
  public StockStatus getStockStatus() {
    return stockStatus;
  }

  /**
   * @param stockStatus
   *          the stockStatus to set
   */
  public void setStockStatus(StockStatus stockStatus) {
    this.stockStatus = stockStatus;
  }

  
  /**
   * stockを返します。
   * @return the stock
   */
  public Stock getStock() {
    return stock;
  }

  
  /**
   * stockを設定します。
   * @param stock 設定する stock
   */
  public void setStock(Stock stock) {
    this.stock = stock;
  }

  
  /**
   * @return the cheader
   */
  public CCommodityHeader getCheader() {
    return cheader;
  }

  
  /**
   * @param cheader the cheader to set
   */
  public void setCheader(CCommodityHeader cheader) {
    this.cheader = cheader;
  }

  
  /**
   * @return the cdetail
   */
  public CCommodityDetail getCdetail() {
    return cdetail;
  }

  
  /**
   * @param cdetail the cdetail to set
   */
  public void setCdetail(CCommodityDetail cdetail) {
    this.cdetail = cdetail;
  }


public SetCommodityComposition getSetComposition() {
	return setComposition;
}


public void setSetComposition(SetCommodityComposition setComposition) {
	this.setComposition = setComposition;
}


public List<SetCommodityComposition> getSetCompositionList() {
	return setCompositionList;
}


public void setSetCompositionList(
		List<SetCommodityComposition> setCompositionList) {
	this.setCompositionList = setCompositionList;
}

}
