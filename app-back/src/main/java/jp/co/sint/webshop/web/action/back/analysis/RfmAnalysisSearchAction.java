package jp.co.sint.webshop.web.action.back.analysis;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.service.AnalysisService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.analysis.FmAnalysisSummary;
import jp.co.sint.webshop.service.analysis.FrequencyRank;
import jp.co.sint.webshop.service.analysis.MonetaryRank;
import jp.co.sint.webshop.service.analysis.RecencyRank;
import jp.co.sint.webshop.service.analysis.RfAnalysisSummary;
import jp.co.sint.webshop.service.analysis.RfmAnalysisData;
import jp.co.sint.webshop.service.analysis.RfmAnalysisSearchCondition;
import jp.co.sint.webshop.service.analysis.RmAnalysisSummary;
import jp.co.sint.webshop.utility.BigDecimalUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.analysis.RfmAnalysisBean;
import jp.co.sint.webshop.web.bean.back.analysis.RfmAnalysisBean.FmAnalysisReportBean;
import jp.co.sint.webshop.web.bean.back.analysis.RfmAnalysisBean.RfAnalysisReportBean;
import jp.co.sint.webshop.web.bean.back.analysis.RfmAnalysisBean.RmAnalysisReportBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.analysis.AnalysisErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1070710:RFM分析のアクションクラスです。

 * 
 * @author System Integrator Corp.
 */
