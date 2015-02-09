package jp.co.sint.webshop.web.action.back.shop;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.configure.WebshopConfig;
import jp.co.sint.webshop.data.domain.OperatingMode;
import jp.co.sint.webshop.data.dto.DeliveryAppointedTime;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ShopManagementService;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.shop.DeliveryTypeBean;
import jp.co.sint.webshop.web.bean.back.shop.DeliveryTypeBean.DeliveryTimeDetail;
import jp.co.sint.webshop.web.bean.back.shop.DeliveryTypeBean.DeliveryTypeDetail;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.CompleteMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.message.back.shop.ShopErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1050610:配送種別設定のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class DeliveryTypeOpenTimeAction extends WebBackAction<DeliveryTypeBean> {

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
    DeliveryTypeBean reqBean = getBean();
    // DeliveryTypeNoの取得
    String[] argsTmp = getRequestParameter().getPathArgs();
    String typeNo = "";
    String complete = "";
    if (argsTmp.length > 0) {
      typeNo = argsTmp[0];
      if (argsTmp.length > 1) {
        complete = argsTmp[1];
      }
    } else {
      addErrorMessage(WebMessage.get(ShopErrorMessage.CODE_FAILED,
          Messages.getString("web.action.back.shop.DeliveryTypeOpenTimeAction.0")));
      setRequestBean(getBean());
      return BackActionResult.RESULT_SUCCESS;
    }

    ShopManagementService service = ServiceLocator.getShopManagementService(getLoginInfo());

    // 配送種別存在チェック
    if (service.getDeliveryType(getLoginInfo().getShopCode(), NumUtil.toLong(typeNo)) == null) {
      addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
          Messages.getString("web.action.back.shop.DeliveryTypeOpenTimeAction.0")));
      setRequestBean(getBean());
      return BackActionResult.RESULT_SUCCESS;
    }

    List<DeliveryAppointedTime> timeList = service.getDeliveryAppointedTimeList(getLoginInfo().getShopCode(), NumUtil
        .toLong(typeNo));
    List<DeliveryTimeDetail> timeDetailList = new ArrayList<DeliveryTimeDetail>();

    for (DeliveryAppointedTime time : timeList) {
      DeliveryTimeDetail detail = new DeliveryTimeDetail();

      detail.setDeliveryAppointedTimeCode(time.getDeliveryAppointedTimeCode());
      detail.setDeliveryAppointedTimeName(time.getDeliveryAppointedTimeName());
      detail.setDeliveryAppointedStartTime(NumUtil.toString(time.getDeliveryAppointedTimeStart()));
      detail.setDeliveryAppointedEndTime(NumUtil.toString(time.getDeliveryAppointedTimeEnd()));
      detail.setUpdateDatetime(time.getUpdatedDatetime());

      timeDetailList.add(detail);
    }

    DeliveryTimeDetail deliveryTimeRegister = new DeliveryTimeDetail();
    deliveryTimeRegister.setDeliveryAppointedTimeCode("");
    deliveryTimeRegister.setDeliveryAppointedTimeName("");
    deliveryTimeRegister.setDeliveryAppointedStartTime("");
    deliveryTimeRegister.setDeliveryAppointedEndTime("");
    reqBean.setDeliveryTimeRegister(deliveryTimeRegister);

    List<DeliveryTypeDetail> deliveryTypeList = getBean().getDeliveryTypeList();

    for (DeliveryTypeDetail type : deliveryTypeList) {
      if (type.getDeliveryTypeNo().equals(typeNo)) {
        reqBean.setSelectDeliveryTypeName(type.getDeliveryTypeName());
        reqBean.setSelectDeliveryTypeNo(typeNo);
      }
    }
    reqBean.setDispTime(true);
    reqBean.setDeliveryTimeList(timeDetailList);

    // 完了メッセージ設定
    if (complete.length() > 0) {
      if (complete.equals(WebConstantCode.COMPLETE_INSERT)) {
        addInformationMessage(WebMessage.get(CompleteMessage.REGISTER_COMPLETE,
            Messages.getString("web.action.back.shop.DeliveryTypeOpenTimeAction.1")));
      } else if (complete.equals(WebConstantCode.COMPLETE_UPDATE)) {
        addInformationMessage(WebMessage.get(CompleteMessage.UPDATE_COMPLETE,
            Messages.getString("web.action.back.shop.DeliveryTypeOpenTimeAction.1")));
      } else if (complete.equals(WebConstantCode.COMPLETE_DELETE)) {
        addInformationMessage(WebMessage.get(CompleteMessage.DELETE_COMPLETE,
            Messages.getString("web.action.back.shop.DeliveryTypeOpenTimeAction.1")));
      }

    }

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

    setRequestBean(bean);
  }

  /**
   * beanのcreateAttributeを実行するかどうかの設定
   * 
   * @return true - 実行する false-実行しない
   */
  @Override
  public boolean isCallCreateAttribute() {
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
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.shop.DeliveryTypeOpenTimeAction.2");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "4105061006";
  }

}
