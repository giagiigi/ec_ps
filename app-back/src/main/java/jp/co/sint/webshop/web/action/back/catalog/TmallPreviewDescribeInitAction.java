package jp.co.sint.webshop.web.action.back.catalog;

import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.catalog.TmallPreviewDescribeBean;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1040230:カテゴリ－関連付けのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class TmallPreviewDescribeInitAction extends WebBackAction<TmallPreviewDescribeBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return true;
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
    setBean(getBean());
    return BackActionResult.RESULT_SUCCESS;
  }


  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {
  }


  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("商品描述预览");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3104030001";
  }

}
