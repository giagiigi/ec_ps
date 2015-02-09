package jp.co.sint.webshop.service.analysis;

import java.io.Serializable;

public class Conversion implements Serializable {

  /** serial version UID */
  private static final long serialVersionUID = 1L;

  /** 集計単位(時間帯，日付，月，曜日) */
  private String label;

  /** コンバージョン率 */
  private String conversionRate;

  /**
   * labelを返します。
   * 
   * @return the label
   */
  public String getLabel() {
    return label;
  }

  /**
   * conversionRateを返します。
   * 
   * @return the conversionRate
   */
  public String getConversionRate() {
    return conversionRate;
  }

  /**
   * labelを設定します。
   * 
   * @param label
   *          設定する label
   */
  public void setLabel(String label) {
    this.label = label;
  }

  /**
   * conversionRateを設定します。
   * 
   * @param conversionRate
   *          設定する conversionRate
   */
  public void setConversionRate(String conversionRate) {
    this.conversionRate = conversionRate;
  }

}
