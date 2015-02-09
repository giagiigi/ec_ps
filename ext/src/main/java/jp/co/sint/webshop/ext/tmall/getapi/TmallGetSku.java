package jp.co.sint.webshop.ext.tmall.getapi;

import jp.co.sint.webshop.ext.tmall.LogOutPutRecord;
import jp.co.sint.webshop.ext.tmall.TmallConstant;
import jp.co.sint.webshop.service.tmall.TmallCommoditySku;
import jp.co.sint.webshop.service.tmall.TmallGetSkuService;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.StringUtil;

import org.apache.log4j.Logger;

import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.Sku;
import com.taobao.api.request.ItemSkuGetRequest;
import com.taobao.api.response.ItemSkuGetResponse;

public class TmallGetSku implements TmallGetSkuService {

  /**
   * sku获取 api:taobao.item.sku.get
   */
  int ifId = 5;

  public TmallCommoditySku getSku(TmallCommoditySku tcs) {

    Logger logger = Logger.getLogger(this.getClass());

    // 获取url
    TmallConstant tc = DIContainer.get(TmallConstant.class.getSimpleName());

    LogOutPutRecord logtool = new LogOutPutRecord(tc.getApiLogpath(), "");

    TmallCommoditySku reTcs = null;

    TaobaoClient client = new DefaultTaobaoClient(tc.getReqUrl(), tc.getAppKey(), tc.getAppSercet());
    ItemSkuGetRequest req = new ItemSkuGetRequest();
    req.setFields("sku_id,num_iid,properties,quantity,price,outer_id,created,modified,status");
    try {
      if (!StringUtil.isNullOrEmpty(tcs.getSkuId()) && !StringUtil.isNullOrEmpty(tcs.getNumiid())) {
        //req.setSkuId(Long.parseLong(tcs.getSkuId()));
        req.setNumIid(Long.parseLong(tcs.getNumiid()));
      } else {
        return reTcs;
      }
      ItemSkuGetResponse response = client.execute(req, tc.getSessionKey());
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
        logtool.log("taobao.item.sku.get（SKU获取失败）", ifId);
        logtool.log(response.getErrorCode() + "_" + subMsg + "_" + msg, ifId);
        logtool.log(logtool.printHorizontalLine());
        return reTcs;
      } else {
        logtool.log("taobao.item.sku.get（SKU获取成功）", ifId);
        logtool.log(logtool.printHorizontalLine());
        reTcs = new TmallCommoditySku();
        Sku sku = response.getSku();
        reTcs.setNumiid(sku.getNumIid().toString());
        reTcs.setSkuId(sku.getSkuId().toString());
        reTcs.setProperties(sku.getProperties());
        reTcs.setQuantity(sku.getQuantity().toString());
        reTcs.setPrice(sku.getPrice());
        reTcs.setOuterId(sku.getOuterId());
        return reTcs;
      }
    } catch (Exception e1) {
      e1.printStackTrace();
    }
    return reTcs;
  }
}
