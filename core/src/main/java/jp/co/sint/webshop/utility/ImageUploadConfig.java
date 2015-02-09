package jp.co.sint.webshop.utility;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 顧客問い合わせ情報
 * 
 * @author System Integrator Corp.
 */
public class ImageUploadConfig implements Serializable {

  /**
   * serial version UID
   */
  private static final long serialVersionUID = 1L;

  /** 原图片文件路径 */
  private String orgImgPath;

  /** 原活动图片文件路径 */
  private String campImgPath;

  /** 待处理图片文件路径 */
  private String unOperImgPath;

  /** resize后的图片文件路径 */
  private String resizeImgPath;

  /** 上传后的图片文件路径 */
  private String uploadImgPath;

  /** 上传到tmall的商品图片的后缀 */
  private String tmallUploadImgFix;

  // 20130905 txw add start
  /** 淘宝不使用或者淘宝上不存在商品备份目录 */
  private String tmallDisabledImgPath;

  /** 上传到tmall的辅图商品1图片的后缀 */
  private String assistImg1Fix;

  /** 上传到tmall的辅图商品2图片的后缀 */
  private String assistImg2Fix;

  /** 上传到tmall的辅图商品3图片的后缀 */
  private String assistImg3Fix;

  /** 上传到tmall的辅图商品4图片的后缀 */
  private String assistImg4Fix;

  /** 原商品图片宽 */
  private int orgImgWidth = 800;

  /** 原商品图片高 */
  private int orgImgHight = 800;

  // 20130905 txw add end
  
  // 2014/05/02 京东WBS对应 ob_姚 add start
  /** 京东不使用或者京东上不存在商品备份目录 */
  private String jdDisabledImgPath;
  
  /** 上传到京东的商品图片的后缀 */
  private String jdUploadImgFix;
  
  /** 新规京东商品时的商品图片路径 */
  private String jdCommodityAddImgPath;
  // 2014/05/02 京东WBS对应 ob_姚 add end

  /** 需生成sku图片的属性配置列表 */
  private List<String> skuImgList = new ArrayList<String>();

  /** ftp服务器信息配置列表 */
  private List<String> ftpInfoList = new ArrayList<String>();

  private String staticUrl;

  private String tmallImgUrl;

  // 2014/05/05 京东WBS对应 ob_姚 add start
  private String jdImgUrl;
  // 2014/05/05 京东WBS对应 ob_姚 add end
  
  private String localImgUrl1Cn;

  private String localImgUrl1En;

  private String localImgUrl1Jp;

  private String localMomentUrl;

  private List<String> localImgUrl2ListCn = new ArrayList<String>();

  private List<String> localImgUrl2ListEn = new ArrayList<String>();

  private List<String> localImgUrl2ListJp = new ArrayList<String>();

  // 20131029 txw add start
  private String topImgPath;

  private String topLeftHtmlPath;

  private String topCenterHtmlPath;

  private String topRightHtmlPath;

  private String tagsHtmlAnyPath;

  private String tagsImgPath;

  private List<String> otherServerTopPath = new ArrayList<String>();

  private List<String> otherServerTagsPath = new ArrayList<String>();

  // 20131029 txw add end

  /**
   * @return the orgImgPath
   */
  public String getOrgImgPath() {
    return orgImgPath;
  }

  /**
   * @param orgImgPath
   *          the orgImgPath to set
   */
  public void setOrgImgPath(String orgImgPath) {
    this.orgImgPath = orgImgPath;
  }

  /**
   * @return the unOperImgPath
   */
  public String getUnOperImgPath() {
    return unOperImgPath;
  }

  /**
   * @param unOperImgPath
   *          the unOperImgPath to set
   */
  public void setUnOperImgPath(String unOperImgPath) {
    this.unOperImgPath = unOperImgPath;
  }

  /**
   * @return the resizeImgPath
   */
  public String getResizeImgPath() {
    return resizeImgPath;
  }

