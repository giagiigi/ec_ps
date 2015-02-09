package jp.co.sint.webshop.service.catalog;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.utility.StringUtil;

/**
 * 「入出库オペレーション」のコード定義を表す列挙クラスです。
 * 
 * @author System Integrator Corp.
 */
public enum StockOperationType implements CodeAttribute {
  /** 入库 */
  ENTRY("入库", "0"),

  /** 出库 */
  DELIVER("出库", "1"),

  /** 订购 */
  ALLOCATE("订购", "2"),

  /** 预约 */   
  RESERVING("预约", "3"),

  /** 订购取消 */
  DEALLOCATE("订购取消", "4"),

  /** 预约取消 */
  CANCEL_RESERVING("预约取消", "5");

  private String name;

  private String value;

  private StockOperationType(String name, String value) {
    this.name = name;
    this.value = value;
  }

  public String getName() {
    return name;
  }

  public String getValue() {
    return value;
  }

  /**
   * Long型のコード値を返します。
   * 
   * @return コード値
   */
  public Long longValue() {
    return Long.valueOf(this.value);
  }

  /**
   * 指定されたコード名を持つ在库オペレーションタイプを返します。
   * 
   * @param name
   *          コード名
   * @return 在库オペレーションタイプ
   */
  public static StockOperationType fromName(String name) {
    for (StockOperationType p : StockOperationType.values()) {
      if (p.getName().equals(name)) {
        return p;
      }
    }
    return null;
  }

  /**
   * 指定されたコード値を持つ在库オペレーションタイプを返します。
   * 
   * @param value
   *          コード値
   * @return 在库オペレーションタイプ
   */
  public static StockOperationType fromValue(String value) {
    for (StockOperationType p : StockOperationType.values()) {
      if (p.getValue().equals(value)) {
        return p;
      }
    }
    return null;
  }

  /**
   * 指定されたコード値を持つ在库オペレーションタイプを返します。
   * 
   * @param value
   *          コード値
   * @return 在库オペレーションタイプ
   */
  public static StockOperationType fromValue(Long value) {
    return fromValue(Long.toString(value));
  }

  /**
   * 指定されたコード値が有効かどうかを返します。
   * 
   * @param value
   *          コード値
   * @return コード値が有効であればtrue
   */
  public static boolean isValid(String value) {
    if (StringUtil.hasValue(value)) {
      for (StockOperationType p : StockOperationType.values()) {
        if (p.getValue().equals(value)) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * 指定されたコード値が有効かどうかを返します。
   * 
   * @param value
   *          コード値
   * @return コード値が有効であればtrue
   */
  public static boolean isValid(Long value) {
    return isValid(Long.toString(value));
  }

}
