package jp.co.sint.webshop.web.bean.back.catalog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.data.domain.BrandType;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.web.bean.UISearchBean;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerValue;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U1040910:品牌管理。
 * 
 * @author System Integrator Corp.
 */
public class BrandListBean extends UIBackBean implements UISearchBean {

  private static final long serialVersionUID = 1L;

  private String mode;

  private BrandDetailBean edit = new BrandDetailBean();

  private List<BrandDetailBean> list = new ArrayList<BrandDetailBean>();

  private List<CodeAttribute> shopList;

  @Required
  @Length(16)
  @AlphaNum2
  @Metadata(name = "ショップ")
  private String searchShopCode;

  @Length(16)
  @AlphaNum2
  @Metadata(name = "品牌名称编号")
  private String searchBrandCode;

  @Length(50)
  @Metadata(name = "品牌名称")
  private String searchBrandName;

  @Length(50)
  @AlphaNum2
  @Metadata(name = "品牌英文名称")
  private String searchBrandEnglishName;

  // 20120515 tuxinwei add start
  @Length(50)
  @AlphaNum2
  @Metadata(name = "品牌日文名称")
  private String searchBrandJapaneseName;

  // 20120515 tuxinwei add end

  private String brandEditDisplayMode;

  private boolean searchButtonDisplayFlg;

  private boolean registerTableDisplayFlg;

  private boolean updateButtonDisplayFlg;

  private boolean registerButtonDisplayFlg;

  private boolean searchTableDisplayFlg;

  private boolean searchResultTableDisplayFlg;

  private boolean displayNextButton = false;

  private PagerValue pagerValue;

  private List<CodeAttribute> brandTypeList = new ArrayList<CodeAttribute>();

  /**
   * U1040910:品牌管理明细。
   * 
   * @author System Integrator Corp.
   */
  public static class BrandDetailBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Length(16)
    @AlphaNum2
    @Metadata(name = "ショップコード")
    private String shopCode;

    @Required
    @Length(16)
    @AlphaNum2
    @Metadata(name = "品牌名称编号")
    private String brandCode;

    @Required
    @Length(50)
    @Metadata(name = "品牌名称")
    private String brandName;

    @Length(50)
    @Metadata(name = "品牌名称略称")
    private String brandNameAbbr;

    @Length(50)
    // @AlphaNum2
    @Metadata(name = "品牌英文名称")
    private String brandEnglishName;

    // 20120515 tuxinwei add start
    @Length(50)
    @Metadata(name = "品牌日文名称")
    private String brandJapaneseName;

    // 20120515 tuxinwei add end

    @Length(1000)
    @Metadata(name = "品牌说明")
    private String brandDescription;

    // 20120524 tuxinwei add start
    @Length(1000)
    @Metadata(name = "品牌说明(英文)")
    private String brandDescriptionEn;

    @Length(1000)
    @Metadata(name = "品牌说明(日文)")
    private String brandDescriptionJp;

    // 20120524 tuxinwei add end

    /** 淘宝品牌ID */
    @Required
    @Length(16)
    @AlphaNum2
    @Metadata(name = "淘宝品牌ID")
    private String tmallBrandCode;

    /** 淘宝品牌名 */
    @Length(50)
    @Metadata(name = "淘宝品牌名称 ")
    private String tmallBrandName;

    @Length(2)
    @Metadata(name = "品牌区分")
    private Long brandType = 0L;

    // add by zhangzhengtao 20130527 start
    /** 検索Keyword（中文）2 */
    @Length(30)
    @Metadata(name = " 検索Keyword（中文）2")
    private String keywordCn2;

    /** 検索Keyword（日文）2 */
    @Length(30)
    @Metadata(name = " 検索Keyword（日文）2")
    private String keywordJp2;

    /** 検索Keyword（英文）2 */
    @Length(30)
    @Metadata(name = " 検索Keyword（英文）2")
    private String keywordEn2;

    // add by zhangzhengtao 20130527 end

    // 20130808 txw add start
    @Length(60)
    @Metadata(name = "TITLE")
    private String title;

    @Length(60)
    @Metadata(name = "TITLE(英文)")
    private String titleEn;

