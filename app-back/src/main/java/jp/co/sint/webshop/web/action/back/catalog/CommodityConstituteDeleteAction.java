package jp.co.sint.webshop.web.action.back.catalog;

import java.util.List;

import jp.co.sint.webshop.data.dto.CCommodityHeader;
import jp.co.sint.webshop.data.dto.CommodityHeader;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.catalog.CommodityConstituteBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

public class CommodityConstituteDeleteAction extends CommodityConstituteRegisterAction {

  @Override
  public boolean authorize() {
    return Permission.COMMODITY_CONSTITUTE_DELETE.isGranted(getLoginInfo());
  }

  @Override
  public boolean validate() {
    String pa = null;
    String[] path = getRequestParameter().getPathArgs();
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    if (StringUtil.hasValueAllOf(path)) {
      pa = path[0];
    }
    List<CCommodityHeader> list = service.getOriginalCode(pa);
    CommodityHeader cheader = service.getCommodityHeader("00000000", pa);
    CCommodityHeader header = new CCommodityHeader();
    if (list.size() > 0) {
      header = list.get(0);
    }
    if (!NumUtil.isNull(header.getTmallCommodityId()) || cheader != null && StringUtil.hasValue(cheader.getCommodityCode())) {
      addErrorMessage("不能删除该条数据！");
      return false;
    }
    return true;
  }

  @Override
  public WebActionResult callService() {
    String[] path = getRequestParameter().getPathArgs();
    CommodityConstituteBean bean = getBean();
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    String pa = null;
    if (StringUtil.hasValueAllOf(path)) {
      pa = path[0];
    }
    List<CCommodityHeader> list = service.getOriginalCode(pa);
    if (list.size() > 0) {
      bean.setOriginalCommodityCode(list.get(0).getOriginalCommodityCode());
    }
    ServiceResult result = service.deleteCCommodityDetailByCommodityCode(pa);

    if (result.hasError()) {
      for (ServiceErrorContent error : result.getServiceErrorList()) {
        if (error.equals(CommonServiceErrorContent.NO_DATA_ERROR)) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_DEFAULT_ERROR));
        } else if (error.equals(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR)) {
          addErrorMessage(WebMessage.get(ActionErrorMessage.DELETE_ERROR));
        }
      }
      setRequestBean(bean);
      return BackActionResult.SERVICE_ERROR;
    }
    this.setRequestBean(bean);
    setNextUrl("/app/catalog/commodity_constitute/register/" + bean.getOriginalCommodityCode());
    return super.callService();
  }

  public String getActionName() {
    return Messages.getString("web.bean.back.catalog.CommodityConstituteInitAction.2");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3104015003";
  }
}
