package jp.co.sint.webshop.web.action.back.shop;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.configure.WebshopConfig;
import jp.co.sint.webshop.data.domain.DeliverySpecificationType;
import jp.co.sint.webshop.data.domain.OperatingMode;
import jp.co.sint.webshop.data.dto.DeliveryType;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ShopManagementService;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.shop.DeliveryTypeBean;
import jp.co.sint.webshop.web.bean.back.shop.DeliveryTypeBean.DeliveryTypeDetail;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.CompleteMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1050610:配送種別設定のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class DeliveryTypeInitAction extends WebBackAction<DeliveryTypeBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    boolean authorization = false;
    if (getConfig().getOperatingMode().equals(OperatingMode.ONE)) {
      authorization = Permission.SHOP_MANAGEMENT_READ_SITE.isGranted(getLoginInfo());
    } else {
      authorization = Permission.SHOP_MANAGEMENT_READ_SHOP.isGranted(getLoginInfo());
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
    ShopManagementService shopService = ServiceLocator.getShopManagementService(getLoginInfo());

    List<DeliveryType> deliveryTypeList = shopService.getDeliveryTypeList(getLoginInfo().getShopCode());
    List<DeliveryTypeDetail> detailList = new ArrayList<DeliveryTypeDetail>();

    for (DeliveryType type : deliveryTypeList) {
      DeliveryTypeDetail detail = new DeliveryTypeDetail();
      detail.setDeliveryTypeNo(NumUtil.toString(type.getDeliveryTypeNo()));
      detail.setDeliveryTypeName(type.getDeliveryTypeName());
      detail.setParcelUrl(type.getParcelUrl());
      if (type.getDeliverySpecificationType() == null) {
        detail.setDeliverySpecificationType(DeliverySpecificationType.NONE.getValue());
      } else {
        DeliverySpecificationType specType = DeliverySpecificationType.fromValue(type.getDeliverySpecificationType());
        detail.setDeliverySpecificationType(specType.getValue());
      }

      detail.setDeletableFlg(shopService.isDeletableDelivery(type.getShopCode(), type.getDeliveryTypeNo()));
      if (deliveryTypeList.size() <= 1) {
        detail.setDeletableFlg(false);
      }

      detailList.add(detail);
    }

    DeliveryTypeBean reqBean = new DeliveryTypeBean();
    reqBean.setDeliveryTypeList(detailList);

    // 処理完了メッセージを設定する
    setCompleteMessage();

    setRequestBean(reqBean);
    setNextUrl(null);

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {
    DeliveryTypeBean bean = (DeliveryTypeBean) getRequestBean();

    bean.setDisplayDeleteButtonFlg(isDisplayDeleteButton());
    bean.setDisplayRegisterButtonFlg(isDisplayUpdateButton());
    bean.setDisplaySelectButtonFlg(isDisplaySelectButton());

    setRequestBean(bean);
  }

  private boolean isDisplaySelectButton() {
    BackLoginInfo login = getLoginInfo();
    WebshopConfig config = getConfig();
    if (config.getOperatingMode() == OperatingMode.ONE) {
      return Permission.SHOP_MANAGEMENT_READ_SITE.isGranted(login);
    } else if (config.getOperatingMode() == OperatingMode.MALL || config.getOperatingMode() == OperatingMode.SHOP) {
      return Permission.SHOP_MANAGEMENT_READ_SHOP.isGranted(login);
    }
    return false;
  }

  private boolean isDisplayDeleteButton() {
    BackLoginInfo login = getLoginInfo();
    WebshopConfig config = getConfig();
    if (config.getOperatingMode() == OperatingMode.ONE) {
      return Permission.SHOP_MANAGEMENT_DELETE_SITE.isGranted(login);
    } else if (config.getOperatingMode() == OperatingMode.MALL || config.getOperatingMode() == OperatingMode.SHOP) {
      return Permission.SHOP_MANAGEMENT_DELETE_SHOP.isGranted(login);
    }
    return false;
  }

  private boolean isDisplayUpdateButton() {
    BackLoginInfo login = getLoginInfo();
    WebshopConfig config = getConfig();
    if (config.getOperatingMode() == OperatingMode.ONE) {
      return Permission.SHOP_MANAGEMENT_UPDATE_SITE.isGranted(login);
    } else if (config.getOperatingMode() == OperatingMode.MALL || config.getOperatingMode() == OperatingMode.SHOP) {
      return Permission.SHOP_MANAGEMENT_UPDATE_SHOP.isGranted(login);
    }
    return false;
  }

  /**
   * 処理完了パラメータがあれば、処理完了メッセージをセットします。
   * 
   * @param completeParam
   */
  private void setCompleteMessage() {
    String[] tmpList = getRequestParameter().getPathArgs();
    if (tmpList.length < 1) {
      return;
    }
    if (tmpList[0].equals(WebConstantCode.COMPLETE_DELETE)) {
      addInformationMessage(WebMessage.get(CompleteMessage.DELETE_COMPLETE,
          Messages.getString("web.action.back.shop.DeliveryTypeInitAction.0")));
    }
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.shop.DeliveryTypeInitAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "4105061003";
  }

}
