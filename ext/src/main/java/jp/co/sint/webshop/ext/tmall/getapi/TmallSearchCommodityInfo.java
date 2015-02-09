package jp.co.sint.webshop.ext.tmall.getapi;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.Item;
import com.taobao.api.domain.Sku;
import com.taobao.api.request.ItemGetRequest;
import com.taobao.api.response.ItemGetResponse;

import jp.co.sint.webshop.ext.tmall.LogOutPutRecord;
import jp.co.sint.webshop.ext.tmall.TmallConstant;
import jp.co.sint.webshop.service.tmall.TmallCommodityHeader;
import jp.co.sint.webshop.service.tmall.TmallCommoditySku;
import jp.co.sint.webshop.service.tmall.TmallSearchCommodityInfoService;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.StringUtil;

public class TmallSearchCommodityInfo implements TmallSearchCommodityInfoService {

  /**
   * 查询单个商品信息 
   * api:taobao.item.get
   */
  int ifId = 12;

  public TmallCommodityHeader searchCommodity(String numIid) {

    Logger logger = Logger.getLogger(this.getClass());

    // 获取url
    TmallConstant tc = DIContainer.get(TmallConstant.class.getSimpleName());
    
    LogOutPutRecord logtool = new LogOutPutRecord(tc.getApiLogpath(), "");

    TaobaoClient client = new DefaultTaobaoClient(tc.getReqUrl(), tc.getAppKey(), tc.getAppSercet());
    ItemGetRequest req = new ItemGetRequest();
    req.setFields("num_iid,cid,price,sku,num");
    if(!StringUtil.isNullOrEmpty(numIid)){
      req.setNumIid(Long.parseLong(numIid));
    }
    try {
      ItemGetResponse response = client.execute(req, tc.getSessionKey());
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
        logtool.log("taobao.item.get（查询单个商品信息失败）", ifId);
        logtool.log(response.getErrorCode() + "_" + subMsg + "_" + msg, ifId);
        logtool.log(logtool.printHorizontalLine());
        return null;
      } else {
        Item item = response.getItem();
        if (item != null) {
          logtool.log("taobao.item.get（查询单个商品信息成功）", ifId);
          logtool.log(logtool.printHorizontalLine());
          TmallCommodityHeader tch = new TmallCommodityHeader();
          List<TmallCommoditySku> tmallCommoditySkuList = new ArrayList<TmallCommoditySku>();
          tch.setNumiid(item.getNumIid() + "");
          tch.setCid(item.getCid() + "");
          tch.setPrice(item.getPrice());
          tch.setNum(item.getNum().toString());
          List<Sku> skuItem = item.getSkus();
          Sku sku = null;
          if(skuItem != null){
            for (int i = 0; i < skuItem.size(); i++) {
              TmallCommoditySku tcs = new TmallCommoditySku();
              sku = (Sku) skuItem.get(i);
              tcs.setSkuId(sku.getSkuId() + "");
              tcs.setPrice(sku.getPrice());
              tmallCommoditySkuList.add(tcs);
            }
            tch.setTmallCommoditySkuList(tmallCommoditySkuList);
          }
          return tch;
        }
      }
    } catch (Exception e1) {
      e1.printStackTrace();
    }
    return null;
  }
}
