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
public class EnqueteEditRegisterBasicAction extends EnqueteEditBaseAction {

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

    // 登録処理
    CommunicationService service = ServiceLocator.getCommunicationService(getLoginInfo());
    Enquete enquete = new Enquete();
    enquete.setEnqueteCode(enqueteBean.getEnqueteCode());
    enquete.setEnqueteName(enqueteBean.getEnqueteName());
    //add by cs_yuli 20120516 start 
    enquete.setEnqueteNameEn(enqueteBean.getEnqueteNameEn());
    enquete.setEnqueteNameJp(enqueteBean.getEnqueteNameJp());
    //add by cs_yuli 20120516 end 

    enquete.setEnqueteStartDate(DateUtil.fromString(enqueteBean.getEnqueteStartDate()));
    enquete.setEnqueteEndDate(DateUtil.fromString(enqueteBean.getEnqueteEndDate()));
    enquete.setEnqueteInvestPoint(NumUtil.parse(enqueteBean.getEnqueteInvestPoint()));
    ServiceResult result = service.insertEnquete(enquete);

    // エラー処理
    if (result.hasError()) {
      for (ServiceErrorContent error : result.getServiceErrorList()) {
        if (error.equals(CommunicationServiceErrorContent.DUPLICATED_CODE_ERROR)) {
          addErrorMessage(Message.get(ServiceErrorMessage.DUPLICATED_REGISTER_ERROR,
              Messages.getString("web.action.back.communication.EnqueteEditRegisterBasicAction.0")));
        } else if (error.equals(CommunicationServiceErrorContent.DUPLICATED_PERIOD_ERROR)) {
          addErrorMessage(Message.get(ServiceErrorMessage.DUPLICATED_REGISTER_ERROR,
              Messages.getString("web.action.back.communication.EnqueteEditRegisterBasicAction.1")));
        } else if (error.equals(CommonServiceErrorContent.VALIDATION_ERROR)) {
          return BackActionResult.SERVICE_VALIDATION_ERROR;
        } else {
          return BackActionResult.SERVICE_ERROR;
        }
      }
      this.setRequestBean(enqueteBean);
      return BackActionResult.RESULT_SUCCESS;
    } else {
      addInformationMessage(WebMessage.get(CompleteMessage.REGISTER_COMPLETE,
          Messages.getString("web.action.back.communication.EnqueteEditRegisterBasicAction.2")));
    }

    // nextBean生成
    EnqueteEditBean nextBean = enqueteBean;
    enquete = service.getEnquete(enqueteBean.getEnqueteCode());
    nextBean.setUpdateDatetime(enquete.getUpdatedDatetime());

    // 設問エリアを表示、選択肢エリアを非表示
    nextBean.setEnqueteButtonDisplay(false);
    nextBean.setQuestionsAreaDisplay(true);
    nextBean.setChoicesAreaDisplay(false);

    this.setRequestBean(nextBean);
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.communication.EnqueteEditRegisterBasicAction.3");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "5106012007";
  }

}
