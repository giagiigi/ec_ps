package jp.co.sint.webshop.ext.tmall.getapi;

import java.io.File;

import jp.co.sint.webshop.ext.tmall.LogOutPutRecord;
import jp.co.sint.webshop.ext.tmall.TmallConstant;
import jp.co.sint.webshop.service.tmall.TmallCommodityImgHistory;
import jp.co.sint.webshop.service.tmall.TmallUploadDescribeImgService;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.StringUtil;

import org.apache.log4j.Logger;

import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.FileItem;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.Picture;
import com.taobao.api.request.PictureUploadRequest;
import com.taobao.api.response.PictureUploadResponse;

public class TmallCommodityDescribeImgUpload implements TmallUploadDescribeImgService{

  /**
   * 商品图片上传
   * api:taobao.item.img.upload
   */
  int ifId = 2;
  
  public TmallCommodityImgHistory imageUpload(TmallCommodityImgHistory tcih) {

    Logger logger = Logger.getLogger(this.getClass());

    // 获取url
    TmallConstant tc = DIContainer.get(TmallConstant.class.getSimpleName());
    
    LogOutPutRecord logtool = new LogOutPutRecord(tc.getApiLogpath(), "");
    
    TmallCommodityImgHistory reTci = null;

    TaobaoClient client = new DefaultTaobaoClient(tc.getReqUrl(), tc.getAppKey(), tc.getAppSercet());
    PictureUploadRequest  req = new PictureUploadRequest();
    
    if (!StringUtil.isNullOrEmpty(tcih.getTitle())) {
      req.setTitle(tcih.getTitle());
    }
    FileItem fItem = new FileItem(new File(tcih.getImageUrl()));
    req.setImg(fItem);
    req.setImageInputTitle(tcih.getImageInputTitle());
    req.setPictureCategoryId(tcih.getPictureCategoryId());
    
    try {
      PictureUploadResponse response = client.execute(req, tc.getSessionKey());
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
        logtool.log("taobao.item.img.upload（商品描述图片上传失败）", ifId);
        logtool.log(response.getErrorCode() + "_" + subMsg + "_" + msg, ifId);
        logtool.log(logtool.printHorizontalLine());
        return reTci;
      } else {
        logtool.log("taobao.item.img.upload（商品描述图片上传成功）", ifId);
        logtool.log(logtool.printHorizontalLine());
        reTci = new TmallCommodityImgHistory();
        Picture picture = response.getPicture();
//        reTci.setImgId(itemImg.getId().toString());
        reTci.setReturnImgUrl(picture.getPicturePath());
        return reTci;
      }
    } catch (Exception e1) {
      e1.printStackTrace();
    }
    return reTci;
  }

}
