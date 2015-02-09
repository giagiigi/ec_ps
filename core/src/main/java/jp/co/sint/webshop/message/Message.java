package jp.co.sint.webshop.message;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import jp.co.sint.webshop.configure.LocaleContext;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.StringUtil;

public final class Message {
  
  private Message() {
  }

  /**
   * メッセージを取得する。<br>
   * 指定された情報種別に関するメッセージを取得する。<br>
   * 情報種別にはErrorInfo ValidationInfo SimpleInfo等が存在する。<br>
   * 
   * @param type
   *          情報種別
   * @param params
   *          パラメータ
   * @return 指定された情報種別に関するメッセージ
   */
  public static String get(MessageType type, String... params) {
    LocaleContext localeContext = DIContainer.getLocaleContext();
    Locale locale = localeContext.getCurrentLocale();
    return get(type, locale, params);
  }
  
  public static String get(MessageType type, Locale locale, String... params) {
    ResourceBundle bundle = null;
    String message = "";
    try {
      bundle = ResourceBundle.getBundle(type.getMessageResource(), locale);
      String pattern = StringUtil.coalesce(bundle.getString(type.getMessagePropertyId()), "");
      message = MessageFormat.format(pattern, (Object[]) params);
    } catch (MissingResourceException mrEx) {
      mrEx.printStackTrace();
    } catch (RuntimeException rEx) {
      rEx.printStackTrace();
    }
    return message;
  }

}
