package jp.co.sint.webshop.utility;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日付クラス操作のためのユーティリティクラスです。 このクラスをインスタンス化することはできません。
 * 
 * @author System Integrator Corp.
 */
public final class DateUtil {

  public static final String DEFAULT_DATE_FORMAT = "yyyy/MM/dd";
  
  public static final String DEFAULT_DATE_HOUR_FORMAT = "yyyy/MM/dd HH";
  
  public static final String DEFAULT_SHORT_DATE_FORMAT = "yy/MM/dd";

  public static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";

  public static final String DEFAULT_DATETIME_FORMAT = "yyyy/MM/dd HH:mm:ss";
//add by os012 20120105 start
  public static final String DEFAULT_TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss";
//add by os012 20120105 end  
  public static final String TIMESTAMP_FORMAT = "yyyyMMddHHmmss";

  public static final String ALIPAY_DATE_FORMAT = "yyyy-MM-dd";

  // 20120416 wjh add start
  public static final String DATETAMP_FORMAT = "yyyyMMdd";
  // 20120416 wjh add end
  /**
   * 最小値の日時を返します。
   * 
   * @return 最小日時(1970/01/01 00:00:00,000 JST)
   */
  public static Date getMin() {
    // 1970/01/01 00:00:00,000 JST
    return new Date(0L);
  }

  /**
   * 最大値の日時を返します。
   * 
   * @return 最大日時(2100/01/01 00:00:00,000 JST)
   */
  public static Date getMax() {
    // 2100/01/01 00:00:00,000 JST
    return new Date(4102412400000L);
  }

  /**
   * 新しいDateUtilを生成します。
   */
  private DateUtil() {
  }

  /**
   * 複製されたDateオブジェクトを返します。
   * 
   * @param date
   *          対象となるDateオブジェクト
   * @return 複製されたDateオブジェクト。<br>
   *         dateがnullの場合はnullを返します。
   */
  public static Date immutableCopy(Date date) {
    Date result = null;
    if (date != null) {
      result = new Date(date.getTime());
    }
    return result;
  }

  /**
   * Dateオブジェクトを規定の書式文字列でフォーマットします。
   * 
   * @param date
   *          対象となるDateオブジェクト
   * @return 引数で受け取ったオブジェクトを文字列型にフォーマットした結果を返します。<br>
   *         dateがnullの場合はnullを返します。
   */
  public static String toDateString(Date date) {
    String result;
    if (date == null) {
      result = "";
    } else {
      result = toDateTimeString(date, DEFAULT_DATE_FORMAT);
    }
    return result;
  }

  /**
   * Long型の日付(ミリ秒単位)を規定の書式文字列でフォーマットします。
   * 
   * @param offset
   *          対象となるLongオブジェクト
   * @return 引数で受け取ったオブジェクトを文字列型にフォーマットした結果を返します。
   */
  public static String toDateString(Long offset) {
    return toDateTimeString(offset, DEFAULT_DATE_FORMAT);
  }

  /**
   * Dateオブジェクトを規定の書式文字列でフォーマットします。
   * 
   * @param date
   *          対象となるDateオブジェクト
   * @return 引数で受け取ったオブジェクトを文字列型にフォーマットした結果を返します。<br>
   *         dateがnullの場合はnullを返します。
   */
  public static String toDateTimeString(Date date) {
    String result;
    if (date == null) {
      result = "";
    } else {
      result = toDateTimeString(date, DEFAULT_DATETIME_FORMAT);
    }
    return result;
  }

  /**
   * Dateオブジェクトを規定の書式文字列でフォーマットします。
   * 
   * @param date
   *          対象となるDateオブジェクト
   * @return 引数で受け取ったオブジェクトを文字列型にフォーマットした結果を返します。<br>
   *         dateがnullの場合はnullを返します。
   */
  public static String toDateAndHourString(Date date) {
    String result;
    if (date == null) {
      result = "";
    } else {
      result = toDateTimeString(date, DEFAULT_DATE_HOUR_FORMAT);
    }
    return result;
  }
  
  /**
   * Long型の日付(ミリ秒単位)を規定の書式文字列でフォーマットします。
   * 
   * @param offset
   *          対象となるLongオブジェクト
   * @return 引数で受け取ったオブジェクトを文字列型にフォーマットした結果を返します。
   */
  public static String toDateTimeString(Long offset) {
    return toDateTimeString(offset, DEFAULT_DATETIME_FORMAT);
  }

