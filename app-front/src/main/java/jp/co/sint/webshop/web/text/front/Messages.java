/**
 * 
 */
package jp.co.sint.webshop.web.text.front;

import jp.co.sint.webshop.text.M17NMessageBase;

/**
 * @author System Integrator Corp.
 */
public final class Messages {

  private static final String BUNDLE_NAME = "jp.co.sint.webshop.web.text.front.FrontMessages"; //$NON-NLS-1$

  private Messages() {
  }

  public static String getString(String key) {
    return M17NMessageBase.getString(key, BUNDLE_NAME);
  }

  public static String log(String key) {
    return M17NMessageBase.log(key, BUNDLE_NAME);
  }
}
