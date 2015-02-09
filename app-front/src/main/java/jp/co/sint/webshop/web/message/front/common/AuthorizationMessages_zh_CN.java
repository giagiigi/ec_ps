package jp.co.sint.webshop.web.message.front.common;

import java.util.ListResourceBundle;

/**
 * メッセージリソースのクラスです。

 * 
 * @author System Integrator Corp.
 */
public class AuthorizationMessages_zh_CN extends ListResourceBundle {

  @Override
  protected Object[][] getContents() {
    Object[][] obj = {
        // 認証エラーメッセージ
        {
            "authorization_error", "您输入的用户名或密码有误。（请检查是否拼写错误或开启了大小写锁定键）"
        },
        {
          "mobile_complete", "手机认证完成。"
        },
        {
          "mobile_complete_error", "手机认证失败。"
        }, {
          "mobile_double_error", "您填写的手机号码已被注册。请确认您填写的号码是否正确。"
        },
    };
    return obj;
  }

}
