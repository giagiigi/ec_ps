package jp.co.sint.webshop.service.customer;

import jp.co.sint.webshop.text.Messages;

/**
 * 定数クラス
 * 
 * @author System Integrator Corp.
 */
public final class CustomerConstant {

  /**
   * default constructor
   */
  private CustomerConstant() {
  }

  /** デフォルト顧客グループコード */
  public static final String DEFAULT_GROUP_CODE = Messages.getString("service.customer.CustomerConstant.0");

  // 10.1.3 10150 修正 ここから
  /** @deprecated SELF_ADDRESS_NO を使用してください */
  public static final long SELFE_ADDRESS_NO = 0L;
  /** @deprecated SELF_ADDRESS_ALIAS を使用してください */
  public static final String SELFE_ADDRESS_AIES = Messages.getString("service.customer.CustomerConstant.1");

  /** 本人アドレス帳番号 */
  public static final long SELF_ADDRESS_NO = 0L;

  /** 本人アドレス帳呼称 */
  public static final String SELF_ADDRESS_ALIAS = Messages.getString("service.customer.CustomerConstant.1");
  // 10.1.3 10150 修正 ここまで

  /** ポイント履歴説明文 */
  public static final String POINT_DESCRIPTION = Messages.getString("service.customer.CustomerConstant.2");;

  /** データマスク用アスタリスク */
  public static final String MASK_DATA = Messages.getString("service.customer.CustomerConstant.3");

  /** ゲスト用顧客コード */
  public static final String GUEST_CUSTOMER_CODE = Messages.getString("service.customer.CustomerConstant.4");

  /** ポイントオーバーフロー時エラーコード */
  public static final long POINT_OVERFLOW_CODE = 2L;

  public static boolean isGuest(String customerCode) {
    return !isCustomer(customerCode);
  }

  public static boolean isCustomer(String customerCode) {
    return customerCode.startsWith(Messages.getString("service.customer.CustomerConstant.5"));
  }
}
