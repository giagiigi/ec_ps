package jp.co.sint.webshop.web.action.back.communication;

import java.util.List;

import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.communication.EnqueteList;
import jp.co.sint.webshop.service.communication.EnqueteListSearchCondition;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.communication.EnqueteListBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerUtil;

/**
 * U1060110:アンケート管理のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class EnqueteListSearchAction extends EnqueteListBaseAction {

  private EnqueteListSearchCondition condition;

  /**
   * 初期処理を実行します
   */
  @Override
  public void init() {
    condition = new EnqueteListSearchCondition();
  }

  protected EnqueteListSearchCondition getCondition() {
    return PagerUtil.createSearchCondition(getRequestParameter(), condition);
  }

  protected EnqueteListSearchCondition getSearchCondition() {
    return this.condition;
  }

  /**
   * beanのcreateAttributeを実行するかどうかの設定
   * 
   * @return true - 実行する false-実行しない
   */
  @Override
  public boolean isCallCreateAttribute() {
    return true;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    CommunicationService svc = ServiceLocator.getCommunicationService(getLoginInfo());

    // 検索条件をセット
    EnqueteListBean searchBean = getBean();
    condition.setSearchEnqueteCodeFrom(searchBean.getSearchEnqueteCodeFrom());
    condition.setSearchEnqueteCodeTo(searchBean.getSearchEnqueteCodeTo());
    condition.setSearchEnqueteName(searchBean.getSearchEnqueteName());
    condition.setSearchEnqueteStartDateFrom(searchBean.getSearchEnqueteStartDateFrom());
    condition.setSearchEnqueteStartDateTo(searchBean.getSearchEnqueteStartDateTo());
    condition.setSearchEnqueteEndDateFrom(searchBean.getSearchEnqueteEndDateFrom());
    condition.setSearchEnqueteEndDateTo(searchBean.getSearchEnqueteEndDateTo());

    condition = getCondition();

    // 検索処理実行
    SearchResult<EnqueteList> result = svc.getEnqueteList(condition);

    // 件数0件,オーバーフローチェック
    prepareSearchWarnings(result, SearchWarningType.BOTH);

    // 入力された検索条件を保持
    EnqueteListBean nextBean = searchBean;
    nextBean.getList().clear();

    // ページ情報を追加
    nextBean.setPagerValue(PagerUtil.createValue(result));

    // 結果一覧を作成
    List<EnqueteList> enqueteList = result.getRows();
    addResult(enqueteList, nextBean);

    this.setRequestBean(nextBean);
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {

    EnqueteListBean enqueteBean = getBean();

    // bean(検索条件)のvalidationチェック
    if (!validateBean(enqueteBean)) {
      return false;
    }

    // 日付の大小関係チェック
    condition.setSearchEnqueteStartDateFrom(enqueteBean.getSearchEnqueteStartDateFrom());
    condition.setSearchEnqueteStartDateTo(enqueteBean.getSearchEnqueteStartDateTo());
    condition.setSearchEnqueteEndDateFrom(enqueteBean.getSearchEnqueteEndDateFrom());
    condition.setSearchEnqueteEndDateTo(enqueteBean.getSearchEnqueteEndDateTo());

    if (!condition.isValid()) {
      addErrorMessage(WebMessage.get(ActionErrorMessage.PERIOD_ERROR));
      return false;
    }

    return true;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.communication.EnqueteListSearchAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "5106011004";
  }

}
