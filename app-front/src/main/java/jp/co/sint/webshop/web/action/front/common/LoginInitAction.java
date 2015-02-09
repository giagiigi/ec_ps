package jp.co.sint.webshop.web.action.front.common;

import jp.co.sint.webshop.service.DataIOService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.data.ContentsListResult;
import jp.co.sint.webshop.service.data.ContentsPath;
import jp.co.sint.webshop.service.data.ContentsSearchCondition;
import jp.co.sint.webshop.service.data.ContentsType;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.action.front.WebFrontAction;
import jp.co.sint.webshop.web.bean.front.common.LoginBean;

/**
 * U2010110:ログインのデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class LoginInitAction extends WebFrontAction<LoginBean> {

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    LoginBean bean = new LoginBean();
    //20111223 os013 add start
    //支付宝快捷登录参数取得
    Object result = null;
    DataIOService service = ServiceLocator.getDataIOService(getLoginInfo());
    result=service.getFastpayAlipayBean();
    bean.setFastpayFormObject(result); 
    //20111223 os013 add end
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

  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {
    LoginBean bean = (LoginBean) getRequestBean();
    bean.setGuestOrderButtonDisplayFlg(false);
    if (getRequestParameter().getPathArgs().length > 0) {
      if (getRequestParameter().getPathArgs()[0].equals("order") && getCart().getItemCount() > 0) {
        bean.setGuestOrderButtonDisplayFlg(true);
        String parameter = "";
        for (int i = 1; i < getRequestParameter().getPathArgs().length; i++) {
          parameter += "/" + getRequestParameter().getPathArgs()[i];
        }
        bean.setGuestOrderUrlParameter(parameter);
      }
    }

    // 広告コンテンツ設定（ログイン時広告）
    DataIOService service = ServiceLocator.getDataIOService(getLoginInfo());
    ContentsSearchCondition condition = new ContentsSearchCondition();
    condition.setContentsType(ContentsType.CONTENT_SITE_LOGIN);
    ContentsPath path = DIContainer.get("contentsPath");
    ContentsListResult result = service.getRandomContents(condition);
    if (result.getFileName() == null) {
      result.setFileName("");
    } else {
      bean.setAdvertiseImageUrl(path.getLoginPath() + "/" + result.getFileName());
      bean.setAdvertiseLinkUrl(service.getContentsData(path.getContentsSharedPath() + path.getLoginPath() + "/"
          + result.getFileName().substring(0, result.getFileName().lastIndexOf(".")) + ".txt"));
    }

    setRequestBean(bean);
  }
}
