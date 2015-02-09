package jp.co.sint.webshop.web.action.back.communication;

import jp.co.sint.webshop.data.dto.GiftCardIssueDetail;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.communication.GiftCardRuleEditBean;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1060110:アンケート管理のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class GiftCardRuleEditSearchAction extends GiftCardRuleEditBaseAction {


  public boolean authorize() {
    if(super.authorize()){
      if (Permission.GIFT_CARD_RULE_UPDATE_SHOP.isGranted(getLoginInfo()) == false) {
        return false;
      }
    }
    return true;
  }

  @Override
  public WebActionResult callService() {
    CommunicationService service = ServiceLocator.getCommunicationService(getLoginInfo());
    GiftCardRuleEditBean bean = getBean();
   
    GiftCardIssueDetail result = service.searchGiftCardIssueDetailEdit(bean.getSearchCardId(),bean.getCardCode());
    if (result == null) {
      addErrorMessage("印刷编号不存在");
      bean.setDetail(null);
      setRequestBean(bean);
      return BackActionResult.RESULT_SUCCESS;
    }
    bean.setDetail(result);
    
    setRequestBean(bean);
    return BackActionResult.RESULT_SUCCESS;
  }

  @Override
  public boolean validate() {
    GiftCardRuleEditBean bean = getBean();
    if (StringUtil.isNullOrEmpty(bean.getSearchCardId())) {
      addErrorMessage("印刷编号必须输入");
      return false ;
    }
    
    return true ;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.communication.GiftCardRuleEditSearchAction.0");
  }
  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "5106141006";
  }
}
