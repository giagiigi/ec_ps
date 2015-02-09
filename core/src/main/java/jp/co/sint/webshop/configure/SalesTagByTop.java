package jp.co.sint.webshop.configure;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SalesTagByTop implements Serializable {

  private static final long serialVersionUID = 1L;

  // 首页分类商品，根据tag查询
  private List<String> salesTagByTop = new ArrayList<String>();

  /**
   * @return the salesTagByTop
   */
  public List<String> getSalesTagByTop() {
    return salesTagByTop;
  }

  /**
   * @param salesTagByTop
   *          the salesTagByTop to set
   */
  public void setSalesTagByTop(List<String> salesTagByTop) {
    this.salesTagByTop = salesTagByTop;
  }

}
