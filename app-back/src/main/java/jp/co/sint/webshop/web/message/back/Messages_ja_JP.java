package jp.co.sint.webshop.web.message.back;

import java.util.ListResourceBundle;

/**
 * @author System Integrator Corp.
 */
public class Messages_ja_JP extends ListResourceBundle {

  protected Object[][] getContents() {
    return new Object[][] {
        {// 登録完了メッセージ
            "register_complete", "{0}の登録を完了しました。"
        }, {// 更新完了メッセージ
            "update_complete", "{0}の更新を完了しました。"
        }, {// 削除完了メッセージ
            "delete_complete", "{0}の削除を完了しました。"
        }, {// キャンセル完了メッセージ
            "cancel_complete", "{0}のキャンセルを完了しました。"
        }, {// ファイルアップロード完了メッセージ
            "upload_complete", "{0}のアップロードを完了しました。"
        }, {// ファイルアップロード失敗エラーメッセージ
            "upload_failed", "{0}のアップロードに失敗しました。"
        }, {// CSV出力完了メッセージ
            "csv_export_complete", "{0}のCSV出力を完了しました。"
        }, {// CSV取込成功メッセージ
            "csv_import_complete", "CSVの取り込みを完了しました。"
        }, {// CSV取込一部成功メッセージ
            "csv_import_partial", "CSVの取り込みに一部成功しました。"
        }, {// CSV取込失敗エラーメッセージ
            "csv_import_failed", "CSVの取り込みに失敗しました。"
        }, {// 更新失敗エラーメッセージ
            "update_failed", "{0}の更新に失敗しました。"
        }, {// データ重複エラーメッセージ
            "duplicated_register_default_error", "すでに登録済みのデータです。"
        }, {// データ重複エラーメッセージ
            "duplicated_register_error", "すでに登録済みの{0}です。"
        }, {// 該当データ未存在エラーメッセージ
            "no_data_default_error", "該当のデータは存在しないか、すでに削除済みです。"
        }, {// 該当データ未存在エラーメッセージ
            "no_data_error", "該当の{0}は存在しないか、すでに削除済みです。"
        }, {// 初期データ削除エラーメッセージ
            "default_delete", "{0}の初期データは削除できません。"
        }, {// データ削除エラーメッセージ
            "delete_error", "{0}の削除に失敗しました。"
        }, {// データ未選択時エラーメッセージ
            "no_checked", "{0}が選択されていません。"
        }, {// 検索条件不正エラーメッセージ
            "searchCondition_error", "検索条件が不正です。"
        }, {// 大小不正エラーメッセージ
            "comparison_error", "{0}の大小関係が不正です。"
        }, {// 指定期間不正エラーメッセージ
            "period_error", "日付の大小関係が不正です。"
        }, {// 指定期間不正エラーメッセージ(パラメータ有り)
            "period_error_with_param", "{0}の大小関係が不正です。"
        }, {// デフォルト顧客グループ削除時メッセージ
            "delete_default_customergroup", "規定の顧客グループは削除できません。"
        }, {// 会員登録済み顧客グループ削除時メッセージ
            "delete_registered_customergroup", "会員が登録されている顧客グループは削除できません。"
        }, {// 本人アドレス削除エラーメッセージ
            "delete_self_address_error", "ご本人のアドレスは削除できません。"
        }, {// 保有ポイントマイナスエラー
            "rest_point_minus_error", "保有ポイントをマイナスに設定することはできません。"
        }, {// 未入金・売上未確定データ有エラーメッセージ
            "payment_or_not_fixed_error", "未入金または売上未確定の受注があるため削除できません。"
        }, {// ショップコード非存在エラー
            "no_shop_code_error", "ショップコードが存在しません。"
        }, {// メールキュー登録メッセージ
            "register_mailque_complete", "メール送信リストに登録しました。"
        }, {// メールキュー登録失敗メッセージ
            "register_mailqueue_failed", "{0}のメールキュー登録に失敗しました。"
        }, {// 入金確認メール送信完了メッセージ
            "send_confirm_mail_complete", "入金確認メールを送信しました。"
        }, {// 入金督促メール送信完了メッセージ
            "send_remind_mail_complete", "入金督促メールを送信しました。"
        }, {// 出荷報告メール送信完了メッセージ
            "send_shipping_mail_complete", "出荷番号:{0}の出荷報告メールを送信しました。"
        }, {// 出荷報告メール送信完了メッセージ(番号無し)
            "send_shipping_mail_without_no", "出荷報告メールを送信しました。"
        }, 
        //Add by V10-CH start
        {// メールキュー登録メッセージ
          "register_smsque_complete", "SMS发送リストに登録しました。"
        }, {// メールキュー登録失敗メッセージ
          "register_smsqueue_failed", "{0}のメールキュー登録に失敗しました。"
        },
        {// 出荷報告SMS送信完了メッセージ
          "send_shipping_sms_complete", "出荷番号:{0}の出荷報告SMS送信しました。"
        }, {// 出荷報告SMS送信完了メッセージ(番号無し)
          "send_shipping_sms_without_no", "出荷報告SMS送信しました。"
        },
        //Add by V10-CH end       
        {// 必須入力エラーメッセージ
            "required_error", "{0}は必ず入力してください。"
        }, {// 0件削除エラー
            "zero_data_delete_error", "{0}が0件になるため削除できません。"
        }, {// 0件削除エラー
            "delete_max_commission_error", "手数料が最大値のため削除できません。"
        }, {// パスワード不一致エラーメッセージ
            "not_same_password", "入力パスワードと確認用パスワードが異なります。"
        }, {// パスワード最小値未満エラーメッセージ
            "less_than_password", "パスワードは、{0}桁以上の半角英数文字で入力してください。"
        }, {//电话号码与手机号码至少输入一个
            "no_number","电话番号を教えてくれと携帯電話番号を入力して少なくともは一つしかなかった。"
        }, {//有効な电话番号を入力してください
            "true_number","有効な电话番号を入力してください。"
        }, {// 検索結果なしメッセージ
            "search_result_not_found", "検索結果が0件です。"
        }, {// 検索結果オーバーフローメッセージ
            "search_result_overflow", "検索可能上限数を超えたため検索結果{0}件中、先頭の{1}件を表示します。"
        }, {// URL不正エラーメッセージ
            "bad_url", "不正なURLです。"
        }, {// CSV入出力権限なしエラーメッセージ
            "csv_io_no_auth", "該当のCSV入出力権限がありません。"
        }, {// 入出力対象なしエラーメッセージ
            "no_io_subject", "入出力できる項目がありません。<br />権限が正しく設定されているか確認してください。"
        }, {// 該当CSVデータなしエラーメッセージ
            "csv_output_no_data", "該当のCSVデータがありません。"
        }, {// 文字数超過エラーメッセージ
            "string_over", "{0}は{1}文字以内で入力してください。"
        }, {// データ連携中メッセージ
            "data_transported", "データ連携済みです。"
        }, {// データ連携中メッセージ
            "format_error", "{0}は書式に誤りがあります。"
        }, {// 登録確認メッセージ
            "register_confirm", "こちらの内容で登録します。よろしいですか？"
        }, {// コンテンツアップロード時キャンペーン未存在エラーメッセージ
            "no_campaign_error", "キャンペーンが登録されていないためコンテンツをアップロードすることができません。キャンペーンを登録してください。"
        }, {// 削除完了メッセージ
            "customer_withdrawed_complete", "顧客の退会処理が完了しました。"
        }, {// ショップ登録完了メッセージ
            "shop_register_complete", "ショップの登録を完了しました。管理ユーザ登録メニューから{0}の管理ユーザを登録してください。"
        }, {// 受注修正メール送信完了メッセージ
            "send_modify_mail_complete", "顧客コード：{0} {1} {2}様に注文受付メールを再送しました。"
        }, {// 在线客服使用时，SCRIPT内容（尾部）必须输入
            "Script_text", "{0}使用时，SCRIPT内容必须输入。"
        },{// 使用积分请设定为{0}的倍数
            "point_small_not_error", "使用积分请设定为{0}的倍数。"
        },

    };
  }
}
