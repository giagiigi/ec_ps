package jp.co.sint.webshop.utility;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import jp.co.sint.webshop.data.domain.PaymentMethodType;

public class PaymentConfig implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  public PaymentConfig() {
  }

  private String netUrl;

  private BigDecimal cvsPaymentMaximum;

  private HashMap<String, BigDecimal> digitalCashPaymentMaximum = new HashMap<String, BigDecimal>();

  private HashSet<PaymentMethodType> phantomOrderTypes = new HashSet<PaymentMethodType>();

  /**
   * cvsPaymentMaximumを取得します。
   * 
   * @return cvsPaymentMaximum
   */
  public BigDecimal getCvsPaymentMaximum() {
    return cvsPaymentMaximum;
  }

  /**
   * cvsPaymentMaximumを設定します。
   * 
   * @param cvsPaymentMaximum
   *          cvsPaymentMaximum
   */
  public void setCvsPaymentMaximum(BigDecimal cvsPaymentMaximum) {
    this.cvsPaymentMaximum = cvsPaymentMaximum;
  }

  /**
   * digitalCashPaymentMaximumを取得します。
   * 
   * @return digitalCashPaymentMaximum
   */
  public Map<String, BigDecimal> getDigitalCashPaymentMaximum() {
    return digitalCashPaymentMaximum;
  }

  /**
   * digitalCashPaymentMaximumを取得します。
   * 
   * @return digitalCashPaymentMaximum
   */
  public BigDecimal getDigitalCashPaymentMaximum(String digitalCashType) {
    return digitalCashPaymentMaximum.get(digitalCashType);
  }

  /**
   * digitalCashPaymentMaximumを設定します。
   * 
   * @param digitalCashPaymentMaximum
   *          digitalCashPaymentMaximum
   */
  public void setDigitalCashPaymentMaximum(Map<String, BigDecimal> digitalCashPaymentMaximum) {
    CollectionUtil.copyAll(this.digitalCashPaymentMaximum, digitalCashPaymentMaximum);
  }

  /**
   * phantomOrderTypesを取得します。
   * 
   * @return phantomOrderTypes phantomOrderTypes
   */
  public Set<PaymentMethodType> getPhantomOrderTypes() {
    return phantomOrderTypes;
  }

  /**
   * phantomOrderTypesを設定します。
   * 
   * @param phantomOrderTypes
   *          phantomOrderTypes
   */
  public void setPhantomOrderTypes(Set<PaymentMethodType> phantomOrderTypes) {
    CollectionUtil.copyAll(this.phantomOrderTypes, phantomOrderTypes);
  }

  public String getNetUrl() {
    return netUrl;
  }

  public void setNetUrl(String netUrl) {
    this.netUrl = netUrl;
  }

}
