package jp.co.sint.webshop.web.bean.back.catalog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Range;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.web.bean.UISearchBean;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerValue;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U1040410:タグマスタのデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class TagBean extends UIBackBean implements UISearchBean {

  private static final long serialVersionUID = 1L;

  private String mode;

  private TagDetailBean edit = new TagDetailBean();

  private List<TagDetailBean> list = new ArrayList<TagDetailBean>();

  private List<CodeAttribute> shopList;

  @Required
  @Length(16)
  @AlphaNum2
  @Metadata(name = "ショップ")
  private String searchShopCode;

  @Length(16)
  @AlphaNum2
  @Metadata(name = "タグコード(From)")
  private String searchTagCodeStart;

  @Length(16)
  @AlphaNum2
  @Metadata(name = "タグコード(To)")
  private String searchTagCodeEnd;

  @Length(50)
  @Metadata(name = "タグ名")
  private String searchTagName;

  private boolean searchButtonDisplayFlg;

  private boolean registerTableDisplayFlg;

  private boolean updateButtonDisplayFlg;

  private boolean registerButtonDisplayFlg;

  private PagerValue pagerValue;

  /**
   * U1040410:タグマスタのデータモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class TagDetailBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Length(16)
    @AlphaNum2
    @Metadata(name = "ショップコード")
    private String shopCode;

    @Required
    @Length(16)
    @AlphaNum2
    @Metadata(name = "タグコード")
    private String tagCode;

    @Required
    @Length(50)
    @Metadata(name = "タグ名称")
    private String tagName;
    
    //20120514 tuxinwei add start
    @Required
    @Length(50)
    @Metadata(name = "タグ名称(英文)")
    private String tagNameEn;
    
    @Required
    @Length(50)
    @Metadata(name = "タグ名称(日本语)")
    private String tagNameJp;
    //20120514 tuxinwei add end

    @Digit
    @Length(8)
    @Range(min = 0, max = 99999999)
    @Metadata(name = "表示順")
    private String displayOrder;

    private Date updatedDatetime;

    private boolean deleteButtonDisplayFlg;

    /**
     * shopCodeを設定します。
     * 
     * @param shopCode
     *          shopCode
     */
    public void setShopCode(String shopCode) {
      this.shopCode = shopCode;
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
     * tagCodeを設定します。
     * 
     * @param tagCode
     *          tagCode
     */
    public void setTagCode(String tagCode) {
      this.tagCode = tagCode;
    }

    /**
     * tagCodeを取得します。
     * 
     * @return tagCode
     */
    public String getTagCode() {
      return tagCode;
    }

    /**
     * tagNameを設定します。
     * 
     * @param tagName
     *          tagName
     */
    public void setTagName(String tagName) {
      this.tagName = tagName;
    }

    /**
     * tagNameを取得します。
     * 
     * @return tagName
     */
    public String getTagName() {
      return tagName;
    }

    /**
     * displayOrderを設定します。
     * 
     * @param displayOrder
     *          displayOrder
     */
    public void setDisplayOrder(String displayOrder) {
      this.displayOrder = displayOrder;
    }

    /**
     * displayOrderを取得します。
     * 
     * @return displayOrder
     */
    public String getDisplayOrder() {
      return displayOrder;
    }

    /**
     * updatedDatetimeを設定します。
     * 
     * @param updatedDatetime
     *          updatedDatetime
     */
    public void setUpdatedDatetime(Date updatedDatetime) {
      this.updatedDatetime = DateUtil.immutableCopy(updatedDatetime);
    }

    /**
     * updatedDatetimeを取得します。
     * 
     * @return updatedDatetime
     */
    public Date getUpdatedDatetime() {
      return DateUtil.immutableCopy(updatedDatetime);
    }

    /**
     * deleteButtonDisplayFlgを取得します。
     * 
     * @return deleteButtonDisplayFlg
     */
    public boolean isDeleteButtonDisplayFlg() {
      return deleteButtonDisplayFlg;
    }

    /**
     * deleteButtonDisplayFlgを設定します。
     * 
     * @param deleteButtonDisplayFlg
     *          deleteButtonDisplayFlg
     */
    public void setDeleteButtonDisplayFlg(boolean deleteButtonDisplayFlg) {
      this.deleteButtonDisplayFlg = deleteButtonDisplayFlg;
    }

    
    /**
     * @return the tagNameEn
     */
    public String getTagNameEn() {
      return tagNameEn;
    }

    
    /**
     * @param tagNameEn the tagNameEn to set
     */
    public void setTagNameEn(String tagNameEn) {
      this.tagNameEn = tagNameEn;
    }

    
    /**
     * @return the tagNameJp
     */
    public String getTagNameJp() {
      return tagNameJp;
    }

    
    /**
     * @param tagNameJp the tagNameJp to set
     */
    public void setTagNameJp(String tagNameJp) {
      this.tagNameJp = tagNameJp;
    }

  }

  /**
   * modeを取得します。
   * 
   * @return mode
   */
  public String getMode() {
    return mode;
  }

  /**
   * modeを設定します。
   * 
   * @param mode
   *          mode
   */
  public void setMode(String mode) {
    this.mode = mode;
  }

  /**
   * editを設定します。
   * 
   * @param edit
   *          edit
   */
  public void setEdit(TagDetailBean edit) {
    this.edit = edit;
  }

  /**
   * editを取得します。
   * 
   * @return edit
   */
  public TagDetailBean getEdit() {
    return edit;
  }

  /**
   * タグリストを設定します。
   * 
   * @param list
   *          list
   */
  public void setList(List<TagDetailBean> list) {
    this.list = list;
  }

  /**
   * タグリストを取得します。
   * 
   * @return list
   */
  public List<TagDetailBean> getList() {
    return list;
  }

  /**
   * searchShopCodeを取得します。
   * 
   * @return searchShopCode
   */
  public String getSearchShopCode() {
    return searchShopCode;
  }

  /**
   * shopListを取得します。
   * 
   * @return shopList
   */
  public List<CodeAttribute> getShopList() {
    return shopList;
  }

  /**
   * searchShopCodeを設定します。
   * 
   * @param searchShopCode
   *          searchShopCode
   */
  public void setSearchShopCode(String searchShopCode) {
    this.searchShopCode = searchShopCode;
  }

  /**
   * shopListを設定します。
   * 
   * @param shopList
   *          shopList
   */
  public void setShopList(List<CodeAttribute> shopList) {
    this.shopList = shopList;
  }

  /**
   * registerButtonDisplayFlgを取得します。
   * 
   * @return registerButtonDisplayFlg
   */
  public boolean isRegisterButtonDisplayFlg() {
    return registerButtonDisplayFlg;
  }

  /**
   * registerButtonDisplayFlgを設定します。
   * 
   * @param registerButtonDisplayFlg
   *          registerButtonDisplayFlg
   */
  public void setRegisterButtonDisplayFlg(boolean registerButtonDisplayFlg) {
    this.registerButtonDisplayFlg = registerButtonDisplayFlg;
  }

  /**
   * registerTableDisplayFlgを取得します。
   * 
   * @return registerTableDisplayFlg
   */
  public boolean isRegisterTableDisplayFlg() {
    return registerTableDisplayFlg;
  }

  /**
   * registerTableDisplayFlgを設定します。
   * 
   * @param registerTableDisplayFlg
   *          registerTableDisplayFlg
   */
  public void setRegisterTableDisplayFlg(boolean registerTableDisplayFlg) {
    this.registerTableDisplayFlg = registerTableDisplayFlg;
  }

  /**
   * searchButtonDisplayFlgを取得します。
   * 
   * @return searchButtonDisplayFlg
   */
  public boolean isSearchButtonDisplayFlg() {
    return searchButtonDisplayFlg;
  }

  /**
   * searchButtonDisplayFlgを設定します。
   * 
   * @param searchButtonDisplayFlg
   *          searchButtonDisplayFlg
   */
  public void setSearchButtonDisplayFlg(boolean searchButtonDisplayFlg) {
    this.searchButtonDisplayFlg = searchButtonDisplayFlg;
  }

  /**
   * updateButtonDisplayFlgを取得します。
   * 
   * @return updateButtonDisplayFlg
   */
  public boolean isUpdateButtonDisplayFlg() {
    return updateButtonDisplayFlg;
  }

  /**
   * updateButtonDisplayFlgを設定します。
   * 
   * @param updateButtonDisplayFlg
   *          updateButtonDisplayFlg
   */
  public void setUpdateButtonDisplayFlg(boolean updateButtonDisplayFlg) {
    this.updateButtonDisplayFlg = updateButtonDisplayFlg;
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
    String[] param = reqparam.getPathArgs();

    if (param.length <= 0) {
      searchShopCode = reqparam.get("shopList");
      searchTagCodeStart = reqparam.get("searchTagCodeStart");
      searchTagCodeEnd = reqparam.get("searchTagCodeEnd");
      searchTagName = reqparam.get("searchTagName");
    }
    reqparam.copy(edit);
  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U1040410";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.back.catalog.TagBean.0");
  }

  /**
   * pagerValueを設定します。
   * 
   * @param pagerValue
   *          pagerValue
   */
  public void setPagerValue(PagerValue pagerValue) {
    this.pagerValue = pagerValue;
  }

  /**
   * pagerValueを取得します。
   * 
   * @return pagerValue
   */
  public PagerValue getPagerValue() {
    return pagerValue;
  }

  /**
   * searchTagCodeEndを取得します。
   * 
   * @return searchTagCodeEnd
   */
  public String getSearchTagCodeEnd() {
    return searchTagCodeEnd;
  }

  /**
   * searchTagCodeEndを設定します。
   * 
   * @param searchTagCodeEnd
   *          searchTagCodeEnd
   */
  public void setSearchTagCodeEnd(String searchTagCodeEnd) {
    this.searchTagCodeEnd = searchTagCodeEnd;
  }

  /**
   * searchTagCodeStartを取得します。
   * 
   * @return searchTagCodeStart
   */
  public String getSearchTagCodeStart() {
    return searchTagCodeStart;
  }

  /**
   * searchTagCodeStartを設定します。
   * 
   * @param searchTagCodeStart
   *          searchTagCodeStart
   */
  public void setSearchTagCodeStart(String searchTagCodeStart) {
    this.searchTagCodeStart = searchTagCodeStart;
  }

  /**
   * searchTagNameを取得します。
   * 
   * @return searchTagName
   */
  public String getSearchTagName() {
    return searchTagName;
  }

  /**
   * searchTagNameを設定します。
   * 
   * @param searchTagName
   *          searchTagName
   */
  public void setSearchTagName(String searchTagName) {
    this.searchTagName = searchTagName;
  }

}
