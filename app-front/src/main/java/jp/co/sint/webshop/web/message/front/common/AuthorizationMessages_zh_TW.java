package jp.co.sint.webshop.web.message.front.common;

import java.util.ListResourceBundle;

/**
 * メッセージリソースのクラスです。
 * 
 * @author System Integrator Corp.
 */
public class AuthorizationMessages_zh_TW extends ListResourceBundle {

  @Override
  protected Object[][] getContents() {
    Object[][] obj = {
        // 認証エラーメッセージ
        {
            "authorization_error", "登陸認證失敗。"
        },
    };
    return obj;
  }

}
