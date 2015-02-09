package jp.co.sint.webshop.web.action.back.customer;

import jp.co.sint.webshop.data.csv.CsvSchema;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.customer.PointStatusListSearchCondition;
import jp.co.sint.webshop.service.data.CsvExportCondition;
import jp.co.sint.webshop.service.data.CsvExportType;
import jp.co.sint.webshop.service.data.csv.PointStatusAllExportCondition;
import jp.co.sint.webshop.service.data.csv.PointStatusShopExportCondition;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.WebExportAction;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.customer.PointStatusBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1030310:ポイント利用状況のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class PointStatusExportAction extends WebBackAction<PointStatusBean> implements WebExportAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    BackLoginInfo login = getLoginInfo();
    return Permission.CUSTOMER_POINT_IO.isGranted(login);
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    PointStatusListSearchCondition searchCondition = new PointStatusListSearchCondition();

    // 検索条件をセット
    PointStatusBean bean = getBean();
    searchCondition.setSearchShopCode(bean.getSearchShopCode());
    searchCondition.setSearchIssueType(bean.getSearchIssueType());
    searchCondition.setSearchPointIssueStatus(bean.getSearchPointIssueStatus());
    searchCondition.setSearchPointIssueStartDate(bean.getSearchPointIssueStartDate());
    searchCondition.setSearchPointIssueEndDate(bean.getSearchPointIssueEndDate());
    searchCondition.setSearchSummaryCondition(bean.getSearchSummaryCondition());

//  add by V10-CH start
    //SiteManagementService siteManagementService = ServiceLocator.getSiteManagementService(getLoginInfo());
    //PointRule pointRule = siteManagementService.getPointRule();
    //searchCondition.setScale(pointRule.getAmplificationRate().intValue());
    //add by V10-CH end
    
    // CSV出力処理実行
    if (bean.getSearchSummaryCondition().equals(PointStatusBean.SUMMARY_TYPE_ALL)) {
      // 集計対象が全明細の場合
      PointStatusAllExportCondition condition = CsvExportType.EXPORT_CSV_POINT_STATUS_ALL.createConditionInstance();
      condition.setSearchCondition(searchCondition);

      this.exportCondition = condition;
      setNextUrl("/download");
      // 集計対象がショップ別の場合
    } else if (bean.getSearchSummaryCondition().equals(PointStatusBean.SUMMARY_TYPE_SHOP)) {
      PointStatusShopExportCondition condition = CsvExportType.EXPORT_CSV_POINT_STATUS_SHOP.createConditionInstance();
      condition.setSearchCondition(searchCondition);

      this.exportCondition = condition;

      setNextUrl("/download");
    } else {
      addErrorMessage(WebMessage.get(ActionErrorMessage.NO_CHECKED,
          Messages.getString("web.action.back.customer.PointStatusExportAction.0")));
    }

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
    PointStatusBean bean = getBean();

    // bean(検索条件)のvalidationチェック
    result = validateBean(bean);
    if (!result) {
      return result;
    }

    // 日付の大小関係チェック
    PointStatusListSearchCondition validatedCondition = new PointStatusListSearchCondition();
    validatedCondition.setSearchPointIssueStartDate(bean.getSearchPointIssueStartDate());
    validatedCondition.setSearchPointIssueEndDate(bean.getSearchPointIssueEndDate());

    if (!validatedCondition.isValid()) {
      addErrorMessage(WebMessage.get(ActionErrorMessage.SEARCHCONDITION_ERROR));
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
