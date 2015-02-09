package jp.co.sint.webshop.web.bean.back.shop;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.sms.SmsTemplateTag;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U1051110:メールテンプレートのデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class SmstemplateEditBean extends UIBackBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private List<CodeAttribute> smsTypeList = new ArrayList<CodeAttribute>();

  private List<SmsCompositionDetail> smsDetailList = new ArrayList<SmsCompositionDetail>();

  /** システム:情報メールの区分値 */
  private List<NameValue> smsUseFlgDivList = NameValue.asList("1:" + Messages.getString("web.bean.back.shop.SmstemplateEditBean.1")
      + "/2:" + Messages.getString("web.bean.back.shop.SmstemplateEditBean.2"));

  /** テンプレート区分（システム:情報メール） */
  private String smsUseFlgDiv;

  @Required
  @Digit
  private String smsType;

  private String smsTypeName;

  @Length(50)
  @Metadata(name = "送信者名", order = 3)
  private String senderName;

  private String previewText;

  private String modeDiv;

  private boolean displayInsertButton;

  private boolean displayDeleteButton;

  private boolean displayRegisterButton;

  private boolean displayPreviewButton;

  private boolean displayPreviewRegisterButton;

  private Date updateDatetime;
  
  private String smsText;

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

    reqparam.copy(this);

    String smsUseFlgDivTmp = reqparam.get("smsUseFlgDiv");
    this.setSmsUseFlgDiv(StringUtil.coalesceEmptyValue(smsUseFlgDivTmp, this.getSmsUseFlgDiv()));
    String smsTypeTmp = "";
    if (StringUtil.isNull(this.getSmsUseFlgDiv())) {
      smsTypeTmp = reqparam.get("smsType");
    }
    this.setSmsType(StringUtil.coalesceEmptyValue(smsTypeTmp, this.getSmsType()));

    for (SmsCompositionDetail detail : this.getSmsDetailList()) {
      detail.setSmsText(reqparam.get("smsText"));
    }
  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U1051030";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.back.shop.SmstemplateEditBean.0");
  }

  /**
   * U1051110:メールテンプレートのサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class SmsCompositionDetail implements Serializable {

    /** serial version uid */
    private static final long serialVersionUID = 1L;

    @Required
    @Length(10000)
    @Metadata(name = "メールテンプレート本文", order = 1)
    private String smsText;

    private String templateTag;

    private List<SmsTag> smsTagList = new ArrayList<SmsTag>();

    private Long smsUseFlg;

    private Date updateDatetime;

    private String smsCompositionName;

    private String smsType;

    private String updateUser;

    private String createdUser;

    private Date createdDatetime;

    private int branchNo;

    /**
     * smsTypeを取得します。
     * 
     * @return smsType smsType
     */
    public String getSmsType() {
      return smsType;
    }

    /**
     * smsTypeを設定します。
     * 
     * @param smsType
     *          smsType
     */
    public void setSmsType(String smsType) {
      this.smsType = smsType;
    }

    /**
     * smsCompositionNameを取得します。
     * 
     * @return smsCompositionName smsCompositionName
     */
    public String getSmsCompositionName() {
      return smsCompositionName;
    }

    /**
     * smsCompositionNameを設定します。
     * 
     * @param smsCompositionName
     *          smsCompositionName
     */
    public void setSmsCompositionName(String smsCompositionName) {
      this.smsCompositionName = smsCompositionName;
    }

    /**
     * smsTextを取得します。
     * 
     * @return smsText
     */
    public String getSmsText() {
      return smsText;
    }

    /**
     * templateTagを取得します。
     * 
     * @return templateTag
     */
    public String getTemplateTag() {
      return templateTag;
    }

    /**
     * smsTextを設定します。
     * 
     * @param smsText
     *          smsText
     */
    public void setSmsText(String smsText) {
      this.smsText = smsText;
    }

    /**
     * templateTagを設定します。
     * 
     * @param templateTag
     *          templateTag
     */
    public void setTemplateTag(String templateTag) {
      this.templateTag = templateTag;
    }

    /**
     * smsTagListを取得します。
     * 
     * @return smsTagList
     */
    public List<SmsTag> getSmsTagList() {
      return smsTagList;
    }

    /**
     * smsTagListを設定します。
     * 
     * @param smsTagList
     *          smsTagList
     */
    public void setSmsTagList(List<SmsTag> smsTagList) {
      this.smsTagList = smsTagList;
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
     * smsUseFlgを取得します。
     * 
     * @return smsUseFlg smsUseFlg
     */
    public Long getSmsUseFlg() {
      return smsUseFlg;
    }

    /**
     * smsUseFlgを設定します。
     * 
     * @param smsUseFlg
     *          smsUseFlg
     */
    public void setSmsUseFlg(Long smsUseFlg) {
      this.smsUseFlg = smsUseFlg;
    }

    /**
     * createdDatetimeを取得します。
     * 
     * @return createdDatetime createdDatetime
     */
    public Date getCreatedDatetime() {
      return createdDatetime;
    }

    /**
     * createdDatetimeを設定します。
     * 
     * @param createdDatetime
     *          createdDatetime
     */
    public void setCreatedDatetime(Date createdDatetime) {
      this.createdDatetime = createdDatetime;
    }

    /**
     * createdUserを取得します。
     * 
     * @return createdUser createdUser
     */
    public String getCreatedUser() {
      return createdUser;
    }

    /**
     * createdUserを設定します。
     * 
     * @param createdUser
     *          createdUser
     */
    public void setCreatedUser(String createdUser) {
      this.createdUser = createdUser;
    }

    /**
     * updateUserを取得します。
     * 
     * @return updateUser updateUser
     */
    public String getUpdateUser() {
      return updateUser;
    }

    /**
     * updateUserを設定します。
     * 
     * @param updateUser
     *          updateUser
     */
    public void setUpdateUser(String updateUser) {
      this.updateUser = updateUser;
    }

    public int getBranchNo() {
      return branchNo;
    }

    public void setBranchNo(int branchNo) {
      this.branchNo = branchNo;
    }

  }

  /**
   * smsTypeを取得します。
   * 
   * @return smsType
   */
  public String getSmsType() {
    return smsType;
  }

  /**
   * smsTypeを設定します。
   * 
   * @param smsType
   *          smsType
   */
  public void setSmsType(String smsType) {
    this.smsType = smsType;
  }

  /**
   * smsTypeListを取得します。
   * 
   * @return smsTypeList
   */
  public List<CodeAttribute> getSmsTypeList() {
    return smsTypeList;
  }

  /**
   * smsTypeListを設定します。
   * 
   * @param smsTypeList
   *          smsTypeList
   */
  public void setSmsTypeList(List<CodeAttribute> smsTypeList) {
    this.smsTypeList = smsTypeList;
  }

  /**
   * modeDivを取得します。
   * 
   * @return modeDiv
   */
  public String getModeDiv() {
    return modeDiv;
  }

  /**
   * modeDivを設定します。
   * 
   * @param modeDiv
   *          modeDiv
   */
  public void setModeDiv(String modeDiv) {
    this.modeDiv = modeDiv;
  }

  /**
   * smsTypeNameを取得します。
   * 
   * @return smsTypeName
   */
  public String getSmsTypeName() {
    return smsTypeName;
  }

  /**
   * smsTypeNameを設定します。
   * 
   * @ram smsTypeName smsTypeName
   */
  public void setSmsTypeName(String smsTypeName) {
    this.smsTypeName = smsTypeName;
  }

  /**
   * previewTextを取得します。
   * 
   * @return previewText
   */
  public String getPreviewText() {
    return previewText;
  }

  /**
   * previewTextを設定します。
   * 
   * @param previewText
   *          previewText
   */
  public void setPreviewText(String previewText) {
    this.previewText = previewText;
  }

  /**
   * displayDeleteButtonを取得します。
   * 
   * @return displayDeleteButton
   */
  public boolean isDisplayDeleteButton() {
    return displayDeleteButton;
  }

  /**
   * displayDeleteButtonを設定します。
   * 
   * @param displayDeleteButton
   *          displayDeleteButton
   */
  public void setDisplayDeleteButton(boolean displayDeleteButton) {
    this.displayDeleteButton = displayDeleteButton;
  }

  /**
   * displayInsertButtonを取得します。
   * 
   * @return displayInsertButton
   */
  public boolean isDisplayInsertButton() {
    return displayInsertButton;
  }

  /**
   * displayInsertButtonを設定します。
   * 
   * @param displayInsertButton
   *          displayInsertButton
   */
  public void setDisplayInsertButton(boolean displayInsertButton) {
    this.displayInsertButton = displayInsertButton;
  }

  /**
   * displayRegisterButtonを取得します。
   * 
   * @return displayRegisterButton
   */
  public boolean isDisplayRegisterButton() {
    return displayRegisterButton;
  }

  /**
   * displayRegisterButtonを設定します。
   * 
   * @param displayRegisterButton
   *          displayRegisterButton
   */
  public void setDisplayRegisterButton(boolean displayRegisterButton) {
    this.displayRegisterButton = displayRegisterButton;
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
   * senderNameを取得します。
   * 
   * @return senderName
   */
  public String getSenderName() {
    return senderName;
  }

  /**
   * senderNameを設定します。
   * 
   * @param senderName
   *          senderName
   */
  public void setSenderName(String senderName) {
    this.senderName = senderName;
  }

  /**
   * displayPreviewRegisterButtonを取得します。
   * 
   * @return displayPreviewRegisterButton
   */
  public boolean getDisplayPreviewRegisterButton() {
    return displayPreviewRegisterButton;
  }

  /**
   * displayPreviewRegisterButtonを設定します。
   * 
   * @param displayPreviewRegisterButton
   *          displayPreviewRegisterButton
   */
  public void setDisplayPreviewRegisterButton(boolean displayPreviewRegisterButton) {
    this.displayPreviewRegisterButton = displayPreviewRegisterButton;
  }

  public static class SmsTag implements SmsTemplateTag, Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String name;

    private String value;

    private boolean required;

    private String tagDiv;

    private String dummyData;

    /**
     * dummyDataを取得します。
     * 
     * @return dummyData
     */
    public String getDummyData() {
      return dummyData;
    }

    /**
     * dummyDataを設定します。
     * 
     * @param dummyData
     *          dummyData
     */
    public void setDummyData(String dummyData) {
      this.dummyData = dummyData;
    }

    /**
     * tagDivを取得します。
     * 
     * @return tagDiv
     */
    public String getTagDiv() {
      return tagDiv;
    }

    /**
     * tagDivを設定します。
     * 
     * @param tagDiv
     *          tagDiv
     */
    public void setTagDiv(String tagDiv) {
      this.tagDiv = tagDiv;
    }

    /**
     * nameを取得します。
     * 
     * @return name
     */
    public String getName() {
      return name;
    }

    /**
     * nameを設定します。
     * 
     * @param name
     *          name
     */
    public void setName(String name) {
      this.name = name;
    }

    /**
     * requiredを取得します。
     * 
     * @return required
     */
    public boolean isRequired() {
      return required;
    }

    /**
     * requiredを設定します。
     * 
     * @param required
     *          required
     */
    public void setRequired(boolean required) {
      this.required = required;
    }

    /**
     * valueを取得します。
     * 
     * @return value
     */
    public String getValue() {
      return value;
    }

    /**
     * valueを設定します。
     * 
     * @param value
     *          value
     */
    public void setValue(String value) {
      this.value = value;
    }

  }

  /**
   * displayPreviewButtonを取得します。
   * 
   * @return displayPreviewButton
   */
  public boolean isDisplayPreviewButton() {
    return displayPreviewButton;
  }

  /**
   * displayPreviewButtonを設定します。
   * 
   * @param displayPreviewButton
   *          displayPreviewButton
   */
  public void setDisplayPreviewButton(boolean displayPreviewButton) {
    this.displayPreviewButton = displayPreviewButton;
  }

  /**
   * smsUseFlgを取得します。
   * 
   * @return smsUseFlg smsUseFlg
   */
  public String getSmsUseFlgDiv() {
    return smsUseFlgDiv;
  }

  /**
   * smsUseFlgを設定します。
   * 
   * @param smsUseFlg
   *          smsUseFlg
   */
  public void setSmsUseFlgDiv(String smsUseFlgDiv) {
    this.smsUseFlgDiv = smsUseFlgDiv;
  }

  /**
   * smsDetailListを取得します。
   * 
   * @return smsDetailList smsDetailList
   */
  public List<SmsCompositionDetail> getSmsDetailList() {
    return smsDetailList;
  }

  /**
   * smsDetailListを設定します。
   * 
   * @param smsDetailList
   *          smsDetailList
   */
  public void setSmsDetailList(List<SmsCompositionDetail> smsDetailList) {
    this.smsDetailList = smsDetailList;
  }

  /**
   * smsUseFlgDivListを取得します。
   * 
   * @return smsUseFlgDivList smsUseFlgDivList
   */
  public List<NameValue> getSmsUseFlgDivList() {
    return smsUseFlgDivList;
  }

  /**
   * smsUseFlgDivListを設定します。
   * 
   * @param smsUseFlgDivList
   *          smsUseFlgDivList
   */
  public void setSmsUseFlgDivList(List<NameValue> smsUseFlgDivList) {
    this.smsUseFlgDivList = smsUseFlgDivList;
  }

  
  public String getSmsText() {
    return smsText;
  }

  
  public void setSmsText(String smsText) {
    this.smsText = smsText;
  }
}
