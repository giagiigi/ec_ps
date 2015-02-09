package jp.co.sint.webshop.web.action.back.service;

import java.text.MessageFormat;

import jp.co.sint.webshop.data.dto.InquiryDetail;
import jp.co.sint.webshop.message.Message;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.customer.InquirySearchInfo;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.service.InquiryDetailBean;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

public class InquiryDetailReplyAction extends WebBackAction<InquiryDetailBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return getLoginInfo().hasPermission(Permission.SERVICE_COMPLAINT_DATA_UPDATE);
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    boolean result = true;
    InquiryDetailBean bean = getBean();
    result = validateBean(bean);
    return result;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    InquiryDetailBean bean = getBean();

    CustomerService service = ServiceLocator.getCustomerService(getLoginInfo());

    // 咨询信息
    InquirySearchInfo inquiryInfo = service.getInquiryInfo(bean.getInquiryHeaderNo());
    if (inquiryInfo == null) {
        addErrorMessage(Message.get(ServiceErrorMessage.NO_DATA_ERROR, MessageFormat.format(Messages
            .getString("web.action.back.service.InquiryDetailReplyAction.1"), bean.getInquiryHeaderNo())));
        setRequestBean(bean);
        return BackActionResult.RESULT_SUCCESS;
    }
    
    InquiryDetail inquiryDetail = new InquiryDetail();

    // Detail
    inquiryDetail.setInquiryHeaderNo(NumUtil.toLong(bean.getInquiryHeaderNo(), null));
    inquiryDetail.setInquiryContents(bean.getReplyInquiryContents());
    inquiryDetail.setInquiryStatus(NumUtil.toLong(bean.getReplyInquiryStatus(), null));

    // データベース更新処理
    ServiceResult customerResult = service.insertInquiryDetail(inquiryDetail);

    // 情報更新時エラー
    if (customerResult.hasError()) {
      for (ServiceErrorContent result : customerResult.getServiceErrorList()) {
        if (result.equals(CommonServiceErrorContent.VALIDATION_ERROR)) {
          // service内部Validationエラー
          return BackActionResult.SERVICE_VALIDATION_ERROR;
        } else {
          return BackActionResult.SERVICE_ERROR;
        }
      }
      setNextUrl(null);
    }

    if (bean.isReplyLastPage()) {
      setNextUrl("/app/service/inquiry_detail/search/reply/last");
    } else {
      setNextUrl("/app/service/inquiry_detail/search/reply");
    }
    
    bean.setReplyInquiryContents(StringUtil.EMPTY);
    bean.setReplyLastPage(false);
    
    setRequestBean(bean);

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.service.InquiryDetailReplyAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "8109031004";
  }

}
