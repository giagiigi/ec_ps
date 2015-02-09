package jp.co.sint.webshop.web.action.back.communication;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.domain.EnqueteQuestionType;
import jp.co.sint.webshop.data.domain.NecessaryFlg;
import jp.co.sint.webshop.data.dto.Enquete;
import jp.co.sint.webshop.data.dto.EnqueteChoice;
import jp.co.sint.webshop.data.dto.EnqueteQuestion;
import jp.co.sint.webshop.data.dto.EnqueteReplyInput;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.communication.EnqueteSummaryBean;
import jp.co.sint.webshop.web.bean.back.communication.EnqueteSummaryBean.EnqueteQuestionListBean;
import jp.co.sint.webshop.web.bean.back.communication.EnqueteSummaryBean.EnqueteQuestionListBean.EnqueteChoicesListBean;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1060130:アンケート分析のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class EnqueteSummaryInitAction extends WebBackAction<EnqueteSummaryBean> {

  // 自由回答式設問の最大表示件数
  private static final int MAXIMUM_DISPLAY_RESULT_COMMENT = 10;

  // 各選択肢回答者の全回答者数に占める割合の最大桁数(小数以下何桁までか)
  private static final int MAXIMUM_DECIMAL_PLACES = 1;

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return Permission.ENQUETE_READ_SITE.isGranted(getLoginInfo());
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    String[] path = getRequestParameter().getPathArgs();
    if (path.length < 1) {
      setNextUrl("/app/communication/enquete_list/init");
      return false;
    }
    return true;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    String[] path = getRequestParameter().getPathArgs();
    String enqueteCode = path[0];
    CommunicationService service = ServiceLocator.getCommunicationService(getLoginInfo());

    // アンケート基本情報生成
    Enquete enquete = service.getEnquete(enqueteCode);
    if (enquete == null) {
      setNextUrl("/app/communication/enquete_list/init");
      return BackActionResult.RESULT_SUCCESS;
    }
    EnqueteSummaryBean bean = new EnqueteSummaryBean();
    bean.setEnqueteName(enquete.getEnqueteName());
    bean.setEnqueteNameEn(enquete.getEnqueteNameEn());
    bean.setEnqueteNameJp(enquete.getEnqueteNameJp());
    bean.setEnqueteCode(enqueteCode);

    // アンケート全体の回答者数を取得
    Long enqueteAnswerCount = service.getEnqueteAnswerCount(enqueteCode);
    bean.setRepliedEnqueteCount(NumUtil.toString(enqueteAnswerCount));

    // 設問部分生成
    List<EnqueteQuestion> questionList = service.getEnqueteQuestionList(enqueteCode);
    for (EnqueteQuestion q : questionList) {
      EnqueteQuestionListBean questionBean = new EnqueteQuestionListBean();
      questionBean.setEnqueteQuestionNo(Long.toString(q.getEnqueteQuestionNo()));
      questionBean.setQuestionDisplayOrder(Long.toString(q.getDisplayOrder()));
      questionBean.setEnqueteQuestionType(EnqueteQuestionType.fromValue(q.getEnqueteQuestionType()).getName());
      questionBean.setNecessaryFlg(q.getNecessaryFlg().equals(NecessaryFlg.NECESSARY.longValue())); // 必須入力の設問かどうか
      questionBean.setEnqueteQuestionContent(q.getEnqueteQuestionContent());
      questionBean.setEnqueteQuestionContentEn(q.getEnqueteQuestionContentEn());
      questionBean.setEnqueteQuestionContentJp(q.getEnqueteQuestionContentJp());

      // 設問別回答者数を取得
      Long answeredPersonCount = service.getRepliedQuestionPersonsCount(enqueteCode, q.getEnqueteQuestionNo(), q
          .getEnqueteQuestionType());
      questionBean.setRepliedQuestionCount(NumUtil.toString(answeredPersonCount));

      // 未回答者数＝全体の回答者数－設問別回答者数
      Long noRepliedQuestionCount = enqueteAnswerCount - answeredPersonCount;
      questionBean.setNoRepliedQuestionCount(NumUtil.toString(noRepliedQuestionCount));

      // 未回答者数比率＝未回答者数÷(設問別回答数合計＋未回答者数)×100
      String noRepliedChoicesRate = calculateToDecimal(noRepliedQuestionCount, service.getRepliedQuestionCount(q.getEnqueteCode(),
          q.getEnqueteQuestionNo())
          + noRepliedQuestionCount);
      questionBean.setNoRepliedChoicesRate(noRepliedChoicesRate);

      if (q.getEnqueteQuestionType().equals(EnqueteQuestionType.FREE.longValue())) { // 回答タイプが自由回答の場合
        List<EnqueteReplyInput> list = service.getEnqueteReplyInputList(q.getEnqueteCode(), q.getEnqueteQuestionNo());
        if (list.size() > 0) {
          List<String> replyList = new ArrayList<String>();
          for (EnqueteReplyInput r : list) {
            String reply = r.getEnqueteReply();
            replyList.add(reply);
            // 最大表示数を超えたら打ち切る
            if (replyList.size() >= MAXIMUM_DISPLAY_RESULT_COMMENT) {
              break;
            }
          }
          questionBean.setCommentList(replyList);
        }
      } else { // 回答タイプが選択式の場合
        List<EnqueteChoice> choiceList = service.getEnqueteChoiceList(q.getEnqueteCode(), q.getEnqueteQuestionNo());
        for (EnqueteChoice c : choiceList) {
          EnqueteChoicesListBean choiceBean = new EnqueteChoicesListBean();
          choiceBean.setEnqueteChoicesNo(Long.toString(c.getEnqueteChoicesNo()));
          choiceBean.setEnqueteChoices(c.getEnqueteChoices());
          choiceBean.setEnqueteChoicesEn(c.getEnqueteChoicesEn());
          choiceBean.setEnqueteChoicesJp(c.getEnqueteChoicesJp());

          // 回答者数を取得
          Long choicesCount = service.getRepliedChoiceCount(c.getEnqueteCode(), c.getEnqueteQuestionNo(), c.getEnqueteChoicesNo());
          choiceBean.setRepliedChoicesCount(NumUtil.toString(choicesCount));

          // 回答者数比率＝選択肢別回答者数÷(選択肢別回答数合計＋未回答者数)×100
          String repliedChoicesRate = calculateToDecimal(choicesCount, service.getRepliedQuestionCount(q.getEnqueteCode(), q
              .getEnqueteQuestionNo())
              + noRepliedQuestionCount);
          choiceBean.setRepliedChoicesRate(repliedChoicesRate);

          questionBean.getChoicesList().add(choiceBean);
        }
      }
      bean.getQuestionList().add(questionBean);
    }

    setRequestBean(bean);
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * 割合を計算します。(小数第2位で四捨五入)
   * 
   * @param numerator
   *          分子
   * @param denominator
   *          分母
   * @return "0.0"
   */
  private String calculateToDecimal(Long numerator, Long denominator) {

    // 0除算回避
    if (denominator == 0L) {
      return "0.0";
    }

    double answer = numerator.doubleValue() / denominator.doubleValue() * 100;
    BigDecimal decimal = new BigDecimal(answer);
    return "" + decimal.setScale(MAXIMUM_DECIMAL_PLACES, BigDecimal.ROUND_HALF_UP).doubleValue();

  }

  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {
    // CSV出力ボタン表示制御
    EnqueteSummaryBean bean = (EnqueteSummaryBean) getRequestBean();
    bean.setDisplayExportButtonFlg(Permission.ENQUETE_DATA_IO_SITE.isGranted(getLoginInfo()));

    setRequestBean(bean);
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.communication.EnqueteSummaryInitAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "5106013002";
  }

}
