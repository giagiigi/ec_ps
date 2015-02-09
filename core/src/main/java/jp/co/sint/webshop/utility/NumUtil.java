package jp.co.sint.webshop.utility;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import org.apache.log4j.Logger;

/**
 * 数値を扱うユーティリティクラスです。このクラスをインスタンス化することはできません。
 * 
 * @author System Integrator Corp.
 */
public final class NumUtil {

  private NumUtil() {
  }

  /**
   * String型の数値情報をLong型に変換します。<BR>
   * 
   * @param value
   *          対象となる文字列
   * @return valueで渡された数値文字列のLong値。valueが数値文字列でないとき(nullを含む)、0Lを返します。
   */
  public static Long toLong(String value) {
    return toLong(value, 0L);
  }

  // add by zhanghaibin start 2010-05-18
  public static BigDecimal parse(String value) {
    return parse(value, BigDecimal.ZERO);
  }

  public static BigDecimal parse(String value, BigDecimal defaultValue) {
    BigDecimal result = null;
    try {
      result = new BigDecimal(value);
    } catch (Exception e) {
      result = defaultValue;
    }
    return result;
  }

  public static String parseStringWithoutZero(BigDecimal defaultValue) {
    String result = "";
    try {
      result = defaultValue.toString();
      Double temp = Double.parseDouble(result);
      result = temp.toString();
    } catch (Exception e) {
      result = "";
    }
    return result;
  }
  
  public static BigDecimal parseBigDecimalWithoutZero(BigDecimal defaultValue) {
    BigDecimal bigDecimal = BigDecimal.ZERO;
    String tmpStr = "";
    try {
      tmpStr = defaultValue.toString();
      Double tempDul = Double.parseDouble(tmpStr);
      bigDecimal = new BigDecimal(tempDul.toString());
    } catch (Exception e) {
      bigDecimal = BigDecimal.ZERO;
    }
    return bigDecimal;
  }
  
  // add by zhanghaibin end 2010-05-18
  /**
   * String型の数値情報をLong型に変換します。<BR>
   * 
   * @param value
   *          対象となる文字列
   * @param defaultValue
   *          ダミーのインスタンス
   * @return valueで渡された数値文字列のLong値を返します。valueが数値文字列でないとき(nullを含む)、defaultValueで指定した値を返します。
   */
  public static Long toLong(String value, Long defaultValue) {
    Long result = null;
    try {
      result = Long.parseLong(value);
    } catch (Exception e) {
      result = defaultValue;
    }
    return result;
  }

  /**
   * Long型のインスタンスをプリミティブ型のlong値に変換します。
   * 
   * @param value
   *          対象となるLong型インスタンス
   * @return long型に変換された値を返します。valueが数値でないとき(nullを含む)、0Lを返します。
   */
  public static long toPrimitiveLong(Long value) {
    return toPrimitiveLong(value, 0L);
  }

  /**
   * Long型のインスタンスをプリミティブ型のlong値に変換します。
   * 
   * @param value
   *          対象となるLong型インスタンス
   * @param defaultValue
   *          ダミーのlong値
   * @return valueで渡されたLong型インスタンスのlong値を返します。valueが数値でないとき(nullを含む)、defaultValueで指定した値を返します。
   */
  public static long toPrimitiveLong(Long value, long defaultValue) {
    long num = 0L;
    if (isNull(value)) {
      num = defaultValue;
    } else {
      num = value.longValue();
    }
    return num;
  }

  /**
   * 数値がnullかどうかを判定します。
   * 
   * @param num
   *          対象となる数値
   * @return 数値がnullだとtrueを、nullでなければfalseを返します。
   */
  public static boolean isNull(Number num) {
    return num == null;
  }

  /**
   * 文字列が数値かどうかを判定します。
   * 
   * @param value
   *          対象となる文字列
   * @return 文字列が数値だとtrueを、数値でなければfalseを返します。
   */
  public static boolean isNum(String value) {
    boolean result = false;
    try {
      Long.parseLong(value);
      result = true;
    } catch (Exception e) {
      result = false;
    }
    return result;
  }

  /**
   * Long型の値をString型に変換します。<BR>
   * 引数がnullの場合は,""(空文字列)を返します。<BR>
   * 
   * @param num
   *          対象となる数値
   * @return 数値を表す文字列を返します。
   */
  public static String toString(Long num) {
    if (isNull(num)) {
      return "";
    }
    return num.toString();
  }
  
