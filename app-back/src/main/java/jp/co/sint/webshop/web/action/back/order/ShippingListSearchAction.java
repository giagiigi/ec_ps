package jp.co.sint.webshop.web.action.back.order;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.service.OrderService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.service.order.ShippingList;
import jp.co.sint.webshop.service.order.ShippingListSearchCondition;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.order.ShippingListBean;
import jp.co.sint.webshop.web.bean.back.order.ShippingListBean.ShippingSearchedListBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.message.back.CompleteMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerUtil;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1020410:出荷管理のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class ShippingListSearchAction extends ShippingListBaseAction {

  private ShippingListSearchCondition condition;

  /**
   * 初期処理を実行します
   */
  @Override
  public void init() {
    condition = new ShippingListSearchCondition();
  }

  protected ShippingListSearchCondition getCondition() {
    return PagerUtil.createSearchCondition(getRequestParameter(), condition);
  }

  protected ShippingListSearchCondition getSearchCondition() {
    return this.condition;
  }

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    boolean auth;
    BackLoginInfo login = getLoginInfo();

    if (Permission.SHIPPING_UPDATE_SITE.isGranted(login)) {
      auth = true;
    } else if (Permission.SHIPPING_READ_SITE.isGranted(login)) {
      auth = true;
    } else if (Permission.SHIPPING_UPDATE_SHOP.isGranted(login)) {
      auth = true;
    } else if (Permission.SHIPPING_READ_SHOP.isGranted(login)) {
      auth = true;
    } else {
      auth = false;
    }
    return auth;
  }

  /**
   * beanのcreateAttributeを実行します。
   * 
   * @return 実行するならtrue
   */
  @Override
  public boolean isCallCreateAttribute() {
    String[] param = getRequestParameter().getPathArgs();
    String completeParam = "";
    if (param.length == 1) {
      completeParam = param[0];
    }

    return !completeParam.equals(WebConstantCode.COMPLETE_REGISTER) && !completeParam.equals(WebConstantCode.COMPLETE_UPLOAD);
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    ShippingListBean reqBean = (ShippingListBean) getBean();

    boolean beanValidResult = true;
    beanValidResult = validateBean(reqBean);

    // 個別のバリデーションチェック
    boolean validResult = true;

    Date fromShippingDate = DateUtil.fromString(reqBean.getSearchFromShippingDatetime());
    Date toShippingDate = DateUtil.fromString(reqBean.getSearchToShippingDatetime());
    if (fromShippingDate != null && toShippingDate != null) {
      if (toShippingDate.before(fromShippingDate)) {
        addErrorMessage(WebMessage.get(ActionErrorMessage.PERIOD_ERROR_WITH_PARAM, Messages
            .getString("web.action.back.order.ShippingListSearchAction.0")));
        validResult &= false;
      }
    }

    Date fromDirectDate = DateUtil.fromString(reqBean.getSearchFromShippingDirectDate());
    Date toDirectDate = DateUtil.fromString(reqBean.getSearchToShippingDirectDate());
    if (fromDirectDate != null && toDirectDate != null) {
      if (toDirectDate.before(fromDirectDate)) {
        addErrorMessage(WebMessage.get(ActionErrorMessage.PERIOD_ERROR_WITH_PARAM, Messages
            .getString("web.action.back.order.ShippingListSearchAction.1")));
        validResult &= false;
      }
    }

    Date fromOrderDate = DateUtil.fromString(reqBean.getSearchFromOrderDate());
    Date toOrderDate = DateUtil.fromString(reqBean.getSearchToOrderDate());
    if (fromOrderDate != null && toOrderDate != null) {
      if (toOrderDate.before(fromOrderDate)) {
        addErrorMessage(WebMessage.get(ActionErrorMessage.PERIOD_ERROR_WITH_PARAM, Messages
            .getString("web.action.back.order.ShippingListSearchAction.2")));
        validResult &= false;
      }
    }

    boolean result = false;
    if (beanValidResult && validResult) {
      result = true;
    } else {
      result = false;
    }

    return result;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    UtilService utilService = ServiceLocator.getUtilService(getLoginInfo());
    ShippingListBean reqBean = getBean();

    ShippingListBean nextBean = reqBean;
    nextBean.setSearchShopList(utilService.getShopNamesDefaultAllShop(false, false));
    condition = createSearchCondition(reqBean);
    condition.setSearchOrderFlg("1");
    // 10.1.4 K00168 修正 ここから
    // condition = getCondition();
    condition = PagerUtil.createSearchCondition(getRequestParameter(), condition);
    // 10.1.4 K00168 修正 ここまで

    OrderService svc = ServiceLocator.getOrderService(getLoginInfo());
    SearchResult<ShippingList> result = svc.searchShippingList(condition);

    nextBean.setPagerValue(PagerUtil.createValue(result));

    List<ShippingList> shippingList = result.getRows();
    List<ShippingSearchedListBean> list = new ArrayList<ShippingSearchedListBean>();
    setShippingList(nextBean, shippingList, list);

    nextBean.setSearchShippingStatus(reqBean.getSearchShippingStatus());

    if (getLoginInfo().isShop()) {
      nextBean.setSearchDeliveryType(utilService.getDeliveryTypes(getLoginInfo().getShopCode(), true));
    } else {
      if (reqBean.getSearchShopCode() != null) {
        nextBean.setSearchDeliveryType(utilService.getDeliveryTypes(reqBean.getSearchShopCode(), true));
      }
    }

    if (result.getRowCount() == 0) {
      addWarningMessage(WebMessage.get(ActionErrorMessage.SEARCH_RESULT_NOT_FOUND));
    } else if (result.isOverflow()) {
      addWarningMessage(WebMessage.get(ActionErrorMessage.SEARCH_RESULT_OVERFLOW, NumUtil.formatNumber("" + result.getRowCount()),
          "" + NumUtil.formatNumber("" + condition.getMaxFetchSize())));
    }

    nextBean.getEdit().setUpdateWithShipping(true);

    setRequestBean(nextBean);

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {
    super.prerender();
    String[] param = getRequestParameter().getPathArgs();
    String completeParam = "";
    if (param.length == 1) {
      completeParam = param[0];
    }

    if (completeParam.equals(WebConstantCode.COMPLETE_REGISTER)) {
      if (getBean().isSeccessUpdate()) {
        addInformationMessage(WebMessage.get(CompleteMessage.UPDATE_COMPLETE, Messages
            .getString("web.action.back.order.ShippingListSearchAction.3")));
      }
      if (getBean().getErrorMessageDetailList() != null) {
        for (String error : getBean().getErrorMessageDetailList()) {
          addErrorMessage(error);
        }
      }
    } else if (completeParam.equals(WebConstantCode.COMPLETE_UPLOAD)) {
      addInformationMessage(WebMessage.get(CompleteMessage.UPDATE_COMPLETE, Messages
          .getString("web.action.back.order.ShippingListSearchAction.3")));
    }
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.order.ShippingListSearchAction.4");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "1102041010";
  }

}
