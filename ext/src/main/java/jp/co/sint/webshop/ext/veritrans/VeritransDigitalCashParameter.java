package jp.co.sint.webshop.ext.veritrans;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import jp.co.sint.webshop.data.dto.Shop;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceLoginInfo;
import jp.co.sint.webshop.service.SiteManagementService;
import jp.co.sint.webshop.service.cart.CashierPayment;
import jp.co.sint.webshop.service.cart.CashierPayment.CashierPaymentTypeDigitalCash;
import jp.co.sint.webshop.service.order.OrderContainer;
import jp.co.sint.webshop.service.order.PaymentParameter;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.veritrans.em.gwlib.Transaction;

public class VeritransDigitalCashParameter extends PaymentParameter {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /** 電子マネータイプ Edy */
  private static final String EM_TYPE_EDY = "1";

  /** 電子マネータイプ モバイルSuica */
  private static final String EM_TYPE_MOBILE_SUICA = "2";

  /** 返金取引ID */
  private String returnOrderId;

  /** メールアドレス */
  private String email;

  /** 画面タイトル */
  private String screenTitle;

  /** 支払完了通知URL */
  private String completeNoticeUrl;

  /** 電子マネー区分 */
  private VeritransDigitalCashType digitalCashType;

  private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyyMMddHHmmss");

  /**
   * completeNoticeUrlを取得します。
   * 
   * @return completeNoticeUrl
   */

  public String getCompleteNoticeUrl() {
    return completeNoticeUrl;
  }

  /**
   * digitalCashTypeを取得します。
   * 
   * @return digitalCashType
   */

  public VeritransDigitalCashType getDigitalCashType() {
    return digitalCashType;
  }

  /**
   * emailを取得します。
   * 
   * @return email
   */

  public String getEmail() {
    return email;
  }

  /**
   * returnOrderIdを取得します。
   * 
   * @return returnOrderId
   */

  public String getReturnOrderId() {
    return returnOrderId;
  }

  /**
   * screenTitleを取得します。
   * 
   * @return screenTitle
   */

  public String getScreenTitle() {
    return screenTitle;
  }

  /**
   * completeNoticeUrlを設定します。
   * 
   * @param completeNoticeUrl
   *          completeNoticeUrl
   */
  public void setCompleteNoticeUrl(String completeNoticeUrl) {
    this.completeNoticeUrl = completeNoticeUrl;
  }

  /**
   * digitalCashTypeを設定します。
   * 
   * @param digitalCashType
   *          digitalCashType
   */
  public void setDigitalCashType(VeritransDigitalCashType digitalCashType) {
    this.digitalCashType = digitalCashType;
  }

  /**
   * emailを設定します。
   * 
   * @param email
   *          email
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * returnOrderIdを設定します。
   * 
   * @param returnOrderId
   *          returnOrderId
   */
  public void setReturnOrderId(String returnOrderId) {
    this.returnOrderId = returnOrderId;
  }

  /**
   * screenTitleを設定します。
   * 
   * @param screenTitle
   *          screenTitle
   */
  public void setScreenTitle(String screenTitle) {
    this.screenTitle = screenTitle;
  }

  public Map<String, String> getArgs(int oper) {
    Map<String, String> map = null;
    switch (oper) {
      case Transaction.CMD_ENTRY:
        map = getEntryRequestParameter();
        break;
      case Transaction.CMD_RETURN:
        map = getCancelRequestParameter();
        break;
      default:
        break;
    }
    return map;
  }

  /**
   * 決済申込電文用のパラメータを返します <BR>
   * 
   * @return
   */
  private Map<String, String> getEntryRequestParameter() {
    Map<String, String> map = new HashMap<String, String>();

    map.put(Transaction.REQ_ORDER_ID, this.getOrderId());
    map.put(Transaction.REQ_AMOUNT, String.valueOf(this.getAmount()));
    map.put(Transaction.REQ_SETTLEMENT_METHOD, this.getDigitalCashType().getValue());

    switch (this.getDigitalCashType()) {
      case MOBILE_EDY:
        map.put(Transaction.REQ_EM_TYPE, EM_TYPE_EDY);
        map.put(Transaction.REQ_SETTLEMENT_LIMIT, FORMATTER.format(this.getPaymentLimitDate()));
        map.put(Transaction.REQ_MAIL_ADDR, this.getEmail());
        break;
      case MAIL_SUICA:
        map.put(Transaction.REQ_EM_TYPE, EM_TYPE_MOBILE_SUICA);
        map.put(Transaction.REQ_SETTLEMENT_LIMIT, FORMATTER.format(this.getPaymentLimitDate()));
        map.put(Transaction.REQ_MAIL_ADDR, this.getEmail());
        map.put(Transaction.REQ_SCREEN_TITLE, this.getScreenTitle());
        break;
      default:
        break;
    }
    return map;
  }

  /**
   * 返金申込電文用のパラメータを返します <BR>
   * 
   * @return
   */
  private Map<String, String> getCancelRequestParameter() {
    Map<String, String> map = new HashMap<String, String>();

    map.put(Transaction.REQ_ORDER_ID, this.getOrderId());
    map.put(Transaction.REQ_RETURN_ORDER_ID, this.getReturnOrderId());
    map.put(Transaction.REQ_AMOUNT, String.valueOf(this.getAmount()));

    if (this.getDigitalCashType() == VeritransDigitalCashType.MAIL_SUICA) {
      map.put(Transaction.REQ_EM_TYPE, EM_TYPE_MOBILE_SUICA);
      map.put(Transaction.REQ_SETTLEMENT_METHOD, this.getDigitalCashType().getValue());
      map.put(Transaction.REQ_SETTLEMENT_LIMIT, FORMATTER.format(this.getPaymentLimitDate()));
      map.put(Transaction.REQ_SCREEN_TITLE, this.getScreenTitle());
    } else {
      map.put(Transaction.REQ_EM_TYPE, EM_TYPE_EDY);
    }

    return map;
  }

  @Override
  public void setCashierPayment(CashierPayment cashier) {
    CashierPaymentTypeDigitalCash digitalCash = (CashierPaymentTypeDigitalCash) cashier.getSelectPayment();

    this.setDigitalCashType(VeritransDigitalCashType.fromValue(digitalCash.getDigitalCashType()));
    this.setEmail(digitalCash.getDigitalCashPaymentEmail());

    // MobileEdy,CyberEdyかどうかを判定し、モバイル、フロントのトップページを設定
    String completeUrl = "";
    if (digitalCash.getDigitalCashType().equals(VeritransDigitalCashType.MOBILE_EDY.getValue())) {
      completeUrl = DIContainer.getWebshopConfig().getMobileTopPageUrl() + "/app/common/index/";
    }
    this.setCompleteNoticeUrl(completeUrl);

    // サイト名称を設定
    SiteManagementService service = ServiceLocator.getSiteManagementService(ServiceLoginInfo.getInstance());
    Shop site = service.getSite();
    this.setScreenTitle(site.getShopName());

  }

  @Override
  public void setOrderContainer(OrderContainer orderContainer) {
    // 電子マネー決済の場合は処理なし
  }

}
