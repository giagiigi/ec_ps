/**
 * 
 */
package jp.co.sint.webshop.utility;

import java.text.MessageFormat;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import jp.co.sint.webshop.validation.ValidatorUtil;
import java.util.Properties;
/**
 * @author System Integrator Corp.
 */
public final class LocaleUtil {

  private LocaleUtil() {
  }
  public static String getLanguageCode(Locale locale) {
    return MessageFormat.format("{0}-{1}", locale.getLanguage(), locale.getCountry()).toLowerCase(Locale.US);
  }

  public static Locale getLocale(String exLangCode) {
    Locale locale = Locale.getDefault();
    if (StringUtil.hasValue(exLangCode) && ValidatorUtil.matchesWith(exLangCode, "[A-za-z]{2}([-_][A-Za-z]{2})")) {
      String[] args = exLangCode.split("[-_]");
      if (args.length == 2) {
        locale = new Locale(args[0], args[1]);
      } else {
        locale = new Locale(args[0]);
      }
    }
    return locale;
  }

  public static Map<String, String> getResourceMap(ResourceBundle bundle) {
    Map<String, String> map = new HashMap<String, String>();
    for (Enumeration<String> en = bundle.getKeys(); en.hasMoreElements();) {
      String key = en.nextElement();
      map.put(key, bundle.getString(key));
    }
    return map;
  }
  /***
	 * 操作系统语言地区
	 * 
	 * @return osLanguageCode
	 */
	public static String getOsLanguageCode() {
		Properties props = System.getProperties(); // 获得系统属性集
		String osLanguage = props.getProperty("user.language").toLowerCase(); // 操作系统语言
		String osRegion = props.getProperty("user.country").toLowerCase(); // 操作系统地区
		String osLanguageCode = osLanguage + "-" + osRegion;
		return osLanguageCode;
	}
}
