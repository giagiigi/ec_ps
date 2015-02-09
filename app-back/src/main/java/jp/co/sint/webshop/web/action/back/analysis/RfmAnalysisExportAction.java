package jp.co.sint.webshop.web.action.back.analysis;

import jp.co.sint.webshop.data.csv.CsvSchema;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.analysis.FrequencyRank;
import jp.co.sint.webshop.service.analysis.MonetaryRank;
import jp.co.sint.webshop.service.analysis.RecencyRank;
import jp.co.sint.webshop.service.analysis.RfmAnalysisExportSearchCondition;
import jp.co.sint.webshop.service.data.CsvExportCondition;
import jp.co.sint.webshop.service.data.CsvExportType;
import jp.co.sint.webshop.service.data.csv.FmAnalysisExportCondition;
import jp.co.sint.webshop.service.data.csv.RfAnalysisExportCondition;
import jp.co.sint.webshop.service.data.csv.RmAnalysisExportCondition;
import jp.co.sint.webshop.utility.BigDecimalUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.WebExportAction;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.analysis.RfmAnalysisBean;
import jp.co.sint.webshop.web.exception.URLNotFoundException;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.message.back.analysis.AnalysisErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1070710:RFM分析のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class RfmAnalysisExportAction extends WebBackAction<RfmAnalysisBean> implements WebExportAction {

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
    return (Permission.ANALYSIS_DATA_SHOP.isGranted(login) && getConfig().isOne())
        || Permission.ANALYSIS_DATA_SITE.isGranted(login);
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

    String[] pathArgs = getRequestParameter().getPathArgs();
    if (pathArgs.length < 3) {
      throw new URLNotFoundException(WebMessage.get(ActionErrorMessage.BAD_URL));
    }

    String type = pathArgs[0];
    if (!type.equals("rf") && !type.equals("rm") && !type.equals("fm")) {
      throw new URLNotFoundException(WebMessage.get(ActionErrorMessage.BAD_URL));
    }

    String row = pathArgs[1];
    String column = pathArgs[2];
    if ((!row.equals("A") && !row.equals("B") && !row.equals("C"))
        || (!column.equals("A") && !column.equals("B") && !column.equals("C"))) {
      throw new URLNotFoundException(WebMessage.get(ActionErrorMessage.BAD_URL));
    }

    if (validateBean(searchBean)) {
      result = true;

      if (NumUtil.toLong(searchBean.getRecencyThresholdA()) >= NumUtil.toLong(searchBean.getRecencyThresholdB())) {
        result &= false;
        addErrorMessage(WebMessage.get(AnalysisErrorMessage.SEARCH_RANGE_ERROR,
            Messages.getString("web.action.back.analysis.RfmAnalysisExportAction.0"),
            Messages.getString("web.action.back.analysis.RfmAnalysisExportAction.1")));
      }

      if (NumUtil.toLong(searchBean.getFrequencyThresholdA()) <= NumUtil.toLong(searchBean.getFrequencyThresholdB())) {
        result &= false;
        addErrorMessage(WebMessage.get(AnalysisErrorMessage.SEARCH_RANGE_ERROR,
            Messages.getString("web.action.back.analysis.RfmAnalysisExportAction.2"),
            Messages.getString("web.action.back.analysis.RfmAnalysisExportAction.3")));
      }

      if (!BigDecimalUtil.isAbove(NumUtil.parse(searchBean.getMonetaryThresholdA()),
          NumUtil.parse(searchBean.getMonetaryThresholdB()))) {
        result &= false;
        addErrorMessage(WebMessage.get(AnalysisErrorMessage.SEARCH_RANGE_ERROR,
            Messages.getString("web.action.back.analysis.RfmAnalysisExportAction.4"),
            Messages.getString("web.action.back.analysis.RfmAnalysisExportAction.5")));
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

    RfmAnalysisExportSearchCondition searchCondition = new RfmAnalysisExportSearchCondition();
    searchCondition.setFrequencyPeriod(Integer.parseInt(bean.getFrequencyPeriod()));
    searchCondition.setFrequencyThresholdA(NumUtil.toLong(bean.getFrequencyThresholdA()).intValue());
    searchCondition.setFrequencyThresholdB(NumUtil.toLong(bean.getFrequencyThresholdB()).intValue());
    searchCondition.setMonetaryPeriod(NumUtil.toLong(bean.getMonetaryPeriod()).intValue());
    searchCondition.setMonetaryThresholdA(NumUtil.toLong(bean.getMonetaryThresholdA()));
    searchCondition.setMonetaryThresholdB(NumUtil.toLong(bean.getMonetaryThresholdB()));
    searchCondition.setRecencyThresholdA(NumUtil.toLong(bean.getRecencyThresholdA()).intValue());
    searchCondition.setRecencyThresholdB(NumUtil.toLong(bean.getRecencyThresholdB()).intValue());
    
    String[] pathArgs = getRequestParameter().getPathArgs();

    if (pathArgs[0].equals("rf")) {
      RecencyRank rRank = RecencyRank.fromValue(pathArgs[1]);
      FrequencyRank fRank = FrequencyRank.fromValue(pathArgs[2]);
      searchCondition.setRecencyRank(rRank);
      searchCondition.setFrequencyRank(fRank);

      RfAnalysisExportCondition condition = CsvExportType.EXPORT_CSV_RF_ANALYSIS.createConditionInstance();
      condition.setSearchCondition(searchCondition);

      this.exportCondition = condition;
    } else if (pathArgs[0].equals("rm")) {
      RecencyRank rRank = RecencyRank.fromValue(pathArgs[1]);
      MonetaryRank mRank = MonetaryRank.fromValue(pathArgs[2]);
      searchCondition.setRecencyRank(rRank);
      searchCondition.setMonetaryRank(mRank);

      RmAnalysisExportCondition condition = CsvExportType.EXPORT_CSV_RM_ANALYSIS.createConditionInstance();
      condition.setSearchCondition(searchCondition);

      this.exportCondition = condition;
    } else if (pathArgs[0].equals("fm")) {
      FrequencyRank fRank = FrequencyRank.fromValue(pathArgs[1]);
      MonetaryRank mRank = MonetaryRank.fromValue(pathArgs[2]);
      searchCondition.setFrequencyRank(fRank);
      searchCondition.setMonetaryRank(mRank);

      FmAnalysisExportCondition condition = CsvExportType.EXPORT_CSV_FM_ANALYSIS.createConditionInstance();
      condition.setSearchCondition(searchCondition);

      this.exportCondition = condition;
    } else {
      throw new URLNotFoundException(WebMessage.get(ActionErrorMessage.BAD_URL));
    }

    setNextUrl("/download");

    setRequestBean(bean);
    return BackActionResult.RESULT_SUCCESS;
  }

  private CsvExportCondition<? extends CsvSchema> exportCondition;

  public CsvExportCondition<? extends CsvSchema> getExportCondition() {
    return this.exportCondition;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.analysis.RfmAnalysisExportAction.6");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "6107071001";
  }

}
