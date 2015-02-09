package jp.co.sint.webshop.web.message.front.mypage;

import java.util.ListResourceBundle;

/**
 * メッセージリソースのクラスです。

 * 
 * @author System Integrator Corp.
 */
public class MypageMessages_en_US extends ListResourceBundle {

  @Override
  protected Object[][] getContents() {
    Object[][] obj = {
        // 本人アドレス削除エラーメッセージ
        {
            "delete_self_address_error", "You can not delete your own address."
        },
        // パスワード再発行画面利用不可メッセージ

        {
            "not_used_reminder", "This URL may be expired or wrong"
        },
        // お問い合わせ送信エラーメッセージ
        {
            "inquiry_error", "Failed in sending the inquiry E-mail."
        },
        // 注文キャンセル不可メッセージ
        {
            "disapprove_cancel_order", "You can not cancel this order."
        },
        // 退会依頼済みメッセージ
        {
            "requested_withdrawal", "Withdrawal request is processing now."
        },
        // メールマガジン登録済みメッセージ
        {
            "mailmagazine_duplicated_error", "The follwing mail magazine has already been registered for delivery."
        },
        // メールマガジン未登録メッセージ

        {
            "mailmagazine_canceled_error", "The following mail magazine has not been registered for delivery."
        },
        // 受注情報変更エラーメッセージ
        {
            "order_changed_error", "The order info has been modified."
        },
        // クレジットキャンセルエラー

        {
            "credit_cancel_error", "Failed in processing the card company. Please cancel again."
        },
        // 現パスワード不一致エラー
        {
            "not_match_password_error", "Current password is different form the registered password."
        },
        // パスワードポリシーエラー
        {
            "password_policy_error", "The password format is entered incorrectly."
        },
        // メールマガジン配信登録/停止完了エラー

        {
            "expired_url_error", "This URL has already been expired. Please retry the application for {0}."
        },
        // メールマガジン配信停止完了メッセージ
        {
            "mailmagazine_cancel_complete", "Completed the cancel of mail magazine delivery."
        },
        // メールマガジン申し込み用メール送信エラー

        {
            "mailmagazine_sendmail_error", "Failed in sending E-mail for {0}."
        },
        // メールマガジン申し込み用メール送信完了メッセージ

        {
            "mailmagazine_sendmail_complete",
            "The mail for {0} has been sent. Please finish {1} by clicking the URL written in the Email."
        },
        // メールマガジン大量送信エラー

        {
            "massive_send_error",
            "Unable to send an Email to the intended adress, because there may be the possibility of"
            + " massive continuous transmission."
        },
        // ポイントオーバーフローメッセージ
        {
            "point_overflow_error", "Exceeded The maximum point value({0}) you can use."
        },
        // 受注未入金顧客削除エラーメッセージ

        {
            "requested_withdrawal_customer_not_payment_order_error",
            "Unable to request withdrawal, because there is the unpaid order."
        },
        //20120523 tuxinwei add start
        {
          "customer_group_upgrades_remind_one", "You spend another{0}yuan {1}Month can be{2}oh!"
        },
        //20120523 tuxinwei add end
        //20120524 tuxinwei add start
        {
          "customer_group_upgrades_remind_three", "Congratulations you, you will be {0} {1} of the month!"
        }
        
        //20120524 tuxinwei add end
        // soukai add 2013/04/11 ob start
        ,
        {
          "url_out_date", "sorry！url is error."
        }
        // soukai add 2013/04/11 ob end
        ,
        {
            "customer_group_upgrades_remind_one", "Spend {0}RMB and you will be a {2} from {1}"
        },
        {
            "customer_group_upgrades_remind_two", "Congratulations! You will be a {1} from {0}  Spend {2}RMB and you will be a {3}."
        },
        {
            "customer_group_upgrades_remind_three", "Congratulations! You will be a {1} from {0} "
        }, {/**2013/04/01 优惠券对应 ob add start*/
          "search_result_overflow",
          "The searching result is bigger than the upper limit, "
              + "so only {1} items at the beginning of {0} searching results can be displayed."
        },
        {
          "coupon_exchange_success", "Redeemed Successfully"
        },
        {
          "not_frient_use_coupon","Your friends have not used any of the coupons."
        },
        {
          "coupon_exchange_unsuccess", "Redeem coupon failed." 
        }
        /**2013/04/01 优惠券对应 ob add end*/
    };
    return obj;
  }
}