  /**
   * Long型の日付(ミリ秒単位)を規定の書式文字列でフォーマットします。
   * 
   * @param offset
   *          対象となるLongオブジェクト
   * @param format
   *          書式文字列
   * @return 引数で受け取ったオブジェクトを文字列型にフォーマットした結果を返します。
   */
  public static String toDateTimeString(Long offset, String format) {
    return (new SimpleDateFormat(format)).format(new Date(offset));
  }

  /**
   * Dateオブジェクトを規定の書式文字列でフォーマットします。
   * 
   * @param d
   *          対象となるDateオブジェクト
   * @param format
   *          書式文字列
   * @return 引数で受け取ったオブジェクトを文字列型にフォーマットした結果を返します。
   */
  public static String toDateTimeString(Date d, String format) {
    return (new SimpleDateFormat(format)).format(d);
  }

  /**
   * 日付文字列(yyyy/MM/dd)からDateオブジェクトを生成します。
   * 
   * @param dateString
   *          日付文字列
   * @return 引数で受け取ったオブジェクトをDate型にフォーマットした結果を返します。<br>
   *         dateStringがnullの場合はnullを返します。
   */
  public static Date fromString(String dateString) {
    Date result;
    if (StringUtil.isNullOrEmpty(dateString)) {
      result = null;
    } else {
      result = fromString(dateString, false);
    }
    return result;
  }

  /**
   * 判断两个日期相差的天数
   * @param fromDate
   * @param toDate
   * @return
   */
  public static long getDaysFromTwoDateString(String fromDate, String toDate){
    long tmpLong = 0L;
    try {
      
      tmpLong = (DateUtil.fromString(toDate).getTime()-DateUtil.fromString(fromDate).getTime() ) / 86400000;
      
    } catch (Exception e) {
      tmpLong = 0L;
      // TODO: handle exception
    }
    return tmpLong;
  }
  /**
   * 日付(yyyy/MM/dd)または日付時刻(yyyy/MM/dd HH:mm:ss)文字列からDateオブジェクトを生成します。
   * 
   * @param dateString
   *          日付・日付時刻文字列
   * @param isDateTime
   *          日付時刻文字列ならtrue、日付のみならfalse
   * @return 引数で受け取ったオブジェクトをDate型にフォーマットした結果を返します。
   */
  public static Date fromString(String dateString, boolean isDateTime) {
    String formatString = DEFAULT_DATE_FORMAT;
    if (isDateTime) {
      formatString = DEFAULT_DATETIME_FORMAT;
    }
    return parseString(dateString, formatString);
  }
  
  /**
   * 日付(yyyy/MM/dd)または日付時刻(yyyy-MM-dd HH:mm:ss)文字列からDateオブジェクトを生成します。
   * 
   * add by os012 20120105 start
   * @param dateString
   *          日付・日付時刻文字列
   * @param isDateTime
   *          日付時刻文字列ならtrue、日付のみならfalse
   * @return 引数で受け取ったオブジェクトをDate型にフォーマットした結果を返します。
   */
  public static Date fromTimestampString(String dateString) {
     String formatString = DEFAULT_TIMESTAMP_FORMAT;
    return parseString(dateString, formatString);
  }
  /**
   * 日付文字列、書式文字列からDateオブジェクトを生成します。<br>
   * 引数で受け取ったdateStringがnullまたは空白、あるいはformatStringがnullだった場合、nullを返します。
   * 
   * @param dateString
   *          日付・日付時刻文字列
   * @param formatString
   *          書式文字列
   * @return 引数で受け取ったオブジェクトをDate型にフォーマットした結果を返します。
   */
  public static Date parseString(String dateString, String formatString) {
    Date result = null;
    DateFormat pattern = new SimpleDateFormat(formatString);
    try {
      pattern.setLenient(false);
      result = pattern.parse(dateString);
    } catch (ParseException pe) {
      result = null;
    } catch (NullPointerException npe) {
      result = null;
    }
    return result;
  }

  /**
   * 該当年月の初日のDateオブジェクトを生成します。
   * 
   * @param year
   *          該当年
   * @param month
   *          該当月
   * @return 引数で受け取ったオブジェクトをDate型にフォーマットした結果を返します。
   */
  public static Date fromYearMonth(String year, String month) {
    String firstDayString = year + "/" + month + "/" + "01";

    return fromString(firstDayString);
  }

