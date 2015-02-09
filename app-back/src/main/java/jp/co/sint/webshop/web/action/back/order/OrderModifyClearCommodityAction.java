package jp.co.sint.webshop.web.action.back.order;

import java.util.List;

import jp.co.sint.webshop.data.domain.CommodityType;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.order.OrderModifyBean;
import jp.co.sint.webshop.web.bean.back.order.OrderModifyBean.ShippingHeaderBean;
import jp.co.sint.webshop.web.bean.back.order.OrderModifyBean.ShippingHeaderBean.ShippingDetailBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.message.back.order.OrderErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1020230:受注修正のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class OrderModifyClearCommodityAction extends OrderModifyBaseAction {

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
	  super.setAddressList(getBean());
    boolean valid = false;
    if (getPathArgsLength() < 4) {
      // URLPathのサイズチェック
      addErrorMessage(WebMessage.get(ActionErrorMessage.BAD_URL));
    } else {
      // クリア商品情報存在チェック
      ShippingDetailBean detail = getBean().getShippingDetailBean(getPathShopCode(), getPathAddressNo(), getPathDeliveryTypeCode(),
          getPathSkuCode(),getPathArg(4));
      if (detail == null) {
        addErrorMessage(WebMessage.get(ActionErrorMessage.BAD_URL));
      } else {
        // 配送先がなくなる削除は不可
        if (getBean().getShippingHeaderBean(getPathShopCode(), getPathAddressNo(), getPathDeliveryTypeCode())
            .getShippingDetailList().size() <= 1
            && getBean().getShippingHeaderList().size() == 1) {
          addErrorMessage(WebMessage.get(OrderErrorMessage.DELETE_ALL_DELIVERY));
          return false;
        }
        if (CommodityType.GIFT.longValue().equals(detail.getCommodityType())) {
        	if (!detail.getCampaignCode().equals(getPathArg(4))) {
        		addErrorMessage(WebMessage.get(ActionErrorMessage.BAD_URL));
        		return false;
        	}
        }
        if (CommodityType.GENERALGOODS.longValue().equals(detail.getCommodityType())) {
        	int commodityCount = 0;
        	for (ShippingDetailBean detailBean : getBean().getShippingHeaderBean(getPathShopCode(), getPathAddressNo(), getPathDeliveryTypeCode())
                    .getShippingDetailList()) {
        		if (CommodityType.GENERALGOODS.longValue().equals(detailBean.getCommodityType())) {
        			commodityCount++;
        		}
        	}
        	if (commodityCount==1) {
        		addErrorMessage(WebMessage.get(OrderErrorMessage.DELETE_ALL_DELIVERY));
                return false;
        	}
        }
        valid = true;
      }
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
    OrderModifyBean bean = getBean();

    ShippingHeaderBean header = bean.getShippingHeaderBean(getPathShopCode(), getPathAddressNo(), getPathDeliveryTypeCode());
    List<ShippingDetailBean> detailList = header.getShippingDetailList();
    ShippingDetailBean detailBean = bean.getShippingDetailBean(header, getPathSkuCode(),getPathArg(4));
    if (detailBean !=null && StringUtil.hasValue(detailBean.getCampaignName()) 
    		&& CommodityType.GENERALGOODS.longValue().equals(detailBean.getCommodityType())) {
    	detailBean.getOrderDetailCommodityInfo().setRetailPrice(detailBean.getOrderDetailCommodityInfo().getRetailPrice().add(detailBean.getDiscountValue()));
    }
    detailList.remove(bean.getShippingDetailBean(header, getPathSkuCode(),getPathArg(4)));

    if (detailList.size() <= 0) {
      List<ShippingHeaderBean> headerList = bean.getShippingHeaderList();
      headerList.remove(header);

      // 配送先が1箇所の場合削除ボタン非表示
      if (headerList.size() == 1) {
        headerList.get(0).setDeletable(false);
      }
    }

    this.recomputeGift(bean, true);
    recomputePrice(bean);
    recomputeShippingCharge(bean);
    recomputePoint(bean);
    recomputePaymentCommission(bean);
    recomputePrice(bean);
    createDeliveryToBean(bean);
    createOutCardPrice();
    setRequestBean(bean);
    return BackActionResult.RESULT_SUCCESS;
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

  private String getPathShopCode() {
    return getPathArg(0);
  }

  private String getPathAddressNo() {
    return getPathArg(1);
  }

  private String getPathDeliveryTypeCode() {
    return getPathArg(2);
  }

  private String getPathSkuCode() {
    return getPathArg(3);
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.order.OrderModifyClearCommodityAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "1102023002";
  }
}
