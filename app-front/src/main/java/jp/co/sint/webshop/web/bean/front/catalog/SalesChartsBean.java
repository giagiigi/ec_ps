package jp.co.sint.webshop.web.bean.front.catalog;

import java.io.Serializable;
import java.util.List;

import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.web.bean.UISubBean;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * カテゴリツリーのデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class SalesChartsBean extends UISubBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private List<DetailBean> salesChartsList;

  public static class DetailBean implements Serializable {

    private static final long serialVersionUID = 1L;

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
      
      private String originalCommodityCode;
      
      private Long combinationAmount;

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

      
      /**
       * @return the originalCommodityCode
       */
      public String getOriginalCommodityCode() {
        return originalCommodityCode;
      }

      
      /**
       * @param originalCommodityCode the originalCommodityCode to set
       */
      public void setOriginalCommodityCode(String originalCommodityCode) {
        this.originalCommodityCode = originalCommodityCode;
      }

      
      /**
       * @return the combinationAmount
       */
      public Long getCombinationAmount() {
        return combinationAmount;
      }

      
      /**
       * @param combinationAmount the combinationAmount to set
       */
      public void setCombinationAmount(Long combinationAmount) {
        this.combinationAmount = combinationAmount;
      }
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
   * @return the salesChartsList
   */
  public List<DetailBean> getSalesChartsList() {
    return salesChartsList;
  }

  /**
   * @param salesChartsList
   *          the salesChartsList to set
   */
  public void setSalesChartsList(List<DetailBean> salesChartsList) {
    this.salesChartsList = salesChartsList;
  }

}
