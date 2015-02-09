package jp.co.sint.webshop.web.action.back.service;

import jp.co.sint.webshop.data.dto.InquiryHeader;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.service.InquiryDetailBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

public class InquiryDetailInitAction extends WebBackAction<InquiryDetailBean> {

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
    if (StringUtil.hasValue(inquiryNo())) {
      return true;
    }
    return false;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    InquiryDetailBean bean = new InquiryDetailBean();

    // 検索結果を取得
    CustomerService service = ServiceLocator.getCustomerService(getLoginInfo());

    // 咨询信息
    InquiryHeader inquiryHeader = service.getInquiryHeader(inquiryNo());
    if (inquiryHeader == null) {
      setNextUrl("/app/service/inquiry_list");
      setRequestBean(bean);
      return BackActionResult.RESULT_SUCCESS;
    }

    bean.setInquiryHeaderNo(NumUtil.toString(inquiryHeader.getInquiryHeaderNo()));

    String completeParam = completeParam();
    if (StringUtil.hasValue(completeParam) && completeParam.equals("register")) {
      setNextUrl("/app/service/inquiry_detail/search/" + WebConstantCode.COMPLETE_REGISTER);
    } else {
      setNextUrl("/app/service/inquiry_detail/search");
    }

    setRequestBean(bean);

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * 表示ボタン・入力テキストモード設定
   */
  @Override
  public void prerender() {
    InquiryDetailBean bean = (InquiryDetailBean) getRequestBean();
    bean.setDisplayReplyButton(Permission.SERVICE_COMPLAINT_DATA_UPDATE.isGranted(getLoginInfo()));
    bean.setDisplayEditButton(Permission.SERVICE_COMPLAINT_DATA_UPDATE.isGranted(getLoginInfo()));
    bean.setDisplayDeleteButton(Permission.SERVICE_COMPLAINT_DATA_DELETE.isGranted(getLoginInfo()));
  }

  /**
   * 从URL取得咨询编号。
   * 
   * @return inquiryNo
   */
  private String inquiryNo() {
    String[] tmpArgs = getRequestParameter().getPathArgs();
    if (tmpArgs.length > 0) {
      return tmpArgs[0];
    } else {
      return "";
    }
  }

  /**
   * 从URL取得新规完成的操作。<BR>
   * 
   * @return targetMode
   */
  private String completeParam() {
    String[] tmpArgs = getRequestParameter().getPathArgs();
    if (tmpArgs.length > 1) {
      return tmpArgs[1];
    } else {
      return "";
    }
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.service.InquiryDetailInitAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "8109031001";
  }
}
