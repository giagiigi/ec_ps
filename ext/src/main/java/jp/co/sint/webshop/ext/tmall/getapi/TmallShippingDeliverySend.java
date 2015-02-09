package jp.co.sint.webshop.ext.tmall.getapi;

import org.apache.log4j.Logger;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.Shipping;
import com.taobao.api.request.LogisticsDummySendRequest;
import com.taobao.api.request.LogisticsOfflineSendRequest;
import com.taobao.api.response.LogisticsDummySendResponse;
import com.taobao.api.response.LogisticsOfflineSendResponse;

import jp.co.sint.webshop.ext.tmall.LogOutPutRecord;
import jp.co.sint.webshop.ext.tmall.TmallConstant;
import jp.co.sint.webshop.service.tmall.TmallShippingDeliverySendService;
import jp.co.sint.webshop.service.tmall.TmallShippingDelivery;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.StringUtil;

public class TmallShippingDeliverySend implements TmallShippingDeliverySendService {

  /**
   * Alipay批量发货通知taobao.logistics.offline.send
   */
  int ifId = 14;

  public boolean ShippingDeliverySend(TmallShippingDelivery tsd) {

    Logger logger = Logger.getLogger(this.getClass());
    // 获取key
    TmallConstant tc = DIContainer.get(TmallConstant.class.getSimpleName());

    LogOutPutRecord logtool = new LogOutPutRecord(tc.getApiLogpath(), "");

    TaobaoClient client = new DefaultTaobaoClient(tc.getReqUrl(), tc.getAppKey(), tc.getAppSercet());

    if (tsd.getOrderTypes().equals("POSTFREE")) {
      // 如果是补拍邮费，那么执行无需物流发货
      LogisticsDummySendRequest req = new LogisticsDummySendRequest();
      req.setTid(Long.parseLong(tsd.getTid()));
      try {
        LogisticsDummySendResponse response = client.execute(req, tc.getSessionKey());
        if (!StringUtil.isNullOrEmpty(response.getErrorCode())) {
          logger.error(response.getErrorCode() + "_" + response.getSubMsg() + "_" + response.getMsg());
          logtool.log("taobao.logistics.dummy.send （Alipay批量发货通知失败）", ifId);
          logtool.log(response.getErrorCode() + "_" + response.getSubMsg() + "_" + response.getMsg(), ifId);
          logtool.log(logtool.printHorizontalLine());
          return false;
        } else {
          logtool.log("taobao.logistics.dummy.send （Alipay批量发货通知成功）", ifId);
          logtool.log(logtool.printHorizontalLine());
          Shipping ship = response.getShipping();
          return ship.getIsSuccess();
        }
      } catch (ApiException e1) {
        e1.printStackTrace();
      }
    } else {
      // 执行物流发货
      LogisticsOfflineSendRequest req = new LogisticsOfflineSendRequest();
      req.setTid(Long.parseLong(tsd.getTid()));
      req.setOutSid(tsd.getOutSid());
      req.setCompanyCode(tsd.getCompanyCode());
      try {
        LogisticsOfflineSendResponse response = client.execute(req, tc.getSessionKey());
        if (!StringUtil.isNullOrEmpty(response.getErrorCode())) {
          logger.error(response.getErrorCode() + "_" + response.getSubMsg() + "_" + response.getMsg());
          logtool.log("taobao.logistics.offline.send （Alipay批量发货通知失败）", ifId);
          logtool.log(response.getErrorCode() + "_" + response.getSubMsg() + "_" + response.getMsg(), ifId);
          logtool.log(logtool.printHorizontalLine());
          return false;
        } else {
          logtool.log("taobao.logistics.offline.send （Alipay批量发货通知成功）", ifId);
          logtool.log(logtool.printHorizontalLine());
          Shipping ship = response.getShipping();
          return ship.getIsSuccess();
        }
      } catch (ApiException e1) {
        e1.printStackTrace();
      }
    }

    return false;
  }
}