  /**
   * 該当年の初日のDateオブジェクトを生成します。
   * 
   * @param year
   *          該当年
   * @return 引数で受け取ったオブジェクトをDate型にフォーマットした結果を返します。
   */
  public static Date fromYear(String year) {
    String firstDayString = year + "/" + "01" + "/" + "01";

    return fromString(firstDayString);
  }

  /**
   * 現在日付のDateオブジェクトを生成します。
   * このメソッドが返す時刻の精度は1秒単位です。java.util.Dateの持つミリ秒単位の精度は切り捨てられます。
   * 
   * @return 現在日付のDateオブジェクトを返します。
   */
  public static Date getSysdate() {
    return new Date((System.currentTimeMillis() / 1000) * 1000);
  }

  /**
   * 現在日付の文字列を生成します。
   * 
   * @return 現在日付の文字列を返します。
   */
  public static String getSysdateString() {
    String sysdate = toDateString(getSysdate());
    return sysdate;
  }

  /**
   * 現在時刻をyyyyMMddHHmmss形式で取得します。
   * 
   * @return 現在時刻(yyyyMMddHHmmss)を返します。
   */
  public static String getTimeStamp() {
    return toDateTimeString(getSysdate(), TIMESTAMP_FORMAT);
  }
 
  public static String getTimeStamp(Date date) {
    return toDateTimeString(date, TIMESTAMP_FORMAT);
  }
  
  /**
   * 現在時刻をyyyyMMddHHmmss形式で取得します。
   * 
   * @return 現在時刻(yyyyMMddHHmmss)を返します。
   */
  public static String getDateTimeString() {
    return toDateTimeString(getSysdate(), DEFAULT_DATETIME_FORMAT);
  }

  /**
   * 日付型のチェックを行います。
   * 
   * @param dateString
   *          日付文字列
   * @return 日付文字列を正常にDate型にフォーマットされた場合、trueを返します。<br>
   *         日付文字列を正常にDate型にフォーマットできなかった場合(dateStringがnullの場合を含む)、falseを返します。
   */
  public static boolean isCorrect(String dateString) {
    return (fromString(dateString) != null);
  }

  /**
   * Dateから年(yyyy)を取得します。
   * 
   * @param date
   *          対象となるDateオブジェクト
   * @return 年(yyyy)を返します。
   */
  public static String getYYYY(Date date) {
    DateFormat pattern = new SimpleDateFormat("yyyy");
    return pattern.format(date);
  }

  /**
   * Dateから月(MM)を取得します。
   * 
   * @param date
   *          対象となるDateオブジェクト
   * @return 月(MM)を返します。
   */
  public static String getMM(Date date) {
    DateFormat pattern = new SimpleDateFormat("MM");
    return pattern.format(date);
  }

  /**
   * Dateから日付(dd)を取得します。
   * 
   * @param date
   *          対象となるDateオブジェクト
   * @return 日付(dd)を返します。
   */
  public static String getDD(Date date) {
    DateFormat pattern = new SimpleDateFormat("dd");
    return pattern.format(date);
  }

  /**
   * Dateから時刻(HH)を取得します。
   * 
   * @param date
   *          対象となるDateオブジェクト
   * @return 時刻(HH)を返します。
   */
  public static String getHH(Date date) {
    DateFormat pattern = new SimpleDateFormat("HH");
    return pattern.format(date);
  }
  /**
   * Dateから時刻(HH:mm:ss)を取得します。
   * 
   * @param date
   *          対象となるDateオブジェクト
   * @return 時刻(HH:mm:ss)を返します。
   */
  public static String getHHmmss(Date date) {
    DateFormat pattern = new SimpleDateFormat("HH:mm:ss");
    return pattern.format(date);
  }
  /**
   * 月として指定された値が正しい(1～12)かどうかか判定します。
   * 
   * @param month
   *          対象月
   * @return 月として指定された値が正しい場合、trueを返します。<br>
   *         月として指定された値が正しくない場合(null、空白を含む)、falseを返します。
   */
  public static boolean isValidMonth(String month) {
    boolean result = false;

    if (StringUtil.isNullOrEmpty(month)) {
      return false;
    }

    try {
      int monthInt = Integer.parseInt(month);

      result = monthInt >= 1 && monthInt <= 12;
    } catch (NumberFormatException e) {
      result = false;
    }
    return result;
  }

