package jp.co.sint.webshop.web.action.back.catalog;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.api.jd.JdApiConfig;
import jp.co.sint.webshop.data.dao.CCommodityHeaderDao;
import jp.co.sint.webshop.data.dao.CommodityDescribeDao;
import jp.co.sint.webshop.data.dao.DescribeImgHistoryDao;
import jp.co.sint.webshop.data.domain.JdUseFlg;
import jp.co.sint.webshop.data.dto.CCommodityHeader;
import jp.co.sint.webshop.data.dto.CommodityDescribe;
import jp.co.sint.webshop.data.dto.CommodityHeader;
import jp.co.sint.webshop.data.dto.CyncroResult;
import jp.co.sint.webshop.data.dto.DescribeImgHistory;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.JdService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.tmall.TmallCommodityHeader;
import jp.co.sint.webshop.service.tmall.TmallCommodityImgHistory;
import jp.co.sint.webshop.service.tmall.TmallService;
import jp.co.sint.webshop.utility.CommodityDescImageUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.ImageUploadConfig;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.validation.BeanValidator;
import jp.co.sint.webshop.validation.ValidationSummary;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.catalog.TmallRelatedDescribeBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.webutility.WebConstantCode;
import org.apache.log4j.Logger;

public class TmallRelatedDescribeUpdateAction extends WebBackAction<TmallRelatedDescribeBean> {

  ImageUploadConfig iuConfig = DIContainer.getImageUploadConfig();

  private String localImgUrl1Cn = iuConfig.getLocalImgUrl1Cn();

  private String localImgUrl1En = iuConfig.getLocalImgUrl1En();

  private String localImgUrl1Jp = iuConfig.getLocalImgUrl1Jp();

  private String tmallImgUrl = iuConfig.getTmallImgUrl();
  
  // 2014/05/05 京东WBS对应 ob_卢 add start
  private String jdImgUrl = iuConfig.getJdImgUrl();
  
  private JdApiConfig config = DIContainer.getJdApiConfig();
  // 2014/05/05 京东WBS对应 ob_卢 add end

  private String localMomentUrl = iuConfig.getLocalMomentUrl();

  private List<String> localImgUrl2ListCn = iuConfig.getLocalImgUrl2ListCn();

  private List<String> localImgUrl2ListEn = iuConfig.getLocalImgUrl2ListEn();

  private List<String> localImgUrl2ListJp = iuConfig.getLocalImgUrl2ListJp();

  @Override
  public boolean authorize() {
    boolean authorization;

    // モードに関わらず、カテゴリの参照権限があれば表示
    BackLoginInfo login = getLoginInfo();
    authorization = Permission.CATEGORY_READ.isGranted(login) || Permission.COMMODITY_READ.isGranted(login);

    if (login.isShop() && getRequestParameter().getPathArgs().length > 0) {

      String shop = getRequestParameter().getPathArgs()[0];
      if (StringUtil.isNullOrEmpty(shop)) {
        return false;
      }

      authorization &= shop.equals(login.getShopCode());
    }

    return authorization;

  }

  @Override
  public boolean validate() {

    ValidationSummary summary = BeanValidator.validate(getBean());
    if (summary.hasError()) {
      getDisplayMessage().getErrors().addAll(summary.getErrorMessages());
      return false;
    }

    return true;
  }

  /**
   * Action
   * 
   * @return Action
   */
  public String getActionName() {
    return "商品描述更新";
  }

  public String getOperationCode() {
    return "8110000002";
  }

