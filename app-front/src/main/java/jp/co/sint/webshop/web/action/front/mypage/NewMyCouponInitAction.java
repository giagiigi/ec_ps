package jp.co.sint.webshop.web.action.front.mypage;

import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.bean.front.mypage.NewMyCouponBean;

/**
 * クーポン履歴のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class NewMyCouponInitAction extends NewMyCouponBaseAction {

  /**
   * 初期処理を実行します
   */
  @Override
  public void init() {
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    NewMyCouponBean bean = getBean();

    // 显示选项卡,切换分页的时候才会显示第一个
    String[] params = getRequestParameter().getPathArgs();
    if (params != null && params.length > 0) {
      bean.setTabIndex(params[0]);
    }
    if (bean == null) {
      bean = new NewMyCouponBean();
    }
    bean = initNewMyCouponBean(bean);
    setRequestBean(bean);
    return FrontActionResult.RESULT_SUCCESS;
  }

  /**
   * キャンセル完了表示
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