    @Length(60)
    @Metadata(name = "TITLE(日文)")
    private String titleJp;

    @Length(100)
    @Metadata(name = "DESCRIPTION")
    private String description;

    @Length(100)
    @Metadata(name = "DESCRIPTION(英文)")
    private String descriptionEn;

    @Length(100)
    @Metadata(name = "DESCRIPTION(日文)")
    private String descriptionJp;

    @Length(100)
    @Metadata(name = "KEYWORD")
    private String keyword;

    @Length(100)
    @Metadata(name = "KEYWORD(英文)")
    private String keywordEn;

    @Length(100)
    @Metadata(name = "KEYWORD(日文)")
    private String keywordJp;

    // 20130808 txw add end

    /**
     * @return the brandType
     */
    public Long getBrandType() {
      return brandType;
    }

    /**
     * @return the title
     */
    public String getTitle() {
      return title;
    }

    /**
     * @return the titleEn
     */
    public String getTitleEn() {
      return titleEn;
    }

    /**
     * @return the titleJp
     */
    public String getTitleJp() {
      return titleJp;
    }

    /**
     * @return the description
     */
    public String getDescription() {
      return description;
    }

    /**
     * @return the descriptionEn
     */
    public String getDescriptionEn() {
      return descriptionEn;
    }

    /**
     * @return the descriptionJp
     */
    public String getDescriptionJp() {
      return descriptionJp;
    }

    /**
     * @return the keyword
     */
    public String getKeyword() {
      return keyword;
    }

    /**
     * @return the keywordEn
     */
    public String getKeywordEn() {
      return keywordEn;
    }

    /**
     * @return the keywordJp
     */
    public String getKeywordJp() {
      return keywordJp;
    }

    /**
     * @param title
     *          the title to set
     */
    public void setTitle(String title) {
      this.title = title;
    }

    /**
     * @param titleEn
     *          the titleEn to set
     */
    public void setTitleEn(String titleEn) {
      this.titleEn = titleEn;
    }

    /**
     * @param titleJp
     *          the titleJp to set
     */
    public void setTitleJp(String titleJp) {
      this.titleJp = titleJp;
    }

    /**
     * @param description
     *          the description to set
     */
    public void setDescription(String description) {
      this.description = description;
    }

    /**
     * @param descriptionEn
     *          the descriptionEn to set
     */
    public void setDescriptionEn(String descriptionEn) {
      this.descriptionEn = descriptionEn;
    }

    /**
     * @param descriptionJp
     *          the descriptionJp to set
     */
    public void setDescriptionJp(String descriptionJp) {
      this.descriptionJp = descriptionJp;
    }

    /**
     * @param keyword
     *          the keyword to set
     */
    public void setKeyword(String keyword) {
      this.keyword = keyword;
    }

    /**
     * @param keywordEn
     *          the keywordEn to set
     */
    public void setKeywordEn(String keywordEn) {
      this.keywordEn = keywordEn;
    }

    /**
     * @param keywordJp
     *          the keywordJp to set
     */
    public void setKeywordJp(String keywordJp) {
      this.keywordJp = keywordJp;
    }

    /**
     * @return the keywordCn2
     */
    public String getKeywordCn2() {
      return keywordCn2;
    }

    /**
     * @param keywordCn2
     *          the keywordCn2 to set
     */
    public void setKeywordCn2(String keywordCn2) {
      this.keywordCn2 = keywordCn2;
    }

    /**
     * @return the keywordJp2
     */
    public String getKeywordJp2() {
      return keywordJp2;
    }

    /**
     * @param keywordJp2
     *          the keywordJp2 to set
     */
    public void setKeywordJp2(String keywordJp2) {
      this.keywordJp2 = keywordJp2;
    }

    /**
     * @return the keywordEn2
     */
    public String getKeywordEn2() {
      return keywordEn2;
    }

    /**
     * @param keywordEn2
     *          the keywordEn2 to set
     */
    public void setKeywordEn2(String keywordEn2) {
      this.keywordEn2 = keywordEn2;
    }

    /**
     * @param brandType
     *          the brandType to set
     */
    public void setBrandType(Long brandType) {
      this.brandType = brandType;
    }

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
     * BrandCodeを設定します。
     * 
     * @param BrandCode
     *          BrandCode
     */
    public void setBrandCode(String brandCode) {
      this.brandCode = brandCode;
    }

