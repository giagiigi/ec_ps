package jp.co.sint.webshop.configure;

import java.io.Serializable;

import jp.co.sint.webshop.utility.CodeUtil;
import jp.co.sint.webshop.utility.StringUtil;

/**
 * applicationContext-messages.xmlの内容を読み込んで初期化します。
 * 
 * @author System Integrator Corp.
 */
public class WebshopMessage implements Serializable {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private String frontCustomerRegisterMembersRule;

  private String mobileCustomerRegisterMembersRule;

  private String frontGuestPurchaseMembersRule;

  private String mobileGuestPurchaseMembersRule;

  private String frontContactInformation;

  private String backContactInformation;

  private String mobileContactInformation;

  /**
   * mobileCustomerRegisterMembersRuleを返します。
   * 
   * @return the mobileCustomerRegisterMembersRule
   */
  public String getMobileCustomerRegisterMembersRule() {
    return StringUtil.coalesce(CodeUtil.getEntry("mobileCustomerRegisterMembersRule"), mobileCustomerRegisterMembersRule);
  }

  /**
   * mobileGuestPurchaseMembersRuleを返します。
   * 
   * @return the mobileGuestPurchaseMembersRule
   */
  public String getMobileGuestPurchaseMembersRule() {
    return StringUtil.coalesce(CodeUtil.getEntry("mobileGuestPurchaseMembersRule"), mobileGuestPurchaseMembersRule);

  }

  /**
   * mobileCustomerRegisterMembersRuleを設定します。
   * 
   * @param mobileCustomerRegisterMembersRule
   *          設定する mobileCustomerRegisterMembersRule
   */
  public void setMobileCustomerRegisterMembersRule(String mobileCustomerRegisterMembersRule) {
    this.mobileCustomerRegisterMembersRule = mobileCustomerRegisterMembersRule;
  }

  /**
   * mobileGuestPurchaseMembersRuleを設定します。
   * 
   * @param mobileGuestPurchaseMembersRule
   *          設定する mobileGuestPurchaseMembersRule
   */
  public void setMobileGuestPurchaseMembersRule(String mobileGuestPurchaseMembersRule) {
    this.mobileGuestPurchaseMembersRule = mobileGuestPurchaseMembersRule;
  }

  /**
   * frontCustomerRegisterMembersRuleを返します。
   * 
   * @return the frontCustomerRegisterMembersRule
   */
  public String getFrontCustomerRegisterMembersRule() {
    return StringUtil.coalesce(CodeUtil.getEntry("frontCustomerRegisterMembersRule"), frontCustomerRegisterMembersRule);
  }

  /**
   * frontGuestPurchaseMembersRuleを返します。
   * 
   * @return the frontGuestPurchaseMembersRule
   */
  public String getFrontGuestPurchaseMembersRule() {
    return StringUtil.coalesce(CodeUtil.getEntry("frontGuestPurchaseMembersRule"), frontGuestPurchaseMembersRule);
  }

  /**
   * frontCustomerRegisterMembersRuleを設定します。
   * 
   * @param frontCustomerRegisterMembersRule
   *          設定する frontCustomerRegisterMembersRule
   */
  public void setFrontCustomerRegisterMembersRule(String frontCustomerRegisterMembersRule) {
    this.frontCustomerRegisterMembersRule = frontCustomerRegisterMembersRule;
  }

  /**
   * frontGuestPurchaseMembersRuleを設定します。
   * 
   * @param frontGuestPurchaseMembersRule
   *          設定する frontGuestPurchaseMembersRule
   */
  public void setFrontGuestPurchaseMembersRule(String frontGuestPurchaseMembersRule) {
    this.frontGuestPurchaseMembersRule = frontGuestPurchaseMembersRule;
  }

  /**
   * frontContactInformationを返します。
   * 
   * @return the frontContactInformation
   */
  public String getFrontContactInformation() {
    return StringUtil.coalesce(CodeUtil.getEntry("frontContactInformation"), frontContactInformation);
  }

  /**
   * frontContactInformationを設定します。
   * 
   * @param frontContactInformation
   *          設定する frontContactInformation
   */
  public void setFrontContactInformation(String frontContactInformation) {
    this.frontContactInformation = frontContactInformation;
  }

  /**
   * backContactInformationを返します。
   * 
   * @return the backContactInformation
   */
  public String getBackContactInformation() {
    return StringUtil.coalesce(CodeUtil.getEntry("backContactInformation"), backContactInformation);
  }

  /**
   * backContactInformationを設定します。
   * 
   * @param backContactInformation
   *          設定する backContactInformation
   */
  public void setBackContactInformation(String backContactInformation) {
    this.backContactInformation = backContactInformation;
  }

  /**
   * mobileContactInformationを返します。
   * 
   * @return the mobileContactInformation
   */
  public String getMobileContactInformation() {
    return StringUtil.coalesce(CodeUtil.getEntry("mobileContactInformation"), mobileContactInformation);
  }

  /**
   * mobileContactInformationを設定します。
   * 
   * @param mobileContactInformation
   *          設定する mobileContactInformation
   */
  public void setMobileContactInformation(String mobileContactInformation) {
    this.mobileContactInformation = mobileContactInformation;
  }

}
