package jp.co.sint.webshop.web.message.back.customer;

import java.util.ListResourceBundle;

/**
 * メッセージリソースのクラスです。
 * 
 * @author System Integrator Corp.
 */
public class CustomerMessages_en_US extends ListResourceBundle {

  @Override
  protected Object[][] getContents() {
    Object[][] obj = {
        // 本人アドレス削除エラーメッセージ
        {
            "delete_self_address_error", "Unable to delete self address."
        },
        // 受注未確定顧客削除エラーメッセージ
        {
            "delete_customer_order_error", "Failed in withdrawal. There is a sales unconfirmed order or unpaid order."
        },
        // 本人アドレス編集エラーメッセージ
        {
            "edit_self_address_error", "Unable to edit self address."
        },
        // 情報メール送信対象未存在エラーメッセージ
        {
            "information_mail_no_data", "There is no customer of info mail target exist."
        },
        // ポイント最大値超過エラーメッセージ
        {
            "point_overflow", "It has exceeded the max value point({0})."
        },
        // ポイント登録(調整入力失敗)エラーメッセージ
        {
            "point_insert_failure", "Point registration falure."
        },
        // パスワードポリシーエラー
        {
            "password_policy_error", "Invalid password format."
        },
        // 情報メール未存在エラー
        {
            "not_exist_information_mail_error", "There is no info mail can be sent exist."
        },
        // ポイントシステム停止中登録エラー
        {
            "point_system_disabled_register", "Unable to invest point, since the point system is disabled now."
        },
        // ポイントシステム停止中削除エラー
        {
            "point_system_disabled_delete", "Unable to delete the point, since the point system is disabled now."
        },
        // ゼロポイント追加エラー
        {
            "point_insert_zero_error", "Unable to register 0 point."
        },
        {
          "coupon_system_disabled_register", "因当前优惠券系统不能使用，无法给与优惠券。"
        },
        {
          "coupon_issue_not_exists_register", "指定的优惠券编号不存在。"
        },
        {
            "out_of_coupon_issue_date", "指定的优惠券不在发行期间内。"
        }
    };
    return obj;
  }
}
