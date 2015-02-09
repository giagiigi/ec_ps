package jp.co.sint.webshop.web.action.front.order;

import jp.co.sint.webshop.data.domain.LoginType;
import jp.co.sint.webshop.web.webutility.PaymentSupporter;

/**
 * お支払い方法のアクションの基底クラスです
 * 
 * @author System Integrator Corp.
 */
public final class PaymentSupporterFactory {

  private PaymentSupporterFactory() {
  }

  /**
   * 画面表示用お支払い方法情報を生成します
   * 
   * @return supporter
   */
  public static PaymentSupporter createPaymentSuppoerter() {
    PaymentSupporter supporter = new PaymentSupporter(LoginType.FRONT);
    return supporter;
  }
}
