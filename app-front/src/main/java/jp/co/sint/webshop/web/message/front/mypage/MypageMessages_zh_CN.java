package jp.co.sint.webshop.web.message.front.mypage;

import java.util.ListResourceBundle;

/**
 * メッセージリソースのクラスです。

 * 
 * @author System Integrator Corp.
 */
public class MypageMessages_zh_CN extends ListResourceBundle {

  @Override
  protected Object[][] getContents() {
    Object[][] obj = {
        // 本人アドレス削除エラーメッセージ
        {
            "delete_self_address_error", "本人的地址不能删除。"
        },
        // パスワード再発行画面利用不可メッセージ
        {
            "not_used_reminder", "这个URL有效期限过期或者URL错误。"
        },
        // お問い合わせ送信エラーメッセージ
        {
            "inquiry_error", "咨询邮件的发送失败了。"
        },
        // 注文キャンセル不可メッセージ
        {
            "disapprove_cancel_order", "已支付完成的订单不可取消。"
        },
        // 退会依頼済みメッセージ
        {
            "requested_withdrawal", "现在、退会依赖中。"
        },
        // メールマガジン登録済みメッセージ
        {
            "mailmagazine_duplicated_error", "以下的电子杂志已经发送登录。"
        },
        // メールマガジン未登録メッセージ

        {
            "mailmagazine_canceled_error", "以下的电子杂志没发送登录。"
        },
        // 受注情報変更エラーメッセージ
        {
            "order_changed_error", "订单信息被变更。"
        },
        // クレジットキャンセルエラー

        {
            "credit_cancel_error", "信用卡公司的处理失败了。请再次点击取消。"
        }
        // soukai add 2013/04/11 ob start
        ,
        {
          "url_out_date", "对不起！您打开的链接已经无效。"
        }
        // soukai add 2013/04/11 ob end
        ,
        // 現パスワード不一致エラー
        {
            "not_match_password_error", "输入的原密码与登录密码不相符。"
        },
        // パスワードポリシーエラー
        {
            "password_policy_error", "密码的输入格式不正确。"
        },
        // メールマガジン配信登録/停止完了エラー

        {
            "expired_url_error", "这个URL有效期限过了。请再一次，重做{0}的申请。"
        },
        // メールマガジン配信停止完了メッセージ
        {
            "mailmagazine_cancel_complete", "完成了电子杂志的停止发送。"
        },
        // メールマガジン申し込み用メール送信エラー

        {
            "mailmagazine_sendmail_error", "{0}用的有加发送失败。"
        },
        // メールマガジン申し込み用メール送信完了メッセージ

        {
            "mailmagazine_sendmail_complete", "{0}用的邮件已发送。请单击邮件中的URL完成{1}。"
        },
        // メールマガジン大量送信エラー

        {
            "massive_send_error", "输入的邮件地址可能已经被大量连续发送，所以邮件不能发送。"
        },
        // ポイントオーバーフローメッセージ
        {
            "point_overflow_error", "超过了能利用的积分的最大值({0})。"
        },
        // 受注未入金顧客削除エラーメッセージ

        {
            "requested_withdrawal_customer_not_payment_order_error", "因为有未付款的订购，不能要求退会。"
        },
        {
            "customer_group_upgrades_remind_one", "您再消费{0}元 {1}月就能成为{2}哦!"
        },
        {
            "customer_group_upgrades_remind_two", "恭喜您，您将成为{0}月的{1}! 再消费{2}元就能成为{3}哦!"
        },
        {
            "customer_group_upgrades_remind_three", "恭喜您，您将成为{0}月的{1}!"
        },
        {/**2013/04/01 优惠券对应 ob add start*/
          "search_result_overflow", "检索的数量超过了上限，所以只显示{0}件检索结果中的开头{1}件。"
        },
        {
          "coupon_exchange_success", "兑换成功。"
        },
        {
          "not_frient_use_coupon","您的朋友还未使用任何优惠劵。"
        },
        {
          "coupon_exchange_unsuccess", "优惠劵兑换失败。" 
        }
        /**2013/04/01 优惠券对应 ob add end*/
    };
    return obj;
  }
}
