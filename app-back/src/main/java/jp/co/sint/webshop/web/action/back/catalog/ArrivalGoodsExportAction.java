package jp.co.sint.webshop.web.action.back.catalog;

import jp.co.sint.webshop.data.csv.CsvSchema;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.catalog.ArrivalGoodsSearchCondition;
import jp.co.sint.webshop.service.data.CsvExportCondition;
import jp.co.sint.webshop.service.data.CsvExportType;
import jp.co.sint.webshop.service.data.csv.ArrivalGoodsExportCondition;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.WebExportAction;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.catalog.ArrivalGoodsBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerUtil;

/**
 * U1040710:入荷お知らせのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class ArrivalGoodsExportAction extends WebBackAction<ArrivalGoodsBean> implements WebExportAction {

  /**
   * 初期処理を実行します
   */
  @Override
  public void init() {
    super.init();
    if (getLoginInfo().isShop() || getConfig().isOne()) {
      getBean().setSearchShopCode(getLoginInfo().getShopCode());
    }
  }

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return Permission.COMMODITY_DATA_IO.isGranted(getLoginInfo());
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
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    if (getLoginInfo().isShop()) {
      getBean().setSearchShopCode(getLoginInfo().getShopCode());
    }

    // 検索条件の生成
    String[] skuCode = getRequestParameter().getPathArgs();
    if (skuCode.length < 1) {
      addErrorMessage(WebMessage.get(ActionErrorMessage.NO_CHECKED,
          Messages.getString("web.action.back.catalog.ArrivalGoodsExportAction.0")));
      return BackActionResult.RESULT_SUCCESS;
    }

    ArrivalGoodsSearchCondition searchCondition = new ArrivalGoodsSearchCondition();
    searchCondition.setSearchShopCode(getBean().getSearchShopCode());
    searchCondition.setSearchCommodityName(getBean().getSearchCommodityName());
    searchCondition.setSearchSkuCode(skuCode[0]);
    searchCondition = PagerUtil.createSearchCondition(getRequestParameter(), searchCondition);

    ArrivalGoodsExportCondition condition = CsvExportType.EXPORT_CSV_ARRIVAL_GOODS.createConditionInstance();
    condition.setCondition(searchCondition);
    this.exportCondition = condition;

    setNextUrl("/download");

    // 画面表示用Beanを次画面Beanを設定
    setRequestBean(getBean());

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
    return Messages.getString("web.action.back.catalog.ArrivalGoodsExportAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3104071002";
  }

}