  /**
   * @param resizeImgPath
   *          the resizeImgPath to set
   */
  public void setResizeImgPath(String resizeImgPath) {
    this.resizeImgPath = resizeImgPath;
  }

  /**
   * @return the uploadImgPath
   */
  public String getUploadImgPath() {
    return uploadImgPath;
  }

  /**
   * @param uploadImgPath
   *          the uploadImgPath to set
   */
  public void setUploadImgPath(String uploadImgPath) {
    this.uploadImgPath = uploadImgPath;
  }

  /**
   * @return the tmallUploadImgFix
   */
  public String getTmallUploadImgFix() {
    return tmallUploadImgFix;
  }

  /**
   * @param tmallUploadImgFix
   *          the tmallUploadImgFix to set
   */
  public void setTmallUploadImgFix(String tmallUploadImgFix) {
    this.tmallUploadImgFix = tmallUploadImgFix;
  }

  /**
   * @return the skuImgList
   */
  public List<String> getSkuImgList() {
    return skuImgList;
  }

  /**
   * @param skuImgList
   *          the skuImgList to set
   */
  public void setSkuImgList(List<String> skuImgList) {
    this.skuImgList = skuImgList;
  }

  /**
   * @return the ftpInfoList
   */
  public List<String> getFtpInfoList() {
    return ftpInfoList;
  }

  /**
   * @param ftpInfoList
   *          the ftpInfoList to set
   */
  public void setFtpInfoList(List<String> ftpInfoList) {
    this.ftpInfoList = ftpInfoList;
  }

  /**
   * @return the campImgPath
   */
  public String getCampImgPath() {
    return campImgPath;
  }

  /**
   * @param campImgPath
   *          the campImgPath to set
   */
  public void setCampImgPath(String campImgPath) {
    this.campImgPath = campImgPath;
  }

  /**
   * @return the tmallDisabledImgPath
   */
  public String getTmallDisabledImgPath() {
    return tmallDisabledImgPath;
  }

  /**
   * @return the assistImg1Fix
   */
  public String getAssistImg1Fix() {
    return assistImg1Fix;
  }

  /**
   * @return the assistImg2Fix
   */
  public String getAssistImg2Fix() {
    return assistImg2Fix;
  }

  /**
   * @return the assistImg3Fix
   */
  public String getAssistImg3Fix() {
    return assistImg3Fix;
  }

  /**
   * @return the assistImg4Fix
   */
  public String getAssistImg4Fix() {
    return assistImg4Fix;
  }

  /**
   * @param tmallDisabledImgPath
   *          the tmallDisabledImgPath to set
   */
  public void setTmallDisabledImgPath(String tmallDisabledImgPath) {
    this.tmallDisabledImgPath = tmallDisabledImgPath;
  }

  /**
   * @param assistImg1Fix
   *          the assistImg1Fix to set
   */
  public void setAssistImg1Fix(String assistImg1Fix) {
    this.assistImg1Fix = assistImg1Fix;
  }

  /**
   * @param assistImg2Fix
   *          the assistImg2Fix to set
   */
  public void setAssistImg2Fix(String assistImg2Fix) {
    this.assistImg2Fix = assistImg2Fix;
  }

  /**
   * @param assistImg3Fix
   *          the assistImg3Fix to set
   */
  public void setAssistImg3Fix(String assistImg3Fix) {
    this.assistImg3Fix = assistImg3Fix;
  }

  /**
   * @param assistImg4Fix
   *          the assistImg4Fix to set
   */
  public void setAssistImg4Fix(String assistImg4Fix) {
    this.assistImg4Fix = assistImg4Fix;
  }

  /**
   * @return the orgImgWidth
   */
  public int getOrgImgWidth() {
    return orgImgWidth;
  }

  /**
   * @return the orgImgHight
   */
  public int getOrgImgHight() {
    return orgImgHight;
  }

  /**
   * @param orgImgWidth
   *          the orgImgWidth to set
   */
  public void setOrgImgWidth(int orgImgWidth) {
    this.orgImgWidth = orgImgWidth;
  }

