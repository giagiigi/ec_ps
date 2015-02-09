package jp.co.sint.webshop.web.action.back.order;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.domain.CommodityType;
import jp.co.sint.webshop.data.domain.DeliverySpecificationType;
import jp.co.sint.webshop.data.domain.OrderStatus;
import jp.co.sint.webshop.data.domain.SetCommodityFlg;
import jp.co.sint.webshop.data.dto.Campaign;
import jp.co.sint.webshop.data.dto.CommodityHeader;
import jp.co.sint.webshop.data.dto.CustomerAddress;
import jp.co.sint.webshop.data.dto.DeliveryType;
import jp.co.sint.webshop.data.dto.OrderDetail;
import jp.co.sint.webshop.data.dto.ShippingDetail;
import jp.co.sint.webshop.data.dto.ShippingHeader;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.Price;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ShopManagementService;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.service.catalog.CommodityInfo;
import jp.co.sint.webshop.service.catalog.TaxUtil;
import jp.co.sint.webshop.service.customer.CustomerConstant;
import jp.co.sint.webshop.service.order.ShippingContainer;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.Sku;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.order.OrderModifyBean;
import jp.co.sint.webshop.web.bean.back.order.OrderModifyBean.AddCommodityBean;
import jp.co.sint.webshop.web.bean.back.order.OrderModifyBean.GiftAttribute;
import jp.co.sint.webshop.web.bean.back.order.OrderModifyBean.ShippingAddress;
import jp.co.sint.webshop.web.bean.back.order.OrderModifyBean.ShippingHeaderBean;
import jp.co.sint.webshop.web.bean.back.order.OrderModifyBean.ShippingHeaderBean.ShippingDetailBean;
import jp.co.sint.webshop.web.exception.URLNotFoundException;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.message.back.order.OrderErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1020230:受注修正のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class OrderModifyAddCommodityAction extends OrderModifyBaseAction {

  /**
   * 初期処理を実行します。
   */
  @Override
  public void init() {
    if (getConfig().isOne()) {
      OrderModifyBean bean = getBean();
      bean.getAddCommodityEdit().setShopListCode(getLoginInfo().getShopCode());
      setBean(bean);
    }
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {

    super.setAddressList(getBean());
    AddCommodityBean bean = getBean().getAddCommodityEdit();

    if (StringUtil.hasValueAllOf(this.getPathArg(0), this.getPathArg(1))) {
      String skuCode = this.getPathArg(0);
      String campaignCode = this.getPathArg(1);
      boolean giftAddFlg = false;
      for (ShippingDetailBean detailBean : getBean().getGiftList()) {
        if (detailBean.getSkuCode().equals(skuCode) && detailBean.getCampaignCode().equals(campaignCode)) {
          giftAddFlg = true;
          break;
        }
      }
      if (giftAddFlg) {
        return true;
      }
    }
    if (StringUtil.hasValue(this.getPathArg(0)) && this.getPathArg(0).equals("add_set")) {
      if (getBean().getSetCommodityInfo() == null) {
        throw new URLNotFoundException(WebMessage.get(ActionErrorMessage.BAD_URL));
      }
      return true;
    } else if (StringUtil.hasValue(this.getPathArg(0)) && this.getPathArg(0).equals("clear_set")) {
      return true;
    }
    boolean valid = validateBean(bean);
    if (!valid) {
      return valid;
    }

    String shopCode = bean.getShopListCode();
    String skuCode = bean.getSkuCode();
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    CommodityInfo commodity = service.getSkuInfo(shopCode, skuCode);
    if (commodity == null) {
      addErrorMessage(WebMessage.get(OrderErrorMessage.NOT_EXIST, skuCode));
      return false;
    }

    if (!isChangeAbleOrder()) {
      return false;
    }

    TaxUtil tu = DIContainer.get("TaxUtil");
    Long taxRate = tu.getTaxRate();
    CommodityHeader header = commodity.getHeader();
    Campaign campaign = service.getAppliedCampaignInfo(header.getShopCode(), header.getCommodityCode());

    // 追加商品が予約商品の場合エラー
    Price price = new Price(commodity.getHeader(), commodity.getDetail(), campaign, taxRate);
    if (price.isReserved()) {
      addErrorMessage(WebMessage.get(OrderErrorMessage.ORDER_MODIFY_ADD_RESERVE));
      return false;
    }
    if (!service.isListed(shopCode, commodity.getHeader().getCommodityCode())) {
      addErrorMessage(WebMessage.get(OrderErrorMessage.NOT_EXIST, skuCode));
      return false;
    }

    if (CommodityType.GIFT.longValue().equals(header.getCommodityType())) {
      addErrorMessage(WebMessage.get(OrderErrorMessage.NOT_EXIST, skuCode));
      return false;
    }
    if (SetCommodityFlg.OBJECTIN.longValue().equals(header.getSetCommodityFlg())) {
      valid &= validationSetStock(getBean(), skuCode, false);
      setRequestBean(getBean());
    } else {
      valid &= validationStock(shopCode, skuCode, getOldCommodityTotalAmount(shopCode, skuCode), getCurrentCommodityTotalAmount(
          shopCode, skuCode)
          + NumUtil.toLong(bean.getAddAmount()));
      if (valid) {
        Long allCount = 0L;
        Long allStock = 0L;
        if (StringUtil.hasValue(header.getOriginalCommodityCode()) && header.getCombinationAmount() != null) {
          allStock = service.getAvailableStock(header.getShopCode(), header.getOriginalCommodityCode());
          allCount = (getCurrentCommodityTotalAmount(shopCode, skuCode) + NumUtil.toLong(bean.getAddAmount()) - getOldCommodityTotalAmount(
              shopCode, skuCode))
              * header.getCombinationAmount();
        } else {
          allStock = service.getAvailableStock(header.getShopCode(), skuCode);
          allCount = getCurrentCommodityTotalAmount(shopCode, skuCode) + NumUtil.toLong(bean.getAddAmount())
              - getOldCommodityTotalAmount(shopCode, skuCode);
        }

        for (ShippingHeaderBean headerBean : getBean().getShippingHeaderList()) {
          if (headerBean.getShippingShopCode().equals(shopCode)) {
            for (ShippingDetailBean detail : headerBean.getShippingDetailList()) {
              if (!detail.getSkuCode().equals(skuCode)) {
                CommodityHeader ch = service.getCommodityHeader(shopCode, detail.getSkuCode());
                boolean rel = false;
                if (StringUtil.hasValue(header.getOriginalCommodityCode())) {
                  rel = (header.getOriginalCommodityCode().equals(ch.getCommodityCode()) || (StringUtil.hasValue(ch
                      .getOriginalCommodityCode()) && header.getOriginalCommodityCode().equals(ch.getOriginalCommodityCode())));
                } else {
                  rel = (StringUtil.hasValue(ch.getOriginalCommodityCode()) && header.getCommodityCode().equals(
                      ch.getOriginalCommodityCode()));
                }
                
                if (rel) {
                  if (StringUtil.hasValue(ch.getOriginalCommodityCode()) && ch.getCombinationAmount() != null) {
                    allCount = allCount
                        + (NumUtil.toLong(detail.getShippingDetailCommodityAmount()) - getOldCommodityTotalAmount(shopCode, detail
                            .getSkuCode())) * ch.getCombinationAmount();
                  } else {
                    allCount = allCount + NumUtil.toLong(detail.getShippingDetailCommodityAmount())
                        - getOldCommodityTotalAmount(shopCode, detail.getSkuCode());
                  }
                }
              }
            }
          }
        }
        if (allCount > allStock) {
          addErrorMessage("可用库存不足。");
          return false;
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
    OrderModifyBean bean = getBean();
    if (StringUtil.hasValue(this.getPathArg(0)) && this.getPathArg(0).equals("clear_set")) {
      bean.setSetCommodityInfo(null);
      setRequestBean(bean);
      return BackActionResult.RESULT_SUCCESS;
    }
    boolean giftAddFlg = false;
    boolean setFlg = false;
    ShippingDetailBean giftDetailBean = null;
    if (StringUtil.hasValueAllOf(this.getPathArg(0), this.getPathArg(1))) {
      String skuCode = this.getPathArg(0);
      String campaignCode = this.getPathArg(1);
      for (ShippingDetailBean detailBean : getBean().getGiftList()) {
        if (detailBean.getSkuCode().equals(skuCode) && detailBean.getCampaignCode().equals(campaignCode)) {
          giftAddFlg = true;
          giftDetailBean = detailBean;
          break;
        }
      }
    }

    if (giftAddFlg) {
      bean.getShippingHeaderList().get(0).getShippingDetailList().add(giftDetailBean);
    } else {

      AddCommodityBean addition = bean.getAddCommodityEdit();
      String shopCode = addition.getShopListCode();
      String skuCode = addition.getSkuCode();
      CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
      CommodityInfo commodity = service.getSkuInfo(shopCode, skuCode);
      if (StringUtil.hasValue(this.getPathArg(0)) && this.getPathArg(0).equals("add_set")) {
        if (!validationSetStock(bean, bean.getSetCommodityInfo().getSkuCode(), true)) {
          setRequestBean(bean);
          return BackActionResult.RESULT_SUCCESS;
        }
      } else {
        if (!SetCommodityFlg.OBJECTIN.longValue().equals(commodity.getHeader().getSetCommodityFlg())) {
          addCommodityToShippingList(bean, addition);
          // 出荷先数の変更に伴って削除ボタン制御
          boolean deletable = (bean.getShippingHeaderList().size() > 1);
          for (ShippingHeaderBean header : bean.getShippingHeaderList()) {
            header.setDeletable(deletable);
          }
        }
      }
    }

    recomputeGift(bean, true);
    this.recomputeShippingCharge(bean);
    recomputePrice(bean);
    recomputePoint(bean);
    recomputePaymentCommission(bean);
    recomputePrice(bean);

    numberLimitValidation(createOrderContainer(bean));

    if (!giftAddFlg && !setFlg) {
      // 画面入力情報のクリア
      AddCommodityBean addCommodityBean = bean.getAddCommodityEdit();
      addCommodityBean.setShopListCode("");
      addCommodityBean.setSkuCode("");
      addCommodityBean.setAddAmount("");
    }

    createDeliveryToBean(bean);
    createOutCardPrice();
    setRequestBean(bean);
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * 配送先に商品を追加する
   * 
   * @param bean
   * @param addData
   */
  private void addCommodityToShippingList(OrderModifyBean bean, AddCommodityBean addData) {
    // 商品情報取得
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    CommodityInfo commodity = service.getSkuInfo(bean.getAddCommodityEdit().getShopListCode(), addData.getSkuCode());
    if (commodity == null) {
      addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, Messages
          .getString("web.action.back.order.OrderModifyAddCommodityAction.0")));
      return;
    }
    // 旧商品と同じものなら古い配送種別情報を使う
    Long deliveryTypeNo = getDeliveryTypeNo(commodity.getHeader().getShopCode(), commodity.getHeader().getCommodityCode());
    if (deliveryTypeNo == null) {
      deliveryTypeNo = commodity.getHeader().getDeliveryTypeNo();
    }

    boolean failAddition = true;
    for (ShippingHeaderBean header : bean.getShippingHeaderList()) {
      // 同一配送先+配送種別情報があれば更新する
      if (addCommodityToShipping(header, addData)) {
        recomputeShippingCharge(bean);
      }
      failAddition = false;
    }

    // 登録先が無かった場合はエラーとする
    if (failAddition) {
      String customerCode = bean.getOrderHeaderEdit().getCustomerCode();
      if (CustomerConstant.isCustomer(customerCode)) {
        ShopManagementService shopService = ServiceLocator.getShopManagementService(getLoginInfo());
        UtilService utilService = ServiceLocator.getUtilService(getLoginInfo());

        ShippingHeaderBean headerBean = new ShippingHeaderBean();
        headerBean.setModified(true);
        
        // 顧客マスタからアドレス情報取得
        CustomerService custService = ServiceLocator.getCustomerService(getLoginInfo());
        CustomerAddress custAddress = custService.getCustomerAddress(customerCode, NumUtil.toLong(addData.getAddressListCode()));
        if (custAddress == null) {
          
          // DBに存在しない顧客の場合は出荷ヘッダから情報を取得する
          ShippingHeader header = getShippingHeaderFromAddressNo(addData.getAddressListCode());
          if (header == null) {
            addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, Messages
                .getString("web.action.back.order.OrderModifyAddCommodityAction.1")));
            return;
          }
          ShippingAddress address = new ShippingAddress();
          address.setFirstName(header.getAddressFirstName());
          address.setFirstNameKana(header.getAddressFirstNameKana());
          address.setLastName(header.getAddressLastName());
          address.setLastNameKana(header.getAddressLastNameKana());
          address.setPostalCode(header.getPostalCode());
          address.setPrefectureCode(header.getPrefectureCode());
          address.setAddress1(header.getAddress1());
          address.setAddress2(header.getAddress2());
          address.setAddress3(header.getAddress3());
          address.setAddress4(header.getAddress4());
          if (StringUtil.hasValue(header.getPhoneNumber())) {
            String[] phoneNumber = header.getPhoneNumber().split("-");
            if (phoneNumber.length == 2) {
              address.setPhoneNumber1(phoneNumber[0]);
              address.setPhoneNumber2(phoneNumber[1]);
            } else if (phoneNumber.length == 3) {
              address.setPhoneNumber1(phoneNumber[0]);
              address.setPhoneNumber2(phoneNumber[1]);
              address.setPhoneNumber3(phoneNumber[2]);
            }
          } else {
            address.setPhoneNumber1("");
            address.setPhoneNumber2("");
            address.setPhoneNumber3("");
          }
          address.setMobileNumber(header.getMobileNumber());
          headerBean.setShippingAddressNo(header.getAddressNo());
          headerBean.setAddress(address);
        } else {
          ShippingAddress address = new ShippingAddress();
          address.setFirstName(custAddress.getAddressFirstName());
          address.setFirstNameKana(custAddress.getAddressFirstNameKana());
          address.setLastName(custAddress.getAddressLastName());
          address.setLastNameKana(custAddress.getAddressLastNameKana());
          address.setPostalCode(custAddress.getPostalCode());
          address.setPrefectureCode(custAddress.getPrefectureCode());
          address.setAddress1(custAddress.getAddress1());
          address.setAddress2(custAddress.getAddress2());
          address.setAddress3(custAddress.getAddress3());
          address.setAddress4(custAddress.getAddress4());
          if (StringUtil.hasValue(custAddress.getPhoneNumber())) {
            String[] phoneNumber = custAddress.getPhoneNumber().split("-");
            if (phoneNumber.length == 2) {
              address.setPhoneNumber1(phoneNumber[0]);
              address.setPhoneNumber2(phoneNumber[1]);
            } else if (phoneNumber.length == 3) {
              address.setPhoneNumber1(phoneNumber[0]);
              address.setPhoneNumber2(phoneNumber[1]);
              address.setPhoneNumber3(phoneNumber[2]);
            }
          }
          address.setMobileNumber(custAddress.getMobileNumber());
          headerBean.setShippingAddressNo(custAddress.getAddressNo());
          headerBean.setAddress(address);
        }
        String shopCode = commodity.getHeader().getShopCode();
        String shopName = utilService.getShopName(shopCode);
        DeliveryType deliveryType = shopService.getDeliveryType(shopCode, deliveryTypeNo);

        headerBean.setShippingShopCode(shopCode);
        headerBean.setShippingShopName(shopName);
        headerBean.setShippingTypeCode(NumUtil.toString(commodity.getHeader().getDeliveryTypeNo()));
        headerBean.setShippingTypeName(deliveryType.getDeliveryTypeName());

        // 配送指定日時の設定
        DeliverySpecificationType deliverySType = DeliverySpecificationType.fromValue(deliveryType.getDeliverySpecificationType());
        if (deliverySType == DeliverySpecificationType.DATE_AND_TIME || deliverySType == DeliverySpecificationType.DATE_ONLY) {
          headerBean.setDisplayDeliveryAppointedDate(true);
        } else {
          headerBean.setDisplayDeliveryAppointedDate(false);
        }
        headerBean.setDeliveryAppointedDate("");

        // 配送希望時間帯
        if (deliverySType == DeliverySpecificationType.DATE_AND_TIME || deliverySType == DeliverySpecificationType.TIME_ONLY) {
          headerBean.setDeliveryAppointedStartTimeList(createTimeList());
          headerBean.setDeliveryAppointedEndTimeList(createTimeList());
          headerBean.setDeliveryAppointedStartTime("");
          headerBean.setDeliveryAppointedEndTime("");
          headerBean.setDisplayDeliveryAppointedTime(true);
        } else {
          headerBean.setDisplayDeliveryAppointedTime(false);
        }

        headerBean.setDeliveryRemark("");
        List<ShippingDetailBean> shippingDetailList = new ArrayList<ShippingDetailBean>();
        ShippingDetailBean detail = createShippingDetailBean(shopCode, addData.getSkuCode(), "", NumUtil.toLong(addData
            .getAddAmount()), 0L);
        shippingDetailList.add(detail);
        headerBean.setShippingDetailList(shippingDetailList);
        recomputeShippingCharge(bean);
        bean.getShippingHeaderList().add(headerBean);
      } else {
        addErrorMessage(WebMessage.get(OrderErrorMessage.ADD_COMMODITY_SHIPPING_NOT_FOUND));

      }
    }
  }

  private Long getDeliveryTypeNo(String shopCode, String commodityCode) {
    String skuCode = "";
    for (OrderDetail detail : getBean().getOldOrderContainer().getOrderDetails()) {
      if (detail.getCommodityCode().equals(commodityCode)) {
        skuCode = detail.getSkuCode();
      }
    }
    if (StringUtil.isNullOrEmpty(skuCode)) {
      return null;
    }
    for (ShippingContainer container : getBean().getOldOrderContainer().getShippings()) {
      ShippingDetail detail = container.getShippingDetail(new Sku(shopCode, skuCode));
      if (detail != null) {
        return container.getShippingHeader().getDeliveryTypeNo();
      }
    }
    return null;
  }

  /**
   * 出荷先情報に商品を追加する。<BR>
   * 同一SKUがあれば数値のみ追加
   * 
   * @param baseBean
   * @param addData
   */
  public boolean addCommodityToShipping(ShippingHeaderBean baseBean, AddCommodityBean addData) {
    // 数量最大値の取得
    int maxLength = 0;
    try {
      maxLength = ShippingDetailBean.class.getField("shippingDetailSkuCode").getAnnotation(Length.class).value();
    } catch (NoSuchFieldException e) {
      // 取得に失敗した場合は4桁
      maxLength = 4;
    }

    // 別商品追加判定フラグ true-別商品と判断し出荷先に商品を追加 false-同一商品として数量のみ更新
    boolean insert = true;
    for (ShippingDetailBean detail : baseBean.getShippingDetailList()) {
      if (detail.getSkuCode().equals(addData.getSkuCode()) && !detail.isDiscountCommodity()) {
        // 同一SKUコードのものがあれば数値のみ追加
        String amount = NumUtil.summary(detail.getShippingDetailCommodityAmount(), addData.getAddAmount());
        // 合計値が限界値を超える場合はエラーを設定して追加せずに終了
        if (amount.length() > maxLength) {
          addErrorMessage(WebMessage.get(OrderErrorMessage.ADD_COMMODITY_AMOUNT_OVER, Integer.toString(maxLength)));
          return false;
        }
        // 対象ギフトが使用不可かつ、ギフトコードが旧受注のままの場合エラー
        GiftAttribute gift = getGiftAttribute(detail, detail.getGiftCode());
        if (gift != null && StringUtil.hasValue(gift.getValue()) && !gift.isUpdatableGift()
            && StringUtil.coalesce(detail.getGiftCode(), "").equals(StringUtil.coalesce(detail.getOrgGiftCode(), ""))) {
          addErrorMessage(WebMessage.get(OrderErrorMessage.CANNOT_CHANGE_GIFT_AMOUNT, gift.getGiftName()));
          return false;
        } else if (gift == null) {
          addErrorMessage(WebMessage.get(OrderErrorMessage.CANNOT_CHANGE_GIFT_AMOUNT, ""));
          return false;
        }
        detail.setShippingDetailCommodityAmount(amount);
        insert = false;
      }
    }
    if (insert) {
      Long amount = NumUtil.toLong(addData.getAddAmount());
      ShippingDetailBean detail = createShippingDetailBean(addData.getShopListCode(), addData.getSkuCode(), "", NumUtil
          .toLong(addData.getAddAmount()), amount);
      boolean reserveOrder = getBean().getOrderStatus() == OrderStatus.RESERVED;
      if (detail.isReserve() != reserveOrder) {
        // 販売商品と予約商品の同一受注での購入は不可

        addErrorMessage(WebMessage.get(OrderErrorMessage.ADD_COMMODITY_SHIPPING_NOT_FOUND));
        return false;
      } else if (detail.isReserve()) {
        // 予約商品は別SKUの追加は不可
        addErrorMessage(WebMessage.get(OrderErrorMessage.ADD_COMMODITY_SHIPPING_NOT_FOUND));
        return false;
      }
      List<ShippingDetailBean> shippingDetailList = baseBean.getShippingDetailList();
      shippingDetailList.add(detail);
    }
    return true;
  }

  /**
   * AddressNoに一致する旧受注の出荷ヘッダを取得する（住所情報取得用）<br>
   * 配送種別の取得に使用してはいけない
   * 
   * @param addressNo
   * @return
   */
  private ShippingHeader getShippingHeaderFromAddressNo(String addressNo) {
    Long longAddressNo = NumUtil.toLong(addressNo);
    for (ShippingContainer shipping : getBean().getOldOrderContainer().getShippings()) {
      ShippingHeader header = shipping.getShippingHeader();
      if (header.getAddressNo().equals(longAddressNo)) {
        return header;
      }
    }
    return null;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.order.OrderModifyAddCommodityAction.2");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "1102023001";
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
}
