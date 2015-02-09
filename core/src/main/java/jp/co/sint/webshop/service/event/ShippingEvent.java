package jp.co.sint.webshop.service.event;

/**
 * コンポーネント内でOrderServiceのイベントが発生したことを示すイベントです。
 * 
 * @author System Integrator Corp.
 */
public class ShippingEvent implements ServiceEvent {

  /**   */
  private static final long serialVersionUID = 1L;

  private String shippingNo;

  private boolean SendFlg;
  
  private String type;
  
  public ShippingEvent() {
  }

  public ShippingEvent(String shippingNo, boolean SendFlg, String type) {
    this.shippingNo = shippingNo;
    this.SendFlg = SendFlg;
    this.type = type;
  }

  public ShippingEvent(String shippingNo) {
    this.shippingNo = shippingNo;
    this.SendFlg = true;
  }

  /**
   * @return the shippingNo
   */
  public String getShippingNo() {
    return shippingNo;
  }

  /**
   * @param shippingNo
   *          the shippingNo to set
   */
  public void setShippingNo(String shippingNo) {
    this.shippingNo = shippingNo;
  }
  
  
  
  /**
   * sendFlgを取得します。
   *
   * @return SendFlg sendFlg
   */
  public boolean isSendFlg() {
    return SendFlg;
  }

  
  /**
   * sendFlgを設定します。
   *
   * @param sendFlg 
   *          sendFlg
   */
  public void setSendFlg(boolean sendFlg) {
    SendFlg = sendFlg;
  }

  
  /**
   * typeを取得します。
   *
   * @return type type
   */
  public String getType() {
    return type;
  }
  
}
