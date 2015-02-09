package jp.co.sint.webshop.web.action.back.communication;

import java.util.List;

import jp.co.sint.webshop.data.domain.EnqueteQuestionType;
import jp.co.sint.webshop.data.domain.NecessaryFlg;
import jp.co.sint.webshop.data.dto.Enquete;
import jp.co.sint.webshop.data.dto.EnqueteChoice;
import jp.co.sint.webshop.data.dto.EnqueteQuestion;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.communication.EnqueteEditBean;
import jp.co.sint.webshop.web.bean.back.communication.EnqueteEditBean.QuestionListBean;
import jp.co.sint.webshop.web.bean.back.communication.EnqueteEditBean.QuestionListBean.ChoicesListBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1060120:アンケートマスタのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public abstract class EnqueteEditBaseAction extends WebBackAction<EnqueteEditBean> {

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public abstract WebActionResult callService();

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    BackLoginInfo login = getLoginInfo();

    return Permission.ENQUETE_READ_SITE.isGranted(login) || Permission.ENQUETE_UPDATE_SITE.isGranted(login);
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
   * アンケートマスタを再表示します(選択肢なし)
   * 
   * @param enqueteCode
   * @return searchEnquete(enqueteCode, "")
   */
  public EnqueteEditBean searchEnquete(String enqueteCode) {
    return searchEnquete(enqueteCode, "");
  }

  /**
   * アンケートマスタを再表示します(選択肢あり)
   * 
   * @param enqueteCode
   * @param questionNo
   * @return bean
   */
  public EnqueteEditBean searchEnquete(String enqueteCode, String questionNo) {
    // 再表示
    CommunicationService service = ServiceLocator.getCommunicationService(getLoginInfo());
    EnqueteEditBean bean = new EnqueteEditBean();

    Enquete enquete = service.getEnquete(enqueteCode);
    bean.setEnqueteCode(enquete.getEnqueteCode());
    bean.setEnqueteName(enquete.getEnqueteName());
	// add by cs_yuli 20120516 start
	bean.setEnqueteNameEn(enquete.getEnqueteNameEn());
	bean.setEnqueteNameJp(enquete.getEnqueteNameJp());
	// add by cs_yuli 20120516 end
    bean.setEnqueteStartDate(DateUtil.toDateString(enquete.getEnqueteStartDate()));
    bean.setEnqueteEndDate(DateUtil.toDateString(enquete.getEnqueteEndDate()));
    bean.setEnqueteInvestPoint(NumUtil.toString(enquete.getEnqueteInvestPoint()));
    bean.setUpdateDatetime(enquete.getUpdatedDatetime());

    Long answerCount = service.getEnqueteAnswerCount(enqueteCode);

    if (Permission.ENQUETE_UPDATE_SITE.isDenied(getLoginInfo())) {
      // 更新権限がなかったらreadonly
      bean.setMainEditMode(WebConstantCode.DISPLAY_READONLY);
      bean.setDetailEditMode(WebConstantCode.DISPLAY_READONLY);
    } else {
      bean.setMainEditMode(WebConstantCode.DISPLAY_EDIT);
      // 回答者がいたら設問情報修正不可
      if (answerCount > 0) {
        bean.setDetailEditMode(WebConstantCode.DISPLAY_READONLY);
      } else {
        bean.setDetailEditMode(WebConstantCode.DISPLAY_EDIT);
      }
    }

    List<EnqueteQuestion> questionList = service.getEnqueteQuestionList(enqueteCode);
    boolean choiceEditButtonDisplay = false;
    for (EnqueteQuestion q : questionList) {
      QuestionListBean questionBean = new QuestionListBean();
      questionBean.setEnqueteQuestionNo(Long.toString(q.getEnqueteQuestionNo()));
      questionBean.setQuestionDisplayOrder(Long.toString(q.getDisplayOrder()));
      questionBean.setEnqueteQuestionContent(q.getEnqueteQuestionContent());
      //add by cs_yuli 20120516 start 
      questionBean.setEnqueteQuestionContentEn(q.getEnqueteQuestionContentEn());
      questionBean.setEnqueteQuestionContentJp(q.getEnqueteQuestionContentJp());
      //add by cs_yuli 20120516 end
      questionBean.setEnqueteQuestionType(EnqueteQuestionType.fromValue(q.getEnqueteQuestionType()).getName());
      questionBean.setRequiredFlg(NecessaryFlg.fromValue(q.getNecessaryFlg()).getName());
      bean.getQuestionList().add(questionBean);

      // 一つでも選択式の設問が存在すれば選択肢編集ボタンを表示する
      choiceEditButtonDisplay |= q.getEnqueteQuestionType().equals(EnqueteQuestionType.CHECK.longValue())
          || q.getEnqueteQuestionType().equals(EnqueteQuestionType.RADIO.longValue());
    }
    bean.setChoiceEditButtonDisplay(choiceEditButtonDisplay);

    // 選択肢部分再表示
    if (StringUtil.hasValue(questionNo)) {
      List<EnqueteChoice> choiceList = service.getEnqueteChoiceList(enqueteCode, Long.parseLong(questionNo));
      for (EnqueteChoice c : choiceList) {
        ChoicesListBean choiceBean = new ChoicesListBean();
        choiceBean.setEnqueteChoicesNo(c.getEnqueteChoicesNo().toString());
        choiceBean.setChoicesDisplayOrder(c.getDisplayOrder().toString());
      //add by cs_yuli 20120516 start 
        choiceBean.setEnqueteChoicesEn(c.getEnqueteChoicesEn());
        choiceBean.setEnqueteChoicesJp(c.getEnqueteChoicesJp());
      //add by cs_yuli 20120516 end 
        choiceBean.setEnqueteChoices(c.getEnqueteChoices());
        bean.getChoicesList().add(choiceBean);
      }
    }

    return bean;
  }

}
