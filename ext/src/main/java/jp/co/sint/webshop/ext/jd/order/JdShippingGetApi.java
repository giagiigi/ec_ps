package jp.co.sint.webshop.ext.jd.order;

import jp.co.sint.webshop.api.jd.JdApiConfig;
import jp.co.sint.webshop.ext.jd.LogOutPutRecord;
import jp.co.sint.webshop.service.jd.JdShippingDeliverySendService;
import jp.co.sint.webshop.utility.DIContainer;

import org.apache.log4j.Logger;

import com.jd.open.api.sdk.DefaultJdClient;
import com.jd.open.api.sdk.JdClient;
import com.jd.open.api.sdk.request.order.OrderSopOutstorageRequest;
import com.jd.open.api.sdk.response.order.OrderSopOutstorageResponse;

public class JdShippingGetApi implements JdShippingDeliverySendService{
  
  int ifId = 3;
  
  public boolean getJdShippingApiList(String order_id, String logistics_id, String waybill) {

    Logger logger = Logger.getLogger(this.getClass());
    try{
      // 获取ReqUrl,AppKey,AppSercet
      JdApiConfig jc = DIContainer.get(JdApiConfig.class.getSimpleName());
      JdApiConfig constant = DIContainer.get(JdApiConfig.class.getSimpleName());
      LogOutPutRecord logtool = new LogOutPutRecord(constant.getApiLogpath(), "");
      //实例化对象
      JdClient client = new DefaultJdClient(jc.getReqUrl(), jc.getSessionKey(), jc.getAppKey(), jc.getAppSercet());
      OrderSopOutstorageRequest request = new OrderSopOutstorageRequest();
      
      //设置对象参数
      request.setOrderId(order_id);
      request.setLogisticsId(logistics_id);
      request.setWaybill(waybill);
      
      //调用api获取数据集
      OrderSopOutstorageResponse response = client.execute(request);        
      if (response.getCode().equals("0")) {
        logtool.log("（京东批量发货通知成功）", ifId);
        logtool.log(logtool.printHorizontalLine());
        return true;
      } else{
        logger.error(response.getCode() + "_" + response.getMsg());
        logtool.log("（京东批量发货通知失败）", ifId);
        logtool.log(response.getCode() + "_" + response.getMsg(), ifId);
        logtool.log(logtool.printHorizontalLine());
        return false;
      }
      
    } catch (Exception e1) {
      e1.printStackTrace();
    }
    return false;
  }
}


