package jp.co.sint.webshop.ext.tmall.getapi;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jp.co.sint.webshop.configure.TmallSendMailConfig;
import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.SimpleQuery;
import jp.co.sint.webshop.ext.tmall.LogOutPutRecord;
import jp.co.sint.webshop.ext.tmall.TmallConstant;
import jp.co.sint.webshop.mail.MailInfo;
import jp.co.sint.webshop.service.MailingService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceLoginInfo;
import jp.co.sint.webshop.service.tmall.TmallCommodityHeader;
import jp.co.sint.webshop.service.tmall.TmallCommoditySku;
import jp.co.sint.webshop.service.tmall.TmallUpdateSkuStockService;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.StringUtil;

import org.apache.log4j.Logger;

import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.Item;
import com.taobao.api.request.ItemQuantityUpdateRequest;
import com.taobao.api.response.ItemQuantityUpdateResponse;

public class TmallUpdateSkuStock implements TmallUpdateSkuStockService {

  private TmallConnection tconnection = new TmallConnection();

  /**
   * sku库存更新 api:taobao.item.quantity.update
   */
  int ifId = 17;

  public String updateSkuStock(TmallCommoditySku tcs) {

    Logger logger = Logger.getLogger(this.getClass());

    // 获取url
    TmallConstant tc = DIContainer.get(TmallConstant.class.getSimpleName());

    LogOutPutRecord logtool = new LogOutPutRecord(tc.getApiLogpath(), "");

    TaobaoClient client = new DefaultTaobaoClient(tc.getReqUrl(), tc.getAppKey(), tc.getAppSercet());
    ItemQuantityUpdateRequest req = new ItemQuantityUpdateRequest();

    req.setNumIid(Long.parseLong(tcs.getNumiid())); // 所属商品id

    if (!StringUtil.isNullOrEmpty(tcs.getSkuId())) {
      req.setSkuId(Long.parseLong(tcs.getSkuId())); // tmall—sku-id
    }

    if (!StringUtil.isNullOrEmpty(tcs.getOuterId())) {
      req.setOuterId(tcs.getOuterId()); // EC-sku-id
    }
    // 如果tcs.getQuantity()+tmall上库存小于0，是会更新失败的

    if (!StringUtil.isNullOrEmpty(tcs.getQuantity())) {
      logtool.log("更新库存数为" + tcs.getQuantity(), ifId);
      req.setQuantity(Long.parseLong(tcs.getQuantity())); // 数量
    }

    if (!StringUtil.isNullOrEmpty(tcs.getUpdateType())) {
      req.setType(Long.parseLong(tcs.getUpdateType())); // 更新类型1：全量2：增量
    }

    try {
      ItemQuantityUpdateResponse response = client.execute(req, tc.getSessionKey());
      if (!StringUtil.isNullOrEmpty(response.getErrorCode())) {

        ItemQuantityUpdateRequest req2 = new ItemQuantityUpdateRequest();
        req2.setNumIid(Long.parseLong(tcs.getNumiid())); // 所属商品id

        if (!StringUtil.isNullOrEmpty(tcs.getSkuId())) {
          req2.setSkuId(Long.parseLong(tcs.getSkuId())); // tmall—sku-id
        }

        if (!StringUtil.isNullOrEmpty(tcs.getQuantity())) {
          req2.setQuantity(Long.parseLong(tcs.getQuantity())); // 数量
        }

        if (!StringUtil.isNullOrEmpty(tcs.getUpdateType())) {
          req2.setType(Long.parseLong(tcs.getUpdateType())); // 更新类型1：全量2：增量
        }
        ItemQuantityUpdateResponse response2 = client.execute(req2, tc.getSessionKey());
        logtool.log(response2.getErrorCode(), ifId);
        if (!StringUtil.isNullOrEmpty(response2.getErrorCode())) {
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
          logger.error(tcs.getOuterId());
          logger.error(tcs.getNumiid());
          logtool.log("taobao.item.quantity.update（商品库存更新失败）", ifId);
          logtool.log(response.getErrorCode() + "_" + subMsg + "_" + msg, ifId);
          logtool.log(logtool.printHorizontalLine());
          return null;
        } else {
          return "success";
        }
      } else {
        logtool.log("taobao.item.quantity.update（商品库存更新成功）", ifId);
        logtool.log(logtool.printHorizontalLine());
        Item item = response.getItem();
        Date date = item.getModified();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
      }
    } catch (Exception e1) {
      e1.printStackTrace();
    }
    return null;
  }

