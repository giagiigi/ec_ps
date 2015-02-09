package jp.co.sint.webshop.web.action.back.communication;

import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.data.dto.FreePostageRule;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.communication.FreePostageListSearchCondition;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.validation.BeanValidator;
import jp.co.sint.webshop.validation.ValidationSummary;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.communication.FreePostageListBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerUtil;

/**
 * 免邮促销一览检索表示处理
 * 
 * @author Kousen.
 */
public class FreePostageListSearchAction extends FreePostageListBaseAction {

  private FreePostageListSearchCondition condition;

  /**
   * 初期処理を実行します
   */
  @Override
  public void init() {
    condition = new FreePostageListSearchCondition();
  }

  protected FreePostageListSearchCondition getCondition() {
    return PagerUtil.createSearchCondition(getRequestParameter(), condition);
  }

  protected FreePostageListSearchCondition getSearchCondition() {
    return this.condition;
  }

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return Permission.FREE_POSTAGE_READ_SHOP.isGranted(getLoginInfo());
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    // bean(検索条件)のvalidationチェック

    ValidationSummary summary = BeanValidator.validate(getBean());
    if (summary.hasError()) {
      getDisplayMessage().getErrors().addAll(summary.getErrorMessages());
      return false;
    }

    condition = getCondition();
    // 日付の大小関係チェック
    condition.setSearchFreeStartDateFrom(getBean().getSearchFreeStartDateFrom());
    condition.setSearchFreeStartDateTo(getBean().getSearchFreeStartDateTo());

    if (condition.isValid()) {
      return true;
    } else {
      if (StringUtil.hasValueAllOf(condition.getSearchFreeStartDateFrom(), condition.getSearchFreeStartDateTo())) {
        if (!StringUtil.isCorrectRange(condition.getSearchFreeStartDateFrom(), condition.getSearchFreeStartDateTo())) {
          addErrorMessage(WebMessage.get(ActionErrorMessage.PERIOD_ERROR_WITH_PARAM, "活动期间"));
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
    CommunicationService service = ServiceLocator.getCommunicationService(getLoginInfo());

    // 検索条件の設定
    FreePostageListBean bean = getBean();

    condition.setSearchFreePostageCode(bean.getSearchFreePostageCode());
    condition.setSearchFreePostageName(bean.getSearchFreePostageName());
    condition.setSearchFreeStartDateFrom(bean.getSearchFreeStartDateFrom());
    condition.setSearchFreeStartDateTo(bean.getSearchFreeStartDateTo());
    condition.setSearchStatus(bean.getSearchStatus());
    condition = getCondition();

    // 検索処理実行
    SearchResult<FreePostageRule> result = service.getFreePostageList(condition);

    // 件数0件,オーバーフローチェック
    prepareSearchWarnings(result, SearchWarningType.BOTH);

    // ページ情報を追加
    bean.setPagerValue(PagerUtil.createValue(result));

    // 結果一覧を作成
    bean.setList(createList(result.getRows()));

    setRequestBean(bean);
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.communication.FreePostageListSearchAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "5106131002";
  }

}
