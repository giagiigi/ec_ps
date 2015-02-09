package jp.co.sint.webshop.web.message.back.order;

import java.util.ListResourceBundle;

/**
 * メッセージリソースのクラスです。
 * 
 * @author System Integrator Corp.
 */
public class PaymentMessages_zh_CN extends ListResourceBundle {

  @Override
  protected Object[][] getContents() {
    Object[][] obj = {
        {
            "invalid_card_information_error", "顾客的信用卡不能使用。"
        }, {
            "invalid_customer_name_error", "支付方法里填入的收取人(姓/名)请填入全角。"
        }, {
            "invalid_customer_name_kana_error", "支付方法里填入的收取人片假名(姓/名)请填入全角片假名。"
        }, {
            "invalid_email_error", "支付方法里填入的邮件地址是无效的。"
        }, {
            "invalid_other_error", "{0}无法使用。请变更支付方法。"
        }
    };
    return obj;
  }

}
