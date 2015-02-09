package jp.co.sint.webshop.web.bean.front.customer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.attribute.Email;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.web.bean.front.UIFrontBean;
import jp.co.sint.webshop.web.text.front.Messages;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U2060420:メールマガジン登録のデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class MailMagazineBean extends UIFrontBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  @Required
  @Length(256)
  @Email
  @Metadata(name = "メールアドレス")
  private String email;

  private List<MailMagazineListBean> list = new ArrayList<MailMagazineListBean>();

  /**
   * U2060420:メールマガジン登録のサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class MailMagazineListBean implements Serializable {

    /** serial version uid */
    private static final long serialVersionUID = 1L;

    private String mailMagazineCode;

    private String mailMagazineItem;

    private String title;

    private String description;

    /**
     * mailMagazineItemを返します。
     * 
     * @return the mailMagazineItem
     */
    public String getMailMagazineItem() {
      return mailMagazineItem;
    }

    /**
     * mailMagazineItemを設定します。
     * 
     * @param mailMagazineItem
     *          設定する mailMagazineItem
     */
    public void setMailMagazineItem(String mailMagazineItem) {
      this.mailMagazineItem = mailMagazineItem;
    }

    /**
     * @return description
     */
    public String getDescription() {
      return description;
    }

    /**
     * @param description
     *          設定する description
     */
    public void setDescription(String description) {
      this.description = description;
    }

    /**
     * @return mailMagazineCode
     */
    public String getMailMagazineCode() {
      return mailMagazineCode;
    }

    /**
     * @param mailMagazineCode
     *          設定する mailMagazineCode
     */
    public void setMailMagazineCode(String mailMagazineCode) {
      this.mailMagazineCode = mailMagazineCode;
    }

    /**
     * @return title
     */
    public String getTitle() {
      return title;
    }

    /**
     * @param title
     *          設定する title
     */
    public void setTitle(String title) {
      this.title = title;
    }

  }

  /**
   * emailを取得します。
   * 
   * @return email
   */
  public String getEmail() {
    return email;
  }

  /**
   * emailを設定します。
   * 
   * @param email
   *          email
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * メールマガジン一覧を取得します。
   * 
   * @return list
   */
  public List<MailMagazineListBean> getList() {
    return list;
  }

  /**
   * メールマガジン一覧を設定します。
   * 
   * @param list
   *          list
   */
  public void setList(List<MailMagazineListBean> list) {
    this.list = list;
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
    // 10.1.7 10303 追加 ここから
    for (MailMagazineListBean mailList : list) {
      mailList.setMailMagazineItem(null);
    }
    // 10.1.7 10303 追加 ここまで
    String[] paramList = reqparam.getAll("checkBox");
    for (String param : paramList) {
      for (MailMagazineListBean mailList : list) {
        if (param.equals(mailList.getMailMagazineCode())) {
          mailList.setMailMagazineItem(param);
        }
      }
    }
  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U2060420";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.front.customer.MailMagazineBean.0");
  }

}
