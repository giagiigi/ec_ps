package jp.co.sint.webshop.web.message.back.order;

import java.util.ListResourceBundle;

/**
 * メッセージリソースのクラスです。
 * 
 * @author System Integrator Corp.
 */
public class PaymentMessages_en_US extends ListResourceBundle {

  @Override
  protected Object[][] getContents() {
    Object[][] obj = {
        {
            "invalid_card_information_error", "Your card is not available."
        },
        {
            "invalid_customer_name_error",
            "Please input the receiver(family name/given name) registered in payment method in full-size character."
        },
        {
            "invalid_customer_name_kana_error",
            "Please input the receiver kana(family name/given name) registered in payment method in full-size kana character."
        }, {
            "invalid_email_error", "The E-mail address that input in the payment method is invalid."
        }, {
            "invalid_other_error", "{0}is disabled now. Please change the payment method."
        }
    };
    return obj;
  }

}
