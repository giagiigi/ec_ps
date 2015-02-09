package jp.co.sint.webshop.ext;

import jp.co.sint.webshop.service.result.PaymentErrorContent;

public interface ErrorReasonMapper {

  PaymentErrorContent createErrorContent(String errorCode);

}
