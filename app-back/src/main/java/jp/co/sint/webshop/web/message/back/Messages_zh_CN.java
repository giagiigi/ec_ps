package jp.co.sint.webshop.web.message.back;

import java.util.ListResourceBundle;

/**
 * @author System Integrator Corp.
 */
public class Messages_zh_CN extends ListResourceBundle {

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
        },
        //2013/04/01 优惠券对应 ob add start
        {// データ重複エラーメッセージ
          "register_error", "{0}已存在。"
        }
        //2013/04/01 优惠券对应 ob add end
        , {// 該当データ未存在エラーメッセージ
            "no_data_default_error", "该数据不存在，已经删除。"
        }, {// 該当データ未存在エラーメッセージ
            "no_data_error", "该{0}不存在、已经删除。"
        }, {// 初期データ削除エラーメッセージ
            "default_delete", "{0}的初始数据不能删除。"
        }, {// データ削除エラーメッセージ
            "delete_error", "{0}删除失败。"
        }, {// データ未選択時エラーメッセージ
            "no_checked", "{0}没被选择。"
        // 2012/12/13 促销对应 ob add start
        }, {// データ未選択時エラーメッセージ
            "no_checked_new", "请选择对象数据。"
        }, {
            "register_failed_error","{0}登录失败。"
        }, {
            "register_seccess","{0}登录成功。"
        // 2012/12/13 促销对应 ob add end
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
        }, {// 出荷報告メール送信完了メッセージ
            "send_shipping_mail_complete", "出货编号:{0}的出货报告邮件已发送。"
        }, {// 出荷報告メール送信完了メッセージ(番号無し)
            "send_shipping_mail_without_no", "出货报告邮件已发送。"
        }, 
        //Add by V10-CH start
        {// メールキュー登録メッセージ
          "register_smsque_complete", "在SMS发送列表里登录。"
        }, {// メールキュー登録失敗メッセージ
          "register_smsqueue_failed", "{0}的SMS队列登录失败。"
        },
        {// 出荷報告SMS送信完了メッセージ(番号無し)
          "send_shipping_sms_without_no", "出货报告SMS已发送。"
        }, 
        {// 出荷報告SMS送信完了メッセージ
          "send_shipping_sms_complete", "出货编号:{0}的出货报告SMS已发送。"
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
        }, {//email不一致 add by yl 2111212
            "not_same_email", "输入的邮件地址和确认邮件地址不一样。"
        },
        {//add by lc 2012-03-08 start
            "name_length_err", "{0}最多输入14个汉字或者20个英文数字。"
        },
        {
            "address_length_err", "{0}最多输入100个汉字或者200个英文数字。"
        },
        {
          "invoce_length_err", "{0}最多输入35个汉字或者70个英文数字。"
        },//add by lc 2012-03-08 end
        {// パスワード最小値未満エラーメッセージ
            "less_than_password", "密码、请输入{0}位以上的半角英数文字。"
        }, {//电话号码与手机号码至少输入一个
            "no_number","电话号码与手机号码至少输入一个。"
        }, {//电话号码与手机号码至少输入一个
            "true_number","请输入有效的电话号码。"
        }, {// 検索結果なしメッセージ
            "search_result_not_found", "检索结果是0件。"
        }, {// 検索結果オーバーフローメッセージ
            "search_result_overflow", "检索的数量超过了上限，所以只显示{0}件检索结果中的开头{1}件。"
        }, {// URL不正エラーメッセージ
            "bad_url", "不正确的url。"
        }, {// CSV入出力権限なしエラーメッセージ
            "csv_io_no_auth", "没有该CSV导入导出的权限。"
        }, {// 入出力対象なしエラーメッセージ
            "no_io_subject", "没有可以导入导出的项目。<br />请确认权限设置是否正确。"
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
            "send_modify_mail_complete", "向顾客编号为{0} {1} {2}的顾客再次发送了购买订单邮件。"
        }, {// 在线客服使用时，SCRIPT内容（头部）必须输入。
            "Script_text1", "在线客服使用时，SCRIPT内容（头部）必须输入。"
        }, {// 在线客服使用时，SCRIPT内容（尾部）必须输入
            "Script_text2", "在线客服使用时，SCRIPT内容（尾部）必须输入。"
        }, {// 在线客服使用时，SCRIPT内容（尾部）必须输入
            "Script_text", "{0}使用时，SCRIPT内容必须输入。"
        }, {// 使用积分请设定为{0}的倍数
            "point_small_not_error", "使用积分请设定为{0}的倍数。"
        }, {// 同期化多选框非空判断
            "commodity_syncro_null_error", "{0}多选框必须至少选中一个。"
        }, {// 品牌编号不存在 os014 2012-01-31 
            "c_commodity_header_edit_brandcode_error", "{0}，品牌编号不存在。"
        }, {// soukai update 2012/01/31 ob start
        	 // 检索结果超过上限时提示错误信息
        	"search_result_beyond_cap", "检索结果超过上限，请输入详细的检索条件。"
        	// soukai update 2012/01/31 ob end
          //20120204 ob add start
        }, {
          "img_err", "{0} 读入出错 - {1}。"
        }, {
          "img_upload_over", "[{0}] 商品图片上传批处理执行完成。"
        }, {
          "img_upload_success", "{0}件完成处理，正常结束。"
        }, {
          "img_upload_final", "[{0}] 件处理中发生错误，异常结束。"
        }
          //20120204 ob add end
         ,{
           "commodity_cyncro_complete","商品同期化结束"
         }
    };
  }

}
