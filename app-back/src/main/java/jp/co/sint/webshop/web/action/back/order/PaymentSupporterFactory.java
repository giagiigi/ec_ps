package jp.co.sint.webshop.web.action.back.order;

import jp.co.sint.webshop.data.domain.LoginType;
import jp.co.sint.webshop.web.webutility.PaymentSupporter;

/**
 * 支払方法情報生成クラスです。
 * 
 * @author System Integrator Corp.
 */
public final class PaymentSupporterFactory {

  private PaymentSupporterFactory() {
  }

  /**
   * 支払方法情報を生成します。
   * 
   * @return supporter
   */
  public static PaymentSupporter createPaymentSuppoerter() {
    PaymentSupporter supporter = new PaymentSupporter(LoginType.BACK);
    return supporter;
  }
}
