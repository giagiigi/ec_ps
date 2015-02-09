
package jp.co.sint.webshop.utility;

import java.util.List;

/**
 * 
 * @author System Integrator Corp.
 */
public final class SetCommodityUtil {

  public static final String SET_COMMODITY_DELIMITER = ":";

  /** Default Constructor */
  private SetCommodityUtil() {
  }

  public static String createProvisionalSkuCode(String skuCode, List<Sku> compositionList) {
    StringBuilder builder = new StringBuilder();
    builder.append(skuCode);
    if (compositionList != null) {
      for (Sku sku : compositionList) {
        builder.append(SET_COMMODITY_DELIMITER);
        builder.append(sku.getSkuCode());
      }
    }
    return builder.toString();
  }
}
