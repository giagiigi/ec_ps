package jp.co.sint.webshop.service.catalog;

import jp.co.sint.webshop.data.dto.Stock;

public class StockListSearchInfo extends Stock {

	/** Serial Version UID */
	private static final long serialVersionUID = 1L;

	/** 商品名 */
	private String commodityName;

	/** SKU名称 */
	private String skuName;

	/** 規格1値の文字列 */
	private String standard1Name;

	/** 規格2値の文字列 */
	private String standard2Name;

	 //在库品区分
  private String onStockFlag;
  
  // 京东在库分配比例
  private Long shareRatioJd;
  // 京东在庫数
  private Long stockJd;
  // 京东引当数
  private Long allocatedJd;
  // 天猫在库分配比例
  private Long shareRatioTmall;
  
  public String getOnStockFlag() {
    return onStockFlag;
  }
  
  /**
   * @return the shareRatioTmall
   */
  public Long getShareRatioTmall() {
    return shareRatioTmall;
  }

  /**
   * @param shareRatioTmall the shareRatioTmall to set
   */
  public void setShareRatioTmall(Long shareRatioTmall) {
    this.shareRatioTmall = shareRatioTmall;
  }

  /**
   * @return the shareRatioJd
   */
  public Long getShareRatioJd() {
    return shareRatioJd;
  }

  /**
   * @param shareRatioJd the shareRatioJd to set
   */
  public void setShareRatioJd(Long shareRatioJd) {
    this.shareRatioJd = shareRatioJd;
  }

  /**
   * @return the stockJd
   */
  public Long getStockJd() {
    return stockJd;
  }

  /**
   * @param stockJd the stockJd to set
   */
  public void setStockJd(Long stockJd) {
    this.stockJd = stockJd;
  }

  /**
   * @return the allocatedJd
   */
  public Long getAllocatedJd() {
    return allocatedJd;
  }

  /**
   * @param allocatedJd the allocatedJd to set
   */
  public void setAllocatedJd(Long allocatedJd) {
    this.allocatedJd = allocatedJd;
  }

  public void setOnStockFlag(String onStockFlag) {
    this.onStockFlag = onStockFlag;
  }
	
	
	public String getCommodityName() {
		return commodityName;
	}

	public void setCommodityName(String commodityName) {
		this.commodityName = commodityName;
	}

	public String getSkuName() {
		return skuName;
	}

	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}

	public String getStandard1Name() {
		return standard1Name;
	}

	public void setStandard1Name(String standard1Name) {
		this.standard1Name = standard1Name;
	}

	public String getStandard2Name() {
		return standard2Name;
	}

	public void setStandard2Name(String standard2Name) {
		this.standard2Name = standard2Name;
	}

}
