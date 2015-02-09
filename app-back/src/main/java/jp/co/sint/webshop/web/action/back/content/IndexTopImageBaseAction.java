package jp.co.sint.webshop.web.action.back.content;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.service.data.ContentsPath;
import jp.co.sint.webshop.utility.CommodityDescImageUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.ImageUploadConfig;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.content.IndexTopImageBean;
import jp.co.sint.webshop.web.bean.back.content.IndexTopImageBean.IndexTopImageDetailBean;
import jp.co.sint.webshop.web.file.UploadContents;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 首页TOP内容共通处理
 * 
 * @author Kousen.
 */
public abstract class IndexTopImageBaseAction extends WebBackAction<IndexTopImageBean> {

  private ImageUploadConfig uploadConfig = DIContainer.getImageUploadConfig();

  /**
   * 更新图片连接与ALT，并上传到其它服务器
   * 
   * @author Kousen.
   */
  public boolean setImgInfo(String imgName, IndexTopImageDetailBean detail) {
    ContentsPath path = DIContainer.get("contentsPath");
    StringBuilder builder = new StringBuilder();
    builder.append(path.getContentsSharedPath());
    builder.append("/" + getBean().getLanguageCode());
    String leftAddress = builder.toString() + uploadConfig.getTopLeftHtmlPath();
    String centerAddress = builder.toString() + uploadConfig.getTopCenterHtmlPath();
    String rightAddress = builder.toString() + uploadConfig.getTopRightHtmlPath();
    String tempAddress = "";
    File leftFile = new File(leftAddress);
    File centerFile = new File(centerAddress);
    File rightFile = new File(rightAddress);
    try {
      boolean successFlg = false;
      Document leftDoc = Jsoup.parse(leftFile, "UTF-8", leftAddress);
      Elements leftLinks = leftDoc.getElementsByTag("a");
      for (Element link : leftLinks) {
        Elements img = link.getElementsByTag("img");
        String linkSrc = img.get(0).attr("src");
        String[] srcPaths = linkSrc.split("/");
        String name = srcPaths[srcPaths.length - 1].replace(".jpg", "");
        // 根据图片名判断是否是当然修改内容
        if (imgName.equals(name)) {
          link.attr("href", detail.getImgLink());
          img.get(0).attr("alt", detail.getImgAlt());

          try {
            FileOutputStream fos = new FileOutputStream(leftAddress, false);
            fos.write(leftDoc.html().getBytes());
            fos.close();
            successFlg = true;
            tempAddress = leftAddress;
            break;
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      }

      if (!successFlg) {
        Document centerDoc = Jsoup.parse(centerFile, "UTF-8", centerAddress);
        Elements centerLinks = centerDoc.getElementsByTag("a");
        int fromIndex = 0;
        String fromImgSrc = "";
        for (Element link : centerLinks) {
          fromIndex++;
          Elements img = link.getElementsByTag("img");
          String linkSrc = img.get(0).attr("src");
          String[] srcPaths = linkSrc.split("/");
          String name = srcPaths[srcPaths.length - 1].replace(".jpg", "");
          // 根据图片名判断是否是当然修改内容
          if (imgName.equals(name)) {
            link.attr("href", detail.getImgLink());
            img.get(0).attr("alt", detail.getImgAlt());
            successFlg = true;
            fromImgSrc = linkSrc;
            break;
          }
        }
        
        if(successFlg) {
          int toIndex = Integer.parseInt(detail.getImgOrder());
          if(fromIndex != toIndex) {
            Element tolink = centerLinks.get(toIndex - 1);
            Elements toImg = tolink.getElementsByTag("img");
            String toImgSrc = toImg.get(0).attr("src");
            String toImgAlt = toImg.get(0).attr("alt");
            String toLinkHref = tolink.attr("href");
            
            //变换图片链接，SRC，ALT
            tolink.attr("href", detail.getImgLink());
            toImg.attr("alt", detail.getImgAlt());
            toImg.attr("src", fromImgSrc);
            
            Element fromlink = centerLinks.get(fromIndex - 1);
            fromlink.attr("href", toLinkHref);
            Elements fromImg = fromlink.getElementsByTag("img");
            fromImg.get(0).attr("alt", toImgAlt);
            fromImg.get(0).attr("src", toImgSrc);
          }
          
          try {
            FileOutputStream fos = new FileOutputStream(centerAddress, false);
            fos.write(centerDoc.html().getBytes());
            fos.close();
            tempAddress = centerAddress;
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      }

      if (!successFlg) {
        Document rightDoc = Jsoup.parse(rightFile, "UTF-8", rightAddress);
        Elements rightLinks = rightDoc.getElementsByTag("a");
        int fromIndex = 0;
        String fromImgSrc = "";
        for (Element link : rightLinks) {
          fromIndex++;
          Elements img = link.getElementsByTag("img");
          String linkSrc = img.get(0).attr("src");
          String[] srcPaths = linkSrc.split("/");
          String name = srcPaths[srcPaths.length - 1].replace(".jpg", "");
          // 根据图片名判断是否是当然修改内容
          if (imgName.equals(name)) {
            link.attr("href", detail.getImgLink());
            img.get(0).attr("alt", detail.getImgAlt());
            successFlg = true;
            fromImgSrc = linkSrc;
            break;
          }
        }
        
        if(successFlg) {
          int toIndex = Integer.parseInt(detail.getImgOrder());
          if(fromIndex != toIndex) {
            Element tolink = rightLinks.get(toIndex - 1);
            Elements toImg = tolink.getElementsByTag("img");
            String toImgSrc = toImg.get(0).attr("src");
            String toImgAlt = toImg.get(0).attr("alt");
            String toLinkHref = tolink.attr("href");
            
            //变换图片链接，SRC，ALT
            tolink.attr("href", detail.getImgLink());
            toImg.attr("alt", detail.getImgAlt());
            toImg.attr("src", fromImgSrc);
            
            Element fromlink = rightLinks.get(fromIndex - 1);
            fromlink.attr("href", toLinkHref);
            Elements fromImg = fromlink.getElementsByTag("img");
            fromImg.get(0).attr("alt", toImgAlt);
            fromImg.get(0).attr("src", toImgSrc);
          }
          
          try {
            FileOutputStream fos = new FileOutputStream(rightAddress, false);
            fos.write(rightDoc.html().getBytes());
            fos.close();
            tempAddress = rightAddress;
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      }

      // linux系统时向其它服务器上传
      if (!CommodityDescImageUtil.isOSType() && successFlg) {
        // 向其它服务器上传
        ImageUploadConfig imageUploadConfig = DIContainer.getImageUploadConfig();
        List<String> list = imageUploadConfig.getOtherServerTopPath();
        for (String address : list) {
          String[] str = address.split(",");
          if (!CommodityDescImageUtil
              .scpSendImg(str[0], tempAddress, str[3] + getBean().getLanguageCode() + str[5], str[1], str[2])) {
            addErrorMessage("HTML文件:" + tempAddress + "向服务器：" + str[0] + "上传失败。");
          }
        }
        StringBuilder builderImg = new StringBuilder();
        builderImg.append(path.getContentsSharedPath());
        builderImg.append("/" + getBean().getLanguageCode() + imageUploadConfig.getTopImgPath() + "/" + imgName + ".jpg");
        String imgAddress = builderImg.toString();
        for (String address : list) {
          String[] str = address.split(",");
          if (str[6].equals("1")) {
            if (!CommodityDescImageUtil.scpSendImg(str[0], imgAddress, str[3] + getBean().getLanguageCode() + str[4], str[1],
                str[2])) {
              addErrorMessage("图片：" + imgName + "向服务器：" + str[0] + "上传失败。");
            }
          }
        }
      }

      return successFlg;
    } catch (IOException e) {
      e.printStackTrace();
    }

    return false;
  }

  /**
   * 读取图片信息
   * 
   * @author Kousen.
   */
  public List<IndexTopImageDetailBean> getDetailList(String languageCode) {
    List<IndexTopImageDetailBean> list = new ArrayList<IndexTopImageDetailBean>();

    ContentsPath path = DIContainer.get("contentsPath");
    StringBuilder builder = new StringBuilder();
    builder.append(path.getContentsSharedPath());
    builder.append("/" + languageCode);
    String leftAddress = builder.toString() + uploadConfig.getTopLeftHtmlPath();
    String centerAddress = builder.toString() + uploadConfig.getTopCenterHtmlPath();
    String rightAddress = builder.toString() + uploadConfig.getTopRightHtmlPath();
    File leftFile = new File(leftAddress);
    File centerFile = new File(centerAddress);
    File rightFile = new File(rightAddress);
    try {
      Document leftDoc = Jsoup.parse(leftFile, "UTF-8", leftAddress);
      Elements leftLinks = leftDoc.getElementsByTag("a");
      for (Element link : leftLinks) {
        IndexTopImageDetailBean detail = new IndexTopImageDetailBean();
        Elements img = link.getElementsByTag("img");
        String linkHref = link.attr("href");
        String linkSrc = img.get(0).attr("src");
        if(linkSrc.contains("http://static.pinstore.cn")){
          linkSrc = linkSrc.replace("http://static.pinstore.cn", "http://192.168.201.21");
        }
        String linkAlt = img.get(0).attr("alt");
        String[] srcPaths = linkSrc.split("/");
        String imgName = srcPaths[srcPaths.length - 1].replace(".jpg", "");

        detail.setImgLink(linkHref);
        detail.setImgUrl(linkSrc);
        detail.setImgAlt(linkAlt);
        detail.setImgName(imgName);
        detail.setImageContents(imgName + "/" + UploadContents.INDEX_TOP_IMAGE + "/" + languageCode);

        list.add(detail);
      }

      Document centerDoc = Jsoup.parse(centerFile, "UTF-8", centerAddress);
      Elements centerLinks = centerDoc.getElementsByTag("a");
      int c = 1;
      for (Element link : centerLinks) {
        IndexTopImageDetailBean detail = new IndexTopImageDetailBean();
        Elements img = link.getElementsByTag("img");
        String linkHref = link.attr("href");
        String linkSrc = img.get(0).attr("src");
        if(linkSrc.contains("http://static.pinstore.cn")){
          linkSrc = linkSrc.replace("http://static.pinstore.cn", "http://192.168.201.21");
        }
        String linkAlt = img.get(0).attr("alt");
        String[] srcPaths = linkSrc.split("/");
        String imgName = srcPaths[srcPaths.length - 1].replace(".jpg", "");

        detail.setImgLink(linkHref);
        detail.setImgUrl(linkSrc);
        detail.setImgAlt(linkAlt);
        detail.setImgName(imgName);
        detail.setImageContents(imgName + "/" + UploadContents.INDEX_TOP_IMAGE + "/" + languageCode);
        detail.setImgOrder(NumUtil.toString(c));

        list.add(detail);
        c+=1;
      }

      Document rightDoc = Jsoup.parse(rightFile, "UTF-8", rightAddress);
      Elements rightLinks = rightDoc.getElementsByTag("a");
      int r = 1;
      for (Element link : rightLinks) {
        IndexTopImageDetailBean detail = new IndexTopImageDetailBean();
        Elements img = link.getElementsByTag("img");
        String linkHref = link.attr("href");
        String linkSrc = img.get(0).attr("src");
        if(linkSrc.contains("http://static.pinstore.cn")){
          linkSrc = linkSrc.replace("http://static.pinstore.cn", "http://192.168.201.21");
        }
        String linkAlt = img.get(0).attr("alt");
        String[] srcPaths = linkSrc.split("/");
        String imgName = srcPaths[srcPaths.length - 1].replace(".jpg", "");

        detail.setImgLink(linkHref);
        detail.setImgUrl(linkSrc);
        detail.setImgAlt(linkAlt);
        detail.setImgName(imgName);
        detail.setImageContents(imgName + "/" + UploadContents.INDEX_TOP_IMAGE + "/" + languageCode);
        detail.setImgOrder(NumUtil.toString(r));
        
        list.add(detail);
        r+=1;
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return list;
  }
}
