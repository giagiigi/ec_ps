package jp.co.sint.webshop.web.message.front.order;

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
            "invalid_card_information_error", "Your card can not be used."
        }, {
            "invalid_customer_name_error", "Please enter the Recipient(family name/given name) in Full character."
        }, {
            "invalid_customer_name_kana_error", "Please enter the Recipient Kana(family name/given name) in Full Kana character."
        }, {
            "invalid_email_error", "Please input valid E-mail address."
        }, {
            "invalid_other_error", "Unable to use {0}. Please change the payment method."
        }
    };
    return obj;
  }

}
