package jp.co.sint.webshop.web.message.front;

import java.util.ListResourceBundle;

/**
 * メッセージリソースのクラスです。

 * 
 * @author System Integrator Corp.
 */
public class Messages_en_US extends ListResourceBundle {

  @Override
  protected Object[][] getContents() {
    Object[][] obj = {
        // 登録完了メッセージ

        {
            "register_complete", "The {0} has been registered."
        },
        // 更新完了メッセージ

        {
            "update_complete", "The {0} has been updated."
        },
        // 削除完了メッセージ

        {
            "delete_complete", "The {0} has been deleted."
        },
        // 該当データ未存在エラーメッセージ
        {
            "no_data_error", "The {0} doesn't exist or already deleted."
        },
        // データ重複エラーメッセージ

        {
            "duplicated_register_error", "The {0} was already registered."
        },
        // キャンセル完了メッセージ
        {
            "cancel_complete", "The {0} has been canceled."
        },
        // 必須入力エラーメッセージ
        {
            "required_error", "The {0} is required."
        },
        // パスワード不一致エラーメッセージ

        {
            "not_same_password", "The Password is not the same with the Confirm password."
        },
        // パスワード最小値未満エラーメッセージ
        {
            "less_than_password", "Please set the password more than {0} chr with half size alpha-num."
        },
        //Add by V10-CH start
        {
          "no_number", "Telephone number and mobile phone number must be at least one input."
        },
        //Add by V10-CH end
        // メール送信完了メッセージ
         {"true_number","Please enter a valid phone number."
          
        },
        {
            "sendmail_complete", "The {0} mail has been sent."
        },
        // 20130219 yyq add start
        {
          "invoice_taxpayer_code_length_err", "{0}is not correct, the effective length is 15, 18 or 20."
        },
        // 20130219 yyq add end
        // 検索条件不正エラーメッセージ
        {
            "searchCondition_error", "Incorrect search condition."
        },
        // データ未選択時エラーメッセージ

        {
            "no_checked", "The {0} is not selected."
        },
        // 問い合わせ送信完了メッセージ

        {
            "inquiry_complete", "The inquiry mail has been sent with the contents as follows."
        },
        // URL不正エラーメッセージ
        {
            "bad_url", "Incorrect URL."
        },
        // 大小不正エラーメッセージ
        {
            "comparison_error", "The large-small relation of {0} is incorrect."
        },
        // 文字数超過エラーメッセージ

        {
            "string_over", "Please input {0} less than {1} chr."
        }
        // 2013/04/01 优惠券对应 ob add start
        ,{
            "mobile_auth_code_over", "Verification code is wrong, or validation period is expired. Please send code again."
         }
        // 2013/04/01 优惠券对应 ob add en
    };
    return obj;
  }
}
