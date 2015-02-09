package jp.co.sint.webshop.web.bean.back.shop;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.data.attribute.Currency;
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Precision;
import jp.co.sint.webshop.data.attribute.Range;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.data.attribute.Url;
import jp.co.sint.webshop.data.domain.DeliverySpecificationType;
import jp.co.sint.webshop.data.domain.DisplayFlg;
import jp.co.sint.webshop.data.domain.TaxType;
import jp.co.sint.webshop.utility.ArrayUtil;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U1050620:配送種別設定明細のデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class DeliveryTypeEditBean extends UIBackBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  @Required
  @Length(40)
  @Metadata(name = "配送種別名", order = 2)
  private String deliveryTypeName;

  @Required
  @Digit
  @Length(4)
  @Range(min = 0, max = 9999)
  @Metadata(name = "配送種別コード", order = 1)
  private String deliveryTypeNo;

  @Required
  @Metadata(name = "送料税区分", order = 7)
  private String chargeTaxType;

  @Required
  @Metadata(name = "購入金額による送料設定", order = 8)
  private String shippingChargeFreeFlg;

  @Required
  @Length(11)
  @Currency
  @Range(min = 0, max = 99999999)
  @Precision(precision = 10, scale = 2)
  @Metadata(name = "購入金額による送料設定の金額", order = 9)
  private String shippingChargeFreeThreshold;

  @Required
  @Metadata(name = "複数商品購入時の送料計算", order = 10)
  private String shippingChargeFlg;

  @Required
  @Length(11)
  @Currency
  @Range(min = 0, max = 99999999)
  @Precision(precision = 10, scale = 2)
  @Metadata(name = "複数商品購入時の送料計算の金額", order = 11)
  private String shippingChargeThreshold;

  @Required
  @Metadata(name = "配送日時指定", order = 3)
  private String deliverySpecificationType;

  @Url
  @Length(128)
  @Metadata(name = "宅配伝票URL", order = 4)
  private String parcelUrl;

  @Required
  @Metadata(name = "表示区分", order = 6)
  private String displayFlg;

  private List<RegionBlockCharge> regionBlockChargeList = new ArrayList<RegionBlockCharge>();

  private boolean insertMode;

  private boolean updateMode;

  private boolean displayDeleteButtonFlg;

  private boolean displayUpdateButtonFlg;

  private String deliveryCodeDisplayMode;

  private Date updateDatetime;

  private DeliverySpecificationType[] deliverySpecificationTypeList = DeliverySpecificationType.values();

  private DisplayFlg[] displayFlgList = DisplayFlg.values();

  private TaxType[] taxType = TaxType.values();

  private List<NameValue> shippingChargeFlgList = NameValue.asList("0:"
      + Messages.getString("web.bean.back.shop.DeliveryTypeEditBean.1")
      + "/1:"
      + Messages.getString("web.bean.back.shop.DeliveryTypeEditBean.2"));

  /**
   * サブJSPを設定します。
   */
  @Override
  public void setSubJspId() {
  }

  /**
   * リクエストパラメータから値を取得します。
   * 
   * @param reqparam
   *          リクエストパラメータ
   */
  public void createAttributes(RequestParameter reqparam) {
    this.setDeliveryTypeNo(reqparam.get("deliveryTypeNo"));
    this.setDeliveryTypeName(reqparam.get("deliveryTypeName"));
    this.setDeliverySpecificationType(reqparam.get("deliverySpecificationType"));
    this.setParcelUrl(reqparam.get("parcelUrl"));
    this.setDisplayFlg(reqparam.get("displayFlg"));
    //modify by V10-CH start
    //this.setChargeTaxType(reqparam.get("chargeTaxType"));
    this.setChargeTaxType(TaxType.NO_TAX.getValue());
    //modify by V10-CH end 
    String freeFlg = reqparam.get("shippingChargeFreeFlg");
    if (freeFlg == null || freeFlg.length() == 0) {
      this.setShippingChargeFreeFlg("0");
    } else {
      this.setShippingChargeFreeFlg("1");
    }
    String shippingChargeFreeThresholdTmp = reqparam.get("shippingChargeFreeThreshold");
    if (shippingChargeFreeThresholdTmp.equals("")) {
      this.setShippingChargeFreeThreshold("0");
    } else {
      this.setShippingChargeFreeThreshold(reqparam.get("shippingChargeFreeThreshold"));
    }
    this.setShippingChargeFlg(reqparam.get("shippingChargeFlg"));
    String shippingChargeThresholdTmp = reqparam.get("shippingChargeThreshold");
    if (shippingChargeThresholdTmp.equals("")) {
      this.setShippingChargeThreshold("0");
    } else {
      this.setShippingChargeThreshold(shippingChargeThresholdTmp);
    }

    String[] keys = {
        "regionBlockId", "shippingCharge", "leadTime"
    };
    for (RegionBlockCharge charge : regionBlockChargeList) {
      Map<String, String> map = reqparam.getListDataWithKey(charge.getRegionBlockId(), keys);
      charge.setShippingCharge(map.get("shippingCharge"));
      charge.setLeadTime(map.get("leadTime"));
    }
  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U1050620";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.back.shop.DeliveryTypeEditBean.0");
  }

  /**
   * U1050620:配送種別設定明細のサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class RegionBlockCharge implements Serializable {

    /** serial version uid */
    private static final long serialVersionUID = 1L;

    private String regionBlockId;

    private String regionBlockName;

    @Required
    @Length(11)
    @Currency
    @Range(min = 0, max = 99999999)
    @Precision(precision = 10, scale = 2)
    @Metadata(name = "送料", order = 12)
    private String shippingCharge;

    @Required
    @Digit
    @Length(2)
    @Range(min = 0, max = 99)
    @Metadata(name = "リードタイム", order = 13)
    private String leadTime;

    private Date updateDatetime;

    /**
     * updateDatetimeを取得します。
     * 
     * @return updateDatetime
     */
    public Date getUpdateDatetime() {
      return DateUtil.immutableCopy(updateDatetime);
    }

    /**
     * updateDatetimeを設定します。
     * 
     * @param udateDatetime
     *          更新日時
     */
    public void setUpdateDatetime(Date udateDatetime) {
      this.updateDatetime = DateUtil.immutableCopy(udateDatetime);
    }

    /**
     * regionBlockIdを取得します。
     * 
     * @return regionBlockId
     */
    public String getRegionBlockId() {
      return regionBlockId;
    }

    /**
     * regionBlockNameを取得します。
     * 
     * @return regionBlockName
     */
    public String getRegionBlockName() {
      return regionBlockName;
    }

    /**
     * shippingChargeを取得します。
     * 
     * @return shippingCharge
     */
    public String getShippingCharge() {
      return shippingCharge;
    }

    /**
     * leadTimeを取得します。
     * 
     * @return leadTime
     */
    public String getLeadTime() {
      return leadTime;
    }

    /**
     * regionBlockIdを設定します。
     * 
     * @param regionBlockId
     *          regionBlockId
     */
    public void setRegionBlockId(String regionBlockId) {
      this.regionBlockId = regionBlockId;
    }

    /**
     * regionBlockNameを設定します。
     * 
     * @param regionBlockName
     *          regionBlockName
     */
    public void setRegionBlockName(String regionBlockName) {
      this.regionBlockName = regionBlockName;
    }

    /**
     * shippingChargeを設定します。
     * 
     * @param shippingCharge
     *          shippingCharge
     */
    public void setShippingCharge(String shippingCharge) {
      this.shippingCharge = shippingCharge;
    }

    /**
     * leadTimeを設定します。
     * 
     * @param leadTime
     *          leadTime
     */
    public void setLeadTime(String leadTime) {
      this.leadTime = leadTime;
    }

  }

  /**
   * chargeTaxTypeを取得します。
   * 
   * @return chargeTaxType
   */
  public String getChargeTaxType() {
    return chargeTaxType;
  }

  /**
   * chargeTaxTypeを設定します。
   * 
   * @param chargeTaxType
   *          chargeTaxType
   */
  public void setChargeTaxType(String chargeTaxType) {
    this.chargeTaxType = chargeTaxType;
  }

  /**
   * deliveryTypeNoを取得します。
   * 
   * @return deliveryTypeNo
   */
  public String getDeliveryTypeNo() {
    return deliveryTypeNo;
  }

  /**
   * deliveryTypeNoを設定します。
   * 
   * @param deliveryTypeNo
   *          deliveryTypeNo
   */
  public void setDeliveryTypeNo(String deliveryTypeNo) {
    this.deliveryTypeNo = deliveryTypeNo;
  }

  /**
   * deliveryTypeNameを取得します。
   * 
   * @return deliveryTypeName
   */
  public String getDeliveryTypeName() {
    return deliveryTypeName;
  }

  /**
   * deliveryTypeNameを設定します。
   * 
   * @param deliveryTypeName
   *          deliveryTypeName
   */
  public void setDeliveryTypeName(String deliveryTypeName) {
    this.deliveryTypeName = deliveryTypeName;
  }

  /**
   * deliverySpecificationTypeを取得します。
   * 
   * @return deliverySpecificationType
   */
  public String getDeliverySpecificationType() {
    return deliverySpecificationType;
  }

  /**
   * deliverySpecificationTypeを設定します。
   * 
   * @param deliverySpecificationType
   *          deliverySpecificationType
   */
  public void setDeliverySpecificationType(String deliverySpecificationType) {
    this.deliverySpecificationType = deliverySpecificationType;
  }

  /**
   * displayFlgを取得します。
   * 
   * @return displayFlg
   */
  public String getDisplayFlg() {
    return displayFlg;
  }

  /**
   * displayFlgを設定します。
   * 
   * @param displayFlg
   *          displayFlg
   */
  public void setDisplayFlg(String displayFlg) {
    this.displayFlg = displayFlg;
  }

  /**
   * parcelUrlを取得します。
   * 
   * @return parcelUrl
   */
  public String getParcelUrl() {
    return parcelUrl;
  }

  /**
   * parcelUrlを設定します。
   * 
   * @param parcelUrl
   *          parcelUrl
   */
  public void setParcelUrl(String parcelUrl) {
    this.parcelUrl = parcelUrl;
  }

  /**
   * regionBlockChargeListを取得します。
   * 
   * @return regionBlockChargeList
   */
  public List<RegionBlockCharge> getRegionBlockChargeList() {
    return regionBlockChargeList;
  }

  /**
   * regionBlockChargeListを設定します。
   * 
   * @param regionBlockChargeList
   *          regionBlockChargeList
   */
  public void setRegionBlockChargeList(List<RegionBlockCharge> regionBlockChargeList) {
    this.regionBlockChargeList = regionBlockChargeList;
  }

  /**
   * shippingChargeFreeThresholdを取得します。
   * 
   * @return shippingChargeFreeThreshold
   */
  public String getShippingChargeFreeThreshold() {
    return shippingChargeFreeThreshold;
  }

  /**
   * shippingChargeFreeThresholdを設定します。
   * 
   * @param shippingChargeFreeThreshold
   *          shippingChargeFreeThreshold
   */
  public void setShippingChargeFreeThreshold(String shippingChargeFreeThreshold) {
    this.shippingChargeFreeThreshold = shippingChargeFreeThreshold;
  }

  /**
   * shippingChargeFlgを取得します。
   * 
   * @return shippingChargeFlg
   */
  public String getShippingChargeFlg() {
    return shippingChargeFlg;
  }

  /**
   * shippingChargeFlgを設定します。
   * 
   * @param shippingChargeFlg
   *          shippingChargeFlg
   */
  public void setShippingChargeFlg(String shippingChargeFlg) {
    this.shippingChargeFlg = shippingChargeFlg;
  }

  /**
   * shippingChargeFreeFlgを取得します。
   * 
   * @return shippingChargeFreeFlg
   */
  public String getShippingChargeFreeFlg() {
    return shippingChargeFreeFlg;
  }

  /**
   * shippingChargeFreeFlgを設定します。
   * 
   * @param shippingChargeFreeFlg
   *          shippingChargeFreeFlg
   */
  public void setShippingChargeFreeFlg(String shippingChargeFreeFlg) {
    this.shippingChargeFreeFlg = shippingChargeFreeFlg;
  }

  /**
   * shippingChargeThresholdを取得します。
   * 
   * @return shippingChargeThreshold
   */
  public String getShippingChargeThreshold() {
    return shippingChargeThreshold;
  }

  /**
   * shippingChargeThresholdを設定します。
   * 
   * @param shippingChargeThreshold
   *          shippingChargeThreshold
   */
  public void setShippingChargeThreshold(String shippingChargeThreshold) {
    this.shippingChargeThreshold = shippingChargeThreshold;
  }

  /**
   * insertModeを取得します。
   * 
   * @return insertMode
   */
  public boolean isInsertMode() {
    return insertMode;
  }

  /**
   * insertModeを設定します。
   * 
   * @param insertMode
   *          insertMode
   */
  public void setInsertMode(boolean insertMode) {
    this.insertMode = insertMode;
  }

  /**
   * updateModeを取得します。
   * 
   * @return updateMode
   */
  public boolean isUpdateMode() {
    return updateMode;
  }

  /**
   * updateModeを設定します。
   * 
   * @param updateMode
   *          updateMode
   */
  public void setUpdateMode(boolean updateMode) {
    this.updateMode = updateMode;
  }

  /**
   * updateDatetimeを取得します。
   * 
   * @return updateDatetime
   */
  public Date getUpdateDatetime() {
    return DateUtil.immutableCopy(updateDatetime);
  }

  /**
   * updateDatetimeを設定します。
   * 
   * @param updateDatetime
   *          updateDatetime
   */
  public void setUpdateDatetime(Date updateDatetime) {
    this.updateDatetime = DateUtil.immutableCopy(updateDatetime);
  }

  /**
   * deliverySpecificationTypeListを取得します。
   * 
   * @return deliverySpecificationTypeList
   */
  public DeliverySpecificationType[] getDeliverySpecificationTypeList() {
    return ArrayUtil.immutableCopy(deliverySpecificationTypeList);
  }

  /**
   * displayFlgListを取得します。
   * 
   * @return displayFlgList
   */
  public DisplayFlg[] getDisplayFlgList() {
    return ArrayUtil.immutableCopy(displayFlgList);
  }

  /**
   * taxTypeを取得します。
   * 
   * @return taxType
   */
  public TaxType[] getTaxType() {
    return ArrayUtil.immutableCopy(taxType);
  }

  /**
   * shippingChargeFlgListを取得します。
   * 
   * @return shippingChargeFlgList
   */
  public List<NameValue> getShippingChargeFlgList() {
    return shippingChargeFlgList;
  }

  /**
   * deliveryCodeDisplayModeを取得します。
   * 
   * @return deliveryCodeDisplayMode
   */
  public String getDeliveryCodeDisplayMode() {
    return deliveryCodeDisplayMode;
  }

  /**
   * deliveryCodeDisplayModeを設定します。
   * 
   * @param deliveryCodeDisplayMode
   *          deliveryCodeDisplayMode
   */
  public void setDeliveryCodeDisplayMode(String deliveryCodeDisplayMode) {
    this.deliveryCodeDisplayMode = deliveryCodeDisplayMode;
  }

  /**
   * displayDeleteButtonFlgを取得します。
   * 
   * @return displayDeleteButtonFlg
   */
  public boolean getDisplayDeleteButtonFlg() {
    return displayDeleteButtonFlg;
  }

  /**
   * displayDeleteButtonFlgを設定します。
   * 
   * @param displayDeleteButtonFlg
   *          displayDeleteButtonFlg
   */
  public void setDisplayDeleteButtonFlg(boolean displayDeleteButtonFlg) {
    this.displayDeleteButtonFlg = displayDeleteButtonFlg;
  }

  /**
   * displayUpdateButtonFlgを取得します。
   * 
   * @return displayUpdateButtonFlg
   */
  public boolean getDisplayUpdateButtonFlg() {
    return displayUpdateButtonFlg;
  }

  /**
   * displayUpdateButtonFlgを設定します。
   * 
   * @param displayUpdateButtonFlg
   *          displayUpdateButtonFlg
   */
  public void setDisplayUpdateButtonFlg(boolean displayUpdateButtonFlg) {
    this.displayUpdateButtonFlg = displayUpdateButtonFlg;
  }
  
}
