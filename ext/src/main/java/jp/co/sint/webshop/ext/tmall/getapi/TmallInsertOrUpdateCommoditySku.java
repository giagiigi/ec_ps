package jp.co.sint.webshop.ext.tmall.getapi;

import jp.co.sint.webshop.ext.tmall.LogOutPutRecord;
import jp.co.sint.webshop.ext.tmall.TmallConstant;
import jp.co.sint.webshop.service.tmall.TmallCommoditySku;
import jp.co.sint.webshop.service.tmall.TmallInsertOrUpdateCommoditySkuService;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.StringUtil;

import org.apache.log4j.Logger;

import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.Sku;
import com.taobao.api.request.ItemSkuAddRequest;
import com.taobao.api.request.ItemSkuUpdateRequest;
import com.taobao.api.response.ItemSkuAddResponse;
import com.taobao.api.response.ItemSkuUpdateResponse;

public class TmallInsertOrUpdateCommoditySku implements TmallInsertOrUpdateCommoditySkuService {

  /**
   * 商品Sku添加or更新 api:taobao.item.sku.add api:taobao.item.sku.update
   */

  public String insertOrUpdateCommoditySku(TmallCommoditySku tcs, String type) {

    Logger logger = Logger.getLogger(this.getClass());
    
    String skuId = "";
    
    // 获取url
    TmallConstant tc = DIContainer.get(TmallConstant.class.getSimpleName());
    LogOutPutRecord logtool = new LogOutPutRecord(tc.getApiLogpath(), "");

    TaobaoClient client = new DefaultTaobaoClient(tc.getReqUrl(), tc.getAppKey(), tc.getAppSercet());
    if (type.equals("INSERT")) {
      int ifId = 9;
      ItemSkuAddRequest req = new ItemSkuAddRequest();
      if (!StringUtil.isNullOrEmpty(tcs.getQuantity())) {
        req.setQuantity(Long.parseLong(tcs.getQuantity()));
      }
      req.setPrice(tcs.getPrice());
      if (!StringUtil.isNullOrEmpty(tcs.getNumiid())) {
        req.setNumIid(Long.parseLong(tcs.getNumiid()));
      }
      req.setProperties(tcs.getProperties());
      req.setOuterId(tcs.getOuterId());
      if (tcs.getItemPrice() != null) {
        req.setItemPrice(tcs.getItemPrice());
      }
      try {
        ItemSkuAddResponse response = client.execute(req, tc.getSessionKey());
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
          logtool.log("taobao.item.sku.add（商品SKU新增失败）", ifId);
          logtool.log(response.getErrorCode() + "_" + subMsg + "_" + msg, ifId);
          logtool.log(logtool.printHorizontalLine());
          skuId = "TmallReturn:" + subMsg;
          //return null;
        } else {
          logtool.log("taobao.item.sku.add（商品SKU新增成功）", ifId);
          logtool.log(logtool.printHorizontalLine());
          Sku sku = response.getSku();
          return sku.getSkuId() + "";
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    if (type.equals("UPDATE")) {
      int ifId = 10;
      ItemSkuUpdateRequest req = new ItemSkuUpdateRequest();

      // if(!StringUtil.isNullOrEmpty(tcs.getQuantity())){
      // req.setQuantity(Long.parseLong(tcs.getQuantity()));
      // }
      if (!StringUtil.isNullOrEmpty(tcs.getNumiid())) {
        req.setNumIid(Long.parseLong(tcs.getNumiid()));
      }
      if (tcs.getPrice() != null) {
        req.setPrice(tcs.getPrice());
      }
      req.setProperties(tcs.getProperties());
      req.setOuterId(tcs.getOuterId());
      if (tcs.getItemPrice() != null) {
        req.setItemPrice(tcs.getItemPrice());
      }
      try {
        ItemSkuUpdateResponse response = client.execute(req, tc.getSessionKey());
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
          logtool.log("taobao.item.sku.update（商品SKU更新失败）", ifId);
          logtool.log(response.getErrorCode() + "_" + subMsg + "_" + msg, ifId);
          logtool.log(logtool.printHorizontalLine());
          skuId = "TmallReturn:" + subMsg;
          //return null;
        } else {
          logtool.log("taobao.item.sku.update（商品SKU更新成功）", ifId);
          logtool.log(logtool.printHorizontalLine());
          Sku sku = response.getSku();
          return sku.getSkuId() + "";
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return skuId;
  }
}
