package jp.co.sint.webshop.utility;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.sql.DataSource;

import jp.co.sint.webshop.api.jd.JdApiConfig;
import jp.co.sint.webshop.configure.AdvertValue;
import jp.co.sint.webshop.configure.CouponNoticeValue;
import jp.co.sint.webshop.configure.DeliveryCompanyNoMapping;
import jp.co.sint.webshop.configure.ExampleValue;
import jp.co.sint.webshop.configure.InvoiceValue;
import jp.co.sint.webshop.configure.LocaleContext;
import jp.co.sint.webshop.configure.PlanDetailTypeValue;
import jp.co.sint.webshop.configure.SalesTagByTop;
import jp.co.sint.webshop.configure.SitemapConfig;
import jp.co.sint.webshop.configure.SmsValue;
import jp.co.sint.webshop.configure.StockValue;
import jp.co.sint.webshop.configure.TaxClassValue;
import jp.co.sint.webshop.configure.WebShopSpecialConfig;
import jp.co.sint.webshop.configure.WebshopConfig;
import jp.co.sint.webshop.configure.WebshopMessage;
import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.GenericDao;
import jp.co.sint.webshop.data.TransactionManager;
import jp.co.sint.webshop.data.cache.CacheContainer;
import jp.co.sint.webshop.data.cache.impl.CacheContainerStub;
import jp.co.sint.webshop.data.csv.CsvHandler;
import jp.co.sint.webshop.data.csv.CsvSchema;
import jp.co.sint.webshop.mail.MailSender;
import jp.co.sint.webshop.service.cart.Cart;
import jp.co.sint.webshop.service.cart.CashierPayment.CashierPaymentTypeBase;
import jp.co.sint.webshop.service.data.CsvSchemaType;
import jp.co.sint.webshop.service.order.DummyPayment;
import jp.co.sint.webshop.service.order.PaymentProvider;
import jp.co.sint.webshop.sms.SmsSender;
import jp.co.sint.webshop.validation.NumberLimitPolicy;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;

/**
 * 外部ファイルの初期設定情報を読み込むユーティリティクラスです。
 * 
 * @author System Integrator Corp.
 */
public final class DIContainer {

  private static ApplicationContext context;

  /**
   * ApplicationContextを設定します。
   * 
   * @param ctx
   *          ApplicationContext
   */
  public static synchronized void setApplicationContext(ApplicationContext ctx) {
    context = ctx;
  }

  /**
   * ApplicationContextを取得します。
   * 
   * @return ApplicationContext
   */
  public static ApplicationContext getApplicationContext() {
    return context;
  }

  private static Object getBean(String name) {
    Object obj = null;
    try {
      obj = getApplicationContext().getBean(name);
    } catch (NoSuchBeanDefinitionException nbdEx) {
      throw new LoadingObjectFailureException(nbdEx);
    }
    return obj;
  }

  private static Resource getResource(String name) {
    return getApplicationContext().getResource(name);
  }

  private DIContainer() {
  }

  /**
   * システム設定情報を取得します。
   * 
   * @return WebshopConfigのインスタンス
   */
  public static WebshopConfig getWebshopConfig() {
    return (WebshopConfig) getBean(WebshopConfig.class.getSimpleName());
  }

  /**
   * サンプルデータを取得します。
   * 
   * @return ExampleValueのインスタンス
   */
  public static ExampleValue getExampleValue() {
    return (ExampleValue) getBean(ExampleValue.class.getSimpleName());
  }

  /**
   * 获取库存基本应用。
   * 
   * @return StockValueのインスタンス
   */
  public static StockValue getStockValue() {
    return (StockValue) getBean(StockValue.class.getSimpleName());
  }

  /**
   * 获取发票商品信息。
   * 
   * @return InvoiceValueのインスタンス
   */
  public static InvoiceValue getInvoiceValue() {
    return (InvoiceValue) getBean(InvoiceValue.class.getSimpleName());
  }

  /**
   * 広告を応用します
   * 
   * @return AdvertValueのインスタンス
   */
  public static AdvertValue getAdvertValue() {
    return (AdvertValue) getBean(AdvertValue.class.getSimpleName());
  }

  /**
   * SMS应用
   */
  public static SmsValue getSmsValue() {
    return (SmsValue) getBean(SmsValue.class.getSimpleName());
  }

  /**
   * 引数で受取った型のDaoを取得します。<br>
   * 引数で受取った型が存在しなかった場合、LoadingObjectFailureExceptionをスローします。
   * 
   * @param <T>
   *          daoClass
   * @return GenericDaoを拡張したクラスのインスタンス
   */
  @SuppressWarnings("unchecked")
  public static <T extends GenericDao>T getDao(Class<T> daoClass) {
    return (T) getBean(daoClass.getSimpleName());
  }

