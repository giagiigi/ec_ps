package jp.co.sint.webshop.web.action.back.shop;

import java.util.List;

import jp.co.sint.webshop.data.domain.OperatingMode;
import jp.co.sint.webshop.data.dto.DeliveryAppointedTime;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.ShopManagementService;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.UIMainBean;
import jp.co.sint.webshop.web.bean.back.shop.DeliveryTypeBean;
import jp.co.sint.webshop.web.bean.back.shop.DeliveryTypeBean.DeliveryTimeDetail;
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
public class DeliveryTypeUpdateTimeAction extends WebBackAction<DeliveryTypeBean> {

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
    boolean valid = validateBean(getBean());

    String updateTimeCode = getUpdateTimeCode();

    if (updateTimeCode.length() <= 0) {
      addErrorMessage(WebMessage.get(ShopErrorMessage.CODE_FAILED,
          Messages.getString("web.action.back.shop.DeliveryTypeUpdateTimeAction.0")));
      return false;
    }
    for (DeliveryTimeDetail detail : getBean().getDeliveryTimeList()) {
      if (detail.getDeliveryAppointedTimeCode().equals(updateTimeCode)) {
        if (validateBean(detail)) {

          String start = detail.getDeliveryAppointedStartTime();
          String end = detail.getDeliveryAppointedEndTime();

          if (Long.parseLong(start) >= Long.parseLong(end)) {
            addErrorMessage(WebMessage.get(ShopErrorMessage.DELIVERY_TYPE_START_END_ERROR));
            valid = false;
          } else {
            valid = true;
          }
        } else {
          valid = false;
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

    DeliveryTimeDetail updateTime = null;

    for (DeliveryTimeDetail detail : getBean().getDeliveryTimeList()) {
      if (detail.getDeliveryAppointedTimeCode().equals(getUpdateTimeCode())) {
        updateTime = detail;
      }
    }
    if (updateTime == null) {
      addErrorMessage(WebMessage.get(ShopErrorMessage.CODE_FAILED,
          Messages.getString("web.action.back.shop.DeliveryTypeUpdateTimeAction.0")));
      return BackActionResult.RESULT_SUCCESS;
    }

    DeliveryAppointedTime updateTimeDto = new DeliveryAppointedTime();
    updateTimeDto.setShopCode(getLoginInfo().getShopCode());
    updateTimeDto.setDeliveryTypeNo(NumUtil.toLong(getBean().getSelectDeliveryTypeNo()));
    updateTimeDto.setDeliveryAppointedTimeCode(updateTime.getDeliveryAppointedTimeCode());
    updateTimeDto.setDeliveryAppointedTimeName(updateTime.getDeliveryAppointedTimeName());
    updateTimeDto.setDeliveryAppointedTimeStart(Long.parseLong(updateTime.getDeliveryAppointedStartTime()));
    updateTimeDto.setDeliveryAppointedTimeEnd(Long.parseLong(updateTime.getDeliveryAppointedEndTime()));
    updateTimeDto.setUpdatedDatetime(updateTime.getUpdateDatetime());

    // 重複チェック
    List<DeliveryAppointedTime> timeList = service.getDeliveryAppointedTimeList(updateTimeDto.getShopCode(), updateTimeDto
        .getDeliveryTypeNo(), updateTimeDto.getDeliveryAppointedTimeStart(), updateTimeDto.getDeliveryAppointedTimeEnd());
    if (timeList.size() > 0) {
      for (DeliveryAppointedTime time : timeList) {
        if (time.getDeliveryAppointedTimeCode().equals(updateTimeDto.getDeliveryAppointedTimeCode())) {
          break;
        } else {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.DUPLICATED_REGISTER_ERROR,
              Messages.getString("web.action.back.shop.DeliveryTypeUpdateTimeAction.1")));
          return BackActionResult.RESULT_SUCCESS;
        }
      }
    }

    ServiceResult result = service.updateDeliveryAppointedTime(updateTimeDto);
    if (result.hasError()) {
      setNextUrl(null);
      return BackActionResult.SERVICE_ERROR;
    }

    setRequestBean(getBean());

    setNextUrl("/app/shop/delivery_type/open_time/"
        + NumUtil.toString(updateTimeDto.getDeliveryTypeNo()) + "/" + WebConstantCode.COMPLETE_UPDATE);

    return BackActionResult.RESULT_SUCCESS;
  }

  private String getUpdateTimeCode() {
    String[] tmpList = getRequestParameter().getPathArgs();

    if (tmpList.length > 0) {
      return tmpList[0];
    } else {
      return "";
    }
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
    return Messages.getString("web.action.back.shop.DeliveryTypeUpdateTimeAction.2");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "4105061007";
  }

}
