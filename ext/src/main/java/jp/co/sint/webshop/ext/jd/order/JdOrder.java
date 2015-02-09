package jp.co.sint.webshop.ext.jd.order;

import java.util.List;

import com.jd.open.api.sdk.domain.order.OrderSearchInfo;

import jp.co.sint.webshop.service.jd.order.JdOrderParameter;
import jp.co.sint.webshop.service.jd.order.JdOrderProvider;
import jp.co.sint.webshop.service.jd.order.JdOrderResult;
import jp.co.sint.webshop.service.jd.order.JdOrderResultBean;
import jp.co.sint.webshop.service.jd.order.JdOrderResultImpl;

public class JdOrder implements JdOrderProvider {

  @Override
  public JdOrderParameter createParameterInstance() {
    return new JdOrderParameter();
  }

  @Override
  public JdOrderResult orderDownload(JdOrderParameter parameter) {

    JdOrderResultImpl impl = new JdOrderResultImpl();

    // API 业务处理
    JdOrderGetApi orderApi = new JdOrderGetApi();
    JdOrderResultBean resultBean = new JdOrderResultBean();
    List<OrderSearchInfo> list = orderApi.getJdOrderApiList(parameter.getStartDate(), parameter.getEndDate(), parameter
        .getOrderStatus());
    if (list == null) {
      resultBean.setErrorFlg(true);
    } else {
      resultBean.setOrderList(list);
    }

    impl.setResultBean(resultBean);

    return impl;
  }

}
