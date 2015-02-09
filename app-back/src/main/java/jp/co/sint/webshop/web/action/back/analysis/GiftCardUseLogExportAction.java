package jp.co.sint.webshop.web.action.back.analysis;

import jp.co.sint.webshop.data.csv.CsvSchema;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.analysis.GiftCardUseLogListSearchCondition;
import jp.co.sint.webshop.service.data.CsvExportCondition;
import jp.co.sint.webshop.service.data.CsvExportType;
import jp.co.sint.webshop.service.data.csv.GiftCardUseLogExportCondition;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.WebExportAction;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.analysis.GiftCardUseLogBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;

public class GiftCardUseLogExportAction extends WebBackAction<GiftCardUseLogBean> implements WebExportAction {

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

    GiftCardUseLogBean searchBean = getBean();

    // 検索結果リストを取得
    GiftCardUseLogListSearchCondition searchCondition = new GiftCardUseLogListSearchCondition();
      searchCondition.setSearchCardId(searchBean.getSearchCardId());
      searchCondition.setSearchCustomerCodeEnd(searchBean.getSearchCustomerCodeEnd());
      searchCondition.setSearchCustomerCodeStart(searchBean.getSearchCustomerCodeStart());
      searchCondition.setSearchCustomerName(searchBean.getSearchCustomerName());
      searchCondition.setSearchEmail(searchBean.getSearchEmail());
      searchCondition.setSearchOrderNo(searchBean.getSearchOrderNo());
      searchCondition.setSearchShippingStartDatetimeFrom(searchBean.getSearchShippingStartDatetimeFrom());
      searchCondition.setSearchShippingStartDatetimeTo(searchBean.getSearchShippingStartDatetimeTo());
      searchCondition.setSearchTelephoneNum(searchBean.getSearchTelephoneNum());

    
    GiftCardUseLogExportCondition condition = CsvExportType.EXPORT_CSV_GIFT_CART_USE_LOG.createConditionInstance();
    condition.setSearchCondition(searchCondition);
    this.exportCondition = condition;

    setNextUrl("/download");
    setRequestBean(searchBean);
    return BackActionResult.RESULT_SUCCESS;
  }

  private CsvExportCondition<? extends CsvSchema> exportCondition;

  public CsvExportCondition<? extends CsvSchema> getExportCondition() {
    return exportCondition;
  }

}