public class RfmAnalysisSearchAction extends WebBackAction<RfmAnalysisBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。

   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    BackLoginInfo login = getLoginInfo();

    // 分析参照_ショップの権限を持つユーザは
    // 一店舗モードのときのみアクセス可能
    // 分析参照_サイトの権限を持つユーザは常にアクセス可能

    return (Permission.ANALYSIS_READ_SHOP.isGranted(login) && getConfig().isOne())
        || Permission.ANALYSIS_READ_SITE.isGranted(login);
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。

   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    RfmAnalysisBean searchBean = getBean();
    boolean result = false;

    if (validateBean(searchBean)) {
      result = true;

      if (NumUtil.toLong(searchBean.getRecencyThresholdA()) >= NumUtil.toLong(searchBean.getRecencyThresholdB())) {
        result &= false;
        addErrorMessage(WebMessage.get(AnalysisErrorMessage.SEARCH_RANGE_ERROR,
            Messages.getString("web.action.back.analysis.RfmAnalysisSearchAction.0"),
            Messages.getString("web.action.back.analysis.RfmAnalysisSearchAction.1")));
      }

      if (NumUtil.toLong(searchBean.getFrequencyThresholdA()) <= NumUtil.toLong(searchBean.getFrequencyThresholdB())) {
        result &= false;
        addErrorMessage(WebMessage.get(AnalysisErrorMessage.SEARCH_RANGE_ERROR,
            Messages.getString("web.action.back.analysis.RfmAnalysisSearchAction.2"),
            Messages.getString("web.action.back.analysis.RfmAnalysisSearchAction.3")));
      }

      if (!BigDecimalUtil.isAbove(NumUtil.parse(searchBean.getMonetaryThresholdA()),
          NumUtil.parse(searchBean.getMonetaryThresholdB()))) {
        result &= false;
        addErrorMessage(WebMessage.get(AnalysisErrorMessage.SEARCH_RANGE_ERROR,
            Messages.getString("web.action.back.analysis.RfmAnalysisSearchAction.4"),
            Messages.getString("web.action.back.analysis.RfmAnalysisSearchAction.5")));
      }

    }
    return result;
  }

  /**
   * アクションを実行します。

   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    RfmAnalysisBean bean = getBean();

    boolean hasExportAuthority;
    BackLoginInfo login = getLoginInfo();
    hasExportAuthority = (Permission.ANALYSIS_DATA_SHOP.isGranted(login) && getConfig().isOne())
        || Permission.ANALYSIS_DATA_SITE.isGranted(login);

    bean.setExportAuthority(hasExportAuthority);

    AnalysisService service = ServiceLocator.getAnalysisService(getLoginInfo());

    RfmAnalysisSearchCondition condition = new RfmAnalysisSearchCondition();
    condition.setFrequencyPeriod(Integer.parseInt(bean.getFrequencyPeriod()));
    condition.setFrequencyThresholdA(Integer.parseInt(bean.getFrequencyThresholdA()));
    condition.setFrequencyThresholdB(Integer.parseInt(bean.getFrequencyThresholdB()));
    condition.setMonetaryPeriod(Integer.parseInt(bean.getMonetaryPeriod()));
    condition.setMonetaryThresholdA(NumUtil.toLong(bean.getMonetaryThresholdA()));
    condition.setMonetaryThresholdB(NumUtil.toLong(bean.getMonetaryThresholdB()));
    condition.setRecencyThresholdA(Integer.parseInt(bean.getRecencyThresholdA()));
    condition.setRecencyThresholdB(Integer.parseInt(bean.getRecencyThresholdB()));

    RfmAnalysisData data = service.getRfmAnalysis(condition);

    List<List<RfAnalysisReportBean>> rfMatrix = new ArrayList<List<RfAnalysisReportBean>>();
    for (RecencyRank rRank : RecencyRank.values()) {
      List<RfAnalysisReportBean> rfList = new ArrayList<RfAnalysisReportBean>();
      for (FrequencyRank fRank : FrequencyRank.values()) {
        RfAnalysisReportBean rfBean = new RfAnalysisReportBean();
        RfAnalysisSummary summary = data.getRf(rRank, fRank);

        rfBean.setRecencyRank(rRank.getValue());
        rfBean.setFrequencyRank(fRank.getValue());
        rfBean.setCustomerCount(NumUtil.toString(summary.getCustomerCount()));
        rfBean.setCustomerCountRatio(summary.getCustomerCountRatio());
        rfBean.setOrderCountAvarage(summary.getOrderCountAvarage());
        rfBean.setOrderCountRatio(summary.getOrderCountRatio());
        rfBean.setPurchasingAmountAvarage(summary.getPurchasingAmountAvarage());
        rfBean.setPurchasingAmountRatio(summary.getPurchasingAmountRatio());

        rfList.add(rfBean);
      }
      rfMatrix.add(rfList);
    }
    bean.setRfAnalysisResult(rfMatrix);

    List<List<RmAnalysisReportBean>> rmMatrix = new ArrayList<List<RmAnalysisReportBean>>();
    for (RecencyRank rRank : RecencyRank.values()) {
      List<RmAnalysisReportBean> rmList = new ArrayList<RmAnalysisReportBean>();
      for (MonetaryRank mRank : MonetaryRank.values()) {
        RmAnalysisReportBean rmBean = new RmAnalysisReportBean();
        RmAnalysisSummary summary = data.getRm(rRank, mRank);

        rmBean.setRecencyRank(rRank.getValue());
        rmBean.setMonetaryRank(mRank.getValue());
        rmBean.setCustomerCount(NumUtil.toString(summary.getCustomerCount()));
        rmBean.setCustomerCountRatio(summary.getCustomerCountRatio());
        rmBean.setTotalPurchasedAmount(NumUtil.toString(summary.getTotalPurchasedAmount()));
        rmBean.setTotalPurchasedAmountRatio(summary.getTotalPurchasedAmountRatio());

        rmList.add(rmBean);
      }
      rmMatrix.add(rmList);
    }
    bean.setRmAnalysisResult(rmMatrix);

    List<List<FmAnalysisReportBean>> fmMatrix = new ArrayList<List<FmAnalysisReportBean>>();
    for (FrequencyRank fRank : FrequencyRank.values()) {
      List<FmAnalysisReportBean> fmList = new ArrayList<FmAnalysisReportBean>();
      for (MonetaryRank mRank : MonetaryRank.values()) {
        FmAnalysisReportBean fmBean = new FmAnalysisReportBean();
        FmAnalysisSummary summary = data.getFm(fRank, mRank);

        fmBean.setFrequencyRank(fRank.getValue());
        fmBean.setMonetaryRank(mRank.getValue());
        fmBean.setCustomerCount(NumUtil.toString(summary.getCustomerCount()));
        fmBean.setCustomerCountRatio(summary.getCustomerCountRatio());
        fmBean.setOrderCountAvarage(summary.getOrderCountAvarage());
        fmBean.setOrderCountRatio(summary.getOrderCountRatio());
        fmBean.setPurchasingAmountAvarage(summary.getPurchasingAmountAvarage());
        fmBean.setPurchasingAmountRatio(summary.getPurchasingAmountRatio());
        fmBean.setTotalPurchasedAmount(NumUtil.toString(summary.getTotalPurchasedAmount()));
        fmBean.setTotalPurchasedAmountRatio(summary.getTotalPurchasedAmountRatio());

        fmList.add(fmBean);
      }
      fmMatrix.add(fmList);
    }
    bean.setFmAnalysisResult(fmMatrix);

    bean.setDisplaySearchResultFlg(true);
    addCookie(bean);

    setRequestBean(bean);
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * cookieに検索条件を保持します。

   * 内容はRの閾値A、Rの閾値B、Fの集計期間、Fの閾値A、Fの閾値B、Mの集計期間、Mの閾値A、Mの閾値Bをそれぞれ";"でつないだものです。

   */
  private void addCookie(RfmAnalysisBean bean) {
    String path = "/app/analysis";
    int alivePeriod = 60 * 60 * 24 * 365 * 30;
    StringBuilder builder = new StringBuilder();
    builder.append(bean.getRecencyThresholdA() + ";");
    builder.append(bean.getRecencyThresholdB() + ";");
    builder.append(bean.getFrequencyPeriod() + ";");
    builder.append(bean.getFrequencyThresholdA() + ";");
    builder.append(bean.getFrequencyThresholdB() + ";");
    builder.append(bean.getMonetaryPeriod() + ";");
    builder.append(bean.getMonetaryThresholdA() + ";");
    builder.append(bean.getMonetaryThresholdB());
    getCookieContainer().addSecureCookie(RfmAnalysisBean.COOKIE_NAME, builder.toString(), path, alivePeriod);
  }

  /**
   * Action名の取得
   * 
   * @return Action名

   */
  public String getActionName() {
    return Messages.getString("web.action.back.analysis.RfmAnalysisSearchAction.6");
  }

  /**
   * オペレーションコードの取得

   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "6107071003";
  }

}
