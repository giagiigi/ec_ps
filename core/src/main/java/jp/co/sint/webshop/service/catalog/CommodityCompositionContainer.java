package jp.co.sint.webshop.service.catalog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.dto.CommodityDetail;
import jp.co.sint.webshop.data.dto.Stock;

public class CommodityCompositionContainer implements Serializable {

	  /** serial version uid */
	  private static final long serialVersionUID = 1L;

	  private String shopCode;
	  
	  private String commodityCode;
	  
	  private String commodityName;
	  
	  private String commodityNameJp;
	  
	  private String commodityNameEn;
	  
	  private String representSkuCode;
	  
	  private String representSkuUnitPrice;
	  
	  private String stockManagementType;
	  
	  private boolean availableStockQuantity;
	  
	  private String commodityStandard1Name;
	  
	  private String commodityStandard1NameJp;
	  
	  private String commodityStandard1NameEn;

	  private String commodityStandard2Name;
	  
	  private String commodityStandard2NameJp;
	  
	  private String commodityStandard2NameEn;
	  
	  private boolean salableComposition;
	  
	  private List<CommodityDetail> commodityDetailList = new ArrayList<CommodityDetail>();
	  
	  private List<Stock> stockList = new ArrayList<Stock>();

	  /**
	   * shopCodeを取得します。
	   * @return shopCode
	   */
	  public String getShopCode() {
	      return shopCode;
	  }

	  /**
	   * shopCodeを設定します。
	   * @param shopCode shopCode
	   */
	  public void setShopCode(String shopCode) {
	      this.shopCode = shopCode;
	  }

	  /**
	   * commodityCodeを取得します。
	   * @return commodityCode
	   */
	  public String getCommodityCode() {
	      return commodityCode;
	  }

	  /**
	   * commodityCodeを設定します。
	   * @param commodityCode commodityCode
	   */
	  public void setCommodityCode(String commodityCode) {
	      this.commodityCode = commodityCode;
	  }

	  /**
	   * commodityNameを取得します。
	   * @return commodityName
	   */
	  public String getCommodityName() {
	      return commodityName;
	  }

	  /**
	   * commodityNameを設定します。
	   * @param commodityName commodityName
	   */
	  public void setCommodityName(String commodityName) {
	      this.commodityName = commodityName;
	  }

	  /**
	   * representSkuCodeを取得します。
	   * @return representSkuCode
	   */
	  public String getRepresentSkuCode() {
	      return representSkuCode;
	  }

	  /**
	   * representSkuCodeを設定します。
	   * @param representSkuCode representSkuCode
	   */
	  public void setRepresentSkuCode(String representSkuCode) {
	      this.representSkuCode = representSkuCode;
	  }

	  /**
	   * representSkuUnitPriceを取得します。
	   * @return representSkuUnitPrice
	   */
	  public String getRepresentSkuUnitPrice() {
	      return representSkuUnitPrice;
	  }

	  /**
	   * representSkuUnitPriceを設定します。
	   * @param representSkuUnitPrice representSkuUnitPrice
	   */
	  public void setRepresentSkuUnitPrice(String representSkuUnitPrice) {
	      this.representSkuUnitPrice = representSkuUnitPrice;
	  }

	  /**
	   * stockManagementTypeを取得します。
	   * @return stockManagementType
	   */
	  public String getStockManagementType() {
	      return stockManagementType;
	  }

	  /**
	   * stockManagementTypeを設定します。
	   * @param stockManagementType stockManagementType
	   */
	  public void setStockManagementType(String stockManagementType) {
	      this.stockManagementType = stockManagementType;
	  }

	  /**
	   * availableStockQuantityを取得します。
	   * @return availableStockQuantity
	   */
	  public boolean isAvailableStockQuantity() {
	      return availableStockQuantity;
	  }

	  /**
	   * availableStockQuantityを設定します。
	   * @param availableStockQuantity availableStockQuantity
	   */
	  public void setAvailableStockQuantity(boolean availableStockQuantity) {
	      this.availableStockQuantity = availableStockQuantity;
	  }

	  /**
	   * commodityStandard1Nameを取得します。
	   * @return commodityStandard1Name
	   */
	  public String getCommodityStandard1Name() {
	      return commodityStandard1Name;
	  }

	  /**
	   * commodityStandard1Nameを設定します。
	   * @param commodityStandard1Name commodityStandard1Name
	   */
	  public void setCommodityStandard1Name(String commodityStandard1Name) {
	      this.commodityStandard1Name = commodityStandard1Name;
	  }

	  /**
	   * commodityStandard2Nameを取得します。
	   * @return commodityStandard2Name
	   */
	  public String getCommodityStandard2Name() {
	      return commodityStandard2Name;
	  }

	  /**
	   * commodityStandard2Nameを設定します。
	   * @param commodityStandard2Name commodityStandard2Name
	   */
	  public void setCommodityStandard2Name(String commodityStandard2Name) {
	      this.commodityStandard2Name = commodityStandard2Name;
	  }

	  /**
	   * salableCompositionを取得します。
	   * @return salableComposition
	   */
	  public boolean isSalableComposition() {
	      return salableComposition;
	  }

	  /**
	   * salableCompositionを設定します。
	   * @param salableComposition salableComposition
	   */
	  public void setSalableComposition(boolean salableComposition) {
	      this.salableComposition = salableComposition;
	  }

	  /**
	   * commodityDetailListを取得します。
	   * @return commodityDetailList
	   */
	  public List<CommodityDetail> getCommodityDetailList() {
	      return commodityDetailList;
	  }

	  /**
	   * commodityDetailListを設定します。
	   * @param commodityDetailList commodityDetailList
	   */
	  public void setCommodityDetailList(List<CommodityDetail> commodityDetailList) {
	      this.commodityDetailList = commodityDetailList;
	  }

	  /**
	   * stockListを取得します。
	   * @return stockList
	   */
	  public List<Stock> getStockList() {
	      return stockList;
	  }

	  /**
	   * stockListを設定します。
	   * @param stockList stockList
	   */
	  public void setStockList(List<Stock> stockList) {
	      this.stockList = stockList;
	  }

	public String getCommodityNameJp() {
		return commodityNameJp;
	}

	public void setCommodityNameJp(String commodityNameJp) {
		this.commodityNameJp = commodityNameJp;
	}

	public String getCommodityNameEn() {
		return commodityNameEn;
	}

	public void setCommodityNameEn(String commodityNameEn) {
		this.commodityNameEn = commodityNameEn;
	}

	public String getCommodityStandard1NameJp() {
		return commodityStandard1NameJp;
	}

	public void setCommodityStandard1NameJp(String commodityStandard1NameJp) {
		this.commodityStandard1NameJp = commodityStandard1NameJp;
	}

	public String getCommodityStandard1NameEn() {
		return commodityStandard1NameEn;
	}

	public void setCommodityStandard1NameEn(String commodityStandard1NameEn) {
		this.commodityStandard1NameEn = commodityStandard1NameEn;
	}

	public String getCommodityStandard2NameJp() {
		return commodityStandard2NameJp;
	}

	public void setCommodityStandard2NameJp(String commodityStandard2NameJp) {
		this.commodityStandard2NameJp = commodityStandard2NameJp;
	}

	public String getCommodityStandard2NameEn() {
		return commodityStandard2NameEn;
	}

	public void setCommodityStandard2NameEn(String commodityStandard2NameEn) {
		this.commodityStandard2NameEn = commodityStandard2NameEn;
	}
	  

	}
