package jp.co.sint.webshop.web.action.back.shop;

import jp.co.sint.webshop.data.domain.OperatingMode;
import jp.co.sint.webshop.data.dto.DeliveryRegionCharge;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.ShopManagementService;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.shop.DeliveryCompanyChargeBean;
import jp.co.sint.webshop.web.bean.back.shop.DeliveryCompanyChargeBean.DeliveryCompanyChargeEditBean;
import jp.co.sint.webshop.web.exception.URLNotFoundException;

/**
 * U1030740:ポイントルールのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class DeliveryCompanyChargeUpdateAction extends DeliveryCompanyChargeBaseAction {

  /**
   * 确认权限
   * @return 是否通过
   */
  @Override
  public boolean authorize() {
   boolean authorization = false;
   if (getBean().getDeliveryCompanyChargeEditBean()==null ||
	   StringUtil.isNullOrEmpty(getBean().getDeliveryCompanyChargeEditBean().getPrefectureName())) {
	   return false;
   }
   if (getConfig().getOperatingMode().equals(OperatingMode.ONE)) {
     authorization = Permission.SHOP_MANAGEMENT_UPDATE_SITE.isGranted(getLoginInfo());
   } else {
     authorization = Permission.SHOP_MANAGEMENT_UPDATE_SHOP.isGranted(getLoginInfo());
   }
   return authorization;
  }

  /**
   * 验证参数值的合法性
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
	  DeliveryCompanyChargeBean bean=getBean();
	  DeliveryCompanyChargeEditBean detailBean=bean.getDeliveryCompanyChargeEditBean();
      boolean result= validateBean(detailBean);
      return result;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
	ShopManagementService service = ServiceLocator.getShopManagementService(getLoginInfo());
	ServiceResult serviceResult=null ;
	DeliveryCompanyChargeBean bean=getBean();
	DeliveryRegionCharge dto = new DeliveryRegionCharge();
	setDeliveryRegionChargeDto(dto,bean);
	serviceResult=service.updateDeliveryRegionCharge(dto);
	if(serviceResult.hasError())
	{
		throw new URLNotFoundException();
	}
	setNextUrl("/app/shop/delivery_company_charge/init/updated" + bean.toQueryString());
//	super.getDeliveryRegionChargeInfoList(bean);
//	this.addInformationMessage(WebMessage.get(CompleteMessage.UPDATE_COMPLETE, "运费设定"));
	setRequestBean(bean);
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return "运费设定更新处理";
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "4105131003";
  }

}
