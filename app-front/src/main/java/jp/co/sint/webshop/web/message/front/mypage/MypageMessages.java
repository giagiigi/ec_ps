package jp.co.sint.webshop.web.message.front.mypage;

import java.util.ListResourceBundle;

/**
 * メッセージリソースのクラスです。
 * 
 * @author System Integrator Corp.
 */
public class MypageMessages extends ListResourceBundle {

  @Override
  protected Object[][] getContents() {
    Object[][] obj = {
        // 本人アドレス削除エラーメッセージ
        {
            "delete_self_address_error", "ご本人のアドレスは削除できません。"
        },
        // パスワード再発行画面利用不可メッセージ
        {
            "not_used_reminder", "このURLは有効期限切れか、URLに誤りがあります。"
        },
        // お問い合わせ送信エラーメッセージ
        {
            "inquiry_error", "お問い合わせメールの送信に失敗しました。"
        },
        // 注文キャンセル不可メッセージ
        {
            "disapprove_cancel_order", "この注文はキャンセルできません。"
        },
        // 退会依頼済みメッセージ
        {
            "requested_withdrawal", "現在、退会依頼中です。"
        },
        // メールマガジン登録済みメッセージ
        {
            "mailmagazine_duplicated_error", "以下のメールマガジンはすでに配信登録しています。"
        },
        // メールマガジン未登録メッセージ
        {
            "mailmagazine_canceled_error", "以下のメールマガジンは配信登録していません。"
        },
        // 受注情報変更エラーメッセージ
        {
            "order_changed_error", "受注情報が変更されています。"
        },
        // クレジットキャンセルエラー
        {
            "credit_cancel_error", "カード会社の処理に失敗しました。もう一度キャンセルして下さい。"
        },
        // 現パスワード不一致エラー
        {
            "not_match_password_error", "入力された現パスワードと、登録されているパスワードが一致しません。"
        },
        // パスワードポリシーエラー
        {
            "password_policy_error", "パスワードの入力形式が正しくありません。"
        },
        // メールマガジン配信登録/停止完了エラー
        {
            "expired_url_error", "このURLは有効期限が切れています。もう一度、{0}の申し込みをやり直してください。"
        },
        // メールマガジン配信停止完了メッセージ
        {
            "mailmagazine_cancel_complete", "メールマガジンの配信停止を完了しました。"
        },
        // メールマガジン申し込み用メール送信エラー
        {
            "mailmagazine_sendmail_error", "{0}用のメール送信に失敗しました。"
        },
        // メールマガジン申し込み用メール送信完了メッセージ
        {
            "mailmagazine_sendmail_complete", "{0}用のメールを送信しました。メールに記載されているURLをクリックして{1}を完了してください。"
        },
        // メールマガジン大量送信エラー
        {
            "massive_send_error", "入力されたメールアドレスへのメールは、大量に連続送信されている可能性があるため、送信することができません。"
        },
        // ポイントオーバーフローメッセージ
        {
            "point_overflow_error", "ご利用可能なポイントの最大値({0})を超えています。"
        },
        // 受注未入金顧客削除エラーメッセージ
        {
            "requested_withdrawal_customer_not_payment_order_error", "未入金の注文があるため、退会を依頼できません。"
        },
        //20120523 tuxinwei add start
        {
          "customer_group_upgrades_remind_one", "もう少し消費{0}元 {1}月になれる{2}ああ!"
        },
        //20120523 tuxinwei add end
        //20120524 tuxinwei add start
        {
          "customer_group_upgrades_remind_three", "おめでとうございます、あなたになる{0}月の{1}です!"
        },
        //20120524 tuxinwei add end
        {/**2013/04/01 优惠券对应 ob add start*/
          "search_result_overflow", "検索可能上限数を超えたため検索結果{0}件中、先頭の{1}件を表示します。"
        },
        {
          "coupon_history_no_date_error", "没有发行履历。 "
        }/**2013/04/01 优惠券对应 ob add end*/
    };
    return obj;
  }
}
