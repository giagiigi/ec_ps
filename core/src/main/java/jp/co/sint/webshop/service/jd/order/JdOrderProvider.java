package jp.co.sint.webshop.service.jd.order;

public interface JdOrderProvider {

  /**
   * 参数初始化
   * 
   * @return
   */
  JdOrderParameter createParameterInstance();

  /**
   * 京东订单下载
   * 
   * @param parameter
   * @return
   */
  JdOrderResult orderDownload(JdOrderParameter parameter);
}
