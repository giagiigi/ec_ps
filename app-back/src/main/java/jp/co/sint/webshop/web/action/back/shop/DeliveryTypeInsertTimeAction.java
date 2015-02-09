package jp.co.sint.webshop.web.action.back.shop;

import java.util.List;

import jp.co.sint.webshop.data.domain.OperatingMode;
import jp.co.sint.webshop.data.dto.DeliveryAppointedTime;
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
import jp.co.sint.webshop.web.bean.UIMainBean;
import jp.co.sint.webshop.web.bean.back.shop.DeliveryTypeBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.message.back.shop.ShopErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1050610:配送種別設定のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class DeliveryTypeInsertTimeAction extends WebBackAction<DeliveryTypeBean> {

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
    boolean valid = false;

    boolean tmpValid = validateBean(getBean());
    valid = tmpValid;

    if (validateBean(getBean().getDeliveryTimeRegister())) {
      String start = getBean().getDeliveryTimeRegister().getDeliveryAppointedStartTime();
      String end = getBean().getDeliveryTimeRegister().getDeliveryAppointedEndTime();

      if (Long.parseLong(start) >= Long.parseLong(end)) {
        addErrorMessage(WebMessage.get(ShopErrorMessage.DELIVERY_TYPE_START_END_ERROR));
        valid = false;
      } else {
        valid = true;
      }
    } else {
      valid = false;
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

    DeliveryAppointedTime time = new DeliveryAppointedTime();
    time.setShopCode(getLoginInfo().getShopCode());
    time.setDeliveryTypeNo(NumUtil.toLong(getBean().getSelectDeliveryTypeNo()));
    time.setDeliveryAppointedTimeCode(getBean().getDeliveryTimeRegister().getDeliveryAppointedTimeCode());
    time.setDeliveryAppointedTimeName(getBean().getDeliveryTimeRegister().getDeliveryAppointedTimeName());
    time.setDeliveryAppointedTimeStart(NumUtil.toLong(getBean().getDeliveryTimeRegister().getDeliveryAppointedStartTime()));
    time.setDeliveryAppointedTimeEnd(Long.parseLong(getBean().getDeliveryTimeRegister().getDeliveryAppointedEndTime()));

    // 重複チェック
    List<DeliveryAppointedTime> timeList = service.getDeliveryAppointedTimeList(time.getShopCode(), time.getDeliveryTypeNo(), time
        .getDeliveryAppointedTimeStart(), time.getDeliveryAppointedTimeEnd());
    if (timeList.size() > 0) {
      addErrorMessage(WebMessage.get(ServiceErrorMessage.DUPLICATED_REGISTER_ERROR,
          Messages.getString("web.action.back.shop.DeliveryTypeInsertTimeAction.0")));
      return BackActionResult.RESULT_SUCCESS;
    }

    ServiceResult result = service.insertDeliveryAppointedTime(time);
    if (result.hasError()) {
      for (ServiceErrorContent error : result.getServiceErrorList()) {
        if (error == CommonServiceErrorContent.DUPLICATED_REGISTER_ERROR) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.DUPLICATED_REGISTER_ERROR,
              Messages.getString("web.action.back.shop.DeliveryTypeInsertTimeAction.1")));
          return BackActionResult.RESULT_SUCCESS;
        }
      }
      setNextUrl(null);
      return BackActionResult.SERVICE_ERROR;
    }

    setNextUrl("/app/shop/delivery_type/open_time/" + time.getDeliveryTypeNo() + "/"
        + WebConstantCode.COMPLETE_INSERT);

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {
    UIMainBean bean = getRequestBean();
    if (bean == null) {
      setRequestBean(getBean());
    }
  }


  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.shop.DeliveryTypeInsertTimeAction.2");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "4105061004";
  }

}
