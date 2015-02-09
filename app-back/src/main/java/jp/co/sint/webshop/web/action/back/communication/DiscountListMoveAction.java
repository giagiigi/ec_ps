package jp.co.sint.webshop.web.action.back.communication;

import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.communication.DiscountInfo;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.DisplayTransition;

/**
 * U1061210:限时限量折扣一览画面跳转处理
 * 
 * @author KS.
 */
public class DiscountListMoveAction extends DiscountListSearchAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
  String[] urlParam = getRequestParameter().getPathArgs();
  if (urlParam.length > 0 && (urlParam[0].equals("new"))) {
    return Permission.DISCOUNT_READ_SHOP.isGranted(getLoginInfo()) && Permission.DISCOUNT_UPDATE_SHOP.isGranted(getLoginInfo());
  }
  if (urlParam.length > 0 && urlParam[0].equals("select")) {
    return Permission.DISCOUNT_READ_SHOP.isGranted(getLoginInfo());
    }
    return false;
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    String[] urlParam = getRequestParameter().getPathArgs();
    return (urlParam.length > 0 && urlParam[0].equals("new")) || (urlParam.length > 1);
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    String[] urlParam = getRequestParameter().getPathArgs();
    String discountCode = "";
    // 由URL参数判断更新操作
    if (urlParam.length > 1) {
      discountCode = urlParam[1];
      CommunicationService cs = ServiceLocator.getCommunicationService(getLoginInfo());
      DiscountInfo discountInfo = cs.getDiscountInfo(discountCode);
      // 无更新数据判断
      if (discountInfo == null || discountInfo.getDiscountHeader() == null) {
        setNextUrl(null);
        setRequestBean(getBean());
        addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, "限时限量折扣"));
        return BackActionResult.RESULT_SUCCESS;
      }
    }
    // URL设定
    if (urlParam[0].equals("new")) {
      setNextUrl("/app/communication/discount_edit");
    } else if (urlParam[0].equals("select")) {
      setNextUrl("/app/communication/discount_edit/select/" + urlParam[1]);
    } else {
      setRequestBean(getBean());
      return super.callService();
    }

    DisplayTransition.add(getBean(), "/app/communication/discount_list/search_back", getSessionContainer());
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
      return Messages.getString("web.bean.back.communication.DiscountListMoveAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
      return "5106121002";
   }

}
