package jp.co.sint.webshop.web.action.front.catalog;

import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.action.front.WebFrontAction;
import jp.co.sint.webshop.web.bean.front.catalog.CategoryBean;

/**
 * U2040210:カテゴリトップのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class CategoryListInitAction extends WebFrontAction<CategoryBean> {


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
    return FrontActionResult.RESULT_SUCCESS;
  }
}
