package jp.co.sint.webshop.web.action.back.analysis;

import jp.co.sint.webshop.data.domain.CouponType;
import jp.co.sint.webshop.service.communication.PrivateCouponListSearchCondition;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.analysis.PublicCouponBean;

/**
 * U1070210:商品別アクセスログ集計の基底クラスです。
 * 
 * @author OB.
 */
public abstract class PublicCouponBaseAction extends WebBackAction<PublicCouponBean> {
	
	public void setSearchCondition(PrivateCouponListSearchCondition condition,PublicCouponBean bean){
		
	    //优惠券规则名称
	    condition.setSearchCouponName(bean.getSearchCouponName());
	    //优惠券类别
	    condition.setSearchCouponType(CouponType.COMMON_DISTRIBUTION.getValue());
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
