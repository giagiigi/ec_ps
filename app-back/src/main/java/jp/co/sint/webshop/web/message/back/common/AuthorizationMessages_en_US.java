package jp.co.sint.webshop.web.message.back.common;

import java.util.ListResourceBundle;

/**
 * メッセージリソースのクラスです。
 * 
 * @author System Integrator Corp.
 */
public class AuthorizationMessages_en_US extends ListResourceBundle {

  @Override
  protected Object[][] getContents() {
    Object[][] obj = {
      // 認証エラーメッセージ
      {
          "authorization_error", "Failed in authorizing the admin user."
      },
    };
    return obj;
  }

}
