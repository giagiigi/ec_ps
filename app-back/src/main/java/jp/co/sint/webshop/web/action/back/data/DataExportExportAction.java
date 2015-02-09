package jp.co.sint.webshop.web.action.back.data;

import jp.co.sint.webshop.data.csv.CsvSchema;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.data.CsvConditionImpl;
import jp.co.sint.webshop.service.data.CsvExportCondition;
import jp.co.sint.webshop.service.data.CsvExportType;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.WebExportAction;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.data.DataExportBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1080110:データ一括出力のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class DataExportExportAction extends WebBackAction<DataExportBean> implements WebExportAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    boolean result = false;
    BackLoginInfo login = getLoginInfo();

    // データ入出力アクセス_サイトを持つユーザは常にアクセス可能
    if (Permission.DATA_IO_ACCESS_SITE.isGranted(login) || Permission.DATA_IO_ACCESS_SHOP.isGranted(login)) {
      result = true;
    }

    // 権限のチェック
    CsvExportType type = CsvExportType.fromValue(getBean().getExportDataType());
    if (type == null) {
      return false;
    }
    for (Permission permission : type.getPermissions()) {
      result |= permission.isGranted(login);
    }

    return result;
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    DataExportBean bean = getBean();
    return validateBean(bean);
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    DataExportBean reqBean = getBean();
    BackLoginInfo login = getLoginInfo();

    CsvExportType exType = CsvExportType.fromValue(reqBean.getExportDataType());
    CsvConditionImpl<CsvSchema> condition = exType.createConditionInstance();
    condition.setHeader(reqBean.getCsvHeaderCondition().equals(WebConstantCode.VALUE_TRUE));
    if (login.isShop()) {
      condition.setShopCode(login.getShopCode());
    }
    this.exportCondition = condition;

    setNextUrl("/download");

    setRequestBean(reqBean);

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
    return Messages.getString("web.action.back.data.DataExportExportAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "7108011001";
  }

}
