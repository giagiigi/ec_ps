package jp.co.sint.webshop.web.message.back;

import java.util.ListResourceBundle;

/**
 * @author System Integrator Corp.
 */
public class Messages_en_US extends ListResourceBundle {

  protected Object[][] getContents() {
    return new Object[][] {
        {// 登録完了メッセージ
            "register_complete", "{0}has been registered."
        }, {// 更新完了メッセージ
            "update_complete", "{0}has been updated."
        }, {// 削除完了メッセージ
            "delete_complete", "{0}has been deleted."
        }, {// キャンセル完了メッセージ
            "cancel_complete", "{0}has been canceled."
        }, {// ファイルアップロード完了メッセージ
            "upload_complete", "{0}has been uploaded."
        }, {// ファイルアップロード失敗エラーメッセージ
            "upload_failed", "{0} has been failed to upload."
        }, {// CSV出力完了メッセージ
            "csv_export_complete", "{0} has been exported completely."
        }, {// CSV取込成功メッセージ
            "csv_import_complete", "CSV has been imported successfully."
        }, {// CSV取込一部成功メッセージ
            "csv_import_partial", "Part of CSV has been imported successfully."
        }, {// CSV取込失敗エラーメッセージ
            "csv_import_failed", "Failed to read CSV."
        }, {// 更新失敗エラーメッセージ
            "update_failed", "{0} has been failed to update."
        }, {// データ重複エラーメッセージ
            "duplicated_register_default_error", "The data has been regidtered duplicately."
        }, {// データ重複エラーメッセージ
            "duplicated_register_error", "{0}has been regidtered duplicately."
        }, {// 該当データ未存在エラーメッセージ
            "no_data_default_error", "The data does not exist, has been deleted."
        }, {// 該当データ未存在エラーメッセージ
            "no_data_error", "{0}does not exist, has been deleted."
        }, {// 初期データ削除エラーメッセージ
            "default_delete", "Default data of {0} can not be deleted."
        }, {// データ削除エラーメッセージ
            "delete_error", "{0} is failed to delete."
        }, {// データ未選択時エラーメッセージ
            "no_checked", "{0} has not been selected."
        }, {// 検索条件不正エラーメッセージ
            "searchCondition_error", "Search conditions are not right."
        }, {// 大小不正エラーメッセージ
            "comparison_error", "Size of {0} is not correct."
        }, {// 指定期間不正エラーメッセージ
            "period_error", "Period is not correct."
        }, {// 指定期間不正エラーメッセージ(パラメータ有り)
            "period_error_with_param", "Period of {0} is not correct."
        }, {// デフォルト顧客グループ削除時メッセージ
            "delete_default_customergroup", "Default customer group can not be deleted."
        }, {// 会員登録済み顧客グループ削除時メッセージ
            "delete_registered_customergroup", "The customer group which members belonging to can not be deleted."
        }, {// 本人アドレス削除エラーメッセージ
            "delete_self_address_error", "Address of yourself can not be deleted."
        }, {// 保有ポイントマイナスエラー
            "rest_point_minus_error", "Points can not be negative."
        }, {// 未入金・売上未確定データ有エラーメッセージ
            "payment_or_not_fixed_error",
            "Because the orders have not yet been paid or sales have not been unconfirmed, they can not be deleted."
        }, {// ショップコード非存在エラー
            "no_shop_code_error", "Shop code does not exist."
        }, {// メールキュー登録メッセージ
            "register_mailque_complete", "Mailque has been registered completely."
        }, {// メールキュー登録失敗メッセージ
            "register_mailqueue_failed", "Mailque has been failed to register."
        }, {// 入金確認メール送信完了メッセージ
            "send_confirm_mail_complete", "E-mail of payment confirmation has been sent."
        }, {// 入金督促メール送信完了メッセージ
            "send_remind_mail_complete", "E-mail of payment urge has been sent."
        }, {// 出荷報告メール送信完了メッセージ
            "send_shipping_mail_complete", "Shipping E-mail of Shipping code:{0} has been sent."
        }, {// 出荷報告メール送信完了メッセージ(番号無し)
            "send_shipping_mail_without_no", "Shipping E-mail has been sent."
        },
        //Add by V10-CH start
        {// メールキュー登録メッセージ
          "register_smsque_complete", "Smsque has been registered completely."
        }, {// メールキュー登録失敗メッセージ
          "register_smsqueue_failed", "Smsque has been failed to register."
        },
        {// 出荷報告SMS送信完了メッセージ
          "send_shipping_sms_complete", "Shipping SMS of Shipping code:{0} has been sent."
        }, {// 出荷報告SMS送信完了メッセージ(番号無し)
          "send_shipping_sms_without_no", "Shipping SMS has been sent."
        }
        //Add by V10-CH end
        , {// 必須入力エラーメッセージ
            "required_error", "{0} is required."
        }, {// 0件削除エラー
            "zero_data_delete_error", "There will be none, if {0} is deleted."
        }, {// 0件削除エラー
            "delete_max_commission_error", "Payment Commission is the highest, so can not be deleted."
        }, {// パスワード不一致エラーメッセージ
            "not_same_password", "Password and confirm-password entered is not the same."
        }, {// パスワード最小値未満エラーメッセージ
            "less_than_password", "Please enter the password with more than {0} half-angle words in English letters and numbers."
        }, {//电话番号を入力しなかったから,せめて,携帯電話の番号とは一つしかなかった
            "no_number","Telephone number and mobile phone number must be at least one input."
        }, {//有効な电话番号を入力してください
            "true_number","Please enter a valid phone number."
        },{// 検索結果なしメッセージ
            "search_result_not_found", "The searching result is null."
        }, {// 検索結果オーバーフローメッセージ
            "search_result_overflow",
            "The searching result is bigger than the upper limit, "
                + "so only {1} items at the beginning of {0} searching results can be displayed."
        }, {// URL不正エラーメッセージ
            "bad_url", "URL is not correct."
        }, {// CSV入出力権限なしエラーメッセージ
            "csv_io_no_auth", "Do not have the authority to import or export the CSV."
        }, {// 入出力対象なしエラーメッセージ
            "no_io_subject", "There is no items to import or export, please confirm whether authority setting is correct."
        }, {// 該当CSVデータなしエラーメッセージ
            "csv_output_no_data", "There is no CSV data."
        }, {// 文字数超過エラーメッセージ
            "string_over", "{0},please enter  characters within  {1}."
        }, {// データ連携中メッセージ
            "data_transported", "Data have been Interrelated."
        }, {// データ連携中メッセージ
            "format_error", "Format of {0} is  error."
        }, {// 登録確認メッセージ
            "register_confirm", "Are you sure to register the contents?"
        }, {// コンテンツアップロード時キャンペーン未存在エラーメッセージ
            "no_campaign_error",
            "Activities have not been registered yet,so uploading content is not possible.Please regidter activities firstly."
        }, {// 削除完了メッセージ
            "customer_withdrawed_complete", "Retirement  of customers has been dealed."
        }, {// ショップ登録完了メッセージ
            "shop_register_complete",
            "The shop has been registered completely.Please register the admin account in the menu {0} of user management."
        }, {// 受注修正メール送信完了メッセージ
            "send_modify_mail_complete", "Sent E-mail of purchase orders to customers of  No. {0}{1}{2} again."
        }, {// 在线客服使用时，SCRIPT内容（尾部）必须输入
            "Script_text", "{0}使用时，SCRIPT内容必须输入。"
        },{// 使用积分请设定为{0}的倍数
            "point_small_not_error", "使用积分请设定为{0}的倍数。"
        },
    };
  }

}
