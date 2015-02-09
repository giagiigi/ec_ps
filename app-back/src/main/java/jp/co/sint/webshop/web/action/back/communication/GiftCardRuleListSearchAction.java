package jp.co.sint.webshop.web.action.back.communication;

import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.data.dto.GiftCardRule;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.communication.GiftCardRuleListSearchCondition;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.communication.GiftCardRuleListBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerUtil;

/**
 * U1060110:アンケート管理のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class GiftCardRuleListSearchAction extends GiftCardRuleListBaseAction {

  private GiftCardRuleListSearchCondition condition;

  /**
   * 初期処理を実行します
   */
  @Override
  public void init() {
    condition = new GiftCardRuleListSearchCondition();
  }

  protected GiftCardRuleListSearchCondition getCondition() {
    return PagerUtil.createSearchCondition(getRequestParameter(), condition);
  }

  protected GiftCardRuleListSearchCondition getSearchCondition() {
    return this.condition;
  }

  @Override
  public boolean authorize() {
    BackLoginInfo login = getLoginInfo();
    if (null == login) {
      return false;
    }
    // 没有更新和查看权限,不显示
    if (Permission.GIFT_CARD_RULE_READ_SHOP.isGranted(login)) {
      return true;
    }
    return false;
  }

  @Override
  public WebActionResult callService() {
    CommunicationService service = ServiceLocator.getCommunicationService(getLoginInfo());
    GiftCardRuleListBean bean = getBean();
    condition.setSearchCardCode(bean.getSearchCardCode());
    condition.setSearchCardName(bean.getSearchCardName());
    condition = getCondition();
    SearchResult<GiftCardRule> result = service.searchGiftCardRuleList(condition);

    // 件数0件,オーバーフローチェック
    prepareSearchWarnings(result, SearchWarningType.BOTH);

    bean.setPagerValue(PagerUtil.createValue(result));

    bean.setList(createList(result.getRows()));

    setRequestBean(bean);
    return BackActionResult.RESULT_SUCCESS;
  }

  @Override
  public boolean validate() {
    return true;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.communication.GiftCardRuleListSearchAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "5106141010";
  }
  
}
