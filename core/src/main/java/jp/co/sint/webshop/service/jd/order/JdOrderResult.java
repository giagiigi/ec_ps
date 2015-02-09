package jp.co.sint.webshop.service.jd.order;

import java.io.Serializable;


public interface JdOrderResult extends Serializable {

  /**
   * 返回结果Bean
   * @return
   */
  JdOrderResultBean getResultBean();
  
}