  @Override
  public WebActionResult callService() {
    Logger logger = Logger.getLogger(this.getClass());
    String[] param = getRequestParameter().getPathArgs();
    // 取得语言种类
    String type = param[1];
    TmallRelatedDescribeBean bean = getBean();
    // 描述表更新插入标志
    boolean flag = false;
    // 2014/05/09 京东WBS对应 ob_卢 add start
    // 京东描述更新标志
    boolean jdDescflag = false;
    // 2014/05/09 京东WBS对应 ob_卢 add end
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    DescribeImgHistoryDao historyDao = DIContainer.getDao(DescribeImgHistoryDao.class);
    CommodityDescribeDao dao = DIContainer.getDao(CommodityDescribeDao.class);

    CommodityDescribe describe = dao.load("00000000", bean.getCommodityCode());
    CommodityHeader header = service.getCommodityHeader("00000000", bean.getCommodityCode());
    if (header == null) {
      setRequestBean(bean);
      addErrorMessage("该商品不存在");
      return BackActionResult.RESULT_SUCCESS;
    }
    if (describe == null) {
      flag = true;
      describe = new CommodityDescribe();
      describe.setShopCode("00000000");
      describe.setCommodityCode(bean.getCommodityCode());
    }

    // 商品中文描述
    if (type.equals("cn")) {
      String imgStrOld = bean.getDecribeCn();
      // 图片转换以及上传
      imgStrOld = changeStrImg(imgStrOld, historyDao, service, bean.getCommodityCode(), "cn");
      if (imgStrOld.equals("error1")) {
        setRequestBean(bean);
        addErrorMessage("图片转换失败");
        return BackActionResult.RESULT_SUCCESS;
      }
      if (imgStrOld.equals("error2")) {
        setRequestBean(bean);
        addErrorMessage("图片上传服务器失败");
        return BackActionResult.RESULT_SUCCESS;
      }
      describe.setDecribeCn(imgStrOld);
      header.setCommodityDescriptionPc(imgStrOld);
      if (imgStrOld.length() > 25000) {
        setRequestBean(bean);
        addErrorMessage("中文描述超过25000个字符，描述更新失败！");
        return BackActionResult.RESULT_SUCCESS;
      }

      // 商品英文描述
    } else if (type.equals("en")) {
      String imgStrOld = bean.getDecribeEn();
      // 图片转换以及上传
      imgStrOld = changeStrImg(imgStrOld, historyDao, service, bean.getCommodityCode(), "en");
      if (imgStrOld.equals("error1")) {
        setRequestBean(bean);
        addErrorMessage("图片转换失败");
        return BackActionResult.RESULT_SUCCESS;
      }
      if (imgStrOld.equals("error2")) {
        setRequestBean(bean);
        addErrorMessage("图片上传服务器失败");
        return BackActionResult.RESULT_SUCCESS;
      }
      describe.setDecribeEn(imgStrOld);
      header.setCommodityDescriptionPcEn(imgStrOld);
      if (imgStrOld.length() > 25000) {
        setRequestBean(bean);
        addErrorMessage("英文描述超过25000个字符，描述更新失败！");
        return BackActionResult.RESULT_SUCCESS;
      }

      // 商品日文描述
    } else if (type.equals("jp")) {
      String imgStrOld = bean.getDecribeJp();
      // 图片转换以及上传
      imgStrOld = changeStrImg(imgStrOld, historyDao, service, bean.getCommodityCode(), "jp");
      if (imgStrOld.equals("error1")) {
        setRequestBean(bean);
        addErrorMessage("图片转换失败");
        return BackActionResult.RESULT_SUCCESS;
      }
      if (imgStrOld.equals("error2")) {
        setRequestBean(bean);
        addErrorMessage("图片上传服务器失败");
        return BackActionResult.RESULT_SUCCESS;
      }
      describe.setDecribeJp(imgStrOld);
      header.setCommodityDescriptionPcJp(imgStrOld);
      if (imgStrOld.length() > 25000) {
        setRequestBean(bean);
        addErrorMessage("日文描述超过25000个字符，描述更新失败！");
        return BackActionResult.RESULT_SUCCESS;
      }

      // 商品Tmall描述
    } else if (type.equals("tmall")) {
      // 取得图片URL
      String imgUrl = tmallImgUrl;

      String imgStrOld = bean.getDecribeTmall();
      // 获得描述中的所有图片src字符串
      List<String> imgListSrc = CommodityDescImageUtil.getImg(imgStrOld);
      // 获得src属性中的url字符串
      List<String> imgListReal = new ArrayList<String>();
      for (int i = 0; i < imgListSrc.size(); i++) {
        int begin = imgListSrc.get(i).indexOf("\"") + 1;
        int end = imgListSrc.get(i).lastIndexOf("\"");
        imgListReal.add(imgListSrc.get(i).substring(begin, end));
      }
      String newStrEnd = "";
      TmallService tmallservice = ServiceLocator.getTmallService(getLoginInfo());

      for (String str : imgListReal) {
        // 取得商品tmall图片上传总数
        Long historyCount = service.getAllDescribeImgHistory(bean.getCommodityCode(), type);

        if (str.contains(";base64,")) {// base64 图片的处理
          int location = str.indexOf(";base64,");
          newStrEnd = str.substring(location + 8);
          String avalibleUrl1 = changeToNewUrlBase64(newStrEnd, historyCount, bean.getCommodityCode(), "tmall");
          if (avalibleUrl1.equals("error")) {
            setRequestBean(bean);
            addErrorMessage("图片转换失败");
            return BackActionResult.RESULT_SUCCESS;
          }

          DescribeImgHistory history = new DescribeImgHistory();
          String imgName = bean.getCommodityCode() + "_" + type + "_" + historyCount + ".jpg";
          avalibleUrl1 = localMomentUrl + imgName;

          TmallCommodityImgHistory tmallHistory = new TmallCommodityImgHistory();
          tmallHistory.setImageUrl(avalibleUrl1);
          tmallHistory.setPictureCategoryId(10787116787242234L);
          tmallHistory.setImageInputTitle(imgName);

          // 图片上传淘宝
          TmallCommodityImgHistory tmallHistoryReturn = tmallservice.describeImageUpload(tmallHistory);
          if (tmallHistoryReturn == null) {
            setRequestBean(bean);
            addErrorMessage("图片上传淘宝失败");
            return BackActionResult.RESULT_SUCCESS;
          }

          history.setCommodityCode(bean.getCommodityCode());
          history.setLang(type);
          history.setShopCode("00000000");
          history.setDownloadImgUrl("");
          history.setImgName(imgName);
          history.setTmallReturnUrl(tmallHistoryReturn.getReturnImgUrl());
          // 存入履历表
          historyDao.insert(history);
          imgStrOld = imgStrOld.replace(str, tmallHistoryReturn.getReturnImgUrl());

        } else {// download 图片的处理
          String avalibleUrl2 = changeToNewUrl(str, historyCount, bean.getCommodityCode(), "tmall");
          if (avalibleUrl2.equals("error")) {
            setRequestBean(bean);
            addErrorMessage("图片上传服务器失败");
            return BackActionResult.RESULT_SUCCESS;
          }

          if (!str.contains(imgUrl) && !str.contains("taobao")) {
            DescribeImgHistory history = new DescribeImgHistory();
            String imgName = bean.getCommodityCode() + "_" + type + "_" + historyCount + ".jpg";
            avalibleUrl2 = localMomentUrl + imgName;

            TmallCommodityImgHistory tmallHistory = new TmallCommodityImgHistory();
            tmallHistory.setImageUrl(avalibleUrl2);
            tmallHistory.setPictureCategoryId(10787116787242234L);
            tmallHistory.setImageInputTitle(imgName);
            TmallCommodityImgHistory tmallHistoryReturn = tmallservice.describeImageUpload(tmallHistory);
            if (tmallHistoryReturn == null) {
              setRequestBean(bean);
              addErrorMessage("图片上传淘宝失败");
              return BackActionResult.RESULT_SUCCESS;
            }

            history.setCommodityCode(bean.getCommodityCode());
            history.setLang(type);
            history.setShopCode("00000000");
            history.setDownloadImgUrl(str);
            history.setImgName(imgName);
            history.setTmallReturnUrl(tmallHistoryReturn.getReturnImgUrl());
            historyDao.insert(history);
            imgStrOld = imgStrOld.replace(str, tmallHistoryReturn.getReturnImgUrl());
          }
        }
      }
      
      if (imgStrOld.length() > 25000) {
        setRequestBean(bean);
        addErrorMessage("Tmall描述超过25000个字符，描述更新失败！");
        return BackActionResult.RESULT_SUCCESS;
      }

      // tmall描述保存
      describe.setDecribeTmall(imgStrOld);
      if (StringUtil.isNullOrEmpty(getBean().getDecribeTmall())) {
        setRequestBean(bean);
        addErrorMessage("Tmall商品描述不能低于5个字符！");
        return BackActionResult.RESULT_SUCCESS;
      }
      // 更新tmall描述
      TmallCommodityHeader tcheader = new TmallCommodityHeader();
      tcheader.setNumiid(bean.getTmallCommodityId().toString());
      tcheader.setDesc(imgStrOld);
      String result = tmallservice.insertOrUpdateCommodityHeader(tcheader, "DESC");
      if (!result.equals(bean.getTmallCommodityId() + "_SUCCESS")) {
        setRequestBean(bean);
        addErrorMessage("描述上传淘宝失败:" + result);
        return BackActionResult.RESULT_SUCCESS;
      }
      String result1 = tmallservice.commodityDelisting(bean.getTmallCommodityId().toString());
      if (result1 == null) {
        setRequestBean(bean);
        logger.error("商品下架失败");
      }
    }
    
    // 2014/05/05 京东WBS对应 ob_卢 add start
    // 商品jd描述
    else if (type.equals("jd")) {
     // 取得图片URL
     String imgUrl = jdImgUrl;

     String imgStrOld = bean.getDecribeJd();
     // 获得描述中的所有图片src字符串
     List<String> imgListSrc = CommodityDescImageUtil.getImg(imgStrOld);
     // 获得src属性中的url字符串
     List<String> imgListReal = new ArrayList<String>();
     for (int i = 0; i < imgListSrc.size(); i++) {
       int begin = imgListSrc.get(i).indexOf("\"") + 1;
       int end = imgListSrc.get(i).lastIndexOf("\"");
       imgListReal.add(imgListSrc.get(i).substring(begin, end));
     }
     String newStrEnd = "";
     JdService jdservice = ServiceLocator.getJdService(getLoginInfo());

     for (String str : imgListReal) {
       // 取得商品jd图片上传总数
       Long historyCount = service.getAllDescribeImgHistory(bean.getCommodityCode(), type);

       if (str.contains(";base64,")) {// base64 图片的处理
         int location = str.indexOf(";base64,");
         newStrEnd = str.substring(location + 8);
         String avalibleUrl1 = changeToNewUrlBase64(newStrEnd, historyCount, bean.getCommodityCode(), "jd");
         if (avalibleUrl1.equals("error")) {
           setRequestBean(bean);
           addErrorMessage("图片转换失败");
           return BackActionResult.RESULT_SUCCESS;
         }

         String imgName = bean.getCommodityCode() + "_" + type + "_" + historyCount + ".jpg";
         avalibleUrl1 = localMomentUrl + imgName;

         // 图片上传京东
         String jdImgReturn = jdservice.ImgzonePictureUploadApi(avalibleUrl1, imgName);
         if (jdImgReturn == null) {
           setRequestBean(bean);
           addErrorMessage("图片上传京东失败");
           return BackActionResult.RESULT_SUCCESS;
         }

         jdImgReturn = config.getJdImgUrl() + jdImgReturn;
         imgStrOld = imgStrOld.replace(str, jdImgReturn);

       } else {// download 图片的处理
         String avalibleUrl2 = changeToNewUrl(str, historyCount, bean.getCommodityCode(), "jd");
         if (avalibleUrl2.equals("error")) {
           setRequestBean(bean);
           addErrorMessage("图片上传服务器失败");
           return BackActionResult.RESULT_SUCCESS;
         }

         if (!str.contains(imgUrl) && !str.contains("jd")) {
           String imgName = bean.getCommodityCode() + "_" + type + "_" + historyCount + ".jpg";
           avalibleUrl2 = localMomentUrl + imgName;
           
           // 图片上传京东
           String jdImgReturn = jdservice.ImgzonePictureUploadApi(avalibleUrl2, imgName);
           if (jdImgReturn == null) {
             setRequestBean(bean);
             addErrorMessage("图片上传京东失败");
             return BackActionResult.RESULT_SUCCESS;
           }
           jdImgReturn = config.getJdImgUrl() + jdImgReturn;
           imgStrOld = imgStrOld.replace(str, jdImgReturn);
         }
       }
     }
     
     if (imgStrOld.length() > 30000) {
       setRequestBean(bean);
       addErrorMessage("Jd描述超过30000个字符，描述更新失败！");
       return BackActionResult.RESULT_SUCCESS;
     }

     // jd描述保存
     describe.setDecribeJd(imgStrOld);
     
     
     
     // 更新京东同期状态
     CCommodityHeaderDao cchDao = DIContainer.getDao(CCommodityHeaderDao.class);
     CCommodityHeader cchDto = cchDao.load(DIContainer.getWebshopConfig().getSiteShopCode(), bean.getCommodityCode());
     
     if (cchDto != null && JdUseFlg.ENABLED.longValue().equals(bean.getJdUseFlg()) 
         && !NumUtil.isNull(cchDto.getJdCommodityId())) {
       jdDescflag = true;
     }
   }
    // 2014/05/05 京东WBS对应 ob_卢 add end
    
    
    // 清空临时目录中图片
    deleteTempFiles();

    if (flag) {
      // 2014/05/09 京东WBS对应 ob_卢 update start
//      dao.insert(describe);
      ServiceResult result = service.insertCommodityDescribe(describe, jdDescflag);
      
      if (result.hasError()) {
        setRequestBean(bean);
        logger.warn(result.toString());
        return BackActionResult.SERVICE_ERROR;
      }
      // 2014/05/09 京东WBS对应 ob_卢 update end
      
      
    } else {
      // 2014/05/09 京东WBS对应 ob_卢 update start
//      ServiceResult result = service.updateCommodityDescribe(describe);
      ServiceResult result = service.updateCommodityDescribe(describe, jdDescflag);
      // 2014/05/09 京东WBS对应 ob_卢 update end
      
      if (result.hasError()) {
        setRequestBean(bean);
        logger.warn(result.toString());
        return BackActionResult.SERVICE_ERROR;
      }
    }
    ServiceResult result1 = service.updateCommodityHeader(header);
    if (result1.hasError()) {
      setRequestBean(bean);
      logger.warn(result1.toString());
      return BackActionResult.SERVICE_ERROR;
    } else {
      
      // 2014/05/20 京东WBS对应 ob_姚 add start
      // 更新京东描述
      if (type.equals("jd")) {
        JdService jdService = ServiceLocator.getJdService(getLoginInfo());
        CyncroResult resultMap = new CyncroResult();
        
        CCommodityHeaderDao headerDao = DIContainer.getDao(CCommodityHeaderDao.class);
        CCommodityHeader commodity = headerDao.load("00000000", bean.getCommodityCode());
        jdService.updateCommodityApi(resultMap, commodity, true);

        if(resultMap.hasError()){
          setRequestBean(bean);
          logger.warn(resultMap.getErrorList().get(0).getErrorInfo());
          addErrorMessage(resultMap.getErrorList().get(0).getErrorInfo());
          return BackActionResult.RESULT_SUCCESS;
        }
      }
      // 2014/05/20 京东WBS对应 ob_姚 add end
      setNextUrl("/app/catalog/tmall_related_describe/init/00000000/" + bean.getCommodityCode() + "/"
          + WebConstantCode.COMPLETE_UPDATE);
    }
    

    return BackActionResult.RESULT_SUCCESS;
  }

