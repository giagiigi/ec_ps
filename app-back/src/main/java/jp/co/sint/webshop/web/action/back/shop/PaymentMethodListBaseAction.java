package jp.co.sint.webshop.web.action.back.shop;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.domain.PaymentMethodType;
import jp.co.sint.webshop.data.dto.Commission;
import jp.co.sint.webshop.data.dto.PaymentMethod;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ShopManagementService;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.shop.PaymentmethodListBean;
import jp.co.sint.webshop.web.bean.back.shop.PaymentmethodListBean.PaymentMethodDetail;

/**
 * U1050510:支払方法一覧のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public abstract class PaymentMethodListBaseAction extends WebBackAction<PaymentmethodListBean> {

  /**
   * 指定のショップに関連付いている支払方法リストを取得
   * 
   * @param shopCode
   * @return paymentMethodList
   */
  public List<PaymentMethodDetail> getPaymentMethodDetailList(String shopCode) {
    ShopManagementService service = ServiceLocator.getShopManagementService(getLoginInfo());

    List<PaymentMethod> paymentList = service.getPaymentMethodList(shopCode);

    List<PaymentMethodDetail> paymentMethodList = new ArrayList<PaymentMethodDetail>();
    for (PaymentMethod paymentMethod : paymentList) {
      if (paymentMethod.getPaymentMethodType().equals(PaymentMethodType.NO_PAYMENT.getValue())
          || paymentMethod.getPaymentMethodType().equals(PaymentMethodType.POINT_IN_FULL.getValue())) {
        continue;
      }

      PaymentMethodDetail paymentMethodDetail = new PaymentMethodDetail();
      paymentMethodDetail.setShopCode(paymentMethod.getShopCode());
      paymentMethodDetail.setPaymentMethodNo(NumUtil.toString(paymentMethod.getPaymentMethodNo()));
      paymentMethodDetail.setPaymentMethodName(paymentMethod.getPaymentMethodName());
      paymentMethodDetail.setPaymentCommissionTaxType(NumUtil.toString(paymentMethod.getPaymentCommissionTaxType()));
      if (paymentMethod.getPaymentMethodType() != null) {
        paymentMethodDetail.setPaymentMethodType(paymentMethod.getPaymentMethodType());
      }
      paymentMethodDetail.setAdvanceLaterFlg(Long.toString(paymentMethod.getAdvanceLaterFlg()));

      // 支払方法に関連付いている手数料一覧を取得する
      List<Commission> commissionList = service.getCommissionList(paymentMethod.getShopCode(), paymentMethod.getPaymentMethodNo());

      paymentMethodDetail.setCommissionRegistered(commissionList.size() > 0);

      // 支払方法が削除可能か判断する
      paymentMethodDetail.setDeleteButtonFlg(service.isDeletablePayment(shopCode, paymentMethod.getPaymentMethodNo()));

      paymentMethodList.add(paymentMethodDetail);
    }
    return paymentMethodList;
  }
}
