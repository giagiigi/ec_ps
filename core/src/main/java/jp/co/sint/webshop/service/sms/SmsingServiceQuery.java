package jp.co.sint.webshop.service.sms;

/**
 * メールサービスで使用するクエリを集約したクラス
 * 
 * @author System Integrator Corp.
 */
public final class SmsingServiceQuery {

  /** default constructor */
  private SmsingServiceQuery() {
  }

  public static final String LOAD_SMS_USEFLG = "SELECT SMS_USE_FLG FROM SMS_TEMPLATE_DETAIL WHERE SMS_TYPE = ?";
  
}
