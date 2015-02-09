package jp.co.sint.webshop.service.analysis;

import java.io.Serializable;

import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Required;

public class RfmAnalysisSearchCondition implements Serializable {

  /** serial version UID */
  private static final long serialVersionUID = 1L;

  /** Fの集計を行う期間，月単位 */
  @Required
  @Metadata(name = "Fの集計期間")
  private int frequencyPeriod;

  /** F-Aの閾値 */
  @Required
  @Metadata(name = "FAの閾値")
  private int frequencyThresholdA;

  /** F-Bの閾値 */
  @Required
  @Metadata(name = "FBの閾値")
  private int frequencyThresholdB;

  /** Mの集計を行う期間，月単位 */
  @Required
  @Metadata(name = "Mの集計期間")
  private int monetaryPeriod;

  /** M-Aの閾値 */
  @Required
  @Metadata(name = "MAの閾値")
  private Long monetaryThresholdA;

  /** M-Bの閾値 */
  @Required
  @Metadata(name = "MBの閾値")
  private Long monetaryThresholdB;

  /** R-Aの閾値，日単位 */
  @Required
  @Metadata(name = "RAの閾値")
  private int recencyThresholdA;

  /** R-Bの閾値，日単位 */
  @Required
  @Metadata(name = "RBの閾値")
  private int recencyThresholdB;

  /**
   * @return the frequencyPeriod
   */
  public int getFrequencyPeriod() {
    return frequencyPeriod;
  }

  /**
   * @return the frequencyThresholdA
   */
  public int getFrequencyThresholdA() {
    return frequencyThresholdA;
  }

  /**
   * @return the frequencyThresholdB
   */
  public int getFrequencyThresholdB() {
    return frequencyThresholdB;
  }

  /**
   * @return the monetaryPeriod
   */
  public int getMonetaryPeriod() {
    return monetaryPeriod;
  }

  /**
   * @return the monetaryThresholdA
   */
  public Long getMonetaryThresholdA() {
    return monetaryThresholdA;
  }

  /**
   * @return the monetaryThresholdB
   */
  public Long getMonetaryThresholdB() {
    return monetaryThresholdB;
  }

  /**
   * @return the recencyThresholdA
   */
  public int getRecencyThresholdA() {
    return recencyThresholdA;
  }

  /**
   * @return the recencyThresholdB
   */
  public int getRecencyThresholdB() {
    return recencyThresholdB;
  }

  /**
   * @param frequencyPeriod
   *          the frequencyPeriod to set
   */
  public void setFrequencyPeriod(int frequencyPeriod) {
    this.frequencyPeriod = frequencyPeriod;
  }

  /**
   * @param frequencyThresholdA
   *          the frequencyThresholdA to set
   */
  public void setFrequencyThresholdA(int frequencyThresholdA) {
    this.frequencyThresholdA = frequencyThresholdA;
  }

  /**
   * @param frequencyThresholdB
   *          the frequencyThresholdB to set
   */
  public void setFrequencyThresholdB(int frequencyThresholdB) {
    this.frequencyThresholdB = frequencyThresholdB;
  }

  /**
   * @param monetaryPeriod
   *          the monetaryPeriod to set
   */
  public void setMonetaryPeriod(int monetaryPeriod) {
    this.monetaryPeriod = monetaryPeriod;
  }

  /**
   * @param monetaryThresholdA
   *          the monetaryThresholdA to set
   */
  public void setMonetaryThresholdA(Long monetaryThresholdA) {
    this.monetaryThresholdA = monetaryThresholdA;
  }

  /**
   * @param monetaryThresholdB
   *          the monetaryThresholdB to set
   */
  public void setMonetaryThresholdB(Long monetaryThresholdB) {
    this.monetaryThresholdB = monetaryThresholdB;
  }

  /**
   * @param recencyThresholdA
   *          the recencyThresholdA to set
   */
  public void setRecencyThresholdA(int recencyThresholdA) {
    this.recencyThresholdA = recencyThresholdA;
  }

  /**
   * @param recencyThresholdB
   *          the recencyThresholdB to set
   */
  public void setRecencyThresholdB(int recencyThresholdB) {
    this.recencyThresholdB = recencyThresholdB;
  }

}
