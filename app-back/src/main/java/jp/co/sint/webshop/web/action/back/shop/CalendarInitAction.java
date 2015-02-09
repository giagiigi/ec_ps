package jp.co.sint.webshop.web.action.back.shop;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.sint.webshop.configure.WebshopConfig;
import jp.co.sint.webshop.data.domain.OperatingMode;
import jp.co.sint.webshop.data.dto.Holiday;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ShopManagementService;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.shop.CalendarBean;
import jp.co.sint.webshop.web.bean.back.shop.CalendarBean.HolidayDate;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.CompleteMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1050710:カレンダーマスタのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class CalendarInitAction extends WebBackAction<CalendarBean> {

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
      auth = Permission.SHOP_MANAGEMENT_READ_SITE.isGranted(login);
    } else if (config.getOperatingMode() == OperatingMode.MALL || config.getOperatingMode() == OperatingMode.SHOP) {
      auth = Permission.SHOP_MANAGEMENT_READ_SHOP.isGranted(login);
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
    return true;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    CalendarBean requestBean = new CalendarBean();

    CalendarBean bean = getBean();
    Date baseDate = DateUtil.fromString(bean.getBaseYear() + "/" + bean.getBaseMonth() + "/01");

    if (baseDate == null) {
      baseDate = DateUtil.getSysdate();
    }

    requestBean.setBaseYear(DateUtil.getYYYY(baseDate));
    requestBean.setBaseMonth(DateUtil.getMM(baseDate));

    ShopManagementService service = ServiceLocator.getShopManagementService(getLoginInfo());

    // 前月の取得
    List<Holiday> prevList = service.getHoliday(getLoginInfo().getShopCode(), DateUtil.addMonth(baseDate, -1));
    List<HolidayDate> prevStrList = new ArrayList<HolidayDate>();
    for (Holiday h : prevList) {
      HolidayDate date = new HolidayDate();
      date.setHolidayDate(DateUtil.getDD(h.getHoliday()));
      prevStrList.add(date);
    }
    requestBean.setPrevHoliday(prevStrList);

    // 今月の取得
    List<Holiday> currentList = service.getHoliday(getLoginInfo().getShopCode(), baseDate);
    List<HolidayDate> currentStrList = new ArrayList<HolidayDate>();
    for (Holiday h : currentList) {
      HolidayDate date = new HolidayDate();
      date.setHolidayDate(DateUtil.getDD(h.getHoliday()));
      currentStrList.add(date);
    }
    requestBean.setCurrentHoliday(currentStrList);

    // 翌月の取得
    List<Holiday> nextList = service.getHoliday(getLoginInfo().getShopCode(), DateUtil.addMonth(baseDate, 1));
    List<HolidayDate> nextStrList = new ArrayList<HolidayDate>();
    for (Holiday h : nextList) {
      HolidayDate date = new HolidayDate();
      date.setHolidayDate(DateUtil.getDD(h.getHoliday()));
      nextStrList.add(date);
    }
    requestBean.setNextHoliday(nextStrList);

    requestBean.setRegisterButtonDisplay(isDisplayRegisterButton());

    setRequestBean(requestBean);
    setNextUrl(null);

    // メッセージの登録
    String[] pathInfos = getRequestParameter().getPathArgs();
    if (pathInfos.length > 0) {
      if (pathInfos[0].equals(WebConstantCode.COMPLETE_REGISTER)) {
        addInformationMessage(WebMessage.get(CompleteMessage.REGISTER_COMPLETE,
            Messages.getString("web.action.back.shop.CalendarInitAction.0")));
      }
    }

    return BackActionResult.RESULT_SUCCESS;
  }

  @Override
  public boolean isCallCreateAttribute() {
    String[] pathInfos = getRequestParameter().getPathArgs();

    return pathInfos.length <= 0;
  }

  private boolean isDisplayRegisterButton() {
    WebshopConfig config = getConfig();
    BackLoginInfo login = getLoginInfo();
    if (config.getOperatingMode() == OperatingMode.ONE) {
      if (Permission.SHOP_MANAGEMENT_UPDATE_SITE.isGranted(login)) {
        return true;
      }
    } else if (config.getOperatingMode() == OperatingMode.MALL || config.getOperatingMode() == OperatingMode.SHOP) {
      if (Permission.SHOP_MANAGEMENT_UPDATE_SHOP.isGranted(login)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.shop.CalendarInitAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "4105071001";
  }

}
