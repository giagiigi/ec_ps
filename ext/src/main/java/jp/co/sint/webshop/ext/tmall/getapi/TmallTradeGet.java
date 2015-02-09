package jp.co.sint.webshop.ext.tmall.getapi;

import org.apache.log4j.Logger;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.Trade;
import com.taobao.api.request.TradeGetRequest;
import com.taobao.api.response.TradeGetResponse;

import jp.co.sint.webshop.ext.tmall.LogOutPutRecord;
import jp.co.sint.webshop.ext.tmall.TmallConstant;
import jp.co.sint.webshop.service.tmall.TmallTradeGetService;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.StringUtil;

public class TmallTradeGet implements TmallTradeGetService {

  /**
   * 查询单笔订单交易状态
   * api:taobao.trade.get
   */
  int ifId = 18;

  public String searchTmallTradeGet(String tid) {

    Logger logger = Logger.getLogger(this.getClass());
    // 获取url
    TmallConstant tc = DIContainer.get(TmallConstant.class.getSimpleName());
    
    LogOutPutRecord logtool = new LogOutPutRecord(tc.getApiLogpath(), "");

    TaobaoClient client = new DefaultTaobaoClient(tc.getReqUrl(), tc.getAppKey(), tc.getAppSercet());
    TradeGetRequest req = new TradeGetRequest();
    req.setFields("status");
    req.setTid(Long.parseLong(tid));
    
    try {
      TradeGetResponse  response = client.execute(req, tc.getSessionKey());
      if (!StringUtil.isNullOrEmpty(response.getErrorCode())) {
        logger.error(response.getErrorCode() + "_" + response.getSubMsg() + "_" + response.getMsg());
        logtool.log("taobao.trade.get（查询单笔订单交易状态失败）", ifId);
        logtool.log(response.getErrorCode() + "_" + response.getSubMsg() + "_" + response.getMsg(), ifId);
        logtool.log(logtool.printHorizontalLine());
        return null;
      } else {
        logtool.log("taobao.trade.get（查询单笔订单交易状态成功）", ifId);
        logtool.log(logtool.printHorizontalLine());
        Trade trade = response.getTrade();
        return trade.getStatus();
      }
    } catch (ApiException e1) {
      e1.printStackTrace();
    }
    return null;
  }
}
