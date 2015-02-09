package jp.co.sint.webshop.web.action.back.catalog;

import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.catalog.CommodityEditBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1040120:商品登録のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class CommodityEditBackAction extends CommodityEditBaseAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return Permission.COMMODITY_UPDATE.isGranted(getLoginInfo());
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

    CommodityEditBean reqBean = getBean();

    setRequestBean(reqBean);

    return BackActionResult.RESULT_SUCCESS;

  }

  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {

    CommodityEditBean reqBean = (CommodityEditBean) getRequestBean();
    //add by twh 2012-12-4 17:16:27 start
    //返回时页面显示的格式设置
    setDisplayControl(reqBean);
    if(!reqBean.isDisplaySuitCommodity()){
      reqBean.setCommodityCodeDisplayMode(WebConstantCode.DISPLAY_EDIT);
      reqBean.setCommodityEditDisplayMode(WebConstantCode.DISPLAY_EDIT);
      reqBean.setDisplayNextButton(true);
      reqBean.setDisplaySuitCommodity(false);
    }
    //add by twh 2012-12-4 17:16:27 end
    reqBean.setDisplayCancelButton(false);

    setRequestBean(reqBean);
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.catalog.CommodityEditBackAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3104012001";
  }

}
