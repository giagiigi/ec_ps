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
 * 「都道府県名」のコード定義を表す列挙クラスです。
 *
 * @author System Integrator Corp.
 *
 */
public enum PrefectureName implements CodeAttribute {

  /** 「北海道」を表す値です。 */
  SHANGHAI("上海", "上海"),

  /** 「青森県」を表す値です。 */
  BEIJING("北京", "北京"),

  /** 「岩手県」を表す値です。 */
  TIANJIN("天津", "天津"),

  /** 「宮城県」を表す値です。 */
  HEBEI("河北", "河北"),

  /** 「秋田県」を表す値です。 */
  JIANGSU("江苏", "江苏"),

  /** 「山形県」を表す値です。 */
  ZHEJIANG("浙江", "浙江"),

  /** 「福島県」を表す値です。 */
  CHONGQING("重庆", "重庆"),

  /** 「茨城県」を表す値です。 */
  NEIMENGGU("内蒙古", "内蒙古"),

  /** 「栃木県」を表す値です。 */
  LIAONING("辽宁", "辽宁"),

  /** 「群馬県」を表す値です。 */
  JILIN("吉林", "吉林"),

  /** 「埼玉県」を表す値です。 */
  HEILONGJIANG("黑龙江", "黑龙江"),

  /** 「千葉県」を表す値です。 */
  SICHUAN("四川", "四川"),

  /** 「東京都」を表す値です。 */
  ANHUI("安徽", "安徽"),

  /** 「神奈川県」を表す値です。 */
  FUJIAN("福建", "福建"),

  /** 「新潟県」を表す値です。 */
  JIANGXI("江西", "江西"),

  /** 「富山県」を表す値です。 */
  SHANDONG("山东", "山东"),

  /** 「石川県」を表す値です。 */
  HENAN("河南", "河南"),

  /** 「福井県」を表す値です。 */
  HUBEI("湖北", "湖北"),

  /** 「山梨県」を表す値です。 */
  HUNAN("湖南", "湖南"),

  /** 「長野県」を表す値です。 */
  GUANGDONG("广东", "广东"),

  /** 「岐阜県」を表す値です。 */
  GUANGXI("广西", "广西"),

  /** 「静岡県」を表す値です。 */
  HAINAN("海南", "海南"),

  /** 「愛知県」を表す値です。 */
  GUIZHOU("贵州", "贵州"),

  /** 「三重県」を表す値です。 */
  YUNNAN("云南", "云南"),

  /** 「滋賀県」を表す値です。 */
  XIZANG("西藏", "西藏"),

  /** 「京都府」を表す値です。 */
  SHANXI("陕西", "陕西"),

  /** 「大阪府」を表す値です。 */
  GANSU("甘肃", "甘肃"),

  /** 「兵庫県」を表す値です。 */
  QINGHAI("青海", "青海"),

  /** 「奈良県」を表す値です。 */
  XINJIANG("新疆", "新疆"),

  /** 「和歌山県」を表す値です。 */
  NINGXIA("宁夏", "宁夏"),

  /** 「鳥取県」を表す値です。 */
  SHANXI1("山西", "山西");

  private String name;

  private String value;

  private PrefectureName(String name, String value) {
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
   * 指定されたコード名を持つ都道府県名を返します。
   *
   * @param name コード名
   * @return 都道府県名
   */
  public static PrefectureName fromName(String name) {
    for (PrefectureName p : PrefectureName.values()) {
      if (p.getName().equals(name)) {
        return p;
      }
    }
    return null;
  }

  /**
   * 指定されたコード値を持つ都道府県名を返します。
   *
   * @param value コード値
   * @return 都道府県名
   */
  public static PrefectureName fromValue(String value) {
    for (PrefectureName p : PrefectureName.values()) {
      if (p.getValue().equals(value)) {
        return p;
      }
    }
    return null;
  }

  /**
   * 指定されたコード値を持つ都道府県名を返します。
   *
   * @param value コード値
   * @return 都道府県名
   */
  public static PrefectureName fromValue(Long value) {
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
      for (PrefectureName p : PrefectureName.values()) {
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
