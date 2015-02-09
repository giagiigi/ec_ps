package jp.co.sint.webshop.ext.tmall.getapi;

import java.io.File;

import org.apache.log4j.Logger;

import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.FileItem;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.ItemImg;
import com.taobao.api.request.ItemImgUploadRequest;
import com.taobao.api.response.ItemImgUploadResponse;

import jp.co.sint.webshop.ext.tmall.LogOutPutRecord;
import jp.co.sint.webshop.ext.tmall.TmallConstant;
import jp.co.sint.webshop.service.tmall.TmallCommodityImg;
import jp.co.sint.webshop.service.tmall.TmallUploadImgService;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;

public class TmallCommodityImgUpload implements TmallUploadImgService{

  /**
   * 商品图片上传
   * api:taobao.item.img.upload
   */
  int ifId = 2;
  
  public TmallCommodityImg imageUpload(TmallCommodityImg tci) {

    Logger logger = Logger.getLogger(this.getClass());

    // 获取url
    TmallConstant tc = DIContainer.get(TmallConstant.class.getSimpleName());
    
    LogOutPutRecord logtool = new LogOutPutRecord(tc.getApiLogpath(), "");
    
    TmallCommodityImg reTci = null;

    TaobaoClient client = new DefaultTaobaoClient(tc.getReqUrl(), tc.getAppKey(), tc.getAppSercet());
    ItemImgUploadRequest req = new ItemImgUploadRequest();
    
    if (!StringUtil.isNullOrEmpty(tci.getImgId())) {
      req.setId(Long.parseLong(tci.getImgId()));
    }
    if (!StringUtil.isNullOrEmpty(tci.getTmallCommodityId())) {
      req.setNumIid(Long.parseLong(tci.getTmallCommodityId()));
    }
    FileItem fItem = new FileItem(new File(tci.getImageUrl()));
    req.setImage(fItem);
    req.setIsMajor(tci.isIs_major());
    
    if(!NumUtil.isNull(tci.getPosition())){
      req.setPosition(tci.getPosition());
    }
    
    try {
      ItemImgUploadResponse response = client.execute(req, tc.getSessionKey());
      if (!StringUtil.isNullOrEmpty(response.getErrorCode())) {
        String subMsg = "";
        String msg = "";
        if (!StringUtil.isNullOrEmpty(response.getSubMsg())) {
          subMsg = response.getSubMsg();
          if (subMsg.equals("商品图片不能超过5张！")) {
            reTci = new TmallCommodityImg();
            reTci.setImgId(subMsg);
            reTci.setReturnImgUrl(subMsg);
            return reTci;
          }
        } else {
          subMsg = "";
        }
        if (!StringUtil.isNullOrEmpty(response.getMsg())) {
          msg = response.getMsg();
        } else {
          msg = "";
        }
        logger.error(response.getErrorCode() + "_" + subMsg + "_" + msg);
        logtool.log("taobao.item.img.upload（商品图片上传失败）", ifId);
        logtool.log(response.getErrorCode() + "_" + subMsg + "_" + msg, ifId);
        logtool.log(logtool.printHorizontalLine());
        return reTci;
      } else {
        logtool.log("taobao.item.img.upload（商品图片上传成功）", ifId);
        logtool.log(logtool.printHorizontalLine());
        reTci = new TmallCommodityImg();
        ItemImg itemImg = response.getItemImg();
        reTci.setImgId(itemImg.getId().toString());
        reTci.setReturnImgUrl(itemImg.getUrl());
        return reTci;
      }
    } catch (Exception e1) {
      e1.printStackTrace();
    }
    return reTci;
  }
}
