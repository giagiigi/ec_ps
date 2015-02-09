package jp.co.sint.webshop.web.action.back.communication;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.communication.MessageHeadLine;
import jp.co.sint.webshop.service.communication.CustomerMessageSearchCondition;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.validation.BeanValidator;
import jp.co.sint.webshop.validation.ValidationSummary;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.communication.CustomerMessageBean;
import jp.co.sint.webshop.web.bean.back.communication.CustomerMessageBean.MessageDetailBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerUtil;

/**
 * U1061210:顾客留言一览画面检索处理
 * 
 * @author KS.
 */
public class CustomerMessageSearchAction extends WebBackAction<CustomerMessageBean> {

  private CustomerMessageSearchCondition condition;

  protected CustomerMessageSearchCondition getCondition() {
    return PagerUtil.createSearchCondition(getRequestParameter(), condition);
  }

  protected CustomerMessageSearchCondition getSearchCondition() {
    return this.condition;
  }

  /**
   * 初期処理を実行します
   */
  @Override
  public void init() {
    condition = new CustomerMessageSearchCondition();
  }

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return Permission.MESSAGE_READ_SHOP.isGranted(getLoginInfo());
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    ValidationSummary summary = BeanValidator.validate(getBean());
    if (summary.hasError()) {
      getDisplayMessage().getErrors().addAll(summary.getErrorMessages());
      return false;
    }

    condition = getCondition();
    // 日付の大小関係チェック
    condition.setSearchMessageStartDatetimeFrom(getBean().getSearchMessageStartDatetimeFrom());
    condition.setSearchMessageEndDatetimeTo(getBean().getSearchMessageEndDatetimeTo());

    if (condition.isValid()) {
      return true;
    } else {
      if (StringUtil.hasValueAllOf(condition.getSearchMessageStartDatetimeFrom(), condition.getSearchMessageEndDatetimeTo())) {
        if (!StringUtil.isCorrectRange(condition.getSearchMessageStartDatetimeFrom(), condition.getSearchMessageEndDatetimeTo())) {
          addErrorMessage(WebMessage.get(ActionErrorMessage.PERIOD_ERROR_WITH_PARAM, "留言期间"));
        }
      }
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
    CustomerMessageBean bean = getBean();
    CommunicationService service = ServiceLocator.getCommunicationService(getLoginInfo());
    List<MessageDetailBean> detailList = new ArrayList<MessageDetailBean>();

    // 設置查詢條件
    condition = getCondition();
    condition.setSearchCustomerCode(bean.getSearchCustomerCode());
    condition.setSearchMessageStartDatetimeFrom(bean.getSearchMessageStartDatetimeFrom());
    condition.setSearchMessageEndDatetimeTo(bean.getSearchMessageEndDatetimeTo());
    SearchResult<MessageHeadLine> result = service.getMessageList(condition);

    // オーバーフローチェック
    prepareSearchWarnings(result, SearchWarningType.BOTH);

    // 清空List
    bean.getList().clear();

    // 將查詢出的数据保存在BEAN中
    for (MessageHeadLine lhl : result.getRows()) {
      MessageDetailBean detail = new MessageDetailBean();
      detail.setCustomerCode(lhl.getCustomerCode());
      detail.setMessageDatetime(DateUtil.toDateTimeString(lhl.getCreatedDatetime()));
      detail.setMessageContent(lhl.getMessage());
      detail.setOrmRowid(lhl.getOrmRowid());
      detailList.add(detail);
    }

    bean.setPagerValue(PagerUtil.createValue(result));
    bean.setList(detailList);

    setRequestBean(bean);

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.bean.back.communication.CustomerMessageSearchAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "5106121003";
  }

}
