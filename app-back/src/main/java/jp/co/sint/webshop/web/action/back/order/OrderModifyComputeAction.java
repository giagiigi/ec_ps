package jp.co.sint.webshop.web.action.back.order;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.domain.CommodityType;
import jp.co.sint.webshop.data.dto.OrderDetail;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.catalog.CommodityInfo;
import jp.co.sint.webshop.service.order.ShippingContainer;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.Sku;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.order.OrderModifyBean;
import jp.co.sint.webshop.web.bean.back.order.OrderModifyBean.GiftAttribute;
import jp.co.sint.webshop.web.bean.back.order.OrderModifyBean.ShippingAddress;
import jp.co.sint.webshop.web.bean.back.order.OrderModifyBean.ShippingHeaderBean;
import jp.co.sint.webshop.web.bean.back.order.OrderModifyBean.ShippingHeaderBean.ShippingDetailBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.message.back.order.OrderErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1020230:受注修正のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class OrderModifyComputeAction extends OrderModifyBaseAction {

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    boolean valid = true;
    super.setAddressList(getBean());
    if (getPathArgsLength() < 3) {
      addErrorMessage(WebMessage.get(ActionErrorMessage.BAD_URL));
    } else {
      ShippingHeaderBean header = getBean().getShippingHeaderBean(getPathShopCode(), getPathAddressNo(), getPathDeliveryTypeCode());
      if (header == null) {
        addErrorMessage(WebMessage.get(ActionErrorMessage.BAD_URL));
      }

      if (!isChangeAbleOrder()) {
        return false;
      }

      valid &= validateShippingAddress(header.getAddress());
      if (!valid) {
        return valid;
      }

      List<Sku> checkSkuList = new ArrayList<Sku>();
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
      valid &= validationShippingDetailStock(getBean(), false);
    }

    if (valid) {
      valid = validationCombineStock();
    }
    return valid;
  }

  private boolean validateShippingAddress(ShippingAddress address) {
    return validateBean(address, Messages.getString("web.action.back.order.OrderModifyComputeAction.0"));
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    OrderModifyBean bean = getBean();
    ShippingHeaderBean updateShipping = bean
        .getShippingHeaderBean(getPathShopCode(), getPathAddressNo(), getPathDeliveryTypeCode());

    // 数量・ギフト・SKUが旧受注と変わっていない場合は更新しない
    boolean update = false;
    ShippingContainer orgShipping = bean.getOldOrderContainer()
        .getShipping(StringUtil.coalesce(updateShipping.getShippingNo(), ""));

    for (ShippingDetailBean detail : updateShipping.getShippingDetailList()) {
      updateOrderDetailCommodityInfo(updateShipping.getShippingShopCode(), detail.getSkuCode(), detail);
      // 旧配送にないSKUの場合再計算
      if (orgShipping == null
          || orgShipping.getShippingDetail(new Sku(orgShipping.getShippingHeader().getShopCode(), detail.getSkuCode())) == null) {
        update = true;
      }
      // ギフトか数量が変更されている場合は再計算
      if (!detail.getOrgShippingDetailCommodityAmount().equals(NumUtil.toLong(detail.getShippingDetailCommodityAmount()))
          || !detail.getOrgGiftCode().equals(detail.getGiftCode())) {
        update = true;
      }
    }
    // 住所が変更になっている場合は再計算
    if (orgShipping != null
        && !orgShipping.getShippingHeader().getPrefectureCode().equals(updateShipping.getAddress().getPrefectureCode())) {
      update = true;
    }

    update = true;
    if (update || updateShipping.isModified()) {
      this.recomputeGift(bean, true);
      recomputeShippingCharge(bean);
      recomputePrice(bean);
      recomputePoint(bean);
      recomputePaymentCommission(bean);
      recomputePrice(bean);
      numberLimitValidation(createOrderContainer(bean));
      createDeliveryToBean(bean);
    }
    createOutCardPrice();
    setRequestBean(bean);
    return BackActionResult.RESULT_SUCCESS;
  }

  private void updateOrderDetailCommodityInfo(String shopCode, String skuCode, ShippingDetailBean detail) {
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());

    OrderDetail newOrderDetail = null;
    // 配送商品情報にOrderContainerを設定（登録時に使用する）
    OrderDetail oldOrderDetail = getBean().getOldOrderContainer().getOrderDetail(new Sku(shopCode, skuCode));
    if (oldOrderDetail != null) {
      newOrderDetail = detail.getOrderDetailCommodityInfo();
      if (CommodityType.GENERALGOODS.longValue().equals(detail.getCommodityType())) {
        if (StringUtil.hasValue(detail.getCampaignCode()) && !detail.getDiscountType().equals("2")) {
          if (detail.getDiscountValue() != null) {
            newOrderDetail.setRetailPrice(newOrderDetail.getRetailPrice().add(detail.getDiscountValue()));
          }
          detail.setCampaignName(null);
          detail.setDiscountValue(null);
          checkCampaignDiscount(getBean(), skuCode, true);
        }
      }
    } else {
      CommodityInfo skuInfo = service.getSkuInfo(shopCode, skuCode);
      newOrderDetail = createOrderDetailCommodityInfo(skuInfo);
      detail.setOrderDetailCommodityInfo(newOrderDetail);
      if (CommodityType.GENERALGOODS.longValue().equals(detail.getCommodityType())) {
        detail.setCampaignName(null);
        detail.setDiscountValue(null);
        if (StringUtil.hasValue(detail.getCampaignCode())) {
          checkCampaignDiscount(getBean(), skuCode, true);
        }
      }
    }
    detail.setOrderDetailCommodityInfo(newOrderDetail);
  }

  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {
    OrderModifyBean bean = (OrderModifyBean) getRequestBean();

    bean.setOperationMode("shipping");

    setRequestBean(bean);
  }

  private int getPathArgsLength() {
    return getRequestParameter().getPathArgs().length;
  }

  private String getPathArg(int index) {
    if (getPathArgsLength() > index) {
      return getRequestParameter().getPathArgs()[index];
    }
    return "";
  }

  private String getPathShopCode() {
    return getPathArg(0);
  }

  private String getPathAddressNo() {
    return getPathArg(1);
  }

  private String getPathDeliveryTypeCode() {
    return getPathArg(2);
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.order.OrderModifyComputeAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "1102023003";
  }

}
