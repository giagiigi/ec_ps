package jp.co.sint.webshop.web.bean.back.shop;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U1050710:カレンダーマスタのデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class CalendarBean extends UIBackBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private List<HolidayDate> prevHoliday = new ArrayList<HolidayDate>();

  private List<HolidayDate> currentHoliday = new ArrayList<HolidayDate>();

  private List<HolidayDate> nextHoliday = new ArrayList<HolidayDate>();

  private boolean registerButtonDisplay;

  @Digit
  @Length(4)
  private String baseYear;

  @Digit
  @Length(2)
  private String baseMonth;

  /**
   * リクエストパラメータから値を取得します。
   * 
   * @param reqparam
   *          リクエストパラメータ
   */
  public void createAttributes(RequestParameter reqparam) {
    prevHoliday = new ArrayList<HolidayDate>();
    currentHoliday = new ArrayList<HolidayDate>();
    nextHoliday = new ArrayList<HolidayDate>();

    setBaseYear(StringUtil.coalesce(reqparam.get("baseYear"), getBaseYear()));
    setBaseMonth(StringUtil.coalesce(reqparam.get("baseMonth"), getBaseMonth()));

    String[] prevCalendar = reqparam.getAll("prevCalendar");
    for (String s : prevCalendar) {
      if (StringUtil.hasValue(s)) {
        HolidayDate prev = new HolidayDate();
        prev.setHolidayDate(s);
        prevHoliday.add(prev);
      }
    }
    String[] currentCalendar = reqparam.getAll("currentCalendar");
    for (String s : currentCalendar) {
      if (StringUtil.hasValue(s)) {
        HolidayDate current = new HolidayDate();
        current.setHolidayDate(s);
        currentHoliday.add(current);
      }
    }
    String[] nextCalendar = reqparam.getAll("nextCalendar");
    for (String s : nextCalendar) {
      if (StringUtil.hasValue(s)) {
        HolidayDate next = new HolidayDate();
        next.setHolidayDate(s);
        nextHoliday.add(next);
      }
    }
  }

  /**
   * baseMonthを取得します。
   * 
   * @return baseMonth
   */
  public String getBaseMonth() {
    return baseMonth;
  }

  /**
   * baseYearを取得します。
   * 
   * @return baseYear
   */
  public String getBaseYear() {
    return baseYear;
  }

  /**
   * currentHolidayを取得します。
   * 
   * @return currentHoliday
   */
  public List<HolidayDate> getCurrentHoliday() {
    return currentHoliday;
  }

  /**
   * nextHolidayを取得します。
   * 
   * @return nextHoliday
   */
  public List<HolidayDate> getNextHoliday() {
    return nextHoliday;
  }

  /**
   * prevHolidayを取得します。
   * 
   * @return prevHoliday
   */
  public List<HolidayDate> getPrevHoliday() {
    return prevHoliday;
  }

  /**
   * baseMonthを設定します。
   * 
   * @param baseMonth
   *          baseMonth
   */
  public void setBaseMonth(String baseMonth) {
    this.baseMonth = baseMonth;
  }

  /**
   * baseYearを設定します。
   * 
   * @param baseYear
   *          baseYear
   */
  public void setBaseYear(String baseYear) {
    this.baseYear = baseYear;
  }

  /**
   * currentHolidayを設定します。
   * 
   * @param currentHoliday
   *          currentHoliday
   */
  public void setCurrentHoliday(List<HolidayDate> currentHoliday) {
    this.currentHoliday = currentHoliday;
  }

  /**
   * nextHolidayを設定します。
   * 
   * @param nextHoliday
   *          nextHoliday
   */
  public void setNextHoliday(List<HolidayDate> nextHoliday) {
    this.nextHoliday = nextHoliday;
  }

  /**
   * prevHolidayを設定します。
   * 
   * @param prevHoliday
   *          prevHoliday
   */
  public void setPrevHoliday(List<HolidayDate> prevHoliday) {
    this.prevHoliday = prevHoliday;
  }

  /**
   * サブJSPを設定します。
   */
  @Override
  public void setSubJspId() {
  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U1050710";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.back.shop.CalendarBean.0");
  }

  /**
   * registerButtonDisplayを取得します。
   * 
   * @return registerButtonDisplay
   */
  public boolean isRegisterButtonDisplay() {
    return registerButtonDisplay;
  }

  /**
   * registerButtonDisplayを設定します。
   * 
   * @param registerButtonDisplay
   *          registerButtonDisplay
   */
  public void setRegisterButtonDisplay(boolean registerButtonDisplay) {
    this.registerButtonDisplay = registerButtonDisplay;
  }

  /**
   * U1050710:カレンダーマスタのサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class HolidayDate implements Serializable {

    /** */
    private static final long serialVersionUID = 1L;

    @Digit
    private String holidayDate;

    /**
     * holidayDateを取得します。
     * 
     * @return holidayDate
     */
    public String getHolidayDate() {
      return holidayDate;
    }

    /**
     * holidayDateを設定します。
     * 
     * @param holidayDate
     *          holidayDate
     */
    public void setHolidayDate(String holidayDate) {
      this.holidayDate = holidayDate;
    }
  }

}
