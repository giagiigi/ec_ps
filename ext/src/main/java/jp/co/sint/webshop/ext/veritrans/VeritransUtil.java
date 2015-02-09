package jp.co.sint.webshop.ext.veritrans;

import jp.co.veritrans.em.gwlib.PushAuthentication;
import Jp.BuySmart.CVS.GWLib.JceResource;
import Jp.BuySmart.CVS.GWLib.PushHelper;

public final class VeritransUtil {

  private VeritransUtil() {
  }

  public static boolean checkCvsHmac(String secretKey, String messageBody, String hmac) {
    return PushHelper.getInstance().checkHmac(JceResource.DEFAULT_PROVIDER, secretKey, messageBody, hmac);
  }

  public static boolean checkDigitalCashHmac(String secretKey, String messageBody, String hmac) {
    return PushAuthentication.checkMessage(secretKey, messageBody, hmac);
  }

}
