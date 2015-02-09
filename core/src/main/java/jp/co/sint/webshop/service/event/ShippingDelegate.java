package jp.co.sint.webshop.service.event;


public interface ShippingDelegate {

  void shippingUpdated(ShippingEvent event,String OrderType);

}
