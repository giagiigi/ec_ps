package jp.co.sint.webshop.service.order;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.code.SequenceType;
import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.dto.OrderHeader;
import jp.co.sint.webshop.service.LoginInfo;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceLoginInfo;
import jp.co.sint.webshop.service.ShopManagementService;
import jp.co.sint.webshop.service.cart.CashierPayment;
import jp.co.sint.webshop.service.cart.CashierPayment.CashierPaymentTypeBase;
import jp.co.sint.webshop.service.shop.PaymentMethodSuite;
import jp.co.sint.webshop.utility.BigDecimalUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;

import org.apache.log4j.Logger;

/**
 * 支払プロバイダの管理クラス。<BR>
 * 
 * @author System Integrator Corp.
 */
public class PaymentProviderManager {

  /** 支払プロバイダ */
  private PaymentProvider provider;

  private LoginInfo loginInfo = ServiceLoginInfo.getInstance(); // 10.1.6 10284
                                                                // 追加

  /** 支払パラメータ */
  private PaymentParameter parameter;

  @Deprecated
  // 10.1.6 10284 追加
  public PaymentProviderManager() {
  }

  @Deprecated
  // 10.1.6 10284 追加
  public PaymentProviderManager(CashierPayment cashier, OrderContainer order) {
    init(cashier, order);
  }

  // 10.1.6 10284 追加 ここから
  public PaymentProviderManager(LoginInfo loginInfo) {
    this.loginInfo = loginInfo;
  }

  public PaymentProviderManager(CashierPayment cashier, OrderContainer order, LoginInfo loginInfo) {
    this.loginInfo = loginInfo;
    init(cashier, order);
  }

  // 10.1.6 10284 追加 ここまで

  /**
   * 与信処理を行います。<BR>
   * 
   * @param cashier
   *          キャッシャー支払方法情報
   * @param order
   *          受注情報
   * @return 与信結果。成功:true 失敗:false
   */
  public PaymentResult entry(CashierPayment cashier, OrderContainer order) {

    Logger logger = Logger.getLogger(this.getClass());

    logger.debug("Payment Operation started : ENTRY");

    // プロバイダ・パラメータを初期化
    init(cashier, order);

    // プロバイダ・パラメータが正常に初期化されたかチェック
    if (provider == null || parameter == null) {
      return new PaymentResultImpl(PaymentResultType.FAILED);
    }

    // 与信処理
    PaymentResult result = provider.entry(parameter);

    // 与信結果のチェック
    if (result.hasError()) {
      dispose();
      logger.debug("Payment Operation failed : ENTRY");
    } else {
      logger.debug("Payment Operation succeeded : ENTRY");
    }

    return result;

  }

  /**
   * 売上処理を単独で行います。先に{@link #entry(CashierPayment, OrderContainer)}<BR>
   * を呼び出す必要はありません。
   * 
   * @param cashier
   * @param order
   * @return 売上処理結果。成功:true 失敗:false
   */
  public boolean invoice(CashierPayment cashier, OrderContainer order) {
    init(cashier, order);
    return invoice();
  }

  /**
   * 売上処理を行います。<BR>
   * 本メソッドで売上処理を行うには、先に{@link #entry(CashierPayment, OrderContainer)}<BR>
   * による与信処理が成功している必要があります。<BR>
   * {@link #entry(CashierPayment, OrderContainer)}の呼び出しでfalseを返していた場合、本メソッドはtrue
   * を返しますが、処理は行われません。
   * 
   * @return 売上処理結果。成功:true 失敗:false
   */
  public boolean invoice() {
    Logger logger = Logger.getLogger(this.getClass());

    logger.debug("Payment Operation started : INVOICE");

    if (provider == null || parameter == null) {
      logger.debug("Payment Operation is not executable : INVOICE");
      return true;
    }

    PaymentResult result = provider.invoice(parameter);
    if (result.hasError()) {
      logger.debug("Payment Operation failed : INVOICE");
      dispose();
      return false;
    }

    logger.debug("Payment Operation succeeded : INVOICE");
    return true;
  }

  /**
   * キャンセル処理を単独で行います。先に{@link #invoice()}<BR>
   * を呼び出す必要はありません。
   * 
   * @param cashier
   * @param order
   * @return キャンセル処理結果。成功:true 失敗:false
   */
  public boolean cancel(CashierPayment cashier, OrderContainer order) {
    init(cashier, order);
    return cancel();
  }

  /**
   * キャンセル処理を行います。<BR>
   * 本メソッドで処理を行うには、先に{@link #invoice()}による売上処理が成功している必要があります。
   * {@link #entry(CashierPayment, OrderContainer)} または {@link #invoice()}の呼び出しでfalse
   * を返していた場合、本メソッドはtrueを返しますが、処理は行われません。
   * 
   * @return キャンセル処理結果。成功:true 失敗:false
   */
  public boolean cancel() {
    Logger logger = Logger.getLogger(this.getClass());

    logger.debug("Payment Operation started : CANCEL");

    if (provider == null || parameter == null) {
      logger.debug("Payment Operation not executable : CANCEL");
      return true;
    }

    PaymentResult result = provider.cancel(parameter);
    if (result.hasError()) {
      logger.debug("Payment Operation failed : CANCEL");
      return false;
    }

    logger.debug("Payment Operation succeeded : CANCEL");
    dispose();
    return true;
  }

