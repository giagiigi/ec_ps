package jp.co.sint.webshop.web.action.back.shop;

import java.util.List;

import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.dao.DeliveryRegionChargeDao;
import jp.co.sint.webshop.data.domain.OperatingMode;
import jp.co.sint.webshop.data.dto.DeliveryRegionCharge;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.shop.DeliveryCompanyChargeBean;

/**
 * @author System Integrator Corp.
 */
public class DeliveryCompanyChargeCopyAction extends DeliveryCompanyChargeBaseAction {

  /**
   * 
   * @return 
   */
  @Override
  public boolean authorize() {
  	boolean authorization = false;
  	if (getConfig().getOperatingMode().equals(OperatingMode.ONE)) {
  	   authorization = Permission.SHOP_MANAGEMENT_UPDATE_SITE.isGranted(getLoginInfo());
  	} else {
  	   authorization = Permission.SHOP_MANAGEMENT_UPDATE_SHOP.isGranted(getLoginInfo());
  	}
    return authorization;
  }

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
	DeliveryCompanyChargeBean bean=getBean();
	copyDeliveryCompany(bean);
	setNextUrl("/app/shop/delivery_company_charge/init/copy" + bean.toQueryString());
	setRequestBean(bean);
	return BackActionResult.RESULT_SUCCESS;
  }


  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return "运费设定明细选择处理";
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "4105131002";
  }

  private void copyDeliveryCompany(DeliveryCompanyChargeBean bean){
    DeliveryRegionChargeDao deliveryRegionChargeDao = DIContainer.getDao(DeliveryRegionChargeDao.class);
    List<DeliveryRegionCharge> drcList = deliveryRegionChargeDao.findByQuery("SELECT * FROM DELIVERY_REGION_CHARGE WHERE DELIVERY_COMPANY_NO= 'D000'");
    for (DeliveryRegionCharge drCharge : drcList) {
      drCharge.setDeliveryCompanyNo(bean.getDefaultCompanyNo());
      drCharge.setOrmRowid(DatabaseUtil.DEFAULT_ORM_ROWID);
      deliveryRegionChargeDao.insert(drCharge);
    }
  }
  
}
