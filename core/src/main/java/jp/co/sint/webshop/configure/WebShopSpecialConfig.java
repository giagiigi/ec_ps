package jp.co.sint.webshop.configure;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 広告に対応する
 * 
 * @author System Integrator Corp.
 */
public class WebShopSpecialConfig implements Serializable {

  private static final long serialVersionUID = 1L;

  // 后台商品编辑中有权利编辑有效期的用户
  private List<String> shelfLifeUserList = new ArrayList<String>();

  
  /**
   * @return the shelfLifeUserList
   */
  public List<String> getShelfLifeUserList() {
    return shelfLifeUserList;
  }

  
  /**
   * @param shelfLifeUserList the shelfLifeUserList to set
   */
  public void setShelfLifeUserList(List<String> shelfLifeUserList) {
    this.shelfLifeUserList = shelfLifeUserList;
  }

}