  /**
   * Dateから、該当年月の最終日を取得します
   * 
   * @param date
   *          対象となるDateオブジェクト
   * @return 該当年月の最終日を返します。
   */
  public static String getEndDay(Date date) {
    SimpleDateFormat format = new SimpleDateFormat("yyyy/MM");
    format.format(date);
    Calendar c = format.getCalendar();

    return Integer.toString(c.getActualMaximum(Calendar.DATE));
  }

  /**
   * 文字列で指定した年月から、該当年月の最終日を取得します。
   * 
   * @param year
   *          対象年
   * @param month
   *          対象月
   * @return 該当年月の最終日を返します。<br>
   *         日付の取得に失敗した場合、nullを返します。
   */
  public static String getEndDay(int year, int month) {
    Calendar c = Calendar.getInstance();
    if (!isValidMonth("" + month)) {
      return null;
    }

    // 年における月は0から始まるため，1を引いている
    c.set(year, month - 1, 1);

    return Integer.toString(c.getActualMaximum(Calendar.DATE));
  }

  /**
   * 指定日付にX月足した日付を返します。
   * 
   * @param date
   *          基準日
   * @param addition
   *          足す月数
   * @return 指定日付(date)にX(addition)月足した日付を返します。
   */
  public static Date addMonth(Date date, int addition) {
    return addTime(date, Calendar.MONTH, addition);
  }

  /**
   * 指定日付にX日足した日付を返します。
   * 
   * @param date
   *          基準日
   * @param addition
   *          足す日付
   * @return 指定日付(date) にX(addition)日足した日付を返します。
   */
  public static Date addDate(Date date, int addition) {
    return addTime(date, Calendar.DATE, addition);
  }

  /**
   * 指定日時にX時間足した時間を返します。
   * 
   * @param date
   *          基準日時
   * @param addition
   *          足す時間
   * @return 指定日時(date)にX(addition)時間足した時間を返します。
   */
  public static Date addHour(Date date, int addition) {
    return addTime(date, Calendar.HOUR_OF_DAY, addition);
  }

  /**
   * 指定日時にX分足した時間を返します。
   * 
   * @param date
   *          基準日時
   * @param addition
   *          足す分
   * @return 指定日時(date)にX(addition)分足した時間を返します。
   */
  public static Date addMinute(Date date, int addition) {
    return addTime(date, Calendar.MINUTE, addition);
  }

  /**
   * 指定日時に指定されたタイプ(月/日/時間)にX(月/日/時間)足した時間を返します。
   * 
   * @param date
   *          基準日時
   * @param type
   *          タイプ(月/日/時間)
   * @param addition
   *          足す対象(月/日/時間)
   * @return 指定日時(date)にX(addition)(月/日/時間)足した(日付/時間)を返します。
   */
  private static Date addTime(Date date, int type, int addition) {
    SimpleDateFormat format = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
    format.format(date);
    Calendar c = format.getCalendar();
    c.add(type, addition);
    return c.getTime();
  }

  private static Date setTime(Date date, int field, int value) {
    Date result = null;
    if (date != null) {
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(date);
      calendar.set(field, value);
      result = calendar.getTime();
    }
    return result;
  }

  public static Date setHour(Date date, int value) {
    return setTime(date, Calendar.HOUR_OF_DAY, value);
  }

  public static Date setMinute(Date date, int value) {
    return setTime(date, Calendar.MINUTE, value);
  }

  public static Date setSecond(Date date, int value) {
    return setTime(date, Calendar.SECOND, value);
  }

  /**
   * 期間StartとEndの文字列渡し、現在期間中であればtrueを返す。 <br>
   * StartとEndどちらか一方だけnullまたは空文字であれば、Start→最小日時、End→最大日時に変換します。
   * 
   * @param startDatetime
   *          開始
   * @param endDatetime
   *          終了
   * @return 期間Start、Endが期間中である場合、trueを返します。<br>
   *         期間Start、Endが期間中でない場合、falseを返します。<br>
   *         startDatetimeがnullまたは空文字かつ、endDatetimeがnullまたは空文字の場合、falseを返します。
   */
  public static boolean isPeriodString(String startDatetime, String endDatetime) {
    return isPeriodString(startDatetime, endDatetime, getSysdateString());
  }