  // 获得淘宝库存可销售数
  public Map<String, String> getTmallStockAll() {

    Map<String, String> obj = null;
    try {
      obj = new HashMap<String, String>();
      tconnection.init();
      Query existsValueSql = new SimpleQuery("SELECT C.TMALL_COMMODITY_ID,(S.STOCK_TMALL-S.ALLOCATED_TMALL) AS NUM FROM STOCK S "
          + " INNER JOIN C_COMMODITY_HEADER C ON C.COMMODITY_CODE = S.COMMODITY_CODE " + " WHERE C.TMALL_COMMODITY_ID IS NOT NULL ");
      obj = tconnection.executeScalarGetListTmallStock(existsValueSql);
      if (obj == null || obj.size() <= 0) {
        return null;
      }
      tconnection.getConnection().commit();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (tconnection != null) {
        tconnection.dispose();
      }
    }
    return obj;
  }

  // 全量更新淘宝库存
  @SuppressWarnings("unchecked")
  public void updateTmallStock() {
    Map<String, String> obj = getTmallStockAll();
    Iterator it = obj.entrySet().iterator();

    while (it.hasNext()) {
      Map.Entry pairs = (Map.Entry) it.next();
      TmallCommoditySku tcs = new TmallCommoditySku();
      tcs.setNumiid(pairs.getKey().toString());
      tcs.setQuantity(pairs.getValue().toString());
      tcs.setUpdateType("1");
      updateSkuStock(tcs);
    }
  }

  // 获得淘宝库存可销售数sku单位
  public List<TmallCommoditySku> getTmallStockAllBySku() {

    List<TmallCommoditySku> obj = null;
    try {
      obj = new ArrayList<TmallCommoditySku>();
      tconnection.init();
      Query existsValueSql = new SimpleQuery(
          " SELECT CH.TMALL_COMMODITY_ID, "
              + "        CD.TMALL_SKU_ID, "
              + " (CASE WHEN (COMBINATION_AMOUNT IS NULL AND  (SELECT SUM(CCH.COMBINATION_AMOUNT) FROM C_COMMODITY_HEADER CCH WHERE CCH.ORIGINAL_COMMODITY_CODE = CH.COMMODITY_CODE ) IS NULL) THEN (S.STOCK_TMALL-S.ALLOCATED_TMALL) "
              + "       WHEN (COMBINATION_AMOUNT IS NULL AND  (SELECT SUM(CCH.COMBINATION_AMOUNT) FROM C_COMMODITY_HEADER CCH WHERE CCH.ORIGINAL_COMMODITY_CODE = CH.COMMODITY_CODE ) > 0) THEN (S.STOCK_TMALL-S.ALLOCATED_TMALL-(SELECT SUM(TSA.STOCK_QUANTITY-TSA.ALLOCATED_QUANTITY) FROM TMALL_STOCK_ALLOCATION TSA WHERE TSA.SKU_CODE IN (SELECT CCH.COMMODITY_CODE FROM C_COMMODITY_HEADER CCH WHERE CCH.ORIGINAL_COMMODITY_CODE = CH.COMMODITY_CODE ) )) "
              + "       ELSE (SELECT TSA.STOCK_QUANTITY-TSA.ALLOCATED_QUANTITY FROM TMALL_STOCK_ALLOCATION TSA WHERE TSA.SKU_CODE = CD.SKU_CODE ) /CH.COMBINATION_AMOUNT END)::NUMERIC(10,0) "
              + " AS NUM , " + " CD.SKU_CODE, " + " CASE WHEN CD.TMALL_USE_FLG = 0 THEN '不使用' ELSE '使用' END AS TMALL_USE_FLG, "
              + " S.STOCK_TOTAL,S.STOCK_QUANTITY,S.STOCK_TMALL,S.ALLOCATED_QUANTITY,S.ALLOCATED_TMALL,S.STOCK_THRESHOLD,S.SHARE_RATIO "
              + " FROM STOCK S " + " INNER JOIN C_COMMODITY_HEADER CH ON CH.COMMODITY_CODE = S.COMMODITY_CODE "
              + " INNER JOIN C_COMMODITY_DETAIL CD ON S.SKU_CODE=CD.SKU_CODE " + " WHERE CH.TMALL_COMMODITY_ID IS NOT NULL AND CD.TMALL_USE_FLG <> 0 "
              + " ORDER BY CH.COMMODITY_CODE ");
      obj = tconnection.executeGetListTmallStock(existsValueSql);
      if (obj == null || obj.size() <= 0) {
        return null;
      }
      tconnection.getConnection().commit();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (tconnection != null) {
        tconnection.dispose();
      }
    }
    return obj;
  }

