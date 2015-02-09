package jp.co.sint.webshop.web.action.back.catalog;

import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.catalog.StockListBean;
import jp.co.sint.webshop.web.text.back.Messages;


/**
 * U1040710:入荷お知らせのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class StockListInitAction extends WebBackAction<StockListBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
	  //return true;
    return Permission.COMMODITY_READ.isGranted(getLoginInfo()) || Permission.CATALOG_READ.isGranted(getLoginInfo());
	  
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
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    StockListBean bean = new StockListBean();

    bean.setSearchShopCode(getLoginInfo().getShopCode());
    // 画面表示用Beanを次画面Beanを設定
    setRequestBean(bean);
    
    if (Permission.COMMODITY_UPDATE.isGranted(getLoginInfo())) {
      bean.setRegisterButtonDisplayFlg(true);
    }else {
      bean.setRegisterButtonDisplayFlg(false);
    }
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.catalog.StockListInitAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3180000303";
  }

}
