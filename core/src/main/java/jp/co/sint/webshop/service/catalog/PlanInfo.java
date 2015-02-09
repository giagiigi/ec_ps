package jp.co.sint.webshop.service.catalog;

import java.io.Serializable;

import jp.co.sint.webshop.data.dto.CommodityDetail;
import jp.co.sint.webshop.data.dto.CommodityHeader;
import jp.co.sint.webshop.data.dto.ReviewSummary;

public class PlanInfo implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private String planCode;
  private String detailType;
  private String detailCode;
  private String detailName;
  private String detailNameEn;
  private String detailNameJp;
  private String detailUrl;
  private String detailUrlEn;
  private String detailUrlJp;
  private String showCommodityCount;
  private String commodityCode;
  private Long useFlg;
  private CommodityHeader commodityHeader;
  private CommodityDetail commodityDetail;
  private ReviewSummary reviewSummary;
  //商品所属优惠活动名称
  private String campaignName;
  /**
   * @return the planCode
   */
  public String getPlanCode() {
    return planCode;
  }

  /**
   * @param planCode
   *          the planCode to set
   */
  public void setPlanCode(String planCode) {
    this.planCode = planCode;
  }

  /**
   * @return the detailType
   */
  public String getDetailType() {
    return detailType;
  }

  /**
   * @param detailType
   *          the detailType to set
   */
  public void setDetailType(String detailType) {
    this.detailType = detailType;
  }

  /**
   * @return the detailCode
   */
  public String getDetailCode() {
    return detailCode;
  }

  /**
   * @param detailCode
   *          the detailCode to set
   */
  public void setDetailCode(String detailCode) {
    this.detailCode = detailCode;
  }

  /**
   * @return the detailName
   */
  public String getDetailName() {
    return detailName;
  }

  /**
   * @param detailName
   *          the detailName to set
   */
  public void setDetailName(String detailName) {
    this.detailName = detailName;
  }

  /**
   * @return the detailUrl
   */
  public String getDetailUrl() {
    return detailUrl;
  }

  /**
   * @param detailUrl
   *          the detailUrl to set
   */
  public void setDetailUrl(String detailUrl) {
    this.detailUrl = detailUrl;
  }

  /**
   * @return the showCommodityCount
   */
  public String getShowCommodityCount() {
    return showCommodityCount;
  }

  /**
   * @param showCommodityCount
   *          the showCommodityCount to set
   */
  public void setShowCommodityCount(String showCommodityCount) {
    this.showCommodityCount = showCommodityCount;
  }

  /**
   * @return the commodityCode
   */
  public String getCommodityCode() {
    return commodityCode;
  }

  /**
   * @param commodityCode
   *          the commodityCode to set
   */
  public void setCommodityCode(String commodityCode) {
    this.commodityCode = commodityCode;
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
   * @return the detailNameEn
   */
  public String getDetailNameEn() {
    return detailNameEn;
  }

  
  /**
   * @param detailNameEn the detailNameEn to set
   */
  public void setDetailNameEn(String detailNameEn) {
    this.detailNameEn = detailNameEn;
  }

  
  /**
   * @return the detailNameJp
   */
  public String getDetailNameJp() {
    return detailNameJp;
  }

  
  /**
   * @param detailNameJp the detailNameJp to set
   */
  public void setDetailNameJp(String detailNameJp) {
    this.detailNameJp = detailNameJp;
  }

  
  /**
   * @return the detailUrlEn
   */
  public String getDetailUrlEn() {
    return detailUrlEn;
  }

  
  /**
   * @param detailUrlEn the detailUrlEn to set
   */
  public void setDetailUrlEn(String detailUrlEn) {
    this.detailUrlEn = detailUrlEn;
  }

  
  /**
   * @return the detailUrlJp
   */
  public String getDetailUrlJp() {
    return detailUrlJp;
  }

  
  /**
   * @param detailUrlJp the detailUrlJp to set
   */
  public void setDetailUrlJp(String detailUrlJp) {
    this.detailUrlJp = detailUrlJp;
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

  
  /**
   * @return the campaignName
   */
  public String getCampaignName() {
    return campaignName;
  }

  
  /**
   * @param campaignName the campaignName to set
   */
  public void setCampaignName(String campaignName) {
    this.campaignName = campaignName;
  }

}
