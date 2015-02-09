package jp.co.sint.webshop.ext.chinapay;

import org.apache.log4j.Logger;

import jp.co.sint.webshop.service.chinapay.PaymentChinapayParameter;
import jp.co.sint.webshop.service.chinapay.PaymentChinapayProvider;
import jp.co.sint.webshop.service.chinapay.PaymentChinapayResult;
import jp.co.sint.webshop.service.chinapay.PaymentChinapayResultBean;
import jp.co.sint.webshop.service.chinapay.PaymentChinapayResultImpl;
import jp.co.sint.webshop.service.chinapay.PaymentChinapayResultType;
import jp.co.sint.webshop.utility.DIContainer;

/**
 * 银联支付处理
 * 
 * @author kousen
 */
public class ChinapayInnerCard implements PaymentChinapayProvider {

  /**
   * 银联支付信息设定
   */
  public PaymentChinapayResult payment(PaymentChinapayParameter parameter,String methodType) {

    PaymentChinapayResultImpl impl = new PaymentChinapayResultImpl();

    PaymentChinapayResultBean bean = impl.getResultBean();

    ChinapayInnerConfig config = DIContainer.get(ChinapayInnerConfig.class.getSimpleName());

    // 银联支付参数初期化
    bean = ChinapayUtil.initInnerBean(parameter, config);

    // 关键参数加密
    bean.setCheckValue(ChinapayUtil.getInnerCheckValue(new Object[] {
        bean.getMerchantId(), bean.getOrderId(), bean.getTransactionAmount(), bean.getCurrencyId(), bean.getTransactionDate(),
        bean.getTransactionType(), bean.getPrivate1()
    }, config));

    // 返回结果信息设置
    impl.setResultBean(bean);

    // 设置处理结果
    impl.setPaymentResultType(PaymentChinapayResultType.COMPLETED);

    return impl;
  }

  public PaymentChinapayResult find(PaymentChinapayParameter parameter,String type) {
    Logger logger = Logger.getLogger(this.getClass());
    PaymentChinapayResultImpl impl = new PaymentChinapayResultImpl();
    ChinapayInnerConfig config = DIContainer.get(ChinapayInnerConfig.class.getSimpleName());
    PaymentChinapayResultBean bean = ChinapayUtil.initInnerBean(parameter, config);
    impl.setResultBean(ChinapayUtil.queryInnerRequest(bean, config));
    impl.setPaymentResultType(PaymentChinapayResultType.COMPLETED);
    logger.info("银联查询(" + parameter.getOrderId() + ")(应答码:" + impl.getResultBean().getResponseCode() + ")(交易状态:"
        + impl.getResultBean().getTransactionStatus() + ")");
    return impl;
  }

  public PaymentChinapayParameter createParameterInstance() {
    return new PaymentChinapayParameter();
  }
}