  // Base64图片处理
  private String changeToNewUrlBase64(String oldUrl, Long historyNum, String commodityCode, String type) {
    String newUrl = "";
    String imgName = commodityCode + "_" + type + "_" + historyNum + ".jpg";
    List<String> list = new ArrayList<String>();
    String imgUrl = "";
    if (type.equals("cn")) {
      list = localImgUrl2ListCn;
      imgUrl = localImgUrl1Cn;
    } else if (type.equals("en")) {
      list = localImgUrl2ListEn;
      imgUrl = localImgUrl1En;
    } else if (type.equals("jp")) {
      list = localImgUrl2ListJp;
      imgUrl = localImgUrl1Jp;
    } // 2014/05/08 京东WBS对应 ob_卢 add start
    else if (type.equals("jd")) {
      list.add(jdImgUrl);
      imgUrl = jdImgUrl;
    } else{
    // 2014/05/08 京东WBS对应 ob_卢 add endelse {
      list.add(tmallImgUrl);
      imgUrl = tmallImgUrl;
    }

    CommodityDescImageUtil.GenerateImage(oldUrl, localMomentUrl + imgName);
    if (!CommodityDescImageUtil.isOSType()) {
      for (String localImgUrl2 : list) {
        String[] str = localImgUrl2.split(",");
        if (CommodityDescImageUtil.scpSendImg(str[0], localMomentUrl + imgName, str[3], str[1], str[2])) {
          newUrl = imgUrl + imgName;
        } else {
          return "error";
        }
      }

    } else {
      for (String localImgUrl2 : list) {
        if (CommodityDescImageUtil.GenerateImage(oldUrl, localImgUrl2.split(",")[3] + imgName)) {
          newUrl = imgUrl + imgName;
        } else {
          return "error";
        }
      }
    }

    return newUrl;
  }

