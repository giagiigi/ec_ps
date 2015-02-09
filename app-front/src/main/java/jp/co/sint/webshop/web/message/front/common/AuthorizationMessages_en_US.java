package jp.co.sint.webshop.web.message.front.common;

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
            "authorization_error", "Login authentication failed."
        },
        {
          "mobile_complete", "Phone authentication is successful"
        },
        {
          "mobile_complete_error", "Phone authentication failure"
        },
        {
          "mobile_double_error", "The number has been already used.Please check again."
        },
    };
    return obj;
  }

}
