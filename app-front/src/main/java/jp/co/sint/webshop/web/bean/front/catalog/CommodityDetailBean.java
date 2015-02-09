package jp.co.sint.webshop.web.bean.front.catalog;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.data.attribute.Email;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Quantity;
import jp.co.sint.webshop.data.dto.RelatedBrand;
import jp.co.sint.webshop.data.dto.RelatedSiblingCategory;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.bean.front.UIFrontBean;
import jp.co.sint.webshop.web.text.front.Messages;
import jp.co.sint.webshop.web.webutility.PagerValue;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U2040510:商品詳細のデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class CommodityDetailBean extends UIFrontBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private String shopCode;

  private String skuCode;

  private String commodityCode;

  private String innerQuantity;

  private String priceMode;

  private String discountPrice;

  private String unitPrice;

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

  private String saleFlag;

  private boolean saleFlagFlag = false;

  @Quantity
  private String quantity;

  private String giftCode;

  private String categoryCode;

  private boolean picFlg1 = false;

  private boolean picFlg2 = false;

  private boolean picFlg3 = false;

  private boolean picFlg4 = false;

  private boolean picFlg5 = false;

  private boolean picFlgMid = false;

  private boolean picFlgMid1 = false;

  private boolean picFlgMid2 = false;

  private boolean picFlgMid3 = false;

  private boolean picFlgMid4 = false;

  @Length(256)
  @Email
  @Metadata(name = "メールアドレス", order = 3)
  private String email;

  private String metaTag;

  private List<String> partsList = new ArrayList<String>();

  private PagerValue pagerValue;

  private String detailMessage;

  private String previewDigest;

  private boolean preview = false;

  private List<String> campaignTip = new ArrayList<String>();
  
  private List<RelatedBrandBean> brands = new ArrayList<RelatedBrandBean>();
  
  private List<RelatedSiblingCategoryBean> categorys = new ArrayList<RelatedSiblingCategoryBean>();

  /**
   * isPreviewを取得します。
   * 
   * @return isPreview
   */
  public boolean isPreview() {
    return preview;
  }

  /**
   * isPreviewを設定します。
   * 
   * @param preview
   *          isPreview
   */
  public void setPreview(boolean preview) {
    this.preview = preview;
  }

  /**
   * previewDigestを取得します。
   * 
   * @return previewDigest
   */
  public String getPreviewDigest() {
    return previewDigest;
  }

  /**
   * previewDigestを設定します。
   * 
   * @param previewDigest
   *          previewDigest
   */
  public void setPreviewDigest(String previewDigest) {
    this.previewDigest = previewDigest;
  }

  /**
   * サブJSPを設定します。
   */
  @Override
  public void setSubJspId() {
    addSubJspId("/common/header");
    addSubJspId("/catalog/" + DisplayPartsCode.CART.getName());
    // addSubJspId("/catalog/" + DisplayPartsCode.RECOMMEND_A.getName());
    addSubJspId("/catalog/" + DisplayPartsCode.RECOMMEND_C.getName());
    addSubJspId("/catalog/" + DisplayPartsCode.RECOMMEND_B.getName());
    addSubJspId("/catalog/" + DisplayPartsCode.REVIEWS.getName());
    // addSubJspId("/catalog/category_tree");
    addSubJspId("/catalog/topic_path");
    // addSubJspId("/catalog/campaign_info");
    addSubJspId("/catalog/sales_recommend");
    addSubJspId("/catalog/sales_star");
  }

  /**
   * リクエストパラメータから値を取得します。
   * 
   * @param reqparam
   *          リクエストパラメータ
   */
  public void createAttributes(RequestParameter reqparam) {
    shopCode = reqparam.get("shopCode");
    skuCode = reqparam.get("selectSkuCode");
    commodityCode = reqparam.get("commodityCode");
    quantity = reqparam.get("quantity");
    giftCode = reqparam.get("giftCode");
    categoryCode = reqparam.get("searchCategoryCode");
    previewDigest = reqparam.get("previewDigest");
  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U2040510";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.front.catalog.CommodityDetailBean.0");
  }

  /**
   * pagerValueを取得します。
   * 
   * @return pagerValue
   */
  public PagerValue getPagerValue() {
    return pagerValue;
  }

  /**
   * pagerValueを設定します。
   * 
   * @param pagerValue
   *          pagerValue
   */
  public void setPagerValue(PagerValue pagerValue) {
    this.pagerValue = pagerValue;
  }

  /**
   * quantityを取得します。
   * 
   * @return quantity
   */
  public String getQuantity() {
    return quantity;
  }

  /**
   * quantityを設定します。
   * 
   * @param quantity
   *          quantity
   */
  public void setQuantity(String quantity) {
    this.quantity = quantity;
  }

  /**
   * shopCodeを取得します。
   * 
   * @return shopCode
   */
  public String getShopCode() {
    return shopCode;
  }

  /**
   * shopCodeを設定します。
   * 
   * @param shopCode
   *          shopCode
   */
  public void setShopCode(String shopCode) {
    this.shopCode = shopCode;
  }

  /**
   * skuCodeを取得します。
   * 
   * @return skuCode
   */
  public String getSkuCode() {
    return skuCode;
  }

  /**
   * skuCodeを設定します。
   * 
   * @param skuCode
   *          skuCode
   */
  public void setSkuCode(String skuCode) {
    this.skuCode = skuCode;
  }

  /**
   * commodityCodeを取得します。
   * 
   * @return commodityCode
   */
  public String getCommodityCode() {
    return commodityCode;
  }

  /**
   * commodityCodeを設定します。
   * 
   * @param commodityCode
   *          commodityCode
   */
  public void setCommodityCode(String commodityCode) {
    this.commodityCode = commodityCode;
  }

  /**
   * giftCodeを取得します。
   * 
   * @return giftCode
   */
  public String getGiftCode() {
    return giftCode;
  }

  /**
   * giftCodeを設定します。
   * 
   * @param giftCode
   *          giftCode
   */
  public void setGiftCode(String giftCode) {
    this.giftCode = giftCode;
  }

  /**
   * categoryCodeを取得します。
   * 
   * @return categoryCode
   */
  public String getCategoryCode() {
    return categoryCode;
  }

  /**
   * categoryCodeを設定します。
   * 
   * @param categoryCode
   *          categoryCode
   */
  public void setCategoryCode(String categoryCode) {
    this.categoryCode = categoryCode;
  }

  /**
   * partsListを取得します。
   * 
   * @return partsList
   */
  public List<String> getPartsList() {
    return partsList;
  }

  /**
   * partsListを設定します。
   * 
   * @param partsList
   *          partsList
   */
  public void setPartsList(List<String> partsList) {
    this.partsList = partsList;
  }

  /**
   * emailを取得します。
   * 
   * @return email
   */
  public String getEmail() {
    return email;
  }

  /**
   * emailを設定します。
   * 
   * @param email
   *          email
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * metaTagを取得します。
   * 
   * @return metaTag
   */
  public String getMetaTag() {
    return metaTag;
  }

  /**
   * metaTagを設定します。
   * 
   * @param metaTag
   *          metaTag
   */
  public void setMetaTag(String metaTag) {
    this.metaTag = metaTag;
  }

  /**
   * detailMessageを取得します。
   * 
   * @return detailMessage
   */
  public String getDetailMessage() {
    return detailMessage;
  }

  /**
   * detailMessageを設定します。
   * 
   * @param detailMessage
   *          detailMessage
   */
  public void setDetailMessage(String detailMessage) {
    this.detailMessage = detailMessage;
  }

  /**
   * 「パーツコード」のコード定義を表す列挙クラスです。
   * 
   * @author System Integrator Corp.
   */
  public static enum DisplayPartsCode implements CodeAttribute {

    /** 「メイン」を表す値です。 */
    CART("detail_cart", "00"),

    /** 「手動おすすめ」を表す値です。 */
    RECOMMEND_A("detail_recommend_a", "01"),

    // add by wjw 20120103 end
    /** 「同一品牌商品表示」を表す値です。 */
    RECOMMEND_C("detail_recommend_c", "04"),
    // add by wjw 20120103 end

    /** 「自動おすすめ」を表す値です。 */
    RECOMMEND_B("detail_recommend_b", "02"),

    /** 「レビュー」を表す値です。 */
    REVIEWS("detail_review", "03");

    private String name;

    private String value;

    private DisplayPartsCode(String name, String value) {
      this.name = name;
      this.value = value;
    }

    /**
     * コード名称を返します。
     * 
     * @return コード名称
     */
    public String getName() {
      return name;
    }

    /**
     * コード値を返します。
     * 
     * @return コード値
     */
    public String getValue() {
      return this.value;
    }

    /**
     * Long型のコード値を返します。
     * 
     * @return コード値
     */
    public Long longValue() {
      return Long.valueOf(this.value);
    }

    /**
     * 指定されたコード名を持つパーツコードを返します。
     * 
     * @param name
     *          コード名
     * @return パーツコード
     */
    public static DisplayPartsCode fromName(String name) {
      for (DisplayPartsCode p : DisplayPartsCode.values()) {
        if (p.getName().equals(name)) {
          return p;
        }
      }
      return null;
    }

    /**
     * 指定されたコード値を持つパーツコードを返します。
     * 
     * @param value
     *          コード値
     * @return パーツコード
     */
    public static DisplayPartsCode fromValue(String value) {
      for (DisplayPartsCode p : DisplayPartsCode.values()) {
        if (p.getValue().equals(value)) {
          return p;
        }
      }
      return null;
    }

    /**
     * 指定されたコード値を持つパーツコードを返します。
     * 
     * @param value
     *          コード値
     * @return パーツコード
     */
    public static DisplayPartsCode fromValue(Long value) {
      return fromValue(Long.toString(value));
    }

    /**
     * 指定されたコード値が有効かどうかを返します。
     * 
     * @param value
     *          コード値
     * @return コード値が有効であればtrue
     */
    public static boolean isValid(String value) {
      if (StringUtil.hasValue(value)) {
        for (DisplayPartsCode p : DisplayPartsCode.values()) {
          if (p.getValue().equals(value)) {
            return true;
          }
        }
      }
      return false;
    }

    /**
     * 指定されたコード値が有効かどうかを返します。
     * 
     * @param value
     *          コード値
     * @return コード値が有効であればtrue
     */
    public static boolean isValid(Long value) {
      return isValid(Long.toString(value));
    }

    /**
     * パーツIDを取得します。
     * 
     * @param partsCode
     *          パーツコード
     * @return パーツID
     */
    public static String getPartsId(String partsCode) {
      return fromValue(partsCode).getName();
    }
  }

  /**
   * @return the campaignTip
   */
  public List<String> getCampaignTip() {
    return campaignTip;
  }

  /**
   * @param campaignTip
   *          the campaignTip to set
   */
  public void setCampaignTip(List<String> campaignTip) {
    this.campaignTip = campaignTip;
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
   * @return the saleFlagFlag
   */
  public boolean isSaleFlagFlag() {
    return saleFlagFlag;
  }

  /**
   * @param saleFlagFlag
   *          the saleFlagFlag to set
   */
  public void setSaleFlagFlag(boolean saleFlagFlag) {
    this.saleFlagFlag = saleFlagFlag;
  }

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
  public String getInnerQuantity() {
    return innerQuantity;
  }

  /**
   * @param innerQuantity
   *          the innerQuantity to set
   */
  public void setInnerQuantity(String innerQuantity) {
    this.innerQuantity = innerQuantity;
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
   * @return the picFlg1
   */
  public boolean isPicFlg1() {
    return picFlg1;
  }

  /**
   * @param picFlg1
   *          the picFlg1 to set
   */
  public void setPicFlg1(boolean picFlg1) {
    this.picFlg1 = picFlg1;
  }

  /**
   * @return the picFlg2
   */
  public boolean isPicFlg2() {
    return picFlg2;
  }

  /**
   * @param picFlg2
   *          the picFlg2 to set
   */
  public void setPicFlg2(boolean picFlg2) {
    this.picFlg2 = picFlg2;
  }

  /**
   * @return the picFlg3
   */
  public boolean isPicFlg3() {
    return picFlg3;
  }

  /**
   * @param picFlg3
   *          the picFlg3 to set
   */
  public void setPicFlg3(boolean picFlg3) {
    this.picFlg3 = picFlg3;
  }

  /**
   * @return the picFlg4
   */
  public boolean isPicFlg4() {
    return picFlg4;
  }

  /**
   * @param picFlg4
   *          the picFlg4 to set
   */
  public void setPicFlg4(boolean picFlg4) {
    this.picFlg4 = picFlg4;
  }

  /**
   * @return the picFlg5
   */
  public boolean isPicFlg5() {
    return picFlg5;
  }

  /**
   * @param picFlg5
   *          the picFlg5 to set
   */
  public void setPicFlg5(boolean picFlg5) {
    this.picFlg5 = picFlg5;
  }

  /**
   * @return the picFlgMid
   */
  public boolean isPicFlgMid() {
    return picFlgMid;
  }

  /**
   * @param picFlgMid
   *          the picFlgMid to set
   */
  public void setPicFlgMid(boolean picFlgMid) {
    this.picFlgMid = picFlgMid;
  }

  /**
   * @return the picFlgMid1
   */
  public boolean isPicFlgMid1() {
    return picFlgMid1;
  }

  /**
   * @param picFlgMid1
   *          the picFlgMid1 to set
   */
  public void setPicFlgMid1(boolean picFlgMid1) {
    this.picFlgMid1 = picFlgMid1;
  }

  /**
   * @return the picFlgMid2
   */
  public boolean isPicFlgMid2() {
    return picFlgMid2;
  }

  /**
   * @param picFlgMid2
   *          the picFlgMid2 to set
   */
  public void setPicFlgMid2(boolean picFlgMid2) {
    this.picFlgMid2 = picFlgMid2;
  }

  /**
   * @return the picFlgMid3
   */
  public boolean isPicFlgMid3() {
    return picFlgMid3;
  }

  /**
   * @param picFlgMid3
   *          the picFlgMid3 to set
   */
  public void setPicFlgMid3(boolean picFlgMid3) {
    this.picFlgMid3 = picFlgMid3;
  }

  /**
   * @return the picFlgMid4
   */
  public boolean isPicFlgMid4() {
    return picFlgMid4;
  }

  /**
   * @param picFlgMid4
   *          the picFlgMid4 to set
   */
  public void setPicFlgMid4(boolean picFlgMid4) {
    this.picFlgMid4 = picFlgMid4;
  }

  

  
  public static class RelatedBrandBean {

    private String commodityCode;

    private String brandCode;

    private String brandName;

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
     * @return the brandCode
     */
    public String getBrandCode() {
      return brandCode;
    }

    /**
     * @param brandCode
     *          the brandCode to set
     */
    public void setBrandCode(String brandCode) {
      this.brandCode = brandCode;
    }

    /**
     * @return the brandName
     */
    public String getBrandName() {
      return brandName;
    }

    /**
     * @param brandName
     *          the brandName to set
     */
    public void setBrandName(String brandName) {
      this.brandName = brandName;
    }

  }
  
  public static class RelatedSiblingCategoryBean {

    private String commodityCode;

    private String categoryCode;

    private String categoryName;

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
   * @return the brands
   */
  public List<RelatedBrandBean> getBrands() {
    return brands;
  }

  
  /**
   * @param brands the brands to set
   */
  public void setBrands(List<RelatedBrandBean> brands) {
    this.brands = brands;
  }

  
  /**
   * @return the categorys
   */
  public List<RelatedSiblingCategoryBean> getCategorys() {
    return categorys;
  }

  
  /**
   * @param categorys the categorys to set
   */
  public void setCategorys(List<RelatedSiblingCategoryBean> categorys) {
    this.categorys = categorys;
  }

}
