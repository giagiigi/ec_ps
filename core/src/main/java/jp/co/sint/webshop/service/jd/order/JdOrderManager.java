package jp.co.sint.webshop.service.jd.order;

import jp.co.sint.webshop.service.jd.JdShippingDeliverySendService;
import jp.co.sint.webshop.utility.DIContainer;

/**
 * 京东订单管理
 * 
 * @author kousen
 */
public class JdOrderManager {
  
  private JdGetAreasService jdGetAreasService;
  
  private JdShippingDeliverySendService jdShippingDeliverySendService;

  // 调用API接口
  private JdOrderProvider provider;

  // 调用API参数
  private JdOrderParameter parameter;

  public JdOrderManager() {
  }

  public JdOrderResult orderDownload(String startDate, String endDate, String orderStatus) {

    // 支付宝支付
    initParam(startDate, endDate, orderStatus);

    JdOrderResult result = provider.orderDownload(parameter);

    return result;
  }
  
  // 获得JD地址薄信息
  public boolean getJdAreas() {
    if (jdGetAreasService == null) {
      jdGetAreasService = DIContainer.get("JdGetAreas");
    }
    boolean result = jdGetAreasService.getJdAreas();
    return result;
  }

  // JD批量发货
  public boolean getJdShippingApiList(String order_id,String logistics_id,String waybill) {
    if (jdShippingDeliverySendService == null) {
      jdShippingDeliverySendService = DIContainer.get("JdShippingDeliverySendService");
    }
    boolean result = jdShippingDeliverySendService.getJdShippingApiList(order_id, logistics_id, waybill);
    return result;
  }

  
  /**
   * 初始化参数
   * 
   * @param container
   */
  private void initParam(String startDate, String endDate, String orderStatus) {
    if (provider == null) {
      provider = DIContainer.get("JdOrder");
    }

    if (parameter == null) {
      parameter = provider.createParameterInstance();
    }
    // parameter 参数设定
    parameter.setStartDate(startDate);
    parameter.setEndDate(endDate);
    parameter.setOrderStatus(orderStatus);
  }
}
