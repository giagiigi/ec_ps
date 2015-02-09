package jp.co.sint.webshop.web.bean.back.analysis;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.attribute.Currency;
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Precision;
import jp.co.sint.webshop.data.attribute.Range;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U1070710:RFM分析のデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class RfmAnalysisBean extends UIBackBean {

  public static final String COOKIE_NAME = "rfm_analysis";

  /**
   * serial version UID
   */
  private static final long serialVersionUID = 1L;

  /** RAランクの閾値 */
  @Required
  @Digit
  @Range(min = 1, max = 999)
  @Length(3)
  @Metadata(name = "RAランクの経過日数", order = 1)
  private String recencyThresholdA;

  /** RBランクの閾値 */
  @Required
  @Digit
  @Range(min = 1, max = 999)
  @Length(3)
  @Metadata(name = "RBランクの経過日数", order = 2)
  private String recencyThresholdB;

  /** Fの集計対象期間 */
  @Required
  @Digit
  @Range(min = 1, max = 999)
  @Length(3)
  @Metadata(name = "Fの集計期間", order = 3)
  private String frequencyPeriod;

  /** FAランクの閾値 */
  @Required
  @Digit
  @Range(min = 0, max = 999)
  @Length(3)
  @Metadata(name = "FAランクの購入回数", order = 4)
  private String frequencyThresholdA;

  /** FBランクの閾値 */
  @Required
  @Digit
  @Range(min = 0, max = 999)
  @Length(3)
  @Metadata(name = "FBランクの購入回数", order = 5)
  private String frequencyThresholdB;

  /** Mの集計対象期間 */
  @Required
  @Digit
  @Range(min = 1, max = 999)
  @Length(3)
  @Metadata(name = "Mの集計期間", order = 6)
  private String monetaryPeriod;

  /** MAランクの閾値 */
  @Required
  @Length(11)
  @Currency
  @Precision(precision = 10, scale = 2)
  @Range(min = 0, max = 99999999)
  @Metadata(name = "MAランクの購入金額", order = 7)
  private String monetaryThresholdA;

  /** MBランクの閾値 */
  @Required
  @Length(11)
  @Currency
  @Precision(precision = 10, scale = 2)
  @Range(min = 0, max = 99999999)
  @Metadata(name = "MBランクの購入金額", order = 8)
  private String monetaryThresholdB;

  /** RF分析結果 */
  private List<List<RfAnalysisReportBean>> rfAnalysisResult = new ArrayList<List<RfAnalysisReportBean>>();

  /** RM分析結果 */
  private List<List<RmAnalysisReportBean>> rmAnalysisResult = new ArrayList<List<RmAnalysisReportBean>>();

  /** FM分析結果 */
  private List<List<FmAnalysisReportBean>> fmAnalysisResult = new ArrayList<List<FmAnalysisReportBean>>();

  /** 分析結果表示有無 true:表示する false:表示しない */
  private boolean displaySearchResultFlg;

  /** CSV出力権限有無 true:権限あり false:権限なし */
  private boolean exportAuthority;

  /**
   * U1070710:RFM分析のサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class RfAnalysisReportBean implements Serializable {

    /** serial version UID */
    private static final long serialVersionUID = 1L;

    /** 顧客数 */
    private String customerCount;

    /** 顧客数構成率 */
    private String customerCountRatio;

    /** 平均購入商品数 */
    private String purchasingAmountAvarage;

    /** 購入商品数構成率 */
    private String purchasingAmountRatio;

    /** 平均購入回数 */
    private String orderCountAvarage;

    /** 購入回数構成率 */
    private String orderCountRatio;

    /** "R"のランク */
    private String recencyRank;

    /** "Fのランク" */
    private String frequencyRank;

    /**
     * frequencyRankを取得します。
     * 
     * @return frequencyRank
     */
    public String getFrequencyRank() {
      return frequencyRank;
    }

    /**
     * recencyRankを取得します。
     * 
     * @return recencyRank
     */
    public String getRecencyRank() {
      return recencyRank;
    }

    /**
     * frequencyRankを設定します。
     * 
     * @param frequencyRank
     *          frequencyRank
     */
    public void setFrequencyRank(String frequencyRank) {
      this.frequencyRank = frequencyRank;
    }

    /**
     * recencyRankを設定します。
     * 
     * @param recencyRank
     *          recencyRank
     */
    public void setRecencyRank(String recencyRank) {
      this.recencyRank = recencyRank;
    }

    /**
     * customerCountを取得します。
     * 
     * @return customerCount
     */
    public String getCustomerCount() {
      return customerCount;
    }

    /**
     * customerCountRatioを取得します。
     * 
     * @return customerCountRatio
     */
    public String getCustomerCountRatio() {
      return customerCountRatio;
    }

    /**
     * orderCountAvarageを取得します。
     * 
     * @return orderCountAvarage
     */
    public String getOrderCountAvarage() {
      return orderCountAvarage;
    }

    /**
     * orderCountRatioを取得します。
     * 
     * @return orderCountRatio
     */
    public String getOrderCountRatio() {
      return orderCountRatio;
    }

    /**
     * purchasingAmountAvarageを取得します。
     * 
     * @return purchasingAmountAvarage
     */
    public String getPurchasingAmountAvarage() {
      return purchasingAmountAvarage;
    }

    /**
     * purchasingAmountRatioを取得します。
     * 
     * @return purchasingAmountRatio
     */
    public String getPurchasingAmountRatio() {
      return purchasingAmountRatio;
    }

    /**
     * customerCountを設定します。
     * 
     * @param customerCount
     *          customerCount
     */
    public void setCustomerCount(String customerCount) {
      this.customerCount = customerCount;
    }

    /**
     * customerCountRatioを設定します。
     * 
     * @param customerCountRatio
     *          customerCountRatio
     */
    public void setCustomerCountRatio(String customerCountRatio) {
      this.customerCountRatio = customerCountRatio;
    }

    /**
     * orderCountAvarageを設定します。
     * 
     * @param orderCountAvarage
     *          orderCountAvarage
     */
    public void setOrderCountAvarage(String orderCountAvarage) {
      this.orderCountAvarage = orderCountAvarage;
    }

    /**
     * orderCountRatioを設定します。
     * 
     * @param orderCountRatio
     *          orderCountRatio
     */
    public void setOrderCountRatio(String orderCountRatio) {
      this.orderCountRatio = orderCountRatio;
    }

    /**
     * purchasingAmountAvarageを設定します。
     * 
     * @param purchasingAmountAvarage
     *          purchasingAmountAvarage
     */
    public void setPurchasingAmountAvarage(String purchasingAmountAvarage) {
      this.purchasingAmountAvarage = purchasingAmountAvarage;
    }

    /**
     * purchasingAmountRatioを設定します。
     * 
     * @param purchasingAmountRatio
     *          purchasingAmountRatio
     */
    public void setPurchasingAmountRatio(String purchasingAmountRatio) {
      this.purchasingAmountRatio = purchasingAmountRatio;
    }

  }

  /**
   * U1070710:RFM分析のサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class RmAnalysisReportBean implements Serializable {

    /** serial version UID */
    private static final long serialVersionUID = 1L;

    /** 顧客数 */
    private String customerCount;

    /** 顧客数構成率 */
    private String customerCountRatio;

    /** 累計購入金額 */
    private String totalPurchasedAmount;

    /** 累計購入金額構成率 */
    private String totalPurchasedAmountRatio;

    /** "R"のランク */
    private String recencyRank;

    /** "M"のランク */
    private String monetaryRank;

    /**
     * monetaryRankを取得します。
     * 
     * @return monetaryRank
     */
    public String getMonetaryRank() {
      return monetaryRank;
    }

    /**
     * recencyRankを取得します。
     * 
     * @return recencyRank
     */
    public String getRecencyRank() {
      return recencyRank;
    }

    /**
     * monetaryRankを設定します。
     * 
     * @param monetaryRank
     *          monetaryRank
     */
    public void setMonetaryRank(String monetaryRank) {
      this.monetaryRank = monetaryRank;
    }

    /**
     * recencyRankを設定します。
     * 
     * @param recencyRank
     *          recencyRank
     */
    public void setRecencyRank(String recencyRank) {
      this.recencyRank = recencyRank;
    }

    /**
     * customerCountを取得します。
     * 
     * @return customerCount
     */
    public String getCustomerCount() {
      return customerCount;
    }

    /**
     * customerCountRatioを取得します。
     * 
     * @return customerCountRatio
     */
    public String getCustomerCountRatio() {
      return customerCountRatio;
    }

    /**
     * totalPurchasedAmountを取得します。
     * 
     * @return totalPurchasedAmount
     */
    public String getTotalPurchasedAmount() {
      return totalPurchasedAmount;
    }

    /**
     * totalPurchasedAmountRatioを取得します。
     * 
     * @return totalPurchasedAmountRatio
     */
    public String getTotalPurchasedAmountRatio() {
      return totalPurchasedAmountRatio;
    }

    /**
     * customerCountを設定します。
     * 
     * @param customerCount
     *          customerCount
     */
    public void setCustomerCount(String customerCount) {
      this.customerCount = customerCount;
    }

    /**
     * customerCountRatioを設定します。
     * 
     * @param customerCountRatio
     *          customerCountRatio
     */
    public void setCustomerCountRatio(String customerCountRatio) {
      this.customerCountRatio = customerCountRatio;
    }

    /**
     * totalPurchasedAmountを設定します。
     * 
     * @param totalPurchasedAmount
     *          totalPurchasedAmount
     */
    public void setTotalPurchasedAmount(String totalPurchasedAmount) {
      this.totalPurchasedAmount = totalPurchasedAmount;
    }

    /**
     * totalPurchasedAmountRatioを設定します。
     * 
     * @param totalPurchasedAmountRatio
     *          totalPurchasedAmountRatio
     */
    public void setTotalPurchasedAmountRatio(String totalPurchasedAmountRatio) {
      this.totalPurchasedAmountRatio = totalPurchasedAmountRatio;
    }

  }

  /**
   * U1070710:RFM分析のサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class FmAnalysisReportBean implements Serializable {

    /** serial version UID */
    private static final long serialVersionUID = 1L;

    /** 顧客数 */
    private String customerCount;

    /** 顧客数構成率 */
    private String customerCountRatio;

    /** 平均購入商品数 */
    private String purchasingAmountAvarage;

    /** 購入商品数構成率 */
    private String purchasingAmountRatio;

    /** 平均購入回数 */
    private String orderCountAvarage;

    /** 購入回数構成率 */
    private String orderCountRatio;

    /** 累計購入金額 */
    private String totalPurchasedAmount;

    /** 累計購入金額構成率 */
    private String totalPurchasedAmountRatio;

    /** "F"のランク */
    private String frequencyRank;

    /** "M"のランク */
    private String monetaryRank;

    /**
     * frequencyRankを取得します。
     * 
     * @return frequencyRank
     */
    public String getFrequencyRank() {
      return frequencyRank;
    }

    /**
     * monetaryRankを取得します。
     * 
     * @return monetaryRank
     */
    public String getMonetaryRank() {
      return monetaryRank;
    }

    /**
     * frequencyRankを設定します。
     * 
     * @param frequencyRank
     *          frequencyRank
     */
    public void setFrequencyRank(String frequencyRank) {
      this.frequencyRank = frequencyRank;
    }

    /**
     * monetaryRankを設定します。
     * 
     * @param monetaryRank
     *          monetaryRank
     */
    public void setMonetaryRank(String monetaryRank) {
      this.monetaryRank = monetaryRank;
    }

    /**
     * customerCountを取得します。
     * 
     * @return customerCount
     */
    public String getCustomerCount() {
      return customerCount;
    }

    /**
     * customerCountRatioを取得します。
     * 
     * @return customerCountRatio
     */
    public String getCustomerCountRatio() {
      return customerCountRatio;
    }

    /**
     * orderCountAvarageを取得します。
     * 
     * @return orderCountAvarage
     */
    public String getOrderCountAvarage() {
      return orderCountAvarage;
    }

    /**
     * orderCountRatioを取得します。
     * 
     * @return orderCountRatio
     */
    public String getOrderCountRatio() {
      return orderCountRatio;
    }

    /**
     * purchasingAmountAvarageを取得します。
     * 
     * @return purchasingAmountAvarage
     */
    public String getPurchasingAmountAvarage() {
      return purchasingAmountAvarage;
    }

    /**
     * purchasingAmountRatioを取得します。
     * 
     * @return purchasingAmountRatio
     */
    public String getPurchasingAmountRatio() {
      return purchasingAmountRatio;
    }

    /**
     * totalPurchasedAmountを取得します。
     * 
     * @return totalPurchasedAmount
     */
    public String getTotalPurchasedAmount() {
      return totalPurchasedAmount;
    }

    /**
     * totalPurchasedAmountRatioを取得します。
     * 
     * @return totalPurchasedAmountRatio
     */
    public String getTotalPurchasedAmountRatio() {
      return totalPurchasedAmountRatio;
    }

    /**
     * customerCountを設定します。
     * 
     * @param customerCount
     *          customerCount
     */
    public void setCustomerCount(String customerCount) {
      this.customerCount = customerCount;
    }

    /**
     * customerCountRatioを設定します。
     * 
     * @param customerCountRatio
     *          customerCountRatio
     */
    public void setCustomerCountRatio(String customerCountRatio) {
      this.customerCountRatio = customerCountRatio;
    }

    /**
     * orderCountAvarageを設定します。
     * 
     * @param orderCountAvarage
     *          orderCountAvarage
     */
    public void setOrderCountAvarage(String orderCountAvarage) {
      this.orderCountAvarage = orderCountAvarage;
    }

    /**
     * orderCountRatioを設定します。
     * 
     * @param orderCountRatio
     *          orderCountRatio
     */
    public void setOrderCountRatio(String orderCountRatio) {
      this.orderCountRatio = orderCountRatio;
    }

    /**
     * purchasingAmountAvarageを設定します。
     * 
     * @param purchasingAmountAvarage
     *          purchasingAmountAvarage
     */
    public void setPurchasingAmountAvarage(String purchasingAmountAvarage) {
      this.purchasingAmountAvarage = purchasingAmountAvarage;
    }

    /**
     * purchasingAmountRatioを設定します。
     * 
     * @param purchasingAmountRatio
     *          purchasingAmountRatio
     */
    public void setPurchasingAmountRatio(String purchasingAmountRatio) {
      this.purchasingAmountRatio = purchasingAmountRatio;
    }

    /**
     * totalPurchasedAmountを設定します。
     * 
     * @param totalPurchasedAmount
     *          totalPurchasedAmount
     */
    public void setTotalPurchasedAmount(String totalPurchasedAmount) {
      this.totalPurchasedAmount = totalPurchasedAmount;
    }

    /**
     * totalPurchasedAmountRatioを設定します。
     * 
     * @param totalPurchasedAmountRatio
     *          totalPurchasedAmountRatio
     */
    public void setTotalPurchasedAmountRatio(String totalPurchasedAmountRatio) {
      this.totalPurchasedAmountRatio = totalPurchasedAmountRatio;
    }

  }

  /**
   * サブJSPを設定します。
   */
  @Override
  public void setSubJspId() {
  }

  /**
   * リクエストパラメータから値を取得します。
   * 
   * @param reqparam
   *          リクエストパラメータ
   */
  public void createAttributes(RequestParameter reqparam) {
    setRecencyThresholdA(reqparam.get("recencyThresholdA"));
    setRecencyThresholdB(reqparam.get("recencyThresholdB"));
    setFrequencyPeriod(reqparam.get("frequencyPeriod"));
    setFrequencyThresholdA(reqparam.get("frequencyThresholdA"));
    setFrequencyThresholdB(reqparam.get("frequencyThresholdB"));
    setMonetaryPeriod(reqparam.get("monetaryPeriod"));
    setMonetaryThresholdA(reqparam.get("monetaryThresholdA"));
    setMonetaryThresholdB(reqparam.get("monetaryThresholdB"));
  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U1070710";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.back.analysis.RfmAnalysisBean.0");
  }

  /**
   * frequencyPeriodを取得します。
   * 
   * @return frequencyPeriod
   */
  public String getFrequencyPeriod() {
    return frequencyPeriod;
  }

  /**
   * frequencyThresholdAを取得します。
   * 
   * @return frequencyThresholdA
   */
  public String getFrequencyThresholdA() {
    return frequencyThresholdA;
  }

  /**
   * frequencyThresholdBを取得します。
   * 
   * @return frequencyThresholdB
   */
  public String getFrequencyThresholdB() {
    return frequencyThresholdB;
  }

  /**
   * monetaryPeriodを取得します。
   * 
   * @return monetaryPeriod
   */
  public String getMonetaryPeriod() {
    return monetaryPeriod;
  }

  /**
   * monetaryThresholdAを取得します。
   * 
   * @return monetaryThresholdA
   */
  public String getMonetaryThresholdA() {
    return monetaryThresholdA;
  }

  /**
   * monetaryThresholdBを取得します。
   * 
   * @return monetaryThresholdB
   */
  public String getMonetaryThresholdB() {
    return monetaryThresholdB;
  }

  /**
   * recencyThresholdAを取得します。
   * 
   * @return recencyThresholdA
   */
  public String getRecencyThresholdA() {
    return recencyThresholdA;
  }

  /**
   * recencyThresholdBを取得します。
   * 
   * @return recencyThresholdB
   */
  public String getRecencyThresholdB() {
    return recencyThresholdB;
  }

  /**
   * frequencyPeriodを設定します。
   * 
   * @param frequencyPeriod
   *          frequencyPeriod
   */
  public void setFrequencyPeriod(String frequencyPeriod) {
    this.frequencyPeriod = frequencyPeriod;
  }

  /**
   * frequencyThresholdAを設定します。
   * 
   * @param frequencyThresholdA
   *          frequencyThresholdA
   */
  public void setFrequencyThresholdA(String frequencyThresholdA) {
    this.frequencyThresholdA = frequencyThresholdA;
  }

  /**
   * frequencyThresholdBを設定します。
   * 
   * @param frequencyThresholdB
   *          frequencyThresholdB
   */
  public void setFrequencyThresholdB(String frequencyThresholdB) {
    this.frequencyThresholdB = frequencyThresholdB;
  }

  /**
   * monetaryPeriodを設定します。
   * 
   * @param monetaryPeriod
   *          monetaryPeriod
   */
  public void setMonetaryPeriod(String monetaryPeriod) {
    this.monetaryPeriod = monetaryPeriod;
  }

  /**
   * monetaryThresholdAを設定します。
   * 
   * @param monetaryThresholdA
   *          monetaryThresholdA
   */
  public void setMonetaryThresholdA(String monetaryThresholdA) {
    this.monetaryThresholdA = monetaryThresholdA;
  }

  /**
   * monetaryThresholdBを設定します。
   * 
   * @param monetaryThresholdB
   *          monetaryThresholdB
   */
  public void setMonetaryThresholdB(String monetaryThresholdB) {
    this.monetaryThresholdB = monetaryThresholdB;
  }

  /**
   * recencyThresholdAを設定します。
   * 
   * @param recencyThresholdA
   *          recencyThresholdA
   */
  public void setRecencyThresholdA(String recencyThresholdA) {
    this.recencyThresholdA = recencyThresholdA;
  }

  /**
   * recencyThresholdBを設定します。
   * 
   * @param recencyThresholdB
   *          recencyThresholdB
   */
  public void setRecencyThresholdB(String recencyThresholdB) {
    this.recencyThresholdB = recencyThresholdB;
  }

  /**
   * fmAnalysisResultを取得します。
   * 
   * @return fmAnalysisResult
   */
  public List<List<FmAnalysisReportBean>> getFmAnalysisResult() {
    return fmAnalysisResult;
  }

  /**
   * rfAnalysisResultを取得します。
   * 
   * @return rfAnalysisResult
   */
  public List<List<RfAnalysisReportBean>> getRfAnalysisResult() {
    return rfAnalysisResult;
  }

  /**
   * rmAnalysisResultを取得します。
   * 
   * @return rmAnalysisResult
   */
  public List<List<RmAnalysisReportBean>> getRmAnalysisResult() {
    return rmAnalysisResult;
  }

  /**
   * fmAnalysisResultを設定します。
   * 
   * @param fmAnalysisResult
   *          fmAnalysisResult
   */
  public void setFmAnalysisResult(List<List<FmAnalysisReportBean>> fmAnalysisResult) {
    this.fmAnalysisResult = fmAnalysisResult;
  }

  /**
   * rfAnalysisResultを設定します。
   * 
   * @param rfAnalysisResult
   *          rfAnalysisResult
   */
  public void setRfAnalysisResult(List<List<RfAnalysisReportBean>> rfAnalysisResult) {
    this.rfAnalysisResult = rfAnalysisResult;
  }

  /**
   * rmAnalysisResultを設定します。
   * 
   * @param rmAnalysisResult
   *          rmAnalysisResult
   */
  public void setRmAnalysisResult(List<List<RmAnalysisReportBean>> rmAnalysisResult) {
    this.rmAnalysisResult = rmAnalysisResult;
  }

  /**
   * displaySearchResultFlgを取得します。
   * 
   * @return displaySearchResultFlg
   */
  public boolean isDisplaySearchResultFlg() {
    return displaySearchResultFlg;
  }

  /**
   * displaySearchResultFlgを設定します。
   * 
   * @param displaySearchResultFlg
   *          displaySearchResultFlg
   */
  public void setDisplaySearchResultFlg(boolean displaySearchResultFlg) {
    this.displaySearchResultFlg = displaySearchResultFlg;
  }

  /**
   * exportAuthorityを取得します。
   * 
   * @return exportAuthority
   */
  public boolean isExportAuthority() {
    return exportAuthority;
  }

  /**
   * exportAuthorityを設定します。
   * 
   * @param exportAuthority
   *          exportAuthority
   */
  public void setExportAuthority(boolean exportAuthority) {
    this.exportAuthority = exportAuthority;
  }

}
