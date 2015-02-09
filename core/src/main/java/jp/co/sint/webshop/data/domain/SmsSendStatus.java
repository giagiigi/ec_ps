//
//Copyright(C) 2007-2008 System Integrator Corp.
//All rights reserved.
//
// このファイルはコード定義ドキュメントから自動生成されます。
// 直接編集しないで下さい。
//
package jp.co.sint.webshop.data.domain;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.utility.CodeUtil;
import jp.co.sint.webshop.utility.StringUtil;

/**
 * 「メール送信ステータス」のコード定義を表す列挙クラスです。
 *
 * @author System Integrator Corp.
 *
 */
public enum SmsSendStatus implements CodeAttribute {

  /** 「未送信」を表す値です。 */
  NOT_SENT("未送信", "0"),

  /** 「すべて送信済み」を表す値です。 */
  SENT_ALL("すべて送信済み", "1"),

  /** 「一部送信エラー」を表す値です。 */
  PARTIAL_FAILED("一部送信エラー", "2"),

  /** 「すべて送信エラー」を表す値です。 */
  FAILED_ALL("すべて送信エラー", "3");

  private String name;

  private String value;

  private SmsSendStatus(String name, String value) {
    this.name = name;
    this.value = value;
  }
  /**
   * コード名称を返します。
   * @return コード名称
   */
  public String getName() {
    return StringUtil.coalesce(CodeUtil.getName(this), this.name);
  }

  /**
   * コード値を返します。
   * @return コード値
   */
  public String getValue() {
    return StringUtil.coalesce(CodeUtil.getValue(this), this.value);
  }

  /**
   * Long型のコード値を返します。
   * @return コード値
   */
  public Long longValue() {
    return Long.valueOf(this.value);
  }

  /**
   * 指定されたコード名を持つメール送信ステータスを返します。
   *
   * @param name コード名
   * @return メール送信ステータス
   */
  public static SmsSendStatus fromName(String name) {
    for (SmsSendStatus p : SmsSendStatus.values()) {
      if (p.getName().equals(name)) {
        return p;
      }
    }
    return null;
  }

  /**
   * 指定されたコード値を持つメール送信ステータスを返します。
   *
   * @param value コード値
   * @return メール送信ステータス
   */
  public static SmsSendStatus fromValue(String value) {
    for (SmsSendStatus p : SmsSendStatus.values()) {
      if (p.getValue().equals(value)) {
        return p;
      }
    }
    return null;
  }

  /**
   * 指定されたコード値を持つメール送信ステータスを返します。
   *
   * @param value コード値
   * @return メール送信ステータス
   */
  public static SmsSendStatus fromValue(Long value) {
    return fromValue(Long.toString(value));
  }

  /**
   * 指定されたコード値が有効かどうかを返します。
   *
   * @param value コード値
   * @return コード値が有効であればtrue
   */
  public static boolean isValid(String value) {
    if (StringUtil.hasValue(value)) {
      for (SmsSendStatus p : SmsSendStatus.values()) {
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
   * @param value コード値
   * @return コード値が有効であればtrue
   */
  public static boolean isValid(Long value) {
    return isValid(Long.toString(value));
  }
}
