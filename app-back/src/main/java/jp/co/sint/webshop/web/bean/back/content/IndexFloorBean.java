package jp.co.sint.webshop.web.bean.back.content;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * 首页楼层内容管理。
 * 
 * @author Kousen.
 */
public class IndexFloorBean extends UIBackBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private String languageCode;

  private String displayMode;

  private String floorCode;

  private String title;

  private List<CodeAttribute> floorList = new ArrayList<CodeAttribute>();

  private List<IndexFloorDetailBean> list = new ArrayList<IndexFloorDetailBean>();

  private IndexFloorDetailBean editBean = new IndexFloorDetailBean();

  public static class IndexFloorDetailBean implements Serializable {

    /**
     * 图片信息
     */
    private static final long serialVersionUID = 1L;

    private String imgName;

    private String imgUrl;

    private String imageContents;

    @Length(100)
    @Metadata(name = "图片连接", order = 1)
    private String imgLink;

    @Length(100)
    @Metadata(name = "图片ALT", order = 2)
    private String imgAlt;

    /**
     * @return the imgName
     */
    public String getImgName() {
      return imgName;
    }

    /**
     * @return the imgUrl
     */
    public String getImgUrl() {
      return imgUrl;
    }

    /**
     * @return the imgLink
     */
    public String getImgLink() {
      return imgLink;
    }

    /**
     * @return the imgAlt
     */
    public String getImgAlt() {
      return imgAlt;
    }

    /**
     * @param imgName
     *          the imgName to set
     */
    public void setImgName(String imgName) {
      this.imgName = imgName;
    }

    /**
     * @param imgUrl
     *          the imgUrl to set
     */
    public void setImgUrl(String imgUrl) {
      this.imgUrl = imgUrl;
    }

    /**
     * @param imgLink
     *          the imgLink to set
     */
    public void setImgLink(String imgLink) {
      this.imgLink = imgLink;
    }

    /**
     * @param imgAlt
     *          the imgAlt to set
     */
    public void setImgAlt(String imgAlt) {
      this.imgAlt = imgAlt;
    }

    /**
     * @return the imageContents
     */
    public String getImageContents() {
      return imageContents;
    }

    /**
     * @param imageContents
     *          the imageContents to set
     */
    public void setImageContents(String imageContents) {
      this.imageContents = imageContents;
    }

  }

  /**
   * @return the languageCode
   */
  public String getLanguageCode() {
    return languageCode;
  }

  /**
   * @return the list
   */
  public List<IndexFloorDetailBean> getList() {
    return list;
  }

  /**
   * @return the editBean
   */
  public IndexFloorDetailBean getEditBean() {
    return editBean;
  }

  /**
   * @param languageCode
   *          the languageCode to set
   */
  public void setLanguageCode(String languageCode) {
    this.languageCode = languageCode;
  }

  /**
   * @param list
   *          the list to set
   */
  public void setList(List<IndexFloorDetailBean> list) {
    this.list = list;
  }

  /**
   * @param editBean
   *          the editBean to set
   */
  public void setEditBean(IndexFloorDetailBean editBean) {
    this.editBean = editBean;
  }

  /**
   * @return the displayMode
   */
  public String getDisplayMode() {
    return displayMode;
  }

  /**
   * @param displayMode
   *          the displayMode to set
   */
  public void setDisplayMode(String displayMode) {
    this.displayMode = displayMode;
  }

  /**
   * @return the floorList
   */
  public List<CodeAttribute> getFloorList() {
    return floorList;
  }

  /**
   * @param floorList
   *          the floorList to set
   */
  public void setFloorList(List<CodeAttribute> floorList) {
    this.floorList = floorList;
  }

  /**
   * @return the floorCode
   */
  public String getFloorCode() {
    return floorCode;
  }

  /**
   * @param floorCode
   *          the floorCode to set
   */
  public void setFloorCode(String floorCode) {
    this.floorCode = floorCode;
  }

  /**
   * @return the title
   */
  public String getTitle() {
    return title;
  }

  /**
   * @param title
   *          the title to set
   */
  public void setTitle(String title) {
    this.title = title;
  }

  /**
   * サブJSPを設定します。
   */
  @Override
  public void setSubJspId() {
  }

  /**
   * リクエストパラメータから値を取得します。
   * 
   * @param reqparam
   *          リクエストパラメータ
   */
  public void createAttributes(RequestParameter reqparam) {
    reqparam.copy(this);
  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U1100210";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.back.content.IndexFloorBean.0");
  }

}
