package jp.co.sint.webshop.web.bean.back.customer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Email;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U1030610:情報メール送信のデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class InformationSendBean extends UIBackBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  /** メールタイプ */
  @Required
  @Digit
  @Metadata(name = "テンプレート名称")
  private String templateCode;

  /** テンプレート名称 */
  private List<CodeAttribute> templateList = new ArrayList<CodeAttribute>();

  /** メール件名 */
  @Required
  @Length(100)
  @Metadata(name = "メール件名")
  private String subject;

  /** TEXT / HTML区分(コード) */
  @Required
  @Metadata(name = "TEXT/HTML")
  private String contentType;

  /** 送信者名 */
  @Length(50)
  @Metadata(name = "送信者名")
  private String senderName;

  /** 送信者アドレス */
  @Required
  @Length(256)
  @Email
  @Metadata(name = "送信者アドレス")
  private String fromAddress;

  /** BCCアドレス */
  @Length(256)
  @Email
  @Metadata(name = "BCCアドレス")
  private String bccAddress;

  /** メールテンプレート詳細 */
  private List<MailDetail> mailDetailList = new ArrayList<MailDetail>();
  
  /** メール構成 */
  private String mailComposition;
  
  private String previewText;

  private String modeDiv;

  /** 編集モード */
  private String editMode;

  /** 送信ボタン表示フラグ */
  private boolean displaySendButtonFlg;

  /** 次へボタン表示フラグ */
  private boolean displayNextButtonFlg;

  /** 戻るボタン表示フラグ */
  private boolean displayBackButtonFlg;

  /** メールテンプレートリンク表示フラグ */
  private boolean displayMailTemplateLinkFlg;

  /** メールテンプレート文言表示フラグ */
  private boolean displayMailTemplateCautionFlg;
  
  /** 更新日時 */
  private Date updatedDatetime;

  /** 顧客コードfrom(検索) */
  private String searchCustomerFrom;

  /** 顧客コードto(検索) */
  private String searchCustomerTo;

  /** 顧客グループコード(検索) */
  private String searchCustomerGroupCode;

  /** 顧客名(検索) */
  private String searchCustomerName;

  /** 顧客名カナ(検索) */
  private String searchCustomerNameKana;

  /** メールアドレス(検索) */
  private String searchEmail;


  //Add by V10-CH start
  private boolean displaySmsTemplateCautionFlg;
  
  private boolean displaySmsTemplateLinkFlg;
  
  private String smsComposition;
  
  private List<SmsDetail> smsDetailList = new ArrayList<SmsDetail>();
  //Add by V10-CH end
  /** 電話番号(検索) */
  @Digit
  @Length(18)
  @Metadata(name = "电话号码")
  private String searchTel;
  
  //Add by V10-CH start
  /** 手機號碼(檢索) */
  @Digit
  @Length(11)
  @Metadata(name = "手机号码")
  private String searchMobile;
  //Add by V10-CH end
  
  /** 希望メール区分(検索) */
  private String searchRequestMailType;

  /** クライアントメール区分(検索) */
  private String searchClientMailType;

  /**
   * U1030610:メールテンプレートのサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class MailDetail implements Serializable {

    /** serial version uid */
    private static final long serialVersionUID = 1L;

    @Digit
    @Metadata(name = "メールテンプレート枝番号")
    private String branchNo;

    @Digit
    @Metadata(name = "メールテンプレート親枝番号")
    private String parentBranchNo;

    @Required
    @Length(10000)
    @Metadata(name = "本文")
    private String mailText;

    private String mailCompositionName;

    private String templateTag;

    private Long mailTemplateDepth;

    private List<CodeAttribute> mailTagList = new ArrayList<CodeAttribute>();

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
    public List<CodeAttribute> getMailTagList() {
      return mailTagList;
    }

    /**
     * mailTagListを設定します。
     * 
     * @param mailTagList
     *          mailTagList
     */
    public void setMailTagList(List<CodeAttribute> mailTagList) {
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
  
  //Add by V10-CH start
  public static class SmsDetail implements Serializable{


    /** serial version uid */
    private static final long serialVersionUID = 1L;



    @Required
    @Length(10000)
    @Metadata(name = "本文")
    private String smsText;

    private String smsCompositionName;

    private String templateTag;

    private Long smsTemplateDepth;

    private List<CodeAttribute> smsTagList = new ArrayList<CodeAttribute>();

    private Date updateDatetime;

    private Long smsUseFlg;

    
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
     * mailTextを取得します。
     * 
     * @return mailText
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
     * typeNameを取得します。
     * 
     * @return typeName
     */
    public String getSmsCompositionName() {
      return smsCompositionName;
    }


    /**
     * mailTextを設定します。
     * 
     * @param mailText
     *          mailText
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
     * typeNameを設定します。
     * 
     * @param typeName
     *          typeName
     */
    public void setSmsCompositionName(String typeName) {
      this.smsCompositionName = typeName;
    }

    /**
     * mailTagListを取得します。
     * 
     * @return mailTagList
     */
    public List<CodeAttribute> getSmsTagList() {
      return smsTagList;
    }

    /**
     * mailTagListを設定します。
     * 
     * @param mailTagList
     *          mailTagList
     */
    public void setSmsTagList(List<CodeAttribute> smsTagList) {
      this.smsTagList = smsTagList;
    }

    /**
     * mailTemplateDepthを取得します。
     * 
     * @return mailTemplateDepth
     */
    public Long getSmsTemplateDepth() {
      return smsTemplateDepth;
    }

    /**
     * mailTemplateDepthを設定します。
     * 
     * @param mailTemplateDepth
     *          mailTemplateDepth
     */
    public void setSmsTemplateDepth(Long smsTemplateDepth) {
      this.smsTemplateDepth = smsTemplateDepth;
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
  //Add by V10-CH end

  /**
   * searchClientMailTypeを取得します。
   * 
   * @return searchClientMailType
   */
  public String getSearchClientMailType() {
    return searchClientMailType;
  }

  /**
   * searchClientMailTypeを設定します。
   * 
   * @param searchClientMailType
   *          searchClientMailType
   */
  public void setSearchClientMailType(String searchClientMailType) {
    this.searchClientMailType = searchClientMailType;
  }

  /**
   * displayMailTemplateCautionFlgを取得します。
   * 
   * @return displayMailTemplateCautionFlg
   */
  public boolean isDisplayMailTemplateCautionFlg() {
    return displayMailTemplateCautionFlg;
  }

  /**
   * displayMailTemplateCautionFlgを設定します。
   * 
   * @param displayMailTemplateCautionFlg
   *          displayMailTemplateCautionFlg
   */
  public void setDisplayMailTemplateCautionFlg(boolean displayMailTemplateCautionFlg) {
    this.displayMailTemplateCautionFlg = displayMailTemplateCautionFlg;
  }

  /**
   * displayMailTemplateLinkFlgを取得します。
   * 
   * @return displayMailTemplateLinkFlg
   */
  public boolean isDisplayMailTemplateLinkFlg() {
    return displayMailTemplateLinkFlg;
  }

  /**
   * displayMailTemplateLinkFlgを設定します。
   * 
   * @param displayMailTemplateLinkFlg
   *          displayMailTemplateLinkFlg
   */
  public void setDisplayMailTemplateLinkFlg(boolean displayMailTemplateLinkFlg) {
    this.displayMailTemplateLinkFlg = displayMailTemplateLinkFlg;
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
   * displayNextButtonFlgを取得します。
   * 
   * @return displayNextButtonFlg
   */
  public boolean isDisplayNextButtonFlg() {
    return displayNextButtonFlg;
  }

  /**
   * displayNextButtonFlgを設定します。
   * 
   * @param displayNextButtonFlg
   *          displayNextButtonFlg
   */
  public void setDisplayNextButtonFlg(boolean displayNextButtonFlg) {
    this.displayNextButtonFlg = displayNextButtonFlg;
  }

  /**
   * displaySendButtonFlgを取得します。
   * 
   * @return displaySendButtonFlg
   */
  public boolean isDisplaySendButtonFlg() {
    return displaySendButtonFlg;
  }

  /**
   * displaySendButtonFlgを設定します。
   * 
   * @param displaySendButtonFlg
   *          displaySendButtonFlg
   */
  public void setDisplaySendButtonFlg(boolean displaySendButtonFlg) {
    this.displaySendButtonFlg = displaySendButtonFlg;
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
    for (MailDetail detail : this.getMailDetailList()) {
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
    return "U1030610";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.back.customer.InformationSendBean.0");
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

  /**
   * displayBackButtonFlgを取得します。
   * 
   * @return displayBackButtonFlg
   */
  public boolean isDisplayBackButtonFlg() {
    return displayBackButtonFlg;
  }

  /**
   * displayBackButtonFlgを設定します。
   * 
   * @param displayBackButtonFlg
   *          displayBackButtonFlg
   */
  public void setDisplayBackButtonFlg(boolean displayBackButtonFlg) {
    this.displayBackButtonFlg = displayBackButtonFlg;
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
   * fromAddressを取得します。
   * 
   * @return fromAddress
   */
  public String getFromAddress() {
    return fromAddress;
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
   * subjectを取得します。
   * 
   * @return subject
   */
  public String getSubject() {
    return subject;
  }

  /**
   * templateCodeを取得します。
   * 
   * @return templateCode
   */
  public String getTemplateCode() {
    return templateCode;
  }

  /**
   * templateListを取得します。
   * 
   * @return templateList
   */
  public List<CodeAttribute> getTemplateList() {
    return templateList;
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
   * fromAddressを設定します。
   * 
   * @param fromAddress
   *          fromAddress
   */
  public void setFromAddress(String fromAddress) {
    this.fromAddress = fromAddress;
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
   * subjectを設定します。
   * 
   * @param subject
   *          subject
   */
  public void setSubject(String subject) {
    this.subject = subject;
  }

  /**
   * templateCodeを設定します。
   * 
   * @param templateCode
   *          templateCode
   */
  public void setTemplateCode(String templateCode) {
    this.templateCode = templateCode;
  }

  /**
   * templateListを設定します。
   * 
   * @param templateList
   *          templateList
   */
  public void setTemplateList(List<CodeAttribute> templateList) {
    this.templateList = templateList;
  }

  /**
   * mailDetailListを取得します。
   * 
   * @return mailDetailList
   */
  public List<MailDetail> getMailDetailList() {
    return mailDetailList;
  }

  /**
   * mailDetailListを設定します。
   * 
   * @param mailDetailList
   *          mailDetailList
   */
  public void setMailDetailList(List<MailDetail> mailDetailList) {
    this.mailDetailList = mailDetailList;
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
   * searchCustomerFromを取得します。
   * 
   * @return searchCustomerFrom
   */
  public String getSearchCustomerFrom() {
    return searchCustomerFrom;
  }

  /**
   * searchCustomerGroupCodeを取得します。
   * 
   * @return searchCustomerGroupCode
   */
  public String getSearchCustomerGroupCode() {
    return searchCustomerGroupCode;
  }

  /**
   * searchCustomerNameを取得します。
   * 
   * @return searchCustomerName
   */
  public String getSearchCustomerName() {
    return searchCustomerName;
  }

  /**
   * searchCustomerNameKanaを取得します。
   * 
   * @return searchCustomerNameKana
   */
  public String getSearchCustomerNameKana() {
    return searchCustomerNameKana;
  }

  /**
   * searchCustomerToを取得します。
   * 
   * @return searchCustomerTo
   */
  public String getSearchCustomerTo() {
    return searchCustomerTo;
  }

  /**
   * searchEmailを取得します。
   * 
   * @return searchEmail
   */
  public String getSearchEmail() {
    return searchEmail;
  }

  /**
   * searchRequestMailTypeを取得します。
   * 
   * @return searchRequestMailType
   */
  public String getSearchRequestMailType() {
    return searchRequestMailType;
  }

  /**
   * searchTelを取得します。
   * 
   * @return searchTel
   */
  public String getSearchTel() {
    return searchTel;
  }

  /**
   * searchCustomerFromを設定します。
   * 
   * @param searchCustomerFrom
   *          searchCustomerFrom
   */
  public void setSearchCustomerFrom(String searchCustomerFrom) {
    this.searchCustomerFrom = searchCustomerFrom;
  }

  /**
   * searchCustomerGroupCodeを設定します。
   * 
   * @param searchCustomerGroupCode
   *          searchCustomerGroupCode
   */
  public void setSearchCustomerGroupCode(String searchCustomerGroupCode) {
    this.searchCustomerGroupCode = searchCustomerGroupCode;
  }

  /**
   * searchCustomerNameを設定します。
   * 
   * @param searchCustomerName
   *          searchCustomerName
   */
  public void setSearchCustomerName(String searchCustomerName) {
    this.searchCustomerName = searchCustomerName;
  }

  /**
   * searchCustomerNameKanaを設定します。
   * 
   * @param searchCustomerNameKana
   *          searchCustomerNameKana
   */
  public void setSearchCustomerNameKana(String searchCustomerNameKana) {
    this.searchCustomerNameKana = searchCustomerNameKana;
  }

  /**
   * searchCustomerToを設定します。
   * 
   * @param searchCustomerTo
   *          searchCustomerTo
   */
  public void setSearchCustomerTo(String searchCustomerTo) {
    this.searchCustomerTo = searchCustomerTo;
  }

  /**
   * searchEmailを設定します。
   * 
   * @param searchEmail
   *          searchEmail
   */
  public void setSearchEmail(String searchEmail) {
    this.searchEmail = searchEmail;
  }

  /**
   * searchRequestMailTypeを設定します。
   * 
   * @param searchRequestMailType
   *          searchRequestMailType
   */
  public void setSearchRequestMailType(String searchRequestMailType) {
    this.searchRequestMailType = searchRequestMailType;
  }

  /**
   * searchTelを設定します。
   * 
   * @param searchTel
   *          searchTel
   */
  public void setSearchTel(String searchTel) {
    this.searchTel = searchTel;
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
   * searchMobileを取得します。
   *
   * @return searchMobile searchMobile
   */
  public String getSearchMobile() {
    return searchMobile;
  }

  
  /**
   * searchMobileを設定します。
   *
   * @param searchMobile 
   *          searchMobile
   */
  public void setSearchMobile(String searchMobile) {
    this.searchMobile = searchMobile;
  }

  
  /**
   * smsDetailListを取得します。
   *
   * @return smsDetailList smsDetailList
   */
  public List<SmsDetail> getSmsDetailList() {
    return smsDetailList;
  }

  
  /**
   * smsDetailListを設定します。
   *
   * @param smsDetailList 
   *          smsDetailList
   */
  public void setSmsDetailList(List<SmsDetail> smsDetailList) {
    this.smsDetailList = smsDetailList;
  }

  
  /**
   * smsCompositionを取得します。
   *
   * @return smsComposition smsComposition
   */
  public String getSmsComposition() {
    return smsComposition;
  }

  
  /**
   * smsCompositionを設定します。
   *
   * @param smsComposition 
   *          smsComposition
   */
  public void setSmsComposition(String smsComposition) {
    this.smsComposition = smsComposition;
  }

  
  /**
   * displaySmsTemplateCautionFlgを取得します。
   *
   * @return displaySmsTemplateCautionFlg displaySmsTemplateCautionFlg
   */
  public boolean isDisplaySmsTemplateCautionFlg() {
    return displaySmsTemplateCautionFlg;
  }

  
  /**
   * displaySmsTemplateCautionFlgを設定します。
   *
   * @param displaySmsTemplateCautionFlg 
   *          displaySmsTemplateCautionFlg
   */
  public void setDisplaySmsTemplateCautionFlg(boolean displaySmsTemplateCautionFlg) {
    this.displaySmsTemplateCautionFlg = displaySmsTemplateCautionFlg;
  }

  
  /**
   * displaySmsTemplateLinkFlgを取得します。
   *
   * @return displaySmsTemplateLinkFlg displaySmsTemplateLinkFlg
   */
  public boolean isDisplaySmsTemplateLinkFlg() {
    return displaySmsTemplateLinkFlg;
  }

  
  /**
   * displaySmsTemplateLinkFlgを設定します。
   *
   * @param displaySmsTemplateLinkFlg 
   *          displaySmsTemplateLinkFlg
   */
  public void setDisplaySmsTemplateLinkFlg(boolean displaySmsTemplateLinkFlg) {
    this.displaySmsTemplateLinkFlg = displaySmsTemplateLinkFlg;
  }

}
