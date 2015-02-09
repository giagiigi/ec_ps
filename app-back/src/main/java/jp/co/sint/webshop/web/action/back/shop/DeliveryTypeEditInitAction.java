package jp.co.sint.webshop.web.action.back.shop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.sint.webshop.data.domain.DisplayFlg;
import jp.co.sint.webshop.data.domain.OperatingMode;
import jp.co.sint.webshop.data.domain.TaxType;
import jp.co.sint.webshop.data.dto.DeliveryType;
import jp.co.sint.webshop.data.dto.RegionBlock;
import jp.co.sint.webshop.data.dto.Shop;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ShopManagementService;
import jp.co.sint.webshop.service.shop.ShippingChargeSuite;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.shop.DeliveryTypeEditBean;
import jp.co.sint.webshop.web.bean.back.shop.DeliveryTypeEditBean.RegionBlockCharge;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.CompleteMessage;
import jp.co.sint.webshop.web.message.back.shop.ShopErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1050620:配送種別設定明細のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class DeliveryTypeEditInitAction extends
		WebBackAction<DeliveryTypeEditBean> {

	/**
	 * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
	 * 
	 * @return アクションの実行を認可する場合はtrue
	 */
	@Override
	public boolean authorize() {
		boolean authorization = false;
		if (getConfig().getOperatingMode().equals(OperatingMode.ONE)) {
			authorization = Permission.SHOP_MANAGEMENT_READ_SITE
					.isGranted(getLoginInfo());
		} else {
			authorization = Permission.SHOP_MANAGEMENT_READ_SHOP
					.isGranted(getLoginInfo());
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
		return true;
	}

	/**
	 * アクションを実行します。
	 * 
	 * @return アクションの実行結果
	 */
	@Override
	public WebActionResult callService() {
		DeliveryTypeEditBean reqBean = getBean();

		if (getCompleteParam().equals(WebConstantCode.COMPLETE_DELETE)) {
			getBean().setUpdateMode(false);
			getBean().setInsertMode(false);

			setRequestBean(reqBean);
			return BackActionResult.RESULT_SUCCESS;
		}

		ShopManagementService service = ServiceLocator
				.getShopManagementService(getLoginInfo());
		// 更新時の情報取得処理
		if (getDeliveryTypeNo().length() > 0) {

			DeliveryType deliveryType = service.getDeliveryType(getLoginInfo()
					.getShopCode(), NumUtil.toLong(getDeliveryTypeNo()));

			if (deliveryType == null) {
				addErrorMessage(WebMessage
						.get(
								ShopErrorMessage.CODE_FAILED,
								Messages
										.getString("web.action.back.shop.DeliveryTypeEditInitAction.0")));

				setRequestBean(reqBean);
				return BackActionResult.RESULT_SUCCESS;
			}

			reqBean.setDeliveryTypeNo(NumUtil.toString(deliveryType
					.getDeliveryTypeNo()));
			reqBean.setDeliveryTypeName(deliveryType.getDeliveryTypeName());
			reqBean.setDeliverySpecificationType(Long.toString(deliveryType
					.getDeliverySpecificationType()));
			reqBean.setChargeTaxType(Long.toString(deliveryType
					.getShippingChargeTaxType()));
			reqBean.setParcelUrl(deliveryType.getParcelUrl());
			reqBean.setShippingChargeFreeFlg(Long.toString(deliveryType
					.getShippingChargeFreeFlg()));
			reqBean.setShippingChargeFreeThreshold(NumUtil
					.toString(deliveryType.getShippingChargeFreeThreshold()));
			reqBean.setShippingChargeFlg(Long.toString(deliveryType
					.getShippingChargeFlg()));
			reqBean.setShippingChargeThreshold(NumUtil.toString(deliveryType
					.getShippingChargeThreshold()));
			reqBean.setDisplayFlg(Long.toString(deliveryType.getDisplayFlg()));
			reqBean.setUpdateDatetime(deliveryType.getUpdatedDatetime());

			Map<String, ShippingChargeSuite> shippingMap = convertListToMap(service
					.getShippingChargeList(deliveryType.getShopCode(),
							deliveryType.getDeliveryTypeNo()));
			List<RegionBlockCharge> orgBlockList = getRegionBlockChargeOrgList();
			for (RegionBlockCharge orgBlock : orgBlockList) {
				ShippingChargeSuite shipping = shippingMap.get(orgBlock
						.getRegionBlockId());
				if (shipping != null) {
					orgBlock.setRegionBlockName(shipping.getRegionBlockName());
					orgBlock.setRegionBlockId(Long.toString(shipping
							.getShippingCharge().getRegionBlockId()));
					orgBlock.setShippingCharge(NumUtil.toString(shipping
							.getShippingCharge().getShippingCharge()));
					orgBlock.setLeadTime(Long.toString(shipping
							.getShippingCharge().getLeadTime()));
					orgBlock.setUpdateDatetime(shipping.getShippingCharge()
							.getUpdatedDatetime());
				}

			}
			reqBean.setRegionBlockChargeList(orgBlockList);

			reqBean.setInsertMode(false);
			reqBean.setUpdateMode(true);
			reqBean.setDisplayDeleteButtonFlg(service.isDeletableDelivery(
					deliveryType.getShopCode(), deliveryType
							.getDeliveryTypeNo()));
			reqBean.setDeliveryCodeDisplayMode(WebConstantCode.DISPLAY_HIDDEN);
		} else {
			// 新規登録時
			reqBean.setRegionBlockChargeList(getRegionBlockChargeOrgList());

			reqBean.setDisplayFlg(DisplayFlg.VISIBLE.getValue());
			reqBean.setChargeTaxType(TaxType.INCLUDED.getValue());

			reqBean.setInsertMode(true);
			reqBean.setUpdateMode(false);
			reqBean.setDeliveryCodeDisplayMode(WebConstantCode.DISPLAY_EDIT);
		}
		setRequestBean(reqBean);

		return BackActionResult.RESULT_SUCCESS;
	}

	/**
	 * 地域ブロックマスタから地域ブロック一覧を取得し<BR>
	 * 金額情報を付加したオブジェクト(RegionBlockCharge)のリストとして返す
	 * 
	 * @return regionBlockList
	 */
	private List<RegionBlockCharge> getRegionBlockChargeOrgList() {
		ShopManagementService service = ServiceLocator
				.getShopManagementService(getLoginInfo());
		Shop shop = new Shop();
		shop.setShopCode(getLoginInfo().getShopCode());
		List<RegionBlock> orgBlockList = service.getRegionBlockList(shop);
		List<RegionBlockCharge> regionBlockList = new ArrayList<RegionBlockCharge>();
		for (RegionBlock block : orgBlockList) {
			RegionBlockCharge charge = new RegionBlockCharge();
			charge.setRegionBlockId(Long.toString(block.getRegionBlockId()));
			charge.setRegionBlockName(block.getRegionBlockName());
			charge.setShippingCharge("");

			regionBlockList.add(charge);
		}
		return regionBlockList;
	}

	private Map<String, ShippingChargeSuite> convertListToMap(
			List<ShippingChargeSuite> chargeList) {
		Map<String, ShippingChargeSuite> map = new HashMap<String, ShippingChargeSuite>();
		for (ShippingChargeSuite charge : chargeList) {
			map.put(Long
					.toString(charge.getShippingCharge().getRegionBlockId()),
					charge);
		}
		return map;
	}

	/**
	 * URLパラメータに設定された配送種別コードを取得する
	 * 
	 * @return ""
	 */
	private String getDeliveryTypeNo() {
		String[] tmpArgs = getRequestParameter().getPathArgs();
		if (tmpArgs.length > 0) {
			return tmpArgs[0];
		}
		return "";
	}

	private String getCompleteParam() {
		String[] pathInfoParams = getRequestParameter().getPathArgs();
		if (pathInfoParams.length > 1) {
			return pathInfoParams[1];
		}
		return "";
	}

	/**
	 * 画面表示に必要な項目を設定・初期化します。
	 */
	public void prerender() {
		if (getCompleteParam().equals(WebConstantCode.COMPLETE_DELETE)) {
			addInformationMessage(WebMessage
					.get(
							CompleteMessage.DELETE_COMPLETE,
							Messages
									.getString("web.action.back.shop.DeliveryTypeEditInitAction.0")));
		} else {
			if (getCompleteParam().equals(WebConstantCode.COMPLETE_INSERT)) {
				addInformationMessage(WebMessage
						.get(
								CompleteMessage.REGISTER_COMPLETE,
								Messages
										.getString("web.action.back.shop.DeliveryTypeEditInitAction.0")));
			} else if (getCompleteParam().equals(
					WebConstantCode.COMPLETE_UPDATE)) {
				addInformationMessage(WebMessage
						.get(
								CompleteMessage.UPDATE_COMPLETE,
								Messages
										.getString("web.action.back.shop.DeliveryTypeEditInitAction.0")));
			}

			DeliveryTypeEditBean bean = (DeliveryTypeEditBean) getRequestBean();

			boolean displayUpdateButtonFlg = false;

			boolean displayDeleteButtonFlg = bean.getDisplayDeleteButtonFlg();

			if (getConfig().getOperatingMode().equals(OperatingMode.ONE)) {
				if (Permission.SHOP_MANAGEMENT_UPDATE_SITE
						.isGranted(getLoginInfo())) {
					displayUpdateButtonFlg = true;
				}
				if (!Permission.SHOP_MANAGEMENT_DELETE_SITE
						.isGranted(getLoginInfo())) {
					displayDeleteButtonFlg = false;
				}
			} else {
				if (Permission.SHOP_MANAGEMENT_UPDATE_SHOP
						.isGranted(getLoginInfo())) {
					displayUpdateButtonFlg = true;
				}
				if (!Permission.SHOP_MANAGEMENT_DELETE_SHOP
						.isGranted(getLoginInfo())) {
					displayDeleteButtonFlg = false;
				}
			}
			ShopManagementService service = ServiceLocator
					.getShopManagementService(getLoginInfo());
			List<DeliveryType> deliveryTypeList = service
					.getDeliveryTypeList(getLoginInfo().getShopCode());

			if (deliveryTypeList.size() <= 1) {
				displayDeleteButtonFlg = false;
			}

			bean.setDisplayUpdateButtonFlg(displayUpdateButtonFlg);

			bean.setDisplayDeleteButtonFlg(displayDeleteButtonFlg);

			setRequestBean(bean);
		}
	}

	/**
	 * Action名の取得
	 * 
	 * @return Action名
	 */
	public String getActionName() {
		return Messages
				.getString("web.action.back.shop.DeliveryTypeEditInitAction.1");
	}

	/**
	 * オペレーションコードの取得
	 * 
	 * @return オペレーションコード
	 */
	public String getOperationCode() {
		return "4105062002";
	}

}
