package jp.co.sint.webshop.utility;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 顧客問い合わせ情報
 * 
 * @author System Integrator Corp.
 */
public class InquiryConfig implements Serializable {

  /**
   * serial version UID
   */
  private static final long serialVersionUID = 1L;

  /** 顧客問い合わせ件名 */
  private List<String> inquirySubjects = new ArrayList<String>();

  /** 顧客問い合わせ件名(商品について) */
  private String commodityInquirySubject;

  /** 顧客問い合わせ件名(注文/予約内容について) */
  private String orderInquirySubject;

  /** 問い合わせ送信先メールアドレス(To) */
  private String toAddress;

  /** 問い合わせ送信先メールアドレス(Cc) */
  private String ccAddress;

  /** 問い合わせ送信先メールアドレス(Bcc) */
  private String bccAddress;

  /**
   * commodityInquirySubjectを取得します。
   * 
   * @return commodityInquirySubject
   */
  public String getCommodityInquirySubject() {
    return StringUtil.coalesce(CodeUtil.getEntry("commodityInquirySubject"), commodityInquirySubject);
  }

  /**
   * commodityInquirySubjectを設定します。
   * 
   * @param commodityInquirySubject
   *          commodityInquirySubject
   */
  public void setCommodityInquirySubject(String commodityInquirySubject) {
    this.commodityInquirySubject = commodityInquirySubject;
  }

  /**
   * orderInquirySubjectを取得します。
   * 
   * @return orderInquirySubject
   */
  public String getOrderInquirySubject() {
    return StringUtil.coalesce(CodeUtil.getEntry("orderInquirySubject"), orderInquirySubject);
  }

  /**
   * orderInquirySubjectを設定します。
   * 
   * @param orderInquirySubject
   *          orderInquirySubject
   */
  public void setOrderInquirySubject(String orderInquirySubject) {
    this.orderInquirySubject = orderInquirySubject;
  }

  /**
   * inquirySubjectsを取得します。
   * 
   * @return inquirySubjects
   */
  public List<String> getInquirySubjects() {
    List<String> result = CodeUtil.getListEntry("inquirySubjects");
    if (result == null || result.isEmpty()) {
      return inquirySubjects;
    }
    return result;
  }

  /**
   * inquirySubjectsを設定します。
   * 
   * @param inquirySubjects
   *          inquirySubjects
   */
  public void setInquirySubjects(List<String> inquirySubjects) {
    this.inquirySubjects = inquirySubjects;
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
   * ccAddressを取得します。
   * 
   * @return ccAddress
   */
  public String getCcAddress() {
    return ccAddress;
  }

  /**
   * ccAddressを設定します。
   * 
   * @param ccAddress
   *          ccAddress
   */
  public void setCcAddress(String ccAddress) {
    this.ccAddress = ccAddress;
  }

  /**
   * toAddressを取得します。
   * 
   * @return toAddress
   */
  public String getToAddress() {
    return toAddress;
  }

  /**
   * toAddressを設定します。
   * 
   * @param toAddress
   *          toAddress
   */
  public void setToAddress(String toAddress) {
    this.toAddress = toAddress;
  }

}
