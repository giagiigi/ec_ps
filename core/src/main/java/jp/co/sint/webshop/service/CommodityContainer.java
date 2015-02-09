package jp.co.sint.webshop.service;

import java.io.Serializable;

import jp.co.sint.webshop.data.dto.ArrivalGoods;
import jp.co.sint.webshop.data.dto.Brand;
import jp.co.sint.webshop.data.dto.Campaign;
import jp.co.sint.webshop.data.dto.CampaignCommodity;
import jp.co.sint.webshop.data.dto.Category;
import jp.co.sint.webshop.data.dto.CommodityDetail;
import jp.co.sint.webshop.data.dto.CommodityHeader;
import jp.co.sint.webshop.data.dto.CommodityLayout;
import jp.co.sint.webshop.data.dto.Gift;
import jp.co.sint.webshop.data.dto.GiftCommodity;
import jp.co.sint.webshop.data.dto.PopularRankingDetail;
import jp.co.sint.webshop.data.dto.RelatedCommodityA;
import jp.co.sint.webshop.data.dto.RelatedCommodityB;
import jp.co.sint.webshop.data.dto.ReviewPost;
import jp.co.sint.webshop.data.dto.ReviewSummary;
import jp.co.sint.webshop.data.dto.Shop;
import jp.co.sint.webshop.data.dto.Stock;
import jp.co.sint.webshop.data.dto.StockStatus;
import jp.co.sint.webshop.data.dto.Tag;
import jp.co.sint.webshop.data.dto.TagCommodity;
import jp.co.sint.webshop.service.catalog.ContainerAddInfo;

public class CommodityContainer implements Serializable {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private CommodityHeader commodityHeader;

  private CommodityDetail commodityDetail;

  private Stock stock;

  private Category category;

  private CampaignCommodity campaignCommodity;

  private TagCommodity tagCommodity;

  private GiftCommodity giftCommodity;

  private RelatedCommodityA relatedCommodityA;

  private RelatedCommodityB relatedCommodityB;

  private Campaign campaign;

  private Gift gift;

  private Tag tag;

  private StockStatus stockStatus;

  private ReviewPost reviewPost;

  private ReviewSummary reviewSummary;

  private PopularRankingDetail popularRankingDetail;

  private ArrivalGoods arrivalGoods;

  private Shop shop;

  private ContainerAddInfo containerAddInfo;

  private CommodityLayout commodityLayout;

  private Brand brand;
  
  private Long useFlg;

  /**
   * commodityLayoutを返します。
   * 
   * @return the commodityLayout
   */
  public CommodityLayout getCommodityLayout() {
    return commodityLayout;
  }

  /**
   * commodityLayoutを設定します。
   * 
   * @param commodityLayout
   *          設定する commodityLayout
   */
  public void setCommodityLayout(CommodityLayout commodityLayout) {
    this.commodityLayout = commodityLayout;
  }

  /**
   * @return the arrivalGoods
   */
  public ArrivalGoods getArrivalGoods() {
    return arrivalGoods;
  }

  /**
   * @param arrivalGoods
   *          the arrivalGoods to set
   */
  public void setArrivalGoods(ArrivalGoods arrivalGoods) {
    this.arrivalGoods = arrivalGoods;
  }

  /**
   * @return the campaign
   */
  public Campaign getCampaign() {
    return campaign;
  }

  /**
   * @param campaign
   *          the campaign to set
   */
  public void setCampaign(Campaign campaign) {
    this.campaign = campaign;
  }

  /**
   * @return the campaignCommodity
   */
  public CampaignCommodity getCampaignCommodity() {
    return campaignCommodity;
  }

  /**
   * @param campaignCommodity
   *          the campaignCommodity to set
   */
  public void setCampaignCommodity(CampaignCommodity campaignCommodity) {
    this.campaignCommodity = campaignCommodity;
  }

  /**
   * @return the category
   */
  public Category getCategory() {
    return category;
  }

  /**
   * @param category
   *          the category to set
   */
  public void setCategory(Category category) {
    this.category = category;
  }

  /**
   * @return the commodityDetail
   */
  public CommodityDetail getCommodityDetail() {
    return commodityDetail;
  }

  /**
   * @param commodityDetail
   *          the commodityDetail to set
   */
  public void setCommodityDetail(CommodityDetail commodityDetail) {
    this.commodityDetail = commodityDetail;
  }

