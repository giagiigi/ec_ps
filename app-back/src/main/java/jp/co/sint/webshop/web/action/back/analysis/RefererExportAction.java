package jp.co.sint.webshop.web.action.back.analysis;

import jp.co.sint.webshop.data.csv.CsvSchema;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.analysis.RefererSearchCondition;
import jp.co.sint.webshop.service.data.CsvExportCondition;
import jp.co.sint.webshop.service.data.CsvExportType;
import jp.co.sint.webshop.service.data.csv.RefererExportCondition;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.WebExportAction;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.analysis.RefererBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

import org.apache.log4j.Logger;

/**
 * U1070130:リファラー集計のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class RefererExportAction extends WebBackAction<RefererBean> implements WebExportAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    BackLoginInfo login = getLoginInfo();
    // 運用モードに関係なく、サイト管理者の参照権限＋データ入出力権限がなければ認証エラー
    return Permission.ANALYSIS_READ_SITE.isGranted(login) && Permission.ANALYSIS_DATA_SITE.isGranted(login);
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    RefererBean bean = getBean();

    RefererSearchCondition searchCondition = new RefererSearchCondition();
    searchCondition.setSearchStartDate(DateUtil.fromString(bean.getSearchStartDate()));
    searchCondition.setSearchEndDate(DateUtil.fromString(bean.getSearchEndDate()));
    searchCondition.setClientGroup(bean.getClientGroupCondition());

    RefererExportCondition condition = CsvExportType.EXPORT_CSV_REFERER.createConditionInstance();
    condition.setSearchCondition(searchCondition);
    this.exportCondition = condition;

    setRequestBean(bean);
    setNextUrl("/download");

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    Logger logger = Logger.getLogger(this.getClass());

    RefererBean bean = getBean();
    boolean result = false;

    if (validateBean(bean)) {
      if (StringUtil.isCorrectRange(bean.getSearchStartDate(), bean.getSearchEndDate())) {
        result = true;
      } else {
        logger.error(WebMessage.get(ActionErrorMessage.PERIOD_ERROR));
        addErrorMessage(WebMessage.get(ActionErrorMessage.PERIOD_ERROR));
        result = false;
      }
    }
    return result;
  }

  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {
    BackLoginInfo login = getLoginInfo();
    // データ入出力権限が付与されていれば、CSV出力ボタンを表示する
    if (Permission.ANALYSIS_DATA_SITE.isGranted(login)) {
      RefererBean bean = (RefererBean) getRequestBean();
      bean.setExportAuthority(true);
      setRequestBean(bean);
    }
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
    return Messages.getString("web.action.back.analysis.RefererExportAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "6107013001";
  }

}
