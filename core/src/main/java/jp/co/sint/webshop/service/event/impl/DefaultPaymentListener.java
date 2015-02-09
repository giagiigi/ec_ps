package jp.co.sint.webshop.service.event.impl;

import jp.co.sint.webshop.service.event.PaymentEvent;
import jp.co.sint.webshop.service.event.PaymentListener;
import jp.co.sint.webshop.text.Messages;

import org.apache.log4j.Logger;

public class DefaultPaymentListener implements PaymentListener {

  private static final long serialVersionUID = 1L;

  public DefaultPaymentListener() {
    Logger logger = Logger.getLogger(this.getClass());
    logger.info(Messages.log("service.event.impl.DefaultPaymentListener.0"));
  }
  
  public void paymentDateUpdated(PaymentEvent event) {
    Logger logger = Logger.getLogger(this.getClass());
    logger.info(Messages.log("service.event.impl.DefaultPaymentListener.1"));
  }

}
