package jp.co.sint.webshop.service.order;

import java.io.Serializable;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.service.cart.CashierPayment;
import jp.co.sint.webshop.service.cart.CashierPayment.CashierPaymentTypeBase;

/**
 * ダミーの支払プロバイダです。決済処理を必要としない支払方法で使われます。
 * 
 * @author System Integrator Corp.
 */
public class DummyPayment implements PaymentProvider, Serializable {

  private static final long serialVersionUID = 1L;

  public PaymentResult cancel(PaymentParameter parameter) {
    return new PaymentResultImpl(PaymentResultType.COMPLETED);
  }

  public PaymentResult entry(PaymentParameter parameter) {
    return new PaymentResultImpl(PaymentResultType.COMPLETED);
  }

  public PaymentResult invoice(PaymentParameter parameter) {
    return new PaymentResultImpl(PaymentResultType.COMPLETED);
  }

  public PaymentResult query(PaymentParameter parameter) {
    return new PaymentResultImpl(PaymentResultType.COMPLETED);
  }

  public PaymentParameter createParameterInstance() {
    return new DummyParameter();
  }

  public static class DummyParameter extends PaymentParameter {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Override
    public void setCashierPayment(CashierPayment cashier) {
      // ダミー決済は処理なし
    }

    @Override
    public void setOrderContainer(OrderContainer orderContainer) {
      // ダミー決済は処理なし
    }

  }

  public CodeAttribute[] getCodeList(CashierPaymentTypeBase cashier) {
    // ダミーはコード値のリストを持たないため、空のリストを返す
    return new CodeAttribute[0];
  }

}
