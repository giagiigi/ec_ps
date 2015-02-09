package jp.co.sint.webshop.code;

import java.util.HashMap;
import java.util.Map;

import jp.co.sint.webshop.data.domain.ArrivalGoodsFlg;
import jp.co.sint.webshop.data.domain.DisplayClientType;
import jp.co.sint.webshop.data.domain.StockManagementType;
import jp.co.sint.webshop.data.domain.TaxType;

/**
 * コード操作のユーティリティクラスです。
 * 
 * @author System Integrator Corp.
 */
public final class CodeUtil {

  private static Map<Class<?>, CodeAttribute> m;

  static {
    m = new HashMap<Class<?>, CodeAttribute>();
    m.put(DisplayClientType.class, DisplayClientType.ALL);
    m.put(StockManagementType.class, StockManagementType.WITH_QUANTITY);
    m.put(TaxType.class, TaxType.INCLUDED);
    m.put(ArrivalGoodsFlg.class, ArrivalGoodsFlg.UNACCEPTABLE);
  }

  private CodeUtil() {
  }

  /**
   * 列挙型で定義されているコードドメインの、デフォルト値を取得します。
   * 
   * @param obj
   *          列挙型で定義されているコードクラスの型情報
   * @return デフォルト値があれば対象のCodeAttibuteオブジェクト、なければnull
   */
  public static CodeAttribute getDefaultValue(Class<? extends Object> obj) {
    return m.get(obj);
  }

}
