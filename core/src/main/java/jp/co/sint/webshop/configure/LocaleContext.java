package jp.co.sint.webshop.configure;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jp.co.sint.webshop.utility.BeanUtil;
import jp.co.sint.webshop.utility.CollectionUtil;
import jp.co.sint.webshop.utility.LocaleUtil;

/**
 * SI Web Shoppingの地域・言語設定管理クラスです。
 * 
 * @author System Integrator Corp.
 */
public class LocaleContext {

  public static final Locale CODING_LOCALE = Locale.ENGLISH;

  private List<String> supportingLanguages = new ArrayList<String>();

  private List<String> deliveryCountries = new ArrayList<String>();

  private Locale defaultLocale;

  private Locale systemLocale;

  private ThreadLocal<Locale> currentLocale = new ThreadLocal<Locale>();

  // M17N-0004　追加　ここから
  private boolean enableKanaField;
  // M17N-0004　追加　ここまで
  /**
   * 対応言語コードのリストを取得します。
   * 
   * @return supportingLanguages 対応している言語コード(ISO 639形式)のリスト
   */
  public List<String> getSupportingLanguages() {
    return supportingLanguages;
  }

  /**
   * 対応言語コードのリストを設定します。
   * 
   * @param supportingLanguages
   *          対応言語コードのリスト
   */
  public void setSupportingLanguages(List<String> supportingLanguages) {
    this.supportingLanguages.clear();
    CollectionUtil.copyAll(this.supportingLanguages, supportingLanguages);
  }

  //M17N-0006 ここから
  /**
   * 指定された言語コード値が有効かどうかを返します。

   * @param languageCode
   *          言語コード値
   * @return 言語コード値が有効であればtrue
   */
  public boolean isValidLanguage(String languageCode) {
    return getSupportingLanguages().contains(languageCode);
    }
  
  /**
   * 指定された国コード値が有効かどうかを返します。

   * @param countryCode
   *          国コード値
   * @return 国コード値が有効であればtrue
   */
  public boolean isValidCountry(String countryCode) {
    return getDeliveryCountries().contains(countryCode);
  }
  //M17N-0006 ここまで
  /**
   * deliveryCountriesを取得します。
   * 
   * @return deliveryCountries deliveryCountries
   */
  public List<String> getDeliveryCountries() {
    return deliveryCountries;
  }

  /**
   * deliveryCountriesを設定します。
   * 
   * @param deliveryCountries
   *          deliveryCountries
   */
  public void setDeliveryCountries(List<String> deliveryCountries) {
    this.deliveryCountries.clear();
    CollectionUtil.copyAll(this.deliveryCountries, deliveryCountries);
  }

  /**
   * デフォルトロケールを取得します。 デフォルトロケールが設定されていない場合は、代替値としてjava.util.Locale.JAPANを返します。
   * 
   * @return デフォルトロケール
   */
  public Locale getDefaultLocale() {
    return BeanUtil.coalesce(defaultLocale, Locale.JAPAN);
  }

  /**
   * デフォルトロケールを設定します。
   * 
   * @param defaultLocale
   *          デフォルトロケール
   */
  public void setDefaultLocale(Locale defaultLocale) {
    this.defaultLocale = defaultLocale;
  }

  /**
   * システムロケールを取得します。 システムロケールが設定されていない場合は、代替値としてデフォルトロケールを返します。
   * 
   * @return システムロケール
   */
  public Locale getSystemLocale() {
    return BeanUtil.coalesce(this.systemLocale, this.getDefaultLocale());
  }

  /**
   * システムロケールを設定します。
   * 
   * @param systemLocale
   *          システムロケール
   */
  public void setSystemLocale(Locale systemLocale) {
    this.systemLocale = systemLocale;
  }

  /**
   * 現在のスレッドで有効な、表示ロケールを取得します。
   * <p>
   * カレント表示ロケールが設定されていない場合は、代替値としてデフォルトロケールを返します。
   * </p>
   * 
   * @return 表示ロケール
   */
  public Locale getCurrentLocale() {
    return BeanUtil.coalesce(this.currentLocale.get(), this.getDefaultLocale());
  }

  /**
   * 現在のスレッドで有効な、表示ロケールの言語コードを取得します。
   * <p>
   * カレント表示ロケールが設定されていない場合は、代替値としてデフォルトロケールの言語コードを返します。
   * </p>
   * 
   * @return 表示ロケールの言語コード
   */
  public String getCurrentLanguageCode() {
    return LocaleUtil.getLanguageCode(this.getCurrentLocale());
  }

  /**
   * システムロケールの言語コードを取得します。 システムロケールが設定されていない場合は、
   * 代替値としてデフォルトロケールの言語コードを返します。
   * 
   * @return システムロケールの言語コード
   */
  public String getSystemLanguageCode() {
    return LocaleUtil.getLanguageCode(this.getSystemLocale());
  }
  
  /**
   * 現在のスレッドで有効な表示ロケールを設定します。
   * 
   * @param frontLocale
   *          表示用ロケール
   */
  public void setCurrentLocale(Locale currentLocale) {
    this.currentLocale.set(currentLocale);
  }

  /**
   * enableKanaFieldを取得します。
   * 
   * @return the enableKanaField
   */
  public boolean isEnableKanaField() {
    return enableKanaField;
  }

  /**
   * enableKanaFieldを設定します。
   * 
   * @param enableKanaField
   * 　　　　　　　　enableKanaField
   */
  public void setEnableKanaField(boolean enableKanaField) {
    this.enableKanaField = enableKanaField;
  }

}
