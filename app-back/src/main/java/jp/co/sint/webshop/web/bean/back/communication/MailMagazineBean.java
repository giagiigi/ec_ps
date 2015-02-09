package jp.co.sint.webshop.web.bean.back.communication;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U1060410:メールマガジンマスタのデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class MailMagazineBean extends UIBackBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  @Required
  @AlphaNum2
  @Length(16)
  @Metadata(name = "メールマガジンコード", order = 1)
  private String registeredMailMagazineCode;

  @Required
  @Length(20)
  @Metadata(name = "タイトル", order = 2)
  private String registeredTitle;

  @Required
  @Length(300)
  @Metadata(name = "内容説明", order = 3)
  private String registeredDescription;

  @Required
  @Metadata(name = "表示状態", order = 4)
  private String registeredDisplayStatus = "0";

  private boolean deleteDisplayFlg;

  private boolean registerDisplayFlg;

  private boolean exportDisplayFlg;

  private String displayMode;

  private Date updateDatetime;

  private List<MailMagazineBeanList> list = new ArrayList<MailMagazineBeanList>();

  /**
   * U1060410:メールマガジンマスタのサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class MailMagazineBeanList implements Serializable {

    /**
     * serial version uid
     */
    private static final long serialVersionUID = 1L;

    private String mailMagazineCode;

    private String title;

    private String description;

    private String displayStatus;

    private String subscriberAmount;

    /**
     * descriptionを取得します。
     * 
     * @return description
     */
    public String getDescription() {
      return description;
    }

    /**
     * descriptionを設定します。
     * 
     * @param description
     */
    public void setDescription(String description) {
      this.description = description;
    }

    /**
     * displayStatusを取得します。
     * 
     * @return displayStatus
     */
    public String getDisplayStatus() {
      return displayStatus;
    }

    /**
     * displayStatusを設定します。
     * 
     * @param displayStatus
     */
    public void setDisplayStatus(String displayStatus) {
      this.displayStatus = displayStatus;
    }

    /**
     * mailMagazineCodeを取得します。
     * 
     * @return mailMagazineCode
     */
    public String getMailMagazineCode() {
      return mailMagazineCode;
    }

    /**
     * mailMagazineCodeを設定します。
     * 
     * @param mailMagazineCode
     */
    public void setMailMagazineCode(String mailMagazineCode) {
      this.mailMagazineCode = mailMagazineCode;
    }

    /**
     * subscriberAmountを取得します。
     * 
     * @return subscriberAmount
     */
    public String getSubscriberAmount() {
      return subscriberAmount;
    }

    /**
     * subscriberAmountを設定します。
     * 
     * @param subscriberAmount
     */
    public void setSubscriberAmount(String subscriberAmount) {
      this.subscriberAmount = subscriberAmount;
    }

    /**
     * titleを取得します。
     * 
     * @return title
     */
    public String getTitle() {
      return title;
    }

    /**
     * titleを設定します。
     * 
     * @param title
     */
    public void setTitle(String title) {
      this.title = title;
    }
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
   */
  public void setUpdateDatetime(Date updateDatetime) {
    this.updateDatetime = DateUtil.immutableCopy(updateDatetime);
  }

  /**
   * deleteDisplayFlgを取得します。
   * 
   * @return deleteDisplayFlg
   */
  public boolean isDeleteDisplayFlg() {
    return deleteDisplayFlg;
  }

  /**
   * deleteDisplayFlgを設定します。
   * 
   * @param deleteDisplayFlg
   */
  public void setDeleteDisplayFlg(boolean deleteDisplayFlg) {
    this.deleteDisplayFlg = deleteDisplayFlg;
  }

  /**
   * exportDisplayFlgを取得します。
   * 
   * @return exportDisplayFlg
   */
  public boolean isExportDisplayFlg() {
    return exportDisplayFlg;
  }

  /**
   * exportDisplayFlgを設定します。
   * 
   * @param exportDisplayFlg
   */
  public void setExportDisplayFlg(boolean exportDisplayFlg) {
    this.exportDisplayFlg = exportDisplayFlg;
  }

  /**
   * listを取得します。
   * 
   * @return list
   */
  public List<MailMagazineBeanList> getList() {
    return list;
  }

  /**
   * listを設定します。
   * 
   * @param list
   */
  public void setList(List<MailMagazineBeanList> list) {
    this.list = list;
  }

  /**
   * registerDisplayFlgを取得します。
   * 
   * @return registerDisplayFlg
   */
  public boolean isRegisterDisplayFlg() {
    return registerDisplayFlg;
  }

  /**
   * registerDisplayFlgを設定します。
   * 
   * @param registerDisplayFlg
   */
  public void setRegisterDisplayFlg(boolean registerDisplayFlg) {
    this.registerDisplayFlg = registerDisplayFlg;
  }

  /**
   * registeredDescriptionを取得します。
   * 
   * @return registeredDescription
   */
  public String getRegisteredDescription() {
    return registeredDescription;
  }

  /**
   * registeredDescriptionを設定します。
   * 
   * @param registeredDescription
   */
  public void setRegisteredDescription(String registeredDescription) {
    this.registeredDescription = registeredDescription;
  }

  /**
   * registeredDisplayStatusを取得します。
   * 
   * @return registeredDisplayStatus
   */
  public String getRegisteredDisplayStatus() {
    return registeredDisplayStatus;
  }

  /**
   * registeredDisplayStatusを設定します。
   * 
   * @param registeredDisplayStatus
   */
  public void setRegisteredDisplayStatus(String registeredDisplayStatus) {
    this.registeredDisplayStatus = registeredDisplayStatus;
  }

  /**
   * registeredMailMagazineCodeを取得します。
   * 
   * @return registeredMailMagazineCode
   */
  public String getRegisteredMailMagazineCode() {
    return registeredMailMagazineCode;
  }

  /**
   * registeredMailMagazineCodeを設定します。
   * 
   * @param registeredMailMagazineCode
   */
  public void setRegisteredMailMagazineCode(String registeredMailMagazineCode) {
    this.registeredMailMagazineCode = registeredMailMagazineCode;
  }

  /**
   * registeredTitleを取得します。
   * 
   * @return registeredTitle
   */
  public String getRegisteredTitle() {
    return registeredTitle;
  }

  /**
   * registeredTitleを設定します。
   * 
   * @param registeredTitle
   */
  public void setRegisteredTitle(String registeredTitle) {
    this.registeredTitle = registeredTitle;
  }

  /**
   * displayModeを取得します。
   * 
   * @return displayMode
   */
  public String getDisplayMode() {
    return displayMode;
  }

  /**
   * displayModeを設定します。
   * 
   * @param displayMode
   */
  public void setDisplayMode(String displayMode) {
    this.displayMode = displayMode;
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
  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U1060410";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.back.communication.MailMagazineBean.0");
  }

}
