package jp.co.sint.webshop.web.action.back.analysis;

import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.analysis.RfmAnalysisBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1070710:RFM分析のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class RfmAnalysisInitAction extends WebBackAction<RfmAnalysisBean> {

  private static final String DEFAULT_RECENCY_THRESHOLD_A = "60";

  private static final String DEFAULT_RECENCY_THRESHOLD_B = "180";

  private static final String DEFAULT_FREQUENCY_PERIOD = "6";

  private static final String DEFAULT_FREQUENCY_THRESHOLD_A = "10";

  private static final String DEFAULT_FREQUENCY_THRESHOLD_B = "5";

  private static final String DEFAULT_MONETARY_PERIOD = "6";

  private static final String DEFAULT_MONETARY_THRESHOLD_A = "50000";

  private static final String DEFAULT_MONETARY_THRESHOLD_B = "20000";

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
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    RfmAnalysisBean bean = getBean();

    // cookieから初期値を取得
    String cookieValue = getCookieContainer().getCookie(RfmAnalysisBean.COOKIE_NAME);
    if (StringUtil.hasValue(cookieValue) && (cookieValue.split(";").length) == 8) {
      String[] valueToken = cookieValue.split(";");
      bean.setRecencyThresholdA(valueToken[0]);
      bean.setRecencyThresholdB(valueToken[1]);
      bean.setFrequencyPeriod(valueToken[2]);
      bean.setFrequencyThresholdA(valueToken[3]);
      bean.setFrequencyThresholdB(valueToken[4]);
      bean.setMonetaryPeriod(valueToken[5]);
      bean.setMonetaryThresholdA(valueToken[6]);
      bean.setMonetaryThresholdB(valueToken[7]);
    } else {
      bean.setRecencyThresholdA(DEFAULT_RECENCY_THRESHOLD_A);
      bean.setRecencyThresholdB(DEFAULT_RECENCY_THRESHOLD_B);
      bean.setFrequencyPeriod(DEFAULT_FREQUENCY_PERIOD);
      bean.setFrequencyThresholdA(DEFAULT_FREQUENCY_THRESHOLD_A);
      bean.setFrequencyThresholdB(DEFAULT_FREQUENCY_THRESHOLD_B);
      bean.setMonetaryPeriod(DEFAULT_MONETARY_PERIOD);
      bean.setMonetaryThresholdA(DEFAULT_MONETARY_THRESHOLD_A);
      bean.setMonetaryThresholdB(DEFAULT_MONETARY_THRESHOLD_B);
    }

    bean.setDisplaySearchResultFlg(false);

    boolean hasExportAuthority;
    BackLoginInfo login = getLoginInfo();
    hasExportAuthority = (Permission.ANALYSIS_DATA_SHOP.isGranted(login) && getConfig().isOne())
        || Permission.ANALYSIS_DATA_SITE.isGranted(login);

    bean.setExportAuthority(hasExportAuthority);

    setRequestBean(bean);
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    return true;
  }


  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.analysis.RfmAnalysisInitAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "6107071002";
  }

}
