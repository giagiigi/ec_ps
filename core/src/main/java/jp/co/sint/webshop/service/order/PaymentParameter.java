package jp.co.sint.webshop.service.order;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import jp.co.sint.webshop.service.cart.CashierPayment;
import jp.co.sint.webshop.utility.DateUtil;

/**
 * 決済で必要なパラメータのセットです。
 * 
 * @author System Integrator Corp.
 */
public abstract class PaymentParameter implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private HashMap<String, String> parameters = new HashMap<String, String>();
  
  /** ショップコード */
  private String shopCode;
  
  /** 受注No */
  private String orderId;

  /** 決済金額 */
  private BigDecimal amount;
  
  /** マーチャントID */
  private String merchantId;
  
  /** 秘密鍵 */
  private String secretKey;
  
  /** 支払期限 */
  private Date paymentLimitDate;
  
  
  
  /**
   * shopCodeを返します。
   * @return the shopCode
   */
  public String getShopCode() {
    return shopCode;
  }


  
  /**
   * shopCodeを設定します。
   * @param shopCode 設定する shopCode
   */
  public void setShopCode(String shopCode) {
    this.shopCode = shopCode;
  }


  /**
   * amountを取得します。
   *
   * @return amount
   */
  public BigDecimal getAmount() {
    return amount;
  }

  
  /**
   * amountを設定します。
   *
   * @param amount 設定する amount
   */
  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  
  /**
   * orderIdを取得します。
   *
   * @return orderId
   */
  public String getOrderId() {
    return orderId;
  }

  
  /**
   * orderIdを設定します。
   *
   * @param orderId 設定する orderId
   */
  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }

  /**
   * 決済パラメータのセットを表すMapオブジェクトを返します。
   * マーチャントID、秘密鍵が含まれます。
   */
  public Map<String, String> getParameters() {
    return parameters;
  }
  
  /**
   * キャッシャー支払情報を設定します。
   * @param cashier
   */
  public abstract void setCashierPayment(CashierPayment cashier);

  /**
   * 受注情報を設定します。
   * @param orderContainer
   */
  public abstract void setOrderContainer(OrderContainer orderContainer);

  
  /**
   * merchantIdを返します。
   * @return the merchantId
   */
  public String getMerchantId() {
    return merchantId;
  }


  
  /**
   * secretKeyを返します。
   * @return the secretKey
   */
  public String getSecretKey() {
    return secretKey;
  }


  
  /**
   * merchantIdを設定します。
   * @param merchantId 設定する merchantId
   */
  public void setMerchantId(String merchantId) {
    this.merchantId = merchantId;
  }


  
  /**
   * secretKeyを設定します。
   * @param secretKey 設定する secretKey
   */
  public void setSecretKey(String secretKey) {
    this.secretKey = secretKey;
  }


  
  /**
   * paymentLimitDateを返します。
   * @return the paymentLimitDate
   */
  public Date getPaymentLimitDate() {
    return DateUtil.immutableCopy(paymentLimitDate);
  }


  
  /**
   * paymentLimitDateを設定します。
   * @param paymentLimitDate 設定する paymentLimitDate
   */
  public void setPaymentLimitDate(Date paymentLimitDate) {
    this.paymentLimitDate = DateUtil.immutableCopy(paymentLimitDate);
  }
  
}
