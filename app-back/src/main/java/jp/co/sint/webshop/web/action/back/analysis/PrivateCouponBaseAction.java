package jp.co.sint.webshop.web.action.back.analysis;

import jp.co.sint.webshop.service.communication.PrivateCouponListSearchCondition;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.analysis.PrivateCouponBean;

/**
 * 
 * 
 * @author   OB
 */
public abstract class PrivateCouponBaseAction extends WebBackAction<PrivateCouponBean> {
	
	public void setSearchCondition(PrivateCouponListSearchCondition condition,PrivateCouponBean bean){
		
		//优惠券规则编号
	    condition.setSearchCouponCode(bean.getSearchCouponCode());
	    //优惠券规则名称
	    condition.setSearchCouponName(bean.getSearchCouponName());
	    //优惠券类别
	    condition.setSearchCouponType(bean.getSearchCampaignType());
	    //优惠券发行类别
	    condition.setSearchCampaignType(bean.getSearchCouponType());
	    //优惠券利用开始日时(From)
	    condition.setSearchMinUseStartDatetimeFrom(bean.getSearchMinUseStartDatetimeFrom());
	    //优惠券利用开始日时(To)
	    condition.setSearchMinUseStartDatetimeTo(bean.getSearchMinUseStartDatetimeTo());
	    //优惠券利用结束日时(From)
	    condition.setSearchMinUseEndDatetimeFrom(bean.getSearchMinUseEndDatetimeFrom());
	    //优惠券利用结束日时(To)
	    condition.setSearchMinUseEndDatetimeTo(bean.getSearchMinUseEndDatetimeTo());
	    //发行状态
	    condition.setSearchCouponActivityStatus(bean.getSearchCouponActivityStatus());
	}

 
}
