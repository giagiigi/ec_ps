package jp.co.sint.webshop.web.action.back.order;

import java.lang.annotation.Annotation;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.configure.WebshopConfig;
import jp.co.sint.webshop.data.attribute.Precision;
import jp.co.sint.webshop.data.domain.OperatingMode;
import jp.co.sint.webshop.data.domain.ShippingStatus;
import jp.co.sint.webshop.data.dto.ShippingDetail;
import jp.co.sint.webshop.data.dto.ShippingHeader;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.LoginInfo;
import jp.co.sint.webshop.service.OrderService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.customer.CustomerConstant;
import jp.co.sint.webshop.service.order.OrderContainer;
import jp.co.sint.webshop.service.order.ShippingContainer;
import jp.co.sint.webshop.utility.BigDecimalUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.validation.CurrencyValidator;
import jp.co.sint.webshop.validation.PrecisionValidator;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.order.OrderReturnBean;
import jp.co.sint.webshop.web.bean.back.order.OrderReturnBean.ShippingBean;
import jp.co.sint.webshop.web.bean.back.order.OrderReturnBean.ShippingBean.ShippingDetailBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.InformationMessage;
import jp.co.sint.webshop.web.message.back.order.OrderErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

import org.apache.log4j.Logger;

/**
 * U1020260:受注返品のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class OrderReturnConfirmAction extends WebBackAction<OrderReturnBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    LoginInfo login = getLoginInfo();
    WebshopConfig config = getConfig();
    boolean auth = false;
    if (config.getOperatingMode() == OperatingMode.MALL) {
      auth = Permission.ORDER_MODIFY_SITE.isGranted(login);
    } else if (config.getOperatingMode() == OperatingMode.SHOP) {
      auth = Permission.ORDER_MODIFY_SHOP.isGranted(login) || Permission.ORDER_MODIFY_SITE.isGranted(login);
    } else if (config.getOperatingMode() == OperatingMode.ONE) {
      auth = Permission.ORDER_MODIFY_SITE.isGranted(login);
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
    Logger logger = Logger.getLogger(this.getClass());
    OrderReturnBean bean = getBean();
    boolean valid = validateBean(bean);
    // 10.1.2 10075 削除 ここから
//    if (StringUtil.hasValue(bean.getReturnShippingCharge())) {
//      if (NumUtil.toLong(bean.getReturnShippingCharge()) < 0L) {
//        valid &= false;
//        addErrorMessage(WebMessage.get(OrderErrorMessage.INPUT_NEGATIVE_VALUE_ERROR, "送料"));
//      }
//    }
//    if (StringUtil.hasValue(bean.getSalesPrice())) {
//      if (NumUtil.toLong(bean.getSalesPrice()) < 0L) {
//        valid &= false;
//        addErrorMessage(WebMessage.get(OrderErrorMessage.INPUT_NEGATIVE_VALUE_ERROR, "単価(税込)"));
//      }
//    }
//    if (StringUtil.hasValue(bean.getDetailGiftPrice())) {
//      if (NumUtil.toLong(bean.getDetailGiftPrice()) < 0L) {
//        valid &= false;
//        addErrorMessage(WebMessage.get(OrderErrorMessage.INPUT_NEGATIVE_VALUE_ERROR, "ギフト価格(税込)"));
//      }
//    }
//    if (StringUtil.hasValue(bean.getPurchasingAmount())) {
//      if (NumUtil.toLong(bean.getPurchasingAmount()) < 0L) {
//        valid &= false;
//        addErrorMessage(WebMessage.get(OrderErrorMessage.INPUT_NEGATIVE_VALUE_ERROR, "数量"));
//      }
//    }
    // 10.1.2 10075 削除 ここまで
    if (valid) {
      try {
//        int length = 0;
        int precision = 0;
        int scale = 0;
        Annotation[] annotations = ShippingHeader.class.getDeclaredField("returnItemLossMoney").getAnnotations();
//        for (Annotation annotation : annotations) {
//          if (annotation.annotationType().equals(Length.class)) {
//            Length lengthAnnotation = (Length) annotation;
//            length = lengthAnnotation.value();
//          }
//        }
        for (Annotation annotation : annotations) {
          if (annotation.annotationType().equals(Precision.class)) {
            Precision precisionAnnotation = (Precision) annotation;
            precision = precisionAnnotation.precision();
            scale = precisionAnnotation.scale();
          }
        }
        // 返品損金額の桁数チェック
//        LengthValidator lengthValidator = new LengthValidator(length);
//        if (!lengthValidator.isValid(getSummary(bean))) {
//          addErrorMessage(WebMessage.get(OrderErrorMessage.RETURN_ITEM_LOSS_MONEY_LENGTH_OVER, Integer.toString(length)));
//          return false;
//        }
        PrecisionValidator precisionValidator = new PrecisionValidator(precision, scale);
        if (!precisionValidator.isValid(getSummary(bean))) {
          addErrorMessage(precisionValidator.getMessage());
          return false;
        }
        CurrencyValidator currencyValidator = new CurrencyValidator();
        if (!currencyValidator.isValid(getSummary(bean))) {
          addErrorMessage(precisionValidator.getMessage());
          return false;
        }
      } catch (NoSuchFieldException e) {
        logger.error(e);
      }
      // 商品無しの場合は数量チェック無し
      if (bean.getShopSkuCode().equals("0")) {
        return valid;
      }

      Long orderSkuNum = getOrderSkuNum(bean.getShopSkuCode());
      Long returnAmount = Long.parseLong(bean.getPurchasingAmount());
      // 全購入商品数より返品数のほうが大きかった場合エラーとする
      if (orderSkuNum < returnAmount) {
        addErrorMessage(WebMessage.get(OrderErrorMessage.RETURN_OVER));
        valid = false;
      }
      OrderService orderService = ServiceLocator.getOrderService(getLoginInfo());
      OrderContainer container = orderService.getOrder(bean.getOrderNo());
      if (CustomerConstant.isCustomer(container.getOrderHeader().getCustomerCode())) {
        CustomerService custService = ServiceLocator.getCustomerService(getLoginInfo());
        if (custService.isWithdrawed(container.getOrderHeader().getCustomerCode())) {
          addErrorMessage(WebMessage.get(OrderErrorMessage.WITHDRAWAL_CUSTOMER, bean.getOrderNo()));
          valid &= false;
        }
      }
    }
    return valid;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    OrderReturnBean bean = getBean();
    bean.setDispStatus(WebConstantCode.DISPLAY_HIDDEN);
    bean.setConfirmButtonDisplay(false);
    bean.setRegisterButtonDisplay(true);

    BigDecimal summary = getSummary(bean);
    bean.setReturnCharge(summary);

    setRequestBean(bean);

    addWarningMessage(WebMessage.get(InformationMessage.REGISTER_CONFIRM));

    return BackActionResult.RESULT_SUCCESS;
  }

  private BigDecimal getSummary(OrderReturnBean bean) {
//    Long salesPrice = NumUtil.toLong(bean.getSalesPrice());
//    Long gift = NumUtil.toLong(bean.getDetailGiftPrice());
//    Long amount = NumUtil.toLong(bean.getPurchasingAmount()) * -1;
//    return (salesPrice + gift) * amount - NumUtil.toLong(bean.getReturnShippingCharge());
    BigDecimal salesPrice = NumUtil.parse(bean.getSalesPrice());
    BigDecimal gift = NumUtil.parse(bean.getDetailGiftPrice());
    BigDecimal amount = NumUtil.parse(bean.getPurchasingAmount()).negate();
    return BigDecimalUtil.subtract(BigDecimalUtil.multiply(BigDecimalUtil.add(salesPrice, gift), amount), NumUtil.parse(bean
        .getReturnShippingCharge()));
  }

  /**
   * shippingDetailBeanNoの注文商品の合計数を取得する
   * 
   * @param shippingDetailBeanNo
   *          出荷明細番号
   * @return 注文商品の合計数
   */
  private Long getOrderSkuNum(String shippingDetailBeanNo) {
    Long num = 0L;
    for (ShippingDetail detail : getOrderDetailFromShopSkuCode(shippingDetailBeanNo)) {
      num += detail.getPurchasingAmount();
    }

    return num;
  }

  private List<ShippingDetail> getOrderDetailFromShopSkuCode(String shippingDetailBeanNo) {
    for (ShippingBean shipping : getBean().getShippingList()) {
      for (ShippingDetailBean detail : shipping.getShippingDetailList()) {
        if (detail.getShippingDetailBeanNo().equals(shippingDetailBeanNo)) {
          return getOrderDetailFromShopSkuCode(shipping.getShopCode(), detail.getSkuCode());
        }
      }
    }
    return new ArrayList<ShippingDetail>();
  }

  /**
   * ショップコード＋SKUコードから同一のショップコード・SKUコードの出荷明細の一覧を取得する
   * 
   * @param shopCode
   *          ショップコード
   * @param skuCode
   *          SKUコード
   * @return 出荷明細一覧
   */
  private List<ShippingDetail> getOrderDetailFromShopSkuCode(String shopCode, String skuCode) {
    List<ShippingDetail> detailList = new ArrayList<ShippingDetail>();
    OrderService service = ServiceLocator.getOrderService(getLoginInfo());

    OrderContainer container = service.getOrder(getBean().getOrderNo());
    for (ShippingContainer shipping : container.getShippings()) {
      if (shipping.getShippingHeader().getShippingStatus().equals(ShippingStatus.SHIPPED.longValue())) {
        for (ShippingDetail detail : shipping.getShippingDetails()) {
          if (detail.getShopCode().equals(shopCode) && detail.getSkuCode().equals(skuCode)) {
            detailList.add(detail);
          }
        }
      }
    }

    return detailList;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.order.OrderReturnConfirmAction.4");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "1102026002";
  }

}
