package jp.co.sint.webshop.service.fastpay;

import java.io.Serializable;

public interface FastpayAlipayResult extends Serializable {

  /**
   * 获取返回结果
   * 
   * @return
   */
  FastpayAlipayResultBean getResultBean();
}
