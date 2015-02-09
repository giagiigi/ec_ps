package jp.co.sint.webshop.service.alipay;

import java.io.Serializable;

public interface PaymentAlipayResult extends Serializable {

  /**
   * 获取返回结果
   * 
   * @return
   */
  PaymentAlipayResultBean getResultBean();
}
