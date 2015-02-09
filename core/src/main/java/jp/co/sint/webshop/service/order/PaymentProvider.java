package jp.co.sint.webshop.service.order;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.service.cart.CashierPayment.CashierPaymentTypeBase;

/**
 * 支払インタフェース
 * 
 * @author System Integrator Corp.
 */
public interface PaymentProvider {

  /**
   * 問い合わせ
   * 
   * @param parameter
   * @return 問い合わせ
   */
  PaymentResult query(PaymentParameter parameter);

  /**
   * キャンセル処理
   * 
   * @param parameter
   * @return キャンセル処理
   */
  PaymentResult cancel(PaymentParameter parameter);

  /**
   * 申込処理
   * 
   * @param parameter
   * @return 申し込み処理
   */
  PaymentResult entry(PaymentParameter parameter);

  /**
   * 請求処理
   * 
   * @param parameter
   * @return 請求処理
   */
  PaymentResult invoice(PaymentParameter parameter);

  /**
   * 支払に必要なパラメータインスタンスを生成します。
   * 
   * @return 支払に必要なパラメータインスタンス
   */
  PaymentParameter createParameterInstance();

  /**
   * 支払に必要なコード値、名前のリストを取得します。
   * 
   * @param cashier
   * @return 支払に必要なコード値、名前のリスト
   */
  CodeAttribute[] getCodeList(CashierPaymentTypeBase cashier);

}
