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
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerUtil;

/**
 * U1060110:アンケート管理のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class EnqueteListInitAction extends EnqueteListBaseAction {

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    CommunicationService svc = ServiceLocator.getCommunicationService(getLoginInfo());
    EnqueteListSearchCondition condition = new EnqueteListSearchCondition();
    condition = PagerUtil.createSearchCondition(getRequestParameter(), condition);

    // アンケートを全件取得する
    SearchResult<EnqueteList> result = svc.getEnqueteList(condition);

    // オーバーフローチェック
    prepareSearchWarnings(result, SearchWarningType.OVERFLOW);

    EnqueteListBean nextBean = new EnqueteListBean();

    // ページ情報を追加
    nextBean.setPagerValue(PagerUtil.createValue(result));

    // 結果一覧を作成
    List<EnqueteList> enqueteList = result.getRows();
    addResult(enqueteList, nextBean);

    this.setRequestBean(nextBean);
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.communication.EnqueteListInitAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "5106011002";
  }

}
