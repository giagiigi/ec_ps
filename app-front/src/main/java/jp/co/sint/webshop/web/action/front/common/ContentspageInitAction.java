package jp.co.sint.webshop.web.action.front.common;

import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.action.front.WebFrontAction;
import jp.co.sint.webshop.web.bean.front.common.ContentspageBean;

/**
 * 
 * 
 * @author kousen.
 */
public class ContentspageInitAction extends WebFrontAction<ContentspageBean> {

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    ContentspageBean bean = new ContentspageBean();
    String[] urlParam = getRequestParameter().getPathArgs();
    if (urlParam.length >= 1) {
      bean.setType(urlParam[0]);
    }
    setRequestBean(bean);
    setNextUrl(null);

    return FrontActionResult.RESULT_SUCCESS;

  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    return true;
  }
}
