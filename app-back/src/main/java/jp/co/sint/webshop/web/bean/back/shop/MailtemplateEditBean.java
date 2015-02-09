package jp.co.sint.webshop.web.bean.back.shop;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Email;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.mail.MailTemplateTag;
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
public class MailtemplateEditBean extends UIBackBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  /** 情報メール種別選択項目一覧 */
  private List<CodeAttribute> informationMailList = new ArrayList<CodeAttribute>();

  /** その他メール種別選択項目一覧 */
  private List<CodeAttribute> mailTypeList = new ArrayList<CodeAttribute>();

  /** メールがHTMLかTextか判断するもの */
  private List<CodeAttribute> mailContentType = new ArrayList<CodeAttribute>();

  /** システム:情報メールの区分値 */
  private List<NameValue> templateDivList = NameValue.asList("0:"
      + Messages.getString("web.bean.back.shop.MailtemplateEditBean.1")
      + "/1:"
      + Messages.getString("web.bean.back.shop.MailtemplateEditBean.2"));

  /** テンプレート区分（システム:情報メール） */
  private String templateDiv;

  /** メールタイプ(選択値) */
  @Required
  @Digit
  private String mailType;

  /** メールタイプ名 */
  private String mailTypeName;

  /** メール構成 */
  private String mailComposition;

  @Length(20)
  @Metadata(name = "メール名", order = 1)
  private String newMailTypeName;

  private List<MailCompositionDetail> mailDetailList = new ArrayList<MailCompositionDetail>();

  @Required
  @Length(100)
  @Metadata(name = "件名", order = 1)
  private String subject;

  @Required
  @Metadata(name = "HTML/TEXT", order = 2)
  private String contentType;

  @Length(50)
  @Metadata(name = "送信者名", order = 3)
  private String senderName;

  @Required
  @Length(256)
  @Email
  @Metadata(name = "送信者アドレス", order = 4)
  private String fromAddress;

  @Length(256)
  @Email
  @Metadata(name = "BCCアドレス", order = 5)
  private String bccAddress;

  private String previewText;

  private String modeDiv;

  private boolean displayInsertButton;

  private boolean displayDeleteButton;

  private boolean displayRegisterButton;

  private boolean displayPreviewButton;

  private boolean displayPreviewRegisterButton;

  private Date updateDatetime;

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

    String templateDivTmp = reqparam.get("templateDiv");
    this.setTemplateDiv(StringUtil.coalesceEmptyValue(templateDivTmp, this.getTemplateDiv()));
    String mailTypeTmp = "";
    if (this.getTemplateDiv() == null) {
      mailTypeTmp = "";
    } else if (this.getTemplateDiv().equals("1")) {
      mailTypeTmp = reqparam.get("informationMailType");
    } else if (this.getTemplateDiv().equals("0")) {
      mailTypeTmp = reqparam.get("mailType");
    }
    this.setMailType(StringUtil.coalesceEmptyValue(mailTypeTmp, this.getMailType()));

    for (MailCompositionDetail detail : this.getMailDetailList()) {
      Map<String, String> map = reqparam.getListDataWithKey(detail.getBranchNo(), "branchNo", "mailText");
      detail.setMailText(map.get("mailText"));
    }
  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U1051110";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.back.shop.MailtemplateEditBean.0");
  }

  /**
   * U1051110:メールテンプレートのサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class MailCompositionDetail implements Serializable {

    /** serial version uid */
    private static final long serialVersionUID = 1L;

    @Digit
    @Metadata(name = "メールテンプレート枝番号", order = 1)
    private String branchNo;

    @Digit
    @Metadata(name = "メールテンプレート親枝番号", order = 1)
    private String parentBranchNo;

    @Required
    @Length(10000)
    @Metadata(name = "メールテンプレート本文", order = 1)
    private String mailText;

    private String mailCompositionName;

    private String templateTag;

    private Long mailTemplateDepth;

    private List<MailTag> mailTagList = new ArrayList<MailTag>();

    private Date updateDatetime;

    /**
     * branchNoを取得します。
     * 
     * @return branchNo
     */
    public String getBranchNo() {
      return branchNo;
    }

    /**
     * mailTextを取得します。
     * 
     * @return mailText
     */
    public String getMailText() {
      return mailText;
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
     * typeNameを取得します。
     * 
     * @return typeName
     */
    public String getMailCompositionName() {
      return mailCompositionName;
    }

    /**
     * branchNoを設定します。
     * 
     * @param branchNo
     *          branchNo
     */
    public void setBranchNo(String branchNo) {
      this.branchNo = branchNo;
    }

    /**
     * mailTextを設定します。
     * 
     * @param mailText
     *          mailText
     */
    public void setMailText(String mailText) {
      this.mailText = mailText;
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
     * typeNameを設定します。
     * 
     * @param typeName
     *          typeName
     */
    public void setMailCompositionName(String typeName) {
      this.mailCompositionName = typeName;
    }

    /**
     * mailTagListを取得します。
     * 
     * @return mailTagList
     */
    public List<MailTag> getMailTagList() {
      return mailTagList;
    }

    /**
     * mailTagListを設定します。
     * 
     * @param mailTagList
     *          mailTagList
     */
    public void setMailTagList(List<MailTag> mailTagList) {
      this.mailTagList = mailTagList;
    }

    /**
     * mailTemplateDepthを取得します。
     * 
     * @return mailTemplateDepth
     */
    public Long getMailTemplateDepth() {
      return mailTemplateDepth;
    }

    /**
     * mailTemplateDepthを設定します。
     * 
     * @param mailTemplateDepth
     *          mailTemplateDepth
     */
    public void setMailTemplateDepth(Long mailTemplateDepth) {
      this.mailTemplateDepth = mailTemplateDepth;
    }

    /**
     * parentBranchNoを取得します。
     * 
     * @return parentBranchNo
     */
    public String getParentBranchNo() {
      return parentBranchNo;
    }

    /**
     * parentBranchNoを設定します。
     * 
     * @param parentBranchNo
     *          parentBranchNo
     */
    public void setParentBranchNo(String parentBranchNo) {
      this.parentBranchNo = parentBranchNo;
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
   * mailDetailListを取得します。
   * 
   * @return mailDetailList
   */
  public List<MailCompositionDetail> getMailDetailList() {
    return mailDetailList;
  }

  /**
   * mailDetailListを設定します。
   * 
   * @param mailDetailList
   *          mailDetailList
   */
  public void setMailDetailList(List<MailCompositionDetail> mailDetailList) {
    this.mailDetailList = mailDetailList;
  }

  /**
   * informationMailListを取得します。
   * 
   * @return informationMailList
   */
  public List<CodeAttribute> getInformationMailList() {
    return informationMailList;
  }

  /**
   * informationMailListを設定します。
   * 
   * @param informationMailList
   *          informationMailList
   */
  public void setInformationMailList(List<CodeAttribute> informationMailList) {
    this.informationMailList = informationMailList;
  }

  /**
   * mailTypeを取得します。
   * 
   * @return mailType
   */
  public String getMailType() {
    return mailType;
  }

  /**
   * mailTypeを設定します。
   * 
   * @param mailType
   *          mailType
   */
  public void setMailType(String mailType) {
    this.mailType = mailType;
  }

  /**
   * mailTypeListを取得します。
   * 
   * @return mailTypeList
   */
  public List<CodeAttribute> getMailTypeList() {
    return mailTypeList;
  }

  /**
   * mailTypeListを設定します。
   * 
   * @param mailTypeList
   *          mailTypeList
   */
  public void setMailTypeList(List<CodeAttribute> mailTypeList) {
    this.mailTypeList = mailTypeList;
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
   * newMailTypeNameを取得します。
   * 
   * @return newMailTypeName
   */
  public String getNewMailTypeName() {
    return newMailTypeName;
  }

  /**
   * newMailTypeNameを設定します。
   * 
   * @param newMailTypeName
   *          newMailTypeName
   */
  public void setNewMailTypeName(String newMailTypeName) {
    this.newMailTypeName = newMailTypeName;
  }

  /**
   * subjectを取得します。
   * 
   * @return subject
   */
  public String getSubject() {
    return subject;
  }

  /**
   * subjectを設定します。
   * 
   * @param subject
   *          subject
   */
  public void setSubject(String subject) {
    this.subject = subject;
  }

  /**
   * templateDivを取得します。
   * 
   * @return templateDiv
   */
  public String getTemplateDiv() {
    return templateDiv;
  }

  /**
   * templateDivを設定します。
   * 
   * @param templateDiv
   *          templateDiv
   */
  public void setTemplateDiv(String templateDiv) {
    this.templateDiv = templateDiv;
  }

  /**
   * mailCompositionを取得します。
   * 
   * @return mailComposition
   */
  public String getMailComposition() {
    return mailComposition;
  }

  /**
   * mailCompositionを設定します。
   * 
   * @param mailComposition
   *          mailComposition
   */
  public void setMailComposition(String mailComposition) {
    this.mailComposition = mailComposition;
  }

  /**
   * templateDivListを取得します。
   * 
   * @return templateDivList
   */
  public List<NameValue> getTemplateDivList() {
    return templateDivList;
  }

  /**
   * templateDivListを設定します。
   * 
   * @param templateDivList
   *          templateDivList
   */
  public void setTemplateDivList(List<NameValue> templateDivList) {
    this.templateDivList = templateDivList;
  }

  /**
   * mailTypeNameを取得します。
   * 
   * @return mailTypeName
   */
  public String getMailTypeName() {
    return mailTypeName;
  }

  /**
   * mailTypeNameを設定します。
   * 
   * @param mailTypeName
   *          mailTypeName
   */
  public void setMailTypeName(String mailTypeName) {
    this.mailTypeName = mailTypeName;
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
   * contentTypeを取得します。
   * 
   * @return contentType
   */
  public String getContentType() {
    return contentType;
  }

  /**
   * contentTypeを設定します。
   * 
   * @param contentType
   *          contentType
   */
  public void setContentType(String contentType) {
    this.contentType = contentType;
  }

  /**
   * fromAddressを取得します。
   * 
   * @return fromAddress
   */
  public String getFromAddress() {
    return fromAddress;
  }

  /**
   * fromAddressを設定します。
   * 
   * @param fromAddress
   *          fromAddress
   */
  public void setFromAddress(String fromAddress) {
    this.fromAddress = fromAddress;
  }

  /**
   * mailContentTypeを取得します。
   * 
   * @return mailContentType
   */
  public List<CodeAttribute> getMailContentType() {
    return mailContentType;
  }

  /**
   * mailContentTypeを設定します。
   * 
   * @param mailContentType
   *          mailContentType
   */
  public void setMailContentType(List<CodeAttribute> mailContentType) {
    this.mailContentType = mailContentType;
  }

  /**
   * bccAddressを取得します。
   * 
   * @return bccAddress
   */
  public String getBccAddress() {
    return bccAddress;
  }

  /**
   * bccAddressを設定します。
   * 
   * @param bccAddress
   *          bccAddress
   */
  public void setBccAddress(String bccAddress) {
    this.bccAddress = bccAddress;
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

  public static class MailTag implements MailTemplateTag, Serializable {

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
}
