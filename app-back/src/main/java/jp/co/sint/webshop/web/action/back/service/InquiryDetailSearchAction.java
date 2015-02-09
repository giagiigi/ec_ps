package jp.co.sint.webshop.web.action.back.service;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.data.domain.IbObType;
import jp.co.sint.webshop.data.domain.InquiryStatus;
import jp.co.sint.webshop.data.domain.InquiryWay;
import jp.co.sint.webshop.data.dto.InquiryDetail;
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
import jp.co.sint.webshop.web.bean.back.service.InquiryDetailBean;
import jp.co.sint.webshop.web.bean.back.service.InquiryDetailBean.DetailSearchedBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.CompleteMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerUtil;

public class InquiryDetailSearchAction extends WebBackAction<InquiryDetailBean> {

  private InquirySearchCondition condition;

  protected InquirySearchCondition getCondition() {
    return PagerUtil.createSearchCondition(getRequestParameter(), condition);
  }

  protected InquirySearchCondition getSearchCondition() {
    return this.condition;
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
    return true;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    InquiryDetailBean bean = getBean();

    // 検索結果リストを取得
    CustomerService service = ServiceLocator.getCustomerService(getLoginInfo());

    // 咨询信息
    InquirySearchInfo inquiryInfo = service.getInquiryInfo(bean.getInquiryHeaderNo());
    if (inquiryInfo == null) {
      return BackActionResult.SERVICE_ERROR;
    }

    bean.setInquiryHeaderNo(inquiryInfo.getInquiryHeaderNo());
    bean.setAcceptDate(inquiryInfo.getAcceptDatetime());
    bean.setCustomerName(inquiryInfo.getCustomerName());
    bean.setCustomerCode(inquiryInfo.getCustomerCode());
    bean.setMobile(inquiryInfo.getMobileNumber());
    bean.setLargeCategory(inquiryInfo.getLargeCategory());
    bean.setSmallCategory(inquiryInfo.getSmallCategory());
    CodeAttribute inquiryWay = InquiryWay.fromValue(inquiryInfo.getInquiryWay());
    if (inquiryWay != null) {
      bean.setInquiryWay(inquiryWay.getName());
    }
    bean.setInquirySubject(inquiryInfo.getInquirySubject());
    CodeAttribute inquiryStatus = InquiryStatus.fromValue(inquiryInfo.getInquiryStatus());
    if (inquiryStatus != null) {
      bean.setInquiryStatus(inquiryStatus.getName());
      bean.setReplyInquiryStatus(inquiryStatus.getValue());
    }
    CodeAttribute ibObType = IbObType.fromValue(inquiryInfo.getIbObType());
    if (ibObType != null) {
      bean.setIbObType(ibObType.getName());
    }
    bean.setCommodityCode(inquiryInfo.getCommodityCode());

    condition = new InquirySearchCondition();
    condition.setSearchInquiryHeaderNo(bean.getInquiryHeaderNo());
    condition = getCondition();

    // 咨询详细信息
    SearchResult<InquiryDetail> result = service.getInquiryDetailList(condition);

    // 跳转最后页处理
    if (moveLastPage()) {
      int currentPage = result.getRowCount() / result.getPageSize();
      if (result.getRowCount() % result.getPageSize() > 0) {
        currentPage += 1;
      }
      condition.setCurrentPage(currentPage);
      result = service.getInquiryDetailList(condition);
    }

    bean.setPagerValue(PagerUtil.createValue(result));

    // 結果一覧を作成
    List<InquiryDetail> resultList = result.getRows();
    List<DetailSearchedBean> inquiryDetailList = new ArrayList<DetailSearchedBean>();
    int rowNo = (condition.getCurrentPage() - 1) * condition.getPageSize();
    for (InquiryDetail inquiryDetail : resultList) {
      DetailSearchedBean detailSearchedBean = new DetailSearchedBean();
      detailSearchedBean.setInquiryHeaderNo(NumUtil.toString(inquiryDetail.getInquiryHeaderNo()));
      detailSearchedBean.setInquiryDetailNo(NumUtil.toString(inquiryDetail.getInquiryDetailNo()));
      detailSearchedBean.setAcceptUpdate(DateUtil.toDateTimeString(inquiryDetail.getAcceptDatetime()));
      detailSearchedBean.setPersonInChargeName(inquiryDetail.getPersonInChargeName());
      detailSearchedBean.setPersonInChargeNo(inquiryDetail.getPersonInChargeNo());
      inquiryStatus = InquiryStatus.fromValue(inquiryDetail.getInquiryStatus());
      if (inquiryStatus != null) {
        detailSearchedBean.setInquiryStatus(inquiryStatus.getName());
      }
      detailSearchedBean.setInquiryContents(inquiryDetail.getInquiryContents());
      rowNo++;
      if (rowNo == 1) {
        detailSearchedBean.setReplyDetail(false);
      } else {
        detailSearchedBean.setReplyDetail(true);
      }
      detailSearchedBean.setRowNo(NumUtil.toString(rowNo));

      inquiryDetailList.add(detailSearchedBean);
    }
    bean.setInquiryDetailList(inquiryDetailList);

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
   * 从URL取得完成的操作。
   * 
   * @return targetMode
   */
  private boolean moveLastPage() {
    String[] tmpArgs = getRequestParameter().getPathArgs();
    return tmpArgs.length > 1;
  }

  /**
   * 処理完了パラメータがあれば、処理完了メッセージをセットします。
   * 
   * @param completeParam
   */
  private void setCompleteMessage() {
    String completeParam = completeParam();
    if (StringUtil.hasValue(completeParam)) {
      if (completeParam.equals("reply")) {
        addInformationMessage(WebMessage.get(CompleteMessage.REGISTER_COMPLETE, Messages
            .getString("web.action.back.service.InquiryDetailSearchAction.1")));
      } else if (completeParam.equals("delete")) {
        addInformationMessage(WebMessage.get(CompleteMessage.DELETE_COMPLETE, Messages
            .getString("web.action.back.service.InquiryDetailSearchAction.1")));
      } else if (completeParam.equals("register")) {
        addInformationMessage(WebMessage.get(CompleteMessage.REGISTER_COMPLETE, Messages
            .getString("web.action.back.service.InquiryDetailSearchAction.1")));
      }
    }
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.service.InquiryDetailSearchAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "8109031002";
  }
}
