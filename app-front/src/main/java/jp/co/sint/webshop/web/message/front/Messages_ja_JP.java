package jp.co.sint.webshop.web.message.front;

import java.util.ListResourceBundle;

/**
 * メッセージリソースのクラスです。
 * 
 * @author System Integrator Corp.
 */
public class Messages_ja_JP extends ListResourceBundle {

  @Override
  protected Object[][] getContents() {
    Object[][] obj = {
        // 登録完了メッセージ
        {
            "register_complete", "{0}の登録を完了しました。"
        },
        // 更新完了メッセージ
        {
            "update_complete", "{0}の変更を完了しました。"
        },
        // 削除完了メッセージ
        {
            "delete_complete", "{0}の削除を完了しました。"
        },
        // 該当データ未存在エラーメッセージ
        {
            "no_data_error", "該当の{0}は存在しないか、すでに削除済みです。"
        },
        // データ重複エラーメッセージ
        {
            "duplicated_register_error", "すでに登録済みの{0}です。"
        },
        // キャンセル完了メッセージ
        {
            "cancel_complete", "{0}のキャンセルを完了しました。"
        },
        // 必須入力エラーメッセージ
        {
            "required_error", "{0}は必ず入力してください。"
        },
        // パスワード不一致エラーメッセージ
        {
            "not_same_password", "入力パスワードと確認用パスワードが異なります。"
        },
        // メールアドレス不一致
        {
          "not_same_verify", "認証コードが正しくありません。"
        },
        {
            "not_same_email", "メールアドレスと一致しません。"
        },
        // パスワード最小値未満エラーメッセージ
        {
            "less_than_password", "パスワードは、{0}桁以上の半角英数文字で入力してください。"
        },
        //Add by V10-CH start
        {
          "no_number", "電話番号を教えてくれと携帯電話番号を入力して少なくともは一つしかなかった。"
        },
        //Add by V10-CH end
        // メール送信完了メッセージ
         {
          "true_number","有効な電話番号を入力してください。"
         },
        {
            "sendmail_complete", "{0}メールを送信しました。"
        },
        // 20130219 yyq add start
        {
          "invoice_taxpayer_code_length_err", "{0}の桁数が間違っています。15桁,18桁,20桁の何れかでご入力ください。"
        },
        // 20130219 yyq add end
        // 検索条件不正エラーメッセージ
        {
            "searchCondition_error", "検索条件が不正です。"
        },
        // データ未選択時エラーメッセージ
        {
            "no_checked", "{0}が選択されていません。"
        },
        // 問い合わせ送信完了メッセージ
        {
            "inquiry_complete", "以下の内容でお問い合わせメールを送信しました。"
        },
        // URL不正エラーメッセージ
        {
            "bad_url", "不正なURLです。"
        },
        // 大小不正エラーメッセージ
        {
            "comparison_error", "{0}の大小関係が不正です。"
        },
        // 文字数超過エラーメッセージ
        {
            "string_over", "{0}は{1}文字以内で入力してください。"
        }
        // 2013/04/01 优惠券对应 ob add start
        ,{
            "mobile_auth_code_over", "認証コードが間違っているか、有効期限が過ぎています。再度コードの発行をお願いします。"
         }
        // 2013/04/01 优惠券对应 ob add en
    };
    return obj;
  }
}
