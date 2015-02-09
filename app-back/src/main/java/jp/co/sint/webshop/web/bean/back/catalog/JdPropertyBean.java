package jp.co.sint.webshop.web.bean.back.catalog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.CategoryCode;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.data.dto.JdCategory;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U1040290:京东属性登录更新。
 * 
 * @author System Integrator Corp.
 */
/**
 * @author kousen
 */
public class JdPropertyBean extends UIBackBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  /** 店铺编号 */
  @Length(16)
  @AlphaNum2
  @Metadata(name = "店铺编号")
  private String shopCode;

  /** 商品编号 */
  @Length(16)
  @AlphaNum2
  @Metadata(name = "商品编号")
  private String commodityCode;

  /** 商品名 */
  @Length(50)
  @Metadata(name = "商品名")
  private String commodityName;

  @Length(16)
  @CategoryCode
  private String categoryCode;

  private String categoryName;

  private String propertyHtml;

  @Length(16)
  @Metadata(name = "京东商城类目编号")
  private String jcategoryId;

  // 商品属性
  private List<PropertyBean> commodityStandardPopList = new ArrayList<PropertyBean>();

  private List<JdCategory> listCategorys = new ArrayList<JdCategory>();

  private List<JdAttributeLableBean> attributeLableListBean = new ArrayList<JdAttributeLableBean>();

  private Date updateDatetime;

  private boolean displayNextButton = false;

  private boolean displayUpdateButton = false;

  private String commodityPropertyJson;

  /**
   * @return the commodityPropertyJson
   */
  public String getCommodityPropertyJson() {
    return commodityPropertyJson;
  }

  /**
   * @param commodityPropertyJson
   *          the commodityPropertyJson to set
   */
  public void setCommodityPropertyJson(String commodityPropertyJson) {
    this.commodityPropertyJson = commodityPropertyJson;
  }

  public static class JdPropertyValueBean implements Serializable {

    /**
       * 
       */
    private static final long serialVersionUID = 1L;

    @Required
    @Length(16)
    @Metadata(name = "属性值id", order = 1)
    private String valueId;

    @Length(50)
    @Metadata(name = "属性值名称", order = 2)
    private String valueName;

    @Required
    @Length(16)
    @Metadata(name = "所属属性id", order = 3)
    private String propertyId;

    /**
     * @return the valueId
     */
    public String getValueId() {
      return valueId;
    }

    /**
     * @param valueId
     *          the valueId to set
     */
    public void setValueId(String valueId) {
      this.valueId = valueId;
    }

    /**
     * @return the valueName
     */
    public String getValueName() {
      return valueName;
    }

    /**
     * @param valueName
     *          the valueName to set
     */
    public void setValueName(String valueName) {
      this.valueName = valueName;
    }

    /**
     * @return the propertyId
     */
    public String getPropertyId() {
      return propertyId;
    }

    /**
     * @param propertyId
     *          the propertyId to set
     */
    public void setPropertyId(String propertyId) {
      this.propertyId = propertyId;
    }

  }

  public static class JdAttributeLableBean implements Serializable {

    /**
       * 
       */
    private static final long serialVersionUID = 1L;

    @Metadata(name = "属性id", order = 2)
    private String propertyId;

    @Metadata(name = "属性名称", order = 3)
    private String propertyName;

    private List<String> valueText = new ArrayList<String>();

    /**
     * @return the propertyId
     */
    public String getPropertyId() {
      return propertyId;
    }

    /**
     * @param propertyId
     *          the propertyId to set
     */
    public void setPropertyId(String propertyId) {
      this.propertyId = propertyId;
    }

    /**
     * @return the propertyName
     */
    public String getPropertyName() {
      return propertyName;
    }

    /**
     * @param propertyName
     *          the propertyName to set
     */
    public void setPropertyName(String propertyName) {
      this.propertyName = propertyName;
    }

    /**
     * @return the valueText
     */
    public List<String> getValueText() {
      return valueText;
    }

    /**
     * @param valueText
     *          the valueText to set
     */
    public void setValueText(List<String> valueText) {
      this.valueText = valueText;
    }

  }

  public static class PropertyBean implements Serializable {

    /**
       * 
       */
    private static final long serialVersionUID = 1L;

    @Required
    @Length(16)
    @Metadata(name = "所属类目ID", order = 1)
    private String categoryId;

    @Required
    @Length(16)
    @Metadata(name = "属性id", order = 2)
    private String propertyId;

    @Required
    @Length(50)
    @Metadata(name = "属性名称", order = 3)
    private String propertyName;

    @Metadata(name = "商品编码", order = 4)
    private String commodityCode;

    @Metadata(name = "是否必填", order = 5)
    private String isReq;

    List<JdPropertyValueBean> values = new ArrayList<JdPropertyValueBean>();

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
     * @return the values
     */
    public List<JdPropertyValueBean> getValues() {
      return values;
    }

    /**
     * @param values
     *          the values to set
     */
    public void setValues(List<JdPropertyValueBean> values) {
      this.values = values;
    }

    /**
     * @return the categoryId
     */
    public String getCategoryId() {
      return categoryId;
    }

    /**
     * @param categoryId
     *          the categoryId to set
     */
    public void setCategoryId(String categoryId) {
      this.categoryId = categoryId;
    }

    /**
     * @return the propertyId
     */
    public String getPropertyId() {
      return propertyId;
    }

    /**
     * @param propertyId
     *          the propertyId to set
     */
    public void setPropertyId(String propertyId) {
      this.propertyId = propertyId;
    }

    /**
     * @return the propertyName
     */
    public String getPropertyName() {
      return propertyName;
    }

    /**
     * @param propertyName
     *          the propertyName to set
     */
    public void setPropertyName(String propertyName) {
      this.propertyName = propertyName;
    }

    /**
     * @return the isReq
     */
    public String getIsReq() {
      return isReq;
    }

    /**
     * @param isReq
     *          the isReq to set
     */
    public void setIsReq(String isReq) {
      this.isReq = isReq;
    }
  }

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
    this.setCategoryCode(reqparam.get("jcategoryId"));
    this.setCategoryName(reqparam.get("categoryName"));
    this.setJcategoryId(reqparam.get("jcategoryId"));
    this.setPropertyHtml(reqparam.get("propertyHtml"));
    // 用于存放所有页面属性 的控件名称
    List<String> propertyIds = new ArrayList<String>();
    reqparam.copy(this);
    /**
     * 处理属性页面元素
     */
    // 用于存放关键属性
    List<PropertyBean> standardPop = new ArrayList<PropertyBean>();
    Map<String, String[]> param = reqparam.getRequestMap();
    Set<String> keys = param.keySet();
    Iterator<String> iterator = keys.iterator();
    while (iterator.hasNext()) {
      String name = iterator.next();
      if ((name.indexOf(String.valueOf(this.getJcategoryId())) >= 0) && name.indexOf("_id") >= 0) {
        // 获取属性Id
        propertyIds.add(name);
      }
    }
    for (int i = 0; i < propertyIds.size(); i++) {
      // 得到属性对应的界面控件名称
      String propertyIdName = propertyIds.get(i);
      // 得到名称的前缀 格式 :brandcode_propertyId
      String namePrefix = propertyIdName.substring(0, propertyIdName.lastIndexOf("_"));
      // 获取属性id
      String propertyId = param.get(propertyIdName)[0];
      // 获取属性名称
      String propertyName = param.get(namePrefix + "_name")[0];

      // 获取属性值ID
      String valueId[] = param.get(namePrefix + "_valueId");

      // 手动输入的值 名称
      String manualValues[] = param.get(namePrefix + "_manual_value");
      PropertyBean property = new PropertyBean();
      if (this.getJcategoryId() != null) {
        property.setCategoryId(this.getJcategoryId());
      }
      property.setPropertyId(propertyId);
      property.setPropertyName(propertyName);
      property.setCommodityCode(this.getCommodityCode());
      List<JdPropertyValueBean> values = new ArrayList<JdPropertyValueBean>();
      if (valueId != null && valueId.length > 0) {
        if (manualValues != null && manualValues.length > 0) {// 手动输入
          for (int j = 0; j < valueId.length; j++) {
            if ("0".equals(valueId[j])) {
              JdPropertyValueBean p = new JdPropertyValueBean();
              p.setPropertyId(propertyId);
              p.setValueId("0");
              p.setValueName(manualValues[0]);
              values.add(p);
              break;
            }
          }
        } else { // 不是手动输入
          for (int j = 0; j < valueId.length; j++) {
            JdPropertyValueBean p = new JdPropertyValueBean();
            p.setPropertyId(propertyId);
            p.setValueId(valueId[j]);
            values.add(p);
          }
        }

      } else {
        // 如果页面没有选择或者输入属性值 设置 一个空的对象
        // 在action中validat时会处理该空对象
        JdPropertyValueBean p = new JdPropertyValueBean();
        values.add(p);
      }
      property.setValues(values);
      // 存放到关键属性集合
      if (propertyIdName.indexOf(String.valueOf(this.getJcategoryId())) >= 0) {
        standardPop.add(property);
      }
    }

    // 设置商品关键属性；
    this.commodityStandardPopList = standardPop;
  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U1040290";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.back.catalog.JdPropertyBean.0");
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
   * @return the commodityCame
   */
  public String getCommodityName() {
    return commodityName;
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
   * @param commodityName
   *          the commodityName to set
   */
  public void setCommodityName(String commodityName) {
    this.commodityName = commodityName;
  }

  /**
   * @return the jcategoryId
   */
  public String getJcategoryId() {
    return jcategoryId;
  }

  /**
   * @param jcategoryId
   *          the jcategoryId to set
   */
  public void setJcategoryId(String jcategoryId) {
    this.jcategoryId = jcategoryId;
  }

  /**
   * @return the displayNextButton
   */
  public boolean isDisplayNextButton() {
    return displayNextButton;
  }

  /**
   * @param displayNextButton
   *          the displayNextButton to set
   */
  public void setDisplayNextButton(boolean displayNextButton) {
    this.displayNextButton = displayNextButton;
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

  /**
   * @return the propertyHtml
   */
  public String getPropertyHtml() {
    return propertyHtml;
  }

  /**
   * @param propertyHtml
   *          the propertyHtml to set
   */
  public void setPropertyHtml(String propertyHtml) {
    this.propertyHtml = propertyHtml;
  }

  /**
   * @return the listCategorys
   */
  public List<JdCategory> getListCategorys() {
    return listCategorys;
  }

  /**
   * @param listCategorys
   *          the listCategorys to set
   */
  public void setListCategorys(List<JdCategory> listCategorys) {
    this.listCategorys = listCategorys;
  }

  /**
   * @return the commodityStandardPopList
   */
  public List<PropertyBean> getCommodityStandardPopList() {
    return commodityStandardPopList;
  }

  /**
   * @param commodityStandardPopList
   *          the commodityStandardPopList to set
   */
  public void setCommodityStandardPopList(List<PropertyBean> commodityStandardPopList) {
    this.commodityStandardPopList = commodityStandardPopList;
  }

  /**
   * @return the displayUpdateButton
   */
  public boolean isDisplayUpdateButton() {
    return displayUpdateButton;
  }

  /**
   * @param displayUpdateButton
   *          the displayUpdateButton to set
   */
  public void setDisplayUpdateButton(boolean displayUpdateButton) {
    this.displayUpdateButton = displayUpdateButton;
  }

  /**
   * @return the attributeLableListBean
   */
  public List<JdAttributeLableBean> getAttributeLableListBean() {
    return attributeLableListBean;
  }

  /**
   * @param attributeLableListBean
   *          the attributeLableListBean to set
   */
  public void setAttributeLableListBean(List<JdAttributeLableBean> attributeLableListBean) {
    this.attributeLableListBean = attributeLableListBean;
  }

}
