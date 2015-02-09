package jp.co.sint.webshop.web.action.back.order;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.sint.webshop.data.domain.DataTransportStatus;
import jp.co.sint.webshop.data.domain.FixedSalesStatus;
import jp.co.sint.webshop.data.domain.ShippingStatus;
import jp.co.sint.webshop.data.dto.OrderHeader;
import jp.co.sint.webshop.data.dto.ShippingHeader;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.OrderService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.service.customer.CustomerConstant;
import jp.co.sint.webshop.service.order.InputShippingReport;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.order.ShippingListBean;
import jp.co.sint.webshop.web.bean.back.order.ShippingListBean.ShippingSearchedListBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.message.back.order.OrderErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1020410:出荷管理のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class ShippingListClearShippingDirectAction extends ShippingListBaseAction {

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
    } else if (Permission.SHIPPING_UPDATE_SHOP.isGranted(login)) {
      auth = true;
    } else {
      auth = false;
    }
    return auth;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    UtilService utilService = ServiceLocator.getUtilService(getLoginInfo());

    ShippingListBean reqBean = (ShippingListBean) getBean();
    reqBean.setSeccessUpdate(false);
    reqBean.getErrorMessageDetailList().clear();

    OrderService service = ServiceLocator.getOrderService(getLoginInfo());
    reqBean.setSearchShopList(utilService.getShopNamesDefaultAllShop(false, false));
    reqBean.setSearchShippingStatusList(reqBean.getSearchShippingStatusList());

    String searchShopCode = "";
    if (getLoginInfo().isSite()) {
      searchShopCode = reqBean.getSearchShopCode();
    } else {
      searchShopCode = getLoginInfo().getShopCode();
    }

    if (StringUtil.hasValue(searchShopCode)) {
      reqBean.setSearchDeliveryType(utilService.getDeliveryTypes(reqBean.getSearchShopCode(), true));
    }

    List<String> errorMessageDetailList = new ArrayList<String>();
    Map<String, Date> clearedShippingOrderInfoMap = new HashMap<String, Date>();

    ServiceResult serviceResult = null;
    for (String shippingNo : reqBean.getShippingCheck()) {
      ShippingHeader shippingHeader = service.getShippingHeader(shippingNo);

      if (shippingHeader == null) {
        errorMessageDetailList.add(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, MessageFormat.format(
            Messages.getString("web.action.back.order.ShippingListClearShippingDirectAction.0"), shippingNo)));
        continue;
      }

      OrderHeader ohDto = service.getOrderHeader(shippingHeader.getOrderNo());
      if (ohDto == null) {
        errorMessageDetailList.add(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, MessageFormat.format(
            Messages.getString("web.action.back.order.ShippingListClearShippingDirectAction.1"),
            shippingHeader.getOrderNo())));
        continue;
      }

      // 顧客退会チェック
      if (CustomerConstant.isCustomer(shippingHeader.getCustomerCode())) {
        CustomerService customerService = ServiceLocator.getCustomerService(getLoginInfo());
        if (customerService.isWithdrawed(shippingHeader.getCustomerCode())) {
          errorMessageDetailList.add(WebMessage.get(OrderErrorMessage.WITHDRAWAL_CUSTOMER, shippingHeader.getOrderNo()));
          continue;
        }
      }

      if (NumUtil.toString(shippingHeader.getShippingStatus()).equals(ShippingStatus.CANCELLED.getValue())) {
        // 出荷ステータスが「キャンセル」の場合は出荷実績登録不可
        errorMessageDetailList.add(WebMessage.get(OrderErrorMessage.CANCELLED_SHIPPING_WITH_NO, shippingHeader.getShippingNo(),
            Messages.getString("web.action.back.order.ShippingListClearShippingDirectAction.2")));
        continue;
      }

      if (shippingHeader.getFixedSalesStatus().equals(FixedSalesStatus.FIXED.longValue())) {
        // 売上確定ステータスが「確定済み」の場合はステータスの変更不可
        errorMessageDetailList.add(WebMessage.get(OrderErrorMessage.FIXED_DATA_WITH_NO, shippingHeader.getOrderNo()));
        continue;
      }

      if (ohDto.getDataTransportStatus().equals(DataTransportStatus.TRANSPORTED.longValue())) {
        // データ連携済みステータスが「連携済み」の場合はステータスの変更不可
        errorMessageDetailList.add(WebMessage.get(OrderErrorMessage.SHIPPING_DATA_TRANSPORTED_WITH_NO, shippingHeader
            .getShippingNo()));
        continue;
      }

      if (!shippingHeader.getShippingStatus().equals(ShippingStatus.IN_PROCESSING.longValue())) {
        // 出荷ステータスが「出荷手配中」の場合以外は、ステータスの変更不可
        errorMessageDetailList.add(WebMessage.get(OrderErrorMessage.NO_STATUS_SHIPPING_ARRANGE, shippingHeader.getShippingNo()));
        continue;
      }

      shippingHeader.setShippingStatus(NumUtil.toLong(ShippingStatus.READY.getValue()));
      shippingHeader.setShippingDirectDate(null);
      shippingHeader.setUpdatedUser(getLoginInfo().getLoginId());

      Date orderUpdatedDatetime = null;

      for (ShippingSearchedListBean sl : reqBean.getList()) {
        if (sl.getShippingNo().equals(shippingNo)) {
          shippingHeader.setUpdatedDatetime(sl.getUpdatedDatetime());
          orderUpdatedDatetime = sl.getOrderUpdatedDatetime();
          if (clearedShippingOrderInfoMap.containsKey(ohDto.getOrderNo())) {
            orderUpdatedDatetime = clearedShippingOrderInfoMap.get(ohDto.getOrderNo());
          }
        }
      }

      InputShippingReport report = new InputShippingReport();
      report.setOrderUpdatedDatetime(orderUpdatedDatetime);
      report.setShippingNo(shippingNo);

      serviceResult = service.clearShippingDirection(report);
      if (serviceResult.hasError()) {
        for (ServiceErrorContent errorContent : serviceResult.getServiceErrorList()) {
          if (errorContent.equals(CommonServiceErrorContent.NO_DATA_ERROR)) {
            errorMessageDetailList.add(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, MessageFormat.format(
                Messages.getString("web.action.back.order.ShippingListClearShippingDirectAction.0"),
                shippingHeader.getShippingNo())));
          } else if (errorContent.equals(CommonServiceErrorContent.VALIDATION_ERROR)) {
            return BackActionResult.SERVICE_VALIDATION_ERROR;
          } else if (errorContent.equals(CommonServiceErrorContent.INPUT_ADEQUATE_ERROR)) {
            errorMessageDetailList.add(WebMessage.get(OrderErrorMessage.FIXED_SHIPPING_STATUS_WITH_NO, shippingHeader
                .getShippingNo()));
          } else if (errorContent.equals(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR)) {
            return BackActionResult.SERVICE_ERROR;
          }
        }
      } else {
        reqBean.setSeccessUpdate(true);
        OrderHeader updatedOrderHeader = service.getOrderHeader(ohDto.getOrderNo());
        clearedShippingOrderInfoMap.put(ohDto.getOrderNo(), updatedOrderHeader.getUpdatedDatetime());
      }
    }

    // エラーメッセージを保持
    reqBean.setErrorMessageDetailList(errorMessageDetailList);

    setNextUrl("/app/order/shipping_list/search_back/register");

    setRequestBean(reqBean);

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    boolean result = true;
    ShippingListBean reqBean = getBean();

    if (reqBean.getShippingCheck().isEmpty()) {
      addErrorMessage(WebMessage.get(ActionErrorMessage.NO_CHECKED,
          Messages.getString("web.action.back.order.ShippingListClearShippingDirectAction.3")));
      result = false;
    }
    return result;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.order.ShippingListClearShippingDirectAction.4");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "1102041001";
  }

}
