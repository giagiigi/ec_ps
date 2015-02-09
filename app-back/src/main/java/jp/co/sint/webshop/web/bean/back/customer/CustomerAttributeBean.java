package jp.co.sint.webshop.web.bean.back.customer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Range;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U1030510:顧客属性のデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class CustomerAttributeBean extends UIBackBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private CustomerAttributeBeanDetail attributeEdit = new CustomerAttributeBeanDetail();

  private List<CustomerAttributeBeanDetail> attributeList = new ArrayList<CustomerAttributeBeanDetail>();

  private CustomerAttributeChoiceBeanDetail attributeChoiceEdit = new CustomerAttributeChoiceBeanDetail();

  private List<CustomerAttributeChoiceBeanDetail> attributeChoiceList = new ArrayList<CustomerAttributeChoiceBeanDetail>();

  private String displayChoicesList;

  private String updateButtonDisplayFlg;

  private String registerButtonDisplayFlg;

  private boolean updateMode;

  private boolean deleteMode;

  private String editMode;
  
  private boolean displayDeleteChoiceButton; // 10.1.7 10304 追加

  /**
   * U1030510:顧客属性のサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class CustomerAttributeBeanDetail implements Serializable {

    /** serial version uid */
    private static final long serialVersionUID = 1L;

    private String customerAttributeNo;

    @Required
    @Length(40)
    @Metadata(name = "顧客属性名", order = 1)
    private String customerAttributeName;

    private String customerAttributeTypeName;

    @Required
    @Length(1)
    private String customerAttributeType;

    @Required
    @Digit
    @Length(8)
    @Range(min = 0, max = 99999999)
    @Metadata(name = "表示順", order = 2)
    private String displayOrder;

    private Date updatedDatetime;

    /**
     * updatedDatetimeを取得します。
     * 
     * @return updatedDatetime
     */
    public Date getUpdatedDatetime() {
      return DateUtil.immutableCopy(updatedDatetime);
    }

    /**
     * updatedDatetimeを設定します。
     * 
     * @param updatedDatetime
     *          updatedDatetime
     */
    public void setUpdatedDatetime(Date updatedDatetime) {
      this.updatedDatetime = DateUtil.immutableCopy(updatedDatetime);
    }

    /**
     * customerAttributeNoを取得します。
     * 
     * @return customerAttributeNo
     */
    public String getCustomerAttributeNo() {
      return customerAttributeNo;
    }

    /**
     * customerAttributeNameを取得します。
     * 
     * @return customerAttributeName
     */
    public String getCustomerAttributeName() {
      return customerAttributeName;
    }

    /**
     * customerAttributeTypeを取得します。
     * 
     * @return customerAttributeType
     */
    public String getCustomerAttributeType() {
      return customerAttributeType;
    }

    /**
     * displayOrderを取得します。
     * 
     * @return displayOrder
     */
    public String getDisplayOrder() {
      return displayOrder;
    }

    /**
     * customerAttributeNoを設定します。
     * 
     * @param customerAttributeNo
     *          customerAttributeNo
     */
    public void setCustomerAttributeNo(String customerAttributeNo) {
      this.customerAttributeNo = customerAttributeNo;
    }

    /**
     * customerAttributeNameを設定します。
     * 
     * @param customerAttributeName
     *          customerAttributeName
     */
    public void setCustomerAttributeName(String customerAttributeName) {
      this.customerAttributeName = customerAttributeName;
    }

    /**
     * customerAttributeTypeを設定します。
     * 
     * @param customerAttributeType
     *          customerAttributeType
     */
    public void setCustomerAttributeType(String customerAttributeType) {
      this.customerAttributeType = customerAttributeType;
    }

    /**
     * displayOrderを設定します。
     * 
     * @param displayOrder
     *          displayOrder
     */
    public void setDisplayOrder(String displayOrder) {
      this.displayOrder = displayOrder;
    }

    /**
     * customerAttributeTypeNameを取得します。
     * 
     * @return customerAttributeTypeName
     */
    public String getCustomerAttributeTypeName() {
      return customerAttributeTypeName;
    }

    /**
     * customerAttributeTypeNameを設定します。
     * 
     * @param customerAttributeTypeName
     *          customerAttributeTypeName
     */
    public void setCustomerAttributeTypeName(String customerAttributeTypeName) {
      this.customerAttributeTypeName = customerAttributeTypeName;
    }

  }

  /**
   * U1030510:顧客属性のサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class CustomerAttributeChoiceBeanDetail implements Serializable {

    /** serial version uid */
    private static final long serialVersionUID = 1L;

    private String customerAttributeNo;

    private String customerAttributeChoiceNo;

    @Required
    @Length(40)
    @Metadata(name = "顧客属性選択肢名", order = 1)
    private String customerAttributeChoices;

    @Required
    @Digit
    @Length(8)
    @Range(min = 0, max = 99999999)
    @Metadata(name = "顧客属性選択肢表示順", order = 2)
    private String displayOrder;

    private Date updatedDatetime;

    /**
     * CustomerAttributeChoicesを取得します。
     * 
     * @return CustomerAttributeChoices
     */
    public String getCustomerAttributeChoices() {
      return customerAttributeChoices;
    }

    /**
     * customerAttributeChoiceNoを取得します。
     * 
     * @return customerAttributeChoiceNo
     */
    public String getCustomerAttributeChoiceNo() {
      return customerAttributeChoiceNo;
    }

    /**
     * customerAttributeNoを取得します。
     * 
     * @return customerAttributeNo
     */
    public String getCustomerAttributeNo() {
      return customerAttributeNo;
    }

    /**
     * displayOrderを取得します。
     * 
     * @return displayOrder
     */
    public String getDisplayOrder() {
      return displayOrder;
    }

    /**
     * CustomerAttributeChoicesを設定します。
     * 
     * @param customerAttributeChoices
     *          顧客属性選択肢名
     */
    public void setCustomerAttributeChoices(String customerAttributeChoices) {
      this.customerAttributeChoices = customerAttributeChoices;
    }

    /**
     * customerAttributeChoiceNoを設定します。
     * 
     * @param customerAttributeChoiceNo
     *          customerAttributeChoiceNo
     */
    public void setCustomerAttributeChoiceNo(String customerAttributeChoiceNo) {
      this.customerAttributeChoiceNo = customerAttributeChoiceNo;
    }

    /**
     * customerAttributeNoを設定します。
     * 
     * @param customerAttributeNo
     *          customerAttributeNo
     */
    public void setCustomerAttributeNo(String customerAttributeNo) {
      this.customerAttributeNo = customerAttributeNo;
    }

    /**
     * displayOrderを設定します。
     * 
     * @param displayOrder
     *          displayOrder
     */
    public void setDisplayOrder(String displayOrder) {
      this.displayOrder = displayOrder;
    }

    /**
     * updatedDatetimeを取得します。
     * 
     * @return updatedDatetime
     */
    public Date getUpdatedDatetime() {
      return DateUtil.immutableCopy(updatedDatetime);
    }

    /**
     * updatedDatetimeを設定します。
     * 
     * @param updatedDatetime
     *          updatedDatetime
     */
    public void setUpdatedDatetime(Date updatedDatetime) {
      this.updatedDatetime = DateUtil.immutableCopy(updatedDatetime);
    }

  }

  /**
   * attributeChoiceEditを取得します。
   * 
   * @return attributeChoiceEdit
   */
  public CustomerAttributeChoiceBeanDetail getAttributeChoiceEdit() {
    return attributeChoiceEdit;
  }

  /**
   * attributeChoiceListを取得します。
   * 
   * @return attributeChoiceList
   */
  public List<CustomerAttributeChoiceBeanDetail> getAttributeChoiceList() {
    return attributeChoiceList;
  }

  /**
   * attributeEditを取得します。
   * 
   * @return attributeEdit
   */
  public CustomerAttributeBeanDetail getAttributeEdit() {
    return attributeEdit;
  }

  /**
   * attributeListを取得します。
   * 
   * @return attributeList
   */
  public List<CustomerAttributeBeanDetail> getAttributeList() {
    return attributeList;
  }

  /**
   * attributeChoiceEditを設定します。
   * 
   * @param attributeChoiceEdit
   *          attributeChoiceEdit
   */
  public void setAttributeChoiceEdit(CustomerAttributeChoiceBeanDetail attributeChoiceEdit) {
    this.attributeChoiceEdit = attributeChoiceEdit;
  }

  /**
   * attributeChoiceListを設定します。
   * 
   * @param attributeChoiceList
   *          attributeChoiceList
   */
  public void setAttributeChoiceList(List<CustomerAttributeChoiceBeanDetail> attributeChoiceList) {
    this.attributeChoiceList = attributeChoiceList;
  }

  /**
   * attributeEditを設定します。
   * 
   * @param attributeEdit
   *          attributeEdit
   */
  public void setAttributeEdit(CustomerAttributeBeanDetail attributeEdit) {
    this.attributeEdit = attributeEdit;
  }

  /**
   * attributeListを設定します。
   * 
   * @param attributeList
   *          attributeList
   */
  public void setAttributeList(List<CustomerAttributeBeanDetail> attributeList) {
    this.attributeList = attributeList;
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
    attributeEdit.setCustomerAttributeName(reqparam.get("customerAttributeName"));
    attributeEdit.setDisplayOrder(reqparam.get("displayOrder"));
    attributeEdit.setCustomerAttributeType(reqparam.get("customerAttributeType"));
    attributeChoiceEdit.setCustomerAttributeChoices(reqparam.get("customerAttributeChoiceNameEdit"));
    attributeChoiceEdit.setDisplayOrder(reqparam.get("customerAttributeChoiceDisplayOrderEdit"));
  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U1030510";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.back.customer.CustomerAttributeBean.0");
  }

  /**
   * displayChoicesListを取得します。
   * 
   * @return displayChoicesList
   */
  public String getDisplayChoicesList() {
    return displayChoicesList;
  }

  /**
   * displayChoicesListを設定します。
   * 
   * @param displayChoicesList
   *          displayChoicesList
   */
  public void setDisplayChoicesList(String displayChoicesList) {
    this.displayChoicesList = displayChoicesList;
  }

  /**
   * updateButtonDisplayFlgを取得します。
   * 
   * @return updateButtonDisplayFlg
   */
  public String getUpdateButtonDisplayFlg() {
    return updateButtonDisplayFlg;
  }

  /**
   * updateButtonDisplayFlgを設定します。
   * 
   * @param updateButtonDisplayFlg
   *          updateButtonDisplayFlg
   */
  public void setUpdateButtonDisplayFlg(String updateButtonDisplayFlg) {
    this.updateButtonDisplayFlg = updateButtonDisplayFlg;
  }

  /**
   * registerButtonDisplayFlgを取得します。
   * 
   * @return registerButtonDisplayFlg
   */
  public String getRegisterButtonDisplayFlg() {
    return registerButtonDisplayFlg;
  }

  /**
   * registerButtonDisplayFlgを設定します。
   * 
   * @param registerButtonDisplayFlg
   *          registerButtonDisplayFlg
   */
  public void setRegisterButtonDisplayFlg(String registerButtonDisplayFlg) {
    this.registerButtonDisplayFlg = registerButtonDisplayFlg;
  }

  /**
   * deleteModeを取得します。
   * 
   * @return deleteMode
   */
  public boolean isDeleteMode() {
    return deleteMode;
  }

  /**
   * deleteModeを設定します。
   * 
   * @param deleteMode
   *          deleteMode
   */
  public void setDeleteMode(boolean deleteMode) {
    this.deleteMode = deleteMode;
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
   * editModeを取得します。
   * 
   * @return editMode
   */
  public String getEditMode() {
    return editMode;
  }

  /**
   * editModeを設定します。
   * 
   * @param editMode
   *          editMode
   */
  public void setEditMode(String editMode) {
    this.editMode = editMode;
  }
  
  // 10.1.7 10304 追加 ここから
  /**
   * displayDeleteChoiceButtonを取得します。
   * 
   * @return displayDeleteChoiceButton displayDeleteChoiceButton
   */
  public boolean isDisplayDeleteChoiceButton() {
    return displayDeleteChoiceButton;
  }

  /**
   * displayDeleteChoiceButtonを設定します。
   * 
   * @param displayDeleteChoiceButton
   *          displayDeleteChoiceButton
   */
  public void setDisplayDeleteChoiceButton(boolean displayDeleteChoiceButton) {
    this.displayDeleteChoiceButton = displayDeleteChoiceButton;
  }
  // 10.1.7 10304 追加 ここまで

}
