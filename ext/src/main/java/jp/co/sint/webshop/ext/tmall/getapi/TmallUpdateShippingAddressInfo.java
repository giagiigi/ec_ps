package jp.co.sint.webshop.ext.tmall.getapi;

import org.apache.log4j.Logger;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.Trade;
import com.taobao.api.request.TradeShippingaddressUpdateRequest;
import com.taobao.api.response.TradeShippingaddressUpdateResponse;

import jp.co.sint.webshop.ext.tmall.LogOutPutRecord;
import jp.co.sint.webshop.ext.tmall.TmallConstant;
import jp.co.sint.webshop.service.tmall.TmallShippingDelivery;
import jp.co.sint.webshop.service.tmall.TmallUpdateShippingAddressInfoService;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.StringUtil;

public class TmallUpdateShippingAddressInfo implements TmallUpdateShippingAddressInfoService {

  /**
   * 修改订单收货信息 api:taobao.trade.shippingaddress.update
   */

  int ifId = 15;

  @Override
  public String updateShippingAddrInfo(TmallShippingDelivery tsc) {
    Logger logger = Logger.getLogger(this.getClass());
    // 获取url
    TmallConstant tc = DIContainer.get(TmallConstant.class.getSimpleName());

    LogOutPutRecord logtool = new LogOutPutRecord(tc.getApiLogpath(), "");

    TaobaoClient client = new DefaultTaobaoClient(tc.getReqUrl(), tc.getAppKey(), tc.getAppSercet());
    TradeShippingaddressUpdateRequest req = new TradeShippingaddressUpdateRequest();
    req.setTid(Long.parseLong(tsc.getTid()));
    // 收货人全名
    if (StringUtil.isNotNull(tsc.getReceiverName())) {
      req.setReceiverName(tsc.getReceiverName());
    }
    // 收货人固定电话
    if (StringUtil.isNotNull(tsc.getReceiverPhone())) {
      req.setReceiverPhone(tsc.getReceiverPhone());
    }
    // 收货人手机
    if (StringUtil.isNotNull(tsc.getReceiverMobile())) {
      req.setReceiverMobile(tsc.getReceiverMobile());
    }
    // 收货详细地址
    if (StringUtil.isNotNull(tsc.getReceiverAddress())) {
      req.setReceiverAddress(tsc.getReceiverAddress());
    }

    try {
      TradeShippingaddressUpdateResponse response = client.execute(req, tc.getSessionKey());
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
        logtool.log("taobao.trade.shippingaddress.update（更新收货信息失败）", ifId);
        logtool.log(response.getErrorCode() + "_" + subMsg + "_" + msg, ifId);
        logtool.log(logtool.printHorizontalLine());
        return null;
      } else {
        logtool.log("taobao.trade.shippingaddress.update（更新收货信息成功）", ifId);
        logtool.log(logtool.printHorizontalLine());
        Trade trade = response.getTrade();
        return trade.getTid() + "";
      }
    } catch (ApiException e1) {
      e1.printStackTrace();
    }
    return null;
  }
}
