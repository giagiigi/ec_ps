package jp.co.sint.webshop.web.action.back.analysis;

import jp.co.sint.webshop.data.csv.CsvSchema;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.analysis.GiftCardLogSearchCondition;
import jp.co.sint.webshop.service.data.CsvExportCondition;
import jp.co.sint.webshop.service.data.CsvExportType;
import jp.co.sint.webshop.service.data.csv.GiftCardLogExportCondition;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.WebExportAction;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.analysis.GiftCardLogBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;

public class GiftCardLogExportAction extends WebBackAction<GiftCardLogBean> implements WebExportAction {

  @Override
  public boolean authorize() {
    // データ入出力権限チェック
    BackLoginInfo login = getLoginInfo();
    if (null == login) {
      return false;
    }
    
    return Permission.ANALYSIS_READ_SITE.isGranted(login);
  }

  @Override
  public boolean validate() {
    return true;
  }

  @Override
  public WebActionResult callService() {

    GiftCardLogBean searchBean = getBean();

    String exportMode = "";

    // parameter[0] : 処理用パラメータ
    String[] parameter = getRequestParameter().getPathArgs();
    if (parameter.length > 0) {
      exportMode = parameter[0];
    }

    // 検索結果リストを取得
    GiftCardLogSearchCondition searchCondition = new GiftCardLogSearchCondition();
      searchCondition.setSearchGiftCardCode(searchBean.getSearchGiftCardCode());
      searchCondition.setSearchGiftCardName(searchBean.getSearchGiftCardName());
      searchCondition.setSearchMinIssueStartDatetimeFrom(searchBean.getSearchMinIssueStartDatetimeFrom());
      searchCondition.setSearchMinIssueStartDatetimeTo(searchBean.getSearchMinIssueStartDatetimeTo());

    // 顧客情報
    if (exportMode.equals("info")) {
      GiftCardLogExportCondition condition = CsvExportType.EXPORT_CSV_GIFT_CART_LOG.createConditionInstance();
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

}
