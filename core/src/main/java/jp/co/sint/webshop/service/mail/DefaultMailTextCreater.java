package jp.co.sint.webshop.service.mail;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import jp.co.sint.webshop.data.domain.MailType;
import jp.co.sint.webshop.mail.MailComposition;
import jp.co.sint.webshop.text.Messages;
import jp.co.sint.webshop.utility.ExPrintWriter;

/**
 * メールテンプレートに登録されるデフォルトデータを生成するクラス
 * 
 * @author System Integrator Corp.
 */
public final class DefaultMailTextCreater {

  private DefaultMailTextCreater() {
  }

  private static Map<MailType, String> subjectMap = new HashMap<MailType, String>();

  private static Map<MailComposition, String> textMap = new HashMap<MailComposition, String>();

  private static boolean point = true;

  /**
   * 引数で与えられたメールタイプのデフォルトメールタイトルを返します。
   * 
   * @param mailType
   *          メールタイプ
   * @return 引数で与えられたメールタイプのデフォルトメールタイトル
   */
  public static String getSubject(MailType mailType, boolean usablePoint) {
    if (point != usablePoint || subjectMap.size() <= 0) {
      point = usablePoint;
      init(usablePoint);
    }
    return subjectMap.get(mailType);
  }

  /**
   * 引数で与えられたメール構造のデフォルトメール本文を返します。
   * 
   * @param composition
   *          メール構造
   * @return 引数で与えられたメール構造のデフォルトメール本文
   */
  public static String getText(MailComposition composition, boolean usablePoint) {
    if (point != usablePoint || subjectMap.size() <= 0) {
      point = usablePoint;
      init(usablePoint);
    }
    return textMap.get(composition);
  }

