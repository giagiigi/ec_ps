package jp.co.sint.webshop.web.action.front.info;

import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.action.front.WebFrontAction;
import jp.co.sint.webshop.web.bean.front.info.AboutusBean;
import jp.co.sint.webshop.web.text.front.Messages;

/**
 * U2050510:ヘルプのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class AboutusInitAction extends WebFrontAction<AboutusBean> {

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    return true;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    AboutusBean bean = new AboutusBean();
    String[] params = getRequestParameter().getPathArgs();
    if (params.length > 0) {
      bean.setFilePath(params[0]);
      bean.setPageTopic(pageTopic(params[0]));
    }else{
      bean.setFilePath("aboutus");
    }
    setRequestBean(bean);
    setNextUrl(null);
    return FrontActionResult.RESULT_SUCCESS;
  }

  public String pageTopic(String pageTopic){
    return Messages.getString("web.bean.front.info.AboutusBean." + pageTopic);
  }
}
