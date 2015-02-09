package jp.co.sint.webshop.web.message.front;

import java.util.ListResourceBundle;

/**
 * メッセージリソースのクラスです。

 * 
 * @author System Integrator Corp.
 */
public class Messages_zh_TW extends ListResourceBundle {

  @Override
  protected Object[][] getContents() {
    Object[][] obj = {
        // 登録完了メッセージ

        {
            "register_complete", "{0}已经登录。"
        },
        // 更新完了メッセージ

        {
            "update_complete", "{0}已经更新。"
        },
        // 削除完了メッセージ

        {
            "delete_complete", "{0}已经删除。"
        },
        // 該当データ未存在エラーメッセージ
        {
            "no_data_error", "{0}不存在，已经被删除。"
        },
        // データ重複エラーメッセージ

        {
            "duplicated_register_error", "{0}已被登录。"
        },
        // キャンセル完了メッセージ
        {
            "cancel_complete", "{0}已被取消。"
        },
        // 必須入力エラーメッセージ
        {
            "required_error", "{0}必须输入。"
        },
        // パスワード不一致エラーメッセージ

        {
            "not_same_password", "输入的密码和确认密码不一样。"
        },
        // パスワード最小値未満エラーメッセージ
        {
            "less_than_password", "密码请输入{0}位以上的半角英数文字。"
        },
        // メール送信完了メッセージ
        //Add by V10-CH start
        {
          "no_number", "电话号码与手机号码至少要输入一个。"
        },
        //Add by V10-CH end
        {"true_number","请输入有效的电话号码。"
          
        },
        {
            "sendmail_complete", "{0}邮件已发送。"
        },
        // 検索条件不正エラーメッセージ
        {
            "searchCondition_error", "检索条件不正确。"
        },
        // データ未選択時エラーメッセージ

        {
            "no_checked", "{0}没被选择。"
        },
        // 問い合わせ送信完了メッセージ

        {
            "inquiry_complete", "以下咨询内容已经发送。"
        },
        // URL不正エラーメッセージ
        {
            "bad_url", "URL不正确"
        },
        // 大小不正エラーメッセージ
        {
            "comparison_error", "{0}的大小关系不正确。"
        },
        // 文字数超過エラーメッセージ

        {
            "string_over", "{0}请{1}文字以内输入。"
        }
    };
    return obj;
  }
}
