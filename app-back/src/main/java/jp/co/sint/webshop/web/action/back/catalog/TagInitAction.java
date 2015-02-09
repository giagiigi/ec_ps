package jp.co.sint.webshop.web.action.back.catalog;

import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.catalog.TagBean;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1040410:タグマスタのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class TagInitAction extends TagBaseAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  public boolean authorize() {
    return Permission.COMMODITY_READ.isGranted(getLoginInfo()) || Permission.CATALOG_READ.isGranted(getLoginInfo());
  }

  /**
   * 指定されたショップコードを元に、ショップが持つタグの一覧を取得します<BR>
   * サイト管理者の場合は、画面で選択されたショップコードを元に一覧を取得します<BR>
   * ショップ管理者の場合は、ログイン情報のショップコードを元に一覧を取得します
   */
  @Override
  public WebActionResult callService() {

    TagBean reqBean = new TagBean();

    reqBean.setSearchShopCode(getLoginInfo().getShopCode());
    
    // 10.1.1 10001 追加 ここから
    UtilService utilService = ServiceLocator.getUtilService(getLoginInfo());
    reqBean.setShopList(utilService.getShopNames(true));
    // 10.1.1 10001 追加 ここまで
    
    setRequestBean(reqBean);

    setNextUrl(null);

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {
    TagBean reqBean = (TagBean) getRequestBean();
    setEditDisplayControl(reqBean);
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.catalog.TagInitAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3104041003";
  }

}
