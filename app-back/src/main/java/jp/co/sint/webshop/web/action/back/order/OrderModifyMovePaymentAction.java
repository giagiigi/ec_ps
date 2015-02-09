package jp.co.sint.webshop.web.action.back.order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.configure.WebshopConfig;
import jp.co.sint.webshop.data.domain.CommodityType;
import jp.co.sint.webshop.data.domain.CouponFunctionEnabledFlg;
import jp.co.sint.webshop.data.domain.OperatingMode;
import jp.co.sint.webshop.data.domain.PointFunctionEnabledFlg;
import jp.co.sint.webshop.data.dto.CouponRule;
import jp.co.sint.webshop.service.LoginInfo;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.SiteManagementService;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.service.customer.CustomerConstant;
import jp.co.sint.webshop.service.order.ShippingContainer;
import jp.co.sint.webshop.utility.BigDecimalUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.Sku;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.order.OrderModifyBean;
import jp.co.sint.webshop.web.bean.back.order.OrderModifyBean.GiftAttribute;
import jp.co.sint.webshop.web.bean.back.order.OrderModifyBean.OrderHeaderBean;
import jp.co.sint.webshop.web.bean.back.order.OrderModifyBean.PaymentAddress;
import jp.co.sint.webshop.web.bean.back.order.OrderModifyBean.ShippingAddress;
import jp.co.sint.webshop.web.bean.back.order.OrderModifyBean.ShippingHeaderBean;
import jp.co.sint.webshop.web.bean.back.order.OrderModifyBean.ShippingHeaderBean.ShippingDetailBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ValidationMessage;
import jp.co.sint.webshop.web.message.back.order.OrderErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1020230:受注修正のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class OrderModifyMovePaymentAction extends OrderModifyBaseAction {

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {

    if (!isChangeAbleOrder()) {
      return false;
    }

    OrderModifyBean bean = getBean();

    super.setAddressList(bean);

    boolean valid = validateOrderHeaderEdit(bean.getOrderHeaderEdit());
    for (ShippingHeaderBean shippingHeader : bean.getShippingHeaderList()) {
      valid &= validateShippingAddress(shippingHeader.getAddress());
    }
    int shippingCount = 0;
    List<Sku> checkSkuList = new ArrayList<Sku>();
    for (ShippingHeaderBean header : bean.getShippingHeaderList()) {
      // 商品の存在しない出荷先は無効とする。（明細なし出荷情報は登録しない）

      if (!validateBean(header)) {
        valid = false;
      }
      //add by lc 2012-03-08 start
      if (StringUtil.getLength(header.getAddress().getLastName()) > 28) {
        addErrorMessage(WebMessage.get(ValidationMessage.NAME_LENGTH_ERR, Messages
            .getString("web.action.back.order.OrderDetailUpdateAction.shipName")));
        valid = false;
      }
      
      if (StringUtil.getLength(header.getAddress().getAddress4()) > 200) {
        addErrorMessage(WebMessage.get(ValidationMessage.ADDRESS_LENGTH_ERR, Messages
            .getString("web.action.back.order.OrderDetailUpdateAction.shippingAddress4")));
        valid = false;
      }
      //add by lc 2012-03-08 end
      for (ShippingDetailBean detail : header.getShippingDetailList()) {
        if (validateBean(detail)) {
          String skuCode = detail.getSkuCode();
          String shopCode = header.getShippingShopCode();
          // 同一の規格は登録しない

          boolean add = true;
          for (Sku sku : checkSkuList) {
            if (sku.getShopCode().equals(shopCode) && sku.getSkuCode().equals(skuCode)) {
              add = false;
            }
          }
          if (add) {
            checkSkuList.add(new Sku(shopCode, skuCode));
          }
        } else {
          valid = false;
        }
        // ギフト数量の変更チェック(変更不可かつ、商品数量が変わっていた場合エラー
        GiftAttribute gift = getGiftAttribute(detail, detail.getGiftCode());
        if (gift != null && StringUtil.hasValue(gift.getValue()) && !gift.isUpdatableGift()) {
          if (detail.getGiftCode().equals(detail.getOrgGiftCode())
              && NumUtil.toLong(detail.getShippingDetailCommodityAmount()) > detail.getOrgShippingDetailCommodityAmount()) {
            addErrorMessage(WebMessage.get(OrderErrorMessage.CANNOT_CHANGE_GIFT_AMOUNT, gift.getGiftName()));
            valid = false;
          }
        } else {
          // 新たに追加されたギフトかつ使用不可の場合エラー

          if (StringUtil.hasValue(detail.getGiftCode())
              && isNotAvailableGift(header.getShippingShopCode(), detail.getSkuCode(), detail.getGiftCode())) {
            addErrorMessage(WebMessage.get(OrderErrorMessage.UNUSABLE_GIFT, gift.getGiftName()));
            valid = false;
          }
        }
      }
      shippingCount++;
    }

    valid &= validationShippingDetailStock(getBean(),true);
    // 有効な（商品が存在する）出荷先が無ければエラーとする
    if (shippingCount <= 0) {
      addErrorMessage(WebMessage.get(OrderErrorMessage.NO_SHIPPING));
      valid = false;
    }

    // 金額限界チェック
    if (valid) {
      valid &= numberLimitValidation(createOrderContainer(bean));
    }
    UtilService s = ServiceLocator.getUtilService(getLoginInfo());
    getBean().getOrderHeaderEdit().getAddress().setCityList(
        s.getCityNames(getBean().getOrderHeaderEdit().getAddress().getPrefectureCode()));
    for (ShippingHeaderBean sh : getBean().getShippingHeaderList()) {
      sh.getAddress().setCityList(s.getCityNames(sh.getAddress().getPrefectureCode()));
      if (StringUtil.isNullOrEmpty(sh.getAddress().getPhoneNumber1())
          && StringUtil.isNullOrEmpty(sh.getAddress().getPhoneNumber2())
          && StringUtil.isNullOrEmpty(sh.getAddress().getPhoneNumber3())
          && StringUtil.isNullOrEmpty(sh.getAddress().getMobileNumber())) {
        addErrorMessage(Messages.getString("web.action.back.order.OrderModifyMovePaymentAction.3")
            + WebMessage.get(ValidationMessage.NO_NUMBER));
        valid = false;
      }
      if (!StringUtil.hasValueAllOf(sh.getAddress().getPhoneNumber1(), sh.getAddress().getPhoneNumber2())) {
        if (StringUtil.hasValue(sh.getAddress().getPhoneNumber1()) || StringUtil.hasValue(sh.getAddress().getPhoneNumber2())
            || StringUtil.hasValue(sh.getAddress().getPhoneNumber3())) {
          if (!(StringUtil.hasValueAllOf(sh.getAddress().getPhoneNumber1(), sh.getAddress().getPhoneNumber2())
              && sh.getAddress().getPhoneNumber1().length() > 1 && sh.getAddress().getPhoneNumber2().length() > 5)) {
            addErrorMessage(Messages.getString("web.action.back.order.OrderModifyMovePaymentAction.6")
                + WebMessage.get(ValidationMessage.TRUE_NUMBER));
            valid = false;
          }
        }
      }
    }
    for (ShippingHeaderBean shippingHeader: getBean().getShippingHeaderList()) {
    	for (ShippingDetailBean detailBean : shippingHeader.getShippingDetailList()) {
    		if (CommodityType.GENERALGOODS.longValue().equals(detailBean.getCommodityType())
    				&& StringUtil.hasValue(detailBean.getCampaignName()) && !detailBean.getDiscountType().equals("2")) {
    			valid &= this.checkCampaignDiscount(getBean(), detailBean.getSkuCode(), false);
    			if (!valid){
    			  return false;
    			}
    		}
    	}
    }
    if (valid) {
      valid = validationCombineStock();
    }
    return valid;
  }

  private boolean validateOrderHeaderEdit(OrderHeaderBean order) {
    boolean valid = validateBean(order, Messages.getString("web.action.back.order.OrderModifyMovePaymentAction.1"));
    PaymentAddress address = order.getAddress();
    valid &= validateBean(address, Messages.getString("web.action.back.order.OrderModifyMovePaymentAction.1"));

    if (StringUtil.isNullOrEmpty(order.getAddress().getPhoneNumber1())
        && StringUtil.isNullOrEmpty(order.getAddress().getPhoneNumber2())
        && StringUtil.isNullOrEmpty(order.getAddress().getPhoneNumber3())
        && StringUtil.isNullOrEmpty(order.getAddress().getMobileNumber())) {
      addErrorMessage(Messages.getString("web.action.back.order.OrderModifyMovePaymentAction.1")
          + WebMessage.get(ValidationMessage.NO_NUMBER));
      valid = false;
    }
    if (!StringUtil.hasValueAllOf(order.getAddress().getPhoneNumber1(), order.getAddress().getPhoneNumber2())) {
      if (StringUtil.hasValue(order.getAddress().getPhoneNumber1()) || StringUtil.hasValue(order.getAddress().getPhoneNumber2())
          || StringUtil.hasValue(order.getAddress().getPhoneNumber3())) {
        if (!(StringUtil.hasValueAllOf(order.getAddress().getPhoneNumber1(), order.getAddress().getPhoneNumber2())
            && order.getAddress().getPhoneNumber1().length() > 1 && order.getAddress().getPhoneNumber2().length() > 5)) {
          addErrorMessage(Messages.getString("web.action.back.order.OrderModifyMovePaymentAction.2")
              + WebMessage.get(ValidationMessage.TRUE_NUMBER));
          valid = false;
        }
      }
    }
    
    if (StringUtil.getLength(order.getAddress().getLastName()) > 28) {
      addErrorMessage(WebMessage.get(ValidationMessage.NAME_LENGTH_ERR, Messages
          .getString("web.action.back.order.OrderDetailUpdateAction.customerName")));
      valid = false;
    }
    
    return valid;
  }

  private boolean validateShippingAddress(ShippingAddress address) {
    return validateBean(address, Messages.getString("web.action.back.order.OrderModifyMovePaymentAction.3")); //$NON-NLS-1$
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    OrderModifyBean bean = getBean();

    UtilService utilService = ServiceLocator.getUtilService(getLoginInfo());
    setDeliveryDatetimeInfo(bean);
    bean.setOperationMode("payment");
    bean.setDisplayMode(WebConstantCode.DISPLAY_EDIT);

    LoginInfo login = getLoginInfo();
    WebshopConfig config = getConfig();
    bean.setDisplayUpdateButton(false);
    if (config.getOperatingMode() == OperatingMode.MALL || config.getOperatingMode() == OperatingMode.ONE) {
      bean.setDisplayUpdateButton(Permission.ORDER_MODIFY_SITE.isGranted(login));
    } else if (config.getOperatingMode() == OperatingMode.SHOP) {
      bean.setDisplayUpdateButton(Permission.ORDER_MODIFY_SHOP.isGranted(login) || Permission.ORDER_MODIFY_SITE.isGranted(login));
    }

    boolean updateShippingInfo = false;

    // 送料再計算
    for (ShippingHeaderBean header : bean.getShippingHeaderList()) {
      if (header.isModified()) {
        recomputeShippingCharge(bean);
        updateShippingInfo = true;
      } else {
        // 旧受注と都道府県コードが変わっていれば再計算
        if (StringUtil.hasValue(header.getShippingNo())) {
          ShippingContainer org = bean.getOldOrderContainer().getShipping(header.getShippingNo());
          if (org != null && !org.getShippingHeader().getPrefectureCode().equals(header.getAddress().getPrefectureCode())) {
            recomputeShippingCharge(bean);
            header.setModified(true);
          }
        }
      }
    }
    // 金額再計算

    recomputePrice(bean);
    
    recomputePoint(bean);
    if (BigDecimalUtil.equals(bean.getPointInfo().getTotalPrice(), BigDecimal.ZERO) || updateShippingInfo) {
      recomputePaymentCommission(bean);
    }
    
    // 配送先情報が更新されていた場合は手数料も更新する
    List<CodeAttribute> commodityNameList = new ArrayList<CodeAttribute>();
    commodityNameList.add(new NameValue("请选择", ""));
    for (ShippingHeaderBean header : bean.getShippingHeaderList()) {
      header.getAddress().setAddress1(utilService.getPrefectureName(header.getAddress().getPrefectureCode()));
      header.getAddress().setAddress2(utilService.getCityName(header.getAddress().getPrefectureCode(), header.getAddress().getCityCode()));
      header.getAddress().setAddress3(utilService.getAreaName(header.getAddress().getAreaCode()));
    }
    for (String invoiceCommodityName : DIContainer.getWebshopConfig().getInvoiceCommodityNameList()) {
        commodityNameList.add(new NameValue(invoiceCommodityName, invoiceCommodityName));
      }
    bean.setInvoiceCommodityNameList(commodityNameList);
    bean.setDiscountTypeList(createDiscountTypeList());
    bean.setPersonalCouponList(createPersonalCouponList());
    
    
    if (StringUtil.isNullOrEmpty(bean.getShippingCharge())) {
    	
    	if (bean.getAfterTotalPrice().getShippingCharge()!=null){
        	bean.setShippingCharge(bean.getAfterTotalPrice().getShippingCharge().toString());
        } else {
        	bean.setShippingCharge("0");
        }
    } else {
    	for (ShippingHeaderBean header : bean.getShippingHeaderList()) {
    		header.setShippingCharge(bean.getShippingCharge());
    	}
    }
    recomputePrice(bean);
    if (StringUtil.isNullOrEmpty(bean.getDiscountPrice())){
    	if (bean.getAfterTotalPrice()!=null) {
        	bean.setDiscountPrice(bean.getAfterTotalPrice().getDiscountPrice().toString());
        } else {
        	bean.setDiscountPrice("0");
        }
    } else {
    	bean.getAfterTotalPrice().setDiscountPrice(new BigDecimal(bean.getDiscountPrice()));
    }
    createDeliveryToBean(bean);
    createOutCardPrice();
    setRequestBean(bean);
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {
    OrderModifyBean reqBean = (OrderModifyBean) getRequestBean();
    SiteManagementService service = ServiceLocator.getSiteManagementService(getLoginInfo());
    Long pointFunctionEnableFlg = service.getPointRule().getPointFunctionEnabledFlg();
    if (pointFunctionEnableFlg.equals(PointFunctionEnabledFlg.DISABLED.longValue())) {
      reqBean.setPointPartDisplayFlg(false);
    } else {
      String customerCode = reqBean.getOrderHeaderEdit().getCustomerCode();
      if (CustomerConstant.isCustomer(customerCode)) {
        reqBean.setPointPartDisplayFlg(true);
      } else {
        reqBean.setPointPartDisplayFlg(false);
      }
    }
    // add by v10-ch start
    CouponRule couponRule = service.getCouponRule();
    Long couponFunctionEnableFlg = couponRule.getCouponFunctionEnabledFlg();
    if (couponFunctionEnableFlg.equals(CouponFunctionEnabledFlg.DISABLED.longValue())) {
      reqBean.setCouponFunctionEnabledFlg(CouponFunctionEnabledFlg.DISABLED.getValue());
      reqBean.getCouponInfoBean().setAfterCouponPrice(BigDecimal.ZERO);
    } else {
      String customerCode = reqBean.getOrderHeaderEdit().getCustomerCode();
      if (CustomerConstant.isCustomer(customerCode)
          && BigDecimalUtil.isBelowOrEquals(couponRule.getCouponInvestPurchasePrice(), reqBean.getAfterTotalPrice()
              .getCommodityPrice()) && reqBean.getCouponInfoBean().getAfterCouponList().size() > 0) {
        reqBean.setCouponFunctionEnabledFlg(CouponFunctionEnabledFlg.ENABLED.getValue());
      } else {
        reqBean.setCouponFunctionEnabledFlg(CouponFunctionEnabledFlg.DISABLED.getValue());
      }
    }
    setRequestBean(reqBean);

  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.order.OrderModifyMovePaymentAction.4");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "1102023007";
  }

}
