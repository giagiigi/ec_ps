package jp.co.sint.webshop.web.action.back.order;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.domain.DataTransportStatus;
import jp.co.sint.webshop.data.domain.DeliveryDateType;
import jp.co.sint.webshop.data.domain.FixedSalesStatus;
import jp.co.sint.webshop.data.domain.InvoiceFlg;
import jp.co.sint.webshop.data.domain.InvoiceSaveFlg;
import jp.co.sint.webshop.data.domain.InvoiceType;
import jp.co.sint.webshop.data.domain.OperatingMode;
import jp.co.sint.webshop.data.domain.OrderFlg;
import jp.co.sint.webshop.data.domain.OrderType;
import jp.co.sint.webshop.data.domain.ShippingStatus;
import jp.co.sint.webshop.data.dto.CommodityHeader;
import jp.co.sint.webshop.data.dto.CustomerVatInvoice;
import jp.co.sint.webshop.data.dto.JdOrderHeader;
import jp.co.sint.webshop.data.dto.JdShippingHeader;
import jp.co.sint.webshop.data.dto.OrderHeader;
import jp.co.sint.webshop.data.dto.OrderInvoice;
import jp.co.sint.webshop.data.dto.ShippingDetail;
import jp.co.sint.webshop.data.dto.ShippingHeader;
import jp.co.sint.webshop.data.dto.TmallOrderHeader;
import jp.co.sint.webshop.data.dto.TmallShippingHeader;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.OrderService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.service.cart.CartCommodityInfo;
import jp.co.sint.webshop.service.cart.CashierShipping;
import jp.co.sint.webshop.service.customer.CustomerConstant;
import jp.co.sint.webshop.service.order.OrderContainer;
import jp.co.sint.webshop.service.order.ShippingContainer;
import jp.co.sint.webshop.service.result.OrderServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.validation.BeanValidator;
import jp.co.sint.webshop.validation.PhoneValidator;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.order.OrderDetailBean;
import jp.co.sint.webshop.web.bean.back.order.OrderDetailBean.InvoiceBean;
import jp.co.sint.webshop.web.bean.back.order.OrderDetailBean.ShippingHeaderBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.message.back.ValidationMessage;
import jp.co.sint.webshop.web.message.back.order.OrderErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1020220:受注管理明細のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class OrderDetailUpdateAction extends OrderDetailBaseAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    boolean authorization = false;

    if (getConfig().getOperatingMode().equals(OperatingMode.MALL) || getConfig().getOperatingMode().equals(OperatingMode.ONE)) {
      authorization = Permission.ORDER_UPDATE_SHOP.isGranted(getLoginInfo())
          || Permission.ORDER_UPDATE_SITE.isGranted(getLoginInfo());
    } else if (getConfig().getOperatingMode().equals(OperatingMode.SHOP)) {
      authorization = true;
    }

    return authorization;
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    boolean validation = true;

    OrderDetailBean bean = getBean();

    // soukai add 2012/01/08 ob start
    UtilService service = ServiceLocator.getUtilService(getLoginInfo());
    // if orderType = JD
    if(bean.getOrderType() == "3") {
      bean.setAddressScript(service.createJdAddressScript());
      bean.setAddressPrefectureList(service.createJdPrefectureList());
      bean.setAddressCityList(service.createJdCityList(bean.getOrderHeaderEdit().getPrefectureCode()));
      bean.setAddressAreaList(service.createJdAreaList(bean.getOrderHeaderEdit().getPrefectureCode(), bean.getOrderHeaderEdit()
        .getCityCode()));
    }
    // if orderType = EC or TMALL
    else {
      bean.setAddressScript(service.createAddressScript());
      bean.setAddressPrefectureList(service.createPrefectureList());
      bean.setAddressCityList(service.createCityList(bean.getOrderHeaderEdit().getPrefectureCode()));
      bean.setAddressAreaList(service.createAreaList(bean.getOrderHeaderEdit().getPrefectureCode(), bean.getOrderHeaderEdit()
        .getCityCode()));
    }
    
    // soukai add 2012/01/08 ob end
    // 受注情報が更新可能な場合のみ、受注情報のvalidationチェックを行う。
    if (bean.getOrderInformationDisplayMode().equals(WebConstantCode.DISPLAY_EDIT)) {

      // validation &= validateBean(bean.getPaymentEdit());
      // validation &= validateBean(bean.getOrderHeaderEdit());
      PhoneValidator phoneValidator = new PhoneValidator();
      boolean phoneResult = false;

      // if(StringUtil.isNullOrEmpty(bean.getOrderHeaderEdit().getCustomerTel1())
      // &&
      // StringUtil.isNullOrEmpty(bean.getOrderHeaderEdit().getCustomerTel2())
      // &&
      // StringUtil.isNullOrEmpty(bean.getOrderHeaderEdit().getCustomerTel3())
      // &&
      // StringUtil.isNullOrEmpty(bean.getOrderHeaderEdit().getCustomerMobile())){
      // addErrorMessage(WebMessage.get(ValidationMessage.NO_NUMBER));
      // validation &= false;
      // }else{
      // if(StringUtil.hasValue(bean.getOrderHeaderEdit().getCustomerTel1()) ||
      // StringUtil.hasValue(bean.getOrderHeaderEdit().getCustomerTel2()) ||
      // StringUtil.hasValue(bean.getOrderHeaderEdit().getCustomerTel3())){
      // if(!(StringUtil.hasValueAllOf(bean.getOrderHeaderEdit().getCustomerTel1(),bean.getOrderHeaderEdit().getCustomerTel2())
      // && bean.getOrderHeaderEdit().getCustomerTel1().length()>1 &&
      // bean.getOrderHeaderEdit().getCustomerTel2().length()>5)){
      // addErrorMessage(WebMessage.get(ValidationMessage.TRUE_NUMBER));
      // validation = false;
      // }else{
      // phoneResult = phoneValidator.isValid(StringUtil.joint('-',
      // bean.getOrderHeaderEdit().getCustomerTel1(),
      // bean.getOrderHeaderEdit().getCustomerTel2(),bean.getOrderHeaderEdit().getCustomerTel3()));
      // if (!phoneResult) {
      // addErrorMessage(phoneValidator.getMessage());
      // }
      // validation &= phoneResult;
      // }
      // }
      // }

      List<String> errorMessageList = BeanValidator.validate(bean.getOrderHeaderEdit()).getErrorMessages();
      if (errorMessageList.size() > 0) {
        validation &= false;
        for (String s : errorMessageList) {
          addErrorMessage(MessageFormat.format(Messages.getString("web.action.back.order.OrderDetailUpdateAction.0"), s));
        }
      }
      // add by lc 2012-03-08 start
      if (StringUtil.getLength(bean.getOrderHeaderEdit().getCustomerLastName()) > 28) {
        addErrorMessage(WebMessage.get(ValidationMessage.NAME_LENGTH_ERR, Messages
            .getString("web.action.back.order.OrderDetailUpdateAction.customerName")));
        validation &= false;
      }
      // add by lc 2012-03-08 end
      // Add by V10-CH start
      if (StringUtil.isNullOrEmpty(bean.getOrderHeaderEdit().getCustomerTel1())
          && StringUtil.isNullOrEmpty(bean.getOrderHeaderEdit().getCustomerTel2())
          && StringUtil.isNullOrEmpty(bean.getOrderHeaderEdit().getCustomerTel3())
          && StringUtil.isNullOrEmpty(bean.getOrderHeaderEdit().getCustomerMobile())) {
        addErrorMessage(Messages.getString("web.action.back.order.OrderDetailUpdateAction.0")
            + WebMessage.get(ValidationMessage.NO_NUMBER));
        validation = false;
      }
      if (!StringUtil.hasValueAllOf(bean.getOrderHeaderEdit().getCustomerTel1(), bean.getOrderHeaderEdit().getCustomerTel2())) {
        phoneResult = phoneValidator.isValid(StringUtil.joint('-', bean.getOrderHeaderEdit().getCustomerTel1(), bean
            .getOrderHeaderEdit().getCustomerTel2(), bean.getOrderHeaderEdit().getCustomerTel3()));
        if (!phoneResult) {
          addErrorMessage(Messages.getString("web.action.back.order.OrderDetailUpdateAction.7")
              + WebMessage.get(ValidationMessage.TRUE_NUMBER));
          validation = false;
        }
      }
      // Add by V10-CH end
    }

    // 出荷情報が更新可能な場合のみ、出荷情報のvalidationチェックを行う。
    if (bean.getShippingInformationDisplayMode().equals(WebConstantCode.DISPLAY_EDIT)) {

      PhoneValidator phoneValidator = new PhoneValidator();
      boolean phoneResult = false;

      for (ShippingHeaderBean shippingHeader : bean.getShippingList()) {
        List<String> shippingErrorMessageList = BeanValidator.validate(shippingHeader).getErrorMessages();
        if (shippingErrorMessageList.size() > 0) {
          validation &= false;
          for (String s : shippingErrorMessageList) {
            addErrorMessage(MessageFormat.format(Messages.getString("web.action.back.order.OrderDetailUpdateAction.1"),
                shippingHeader.getShippingNo(), s));
          }
        }

        if (StringUtil.getLength(shippingHeader.getShippingLastName()) > 28) {
          addErrorMessage(WebMessage.get(ValidationMessage.NAME_LENGTH_ERR, Messages
              .getString("web.action.back.order.OrderDetailUpdateAction.shipName")));
          validation &= false;
        }

        if (StringUtil.getLength(shippingHeader.getShippingAddress4()) > 200) {
          addErrorMessage(WebMessage.get(ValidationMessage.ADDRESS_LENGTH_ERR, Messages
              .getString("web.action.back.order.OrderDetailUpdateAction.shippingAddress4")));
          validation &= false;
        }

        // Add by V10-CH start
        if (StringUtil.isNullOrEmpty(shippingHeader.getShippingTel1())
            && StringUtil.isNullOrEmpty(shippingHeader.getShippingTel2())
            && StringUtil.isNullOrEmpty(shippingHeader.getShippingTel3())
            && StringUtil.isNullOrEmpty(shippingHeader.getMobileTel())) {
          addErrorMessage(MessageFormat.format(Messages.getString("web.action.back.order.OrderDetailUpdateAction.1"),
              shippingHeader.getShippingNo(), "")
              + WebMessage.get(ValidationMessage.NO_NUMBER));
          validation &= false;
        }
        if (!StringUtil.hasValueAllOf(shippingHeader.getShippingTel1(), shippingHeader.getShippingTel2())) {
          phoneResult = phoneValidator.isValid(StringUtil.joint('-', shippingHeader.getShippingTel1(), shippingHeader
              .getShippingTel2(), shippingHeader.getShippingTel3()));
          if (!phoneResult) {
            addErrorMessage(MessageFormat.format(Messages.getString("web.action.back.order.OrderDetailUpdateAction.1"),
                shippingHeader.getShippingNo(), "")
                + WebMessage.get(ValidationMessage.TRUE_NUMBER));
            validation = false;
          }
        }
        // else{
        // if(StringUtil.hasValue(shippingHeader.getShippingTel1()) ||
        // StringUtil.hasValue(shippingHeader.getShippingTel2()) ||
        // StringUtil.hasValue(shippingHeader.getShippingTel3())){
        // if(!(StringUtil.hasValueAllOf(shippingHeader.getShippingTel1(),shippingHeader.getShippingTel2())
        // && shippingHeader.getShippingTel1().length()>1 &&
        // shippingHeader.getShippingTel2().length()>5)){
        // addErrorMessage(WebMessage.get(ValidationMessage.TRUE_NUMBER));
        // validation = false;
        // }
        // }
        // }
        // Add by V10-CH end

        // soukai delete 2012/01/09 ob start
        // 配送希望日チェック
        /*
         * String ymd = shippingHeader.getDeliveryAppointedDate(); if
         * (validation && StringUtil.hasValue(ymd)) { //
         * 変種可能な状態の場合当日もエラーとする為、afterを使いNot判定とする if
         * (shippingHeader.getDisplayMode().equals(WebConstantCode.DISPLAY_EDIT)
         * && !DateUtil.fromString(ymd).after(DateUtil.getSysdate())) {
         * addErrorMessage
         * (WebMessage.get(OrderErrorMessage.PAST_DELIVERY_APPOINTED_DATE));
         * validation = false; } } String startTime =
         * shippingHeader.getDeliveryAppointedStartTime(); String endTime =
         * shippingHeader.getDeliveryAppointedEndTime(); if (validation &&
         * (StringUtil.hasValueAnyOf(startTime, endTime))) { if
         * (StringUtil.isNullOrEmptyAnyOf(startTime, endTime)) {
         * addErrorMessage(
         * WebMessage.get(OrderErrorMessage.DELIVERY_APPOINTED_TIME_BOTH));
         * validation = false; } else if
         * (NumUtil.toLong(shippingHeader.getDeliveryAppointedStartTime()) >=
         * NumUtil.toLong(shippingHeader.getDeliveryAppointedEndTime())) { //
         * 配送希望日時間FromToチェック
         * addErrorMessage(WebMessage.get(ActionErrorMessage.COMPARISON_ERROR,
         * Messages
         * .getString("web.action.back.order.OrderDetailUpdateAction.2")));
         * validation = false; } }
         */
        // soukai delete 2012/01/09 ob end
      }
    }

    // もし都道府県が変更されていた場合、エラーとする
    for (ShippingHeaderBean shippingHeaderBean : bean.getShippingList()) {
      if (!shippingHeaderBean.getOrgShippingPrefectureCode().equals(shippingHeaderBean.getShippingPrefectureCode())) {
        addErrorMessage(WebMessage.get(OrderErrorMessage.CHANGE_PREFECTURE_SHIPPING, shippingHeaderBean.getShippingNo()));
        validation = false;
      }
    }
    // add by V10-CH 170 start
    UtilService s = ServiceLocator.getUtilService(getLoginInfo());
    bean.getOrderHeaderEdit().setCityList(s.getCityNames(bean.getOrderHeaderEdit().getPrefectureCode()));
    // add by V10-CH 170 end
    // soukai 2011/12/29 add ob start
    // 订单发票验证
    InvoiceBean invoiceBean = getBean().getOrderInvoice();
    if (invoiceBean.getInvoiceFlg().equals(InvoiceFlg.NEED.getValue())) {
      // 领取发票
      if (invoiceBean.getInvoiceType().equals(InvoiceType.VAT.getValue())) {
        // 增值税发票
        validation &= validateItems(invoiceBean, "invoiceCommodityName", "invoiceCompanyName", "invoiceTaxpayerCode",
            "invoiceAddress", "invoiceTel", "invoiceBankName", "invoiceBankNo");
        // 2013-02-19 add by yyq 验证纳税人识别号必须是输入15或18或20位 start
        if (StringUtil.getLength(invoiceBean.getInvoiceTaxpayerCode()) != 15
            && StringUtil.getLength(invoiceBean.getInvoiceTaxpayerCode()) != 18
            && StringUtil.getLength(invoiceBean.getInvoiceTaxpayerCode()) != 20) {
          addErrorMessage("纳税人识别号位数不正确，有效长度为15、18、20位");
          validation &= false;
        }
        // 2013-02-19 add by yyq 验证纳税人识别号必须是输入15或18或20位 end
      } else {
        // 通常发票
        validation &= validateItems(invoiceBean, "invoiceCommodityName", "invoiceType", "invoiceCustomerName");
      }
      // add by lc 2012-03-09 start
      if (StringUtil.getLength(invoiceBean.getInvoiceCustomerName()) > 70) {
        addErrorMessage(WebMessage.get(ValidationMessage.INVOICE_LENGTH_ERR, "发票内容(发票抬头)"));
        validation &= false;
      }

      if (StringUtil.getLength(invoiceBean.getInvoiceCompanyName()) > 70) {
        addErrorMessage(WebMessage.get(ValidationMessage.INVOICE_LENGTH_ERR, "发票内容(公司名称)"));
        validation &= false;
      }

      if (StringUtil.getLength(invoiceBean.getInvoiceAddress()) > 200) {
        addErrorMessage(WebMessage.get(ValidationMessage.ADDRESS_LENGTH_ERR, "发票内容(住所)"));
        validation &= false;
      }

      if (StringUtil.getLength(invoiceBean.getInvoiceBankName()) > 70) {
        addErrorMessage(WebMessage.get(ValidationMessage.INVOICE_LENGTH_ERR, "发票内容(银行名称)"));
        validation &= false;
      }
      // add by lc 2012-03-09 end
    }
    // soukai 2011/12/29 add ob end
