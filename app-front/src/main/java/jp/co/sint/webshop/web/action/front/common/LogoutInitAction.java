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
import jp.co.sint.webshop.web.bean.front.common.LogoutBean;

/**
 * U2010110:ログインのデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class LogoutInitAction extends WebFrontAction<LogoutBean> {

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    LogoutBean bean = new LogoutBean();
    // セッション情報を破棄してログアウト画面へ遷移します
    getSessionContainer().logout();

    // 広告コンテンツ設定
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
    //20111221 os013 add start
    //清空cookies
    int alivePeriod = 0;
    String cookiepath = "/";
    getCookieContainer().addSecureCookies(DIContainer.getWebshopConfig().getHostName(), "", cookiepath, alivePeriod);
    //20111221 os013 add end
    setRequestBean(bean);
    //setNextUrl(null);
    setNextUrl("/app/common/index");

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
