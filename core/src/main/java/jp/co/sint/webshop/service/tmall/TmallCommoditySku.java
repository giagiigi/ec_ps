package jp.co.sint.webshop.service.tmall;

import java.io.Serializable;

public class TmallCommoditySku implements Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;

  // sku所属商品型号ID（必填）
  private String numiid;

  // sku_id-tmall（必填）
  private String skuId;

  // sku属性串（必填）
  private String properties;

  // sku库存数（必填）
  private String quantity;

  // sku价格（必填）
  private String price;

  // Sku的商家外部id（必填）
  private String outerId;

  // sku所属商品的价格。当用户新增sku，使商品价格不属于sku价格之间的时候，用于修改商品的价格，使sku能够添加成功
  private String itemPrice;

  // 库存更新方式，可选。1为全量更新，2为增量更新。如果不填，默认为全量更新
  private String updateType;

  /*
   * 对比库存专用字段
   */
  // 总库存
  private String stockTotal;

  // 官网库存
  private String stockQuantity;

  // 淘宝库存
  private String stockTmall;

  // JD库存
  private String stockJd;

  // 官网引当
  private String allocatedQuantity;

  // 淘宝引当
  private String allocatedTmall;

  // 淘宝引当
  private String allocatedJd;

  // 安全库存数
  private String stockThreshold;

  // 官网库存占有比例
  private String shareRatio;

  // 库存区分
  private String stockType;

  public String getUpdateType() {
    return updateType;
  }

  public void setUpdateType(String updateType) {
    this.updateType = updateType;
  }

  public String getSkuId() {
    return skuId;
  }

  public void setSkuId(String skuId) {
    this.skuId = skuId;
  }

  public String getNumiid() {
    return numiid;
  }

  public void setNumiid(String numiid) {
    this.numiid = numiid;
  }

  public String getProperties() {
    return properties;
  }

  public void setProperties(String properties) {
    this.properties = properties;
  }

  public String getQuantity() {
    return quantity;
  }

  public void setQuantity(String quantity) {
    this.quantity = quantity;
  }

  public String getPrice() {
    return price;
  }

  public void setPrice(String price) {
    this.price = price;
  }

  public String getOuterId() {
    return outerId;
  }

  public void setOuterId(String outerId) {
    this.outerId = outerId;
  }

  public String getItemPrice() {
    return itemPrice;
  }

  public void setItemPrice(String itemPrice) {
    this.itemPrice = itemPrice;
  }

  /**
   * @return the stockTotal
   */
  public String getStockTotal() {
    return stockTotal;
  }

  /**
   * @param stockTotal
   *          the stockTotal to set
   */
  public void setStockTotal(String stockTotal) {
    this.stockTotal = stockTotal;
  }

  /**
   * @return the stockQuantity
   */
  public String getStockQuantity() {
    return stockQuantity;
  }

  /**
   * @param stockQuantity
   *          the stockQuantity to set
   */
  public void setStockQuantity(String stockQuantity) {
    this.stockQuantity = stockQuantity;
  }

  /**
   * @return the stockTmall
   */
  public String getStockTmall() {
    return stockTmall;
  }

  /**
   * @param stockTmall
   *          the stockTmall to set
   */
  public void setStockTmall(String stockTmall) {
    this.stockTmall = stockTmall;
  }

  /**
   * @return the allocatedQuantity
   */
  public String getAllocatedQuantity() {
    return allocatedQuantity;
  }

  /**
   * @param allocatedQuantity
   *          the allocatedQuantity to set
   */
  public void setAllocatedQuantity(String allocatedQuantity) {
    this.allocatedQuantity = allocatedQuantity;
  }

  /**
   * @return the allocatedTmall
   */
  public String getAllocatedTmall() {
    return allocatedTmall;
  }

  /**
   * @param allocatedTmall
   *          the allocatedTmall to set
   */
  public void setAllocatedTmall(String allocatedTmall) {
    this.allocatedTmall = allocatedTmall;
  }

  /**
   * @return the stockThreshold
   */
  public String getStockThreshold() {
    return stockThreshold;
  }

  /**
   * @param stockThreshold
   *          the stockThreshold to set
   */
  public void setStockThreshold(String stockThreshold) {
    this.stockThreshold = stockThreshold;
  }

  /**
   * @return the shareRatio
   */
  public String getShareRatio() {
    return shareRatio;
  }

  /**
   * @param shareRatio
   *          the shareRatio to set
   */
  public void setShareRatio(String shareRatio) {
    this.shareRatio = shareRatio;
  }

  /**
   * @return the stockJd
   */
  public String getStockJd() {
    return stockJd;
  }

  /**
   * @param stockJd
   *          the stockJd to set
   */
  public void setStockJd(String stockJd) {
    this.stockJd = stockJd;
  }

  /**
   * @return the allocatedJd
   */
  public String getAllocatedJd() {
    return allocatedJd;
  }

  /**
   * @param allocatedJd
   *          the allocatedJd to set
   */
  public void setAllocatedJd(String allocatedJd) {
    this.allocatedJd = allocatedJd;
  }

  /**
   * @return the stockType
   */
  public String getStockType() {
    return stockType;
  }

  /**
   * @param stockType
   *          the stockType to set
   */
  public void setStockType(String stockType) {
    this.stockType = stockType;
  }

}
