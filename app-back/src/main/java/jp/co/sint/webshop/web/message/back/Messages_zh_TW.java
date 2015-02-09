package jp.co.sint.webshop.web.message.back;

import java.util.ListResourceBundle;

/**
 * @author System Integrator Corp.
 */
public class Messages_zh_TW extends ListResourceBundle {

  protected Object[][] getContents() {
    return new Object[][] {
        {// 登録完了メッセージ
            "register_complete", "{0}已登录。"
        }, {// 更新完了メッセージ
            "update_complete", "{0}已更新。"
        }, {// 削除完了メッセージ
            "delete_complete", "{0}已删除。"
        }, {// キャンセル完了メッセージ
            "cancel_complete", "{0}已取消。"
        }, {// ファイルアップロード完了メッセージ
            "upload_complete", "{0}已上传。"
        }, {// ファイルアップロード失敗エラーメッセージ
            "upload_failed", "{0}上传失败。"
        }, {// CSV出力完了メッセージ
            "csv_export_complete", "{0}已导出CSV。"
        }, {// CSV取込成功メッセージ
            "csv_import_complete", "CSV已读取。"
        }, {// CSV取込一部成功メッセージ
            "csv_import_partial", "一部分CSV读取成功。"
        }, {// CSV取込失敗エラーメッセージ
            "csv_import_failed", "CSV读取失败。"
        }, {// 更新失敗エラーメッセージ
            "update_failed", "{0}更新失败。"
        }, {// データ重複エラーメッセージ
            "duplicated_register_default_error", "已经登录过的数据。"
        }, {// データ重複エラーメッセージ
            "duplicated_register_error", "已经登录过的{0}。"
        }, {// 該当データ未存在エラーメッセージ
            "no_data_default_error", "该数据不存在，已经删除。"
        }, {// 該当データ未存在エラーメッセージ
            "no_data_error", "该{0}不存在、已经删除。"
        }, {// 初期データ削除エラーメッセージ
            "default_delete", "{0}的初始数据不能删除。"
        }, {// データ削除エラーメッセージ
            "delete_error", "{0}删除失败。"
        }, {// データ未選択時エラーメッセージ
            "no_checked", "{0}没被选择。"
        }, {// 検索条件不正エラーメッセージ
            "searchCondition_error", "检索条件不正确。"
        }, {// 大小不正エラーメッセージ
            "comparison_error", "{0}大小关系不正确。"
        }, {// 指定期間不正エラーメッセージ
            "period_error", "日期的大小关系不正确。"
        }, {// 指定期間不正エラーメッセージ(パラメータ有り)
            "period_error_with_param", "{0}大小关系不正确。"
        }, {// デフォルト顧客グループ削除時メッセージ
            "delete_default_customergroup", "规定的顾客组不能删除。"
        }, {// 会員登録済み顧客グループ削除時メッセージ
            "delete_registered_customergroup", "会员被登录的顾客小组不能删除。"
        }, {// 本人アドレス削除エラーメッセージ
            "delete_self_address_error", "本人的地址不能删除。"
        }, {// 保有ポイントマイナスエラー
            "rest_point_minus_error", "持有积分不能设置为负值。"
        }, {// 未入金・売上未確定データ有エラーメッセージ
            "payment_or_not_fixed_error", "因为订单中有未到货款或者销售额未确定，所以不能删除。"
        }, {// ショップコード非存在エラー
            "no_shop_code_error", "店铺编号不存在。"
        }, {// メールキュー登録メッセージ
            "register_mailque_complete", "在邮件送信列表里登录。"
        }, {// メールキュー登録失敗メッセージ
            "register_mailqueue_failed", "{0}的邮件队列登录失败。"
        }, {// 入金確認メール送信完了メッセージ
            "send_confirm_mail_complete", "付款确认邮件已发送。"
        }, {// 入金督促メール送信完了メッセージ
            "send_remind_mail_complete", "付款督促邮件已发送。"
        }, {// 出荷報告SMS送信完了メッセージ
            "send_shipping_mail_complete", "出货编号:{0}的出货报告邮件已发送。"
        }, {// 出荷報告SMS送信完了メッセージ(番号無し)
            "send_shipping_mail_without_no", "出货报告邮件已发送。"
        },
        //Add by V10-CH start
        {// メールキュー登録メッセージ
          "register_smsque_complete", "在SMS发送列表里登录。"
        }, {// メールキュー登録失敗メッセージ
          "register_smsqueue_failed", "{0}的SMS队列登录失败。"
        },
        {// 出荷報告メール送信完了メッセージ
          "send_shipping_sms_complete", "出货编号:{0}的出货报告SMS已发送。"
        }, {// 出荷報告メール送信完了メッセージ(番号無し)
          "send_shipping_sms_without_no", "出货报告SMS已发送。"
        },
        //Add by V10-CH end
        {// 必須入力エラーメッセージ
            "required_error", "{0}必须输入。"
        }, {// 0件削除エラー
            "zero_data_delete_error", "{0}删除掉就只有0件了，所以不能删除。"
        }, {// 0件削除エラー
            "delete_max_commission_error", "手续费是最高了，所以不能删除。"
        }, {// パスワード不一致エラーメッセージ
            "not_same_password", "输入的密码和确认密码不一样。"
        }, {// パスワード最小値未満エラーメッセージ
            "less_than_password", "密码、请输入{0}位以上的半角英数文字。"
        },{//电话号码与手机号码至少输入一个
            "no_number","電話號碼與手機號碼至少輸入一個。"
        }, {//請輸入有效的電話號碼
            "true_number","請輸入有效的電話號碼。"
        },{// 検索結果なしメッセージ
            "search_result_not_found", "检索结果是0件。"
        }, {// 検索結果オーバーフローメッセージ
            "search_result_overflow", "检索的数量超过了上限，所以只显示{0}件检索结果中的开头{1}件。"
        }, {// URL不正エラーメッセージ
            "bad_url", "不正确的url。"
        }, {// CSV入出力権限なしエラーメッセージ
            "csv_io_no_auth", "没有该CSV入出力的权限。"
        }, {// 入出力対象なしエラーメッセージ
            "no_io_subject", "没有可以入出力的项目。<br />请确认权限设置是否正确。"
        }, {// 該当CSVデータなしエラーメッセージ
            "csv_output_no_data", "没有该CSV数据。"
        }, {// 文字数超過エラーメッセージ
            "string_over", "{0}请输入{1}文字以内。"
        }, {// データ連携中メッセージ
            "data_transported", "数据已经互相关联。"
        }, {// データ連携中メッセージ
            "format_error", "{0}格式有错误。"
        }, {// 登録確認メッセージ
            "register_confirm", "登录这里的内容。可以吗？"
        }, {// コンテンツアップロード時キャンペーン未存在エラーメッセージ
            "no_campaign_error", "活动没有登录，不能上传内容。请登录活动。"
        }, {// 削除完了メッセージ
            "customer_withdrawed_complete", "顾客退会已处理。"
        }, {// ショップ登録完了メッセージ
            "shop_register_complete", "商店的登录完成了。请从管理用户登录菜单登录{0}的管理用户。"
        }, {// 受注修正メール送信完了メッセージ
            "send_modify_mail_complete", "向顾客番号为{0} {1} {2}的顾客再次发送了购买订单邮件。"
        }, {// 在线客服使用时，SCRIPT内容（尾部）必须输入
            "Script_text", "{0}使用时，SCRIPT内容必须输入。"
        },{// 使用积分请设定为{0}的倍数
            "point_small_not_error", "使用积分请设定为{0}的倍数。"
        },
    };
  }

}
