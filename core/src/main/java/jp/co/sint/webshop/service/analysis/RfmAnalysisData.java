package jp.co.sint.webshop.service.analysis;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.text.Messages;

import org.apache.log4j.Logger;

public class RfmAnalysisData implements Serializable {

  /** serial version UID */
  private static final long serialVersionUID = 1L;

  private List<RfAnalysisSummary> rfAnalysisResult = new ArrayList<RfAnalysisSummary>();

  private List<RmAnalysisSummary> rmAnalysisResult = new ArrayList<RmAnalysisSummary>();

  private List<FmAnalysisSummary> fmAnalysisResult = new ArrayList<FmAnalysisSummary>();

  public RfAnalysisSummary getRf(RecencyRank rRank, FrequencyRank fRank) {
    Logger logger = Logger.getLogger(this.getClass());
    if (rRank == null || fRank == null) {
      logger.error(Messages.log("service.analysis.RfmAnalysisData.0"));
      return null;
    }

    RfAnalysisSummary result = new RfAnalysisSummary();
    result.setRecencyRank(rRank.getValue());
    result.setFrequencyRank(fRank.getValue());
    result.setCustomerCount(0L);
    result.setCustomerCountRatio("0");
    result.setOrderCountAvarage("0");
    result.setOrderCountRatio("0");
    result.setPurchasingAmountAvarage("0");
    result.setPurchasingAmountRatio("0");
    for (RfAnalysisSummary rf : rfAnalysisResult) {
      if (rf.getRecencyRank().equals(rRank.getValue()) && rf.getFrequencyRank().equals(fRank.getValue())) {
        result = rf;
      }
    }

    return result;
  }

  /**
   * RとMのランクを指定してRm分析の結果を取得します。結果が存在しない場合は全て0である結果を作成して返します。
   */
  public RmAnalysisSummary getRm(RecencyRank rRank, MonetaryRank mRank) {
    Logger logger = Logger.getLogger(this.getClass());
    if (rRank == null || mRank == null) {
      logger.error(Messages.log("service.analysis.RfmAnalysisData.0"));
      return null;
    }
    RmAnalysisSummary result = new RmAnalysisSummary();
    result.setRecencyRank(rRank.getValue());
    result.setMonetaryRank(mRank.getValue());
    result.setCustomerCount(0L);
    result.setCustomerCountRatio("0");
    result.setTotalPurchasedAmount(BigDecimal.ZERO);
    result.setTotalPurchasedAmountRatio("0");
    for (RmAnalysisSummary rm : rmAnalysisResult) {
      if (rm.getRecencyRank().equals(rRank.getValue()) && rm.getMonetaryRank().equals(mRank.getValue())) {
        result = rm;
      }
    }
    return result;
  }

  /**
   * FとMのランクを指定してFm分析の結果を取得します。結果が存在しない場合は全て0である結果を作成して返します。
   */
  public FmAnalysisSummary getFm(FrequencyRank fRank, MonetaryRank mRank) {
    Logger logger = Logger.getLogger(this.getClass());
    if (fRank == null || mRank == null) {
      logger.error(Messages.log("service.analysis.RfmAnalysisData.0"));
      return null;
    }
    FmAnalysisSummary result = new FmAnalysisSummary();
    result.setFrequencyRank(fRank.getValue());
    result.setMonetaryRank(mRank.getValue());
    result.setCustomerCount(0L);
    result.setCustomerCountRatio("0");
    result.setOrderCountAvarage("0");
    result.setOrderCountRatio("0");
    result.setPurchasingAmountAvarage("0");
    result.setPurchasingAmountRatio("0");
    result.setTotalPurchasedAmount(BigDecimal.ZERO);
    result.setTotalPurchasedAmountRatio("0");
    for (FmAnalysisSummary fm : fmAnalysisResult) {
      if (fm.getFrequencyRank().equals(fRank.getValue()) && fm.getMonetaryRank().equals(mRank.getValue())) {
        result = fm;
      }
    }
    return result;
  }

  /**
   * rfAnalysisResultを返します。
   * 
   * @return the rfAnalysisResult
   */
  public List<RfAnalysisSummary> getRfAnalysisResult() {
    return rfAnalysisResult;
  }

  /**
   * rmAnalysisResultを返します。
   * 
   * @return the rmAnalysisResult
   */
  public List<RmAnalysisSummary> getRmAnalysisResult() {
    return rmAnalysisResult;
  }

  /**
   * fmAnalysisResultを返します。
   * 
   * @return the fmAnalysisResult
   */
  public List<FmAnalysisSummary> getFmAnalysisResult() {
    return fmAnalysisResult;
  }

  /**
   * rfAnalysisResultを設定します。
   * 
   * @param rfAnalysisResult
   *          設定する rfAnalysisResult
   */
  public void setRfAnalysisResult(List<RfAnalysisSummary> rfAnalysisResult) {
    this.rfAnalysisResult = rfAnalysisResult;
  }

  /**
   * rmAnalysisResultを設定します。
   * 
   * @param rmAnalysisResult
   *          設定する rmAnalysisResult
   */
  public void setRmAnalysisResult(List<RmAnalysisSummary> rmAnalysisResult) {
    this.rmAnalysisResult = rmAnalysisResult;
  }

  /**
   * fmAnalysisResultを設定します。
   * 
   * @param fmAnalysisResult
   *          設定する fmAnalysisResult
   */
  public void setFmAnalysisResult(List<FmAnalysisSummary> fmAnalysisResult) {
    this.fmAnalysisResult = fmAnalysisResult;
  }

}