    /**
     * BrandCodeを取得します。
     * 
     * @return BrandCode
     */
    public String getBrandCode() {
      return brandCode;
    }

    /**
     * BrandNameを設定します。
     * 
     * @param BrandName
     *          BrandName
     */
    public void setBrandName(String brandName) {
      this.brandName = brandName;
    }

    /**
     * BrandNameを取得します。
     * 
     * @return BrandName
     */
    public String getBrandName() {
      return brandName;
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
     * @param brandNameAbbr
     *          the brandNameAbbr to set
     */
    public void setBrandNameAbbr(String brandNameAbbr) {
      this.brandNameAbbr = brandNameAbbr;
    }

    /**
     * @return the brandNameAbbr
     */
    public String getBrandNameAbbr() {
      return brandNameAbbr;
    }

    /**
     * @param brandEnglishName
     *          the brandEnglishName to set
     */
    public void setBrandEnglishName(String brandEnglishName) {
      this.brandEnglishName = brandEnglishName;
    }

    /**
     * @return the brandEnglishName
     */
    public String getBrandEnglishName() {
      return brandEnglishName;
    }

    /**
     * @param brandDescription
     *          the brandDescription to set
     */
    public void setBrandDescription(String brandDescription) {
      this.brandDescription = brandDescription;
    }

    /**
     * @return the brandDescription
     */
    public String getBrandDescription() {
      return brandDescription;
    }

    /**
     * @param tmallBrandName
     *          the tmallBrandName to set
     */
    public void setTmallBrandName(String tmallBrandName) {
      this.tmallBrandName = tmallBrandName;
    }

    /**
     * @return the tmallBrandName
     */
    public String getTmallBrandName() {
      return tmallBrandName;
    }

    /**
     * @param tmallBrandCode
     *          the tmallBrandCode to set
     */
    public void setTmallBrandCode(String tmallBrandCode) {
      this.tmallBrandCode = tmallBrandCode;
    }

    /**
     * @return the tmallBrandCode
     */
    public String getTmallBrandCode() {
      return tmallBrandCode;
    }

    /**
     * @return the brandJapaneseName
     */
    public String getBrandJapaneseName() {
      return brandJapaneseName;
    }

    /**
     * @param brandJapaneseName
     *          the brandJapaneseName to set
     */
    public void setBrandJapaneseName(String brandJapaneseName) {
      this.brandJapaneseName = brandJapaneseName;
    }

    /**
     * @return the brandDescriptionEn
     */
    public String getBrandDescriptionEn() {
      return brandDescriptionEn;
    }

    /**
     * @param brandDescriptionEn
     *          the brandDescriptionEn to set
     */
    public void setBrandDescriptionEn(String brandDescriptionEn) {
      this.brandDescriptionEn = brandDescriptionEn;
    }

    /**
     * @return the brandDescriptionJp
     */
    public String getBrandDescriptionJp() {
      return brandDescriptionJp;
    }

    /**
     * @param brandDescriptionJp
     *          the brandDescriptionJp to set
     */
    public void setBrandDescriptionJp(String brandDescriptionJp) {
      this.brandDescriptionJp = brandDescriptionJp;
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
  public void setEdit(BrandDetailBean edit) {
    this.edit = edit;
  }

  /**
   * editを取得します。
   * 
   * @return edit
   */
  public BrandDetailBean getEdit() {
    return edit;
  }

  /**
   * タグリストを設定します。
   * 
   * @param list
   *          list
   */
  public void setList(List<BrandDetailBean> list) {
    this.list = list;
  }

  /**
   * タグリストを取得します。
   * 
   * @return list
   */
  public List<BrandDetailBean> getList() {
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
      searchBrandName = reqparam.get("searchBrandName");
      searchBrandCode = reqparam.get("searchBrandCode");
      searchBrandEnglishName = reqparam.get("searchBrandEnglishName");
      // 20120515 tuxinwei add start
      searchBrandJapaneseName = reqparam.get("searchBrandJapaneseName");
      // 20120515 tuxinwei add end

    }
    reqparam.copy(edit);
    edit.setBrandType(NumUtil.toLong(reqparam.get("brandType")));
  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U1040910";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.back.catalog.BrandListBean.0");
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
   * searchBrandNameを取得します。
   * 
   * @return searchBrandName
   */
  public String getSearchBrandName() {
    return searchBrandName;
  }

  /**
   * searchBrandNameを設定します。
   * 
   * @param searchBrandName
   *          searchBrandName
   */
  public void setSearchBrandName(String searchBrandName) {
    this.searchBrandName = searchBrandName;
  }

  /**
   * @param searchBrandCode
   *          the searchBrandCode to set
   */
  public void setSearchBrandCode(String searchBrandCode) {
    this.searchBrandCode = searchBrandCode;
  }

  /**
   * @return the searchBrandCode
   */
  public String getSearchBrandCode() {
    return searchBrandCode;
  }

  // /**
  // * @param searchBrandNameAbbr the searchBrandNameAbbr to set
  // */
  // public void setSearchBrandNameAbbr(String searchBrandNameAbbr) {
  // this.searchBrandNameAbbr = searchBrandNameAbbr;
  // }
  //
  // /**
  // * @return the searchBrandNameAbbr
  // */
  // public String getSearchBrandNameAbbr() {
  // return searchBrandNameAbbr;
  // }

  /**
   * @param searchBrandEnglishName
   *          the searchBrandEnglishName to set
   */
  public void setSearchBrandEnglishName(String searchBrandEnglishName) {
    this.searchBrandEnglishName = searchBrandEnglishName;
  }

  /**
   * @return the searchBrandEnglishName
   */
  public String getSearchBrandEnglishName() {
    return searchBrandEnglishName;
  }

  /**
   * @param searchTableDisplayFlg
   *          the searchTableDisplayFlg to set
   */
  public void setSearchTableDisplayFlg(boolean searchTableDisplayFlg) {
    this.searchTableDisplayFlg = searchTableDisplayFlg;
  }

  /**
   * @return the searchTableDisplayFlg
   */
  public boolean isSearchTableDisplayFlg() {
    return searchTableDisplayFlg;
  }

  /**
   * @param searchResultTableDisplayFlg
   *          the searchResultTableDisplayFlg to set
   */
  public void setSearchResultTableDisplayFlg(boolean searchResultTableDisplayFlg) {
    this.searchResultTableDisplayFlg = searchResultTableDisplayFlg;
  }

  /**
   * @return the searchResultTableDisplayFlg
   */
  public boolean isSearchResultTableDisplayFlg() {
    return searchResultTableDisplayFlg;
  }

  /**
   * @return the brandEditDisplayMode
   */
  public String getBrandEditDisplayMode() {
    return brandEditDisplayMode;
  }

  /**
   * @param brandEditDisplayMode
   *          the brandEditDisplayMode to set
   */
  public void setBrandEditDisplayMode(String brandEditDisplayMode) {
    this.brandEditDisplayMode = brandEditDisplayMode;
  }

  /**
   * @return the displayNextButton
   */
  public boolean isDisplayNextButton() {
    return displayNextButton;
  }

  /**
   * @param displayNextButton
   *          the displayNextButton to set
   */
  public void setDisplayNextButton(boolean displayNextButton) {
    this.displayNextButton = displayNextButton;
  }

  /**
   * @return the brandTypeList
   */
  public List<CodeAttribute> getBrandTypeList() {
    brandTypeList = new ArrayList<CodeAttribute>();
    brandTypeList.addAll(Arrays.asList(BrandType.values()));
    return brandTypeList;
  }

  /**
   * @param brandTypeList
   *          the brandTypeList to set
   */
  public void setBrandTypeList(List<CodeAttribute> brandTypeList) {
    this.brandTypeList = brandTypeList;
  }

  /**
   * @return the searchBrandJapaneseName
   */
  public String getSearchBrandJapaneseName() {
    return searchBrandJapaneseName;
  }

  /**
   * @param searchBrandJapaneseName
   *          the searchBrandJapaneseName to set
   */
  public void setSearchBrandJapaneseName(String searchBrandJapaneseName) {
    this.searchBrandJapaneseName = searchBrandJapaneseName;
  }

}
