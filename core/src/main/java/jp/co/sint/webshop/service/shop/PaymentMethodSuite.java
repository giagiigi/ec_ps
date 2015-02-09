package jp.co.sint.webshop.service.shop;

import java.io.Serializable;
import java.util.List;

import jp.co.sint.webshop.data.dto.Commission;
import jp.co.sint.webshop.data.dto.PaymentMethod;

/**
 * SI Web Shopping 10 支払方法設定情報仕様
 * 
 * @author System Integrator Corp.
 */
public class PaymentMethodSuite implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private PaymentMethod paymentMethod;

  private List<Commission> commissionList;

  /**
   * 支払方法の取得。
   * 
   * @return 支払方法
   */
  public PaymentMethod getPaymentMethod() {
    return paymentMethod;
  }

  /**
   * 支払方法の設定。
   * 
   * @param paymentMethod
   */
  public void setPaymentMethod(PaymentMethod paymentMethod) {
    this.paymentMethod = paymentMethod;
  }

  /**
   * 手数料の一覧を取得。
   * 
   * @return 手数料の一覧
   */
  public List<Commission> getCommissionList() {
    return commissionList;
  }

  /**
   * 手数料一覧を設定。
   * 
   * @param commissionList
   */
  public void setCommissionList(List<Commission> commissionList) {
    this.commissionList = commissionList;
  }
}