  /**
   * 引数で受取った型のインスタンスを取得します。
   * 引数で受取った型が存在しなかった場合、LoadingObjectFailureExceptionをスローします。
   * 
   * @param <T>
   *          取得するインスタンスの型
   * @param name
   *          取得するクラスの名前
   * @return 引数で受取ったクラスのインスタンス
   */
  @SuppressWarnings("unchecked")
  public static <T>T get(String name) {
    return (T) getBean(name);
  }

  public static DataSource getWebshopDataSource() {
    return (DataSource) getBean(DatabaseUtil.WEBSHOP_DATASOURCE);
  }

  /**
   * トランザクションマネージャを取得します。
   * 
   * @return TransactionManagerのインスタンス
   */
  public static TransactionManager getTransactionManager() {
    return (TransactionManager) getBean(TransactionManager.class.getSimpleName());
  }

  /**
   * メール送信エージェントを取得します。
   * 
   * @return メール送信エージェント
   */
  public static MailSender getMailSender() {
    return (MailSender) getBean(MailSender.class.getSimpleName());
  }

  // Add by V10-CH start
  public static SmsSender getSmsSender() {
    return (SmsSender) getBean(SmsSender.class.getSimpleName());
  }

  // Add by V10-CH end
  /**
   * ユーザーエージェントマネージャを取得します。
   * 
   * @return ユーザーエージェントマネージャ
   */
  public static UserAgentManager getUserAgentManager() {
    return (UserAgentManager) getBean(UserAgentManager.class.getSimpleName());
  }

  /**
   * 問い合わせ設定を取得します。
   * 
   * @return 問い合わせ設定
   */
  public static InquiryConfig getInquiryConfig() {
    return (InquiryConfig) getBean(InquiryConfig.class.getSimpleName());
  }

  /**
   * 获取图片上传config
   * 
   * @return ImageUploadConfig
   */
  public static ImageUploadConfig getImageUploadConfig() {
    return (ImageUploadConfig) getBean(ImageUploadConfig.class.getSimpleName());
  }

  /**
   * ショッピングカートを取得します。
   * 
   * @return ショッピングカート
   */
  public static Cart getCart() {
    return (Cart) getBean(Cart.class.getSimpleName());
  }

  /**
   * 限界値設定・チェック処理を取得します。
   * 
   * @return 限界値設定・チェック処理
   */
  public static NumberLimitPolicy getNumberLimitPolicy() {
    return (NumberLimitPolicy) getBean(NumberLimitPolicy.class.getSimpleName());
  }

  /**
   * 支払プロバイダを取得します。<br>
   * cashierはCashierPaymentTypeBaseを継承したクラスであるものとします。<br>
   * <br>
   * CashierPaymentTypeBaseを継承していないクラスを引数で受取った場合、DummyPaymentインスタンスが生成され、これを返します
   * 。
   * 
   * @param cashier
   * @return 支払プロバイダ
   */
  public static PaymentProvider getPaymentProvider(Class<? extends CashierPaymentTypeBase> cashier) {
    Object provider = null;
    try {
      provider = getBean(cashier.getSimpleName());
      if (provider == null) {
        provider = new DummyPayment();
      }
    } catch (Exception e) {
      provider = new DummyPayment();
    }
    return (PaymentProvider) provider;
  }

  /**
   * リソースURLを取得します。
   * 
   * @param resourcePath
   *          リソースパス
   * @return リソースURL
   */
  public static URL getResourceUrl(String resourcePath) {
    Resource resource = getResource(resourcePath);
    URL url = null;
    try {
      url = resource.getURL();
    } catch (IOException ioEx) {
      url = null;
    }
    return url;
  }

  /**
   * リソースファイルを取得します。
   * 
   * @param resourcePath
   *          リソースパス
   * @return リソースファイル
   */
  public static File getResourceFile(String resourcePath) {
    Resource resource = getResource(resourcePath);
    File file = null;
    try {
      file = resource.getFile();
    } catch (IOException ioEx) {
      file = null;
    }
    return file;
  }

  /**
   * メールマガジン配信登録/停止/確認時に送信するメール情報を取得します。
   * 
   * @return メール情報
   */
  public static MailMagazineConfig getMailMagazineConfig() {
    return (MailMagazineConfig) getBean(MailMagazineConfig.class.getSimpleName());
  }

  /**
   * CSV入出力クラスを取得します。
   * 
   * @return CSV入出力クラス
   */
  public static CsvHandler getCsvHandler() {
    return (CsvHandler) getBean(CsvHandler.class.getSimpleName());
  }

  /**
   * CSVスキーマクラスを取得します。
   * 
   * @param type
   *          CSVスキーマタイプ
   * @return CSVスキーマクラス
   */
  @SuppressWarnings("unchecked")
  public static <T extends CsvSchema>T getCsvSchema(CsvSchemaType type) {
    return (T) getBean(type.getId());
  }

