package jp.co.sint.webshop.web.action.back.shop;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.data.domain.OperatingMode;
import jp.co.sint.webshop.data.dto.DeliveryRegionCharge;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ShopManagementService;
import jp.co.sint.webshop.service.shop.DeliveryRegionChargeCondition;
import jp.co.sint.webshop.service.shop.DeliveryRegionChargeInfo;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.shop.DeliveryCompanyChargeBean;
import jp.co.sint.webshop.web.bean.back.shop.DeliveryCompanyChargeBean.DeliveryCompanyChargeEditBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.CompleteMessage;
import jp.co.sint.webshop.web.webutility.PagerUtil;

/**
 * @author System Integrator Corp.
 */
public class DeliveryCompanyChargeBaseAction extends WebBackAction<DeliveryCompanyChargeBean> {
	
	public DeliveryRegionChargeCondition condition;

	public DeliveryRegionChargeCondition getCondition() {
	    return PagerUtil.createSearchCondition(getRequestParameter(), condition);
	  }

	public DeliveryRegionChargeCondition getSearchCondition() {
	    return this.condition;
	  }
	/**
	 * 
	 * @return
	 */
	@Override
	public boolean authorize() {
		return getLoginInfo().isAdmin();
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
		setRequestBean(getBean());
		return BackActionResult.RESULT_SUCCESS;
	}
	
