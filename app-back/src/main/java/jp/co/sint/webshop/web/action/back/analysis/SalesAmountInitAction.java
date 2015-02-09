package jp.co.sint.webshop.web.action.back.analysis;

import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.bean.back.analysis.SalesAmountBean;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1070810:売上集計のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class SalesAmountInitAction extends SalesAmountSearchAction {

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
    SalesAmountBean requestBean = new SalesAmountBean();
    requestBean.setSearchYear(DateUtil.getYYYY(DateUtil.getSysdate()));
    requestBean.setSearchMonth(DateUtil.getMM(DateUtil.getSysdate()));
    setBean(requestBean);

    return super.callService();
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.analysis.SalesAmountInitAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "6107081002";
  }

}