  /**
   * 期間StartとEndと任意の日時の文字列を渡し、任意の日時が期間中であればtrueを返す。<br>
   * StartとEndどちらか一方だけnullまたは空文字であれば、Start→最小日時、End→最大日時に変換します。
   * 
   * @param startDatetime
   *          開始
   * @param endDatetime
   *          終了
   * @param whenString
   *          任意の日時
   * @return 期間Start、Endが期間中である場合、trueを返します。<br>
   *         期間Start、Endが期間中でない場合、falseを返します。<br>
   *         startDatetimeがnullまたは空文字かつ、endDatetimeがnullまたは空文字の場合、falseを返します。
   */
  public static boolean isPeriodString(String startDatetime, String endDatetime, String whenString) {
    if (StringUtil.isNullOrEmpty(startDatetime) && StringUtil.isNullOrEmpty(endDatetime)) {
      return false;
    } else if (DateUtil.isCorrect(startDatetime) && StringUtil.isNullOrEmpty(endDatetime)) {
      endDatetime = toDateString(getMax());
    } else if (StringUtil.isNullOrEmpty(startDatetime) && isCorrect(endDatetime)) {
      startDatetime = toDateString(getMin());
    }

    return isPeriodDate(fromString(startDatetime), fromString(endDatetime), fromString(whenString));

  }
  
  public static boolean isPeriodStringDateTime(String startDatetime, String endDatetime, String whenString) {
    if (StringUtil.isNullOrEmpty(startDatetime) && StringUtil.isNullOrEmpty(endDatetime)) {
      return false;
    } else if (DateUtil.isCorrect(startDatetime) && StringUtil.isNullOrEmpty(endDatetime)) {
      endDatetime = toDateString(getMax());
    } else if (StringUtil.isNullOrEmpty(startDatetime) && isCorrect(endDatetime)) {
      startDatetime = toDateString(getMin());
    }

    return isPeriodDate(fromString(startDatetime,true), fromString(endDatetime,true), fromString(whenString,true));

  }
  /**
   * 期間StartとEndのDate渡し、現在期間中であればtrueを返す。<br>
   * StartとEndどちらか一方だけnullまたは空文字であれば、Start→最小日時、End→最大日時に変換します。
   * 
   * @param startDatetime
   *          開始
   * @param endDatetime
   *          終了
   * @return 期間Start、Endが期間中である場合、trueを返します。<br>
   *         期間Start、Endが期間中でない場合、falseを返します。<br>
   *         startDatetimeがnullまたは空文字かつ、endDatetimeがnullまたは空文字の場合、falseを返します。
   */
  public static boolean isPeriodDate(Date startDatetime, Date endDatetime) {
    return isPeriodDate(startDatetime, endDatetime, getSysdate());
  }

  /**
   * 期間StartとEndと任意の日時のDateを渡し、任意の日時が期間中であればtrueを返す。<br>
   * StartとEndどちらか一方だけnullまたは空文字であれば、Start→最小日時、End→最大日時に変換します。
   * 
   * @param startDatetime
   *          期間開始日時。nullのときは最小値(DateUtil.getMin()）で代替されます。
   * @param endDatetime
   *          期間終了日時。nullのときは最大値(DateUtil.getMax()）で代替されます。
   * @param when
   *          任意の日時
   * @return 期間Start、Endが期間中である場合、trueを返します。<br>
   *         期間Start、Endが期間中でない場合、falseを返します。<br>
   *         startDatetimeがnullまたは空文字かつ、endDatetimeがnullまたは空文字の場合、falseを返します。
   */
  public static boolean isPeriodDate(Date startDatetime, Date endDatetime, Date when) {
    boolean result = false;

    if (startDatetime == null) {
      startDatetime = getMin();
    }
    if (endDatetime == null) {
      endDatetime = getMax();
    }

    // dStartDatetime > when もしくは dEndDatetime < when
    if (startDatetime.after(when) || endDatetime.before(when)) {
      result = false;
    } else {
      result = true;
    }
    return result;
  }

  /**
   * 与えられたDateの時間以下を切り捨てた日付を返します。
   * 
   * @param targetDate
   *          対象の日付型
   * @return 対象の日付型の値の時間以下を切り捨てた値を返します。 targetDateがnullの場合、nullを返します。
   */
  public static Date truncateDate(Date targetDate) {
    return fromString(toDateString(targetDate));
  }

