package jp.co.sint.webshop.web.action.back.catalog;

import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.catalog.CommodityConstituteBean;
import jp.co.sint.webshop.web.text.back.Messages;

public class CommodityConstituteInitAction extends WebBackAction<CommodityConstituteBean> {

  @Override
  public boolean authorize() {
    return Permission.COMMODITY_CONSTITUTE_READ.isGranted(getLoginInfo());
  }

  @Override
  public boolean validate() {
    return true;
  }

  @Override
  public WebActionResult callService() {
    CommodityConstituteBean bean = new CommodityConstituteBean();
    setRequestBean(bean);
    return BackActionResult.RESULT_SUCCESS;
  }

  public String getActionName() {
    return Messages.getString("web.bean.back.catalog.CommodityConstituteInitAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3104015001";
  }

}
