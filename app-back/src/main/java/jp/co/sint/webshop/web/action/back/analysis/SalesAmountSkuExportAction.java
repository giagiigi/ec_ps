package jp.co.sint.webshop.web.action.back.analysis;

import java.util.Date;

import jp.co.sint.webshop.data.csv.CsvSchema;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.analysis.SalesAmountBySkuSearchCondition;
import jp.co.sint.webshop.service.data.CsvExportCondition;
import jp.co.sint.webshop.service.data.CsvExportType;
import jp.co.sint.webshop.service.data.csv.SalesAmountSkuExportCondition;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.WebExportAction;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.analysis.SalesAmountSkuBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

import org.apache.log4j.Logger;

/**
 * U1070830:商品別売上集計のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class SalesAmountSkuExportAction extends WebBackAction<SalesAmountSkuBean> implements WebExportAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    BackLoginInfo login = getLoginInfo();

    return Permission.ANALYSIS_DATA_SITE.isGranted(login) || Permission.ANALYSIS_DATA_SHOP.isGranted(login);

  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    boolean result = true;

    SalesAmountSkuBean bean = getBean();

    result &= validateBean(bean);

    Date startDate = DateUtil.fromString(bean.getSearchStartDate());
    Date endDate = DateUtil.fromString(bean.getSearchEndDate());
    if (startDate != null && endDate != null) {
      if (startDate.after(endDate)) {
        addErrorMessage(WebMessage.get(ActionErrorMessage.PERIOD_ERROR));
        result &= false;
      }
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
    Logger logger = Logger.getLogger(this.getClass());
    SalesAmountSkuBean requestBean = getBean();

    SalesAmountBySkuSearchCondition searchCondition = new SalesAmountBySkuSearchCondition();

    Date searchStartDate = DateUtil.fromString(requestBean.getSearchStartDate());
    Date searchEndDate = DateUtil.fromString(requestBean.getSearchEndDate());
    searchCondition.setSearchStartDate(searchStartDate);
    searchCondition.setSearchEndDate(searchEndDate);
    if (getLoginInfo().isSite()) {
      searchCondition.setShopCode(requestBean.getShopCodeCondition());
    } else {
      searchCondition.setShopCode(getLoginInfo().getShopCode());
    }
    searchCondition.setCommodityCodeStart(requestBean.getCommodityCodeStart());
    searchCondition.setCommodityCodeEnd(requestBean.getCommodityCodeEnd());
    searchCondition.setCommoditySkuName(requestBean.getSkuName());

    SalesAmountSkuExportCondition condition = CsvExportType.EXPORT_CSV_SALES_AMOUNT_SKU.createConditionInstance();
    condition.setSearchCondition(searchCondition);

    this.exportCondition = condition;

    setRequestBean(requestBean);
    setNextUrl("/download");

    logger.debug(Messages.log("web.action.back.analysis.SalesAmountSkuExportAction.0"));

    return BackActionResult.RESULT_SUCCESS;
  }

  private CsvExportCondition<? extends CsvSchema> exportCondition;

  public CsvExportCondition<? extends CsvSchema> getExportCondition() {
    return this.exportCondition;
  }


  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.analysis.SalesAmountSkuExportAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "6107083001";
  }

}
