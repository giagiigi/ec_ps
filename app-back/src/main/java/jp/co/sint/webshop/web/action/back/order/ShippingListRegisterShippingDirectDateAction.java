package jp.co.sint.webshop.web.action.back.order;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.sint.webshop.data.domain.DataTransportStatus;
import jp.co.sint.webshop.data.domain.FixedSalesStatus;
import jp.co.sint.webshop.data.domain.OrderStatus;
import jp.co.sint.webshop.data.domain.ShippingStatus;
import jp.co.sint.webshop.data.dto.OrderHeader;
import jp.co.sint.webshop.data.dto.ShippingHeader;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.OrderService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.customer.CustomerConstant;
import jp.co.sint.webshop.service.order.InputShippingReport;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
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
public class ShippingListRegisterShippingDirectDateAction extends ShippingListBaseAction {

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
          Messages.getString("web.action.back.order.ShippingListRegisterShippingDirectDateAction.0")));
      result = false;
    }

    if (!validateBean(reqBean.getEdit())) {
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

    ShippingListBean reqBean = getBean();
    reqBean.setSeccessUpdate(false);
    reqBean.getErrorMessageDetailList().clear();

    OrderService service = ServiceLocator.getOrderService(getLoginInfo());

    List<String> errorMessageDetailList = new ArrayList<String>();
    Map<String, Date> registeredShippingOrderInfoMap = new HashMap<String, Date>();

    for (String shippingNo : reqBean.getShippingCheck()) {
      ServiceResult serviceResult = null;

      ShippingHeader shippingHeader = service.getShippingHeader(shippingNo);

      if (shippingHeader == null) {
        errorMessageDetailList.add(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
            MessageFormat.format(Messages.getString("web.action.back.order.ShippingListRegisterShippingDirectDateAction.1"),
                shippingNo)));
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
            Messages.getString("web.action.back.order.ShippingListRegisterShippingDirectDateAction.2")));
        continue;
      }

      if (NumUtil.toString(shippingHeader.getFixedSalesStatus()).equals(FixedSalesStatus.FIXED.getValue())) {
        // 売上確定ステータスが「確定済み」の場合はステータスの変更不可
        errorMessageDetailList.add(WebMessage.get(OrderErrorMessage.FIXED_DATA_WITH_NO, shippingHeader.getOrderNo()));
        continue;
      }

      InputShippingReport shippedResult = new InputShippingReport();
      shippedResult.setShippingNo(shippingNo);
      shippedResult.setShippingDirectDate(DateUtil.fromString(reqBean.getEdit().getInputShippingDate()));
      shippedResult.setUpdatedUser(getLoginInfo().getLoginId());

      OrderHeader orderHeader = service.getOrderHeader(shippingHeader.getOrderNo());

      for (ShippingSearchedListBean sl : reqBean.getList()) {
        if (sl.getShippingNo().equals(shippingNo)) {
          Date orderUpdatedDatetime = sl.getOrderUpdatedDatetime();
          // 同一受注の出荷情報が既に更新されていた場合は、更新以前の受注情報の更新日付を取得
          if (registeredShippingOrderInfoMap.containsKey(orderHeader.getOrderNo())) {
            orderUpdatedDatetime = registeredShippingOrderInfoMap.get(orderHeader.getOrderNo());
          }
          shippedResult.setUpdatedDatetime(sl.getUpdatedDatetime());
          shippedResult.setOrderUpdatedDatetime(orderUpdatedDatetime);
        }
      }

      if (orderHeader == null) {
        errorMessageDetailList.add(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
            MessageFormat.format(Messages.getString(
                "web.action.back.order.ShippingListRegisterShippingDirectDateAction.3"),
                shippingHeader.getOrderNo())));
        continue;
      }

      if (NumUtil.toString(orderHeader.getDataTransportStatus()).equals(DataTransportStatus.TRANSPORTED.getValue())) {
        // データ連携済みステータスが「連携済み」の場合はステータスの変更不可
        errorMessageDetailList.add(WebMessage.get(OrderErrorMessage.SHIPPING_DATA_TRANSPORTED_WITH_NO, shippingHeader
            .getShippingNo()));
        continue;
      }

      if (!NumUtil.toString(shippingHeader.getShippingStatus()).equals(ShippingStatus.READY.getValue())) {
        if (reqBean.getEdit().isUpdateWithShipping()) {
          // 「出荷可能」の場合のみ出荷指示可能
          errorMessageDetailList.add(WebMessage
              .get(OrderErrorMessage.IMPOSSIBILITY_SHIPPING_DIRECT, shippingHeader.getShippingNo()));
        } else {
          errorMessageDetailList.add(WebMessage.get(OrderErrorMessage.IMPOSSIBILITY_SHIPPING_DIRECT_DATE, shippingHeader
              .getShippingNo()));
        }
        continue;
      }

      // M17N 10361 追加 ここから
      OrderStatus status = OrderStatus.fromValue(orderHeader.getOrderStatus());
      if (status == OrderStatus.PHANTOM_ORDER || status == OrderStatus.PHANTOM_RESERVATION) {
        errorMessageDetailList.add(WebMessage.get(OrderErrorMessage.SHIPPING_IS_PHANTOM, shippingHeader.getShippingNo()));
        continue;
      }
      // M17N 10361 追加 ここまで
      
      if (reqBean.getEdit().isUpdateWithShipping()) {
        // 出荷手配してしまう場合のみチェックする項目
        if (orderHeader.getOrderStatus().equals(OrderStatus.RESERVED.longValue())) {
          errorMessageDetailList.add(WebMessage.get(OrderErrorMessage.SHIPPING_IS_RESERVED));
          continue;
        }
      }

      if (shippedResult.getShippingDirectDate().before(DateUtil.truncateDate(orderHeader.getOrderDatetime()))) {
        // 出荷指示日が受注日よりも前の日付の場合はエラー
        errorMessageDetailList.add(WebMessage.get(OrderErrorMessage.SHIPPING_INPUT_ORDERDATE_OR_LATER_WITH_NO, shippingHeader
            .getShippingNo(), Messages.getString(
                "web.action.back.order.ShippingListRegisterShippingDirectDateAction.4")));
        continue;
      }

      serviceResult = service.registerShippingDirectionDate(shippedResult, reqBean.getEdit().isUpdateWithShipping());
      if (serviceResult != null) {
        if (serviceResult.hasError()) {
          for (ServiceErrorContent errorContent : serviceResult.getServiceErrorList()) {
            if (errorContent.equals(CommonServiceErrorContent.NO_DATA_ERROR)) {
              errorMessageDetailList.add(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
                  MessageFormat.format(Messages.getString("web.action.back.order.ShippingListRegisterShippingDirectDateAction.1"),
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
          OrderHeader updatedOrderHeader = service.getOrderHeader(orderHeader.getOrderNo());
          registeredShippingOrderInfoMap.put(orderHeader.getOrderNo(), updatedOrderHeader.getUpdatedDatetime());
        }
      }
    }

    // エラーメッセージを保持
    reqBean.setErrorMessageDetailList(errorMessageDetailList);

    setNextUrl("/app/order/shipping_list/search_back/register");

    setRequestBean(reqBean);

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.order.ShippingListRegisterShippingDirectDateAction.5");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "1102041008";
  }

}