  /**
   * 未実装<BR>
   * 再与信処理。<BR>
   * {@link #entry(CashierPayment, OrderContainer)}呼び出し時に渡されたキャッシャー情報、
   * 受注情報を使用して、再度与信処理を行います。取引IDは新規に採番されます。
   * 
   * @return 再与信処理結果。成功:true 失敗:false
   * @throws UnsupportedOperationException
   */
  public boolean reEntry() {
    throw new UnsupportedOperationException("再与信処理はサポートされていません。");
  }

  /**
   * 初期化処理。<BR>
   * 支払プロバイダ・支払パラメータを初期化します。
   * 
   * @param cashier
   * @param orderContainer
   */
  private void init(CashierPayment cashier, OrderContainer orderContainer) {
    Logger logger = Logger.getLogger(this.getClass());

    // キャッシャー情報と受注情報をバリデート
    // 決済に必要な項目が不足していれば、プロバイダとパラメータを初期化しない
    if (!validateCashier(cashier) || !validateOrder(orderContainer)) {
      logger.debug("cashier or order is invalid.");
      return;
    }

    // プロバイダが初期化されていなければ初期化
    if (provider == null) {
      provider = DIContainer.getPaymentProvider(cashier.getSelectPayment().getClass());
    }

    // パラメータが初期化されていなければ初期化
    if (parameter == null) {
      parameter = provider.createParameterInstance();

      // ダミープロバイダの場合は支払パラメータは必要ないので設定しない
      if (!(provider instanceof DummyPayment)) {

        parameter.setShopCode(orderContainer.getOrderHeader().getShopCode());
        parameter.setOrderId(String.valueOf(getPaymentOrderId(orderContainer.getOrderHeader())));
        // modify by V10-CH start
        // parameter.setAmount(orderContainer.getTotalAmount() -
        // orderContainer.getOrderHeader().getUsedPoint());
        parameter.setAmount(BigDecimalUtil
            .subtract(orderContainer.getTotalAmount(), orderContainer.getOrderHeader().getUsedPoint()));
        // modify by V10-CH end
        parameter.setCashierPayment(cashier);
        parameter.setOrderContainer(orderContainer);

        // 10.1.6 10284 修正 ここから
        // ShopManagementService service =
        // ServiceLocator.getShopManagementService(ServiceLoginInfo.getInstance());
        ShopManagementService service = ServiceLocator.getShopManagementService(getLoginInfo());
        // 10.1.6 10284 修正 ここまで
        PaymentMethodSuite method = service.getPaymentMethod(cashier.getShopCode(), NumUtil.toLong(cashier.getPaymentMethodCode()));
        if (method != null && method.getPaymentMethod() != null) {
          parameter.setMerchantId(method.getPaymentMethod().getMerchantId());
          parameter.setSecretKey(method.getPaymentMethod().getSecretKey());
          Long paymentLimitDays = method.getPaymentMethod().getPaymentLimitDays();
          if (paymentLimitDays != null) {
            Date paymentLimitDate = DateUtil.addDate(DateUtil.getSysdate(), paymentLimitDays.intValue());
            parameter.setPaymentLimitDate(paymentLimitDate);
          }
        }
      }
    }
  }

  public static List<CodeAttribute> getCodeList(CashierPaymentTypeBase cashier) {
    PaymentProvider provider = DIContainer.getPaymentProvider(cashier.getClass());
    return Arrays.asList(provider.getCodeList(cashier));
  }

  /**
   * 終了処理。<BR>
   * 支払プロバイダ・支払パラメータにnullを設定します。
   */
  private void dispose() {
    provider = null;
    parameter = null;
  }

  /**
   * キャッシャー情報をバリデートする。
   * 
   * @param cashier
   * @return
   */
  private boolean validateCashier(CashierPayment cashier) {

    String paymentMethodCode = cashier.getSelectPayment().getPaymentMethodCode();
    String paymentMethodType = cashier.getSelectPayment().getPaymentMethodType();
    return StringUtil.hasValueAllOf(paymentMethodCode, paymentMethodType);
  }

  /**
   * 受注情報をバリデートする
   * 
   * @param order
   * @return
   */
  private boolean validateOrder(OrderContainer order) {

    Long orderStatus = order.getOrderHeader().getOrderStatus();
    return orderStatus != null;
  }

  /**
   * 受注ヘッダに取引IDが含まれる場合は、その取引IDを返します。<br>
   * 取引IDが含まれない場合は、新規に取引IDを採番します。<br>
   * 例1.取引IDが含まれる場合: 取引IDを指定して単独キャンセルを行う場合<br>
   * 例2.取引IDが含まれない場合: 新規受注時に与信処理を行う場合
   * 
   * @param header
   * @return 取引ID
   */
  private Long getPaymentOrderId(OrderHeader header) {
    Long paymentOrderId = null;
    if (NumUtil.isNull(header.getPaymentOrderId())) {
      paymentOrderId = DatabaseUtil.generateSequence(SequenceType.PAYMENT_ORDER_ID);
    } else {
      paymentOrderId = header.getPaymentOrderId();
    }
    return paymentOrderId;
  }

  // 10.1.6 10284 追加 ここから
  private LoginInfo getLoginInfo() {
    LoginInfo login = loginInfo;
    if (login == null) {
      login = ServiceLoginInfo.getInstance();
    }
    return login;
  }
  // 10.1.6 10284 追加 ここまで

}
