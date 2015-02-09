package jp.co.sint.webshop.web.message.back.customer;

import java.util.ListResourceBundle;

/**
 * メッセージリソースのクラスです。
 * 
 * @author System Integrator Corp.
 */
public class CustomerMessages_ja_JP extends ListResourceBundle {

  @Override
  protected Object[][] getContents() {
    Object[][] obj = {
        // 本人アドレス削除エラーメッセージ
        {
            "delete_self_address_error", "ご本人のアドレスは削除できません。"
        },
        // 受注未確定顧客削除エラーメッセージ
        {
            "delete_customer_order_error", "売上未確定または未入金の受注があるため、退会処理に失敗しました。"
        },
        // 本人アドレス編集エラーメッセージ
        {
            "edit_self_address_error", "ご本人のアドレスは編集できません。"
        },
        // 情報メール送信対象未存在エラーメッセージ
        {
            "information_mail_no_data", "情報メール送信対象の顧客が存在しません。"
        },
        // ポイント最大値超過エラーメッセージ
        {
            "point_overflow", "登録可能なポイントの最大値({0})を超えています。"
        },
        // ポイント登録(調整入力失敗)エラーメッセージ
        {
            "point_insert_failure", "ポイントの登録に失敗しました。"
        },
        // パスワードポリシーエラー
        {
            "password_policy_error", "パスワードの入力形式が正しくありません。"
        },
        // 情報メール未存在エラー
        {
            "not_exist_information_mail_error", "送信可能な情報メールは存在しません。"
        },
        // ポイントシステム停止中登録エラー
        {
            "point_system_disabled_register", "現在、ポイントシステムは無効のためポイント付与は実行されません。"
        },
        // ポイントシステム停止中削除エラー
        {
            "point_system_disabled_delete", "現在、ポイントシステムは無効のためポイント削除は実行されません。"
        },
        // ゼロポイント追加エラー
        {
            "point_insert_zero_error", "ポイントに0を登録することはできません。"
        },
        {
          "coupon_system_disabled_register", "因当前优惠券系统不能使用，无法给与优惠券。"
        }
    };
    return obj;
  }
}