  // 网络图片引用处理 例:http://www.baidu.com/1.jpg
  private String changeToNewUrl(String oldUrl, Long historyNum, String commodityCode, String type) {
    String newUrl = "";
    String imgName = commodityCode + "_" + type + "_" + historyNum + ".jpg";
    List<String> list = new ArrayList<String>();
    String imgUrl = "";
    if (type.equals("cn")) {
      list = localImgUrl2ListCn;
      imgUrl = localImgUrl1Cn;
    } else if (type.equals("en")) {
      list = localImgUrl2ListEn;
      imgUrl = localImgUrl1En;
    } else if (type.equals("jp")) {
      list = localImgUrl2ListJp;
      imgUrl = localImgUrl1Jp;
    } 
    // 2014/05/06 京东WBS对应 ob_卢 add start
    else if (type.equals("jd")) {
      list.add(jdImgUrl);
      imgUrl = jdImgUrl;
    } 
    // 2014/05/06 京东WBS对应 ob_卢 add end
    else {
      list.add(tmallImgUrl);
      imgUrl = tmallImgUrl;
    }

    if (!oldUrl.contains(imgUrl)) {
      try {
        newUrl = localMomentUrl + imgName;
        CommodityDescImageUtil.netImgUpload(oldUrl, newUrl);
        if (!CommodityDescImageUtil.isOSType()) {
          for (String localImgUrl2 : list) {
            String[] str = localImgUrl2.split(",");
            if (CommodityDescImageUtil.scpSendImg(str[0], localMomentUrl + imgName, str[3], str[1], str[2])) {
              new File("").setReadable(true);
              new File("").setWritable(true);
              new File("").setExecutable(true);
              newUrl = imgUrl + imgName;
            } else {
              return "error";
            }
          }
        } else {
          for (String localImgUrl2 : list) {
            newUrl = localImgUrl2.split(",")[3] + imgName;
            CommodityDescImageUtil.netImgUpload(oldUrl, newUrl);
          }
        }

        return "";
      } catch (Exception e) {
        return "error";
      }
    } else {
      return oldUrl;
    }
  }

