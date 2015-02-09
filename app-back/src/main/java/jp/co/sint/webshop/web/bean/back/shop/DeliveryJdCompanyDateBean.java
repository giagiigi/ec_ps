package jp.co.sint.webshop.web.bean.back.shop;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.data.domain.AppointedTimeType;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.bean.UISearchBean;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.webutility.PagerValue;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * 设定配送时间Bean
 * 
 * @author ob
 */
public class DeliveryJdCompanyDateBean extends UIBackBean implements UISearchBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  // 配送时间段集合
  private List<CodeAttribute> choiceCodeList = new ArrayList<CodeAttribute>();

  // 配送公司关联情报
  private DeliveryRelatedInfoBean relatedInfoBean = new DeliveryRelatedInfoBean();

  // 配送公司关联情报集合
  private List<DeliveryRelatedInfoBean> relatedInfoList = new ArrayList<DeliveryRelatedInfoBean>(0);

  private PagerValue pagerValue = new PagerValue();

  // 配送公司编号
  private String deliveryCompanyNo;

  // 配送公司名
  private String deliveryCompanyName;

  // 地域编号
  private String prefectureCode;

  // 地域名
  private String prefectureName;

  private boolean displayUpdateFlg = false;

  private boolean displayDeleteFlg = false;
  
  private String mode = "";

  /**
   * サブJSPを設定します。
   */
  @Override
  public void setSubJspId() {
  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U1051610";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return "配送希望日时关联情报";
  }

  /**
   * 配送公司关联情报
   * 
   * @author ob
   */
  public static class DeliveryRelatedInfoBean implements Serializable {

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;

    private String deliveryRelatedInfoNo;

    private String shopCode;

    private String deliveryCompanyNo;

    private String prefectureCode;

    @Required
    @Length(1)
    @Metadata(name = "COD区分")
    private Long codType;

    @Length(1)
    @Metadata(name = "配送希望日区分")
    private Long deliveryDateType;

    private Long deliveryAppointedTimeType = AppointedTimeType.DO.longValue();

    private Long deliveryAppointedStartTime;

    private Long deliveryAppointedEndTime;

    private String deliveryAppointedTimeName;

    @Length(10)
    @Digit
    @Metadata(name = "商品重量最小值")
    private String minWeight;

    @Length(10)
    @Digit
    @Metadata(name = "商品重量最大值")
    private String maxWeight;

    public String getMinWeight() {
      return minWeight;
    }

    public void setMinWeight(String minWeight) {
      this.minWeight = minWeight;
    }

    public String getMaxWeight() {
      return maxWeight;
    }

    public void setMaxWeight(String maxWeight) {
      this.maxWeight = maxWeight;
    }

    /**
     * 取得shopCode
     * 
     * @return shopCode
     */
    public String getShopCode() {
      return shopCode;
    }

    /**
     * 设置shopCode
     * 
     * @param shopCode
     *          shopCode
     */
    public void setShopCode(String shopCode) {
      this.shopCode = shopCode;
    }

    /**
     * 取得配送公司编号
     * 
     * @return deliveryCompanyNo
     */
    public String getDeliveryCompanyNo() {
      return deliveryCompanyNo;
    }

    /**
     * 设置配送公司编号
     * 
     * @param deliveryCompanyNo
     *          deliveryCompanyNo
     */
    public void setDeliveryCompanyNo(String deliveryCompanyNo) {
      this.deliveryCompanyNo = deliveryCompanyNo;
    }

    /**
     * 取得省/直辖市/自治区编号
     * 
     * @return prefectureCode
     */
    public String getPrefectureCode() {
      return prefectureCode;
    }

    /**
     * 设置省/直辖市/自治区编号
     * 
     * @param prefectureCode
     *          prefectureCode
     */
    public void setPrefectureCode(String prefectureCode) {
      this.prefectureCode = prefectureCode;
    }

    /**
     * 取得COD区分
     * 
     * @return codeType
     */
    public Long getCodType() {
      return codType;
    }

    /**
     * 取得配送希望日区分
     * 
     * @return deliveryDateType
     */
    public Long getDeliveryDateType() {
      return deliveryDateType;
    }

    /**
     * 设置配送希望日区分
     * 
     * @param deliveryDateType
     *          deliveryDateType
     */
    public void setDeliveryDateType(Long deliveryDateType) {
      this.deliveryDateType = deliveryDateType;
    }

    /**
     * 取得配送时间段区分
     * 
     * @return deliveryAppointedTimeType
     */
    public Long getDeliveryAppointedTimeType() {
      return deliveryAppointedTimeType;
    }

    /**
     * 设置配送时间段编号
     * 
     * @param deliveryAppointedTimeType
     *          deliveryAppointedTimeType
     */
    public void setDeliveryAppointedTimeType(Long deliveryAppointedTimeType) {
      this.deliveryAppointedTimeType = deliveryAppointedTimeType;
    }

    /**
     * @return the deliveryRelatedInfoNo
     */
    public String getDeliveryRelatedInfoNo() {
      return deliveryRelatedInfoNo;
    }

    /**
     * @param deliveryRelatedInfoNo
     *          the deliveryRelatedInfoNo to set
     */
    public void setDeliveryRelatedInfoNo(String deliveryRelatedInfoNo) {
      this.deliveryRelatedInfoNo = deliveryRelatedInfoNo;
    }

    /**
     * @return the deliveryAppointedStartTime
     */
    public Long getDeliveryAppointedStartTime() {
      return deliveryAppointedStartTime;
    }

    /**
     * @param deliveryAppointedStartTime
     *          the deliveryAppointedStartTime to set
     */
    public void setDeliveryAppointedStartTime(Long deliveryAppointedStartTime) {
      this.deliveryAppointedStartTime = deliveryAppointedStartTime;
    }

    /**
     * @return the deliveryAppointedEndTime
     */
    public Long getDeliveryAppointedEndTime() {
      return deliveryAppointedEndTime;
    }

    /**
     * @param deliveryAppointedEndTime
     *          the deliveryAppointedEndTime to set
     */
    public void setDeliveryAppointedEndTime(Long deliveryAppointedEndTime) {
      this.deliveryAppointedEndTime = deliveryAppointedEndTime;
    }

    /**
     * @param codType
     *          the codType to set
     */
    public void setCodType(Long codType) {
      this.codType = codType;
    }

    /**
     * @return the deliveryAppointedTimeName
     */
    public String getDeliveryAppointedTimeName() {
      return deliveryAppointedTimeName;
    }

    /**
     * @param deliveryAppointedTimeName
     *          the deliveryAppointedTimeName to set
     */
    public void setDeliveryAppointedTimeName(String deliveryAppointedTimeName) {
      this.deliveryAppointedTimeName = deliveryAppointedTimeName;
    }

  }

  /**
   * pagerValueを取得します。
   * 
   * @return pagerValue pagerValue
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
   * 取得配送公司编号
   * 
   * @return deliveryCompanyNo
   */
  public String getDeliveryCompanyNo() {
    return deliveryCompanyNo;
  }

  /**
   * 设置配送公司编号
   * 
   * @param deliveryCompanyNo
   *          deliveryCompanyNo
   */
  public void setDeliveryCompanyNo(String deliveryCompanyNo) {
    this.deliveryCompanyNo = deliveryCompanyNo;
  }

  /**
   * 取得配送公司名
   * 
   * @return deliveryCompanyName
   */
  public String getDeliveryCompanyName() {
    return deliveryCompanyName;
  }

  /**
   * 设置配送公司名
   * 
   * @param deliveryCompanyName
   *          deliveryCompanyName
   */
  public void setDeliveryCompanyName(String deliveryCompanyName) {
    this.deliveryCompanyName = deliveryCompanyName;
  }

  /**
   * 取得配送时间段集合
   * 
   * @return choiceCodeList
   */
  public List<CodeAttribute> getChoiceCodeList() {
    return choiceCodeList;
  }

  /**
   * 设置配送时间段集合
   * 
   * @param choiceCodeList
   *          choiceCodeList
   */
  public void setChoiceCodeList(List<CodeAttribute> choiceCodeList) {
    this.choiceCodeList = choiceCodeList;
  }

  /**
   * 取得地域编号
   * 
   * @return prefectureCode
   */
  public String getPrefectureCode() {
    return prefectureCode;
  }

  /**
   * 设置地域编号
   * 
   * @param prefectureCode
   *          prefectureCode
   */
  public void setPrefectureCode(String prefectureCode) {
    this.prefectureCode = prefectureCode;
  }

  /**
   * 取得地域名
   * 
   * @return prefectureCode
   */
  public String getPrefectureName() {
    return prefectureName;
  }

  /**
   * 设置地域名
   * 
   * @param prefectureName
   *          prefectureCode
   */
  public void setPrefectureName(String prefectureName) {
    this.prefectureName = prefectureName;
  }

  /**
   * 取得配送公司关联情报
   * 
   * @return
   */
  public DeliveryRelatedInfoBean getRelatedInfoBean() {
    return relatedInfoBean;
  }

  /**
   * 设置配送公司关联情报
   * 
   * @param relatedInfoBean
   */
  public void setRelatedInfoBean(DeliveryRelatedInfoBean relatedInfoBean) {
    this.relatedInfoBean = relatedInfoBean;
  }

  /**
   * 取得配送公司关联情报集合
   * 
   * @return
   */
  public List<DeliveryRelatedInfoBean> getRelatedInfoList() {
    return relatedInfoList;
  }

  /**
   * 设置配送公司关联情报集合
   * 
   * @param relatedInfoList
   */
  public void setRelatedInfoList(List<DeliveryRelatedInfoBean> relatedInfoList) {
    this.relatedInfoList = relatedInfoList;
  }

  /**
   * 取得页面数值
   * 
   * @param reqparam
   *          RequestParameter
   */
  public void createAttributes(RequestParameter reqparam) {

    this.setRelatedInfoBean(new DeliveryRelatedInfoBean());
    // cod区分取得
    if (!StringUtil.isNullOrEmpty(reqparam.get("codType"))) {
      this.getRelatedInfoBean().setCodType(NumUtil.toLong(reqparam.get("codType")));
    }
    // 希望日指定区分
    if (!StringUtil.isNullOrEmpty(reqparam.get("deliveryDateType"))) {
      this.getRelatedInfoBean().setDeliveryDateType(NumUtil.toLong(reqparam.get("deliveryDateType")));
    }
    // 时间段指定区分
    if (!StringUtil.isNullOrEmpty(reqparam.get("deliveryAppointedTimeType"))) {
      this.getRelatedInfoBean().setDeliveryAppointedTimeType(NumUtil.toLong(reqparam.get("deliveryAppointedTimeType")));
    }
    if (AppointedTimeType.DO.longValue().equals(this.getRelatedInfoBean().getDeliveryAppointedTimeType())) {
      if (StringUtil.hasValue(reqparam.get("deliveryAppointedStartTime"))) {
        this.getRelatedInfoBean().setDeliveryAppointedStartTime(NumUtil.toLong(reqparam.get("deliveryAppointedStartTime")));
      }
      if (StringUtil.hasValue(reqparam.get("deliveryAppointedEndTime"))) {
        this.getRelatedInfoBean().setDeliveryAppointedEndTime(NumUtil.toLong(reqparam.get("deliveryAppointedEndTime")));
      }
    }
    // 商品重量最小值
    if (!StringUtil.isNullOrEmpty(reqparam.get("minWeight"))) {
      this.getRelatedInfoBean().setMinWeight(reqparam.get("minWeight"));
    }
    // 商品重量最大值
    if (!StringUtil.isNullOrEmpty(reqparam.get("maxWeight"))) {
      this.getRelatedInfoBean().setMaxWeight(reqparam.get("maxWeight"));
    }
  }

  /**
   * @return the displayUpdateFlg
   */
  public boolean isDisplayUpdateFlg() {
    return displayUpdateFlg;
  }

  /**
   * @param displayUpdateFlg
   *          the displayUpdateFlg to set
   */
  public void setDisplayUpdateFlg(boolean displayUpdateFlg) {
    this.displayUpdateFlg = displayUpdateFlg;
  }

  /**
   * @return the displayDeleteFlg
   */
  public boolean isDisplayDeleteFlg() {
    return displayDeleteFlg;
  }

  /**
   * @param displayDeleteFlg
   *          the displayDeleteFlg to set
   */
  public void setDisplayDeleteFlg(boolean displayDeleteFlg) {
    this.displayDeleteFlg = displayDeleteFlg;
  }

  
  /**
   * @return the mode
   */
  public String getMode() {
    return mode;
  }

  
  /**
   * @param mode the mode to set
   */
  public void setMode(String mode) {
    this.mode = mode;
  }
}
