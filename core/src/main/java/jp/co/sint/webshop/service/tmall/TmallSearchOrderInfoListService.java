package jp.co.sint.webshop.service.tmall;

public interface TmallSearchOrderInfoListService {

  // 查询订单和发货数据封装到List
  TmallTradeInfoList searchOrderDetailList(String startTime, String endTime);
  
  // 订单下载时判断网络连接是否断开
  String getIsNetError();
}
