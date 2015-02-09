package jp.co.sint.webshop.web.message.back.customer;

import java.util.ListResourceBundle;

/**
 * メッセージリソースのクラスです。
 * 
 * @author System Integrator Corp.
 */
public class CustomerMessages_zh_CN extends ListResourceBundle {

  @Override
  protected Object[][] getContents() {
    Object[][] obj = {
        // 本人アドレス削除エラーメッセージ
        {
            "delete_self_address_error", "不能删除本人的邮件地址。"
        },
        // 受注未確定顧客削除エラーメッセージ
        {
            "delete_customer_order_error", "因有未出货的订单，所以退会处理失败了。"
        },
        // 本人アドレス編集エラーメッセージ
        {
            "edit_self_address_error", "本人的邮件地址不能编辑。"
        },
        // 情報メール送信対象未存在エラーメッセージ
        {
            "information_mail_no_data", "信息邮件送信对象的客户不存在。"
        },// 情報メール送信対象未存在エラーメッセージ

        {
            "information_mail_error_data", "信息邮件确认不正确。"
        },
        // ポイント最大値超過エラーメッセージ
        {
            "point_overflow", "已超过了积分可以登录的最大值({0})。"
        },
        // ポイント登録(調整入力失敗)エラーメッセージ
        {
            "point_insert_failure", "积分登录失败了。"
        },
        // パスワードポリシーエラー
        {
            "password_policy_error", "密码的输入形式不正确。"
        },
        // 情報メール未存在エラー
        {
            "not_exist_information_mail_error", "送信可能的信息邮件不存在。"
        },
        // ポイントシステム停止中登録エラー
        {
            "point_system_disabled_register", "因现在积分是无效状态，无法执行给与积分。"
        },
        // ポイントシステム停止中削除エラー
        {
            "point_system_disabled_delete", "因现在积分系统是无效状态，无法删除积分。"
        },
        // ゼロポイント追加エラー
        {
            "point_insert_zero_error", "积分里不能登陆０。"
        },
        {
            "coupon_system_disabled_register", "因当前优惠券系统不能使用，无法给与优惠券。"
        },
        {
            "coupon_issue_not_exists_register", "指定的优惠券编号不存在。"
        },
        {
            "out_of_coupon_issue_date", "指定的优惠券不在发行期间内。"
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
