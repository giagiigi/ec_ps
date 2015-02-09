package jp.co.sint.webshop.web.action.front.catalog;

import jp.co.sint.webshop.data.domain.SetCommodityFlg;
import jp.co.sint.webshop.data.dto.CommodityHeader;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.CustomerServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.bean.front.catalog.CommodityDetailBean;
import jp.co.sint.webshop.web.exception.URLNotFoundException;
import jp.co.sint.webshop.web.login.front.FrontLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.front.ServiceErrorMessage;
import jp.co.sint.webshop.web.message.front.cart.CartDisplayMessage;
import jp.co.sint.webshop.web.text.front.Messages;

/**
 * U2040510:商品詳細のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class DetailAddfavoriteAction extends DetailBaseAction {

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    // 顧客の存在/退会済みチェック
    CustomerService customerSv = ServiceLocator.getCustomerService(getLoginInfo());
    if (customerSv.isNotFound(getLoginInfo().getCustomerCode()) || customerSv.isInactive(getLoginInfo().getCustomerCode())) {
      setNextUrl("/app/common/index");

      getSessionContainer().logout();
      return FrontActionResult.RESULT_SUCCESS;
    }

    CommodityDetailBean reqBean = getBean();

    //reqBean.setShopCode(getPathInfo(0));
    //reqBean.setCommodityCode(getPathInfo(1));
    //reqBean.setSkuCode(getPathInfo(2));
    reqBean.setShopCode("00000000");
    reqBean.setCommodityCode(getPathInfo(0));
    reqBean.setSkuCode(getPathInfo(1));

    CustomerService service = ServiceLocator.getCustomerService(getLoginInfo());
    
    // 2012/12/20 促销对应 ob add start
    CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());
    CommodityHeader  header = catalogService.getCommodityHeader(reqBean.getShopCode(), reqBean.getCommodityCode());
    if(header!=null && SetCommodityFlg.OBJECTIN.longValue().equals(header.getSetCommodityFlg())){
      throw new URLNotFoundException();
    }
    // 2012/12/20 促销对应 ob add end
    
    FrontLoginInfo login = getLoginInfo();
    ServiceResult result = service.insertFavoriteCommodity(login.getCustomerCode(), reqBean.getShopCode(), reqBean.getSkuCode());
    if (result.hasError()) {
      for (ServiceErrorContent content : result.getServiceErrorList()) {
        if (content.equals(CommonServiceErrorContent.VALIDATION_ERROR)) {
          return FrontActionResult.SERVICE_VALIDATION_ERROR;
        } else if (content.equals(CustomerServiceErrorContent.CUSTOMER_DELETED_ERROR)) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
              Messages.getString("web.action.front.catalog.DetailAddfavoriteAction.0")));
          createCommodityInfo(reqBean);
          setRequestBean(reqBean);
          return FrontActionResult.RESULT_SUCCESS;
        } else if (content.equals(CommonServiceErrorContent.NO_DATA_ERROR)) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
              Messages.getString("web.action.front.catalog.DetailAddfavoriteAction.1")));

          createCommodityInfo(reqBean);
          setRequestBean(reqBean);
        } else if (content.equals(CommonServiceErrorContent.DUPLICATED_REGISTER_ERROR)) {
          addErrorMessage(WebMessage.get(CartDisplayMessage.DUPLICATED_FAVORITE));

          createCommodityInfo(reqBean);
          setRequestBean(reqBean);
          return FrontActionResult.RESULT_SUCCESS;
        }
      }
      return FrontActionResult.SERVICE_ERROR;
    }

    addInformationMessage(WebMessage.get(CartDisplayMessage.COMPLETE_ADD_FAVORITE));
    createCommodityInfo(reqBean);
    setRequestBean(reqBean);

    return FrontActionResult.RESULT_SUCCESS;
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
