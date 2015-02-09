package jp.co.sint.webshop.service.order;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import jp.co.sint.webshop.data.attribute.Datetime;
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.text.Messages;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;

public class ShippingReportCount implements Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;

  /** 受注番号 */
  @Required
  @Length(16)
  @Digit
  @Metadata(name = "受注番号", order = 1)
  private String orderNo;

  /** 出荷番号 */
  @Required
  @Length(16)
  @Digit
  @Metadata(name = "出荷番号", order = 2)
  private String shippingNo;

  /** 出荷日 */
  @Required
  @Datetime
  @Metadata(name = "出荷日", order = 3)
  private String shippingDate;

  /** 宅配便伝票番号 */
  @Length(30)
  @Digit
  @Metadata(name = "宅配便伝票番号", order = 4)
  private String deliverySlipNo;

  /** 到着予定日 */
  @Datetime
  @Metadata(name = "到着予定日", order = 5)
  private String arrivalDate;

  /** 到着時間開始 */
  @Length(2)
  @Digit
  @Metadata(name = "到着時間開始", order = 6)
  private String arrivalTimeStart;

  /** 到着時間終了 */
  @Length(2)
  @Digit
  @Metadata(name = "到着時間終了", order = 7)
  private String arrivalTimeEnd;

  /** 更新ユーザ */
  @Required
  @Metadata(name = "更新ユーザ", order = 8)
  private String updateUser;

  /**
   * 出荷番号を取得します
   * 
   * @return 出荷番号
   */
  public String getShippingNo() {
    return this.shippingNo;
  }

  /**
   * 受注番号を取得します
   * 
   * @return 受注番号
   */
  public String getOrderNo() {
    return this.orderNo;
  }

  /**
   * 宅配便伝票番号を取得します
   * 
   * @return 宅配便伝票番号
   */
  public String getDeliverySlipNo() {
    return this.deliverySlipNo;
  }

  /**
   * 到着予定日を取得します
   * 
   * @return 到着予定日
   */
  public String getArrivalDate() {
    return this.arrivalDate;
  }

  /**
   * 到着時間開始を取得します
   * 
   * @return 到着時間開始
   */
  public String getArrivalTimeStart() {
    return this.arrivalTimeStart;
  }

  /**
   * 到着時間終了を取得します
   * 
   * @return 到着時間終了
   */
  public String getArrivalTimeEnd() {
    return this.arrivalTimeEnd;
  }

  /**
   * 出荷日を取得します
   * 
   * @return 出荷日
   */
  public String getShippingDate() {
    return this.shippingDate;
  }

  /**
   * 出荷番号を設定します
   * 
   * @param val
   *          出荷番号
   */
  public void setShippingNo(String val) {
    this.shippingNo = val;
  }

  /**
   * 受注番号を設定します
   * 
   * @param val
   *          受注番号
   */
  public void setOrderNo(String val) {
    this.orderNo = val;
  }

  /**
   * 宅配便伝票番号を設定します
   * 
   * @param val
   *          宅配便伝票番号
   */
  public void setDeliverySlipNo(String val) {
    this.deliverySlipNo = val;
  }

  /**
   * 到着予定日を設定します
   * 
   * @param val
   *          到着予定日
   */
  public void setArrivalDate(String val) {
    this.arrivalDate = val;
  }

  /**
   * 到着時間開始を設定します
   * 
   * @param val
   *          到着時間開始
   */
  public void setArrivalTimeStart(String val) {
    this.arrivalTimeStart = val;
  }

  /**
   * 到着時間終了を設定します
   * 
   * @param val
   *          到着時間終了
   */
  public void setArrivalTimeEnd(String val) {
    this.arrivalTimeEnd = val;
  }

  /**
   * 出荷日を設定します
   * 
   * @param val
   *          出荷日
   */
  public void setShippingDate(String val) {
    this.shippingDate = val;
  }

  public String getUpdateUser() {
    return updateUser;
  }

  public void setUpdateUser(String val) {
    this.updateUser = val;
  }

  public List<String> isValid() {

    Logger logger = Logger.getLogger(ShippingReportCount.class);
    List<String> list = new ArrayList<String>();
    Date sDate = DateUtil.fromString(this.getShippingDate());
    Date aDate = DateUtil.fromString(this.getArrivalDate());

    // OrderService svc = ServiceLocator.getOrderService();

    // 出荷日と到着日が両方入力されている場合、日付の大小チェックを行う。
    if (sDate != null && aDate != null) {

      // 出荷日と到着日を比較し、到着日のほうが早ければエラーとする。
      if (aDate.before(sDate)) {
        list.add(Messages.getString("service.order.ShippingReportCount.0"));
        logger.debug(Messages.log("service.order.ShippingReportCount.1"));
      }
    }

    // 到着時間開始、到着時間終了の一方だけが入力されていたらエラーとする。
    if (StringUtil.isNullOrEmpty(this.getArrivalTimeStart()) && !StringUtil.isNullOrEmpty(this.getArrivalTimeEnd())) {
      list.add(Messages.getString("service.order.ShippingReportCount.2"));
      logger.debug(Messages.log("service.order.ShippingReportCount.3"));

      // 到着時間開始、到着時間終了の一方だけが入力されていたらエラーとする。
    } else if (!StringUtil.isNullOrEmpty(this.getArrivalTimeStart()) && StringUtil.isNullOrEmpty(this.getArrivalTimeEnd())) {
      list.add(Messages.getString("service.order.ShippingReportCount.4"));
      logger.debug(Messages.log("service.order.ShippingReportCount.5"));

      // 到着時間開始、到着時間終了両方 数値ではない(NULL、空文字、数値以外の文字列だとエラーとする)
    } else if (NumUtil.isNum(this.getArrivalTimeStart()) && NumUtil.isNum(this.getArrivalTimeEnd())) {
      // 到着時間開始≧到着時間終了であればエラーとする。
      if (Integer.parseInt(this.getArrivalTimeStart()) >= Integer.parseInt(this.getArrivalTimeEnd())) {
        list.add(Messages.getString("service.order.ShippingReportCount.6"));
        logger.debug(Messages.log("service.order.ShippingReportCount.7"));
      }
    }

    return list;
  }

  // public List < String > isExist() {
  //
  // Logger logger = Logger.getLogger(ShippingReportCount.class);
  // OrderService svc = new OrderServiceImpl();
  // List<String> messageList = new ArrayList<String>();
  // if (!StringUtil.isNullOrEmpty(this.getOrderNo()) ||
  // !StringUtil.isNullOrEmpty(this.getShippingNo())) {
  // if (svc.isNotExistOrderHeader(this.getOrderNo())) {
  // messageList.add("該当の受注Noデータが存在しません。");
  // } else if (svc.isNotExistShippingHeader(this.getShippingNo())) {
  // messageList.add("該当の出荷Noデータが存在しません。");
  // } else {
  // logger.debug("isValid:corrected");
  // }
  // }
  // return messageList;
  // }
}
