package jp.co.sint.webshop.service.tmall;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.dto.TmallProperty;
import jp.co.sint.webshop.data.dto.TmallPropertyValue;

public class TmallPropertyAndValueInfoList {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;

  // 淘宝属性名称集合
  private List<TmallProperty> tmallProperty = new ArrayList<TmallProperty>();

  // 淘宝属性名称对应属性值集合
  private List<TmallPropertyValue> tmallPropertyValue = new ArrayList<TmallPropertyValue>();

  public List<TmallProperty> getTmallProperty() {
    return tmallProperty;
  }

  public void setTmallProperty(List<TmallProperty> tmallProperty) {
    this.tmallProperty = tmallProperty;
  }

  public List<TmallPropertyValue> getTmallPropertyValue() {
    return tmallPropertyValue;
  }

  public void setTmallPropertyValue(List<TmallPropertyValue> tmallPropertyValue) {
    this.tmallPropertyValue = tmallPropertyValue;
  }

}
