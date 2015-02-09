package jp.co.sint.webshop.web.action.back.order;

import jp.co.sint.webshop.data.domain.DataTransportStatus;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.order.OrderDetailBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.InformationMessage;

/**
 * U1020220:受注管理明細の基底クラスです。
 * 
 * @author System Integrator Corp.
 */
public abstract class OrderDetailBaseAction extends WebBackAction<OrderDetailBean> {
	
	// ECの受注情報の場合
	public static final String EMALL_ORDER_TYPE = "1";
	
  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {
    OrderDetailBean bean = (OrderDetailBean) getRequestBean();

    if (DataTransportStatus.fromValue(bean.getOrderDataTransportFlg()).equals(DataTransportStatus.TRANSPORTED)) {
      addInformationMessage(WebMessage.get(InformationMessage.DATA_TRANSPORTED));
    }
  }
}
