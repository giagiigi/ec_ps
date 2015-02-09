package jp.co.sint.webshop.web.log.back;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.StringUtil;

/**
 * 管理側アクセスログのユーティリティクラスです。
 * 
 * @author System Integrator Corp.
 */
public final class AccessLogUtil {

  /**
   * default constructor
   */
  private AccessLogUtil() {
  }

  /**
   * オペレーションコードからアクション名を取得する。
   * 
   * @param operationCode
   *          オペレーションコード
   * @return オペレーションコードから得られるアクション名
   */
  public static String getActionName(String operationCode) {
    String actionName = "";
    if (StringUtil.hasValue(operationCode)) {
      actionName = getActionNameFromResouce(operationCode);
    }

    if (StringUtil.isNullOrEmpty(actionName)) {
      String accessLogDivCode = operationCode.substring(0, 1);
      AccessLogDiv div = AccessLogDiv.fromValue(accessLogDivCode);
      actionName = div.getName();
    }
    return actionName;
  }

  /**
   * 指定されたアクションがログを出力するものかどうか判断します。
   * 
   * @param operationCode
   *          出力するActionのオペレーションコード
   * @return ログ出力の有無 true-出力する false-出力しない
   */
  public static boolean isLogOut(String operationCode) {
    if (StringUtil.hasValue(operationCode)) {
      // ResouceにあるオペレーションコードのActionのみ出力
      String actionName = getActionNameFromResouce(operationCode);
      return StringUtil.hasValue(actionName);
    } else {
      return false;
    }
  }

  private static String getActionNameFromResouce(String operationCode) {
    
    ResourceBundle bundle = 
      ResourceBundle.getBundle("jp.co.sint.webshop.web.log.back.OperationName", DIContainer.getLocaleContext().getSystemLocale());
    
    try {
      String actionName = StringUtil.coalesce(bundle.getString(operationCode), "");
      return actionName;
    } catch (MissingResourceException e) {
      // リソースに存在しないオペレーションの可能性もあり
      return "";
    }
  }
}
