package jp.co.sint.webshop.service.tmall;

public interface TmallUpdateShippingAddressInfoService {

  // 修改订单收获地址信息：需要传入淘宝发货信息类
  String updateShippingAddrInfo(TmallShippingDelivery tsh);

}