  // 全量更新淘宝库存sku单位
  public void updateTmallStockBySku() {
    List<TmallCommoditySku> tscResList = new ArrayList<TmallCommoditySku>();
    List<TmallCommoditySku> tscList = getTmallStockAllBySku();
    if (tscList != null) {
      for (int i = 0; i < tscList.size(); i++) {
        TmallCommoditySku tcs = new TmallCommoditySku();
        tcs = (TmallCommoditySku) tscList.get(i);
        String resStr = updateStockByResult(tcs);
        if (!resStr.equals("same")) {
          // 淘宝上当前库存
          tcs.setItemPrice(resStr);
          tscResList.add(tcs);
        }
      }
    }
    if (tscResList != null && tscResList.size() > 0) {
      sendMailUpdateErrorSku(tscResList);
    }
  }

  // 判断库存数是否相等，相等则不更新
  public String updateStockByResult(TmallCommoditySku tcs) {

    // 获取url
    TmallConstant tc = DIContainer.get(TmallConstant.class.getSimpleName());
    LogOutPutRecord logtool = new LogOutPutRecord(tc.getApiLogpath(), "");
    String resStr = "same";

    // 当更新sku单位的库存时，else以商品为单位更新
    if (!StringUtil.isNullOrEmpty(tcs.getSkuId())) {
      TmallGetSku tgs = new TmallGetSku();
      TmallCommoditySku tcss = new TmallCommoditySku();
      tcss = tgs.getSku(tcs);
      if (tcss != null) {
        if (tcs.getQuantity() != null && tcss.getQuantity() != null) {
          if (!tcs.getQuantity().equals(tcss.getQuantity())) {
            resStr = tcss.getQuantity();
            // if (Long.parseLong(tcs.getQuantity()) >= 0) {
            // resStr = updateSkuStock(tcs);
            // logtool.log("taobao.item.quantity.update（商品库存全量更新成功，ID:" +
            // tcs.getSkuId() + "）", ifId);
            // } else {
            // logtool.log("taobao.item.quantity.update（该商品可销售数出现负数，请检查，ID:" +
            // tcs.getSkuId() + "）", ifId);
            // }
          } else {
            logtool.log("taobao.item.quantity.update（DB与Tmall库存一致无须更新，ID:" + tcs.getSkuId() + "）", ifId);
          }
          logtool.log(logtool.printHorizontalLine());
        }
      }
    } else {
      TmallSearchCommodityInfo tsci = new TmallSearchCommodityInfo();
      TmallCommodityHeader tch = new TmallCommodityHeader();
      tch = tsci.searchCommodity(tcs.getNumiid());
      if (tch != null) {
        if (tcs.getQuantity() != null && tch.getNum() != null) {
          if (!tcs.getQuantity().equals(tch.getNum())) {
            resStr = tch.getNum();
            // if (Long.parseLong(tcs.getQuantity()) >= 0) {
            // resStr = updateSkuStock(tcs);
            // logtool.log("taobao.item.quantity.update（商品库存全量更新成功，ID:" +
            // tcs.getNumiid() + "）", ifId);
            // } else {
            // logtool.log("taobao.item.quantity.update（该商品可销售数出现负数，请检查，ID:" +
            // tcs.getNumiid() + "）", ifId);
            // }
          } else {
            logtool.log("taobao.item.quantity.update（DB与Tmall库存一致无须更新，ID:" + tcs.getNumiid() + "）", ifId);
          }
          logtool.log(logtool.printHorizontalLine());
        }
      }
    }
    return resStr;
  }

