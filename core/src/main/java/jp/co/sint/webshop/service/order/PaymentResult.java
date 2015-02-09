package jp.co.sint.webshop.service.order;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import jp.co.sint.webshop.service.result.PaymentErrorContent;

/**
 * 支払処理結果インターフェース
 * 
 * @author System Integrator Corp.
 */
public interface PaymentResult extends Serializable {

  /**
   * 支払処理で発生したエラー種別を返します。
   * 
   * @return エラー種別
   */
  List<PaymentErrorContent> getPaymentErrorList();

  /**
   * エラーの有無を判定します。
   * 
   * @return エラーの有無
   */
  boolean hasError();
  
  String getPaymentOrderId();
  
  String getPaymentReceiptNo();
  
  String getPaymentReceiptUrl();
  
  String getMessage();
  
  String getCvsCode();
  
  String getDigitalCashType();
  
  Date getPaymentLimitDate();
  
  PaymentResultType getPaymentResultType();

}
