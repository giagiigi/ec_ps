package jp.co.sint.webshop.web.action.back.order;

import java.util.Date;

import jp.co.sint.webshop.data.domain.DataTransportStatus;
import jp.co.sint.webshop.data.domain.FixedSalesStatus;
import jp.co.sint.webshop.data.domain.OrderStatus;
import jp.co.sint.webshop.data.domain.ReturnItemType;
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
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.order.ShippingDetailBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.message.back.InformationMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.message.back.order.OrderErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1020420:出荷管理明細のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class ShippingDetailRegisterShippingReportAction extends WebBackAction<ShippingDetailBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    boolean auth = false;
    BackLoginInfo login = getLoginInfo();
    if (Permission.SHIPPING_UPDATE_SITE.isGranted(login) || Permission.SHIPPING_UPDATE_SHOP.isGranted(login)) {
      auth = true;
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

    ShippingDetailBean reqBean = getBean();

    InputShippingReport shippingReport = new InputShippingReport();
    shippingReport.setShippingNo(reqBean.getDeliveryHeader().getShippingNo());
    shippingReport.setShippingDate(DateUtil.fromString(reqBean.getDeliveryHeader().getShippingDate()));
    shippingReport.setArrivalDate(DateUtil.fromString(reqBean.getDeliveryHeader().getArrivalDate()));
    shippingReport.setArrivalTimeStart(NumUtil.toLong(reqBean.getDeliveryHeader().getArrivalTimeStart(), null));
    shippingReport.setArrivalTimeEnd(NumUtil.toLong(reqBean.getDeliveryHeader().getArrivalTimeEnd(), null));
    shippingReport.setDeliverySlipNo(reqBean.getDeliveryHeader().getDeliverySlipNo());
    shippingReport.setUpdatedDatetime(reqBean.getDeliveryHeader().getShippingHeaderUpdatedDatetime());
    shippingReport.setUpdatedUser(getLoginInfo().getLoginId());
    shippingReport.setOrderUpdatedDatetime(reqBean.getOrderHeader().getOrderHeaderUpdatedDatetime());

    OrderService service = ServiceLocator.getOrderService(getLoginInfo());
    ShippingHeader shippingHeader = service.getShippingHeader(shippingReport.getShippingNo());
    if (shippingHeader == null) {
      addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
          Messages.getString("web.action.back.order.ShippingDetailRegisterShippingReportAction.0")));
      return BackActionResult.RESULT_SUCCESS;
    }

    OrderHeader orderHeader = service.getOrderHeader(shippingHeader.getOrderNo());
    if (orderHeader == null) {
      addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
          Messages.getString("web.action.back.order.ShippingDetailRegisterShippingReportAction.1")));
      return BackActionResult.RESULT_SUCCESS;
    }

    // 顧客退会チェック
    if (CustomerConstant.isCustomer(orderHeader.getCustomerCode())) {
      CustomerService customerService = ServiceLocator.getCustomerService(getLoginInfo());
      if (customerService.isWithdrawed(orderHeader.getCustomerCode())) {
        addErrorMessage(WebMessage.get(OrderErrorMessage.WITHDRAWAL_CUSTOMER, orderHeader.getOrderNo()));
        return BackActionResult.RESULT_SUCCESS;
      }
    }

    if (orderHeader.getOrderStatus().equals(OrderStatus.RESERVED.longValue())) {
      addErrorMessage(WebMessage.get(OrderErrorMessage.SHIPPING_IS_RESERVED));
      return BackActionResult.RESULT_SUCCESS;
    }

    if (NumUtil.toString(shippingHeader.getFixedSalesStatus()).equals(FixedSalesStatus.FIXED.getValue())) {
      // 売上確定ステータスが「確定済み」の場合はステータスの変更不可
      addErrorMessage(WebMessage.get(OrderErrorMessage.FIXED_DATA_WITH_NO, shippingHeader.getOrderNo()));
    }

    if (NumUtil.toString(orderHeader.getDataTransportStatus()).equals(DataTransportStatus.TRANSPORTED.getValue())) {
      // データ連携済みステータスが「連携済み」の場合はステータスの変更不可
      addErrorMessage(WebMessage.get(OrderErrorMessage.SHIPPING_DATA_TRANSPORTED_WITH_NO, shippingHeader.getShippingNo()));
    }

    if (shippingReport.getShippingDate().before(DateUtil.truncateDate(orderHeader.getOrderDatetime()))) {
      // 出荷日が受注日よりも前の日付の場合はエラー
      addErrorMessage(WebMessage.get(OrderErrorMessage.SHIPPING_INPUT_ORDERDATE_OR_LATER_WITH_NO, shippingHeader.getShippingNo(),
          Messages.getString("web.action.back.order.ShippingDetailRegisterShippingReportAction.2")));
    }

    if (shippingHeader.getShippingDirectDate() != null) {
      if (shippingReport.getShippingDate().before(shippingHeader.getShippingDirectDate())) {
        // 出荷日が出荷指示日よりも前の日付の場合はエラー
        addErrorMessage(WebMessage.get(OrderErrorMessage.SHIPPING_DIRECT_INPUT_ORDERDATE_OR_LATER_WITH_NO, shippingHeader
            .getShippingNo()));
      }
    }

    if (NumUtil.toString(shippingHeader.getShippingStatus()).equals(ShippingStatus.CANCELLED.getValue())) {
      // 出荷ステータスが「キャンセル」の場合は出荷実績登録不可
      addErrorMessage(WebMessage.get(OrderErrorMessage.SHIPPED_RESULT_FAILED_WITH_NO, shippingHeader.getShippingNo()));
    }

    // 出荷ステータスが「出荷手配中」「出荷済み」の場合のみ出荷日実績登録可能
    Long shippingStatus = shippingHeader.getShippingStatus();
    if (shippingStatus.equals(ShippingStatus.NOT_READY.longValue()) || shippingStatus.equals(ShippingStatus.READY.longValue())) {
      addErrorMessage(WebMessage.get(OrderErrorMessage.IMPOSSIBILITY_SHIPPING_REPORT, shippingHeader.getShippingNo()));
    }

    if (shippingHeader.getReturnItemType().equals(ReturnItemType.RETURNED.longValue())) {
      // 返品済み情報は変更不可
      addErrorMessage(WebMessage.get(OrderErrorMessage.UPDATE_RETURNED_SHIPPING));
    }

    if (!getDisplayMessage().getErrors().isEmpty()) {
      return BackActionResult.RESULT_SUCCESS;
    }

    ServiceResult result = service.registerShippingReport(shippingReport);

    if (result.hasError()) {
      for (ServiceErrorContent errorContent : result.getServiceErrorList()) {
        if (errorContent.equals(CommonServiceErrorContent.NO_DATA_ERROR)) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
              Messages.getString("web.action.back.order.ShippingDetailRegisterShippingReportAction.3")));
        } else if (errorContent.equals(CommonServiceErrorContent.INPUT_ADEQUATE_ERROR)) {
          addErrorMessage(WebMessage.get(OrderErrorMessage.SHIPPED_RESULT_FAILED_WITH_NO, shippingReport.getShippingNo()));
        } else if (errorContent.equals(CommonServiceErrorContent.VALIDATION_ERROR)) {
          return BackActionResult.SERVICE_VALIDATION_ERROR;
        } else if (errorContent.equals(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR)) {
          return BackActionResult.SERVICE_ERROR;
        }
      }
      setNextUrl(null);
    } else {
      setNextUrl("/app/order/shipping_detail/init/"
          + reqBean.getDeliveryHeader().getShippingNo() + "/register");
    }

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {

    ShippingDetailBean reqBean = getBean();
    if (validateItems(reqBean.getDeliveryHeader(),
        "shippingDate", "deliverySlipNo", "arrivalDate", "arrivalTimeStart",
        "arrivalTimeEnd")) {

      // 出荷日が未来日の場合エラーとする
      if (DateUtil.fromString(reqBean.getDeliveryHeader().getShippingDate()).after(DateUtil.getSysdate())) {
        addErrorMessage(WebMessage.get(OrderErrorMessage.WILL_SHIPPING_REPORT_DATE));
      }
    }

    Date shippingDate = DateUtil.fromString(reqBean.getDeliveryHeader().getShippingDate());
    Date arrivalDate = DateUtil.fromString(reqBean.getDeliveryHeader().getArrivalDate());

    if (shippingDate != null && arrivalDate != null) {
      if (arrivalDate.before(shippingDate)) {
        addErrorMessage(WebMessage.get(ActionErrorMessage.PERIOD_ERROR));
      }
    }

    if (StringUtil.hasValue(reqBean.getDeliveryHeader().getArrivalTimeStart())
        && StringUtil.isNullOrEmpty(reqBean.getDeliveryHeader().getArrivalTimeEnd())) {
      addErrorMessage(WebMessage.get(ActionErrorMessage.NO_CHECKED,
          Messages.getString("web.action.back.order.ShippingDetailRegisterShippingReportAction.4")));
    } else if (StringUtil.isNullOrEmpty(reqBean.getDeliveryHeader().getArrivalTimeStart())
        && StringUtil.hasValue(reqBean.getDeliveryHeader().getArrivalTimeEnd())) {
      addErrorMessage(WebMessage.get(ActionErrorMessage.NO_CHECKED,
          Messages.getString("web.action.back.order.ShippingDetailRegisterShippingReportAction.5")));
    }

    if (StringUtil.hasValue(reqBean.getDeliveryHeader().getArrivalTimeStart())
        && StringUtil.hasValue(reqBean.getDeliveryHeader().getArrivalTimeEnd())) {
      if (NumUtil.toPrimitiveLong(NumUtil.toLong(reqBean.getDeliveryHeader().getArrivalTimeStart())) > NumUtil
          .toPrimitiveLong(NumUtil.toLong(reqBean.getDeliveryHeader().getArrivalTimeEnd()))) {
        addErrorMessage(WebMessage.get(ActionErrorMessage.COMPARISON_ERROR,
            Messages.getString("web.action.back.order.ShippingDetailRegisterShippingReportAction.6")));
      }
    }

    return getDisplayMessage().getErrors().isEmpty();
  }

  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {
    ShippingDetailBean reqBean = getBean();
    BackLoginInfo login = getLoginInfo();

    boolean displayFlg = false;
    if (Permission.SHIPPING_UPDATE_SITE.isGranted(login) || Permission.SHIPPING_UPDATE_SHOP.isGranted(login)) {
      displayFlg = true;
    } else {
      displayFlg = false;
    }
    CustomerService customerService = ServiceLocator.getCustomerService(getLoginInfo());
    if (CustomerConstant.isCustomer(reqBean.getOrderHeader().getCustomerCode())
        && customerService.isWithdrawed(reqBean.getOrderHeader().getCustomerCode())) {
      displayFlg = false;
    }

    if (DataTransportStatus.fromValue(reqBean.getOrderHeader().getDataTransportStatus()).equals(DataTransportStatus.TRANSPORTED)) {
      addInformationMessage(WebMessage.get(InformationMessage.DATA_TRANSPORTED));
    }

    reqBean.setDisplayFlg(displayFlg);
    setRequestBean(reqBean);

  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.order.ShippingDetailRegisterShippingReportAction.7");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "1102042003";
  }

}
