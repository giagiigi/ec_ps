package jp.co.sint.webshop.web.action.back.order;

import jp.co.sint.webshop.data.csv.CsvSchema;
import jp.co.sint.webshop.service.data.CsvExportCondition;
import jp.co.sint.webshop.service.data.CsvExportType;
import jp.co.sint.webshop.service.data.csv.BrandListExportCondition;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.WebExportAction;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.catalog.BrandListBean;

/**
 * EC商品品牌表
 * 
 * @author System Integrator Corp.
 */
public class BrandListExportAction extends WebBackAction<BrandListBean> implements WebExportAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    // データ入出力権限チェック
    // BackLoginInfo login = getLoginInfo();
    return true;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    BrandListBean searchBean = getBean();

    BrandListExportCondition condition = CsvExportType.EXPORT_CSV_BRAND_LIST.createConditionInstance();
    this.exportCondition = condition;
    
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
    return "EC商品品牌表导出处理";
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3180004007";
  }

  @Override
  public boolean validate() {
    // TODO Auto-generated method stub
    return true;
  }

}