  public static String getSignature() {
    StringWriter s = new StringWriter();
    PrintWriter out = new ExPrintWriter(s);
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.0"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.1"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.2"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.3"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.4"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.5"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.6"));
    out.println();
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.7"));
    out.println();
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.8"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.9"));
    return s.toString();
  }

  private static void init(boolean usablePoint) {
    String signText = getSignature();
    String subject = Messages.getString("service.mail.DefaultMailTextCreater.10");
    String text = Messages.getString("service.mail.DefaultMailTextCreater.11");

    /** 商品入荷お知らせ */
    subject = Messages.getString("service.mail.DefaultMailTextCreater.12");
    subjectMap.put(MailType.ARRIVAL_INFORMATION, subject);
    text = getArrivalInformationText();
    textMap.put(MailComposition.ARRIVAL_GOODS_MAIN, text);
    text = signText;
    textMap.put(MailComposition.ARRIVAL_GOODS_SIGN, text);

    /** 出荷連絡 */
    subject = Messages.getString("service.mail.DefaultMailTextCreater.13");
    subjectMap.put(MailType.SHIPPING_INFORMATION, subject);
    text = getShippingReportMainText();
    textMap.put(MailComposition.SHIPPING_REPORT_MAIN, text);
    text = getShippingReportHeader();
    textMap.put(MailComposition.SHIPPING_REPORT_HEADER, text);
    text = getShippingReportDetail();
    textMap.put(MailComposition.SHIPPING_REPORT_DETAIL, text);
    text = signText;
    textMap.put(MailComposition.SHIPPING_REPORT_SIGN, text);
    
    // 2012-04-01 yyq add start
    /** TM出荷連絡 */
    subject = Messages.getString("service.mail.DefaultMailTextCreater.13");
    subjectMap.put(MailType.SHIPPING_INFORMATION_TM, subject);
    text = getShippingReportMainText();
    textMap.put(MailComposition.TM_SHIPPING_REPORT_MAIN, text);
    text = getShippingReportHeader();
    textMap.put(MailComposition.TM_SHIPPING_REPORT_HEADER, text);
    text = getShippingReportDetail();
    textMap.put(MailComposition.TM_SHIPPING_REPORT_DETAIL, text);
    text = signText;
    textMap.put(MailComposition.TM_SHIPPING_REPORT_SIGN, text);
    // 2012-04-01 yyq add end

    /** 予約キャンセル */
    subject = Messages.getString("service.mail.DefaultMailTextCreater.14");
    subjectMap.put(MailType.CANCELLED_RESERVATION, subject);
    text = getCancelledReservationMain();
    textMap.put(MailComposition.CANCELLED_RESERVATION_MAIN, text);
    text = getCancelledReservationHeader();
    textMap.put(MailComposition.CANCELLED_RESERVATION_SHIPPING_HEADER, text);
    text = getCancelledReservationDetail();
    textMap.put(MailComposition.CANCELLED_RESERVATION_SHIPPING_DETAIL, text);
    text = getCancelledReservationPayment();
    textMap.put(MailComposition.CANCELLED_RESERVATION_PAYMENT, text);
    text = signText;
    textMap.put(MailComposition.CANCELLED_RESERVATION_SIGN, text);

    /** 予約確認 */
    subject = Messages.getString("service.mail.DefaultMailTextCreater.15");
    subjectMap.put(MailType.RESERVATION_DETAIL, subject);
    text = getReservationDetailMain();
    textMap.put(MailComposition.RESERVATION_DETAIL_MAIN, text);
    text = getReservationDetailShippingHeader();
    textMap.put(MailComposition.RESERVATION_DETAIL_SHIPPING_HEADER, text);
    text = getReservationDetailShippingDetail();
    textMap.put(MailComposition.RESERVATION_DETAIL_SHIPPING_DETAIL, text);
    text = getReservationDetailPayment();
    textMap.put(MailComposition.RESERVATION_DETAIL_PAYMENT, text);
    text = signText;
    textMap.put(MailComposition.RESERVATION_DETAIL_SIGN, text);

    /** 入金確認 */
    subject = Messages.getString("service.mail.DefaultMailTextCreater.16");
    subjectMap.put(MailType.RECEIVED_PAYMENT, subject);
    text = getReceivvedPaymentMain();
    textMap.put(MailComposition.RECEIVED_PAYMENT_MAIN, text);
    text = getReceivvedPaymentShipping();
    textMap.put(MailComposition.RECEIVED_PAYMENT_SHIPPING, text);
    text = getReceivvedPaymentShippingCommodity();
    textMap.put(MailComposition.RECEIVED_PAYMENT_SHIPPING_COMMODITY, text);
    text = signText;
    textMap.put(MailComposition.RECEIVED_PAYMENT_SIGN, text);

    /** 入金督促 */
    subject = Messages.getString("service.mail.DefaultMailTextCreater.17");
    subjectMap.put(MailType.PAYMENT_REMINDER, subject);
    text = getPaymentReminderMain();
    textMap.put(MailComposition.PAYMENT_REMINDER_MAIN, text);
    text = getPaymentReminderPaymentInfo();
    textMap.put(MailComposition.PAYMENT_REMINDER_PAYMENT_INFO, text);
    text = getPaymentReminderShipping();
    textMap.put(MailComposition.PAYMENT_REMINDER_SHIPPING, text);
    text = getPaymentReminderShippingCommodity();
    textMap.put(MailComposition.PAYMENT_REMINDER_SHIPPING_COMMODITY, text);
    text = signText;
    textMap.put(MailComposition.PAYMENT_REMINDER_SIGN, text);

    /** 受注キャンセル */
    subject = Messages.getString("service.mail.DefaultMailTextCreater.18");
    subjectMap.put(MailType.CANCELLED_ORDER, subject);
    text = getCancelledOrderMain();
    textMap.put(MailComposition.CANCELLED_ORDER_MAIN, text);
    text = getCancelledOrderShippingHeader();
    textMap.put(MailComposition.CANCELLED_ORDER_SHIPPING_HEADER, text);
    text = getCancelledOrderShippingDetail();
    textMap.put(MailComposition.CANCELLED_ORDER_SHIPPING_DETAIL, text);
    text = getCancelledOrderPayment();
    textMap.put(MailComposition.CANCELLED_ORDER_PAYMENT, text);
    text = signText;
    textMap.put(MailComposition.CANCELLED_ORDER_SIGN, text);

    /** 受注確認(携帯) */
    subject = Messages.getString("service.mail.DefaultMailTextCreater.19");
    subjectMap.put(MailType.ORDER_DETAILS_MOBILE, subject);
    text = getOrderDetailsMobileMain();
    textMap.put(MailComposition.ORDER_DETAILS_MOBILE_MAIN, text);
    textMap.put(MailComposition.ORDER_DETAILS_MOBILE_MODIFY_MESSAGE, Messages.getString("service.mail.DefaultMailTextCreater.20"));
    text = getOrderDetailsMobileShippingHeader();
    textMap.put(MailComposition.ORDER_DETAILS_MOBILE_SHIPPING_HEADER, text);
    text = getOrderDetailsMobileShippingDetail();
    textMap.put(MailComposition.ORDER_DETAILS_MOBILE_SHIPPING_DETAIL, text);
    text = getOrderDetailsMobilePayment();
    textMap.put(MailComposition.ORDER_DETAILS_MOBILE_PAYMENT, text);
    text = signText;
    textMap.put(MailComposition.ORDER_DETAILS_MOBILE_SIGN, text);

    /** 受注確認(PC) */
    subject = Messages.getString("service.mail.DefaultMailTextCreater.21");
    subjectMap.put(MailType.ORDER_DETAILS_PC, subject);
    text = getOrderDetailsPcMain();
    textMap.put(MailComposition.ORDER_DETAILS_PC_MAIN, text);
    // del by lc 2012-04-12 start
    //textMap.put(MailComposition.ORDER_DETAILS_PC_MODIFY_MESSAGE, Messages.getString("service.mail.DefaultMailTextCreater.22"));
    // del by lc 2012-04-12 end
    text = getOrderDetailsPcShippingHeader();
    textMap.put(MailComposition.ORDER_DETAILS_PC_SHIPPING_HEADER, text);
    text = getOrderDetailsPcShippingDetail();
    textMap.put(MailComposition.ORDER_DETAILS_PC_SHIPPING_DETAIL, text);
    text = getOrderDetailsPcPayment();
    textMap.put(MailComposition.ORDER_DETAILS_PC_PAYMENT, text);
    text = signText;
    textMap.put(MailComposition.ORDER_DETAILS_PC_SIGN, text);
  }

  private static String getOrderDetailsPcPayment() {
    StringWriter s = new StringWriter();
    PrintWriter out = new ExPrintWriter(s);

    out.println(Messages.getString("service.mail.DefaultMailTextCreater.23"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.24"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.25"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.26"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.27"));

    return s.toString();
  }

  private static String getOrderDetailsPcShippingDetail() {
    StringWriter s = new StringWriter();
    PrintWriter out = new ExPrintWriter(s);
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.28"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.29"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.30"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.31"));

    return s.toString();

  }

  private static String getOrderDetailsPcShippingHeader() {
    StringWriter s = new StringWriter();
    PrintWriter out = new ExPrintWriter(s);
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.32"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.33"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.34"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.35"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.36"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.37"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.38"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.39"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.40"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.41"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.42"));

    return s.toString();
  }

  private static String getOrderDetailsPcMain() {
    StringWriter s = new StringWriter();
    PrintWriter out = new ExPrintWriter(s);
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.43"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.44"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.45"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.46"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.47"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.48"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.49"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.50"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.51"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.52"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.53"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.54"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.55"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.56"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.57"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.58"));

    return s.toString();
  }

  private static String getOrderDetailsMobilePayment() {
    StringWriter s = new StringWriter();
    PrintWriter out = new ExPrintWriter(s);
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.59"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.60"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.61"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.62"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.63"));

    return s.toString();
  }

  private static String getOrderDetailsMobileShippingDetail() {
    StringWriter s = new StringWriter();
    PrintWriter out = new ExPrintWriter(s);
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.64"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.65"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.66"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.67"));

    return s.toString();
  }

  private static String getOrderDetailsMobileShippingHeader() {
    StringWriter s = new StringWriter();
    PrintWriter out = new ExPrintWriter(s);
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.68"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.69"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.70"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.71"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.72"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.73"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.74"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.75"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.76"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.77"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.78"));

    return s.toString();
  }

  private static String getOrderDetailsMobileMain() {
    StringWriter s = new StringWriter();
    PrintWriter out = new ExPrintWriter(s);
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.79"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.80"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.81"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.82"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.83"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.84"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.85"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.86"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.87"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.88"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.89"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.90"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.91"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.92"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.93"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.94"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.95"));

    return s.toString();
  }

  private static String getCancelledOrderPayment() {
    StringWriter s = new StringWriter();
    PrintWriter out = new ExPrintWriter(s);
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.96"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.97"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.98"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.99"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.100"));

    return s.toString();
  }

  private static String getCancelledOrderShippingDetail() {
    StringWriter s = new StringWriter();
    PrintWriter out = new ExPrintWriter(s);
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.101"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.102"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.103"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.104"));

    return s.toString();
  }

  private static String getCancelledOrderShippingHeader() {
    StringWriter s = new StringWriter();
    PrintWriter out = new ExPrintWriter(s);
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.105"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.106"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.107"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.108"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.109"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.110"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.111"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.112"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.113"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.114"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.115"));

    return s.toString();
  }

  private static String getCancelledOrderMain() {
    StringWriter s = new StringWriter();
    PrintWriter out = new ExPrintWriter(s);
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.116"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.117"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.118"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.119"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.120"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.121"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.122"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.123"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.124"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.125"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.126"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.127"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.128"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.129"));

    return s.toString();
  }

  private static String getPaymentReminderShippingCommodity() {
    StringWriter s = new StringWriter();
    PrintWriter out = new ExPrintWriter(s);
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.130"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.131"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.132"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.133"));

    return s.toString();
  }

  private static String getPaymentReminderShipping() {
    StringWriter s = new StringWriter();
    PrintWriter out = new ExPrintWriter(s);
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.134"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.135"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.136"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.137"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.138"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.139"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.140"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.141"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.142"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.143"));

    return s.toString();

  }

  private static String getPaymentReminderPaymentInfo() {
    StringWriter s = new StringWriter();
    PrintWriter out = new ExPrintWriter(s);
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.144"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.145"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.146"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.147"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.148"));

    return s.toString();

  }

  private static String getPaymentReminderMain() {
    StringWriter s = new StringWriter();
    PrintWriter out = new ExPrintWriter(s);
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.149"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.150"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.151"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.152"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.153"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.154"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.155"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.156"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.157"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.158"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.159"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.160"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.161"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.162"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.163"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.164"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.165"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.166"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.167"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.168"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.169"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.170"));

    return s.toString();
  }

  private static String getReceivvedPaymentShippingCommodity() {
    StringWriter s = new StringWriter();
    PrintWriter out = new ExPrintWriter(s);
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.171"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.172"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.173"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.174"));

    return s.toString();

  }

  private static String getReceivvedPaymentShipping() {
    StringWriter s = new StringWriter();
    PrintWriter out = new ExPrintWriter(s);
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.175"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.176"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.177"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.178"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.179"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.180"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.181"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.182"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.183"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.184"));

    return s.toString();
  }

  private static String getReceivvedPaymentMain() {
    StringWriter s = new StringWriter();
    PrintWriter out = new ExPrintWriter(s);
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.185"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.186"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.187"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.188"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.189"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.190"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.191"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.192"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.193"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.194"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.195"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.196"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.197"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.198"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.199"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.200"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.201"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.202"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.203"));

    return s.toString();

  }

  private static String getReservationDetailPayment() {
    StringWriter s = new StringWriter();
    PrintWriter out = new ExPrintWriter(s);
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.204"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.205"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.206"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.207"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.208"));

    return s.toString();

  }

  private static String getReservationDetailShippingDetail() {
    StringWriter s = new StringWriter();
    PrintWriter out = new ExPrintWriter(s);
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.209"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.210"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.211"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.212"));

    return s.toString();
  }

  private static String getReservationDetailShippingHeader() {
    StringWriter s = new StringWriter();
    PrintWriter out = new ExPrintWriter(s);
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.213"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.214"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.215"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.216"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.217"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.218"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.219"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.220"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.221"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.222"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.223"));

    return s.toString();

  }

  private static String getReservationDetailMain() {
    StringWriter s = new StringWriter();
    PrintWriter out = new ExPrintWriter(s);
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.224"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.225"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.226"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.227"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.228"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.229"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.230"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.231"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.232"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.233"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.234"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.235"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.236"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.237"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.238"));

    return s.toString();

  }

  private static String getCancelledReservationPayment() {
    StringWriter s = new StringWriter();
    PrintWriter out = new ExPrintWriter(s);
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.239"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.240"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.241"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.242"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.243"));

    return s.toString();

  }

  private static String getCancelledReservationDetail() {
    StringWriter s = new StringWriter();
    PrintWriter out = new ExPrintWriter(s);
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.244"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.245"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.246"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.247"));

    return s.toString();

  }

  private static String getCancelledReservationHeader() {
    StringWriter s = new StringWriter();
    PrintWriter out = new ExPrintWriter(s);
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.248"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.249"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.250"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.251"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.252"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.253"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.254"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.255"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.256"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.257"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.258"));

    return s.toString();

  }

  private static String getCancelledReservationMain() {
    StringWriter s = new StringWriter();
    PrintWriter out = new ExPrintWriter(s);
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.259"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.260"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.261"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.262"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.263"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.264"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.265"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.266"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.267"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.268"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.269"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.270"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.271"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.272"));

    return s.toString();

  }

  private static String getShippingReportDetail() {
    StringWriter s = new StringWriter();
    PrintWriter out = new ExPrintWriter(s);
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.273"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.274"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.275"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.276"));

    return s.toString();

  }

  private static String getShippingReportHeader() {
    StringWriter s = new StringWriter();
    PrintWriter out = new ExPrintWriter(s);
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.277"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.278"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.279"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.280"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.281"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.282"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.283"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.284"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.285"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.286"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.287"));

    return s.toString();

  }

  private static String getShippingReportMainText() {
    StringWriter s = new StringWriter();
    PrintWriter out = new ExPrintWriter(s);
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.288"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.289"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.290"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.291"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.292"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.293"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.294"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.295"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.296"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.297"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.298"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.299"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.300"));

    return s.toString();

  }

  private static String getArrivalInformationText() {
    StringWriter s = new StringWriter();
    PrintWriter out = new ExPrintWriter(s);
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.301"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.302"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.303"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.304"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.305"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.306"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.307"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.308"));
    out.println(Messages.getString("service.mail.DefaultMailTextCreater.309"));

    return s.toString();

  }
}
