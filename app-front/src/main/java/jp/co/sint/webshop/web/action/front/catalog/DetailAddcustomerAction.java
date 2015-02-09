package jp.co.sint.webshop.web.action.front.catalog;

import jp.co.sint.webshop.data.dao.CustomerCommodityDao;
import jp.co.sint.webshop.data.dao.CustomerDao;
import jp.co.sint.webshop.data.domain.SetCommodityFlg;
import jp.co.sint.webshop.data.dto.CommodityHeader;
import jp.co.sint.webshop.data.dto.Customer;
import jp.co.sint.webshop.data.dto.CustomerCommodity;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.bean.front.catalog.CommodityDetailBean;
import jp.co.sint.webshop.web.exception.URLNotFoundException;
import jp.co.sint.webshop.web.login.front.FrontLoginInfo;

/**
 * U2040510:商品詳細のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class DetailAddcustomerAction extends DetailBaseAction {

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
    String commodityCode = getPathInfo(0);
    reqBean.setShopCode("00000000");
    reqBean.setCommodityCode(commodityCode);

    // 2012/12/20 促销对应 ob add start
    CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());
    CommodityHeader  header = catalogService.getCommodityHeader(reqBean.getShopCode(), reqBean.getCommodityCode());
    if(header!=null && SetCommodityFlg.OBJECTIN.longValue().equals(header.getSetCommodityFlg())){
      throw new URLNotFoundException();
    }
    // 2012/12/20 促销对应 ob add end
    
    FrontLoginInfo login = getLoginInfo();
    CustomerCommodityDao cusComDao = DIContainer.getDao(CustomerCommodityDao.class);
    CustomerCommodity oldCommodity = cusComDao.load(commodityCode, login.getCustomerCode());
    if (oldCommodity == null) {
      CustomerDao cusDao = DIContainer.getDao(CustomerDao.class);
      Customer customer = cusDao.load( login.getCustomerCode());
      CustomerCommodity cusCommodity = new CustomerCommodity();
      cusCommodity.setCommodityCode(commodityCode);
      cusCommodity.setCustomerCode( customer.getCustomerCode());
      cusCommodity.setLanguageCode(customer.getLanguageCode());
      cusCommodity.setCustomerName(customer.getLastName());
      if (DIContainer.getLocaleContext().getCurrentLanguageCode().equals("zh-cn")) {
        cusCommodity.setCommodityName(header.getCommodityName());
      } else if (DIContainer.getLocaleContext().getCurrentLanguageCode().equals("en-us")) {
        cusCommodity.setCommodityName(header.getCommodityNameEn());
      } else {
        cusCommodity.setCommodityName(header.getCommodityNameJp());
      }

      cusCommodity.setEmail(customer.getEmail());
      cusComDao.insert(cusCommodity);
    }

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
  
  public String getPathInfo(int index) {
    String[] tmp = getRequestParameter().getPathArgs();
    if (tmp.length > index) {
      return tmp[index];
    }
    return "";
  }
}
