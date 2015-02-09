package jp.co.sint.webshop.web.bean.front.catalog;

import java.io.Serializable;
import java.util.List;
import jp.co.sint.webshop.web.bean.UISubBean;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * カテゴリツリーのデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class SalesTagCategoryBean extends UISubBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private List<DetailBean> SalesTagCategoryList;

  private boolean displayMode = false;

  public static class DetailBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String tagCode;

    private String categoryCode;

    private String categoryName;

    private List<PageBean> pageBeanList;

    public static class PageBean implements Serializable {

      private static final long serialVersionUID = 1L;

      private String commodityCode;

      private String commodityName;

      private String unitPrice;

      private String discountPrice;

      private String reservationPrice;

      private String commodityTaxType;

      private String campaignDiscountRate;

      private String discountRate;

      private String discountPrices;

      private boolean displayDiscountRate;

      private String priceMode;

      private Long importCommodityType;

      private Long clearCommodityType;

      private Long reserveCommodityType1;

      private Long reserveCommodityType2;

      private Long reserveCommodityType3;

      private Long newReserveCommodityType1;

      private Long newReserveCommodityType2;

      private Long newReserveCommodityType3;

      private Long newReserveCommodityType4;

      private Long newReserveCommodityType5;

      private Long innerQuantity;
      
      // 限时限量商品（秒杀商品）
      private boolean DiscountCommodityFlg;

      /**
       * @return the newReserveCommodityType1
       */
      public Long getNewReserveCommodityType1() {
        return newReserveCommodityType1;
      }

      /**
       * @param newReserveCommodityType1
       *          the newReserveCommodityType1 to set
       */
      public void setNewReserveCommodityType1(Long newReserveCommodityType1) {
        this.newReserveCommodityType1 = newReserveCommodityType1;
      }

      /**
       * @return the newReserveCommodityType2
       */
      public Long getNewReserveCommodityType2() {
        return newReserveCommodityType2;
      }

      /**
       * @param newReserveCommodityType2
       *          the newReserveCommodityType2 to set
       */
      public void setNewReserveCommodityType2(Long newReserveCommodityType2) {
        this.newReserveCommodityType2 = newReserveCommodityType2;
      }

      /**
       * @return the newReserveCommodityType3
       */
      public Long getNewReserveCommodityType3() {
        return newReserveCommodityType3;
      }

      /**
       * @param newReserveCommodityType3
       *          the newReserveCommodityType3 to set
       */
      public void setNewReserveCommodityType3(Long newReserveCommodityType3) {
        this.newReserveCommodityType3 = newReserveCommodityType3;
      }

      /**
       * @return the newReserveCommodityType4
       */
      public Long getNewReserveCommodityType4() {
        return newReserveCommodityType4;
      }

      /**
       * @param newReserveCommodityType4
       *          the newReserveCommodityType4 to set
       */
      public void setNewReserveCommodityType4(Long newReserveCommodityType4) {
        this.newReserveCommodityType4 = newReserveCommodityType4;
      }

      /**
       * @return the newReserveCommodityType5
       */
      public Long getNewReserveCommodityType5() {
        return newReserveCommodityType5;
      }

      /**
       * @param newReserveCommodityType5
       *          the newReserveCommodityType5 to set
       */
      public void setNewReserveCommodityType5(Long newReserveCommodityType5) {
        this.newReserveCommodityType5 = newReserveCommodityType5;
      }

      /**
       * @return the innerQuantity
       */
      public Long getInnerQuantity() {
        return innerQuantity;
      }

      /**
       * @param innerQuantity
       *          the innerQuantity to set
       */
      public void setInnerQuantity(Long innerQuantity) {
        this.innerQuantity = innerQuantity;
      }

      
      /**
       * @return the discountCommodityflg
       */
      public boolean isDiscountCommodityFlg() {
        return DiscountCommodityFlg;
      }

      
      /**
       * @param discountCommodityflg the discountCommodityflg to set
       */
      public void setDiscountCommodityFlg(boolean DiscountCommodityFlg) {
        this.DiscountCommodityFlg = DiscountCommodityFlg;
      }

      /**
       * @return the importCommodityType
       */
      public Long getImportCommodityType() {
        return importCommodityType;
      }

      /**
       * @param importCommodityType
       *          the importCommodityType to set
       */
      public void setImportCommodityType(Long importCommodityType) {
        this.importCommodityType = importCommodityType;
      }

      /**
       * @return the clearCommodityType
       */
      public Long getClearCommodityType() {
        return clearCommodityType;
      }

      /**
       * @param clearCommodityType
       *          the clearCommodityType to set
       */
      public void setClearCommodityType(Long clearCommodityType) {
        this.clearCommodityType = clearCommodityType;
      }

      /**
       * @return the reserveCommodityType1
       */
      public Long getReserveCommodityType1() {
        return reserveCommodityType1;
      }

      /**
       * @param reserveCommodityType1
       *          the reserveCommodityType1 to set
       */
      public void setReserveCommodityType1(Long reserveCommodityType1) {
        this.reserveCommodityType1 = reserveCommodityType1;
      }

      /**
       * @return the reserveCommodityType2
       */
      public Long getReserveCommodityType2() {
        return reserveCommodityType2;
      }

      /**
       * @param reserveCommodityType2
       *          the reserveCommodityType2 to set
       */
      public void setReserveCommodityType2(Long reserveCommodityType2) {
        this.reserveCommodityType2 = reserveCommodityType2;
      }

      /**
       * @return the reserveCommodityType3
       */
      public Long getReserveCommodityType3() {
        return reserveCommodityType3;
      }

      /**
       * @param reserveCommodityType3
       *          the reserveCommodityType3 to set
       */
      public void setReserveCommodityType3(Long reserveCommodityType3) {
        this.reserveCommodityType3 = reserveCommodityType3;
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
       * @return the campaignDiscountRate
       */
      public String getCampaignDiscountRate() {
        return campaignDiscountRate;
      }

      /**
       * @param campaignDiscountRate
       *          the campaignDiscountRate to set
       */
      public void setCampaignDiscountRate(String campaignDiscountRate) {
        this.campaignDiscountRate = campaignDiscountRate;
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
    }

    /**
     * @return the tagCode
     */
    public String getTagCode() {
      return tagCode;
    }

    /**
     * @param tagCode
     *          the tagCode to set
     */
    public void setTagCode(String tagCode) {
      this.tagCode = tagCode;
    }

    /**
     * @return the categoryCode
     */
    public String getCategoryCode() {
      return categoryCode;
    }

    /**
     * @param categoryCode
     *          the categoryCode to set
     */
    public void setCategoryCode(String categoryCode) {
      this.categoryCode = categoryCode;
    }

    /**
     * @return the pageBeanList
     */
    public List<PageBean> getPageBeanList() {
      return pageBeanList;
    }

    /**
     * @param pageBeanList
     *          the pageBeanList to set
     */
    public void setPageBeanList(List<PageBean> pageBeanList) {
      this.pageBeanList = pageBeanList;
    }

    /**
     * @return the categoryName
     */
    public String getCategoryName() {
      return categoryName;
    }

    /**
     * @param categoryName
     *          the categoryName to set
     */
    public void setCategoryName(String categoryName) {
      this.categoryName = categoryName;
    }
  }

  /**
   * リクエストパラメータから値を取得します。
   * 
   * @param reqparam
   *          リクエストパラメータ
   */
  public void createAttributes(RequestParameter reqparam) {

  }

  /**
   * @return the salesTagCategoryList
   */
  public List<DetailBean> getSalesTagCategoryList() {
    return SalesTagCategoryList;
  }

  /**
   * @param salesTagCategoryList
   *          the salesTagCategoryList to set
   */
  public void setSalesTagCategoryList(List<DetailBean> salesTagCategoryList) {
    SalesTagCategoryList = salesTagCategoryList;
  }

  /**
   * @return the displayMode
   */
  public boolean isDisplayMode() {
    return displayMode;
  }

  /**
   * @param displayMode
   *          the displayMode to set
   */
  public void setDisplayMode(boolean displayMode) {
    this.displayMode = displayMode;
  }

}
