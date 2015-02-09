package jp.co.sint.webshop.ext.veritrans;

import java.util.HashMap;
import java.util.Map;

import jp.co.sint.webshop.ext.ErrorReasonMapper;
import jp.co.sint.webshop.service.result.PaymentErrorContent;

public final class VeritransErrorReasonMapper implements ErrorReasonMapper {

  private Map<String, PaymentErrorContent> map;
  
  private VeritransErrorReasonMapper(Map<String, PaymentErrorContent> map) {
    this.map = map;
  }

  public static ErrorReasonMapper getInstance(Class<?> targetClass) {
    
    if (VeritransCard.class.equals(targetClass)) {
      return new VeritransErrorReasonMapper(getCardErrorMap());
    } else if (VeritransCvs.class.equals(targetClass)) {
      return new VeritransErrorReasonMapper(getCvsErrorMap());
    } else if (VeritransDigitalCash.class.equals(targetClass)) {
      return new VeritransErrorReasonMapper(getDigitalCashErrorMap());
    } else {
      return null;
    }
  }
  
  public PaymentErrorContent createErrorContent(String errorCode) {
    if (map.containsKey(errorCode)) {
      return map.get(errorCode);
    } else {
      return PaymentErrorContent.INVALID_OTHER_PARAMETER_ERROR;
    }
  }

  private static Map<String, PaymentErrorContent> getCardErrorMap() {
    Map<String, PaymentErrorContent> map = new HashMap<String, PaymentErrorContent>();
    map.put("101", PaymentErrorContent.INVALID_CARD_INFORMATION_ERROR);
    map.put("110", PaymentErrorContent.INVALID_CARD_INFORMATION_ERROR);
    map.put("112", PaymentErrorContent.INVALID_CARD_INFORMATION_ERROR);
    map.put("115", PaymentErrorContent.INVALID_CARD_INFORMATION_ERROR);
    map.put("121", PaymentErrorContent.INVALID_CARD_INFORMATION_ERROR);
    return map;
  }
  private static Map<String, PaymentErrorContent> getCvsErrorMap() {
    Map<String, PaymentErrorContent> map = new HashMap<String, PaymentErrorContent>();
    map.put("V09", PaymentErrorContent.INVALID_CUSTOMER_NAME_ERROR);
    map.put("V10", PaymentErrorContent.INVALID_CUSTOMER_NAME_ERROR);
    map.put("V13", PaymentErrorContent.INVALID_PAYMENT_LIMIT_DATE_ERROR);
    return map;
  }
  private static Map<String, PaymentErrorContent> getDigitalCashErrorMap() {
    Map<String, PaymentErrorContent> map = new HashMap<String, PaymentErrorContent>();
    map.put("V14", PaymentErrorContent.INVALID_EMAIL_ERROR);
    return map;
  }
 
}