//    if(!StringUtil.isNullOrEmpty(bean.getOrderType())){
//      if (OrderType.EC.getValue().equals(bean.getOrderType())) {
//        if(!StringUtil.isNullOrEmpty(bean.getOrderFlg()) && OrderFlg.GROUPCHECKED.getValue().equals(bean.getOrderFlg())){
//          addErrorMessage("官网订单无法更新为Tmall订单拦截状态，只允许设定为未检查或已检查。");
//          validation &= false;
//        }
//      }
//    }
    return validation;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    OrderService service = ServiceLocator.getOrderService(getLoginInfo());
    OrderDetailBean bean = getBean();

    // soukai edit 2012/1/7 ob start
    ServiceResult result = null;

    // TMALL受注情報の更新場合
    if (OrderType.TMALL.getValue().equals(bean.getOrderType())) {
      TmallOrderHeader tmallOrderHeaderInfo = service.getTmallOrderHeader(bean.getOrderNo());
      // データ存在チェック
      if (tmallOrderHeaderInfo == null) {
        addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, Messages
            .getString("web.action.back.order.OrderDetailUpdateAction.3")));
        setRequestBean(bean);
        return BackActionResult.RESULT_SUCCESS;
      }
      // 配送指定日から出荷指示日を計算し、受注日以前の日付の場合エラー
      // UtilService utilService =
      // ServiceLocator.getUtilService(getLoginInfo());

      // 顧客退会チェック
      if (CustomerConstant.isCustomer(tmallOrderHeaderInfo.getCustomerCode())) {
        CustomerService customerService = ServiceLocator.getCustomerService(getLoginInfo());
        if (customerService.isWithdrawed(tmallOrderHeaderInfo.getCustomerCode())) {
          addErrorMessage(WebMessage.get(OrderErrorMessage.WITHDRAWAL_CUSTOMER, tmallOrderHeaderInfo.getOrderNo()));
          setRequestBean(bean);
          return BackActionResult.RESULT_SUCCESS;
        }
      }
      // データ連携済チェック
      if (DataTransportStatus.fromValue(tmallOrderHeaderInfo.getDataTransportStatus()).equals(DataTransportStatus.TRANSPORTED)) {
        addErrorMessage(WebMessage.get(OrderErrorMessage.DATA_TRANSPORTED));
        setRequestBean(bean);
        return BackActionResult.RESULT_SUCCESS;
      }
  

      OrderContainer tmallOrderContainer = service.getTmallOrder(bean.getOrderNo());
      // 受注情報が更新可能な場合、受注情報をセット
      if (bean.getOrderInformationDisplayMode().equals(WebConstantCode.DISPLAY_EDIT)) {
        setupTmallOrderHeaderInfo(tmallOrderHeaderInfo, bean);
        tmallOrderContainer.setTmallOrderHeader(tmallOrderHeaderInfo);
      }

      // 出荷情報が更新可能な場合、出荷情報をセット
      if (bean.getShippingInformationDisplayMode().equals(WebConstantCode.DISPLAY_EDIT)) {
        List<ShippingContainer> tmallShippingContainerList = new ArrayList<ShippingContainer>();
        for (ShippingHeaderBean shippingHeader : bean.getShippingList()) {
          TmallShippingHeader tmallShippingHeaderInfo = service.getTmallShippingHeader(shippingHeader.getShippingNo());
          // データ存在チェック
          if (tmallShippingHeaderInfo == null) {
            addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, Messages
                .getString("web.action.back.order.OrderDetailUpdateAction.4")));
            setRequestBean(bean);
            return BackActionResult.RESULT_SUCCESS;
          }
          // 売上確定済み出荷の場合警告を出力する
          if (tmallShippingHeaderInfo.getFixedSalesStatus().equals(FixedSalesStatus.FIXED.longValue())) {
            addWarningMessage(WebMessage.get(OrderErrorMessage.FIXED_DATA_WITH_NO, tmallShippingHeaderInfo.getShippingNo()));
            continue;
          }
          if (tmallShippingHeaderInfo.getShippingStatus().equals(ShippingStatus.IN_PROCESSING.longValue())
              || tmallShippingHeaderInfo.getShippingStatus().equals(ShippingStatus.SHIPPED.longValue())) {
            addErrorMessage("该订单目前已发货，无法进行更新");
            setRequestBean(bean);
            return BackActionResult.RESULT_SUCCESS;
          }
          tmallShippingHeaderInfo.setAddressLastName(shippingHeader.getShippingLastName());
          tmallShippingHeaderInfo.setAddressFirstName(shippingHeader.getShippingFirstName());
          tmallShippingHeaderInfo.setAddressLastNameKana(shippingHeader.getShippingLastNameKana());
          tmallShippingHeaderInfo.setAddressFirstNameKana(shippingHeader.getShippingFirstNameKana());
          tmallShippingHeaderInfo.setPostalCode(shippingHeader.getShippingPostalCode());
          tmallShippingHeaderInfo.setAddress3(shippingHeader.getShippingAddress3());
          tmallShippingHeaderInfo.setAddress4(shippingHeader.getShippingAddress4());
          // tmallShippingHeaderInfo.setPrefectureCode(shippingHeader.getShippingPrefectureCode());
          tmallShippingHeaderInfo.setAddress1(shippingHeader.getShippingAddress1());
          tmallShippingHeaderInfo.setAddress2(shippingHeader.getShippingAddress2());
          String shippingPhoneNumber = shippingHeader.getShippingTel();
          tmallShippingHeaderInfo.setPhoneNumber(shippingPhoneNumber);
          String shippingMobileNumber = shippingHeader.getMobileTel();
          tmallShippingHeaderInfo.setMobileNumber(shippingMobileNumber);
          // 修正前の配送指定日を取得
          // String beforeDeliveryAppointedDate =
          // tmallShippingHeaderInfo.getDeliveryAppointedDate();
          String ymd = shippingHeader.getDeliveryAppointedDate();
          if (DeliveryDateType.fromValue(ymd) != null) {
            ymd = DeliveryDateType.fromValue(ymd).getName();
          }
          tmallShippingHeaderInfo.setDeliveryAppointedDate(ymd);
          String startTime = shippingHeader.getDeliveryAppointedStartTime();
          String endTime = shippingHeader.getDeliveryAppointedEndTime();
          if (StringUtil.hasValue(startTime)) {
            tmallShippingHeaderInfo.setDeliveryAppointedTimeStart(NumUtil.toLong(startTime));
          } else {
            tmallShippingHeaderInfo.setDeliveryAppointedTimeStart(null);
          }
          if (StringUtil.hasValue(endTime)) {
            tmallShippingHeaderInfo.setDeliveryAppointedTimeEnd(NumUtil.toLong(endTime));
          } else {
            tmallShippingHeaderInfo.setDeliveryAppointedTimeEnd(null);
          }

          // soukai add 2012/03/27 ob start
          tmallShippingHeaderInfo.setDeliveryAppointedTimeStart(null);
          tmallShippingHeaderInfo.setDeliveryAppointedTimeEnd(null);
          if (StringUtil.hasValue(shippingHeader.getDeliveryDateTime()) && shippingHeader.getDeliveryDateTime().indexOf("-") >= 0) {
            String times[] = shippingHeader.getDeliveryDateTime().split("-");
            if (times.length > 1) {
              tmallShippingHeaderInfo.setDeliveryAppointedTimeStart(NumUtil.toLong(times[0]));
              tmallShippingHeaderInfo.setDeliveryAppointedTimeEnd(NumUtil.toLong(times[1]));
            } else if (shippingHeader.getDeliveryDateTime().indexOf("-") == 0) {
              tmallShippingHeaderInfo.setDeliveryAppointedTimeEnd(NumUtil.toLong(times[0]));
            } else {
              tmallShippingHeaderInfo.setDeliveryAppointedTimeStart(NumUtil.toLong(times[0]));
            }
          }
          // delete  by yyq 20130318 start