  public void sendMailUpdateErrorSku(List<TmallCommoditySku> tscList) {
    MailInfo mailInfo = new MailInfo();
    StringBuffer sb = new StringBuffer();
    // sb.append("以下SKU库存更新失败:<BR><BR>");
    sb.append("以下商品库存官网与淘宝不一致:<BR><BR>");
    sb.append("<table border=\"1\" style=\"font:13px\">" +
    	       	"<tr>" +
    	       	"<td>行号</td>" +
    	       	"<td>SKU编号</td>" +
    	       	"<td>品店记录淘宝可销售库存数</td>" +
    	       	"<td>淘宝当前实际库存数</td>" +
    	       	//"<td>淘宝使用贩卖标志</td>" +
    	       	"<td>商品总库存数</td>" +
    	       	"<td>品店官网库存数</td>" +
    	       	"<td>品店官网引当数</td>" +
    	        "<td>品店记录淘宝库存数</td>" +
    	        "<td>品店记录淘宝引当数</td>" +
    	        "<td>安全库存数</td>" +
    	        "<td>库存比例</td>" +
    	       	"</tr>");
    for (int i = 0; i < tscList.size(); i++) {
      TmallCommoditySku tcs = new TmallCommoditySku();
      tcs = (TmallCommoditySku) tscList.get(i);
      sb.append("<tr>" +
      		      "<td>" + (i + 1) + "</td>" +
      				  "<td>" + tcs.getOuterId() + "</td>" +
      				  "<td>" + tcs.getQuantity() + "</td>" +
      				  "<td>" + tcs.getItemPrice() + "</td>" +
      				  //"<td>" + tcs.getProperties() + "</td>" +
      				  "<td>" + tcs.getStockTotal() + "</td>" +
      				  "<td>" + tcs.getStockQuantity() + "</td>" +
      				  "<td>" + tcs.getAllocatedQuantity() + "</td>" +
      				  "<td>" + tcs.getStockTmall() + "</td>" +
      				  "<td>" + tcs.getAllocatedTmall() + "</td>" +
      				  "<td>" + tcs.getStockThreshold() + "</td>" +
      				  "<td>" + tcs.getShareRatio() + "</td>" +
      				  "</tr>");
    }
    sb.append("</table>");

    mailInfo.setText(sb.toString());
    mailInfo.setSubject("【品店】商品库存与淘宝当前库存不一致警告");
    mailInfo.setSendDate(DateUtil.getSysdate());

    TmallSendMailConfig tmllMailSend = DIContainer.get(TmallSendMailConfig.class.getSimpleName());
    mailInfo.setFromInfo(tmllMailSend.getMailFromAddr(), tmllMailSend.getMailFromName());
    String[] mailToAddrArray = tmllMailSend.getMailToAddr().split(";");
    String[] mailToNameArray = tmllMailSend.getMailToName().split(";");
    for (int i = 0; i < mailToAddrArray.length; i++) {
      if (i >= mailToNameArray.length) {
        mailInfo.addToList(mailToAddrArray[i], mailToAddrArray[i]);
      } else {
        mailInfo.addToList(mailToAddrArray[i], mailToNameArray[i]);
      }
    }
    mailInfo.setContentType(MailInfo.CONTENT_TYPE_HTML);
    MailingService svc = ServiceLocator.getMailingService(ServiceLoginInfo.getInstance());
    svc.sendImmediate(mailInfo);
  }
}