  // 中日英图片处理以及上传
  private String changeStrImg(String oldStrImg, DescribeImgHistoryDao historyDao, CatalogService service, String commodityCode,
      String type) {
    // 获得图片上传目录http
    String imgUrl = "";
    if (type.equals("cn")) {
      imgUrl = localImgUrl1Cn;
    } else if (type.equals("en")) {
      imgUrl = localImgUrl1En;
    } else if (type.equals("jp")) {
      imgUrl = localImgUrl1Jp;
    } else {
      imgUrl = tmallImgUrl;
    }
    String newStrEnd = "";
    String imgStrOld = oldStrImg;
    // 获得描述中的所有图片src字符串
    List<String> imgListSrc = CommodityDescImageUtil.getImg(imgStrOld);
    // 获得src属性中的url字符串
    List<String> imgListReal = new ArrayList<String>();
    for (int i = 0; i < imgListSrc.size(); i++) {
      int begin = imgListSrc.get(i).indexOf("\"") + 1;
      int end = imgListSrc.get(i).lastIndexOf("\"");
      imgListReal.add(imgListSrc.get(i).substring(begin, end));
    }

    for (String str : imgListReal) {
      // 获得当前语种商品的图片履历总数
      Long historyCount = service.getAllDescribeImgHistory(commodityCode, type);
      if (str.contains(";base64,")) {
        int location = str.indexOf(";base64,");
        newStrEnd = str.substring(location + 8);
        // 处理本地图片的转换以及上传
        String avalibleUrl1 = changeToNewUrlBase64(newStrEnd, historyCount, commodityCode, type);
        if (avalibleUrl1.equals("error")) {
          return "error1";
        }
        DescribeImgHistory history = new DescribeImgHistory();
        String imgName = commodityCode + "_" + type + "_" + historyCount + ".jpg";
        avalibleUrl1 = imgUrl + imgName;
        imgStrOld = imgStrOld.replace(str, avalibleUrl1);
        history.setCommodityCode(commodityCode);
        history.setLang(type);
        history.setShopCode("00000000");
        history.setDownloadImgUrl("");
        history.setImgName(imgName);
        history.setTmallReturnUrl("");
        // 存入图片履历表
        historyDao.insert(history);

      } else {
        // 处理download图片的转换以及上传
        String avalibleUrl2 = changeToNewUrl(str, historyCount, commodityCode, type);
        if (avalibleUrl2.equals("error")) {
          return "error2";
        }
        DescribeImgHistory history = new DescribeImgHistory();
        String imgName = commodityCode + "_" + type + "_" + historyCount + ".jpg";
        // 图片url为本地url就不做处理
        if (!str.contains(imgUrl)) {
          avalibleUrl2 = imgUrl + imgName;
          imgStrOld = imgStrOld.replace(str, avalibleUrl2);
          history.setCommodityCode(commodityCode);
          history.setLang(type);
          history.setShopCode("00000000");
          history.setDownloadImgUrl(str);
          history.setImgName(imgName);
          history.setTmallReturnUrl("");
          // 存入图片履历表
          historyDao.insert(history);
        }

      }
    }
    return imgStrOld;
  }

  // 清空临时目录中图片
  public void deleteTempFiles() {
    boolean isFileCtrl = false;
    File file = new File(localMomentUrl);
 // 2014/05/09 京东WBS对应 ob_卢 add start
    if (file!=null && file.exists()) {
 // 2014/05/09 京东WBS对应 ob_卢 add end  
    for (File file1 : file.listFiles()) {
      for (int j = 0; j < 3; j++) {
        if (file1.renameTo(file1)) {
          isFileCtrl = true;
          break;
        } else {
          System.gc();
          try {
            Thread.sleep(1000);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          continue;
        }
      }
      if (isFileCtrl) {
        file1.delete();
      }
    }
 // 2014/05/09 京东WBS对应 ob_卢 add start
    }
 // 2014/05/09 京东WBS对应 ob_卢 add end
  }
}
