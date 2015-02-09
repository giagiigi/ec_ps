package jp.co.sint.webshop.utility;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * BigDecimalを扱うユーティリティクラスです。このクラスをインスタンス化することはできません。
 * 
 * @author System Integrator Corp.
 */
public final class BigDecimalUtil {

  private BigDecimalUtil() {
  }

  /**
   * 引数で受け取った数値情報をBigDecimalに変換して、加算した値を返します。<BR>
   * 
   * @param num1
   * @param num2
   * @return num1とnum2を加算した値。
   */
  public static BigDecimal add(Number num1, Number num2) {
    return (new BigDecimal(String.valueOf(num1))).add(new BigDecimal(String.valueOf(num2)));
  }

  /**
   * 引数で受け取った数値情報をBigDecimalに変換して、減算した値を返します。<BR>
   * 
   * @param num1
   * @param num2
   * @return num1とnum2を減算した値。
   */
  public static BigDecimal subtract(Number num1, Number num2) {
    return (new BigDecimal(String.valueOf(num1))).subtract(new BigDecimal(String.valueOf(num2)));
  }

  /**
   * 引数で受け取った数値情報をBigDecimalに変換して、乗算した値を返します。<BR>
   * 
   * @param num1
   * @param num2
   * @return num1とnum2を乗算した値。
   */
  public static BigDecimal multiply(Number num1, Number num2) {
    return (new BigDecimal(String.valueOf(num1))).multiply(new BigDecimal(String.valueOf(num2)));
  }

  /**
   * 引数で受け取った数値情報をBigDecimalに変換して、除算した値を返します。<BR>
   * 
   * @param num1
   * @param num2
   * @return num1とnum2を除算した値。
   */
  public static BigDecimal divide(Number num1, Number num2) {
    return (new BigDecimal(String.valueOf(num1))).divide(new BigDecimal(String.valueOf(num2)));
  }

  /**
   * 引数で受け取った数値情報をBigDecimalに変換して、除算した値を返します。<BR>
   * 
   * @param num1
   * @param num2
   * @return num1とnum2を除算した値。
   */
  public static BigDecimal divide(Number num1, Number num2, int scale, RoundingMode roundMode) {
    return (new BigDecimal(String.valueOf(num1))).divide(new BigDecimal(String.valueOf(num2)), scale, roundMode);
  }

  /**
   * 引数で受け取った値が等しいかどうかを判定します。<BR>
   * 
   * @param num1
   * @param num2
   * @return num1 == num2の場合はtrue、num1 != num2の場合はfalseを返します。
   */
  public static boolean equals(BigDecimal num1, BigDecimal num2) {
    return num1.compareTo(num2) == 0;
  }

  /**
   * 第1引数が第2引数より大きいかどうかを判定します。<BR>
   * 
   * @param num1
   * @param num2
   * @return num1 > num2の場合はtrue、num1 <= num2の場合はfalseを返します。
   */
  public static boolean isAbove(BigDecimal num1, BigDecimal num2) {
    return num1.compareTo(num2) == 1;
  }

  /**
   * 第1引数が第2引数より小さいかどうかを判定します。<BR>
   * 
   * @param num1
   * @param num2
   * @return num1 < num2の場合はtrue、num1 >= num2の場合はfalseを返します。
   */
  public static boolean isBelow(BigDecimal num1, BigDecimal num2) {
    return num1.compareTo(num2) == -1;
  }

  /**
   * 第1引数が第2引数以上かどうかを判定します。<BR>
   * 
   * @param num1
   * @param num2
   * @return num1 >= num2の場合はtrue、num1 < num2の場合はfalseを返します。
   */
  public static boolean isAboveOrEquals(BigDecimal num1, BigDecimal num2) {
    return num1.compareTo(num2) == 1 || num1.compareTo(num2) == 0;
  }

  /**
   * 第1引数が第2引数以下かどうかを判定します。<BR>
   * 
   * @param num1
   * @param num2
   * @return num1 <= num2の場合はtrue、num1 > num2の場合はfalseを返します。
   */
  public static boolean isBelowOrEquals(BigDecimal num1, BigDecimal num2) {
    return num1.compareTo(num2) == -1 || num1.compareTo(num2) == 0;
  }

  /**
   * 引数で受け取った値を全て加算した値を返します。<BR>
   * 
   * @param bigDecimals
   * @return bigDecimalsを加算した値。
   */
  public static BigDecimal addAll(BigDecimal... bigDecimals) {
    BigDecimal result = BigDecimal.ZERO;
    for (BigDecimal b : bigDecimals) {
      result = result.add(b);
    }
    return result;
  }

  // V10-CH 170 start
  /**
   * 临时从LONG转换到BigDecimal。<BR>
   * 
   * @param value
   * @return bigDecimal
   */
  public static BigDecimal tempFormatLong(Long value) {
    if (value == null) {
      return null;
    } else {
      return new BigDecimal(value.longValue());
    }
  }

  // 2013/04/07 优惠券对应 ob add start
  /**
   * 临时从LONG转换到BigDecimal。<BR>
   * 
   * @param value
   * @return bigDecimal
   */
  public static BigDecimal tempFormatLong(Long value, BigDecimal defaultValue) {
    if (value == null) {
      return defaultValue;
    } else {
      return new BigDecimal(value.longValue());
    }
  }
  // 2013/04/07 优惠券对应 ob add end
  
  public static boolean isRemainder(BigDecimal src, BigDecimal divisor) {
    return false;
    //return isAbove(src.divide(divisor, 2, RoundingMode.FLOOR).subtract(src.divide(divisor, 0, RoundingMode.FLOOR)), BigDecimal.ZERO);
  }

  public static BigDecimal remainder(BigDecimal src, BigDecimal divisor, int scale) {
    return src.subtract(src.divide(divisor, scale, RoundingMode.FLOOR).multiply(divisor).setScale(scale, RoundingMode.FLOOR));
  }
  // V10-CH 170 end
}
