package jp.co.sint.webshop.web.action.back.shop;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.configure.WebshopConfig;
import jp.co.sint.webshop.data.domain.DeliverySpecificationType;
import jp.co.sint.webshop.data.domain.OperatingMode;
import jp.co.sint.webshop.data.dto.DeliveryType;
import jp.co.sint.webshop.data.dto.ShippingCharge;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.ShopManagementService;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.shop.DeliveryTypeEditBean;
import jp.co.sint.webshop.web.bean.back.shop.DeliveryTypeEditBean.RegionBlockCharge;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.message.back.shop.ShopErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1050620:配送種別設定明細のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class DeliveryTypeEditInsertAction extends WebBackAction<DeliveryTypeEditBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {

    boolean authorization = false;
    if (getConfig().getOperatingMode().equals(OperatingMode.ONE)) {
      authorization = Permission.SHOP_MANAGEMENT_UPDATE_SITE.isGranted(getLoginInfo());
    } else {
      authorization = Permission.SHOP_MANAGEMENT_UPDATE_SHOP.isGranted(getLoginInfo());
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
    DeliveryTypeEditBean bean = getBean();
    boolean valid = validateBean(bean);

    List<RegionBlockCharge> chargeList = bean.getRegionBlockChargeList();
    for (RegionBlockCharge charge : chargeList) {
      if (!validateBean(charge)) {
        valid = false;
        break;
      }
    }

    if (!valid) {
      return valid;
    }
    // 配送指定が「日のみ」か「日時両方」の場合
    WebshopConfig config = getConfig();
    if (bean.getDeliverySpecificationType().equals(DeliverySpecificationType.DATE_ONLY.getValue())
        || bean.getDeliverySpecificationType().equals(DeliverySpecificationType.DATE_AND_TIME.getValue())) {
      for (RegionBlockCharge charge : chargeList) {
        if (Integer.parseInt(charge.getLeadTime()) < config.getMinimalLeadTime()) {
          addErrorMessage(WebMessage.get(ShopErrorMessage.LEAD_TIME_MINIMAL_ERROR, Integer.toString(config.getMinimalLeadTime())));
          valid = false;
          break;
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
    ShopManagementService service = ServiceLocator.getShopManagementService(getLoginInfo());

    DeliveryTypeEditBean bean = getBean();

    DeliveryType deliveryType = new DeliveryType();
    List<ShippingCharge> chargeList = new ArrayList<ShippingCharge>();

    deliveryType.setShopCode(getLoginInfo().getShopCode());
    deliveryType.setDeliveryTypeNo(NumUtil.toLong(bean.getDeliveryTypeNo()));
    deliveryType.setDeliveryTypeName(bean.getDeliveryTypeName());
    deliveryType.setDeliverySpecificationType(Long.parseLong(bean.getDeliverySpecificationType()));
    deliveryType.setParcelUrl(bean.getParcelUrl());
    deliveryType.setDisplayFlg(Long.parseLong(bean.getDisplayFlg()));
    deliveryType.setShippingChargeTaxType(Long.parseLong(bean.getChargeTaxType()));
    deliveryType.setShippingChargeFreeFlg(Long.parseLong(bean.getShippingChargeFreeFlg()));
    deliveryType.setShippingChargeFreeThreshold(NumUtil.parse(bean.getShippingChargeFreeThreshold()));
    deliveryType.setShippingChargeFlg(Long.parseLong(bean.getShippingChargeFlg()));
    deliveryType.setShippingChargeThreshold(NumUtil.parse(bean.getShippingChargeThreshold()));

    for (RegionBlockCharge charge : bean.getRegionBlockChargeList()) {
      ShippingCharge dto = new ShippingCharge();
      dto.setShopCode(getLoginInfo().getShopCode());
      dto.setDeliveryTypeNo(NumUtil.toLong(bean.getDeliveryTypeNo()));
      dto.setRegionBlockId(NumUtil.toLong(charge.getRegionBlockId()));
      dto.setShippingCharge(NumUtil.parse(charge.getShippingCharge()));
      dto.setLeadTime(NumUtil.toLong(charge.getLeadTime()));
      chargeList.add(dto);
    }

    ServiceResult result = service.insertDeliveryType(deliveryType, chargeList);
    if (result.hasError()) {
      for (ServiceErrorContent content : result.getServiceErrorList()) {
        if (content == CommonServiceErrorContent.DUPLICATED_REGISTER_ERROR) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.DUPLICATED_REGISTER_ERROR,
              Messages.getString("web.action.back.shop.DeliveryTypeEditInsertAction.0")));
          setRequestBean(bean);
          return BackActionResult.RESULT_SUCCESS;
        } else if (content == CommonServiceErrorContent.VALIDATION_ERROR) {
          return BackActionResult.SERVICE_VALIDATION_ERROR;
        } else {
          return BackActionResult.SERVICE_ERROR;
        }
      }
    }
    setNextUrl("/app/shop/delivery_type_edit/init/" + getBean().getDeliveryTypeNo() + "/" + WebConstantCode.COMPLETE_INSERT);
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.shop.DeliveryTypeEditInsertAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "4105062003";
  }

}
