package jp.co.sint.webshop.web.message.front.order;

import java.util.ListResourceBundle;

/**
 * メッセージリソースのクラスです。
 * 
 * @author System Integrator Corp.
 */
public class PaymentMessages_zh_TW extends ListResourceBundle {

  @Override
  protected Object[][] getContents() {
    Object[][] obj = {
        {
            "invalid_card_information_error", "客户的卡利用不习惯。"
        }, {
            "invalid_customer_name_error", "接受人(姓/名)请输入全角。"
        }, {
            "invalid_customer_name_kana_error", "接受人假名(姓/名)请输入全角片假名。"
        }, {
            "invalid_email_error", "请输入有效的邮件地址。"
        }, {
            "invalid_other_error", "{0}利用不习惯。请变更支付方式。"
        }
    };
    return obj;
  }

}
