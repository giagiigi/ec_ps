package jp.co.sint.webshop.web.action.back.shop;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.sint.webshop.configure.WebshopConfig;
import jp.co.sint.webshop.data.domain.OperatingMode;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.ShopManagementService;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.shop.CalendarBean;
import jp.co.sint.webshop.web.bean.back.shop.CalendarBean.HolidayDate;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1050710:カレンダーマスタのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class CalendarRegisterAction extends WebBackAction<CalendarBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    boolean auth = false;
    BackLoginInfo login = getLoginInfo();
    WebshopConfig config = getConfig();
    if (config.getOperatingMode() == OperatingMode.ONE) {
      auth = Permission.SHOP_MANAGEMENT_UPDATE_SITE.isGranted(login);
    } else if (config.getOperatingMode() == OperatingMode.MALL || config.getOperatingMode() == OperatingMode.SHOP) {
      auth = Permission.SHOP_MANAGEMENT_UPDATE_SHOP.isGranted(login);
    } else {
      auth = false;
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
    boolean result = true;

    result = validateBean(getBean());

    for (HolidayDate d : getBean().getPrevHoliday()) {
      boolean tmpResult = validateBean(d);
      if (tmpResult) {
        continue;
      } else {
        result = tmpResult;
      }
    }

    for (HolidayDate d : getBean().getCurrentHoliday()) {
      boolean tmpResult = validateBean(d);
      if (tmpResult) {
        continue;
      } else {
        result = tmpResult;
      }
    }

    for (HolidayDate d : getBean().getNextHoliday()) {
      boolean tmpResult = validateBean(d);
      if (tmpResult) {
        continue;
      } else {
        result = tmpResult;
      }
    }

    return result;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    ShopManagementService service = ServiceLocator.getShopManagementService(getLoginInfo());

    CalendarBean currentBean = getBean();
    String year = currentBean.getBaseYear();
    String month = currentBean.getBaseMonth();
    Date baseDate = DateUtil.fromString(year + "/" + month + "/" + "01");

    List<String> dateList = new ArrayList<String>();
    // 前月
    for (HolidayDate h : getBean().getPrevHoliday()) {
      dateList.add(h.getHolidayDate());
    }
    Date prevDate = DateUtil.addMonth(baseDate, -1);
    year = DateUtil.getYYYY(prevDate);
    month = DateUtil.getMM(prevDate);
    ServiceResult prevResult = service.updateHoliday(getLoginInfo().getShopCode(), year, month, dateList);
    if (prevResult.hasError()) {
      return BackActionResult.SERVICE_ERROR;
    } else {
      dateList.clear();
    }

    // 今月
    for (HolidayDate h : getBean().getCurrentHoliday()) {
      dateList.add(h.getHolidayDate());
    }
    year = DateUtil.getYYYY(baseDate);
    month = DateUtil.getMM(baseDate);
    ServiceResult currentResult = service.updateHoliday(getLoginInfo().getShopCode(), year, month, dateList);
    if (currentResult.hasError()) {
      return BackActionResult.SERVICE_ERROR;
    } else {
      dateList.clear();
    }

    // 翌月
    for (HolidayDate h : getBean().getNextHoliday()) {
      dateList.add(h.getHolidayDate());
    }
    Date nextDate = DateUtil.addMonth(baseDate, 1);
    year = DateUtil.getYYYY(nextDate);
    month = DateUtil.getMM(nextDate);
    ServiceResult nextResult = service.updateHoliday(getLoginInfo().getShopCode(), year, month, dateList);
    if (nextResult.hasError()) {
      return BackActionResult.SERVICE_ERROR;
    } else {
      dateList.clear();
    }

    setRequestBean(currentBean);
    setNextUrl("/app/shop/calendar/init/" + WebConstantCode.COMPLETE_REGISTER);
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.shop.CalendarRegisterAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "4105071002";
  }

}
