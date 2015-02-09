package jp.co.sint.webshop.web.action.back.communication;

import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.communication.FriendCouponRuleBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.message.back.CompleteMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U5106111002:朋友推荐优惠券选择查询
 * 
 * @author zhangzhengtao
 */
public class FriendCouponRuleDeleteAction extends FriendCouponRuleInitAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    BackLoginInfo login = getLoginInfo();
    if (null == login) {
      return false;
    }
    // 没有更新和查看权限,不显示
    if (!Permission.FriendCouponRule_DELETE_SHOP.isGranted(login)) {
      return false;
    }
    return true;
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    if (getRequestParameter().getPathArgs().length > 0) {
      return true;
    }
    addErrorMessage(WebMessage.get(ActionErrorMessage.BAD_URL));
    this.setRequestBean(getBean());
    return false;
  }

  @Override
  public WebActionResult callService() {
    String[] path = getRequestParameter().getPathArgs();
    // 从数据库中得到数据
    CommunicationService service = ServiceLocator.getCommunicationService(getLoginInfo());
    ServiceResult result = service.deleteFriendCouponRule(path[0]);
    // 登録処理の成功チェック
    FriendCouponRuleBean bean = getBean();
    if (result.hasError()) {
      for (ServiceErrorContent error : result.getServiceErrorList()) {
        if (error.equals(CommonServiceErrorContent.NO_DATA_ERROR)) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_DEFAULT_ERROR));
        } else if (error.equals(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR)) {
          addErrorMessage(WebMessage.get(ActionErrorMessage.DELETE_ERROR));
        }
      }
      setRequestBean(bean);
      return BackActionResult.SERVICE_ERROR;
    }
    addInformationMessage(WebMessage.get(CompleteMessage.DELETE_COMPLETE, Messages
        .getString("web.bean.back.communication.FriendCouponRuleBean.0")));
    setNextUrl("/app/communication/friend_coupon_rule/init/delete");
    this.setRequestBean(bean);
    return super.callService();
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.communication.FriendCouponRuleDeleteAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "5106111002";
  }
}
