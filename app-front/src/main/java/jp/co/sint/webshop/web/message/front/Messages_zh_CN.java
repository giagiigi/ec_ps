package jp.co.sint.webshop.web.message.front;

import java.util.ListResourceBundle;

/**
 * メッセージリソースのクラスです。

 * 
 * @author System Integrator Corp.
 */
public class Messages_zh_CN extends ListResourceBundle {

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
        //20111212 lirong add start
        // メールアドレス不一致
        {
            "not_same_email", "输入的电子邮件和电子邮件确认不一样。"
        },
        //20111212 lirong add end
        
        //20111215 lirong add start
        //  验证码不一致
        {
            "not_same_verify", "验证码输入不正确。"
        },
        {
            "name_length_err", "{0}最多输入14个汉字或者20个英文数字。"
        },
        {
            "address_length_err", "{0}最多输入100个汉字或者200个英文数字。"
        },
        // 20130219 yyq add start
        {
          "invoice_taxpayer_code_length_err", "{0}位数不正确，有效长度为15、18、20位。"
        },
        // 20130219 yyq add end
        {
            "companyname_length_err", "{0}最多输入35个汉字或者70个英文数字。"
        },
        {
            "bankname_length_err", "{0}最多输入35个汉字或者70个英文数字。"
        },
        //20111215 lirong add end
        // パスワード最小値未満エラーメッセージ
        {
            "less_than_password", "密码请输入{0}位以上的半角英数文字。"
        },
        //Add by V10-CH start
        {
          "no_number", "电话号码与手机号码至少要输入一个。"
        },
        //Add by V10-CH end
        // メール送信完了メッセージ

        {"true_number","请输入有效的电话号码。"
          
        },{
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
        // 2013/04/01 优惠券对应 ob add start
        ,{
            "mobile_auth_code_over", "验证码无效或已超出有效期间。"
         }
        // 2013/04/01 优惠券对应 ob add end
    };
    return obj;
  }
}
