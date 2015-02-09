package jp.co.sint.webshop.service.shop;

import java.io.Serializable;
import java.util.List;

import jp.co.sint.webshop.data.dto.SmsTemplateDetail;

/**
 * SI Web Shopping 10 メールテンプレート
 * 
 * @author System Integrator Corp.
 */
public class SmsTemplateSuite implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;


  /** メールテンプレート詳細情報 */
  private List<SmsTemplateDetail> smsTemplateDetail;

  /**
   * smsTemplateDetailを取得します。
   * 
   * @return the smsTemplateDetail
   */
  public List<SmsTemplateDetail> getSmsTemplateDetail() {
    return smsTemplateDetail;
  }

  /**
   * smsTemplateDetailを設定します。
   * 
   * @param smsTemplateDetail
   *          the smsTemplateDetail to set
   */
  public void setSmsTemplateDetail(List<SmsTemplateDetail> smsTemplateDetail) {
    this.smsTemplateDetail = smsTemplateDetail;
  }

}
