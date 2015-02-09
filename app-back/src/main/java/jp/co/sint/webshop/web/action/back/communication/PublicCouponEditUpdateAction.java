package jp.co.sint.webshop.web.action.back.communication;

import java.math.BigDecimal;

import jp.co.sint.webshop.data.domain.CouponIssueType;
import jp.co.sint.webshop.data.dto.NewCouponRule;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.utility.BigDecimalUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.communication.PublicCouponEditBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1060120:アンケートマスタのアクションクラスです
 * 
 * @author OB
 */
public class PublicCouponEditUpdateAction extends PublicCouponEditBaseAction {

	 public boolean validate() {
		 
		 PublicCouponEditBean bean = getBean();
		 
		 


		//验证是否存在
			if( checktoDuplicate(bean.getCouponId()) == false ){
				
				addErrorMessage(WebMessage
						.get(ServiceErrorMessage.NO_DATA_ERROR,"公共优惠劵规则"));
				setRequestBean(getBean());
				setBean(getBean());

		    return false;
			}
			
     if (bean.getCouponIssueType().equals(CouponIssueType.FIXED.getValue())) {
       if (BigDecimalUtil.isBelowOrEquals(new BigDecimal(getBean().getMinUseOrderAmount()), new BigDecimal(getBean().getMoney()) )) {
         addErrorMessage("利用最小购买金额必须大于优惠金额！");
         setRequestBean(getBean());
         setBean(getBean());
         return false;
       }
     }
			
			
		//验证比率和金额
			if (checkCouponAmount(bean)) {
				return super.validate();
			} else {
				return false;
			}
			

			

	}
  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

		
	    PublicCouponEditBean bean = getBean();

		CommunicationService communicationService = ServiceLocator
				.getCommunicationService(getLoginInfo());
		
		// 根据规则编号得到优惠规则对象
		NewCouponRule couponRule = communicationService
				.getPrivateCoupon(bean.getCouponId());
		
		if(!StringUtil.isNullOrEmpty(bean.getMaxUseOrderAmount())  && Double.parseDouble(bean.getMaxUseOrderAmount())<Double.parseDouble(bean.getMinUseOrderAmount())){
		  setNextUrl(null);
      addErrorMessage("利用最大购买金额不为空时，必须必利用最小购买金额大。");
		  setBean(getBean());
	    return BackActionResult.RESULT_SUCCESS;
		}
		// 将页面bean的值存放到dto中
		 couponRule = setDTOfromBean(bean,couponRule);
		ServiceResult result = communicationService.updateNewCouponRule(couponRule);
		
        //更新失败		
		if (result.hasError()) {
		      for (ServiceErrorContent error : result.getServiceErrorList()) {
		        if (error.equals(CommonServiceErrorContent.VALIDATION_ERROR)) {
		          return BackActionResult.SERVICE_VALIDATION_ERROR;
		        }
		      }
		      setNextUrl(null);
				addErrorMessage(WebMessage.get(
						ServiceErrorMessage.UPDATE_FAILED, bean.getCouponName()));
				setRequestBean(getBean());
		      return BackActionResult.RESULT_SUCCESS;
		    }
		
		//更新成功
		setNextUrl("/app/communication/public_coupon_edit/init/"
				+ WebConstantCode.COMPLETE_UPDATE + "/" + couponRule.getCouponCode() );
		
		setRequestBean(getBean());
		setBean(getBean());

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return "公共优惠券发行规则详细画面更新处理";
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "5106072003";
  }

	public boolean authorize() {

		if(super.authorize()){
			if (Permission.PUBLIC_COUPON_UPDATE_SHOP.isGranted(getLoginInfo()) == false) {
				return false;
			}
		}
		return true;
	}

}
