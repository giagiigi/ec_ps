package jp.co.sint.webshop.web.action.back.communication;

import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.data.domain.ActivityStatus;
import jp.co.sint.webshop.data.dto.PropagandaActivityRule;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.communication.PropagandaActivityRuleListSearchCondition;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.communication.PropagandaActivityRuleListBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerUtil;

/**
 * 宣传品活动一览初期表示处理
 * 
 * @author Kousen.
 */
public class PropagandaActivityRuleListInitAction extends PropagandaActivityRuleListBaseAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return Permission.PROPAGANDA_ACTIVITY_RULE_READ_SHOP.isGranted(getLoginInfo());
  }

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

    CommunicationService service = ServiceLocator.getCommunicationService(getLoginInfo());
    // nextBeanの作成
    PropagandaActivityRuleListBean nextBean = new PropagandaActivityRuleListBean();

    // 实施中
    nextBean.setSearchStatus(ActivityStatus.IN_PROGRESS.getValue());

    PropagandaActivityRuleListSearchCondition condition = new PropagandaActivityRuleListSearchCondition();
    condition.setSearchStatus(nextBean.getSearchStatus());
    // 検索条件の設定:未チェックのレビューを全件取得
    condition = PagerUtil.createSearchCondition(getRequestParameter(), condition);

    // 検索処理の実行
    SearchResult<PropagandaActivityRule> result = service.getPropagandaActivityRuleList(condition);

    // オーバーフローチェック
    prepareSearchWarnings(result, SearchWarningType.OVERFLOW);

    // ページ情報を追加
    nextBean.setPagerValue(PagerUtil.createValue(result));

    // 結果一覧を作成
    nextBean.setList(createList(result.getRows()));

    setRequestBean(nextBean);
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {
    // ボタン表示制御
    PropagandaActivityRuleListBean bean = (PropagandaActivityRuleListBean) getRequestBean();
    bean.setDisplayUpdateButton(Permission.PROPAGANDA_ACTIVITY_RULE_UPDATE_SHOP.isGranted(getLoginInfo()));
    bean.setDisplayDeleteButton(Permission.PROPAGANDA_ACTIVITY_RULE_DELETE_SHOP.isGranted(getLoginInfo()));
    setRequestBean(bean);
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.communication.PropagandaActivityRuleListInitAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "5106151001";
  }

}
