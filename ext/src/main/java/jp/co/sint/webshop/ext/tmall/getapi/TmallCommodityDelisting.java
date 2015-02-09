package jp.co.sint.webshop.ext.tmall.getapi;

import jp.co.sint.webshop.ext.tmall.LogOutPutRecord;
import jp.co.sint.webshop.ext.tmall.TmallConstant;
import jp.co.sint.webshop.service.tmall.TmallCommodityDelistingService;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.StringUtil;

import org.apache.log4j.Logger;

import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.Item;
import com.taobao.api.request.ItemUpdateDelistingRequest;
import com.taobao.api.response.ItemUpdateDelistingResponse;

public class TmallCommodityDelisting implements TmallCommodityDelistingService {

  /**
   * 商品下架 api:taobao.item.update.delisting
   */
  int ifId = 1;

  public String commodityDelisting(String numiid) {

    Logger logger = Logger.getLogger(this.getClass());

    // 获取url
    TmallConstant tc = DIContainer.get(TmallConstant.class.getSimpleName());

    LogOutPutRecord logtool = new LogOutPutRecord(tc.getApiLogpath(), "");

    TaobaoClient client = new DefaultTaobaoClient(tc.getReqUrl(), tc.getAppKey(), tc.getAppSercet());
    ItemUpdateDelistingRequest req = new ItemUpdateDelistingRequest();
    if (!StringUtil.isNullOrEmpty(numiid)) {
      req.setNumIid(Long.parseLong(numiid));
    }
    try {
      ItemUpdateDelistingResponse response = client.execute(req, tc.getSessionKey());
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
        logtool.log("taobao.item.update.delisting（商品下架失败）", ifId);
        logtool.log(response.getErrorCode() + "_" + subMsg + "_" + msg, ifId);
        logtool.log(logtool.printHorizontalLine());
        return null;
      } else {
        logtool.log("taobao.item.update.delisting（商品下架成功）", ifId);
        logtool.log(logtool.printHorizontalLine());
        Item item = response.getItem();
        return item.getNumIid() + "";
      }
    } catch (Exception e1) {
      e1.printStackTrace();
    }
    return null;
  }
}
