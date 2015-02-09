package jp.co.sint.webshop.web.action.back.customer;

import jp.co.sint.webshop.data.csv.CsvSchema;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.customer.CustomerSearchCondition;
import jp.co.sint.webshop.service.data.CsvExportCondition;
import jp.co.sint.webshop.service.data.CsvExportType;
import jp.co.sint.webshop.service.data.csv.CustomerDMExportCondition;
import jp.co.sint.webshop.service.data.csv.CustomerListExportCondition;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.WebExportAction;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.customer.CustomerListBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1030110:顧客マスタのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class CustomerListExportAction extends CustomerListBaseAction implements WebExportAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    // データ入出力権限チェック
    BackLoginInfo login = getLoginInfo();
    return Permission.CUSTOMER_IO.isGranted(login);
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    boolean result = true;
    CustomerListBean bean = getBean();

    // bean(検索条件)のvalidationチェック
    result = validateBean(bean);

    // 顧客コードの大小チェック
    CustomerSearchCondition condition = new CustomerSearchCondition();
    condition.setSearchCustomerFrom(bean.getSearchCustomerFrom());
    condition.setSearchCustomerTo(bean.getSearchCustomerTo());

    //add by V10-CH start
    //SiteManagementService siteManagementService = ServiceLocator.getSiteManagementService(getLoginInfo());
    //PointRule pointRule = siteManagementService.getPointRule();
    //condition.setScale(pointRule.getAmplificationRate().intValue());
    //add by V10-CH end
    
    if (!condition.isValid()) {
      // 検索条件不正エラーメッセージの設定
      addErrorMessage(WebMessage.get(ActionErrorMessage.SEARCHCONDITION_ERROR));

      result = false;
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

    CustomerListBean searchBean = getBean();

    String exportMode = "";

    // parameter[0] : 処理用パラメータ
    String[] parameter = getRequestParameter().getPathArgs();
    if (parameter.length > 0) {
      exportMode = parameter[0];
    }

    // 検索結果リストを取得
    CustomerSearchCondition searchCondition = new CustomerSearchCondition();
    setSearchCondition(searchBean, searchCondition);

    // 顧客情報
    if (exportMode.equals("info")) {
      CustomerListExportCondition condition = CsvExportType.EXPORT_CSV_CUSTOMER_LIST.createConditionInstance();
      condition.setSearchCondition(searchCondition);
      this.exportCondition = condition;
    } else if (exportMode.equals("dm")) {
      CustomerDMExportCondition condition = CsvExportType.EXPORT_CSV_CUSTOMER_DM.createConditionInstance();
      condition.setSearchCondition(searchCondition);
      this.exportCondition = condition;
    }

    setNextUrl("/download");
    setRequestBean(searchBean);
    return BackActionResult.RESULT_SUCCESS;
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
    return Messages.getString("web.action.back.customer.CustomerListExportAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "2103011001";
  }

}
