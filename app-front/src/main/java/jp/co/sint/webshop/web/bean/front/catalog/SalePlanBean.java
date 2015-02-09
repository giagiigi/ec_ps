package jp.co.sint.webshop.web.bean.front.catalog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.data.domain.ReviewScore;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.bean.front.UIFrontBean;
import jp.co.sint.webshop.web.text.front.Messages;
import jp.co.sint.webshop.web.webutility.ClientType;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * 销售企划
 * 
 * @author kousen
 */
public class SalePlanBean extends UIFrontBean {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private String planCode;

  private String planName;

  private String planDescription;

  private Date planStartDatetime;

  private Date planEndDatetime;

  private String saleFlag;

  private List<PlanDetailBean> planDetailList = new ArrayList<PlanDetailBean>();

  private boolean isIpad = false;

  public static class PlanDetailBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String planCode;

    private String detailType;

    private String detailCode;

    private String detailName;

    private String detailUrl;

    private String showCommodityCount;

    private List<PlanCommodityBean> planCommodityList = new ArrayList<PlanCommodityBean>();

    /**
     * @return the planCommodityList
     */
    public List<PlanCommodityBean> getPlanCommodityList() {
      return planCommodityList;
    }

    /**
     * @param planCommodityList
     *          the planCommodityList to set
     */
    public void setPlanCommodityList(List<PlanCommodityBean> planCommodityList) {
      this.planCommodityList = planCommodityList;
    }

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

  }

  public static class PlanCommodityBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String shopCode;

    private String commodityCode;

    private String commodityName;

    private String unitPrice;

    private String discountPrice;

    private String reservationPrice;

    private String commodityTaxType;

    private String discountRate;

    private String discountPrices;

    private boolean displayDiscountRate;

    private String priceMode;

    private String reviewScore;

    private String reviewCount;

    private String commodityDescriptionShort;

    // 20130414 add by yyq start
    private long importCommodityType;

    private long clearCommodityType;

    private long reserveCommodityType1;

    private long reserveCommodityType2;

    private long reserveCommodityType3;

    private long newReserveCommodityType1;

    private long newReserveCommodityType2;

    private long newReserveCommodityType3;

    private long newReserveCommodityType4;

    private long newReserveCommodityType5;

    private long innerQuantity;
    
    private long useFlg;
    // 20130414 add by yyq end
    /**
     * @return the importCommodityType
     */
    public long getImportCommodityType() {
      return importCommodityType;
    }

    /**
     * @param importCommodityType
     *          the importCommodityType to set
     */
    public void setImportCommodityType(long importCommodityType) {
      this.importCommodityType = importCommodityType;
    }

    /**
     * @return the clearCommodityType
     */
    public long getClearCommodityType() {
      return clearCommodityType;
    }

    /**
     * @param clearCommodityType
     *          the clearCommodityType to set
     */
    public void setClearCommodityType(long clearCommodityType) {
      this.clearCommodityType = clearCommodityType;
    }

    /**
     * @return the reserveCommodityType1
     */
    public long getReserveCommodityType1() {
      return reserveCommodityType1;
    }

    /**
     * @param reserveCommodityType1
     *          the reserveCommodityType1 to set
     */
    public void setReserveCommodityType1(long reserveCommodityType1) {
      this.reserveCommodityType1 = reserveCommodityType1;
    }

    /**
     * @return the reserveCommodityType2
     */
    public long getReserveCommodityType2() {
      return reserveCommodityType2;
    }

    /**
     * @param reserveCommodityType2
     *          the reserveCommodityType2 to set
     */
    public void setReserveCommodityType2(long reserveCommodityType2) {
      this.reserveCommodityType2 = reserveCommodityType2;
    }

    /**
     * @return the reserveCommodityType3
     */
    public long getReserveCommodityType3() {
      return reserveCommodityType3;
    }

    /**
     * @param reserveCommodityType3
     *          the reserveCommodityType3 to set
     */
    public void setReserveCommodityType3(long reserveCommodityType3) {
      this.reserveCommodityType3 = reserveCommodityType3;
    }

    /**
     * @return the newReserveCommodityType1
     */
    public long getNewReserveCommodityType1() {
      return newReserveCommodityType1;
    }

    /**
     * @param newReserveCommodityType1
     *          the newReserveCommodityType1 to set
     */
    public void setNewReserveCommodityType1(long newReserveCommodityType1) {
      this.newReserveCommodityType1 = newReserveCommodityType1;
    }

    /**
     * @return the newReserveCommodityType2
     */
    public long getNewReserveCommodityType2() {
      return newReserveCommodityType2;
    }

    /**
     * @param newReserveCommodityType2
     *          the newReserveCommodityType2 to set
     */
    public void setNewReserveCommodityType2(long newReserveCommodityType2) {
      this.newReserveCommodityType2 = newReserveCommodityType2;
    }

    /**
     * @return the newReserveCommodityType3
     */
    public long getNewReserveCommodityType3() {
      return newReserveCommodityType3;
    }

    /**
     * @param newReserveCommodityType3
     *          the newReserveCommodityType3 to set
     */
    public void setNewReserveCommodityType3(long newReserveCommodityType3) {
      this.newReserveCommodityType3 = newReserveCommodityType3;
    }

    /**
     * @return the newReserveCommodityType4
     */
    public long getNewReserveCommodityType4() {
      return newReserveCommodityType4;
    }

    /**
     * @param newReserveCommodityType4
     *          the newReserveCommodityType4 to set
     */
    public void setNewReserveCommodityType4(long newReserveCommodityType4) {
      this.newReserveCommodityType4 = newReserveCommodityType4;
    }

    /**
     * @return the newReserveCommodityType5
     */
    public long getNewReserveCommodityType5() {
      return newReserveCommodityType5;
    }

    /**
     * @param newReserveCommodityType5
     *          the newReserveCommodityType5 to set
     */
    public void setNewReserveCommodityType5(long newReserveCommodityType5) {
      this.newReserveCommodityType5 = newReserveCommodityType5;
    }

    /**
     * @return the innerQuantity
     */
    public long getInnerQuantity() {
      return innerQuantity;
    }

    /**
     * @param innerQuantity
     *          the innerQuantity to set
     */
    public void setInnerQuantity(long innerQuantity) {
      this.innerQuantity = innerQuantity;
    }

    public Long getRoundedReviewScore() {
      Long score = NumUtil.toLong(getReviewScore(), 0L);

      if (score <= 20) {
        return ReviewScore.ONE_STAR.longValue();
      } else if (score <= 40) {
        return ReviewScore.TWO_STARS.longValue();
      } else if (score <= 60) {
        return ReviewScore.THREE_STARS.longValue();
      } else if (score <= 80) {
        return ReviewScore.FOUR_STARS.longValue();
      } else {
        return ReviewScore.FIVE_STARS.longValue();
      }
    }

    /**
     * @return the commodityTaxType
     */
    public String getCommodityTaxType() {
      return commodityTaxType;
    }

    /**
     * @param commodityTaxType
     *          the commodityTaxType to set
     */
    public void setCommodityTaxType(String commodityTaxType) {
      this.commodityTaxType = commodityTaxType;
    }

    /**
     * @return the discountRate
     */
    public String getDiscountRate() {
      return discountRate;
    }

    /**
     * @param discountRate
     *          the discountRate to set
     */
    public void setDiscountRate(String discountRate) {
      this.discountRate = discountRate;
    }

    /**
     * @return the discountPrices
     */
    public String getDiscountPrices() {
      return discountPrices;
    }

    /**
     * @param discountPrices
     *          the discountPrices to set
     */
    public void setDiscountPrices(String discountPrices) {
      this.discountPrices = discountPrices;
    }

    /**
     * @return the displayDiscountRate
     */
    public boolean isDisplayDiscountRate() {
      return displayDiscountRate;
    }

    /**
     * @param displayDiscountRate
     *          the displayDiscountRate to set
     */
    public void setDisplayDiscountRate(boolean displayDiscountRate) {
      this.displayDiscountRate = displayDiscountRate;
    }

    /**
     * @return the shopCode
     */
    public String getShopCode() {
      return shopCode;
    }

    /**
     * @param shopCode
     *          the shopCode to set
     */
    public void setShopCode(String shopCode) {
      this.shopCode = shopCode;
    }

    /**
     * @return the commodityName
     */
    public String getCommodityName() {
      return commodityName;
    }

    /**
     * @param commodityName
     *          the commodityName to set
     */
    public void setCommodityName(String commodityName) {
      this.commodityName = commodityName;
    }

    /**
     * @return the unitPrice
     */
    public String getUnitPrice() {
      return unitPrice;
    }

    /**
     * @param unitPrice
     *          the unitPrice to set
     */
    public void setUnitPrice(String unitPrice) {
      this.unitPrice = unitPrice;
    }

    /**
     * @return the discountPrice
     */
    public String getDiscountPrice() {
      return discountPrice;
    }

    /**
     * @param discountPrice
     *          the discountPrice to set
     */
    public void setDiscountPrice(String discountPrice) {
      this.discountPrice = discountPrice;
    }

    /**
     * @return the reservationPrice
     */
    public String getReservationPrice() {
      return reservationPrice;
    }

    /**
     * @param reservationPrice
     *          the reservationPrice to set
     */
    public void setReservationPrice(String reservationPrice) {
      this.reservationPrice = reservationPrice;
    }

    /**
     * @return the priceMode
     */
    public String getPriceMode() {
      return priceMode;
    }

    /**
     * @param priceMode
     *          the priceMode to set
     */
    public void setPriceMode(String priceMode) {
      this.priceMode = priceMode;
    }

    /**
     * @return the reviewScore
     */
    public String getReviewScore() {
      return reviewScore;
    }

    /**
     * @param reviewScore
     *          the reviewScore to set
     */
    public void setReviewScore(String reviewScore) {
      this.reviewScore = reviewScore;
    }

    /**
     * @return the reviewCount
     */
    public String getReviewCount() {
      return reviewCount;
    }

    /**
     * @param reviewCount
     *          the reviewCount to set
     */
    public void setReviewCount(String reviewCount) {
      this.reviewCount = reviewCount;
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
     * @return the commodityDescriptionShort
     */
    public String getCommodityDescriptionShort() {
      return commodityDescriptionShort;
    }

    /**
     * @param commodityDescriptionShort
     *          the commodityDescriptionShort to set
     */
    public void setCommodityDescriptionShort(String commodityDescriptionShort) {
      this.commodityDescriptionShort = commodityDescriptionShort;
    }

    
    /**
     * @return the useFlg
     */
    public long getUseFlg() {
      return useFlg;
    }

    
    /**
     * @param useFlg the useFlg to set
     */
    public void setUseFlg(long useFlg) {
      this.useFlg = useFlg;
    }

  }

  /**
   * @return the planDetailList
   */
  public List<PlanDetailBean> getPlanDetailList() {
    return planDetailList;
  }

  /**
   * @param planDetailList
   *          the planDetailList to set
   */
  public void setPlanDetailList(List<PlanDetailBean> planDetailList) {
    this.planDetailList = planDetailList;
  }

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
   * @return the planName
   */
  public String getPlanName() {
    return planName;
  }

  /**
   * @param planName
   *          the planName to set
   */
  public void setPlanName(String planName) {
    this.planName = planName;
  }

  /**
   * @return the planDescription
   */
  public String getPlanDescription() {
    return planDescription;
  }

  /**
   * @param planDescription
   *          the planDescription to set
   */
  public void setPlanDescription(String planDescription) {
    this.planDescription = planDescription;
  }

  /**
   * @return the planStartDatetime
   */
  public Date getPlanStartDatetime() {
    return planStartDatetime;
  }

  /**
   * @param planStartDatetime
   *          the planStartDatetime to set
   */
  public void setPlanStartDatetime(Date planStartDatetime) {
    this.planStartDatetime = planStartDatetime;
  }

  /**
   * @return the planEndDatetime
   */
  public Date getPlanEndDatetime() {
    return planEndDatetime;
  }

  /**
   * @param planEndDatetime
   *          the planEndDatetime to set
   */
  public void setPlanEndDatetime(Date planEndDatetime) {
    this.planEndDatetime = planEndDatetime;
  }

  @Override
  public void createAttributes(RequestParameter reqparam) {
  }

  @Override
  public void setSubJspId() {
    addSubJspId("/common/header");
  }

  @Override
  public String getModuleId() {
    return "U2050100";
  }

  @Override
  public String getModuleName() {
    return Messages.getString("web.bean.front.catalog.SaleDetailBean.0");
  }

  /**
   * pageTopicPathを取得します。
   * 
   * @return pageTopicPath
   */
  public List<CodeAttribute> getPageTopicPath() {
    List<CodeAttribute> topicPath = new ArrayList<CodeAttribute>();
    topicPath.add(new NameValue(this.planName, ""));
    return topicPath;
  }

  /**
   * @return the saleFlag
   */
  public String getSaleFlag() {
    return saleFlag;
  }

  /**
   * @param saleFlag
   *          the saleFlag to set
   */
  public void setSaleFlag(String saleFlag) {
    this.saleFlag = saleFlag;
  }

  /**
   * @return the isIpad
   */
  public boolean isIpad() {
    if (!StringUtil.isNullOrEmpty(getClient()) && !getClient().equals(ClientType.OTHER)) {
      ClientType clientType = getClient();
      if (ClientType.IPAD.equals(clientType)) {
        isIpad = true;
      }
    }
    return isIpad;
  }

  /**
   * @param isIpad
   *          the isIpad to set
   */
  public void setIpad(boolean isIpad) {
    this.isIpad = isIpad;
  }

}
