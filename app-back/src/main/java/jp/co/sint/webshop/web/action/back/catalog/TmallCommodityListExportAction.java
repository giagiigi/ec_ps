package jp.co.sint.webshop.web.action.back.catalog;

import jp.co.sint.webshop.data.csv.CsvSchema;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.catalog.CommodityListSearchCondition;
import jp.co.sint.webshop.service.data.CsvExportCondition;
import jp.co.sint.webshop.service.data.CsvExportType;
import jp.co.sint.webshop.service.data.csv.CommodityDataExportCondition;
import jp.co.sint.webshop.utility.NumUtil; // 10.1.7 10307 追加
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.validation.ValidatorUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.WebExportAction;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerUtil;

/**
 * U1040110:商品マスタのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class TmallCommodityListExportAction extends TmallCommodityListBaseAction implements WebExportAction {

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
    boolean isValid = true;

    // 検索条件のvalidationチェック
    isValid &= validateBean(getBean());

    // 商品コードの大小チェック
    if (StringUtil.hasValueAllOf(getBean().getSearchCommodityCodeStart(), getBean().getSearchCommodityCodeEnd())) {
      if (!ValidatorUtil.isCorrectOrder(getBean().getSearchCommodityCodeStart(), getBean().getSearchCommodityCodeEnd())) {
        addErrorMessage(WebMessage.get(ActionErrorMessage.COMPARISON_ERROR,
            Messages.getString("web.action.back.catalog.CommodityListExportAction.0")));
        isValid &= false;
      }
    }

    // 販売開始日時の前後チェック
    if (StringUtil.hasValueAllOf(getBean().getSearchSaleStartDateRangeFrom(), getBean().getSearchSaleStartDateRangeTo())) {
      if (!ValidatorUtil.isCorrectOrder(getBean().getSearchSaleStartDateRangeFrom(), getBean().getSearchSaleStartDateRangeTo())) {
        addErrorMessage(WebMessage.get(ActionErrorMessage.COMPARISON_ERROR,
            Messages.getString("web.action.back.catalog.CommodityListExportAction.1")));
        isValid &= false;
      }
    }

    // 販売終了日時の前後チェック
    if (StringUtil.hasValueAllOf(getBean().getSearchSaleEndDateRangeFrom(), getBean().getSearchSaleEndDateRangeTo())) {
      if (!ValidatorUtil.isCorrectOrder(getBean().getSearchSaleEndDateRangeFrom(), getBean().getSearchSaleEndDateRangeTo())) {
        addErrorMessage(WebMessage.get(ActionErrorMessage.COMPARISON_ERROR,
            Messages.getString("web.action.back.catalog.CommodityListExportAction.2")));
        isValid &= false;
      }
    }

    // 販売終了数量の大小チェック
    // 10.1.7 10307 修正 ここから
    // if (StringUtil.hasValueAllOf(getBean().getSearchStockQuantityStart(), getBean().getSearchStockQuantityEnd())) {
    if (StringUtil.hasValueAllOf(getBean().getSearchStockQuantityStart(), getBean().getSearchStockQuantityEnd())
        && NumUtil.isNum(getBean().getSearchStockQuantityStart()) && NumUtil.isNum(getBean().getSearchStockQuantityEnd())) {
    // 10.1.7 10307 修正 ここまで
      Long start = Long.valueOf(getBean().getSearchStockQuantityStart());
      Long end = Long.valueOf(getBean().getSearchStockQuantityEnd());
      if (!ValidatorUtil.lessThanOrEquals(start, end)) {
        addErrorMessage(WebMessage.get(ActionErrorMessage.COMPARISON_ERROR,
            Messages.getString("web.action.back.catalog.CommodityListExportAction.3")));
        isValid &= false;
      }
    }

    return isValid;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    // 検索条件の生成
    CommodityListSearchCondition searchCondition = new CommodityListSearchCondition();
    setCondition(searchCondition);
    searchCondition = PagerUtil.createSearchCondition(getRequestParameter(), searchCondition);

    CommodityDataExportCondition condition = CsvExportType.EXPORT_CSV_COMMODITY_DATA.createConditionInstance();
    condition.setSearchCondition(searchCondition);
    this.exportCondition = condition;

    setRequestBean(getBean());
    setNextUrl("/download");

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
    return Messages.getString("web.action.back.catalog.CommodityListExportAction.4");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3104015002";
  }

}
