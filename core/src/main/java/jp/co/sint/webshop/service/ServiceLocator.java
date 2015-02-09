package jp.co.sint.webshop.service;

import java.text.MessageFormat;

import jp.co.sint.webshop.service.tmall.TmallService;
import jp.co.sint.webshop.text.Messages;
import jp.co.sint.webshop.utility.DIContainer;

import org.apache.log4j.Logger;

/**
 * SI Web Shopping 10 サービスロケータ. ユーザーコンテキストに基づいたサービスのインスタンスを提供します。
 * 
 * @author System Integrator Corp.
 */
public final class ServiceLocator {

  /** constructor */
  private ServiceLocator() {
  }

  private static void log(StatefulService service) {
    LoginInfo loginInfo = service.getLoginInfo();
    String serviceName = service.getClass().getSimpleName().replace("Impl", "");
    Logger.getLogger(ServiceLocator.class).debug(
        MessageFormat.format(Messages.log("service.ServiceLocator.0"),  serviceName, formatLogin(loginInfo)));
  }

  private static String formatLogin(LoginInfo loginInfo) {
    if (loginInfo == null) {
      return Messages.getString("service.ServiceLocator.1");
    }
    return MessageFormat.format(
        Messages.getString("service.ServiceLocator.2"), loginInfo.getLoginId(), loginInfo.getClass().getSimpleName());
  }

  /**
   * 分析サービスを取得します。
   * 
   * @param loginInfo
   *          ログイン情報
   * @return 分析サービス
   */
  public static AnalysisService getAnalysisService(LoginInfo loginInfo) {
    StatefulService impl = DIContainer.get("Analysis");
    impl.setLoginInfo(loginInfo);
    log(impl);
    return (AnalysisService) impl;
  }

  /**
   * 認証サービスを取得します。
   * 
   * @param loginInfo
   *          ログイン情報
   * @return 認証サービス
   */
  public static AuthorizationService getAuthorizationService(LoginInfo loginInfo) {
    StatefulService impl = DIContainer.get("Authorization");
    impl.setLoginInfo(loginInfo);
    log(impl);
    return (AuthorizationService) impl;
  }

  /**
   * カタログサービスを取得します。
   * 
   * @param loginInfo
   *          ログイン情報
   * @return カタログサービス
   */
  public static CatalogService getCatalogService(LoginInfo loginInfo) {
    StatefulService impl = DIContainer.get("Catalog");
    impl.setLoginInfo(loginInfo);
    log(impl);
    return (CatalogService) impl;
  }

  /**
   * コミュニケーションサービスを取得します。
   * 
   * @param loginInfo
   *          ログイン情報
   * @return コミュニケーションサービス
   */
  public static CommunicationService getCommunicationService(LoginInfo loginInfo) {
    StatefulService impl = DIContainer.get("Communication");
    impl.setLoginInfo(loginInfo);
    log(impl);
    return (CommunicationService) impl;
  }

  /**
   * 顧客サービスを取得します。
   * 
   * @param loginInfo
   *          ログイン情報
   * @return 顧客サービス
   */
  public static CustomerService getCustomerService(LoginInfo loginInfo) {
    StatefulService impl = DIContainer.get("Customer");
    impl.setLoginInfo(loginInfo);
    log(impl);
    return (CustomerService) impl;
  }

  /**
   * データ入出力サービスを取得します。
   * 
   * @param loginInfo
   *          ログイン情報
   * @return データ入出力サービス
   */
  public static DataIOService getDataIOService(LoginInfo loginInfo) {
    StatefulService impl = DIContainer.get("DataIO");
    impl.setLoginInfo(loginInfo);
    log(impl);
    return (DataIOService) impl;
  }

  /**
   * メールサービスを取得します。
   * 
   * @param loginInfo
   *          ログイン情報
   * @return メールサービス
   */
  public static MailingService getMailingService(LoginInfo loginInfo) {
    StatefulService impl = DIContainer.get("Mailing");
    impl.setLoginInfo(loginInfo);
    log(impl);
    return (MailingService) impl;
  }

 //Add by V10-CH start
  public static SmsingService getSmsingService(LoginInfo loginInfo) {
    StatefulService impl = DIContainer.get("Smsing");
    impl.setLoginInfo(loginInfo);
    log(impl);
    return (SmsingService) impl;
  }
  //Add by V10-CH end
  
  
  /**
   * 受注管理サービスを取得します。
   * 
   * @param loginInfo
   *          ログイン情報
   * @return 受注管理サービス
   */
  public static OrderService getOrderService(LoginInfo loginInfo) {
    StatefulService impl = DIContainer.get("Order");
    impl.setLoginInfo(loginInfo);
    log(impl);
    return (OrderService) impl;
  }
// 2014/06/10 库存更新对应 ob_张震 add start
  /**
   * 在庫管理サービスを取得します。
   * 
   * @param loginInfo
   *          ログイン情報
   * @return 在庫管理サービス
   */
  public static StockService getStockService(LoginInfo loginInfo) {
    StatefulService impl = DIContainer.get("Stock");
    impl.setLoginInfo(loginInfo);
    log(impl);
    return (StockService) impl;
  }
// 2014/06/10 库存更新对应 ob_张震 add end
  /**
   * ショップ管理サービスを取得します。
   * 
   * @param loginInfo
   *          ログイン情報
   * @return ショップ管理サービス
   */
  public static ShopManagementService getShopManagementService(LoginInfo loginInfo) {
    StatefulService impl = DIContainer.get("ShopManagement");
    impl.setLoginInfo(loginInfo);
    log(impl);
    return (ShopManagementService) impl;
  }

  /**
   * サイト管理サービスを取得します。
   * 
   * @param loginInfo
   *          ログイン情報
   * @return サイト管理サービス
   */
  public static SiteManagementService getSiteManagementService(LoginInfo loginInfo) {
    StatefulService impl = DIContainer.get("SiteManagement");
    impl.setLoginInfo(loginInfo);
    log(impl);
    return (SiteManagementService) impl;
  }

  /**
   * ユーティリティサービスを取得します。
   * 
   * @param loginInfo
   *          ログイン情報
   * @return ユーティリティサービス
   */
  public static UtilService getUtilService(LoginInfo loginInfo) {
    StatefulService impl = DIContainer.get("Util");
    impl.setLoginInfo(loginInfo);
    log(impl);
    return (UtilService) impl;
  }
  
  // 2014/04/29 京东WBS对应 ob_姚 add start
  /**
   * 京东サービスを取得します。
   * 
   * @param loginInfo
   *          ログイン情報
   * @return 京东サービス
   */
  public static JdService getJdService(LoginInfo loginInfo) {
    StatefulService impl = DIContainer.get("jd");
    impl.setLoginInfo(loginInfo);
    log(impl);
    return (JdService) impl;
  }
  // 2014/04/29 京东WBS对应 ob_姚 add end
  
  // 2012-01-11 yyq add start
  /**
   * 淘宝API调用获得
   * 
   * @param loginInfo
   *          ログイン情報
   * @return 实现结果
   */
  public static TmallService getTmallService(LoginInfo loginInfo) {
    StatefulService impl = DIContainer.get("Tmall");
    impl.setLoginInfo(loginInfo);
    log(impl);
    return (TmallService) impl;
  }
  // 2012-01-11 yyq add end

}
