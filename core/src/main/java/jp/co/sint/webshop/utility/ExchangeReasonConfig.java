package jp.co.sint.webshop.utility;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.code.NameValue;

/**
 * 交换理由情报
 * 
 * @author ks
 */
public class ExchangeReasonConfig implements Serializable {

  /**
   * serial version UID
   */
  private static final long serialVersionUID = 1L;

  /** 交换理由 */
  private List<NameValue> exchangeReason = new ArrayList<NameValue>();

  /** 退货理由 */
  private List<NameValue> returnReason = new ArrayList<NameValue>();

  /**
   * exchangeReasonを取得します。
   * 
   * @return exchangeReason
   */
  public List<NameValue> getExchangeReason() {
    return this.exchangeReason;
  }
  
  public String getExchangeReason(String exchangeReasonValue) {
    if (!StringUtil.isNullOrEmpty(exchangeReasonValue) && this.exchangeReason != null) {
      for (int i = 0; i < exchangeReason.size(); i++) {
        if (exchangeReasonValue.equals(exchangeReason.get(i).getValue())) {
          return exchangeReason.get(i).getName();
        }
      }
    }
    return "";
  }

  /**
   * exchangeReasonを設定します。
   * 
   * @param exchangeReason
   *          exchangeReason
   */
  public void setExchangeReason(List<String> exchangeReason) {

    if (exchangeReason != null && exchangeReason.size() > 0) {

      String strPaymentTimes = "";

      for (int i = 0; i < exchangeReason.size(); i++) {

        if (i > 0) {
          strPaymentTimes = strPaymentTimes + "/" + exchangeReason.get(i);
        } else {
          strPaymentTimes = exchangeReason.get(i);
        }
      }
      this.exchangeReason = NameValue.asList(strPaymentTimes);
    }

  }

  /**
   * exchangeReasonを取得します。
   * 
   * @return exchangeReason
   */
  public List<NameValue> getReturnReason() {
    return this.returnReason;
  }

  public String getReturnReason(String returnReasonValue) {
    if (!StringUtil.isNullOrEmpty(returnReasonValue) && this.returnReason != null) {
      for (int i = 0; i < returnReason.size(); i++) {
        if (returnReasonValue.equals(returnReason.get(i).getValue())) {
          return returnReason.get(i).getName();
        }
      }
    }
    return "";
  }

  /**
   * exchangeReasonを設定します。
   * 
   * @param exchangeReason
   *          exchangeReason
   */
  public void setReturnReason(List<String> returnReason) {

    if (returnReason != null && returnReason.size() > 0) {

      String strPaymentTimes = "";

      for (int i = 0; i < returnReason.size(); i++) {

        if (i > 0) {
          strPaymentTimes = strPaymentTimes + "/" + returnReason.get(i);
        } else {
          strPaymentTimes = returnReason.get(i);
        }
      }
      this.returnReason = NameValue.asList(strPaymentTimes);
    }

  }
}
