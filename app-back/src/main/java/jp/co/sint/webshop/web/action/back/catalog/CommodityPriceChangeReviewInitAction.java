package jp.co.sint.webshop.web.action.back.catalog;

import jp.co.sint.webshop.data.domain.OperatingMode;
import jp.co.sint.webshop.data.domain.OrderFlg;
import jp.co.sint.webshop.data.domain.OrderType;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.catalog.CommodityPriceChangeReviewBean;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1020510:受注確認管理のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class CommodityPriceChangeReviewInitAction extends WebBackAction<CommodityPriceChangeReviewBean> {

  /**
   * 初期処理を実行します。
   */
  @Override
  public void init() {

    CommodityPriceChangeReviewBean bean = new CommodityPriceChangeReviewBean();

    
    setRequestBean(bean);
  }

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return getLoginInfo().hasPermission(Permission.COMMODITY_PRICE_CHANGE);
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

    return BackActionResult.RESULT_SUCCESS;

  }

  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {
    CommodityPriceChangeReviewBean bean = (CommodityPriceChangeReviewBean) getRequestBean();


    setRequestBean(bean);
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.order.OrderConfirmListSearchAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "1102021008";
  }

}