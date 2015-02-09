package jp.co.sint.webshop.web.action.back.service;

import jp.co.sint.webshop.data.csv.CsvSchema;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.customer.InquirySearchCondition;
import jp.co.sint.webshop.service.data.CsvExportCondition;
import jp.co.sint.webshop.service.data.CsvExportType;
import jp.co.sint.webshop.service.data.csv.InquiryListExportCondition;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.WebExportAction;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.service.InquiryListBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

public class InquiryListExportAction extends WebBackAction<InquiryListBean> implements WebExportAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return getLoginInfo().hasPermission(Permission.SERVICE_COMPLAINT_DATA_EXPORT);
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    boolean result = true;
    InquiryListBean bean = getBean();
    // bean(検索条件)のvalidationチェック
    result = validateBean(bean);

    // 日期比较
    if (result) {
      if (StringUtil.hasValueAllOf(bean.getSearchAcceptDateFrom(), bean.getSearchAcceptDateTo())) {
        if (DateUtil.fromString(getBean().getSearchAcceptDateFrom()).after(DateUtil.fromString(getBean().getSearchAcceptDateTo()))) {
          addErrorMessage(WebMessage.get(ActionErrorMessage.PERIOD_ERROR_WITH_PARAM, Messages
              .getString("web.action.back.service.InquiryListExportAction.1")));
          result = false;
        }
      }

      if (StringUtil.hasValueAllOf(bean.getSearchAcceptUpdateFrom(), bean.getSearchAcceptUpdateTo())) {
        if (DateUtil.fromString(getBean().getSearchAcceptUpdateFrom()).after(
            DateUtil.fromString(getBean().getSearchAcceptUpdateTo()))) {
          addErrorMessage(WebMessage.get(ActionErrorMessage.PERIOD_ERROR_WITH_PARAM, Messages
              .getString("web.action.back.service.InquiryListExportAction.2")));
          result = false;
        }
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

    InquiryListBean bean = getBean();

    InquirySearchCondition searchCondition = new InquirySearchCondition();
    searchCondition.setSearchMobile(bean.getSearchMobile());
    searchCondition.setSearchCustomerName(bean.getSearchCustomerName());
    searchCondition.setSearchCustomerCode(bean.getSearchCustomerCode());
    searchCondition.setSearchPersonInChargeName(bean.getSearchPersonInChargeName());
    searchCondition.setSearchPersonInChargeNo(bean.getSearchPersonInChargeNo());
    searchCondition.setSearchAcceptDateFrom(bean.getSearchAcceptDateFrom());
    searchCondition.setSearchAcceptDateTo(bean.getSearchAcceptDateTo());
    searchCondition.setSearchAcceptUpdateFrom(bean.getSearchAcceptUpdateFrom());
    searchCondition.setSearchAcceptUpdateTo(bean.getSearchAcceptUpdateTo());
    searchCondition.setSearchInquiryStatus(bean.getSearchInquiryStatus());
    searchCondition.setSearchLargeCategory(bean.getSearchLargeCategory());
    searchCondition.setSearchSmallCategory(bean.getSearchSmallCategory());
    searchCondition.setSearchInquiryWay(bean.getSearchInquiryWay());
    searchCondition.setSearchIbObType(bean.getSearchIbObType());
    searchCondition.setSearchInquirySubject(bean.getSearchInquirySubject());

    InquiryListExportCondition condition = CsvExportType.EXPORT_CSV_INQUIRY.createConditionInstance();
    condition.setSearchCondition(searchCondition);
    this.exportCondition = condition;

    setNextUrl("/download");
    setRequestBean(bean);
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
    return Messages.getString("web.action.back.service.InquiryListExportAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "8109021004";
  }

}
