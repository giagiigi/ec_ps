package jp.co.sint.webshop.ext.chinapay.wap;

import jp.co.sint.webshop.data.domain.PaymentMethodType;
import jp.co.sint.webshop.ext.chinapay.ChinapayConfig;
import jp.co.sint.webshop.ext.chinapay.ChinapayUtil;
import jp.co.sint.webshop.service.chinapay.PaymentChinapayParameter;
import jp.co.sint.webshop.service.chinapay.PaymentChinapayProvider;
import jp.co.sint.webshop.service.chinapay.PaymentChinapayResult;
import jp.co.sint.webshop.service.chinapay.PaymentChinapayResultBean;
import jp.co.sint.webshop.service.chinapay.PaymentChinapayResultImpl;
import jp.co.sint.webshop.service.chinapay.PaymentChinapayResultType;
import jp.co.sint.webshop.utility.DIContainer;

import org.apache.log4j.Logger;

/**
 * 银联支付处理
 * 
 * @author kousen
 */
public class ChinapayWapCard implements PaymentChinapayProvider {

  /**
   * 银联支付信息设定
   */
  public PaymentChinapayResult payment(PaymentChinapayParameter parameter,String methodType) {

    PaymentChinapayResultImpl impl = new PaymentChinapayResultImpl();

    PaymentChinapayResultBean bean = impl.getResultBean();

    ChinapayWapConfig config = DIContainer.get(ChinapayWapConfig.class.getSimpleName());

    // 银联支付参数初期化
    bean = ChinapayWapUtil.initBean(parameter, config);

    // Web订单生成
    bean.setPaydata(ChinapayWapUtil.getPaydata(bean, config));
    // 返回结果信息设置
    impl.setResultBean(bean);

    // 设置处理结果
    impl.setPaymentResultType(PaymentChinapayResultType.COMPLETED);

    return impl;
  }
  
  public PaymentChinapayResult find(PaymentChinapayParameter parameter,String type) {
    Logger logger = Logger.getLogger(this.getClass());
    PaymentChinapayResultImpl impl = new PaymentChinapayResultImpl();
    ChinapayConfig config = DIContainer.get(ChinapayConfig.class.getSimpleName());
    PaymentChinapayResultBean bean = ChinapayUtil.initBean(parameter, config,PaymentMethodType.CHINA_UNIONPAY.getValue());
    impl.setResultBean(ChinapayUtil.queryRequest(bean, config));
    impl.setPaymentResultType(PaymentChinapayResultType.COMPLETED);
    logger.info("银联查询(" + parameter.getOrderId() + ")(应答码:" + impl.getResultBean().getResponseCode() + ")(交易状态:"
        + impl.getResultBean().getTransactionStatus() + ")");
    return impl;
  }

  public PaymentChinapayParameter createParameterInstance() {
    return new PaymentChinapayParameter();
  }
}
