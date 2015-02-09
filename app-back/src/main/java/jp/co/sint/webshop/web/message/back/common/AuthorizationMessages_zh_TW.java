package jp.co.sint.webshop.web.message.back.common;

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
          "authorization_error", "管理用户的认证失败了。"
      },
    };
    return obj;
  }

}
