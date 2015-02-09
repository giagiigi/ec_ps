package jp.co.sint.webshop.service.jd;

public interface JdShippingDeliverySendService {

  // Alipay批量发货
  boolean getJdShippingApiList(String order_id, String logistics_id, String waybill);
}
