package jp.co.sint.webshop.web.message.back.order;

import java.util.ListResourceBundle;

/**
 * メッセージリソースのクラスです。
 * 
 * @author System Integrator Corp.
 */
public class PaymentMessages_ja_JP extends ListResourceBundle {

  @Override
  protected Object[][] getContents() {
    Object[][] obj = {
        {
            "invalid_card_information_error", "お客様のカードはご利用になれません。"
        }, {
            "invalid_customer_name_error", "支払方法で入力した受取人(姓/名)は全角で入力してください。"
        }, {
            "invalid_customer_name_kana_error", "支払方法で入力した受取人カナ(姓/名)は全角カタカナで入力してください。"
        }, {
            "invalid_email_error", "支払方法で入力したメールアドレスは有効ではありません。"
        }, {
            "invalid_other_error", "{0}は、ご利用になれません。支払方法を変更してください。"
        }
    };
    return obj;
  }

}
