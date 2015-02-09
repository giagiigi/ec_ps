package jp.co.sint.webshop.web.action.back.analysis;

import jp.co.sint.webshop.data.csv.CsvSchema;
import jp.co.sint.webshop.service.analysis.CommodityAccessLogSearchCondition;
import jp.co.sint.webshop.service.data.CsvExportCondition;
import jp.co.sint.webshop.service.data.CsvExportType;
import jp.co.sint.webshop.service.data.csv.CommodityAccessLogExportCondition;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.WebExportAction;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.analysis.CommodityAccessLogBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1070210:商品別アクセスログ集計のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class CommodityAccessLogExportAction extends CommodityAccessLogBaseAction implements WebExportAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return hasExportAuthority();
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    CommodityAccessLogBean searchBean = getBean();
    CommodityAccessLogSearchCondition searchCondition = new CommodityAccessLogSearchCondition();

    searchCondition.setSearchStartDate(DateUtil.fromString(searchBean.getSearchStartDate()));
    searchCondition.setSearchEndDate(DateUtil.fromString(searchBean.getSearchEndDate()));
    searchCondition.setClientGroup(searchBean.getClientGroupCondition());
    searchCondition.setShopCode(searchBean.getShopCodeCondition());
    searchCondition.setSearchCommodityCodeStart(searchBean.getSearchCommodityCodeStart());
    searchCondition.setSearchCommodityCodeEnd(searchBean.getSearchCommodityCodeEnd());
    searchCondition.setCommodityName(searchBean.getCommodityName());

    CommodityAccessLogExportCondition condition = CsvExportType.EXPORT_CSV_COMMODITY_ACCESS_LOG.createConditionInstance();
    condition.setSearchCondition(searchCondition);
    
    this.exportCondition = condition;

    setRequestBean(searchBean);
    setNextUrl("/download");

    return BackActionResult.RESULT_SUCCESS;
  }
  
  private CsvExportCondition<? extends CsvSchema> exportCondition;
  
  public CsvExportCondition<? extends CsvSchema> getExportCondition() {
    return exportCondition;
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    CommodityAccessLogBean searchBean = getBean();
    boolean result = false;

    if (validateBean(searchBean)) {
      result = true;
      // 検索期間が正しいか判定
      if (StringUtil.isCorrectRange(searchBean.getSearchStartDate(), searchBean.getSearchEndDate())) {
        result &= true;
      } else {
        result &= false;
        addErrorMessage(WebMessage.get(ActionErrorMessage.PERIOD_ERROR));
      }

      // 検索商品コード開始，終了共に値があるとき，
      // 範囲が正しいか判定
      if (StringUtil.hasValue(searchBean.getSearchCommodityCodeStart())
          && StringUtil.hasValue(searchBean.getSearchCommodityCodeEnd())) {
        if (StringUtil.isCorrectRange(searchBean.getSearchCommodityCodeStart(), searchBean.getSearchCommodityCodeEnd())) {
          result &= true;
        } else {
          result &= false;
          addErrorMessage(WebMessage.get(ActionErrorMessage.SEARCHCONDITION_ERROR));
        }
      }
    }

    return result;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.analysis.CommodityAccessLogExportAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "6107021001";
  }

}