  /**
   * @param orgImgHight
   *          the orgImgHight to set
   */
  public void setOrgImgHight(int orgImgHight) {
    this.orgImgHight = orgImgHight;
  }

  /**
   * @return the staticUrl
   */
  public String getStaticUrl() {
    return staticUrl;
  }

  /**
   * @param staticUrl
   *          the staticUrl to set
   */
  public void setStaticUrl(String staticUrl) {
    this.staticUrl = staticUrl;
  }

  /**
   * @return the localImgUrl2ListCn
   */
  public List<String> getLocalImgUrl2ListCn() {
    return localImgUrl2ListCn;
  }

  /**
   * @param localImgUrl2ListCn
   *          the localImgUrl2ListCn to set
   */
  public void setLocalImgUrl2ListCn(List<String> localImgUrl2ListCn) {
    this.localImgUrl2ListCn = localImgUrl2ListCn;
  }

  /**
   * @return the localImgUrl2ListEn
   */
  public List<String> getLocalImgUrl2ListEn() {
    return localImgUrl2ListEn;
  }

  /**
   * @param localImgUrl2ListEn
   *          the localImgUrl2ListEn to set
   */
  public void setLocalImgUrl2ListEn(List<String> localImgUrl2ListEn) {
    this.localImgUrl2ListEn = localImgUrl2ListEn;
  }

  /**
   * @return the localImgUrl2ListJp
   */
  public List<String> getLocalImgUrl2ListJp() {
    return localImgUrl2ListJp;
  }

  /**
   * @param localImgUrl2ListJp
   *          the localImgUrl2ListJp to set
   */
  public void setLocalImgUrl2ListJp(List<String> localImgUrl2ListJp) {
    this.localImgUrl2ListJp = localImgUrl2ListJp;
  }

  /**
   * @return the localImgUrl1Cn
   */
  public String getLocalImgUrl1Cn() {
    return localImgUrl1Cn;
  }

  /**
   * @param localImgUrl1Cn
   *          the localImgUrl1Cn to set
   */
  public void setLocalImgUrl1Cn(String localImgUrl1Cn) {
    this.localImgUrl1Cn = localImgUrl1Cn;
  }

  /**
   * @return the localImgUrl1En
   */
  public String getLocalImgUrl1En() {
    return localImgUrl1En;
  }

  /**
   * @param localImgUrl1En
   *          the localImgUrl1En to set
   */
  public void setLocalImgUrl1En(String localImgUrl1En) {
    this.localImgUrl1En = localImgUrl1En;
  }

  /**
   * @return the localImgUrl1Jp
   */
  public String getLocalImgUrl1Jp() {
    return localImgUrl1Jp;
  }

  /**
   * @param localImgUrl1Jp
   *          the localImgUrl1Jp to set
   */
  public void setLocalImgUrl1Jp(String localImgUrl1Jp) {
    this.localImgUrl1Jp = localImgUrl1Jp;
  }

  /**
   * @return the tmallImgUrl
   */
  public String getTmallImgUrl() {
    return tmallImgUrl;
  }

  /**
   * @param tmallImgUrl
   *          the tmallImgUrl to set
   */
  public void setTmallImgUrl(String tmallImgUrl) {
    this.tmallImgUrl = tmallImgUrl;
  }

  /**
   * @return the localMomentUrl
   */
  public String getLocalMomentUrl() {
    return localMomentUrl;
  }

  /**
   * @param localMomentUrl
   *          the localMomentUrl to set
   */
  public void setLocalMomentUrl(String localMomentUrl) {
    this.localMomentUrl = localMomentUrl;
  }

  /**
   * @return the topImgPath
   */
  public String getTopImgPath() {
    return topImgPath;
  }

  /**
   * @return the topLeftHtmlPath
   */
  public String getTopLeftHtmlPath() {
    return topLeftHtmlPath;
  }

  /**
   * @return the topCenterHtmlPath
   */
  public String getTopCenterHtmlPath() {
    return topCenterHtmlPath;
  }

