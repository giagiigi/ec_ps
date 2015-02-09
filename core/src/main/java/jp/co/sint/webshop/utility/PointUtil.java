package jp.co.sint.webshop.utility;

import java.math.BigDecimal;
import java.math.RoundingMode;

import jp.co.sint.webshop.data.attribute.Precision;
import jp.co.sint.webshop.data.dto.Customer;

public final class PointUtil {

  private static final BigDecimal HUNDRED = BigDecimal.valueOf(100L);

  private PointUtil() {

  }

  /**
   * 価格とポイント付与率から、獲得ポイント数を算出します。 計算結果が小数点以下を含む場合は、端数を切り捨てます。
   * priceがゼロ以下のときはゼロを戻します。 rateがゼロ以下のときはゼロを戻します。
   * 
   * @param price
   *          価格
   * @param rate
   *          ポイント付与率
   * @return 獲得ポイント数
   */
  public static long calculatePoint(Long price, Long rate) {
    long result = 0L;
    if (price != null && rate != null) {
      result = calculatePoint(price.longValue(), rate.longValue());
    }
    return result;
  }

  /**
   * 価格とポイント付与率から、獲得ポイント数を算出します。 計算結果が小数点以下を含む場合は、端数を切り捨てます。
   * priceがゼロ以下のときはゼロを戻します。 rateがゼロ以下のときはゼロを戻します。
   * 
   * @param price
   *          価格
   * @param rate
   *          ポイント付与率
   * @return 獲得ポイント数
   */
  public static long calculatePoint(long price, long rate) {
    long result = 0L;
    if (price > 0 && rate > 0) {
      BigDecimal bdPrice = BigDecimal.valueOf(price);
      BigDecimal bdRate = BigDecimal.valueOf(rate);
      BigDecimal bdPoint = bdPrice.multiply(bdRate).divide(HUNDRED, RoundingMode.FLOOR);
      result = bdPoint.longValue();
    }
    return result;
  }

  /**
   * 価格とポイント付与率から、獲得ポイント数を算出します。 計算結果が小数点以下を含む場合は、端数を切り捨てます。
   * priceがゼロ以下のときはゼロを戻します。 rateがゼロ以下のときはゼロを戻します。
   * 
   * @param price
   *          価格
   * @param rate
   *          ポイント付与率
   * @return 獲得ポイント数
   */
  public static BigDecimal calculatePoint(BigDecimal price, Long rate) {
    BigDecimal result = BigDecimal.ZERO;
    if (price != null && rate != null) {
      result = calculatePoint(price, rate.longValue());
    }
    return result;
  }

  /**
   * 価格とポイント付与率から、獲得ポイント数を算出します。 計算結果が小数点以下を含む場合は、端数を切り捨てます。
   * priceがゼロ以下のときはゼロを戻します。 rateがゼロ以下のときはゼロを戻します。
   * 
   * @param price
   *          価格
   * @param rate
   *          ポイント付与率
   * @return 獲得ポイント数
   */
  public static BigDecimal calculatePoint(BigDecimal price, long rate) {
    BigDecimal result = BigDecimal.ZERO;
    if (BigDecimalUtil.isAbove(price, BigDecimal.ZERO) && rate > 0) {
      BigDecimal bdRate = BigDecimal.valueOf(rate);
      BigDecimal bdPoint = price.multiply(bdRate).divide(HUNDRED, 10, RoundingMode.FLOOR);
      // 整数ポイントを返す
      // result = bdPoint.setScale(0, RoundingMode.FLOOR);
      result = bdPoint;
    }
    return result;
  }

  // modified by zhanghaibin start 2010-05-19
  public static BigDecimal getCustomerPointLimit() {
    Precision prec = BeanUtil.getAnnotation(Customer.class, "restPoint", Precision.class);
    return NumUtil.getActualMaximum(prec.precision() - prec.scale(), 0);
  }

  public static String formatPoint(String src) {
    // Integer pointMultiple =
    // DIContainer.getWebshopConfig().getPointMultiple();
    // Long amplificationRate = ServiceLocator.getAmplificationRate();
    String pointFormat = DIContainer.getWebshopConfig().getPointFormat();
    double dsrc = Double.parseDouble(src);
    // dsrc = Math.floor(dsrc / pointMultiple);
    // src=String.valueOf(dsrc);
    // String numof0 = "#,###,##0";
    // String b = "0";
    // if (amplificationRate > 1) {
    // numof0 = numof0 + ".";
    // }
    // String strAmplificationRate = String.valueOf(amplificationRate);
    // for (int i = 0; i < strAmplificationRate.length() - 1; i++) {
    // numof0 += b;
    // }
    java.text.DecimalFormat df = new java.text.DecimalFormat();
    df.applyPattern(pointFormat);
    return df.format(dsrc);
  }

  public static BigDecimal convertToDBValue(BigDecimal issuedPoint) {
    return BigDecimalUtil.multiply(issuedPoint, DIContainer.getWebshopConfig().getPointMultiple());
  }

  public static int getAcquiredPointScale() {
    if (DIContainer.getWebshopConfig().getPointFormat().indexOf(".") >= 0) {
      return DIContainer.getWebshopConfig().getPointFormat().substring(
          DIContainer.getWebshopConfig().getPointFormat().indexOf("."), DIContainer.getWebshopConfig().getPointFormat().length())
          .length() - 1;
    }
    return 0;
  }

  public static String getUsePointWhenUsePointAboveRestPoint(BigDecimal restPoint) {
//    return NumUtil.toString(restPoint.divide(DIContainer.getWebshopConfig().getRmbPointRate(), 0, RoundingMode.FLOOR).multiply(
//        DIContainer.getWebshopConfig().getRmbPointRate()).setScale(getAcquiredPointScale(), RoundingMode.FLOOR));
    return NumUtil.toString(restPoint.setScale(getAcquiredPointScale(), RoundingMode.FLOOR));
  }

  public static String getUsePointWhenUsePointAboveTotalPricePoint(BigDecimal total) {
    if (BigDecimalUtil.isAbove(total, BigDecimal.ZERO)) {
      return NumUtil.toString(total.multiply(DIContainer.getWebshopConfig().getRmbPointRate()).setScale(
          PointUtil.getAcquiredPointScale(), RoundingMode.CEILING));
    } else {
      return NumUtil.toString(BigDecimal.ZERO);
    }
     
  }

  // ポイント差し引き額計算
  public static BigDecimal getTotalPyamentPrice(BigDecimal totalPrice, BigDecimal usedPoint) {
    BigDecimal price = BigDecimalUtil.subtract(totalPrice, usedPoint.divide(DIContainer.getWebshopConfig().getRmbPointRate(),
        2, RoundingMode.CEILING));
    if (BigDecimalUtil.isAbove(BigDecimal.ZERO, price)) {
      price = BigDecimal.ZERO;
    }
    return price;
  }
  
  //ポイント差し引き額計算
  public static BigDecimal getTmallTotalPyamentPrice(BigDecimal totalPrice, BigDecimal usedPoint) {
    BigDecimal price = BigDecimalUtil.subtract(totalPrice, usedPoint.multiply(DIContainer.getWebshopConfig().getTmallPointConvert()));
    if (BigDecimalUtil.isAbove(BigDecimal.ZERO, price)) {
      price = BigDecimal.ZERO;
    }
    return price;
  }
}
