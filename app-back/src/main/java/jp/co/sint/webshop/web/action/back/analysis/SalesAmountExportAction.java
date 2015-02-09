package jp.co.sint.webshop.web.action.back.analysis;

import java.util.Date;

import jp.co.sint.webshop.data.csv.CsvSchema;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.data.CsvExportCondition;
import jp.co.sint.webshop.service.data.CsvExportType;
import jp.co.sint.webshop.service.data.csv.SalesAmountExportCondition;
import jp.co.sint.webshop.utility.DateRange;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.WebExportAction;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.analysis.SalesAmountBean;
import jp.co.sint.webshop.web.bean.back.analysis.SalesAmountBean.SalesAmountBeanDetail;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1070810:売上集計のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class SalesAmountExportAction extends WebBackAction<SalesAmountBean> implements WebExportAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    if (getConfig().isOne()) {
      return false;
    }
    return Permission.ANALYSIS_DATA_SHOP.isGranted(getLoginInfo()) || Permission.ANALYSIS_DATA_SITE.isGranted(getLoginInfo());
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    SalesAmountBean requestBean = getBean();
    requestBean.setSearchSiteResult(new SalesAmountBeanDetail());
    requestBean.getSearchShopResult().clear();

    Date startDate = DateUtil.fromYearMonth(requestBean.getSearchYear(), requestBean.getSearchMonth());
    Date endDate = DateUtil.addDate(DateUtil.addMonth(startDate, 1), -1);

    SalesAmountExportCondition condition = CsvExportType.EXPORT_SALES_AMOUNT.createConditionInstance();
    condition.setSite(false);
    condition.setRange(new DateRange(startDate, endDate));
    this.exportCondition = condition;

    setRequestBean(requestBean);

    return BackActionResult.RESULT_SUCCESS;
  }

  private CsvExportCondition<? extends CsvSchema> exportCondition;

  public CsvExportCondition<? extends CsvSchema> getExportCondition() {
    return this.exportCondition;
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    return validateBean(getBean());
  }

  /**
   * 遷移先の画面のURLを取得します。
   * 
   * @return 遷移先の画面のURL
   */
  public String getNextUrl() {
    return "/download";
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.analysis.SalesAmountExportAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "6107081001";
  }

}
