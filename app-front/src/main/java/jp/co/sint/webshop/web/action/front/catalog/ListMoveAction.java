package jp.co.sint.webshop.web.action.front.catalog;

import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;

/**
 * U2040410:商品一覧のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class ListMoveAction extends ListBaseAction {

  private static final String MODE_MESSAGE = "message";
  
  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    String[] urlParam = getRequestParameter().getPathArgs();
    if (urlParam[0].equals(MODE_MESSAGE)) {
      setNextUrl("/app/catalog/list/init/moveback/"+urlParam[1] );
      return FrontActionResult.RESULT_SUCCESS;
    }
    return FrontActionResult.RESULT_SUCCESS;
  }

  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {
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