  /**
   * Long型の値をString型に変換します。<BR>
   * 引数がnullの場合は,""(空文字列)を返します。<BR>
   * 
   * @param num
   *          対象となる数値
   * @return 数値を表す文字列を返します。
   */
  public static String toString(Long num, String defaultNum) {
    if (isNull(num)) {
      if (StringUtil.hasValue(defaultNum)) {
        return defaultNum;
      }
      return "";
    }
    return num.toString();
  }


  public static String toString(Number num) {
    if (isNull(num)) {
      return "";
    }
    if (num instanceof BigDecimal) {
      return ((BigDecimal) num).toPlainString();
    }
    return num.toString();
  }

  /**
   * String型で表された数値の大小比較を行います。
   * 
   * @param from
   *          開始数値
   * @param to
   *          終了数値
   * @return from <= toならtrueを、from > toならfalseを返します。
   */
  public static boolean isCorrectRange(String from, String to) {
    if (!isNum(from) || !isNum(to)) {
      return false;
    }

    return toLong(from) <= toLong(to);
  }

  /**
   * String型の数字情報を合計します。<br>
   * 引数として受取った文字列が数値以外の場合、0Lとみなして加算を行います。
   * 
   * @param quantitys
   *          加算対象となる数値文字列
   * @return 数字情報の合計を返します。
   */
  public static String summary(String... quantitys) {
    Long summary = 0L;
    for (String quantity : quantitys) {
      summary += toLong(quantity);
    }
    return toString(summary);
  }

  /**
   * Numberインタフェースをもつオブジェクトのlong値を取得します。
   * 
   * @param obj
   *          対象となるオブジェクト。
   * @return 対象となるオブジェクトのlong値。objがNumberでないとき、またはnullのときは0Lを返す。
   */
  public static long parseLong(Object obj) {
    if (obj != null && obj instanceof Number) {
      Number numObj = (Number) obj;
      return numObj.longValue();
    }
    return 0L;
  }

  /**
   * 与えられた数値型の値リストを先頭から走査し、nullでない最初の要素を返します。
   * 
   * @param values
   *          数値型の値リスト
   * @return valuesに与えられた各要素のうち、最初のnullでない要素。valuesが要素なし、
   *         またはvaluesそのものがnullのときはnullを返します。
   */
  public static <T extends Number>T coalesce(T... values) {
    if (values != null && values.length > 0) {
      for (int i = 0; i < values.length; i++) {
        if (values[i] != null) {
          return values[i];
        }
      }
    }
    return null;
  }

  /**
   * 与えられた数値をカンマ区切りの文字列に変更します。nullが与えられた場合はnullを返します。
   */
  public static String formatNumber(Long value) {
    if (isNull(value)) {
      return null;
    }
    NumberFormat format = NumberFormat.getInstance(Locale.CHINA);
    return format.format(value);
  }

  /**
   * 与えられた数値文字列をカンマ区切りの文字列に変更します。nullが与えられた場合はnullを返します。
   */
  public static String formatNumber(String value) {
    if (isDecimal(value)) {
      return formatNumber(parse(value));
    }
    return null;
  }

  public static String round(String value, int scale) {
    DecimalFormat nbf = (DecimalFormat)NumberFormat.getInstance();
    nbf.setMinimumFractionDigits(scale);
    String c = nbf.format(parse(value));
    return c;
  }

  // modified by zhanghaibin start 2010-05-19
  /**
   * 与えられた数値をカンマ区切りの文字列に変更します。nullが与えられた場合はnullを返します。
   */
  public static String formatNumber(Number value) {
    if (isNull(value)) {
      return null;
    }
    // NumberFormat format = NumberFormat.getInstance(Locale.JAPAN);
    NumberFormat format = NumberFormat.getInstance(DIContainer.getLocaleContext().getSystemLocale());
    // DecimalFormat format = (DecimalFormat)NumberFormat.getCurrencyInstance();
    // NumberFormat format = NumberFormat.getCurrencyInstance();
    // format.applyPattern("#,###.00");
    return format.format(value);
  }

  // modified by zhanghaibin end 2010-05-19

  /**
   * 与えられた数値を金額表示に変更します。nullが与えられた場合はnullを返します。
   * 
   * @param value
   *          金額
   * @return valueに与えられた値を金額表示にした文字列
   */
  public static String formatCurrency(Long value) {
    if (isNull(value)) {
      return null;
    }
    StringBuilder builder = new StringBuilder();

    builder.append(formatNumber(value));
    // 20120223 shen delete start
    // builder.append(Messages.getString("numUtil.formatCurrency.0"));
    // 20120223 shen delete end
    return builder.toString();
  }

