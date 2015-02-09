/**
 * 
 */
package jp.co.sint.webshop.text;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import jp.co.sint.webshop.utility.DIContainer;

import org.apache.log4j.Logger;

/**
 * @author System Integrator Corp.
 */
public final class M17NMessageBase {
  
  private M17NMessageBase() {
  }

  public static String getString(String key, String bundleName) {
    Locale locale = Locale.getDefault();
    if (DIContainer.isReady()) {
      locale = DIContainer.getLocaleContext().getCurrentLocale();
    }
    return get(key, bundleName, locale);
  }

  public static String log(String key, String bundleName) {
    Locale locale = Locale.getDefault();
    if (DIContainer.isReady()) {
      locale = DIContainer.getLocaleContext().getSystemLocale();
    }
    return get(key, bundleName, locale);
  }

  public static String get(String key, String bundleName, Locale locale) {
    Logger logger = Logger.getLogger(M17NMessageBase.class);
    String result = "";
    try {
      ResourceBundle bundle = ResourceBundle.getBundle(bundleName, locale);
      result = bundle.getString(key);
    } catch (MissingResourceException e) {
      logger.warn(e);
      result = '!' + key + '!';
    } catch (RuntimeException e) {
      logger.warn(e);
      result = '!' + key + '!';
    }
    return result;
  }

}
