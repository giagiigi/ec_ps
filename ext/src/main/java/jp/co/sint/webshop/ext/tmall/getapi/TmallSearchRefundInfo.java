package jp.co.sint.webshop.ext.tmall.getapi;

import org.apache.log4j.Logger;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.Refund;
import com.taobao.api.request.RefundGetRequest;
import com.taobao.api.response.RefundGetResponse;

import jp.co.sint.webshop.ext.tmall.LogOutPutRecord;
import jp.co.sint.webshop.ext.tmall.TmallConstant;
import jp.co.sint.webshop.service.tmall.TmallSearchRefundInfoService;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.StringUtil;

public class TmallSearchRefundInfo implements TmallSearchRefundInfoService {

  /**
   * 查询单笔退货明细
   * api:taobao.refund.get
   */
  int ifId = 13;

  public String searchRefundPayment(String refundId) {

    Logger logger = Logger.getLogger(this.getClass());
    // 获取url
    TmallConstant tc = DIContainer.get(TmallConstant.class.getSimpleName());
    
    LogOutPutRecord logtool = new LogOutPutRecord(tc.getApiLogpath(), "");

    TaobaoClient client = new DefaultTaobaoClient(tc.getReqUrl(), tc.getAppKey(), tc.getAppSercet());
    RefundGetRequest req = new RefundGetRequest();
    req.setFields("payment");
    if(!StringUtil.isNullOrEmpty(refundId)){
      req.setRefundId(Long.parseLong(refundId));
    }
    try {
      RefundGetResponse response = client.execute(req, tc.getSessionKey());
      if (!StringUtil.isNullOrEmpty(response.getErrorCode())) {
        logger.error(response.getErrorCode() + "_" + response.getSubMsg() + "_" + response.getMsg());
        logtool.log("taobao.refund.get（查询单笔退货明细失败）", ifId);
        logtool.log(response.getErrorCode() + "_" + response.getSubMsg() + "_" + response.getMsg(), ifId);
        logtool.log(logtool.printHorizontalLine());
        return null;
      } else {
        logtool.log("taobao.refund.get（查询单笔退货明细成功）", ifId);
        logtool.log(logtool.printHorizontalLine());
        Refund refund = response.getRefund();
        return refund.getPayment();
      }
    } catch (ApiException e1) {
      e1.printStackTrace();
    }
    return null;
  }
}
