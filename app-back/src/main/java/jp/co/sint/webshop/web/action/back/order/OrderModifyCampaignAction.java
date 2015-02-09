package jp.co.sint.webshop.web.action.back.order;

import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.order.OrderModifyBean;
import jp.co.sint.webshop.web.bean.back.order.OrderModifyBean.ShippingHeaderBean.ShippingDetailBean;
import jp.co.sint.webshop.web.exception.URLNotFoundException;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1020230:受注修正のアクションクラスです。
 * 
 * @author OB.
 */
public class OrderModifyCampaignAction extends OrderModifyBaseAction {
  
  public boolean isCallCreateAttribute() {
    return false;
  }

  public boolean validate() {
    boolean valid = true;
    OrderModifyBean bean = getBean();
    if (!StringUtil.hasValueAllOf(getPathArg(0),getPathArg(1))) {
      throw new URLNotFoundException(WebMessage.get(ActionErrorMessage.BAD_URL));
    }
    if (!getPathArg(0).equals("set") && !getPathArg(0).equals("delete")) {
      throw new URLNotFoundException(WebMessage.get(ActionErrorMessage.BAD_URL));
    }
    ShippingDetailBean orgDetailBean = null;
    for (ShippingDetailBean detailBean : bean.getShippingHeaderList().get(0).getShippingDetailList()) {
      if (detailBean.getSkuCode().equals(getPathArg(1))) {
        orgDetailBean = detailBean;
        break;
      }
    }
    if (orgDetailBean==null) {
      throw new URLNotFoundException(WebMessage.get(ActionErrorMessage.BAD_URL));
    } else {
      orgDetailBean.setCampaignCode(this.getRequestParameter().get("campaignCode_"+orgDetailBean.getSkuCode()));
      setRequestBean(bean);
    }
    if (getPathArg(0).equals("set") && StringUtil.isNullOrEmpty(this.getRequestParameter().get("campaignCode_"+orgDetailBean.getSkuCode()))) {
      addErrorMessage("请输入折扣券编号。");
      valid=false;
    }
    return valid;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
	  OrderModifyBean bean = (OrderModifyBean) getRequestBean();
	  ShippingDetailBean orgDetailBean = null;
	  for (ShippingDetailBean detailBean : bean.getShippingHeaderList().get(0).getShippingDetailList()) {
	      if (detailBean.getSkuCode().equals(getPathArg(1))) {
	        orgDetailBean = detailBean;
	        break;
	      }
	    }
	  if (getPathArg(0).equals("set")) {
		  checkCampaignDiscount(bean,getPathArg(1),true);
	  } else {
		  orgDetailBean.setCampaignCode(null);
		  orgDetailBean.setCampaignName(null);
		  orgDetailBean.getOrderDetailCommodityInfo().setRetailPrice(orgDetailBean.getOrderDetailCommodityInfo().getRetailPrice().add(orgDetailBean.getDiscountValue()));
		  orgDetailBean.setDiscountType(null);
		  orgDetailBean.setDiscountValue(null);
	  }
	  //赠品信息的再计算
	  this.recomputeGift(bean, true);
	  recomputeShippingCharge(bean);
      recomputePrice(bean);
      recomputePoint(bean);
      recomputePaymentCommission(bean);
      recomputePrice(bean);
      numberLimitValidation(createOrderContainer(bean));
      createDeliveryToBean(bean);
	  setRequestBean(getBean());
	  return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.order.OrderModifyComputePointAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "1102023012";
  }
  
  private int getPathArgsLength() {
    return getRequestParameter().getPathArgs().length;
  }

  private String getPathArg(int index) {
    if (getPathArgsLength() > index) {
      return getRequestParameter().getPathArgs()[index];
    }
    return "";
  }

}
