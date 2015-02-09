package jp.co.sint.webshop.web.action.back.communication;

import java.text.MessageFormat;
import java.util.List;

import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.CommunicationServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.utility.ArrayUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.communication.FriendCouponRuleListBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.message.back.CompleteMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.message.back.communication.CommunicationErrorMessage;

/**
 * U1060610:PRIVATEクーポン管理のアクションクラスです
 * 
 * @author OB
 */
public class FriendCouponRuleListDeleteAction extends FriendCouponRuleListSearchAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return Permission.FriendCouponRule_READ_SHOP.isGranted(getLoginInfo())
        && Permission.FriendCouponRule_DELETE_SHOP.isGranted(getLoginInfo());
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

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    FriendCouponRuleListBean bean = getBean();
    CommunicationService service = ServiceLocator.getCommunicationService(getLoginInfo());
    String couponCode = getRequestParameter().getPathArgs()[0];
    // 从数据库中得到数据
    // 削除処理実行
    ServiceResult serviceResult = service.deleteFriendCouponRule(couponCode);
    if (serviceResult.hasError()) {
      for (ServiceErrorContent result : serviceResult.getServiceErrorList()) {
        if (result.equals(CommonServiceErrorContent.NO_DATA_ERROR)) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, MessageFormat.format("朋友推荐优惠券发行规则(优惠券规则编号:{0})",
              couponCode)));
        } else if (result.equals(CommunicationServiceErrorContent.NEWCOUPONRULE_USE_ERROR)) {
          addErrorMessage(WebMessage.get(CommunicationErrorMessage.COUPON_DELETE_ERROR, MessageFormat.format(
              "朋友推荐优惠券发行规则(优惠券规则编号:{0})", couponCode)));
        } else {
          return BackActionResult.SERVICE_ERROR;
        }
      }
    } else {
      addInformationMessage(WebMessage.get(CompleteMessage.DELETE_COMPLETE, MessageFormat.format("朋友推荐优惠券发行规则(优惠券规则编号:{0})",
          couponCode)));
    }

    this.setRequestBean(bean);

    return super.callService();
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return "朋友推荐优惠券发行规则删除处理";
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "5106061002";
  }

}