  // 10.1.3 10057 追加 ここから
  /**
   * 与えられたDateの分以下を切り捨てた日付を返します。
   * 
   * @param targetDate
   *          対象の日付型
   * @return 対象の日付型の値の分以下を切り捨てた値を返します。 targetDateがnullの場合、nullを返します。
   */
  public static Date truncateHour(Date targetDate) {
    if (targetDate == null) {
      return null;
    }
    // 10.1.4 K00168 修正 ここから
    // return parseString(toDateTimeString(targetDate, "yyyy/MM/dd HH"),"yyyy/MM/dd HH");
    return parseString(toDateTimeString(targetDate, "yyyy/MM/dd HH"), "yyyy/MM/dd HH");
    // 10.1.4 K00168 修正 ここまで
  }
  // 10.1.3 10057 追加 ここまで

  /**
   * DateオブジェクトをISO 8601規格(yyyy-MM-dd'T'HH:mm:ss'+09:00')でフォーマットします。
   * 
   * @param d
   *          対象となるDateオブジェクト
   * @return 引数で受け取ったオブジェクトを文字列型(yyyy-MM-dd'T'HH:mm:ss'+09:00')にフォーマットした結果を返します。
   */
  public static String iso8601Datetime(Date d) {
    return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'+09:00'").format(d);
  }

  /**
   * DateオブジェクトをISO 8601規格(yyyy-MM-dd)でフォーマットします。
   * 
   * @param d
   *          対象となるDateオブジェクト
   * @return 引数で受け取ったオブジェクトを文字列型(yyyy-MM-dd)にフォーマットした結果を返します。
   */
  public static String iso8601Date(Date d) {
    return new SimpleDateFormat("yyyy-MM-dd").format(d);
  }

  // 10.1.1 10006 追加 ここから
  /**
   * 設定ファイルで指定したアプリケーション内の最小日時を返します。
   * 
   * @return アプリケーション内最小日時(XXXX/01/01 00:00:00,000 JST)
   */
  public static Date getAppMin() {
    // "XXXX/01/01 00:00:00,000 JST"
    return DateUtil.fromString(DIContainer.getWebshopConfig().getApplicationMinYear() + "/01/01 00:00:00", true);
  }

  /**
   * 設定ファイルで指定したアプリケーション内の最大日時を返します。
   * 
   * @return アプリケーション内最小日時(XXXX/12/31 23:59:59,000 JST)
   */
  public static Date getAppMax() {
    // "XXXX/12/31 23:59:59,000 JST"
    return DateUtil.fromString(DIContainer.getWebshopConfig().getApplicationMaxYear() + "/12/31 23:59:59", true);
  }

  /**
   * 指定された日付がアプリケーション内の日付制限範囲内かどうかを判定します。<br>
   * 指定された日付がnullの場合はfalseを返します。<br>
   * 
   * @param  判定対象となる日付型データ targetDate
   * @return 判定結果(範囲内:true, 範囲外:false)
   */
  public static boolean isCorrectAppDate(Date targetDate) {
    return isCorrectAppDate(targetDate, false);
  }

  /**
   * 指定された日付がアプリケーション内の日付制限範囲内かどうかを判定します。<br>
   * 指定された日付がnull、かつNULL値許可フラグがtrueの場合はtrueを返します。<br>
   * 指定された日付がnull、かつNULL値許可フラグがfalseの場合はfalseを返します。<br>
   * 
   * @param  判定対象となる日付型データ targetDate
   * @param  NULL値許可フラグ allowsNull
   * @return 判定結果(範囲内:true, 範囲外:false)
   */
  public static boolean isCorrectAppDate(Date targetDate, boolean allowsNull) {
    if (targetDate == null) {
      // nullのとき
      return allowsNull;
    } else {
      // 期間内(両端を含む)
      return isPeriodDate(getAppMin(), getAppMax(), targetDate);
    }
  }
  // 10.1.1 10006 追加 ここまで
  
  // 20120221 shen add start
  /**
   * 取得日期的星期
   * 
   * @param 判断对象日期
   * 
   * @return 数值(星期天:1, 星期一:2, 星期二:3, 星期三:4, 星期四:5, 星期五:6, 星期六:7)
   */
  public static int getDayForWeek(Date targetDate) {
    Calendar c = Calendar.getInstance();
    c.setTime(targetDate);
    return c.get(Calendar.DAY_OF_WEEK);
  }
  // 20120221 shen add end
  
  // 20120416 wjh add start
  public static String getDateStamp(Date date) {
    return toDateTimeString(date,DATETAMP_FORMAT);
  }
  // 20120416 wjh add en
}
