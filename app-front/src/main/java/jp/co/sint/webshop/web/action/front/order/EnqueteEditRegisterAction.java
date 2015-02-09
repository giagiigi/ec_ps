package jp.co.sint.webshop.web.action.front.order;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.domain.EnqueteQuestionType;
import jp.co.sint.webshop.data.domain.NecessaryFlg;
import jp.co.sint.webshop.data.dto.Customer;
import jp.co.sint.webshop.data.dto.Enquete;
import jp.co.sint.webshop.data.dto.EnqueteAnswerHeader;
import jp.co.sint.webshop.data.dto.EnqueteReplyChoices;
import jp.co.sint.webshop.data.dto.EnqueteReplyInput;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.communication.EnqueteAnswer;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.CustomerServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.utility.BeanUtil;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.action.front.WebFrontAction;
import jp.co.sint.webshop.web.bean.front.order.EnqueteEditBean;
import jp.co.sint.webshop.web.bean.front.order.EnqueteResultBean;
import jp.co.sint.webshop.web.bean.front.order.EnqueteEditBean.QuestionListBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.front.ServiceErrorMessage;
import jp.co.sint.webshop.web.message.front.ValidationMessage;
import jp.co.sint.webshop.web.message.front.mypage.MypageErrorMessage;
import jp.co.sint.webshop.web.message.front.order.OrderErrorMessage;
import jp.co.sint.webshop.web.text.front.Messages;

