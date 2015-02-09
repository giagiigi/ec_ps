package jp.co.sint.webshop.web.action.back.communication;

import jp.co.sint.webshop.data.dto.DiscountHeader;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.communication.DiscountInfo;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.communication.DiscountEditBean;
import jp.co.sint.webshop.web.exception.URLNotFoundException;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.CompleteMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1061210:限时限量折扣编辑画面选择表示处理
 * 
 * @author System Integrator Corp.
 */
public class DiscountEditSelectAction extends DiscountEditBaseAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  public boolean authorize() {
    return Permission.DISCOUNT_READ_SHOP.isGranted(getLoginInfo());
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  public boolean validate() {
    String[] param = getRequestParameter().getPathArgs();

    DiscountHeader dh = new DiscountHeader();
    CommunicationService communicationService = ServiceLocator.getCommunicationService(getLoginInfo());

    if (param.length > 1) {
      dh = communicationService.getDiscountHeader(param[0]);
      if (dh == null) {
        throw new URLNotFoundException();
      } else {
        if ("register".equals(param[1])) {
          addInformationMessage(WebMessage.get(CompleteMessage.REGISTER_COMPLETE, "折扣活动"));
        } else if ("update".equals(param[1])) {
          addInformationMessage(WebMessage.get(CompleteMessage.UPDATE_COMPLETE, "折扣活动"));
        } else if ("commodityLogin".equals(param[1])) {
          addInformationMessage(WebMessage.get(CompleteMessage.REGISTER_COMPLETE, "明细设定"));
        } else if ("commodityUpdate".equals(param[1])) {
          addInformationMessage(WebMessage.get(CompleteMessage.UPDATE_COMPLETE, "明细设定"));
        } else if ("commodityDelete".equals(param[1])) {
          addInformationMessage(WebMessage.get(CompleteMessage.DELETE_COMPLETE, "明细设定"));
        }
      }
    } else if (param.length == 1) {
      dh = communicationService.getDiscountHeader(param[0]);
      if (dh == null) {
        throw new URLNotFoundException();
      }
    } else {
      throw new URLNotFoundException();
    }
    return true;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  public WebActionResult callService() {
    DiscountEditBean bean = new DiscountEditBean();

    String[] param = getRequestParameter().getPathArgs();

    DiscountInfo discountInfo = new DiscountInfo();
    CommunicationService communicationService = ServiceLocator.getCommunicationService(getLoginInfo());
    discountInfo = communicationService.getDiscountInfo(param[0]);
    if (discountInfo != null && discountInfo.getDiscountHeader() != null) {
      // 为bean赋值
      setDiscountEditBean(bean, discountInfo);
    } else {
      throw new URLNotFoundException();
    }
    bean.setDisplayDeleteFlg(Permission.DISCOUNT_DELETE_SHOP.isGranted(getLoginInfo()));
    if (!Permission.DISCOUNT_UPDATE_SHOP.isGranted(getLoginInfo())) {
      bean.setDisplayMode(WebConstantCode.DISPLAY_READONLY);
      bean.setDisplayLoginButtonFlg(false);
      bean.setDisplayUpdateButtonFlg(false);
      bean.setEditMode(WebConstantCode.DISPLAY_READONLY);
    } else {
      bean.setDisplayMode(WebConstantCode.DISPLAY_READONLY);
      bean.setDisplayLoginButtonFlg(false);
      bean.setDisplayUpdateButtonFlg(true);
      bean.setEditMode(WebConstantCode.DISPLAY_EDIT);
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
    return Messages.getString("web.bean.back.communication.DiscountEditSelectAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "5106122007";
  }

}