  /**
   * SI Web Shoppingで利用するメッセージを取得します。
   * 
   * @return メッセージ
   */
  public static WebshopMessage getWebshopMessage() {
    return (WebshopMessage) getBean(WebshopMessage.class.getSimpleName());
  }

  /**
   * 郵便番号検索プロバイダを取得します。
   * 
   * @return 郵便番号検索プロバイダ
   */
  public static PostalSearch getPostalSearch() {
    return (PostalSearch) getBean(PostalSearch.class.getSimpleName());
  }

  /**
   * 支払方法に関する設定を取得します。
   * 
   * @return 支払方法設定情報
   */
  public static PaymentConfig getPaymentConfig() {
    return (PaymentConfig) getBean(PaymentConfig.class.getSimpleName());
  }

  // V10-CH 170 追加 ここから
  /**
   * ロケール設定情報を取得します。
   * 
   * @return LocaleContextのインスタンス
   */
  public static LocaleContext getLocaleContext() {
    return (LocaleContext) getBean(LocaleContext.class.getSimpleName());
  }

  public static boolean isReady() {
    return getApplicationContext() != null;
  }

  /**
   * 文字変換フィルタを取得します。
   * 
   * @return CharacterConverterのインスタンス
   */
  public static CharacterConverter getCharacterConverter() {
    return (CharacterConverter) getBean(CharacterConverter.class.getSimpleName());
  }

  // add by yl 20111209 start
  /**
   * 会员咨询配置文件取得
   * 
   * @return 会员咨询設定
   */
  public static MemberInquiryConfig getMemberInquiryConfig() {
    return (MemberInquiryConfig) getBean(MemberInquiryConfig.class.getSimpleName());
  }

  /**
   * 退换货交换理由
   * 
   * @return 退换货交换理由設定
   */
  public static ExchangeReasonConfig getExchangeReasonConfig() {
    return (ExchangeReasonConfig) getBean(ExchangeReasonConfig.class.getSimpleName());
  }

  // add by yl 20111209 end
  // V10-CH 170 追加 ここまで

  /**
   * 企划明细类别
   */
  public static PlanDetailTypeValue getPlanDetailTypeValue() {
    return (PlanDetailTypeValue) getBean(PlanDetailTypeValue.class.getSimpleName());
  }

  public static CouponNoticeValue getCouponNoticeValue() {
    return (CouponNoticeValue) getBean(CouponNoticeValue.class.getSimpleName());
  }

  /**
   * 税率区分
   * 
   * @return 税率区分设定
   */
  public static TaxClassValue getTaxClassValue() {
    return (TaxClassValue) getBean(TaxClassValue.class.getSimpleName());
  }

  /**
   * 配送公司mapping mapping结果
   */
  public static DeliveryCompanyNoMapping getDeliveryCompanyNoMapping() {
    return (DeliveryCompanyNoMapping) getBean(DeliveryCompanyNoMapping.class.getSimpleName());
  }

  /**
   * 首页分类商品，根据tag查询
   */
  public static SalesTagByTop getSalesTagByTop() {
    return (SalesTagByTop) getBean(SalesTagByTop.class.getSimpleName());
  }
  // 20130830 txw add start
  /**
   * 网站地图。
   * 
   * @return SitemapConfigのインスタンス
   */
  public static SitemapConfig getSitemapConfig() {
    return (SitemapConfig) getBean(SitemapConfig.class.getSimpleName());
  }
  // 20130830 txw add end
  
  public static CacheContainer getCacheContainer() {
    // 10.5.0 10677 修正 ここから
    // return (CacheContainer) getBean(CacheContainer.class.getSimpleName());
    Logger logger = Logger.getLogger(DIContainer.class);
    CacheContainer container = null;
    try {
      container = (CacheContainer) getBean(CacheContainer.class.getSimpleName());
    } catch (LoadingObjectFailureException e) {
      logger.debug("キャッシュコンテナのDIに失敗しました。");
    }
    if (container == null) {
      logger.debug("キャッシュコンテナがnullです。CacheContainerStubのインスタンスを新規作成して返します。");
      container = new CacheContainerStub();
    }
    return container;
    // 10.5.0 10677 修正 ここまで
  }

//2014/4/28 京东WBS对应 ob_李 add start
  /**
   * 京东API用Config。
   * 
   * @return JdApiConfigのインスタンス
   */
  public static JdApiConfig getJdApiConfig() {
    return (JdApiConfig) getBean(JdApiConfig.class.getSimpleName());
  }
//2014/4/28 京东WBS对应 ob_李 add end
  // 20150120 hdh add start
  public static WebShopSpecialConfig getWebShopSpecialConfig() {
    return (WebShopSpecialConfig) getBean(WebShopSpecialConfig.class.getSimpleName());
  }
  // 20150120 hdh add end
}