/**
 * U2060110:アンケートのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class EnqueteEditRegisterAction extends WebFrontAction<EnqueteEditBean> {

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {

    EnqueteEditBean bean = getBean();

    List<QuestionListBean> questionList = bean.getQuestionList();

    int cnt = 1;

    boolean result = true;
    for (QuestionListBean q : questionList) {
      boolean partialResult = true;

      if (!validateBean(q)) {
        partialResult &= false;
      }
      if (q.getNecessaryFlg().equals(NecessaryFlg.NECESSARY.getValue())) {
        if (q.getEnqueteQuestionType().equals(EnqueteQuestionType.RADIO.getValue())) {
          if (StringUtil.isNullOrEmpty(q.getEnqueteAnswer())) {
            partialResult &= false;
          }
        } else if (q.getEnqueteQuestionType().equals(EnqueteQuestionType.CHECK.getValue())) {
          if (q.getEnqueteAnswerList().size() < 1) {
            partialResult &= false;
          }
        } else {
          if (StringUtil.isNullOrEmpty(q.getEnqueteReply())) {
            partialResult &= false;
          }
        }
        if (!partialResult) {
          addErrorMessage(WebMessage.get(ValidationMessage.REQUIRED_ERROR, Messages.getString("web.action.front.order.EnqueteEditRegisterAction.0") + cnt));
        }
      }
      cnt++;
      result &= partialResult;
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

    EnqueteEditBean bean = getBean();

    CommunicationService service = ServiceLocator.getCommunicationService(getLoginInfo());
    Enquete enquete = service.getEnquete(bean.getEnqueteCode());

    if (enquete == null) {
      bean.setCorrect(false);
      setRequestBean(bean);
      addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
          Messages.getString("web.action.front.order.EnqueteEditRegisterAction.1")));
      return FrontActionResult.RESULT_SUCCESS;
    }

    // アンケート回答データを作成
    EnqueteAnswer answer = new EnqueteAnswer();

    EnqueteAnswerHeader answerHeader = new EnqueteAnswerHeader();
    answerHeader.setCustomerCode(bean.getCustomerCode());
    answerHeader.setEnqueteCode(bean.getEnqueteCode());
    answerHeader.setEnqueteReplyDate(DateUtil.getSysdate());

    List<EnqueteReplyChoices> choiceList = new ArrayList<EnqueteReplyChoices>();
    List<EnqueteReplyInput> reqlyList = new ArrayList<EnqueteReplyInput>();

    for (QuestionListBean q : bean.getQuestionList()) {
      // 単一選択の場合
      if (q.getEnqueteQuestionType().equals(EnqueteQuestionType.RADIO.getValue()) && StringUtil.hasValue(q.getEnqueteAnswer())) {
        EnqueteReplyChoices choice = new EnqueteReplyChoices();
        choice.setEnqueteCode(bean.getEnqueteCode());
        choice.setEnqueteQuestionNo(NumUtil.toLong(q.getEnqueteQuestionNo()));
        choice.setEnqueteChoicesNo(NumUtil.toLong(q.getEnqueteAnswer()));
        choice.setCustomerCode(bean.getCustomerCode());
        choiceList.add(choice);
      }
      // 複数選択の場合
      if (q.getEnqueteQuestionType().equals(EnqueteQuestionType.CHECK.getValue()) && q.getEnqueteAnswerList().size() > 0) {
        for (String a : q.getEnqueteAnswerItem()) {
          EnqueteReplyChoices choice = new EnqueteReplyChoices();
          choice.setEnqueteCode(bean.getEnqueteCode());
          choice.setEnqueteQuestionNo(NumUtil.toLong(q.getEnqueteQuestionNo()));
          choice.setEnqueteChoicesNo(NumUtil.toLong(a));
          choice.setCustomerCode(bean.getCustomerCode());
          choiceList.add(choice);
        }
      }

      // 自由入力の場合
      if (q.getEnqueteQuestionType().equals(EnqueteQuestionType.FREE.getValue()) && StringUtil.hasValue(q.getEnqueteReply())) {
        EnqueteReplyInput reply = new EnqueteReplyInput();
        reply.setEnqueteCode(bean.getEnqueteCode());
        reply.setEnqueteQuestionNo(NumUtil.toLong(q.getEnqueteQuestionNo()));
        reply.setEnqueteReply(q.getEnqueteReply());
        reply.setCustomerCode(bean.getCustomerCode());
        reqlyList.add(reply);
      }
    }

    answer.setAnswerHeader(answerHeader);
    answer.setAnswerChoices(choiceList);
    answer.setAnswerInputs(reqlyList);
    answer.setHashValue(bean.getHashvalue());
    answer.setCustomer(getLoginInfo().isCustomer());

    // データベース更新処理
    ServiceResult enqueteResult = service.postEnqueteAnswer(answer);

    // アンケート更新時エラー
    if (enqueteResult.hasError()) {
      for (ServiceErrorContent result : enqueteResult.getServiceErrorList()) {
        if (result.equals(CommonServiceErrorContent.NO_DATA_ERROR)) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
              Messages.getString("web.action.front.order.EnqueteEditRegisterAction.2")));
          bean.setCorrect(false);
          setRequestBean(bean);
          return FrontActionResult.RESULT_SUCCESS;
        } else if (result.equals(CustomerServiceErrorContent.POINT_OVERFLOW_ERROR)) {
          Length len = BeanUtil.getAnnotation(Customer.class, "temporaryPoint", Length.class);
          String maximum = StringUtil.times("9", len.value());
          addErrorMessage(WebMessage.get(MypageErrorMessage.POINT_OVERFLOW_ERROR, maximum));
          bean.setCorrect(false);
          setRequestBean(bean);
          return FrontActionResult.RESULT_SUCCESS;
        } else if (result.equals(CommonServiceErrorContent.VALIDATION_ERROR)) {
          addErrorMessage(WebMessage.get(OrderErrorMessage.ENQUETE_REGISTER_FAILED));
          EnqueteEditBean nextBean = new EnqueteEditBean();
          nextBean.setCorrect(false);
          setRequestBean(nextBean);
          return FrontActionResult.RESULT_SUCCESS;
        } else {
          return FrontActionResult.SERVICE_ERROR;
        }
      }
    }

    EnqueteResultBean nextBean = new EnqueteResultBean();
    nextBean.setEnqueteInvestPoint(NumUtil.toString(enquete.getEnqueteInvestPoint()));

    setRequestBean(nextBean);
    setNextUrl("/app/order/enquete_result/init");

    return FrontActionResult.RESULT_SUCCESS;
  }
}
