package jp.co.sint.webshop.service.analysis;

public class RfmAnalysisExportSearchCondition extends RfmAnalysisSearchCondition {

  /** serial version UID */
  private static final long serialVersionUID = 1L;

  private RecencyRank recencyRank;

  private FrequencyRank frequencyRank;

  private MonetaryRank monetaryRank;

  /**
   * recencyRankを返します。
   * 
   * @return the recencyRank
   */
  public RecencyRank getRecencyRank() {
    return recencyRank;
  }

  /**
   * frequencyRankを返します。
   * 
   * @return the frequencyRank
   */
  public FrequencyRank getFrequencyRank() {
    return frequencyRank;
  }

  /**
   * monetaryRankを返します。
   * 
   * @return the monetaryRank
   */
  public MonetaryRank getMonetaryRank() {
    return monetaryRank;
  }

  /**
   * recencyRankを設定します。
   * 
   * @param recencyRank
   *          設定する recencyRank
   */
  public void setRecencyRank(RecencyRank recencyRank) {
    this.recencyRank = recencyRank;
  }

  /**
   * frequencyRankを設定します。
   * 
   * @param frequencyRank
   *          設定する frequencyRank
   */
  public void setFrequencyRank(FrequencyRank frequencyRank) {
    this.frequencyRank = frequencyRank;
  }

  /**
   * monetaryRankを設定します。
   * 
   * @param monetaryRank
   *          設定する monetaryRank
   */
  public void setMonetaryRank(MonetaryRank monetaryRank) {
    this.monetaryRank = monetaryRank;
  }

}
