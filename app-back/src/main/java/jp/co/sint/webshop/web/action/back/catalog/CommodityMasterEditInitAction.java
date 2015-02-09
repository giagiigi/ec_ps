package jp.co.sint.webshop.web.action.back.catalog;

import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.catalog.CommodityMasterEditBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;
/**
 * tm/jd多SKU商品登录关联
 */
public class CommodityMasterEditInitAction extends WebBackAction<CommodityMasterEditBean> {
  /**
   * 初期処理を実行します。
   */
  @Override
  public void init() {
    CommodityMasterEditBean bean = new CommodityMasterEditBean();
    setBean(bean);
  }

  @Override
  public boolean authorize() {
    return Permission.COMMODITY_MASTER_LIST.isGranted(getLoginInfo());
  }
  
  @Override
  public boolean validate() {
    return true;
  }
  
  @Override
  public WebActionResult callService() {
    CommodityMasterEditBean bean = new CommodityMasterEditBean();

    bean.setDisplayMode(WebConstantCode.DISPLAY_EDIT);
    bean.setDisplayLoginButtonFlg(true);
    bean.setDisplayUpdateButtonFlg(false);
    bean.setEditMode(WebConstantCode.DISPLAY_EDIT);
    bean.setDisplayDeleteFlg(Permission.DISCOUNT_DELETE_SHOP.isGranted(getLoginInfo()));

    setRequestBean(bean);
    return BackActionResult.RESULT_SUCCESS;
  }
  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {
    CommodityMasterEditBean bean = (CommodityMasterEditBean) getRequestBean();
    setRequestBean(bean);
  }
  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {

    return Messages.getString("web.action.back.catalog.CommodityMasterEditInitAction.0");
  }
  
  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "1102081003";
  }


}
