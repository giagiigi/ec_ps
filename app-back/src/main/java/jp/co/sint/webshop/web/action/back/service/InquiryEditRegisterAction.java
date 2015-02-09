package jp.co.sint.webshop.web.action.back.service;

import jp.co.sint.webshop.data.dto.InquiryDetail;
import jp.co.sint.webshop.data.dto.InquiryHeader;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.customer.InquiryInfo;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.service.InquiryEditBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

public class InquiryEditRegisterAction extends WebBackAction<InquiryEditBean> {

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
    InquiryEditBean bean = getBean();
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

    InquiryEditBean bean = getBean();

    CustomerService service = ServiceLocator.getCustomerService(getLoginInfo());

    InquiryInfo inquiryInfo = new InquiryInfo();
    InquiryHeader inquiryHeader = new InquiryHeader();
    InquiryDetail inquiryDetail = new InquiryDetail();

    // Header
    inquiryHeader.setCustomerCode(bean.getCustomerCode());
    inquiryHeader.setCustomerName(bean.getCustomerName());
    inquiryHeader.setLargeCategory(bean.getLargeCategory());
    inquiryHeader.setSmallCategory(bean.getSmallCategory());
    inquiryHeader.setInquiryWay(NumUtil.toLong(bean.getInquiryWay(), null));
    inquiryHeader.setInquirySubject(bean.getInquirySubject());
    inquiryHeader.setIbObType(NumUtil.toLong(bean.getIbObType(), null));
    inquiryHeader.setCommodityCode(bean.getCommodityCode());
    inquiryInfo.setInquiryHeader(inquiryHeader);

    // Detail
    inquiryDetail.setInquiryContents(bean.getInquiryContents());
    inquiryDetail.setInquiryStatus(NumUtil.toLong(bean.getInquiryStatus(), null));
    inquiryInfo.setInquiryDetail(inquiryDetail);

    // データベース更新処理
    ServiceResult customerResult = service.insertInquiry(inquiryInfo);

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

    setNextUrl("/app/service/inquiry_detail/init/" + inquiryInfo.getInquiryHeader().getInquiryHeaderNo() + "/"
        + WebConstantCode.COMPLETE_REGISTER);
    
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.service.InquiryEditRegisterAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "8109041002";
  }

}