  /**
   * @return the topRightHtmlPath
   */
  public String getTopRightHtmlPath() {
    return topRightHtmlPath;
  }

  /**
   * @return the tagsHtmlAnyPath
   */
  public String getTagsHtmlAnyPath() {
    return tagsHtmlAnyPath;
  }

  /**
   * @param topImgPath
   *          the topImgPath to set
   */
  public void setTopImgPath(String topImgPath) {
    this.topImgPath = topImgPath;
  }

  /**
   * @param topLeftHtmlPath
   *          the topLeftHtmlPath to set
   */
  public void setTopLeftHtmlPath(String topLeftHtmlPath) {
    this.topLeftHtmlPath = topLeftHtmlPath;
  }

  /**
   * @param topCenterHtmlPath
   *          the topCenterHtmlPath to set
   */
  public void setTopCenterHtmlPath(String topCenterHtmlPath) {
    this.topCenterHtmlPath = topCenterHtmlPath;
  }

  /**
   * @param topRightHtmlPath
   *          the topRightHtmlPath to set
   */
  public void setTopRightHtmlPath(String topRightHtmlPath) {
    this.topRightHtmlPath = topRightHtmlPath;
  }

  /**
   * @param tagsHtmlAnyPath
   *          the tagsHtmlAnyPath to set
   */
  public void setTagsHtmlAnyPath(String tagsHtmlAnyPath) {
    this.tagsHtmlAnyPath = tagsHtmlAnyPath;
  }

  /**
   * @return the tagsImgPath
   */
  public String getTagsImgPath() {
    return tagsImgPath;
  }

  /**
   * @param tagsImgPath
   *          the tagsImgPath to set
   */
  public void setTagsImgPath(String tagsImgPath) {
    this.tagsImgPath = tagsImgPath;
  }

  /**
   * @return the otherServerTopPath
   */
  public List<String> getOtherServerTopPath() {
    return otherServerTopPath;
  }

  /**
   * @param otherServerTopPath
   *          the otherServerTopPath to set
   */
  public void setOtherServerTopPath(List<String> otherServerTopPath) {
    this.otherServerTopPath = otherServerTopPath;
  }

  /**
   * @return the otherServerTagsPath
   */
  public List<String> getOtherServerTagsPath() {
    return otherServerTagsPath;
  }

  /**
   * @param otherServerTagsPath
   *          the otherServerTagsPath to set
   */
  public void setOtherServerTagsPath(List<String> otherServerTagsPath) {
    this.otherServerTagsPath = otherServerTagsPath;
  }

  /**
   * @return the jdDisabledImgPath
   */
  public String getJdDisabledImgPath() {
    return jdDisabledImgPath;
  }

  /**
   * @param jdDisabledImgPath the jdDisabledImgPath to set
   */
  public void setJdDisabledImgPath(String jdDisabledImgPath) {
    this.jdDisabledImgPath = jdDisabledImgPath;
  }

  /**
   * @return the jdUploadImgFix
   */
  public String getJdUploadImgFix() {
    return jdUploadImgFix;
  }

  /**
   * @param jdUploadImgFix the jdUploadImgFix to set
   */
  public void setJdUploadImgFix(String jdUploadImgFix) {
    this.jdUploadImgFix = jdUploadImgFix;
  }

  /**
   * @return the jdImgUrl
   */
  public String getJdImgUrl() {
    return jdImgUrl;
  }

  /**
   * @param jdImgUrl the jdImgUrl to set
   */
  public void setJdImgUrl(String jdImgUrl) {
    this.jdImgUrl = jdImgUrl;
  }

  /**
   * @return the jdCommodityAddImgPath
   */
  public String getJdCommodityAddImgPath() {
    return jdCommodityAddImgPath;
  }

  /**
   * @param jdCommodityAddImgPath the jdCommodityAddImgPath to set
   */
  public void setJdCommodityAddImgPath(String jdCommodityAddImgPath) {
    this.jdCommodityAddImgPath = jdCommodityAddImgPath;
  }

}
