package jp.co.sint.webshop.web.action.back.service;

import java.math.BigDecimal;

import jp.co.sint.webshop.data.dao.GiftCardReturnApplyDao;
import jp.co.sint.webshop.data.domain.OrderType;
import jp.co.sint.webshop.data.dto.GiftCardReturnApply;
import jp.co.sint.webshop.service.OrderService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.order.OrderContainer;
import jp.co.sint.webshop.service.order.OrderSummary;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.service.CardRefundBean;

public abstract class CardRefundBaseAction extends WebBackAction<CardRefundBean> {

  public abstract boolean authorize();

  public abstract WebActionResult callService();

  public abstract boolean validate();

  public void setDbToBean(OrderContainer orderContainer, CardRefundBean bean) {
    OrderService orderService = ServiceLocator.getOrderService(getLoginInfo());
    GiftCardReturnApplyDao dao = DIContainer.getDao(GiftCardReturnApplyDao.class);
    GiftCardReturnApply returnBean = dao.load(orderContainer.getOrderNo());
    if (returnBean != null) {
      bean.setRefundPrice(returnBean.getMemberInfoAmount().toString());
      bean.setReturnFlg(returnBean.getConfirmFlg().toString());
    } else {
      bean.setRefundPrice(BigDecimal.ZERO.toString());
      bean.setReturnFlg("-1");
    }
    
    OrderSummary orderSummary = orderService.getOrderSummary(orderContainer.getOrderNo(), OrderType.EC.getValue());
    BigDecimal totalOrderPrice = orderSummary.getTotalAmount();
    totalOrderPrice = totalOrderPrice.add(orderSummary.getPaymentCommission());

    bean.setOrderNo(orderContainer.getOrderNo());
    bean.setOrderPrice(StringUtil.coalesce(NumUtil.toString(totalOrderPrice), ""));
    if (orderContainer.getOrderHeader().getGiftCardUsePrice() != null) {
      bean.setCardUsedPrice(NumUtil.toString(orderContainer.getOrderHeader().getGiftCardUsePrice()));
    }

  }

  public void setBeanToDto(CardRefundBean bean, GiftCardReturnApply gcra) {
    OrderService service = ServiceLocator.getOrderService(getLoginInfo());
    OrderContainer orderContainer = service.getOrder(bean.getOrderNo());

    if (orderContainer != null && orderContainer.getOrderHeader() != null) {
      gcra.setOrderNo(bean.getOrderNo());
      gcra.setCardUseAmount(new BigDecimal(bean.getCardUsedPrice()));
      gcra.setMemberInfoAmount(new BigDecimal(bean.getRefundPrice()));
      gcra.setConfirmAmount(BigDecimal.ZERO);
      gcra.setConfirmFlg(0L);
      gcra.setReturnDate(DateUtil.getSysdate());
      gcra.setCustomerCode(orderContainer.getOrderHeader().getCustomerCode());
      
    }
  }
}
