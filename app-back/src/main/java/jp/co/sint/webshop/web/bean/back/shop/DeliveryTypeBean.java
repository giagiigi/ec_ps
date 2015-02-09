package jp.co.sint.webshop.web.bean.back.shop;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.data.domain.DeliverySpecificationType;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U1050610:配送種別設定のデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class DeliveryTypeBean extends UIBackBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private List<DeliveryTypeDetail> deliveryTypeList = new ArrayList<DeliveryTypeDetail>();

  private DeliveryTimeDetail deliveryTimeRegister = new DeliveryTimeDetail();

  private List<DeliveryTimeDetail> deliveryTimeList = new ArrayList<DeliveryTimeDetail>();

  private String selectDeliveryTypeName;

  private String deleteTimeCode;

  private String deleteDeliveryTypeNo;

  @Required
  @Metadata(name = "配送種別コード", order = 1)
  private String selectDeliveryTypeNo;

  private boolean dispTime;

  private boolean displayDeleteButtonFlg;

  private boolean displayRegisterButtonFlg;

  private boolean displaySelectButtonFlg;

  /**
   * displaySelectButtonFlgを取得します。
   * 
   * @return displaySelectButtonFlg displaySelectButtonFlg
   */
  public boolean isDisplaySelectButtonFlg() {
    return displaySelectButtonFlg;
  }

  /**
   * displaySelectButtonFlgを設定します。
   * 
   * @param displaySelectButtonFlg
   *          displaySelectButtonFlg
   */
  public void setDisplaySelectButtonFlg(boolean displaySelectButtonFlg) {
    this.displaySelectButtonFlg = displaySelectButtonFlg;
  }

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
    setSelectDeliveryTypeNo(reqparam.get("selectDeliveryTypeNo"));
    this.deliveryTimeRegister = new DeliveryTimeDetail();
    this.deliveryTimeRegister.setDeliveryAppointedTimeCode(reqparam.get("registerTimeCode"));
    this.deliveryTimeRegister.setDeliveryAppointedTimeName(reqparam.get("registerTimeName"));
    this.deliveryTimeRegister.setDeliveryAppointedStartTime(reqparam.get("registerTimeStart"));
    this.deliveryTimeRegister.setDeliveryAppointedEndTime(reqparam.get("registerTimeEnd"));
    this.deleteTimeCode = reqparam.get("deleteTimeCode");
    this.deleteDeliveryTypeNo = reqparam.get("deleteDeliveryTypeNo");

    String[] listKey = {
        "updateTimeCode", "updateTimeName", "updateStartTime", "updateEndTime"
    };
    for (DeliveryTimeDetail time : deliveryTimeList) {
      Map<String, String> map = reqparam.getListDataWithKey(time.getDeliveryAppointedTimeCode(), listKey);
      time.setDeliveryAppointedTimeCode(map.get("updateTimeCode"));
      time.setDeliveryAppointedTimeName(map.get("updateTimeName"));
      time.setDeliveryAppointedStartTime(map.get("updateStartTime"));
      time.setDeliveryAppointedEndTime(map.get("updateEndTime"));
    }

  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U1050610";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.back.shop.DeliveryTypeBean.0");
  }

  /**
   * U1050610:配送種別設定のサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class DeliveryTypeDetail implements Serializable {

    /** serial version uid */
    private static final long serialVersionUID = 1L;

    private String deliveryTypeName;

    private String deliveryTypeNo;

    private String deliverySpecificationType = null;

    private String parcelUrl;

    private boolean deletableFlg;

    private String leadTime;

    /**
     * deletableFlgを取得します。
     * 
     * @return deletableFlg
     */
    public boolean isDeletableFlg() {
      return deletableFlg;
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
     * deliveryTypeNameを取得します。
     * 
     * @return deliveryTypeName
     */
    public String getDeliveryTypeName() {
      return deliveryTypeName;
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
     * parcelUrlを取得します。
     * 
     * @return parcelUrl
     */
    public String getParcelUrl() {
      return parcelUrl;
    }

    /**
     * deletableFlgを設定します。
     * 
     * @param deletableFlg
     *          deletableFlg
     */
    public void setDeletableFlg(boolean deletableFlg) {
      this.deletableFlg = deletableFlg;
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
     * deliveryTypeNameを設定します。
     * 
     * @param deliveryTypeName
     *          deliveryTypeName
     */
    public void setDeliveryTypeName(String deliveryTypeName) {
      this.deliveryTypeName = deliveryTypeName;
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
     * deliverySpecificationTypeNameを取得します。
     * 
     * @return deliverySpecificationTypeName deliverySpecificationTypeName
     */
    public String getDeliverySpecificationTypeName() {
      return DeliverySpecificationType.fromValue(this.deliverySpecificationType).getName();
    }

    /**
     * displayOpenTimeFlgを取得します。
     * 
     * @return DisplayOpenTimeFlg
     */
    public boolean isDisplayOpenTimeFlg() {
      return this.deliverySpecificationType.equals(DeliverySpecificationType.DATE_AND_TIME.getValue())
          || this.deliverySpecificationType.equals(DeliverySpecificationType.TIME_ONLY.getValue());
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

  }

  /**
   * U1050610:配送種別設定のサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class DeliveryTimeDetail implements Serializable {

    /** serial version uid */
    private static final long serialVersionUID = 1L;

    @Required
    @Length(16)
    @AlphaNum2
    @Metadata(name = "配送時間帯コード", order = 2)
    private String deliveryAppointedTimeCode;

    @Required
    @Length(15)
    @Metadata(name = "配送時間帯名称", order = 3)
    private String deliveryAppointedTimeName;

    @Required
    @Digit
    @Metadata(name = "開始時間", order = 4)
    private String deliveryAppointedStartTime;

    @Required
    @Digit
    @Metadata(name = "終了時間", order = 5)
    private String deliveryAppointedEndTime;

    private Date updateDatetime;

    /**
     * deliveryAppointedEndTimeを取得します。
     * 
     * @return deliveryAppointedEndTime
     */
    public String getDeliveryAppointedEndTime() {
      return deliveryAppointedEndTime;
    }

    /**
     * deliveryAppointedStartTimeを取得します。
     * 
     * @return deliveryAppointedStartTime
     */
    public String getDeliveryAppointedStartTime() {
      return deliveryAppointedStartTime;
    }

    /**
     * deliveryAppointedTimeCodeを取得します。
     * 
     * @return deliveryAppointedTimeCode
     */
    public String getDeliveryAppointedTimeCode() {
      return deliveryAppointedTimeCode;
    }

    /**
     * deliveryAppointedEndTimeを設定します。
     * 
     * @param deliveryAppointedEndTime
     *          deliveryAppointedEndTime
     */
    public void setDeliveryAppointedEndTime(String deliveryAppointedEndTime) {
      this.deliveryAppointedEndTime = deliveryAppointedEndTime;
    }

    /**
     * deliveryAppointedStartTimeを設定します。
     * 
     * @param deliveryAppointedStartTime
     *          deliveryAppointedStartTime
     */
    public void setDeliveryAppointedStartTime(String deliveryAppointedStartTime) {
      this.deliveryAppointedStartTime = deliveryAppointedStartTime;
    }

    /**
     * deliveryAppointedTimeCodeを設定します。
     * 
     * @param deliveryAppointedTimeCode
     *          deliveryAppointedTimeCode
     */
    public void setDeliveryAppointedTimeCode(String deliveryAppointedTimeCode) {
      this.deliveryAppointedTimeCode = deliveryAppointedTimeCode;
    }

    /**
     * deliveryAppointedTimeNameを取得します。
     * 
     * @return deliveryAppointedTimeName
     */
    public String getDeliveryAppointedTimeName() {
      return deliveryAppointedTimeName;
    }

    /**
     * deliveryAppointedTimeNameを設定します。
     * 
     * @param deliveryAppointedTimeName
     *          deliveryAppointedTimeName
     */
    public void setDeliveryAppointedTimeName(String deliveryAppointedTimeName) {
      this.deliveryAppointedTimeName = deliveryAppointedTimeName;
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

  }

  /**
   * deliveryTimeListを取得します。
   * 
   * @return deliveryTimeList
   */
  public List<DeliveryTimeDetail> getDeliveryTimeList() {
    return deliveryTimeList;
  }

  /**
   * deliveryTimeRegisterを取得します。
   * 
   * @return deliveryTimeRegister
   */
  public DeliveryTimeDetail getDeliveryTimeRegister() {
    return deliveryTimeRegister;
  }

  /**
   * deliveryTypeListを取得します。
   * 
   * @return deliveryTypeList
   */
  public List<DeliveryTypeDetail> getDeliveryTypeList() {
    return deliveryTypeList;
  }

  /**
   * deliveryTimeListを設定します。
   * 
   * @param deliveryTimeList
   *          deliveryTimeList
   */
  public void setDeliveryTimeList(List<DeliveryTimeDetail> deliveryTimeList) {
    this.deliveryTimeList = deliveryTimeList;
  }

  /**
   * deliveryTimeRegisterを設定します。
   * 
   * @param deliveryTimeRegister
   *          deliveryTimeRegister
   */
  public void setDeliveryTimeRegister(DeliveryTimeDetail deliveryTimeRegister) {
    this.deliveryTimeRegister = deliveryTimeRegister;
  }

  /**
   * deliveryTypeListを設定します。
   * 
   * @param deliveryTypeList
   *          deliveryTypeList
   */
  public void setDeliveryTypeList(List<DeliveryTypeDetail> deliveryTypeList) {
    this.deliveryTypeList = deliveryTypeList;
  }

  /**
   * dispTimeを取得します。
   * 
   * @return dispTime
   */
  public boolean isDispTime() {
    return dispTime;
  }

  /**
   * dispTimeを設定します。
   * 
   * @param dispTime
   *          dispTime
   */
  public void setDispTime(boolean dispTime) {
    this.dispTime = dispTime;
  }

  /**
   * displayDeleteButtonFlgを取得します。
   * 
   * @return displayDeleteButtonFlg
   */
  public boolean isDisplayDeleteButtonFlg() {
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
   * @return displayRegisterButtonFlg
   */
  public boolean getDisplayRegisterButtonFlg() {
    return displayRegisterButtonFlg;
  }

  /**
   * displayUpdateButtonFlgを設定します。
   * 
   * @param displayRegisterButtonFlg
   *          displayRegisterButtonFlg
   */
  public void setDisplayRegisterButtonFlg(boolean displayRegisterButtonFlg) {
    this.displayRegisterButtonFlg = displayRegisterButtonFlg;
  }

  /**
   * selectDeliveryTypeNameを取得します。
   * 
   * @return selectDeliveryTypeName
   */
  public String getSelectDeliveryTypeName() {
    return selectDeliveryTypeName;
  }

  /**
   * selectDeliveryTypeNameを設定します。
   * 
   * @param selectDeliveryTypeName
   *          selectDeliveryTypeName
   */
  public void setSelectDeliveryTypeName(String selectDeliveryTypeName) {
    this.selectDeliveryTypeName = selectDeliveryTypeName;
  }

  /**
   * selectdeliveryTypeNoを取得します。
   * 
   * @return selectDeliveryTypeNo
   */
  public String getSelectDeliveryTypeNo() {
    return selectDeliveryTypeNo;
  }

  /**
   * selectDeliveryTypeNoを設定します。
   * 
   * @param selectDeliveryTypeNo
   *          selectDeliveryTypeNo
   */
  public void setSelectDeliveryTypeNo(String selectDeliveryTypeNo) {
    this.selectDeliveryTypeNo = selectDeliveryTypeNo;
  }

  /**
   * deleteTimeCodeを取得します。
   * 
   * @return deleteTimeCode
   */
  public String getDeleteTimeCode() {
    return deleteTimeCode;
  }

  /**
   * deleteTimeCodeを設定します。
   * 
   * @param deleteTimeCode
   *          deleteTimeCode
   */
  public void setDeleteTimeCode(String deleteTimeCode) {
    this.deleteTimeCode = deleteTimeCode;
  }

  /**
   * deleteDeliveryTypeNoを取得します。
   * 
   * @return deleteDeliveryTypeNo
   */
  public String getDeleteDeliveryTypeNo() {
    return deleteDeliveryTypeNo;
  }

  /**
   * deleteDeliveryTypeNoを設定します。
   * 
   * @param deleteDeliveryTypeNo
   *          deleteDeliveryTypeNo
   */
  public void setDeleteDeliveryTypeNo(String deleteDeliveryTypeNo) {
    this.deleteDeliveryTypeNo = deleteDeliveryTypeNo;
  }

}
