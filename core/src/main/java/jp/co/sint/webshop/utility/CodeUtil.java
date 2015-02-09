package jp.co.sint.webshop.utility;

import java.lang.reflect.Field;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

/**
 * Enum/Annotationの多言語対応ユーティリティです。
 * 
 * @author System Integrator Corp.
 */
public final class CodeUtil {

  private CodeUtil() {
  }

  private static Locale getCurrentLocale() {
    return DIContainer.getLocaleContext().getCurrentLocale();
  }
  //add by cs_yuli 20120628 start
  private static Locale getLocaleCode() {
	    return DIContainer.getLocaleContext().getDefaultLocale();
	  }
  public static String getEntryLocal(String key) {
	    return getXmlBundle(RESOURCE_NAME_M17NCONFIG, getLocaleCode(), key);
	  }
  public static String getEntryByLanguage(String key,Locale locale) {
	    return getXmlBundle(RESOURCE_NAME_M17NCONFIG, locale, key);
	  }
  //add by cs_yuli 20120628 end
  private static final String RESOURCE_NAME_M17NCONFIG = "m17nConfig";

  private static final String RESOURCE_NAME_DOMAIN = "jp.co.sint.webshop.data.domain.Resources";

  private static final String RESOURCE_NAME_ATTRIBUTE = "jp.co.sint.webshop.data.attribute.Resources";

  private static final String RESOURCE_NAME_MENU = "jp.co.sint.webshop.web.text.back.BackResource";

  public static String getEntry(String key) {
    return getXmlBundle(RESOURCE_NAME_M17NCONFIG, getCurrentLocale(), key);
  }

  public static List<String> getListEntry(String key) {
    return getListXmlBundle(RESOURCE_NAME_M17NCONFIG, getCurrentLocale(), key);
  }

  public static Map<String, String> getMapEntry(String key) {
    return getMapXmlBundle(RESOURCE_NAME_M17NCONFIG, getCurrentLocale(), key);
  }

  public static <E extends Enum<?>>String getName(E e) {
    return getName(e, getCurrentLocale());
  }
  
//  //M17N-0006 追加 ここから
//  public static <E extends Enum<?>>String getName(E e, String languageCode) {
//    return getName(e, LocaleUtil.getLocale(languageCode));
//  }
//  //M17N-0006 追加 ここまで

  public static <E extends Enum<?>>String getName(E e, Locale locale) {
    return getBundle(RESOURCE_NAME_DOMAIN, locale, getSpecifiedName(e, "name"));
  }

  public static <E extends Enum<?>>String getValue(E e) {
    return getValue(e, getCurrentLocale());
  }

  public static <E extends Enum<?>>String getValue(E e, Locale locale) {
    return getBundle(RESOURCE_NAME_DOMAIN, locale, getSpecifiedName(e, "value"));
  }

  public static <E extends Enum<?>>String getCode(E e) {
    return getCode(e, getCurrentLocale());
  }

  public static <E extends Enum<?>>String getCode(E e, Locale locale) {
    return getBundle(RESOURCE_NAME_DOMAIN, locale, getSpecifiedName(e, "code"));
  }

  public static <E extends Enum<?>>String getDumyData(E e) {
    return getBundle(RESOURCE_NAME_DOMAIN, getCurrentLocale(), getSpecifiedName(e, "dummyData"));
  }

  public static <E extends Enum<?>>String getDumyData(E e, Locale locale) {
    return getBundle(RESOURCE_NAME_DOMAIN, locale, getSpecifiedName(e, "dummyData"));
  }

  public static String getMetadata(Field field, String attrName, Object instance) {
    String className = instance.getClass().getCanonicalName();
    String fieldName = field.getName();
    return getBundle(RESOURCE_NAME_ATTRIBUTE, getCurrentLocale(),
        getSpecifiedName(className, fieldName, "Metadata", attrName));
  }

  private static String getSpecifiedName(String className, String fieldName, String annotation, String attribute) {
    return MessageFormat.format("{0}.{1}.{2}.{3}", className, fieldName, annotation, attribute);
  }

  private static <E extends Enum<?>>String getSpecifiedName(E e, String property) {
    String x = MessageFormat.format("{0}.{1}.{2}", e.getClass().getName(), e.name(), property);
    return x;
  }

  public static <E extends Enum<?>>String getLabel(E e) {
    return getBundle(RESOURCE_NAME_MENU, getCurrentLocale(), getSpecifiedName(e, "label"));
  }

  private static String getBundle(String baseName, Locale locale, String key) {
    String s = null;
    try {
      s = ResourceBundle.getBundle(baseName, locale).getString(key);
    } catch (RuntimeException e) {
      Logger logger = Logger.getLogger(CodeUtil.class);
      logger.warn(e);
      s = null;
    }
    return s;
  }

  private static String getXmlBundle(String baseName, Locale locale, String key) {
    String s = null;
    try {
      s = ResourceBundle.getBundle(baseName, locale, XmlResourceBundle.CONTROL).getString(key);
    } catch (RuntimeException e) {
      Logger logger = Logger.getLogger(CodeUtil.class);
      logger.warn(e);
      s = null;
    }
    return s;
  }

  private static List<String> getListXmlBundle(String baseName, Locale locale, String key) {
    List<String> result = new ArrayList<String>();
    try {
      ResourceBundle rb = ResourceBundle.getBundle(baseName, locale, XmlResourceBundle.CONTROL);
      List<String> keyList = new ArrayList<String>(rb.keySet());
      Collections.sort(keyList);

      for (String s : keyList) {
        if (s.startsWith(key)) {
          result.add(rb.getString(s));
        }
      }
    } catch (RuntimeException e) {
      Logger logger = Logger.getLogger(CodeUtil.class);
      logger.warn(e);
    }
    return result;
  }

  private static Map<String, String> getMapXmlBundle(String baseName, Locale locale, String key) {
    Map<String, String> result = new HashMap<String, String>();
    try {
      ResourceBundle rb = ResourceBundle.getBundle(baseName, locale, XmlResourceBundle.CONTROL);
      for (String s : rb.keySet()) {
        if (s.startsWith(key)) {
          result.put(s, rb.getString(s));
        }
      }
    } catch (RuntimeException e) {
      Logger logger = Logger.getLogger(CodeUtil.class);
      logger.warn(e);
    }
    return result;
  }

}
