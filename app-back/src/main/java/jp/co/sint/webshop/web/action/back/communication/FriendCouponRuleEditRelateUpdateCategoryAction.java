package jp.co.sint.webshop.web.action.back.communication;

import jp.co.sint.webshop.data.dto.FriendCouponRule;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.communication.FriendCouponRuleEditBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.communication.CommunicationErrorMessage;

/**
 * U1060320:公共优惠券发行规则详细画面关联分类处理
 * 
 * @author System Integrator Corp.
 */
public class FriendCouponRuleEditRelateUpdateCategoryAction extends FriendCouponRuleEditBaseAction {

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
    if ("login".equals(getRequestParameter().getPathArgs()[0])) {
      if (StringUtil.isNullOrEmpty(bean.getCategoryDetailBean().getCategoryCode())
          || bean.getCategoryDetailBean().getCategoryCode().equals("0")) {
        addErrorMessage("请选择相应分类！");
        return false;
      }

      CommunicationService communicationService = ServiceLocator.getCommunicationService(getLoginInfo());
      FriendCouponRule rule = communicationService.selectFriendCouponRule(bean.getFriendCouponRuleNo());
      if (rule != null) {
        String attributeValue = rule.getObjectCategory();
        String[] array = new String[] {};
        if (!StringUtil.isNullOrEmpty(attributeValue)) {
          array = attributeValue.split(";");
        }

        for (int i = 0; i < array.length; i++) {
          if (array[i].equals(bean.getCategoryDetailBean().getCategoryCode())) {
            addErrorMessage("已经登录过的分类！");
            return false;
          }
        }
      } else {
        return false;
      }
      if (!(StringUtil.isNullOrEmpty(bean.getCategoryDetailBean().getLimitedNum()) || NumUtil.isNum(bean.getCategoryDetailBean()
          .getLimitedNum()))) {
        addErrorMessage("数量必须是一个整数");
        return false;
      }
      
      
    }
    if ("delete".equals(getRequestParameter().getPathArgs()[0])) {
      if (bean.getCheckedCategoryCode() == null || StringUtil.isNullOrEmpty(bean.getCheckedCategoryCode().get(0))) {
        addErrorMessage("请选中要删除的分类！");
        return false;
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
    FriendCouponRule newCouponRule = new FriendCouponRule();
    // 为dto设置值
    CommunicationService communicationService = ServiceLocator.getCommunicationService(getLoginInfo());
    newCouponRule = communicationService.selectFriendCouponRule(bean.getFriendCouponRuleNo());
    boolean flag = false;
    ServiceResult result = null;
    if (getRequestParameter().getPathArgs() != null && getRequestParameter().getPathArgs().length > 0
        && "login".equals(getRequestParameter().getPathArgs()[0]) && newCouponRule != null) {
      newCouponRule.setObjectCategory(setCategoryJoin(bean, newCouponRule));
      flag = true;
    }

    if (getRequestParameter().getPathArgs() != null && getRequestParameter().getPathArgs().length > 0
        && "delete".equals(getRequestParameter().getPathArgs()[0])) {
      if (newCouponRule != null) {
        String[] array = new String[] {};
        if (StringUtil.hasValue(newCouponRule.getObjectCategory())) {
          array = newCouponRule.getObjectCategory().split(";");
        }
        String str = "";
        
        for (int i = 0; i < array.length; i++) {
          boolean equalFlg = false;
          for (int j = 0; j < bean.getCheckedCategoryCode().size(); j++) {
            if (bean.getCheckedCategoryCode().get(j).equals(array[i].split(":")[0])) {
              equalFlg = true;
              break;
            }
          }

          if (!equalFlg) {
            if (StringUtil.isNullOrEmpty(str)) {
              str = array[i];
            } else {
              str += ";" + array[i];
            }
          }
        }
        newCouponRule.setObjectCategory(str);
        result = communicationService.updateFriendCouponRule(newCouponRule);

        if (result.hasError()) {
          this.addErrorMessage(WebMessage.get(CommunicationErrorMessage.DELETE_FAILED_ERROR, "关联分类"));
          return BackActionResult.RESULT_SUCCESS;
        } else {
          setNextUrl("/app/communication/friend_coupon_rule_edit/init/" + bean.getFriendCouponRuleNo() + "/categoryDelete" + "/succeed");
        }
      }
    }

    if (flag) {
      result = communicationService.updateFriendCouponRule(newCouponRule);
      if (result.hasError()) {
        this.addErrorMessage(WebMessage.get(CommunicationErrorMessage.UPDATE_FAILED_ERROR, bean.getCategoryDetailBean()
            .getCategoryCode()));
        return BackActionResult.RESULT_SUCCESS;

      } else {

        setNextUrl("/app/communication/friend_coupon_rule_edit/init/" + bean.getFriendCouponRuleNo() + "/categoryLogin" + "/succeed");
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
    return "公共优惠券发行规则详细画面关联分类处理";
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "5106102006";
  }

}
