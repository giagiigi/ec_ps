package jp.co.sint.webshop.web.action.back.customer;

import jp.co.sint.webshop.data.csv.CsvSchema;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.customer.CouponStatusSearchCondition;
import jp.co.sint.webshop.service.data.CsvExportCondition;
import jp.co.sint.webshop.service.data.CsvExportType;
import jp.co.sint.webshop.service.data.csv.CouponStatusAllExportCondition;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.WebExportAction;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.customer.CouponStatusBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1030310:ポイント利用状況のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class CouponStatusExportAction extends WebBackAction<CouponStatusBean> implements WebExportAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    BackLoginInfo login = getLoginInfo();
    return Permission.CUSTOMER_COUPON_IO.isGranted(login);
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    CouponStatusSearchCondition searchCondition = new CouponStatusSearchCondition();

    // 検索条件をセット
    CouponStatusBean bean = getBean();
    searchCondition.setSearchIssueFromDate(bean.getSearchIssueFromDate());
    searchCondition.setSearchIssueToDate(bean.getSearchIssueToDate());
    searchCondition.setSearchCouponStatus(bean.getSearchCouponStatus());
    
      // 集計対象が全明細の場合
    CouponStatusAllExportCondition condition = CsvExportType.EXPORT_CSV_COUPON_STATUS_ALL.createConditionInstance();
    condition.setSearchCondition(searchCondition);

    this.exportCondition = condition;
    setNextUrl("/download");

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

    boolean result = true;
    CouponStatusBean bean = getBean();

    // bean(検索条件)のvalidationチェック
    result = validateBean(bean);
    if (!result) {
      return result;
    }

    //日付の大小関係チェック
    CouponStatusSearchCondition validatedCondition = new CouponStatusSearchCondition();
    validatedCondition.setSearchIssueFromDate(bean.getSearchIssueFromDate());
    validatedCondition.setSearchIssueToDate(bean.getSearchIssueToDate());

    if (!validatedCondition.isValid()) {
      addErrorMessage(WebMessage.get(ActionErrorMessage.PERIOD_ERROR));
      result = false;
    }

    return result;
  }

  private CsvExportCondition<? extends CsvSchema> exportCondition;

  public CsvExportCondition<? extends CsvSchema> getExportCondition() {
    return exportCondition;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.customer.PointStatusExportAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "2103031001";
  }

}
