package jp.co.sint.webshop.web.message.front.common;

import java.util.ListResourceBundle;

/**
 * メッセージリソースのクラスです。
 * 
 * @author System Integrator Corp.
 */
public class AuthorizationMessages extends ListResourceBundle {

  @Override
  protected Object[][] getContents() {
    Object[][] obj = {
        // 認証エラーメッセージ
        {
            "authorization_error", "ログイン認証に失敗しました。"
        },
    };
    return obj;
  }

}
