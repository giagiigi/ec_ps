package jp.co.sint.webshop.web.message.back;

/**
 * メッセージのenumです。
 * 
 * @author System Integrator Corp.
 */
public enum BackMessages {

  /** 登録完了メッセージ */
  REGISTER_COMPLETE("register_complete", "{0}の登録を完了しました。"),
  /** 更新完了メッセージ */
  UPDATE_COMPLETE("update_complete", "{0}の更新を完了しました。"),
  /** 削除完了メッセージ */
  DELETE_COMPLETE("delete_complete", "{0}の削除を完了しました。"),
  /** キャンセル完了メッセージ */
  CANCEL_COMPLETE("cancel_complete", "{0}のキャンセルを完了しました。"),
  /** ファイルアップロード完了メッセージ */
  UPLOAD_COMPLETE("upload_complete", "{0}のアップロードを完了しました。"),
  /** ファイルアップロード失敗エラーメッセージ */
  UPLOAD_FAILED("upload_failed", "{0}のアップロードに失敗しました。"),
  /** CSV出力完了メッセージ */
  CSV_EXPORT_COMPLETE("csv_export_complete", "{0}のCSV出力を完了しました。"),
  /** CSV取込成功メッセージ */
  CSV_IMPORT_COMPLETE("csv_import_complete", "CSVの取り込みを完了しました。"),
  /** CSV取込一部成功メッセージ */
  CSV_IMPORT_PARTIAL("csv_import_partial", "CSVの取り込みに一部成功しました。"),
  /** CSV取込失敗エラーメッセージ */
  CSV_IMPORT_FAILED("csv_import_failed", "CSVの取り込みに失敗しました。"),
  /** 更新失敗エラーメッセージ */
  UPDATE_FAILED("update_failed", "{0}の更新に失敗しました。"),
  /** データ重複エラーメッセージ */
  DUPLICATED_REGISTER_DEFAULT_ERROR("duplicated_register_default_error", "すでに登録済みのデータです。"),
  /** データ重複エラーメッセージ */
  DUPLICATED_REGISTER_ERROR("duplicated_register_error", "すでに登録済みの{0}です。"),
  /** 該当データ未存在エラーメッセージ */
  NO_DATA_DEFAULT_ERROR("no_data_default_error", "該当のデータは存在しないか、すでに削除済みです。"),
  /** 該当データ未存在エラーメッセージ */
  NO_DATA_ERROR("no_data_error", "該当の{0}は存在しないか、すでに削除済みです。"),
  /** 初期データ削除エラーメッセージ */
  DEFAULT_DELETE("default_delete", "{0}の初期データは削除できません。"),
  /** データ削除エラーメッセージ */
  DELETE_ERROR("delete_error", "{0}の削除に失敗しました。"),
  /** データ未選択時エラーメッセージ */
  NO_CHECKED("no_checked", "{0}が選択されていません。"),
  /** 検索条件不正エラーメッセージ */
  SEARCHCONDITION_ERROR("searchCondition_error", "検索条件が不正です。"),
  /** 大小不正エラーメッセージ */
  COMPARISON_ERROR("comparison_error", "{0}の大小関係が不正です。"),
  /** 指定期間不正エラーメッセージ */
  PERIOD_ERROR("period_error", "日付の大小関係が不正です。"),
  /** 指定期間不正エラーメッセージ(パラメータ有り) */
  PERIOD_ERROR_WITH_PARAM("period_error_with_param", "{0}の大小関係が不正です。"),
  /** デフォルト顧客グループ削除時メッセージ */
  DELETE_DEFAULT_CUSTOMERGROUP("delete_default_customergroup", "規定の顧客グループは削除できません。"),
  /** 会員登録済み顧客グループ削除時メッセージ */
  DELETE_REGISTERED_CUSTOMERGROUP("delete_registered_customergroup", "会員が登録されている顧客グループは削除できません。"),
  /** 本人アドレス削除エラーメッセージ */
  DELETE_SELF_ADDRESS_ERROR("delete_self_address_error", "ご本人のアドレスは削除できません。"),
  /** 保有ポイントマイナスエラー */
  REST_POINT_MINUS_ERROR("rest_point_minus_error", "保有ポイントをマイナスに設定することはできません。"),
  /** 未入金・売上未確定データ有エラーメッセージ */
  PAYMENT_OR_NOT_FIXED_ERROR("payment_or_not_fixed_error", "未入金または売上未確定の受注があるため削除できません。"),
  /** ショップコード非存在エラー */
  NO_SHOP_CODE_ERROR("no_shop_code_error", "ショップコードが存在しません。"),
  /** メールキュー登録メッセージ */
  REGISTER_MAILQUE_COMPLETE("register_mailque_complete", "メール送信リストに登録しました。"),
  /** メールキュー登録失敗メッセージ */
  REGISTER_MAILQUEUE_FAILED("register_mailqueue_failed", "{0}のメールキュー登録に失敗しました。"),
  /** 入金確認メール送信完了メッセージ */
  SEND_CONFIRM_MAIL_COMPLETE("send_confirm_mail_complete", "入金確認メールを送信しました。"),
  /** 入金督促メール送信完了メッセージ */
  SEND_REMIND_MAIL_COMPLETE("send_remind_mail_complete", "入金督促メールを送信しました。"),
  /** 出荷報告メール送信完了メッセージ */
  SEND_SHIPPING_MAIL_COMPLETE("send_shipping_mail_complete", "出荷番号:{0}の出荷報告メールを送信しました。"),
  /** 出荷報告メール送信完了メッセージ(番号無し) */
  SEND_SHIPPING_MAIL_WITHOUT_NO("send_shipping_mail_without_no", "出荷報告メールを送信しました。"),
  //Add by V10-CH start
  /** メールキュー登録メッセージ */
  REGISTER_SMSQUE_COMPLETE("register_smsque_complete", "SMS发送リストに登録しました。"),
  /** メールキュー登録失敗メッセージ */
  REGISTER_SMSQUEUE_FAILED("register_smsqueue_failed", "{0}のメールキュー登録に失敗しました。"),
  /** 出荷報告メール送信完了メッセージ */
  SEND_SHIPPING_SMS_COMPLETE("send_shipping_sms_complete", "出荷番号:{0}の出荷報告SMS送信しました。"),
  /** 出荷報告メール送信完了メッセージ(番号無し) */
  SEND_SHIPPING_SMS_WITHOUT_NO("send_shipping_sms_without_no", "出荷報告SMS送信しました。"),
  //Add by V10-CH end
  /** 必須入力エラーメッセージ */
  REQUIRED_ERROR("required_error", "{0}は必ず入力してください。"),
  /** 0件削除エラー */
  ZERO_DATA_DELETE_ERROR("zero_data_delete_error", "{0}が0件になるため削除できません。"),
  /** 0件削除エラー */
  DELETE_MAX_COMMISSION_ERROR("delete_max_commission_error", "手数料が最大値のため削除できません。"),
  /** パスワード不一致エラーメッセージ */
  NOT_SAME_PASSWORD("not_same_password", "入力パスワードと確認用パスワードが異なります。"),
  /** パスワード最小値未満エラーメッセージ */
  LESS_THAN_PASSWORD("less_than_password", "パスワードは、{0}桁以上の半角英数文字で入力してください。"),
  //Add by V10-CH start
  /** 电话番号を教えてくれと携帯電話番号を入力して少なくともは一つしかなかった */
  NO_NUMBER("no_number","电话番号を教えてくれと携帯電話番号を入力して少なくともは一つしかなかった。"),
  /** 有効な电话番号を入力してください */
  TRUE_NUMBER("true_number","有効な电话番号を入力してください"),
  //Add by V10-CH end
  /** 検索結果なしメッセージ */
  SEARCH_RESULT_NOT_FOUND("search_result_not_found", "検索結果が0件です。"),
  /** 検索結果オーバーフローメッセージ */
  SEARCH_RESULT_OVERFLOW("search_result_overflow", "検索可能上限数を超えたため検索結果{0}件中、先頭の{1}件を表示します。"),
  /** URL不正エラーメッセージ */
  BAD_URL("bad_url", "不正なURLです。"),
  /** CSV入出力権限なしエラーメッセージ */
  CSV_IO_NO_AUTH("csv_io_no_auth", "該当のCSV入出力権限がありません。"),
  /** 入出力対象なしエラーメッセージ */
  NO_IO_SUBJECT("no_io_subject", "入出力できる項目がありません。<br />権限が正しく設定されているか確認してください。"),
  /** 該当CSVデータなしエラーメッセージ */
  CSV_OUTPUT_NO_DATA("csv_output_no_data", "該当のCSVデータがありません。"),
  /** 文字数超過エラーメッセージ */
  STRING_OVER("string_over", "{0}は{1}文字以内で入力してください。"),
  /** データ連携中メッセージ */
  DATA_TRANSPORTED("data_transported", "データ連携済みです。"),
  /** データ連携中メッセージ */
  FORMAT_ERROR("format_error", "{0}は書式に誤りがあります。"),
  /** 登録確認メッセージ */
  REGISTER_CONFIRM("register_confirm", "こちらの内容で登録します。よろしいですか？"),
  /** コンテンツアップロード時キャンペーン未存在エラーメッセージ */
  NO_CAMPAIGN_ERROR("no_campaign_error", "キャンペーンが登録されていないためコンテンツをアップロードすることができません。キャンペーンを登録してください。"),
  /** 削除完了メッセージ */
  CUSTOMER_WITHDRAWED_COMPLETE("customer_withdrawed_complete", "顧客の退会処理が完了しました。"),
  /** ショップ登録完了メッセージ */
  SHOP_REGISTER_COMPLETE("shop_register_complete", "ショップの登録を完了しました。管理ユーザ登録メニューから{0}の管理ユーザを登録してください。"),
  /** 受注修正メール送信完了メッセージ */
  SEND_MODIFY_MAIL_COMPLETE("send_modify_mail_complete", "顧客コード：{0} {1} {2}様に注文受付メールを再送しました。");

  private String key;

  private String value;

  private BackMessages(String key, String value) {
    this.key = key;
    this.value = value;
  }

  /**
   * メッセージリソースを取得します。
   * 
   * @return メッセージリソース
   */
  public static Object[][] toResource() {
    Object[][] obj = new String[BackMessages.values().length][];
    int i = 0;
    for (BackMessages bm : BackMessages.values()) {
      obj[i++] = new String[] {
          bm.key, bm.value
      };
    }
    return obj;
  }
}
