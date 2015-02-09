
package jp.co.sint.webshop.data.domain;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.utility.CodeUtil;
import jp.co.sint.webshop.utility.StringUtil;

/**
 * 「キャンペーンタイプ」のコード定義を表す列挙クラスです。
 *
 * @author OB.
 *
 */
public enum CampaignType implements CodeAttribute {

  /** 「比例优惠」を表す値です。 */
  PROPORTION("比例", "0"),

  /** 「固定金额优惠」を表す値です。 */
  FIXED("固定金额", "1");

  private String name;

  private String value;

  private CampaignType(String name, String value) {
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
   * 指定されたコード名を持つキャンペーンタイプを返します。
   *
   * @param name コード名
   * @return キャンペーンタイプ
   */
  public static CampaignType fromName(String name) {
    for (CampaignType p : CampaignType.values()) {
      if (p.getName().equals(name)) {
        return p;
      }
    }
    return null;
  }

  /**
   * 指定されたコード値を持つキャンペーンタイプを返します。
   *
   * @param value コード値
   * @return キャンペーンタイプ
   */
  public static CampaignType fromValue(String value) {
    for (CampaignType p : CampaignType.values()) {
      if (p.getValue().equals(value)) {
        return p;
      }
    }
    return null;
  }

  /**
   * 指定されたコード値を持つキャンペーンタイプを返します。
   *
   * @param value コード値
   * @return キャンペーンタイプ
   */
  public static CampaignType fromValue(Long value) {
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
      for (CampaignType p : CampaignType.values()) {
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
