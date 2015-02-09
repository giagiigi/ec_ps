package jp.co.sint.webshop.web.action.back.shop;

import java.util.List;

import jp.co.sint.webshop.data.domain.OperatingMode;
import jp.co.sint.webshop.data.dto.DeliveryCompany;
import jp.co.sint.webshop.data.dto.JdDeliveryRegion;
import jp.co.sint.webshop.data.dto.JdDeliveryRelatedInfo;
import jp.co.sint.webshop.data.dto.JdPrefecture;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ShopManagementService;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.shop.DeliveryJdCompanyDateBean;
import jp.co.sint.webshop.web.exception.URLNotFoundException;

/**
 * 
 * @author 
 */
public class DeliveryJdCompanyDateInitAction extends DeliveryJdCompanyDateBaseAction {

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
   * @return 
   */
  @Override
  public boolean validate() {
	//判定如果没有页面参数传递过来,直接报错
	String[] params = getRequestParameter().getPathArgs();
	if (params==null || params.length<2) {
		throw new URLNotFoundException(); 
	}
	return true;
  }

  /**
   * 查询所以配送时间间隔和配送希望日
   * @return WebActionResult
   */
  public WebActionResult callService() {
	DeliveryJdCompanyDateBean bean=new DeliveryJdCompanyDateBean();
	String[] params = getRequestParameter().getPathArgs();
	String deliveryCompanyNo=params[0];
	String prefectureCode=params[1];
	bean.setDeliveryCompanyNo(deliveryCompanyNo);
	bean.setPrefectureCode(prefectureCode);
	ShopManagementService service = ServiceLocator.getShopManagementService(getLoginInfo());
	DeliveryCompany company=service.getDeliveryCompany(getLoginInfo().getShopCode(), deliveryCompanyNo);
	//配送公司不存在时URL错误
	if (company == null) {
		throw new URLNotFoundException(); 
	}
	JdPrefecture prefectureInfo = service.getPrefectureInfoJd(prefectureCode);
	//配送地域不存在时URL错误
	if (prefectureInfo == null) {
		throw new URLNotFoundException(); 
	}
  bean.setDeliveryCompanyName(company.getDeliveryCompanyName());
  bean.setPrefectureName(prefectureInfo.getPrefectureName());
  bean.setMode("");
  JdDeliveryRegion deliveryRegion = service.getDeliveryRegionJd(getLoginInfo().getShopCode(),deliveryCompanyNo,prefectureCode);
  //配送关联地域不存在时URL错误
  if (deliveryRegion == null) {
    throw new URLNotFoundException(); 
  }
  List<JdDeliveryRelatedInfo> result=service.getDeliveryRelatedInfoListJd(getLoginInfo().getShopCode(), deliveryCompanyNo, prefectureCode);
  //设定配送希望日时一览画面
  setDeliveryRegionDateJd(result,bean);
	
	boolean updateFlg = false;
	boolean deleteFlg = false;
	if (getConfig().getOperatingMode().equals(OperatingMode.ONE)) {
		deleteFlg = Permission.SHOP_MANAGEMENT_DELETE_SITE.isGranted(getLoginInfo());
	   } else {
		deleteFlg = Permission.SHOP_MANAGEMENT_DELETE_SHOP.isGranted(getLoginInfo());
	   }
	if (getConfig().getOperatingMode().equals(OperatingMode.ONE)) {
		updateFlg = Permission.SHOP_MANAGEMENT_UPDATE_SITE.isGranted(getLoginInfo());
	   } else {
		updateFlg = Permission.SHOP_MANAGEMENT_UPDATE_SHOP.isGranted(getLoginInfo());
	   }
	bean.setDisplayDeleteFlg(deleteFlg);
	bean.setDisplayUpdateFlg(updateFlg);
	setRequestBean(bean);
    return BackActionResult.RESULT_SUCCESS;
  }
  
  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return "配送希望日时关联情报初期表示处理";
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "4105161001";
  }

}
