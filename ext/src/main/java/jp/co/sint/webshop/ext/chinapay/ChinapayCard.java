package jp.co.sint.webshop.ext.chinapay;

import org.apache.log4j.Logger;

import jp.co.sint.webshop.data.domain.PaymentMethodType;
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
public class ChinapayCard implements PaymentChinapayProvider {

  /**
   * 银联支付信息设定
   */
  public PaymentChinapayResult payment(PaymentChinapayParameter parameter,String methodType) {

    PaymentChinapayResultImpl impl = new PaymentChinapayResultImpl();

    PaymentChinapayResultBean bean = impl.getResultBean();

    ChinapayConfig config = DIContainer.get(ChinapayConfig.class.getSimpleName());

    ChinapayInnerConfig innerConfig = DIContainer.get(ChinapayInnerConfig.class.getSimpleName());
    
    if (methodType.equals(PaymentMethodType.INNER_CARD.getValue())){
      
      // 银联支付参数初期化
      bean = ChinapayUtil.initInnerBean(parameter, innerConfig);

      // 关键参数加密
      bean.setCheckValue(ChinapayUtil.getInnerCheckValue(new Object[] {
          bean.getMerchantId(), bean.getOrderId(), bean.getTransactionAmount(), bean.getCurrencyId(), bean.getTransactionDate(),
          bean.getTransactionType(), bean.getPrivate1()
      }, innerConfig));
      
    } else {
      
      // 银联支付参数初期化
      bean = ChinapayUtil.initBean(parameter, config,methodType);

      // 关键参数加密
      bean.setCheckValue(ChinapayUtil.getCheckValue(new Object[] {
          bean.getMerchantId(), bean.getOrderId(), bean.getTransactionAmount(), bean.getCurrencyId(), bean.getTransactionDate(),
          bean.getTransactionType(), bean.getPrivate1()
      }, config));
    }


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
    ChinapayInnerConfig innerConfig = DIContainer.get(ChinapayInnerConfig.class.getSimpleName());
    if (type.equals(PaymentMethodType.INNER_CARD.getValue())){
      PaymentChinapayResultBean bean = ChinapayUtil.initInnerBean(parameter, innerConfig);
      impl.setResultBean(ChinapayUtil.queryInnerRequest(bean, innerConfig));
      
    } else {
      PaymentChinapayResultBean bean = ChinapayUtil.initBean(parameter, config,type);
      impl.setResultBean(ChinapayUtil.queryRequest(bean, config));

    }
    impl.setPaymentResultType(PaymentChinapayResultType.COMPLETED);
    logger.info("银联查询(" + parameter.getOrderId() + ")(应答码:" + impl.getResultBean().getResponseCode() + ")(交易状态:"
        + impl.getResultBean().getTransactionStatus() + ")");
    return impl;
  }

  public PaymentChinapayParameter createParameterInstance() {
    return new PaymentChinapayParameter();
  }
}
