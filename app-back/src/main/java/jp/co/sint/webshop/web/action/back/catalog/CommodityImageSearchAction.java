package jp.co.sint.webshop.web.action.back.catalog;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.data.dao.CommodityHeaderDao;
import jp.co.sint.webshop.data.domain.JdUpLoadType;
import jp.co.sint.webshop.data.dto.CommodityHeader;
import jp.co.sint.webshop.data.dto.ImageUploadHistory;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.catalog.CommodityImageSearchCondition;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.FileUtil;
import jp.co.sint.webshop.utility.ImageUploadConfig;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.catalog.CommodityImageBean;
import jp.co.sint.webshop.web.bean.back.catalog.CommodityImageBean.CommodityListResult;
import jp.co.sint.webshop.web.bean.back.catalog.CommodityImageBean.CommodityListResult.ImgInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.webutility.PagerUtil;

/**
 * U1040110:商品マスタのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class CommodityImageSearchAction extends CommodityImageBaseAction {
  
  private CommodityImageSearchCondition condition;
  
  protected CommodityImageSearchCondition getCondition() {
    return PagerUtil.createSearchCondition(getRequestParameter(), condition);
  }

  protected CommodityImageSearchCondition getSearchCondition() {
    return condition;
  }
  
  /**
   * beanのcreateAttributeを実行するかどうかの設定
   * 
   * @return true - 実行する false-実行しない
   */
  @Override
  public boolean isCallCreateAttribute() {
    return true;
  }

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return super.authorize();
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    return true;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    CommodityImageBean bean = getBean();
    
    // 検索条件の作成
    condition = new CommodityImageSearchCondition();
    setCondition(condition);
    
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    SearchResult<ImageUploadHistory> resultList = service.getCommodityImgUploadInfoList(condition);
    
    bean.setPagerValue(PagerUtil.createValue(resultList));
    List<CommodityListResult> commodityListResult = new ArrayList<CommodityListResult>();
    
    List<ImageUploadHistory> dtoList = resultList.getRows();
    if (dtoList.size() < 1) {
      bean.setCommodityListResult(commodityListResult);
      addErrorMessage(WebMessage.get(ActionErrorMessage.SEARCH_RESULT_NOT_FOUND));
      setRequestBean(bean);

      return BackActionResult.RESULT_SUCCESS;
    }
    
    for (ImageUploadHistory dto : dtoList) {
      CommodityListResult detail = new CommodityListResult();
      detail.setSKUCode(dto.getSkuCode());
      String imageName = dto.getUploadCommodityImg().substring(0, dto.getUploadCommodityImg().lastIndexOf("."));
      
      boolean isRepSku = false;
      CommodityHeaderDao commHeaderDao = DIContainer.getDao(CommodityHeaderDao.class);
      isRepSku = commHeaderDao.isRepresentSkuCode(dto.getShopCode(), dto.getSkuCode());
      if (!isRepSku) {
        detail.setMainSKU("否");
        detail.setCommodityName("");
      } else {
        detail.setMainSKU("是");
        CommodityHeader ch = commHeaderDao.load(getLoginInfo().getShopCode(), dto.getCommodityCode());
        detail.setCommodityName(ch.getCommodityName());
        isRepSku = true;
      }
      
      //判断图片本地处理是否成功,如果没成功,直接进入下一次循环
      if (dto.getLocalOperFlg() != 1) {
        continue;
      }
      
      List<String> localImgList = getImgFileList(dto.getSkuCode());
      List<ImgInfo> EC1ImgList = new ArrayList<ImgInfo>();
      List<ImgInfo> EC2ImgList = new ArrayList<ImgInfo>();
      if (localImgList != null) {
        detail.setLocalCommodityImgList(localImgList);
      }
      
      ImageUploadConfig config = DIContainer.getImageUploadConfig();
      if (dto.getEc1UploadFlg() != 0 && config != null) {
        List<String> ftpInfoList = config.getFtpInfoList();
        String EC1Path = "";
       
        if (ftpInfoList.size() > 0) {
          String tmp = ftpInfoList.get(0);
          String [] ftpInfo = tmp.split(",");
          if (ftpInfo.length > 7) {
            EC1Path = ftpInfo[7];
          }
        }
        // 取得修改sku图片尺寸的参数列表
        List<String> skuImgList = config.getSkuImgList();
        if (skuImgList.size() > 0) {
          for (String tmp : skuImgList) {
            ImgInfo img = new ImgInfo();
            String[] skuImgInfo = tmp.split(",");
            if (skuImgInfo.length > 2 && !StringUtil.isNullOrEmpty(EC1Path)) {
              img.setImgName(imageName + skuImgInfo[0] + ".jpg");
              img.setImgURL(EC1Path + imageName + skuImgInfo[0] + ".jpg");
              EC1ImgList.add(img);
            }
            // del by lc 2012-04-11 start for: 修复图片管理时， 出现重复图片的bug
//            if (false && skuImgInfo.length > 2 && !StringUtil.isNullOrEmpty(EC1Path)) {
//              img.setImgName(dto.getCommodityCode() + skuImgInfo[0] + ".jpg");
//              img.setImgURL(EC1Path + dto.getCommodityCode() + skuImgInfo[0] + ".jpg");
//              EC1ImgList.add(img);
//            }
            // del by lc 2012-04-11 end
          }
        }
        detail.setECImgList1(EC1ImgList);
      }
      if (dto.getEc2UploadFlg() != 0 && config != null) {
        List<String> ftpInfoList = config.getFtpInfoList();
        String EC2Path = "";
        if (ftpInfoList.size() > 1) {
          String tmp = ftpInfoList.get(1);
          String [] ftpInfo = tmp.split(",");
          if (ftpInfo.length > 7) {
            EC2Path = ftpInfo[7];
          }
        }
        // 取得修改sku图片尺寸的参数列表
        List<String> skuImgList = config.getSkuImgList();
        if (skuImgList.size() > 0) {
          for (String tmp : skuImgList) {
            ImgInfo img = new ImgInfo();
            String[] skuImgInfo = tmp.split(",");
            if (skuImgInfo.length > 2 && !StringUtil.isNullOrEmpty(EC2Path)) {
              img.setImgName(imageName + skuImgInfo[0] + ".jpg");
              img.setImgURL(EC2Path + imageName + skuImgInfo[0] + ".jpg");
              EC2ImgList.add(img);
            }
            // del by lc 2012-04-11 start for: 修复图片管理时， 出现重复图片的bug
//            if (isRepSku && skuImgInfo.length > 2 && !StringUtil.isNullOrEmpty(EC2Path)) {
//              img.setImgName(dto.getCommodityCode() + skuImgInfo[0] + ".jpg");
//              img.setImgURL(EC2Path + dto.getCommodityCode() + skuImgInfo[0] + ".jpg");
//              EC2ImgList.add(img);
//            }
            // del by lc 2012-04-11 end
          }
        }
        detail.setECImgList2(EC2ImgList); 
      }

      // 代表sku可能会有商品图片
      if (isRepSku) {
        List<String> commodityImgList = getImgFileList(dto.getCommodityCode());
        if (commodityImgList != null) {
          detail.setCommodityImgList(commodityImgList);
        }
      }
      
      if (dto.getTmallUploadFlg() != 0) {
        detail.setTmallImg(dto.getTmallImgUrl());
      } else {
        detail.setTmallImg("");
      }
      
      // 2014/05/06 京东WBS对应 ob_李先超 add start
      if (JdUpLoadType.UPLOAD.longValue().equals(dto.getJdUploadFlg())) {
        detail.setJdImg(dto.getJdImgUrl());
      } else {
        detail.setJdImg("");
      }
      // 2014/05/06 京东WBS对应 ob_李先超 add end
      
      commodityListResult.add(detail);
    }
    bean.setCommodityListResult(commodityListResult);
    
    setRequestBean(bean);

    return BackActionResult.RESULT_SUCCESS;
  }
  
  private List<String> getImgFileList(String fileNameLike) {
    List<String> localImgList = new ArrayList<String>();
    List<File> fileList = new ArrayList<File>();
    ImageUploadConfig config = DIContainer.getImageUploadConfig();
    FileUtil.findFilesByFileName(config.getUploadImgPath(), fileNameLike, fileList);
    
    if (fileList.size() < 1) {
      return null;
    }
    
    for (File file : fileList) {
      localImgList.add(file.getName().split("\\.")[0]);
    }
    
    return localImgList;
  }
  
  private void setCondition(CommodityImageSearchCondition condition) {
    PagerUtil.createSearchCondition(getRequestParameter(), condition);
    condition.setShopCode(getLoginInfo().getShopCode());
    condition.setSearchUploadTime(getBean().getSearchUploadDate());
    
    String[] skuList = getBean().getSearchSKUList().split("\r\n");
    condition.setSkuList(skuList);
  }
  
  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {
    super.prerender();
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return "图片管理";
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3104100002";
  }

}
