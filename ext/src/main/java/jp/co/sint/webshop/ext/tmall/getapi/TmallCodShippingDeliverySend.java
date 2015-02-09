package jp.co.sint.webshop.ext.tmall.getapi;

import org.apache.log4j.Logger;

import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.Shipping;
import com.taobao.api.request.LogisticsOnlineSendRequest;
import com.taobao.api.response.LogisticsOnlineSendResponse;

import jp.co.sint.webshop.ext.tmall.LogOutPutRecord;
import jp.co.sint.webshop.ext.tmall.TmallConstant;
import jp.co.sint.webshop.service.tmall.TmallCodShippingDeliverySendService;
import jp.co.sint.webshop.service.tmall.TmallShippingDelivery;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.StringUtil;

public class TmallCodShippingDeliverySend implements TmallCodShippingDeliverySendService {

  /**
   * COD批量发货通知taobao.logistics.online.send
   */
  int ifId = 16;

  public boolean codShippingDeliverySend(TmallShippingDelivery tsd) {

    Logger logger = Logger.getLogger(this.getClass());
    // 获取key
    TmallConstant tc = DIContainer.get(TmallConstant.class.getSimpleName());

    LogOutPutRecord logtool = new LogOutPutRecord(tc.getApiLogpath(), "");

    TaobaoClient client = new DefaultTaobaoClient(tc.getReqUrl(), tc.getAppKey(), tc.getAppSercet());
    LogisticsOnlineSendRequest req = new LogisticsOnlineSendRequest();
    req.setTid(Long.parseLong(tsd.getTid()));
    req.setOutSid(tsd.getOutSid());
    req.setCompanyCode(tsd.getCompanyCode());
    try {
      LogisticsOnlineSendResponse response = client.execute(req, tc.getSessionKey());
      if (!StringUtil.isNullOrEmpty(response.getErrorCode())) {
        String subMsg = "";
        String msg = "";
        if (!StringUtil.isNullOrEmpty(response.getSubMsg())) {
          subMsg = response.getSubMsg();
        } else {
          subMsg = "";
        }
        if (!StringUtil.isNullOrEmpty(response.getMsg())) {
          msg = response.getMsg();
        } else {
          msg = "";
        }
        logger.error(response.getErrorCode() + "_" + subMsg + "_" + msg);
        logtool.log("taobao.logistics.online.send（Cod批量发货通知失败）", ifId);
        logtool.log(response.getErrorCode() + "_" + subMsg + "_" + msg, ifId);
        logtool.log(logtool.printHorizontalLine());
        return false;
      } else {
        logtool.log("taobao.logistics.online.send（Cod批量发货通知成功）", ifId);
        logtool.log(logtool.printHorizontalLine());
        Shipping ship = response.getShipping();
        return ship.getIsSuccess();
      }
    } catch (Exception e1) {
      e1.printStackTrace();
    }
    return false;
  }
}
