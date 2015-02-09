package jp.co.sint.webshop.web.message.back.common;

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
          "authorization_error", "管理ユーザの認証に失敗しました。"
      },
    };
    return obj;
  }

}
