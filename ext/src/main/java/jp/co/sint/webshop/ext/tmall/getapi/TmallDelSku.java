package jp.co.sint.webshop.ext.tmall.getapi;

import jp.co.sint.webshop.ext.tmall.LogOutPutRecord;
import jp.co.sint.webshop.ext.tmall.TmallConstant;
import jp.co.sint.webshop.service.tmall.TmallCommoditySku;
import jp.co.sint.webshop.service.tmall.TmallDelSkuService;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.StringUtil;

import org.apache.log4j.Logger;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.Sku;
import com.taobao.api.request.ItemSkuDeleteRequest;
import com.taobao.api.response.ItemSkuDeleteResponse;


public class TmallDelSku implements TmallDelSkuService{
  /**
   * sku删除
   * api:taobao.item.sku.delete
   */
  int ifId = 3;
  
  public String deleteSku(TmallCommoditySku tcs) {

    Logger logger = Logger.getLogger(this.getClass());

    // 获取url
    TmallConstant tc = DIContainer.get(TmallConstant.class.getSimpleName());
    
    LogOutPutRecord logtool = new LogOutPutRecord(tc.getApiLogpath(), "");

    TaobaoClient client = new DefaultTaobaoClient(tc.getReqUrl(), tc.getAppKey(), tc.getAppSercet());
    ItemSkuDeleteRequest  req = new ItemSkuDeleteRequest ();
    if(!StringUtil.isNullOrEmpty(tcs.getNumiid()) && !StringUtil.isNullOrEmpty(tcs.getProperties())){
      req.setNumIid(Long.parseLong(tcs.getNumiid()));
      req.setProperties(tcs.getProperties());
    }else{
      return null;
    }

    try {
      ItemSkuDeleteResponse  response = client.execute(req, tc.getSessionKey());
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
        logtool.log("taobao.item.sku.delete（商品SKU删除失败）", ifId);
        logtool.log(response.getErrorCode() + "_" + subMsg + "_" + msg, ifId);
        logtool.log(logtool.printHorizontalLine());
        return null;
      } else {
        logtool.log("taobao.item.sku.delete（商品SKU删除成功）", ifId);
        logtool.log(logtool.printHorizontalLine());
        Sku sku = response.getSku();
        return sku.getNumIid().toString();
      }
    } catch (ApiException e1) {
      e1.printStackTrace();
    }
    return null;
  }
}
