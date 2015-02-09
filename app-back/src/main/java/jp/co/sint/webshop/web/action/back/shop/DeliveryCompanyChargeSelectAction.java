package jp.co.sint.webshop.web.action.back.shop;

import jp.co.sint.webshop.data.domain.OperatingMode;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.shop.DeliveryCompanyChargeBean;
import jp.co.sint.webshop.web.bean.back.shop.DeliveryCompanyChargeBean.DeliveryCompanyChargeEditBean;
import jp.co.sint.webshop.web.exception.URLNotFoundException;

/**
 * @author System Integrator Corp.
 */
public class DeliveryCompanyChargeSelectAction extends DeliveryCompanyChargeBaseAction {

  /**
   * 
   * @return 
   */
  @Override
  public boolean authorize() {
	  //验证参数是否符号要求
	String[] urlParam=getRequestParameter().getPathArgs();
	if(urlParam.length!=1)
	{
		return false;
	}
	if(StringUtil.isNullOrEmpty(urlParam[0]))
	{
		return false;
	}
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
	String[] urlParam=getRequestParameter().getPathArgs();
	DeliveryCompanyChargeBean bean=getBean();
	
	DeliveryCompanyChargeEditBean DCCEditBean = null;
	
	if (bean.getList() !=null && bean.getList().size()>0) {
		for (DeliveryCompanyChargeEditBean detail:bean.getList()) {
			if (detail.getPrefectureCode().equals(urlParam[0])) {
			  DCCEditBean = detail;
				break;
			}
		}
	}
	if (null == DCCEditBean || StringUtil.isNullOrEmpty(DCCEditBean.getPrefectureCode())) {
		throw new URLNotFoundException();
	}
	
	bean.setDeliveryCompanyChargeEidtBean(DCCEditBean);
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

}
