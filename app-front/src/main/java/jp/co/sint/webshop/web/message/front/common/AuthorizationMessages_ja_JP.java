package jp.co.sint.webshop.web.message.front.common;

import java.util.ListResourceBundle;

/**
 * メッセージリソースのクラスです。
 * 
 * @author System Integrator Corp.
 */
public class AuthorizationMessages_ja_JP extends ListResourceBundle {

  @Override
  protected Object[][] getContents() {
    Object[][] obj = {
        // 認証エラーメッセージ
        {
            "authorization_error", "ログイン認証に失敗しました。"
        },
        {
          "mobile_complete", "携帯認証に成功しました。"
        },
        {
          "mobile_complete_error", "携帯認証に失敗しました。"
        }, {
          "mobile_double_error", "この携帯番号は既に利用されています。携帯番号をもう一度お確かめのうえ入力ください。"
        },
    };
    return obj;
  }

}
