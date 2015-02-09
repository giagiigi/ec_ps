package jp.co.sint.webshop.web.action.back.service;

import java.text.MessageFormat;

import jp.co.sint.webshop.data.dto.InquiryHeader;
import jp.co.sint.webshop.message.Message;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.service.InquiryListBean;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.DisplayTransition;

public class InquiryListMoveAction extends WebBackAction<InquiryListBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return true;
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    if (StringUtil.hasValue(targetPage())) {
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
    
    // 検索結果を取得
    CustomerService service = ServiceLocator.getCustomerService(getLoginInfo());

    
    
    String targetPage = targetPage();

    if (targetPage.equals("member")) {
      setNextUrl("/app/service/member_info/init");
    } else if (targetPage.equals("detail")) {
      // 咨询信息
      InquiryHeader inquiryHeader = service.getInquiryHeader(inquiryNo());
      if (inquiryHeader == null) {
        addErrorMessage(Message.get(ServiceErrorMessage.NO_DATA_ERROR, MessageFormat.format(Messages
            .getString("web.action.back.service.InquiryDetailReplyAction.0"), inquiryNo())));
        setRequestBean(getBean());
        return BackActionResult.RESULT_SUCCESS;
      } 
      setNextUrl("/app/service/inquiry_detail/init/" + inquiryNo());
    }
    
    // 前画面情報設定
    DisplayTransition.add(getBean(), "/app/service/inquiry_list/search_back", getSessionContainer());

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * 从URL取得迁移画面。<BR>
   * 
   * @return targetPage
   */
  private String targetPage() {
    String[] tmpArgs = getRequestParameter().getPathArgs();
    if (tmpArgs.length > 0) {
      return tmpArgs[0];
    } else {
      return "";
    }
  }
  
  /**
   * 从URL取得咨询编号。<BR>
   * 
   * @return targetPage
   */
  private String inquiryNo() {
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
    return Messages.getString("web.action.back.service.InquiryListMoveAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "8109021005";
  }
}
