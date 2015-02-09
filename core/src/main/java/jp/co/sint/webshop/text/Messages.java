/**
 * 
 */
package jp.co.sint.webshop.text;

import java.util.Locale;

import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.LocaleUtil;

/**
 * @author System Integrator Corp.
 */
public final class Messages {

  private static final String BUNDLE_NAME = "jp.co.sint.webshop.text.CoreMessages"; //$NON-NLS-1$

  private Messages() {
  }

  public static String getString(String key) {
    return M17NMessageBase.getString(key, BUNDLE_NAME);
  }

  public static String getString(String key, Locale locale) {
    return M17NMessageBase.get(key, BUNDLE_NAME, locale);
  }

  public static String log(String key) {
    return M17NMessageBase.log(key, BUNDLE_NAME);
  }

  //M17N-0006 追加 ここから
  public static String getString(String key, String languageCode) {
    return M17NMessageBase.get(key, BUNDLE_NAME, LocaleUtil.getLocale(languageCode));
  }
  //M17N-0006 追加 ここまで

  public static String getCsvKey(String key) {
    return key;
  }

  public static String getCsvColumnName(String key) {
    return getString(key, DIContainer.getLocaleContext().getCurrentLocale());
  }

}
