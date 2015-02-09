package jp.co.sint.webshop.service.catalog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.sint.webshop.utility.DateUtil;

public class RelatedBaseEvent implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /** ショップコード */
  private String shopCode;

  /** 商品コード */
  private String commodityCode;

  /** 商品名 */
  private String commodityName;

  /** 適用キャンペーン */
  private String appliedCampaign;

  /** 表示順 */
  private String displayOrder;

  /** ランキングスコア */
  private String rankingScore;

  /** 相互関連 */
  private boolean interaction;

  /** 更新日時 */
  private Date updatedDatetime;

  // 20130805 txw add start
  private Long sortNum;
  
  private Long sortNumJp;
  
  private Long sortNumEn;

  private String lang;

  // 20130805 txw add end

  /**
   * @return appliedCampaign
   */
  public String getAppliedCampaign() {
    return appliedCampaign;
  }

  /**
   * @param appliedCampaign
   *          設定する appliedCampaign
   */
  public void setAppliedCampaign(String appliedCampaign) {
    this.appliedCampaign = appliedCampaign;
  }

  /**
   * @return commodityCode
   */
  public String getCommodityCode() {
    return commodityCode;
  }

  /**
   * @param commodityCode
   *          設定する commodityCode
   */
  public void setCommodityCode(String commodityCode) {
    this.commodityCode = commodityCode;
  }

  /**
   * @return commodityName
   */
  public String getCommodityName() {
    return commodityName;
  }

  /**
   * @param commodityName
   *          設定する commodityName
   */
  public void setCommodityName(String commodityName) {
    this.commodityName = commodityName;
  }

  /**
   * @return shopCode
   */
  public String getShopCode() {
    return shopCode;
  }

  /**
   * @param shopCode
   *          設定する shopCode
   */
  public void setShopCode(String shopCode) {
    this.shopCode = shopCode;
  }

  /**
   * @param displayOrder
   *          設定する displayOrder
   */
  public void setDisplayOrder(String displayOrder) {
    this.displayOrder = displayOrder;
  }

  /**
   * @return displayOrder
   */
  public String getDisplayOrder() {
    return displayOrder;
  }

  /**
   * @param interaction
   *          設定する interaction
   */
  public void setInteraction(boolean interaction) {
    this.interaction = interaction;
  }

  /**
   * @return interaction
   */
  public boolean isInteraction() {
    return interaction;
  }

  /**
   * @param updatedDatetime
   *          設定する updatedDatetime
   */
  public void setUpdatedDatetime(Date updatedDatetime) {
    this.updatedDatetime = DateUtil.immutableCopy(updatedDatetime);
  }

  /**
   * @return updatedDatetime
   */
  public Date getUpdatedDatetime() {
    return DateUtil.immutableCopy(updatedDatetime);
  }

  /**
   * @return the rankingScore
   */
  public String getRankingScore() {
    return rankingScore;
  }

  /**
   * @param rankingScore
   *          the rankingScore to set
   */
  public void setRankingScore(String rankingScore) {
    this.rankingScore = rankingScore;
  }

  /**
   * @return the sortNum
   */
  public Long getSortNum() {
    return sortNum;
  }

  /**
   * @return the lang
   */
  public String getLang() {
    return lang;
  }

  /**
   * @param sortNum
   *          the sortNum to set
   */
  public void setSortNum(Long sortNum) {
    this.sortNum = sortNum;
  }

  /**
   * @param lang
   *          the lang to set
   */
  public void setLang(String lang) {
    this.lang = lang;
  }

  
  /**
   * @return the sortNumJp
   */
  public Long getSortNumJp() {
    return sortNumJp;
  }

  
  /**
   * @param sortNumJp the sortNumJp to set
   */
  public void setSortNumJp(Long sortNumJp) {
    this.sortNumJp = sortNumJp;
  }

  
  /**
   * @return the sortNumEn
   */
  public Long getSortNumEn() {
    return sortNumEn;
  }

  
  /**
   * @param sortNumEn the sortNumEn to set
   */
  public void setSortNumEn(Long sortNumEn) {
    this.sortNumEn = sortNumEn;
  }

}
