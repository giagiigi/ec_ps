package jp.co.sint.webshop.web.bean.back.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U1080310:ファイルアップロードのデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class FileUploadBean extends UIBackBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  /** コンテンツタイプの初期値 */
  public static final String DEFAULT_CONTENTS_TYPE_CONDITION = "000000";

  @Required
  @Metadata(name = "コンテンツタイプ", order = 2)
  private String contentsTypeCondition = DEFAULT_CONTENTS_TYPE_CONDITION;

  private List<CodeAttribute> contentsTypeList = new ArrayList<CodeAttribute>();

  @Metadata(name = "カテゴリトップ", order = 3)
  private String categoryTopCondition;

  private List<CodeAttribute> categoryTopList = new ArrayList<CodeAttribute>();

  @Metadata(name = "キャンペーン", order = 4)
  private String campaignCondition;

  private List<CodeAttribute> campaignList = new ArrayList<CodeAttribute>();

  @Required
  @Metadata(name = "ファイルパス", order = 5)
  private String uploadFilePath;

  private String nextUrl;

  private String shopCode;

  /** アップロード権限有無 true:アップロードボタン表示 false:アップロードボタン非表示 */
  private Boolean uploadAuthority = false;

  /** カテゴリトップ表示有無 true:カテゴリドロップダウン表示 false:カテゴリドロップダウン非表示 */
  private Boolean categoryTopDisplay = false;

  /** キャンペーン表示有無 true:キャンペーンドロップダウン表示 false:キャンペーンドロップダウン表示 */
  private Boolean campaignDisplay = false;

  private List<FileUploadDetailBean> searchResultList = new ArrayList<FileUploadDetailBean>();

  /**
   * U1080310:ファイルアップロードのサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class FileUploadDetailBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String uploadFilePath;

    private String uploadFileName;

    private String uploadFileSize;

    private String updatedDateTime;

    private boolean isDirectory;

    private String treeText;

    private String checkBoxId;

    /**
     * checkBoxIdを取得します。
     * 
     * @return checkBoxId
     */
    public String getCheckBoxId() {
      return checkBoxId;
    }

    /**
     * checkBoxIdを設定します。
     * 
     * @param checkBoxId
     *          checkBoxId
     */
    public void setCheckBoxId(String checkBoxId) {
      this.checkBoxId = checkBoxId;
    }

    /**
     * updatedDateTimeを取得します。
     * 
     * @return updatedDateTime
     */
    public String getUpdatedDateTime() {
      return updatedDateTime;
    }

    /**
     * uploadFileNameを取得します。
     * 
     * @return uploadFileName
     */
    public String getUploadFileName() {
      return uploadFileName;
    }

    /**
     * uploadFilePathを取得します。
     * 
     * @return uploadFilePath
     */
    public String getUploadFilePath() {
      return uploadFilePath;
    }

    /**
     * updatedDateTimeを設定します。
     * 
     * @param updatedDateTime
     *          updatedDateTime
     */
    public void setUpdatedDateTime(String updatedDateTime) {
      this.updatedDateTime = updatedDateTime;
    }

    /**
     * uploadFileNameを設定します。
     * 
     * @param uploadFileName
     *          uploadFileName
     */
    public void setUploadFileName(String uploadFileName) {
      this.uploadFileName = uploadFileName;
    }

    /**
     * uploadFilePathを設定します。
     * 
     * @param uploadFilePath
     *          uploadFilePath
     */
    public void setUploadFilePath(String uploadFilePath) {
      this.uploadFilePath = uploadFilePath;
    }

    /**
     * uploadFileSizeを取得します。
     * 
     * @return uploadFileSize
     */
    public String getUploadFileSize() {
      return uploadFileSize;
    }

    /**
     * uploadFileSizeを設定します。
     * 
     * @param uploadFileSize
     *          uploadFileSize
     */
    public void setUploadFileSize(String uploadFileSize) {
      this.uploadFileSize = uploadFileSize;
    }

    /**
     * isDirectoryを取得します。
     * 
     * @return isDirectory
     */
    public boolean isDirectory() {
      return isDirectory;
    }

    /**
     * isDirectoryを設定します。
     * 
     * @param isDirectoryFlg
     *          ディレクトリ有無 true:ディレクトリ false:ファイル
     */
    public void setDirectory(boolean isDirectoryFlg) {
      this.isDirectory = isDirectoryFlg;
    }

    /**
     * treeTextを取得します。
     * 
     * @return treeText
     */
    public String getTreeText() {
      return treeText;
    }

    /**
     * treeTextを設定します。
     * 
     * @param treeText
     *          treeText
     */
    public void setTreeText(String treeText) {
      this.treeText = treeText;
    }

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
    if (reqparam.get("contentsTypeCondition").equals("")) {
      setContentsTypeCondition(DEFAULT_CONTENTS_TYPE_CONDITION);
    } else {
      setContentsTypeCondition(reqparam.get("contentsTypeCondition"));
    }
    if (reqparam.get("categoryTopCondition").equals("")) {
      setCategoryTopCondition("");
    } else {
      setCategoryTopCondition(reqparam.get("categoryTopCondition"));
    }
    if (reqparam.get("campaignCondition").equals("")) {
      setCampaignCondition("");
    } else {
      setCampaignCondition(reqparam.get("campaignCondition"));
    }

    setUploadFilePath(reqparam.get("uploadFilePath"));

  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U1080310";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.back.data.FileUploadBean.0");
  }

  /**
   * contentsTypeConditionを取得します。
   * 
   * @return contentsTypeCondition
   */
  public String getContentsTypeCondition() {
    return contentsTypeCondition;
  }

  /**
   * contentsTypeConditionを設定します。
   * 
   * @param contentsTypeCondition
   *          contentsTypeCondition
   */
  public void setContentsTypeCondition(String contentsTypeCondition) {
    this.contentsTypeCondition = contentsTypeCondition;
  }

  /**
   * contentsTypeListを取得します。
   * 
   * @return contentsTypeList
   */
  public List<CodeAttribute> getContentsTypeList() {
    return contentsTypeList;
  }

  /**
   * contentsTypeListを設定します。
   * 
   * @param contentsTypeList
   *          contentsTypeList
   */
  public void setContentsTypeList(List<CodeAttribute> contentsTypeList) {
    this.contentsTypeList = contentsTypeList;
  }

  /**
   * nextUrlを取得します。
   * 
   * @return nextUrl
   */
  public String getNextUrl() {
    return nextUrl;
  }

  /**
   * nextUrlを設定します。
   * 
   * @param nextUrl
   *          nextUrl
   */
  public void setNextUrl(String nextUrl) {
    this.nextUrl = nextUrl;
  }

  /**
   * uploadFilePathを取得します。
   * 
   * @return uploadFilePath
   */
  public String getUploadFilePath() {
    return uploadFilePath;
  }

  /**
   * uploadFilePathを設定します。
   * 
   * @param uploadFilePath
   *          uploadFilePath
   */
  public void setUploadFilePath(String uploadFilePath) {
    this.uploadFilePath = uploadFilePath;
  }

  /**
   * searchResultListを取得します。
   * 
   * @return searchResultList
   */
  public List<FileUploadDetailBean> getSearchResultList() {
    return searchResultList;
  }

  /**
   * searchResultListを設定します。
   * 
   * @param searchResultList
   *          searchResultList
   */
  public void setSearchResultList(List<FileUploadDetailBean> searchResultList) {
    this.searchResultList = searchResultList;
  }

  /**
   * uploadAuthorityを取得します。
   * 
   * @return uploadAuthority
   */
  public Boolean getUploadAuthority() {
    return uploadAuthority;
  }

  /**
   * uploadAuthorityを設定します。
   * 
   * @param uploadAuthority
   *          uploadAuthority
   */
  public void setUploadAuthority(Boolean uploadAuthority) {
    this.uploadAuthority = uploadAuthority;
  }

  /**
   * campaignConditionを取得します。
   * 
   * @return campaignCondition
   */
  public String getCampaignCondition() {
    return campaignCondition;
  }

  /**
   * campaignConditionを設定します。
   * 
   * @param campaignCondition
   *          campaignCondition
   */
  public void setCampaignCondition(String campaignCondition) {
    this.campaignCondition = campaignCondition;
  }

  /**
   * campaignDisplayを取得します。
   * 
   * @return campaignDisplay
   */
  public Boolean getCampaignDisplay() {
    return campaignDisplay;
  }

  /**
   * campaignDisplayを設定します。
   * 
   * @param campaignDisplay
   *          campaignDisplay
   */
  public void setCampaignDisplay(Boolean campaignDisplay) {
    this.campaignDisplay = campaignDisplay;
  }

  /**
   * campaignListを取得します。
   * 
   * @return campaignList
   */
  public List<CodeAttribute> getCampaignList() {
    return campaignList;
  }

  /**
   * campaignListを設定します。
   * 
   * @param campaignList
   *          campaignList
   */
  public void setCampaignList(List<CodeAttribute> campaignList) {
    this.campaignList = campaignList;
  }

  /**
   * categoryTopConditionを取得します。
   * 
   * @return categoryTopCondition
   */
  public String getCategoryTopCondition() {
    return categoryTopCondition;
  }

  /**
   * categoryTopConditionを設定します。
   * 
   * @param categoryTopCondition
   *          categoryTopCondition
   */
  public void setCategoryTopCondition(String categoryTopCondition) {
    this.categoryTopCondition = categoryTopCondition;
  }

  /**
   * categoryTopDisplayを取得します。
   * 
   * @return categoryTopDisplay
   */
  public Boolean getCategoryTopDisplay() {
    return categoryTopDisplay;
  }

  /**
   * categoryTopDisplayを設定します。
   * 
   * @param categoryTopDisplay
   *          categoryTopDisplay
   */
  public void setCategoryTopDisplay(Boolean categoryTopDisplay) {
    this.categoryTopDisplay = categoryTopDisplay;
  }

  /**
   * categoryTopListを取得します。
   * 
   * @return categoryTopList
   */
  public List<CodeAttribute> getCategoryTopList() {
    return categoryTopList;
  }

  /**
   * categoryTopListを設定します。
   * 
   * @param categoryTopList
   *          categoryTopList
   */
  public void setCategoryTopList(List<CodeAttribute> categoryTopList) {
    this.categoryTopList = categoryTopList;
  }

  /**
   * shopCodeを取得します。
   * 
   * @return shopCode
   */
  public String getShopCode() {
    return shopCode;
  }

  /**
   * shopCodeを設定します。
   * 
   * @param shopCode
   *          shopCode
   */
  public void setShopCode(String shopCode) {
    this.shopCode = shopCode;
  }

}
