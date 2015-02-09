package jp.co.sint.webshop.web.action.back.communication;


import jp.co.sint.webshop.data.dto.FriendCouponRule;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.utility.ArrayUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.communication.FriendCouponRuleEditBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.message.back.communication.CommunicationErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1060320:キャンペーンマスタのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class FriendCouponRuleEditRelateUpdateAction extends FriendCouponRuleEditBaseAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  public boolean authorize() {
    BackLoginInfo login = getLoginInfo();

    // ショップ管理者で更新権限のあるユーザか、サイト管理者で更新権限があり、かつ一店舗モードの
    // 時のみアクセス可能
    boolean auth = Permission.FriendCouponRule_UPDATE_SHOP.isGranted(login);

    if (getRequestParameter().getPathArgs() != null && getRequestParameter().getPathArgs().length > 0) {
      if ("delete".equals(getRequestParameter().getPathArgs()[0])) {
        auth = Permission.FriendCouponRule_DELETE_SHOP.isGranted(login);
      }
    }

    return auth;
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  public boolean validate() {
    FriendCouponRuleEditBean bean = getBean();
    // 新建关联商品登录
    if (getRequestParameter().getPathArgs() != null && getRequestParameter().getPathArgs().length > 0) {
      if ("login".equals(getRequestParameter().getPathArgs()[0])) {
        FriendCouponRule rule = new FriendCouponRule();
        CommunicationService communicationService = ServiceLocator.getCommunicationService(getLoginInfo());
        rule = communicationService.selectFriendCouponRule(bean.getFriendCouponRuleNo());
        boolean flag = validateBean(bean.getCommoditiyDetailBean());
        if (!flag) {
          return false;
        } else {
          boolean commodityFlg = isCommodity(bean, true);
          boolean existFlg = isExists(bean, rule);
          boolean resultFlg = isRelated(bean);
          //所有的活动的关联商品登录时，套餐商品时不可登录
          boolean setCommodityFlg = isSetCommodity(bean);
          if (commodityFlg) {
            // 商品不存在
            addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, "商品:" + bean.getCommoditiyDetailBean().getCommodityCode()));
            return false;
          } else if (resultFlg) {
            // 不是通常品
            addErrorMessage(WebMessage.get(CommunicationErrorMessage.NO_COMMON_COMMODITY));
            return false;
          } else if (setCommodityFlg) {
              addErrorMessage(WebMessage.get(CommunicationErrorMessage.SET_COMMODITY_ERROR));
              return false;
          } else if (existFlg) {
            // 在数据中存在
            addErrorMessage(WebMessage.get(CommunicationErrorMessage.DUPLICATED_COMMODITY_ERROR, "关联"));
            return false;
          }
        }
      } else if ("delete".equals(getRequestParameter().getPathArgs()[0])) {
        if (bean.getCheckedCode().size() < 1
            || !StringUtil.hasValueAllOf(ArrayUtil.toArray(getBean().getCheckedCode(), String.class))) {
          addErrorMessage(WebMessage.get(CommunicationErrorMessage.COMMODITY_NO_CHOOSE, "关联商品"));
          return false;

        }
      }
    }
    return true;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  public WebActionResult callService() {
    FriendCouponRuleEditBean bean = getBean();
    FriendCouponRule rule = new FriendCouponRule();
    // 为dto设置值
    CommunicationService communicationService = ServiceLocator.getCommunicationService(getLoginInfo());
    boolean flag = false;
    ServiceResult result = null;
    if (getRequestParameter().getPathArgs() != null && getRequestParameter().getPathArgs().length > 0
        && "login".equals(getRequestParameter().getPathArgs()[0])) {
      rule = communicationService.selectFriendCouponRule(bean.getFriendCouponRuleNo());
      if (rule != null ) {
        rule.setObjectCommodities(setJoin(bean, rule));
        flag = true;
        this.addInformationMessage(WebMessage.get(CommunicationErrorMessage.REGISTER_SUCCESS_INFO, "关联商品"));
      }
    }
    
    if (getRequestParameter().getPathArgs() != null && getRequestParameter().getPathArgs().length > 0
        && "delete".equals(getRequestParameter().getPathArgs()[0])) {
      rule = communicationService.selectFriendCouponRule(bean.getFriendCouponRuleNo());
      if (rule != null) {
        String str = "";

        for (int i = 0; i < bean.getCommoditiyDetailBeanList().size(); i++) {
          boolean equalFlg = false;
          for (int j = 0; j < bean.getCheckedCode().size(); j++) {
            if (bean.getCheckedCode().get(j).equals(bean.getCommoditiyDetailBeanList().get(i).getCommodityCode())) {
              equalFlg = true;
              break;
            }
          }

          if (!equalFlg) {
            if (StringUtil.isNullOrEmpty(str)) {
              str += bean.getCommoditiyDetailBeanList().get(i).getCommodityCode();
            } else {
              str += ";" + bean.getCommoditiyDetailBeanList().get(i).getCommodityCode();
            }
          }
        }
        rule.setObjectCommodities(str);
        result = communicationService.updateFriendCouponRule(rule);
        
        if (result.hasError()) {
          this.addErrorMessage(WebMessage.get(CommunicationErrorMessage.DELETE_FAILED_ERROR, "关联商品"));
          return BackActionResult.RESULT_SUCCESS;
        } else {
          setNextUrl("/app/communication/friend_coupon_rule_edit/init/" + bean.getFriendCouponRuleNo() +"/relatedDelete"+"/succeed");
        }
      }
    }


    if (flag) {
          result = communicationService.updateFriendCouponRule(rule);
      if (result.hasError()) {
        this.addErrorMessage(WebMessage.get(CommunicationErrorMessage.UPDATE_FAILED_ERROR, bean
            .getCommoditiyDetailBean().getCommodityCode()));
        return BackActionResult.RESULT_SUCCESS;

      } else {
        
        setNextUrl("/app/communication/friend_coupon_rule_edit/init/" + bean.getFriendCouponRuleNo() +"/relatedLogin"+"/succeed");
      }
    }

    setRequestBean(bean);
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.bean.back.communication.NewCampaignEditInitAction.005");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "5106102004";
  }

}
