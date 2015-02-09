package jp.co.sint.webshop.web.action.back.service;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.data.domain.IbObType;
import jp.co.sint.webshop.data.domain.InquiryStatus;
import jp.co.sint.webshop.data.domain.InquiryWay;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.customer.InquirySearchCondition;
import jp.co.sint.webshop.service.customer.InquirySearchInfo;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.service.InquiryListBean;
import jp.co.sint.webshop.web.bean.back.service.InquiryListBean.InquirySearchedBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.message.back.CompleteMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerUtil;

public class InquiryListSearchAction extends WebBackAction<InquiryListBean> {

  private InquirySearchCondition condition;

  protected InquirySearchCondition getCondition() {
    return PagerUtil.createSearchCondition(getRequestParameter(), condition);
  }

  protected InquirySearchCondition getSearchCondition() {
    return this.condition;
  }

  /**
   * 初期処理を実行します
   */
  @Override
  public void init() {
  }

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return getLoginInfo().hasPermission(Permission.SERVICE_COMPLAINT_DATA_READ);
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
              .getString("web.action.back.service.InquiryListSearchAction.1")));
          result = false;
        }
      }

      if (StringUtil.hasValueAllOf(bean.getSearchAcceptUpdateFrom(), bean.getSearchAcceptUpdateTo())) {
        if (DateUtil.fromString(getBean().getSearchAcceptUpdateFrom()).after(
            DateUtil.fromString(getBean().getSearchAcceptUpdateTo()))) {
          addErrorMessage(WebMessage.get(ActionErrorMessage.PERIOD_ERROR_WITH_PARAM, Messages
              .getString("web.action.back.service.InquiryListSearchAction.2")));
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

    condition = new InquirySearchCondition();
    condition.setSearchMobile(bean.getSearchMobile());
    condition.setSearchCustomerName(bean.getSearchCustomerName());
    condition.setSearchCustomerCode(bean.getSearchCustomerCode());
    condition.setSearchPersonInChargeName(bean.getSearchPersonInChargeName());
    condition.setSearchPersonInChargeNo(bean.getSearchPersonInChargeNo());
    condition.setSearchAcceptDateFrom(bean.getSearchAcceptDateFrom());
    condition.setSearchAcceptDateTo(bean.getSearchAcceptDateTo());
    condition.setSearchAcceptUpdateFrom(bean.getSearchAcceptUpdateFrom());
    condition.setSearchAcceptUpdateTo(bean.getSearchAcceptUpdateTo());
    condition.setSearchInquiryStatus(bean.getSearchInquiryStatus());
    condition.setSearchLargeCategory(bean.getSearchLargeCategory());
    condition.setSearchSmallCategory(bean.getSearchSmallCategory());
    condition.setSearchInquiryWay(bean.getSearchInquiryWay());
    condition.setSearchIbObType(bean.getSearchIbObType());
    condition.setSearchInquirySubject(bean.getSearchInquirySubject());
    condition = getCondition();

    // 検索結果リストを取得
    CustomerService service = ServiceLocator.getCustomerService(getLoginInfo());
    SearchResult<InquirySearchInfo> result = service.getInquiryList(condition);
    SearchResult<InquirySearchInfo> countResult = service.getInquiryCountList(condition);

    // 検索結果0件チェック
    if (result.getRowCount() == 0) {
      addWarningMessage(WebMessage.get(ActionErrorMessage.SEARCH_RESULT_NOT_FOUND));
    }

    // オーバーフローチェック
    if (result.isOverflow()) {
      this.addWarningMessage(WebMessage.get(ActionErrorMessage.SEARCH_RESULT_OVERFLOW, NumUtil.formatNumber(""
          + result.getRowCount()), "" + NumUtil.formatNumber("" + condition.getMaxFetchSize())));
    }

    bean.setPagerValue(PagerUtil.createValue(result));

    // 結果一覧を作成
    List<InquirySearchInfo> resultList = result.getRows();
    List<InquirySearchedBean> inquiryList = new ArrayList<InquirySearchedBean>();
    for (InquirySearchInfo inquirySearchInfo : resultList) {
      InquirySearchedBean inquirySearchedBean = new InquirySearchedBean();
      inquirySearchedBean.setInquiryHeaderNo(inquirySearchInfo.getInquiryHeaderNo());
      inquirySearchedBean.setAcceptDate(DateUtil.toDateString(DateUtil.fromString(inquirySearchInfo.getAcceptDatetime())));
      inquirySearchedBean.setAcceptDateTime(DateUtil.getHHmmss(DateUtil.fromString(inquirySearchInfo.getAcceptDatetime(), true)));
      inquirySearchedBean.setCustomerName(inquirySearchInfo.getCustomerName());
      inquirySearchedBean.setCustomerCode(inquirySearchInfo.getCustomerCode());
      inquirySearchedBean.setMobile(inquirySearchInfo.getMobileNumber());
      inquirySearchedBean.setLargeCategory(inquirySearchInfo.getLargeCategory());
      inquirySearchedBean.setSmallCategory(inquirySearchInfo.getSmallCategory());
      CodeAttribute inquiryWay = InquiryWay.fromValue(inquirySearchInfo.getInquiryWay());
      if (inquiryWay != null) {
        inquirySearchedBean.setInquiryWay(inquiryWay.getName());
      }
      inquirySearchedBean.setInquirySubject(inquirySearchInfo.getInquirySubject());
      inquirySearchedBean.setPersonInChargeName(inquirySearchInfo.getPersonInChargeName());
      inquirySearchedBean.setPersonInChargeNo(inquirySearchInfo.getPersonInChargeNo());
      inquirySearchedBean.setAcceptUpdate(DateUtil.toDateString(DateUtil.fromString(inquirySearchInfo.getAcceptUpdatetime())));
      inquirySearchedBean.setAcceptUpdateTime(DateUtil.getHHmmss(DateUtil.fromString(inquirySearchInfo.getAcceptUpdatetime(), true)));
      CodeAttribute inquiryStatus = InquiryStatus.fromValue(inquirySearchInfo.getInquiryStatus());
      if (inquiryStatus != null) {
        inquirySearchedBean.setInquiryStatus(inquiryStatus.getName());
      }
      CodeAttribute ibObType = IbObType.fromValue(inquirySearchInfo.getIbObType());
      if (ibObType != null) {
        inquirySearchedBean.setIbObType(ibObType.getName());
      }
      
      inquiryList.add(inquirySearchedBean);
    }
    bean.setInquiryList(inquiryList);

    String countMessage = "";
    for (InquirySearchInfo inquirySearchInfo : countResult.getRows()) {
      CodeAttribute inquiryStatus = InquiryStatus.fromValue(inquirySearchInfo.getInquiryStatus());
      if (inquiryStatus != null) {
        if (StringUtil.hasValue(countMessage)) {
          countMessage += ", ";
        }
        countMessage += inquirySearchInfo.getInquiryStatusCount()
            + Messages.getString("web.action.back.service.InquiryListSearchAction.3") + inquiryStatus.getName();
      }
    }
    bean.setCountMessage(countMessage);

    setRequestBean(bean);

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * 表示ボタン・入力テキストモード設定
   */
  @Override
  public void prerender() {
    setCompleteMessage();
  }

  /**
   * 从URL取得完成的操作。
   * 
   * @return targetMode
   */
  private String completeParam() {
    String[] tmpArgs = getRequestParameter().getPathArgs();
    if (tmpArgs.length > 0) {
      return tmpArgs[0];
    } else {
      return "";
    }
  }

  /**
   * 処理完了パラメータがあれば、処理完了メッセージをセットします。
   * 
   * @param completeParam
   */
  private void setCompleteMessage() {
    String completeParam = completeParam();
    if (StringUtil.hasValue(completeParam)) {
      if (completeParam.equals("delete")) {
        addInformationMessage(WebMessage.get(CompleteMessage.DELETE_COMPLETE, Messages
            .getString("web.action.back.service.InquiryListSearchAction.4")));
      }
    }
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.service.InquiryListSearchAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "8109021002";
  }
}