  /**
   * @return the commodityHeader
   */
  public CommodityHeader getCommodityHeader() {
    return commodityHeader;
  }

  /**
   * @param commodityHeader
   *          the commodityHeader to set
   */
  public void setCommodityHeader(CommodityHeader commodityHeader) {
    this.commodityHeader = commodityHeader;
  }

  /**
   * @return the gift
   */
  public Gift getGift() {
    return gift;
  }

  /**
   * @param gift
   *          the gift to set
   */
  public void setGift(Gift gift) {
    this.gift = gift;
  }

  /**
   * @return the giftCommodity
   */
  public GiftCommodity getGiftCommodity() {
    return giftCommodity;
  }

  /**
   * @param giftCommodity
   *          the giftCommodity to set
   */
  public void setGiftCommodity(GiftCommodity giftCommodity) {
    this.giftCommodity = giftCommodity;
  }

  /**
   * @return the popularRankingDetail
   */
  public PopularRankingDetail getPopularRankingDetail() {
    return popularRankingDetail;
  }

  /**
   * @param popularRankingDetail
   *          the popularRankingDetail to set
   */
  public void setPopularRankingDetail(PopularRankingDetail popularRankingDetail) {
    this.popularRankingDetail = popularRankingDetail;
  }

  /**
   * @return the relatedCommodityA
   */
  public RelatedCommodityA getRelatedCommodityA() {
    return relatedCommodityA;
  }

  /**
   * @param relatedCommodityA
   *          the relatedCommodityA to set
   */
  public void setRelatedCommodityA(RelatedCommodityA relatedCommodityA) {
    this.relatedCommodityA = relatedCommodityA;
  }

  /**
   * @return the relatedCommodityB
   */
  public RelatedCommodityB getRelatedCommodityB() {
    return relatedCommodityB;
  }

  /**
   * @param relatedCommodityB
   *          the relatedCommodityB to set
   */
  public void setRelatedCommodityB(RelatedCommodityB relatedCommodityB) {
    this.relatedCommodityB = relatedCommodityB;
  }

  /**
   * @return the reviewPost
   */
  public ReviewPost getReviewPost() {
    return reviewPost;
  }

  /**
   * @param reviewPost
   *          the reviewPost to set
   */
  public void setReviewPost(ReviewPost reviewPost) {
    this.reviewPost = reviewPost;
  }

  /**
   * @return the reviewSummary
   */
  public ReviewSummary getReviewSummary() {
    return reviewSummary;
  }

  /**
   * @param reviewSummary
   *          the reviewSummary to set
   */
  public void setReviewSummary(ReviewSummary reviewSummary) {
    this.reviewSummary = reviewSummary;
  }

  /**
   * @return the stock
   */
  public Stock getStock() {
    return stock;
  }

  /**
   * @param stock
   *          the stock to set
   */
  public void setStock(Stock stock) {
    this.stock = stock;
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
   * @return the tag
   */
  public Tag getTag() {
    return tag;
  }

  /**
   * @param tag
   *          the tag to set
   */
  public void setTag(Tag tag) {
    this.tag = tag;
  }

  /**
   * @return the tagCommodity
   */
  public TagCommodity getTagCommodity() {
    return tagCommodity;
  }

  /**
   * @param tagCommodity
   *          the tagCommodity to set
   */
  public void setTagCommodity(TagCommodity tagCommodity) {
    this.tagCommodity = tagCommodity;
  }

  /**
   * @return the shop
   */
  public Shop getShop() {
    return shop;
  }

  /**
   * @param shop
   *          the shop to set
   */
  public void setShop(Shop shop) {
    this.shop = shop;
  }

  /**
   * @return the containerAddInfo
   */
  public ContainerAddInfo getContainerAddInfo() {
    return containerAddInfo;
  }

  /**
   * @param containerAddInfo
   *          the containerAddInfo to set
   */
  public void setContainerAddInfo(ContainerAddInfo containerAddInfo) {
    this.containerAddInfo = containerAddInfo;
  }

  
  /**
   * @return the brand
   */
  public Brand getBrand() {
    return brand;
  }

  
  /**
   * @param brand the brand to set
   */
  public void setBrand(Brand brand) {
    this.brand = brand;
  }

  
  /**
   * @return the useFlg
   */
  public Long getUseFlg() {
    return useFlg;
  }

  
  /**
   * @param useFlg the useFlg to set
   */
  public void setUseFlg(Long useFlg) {
    this.useFlg = useFlg;
  }


}
