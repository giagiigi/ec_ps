package jp.co.sint.webshop.web.action.back.shop;

import java.util.List;

import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.data.domain.OperatingMode;
import jp.co.sint.webshop.data.dto.DeliveryCompany;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ShopManagementService;
import jp.co.sint.webshop.service.shop.DeliveryRegionChargeInfo;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.shop.DeliveryCompanyChargeBean;

/**
 * @author System Integrator Corp.
 */
public class DeliveryCompanyChargeInitAction extends DeliveryCompanyChargeBaseAction {
  /**
   * 
   * @return 
   */
  @Override
  public boolean authorize() {
	  boolean authorization = false;
	    if (getConfig().getOperatingMode().equals(OperatingMode.ONE)) {
	      authorization = Permission.SHOP_MANAGEMENT_READ_SITE.isGranted(getLoginInfo());
	    } else {
	      authorization = Permission.SHOP_MANAGEMENT_READ_SHOP.isGranted(getLoginInfo());
	    }
	    return authorization;
  }

  /**
   * beanのcreateAttributeを実行します。
   * 
   * @return 実行するならtrue
   */
//  @Override
//  public boolean isCallCreateAttribute() {
//    return false;
//  }
  /**
   * @return 
   */
  @Override
  public boolean validate() {
    return true;
  }
  /**
   * 
   * @return 
   */
  public WebActionResult callService() {
    
  	String[] urlParam=getRequestParameter().getPathArgs();
  	DeliveryCompanyChargeBean bean =new DeliveryCompanyChargeBean();
  	if (urlParam!=null && urlParam.length>=1 && ("search".equals(urlParam[0]) || "change".equals(urlParam[0]) || "updated".equals(urlParam[0]) || "copy".equals(urlParam[0]))){
  	  bean = getBean();
      setNextUrl(null);
  	}else {
  	  bean.setDefaultCompanyNo("D000");
  	}
  	SearchResult<DeliveryRegionChargeInfo> result = super.getDeliveryRegionChargeInfoList(bean);
  	
  	if (!(urlParam!=null && urlParam.length>=1 && "search".equals(urlParam[0]))){
  		bean.getDeliveryCompanyChargeEditBean().setPrefectureCode(null);
  		bean.getDeliveryCompanyChargeEditBean().setPrefectureName("共通设定");
  	}
  	
  	//初始化页面时，查询配送公司列表
  	if (urlParam == null || urlParam.length == 0){
    	ShopManagementService shopService = ServiceLocator.getShopManagementService(getLoginInfo());
      List<DeliveryCompany> deliveryCompanyList = shopService.getDeliveryCompanyList(getLoginInfo().getShopCode());
    	bean.getDeliveryCompanyList().add(new NameValue("默认(D000)","D000"));
    	for (DeliveryCompany company : deliveryCompanyList) {
        bean.getDeliveryCompanyList().add(new NameValue(company.getDeliveryCompanyName()+"("+company.getDeliveryCompanyNo()+")",company.getDeliveryCompanyNo()));
      }
  	}
  	
  	setRequestBean(bean);
  	return BackActionResult.RESULT_SUCCESS;
  }


  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return "运费设定初期表示处理";
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "4105131001";
  }

}
