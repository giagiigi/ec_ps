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
 * 「咨询状态」のコード定義を表す列挙クラスです。
 *
 * @author swj.
 *
 */
public enum InquiryStatus implements CodeAttribute {

  /** 「处理中」を表す値です。 */
  PROCESSING("处理中", "0"),

  /** 「等待客户答复」を表す値です。 */
  WAIT_CUSTOMER_REPLY("等待客户答复", "1"),
  
  /** 「等待物流答复」 */
  WAIT_LOGISTICS_REPLY("等待物流答复","2"),
  
  /** 「等待产品答复」を表す値です。 */
  WAIT_PRODUCT_REPLY("等待产品答复", "3"),
  
  /** 「处理中断」 */
  DEAL_WITH_INTERRUPT("处理中断","4"),
  
  /** 「处理完毕/成功」 */
  DEAL_WITH_FINISH("处理完毕/成功 ","5"),
  
  /** 「首次解决 」を表す値です。 */
  FIRST_SOLVE("首次解决 ", "6"),
  
  /** 「升级」を表す値です。 */
  ESCALATION("升级", "7"),
  
  /** 「升级跟进」を表す値です。 */
  ESCALATION_FOLLOW_UP("升级跟进", "8"),
  
  /** 「反馈完毕」を表す値です。 */
  FEEDBACK_COMPLETE("反馈完毕", "9");

  private String name;

  private String value;

  private InquiryStatus(String name, String value) {
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
   * 指定されたコード名を持つ咨询状态を返します。
   *
   * @param name コード名
   * @return 咨询状态
   */
  public static InquiryStatus fromName(String name) {
    for (InquiryStatus p : InquiryStatus.values()) {
      if (p.getName().equals(name)) {
        return p;
      }
    }
    return null;
  }

  /**
   * 指定されたコード値を持つ咨询状态を返します。
   *
   * @param value コード値
   * @return 咨询状态
   */
  public static InquiryStatus fromValue(String value) {
    for (InquiryStatus p : InquiryStatus.values()) {
      if (p.getValue().equals(value)) {
        return p;
      }
    }
    return null;
  }

  /**
   * 指定されたコード値を持つ咨询状态を返します。
   *
   * @param value コード値
   * @return 咨询状态
   */
  public static InquiryStatus fromValue(Long value) {
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
      for (InquiryStatus p : InquiryStatus.values()) {
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
