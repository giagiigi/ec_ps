package jp.co.sint.webshop.configure;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import jp.co.sint.webshop.utility.CodeUtil;
import jp.co.sint.webshop.utility.CollectionUtil;
import jp.co.sint.webshop.utility.DIContainer;

public class HelpMapContainer implements Serializable {

  private static final long serialVersionUID = 1L;

  private HashMap<String, String> helpMap = new HashMap<String, String>();

  /**
   * helpMapを取得します。
   * 
   * @return helpMap helpMap
   */
  public Map<String, String> getHelpMap() {
    Map<String, String> result = CodeUtil.getMapEntry("helpMessages");
    if (result == null || result.isEmpty()) {
      return DIContainer.get("helpMap");
    }
    return result;
  }

  /**
   * helpMapを設定します。
   * 
   * @param helpMap
   *          helpMap
   */
  public void setHelpMap(Map<String, String> helpMap) {
    CollectionUtil.copyAll(this.helpMap, helpMap);
  }

}
