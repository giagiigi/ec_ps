/**
 * 
 */
package jp.co.sint.webshop.text;

import java.text.MessageFormat;
import java.util.Locale;

import jp.co.sint.webshop.utility.DIContainer;

/**
 * @author System Integrator Corp.
 */
public final class M17NText {

  private static final String BUNDLE_NAME = "jp.co.sint.webshop.text.M17NResource"; //$NON-NLS-1$

  private M17NText() {
  }

  public static String getString(String key) {
    return M17NMessageBase.getString(key, BUNDLE_NAME);
  }

  public static String log(String key) {
    return M17NMessageBase.log(key, BUNDLE_NAME);
  }

  public static String getCountryName(String countryCode) {
    Locale locale = DIContainer.getLocaleContext().getCurrentLocale();
    return getCountryName(countryCode, locale);
  }
  public static String getCountryName(String countryCode, Locale locale) {
    return M17NMessageBase.get(MessageFormat.format("country.{0}.name", countryCode), BUNDLE_NAME, locale);
  }

  public static String getAddress1Name(String countryCode) {
    Locale locale = DIContainer.getLocaleContext().getCurrentLocale();
    return getAddress1Name(countryCode, locale); 
  }

  public static String getAddress1Name(String countryCode, Locale locale) {
    return M17NMessageBase.get(MessageFormat.format("country.{0}.address1Name", countryCode), BUNDLE_NAME, locale);
  }

  public static String getRegionName(String countryCode, String regionCode) {
    Locale locale = DIContainer.getLocaleContext().getCurrentLocale();
    return getRegionName(countryCode, regionCode, locale);
  }

  public static String getRegionName(String countryCode, String regionCode, Locale locale) {
    return M17NMessageBase.get(MessageFormat.format("country.{0}.region.{1}.name", countryCode, regionCode), BUNDLE_NAME, locale);
  }

  public static String getCurrencyName(String currencyCode) {
    Locale locale = DIContainer.getLocaleContext().getCurrentLocale();
    return getCurrencyName(currencyCode, locale);
  }

  public static String getCurrencyName(String currencyCode, Locale locale) {
    return M17NMessageBase.get(MessageFormat.format("currency.{0}.name", currencyCode), BUNDLE_NAME, locale);
  }

  public static String getLanguageName(String languageCode) {
    Locale locale = DIContainer.getLocaleContext().getCurrentLocale();
    return getLanguageName(languageCode, locale);
  }

  public static String getLanguageName(String languageCode, Locale locale) {
    return M17NMessageBase.get(MessageFormat.format("language.{0}.name", languageCode), BUNDLE_NAME, locale);
  }

}
