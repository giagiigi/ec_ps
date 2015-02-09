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
 * 「都道府県コード」のコード定義を表す列挙クラスです。
 *
 * @author System Integrator Corp.
 *
 */
public enum PrefectureCode implements CodeAttribute {

  /** 「北海道」を表す値です。 */
  SHANGHAI("上海", "09"),

  /** 「青森県」を表す値です。 */
  BEIJING("北京", "01"),

  /** 「岩手県」を表す値です。 */
  TIANJIN("天津", "02"),

  /** 「宮城県」を表す値です。 */
  HEBEI("河北", "03"),

  /** 「秋田県」を表す値です。 */
  JIANGSU("江苏", "10"),

  /** 「山形県」を表す値です。 */
  ZHEJIANG("浙江", "11"),

  /** 「福島県」を表す値です。 */
  CHONGQING("重庆", "22"),

  /** 「茨城県」を表す値です。 */
  NEIMENGGU("内蒙古", "05"),

  /** 「栃木県」を表す値です。 */
  LIAONING("辽宁", "06"),

  /** 「群馬県」を表す値です。 */
  JILIN("吉林", "07"),

  /** 「埼玉県」を表す値です。 */
  HEILONGJIANG("黑龙江", "08"),

  /** 「千葉県」を表す値です。 */
  SICHUAN("四川", "23"),

  /** 「東京都」を表す値です。 */
  ANHUI("安徽", "12"),

  /** 「神奈川県」を表す値です。 */
  FUJIAN("福建", "13"),

  /** 「新潟県」を表す値です。 */
  JIANGXI("江西", "14"),

  /** 「富山県」を表す値です。 */
  SHANDONG("山东", "15"),

  /** 「石川県」を表す値です。 */
  HENAN("河南", "16"),

  /** 「福井県」を表す値です。 */
  HUBEI("湖北", "17"),

  /** 「山梨県」を表す値です。 */
  HUNAN("湖南", "18"),

  /** 「長野県」を表す値です。 */
  GUANGDONG("广东", "19"),

  /** 「岐阜県」を表す値です。 */
  GUANGXI("广西", "20"),

  /** 「静岡県」を表す値です。 */
  HAINAN("海南", "21"),

  /** 「愛知県」を表す値です。 */
  GUIZHOU("贵州", "24"),

  /** 「三重県」を表す値です。 */
  YUNNAN("云南", "25"),

  /** 「滋賀県」を表す値です。 */
  XIZANG("西藏", "26"),

  /** 「京都府」を表す値です。 */
  SHANXI("陕西", "27"),

  /** 「大阪府」を表す値です。 */
  GANSU("甘肃", "28"),

  /** 「兵庫県」を表す値です。 */
  QINGHAI("青海", "29"),

  /** 「奈良県」を表す値です。 */
  XINJIANG("新疆", "31"),

  /** 「和歌山県」を表す値です。 */
  NINGXIA("宁夏", "30"),

  /** 「鳥取県」を表す値です。 */
  SHANXI1("山西", "04"),
  
  /** 「崇明県」を表す値です。 */
  SHANGHAICHONGMING("上海崇明", "32");

  private String name;

  private String value;

  private PrefectureCode(String name, String value) {
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
   * 指定されたコード名を持つ都道府県コードを返します。
   *
   * @param name コード名
   * @return 都道府県コード
   */
  public static PrefectureCode fromName(String name) {
    for (PrefectureCode p : PrefectureCode.values()) {
      if (p.getName().equals(name)) {
        return p;
      }
    }
    return null;
  }

  /**
   * 指定されたコード値を持つ都道府県コードを返します。
   *
   * @param value コード値
   * @return 都道府県コード
   */
  public static PrefectureCode fromValue(String value) {
    for (PrefectureCode p : PrefectureCode.values()) {
      if (p.getValue().equals(value)) {
        return p;
      }
    }
    return null;
  }

  /**
   * 指定されたコード値を持つ都道府県コードを返します。
   *
   * @param value コード値
   * @return 都道府県コード
   */
  public static PrefectureCode fromValue(Long value) {
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
      for (PrefectureCode p : PrefectureCode.values()) {
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
