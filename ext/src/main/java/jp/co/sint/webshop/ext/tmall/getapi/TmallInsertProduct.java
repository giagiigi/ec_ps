package jp.co.sint.webshop.ext.tmall.getapi;

import java.io.File;

import jp.co.sint.webshop.ext.tmall.LogOutPutRecord;
import jp.co.sint.webshop.ext.tmall.TmallConstant;
import jp.co.sint.webshop.service.tmall.TmallCommodityHeader;
import jp.co.sint.webshop.service.tmall.TmallInsertProductService;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.StringUtil;

import org.apache.log4j.Logger;

import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.FileItem;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.Product;
import com.taobao.api.request.ProductAddRequest;
import com.taobao.api.response.ProductAddResponse;

public class TmallInsertProduct implements TmallInsertProductService {

  /**
   * 上传一个产品的信息 api:taobao.product.add
   */
  int ifId = 11;

  public String insertProduct(TmallCommodityHeader tch) {

    Logger logger = Logger.getLogger(this.getClass());

    String proId = "";

    // 获取url
    TmallConstant tc = DIContainer.get(TmallConstant.class.getSimpleName());

    LogOutPutRecord logtool = new LogOutPutRecord(tc.getApiLogpath(), "");

    TaobaoClient client = new DefaultTaobaoClient(tc.getReqUrl(), tc.getAppKey(), tc.getAppSercet());
    ProductAddRequest req = new ProductAddRequest();

    // 子类目
    if (!StringUtil.isNullOrEmpty(tch.getCid())) {
      req.setCid(Long.parseLong(tch.getCid()));
    }
    // 下列4个属性 如果是必须属性则必须传递
    // 关键属性
    req.setProps(tch.getKeyProps());
    // 非关键属性
    req.setBinds(tch.getNoKeyProps());
    // 销售属性
    req.setSaleProps(tch.getSaleProps());
    // 自定义属性
    req.setCustomerProps(tch.getCustomerProps());
    
    // 更新了最新包才能用
    //req.setPackingList("说明书:1;");
    req.setOuterId(tch.getOuterId());
    // 产品价格
    req.setPrice(tch.getPrice());
    // 产品图片
    FileItem fItem = new FileItem(new File(tch.getImageUrl()));
    req.setImage(fItem);
    // 产品名称
    req.setName(tch.getProName());
    // 设置主图
    req.setMajor(true);
    

    try {
      ProductAddResponse response = client.execute(req, tc.getSessionKey());
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
        logtool.log("taobao.product.add（产品上传失败）:" + tch.getTitle(), ifId);
        logtool.log(response.getErrorCode() + "_" + subMsg + "_" + msg, ifId);
        logtool.log(logtool.printHorizontalLine());
        // if(subMsg.equals("本类目此关键属性的产品已存在, 不可重复添加")){
        // return "exist";
        // }else{
        proId = "TmallReturn:" + subMsg;
        // }
      } else {
        Product pro = response.getProduct();
        if (pro.getProductId() != null) {
          logtool.log("taobao.product.add（产品上传成功）:" + tch.getTitle(), ifId);
          logtool.log(logtool.printHorizontalLine());
          // return pro.getProductId() + "";
          proId = pro.getProductId() + "";
        } else {
          // return null;
          proId = "TmallReturn:" + response.getSubMsg();
        }
      }
    } catch (Exception e1) {
      logtool.log(e1.getMessage(), ifId);
      logtool.log(logtool.printHorizontalLine());
    }
    return proId;
  }
}
