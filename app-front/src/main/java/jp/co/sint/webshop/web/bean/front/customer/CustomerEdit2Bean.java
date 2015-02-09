package jp.co.sint.webshop.web.bean.front.customer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.domain.CustomerAttributeType;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.bean.front.UIFrontBean;
import jp.co.sint.webshop.web.text.front.Messages;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U2030220:お客様情報登録2のデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class CustomerEdit2Bean extends UIFrontBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  /** 顧客コード */
  private String customerCode;

  private List<CustomerAttributeListBean> attributeList = new ArrayList<CustomerAttributeListBean>();

  /**
   * U2030220:お客様情報登録2のサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class CustomerAttributeListBean implements Serializable {

    /** serial version uid */
    private static final long serialVersionUID = 1L;

    /** 顧客属性番号 */
    private String customerAttributeNo;

    /** 顧客属性名称 */
    private String customerAttributeName;

    /** 顧客属性区分 */
    private String customerAttributeType;

    private List<CodeAttribute> attributeChoiceList = new ArrayList<CodeAttribute>();

    /** 顧客属性選択肢番号 */
    @Length(8)
    @Digit
    @Metadata(name = "顧客属性選択肢番号")
    private String attributeAnswer;

    /** 顧客属性選択肢回答リスト表示用 */
    private List<String> attributeAnswerItem = new ArrayList<String>();

    /** 顧客属性選択肢回答リスト */
    private List<CustomerAttributeListAnswerBean> attributeAnswerList = new ArrayList<CustomerAttributeListAnswerBean>();

    /** 顧客属性入力タグ名 */
    private String attributeTextName;

    /**
     * attributeAnswerを取得します。
     * 
     * @return attributeAnswer
     */
    public String getAttributeAnswer() {
      return attributeAnswer;
    }

    /**
     * attributeAnswerを設定します。
     * 
     * @param attributeAnswer
     *          attributeAnswer
     */
    public void setAttributeAnswer(String attributeAnswer) {
      this.attributeAnswer = attributeAnswer;
    }

    /**
     * attributeChoiceListを取得します。
     * 
     * @return attributeChoiceList
     */
    public List<CodeAttribute> getAttributeChoiceList() {
      return attributeChoiceList;
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
     * attributeChoiceListを設定します。
     * 
     * @param attributeChoiceList
     *          attributeChoiceList
     */
    public void setAttributeChoiceList(List<CodeAttribute> attributeChoiceList) {
      this.attributeChoiceList = attributeChoiceList;
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
     * attributeTextNameを取得します。
     * 
     * @return attributeTextName
     */
    public String getAttributeTextName() {
      return attributeTextName;
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
     * attributeTextNameを設定します。
     * 
     * @param attributeTextName
     *          attributeTextName
     */
    public void setAttributeTextName(String attributeTextName) {
      this.attributeTextName = attributeTextName;
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
     * attributeAnswerListを取得します。
     * 
     * @return attributeAnswerList
     */
    public List<CustomerAttributeListAnswerBean> getAttributeAnswerList() {
      return attributeAnswerList;
    }

    /**
     * attributeAnswerListを設定します。
     * 
     * @param attributeAnswerList
     *          attributeAnswerList
     */
    public void setAttributeAnswerList(List<CustomerAttributeListAnswerBean> attributeAnswerList) {
      this.attributeAnswerList = attributeAnswerList;
    }

    /**
     * attributeAnswerItemを取得します。
     * 
     * @return attributeAnswerItem
     */
    public List<String> getAttributeAnswerItem() {
      return attributeAnswerItem;
    }

    /**
     * attributeAnswerItemを設定します。
     * 
     * @param attributeAnswerItem
     *          attributeAnswerItem
     */
    public void setAttributeAnswerItem(List<String> attributeAnswerItem) {
      this.attributeAnswerItem = attributeAnswerItem;
    }

  }

  /**
   * U2030220:お客様情報登録2のサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class CustomerAttributeListAnswerBean implements Serializable {

    /** serial version uid */
    private static final long serialVersionUID = 1L;

    /** 顧客属性選択肢回答(リスト) */
    @Length(8)
    @Digit
    @Metadata(name = "顧客属性選択肢番号")
    private String attributeAnswerList;

    /**
     * attributeAnswerListを取得します。
     * 
     * @return attributeAnswerList
     */
    public String getAttributeAnswerList() {
      return attributeAnswerList;
    }

    /**
     * attributeAnswerListを設定します。
     * 
     * @param attributeAnswerList
     *          attributeAnswerList
     */
    public void setAttributeAnswerList(String attributeAnswerList) {
      this.attributeAnswerList = attributeAnswerList;
    }

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

    // 顧客属性を設定
    setAttributeListParam(reqparam);

  }

  /**
   * 顧客属性を表示します。
   * 
   * @param bean
   * @param reqparam
   */
  private void setAttributeListParam(RequestParameter reqparam) {

    for (CustomerAttributeListBean ca : getAttributeList()) {
      if (ca.getCustomerAttributeType().equals(CustomerAttributeType.RADIO.getValue())) {
        // 単一選択の場合
        ca.setAttributeAnswer(reqparam.get(ca.getAttributeTextName()));
      } else {
        // 複数選択の場合
        List<String> answerItem = new ArrayList<String>();
        List<CustomerAttributeListAnswerBean> answerList = new ArrayList<CustomerAttributeListAnswerBean>();

        String[] paramList = reqparam.getAll(ca.getAttributeTextName());
        if (StringUtil.hasValueAllOf(paramList)) {
          for (String caa : paramList) {
            CustomerAttributeListAnswerBean answerEdit = new CustomerAttributeListAnswerBean();
            answerEdit.setAttributeAnswerList(caa);
            answerList.add(answerEdit);
            answerItem.add(caa);
          }
        }
        ca.setAttributeAnswerList(answerList);
        ca.setAttributeAnswerItem(answerItem); // リスト表示用
      }
    }
    setAttributeList(getAttributeList());
  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U2030220";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.front.customer.CustomerEdit2Bean.0");
  }

  /**
   * customerCodeを取得します。
   * 
   * @return customerCode
   */
  public String getCustomerCode() {
    return customerCode;
  }

  /**
   * customerCodeを設定します。
   * 
   * @param customerCode
   *          customerCode
   */
  public void setCustomerCode(String customerCode) {
    this.customerCode = customerCode;
  }

  /**
   * attributeListを取得します。
   * 
   * @return attributeList
   */
  public List<CustomerAttributeListBean> getAttributeList() {
    return attributeList;
  }

  /**
   * attributeListを設定します。
   * 
   * @param attributeList
   *          attributeList
   */
  public void setAttributeList(List<CustomerAttributeListBean> attributeList) {
    this.attributeList = attributeList;
  }

}
