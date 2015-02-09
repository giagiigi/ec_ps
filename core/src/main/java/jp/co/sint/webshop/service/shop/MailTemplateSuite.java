package jp.co.sint.webshop.service.shop;

import java.io.Serializable;
import java.util.List;

import jp.co.sint.webshop.data.dto.MailTemplateDetail;
import jp.co.sint.webshop.data.dto.MailTemplateHeader;

/**
 * SI Web Shopping 10 メールテンプレート
 * 
 * @author System Integrator Corp.
 */
public class MailTemplateSuite implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /** メールテンプレートヘッダー情報 */
  private MailTemplateHeader mailTemplateHeader;

  /** メールテンプレート詳細情報 */
  private List<MailTemplateDetail> mailTemplateDetail;

  /**
   * mailTemplateDetailを取得します。
   * 
   * @return the mailTemplateDetail
   */
  public List<MailTemplateDetail> getMailTemplateDetail() {
    return mailTemplateDetail;
  }

  /**
   * mailTemplateDetailを設定します。
   * 
   * @param mailTemplateDetail
   *          the mailTemplateDetail to set
   */
  public void setMailTemplateDetail(List<MailTemplateDetail> mailTemplateDetail) {
    this.mailTemplateDetail = mailTemplateDetail;
  }

  /**
   * mailTemplateHeaderを取得します。
   * 
   * @return the mailTemplateHeader
   */
  public MailTemplateHeader getMailTemplateHeader() {
    return mailTemplateHeader;
  }

  /**
   * mailTemplateHeaderを設定します。
   * 
   * @param mailTemplateHeader
   *          the mailTemplateHeader to set
   */
  public void setMailTemplateHeader(MailTemplateHeader mailTemplateHeader) {
    this.mailTemplateHeader = mailTemplateHeader;
  }

}
