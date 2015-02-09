package jp.co.sint.webshop.web.action.back.catalog;

import jp.co.sint.webshop.data.dto.CCommodityHeader;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.catalog.CommodityConstituteBean;
import jp.co.sint.webshop.web.bean.back.catalog.TmallCommodityEditBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.DisplayTransition;

public class CommodityConstituteMoveAction extends WebBackAction<CommodityConstituteBean> {

  @Override
  public boolean authorize() {
    return Permission.COMMODITY_CONSTITUTE_READ.isGranted(getLoginInfo());
  }

  @Override
  public boolean validate() {
    String[] path = getRequestParameter().getPathArgs();
    if (path != null && path.length > 1) {
      return true;
    }
    return false;
  }

  @Override
  public WebActionResult callService() {
    String[] path = getRequestParameter().getPathArgs();
    TmallCommodityEditBean bean = new TmallCommodityEditBean();
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    String type = path[0];
    String selectCommodityCode = path[1];
    CCommodityHeader cch = service.getCCommodityheader("00000000", selectCommodityCode);
    if (cch == null) {
      // 报错信息
      setRequestBean(bean);
      return BackActionResult.RESULT_SUCCESS;
    }
    setRequestBean(bean);
    if (type.equals("add")) {
      setNextUrl("/app/catalog/tmall_commodity_edit/init/" + selectCommodityCode);
    } else if (type.equals("update")) {
      setNextUrl("/app/catalog/tmall_commodity_edit/select/00000000/" + selectCommodityCode + "/edit");
    }
    // 遷移元情報をセッションに保存
    DisplayTransition.add(getBean(), "/app/catalog/commodity_constitute/register/" + selectCommodityCode, getSessionContainer());

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.bean.back.catalog.CommodityConstituteInitAction.3");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3104022001";
  }

}
