package jp.co.sint.webshop.web.action.back.order;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.domain.ReturnItemType;
import jp.co.sint.webshop.data.domain.ShippingStatus;
import jp.co.sint.webshop.data.domain.TaxType;
import jp.co.sint.webshop.data.dto.OrderHeader;
import jp.co.sint.webshop.data.dto.ShippingDetail;
import jp.co.sint.webshop.data.dto.ShippingHeader;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.OrderService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.customer.CustomerConstant;
import jp.co.sint.webshop.service.order.ShippingContainer;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.order.OrderReturnBean;
import jp.co.sint.webshop.web.bean.back.order.OrderReturnBean.ShippingBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.message.back.order.OrderErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1020260:受注返品のアクションクラスです。

 * 
 * @author System Integrator Corp.
 */
public class OrderReturnRegisterAction extends OrderReturnConfirmAction {

  /**
   * アクションを実行します。

   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    OrderReturnBean bean = getBean();

    OrderService service = ServiceLocator.getOrderService(getLoginInfo());

    OrderHeader oHeader = createOrderHeaderInfo(bean);

    ShippingContainer container = createShippingHeader(bean);
    ShippingHeader sHeader = container.getShippingHeader();
    ShippingDetail sDetail = container.getShippingDetails().get(0);

    // 顧客退会チェック

    if (CustomerConstant.isCustomer(oHeader.getCustomerCode())) {
      CustomerService customerService = ServiceLocator.getCustomerService(getLoginInfo());
      if (customerService.isWithdrawed(oHeader.getCustomerCode())) {
        addErrorMessage(WebMessage.get(OrderErrorMessage.WITHDRAWAL_CUSTOMER, oHeader.getOrderNo()));
        return BackActionResult.RESULT_SUCCESS;
      }
    }

    ServiceResult result = service.insertReturnOrder(oHeader, sHeader, sDetail);
    if (result.hasError()) {
      for (ServiceErrorContent content : result.getServiceErrorList()) {
        if (content == CommonServiceErrorContent.NO_DATA_ERROR) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_DEFAULT_ERROR));
          return BackActionResult.RESULT_SUCCESS;
        } else if (content == CommonServiceErrorContent.VALIDATION_ERROR) {
          return BackActionResult.SERVICE_VALIDATION_ERROR;
        } else {
          return BackActionResult.SERVICE_ERROR;
        }
      }
    }

    setNextUrl("/app/order/order_return/init/" + bean.getOrderNo() + "/" + WebConstantCode.COMPLETE_REGISTER);

    return BackActionResult.RESULT_SUCCESS;
  }

  private OrderHeader createOrderHeaderInfo(OrderReturnBean bean) {
    OrderHeader header = new OrderHeader();
    header.setOrderNo(bean.getOrderNo());
    header.setMessage(bean.getMessage());
    header.setCaution(bean.getCaution());
    header.setCustomerCode(bean.getCustomerCode());
    header.setUpdatedDatetime(bean.getOrderUpdateDatetime());
    return header;
  }

  private ShippingContainer createShippingHeader(OrderReturnBean bean) {
    ShippingContainer sContainer = new ShippingContainer();
    // 受注ヘッダから住所を取得 出荷ヘッダ情報の作成

    OrderService service = ServiceLocator.getOrderService(getLoginInfo());
    OrderHeader orderHeader = service.getOrderHeader(bean.getOrderNo());
    ShippingHeader header = new ShippingHeader();
    header.setShippingDate(DateUtil.truncateDate(DateUtil.fromString(DateUtil.getSysdateString())));
    header.setShippingDirectDate(header.getShippingDate());
    header.setOrderNo(bean.getOrderNo());
    header.setAddressLastName(orderHeader.getLastName());
    header.setAddressFirstName(orderHeader.getFirstName());
    header.setAddressLastNameKana(orderHeader.getLastNameKana());
    header.setAddressFirstNameKana(orderHeader.getFirstNameKana());
    header.setPostalCode(orderHeader.getPostalCode());
    header.setPrefectureCode(orderHeader.getPrefectureCode());
    header.setCityCode(orderHeader.getCityCode());
    header.setAddress1(orderHeader.getAddress1());
    header.setAddress2(orderHeader.getAddress2());
    header.setAddress3(orderHeader.getAddress3());
    header.setAddress4(orderHeader.getAddress4());
    header.setPhoneNumber(orderHeader.getPhoneNumber());
    //Add by V10-CH start
    header.setMobileNumber(orderHeader.getMobileNumber());
    //Add by V10-CH end
    header.setShippingCharge(BigDecimal.ZERO);
    header.setShippingChargeTaxType(TaxType.NO_TAX.longValue());
    header.setShippingChargeTaxRate(0L);
    header.setShippingChargeTax(BigDecimal.ZERO);
    header.setShippingStatus(NumUtil.toLong(ShippingStatus.SHIPPED.getValue()));
    //header.setReturnItemLossMoney(NumUtil.parse(bean.getReturnShippingCharge()) * -1);
    header.setReturnItemLossMoney(NumUtil.parse(bean.getReturnShippingCharge()).multiply(BigDecimal.ONE.negate()));
    header.setReturnItemType(ReturnItemType.RETURNED.longValue());

    // 出荷明細情報の作成

    List<ShippingDetail> shippingDetailList = new ArrayList<ShippingDetail>();
    ShippingDetail detail = new ShippingDetail();
    detail.setShippingDetailNo(1L);
    detail.setRetailPrice(NumUtil.parse(bean.getSalesPrice()));
    detail.setPurchasingAmount(NumUtil.toLong(bean.getPurchasingAmount()) * -1);
    detail.setGiftPrice(NumUtil.parse(bean.getDetailGiftPrice()));
    detail.setGiftTaxType(TaxType.NO_TAX.longValue());
    detail.setGiftTax(BigDecimal.ZERO);
    detail.setGiftTaxRate(0L);
    detail.setDiscountAmount(BigDecimal.ZERO);
    detail.setRetailTax(BigDecimal.ZERO);

    // ショップコードとSKUコードは一括取得

    String shopSkuCode = bean.getShopSkuCode();
    if (shopSkuCode.equals("0")) {
      header.setShopCode(bean.getReturnShopCode());
      detail.setShopCode(bean.getReturnShopCode());
      header.setDeliveryTypeNo(0L);
      header.setCustomerCode(orderHeader.getCustomerCode());
    } else {
      String returnItemCode = bean.getShopSkuCode();
      String shopCode = "";
      String skuCode = "";
      String originalShippingNo = "";
      Long deliveryTypeNo = 0L;
      for (ShippingBean shipping : bean.getShippingList()) {
        for (ShippingBean.ShippingDetailBean shippingDetail : shipping.getShippingDetailList()) {
          if (returnItemCode.equals(shippingDetail.getShippingDetailBeanNo())) {
            shopCode = shipping.getShopCode();
            skuCode = shippingDetail.getSkuCode();
            deliveryTypeNo = shipping.getDeliveryTypeNo();
            originalShippingNo = shipping.getShippingNo();
          }
        }
      }
      if (StringUtil.hasValue(shopCode) && StringUtil.hasValue(skuCode)) {
        header.setShopCode(shopCode);
        detail.setShopCode(shopCode);
        detail.setSkuCode(skuCode);
        header.setDeliveryTypeNo(deliveryTypeNo);
        header.setOriginalShippingNo(NumUtil.toLong(originalShippingNo));
        header.setCustomerCode(orderHeader.getCustomerCode());
        header.setAddressNo(service.getShippingHeader(originalShippingNo).getAddressNo());
      } else {
        String msg = MessageFormat.format(
            Messages.getString("web.action.back.order.OrderReturnRegisterAction.0"), shopCode, skuCode);
        throw new RuntimeException(msg);
      }
    }

    shippingDetailList.add(detail);

    // 作成したヘッダ・明細情報をコンテナに保存

    sContainer.setShippingHeader(header);
    sContainer.setShippingDetails(shippingDetailList);

    return sContainer;
  }

  /**
   * Action名の取得
   * 
   * @return Action名

   */
  public String getActionName() {
    return Messages.getString("web.action.back.order.OrderReturnRegisterAction.1");
  }

  /**
   * オペレーションコードの取得

   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "1102026005";
  }

}
