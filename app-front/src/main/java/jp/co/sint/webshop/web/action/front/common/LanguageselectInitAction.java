package jp.co.sint.webshop.web.action.front.common;

import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.action.front.WebFrontAction;
import jp.co.sint.webshop.web.bean.front.common.LanguageselectBean;

/**
 * 语言选择跳转Action
 * @author Tang
 *
 */
public class LanguageselectInitAction extends WebFrontAction<LanguageselectBean>{

  @Override
  public WebActionResult callService() {
    LanguageselectBean bean = new LanguageselectBean();
    String[] urlParams = getRequestParameter().getRequestMap().get("lang");
    if (urlParams != null && urlParams.length > 0) {
      bean.setSearchLanguageType(urlParams[0]);
    }
    setRequestBean(bean);
    return FrontActionResult.RESULT_SUCCESS;
  }

  @Override
  public boolean validate() {
    // TODO Auto-generated method stub
    return true;
  }
  
}
