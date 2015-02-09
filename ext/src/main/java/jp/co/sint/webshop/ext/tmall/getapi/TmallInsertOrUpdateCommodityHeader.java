package jp.co.sint.webshop.ext.tmall.getapi;

import java.util.List;

import org.apache.log4j.Logger;

import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.Item;
import com.taobao.api.domain.Sku;
import com.taobao.api.request.ItemAddRequest;
import com.taobao.api.request.ItemGetRequest;
import com.taobao.api.request.ItemUpdateRequest;
import com.taobao.api.response.ItemAddResponse;
import com.taobao.api.response.ItemGetResponse;
import com.taobao.api.response.ItemUpdateResponse;

import jp.co.sint.webshop.ext.tmall.LogOutPutRecord;
import jp.co.sint.webshop.ext.tmall.TmallConstant;
import jp.co.sint.webshop.service.tmall.TmallCommodityHeader;
import jp.co.sint.webshop.service.tmall.TmallInsertOrUpdateCommodityHeaderService;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.StringUtil;

public class TmallInsertOrUpdateCommodityHeader implements TmallInsertOrUpdateCommodityHeaderService {

  /**
   * 商品Header添加or更新 api:taobao.item.add api:taobao.item.update
   */

  public String insertOrUpdateCommodityHeader(TmallCommodityHeader tch, String type) {

    Logger logger = Logger.getLogger(this.getClass());

    String numIid = "";

    // 获取url
    TmallConstant tc = DIContainer.get(TmallConstant.class.getSimpleName());

    LogOutPutRecord logtool = new LogOutPutRecord(tc.getApiLogpath(), "");

    TaobaoClient client = new DefaultTaobaoClient(tc.getReqUrl(), tc.getAppKey(), tc.getAppSercet());
    if (type.equals("INSERT")) {
      int ifId = 7;

      ItemAddRequest req = new ItemAddRequest();

      req.setType(tch.getType());
      req.setStuffStatus(tch.getStuff_status());
      if (!StringUtil.isNullOrEmpty(tch.getNum())) {
        req.setNum(Long.parseLong(tch.getNum()));
      }
      req.setPrice(tch.getPrice());
      req.setTitle(tch.getTitle());
      req.setDesc(tch.getDesc());
      req.setLocationState(tch.getLocationState());
      req.setLocationCity(tch.getLocationCity());
      req.setApproveStatus("instock");
      if (!StringUtil.isNullOrEmpty(tch.getCid())) {
        req.setCid(Long.parseLong(tch.getCid()));
      }

      if (!StringUtil.isNullOrEmpty(tch.getHasInvoice())) {
        req.setHasInvoice(Boolean.parseBoolean(tch.getHasInvoice()));
      }
      if (!StringUtil.isNullOrEmpty(tch.getAuctionPoint())) {
        req.setAuctionPoint(Long.parseLong(tch.getAuctionPoint()));
      }
      req.setOuterId(tch.getOuterNumiid());

      if (isHzp(tch.getCid())) {
        req.setProps(tch.getProps());
        // 自定义属性ID
        req.setInputPids(tch.getInputPids());
        // 自定义输入串
        req.setInputStr(tch.getInputStr());
      }

      req.setSellerCids(tch.getSellerCids());
      req.setSellPromise("1".equals(tch.getSellPromise())); // 退换货标志
      if (!StringUtil.isNullOrEmpty(tch.getProId()) && !tch.getProId().equals("tmallyhq")) {
        req.setProductId(Long.parseLong(tch.getProId()));
      }

      // 拍下减库存
      req.setSubStock(Long.parseLong(tch.getSubStock()));
      // 运费承担方
      req.setFreightPayer(tch.getFreightPayer());
      
      // 商品重量 运费模板用
      if(StringUtil.hasValue(tch.getWeight())) {
        req.setItemWeight(tch.getWeight());
      }
      // 运费模板ID
      if (!StringUtil.isNullOrEmpty(tch.getPostageId())) {
        req.setPostageId(Long.parseLong(tch.getPostageId()));
      }

      // 生产许可证号(产地：是国外的不能填这个（包含港澳台）)
      if (!StringUtil.isNullOrEmpty(tch.getFoodSecurityPrdLicenseNo())) {
        req.setFoodSecurityPrdLicenseNo(tch.getFoodSecurityPrdLicenseNo());
      }
      // 产品标准号(产地：是国外的不能填这个（包含港澳台）)
      if (!StringUtil.isNullOrEmpty(tch.getFoodSecurityDesignCode())) {
        req.setFoodSecurityDesignCode(tch.getFoodSecurityDesignCode());
      }
      // 厂名
      if (!StringUtil.isNullOrEmpty(tch.getFoodSecurityFactory())) {
        req.setFoodSecurityFactory(tch.getFoodSecurityFactory());
      }
      // 厂址
      if (!StringUtil.isNullOrEmpty(tch.getFoodSecurityFactorySite())) {
        req.setFoodSecurityFactorySite(tch.getFoodSecurityFactorySite());
      }
      // 厂家联系方式
      if (!StringUtil.isNullOrEmpty(tch.getFoodSecurityContact())) {
        req.setFoodSecurityContact(tch.getFoodSecurityContact());
      }
      // 配料表
      if (!StringUtil.isNullOrEmpty(tch.getFoodSecurityMix())) {
        req.setFoodSecurityMix(tch.getFoodSecurityMix());
      }
      // 储藏方法
      if (!StringUtil.isNullOrEmpty(tch.getFoodSecurityPlanStorage())) {
        req.setFoodSecurityPlanStorage(tch.getFoodSecurityPlanStorage());
      }
      // 保质期（必须是数字）
      if (!StringUtil.isNullOrEmpty(tch.getFoodSecurityPeriod())) {
        req.setFoodSecurityPeriod(tch.getFoodSecurityPeriod());
      }
      // 食品添加剂
      if (!StringUtil.isNullOrEmpty(tch.getFoodSecurityFoodAdditive())) {
        req.setFoodSecurityFoodAdditive(tch.getFoodSecurityFoodAdditive());
      }
      // 供货商
      if (!StringUtil.isNullOrEmpty(tch.getFoodSecuritySupplier())) {
        req.setFoodSecuritySupplier(tch.getFoodSecuritySupplier());
      }
      // 生产开始日期，格式必须为yyyy-MM-dd
      if (!StringUtil.isNullOrEmpty(tch.getFoodSecurityProductDateStart())) {
        req.setFoodSecurityProductDateStart(tch.getFoodSecurityProductDateStart());
      }
      // 生产结束日期,格式必须为yyyy-MM-dd
      if (!StringUtil.isNullOrEmpty(tch.getFoodSecurityProductDateEnd())) {
        req.setFoodSecurityProductDateEnd(tch.getFoodSecurityProductDateEnd());
      }
      // 进货开始日期，要在生产日期之后，格式必须为yyyy-MM-dd
      if (!StringUtil.isNullOrEmpty(tch.getFoodSecurityStockDateStart())) {
        req.setFoodSecurityStockDateStart(tch.getFoodSecurityStockDateStart());
      }
      // 进货结束日期，要在生产日期之后，格式必须为yyyy-MM-dd
      if (!StringUtil.isNullOrEmpty(tch.getFoodSecurityStockDateEnd())) {
        req.setFoodSecurityStockDateEnd(tch.getFoodSecurityStockDateEnd());
      }

      try {
        ItemAddResponse response = client.execute(req, tc.getSessionKey());
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
          logtool.log("taobao.item.add（商品Header新增失败）:" + tch.getTitle(), ifId);
          logtool.log(response.getErrorCode() + "_" + subMsg + "_" + msg, ifId);
          logtool.log(logtool.printHorizontalLine());
          numIid = "TmallReturn:" + subMsg;
          // return null;
        } else {
          logtool.log("taobao.item.add（商品Header新增成功）:" + tch.getTitle(), ifId);
          logtool.log(logtool.printHorizontalLine());
          Item item = response.getItem();
          return item.getNumIid() + "";
        }
      } catch (Exception e1) {
        e1.printStackTrace();
      }
    }
    if (type.equals("UPDATE")) {
      int ifId = 8;
      boolean isExistPic = true;
      String skuProperties = "";

      ItemUpdateRequest req = new ItemUpdateRequest();

      if (!StringUtil.isNullOrEmpty(tch.getNumiid())) {
        req.setNumIid(Long.parseLong(tch.getNumiid()));

        try {
          TaobaoClient clientPic = new DefaultTaobaoClient(tc.getReqUrl(), tc.getAppKey(), tc.getAppSercet());
          ItemGetRequest reqPic = new ItemGetRequest();
          reqPic.setFields("pic_url,sku.properties");
          reqPic.setNumIid(Long.parseLong(tch.getNumiid()));
          ItemGetResponse responsePic = clientPic.execute(reqPic, tc.getSessionKey());

          if (!StringUtil.isNullOrEmpty(responsePic.getErrorCode())) {
            String subMsg = "";
            String msg = "";
            if (!StringUtil.isNullOrEmpty(responsePic.getSubMsg())) {
              subMsg = responsePic.getSubMsg();
            } else {
              subMsg = "";
            }
            if (!StringUtil.isNullOrEmpty(responsePic.getMsg())) {
              msg = responsePic.getMsg();
            } else {
              msg = "";
            }
            logger.error(responsePic.getErrorCode() + "_" + subMsg + "_" + msg);
            logtool.log("taobao.item.update（商品Header获得商品主图失败）:" + tch.getTitle(), ifId);
            logtool.log(responsePic.getErrorCode() + "_" + subMsg + "_" + msg, ifId);
            logtool.log(logtool.printHorizontalLine());
            return "TmallReturn:" + subMsg;
          } else {
            logtool.log("taobao.item.update（商品Header获得商品主图成功）:" + tch.getTitle(), ifId);
            Item item = responsePic.getItem();
            // 销售属性价格更新 20141027start
            if (item.getSkus() != null){
              for (int y = 0 ; y <item.getSkus().size();y++){
                Sku sku = (Sku)item.getSkus().get(0);
                skuProperties = sku.getProperties();
              }
            }
            // 销售属性价格更新 20141027end
            if (StringUtil.isNullOrEmpty(item.getPicUrl())) {
              isExistPic = false;
            }
          }
        } catch (Exception e) {
          e.printStackTrace();
          logtool.log("taobao.item.update（商品Header获得商品主图出现异常）:" + e.getMessage());
        }
      }

      // 上架时间
      if (tch.getListTime() != null) {
        if (isExistPic) {
          req.setListTime(tch.getListTime());
          req.setApproveStatus("onsale");
        }
      } else {

        boolean flag = true;
        // 商品名称
        if (!StringUtil.isNullOrEmpty(tch.getTitle())) {
          req.setTitle(tch.getTitle());
        }

        // 商品价格
        if (!StringUtil.isNullOrEmpty(tch.getPrice())) {
          req.setPrice(tch.getPrice());
        }
        // 销售属性价格更新 20141027start
        if (StringUtil.hasValue(skuProperties)){
          req.setSkuProperties(skuProperties);
          req.setSkuPrices(tch.getPrice());
        }
        // 销售属性价格更新 20141027end
        
        // 判断是否是化妆品类目
        // if (!StringUtil.isNullOrEmpty(tch.getCid())) {
        // flag = isHzp(tch.getCid());
        // }
        //
        // if (flag) {

        if (!StringUtil.isNullOrEmpty(tch.getCid())) {
          // req.setCid(Long.parseLong(tch.getCid()));
        }

        if (!StringUtil.isNullOrEmpty(tch.getNum())) {
          if (Long.parseLong(tch.getNum()) <= 0L || !isExistPic) {
            req.setApproveStatus("instock");
          }
        }
        // if (StringUtil.isNullOrEmpty(tch.getSkuPro())) {
        // req.setProps(tch.getProps());
        // } else {
        // req.setProps(tch.getProps() + ";" + tch.getSkuPro());
        // }

        // // 自定义属性ID
        // if (!StringUtil.isNullOrEmpty(tch.getInputPids())) {
        // req.setInputPids(tch.getInputPids());
        // }
        //
        // // 自定义输入串
        // if (!StringUtil.isNullOrEmpty(tch.getInputStr())) {
        // req.setInputStr(tch.getInputStr());
        // }

        // 拍下减库存
        if (!StringUtil.isNullOrEmpty(tch.getSubStock())) {
          req.setSubStock(Long.parseLong(tch.getSubStock()));
        }

        // 运费承担方
        if (Long.parseLong(tch.getNumiid()) == 15275671216L) {
          req.setFreightPayer("seller");
        }
        // 商品重量 运费模板用
        if(StringUtil.hasValue(tch.getWeight())) {
         // req.setItemWeight(tch.getWeight());
        }
        // 运费模板ID
        if (!StringUtil.isNullOrEmpty(tch.getPostageId())) {
         //  req.setPostageId(Long.parseLong(tch.getPostageId()));
        }
        // 产品ID
        if (!StringUtil.isNullOrEmpty(tch.getProId()) && !tch.getProId().equals("tmallyhq")) {
          // req.setProductId(Long.parseLong(tch.getProId()));
        }

        // 生产许可证号(产地：是国外的不能填这个（包含港澳台）)
        if (!StringUtil.isNullOrEmpty(tch.getFoodSecurityPrdLicenseNo())) {
          req.setFoodSecurityPrdLicenseNo(tch.getFoodSecurityPrdLicenseNo());
        }
        // 产品标准号(产地：是国外的不能填这个（包含港澳台）)
        if (!StringUtil.isNullOrEmpty(tch.getFoodSecurityDesignCode())) {
          req.setFoodSecurityDesignCode(tch.getFoodSecurityDesignCode());
        }
        // 厂名
        if (!StringUtil.isNullOrEmpty(tch.getFoodSecurityFactory())) {
          req.setFoodSecurityFactory(tch.getFoodSecurityFactory());
        }
        // 厂址
        if (!StringUtil.isNullOrEmpty(tch.getFoodSecurityFactorySite())) {
          req.setFoodSecurityFactorySite(tch.getFoodSecurityFactorySite());
        }
        // 厂家联系方式
        if (!StringUtil.isNullOrEmpty(tch.getFoodSecurityContact())) {
          req.setFoodSecurityContact(tch.getFoodSecurityContact());
        }
        // 配料表
        if (!StringUtil.isNullOrEmpty(tch.getFoodSecurityMix())) {
          req.setFoodSecurityMix(tch.getFoodSecurityMix());
        }
        // 储藏方法
        if (!StringUtil.isNullOrEmpty(tch.getFoodSecurityPlanStorage())) {
          req.setFoodSecurityPlanStorage(tch.getFoodSecurityPlanStorage());
        }
        // 保质期（必须是数字）
        if (!StringUtil.isNullOrEmpty(tch.getFoodSecurityPeriod())) {
          req.setFoodSecurityPeriod(tch.getFoodSecurityPeriod());
        }
        // 食品添加剂
        if (!StringUtil.isNullOrEmpty(tch.getFoodSecurityFoodAdditive())) {
          req.setFoodSecurityFoodAdditive(tch.getFoodSecurityFoodAdditive());
        }
        // 供货商
        if (!StringUtil.isNullOrEmpty(tch.getFoodSecuritySupplier())) {
          req.setFoodSecuritySupplier(tch.getFoodSecuritySupplier());
        }
        // 生产开始日期，格式必须为yyyy-MM-dd
        if (!StringUtil.isNullOrEmpty(tch.getFoodSecurityProductDateStart())) {
          req.setFoodSecurityProductDateStart(tch.getFoodSecurityProductDateStart());
        }
        // 生产结束日期,格式必须为yyyy-MM-dd
        if (!StringUtil.isNullOrEmpty(tch.getFoodSecurityProductDateEnd())) {
          req.setFoodSecurityProductDateEnd(tch.getFoodSecurityProductDateEnd());
        }
        // 进货开始日期，要在生产日期之后，格式必须为yyyy-MM-dd
        if (!StringUtil.isNullOrEmpty(tch.getFoodSecurityStockDateStart())) {
          req.setFoodSecurityStockDateStart(tch.getFoodSecurityStockDateStart());
        }
        // 进货结束日期，要在生产日期之后，格式必须为yyyy-MM-dd
        if (!StringUtil.isNullOrEmpty(tch.getFoodSecurityStockDateEnd())) {
          req.setFoodSecurityStockDateEnd(tch.getFoodSecurityStockDateEnd());
        }
        // }

      }

      try {
        ItemUpdateResponse response = client.execute(req, tc.getSessionKey());
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
          if (!StringUtil.isNullOrEmpty(response.getSubCode())
              && response.getSubCode().equals("isv.item-update-service-error:IC_CHECKSTEP_SPU_NOT_EXIST-tmall")) {
            return "IC_CHECKSTEP_SPU_NOT_EXIST";
          } else {
            logger.error(response.getErrorCode() + "_" + subMsg + "_" + msg);
            logtool.log("taobao.item.update（商品Header更新失败）:" + tch.getTitle(), ifId);
            logtool.log(response.getErrorCode() + "_" + subMsg + "_" + msg, ifId);
            logtool.log(logtool.printHorizontalLine());
          }
          numIid = "TmallReturn:" + subMsg;
        } else {
          logtool.log("taobao.item.update（商品Header更新成功）:" + tch.getTitle(), ifId);
          logtool.log(logtool.printHorizontalLine());
          Item item = response.getItem();
          return item.getNumIid() + "";
        }
      } catch (Exception e1) {
        e1.printStackTrace();
      }
    }
    if (type.equals("DESC")) {
      int ifId = 8;
      ItemUpdateRequest req = new ItemUpdateRequest();

      if (!StringUtil.isNullOrEmpty(tch.getNumiid())) {
        req.setNumIid(Long.parseLong(tch.getNumiid()));
      }
      if (!StringUtil.isNullOrEmpty(tch.getDesc())) {
        req.setDesc(tch.getDesc());
      }

      try {
        ItemUpdateResponse response = client.execute(req, tc.getSessionKey());
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
          if (!StringUtil.isNullOrEmpty(response.getSubCode())
              && response.getSubCode().equals("isv.item-update-service-error:IC_CHECKSTEP_SPU_NOT_EXIST-tmall")) {
            return "isv.item-update-service-error:IC_CHECKSTEP_SPU_NOT_EXIST-tmall";
          } else {
            logger.error(response.getErrorCode() + "_" + subMsg + "_" + msg);
            logtool.log("taobao.item.update（商品描述更新失败）:" + tch.getTitle(), ifId);
            logtool.log(response.getErrorCode() + "_" + subMsg + "_" + msg, ifId);
            logtool.log(logtool.printHorizontalLine());
          }
          numIid = "TmallReturn:" + subMsg;
        } else {
          logtool.log("taobao.item.update（商品描述更新成功）:" + tch.getTitle(), ifId);
          logtool.log(logtool.printHorizontalLine());
          Item item = response.getItem();
          return item.getNumIid() + "_SUCCESS";
        }
      } catch (Exception e1) {
        e1.printStackTrace();
      }
    }
    return numIid;
  }

  // 判断商品类目是否属于化妆品类目
  public boolean isHzp(String cid) {
    boolean flag = true;
    // 化妆品子类目
    String[] str = {
        "50011990", "50011977", "50011978", "50011979", "50011980", "50011981", "50011982", "50011986", "50011983", "50011987",
        "50011994", "50011995", "50011996", "50011997", "50011993", "50011988", "50011998", "50011991", "50011999", "50012000",
        "50012001", "50012002", "50012003", "50012004", "50012005", "50012006",
    };
    for (int i = 0; i < str.length; i++) {
      if (cid.equals(str[i])) {
        flag = false;
      }
    }

    return flag;
  }
}
