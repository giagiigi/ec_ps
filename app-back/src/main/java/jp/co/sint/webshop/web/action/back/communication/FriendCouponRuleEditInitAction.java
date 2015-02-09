package jp.co.sint.webshop.web.action.back.communication;

import jp.co.sint.webshop.data.dto.FriendCouponRule;
import jp.co.sint.webshop.data.dto.NewCouponRule;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.communication.FriendCouponRuleEditBean;
import jp.co.sint.webshop.web.exception.URLNotFoundException;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.communication.CommunicationErrorMessage;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1060120:アンケートマスタのアクションクラスです
 * 
 * @author OB
 */
public class FriendCouponRuleEditInitAction extends FriendCouponRuleEditBaseAction {

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  public WebActionResult callService() {

    FriendCouponRule rule = new FriendCouponRule();
    CommunicationService communicationService = ServiceLocator.getCommunicationService(getLoginInfo());
    // 更新或注册的情况下,显示信息
    if (getRequestParameter().getPathArgs().length == 2) {
      // 获得新优惠规则编号
      String couponId = getRequestParameter().getPathArgs()[1];
      if (getBean() != null) {
        // 显示提示信息
        setCompleteMessage(couponId);
      }

      // 将数据显示到页面
      showToBean(couponId);

      setRequestBean(getBean());
      setBean(getBean());
      return BackActionResult.RESULT_SUCCESS;
      // 更新的情况下
    } else if (getRequestParameter().getPathArgs().length == 1 || getRequestParameter().getPathArgs().length == 3) {
      if (getRequestParameter().getPathArgs().length == 3 && !StringUtil.isNullOrEmpty(getRequestParameter().getPathArgs()[2])
          && getRequestParameter().getPathArgs()[2].equals("succeed")) {
        rule = communicationService.selectFriendCouponRule(getBean().getFriendCouponRuleNo());
        if (rule == null) {
          throw new URLNotFoundException();

        } else {
          if ("relatedLogin".equals(getRequestParameter().getPathArgs()[1])) {
            this.addInformationMessage(WebMessage.get(CommunicationErrorMessage.REGISTER_SUCCESS_INFO, "关联商品"));
          } else if ("relatedDelete".equals(getRequestParameter().getPathArgs()[1])) {
            this.addInformationMessage(WebMessage.get(CommunicationErrorMessage.DELETE_SUCCESS_INFO, "关联商品"));
            // 20130929 txw add start
          } else if ("categoryLogin".equals(getRequestParameter().getPathArgs()[1])) {
            this.addInformationMessage(WebMessage.get(CommunicationErrorMessage.REGISTER_SUCCESS_INFO, "关联分类"));
          } else if ("categoryDelete".equals(getRequestParameter().getPathArgs()[1])) {
            this.addInformationMessage(WebMessage.get(CommunicationErrorMessage.DELETE_SUCCESS_INFO, "关联分类"));
          } else if ("categoryUpdate".equals(getRequestParameter().getPathArgs()[1])) {
            this.addInformationMessage(WebMessage.get(CommunicationErrorMessage.UPDATE_SUCCESS_INFO, "关联分类"));
          } else if ("brandUpdate".equals(getRequestParameter().getPathArgs()[1])) {
            this.addInformationMessage(WebMessage.get(CommunicationErrorMessage.UPDATE_SUCCESS_INFO, "关联品牌"));
          }
          // 20130929 txw add emd
        }
      }

      // 获得新优惠规则编号
      String couponId = getRequestParameter().getPathArgs()[0];

      // 将数据显示到页面
      showToBean(couponId);

    } else {
      // 页面初期化设置
      initPageSet();
    }
    setRequestBean(getBean());
    setBean(getBean());
    return BackActionResult.RESULT_SUCCESS;
  }

  public void prerender() {
    if (getBean().getMaxUseOrderAmount() == null || getBean().getMaxUseOrderAmount().equals("null")) {
      getBean().setMaxUseOrderAmount("");
    }
    BackLoginInfo login = getLoginInfo();
    // 如果只有查看权限,页面不可编辑,不显示更新和注册按钮
    if (Permission.PUBLIC_COUPON_UPDATE_SHOP.isGranted(login) == false) {

      FriendCouponRuleEditBean bean = getBean();
      bean.setPageFlg(WebConstantCode.DISPLAY_READONLY);
      bean.setDisplayRegistButtonFlg(false);
      bean.setDisplayUpdateButtonFlg(false);
      bean.setDisplayDeleteFlg(false);
      setBean(bean);
      setRequestBean(getBean());
    }

  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return "公共优惠券发行规则详细画面初期表示处理";
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "5106072001";
  }

  public boolean authorize() {

    if (super.authorize()) {

      BackLoginInfo login = getLoginInfo();

      // 注册的时候
      if (getRequestParameter().getPathArgs().length == 0 && Permission.PUBLIC_COUPON_READ_SHOP.isGranted(getLoginInfo()) == true
          && Permission.PUBLIC_COUPON_UPDATE_SHOP.isGranted(getLoginInfo()) == false) {
        return false;
      }

      // 如果有查看权限
      if (Permission.PUBLIC_COUPON_READ_SHOP.isGranted(login)) {
        return true;
      }

    }

    return false;
  }

  public boolean validate() {
    return true;
  }

}
