package jp.co.sint.webshop.web.action.back.communication;

import jp.co.sint.webshop.data.csv.CsvSchema;
import jp.co.sint.webshop.data.domain.EnqueteQuestionType;
import jp.co.sint.webshop.data.dto.EnqueteQuestion;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.data.CsvExportCondition;
import jp.co.sint.webshop.service.data.CsvExportType;
import jp.co.sint.webshop.service.data.csv.EnqueteSummaryChoicesExportCondition;
import jp.co.sint.webshop.service.data.csv.EnqueteSummaryInputExportCondition;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.WebExportAction;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.communication.EnqueteSummaryBean;
import jp.co.sint.webshop.web.exception.URLNotFoundException;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1060130:アンケート分析のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class EnqueteSummaryExportAction extends WebBackAction<EnqueteSummaryBean> implements WebExportAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return Permission.ENQUETE_DATA_IO_SITE.isGranted(getLoginInfo());
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    String[] path = getRequestParameter().getPathArgs();
    if (path.length < 2) {
      setRequestBean(getBean());
      addErrorMessage(WebMessage.get(ActionErrorMessage.BAD_URL));
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

    EnqueteSummaryBean bean = getBean();

    String[] path = getRequestParameter().getPathArgs();
    String enqueteCode = path[0];
    String questionNo = path[1];

    // ヘッダ行を作成(設問内容、回答数)
    CommunicationService service = ServiceLocator.getCommunicationService(getLoginInfo());
    EnqueteQuestion question = service.getEnqueteQuestion(enqueteCode, NumUtil.toLong(questionNo));

    if (question == null || EnqueteQuestionType.fromValue(question.getEnqueteQuestionType()) == null) {
      setRequestBean(getBean());
      addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
          Messages.getString("web.action.back.communication.EnqueteSummaryExportAction.0")));
      return BackActionResult.RESULT_SUCCESS;
    }

    switch (EnqueteQuestionType.fromValue(question.getEnqueteQuestionType())) {
      case RADIO:
      case CHECK:
        EnqueteSummaryChoicesExportCondition choicesCondition = CsvExportType.EXPORT_CSV_ENQUETE_SUMMARY_CHOICE
            .createConditionInstance();
        choicesCondition.setEnqueteCode(enqueteCode);
        choicesCondition.setEnqueteQuestionNo(NumUtil.toLong(questionNo));

        this.exportCondition = choicesCondition;
        break;
      case FREE:
        EnqueteSummaryInputExportCondition inputCondition = CsvExportType.EXPORT_CSV_ENQUETE_SUMMARY_INPUT
            .createConditionInstance();
        inputCondition.setEnqueteCode(enqueteCode);
        inputCondition.setEnqueteQuestionNo(NumUtil.toLong(questionNo));

        this.exportCondition = inputCondition;
        break;
      default:
        throw new URLNotFoundException();
    }

    setNextUrl("/download");
    setRequestBean(bean);

    return BackActionResult.RESULT_SUCCESS;
  }

  private CsvExportCondition<? extends CsvSchema> exportCondition;

  public CsvExportCondition<? extends CsvSchema> getExportCondition() {
    return exportCondition;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.communication.EnqueteSummaryExportAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "5106013001";
  }

}
