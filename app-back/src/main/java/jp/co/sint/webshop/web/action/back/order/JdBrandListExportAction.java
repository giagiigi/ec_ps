package jp.co.sint.webshop.web.action.back.order;

import jp.co.sint.webshop.data.csv.CsvSchema;
import jp.co.sint.webshop.service.data.CsvExportCondition;
import jp.co.sint.webshop.service.data.CsvExportType;
import jp.co.sint.webshop.service.data.csv.JdBrandListExportCondition;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.WebExportAction;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.catalog.JdBrandListBean;

/**
 * 2014/04/25 京东WBS对应 ob_卢 add
 * 京东商品品牌表
 * @author System Integrator Corp.
 */
public class JdBrandListExportAction extends WebBackAction<JdBrandListBean> implements WebExportAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return true;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    JdBrandListBean searchBean = getBean();

    JdBrandListExportCondition condition = CsvExportType.EXPORT_CSV_JD_BRAND_LIST.createConditionInstance();
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
    return "京东商品品牌表导出处理";
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3180004009";
  }

  @Override
  public boolean validate() {
    return true;
  }

}
