package jp.co.sint.webshop.web.action.front.common;

import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.action.front.WebFrontAction;
import jp.co.sint.webshop.web.bean.UIMainBean;

public class AdviceInitAction extends WebFrontAction<UIMainBean> {

  public WebActionResult callService() {

    return FrontActionResult.RESULT_SUCCESS;
  }

  @Override
  public boolean validate() {
    return true;
  }

  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {

  }
}