//          boolean codFlg = false;
//          if (PaymentMethodType.CASH_ON_DELIVERY.getValue().equals(tmallOrderHeaderInfo.getPaymentMethodType())) {
//            codFlg = true;
//          }
//          UtilService utilService = ServiceLocator.getUtilService(ServiceLoginInfo.getInstance());
//          DeliveryCompany deliveryCompany = utilService.getTmallDeliveryCompany(tmallShippingHeaderInfo.getShopCode(),
//              shippingHeader.getShippingPrefectureCode(), codFlg, shippingHeader.getDeliveryAppointedDate(), NumUtil
//                  .toString(tmallShippingHeaderInfo.getDeliveryAppointedTimeStart()), NumUtil.toString(tmallShippingHeaderInfo
//                  .getDeliveryAppointedTimeEnd()));
//          if (deliveryCompany == null || deliveryCompany.getDeliveryCompanyNo() == null) {
//            deliveryCompany = utilService.getDefaultDeliveryCompany();
//          }
//          if (deliveryCompany != null) {
//            tmallShippingHeaderInfo.setDeliveryCompanyNo(deliveryCompany.getDeliveryCompanyNo());
//            tmallShippingHeaderInfo.setDeliveryCompanyName(deliveryCompany.getDeliveryCompanyName());
//          }
          // delete  by yyq 20130318 end

          // soukai add 2012/03/27 ob end
          /*
           * // 配送指定日が設定されている場合出荷指示日を設定する if (ymd != null && !ymd.equals("")) {
           * DeliveryType deliveryType = new DeliveryType();
           * deliveryType.setShopCode(shippingHeader.getShippingShopCode());
           * deliveryType
           * .setDeliveryTypeNo(NumUtil.toLong(shippingHeader.getShippingTypeCode
           * ()));tmallShippingHeaderInfo.setShippingDirectDate(utilService.
           * createShippingDirectDate(DateUtil.fromString(ymd), deliveryType,
           * shippingHeader.getOrgShippingPrefectureCode())); //
           * 手動で出荷指示日が設定されていて、かつDB中に配送指定日がなく、かつ明細画面で配送日が選択されなかった場合 //
           * すでに設定済の出荷指示日を設定する。 } else if (StringUtil.isNullOrEmpty(ymd) &&
           * StringUtil.isNullOrEmpty(beforeDeliveryAppointedDate) &&
           * StringUtil.hasValue(shippingHeader.getShippingDirectDate())) {
           * tmallShippingHeaderInfo
           * .setShippingDirectDate(DateUtil.fromString(shippingHeader
           * .getShippingDirectDate())); } else {
           * tmallShippingHeaderInfo.setShippingDirectDate(null); }
           */
          tmallShippingHeaderInfo.setShippingDirectDate(null);
          tmallShippingHeaderInfo.setDeliveryRemark(shippingHeader.getDeliveryRemark());
          ShippingContainer shippingContainer = new ShippingContainer();
          shippingContainer.setTmallShippingHeader(tmallShippingHeaderInfo);
          for (ShippingContainer sContainer : tmallOrderContainer.getShippings()) {
            if (tmallShippingHeaderInfo.getShippingNo().equals(sContainer.getTmallShippingHeader().getShippingNo())) {
              shippingContainer.setTmallShippingDetails(sContainer.getTmallShippingDetails());
            }
          }
          tmallShippingContainerList.add(shippingContainer);
        }
        tmallOrderContainer.setShippings(tmallShippingContainerList);
      }
      // 订单发票取得
      OrderInvoice orderInvoiceInfo = service.getOrderInvoice(bean.getOrderNo());
      if (orderInvoiceInfo == null) {
        orderInvoiceInfo = new OrderInvoice();
      }
      CustomerVatInvoice customerVatInvoice = tmallOrderContainer.getCustomerVatInvoice();
      if (customerVatInvoice == null) {
        customerVatInvoice = new CustomerVatInvoice();
      }
      setupOrderInvoiceInfo(orderInvoiceInfo, customerVatInvoice, bean);
      tmallOrderContainer.setOrderInvoice(orderInvoiceInfo);
      tmallOrderContainer.setCustomerVatInvoice(customerVatInvoice);
      result = service.updateTmallOrderWithoutPayment(tmallOrderContainer);

    // 受注タイプがJDの場合
    } else if(OrderType.JD.getValue().equals(bean.getOrderType())) {
      
      
      JdOrderHeader jdOrderHeaderInfo = service.getJdOrderHeader(bean.getOrderNo());
      // データ存在チェック
      if (jdOrderHeaderInfo == null) {
        addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, Messages
            .getString("web.action.back.order.OrderDetailUpdateAction.3")));
        setRequestBean(bean);
        return BackActionResult.RESULT_SUCCESS;
      }
      // 配送指定日から出荷指示日を計算し、受注日以前の日付の場合エラー
      // UtilService utilService =
      // ServiceLocator.getUtilService(getLoginInfo());

      // 顧客退会チェック
      if (CustomerConstant.isCustomer(jdOrderHeaderInfo.getCustomerCode())) {
        CustomerService customerService = ServiceLocator.getCustomerService(getLoginInfo());
        if (customerService.isWithdrawed(jdOrderHeaderInfo.getCustomerCode())) {
          addErrorMessage(WebMessage.get(OrderErrorMessage.WITHDRAWAL_CUSTOMER, jdOrderHeaderInfo.getOrderNo()));
          setRequestBean(bean);
          return BackActionResult.RESULT_SUCCESS;
        }
      }
      // データ連携済チェック
      if (DataTransportStatus.fromValue(jdOrderHeaderInfo.getDataTransportStatus()).equals(DataTransportStatus.TRANSPORTED)) {
        addErrorMessage(WebMessage.get(OrderErrorMessage.DATA_TRANSPORTED));
        setRequestBean(bean);
        return BackActionResult.RESULT_SUCCESS;
      }
  

      OrderContainer jdOrderContainer = service.getJdOrder(bean.getOrderNo());
      // 受注情報が更新可能な場合、受注情報をセット
      if (bean.getOrderInformationDisplayMode().equals(WebConstantCode.DISPLAY_EDIT)) {
        //setupJdOrderHeaderInfo(jdOrderHeaderInfo, bean);

        if (StringUtil.hasValue(bean.getOrderFlg())) {
          jdOrderHeaderInfo.setOrderFlg(OrderFlg.fromValue(bean.getOrderFlg()).longValue());
        }
        jdOrderHeaderInfo.setCaution(bean.getOrderHeaderEdit().getCaution());
        
        jdOrderContainer.setJdOrderHeader(jdOrderHeaderInfo);
      }

      // 出荷情報が更新可能な場合、出荷情報をセット
      // 订单发票取得
      OrderInvoice orderInvoiceInfo = service.getOrderInvoice(bean.getOrderNo());
      if (orderInvoiceInfo == null) {
        orderInvoiceInfo = new OrderInvoice();
      }
      CustomerVatInvoice customerVatInvoice = jdOrderContainer.getCustomerVatInvoice();
      if (customerVatInvoice == null) {
        customerVatInvoice = new CustomerVatInvoice();
      }
      setupOrderInvoiceInfo(orderInvoiceInfo, customerVatInvoice, bean);
      jdOrderContainer.setOrderInvoice(orderInvoiceInfo);
      jdOrderContainer.setCustomerVatInvoice(customerVatInvoice);
      result = service.updateJdOrderWithoutPayment(jdOrderContainer);
      
      
    // 受注タイプがECの場合
    } else {
      OrderHeader orderHeaderInfo = service.getOrderHeader(bean.getOrderNo());
      // データ存在チェック
      if (orderHeaderInfo == null) {
        addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, Messages
            .getString("web.action.back.order.OrderDetailUpdateAction.3")));
        setRequestBean(bean);
        return BackActionResult.RESULT_SUCCESS;
      }

      // 配送指定日から出荷指示日を計算し、受注日以前の日付の場合エラー
      // UtilService utilService =
      // ServiceLocator.getUtilService(getLoginInfo());
      // soukai delete 2012/01/10 ob start
      /*
       * boolean hasShippingDirectError = false; for (ShippingHeaderBean header
       * : bean.getShippingList()) { if (header.getDeliveryAppointedDate() !=
       * null && StringUtil.hasValue(header.getDeliveryAppointedDate())) {
       * DeliveryType deliveryType = new DeliveryType();
       * deliveryType.setShopCode(header.getShippingShopCode());
       * deliveryType.setDeliveryTypeNo
       * (NumUtil.toLong(header.getShippingTypeCode())); Date shippingDirectDate
       * =utilService.createShippingDirectDate(DateUtil.fromString(header.
       * getDeliveryAppointedDate()), deliveryType,
       * header.getOrgShippingPrefectureCode()); // 出荷指示日が当日日付以前の場合はエラーとする if
       * (shippingDirectDate.before(orderHeaderInfo.getOrderDatetime())) {
       * addErrorMessage(WebMessage.get(OrderErrorMessage.
       * IMPOSSIBILITY_APPOINTED_DATE_CAUSE_DIRECT_PAST));
       * hasShippingDirectError = true; } } } if (hasShippingDirectError) {
       * setRequestBean(bean); return BackActionResult.RESULT_SUCCESS; }
       */
      // soukai delete 2012/01/10 ob end
      // 顧客退会チェック
      if (CustomerConstant.isCustomer(orderHeaderInfo.getCustomerCode())) {
        CustomerService customerService = ServiceLocator.getCustomerService(getLoginInfo());
        if (customerService.isWithdrawed(orderHeaderInfo.getCustomerCode())) {
          addErrorMessage(WebMessage.get(OrderErrorMessage.WITHDRAWAL_CUSTOMER, orderHeaderInfo.getOrderNo()));
          setRequestBean(bean);
          return BackActionResult.RESULT_SUCCESS;
        }
      }
      // データ連携済チェック
      // if
      // (DataTransportStatus.fromValue(orderHeaderInfo.getDataTransportStatus()).equals(DataTransportStatus.TRANSPORTED))
      // {
      // addErrorMessage(WebMessage.get(OrderErrorMessage.DATA_TRANSPORTED));
      // setRequestBean(bean);
      // return BackActionResult.RESULT_SUCCESS;
      // }
      OrderContainer orderContainer = service.getOrder(bean.getOrderNo());
      // 受注情報が更新可能な場合、受注情報をセット
      if (bean.getOrderInformationDisplayMode().equals(WebConstantCode.DISPLAY_EDIT)) {
        setupOrderHeaderInfo(orderHeaderInfo, bean);
        orderContainer.setOrderHeader(orderHeaderInfo);
      }
      // 出荷情報が更新可能な場合、出荷情報をセット
      if (bean.getShippingInformationDisplayMode().equals(WebConstantCode.DISPLAY_EDIT)) {
        List<ShippingContainer> shippingContainerList = new ArrayList<ShippingContainer>();
        for (ShippingHeaderBean shippingHeader : bean.getShippingList()) {
          ShippingHeader shippingHeaderInfo = service.getShippingHeader(shippingHeader.getShippingNo());
          // データ存在チェック
          if (shippingHeaderInfo == null) {
            addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, Messages
                .getString("web.action.back.order.OrderDetailUpdateAction.4")));
            setRequestBean(bean);
            return BackActionResult.RESULT_SUCCESS;
          }
          // 売上確定済み出荷の場合警告を出力する
          if (shippingHeaderInfo.getFixedSalesStatus().equals(FixedSalesStatus.FIXED.longValue())) {
            addWarningMessage(WebMessage.get(OrderErrorMessage.FIXED_DATA_WITH_NO, shippingHeaderInfo.getShippingNo()));
            continue;
          }
          if (shippingHeaderInfo.getShippingStatus().equals(ShippingStatus.IN_PROCESSING.longValue())
              || shippingHeaderInfo.getShippingStatus().equals(ShippingStatus.SHIPPED.longValue())) {
            addErrorMessage("该订单目前已发货，无法进行更新");
            setRequestBean(bean);
            return BackActionResult.RESULT_SUCCESS;
          }
          shippingHeaderInfo.setAddressLastName(shippingHeader.getShippingLastName());
          shippingHeaderInfo.setAddressFirstName(shippingHeader.getShippingFirstName());
          shippingHeaderInfo.setAddressLastNameKana(shippingHeader.getShippingLastNameKana());
          shippingHeaderInfo.setAddressFirstNameKana(shippingHeader.getShippingFirstNameKana());
          shippingHeaderInfo.setPostalCode(shippingHeader.getShippingPostalCode());
          shippingHeaderInfo.setAddress3(shippingHeader.getShippingAddress3());
          shippingHeaderInfo.setAddress4(shippingHeader.getShippingAddress4());
          // modify by V10-CH 170 start
          // shippingHeaderInfo.setAddress2(shippingHeader.getShippingAddress2());
          // UtilService s = ServiceLocator.getUtilService(getLoginInfo());
          // shippingHeaderInfo.setPrefectureCode(shippingHeader.getShippingPrefectureCode());
          // shippingHeaderInfo.setCityCode(shippingHeader.getShippingCityCode());
          shippingHeaderInfo.setAddress1(shippingHeader.getShippingAddress1());
          shippingHeaderInfo.setAddress2(shippingHeader.getShippingAddress2());
          // modify by V10-CH 170 end
          String shippingPhoneNumber = shippingHeader.getShippingTel();
          shippingHeaderInfo.setPhoneNumber(shippingPhoneNumber);
          // Add by V10-CH start
          String shippingMobileNumber = shippingHeader.getMobileTel();
          shippingHeaderInfo.setMobileNumber(shippingMobileNumber);
          // Add by V10-CH end
          // 10.1.6 10276 追加 ここから
          // 修正前の配送指定日を取得

          // 20111229 shen update start
          // String beforeDeliveryAppointedDate =
          // DateUtil.toDateString(shippingHeaderInfo.getDeliveryAppointedDate());
          // String beforeDeliveryAppointedDate =
          // shippingHeaderInfo.getDeliveryAppointedDate();
          // 20111229 shen update end
          // 10.1.6 10276 追加 ここまで
          String ymd = shippingHeader.getDeliveryAppointedDate();
          // 20111229 shen update start
          // shippingHeaderInfo.setDeliveryAppointedDate(DateUtil.fromString(ymd));
          shippingHeaderInfo.setDeliveryAppointedDate(ymd);
          // 20111229 shen update end
          String startTime = shippingHeader.getDeliveryAppointedStartTime();
          String endTime = shippingHeader.getDeliveryAppointedEndTime();
          if (StringUtil.hasValue(startTime)) {
            shippingHeaderInfo.setDeliveryAppointedTimeStart(NumUtil.toLong(startTime));
          } else {
            shippingHeaderInfo.setDeliveryAppointedTimeStart(null);
          }
          if (StringUtil.hasValue(endTime)) {
            shippingHeaderInfo.setDeliveryAppointedTimeEnd(NumUtil.toLong(endTime));
          } else {
            shippingHeaderInfo.setDeliveryAppointedTimeEnd(null);
          }
          /*
           * // 配送指定日が設定されている場合出荷指示日を設定する if (ymd != null && !ymd.equals("")) {
           * DeliveryType deliveryType = new DeliveryType();
           * deliveryType.setShopCode(shippingHeader.getShippingShopCode());
           * deliveryType
           * .setDeliveryTypeNo(NumUtil.toLong(shippingHeader.getShippingTypeCode
           * ()));shippingHeaderInfo.setShippingDirectDate(utilService.
           * createShippingDirectDate(DateUtil.fromString(ymd), deliveryType,
           * shippingHeader.getOrgShippingPrefectureCode())); // 10.1.6 10276 追加
           * ここから // 手動で出荷指示日が設定されていて、かつDB中に配送指定日がなく、かつ明細画面で配送日が選択されなかった場合 //
           * すでに設定済の出荷指示日を設定する。 } else if (StringUtil.isNullOrEmpty(ymd) &&
           * StringUtil.isNullOrEmpty(beforeDeliveryAppointedDate) &&
           * StringUtil.hasValue(shippingHeader.getShippingDirectDate())) {
           * shippingHeaderInfo
           * .setShippingDirectDate(DateUtil.fromString(shippingHeader
           * .getShippingDirectDate())); // 10.1.6 10276 追加 ここまで } else {
           * shippingHeaderInfo.setShippingDirectDate(null); }
           */
          shippingHeaderInfo.setShippingDirectDate(null);
          shippingHeaderInfo.setDeliveryRemark(shippingHeader.getDeliveryRemark());
          ShippingContainer shippingContainer = new ShippingContainer();
          shippingContainer.setShippingHeader(shippingHeaderInfo);

          for (ShippingContainer sContainer : orderContainer.getShippings()) {
            if (shippingHeaderInfo.getShippingNo().equals(sContainer.getShippingHeader().getShippingNo())) {
              shippingContainer.setShippingDetails(sContainer.getShippingDetails());
            }
          }
          shippingContainerList.add(shippingContainer);
        }
        orderContainer.setShippings(shippingContainerList);
      }
      // 订单发票取得
      OrderInvoice orderInvoiceInfo = service.getOrderInvoice(bean.getOrderNo());
      if (orderInvoiceInfo == null) {
        orderInvoiceInfo = new OrderInvoice();
      }
      CustomerVatInvoice customerVatInvoice = orderContainer.getCustomerVatInvoice();
      if (customerVatInvoice == null) {
        customerVatInvoice = new CustomerVatInvoice();
      }
      setupOrderInvoiceInfo(orderInvoiceInfo, customerVatInvoice, bean);
      //家电发票更改规格  1为需要发票
      CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());
      if(orderInvoiceInfo != null ) {
        for (ShippingContainer sContainer : orderContainer.getShippings()) {
          for (ShippingDetail sd : sContainer.getShippingDetails()) {
            CommodityHeader header = catalogService.getCommodityHeader("00000000", sd.getSkuCode());
            if (header != null) {
              if (header.getNewReserveCommodityType2() == 1L) {
                orderInvoiceInfo.setCommodityName(orderInvoiceInfo.getCommodityName() + "-电器");
                break;
              }
            }
          }
        }
      }
      orderContainer.setOrderInvoice(orderInvoiceInfo);
      orderContainer.setCustomerVatInvoice(customerVatInvoice);
      result = service.updateOrderWithoutPayment(orderContainer);
    }
    // OrderHeader orderHeaderInfo = service.getOrderHeader(bean.getOrderNo());
    //
    // // データ存在チェック
    // if (orderHeaderInfo == null) {
    // addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
    // Messages
    // .getString("web.action.back.order.OrderDetailUpdateAction.3")));
    // setRequestBean(bean);
    // return BackActionResult.RESULT_SUCCESS;
    // }
    // // 配送指定日から出荷指示日を計算し、受注日以前の日付の場合エラー
    // boolean hasShippingDirectError = false;
    // UtilService utilService = ServiceLocator.getUtilService(getLoginInfo());
    // for (ShippingHeaderBean header : bean.getShippingList()) {
    // if (header.getDeliveryAppointedDate() != null &&
    // StringUtil.hasValue(header.getDeliveryAppointedDate())) {
    // DeliveryType deliveryType = new DeliveryType();
    // deliveryType.setShopCode(header.getShippingShopCode());
    // deliveryType.setDeliveryTypeNo(NumUtil.toLong(header.getShippingTypeCode()));
    // Date shippingDirectDate =
    // utilService.createShippingDirectDate(DateUtil.fromString(header.getDeliveryAppointedDate()),
    // deliveryType, header.getOrgShippingPrefectureCode());
    // // 出荷指示日が当日日付以前の場合はエラーとする
    // if (shippingDirectDate.before(orderHeaderInfo.getOrderDatetime())) {
    // addErrorMessage(WebMessage.get(OrderErrorMessage.IMPOSSIBILITY_APPOINTED_DATE_CAUSE_DIRECT_PAST));
    // hasShippingDirectError = true;
    // }
    // }
    // }
    // if (hasShippingDirectError) {
    // setRequestBean(bean);
    // return BackActionResult.RESULT_SUCCESS;
    // }
    // // 顧客退会チェック
    // if (CustomerConstant.isCustomer(orderHeaderInfo.getCustomerCode())) {
    // CustomerService customerService =
    // ServiceLocator.getCustomerService(getLoginInfo());
    // if (customerService.isWithdrawed(orderHeaderInfo.getCustomerCode())) {
    // addErrorMessage(WebMessage.get(OrderErrorMessage.WITHDRAWAL_CUSTOMER,
    // orderHeaderInfo.getOrderNo()));
    // setRequestBean(bean);
    // return BackActionResult.RESULT_SUCCESS;
    // }
    // }
    // // データ連携済チェック
    // if
    // (DataTransportStatus.fromValue(orderHeaderInfo.getDataTransportStatus()).equals(DataTransportStatus.TRANSPORTED))
    // {
    // addErrorMessage(WebMessage.get(OrderErrorMessage.DATA_TRANSPORTED));
    // setRequestBean(bean);
    // return BackActionResult.RESULT_SUCCESS;
    // }
    // OrderContainer orderContainer = service.getOrder(bean.getOrderNo());
    // // 受注情報が更新可能な場合、受注情報をセット
    // if
    // (bean.getOrderInformationDisplayMode().equals(WebConstantCode.DISPLAY_EDIT))
    // {
    // setupOrderHeaderInfo(orderHeaderInfo, bean);
    // orderContainer.setOrderHeader(orderHeaderInfo);
    // }
    // // 出荷情報が更新可能な場合、出荷情報をセット
    // if
    // (bean.getShippingInformationDisplayMode().equals(WebConstantCode.DISPLAY_EDIT))
    // {
    // List<ShippingContainer> shippingContainerList = new
    // ArrayList<ShippingContainer>();
    // for (ShippingHeaderBean shippingHeader : bean.getShippingList()) {
    // ShippingHeader shippingHeaderInfo =
    // service.getShippingHeader(shippingHeader.getShippingNo());
    // // データ存在チェック
    // if (shippingHeaderInfo == null) {
    // addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
    // Messages
    // .getString("web.action.back.order.OrderDetailUpdateAction.4")));
    // setRequestBean(bean);
    // return BackActionResult.RESULT_SUCCESS;
    // }
    // // 売上確定済み出荷の場合警告を出力する
    // if
    // (shippingHeaderInfo.getFixedSalesStatus().equals(FixedSalesStatus.FIXED.longValue()))
    // {
    // addWarningMessage(WebMessage.get(OrderErrorMessage.FIXED_DATA_WITH_NO,
    // shippingHeaderInfo.getShippingNo()));
    // continue;
    // }
    // shippingHeaderInfo.setAddressLastName(shippingHeader.getShippingLastName());
    // shippingHeaderInfo.setAddressFirstName(shippingHeader.getShippingFirstName());
    // shippingHeaderInfo.setAddressLastNameKana(shippingHeader.getShippingLastNameKana());
    // shippingHeaderInfo.setAddressFirstNameKana(shippingHeader.getShippingFirstNameKana());
    // shippingHeaderInfo.setPostalCode(shippingHeader.getShippingPostalCode());
    // shippingHeaderInfo.setAddress3(shippingHeader.getShippingAddress3());
    // shippingHeaderInfo.setAddress4(shippingHeader.getShippingAddress4());
    // // modify by V10-CH 170 start
    // // shippingHeaderInfo.setAddress2(shippingHeader.getShippingAddress2());
    // // UtilService s = ServiceLocator.getUtilService(getLoginInfo());
    // shippingHeaderInfo.setPrefectureCode(shippingHeader.getShippingPrefectureCode());
    // // shippingHeaderInfo.setCityCode(shippingHeader.getShippingCityCode());
    // shippingHeaderInfo.setAddress1(PrefectureCode.fromValue(shippingHeader.getShippingPrefectureCode()).getName());
    // shippingHeaderInfo.setAddress2(shippingHeader.getShippingAddress2());
    // // modify by V10-CH 170 end
    // String shippingPhoneNumber = shippingHeader.getShippingTel();
    // shippingHeaderInfo.setPhoneNumber(shippingPhoneNumber);
    // // Add by V10-CH start
    // String shippingMobileNumber = shippingHeader.getMobileTel();
    // shippingHeaderInfo.setMobileNumber(shippingMobileNumber);
    // // Add by V10-CH end
    // // 10.1.6 10276 追加 ここから
    // // 修正前の配送指定日を取得    //
    // // 20111229 shen update start
    // // String beforeDeliveryAppointedDate =
    // DateUtil.toDateString(shippingHeaderInfo.getDeliveryAppointedDate());
    // String beforeDeliveryAppointedDate =
    // shippingHeaderInfo.getDeliveryAppointedDate();
    // // 20111229 shen update end
    // // 10.1.6 10276 追加 ここまで
    // String ymd = shippingHeader.getDeliveryAppointedDate();
    // // 20111229 shen update start
    // // shippingHeaderInfo.setDeliveryAppointedDate(DateUtil.fromString(ymd));
    // shippingHeaderInfo.setDeliveryAppointedDate(ymd);
    // // 20111229 shen update end
    // String startTime = shippingHeader.getDeliveryAppointedStartTime();
    // String endTime = shippingHeader.getDeliveryAppointedEndTime();
    // if (StringUtil.hasValue(startTime)) {
    // shippingHeaderInfo.setDeliveryAppointedTimeStart(NumUtil.toLong(startTime));
    // } else {
    // shippingHeaderInfo.setDeliveryAppointedTimeStart(null);
    // }
    // if (StringUtil.hasValue(endTime)) {
    // shippingHeaderInfo.setDeliveryAppointedTimeEnd(NumUtil.toLong(endTime));
    // } else {
    // shippingHeaderInfo.setDeliveryAppointedTimeEnd(null);
    // }
    // // 配送指定日が設定されている場合出荷指示日を設定する
    // if (ymd != null && !ymd.equals("")) {
    // DeliveryType deliveryType = new DeliveryType();
    // deliveryType.setShopCode(shippingHeader.getShippingShopCode());
    // deliveryType.setDeliveryTypeNo(NumUtil.toLong(shippingHeader.getShippingTypeCode()));
    // shippingHeaderInfo.setShippingDirectDate(utilService.createShippingDirectDate(DateUtil.fromString(ymd),
    // deliveryType,
    // shippingHeader.getOrgShippingPrefectureCode()));
    // // 10.1.6 10276 追加 ここから
    // // 手動で出荷指示日が設定されていて、かつDB中に配送指定日がなく、かつ明細画面で配送日が選択されなかった場合    //
    // // すでに設定済の出荷指示日を設定する。    //
    // } else if (StringUtil.isNullOrEmpty(ymd) &&
    // StringUtil.isNullOrEmpty(beforeDeliveryAppointedDate)
    // && StringUtil.hasValue(shippingHeader.getShippingDirectDate())) {
    // shippingHeaderInfo.setShippingDirectDate(DateUtil.fromString(shippingHeader.getShippingDirectDate()));
    // // 10.1.6 10276 追加 ここまで
    // } else {
    // shippingHeaderInfo.setShippingDirectDate(null);
    // }
    // shippingHeaderInfo.setDeliveryRemark(shippingHeader.getDeliveryRemark());
    // ShippingContainer shippingContainer = new ShippingContainer();
    // shippingContainer.setShippingHeader(shippingHeaderInfo);
    //
    // for (ShippingContainer sContainer : orderContainer.getShippings()) {
    // if
    // (shippingHeaderInfo.getShippingNo().equals(sContainer.getShippingHeader().getShippingNo()))
    // {
    // shippingContainer.setShippingDetails(sContainer.getShippingDetails());
    // }
    // }
    // shippingContainerList.add(shippingContainer);
    // }
    // orderContainer.setShippings(shippingContainerList);
    // }
    // // soukai add 2011/12/29 ob start
    // // 订单发票取得
    // OrderInvoice orderInvoiceInfo =
    // service.getOrderInvoice(bean.getOrderNo());
    // if (orderInvoiceInfo == null) {
    // orderInvoiceInfo = new OrderInvoice();
    // }
    // setupOrderInvoiceInfo(orderInvoiceInfo, bean);
    // orderContainer.setOrderInvoice(orderInvoiceInfo);
    // // soukai add 2011/12/29 ob end
    // ServiceResult result = service.updateOrderWithoutPayment(orderContainer);

    // soukai edit 2012/1/7 ob end

    if (result.hasError()) {
      boolean notOnlyfixError = false;
      for (ServiceErrorContent error : result.getServiceErrorList()) {
        if (error == OrderServiceErrorContent.TMALL_ACCESS_ERROR) {
          addErrorMessage("淘宝订单的同步处理失败了。");
          setRequestBean(bean);
          return BackActionResult.RESULT_SUCCESS;
        }
        if (error != OrderServiceErrorContent.FIXED_DATA_ERROR) {
          notOnlyfixError = true;
        }
      }
      if (notOnlyfixError) {
        return BackActionResult.SERVICE_ERROR;
      }
    }
    setNextUrl("/app/order/order_detail/init/" + bean.getOrderNo() + "/" + WebConstantCode.COMPLETE_UPDATE);
    setRequestBean(bean);
    return BackActionResult.RESULT_SUCCESS;
  }

  // soukai add 2011/12/29 ob start
  private void setupOrderInvoiceInfo(OrderInvoice orderInvoiceInfo, CustomerVatInvoice customerVatInvoice, OrderDetailBean bean) {
    orderInvoiceInfo.setCommodityName(bean.getOrderInvoice().getInvoiceCommodityName());// 商品规格
    orderInvoiceInfo.setInvoiceType(NumUtil.toLong(bean.getOrderInvoice().getInvoiceType()));// 发票类型
    orderInvoiceInfo.setCustomerName(bean.getOrderInvoice().getInvoiceCustomerName());// 顾客姓名
    orderInvoiceInfo.setCompanyName(bean.getOrderInvoice().getInvoiceCompanyName());// 公司名称
    orderInvoiceInfo.setTaxpayerCode(bean.getOrderInvoice().getInvoiceTaxpayerCode());// 纳税人识别号
    orderInvoiceInfo.setAddress(bean.getOrderInvoice().getInvoiceAddress());// 地址
    orderInvoiceInfo.setTel(bean.getOrderInvoice().getInvoiceTel());// 电话
    orderInvoiceInfo.setBankName(bean.getOrderInvoice().getInvoiceBankName());// 银行名称
    orderInvoiceInfo.setBankNo(bean.getOrderInvoice().getInvoiceBankNo());// 银行支行编号
    orderInvoiceInfo.setInvoiceFlg(bean.getOrderInvoice().getInvoiceFlg());// 是否领取发票
    if (InvoiceSaveFlg.SAVE.getValue().equals(bean.getOrderInvoice().getInvoiceSaveFlg())) {
      customerVatInvoice.setCompanyName(bean.getOrderInvoice().getInvoiceCompanyName());// 公司名称
      customerVatInvoice.setTaxpayerCode(bean.getOrderInvoice().getInvoiceTaxpayerCode());// 纳税人识别号
      customerVatInvoice.setAddress(bean.getOrderInvoice().getInvoiceAddress());// 地址
      customerVatInvoice.setTel(bean.getOrderInvoice().getInvoiceTel());// 电话
      customerVatInvoice.setBankName(bean.getOrderInvoice().getInvoiceBankName());// 银行名称
      customerVatInvoice.setBankNo(bean.getOrderInvoice().getInvoiceBankNo());// 银行支行编号
      customerVatInvoice.setCustomerCode(bean.getOrderHeaderEdit().getCustomerCode());
    } else {
      customerVatInvoice = null;
    }
  }

  // TMALL受注ヘッダ情報の設定
  private void setupTmallOrderHeaderInfo(TmallOrderHeader tmallOrderHeaderInfo, OrderDetailBean bean) {
    tmallOrderHeaderInfo.setLastName(bean.getOrderHeaderEdit().getCustomerLastName());
    tmallOrderHeaderInfo.setFirstName(bean.getOrderHeaderEdit().getCustomerFirstName());
    tmallOrderHeaderInfo.setLastNameKana(bean.getOrderHeaderEdit().getCustomerLastNameKana());
    tmallOrderHeaderInfo.setFirstNameKana(bean.getOrderHeaderEdit().getCustomerFirstNameKana());
    tmallOrderHeaderInfo.setEmail(bean.getOrderHeaderEdit().getCustomerEmail());
    tmallOrderHeaderInfo.setPostalCode(bean.getOrderHeaderEdit().getPostalCode());
    tmallOrderHeaderInfo.setPrefectureCode(bean.getOrderHeaderEdit().getPrefectureCode());
    UtilService service = ServiceLocator.getUtilService(getLoginInfo());
    tmallOrderHeaderInfo.setAddress1(service.getPrefectureName(bean.getOrderHeaderEdit().getPrefectureCode()));
    tmallOrderHeaderInfo.setAddress2(service.getCityName(bean.getOrderHeaderEdit().getPrefectureCode(), bean.getOrderHeaderEdit()
        .getCityCode()));
    tmallOrderHeaderInfo.setPrefectureCode(bean.getOrderHeaderEdit().getPrefectureCode());
    tmallOrderHeaderInfo.setCityCode(bean.getOrderHeaderEdit().getCityCode());
    // soukai add 2012/01/08 ob start
    // tmallOrderHeaderInfo.setAddress3(bean.getOrderHeaderEdit().getAddress3());
    tmallOrderHeaderInfo.setAddress3(service.getAreaName(bean.getOrderHeaderEdit().getAreaCode()));
    tmallOrderHeaderInfo.setAreaCode(bean.getOrderHeaderEdit().getAreaCode());
    if (StringUtil.hasValue(bean.getOrderFlg())) {
      tmallOrderHeaderInfo.setOrderFlg(OrderFlg.fromValue(bean.getOrderFlg()).longValue());
    }
    // soukai add 2012/01/08 ob en
    tmallOrderHeaderInfo.setAddress4(bean.getOrderHeaderEdit().getAddress4());
    tmallOrderHeaderInfo.setPaymentCommission(BigDecimal.ZERO);
    String phoneNumber = "";
    if (StringUtil.hasValueAllOf(bean.getOrderHeaderEdit().getCustomerTel1(), bean.getOrderHeaderEdit().getCustomerTel2(), bean
        .getOrderHeaderEdit().getCustomerTel3())) {
      phoneNumber = StringUtil.joint('-', bean.getOrderHeaderEdit().getCustomerTel1(), bean.getOrderHeaderEdit().getCustomerTel2(),
          bean.getOrderHeaderEdit().getCustomerTel3());
    } else if (StringUtil.hasValueAllOf(bean.getOrderHeaderEdit().getCustomerTel1(), bean.getOrderHeaderEdit().getCustomerTel2())) {
      phoneNumber = StringUtil.joint('-', bean.getOrderHeaderEdit().getCustomerTel1(), bean.getOrderHeaderEdit().getCustomerTel2());
    }
    String mobileNumber = bean.getOrderHeaderEdit().getCustomerMobile();
    tmallOrderHeaderInfo.setPhoneNumber(phoneNumber);
    tmallOrderHeaderInfo.setMobileNumber(mobileNumber);
    tmallOrderHeaderInfo.setMessage(bean.getOrderHeaderEdit().getMessage());
    tmallOrderHeaderInfo.setCaution(bean.getOrderHeaderEdit().getCaution());
    tmallOrderHeaderInfo.setUpdatedDatetime(bean.getOrderHeaderEdit().getUpdateDatetime());
  }
  
  // JD受注ヘッダ情報の設定
  private void setupJdOrderHeaderInfo(JdOrderHeader jdOrderHeaderInfo, OrderDetailBean bean) {
    jdOrderHeaderInfo.setLastName(bean.getOrderHeaderEdit().getCustomerLastName());
    jdOrderHeaderInfo.setFirstName(bean.getOrderHeaderEdit().getCustomerFirstName());
    jdOrderHeaderInfo.setLastNameKana(bean.getOrderHeaderEdit().getCustomerLastNameKana());
    jdOrderHeaderInfo.setFirstNameKana(bean.getOrderHeaderEdit().getCustomerFirstNameKana());
    jdOrderHeaderInfo.setEmail(bean.getOrderHeaderEdit().getCustomerEmail());
    jdOrderHeaderInfo.setPostalCode(bean.getOrderHeaderEdit().getPostalCode());
    jdOrderHeaderInfo.setPrefectureCode(bean.getOrderHeaderEdit().getPrefectureCode());
    UtilService service = ServiceLocator.getUtilService(getLoginInfo());
    jdOrderHeaderInfo.setAddress1(service.getPrefectureName(bean.getOrderHeaderEdit().getPrefectureCode()));
    jdOrderHeaderInfo.setAddress2(service.getCityName(bean.getOrderHeaderEdit().getPrefectureCode(), bean.getOrderHeaderEdit()
        .getCityCode()));
    jdOrderHeaderInfo.setPrefectureCode(bean.getOrderHeaderEdit().getPrefectureCode());
    jdOrderHeaderInfo.setCityCode(bean.getOrderHeaderEdit().getCityCode());
    // soukai add 2012/01/08 ob start
    // jdOrderHeaderInfo.setAddress3(bean.getOrderHeaderEdit().getAddress3());
    jdOrderHeaderInfo.setAddress3(service.getAreaName(bean.getOrderHeaderEdit().getAreaCode()));
    jdOrderHeaderInfo.setAreaCode(bean.getOrderHeaderEdit().getAreaCode());
    if (StringUtil.hasValue(bean.getOrderFlg())) {
      jdOrderHeaderInfo.setOrderFlg(OrderFlg.fromValue(bean.getOrderFlg()).longValue());
    }
    // soukai add 2012/01/08 ob en
    jdOrderHeaderInfo.setAddress4(bean.getOrderHeaderEdit().getAddress4());
    jdOrderHeaderInfo.setPaymentCommission(BigDecimal.ZERO);
    String phoneNumber = "";
    if (StringUtil.hasValueAllOf(bean.getOrderHeaderEdit().getCustomerTel1(), bean.getOrderHeaderEdit().getCustomerTel2(), bean
        .getOrderHeaderEdit().getCustomerTel3())) {
      phoneNumber = StringUtil.joint('-', bean.getOrderHeaderEdit().getCustomerTel1(), bean.getOrderHeaderEdit().getCustomerTel2(),
          bean.getOrderHeaderEdit().getCustomerTel3());
    } else if (StringUtil.hasValueAllOf(bean.getOrderHeaderEdit().getCustomerTel1(), bean.getOrderHeaderEdit().getCustomerTel2())) {
      phoneNumber = StringUtil.joint('-', bean.getOrderHeaderEdit().getCustomerTel1(), bean.getOrderHeaderEdit().getCustomerTel2());
    }
    String mobileNumber = bean.getOrderHeaderEdit().getCustomerMobile();
    jdOrderHeaderInfo.setPhoneNumber(phoneNumber);
    jdOrderHeaderInfo.setMobileNumber(mobileNumber);
    jdOrderHeaderInfo.setMessage(bean.getOrderHeaderEdit().getMessage());
    jdOrderHeaderInfo.setCaution(bean.getOrderHeaderEdit().getCaution());
    jdOrderHeaderInfo.setUpdatedDatetime(bean.getOrderHeaderEdit().getUpdateDatetime());
  }

  // soukai add 2011/12/29 ob end

  private void setupOrderHeaderInfo(OrderHeader orderHeaderInfo, OrderDetailBean bean) {
    orderHeaderInfo.setLastName(bean.getOrderHeaderEdit().getCustomerLastName());
    orderHeaderInfo.setFirstName(bean.getOrderHeaderEdit().getCustomerFirstName());
    orderHeaderInfo.setLastNameKana(bean.getOrderHeaderEdit().getCustomerLastNameKana());
    orderHeaderInfo.setFirstNameKana(bean.getOrderHeaderEdit().getCustomerFirstNameKana());
    orderHeaderInfo.setEmail(bean.getOrderHeaderEdit().getCustomerEmail());
    orderHeaderInfo.setPostalCode(bean.getOrderHeaderEdit().getPostalCode());
    orderHeaderInfo.setPrefectureCode(bean.getOrderHeaderEdit().getPrefectureCode());
    // modify by V10-CH 170 start
    // orderHeaderInfo.setAddress1(PrefectureCode.fromValue(orderHeaderInfo.getPrefectureCode()).getName());
    // orderHeaderInfo.setAddress2(bean.getOrderHeaderEdit().getAddress2());
    UtilService s = ServiceLocator.getUtilService(getLoginInfo());
    orderHeaderInfo.setAddress1(s.getPrefectureName(bean.getOrderHeaderEdit().getPrefectureCode()));
    orderHeaderInfo.setAddress2(s.getCityName(bean.getOrderHeaderEdit().getPrefectureCode(), bean.getOrderHeaderEdit()
        .getCityCode()));

    orderHeaderInfo.setPrefectureCode(bean.getOrderHeaderEdit().getPrefectureCode());
    orderHeaderInfo.setCityCode(bean.getOrderHeaderEdit().getCityCode());
    // modify by V10-CH 170 end
    // soukai add 2012/01/08 ob start
    // orderHeaderInfo.setAddress3(bean.getOrderHeaderEdit().getAddress3());
    orderHeaderInfo.setAreaCode(bean.getOrderHeaderEdit().getAreaCode());
    orderHeaderInfo.setAddress3(s.getAreaName(bean.getOrderHeaderEdit().getAreaCode()));
    if (StringUtil.hasValue(bean.getOrderFlg())) {
      orderHeaderInfo.setOrderFlg(OrderFlg.fromValue(bean.getOrderFlg()).longValue());
    }
    // soukai add 2012/01/08 ob end
    orderHeaderInfo.setAddress4(bean.getOrderHeaderEdit().getAddress4());
    // modify by V10-CH 170 start
    String phoneNumber = "";
    if (StringUtil.hasValueAllOf(bean.getOrderHeaderEdit().getCustomerTel1(), bean.getOrderHeaderEdit().getCustomerTel2(), bean
        .getOrderHeaderEdit().getCustomerTel3())) {
      phoneNumber = StringUtil.joint('-', bean.getOrderHeaderEdit().getCustomerTel1(), bean.getOrderHeaderEdit().getCustomerTel2(),
          bean.getOrderHeaderEdit().getCustomerTel3());
    } else if (StringUtil.hasValueAllOf(bean.getOrderHeaderEdit().getCustomerTel1(), bean.getOrderHeaderEdit().getCustomerTel2())) {
      phoneNumber = StringUtil.joint('-', bean.getOrderHeaderEdit().getCustomerTel1(), bean.getOrderHeaderEdit().getCustomerTel2());
    }
    // Add by V10-CH start
    String mobileNumber = bean.getOrderHeaderEdit().getCustomerMobile();
    // Add by V10-CH end
    // String phoneNumber = bean.getOrderHeaderEdit().getCustomerTel1();
    // modify by V10-CH 170 end
    orderHeaderInfo.setPhoneNumber(phoneNumber);
    // Add by V10-CH start
    orderHeaderInfo.setMobileNumber(mobileNumber);
    // Add by V10-CH end
    orderHeaderInfo.setMessage(bean.getOrderHeaderEdit().getMessage());
    orderHeaderInfo.setCaution(bean.getOrderHeaderEdit().getCaution());
    orderHeaderInfo.setUpdatedDatetime(bean.getOrderHeaderEdit().getUpdateDatetime());
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.order.OrderDetailUpdateAction.5");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "1102022007";
  }

}