	public SearchResult<DeliveryRegionChargeInfo> getDeliveryRegionChargeInfoList(DeliveryCompanyChargeBean bean) {
	  condition = new DeliveryRegionChargeCondition();
	  condition.setDeliveryCompanyNo(bean.getDefaultCompanyNo());
	  getCondition();
	  ShopManagementService service=ServiceLocator.getShopManagementService(getLoginInfo());
	  SearchResult<DeliveryRegionChargeInfo> result=service.getDeliveryRegionCharge(condition);
	  if(result.getRowCount()>0)
	  {
	    setDeliveryRegionChargeInfoList(result.getRows(), bean);
	    bean.setShowCopyInfo(false);
	  }else {
	    bean.setList(null);
	    bean.setShowCopyInfo(true);
	  }
	  //添加页面信息
	  bean.setPagerValue(PagerUtil.createValue(result));
	  return result;
	}
	/**
	 * 将查询结果的信息存放在画面Bean中
	 */
	public void setDeliveryRegionChargeInfoList(List<DeliveryRegionChargeInfo> info,DeliveryCompanyChargeBean bean) {
		bean.setList(new ArrayList<DeliveryCompanyChargeEditBean>());
		DeliveryCompanyChargeEditBean deliveryCompanyChargeBean = null;
		for (DeliveryRegionChargeInfo deliveryRegionChargeInfo : info) {
			deliveryCompanyChargeBean = new DeliveryCompanyChargeEditBean();
			deliveryCompanyChargeBean.setShopCode(deliveryRegionChargeInfo.getShopCode());
			deliveryCompanyChargeBean.setPrefectureCode(deliveryRegionChargeInfo.getPrefectureCode());
			deliveryCompanyChargeBean.setPrefectureName(deliveryRegionChargeInfo.getPrefectureName());
			deliveryCompanyChargeBean.setLeadTime(NumUtil.toString(deliveryRegionChargeInfo.getLeadTime()));
			deliveryCompanyChargeBean.setOrderAmount(deliveryRegionChargeInfo.getOrderAmount().toString());
			deliveryCompanyChargeBean.setDeliveryWeight(deliveryRegionChargeInfo.getDeliveryWeight().toString());
			deliveryCompanyChargeBean.setDeliveryChargeBig(deliveryRegionChargeInfo.getDeliveryChargeBig().toString());
			deliveryCompanyChargeBean.setDeliveryChargeSmall(deliveryRegionChargeInfo.getDeliveryChargeSmall().toString());
			deliveryCompanyChargeBean.setAddCharge(deliveryRegionChargeInfo.getAddCharge().toString());
			deliveryCompanyChargeBean.setAddWeight(deliveryRegionChargeInfo.getAddWeight().toString());
			deliveryCompanyChargeBean.setFreeOrderAmount(deliveryRegionChargeInfo.getFreeOrderAmount().toString());
			deliveryCompanyChargeBean.setFreeWeight(deliveryRegionChargeInfo.getFreeWeight().toString());
			//增加信息
			bean.getList().add(deliveryCompanyChargeBean);
		}
	}
	/**
	 * 填充信息到DeliveryRegionCharge
	 * @param deliveryRegionChargeDto deliveryRegionChargeDto
	 * @param bean DeliveryCompanyChargeBean
	 */
	public void setDeliveryRegionChargeDto(DeliveryRegionCharge deliveryRegionChargeDto,DeliveryCompanyChargeBean bean)
	{
		deliveryRegionChargeDto.setPrefectureCode(bean.getDeliveryCompanyChargeEditBean().getPrefectureCode());
    deliveryRegionChargeDto.setLeadTime(NumUtil.toLong(bean.getDeliveryCompanyChargeEditBean().getLeadTime()));
		deliveryRegionChargeDto.setAddCharge(NumUtil.parse(bean.getDeliveryCompanyChargeEditBean().getAddCharge()));
		deliveryRegionChargeDto.setAddWeight(NumUtil.parse(bean.getDeliveryCompanyChargeEditBean().getAddWeight()));
		deliveryRegionChargeDto.setDeliveryChargeBig(NumUtil.parse(bean.getDeliveryCompanyChargeEditBean().getDeliveryChargeBig()));
		deliveryRegionChargeDto.setDeliveryChargeSmall(NumUtil.parse(bean.getDeliveryCompanyChargeEditBean().getDeliveryChargeSmall()));
		deliveryRegionChargeDto.setDeliveryWeight(NumUtil.parse(bean.getDeliveryCompanyChargeEditBean().getDeliveryWeight()));
		deliveryRegionChargeDto.setFreeOrderAmount(NumUtil.parse(bean.getDeliveryCompanyChargeEditBean().getFreeOrderAmount()));
		deliveryRegionChargeDto.setFreeWeight(NumUtil.parse(bean.getDeliveryCompanyChargeEditBean().getFreeWeight()));
		deliveryRegionChargeDto.setOrderAmount(NumUtil.parse(bean.getDeliveryCompanyChargeEditBean().getOrderAmount()));
		deliveryRegionChargeDto.setDeliveryCompanyNo(bean.getDefaultCompanyNo());
	}

    /**
	* 画面表示に必要な項目を設定・初期化します。
	*/
    public void prerender() {
    	DeliveryCompanyChargeBean bean = (DeliveryCompanyChargeBean)getRequestBean();
    	boolean authorization = false;
    	if (getConfig().getOperatingMode().equals(OperatingMode.ONE)) {
    	  authorization = Permission.SHOP_MANAGEMENT_UPDATE_SITE.isGranted(getLoginInfo());
    	} else {
    	   authorization = Permission.SHOP_MANAGEMENT_UPDATE_SHOP.isGranted(getLoginInfo());
    	}
    	if (bean.getList()==null || bean.getList().size()==0) {
    		authorization = false;
    	}
    	String[] urlParam=getRequestParameter().getPathArgs();
    	if (urlParam!=null && urlParam.length>=1 && "updated".equals(urlParam[0])){
    		this.addInformationMessage(WebMessage.get(CompleteMessage.UPDATE_COMPLETE, "运费设定"));
    	}
    	if (urlParam!=null && urlParam.length>=1 && "copy".equals(urlParam[0])){
        this.addInformationMessage("复制完成。");
      }
    	bean.setShowUpdateInfo(authorization);
    	setRequestBean(bean);
	  }
	
}