  /**
   * 与えられた数値文字列をを金額表示に変更します。数値文字列でない場合はnullを返します。
   * 
   * @param value
   *          金額
   * @return valueに与えられた値を金額表示にした文字列
   */
  public static String formatCurrency(String value) {
    if (isDecimal(value)) {
      return formatCurrency(parse(value));
    }
    return null;
  }

  // 10.1.1 10019 追加 ここから
  /**
   * 与えられた数値が負の数かどうかを判定します。
   * 
   * @param num
   *          判定対象となる数値
   * @return 判定結果(負の場合:true, 0以上の場合:false, nullの場合:false)
   */
  public static boolean isNegative(Long num) {
    if (isNull(num) || num >= 0L) {
      return false;
    }
    return true;
  }

  /**
   * 与えられた数値が正の数かどうかを判定します。
   * 
   * @param num
   *          判定対象となる数値
   * @return 判定結果(正の場合:true, 0以下の場合:false, nullの場合:false)
   */
  public static boolean isPositive(Long num) {
    if (isNull(num) || num <= 0L) {
      return false;
    }
    return true;
  }

  // 10.1.1 10019 追加 ここまで

  //add by V10-CH start
  /**
   * 与えられた数値を金額表示に変更します。nullが与えられた場合はnullを返します。
   * 
   * @param value
   *          金額
   * @return valueに与えられた値を金額表示にした文字列
   */
  public static String formatCurrency(Number value) {
    if (isNull(value)) {
      return null;
    }
    StringBuilder builder = new StringBuilder();
    NumberFormat format = NumberFormat.getCurrencyInstance();
    builder.append(format.format(value));
    // 20120223 shen delete start
    // builder.append(Messages.getString("numUtil.formatCurrency.0"));
    // 20120223 shen delete end
    return builder.toString();
  }

  /**
   * 精度とスケールを指定して、10進数の最大値(各桁を9で埋めた値)を生成します。 たとえば、getActualMaximum(10,
   * 2)を実行すると99999999.99が返されます。
   * 
   * @param precision
   *          BigDecimalの精度部分を指定します。
   * @param scale
   *          BigDecimalのスケール部分を指定します。
   * @return 10進数の最大値(各桁を9で埋めた値)
   */
  public static BigDecimal getActualMaximum(int precision, int scale) {
    if (precision < 1 || scale < 0) {
      throw new IllegalArgumentException();
    }
    BigDecimal maximum = BigDecimal.TEN;
    maximum = maximum.pow(precision).subtract(BigDecimal.ONE);
    maximum = maximum.divide(BigDecimal.TEN.pow(scale), scale, RoundingMode.FLOOR);
    return maximum;
  }
  
  public static boolean isDecimal(String value) {
    boolean result = false;
    try {
      BigDecimal b = new BigDecimal(value);
      Logger.getLogger(NumUtil.class).trace("value:" + toString(b));
      result = true;
    } catch (RuntimeException e) {
      result = false;
    }
    return result;
  }
  public static int getScale(int num) {
    String numStr = String.valueOf(num);
    return numStr == null? 0 : numStr.length();
  }
  //add by V10-CH end
  
  // 2012-04-26 yyq add start
  public static boolean isDecimal(String numStr1, String numStr2) {
    boolean flag = false;

    double douNum1 = Double.parseDouble(numStr1);
    double douNum2 = Double.parseDouble(numStr2);
    //库存分配公式 Tmall库存=总库存*（100-Ec分配比例）/ 100
    double douNumRes = douNum1 * (100 - douNum2) / 100;
    String douNumStrRes = douNumRes + "";

    String[] strNum = douNumStrRes.split("\\.");
    for (int j = 0; j < strNum.length; j++) {
      try {
        if (Long.parseLong(strNum[j + 1]) > 0) {
          return true;
        }
      } catch (Exception e) {
      }
    }
    return flag;
  }
  // 2012-04-26 yyq add end
  
  
  public static String formatDouble(double srcDouble){
    String ret = "";
    try {
      DecimalFormat df=new DecimalFormat("#.00");
      ret = df.format(srcDouble);
    } catch (Exception e) {
      // TODO: handle exception
    }
    return ret;
  }
}
