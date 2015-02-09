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
import jp.co.sint.webshop.web.bean.back.content.IndexFloorBean;
import jp.co.sint.webshop.web.bean.back.content.IndexFloorBean.IndexFloorDetailBean;
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
public abstract class IndexFloorBaseAction extends WebBackAction<IndexFloorBean> {

  private ImageUploadConfig uploadConfig = DIContainer.getImageUploadConfig();

  /**
   * 楼层TITLE
   * 
   * @author Kousen.
   */
  public void setTitle() {
    ContentsPath path = DIContainer.get("contentsPath");
    StringBuilder builder = new StringBuilder();
    builder.append(path.getContentsSharedPath());
    if (getBean().getLanguageCode().equals("zh-cn") || getBean().getFloorCode().equals("9")) {
      builder.append("/" + getBean().getLanguageCode() + uploadConfig.getTagsHtmlAnyPath()
          + NumUtil.toString(Long.parseLong(getBean().getFloorCode())));
    } else {
      builder.append("/" + getBean().getLanguageCode() + uploadConfig.getTagsHtmlAnyPath()
          + NumUtil.toString(Long.parseLong(getBean().getFloorCode()) + 1));
    }
    // builder.append("/" + getBean().getLanguageCode() +
    // uploadConfig.getTagsHtmlAnyPath()
    // + NumUtil.toString(Long.parseLong(getBean().getFloorCode()) + 1));

    String titleAddress = builder.toString() + "_title.html";

    try {
      FileOutputStream fos = new FileOutputStream(titleAddress, false);
      fos.write(getBean().getTitle().getBytes());
      fos.close();

      // linux系统时向其它服务器上传
      if (!CommodityDescImageUtil.isOSType()) {
        // 向其它服务器上传
        ImageUploadConfig imageUploadConfig = DIContainer.getImageUploadConfig();
        List<String> list = imageUploadConfig.getOtherServerTagsPath();
        for (String address : list) {
          String[] str = address.split(",");
          if (!CommodityDescImageUtil.scpSendImg(str[0], titleAddress, str[3] + getBean().getLanguageCode() + str[5], str[1],
              str[2])) {
            addErrorMessage("HTML文件向服务器：" + str[0] + "上传失败。");
          }
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * 楼层图片连接与ALT
   * 
   * @author Kousen.
   */
  public boolean setImgInfo(String imgName) {
    IndexFloorDetailBean detail = getBean().getEditBean();

    ContentsPath path = DIContainer.get("contentsPath");
    StringBuilder builder = new StringBuilder();
    builder.append(path.getContentsSharedPath());
    if (getBean().getLanguageCode().equals("zh-cn") || getBean().getFloorCode().equals("9")) {
      builder.append("/" + getBean().getLanguageCode() + uploadConfig.getTagsHtmlAnyPath()
          + NumUtil.toString(Long.parseLong(getBean().getFloorCode())));
    } else {
      builder.append("/" + getBean().getLanguageCode() + uploadConfig.getTagsHtmlAnyPath()
          + NumUtil.toString(Long.parseLong(getBean().getFloorCode()) + 1));
    }
    // builder.append("/" + getBean().getLanguageCode() +
    // uploadConfig.getTagsHtmlAnyPath()
    // + NumUtil.toString(Long.parseLong(getBean().getFloorCode()) + 1));
    String banner1Address = builder.toString() + "_banner.html";
    String leftAddress = builder.toString() + "_left.html";
    String tempAddress = "";
    File bannerFile = new File(banner1Address);
    File leftFile = new File(leftAddress);

    try {
      boolean successFlg = false;
      if (imgName.equals("l_banner_1")) {
        Document bannerDoc = Jsoup.parse(bannerFile, "UTF-8", banner1Address);
        Elements bannerLinks = bannerDoc.getElementsByTag("a");
        for (Element link : bannerLinks) {
          Elements img = link.getElementsByTag("img");
          String linkSrc = img.get(0).attr("src");
          String[] srcPaths = linkSrc.split("/");
          String name = srcPaths[srcPaths.length - 1].replace(".jpg", "");
          // 根据图片名判断是否是当然修改内容
          if (imgName.equals(name)) {
            link.attr("href", detail.getImgLink());
            img.get(0).attr("alt", detail.getImgAlt());

            try {
              FileOutputStream fos = new FileOutputStream(banner1Address, false);
              fos.write(bannerDoc.html().getBytes());
              fos.close();
              successFlg = true;
              tempAddress = banner1Address;
              break;
            } catch (IOException e) {
              e.printStackTrace();
            }
          }
        }
      } else {
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
      }

      // linux系统时向其它服务器上传
      if (!CommodityDescImageUtil.isOSType() && successFlg) {
        // 向其它服务器上传
        ImageUploadConfig imageUploadConfig = DIContainer.getImageUploadConfig();
        List<String> list = imageUploadConfig.getOtherServerTagsPath();
        for (String address : list) {
          String[] str = address.split(",");
          if (!CommodityDescImageUtil
              .scpSendImg(str[0], tempAddress, str[3] + getBean().getLanguageCode() + str[5], str[1], str[2])) {
            addErrorMessage("HTML文件:" + tempAddress + "向服务器：" + str[0] + "上传失败。");
          }
        }
        StringBuilder builderImg = new StringBuilder();
        builderImg.append(path.getContentsSharedPath());
        builderImg.append("/" + getBean().getLanguageCode() + imageUploadConfig.getTagsImgPath() + "/" + imgName + ".jpg");
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
   * 获取图片信息
   * 
   * @author Kousen.
   */
  public void setDataToBean(IndexFloorBean bean) {
    String languageCode = bean.getLanguageCode();
    String floorCode = bean.getFloorCode();
    List<IndexFloorDetailBean> list = new ArrayList<IndexFloorDetailBean>();

    ContentsPath path = DIContainer.get("contentsPath");
    StringBuilder builder = new StringBuilder();
    builder.append(path.getContentsSharedPath());
    //builder.append("/" + languageCode + uploadConfig.getTagsHtmlAnyPath() + NumUtil.toString(Long.parseLong(floorCode)));
    if (languageCode.equals("zh-cn") || floorCode.equals("9")) {
      builder.append("/" + languageCode + uploadConfig.getTagsHtmlAnyPath() + NumUtil.toString(Long.parseLong(floorCode)));
    } else {
      builder.append("/" + languageCode + uploadConfig.getTagsHtmlAnyPath() + NumUtil.toString(Long.parseLong(floorCode) + 1));
    }

    String banner1Address = builder.toString() + "_banner.html";
    String leftAddress = builder.toString() + "_left.html";
    String titleAddress = builder.toString() + "_title.html";
    File bannerFile = new File(banner1Address);
    File leftFile = new File(leftAddress);
    File titleFile = new File(titleAddress);
    try {
      // 一楼banner
      if (floorCode.equals("1")) {
        Document bannerDoc = Jsoup.parse(bannerFile, "UTF-8", banner1Address);
        Elements bannerLinks = bannerDoc.getElementsByTag("a");

        if (bannerLinks != null && bannerLinks.size() > 0) {
          IndexFloorDetailBean detail = new IndexFloorDetailBean();
          Elements img = bannerLinks.get(0).getElementsByTag("img");
          String linkHref = bannerLinks.get(0).attr("href");
          String linkSrc = img.get(0).attr("src");
          if (linkSrc.contains("http://static.pinstore.cn")) {
            linkSrc = linkSrc.replace("http://static.pinstore.cn", "http://192.168.201.21");
          }
          String linkAlt = img.get(0).attr("alt");
          String[] srcPaths = linkSrc.split("/");
          String imgName = srcPaths[srcPaths.length - 1].replace(".jpg", "");

          detail.setImgLink(linkHref);
          detail.setImgUrl(linkSrc);
          detail.setImgAlt(linkAlt);
          detail.setImgName(imgName);
          detail.setImageContents(imgName + "/" + UploadContents.INDEX_FLOOR_IMAGE + "/" + languageCode);

          list.add(detail);
        }
      }

      // 一楼左边图片
      Document leftDoc = Jsoup.parse(leftFile, "UTF-8", leftAddress);
      Elements leftLinks = leftDoc.getElementsByTag("a");
      for (Element link : leftLinks) {
        IndexFloorDetailBean detail = new IndexFloorDetailBean();
        Elements img = link.getElementsByTag("img");
        String linkHref = link.attr("href");
        String linkSrc = img.get(0).attr("src");
        if (linkSrc.contains("http://static.pinstore.cn")) {
          linkSrc = linkSrc.replace("http://static.pinstore.cn", "http://192.168.201.21");
        }
        String linkAlt = img.get(0).attr("alt");
        String[] srcPaths = linkSrc.split("/");
        String imgName = srcPaths[srcPaths.length - 1].replace(".jpg", "");

        detail.setImgLink(linkHref);
        detail.setImgUrl(linkSrc);
        detail.setImgAlt(linkAlt);
        detail.setImgName(imgName);
        detail.setImageContents(imgName + "/" + UploadContents.INDEX_FLOOR_IMAGE + "/" + languageCode);

        list.add(detail);
      }

      // 一楼TITLE
      Document titleDoc = Jsoup.parse(titleFile, "UTF-8", titleAddress);
      bean.setTitle(titleDoc.html());
    } catch (IOException e) {
      e.printStackTrace();
    }
    bean.setList(list);
  }
}
