package jp.co.sint.webshop.web.action.back.communication;

import jp.co.sint.webshop.data.dto.Enquete;
import jp.co.sint.webshop.message.Message;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.CommunicationServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.communication.EnqueteEditBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.message.back.CompleteMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1060120:アンケートマスタのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class EnqueteEditUpdateBasicAction extends EnqueteEditBaseAction {

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    boolean result = false;
    EnqueteEditBean validateBean = getBean();
    result = validateBean(validateBean);
    if (!result) {
      return result;
    }
    // 日付の大小関係チェック
    result = StringUtil.isCorrectRange(validateBean.getEnqueteStartDate(), validateBean.getEnqueteEndDate());
    if (!result) {
      addErrorMessage(WebMessage.get(ActionErrorMessage.PERIOD_ERROR));
      return result;
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

    // 入力項目取得
    EnqueteEditBean enqueteBean = getBean();

    String[] urlParam = getRequestParameter().getPathArgs();
    if (urlParam.length < 1) {
      addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
          Messages.getString("web.action.back.communication.EnqueteEditUpdateBasicAction.0")));
      this.setRequestBean(enqueteBean);
      return BackActionResult.RESULT_SUCCESS;
    }

    // 更新処理
    Enquete enquete = new Enquete();
    enquete.setEnqueteCode(urlParam[0]);
    enquete.setEnqueteName(enqueteBean.getEnqueteName());
    
    //	add by cs_yuli 20120516 start 
    enquete.setEnqueteNameEn(enqueteBean.getEnqueteNameEn());
    enquete.setEnqueteNameJp(enqueteBean.getEnqueteNameJp());
    //	add by cs_yuli 20120516 end

    enquete.setEnqueteStartDate(DateUtil.fromString(enqueteBean.getEnqueteStartDate()));
    enquete.setEnqueteEndDate(DateUtil.fromString(enqueteBean.getEnqueteEndDate()));
    enquete.setEnqueteInvestPoint(NumUtil.parse(enqueteBean.getEnqueteInvestPoint()));

    CommunicationService service = ServiceLocator.getCommunicationService(getLoginInfo());
    Enquete result = service.getEnquete(urlParam[0]);

    if (result == null) {
      addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
          Messages.getString("web.action.back.communication.EnqueteEditUpdateBasicAction.0")));
      setRequestBean(enqueteBean);
      return BackActionResult.RESULT_SUCCESS;
    }

    enquete.setOrmRowid(result.getOrmRowid());
    enquete.setCreatedUser(result.getCreatedUser());
    enquete.setCreatedDatetime(result.getCreatedDatetime());
    enquete.setUpdatedUser(result.getUpdatedUser());
    enquete.setUpdatedDatetime(enqueteBean.getUpdateDatetime());

    ServiceResult serviceResult = service.updateEnquete(enquete);

    if (serviceResult.hasError()) {
      setRequestBean(enqueteBean);

      for (ServiceErrorContent error : serviceResult.getServiceErrorList()) {
        if (error.equals(CommunicationServiceErrorContent.DUPLICATED_PERIOD_ERROR)) {
          addErrorMessage(Message.get(ServiceErrorMessage.DUPLICATED_REGISTER_ERROR,
              Messages.getString("web.action.back.communication.EnqueteEditUpdateBasicAction.1")));
          return BackActionResult.RESULT_SUCCESS;
        } else if (error.equals(CommonServiceErrorContent.VALIDATION_ERROR)) {
          return BackActionResult.SERVICE_VALIDATION_ERROR;
        }
      }

      return BackActionResult.SERVICE_ERROR;
    }
    addInformationMessage(WebMessage.get(CompleteMessage.UPDATE_COMPLETE,
        Messages.getString("web.action.back.communication.EnqueteEditUpdateBasicAction.0")));

    // 再表示
    EnqueteEditBean nextBean = searchEnquete(urlParam[0]);

    // ボタン表示切替、設問エリアを表示
    nextBean.setQuestionButtonDisplay(false);
    nextBean.setQuestionsAreaDisplay(true);

    this.setRequestBean(nextBean);
    return BackActionResult.RESULT_SUCCESS;

  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.communication.EnqueteEditUpdateBasicAction.2");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "5106012012";
  }

}
