package jp.co.sint.webshop.service.alipayaddress;

import java.io.Serializable;

public interface AlipayAddressResult extends Serializable {

  /**
   * 获取返回结果
   * 
   * @return
   */
  AlipayAddressResultBean getResultBean();
}
