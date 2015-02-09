package jp.co.sint.webshop.web.action.front.order;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.data.domain.EnqueteQuestionType;
import jp.co.sint.webshop.data.domain.LanguageCode;
import jp.co.sint.webshop.data.dto.Enquete;
import jp.co.sint.webshop.data.dto.EnqueteChoice;
import jp.co.sint.webshop.data.dto.EnqueteQuestion;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.action.front.WebFrontAction;
import jp.co.sint.webshop.web.bean.front.order.EnqueteEditBean;
import jp.co.sint.webshop.web.bean.front.order.EnqueteEditBean.QuestionListBean;
import jp.co.sint.webshop.web.login.front.FrontLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.front.ServiceErrorMessage;
import jp.co.sint.webshop.web.text.front.Messages;

/**
 * U2060110:アンケートのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class EnqueteEditInitAction extends WebFrontAction<EnqueteEditBean> {

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
    // セッションから顧客コードを取得
    String customerCode = "";
    FrontLoginInfo login = getLoginInfo();
    customerCode = login.getCustomerCode();

    // 注文完了画面からの遷移かどうかチェック
//    EnqueteEditBean tempBean = (EnqueteEditBean) getSessionContainer().getTempBean();
//    if (tempBean == null || StringUtil.isNullOrEmpty(tempBean.getTransitionFromOrder())
//        || !tempBean.getTransitionFromOrder().equals("fromOrder")) {
//      setNextUrl("/app/common/index");
//
//      return FrontActionResult.RESULT_SUCCESS;
//    }

    EnqueteEditBean bean = new EnqueteEditBean();
    bean.setCustomerCode(customerCode);

    CommunicationService service = ServiceLocator.getCommunicationService(login);

    Enquete enquete = service.getCurrentEnquete();

    boolean correct = true;
    
    //20120521 tuxinwei add start
    String currentLanguageCode = DIContainer.getLocaleContext().getCurrentLanguageCode();
    //20120521 tuxinwei add end
    // アンケートが存在し、未回答である
    if (enquete != null && !service.isAlreadyAnswerEnquete(customerCode, enquete.getEnqueteCode())) {
      bean.setEnqueteCode(enquete.getEnqueteCode());
      bean.setEnqueteInvestPoint(NumUtil.toString(enquete.getEnqueteInvestPoint()));
      
      //20120521 tuxinwei update start
      if(currentLanguageCode.equals(LanguageCode.Zh_Cn.getValue())){
        bean.setEnqueteName(enquete.getEnqueteName());
      } else if(currentLanguageCode.equals(LanguageCode.Ja_Jp.getValue())){
        bean.setEnqueteName(enquete.getEnqueteNameJp());
      } else if(currentLanguageCode.equals(LanguageCode.En_Us.getValue())){
        bean.setEnqueteName(enquete.getEnqueteNameEn());
      }
      //20120521 tuxinwei update end

      List<EnqueteQuestion> questionList = service.getEnqueteQuestionList(enquete.getEnqueteCode());
      // 設問が存在する
      correct &= !questionList.isEmpty();
      List<QuestionListBean> questionBeanList = new ArrayList<QuestionListBean>();

      for (EnqueteQuestion q : questionList) {
        QuestionListBean question = new QuestionListBean();
        question.setEnqueteQuestionNo(NumUtil.toString(q.getEnqueteQuestionNo()));
        //20120521 tuxinwei update start
        if(currentLanguageCode.equals(LanguageCode.Zh_Cn.getValue())){
          question.setEnqueteQuestionContent(q.getEnqueteQuestionContent());
        } else if(currentLanguageCode.equals(LanguageCode.Ja_Jp.getValue())){
          question.setEnqueteQuestionContent(q.getEnqueteQuestionContentJp());
        } else if(currentLanguageCode.equals(LanguageCode.En_Us.getValue())){
          question.setEnqueteQuestionContent(q.getEnqueteQuestionContentEn());
        }
        //20120521 tuxinwei update end
        question.setEnqueteQuestionType(NumUtil.toString(q.getEnqueteQuestionType()));
        question.setNecessaryFlg(NumUtil.toString(q.getNecessaryFlg()));

        // テキスト名
        question.setQuestionTextName("Question" + q.getEnqueteQuestionNo());

        // 多選式、単一選択式のとき
        if (!q.getEnqueteQuestionType().equals(NumUtil.toLong(EnqueteQuestionType.FREE.getValue()))) {
          List<CodeAttribute> answerList = new ArrayList<CodeAttribute>();
          setDefaultSelect(answerList, q.getEnqueteQuestionType());
          // 選択肢があることを確認する
          List<EnqueteChoice> choicesList = service.getEnqueteChoiceList(q.getEnqueteCode(), q.getEnqueteQuestionNo());
          correct &= !choicesList.isEmpty();

          for (EnqueteChoice c : choicesList) {
            //20120521 tuxinwei update start
            if(currentLanguageCode.equals(LanguageCode.Zh_Cn.getValue())){
              answerList.add(new NameValue(c.getEnqueteChoices(), Long.toString(c.getEnqueteChoicesNo())));
            } else if(currentLanguageCode.equals(LanguageCode.Ja_Jp.getValue())){
              answerList.add(new NameValue(c.getEnqueteChoicesJp(), Long.toString(c.getEnqueteChoicesNo())));
            } else if(currentLanguageCode.equals(LanguageCode.En_Us.getValue())){
              answerList.add(new NameValue(c.getEnqueteChoicesEn(), Long.toString(c.getEnqueteChoicesNo())));
            }
            //20120521 tuxinwei update end
          }
          question.setChoicesList(answerList);
        }
        questionBeanList.add(question);
      }
      bean.setQuestionList(questionBeanList);
      bean.setHashvalue(service.calculateEnqueteHash(enquete.getEnqueteCode()));
    } else {
      correct = false;
    }
    bean.setCorrect(correct);

    if (!correct) {
      if (enquete == null) {
        addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
            Messages.getString("web.action.front.order.EnqueteEditInitAction.0")));
      } else {
        addErrorMessage(WebMessage.get(ServiceErrorMessage.DUPLICATED_REGISTER_ERROR,
            Messages.getString("web.action.front.order.EnqueteEditInitAction.0")));
      }
    }

    setRequestBean(bean);

    return FrontActionResult.RESULT_SUCCESS;
  }

  /**
   * 単一選択肢の場合、デフォルト値を設定
   * 
   * @param attributeChoiceList
   *          選択肢
   * @param questionType
   *          質問の種類
   */
  public void setDefaultSelect(List<CodeAttribute> attributeChoiceList, Long questionType) {
    if (questionType.equals(Long.parseLong(EnqueteQuestionType.RADIO.getValue()))) {
      attributeChoiceList.add(new NameValue(Messages.getString(
      "web.action.front.order.EnqueteEditInitAction.1"), ""));
    }
  }

  /**
   * beanのcreateAttributeを実行するかどうかの設定
   * 
   * @return true - 実行する false-実行しない
   */
  public boolean isCallCreateAttribute() {
    return false;
  }
}
