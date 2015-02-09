package jp.co.sint.webshop.web.bean.back.catalog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Range;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.service.catalog.CategoryInfo;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U1040210:カテゴリのデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class CategoryBean extends UIBackBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private CategoryDetailBean edit = new CategoryDetailBean();

  private List<CategoryDetailBean> list = new ArrayList<CategoryDetailBean>();

  private String mode;

  private String fileUrl;

  private String categoryList;

  private boolean deleteButtonDisplayFlg = false;

  private boolean registerButtonDisplayFlg = false;

  private boolean uploadTableDisplayFlg = false;

  private boolean relateButtonDisplayFlg = false;

  private String editMode;

  private boolean isReadonly = false;

  private List<NameValue> csvHeaderType = NameValue.asList("true:" + Messages.getString("web.bean.back.catalog.CategoryBean.1")
      + "/false:" + Messages.getString("web.bean.back.catalog.CategoryBean.2"));

  // 10.1.2 10089 追加 ここから
  private List<CategoryInfo> categoryNodeInfoList;

  // 10.1.2 10089 追加 ここまで

  /**
   * U1040210:カテゴリのサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class CategoryAttributeBean implements Serializable {

    /** serial version uid */
    private static final long serialVersionUID = 1L;

    /** カテゴリ属性番号 */
    @Length(8)
    @Digit
    @Required
    @Metadata(name = "カテゴリ属性番号")
    private String categoryAttributeNo;

    /** カテゴリ属性名称 */
    @Length(15)
    @Metadata(name = "カテゴリ属性名")
    private String categoryAttributeName;

    /** カテゴリ属性名称 */
    @Length(30)
    @Metadata(name = "カテゴリ属性英文名")
    private String categoryAttributeNameEn;

    /** カテゴリ属性名称 */
    @Length(30)
    @Metadata(name = "カテゴリ属性日文名")
    private String categoryAttributeNameJp;

    /** データ行ID */
    @Length(38)
    @Metadata(name = "データ行ID")
    private Long ormRowid;

    /** 更新日時 */
    @Metadata(name = "更新日時")
    private Date updatedDatetime;

    /**
     * categoryAttributeNameを取得します。
     * 
     * @return categoryAttributeName
     */
    public String getCategoryAttributeName() {
      return categoryAttributeName;
    }

    /**
     * categoryAttributeNameを設定します。
     * 
     * @param categoryAttributeName
     *          categoryAttributeName
     */
    public void setCategoryAttributeName(String categoryAttributeName) {
      this.categoryAttributeName = categoryAttributeName;
    }

    /**
     * categoryAttributeNoを取得します。
     * 
     * @return categoryAttributeNo
     */
    public String getCategoryAttributeNo() {
      return categoryAttributeNo;
    }

    /**
     * categoryAttributeNoを設定します。
     * 
     * @param categoryAttributeNo
     *          categoryAttributeNo
     */
    public void setCategoryAttributeNo(String categoryAttributeNo) {
      this.categoryAttributeNo = categoryAttributeNo;
    }

    /**
     * ormRowidを取得します。
     * 
     * @return ormRowid
     */
    public Long getOrmRowid() {
      return ormRowid;
    }

    /**
     * ormRowidを設定します。
     * 
     * @param ormRowid
     *          ormRowid
     */
    public void setOrmRowid(Long ormRowid) {
      this.ormRowid = ormRowid;
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
     * updatedDatetimeを設定します。
     * 
     * @param updatedDatetime
     *          updatedDatetime
     */
    public void setUpdatedDatetime(Date updatedDatetime) {
      this.updatedDatetime = DateUtil.immutableCopy(updatedDatetime);
    }

    /**
     * @param categoryAttributeNameEn
     *          the categoryAttributeNameEn to set
     */
    public void setCategoryAttributeNameEn(String categoryAttributeNameEn) {
      this.categoryAttributeNameEn = categoryAttributeNameEn;
    }

    /**
     * @return the categoryAttributeNameEn
     */
    public String getCategoryAttributeNameEn() {
      return categoryAttributeNameEn;
    }

    /**
     * @param categoryAttributeNameJp
     *          the categoryAttributeNameJp to set
     */
    public void setCategoryAttributeNameJp(String categoryAttributeNameJp) {
      this.categoryAttributeNameJp = categoryAttributeNameJp;
    }

    /**
     * @return the categoryAttributeNameJp
     */
    public String getCategoryAttributeNameJp() {
      return categoryAttributeNameJp;
    }

  }

  /**
   * U1040210:カテゴリのサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class CategoryDetailBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /** 親カテゴリコード */
    @Required
    @Length(16)
    @AlphaNum2
    @Metadata(name = "親カテゴリ")
    private String parentCategoryCode;

    /** カテゴリコード */
    @Required
    @Length(16)
    @AlphaNum2
    @Metadata(name = "カテゴリコード")
    private String categoryCode;

    /** PC用カテゴリ名称 */
    @Required
    @Length(20)
    @Metadata(name = "PC用カテゴリ名")
    private String categoryNamePc;

    // 20120514 tuxinwei add start
    /** PC用カテゴリ名称 */
    @Required
    @Length(100)
    @Metadata(name = "PC用カテゴリ名(英文)")
    private String categoryNamePcEn;

    /** PC用カテゴリ名称 */
    @Required
    @Length(100)
    @Metadata(name = "PC用カテゴリ名(日本语)")
    private String categoryNamePcJp;

    // 20120514 tuxinwei add end

    /** 携帯用カテゴリ名称 */
    @Length(10)
    @Metadata(name = "携帯用カテゴリ名")
    private String categoryNameMobile;

    /** 表示順 */
    // 10.1.4 10143 削除 ここから
    // @Required
    // 10.1.4 10143 削除 ここまで
    @Length(4)
    @Range(min = 0, max = 9999)
    @Digit
    @Metadata(name = "表示順")
    private String displayOrder;

    /** PC商品件数 */
    @Length(12)
    @Metadata(name = "PC商品件数")
    private String commodityCountPc;

    /** 携帯商品件数 */
    @Length(12)
    @Metadata(name = "携帯商品件数")
    private String commodityCountMobile;

    /**
     * add by os012 20111221 start 淘宝分类编号
     */
    @Length(16)
    @Metadata(name = "淘宝分类编号")
    private String categoryIdTmall;
    
    //2014/4/28 京东WBS对应 ob_李 add start
    @AlphaNum2
    @Length(100)
    @Metadata(name = "京东分类编号")
    private String categoryIdJd;
    //2014/4/28 京东WBS对应 ob_李 add end

    // add by os012 20111221 end
    @Length(50)
    @Metadata(name = "meta keyword", order = 17)
    private String metaKeyword;

    @Length(50)
    @Metadata(name = "meta description", order = 18)
    private String metaDescription;

    /** 更新日時 */
    private Date updatedDatetime;

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
    private List<CategoryAttributeBean> attributeList = new ArrayList<CategoryAttributeBean>();

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
     * attributeListを取得します。
     * 
     * @return attributeList
     */
    public List<CategoryAttributeBean> getAttributeList() {
      return attributeList;
    }

    /**
     * attributeListを設定します。
     * 
     * @param attributeList
     *          attributeList
     */
    public void setAttributeList(List<CategoryAttributeBean> attributeList) {
      this.attributeList = attributeList;
    }

    /**
     * categoryCodeを取得します。
     * 
     * @return categoryCode
     */
    public String getCategoryCode() {
      return categoryCode;
    }

    /**
     * categoryCodeを設定します。
     * 
     * @param categoryCode
     *          categoryCode
     */
    public void setCategoryCode(String categoryCode) {
      this.categoryCode = categoryCode;
    }

    /**
     * categoryNameMobileを取得します。
     * 
     * @return categoryNameMobile
     */
    public String getCategoryNameMobile() {
      return categoryNameMobile;
    }

    /**
     * categoryNameMobileを設定します。
     * 
     * @param categoryNameMobile
     *          categoryNameMobile
     */
    public void setCategoryNameMobile(String categoryNameMobile) {
      this.categoryNameMobile = categoryNameMobile;
    }

    /**
     * commodityCountMobileを取得します。
     * 
     * @return commodityCountMobile
     */
    public String getCommodityCountMobile() {
      return commodityCountMobile;
    }

    /**
     * commodityCountMobileを設定します。
     * 
     * @param commodityCountMobile
     *          commodityCountMobile
     */
    public void setCommodityCountMobile(String commodityCountMobile) {
      this.commodityCountMobile = commodityCountMobile;
    }

    /**
     * commodityCountPcを取得します。
     * 
     * @return commodityCountPc
     */
    public String getCommodityCountPc() {
      return commodityCountPc;
    }

    /**
     * commodityCountPcを設定します。
     * 
     * @param commodityCountPc
     *          commodityCountPc
     */
    public void setCommodityCountPc(String commodityCountPc) {
      this.commodityCountPc = commodityCountPc;
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
     * displayOrderを設定します。
     * 
     * @param displayOrder
     *          displayOrder
     */
    public void setDisplayOrder(String displayOrder) {
      this.displayOrder = displayOrder;
    }

    /**
     * parentCategoryCodeを取得します。
     * 
     * @return parentCategoryCode
     */
    public String getParentCategoryCode() {
      return parentCategoryCode;
    }

    /**
     * parentCategoryCodeを設定します。
     * 
     * @param parentCategoryCode
     *          parentCategoryCode
     */
    public void setParentCategoryCode(String parentCategoryCode) {
      this.parentCategoryCode = parentCategoryCode;
    }

    /**
     * categoryNamePcを設定します。
     * 
     * @param categoryNamePc
     *          categoryNamePc
     */
    public void setCategoryNamePc(String categoryNamePc) {
      this.categoryNamePc = categoryNamePc;
    }

    /**
     * categoryNamePcを取得します。
     * 
     * @return categoryNamePc
     */
    public String getCategoryNamePc() {
      return categoryNamePc;
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
     * @param categoryIdTmall
     *          the categoryIdTmall to set
     */
    public void setCategoryIdTmall(String categoryIdTmall) {
      this.categoryIdTmall = categoryIdTmall;
    }

    /**
     * @return the categoryIdTmall
     */
    public String getCategoryIdTmall() {
      return categoryIdTmall;
    }

    /**
     * @return the metaKeyword
     */
    public String getMetaKeyword() {
      return metaKeyword;
    }

    /**
     * @param metaKeyword
     *          the metaKeyword to set
     */
    public void setMetaKeyword(String metaKeyword) {
      this.metaKeyword = metaKeyword;
    }

    /**
     * @return the metaDescription
     */
    public String getMetaDescription() {
      return metaDescription;
    }

    /**
     * @param metaDescription
     *          the metaDescription to set
     */
    public void setMetaDescription(String metaDescription) {
      this.metaDescription = metaDescription;
    }

    /**
     * @return the categoryNamePcEn
     */
    public String getCategoryNamePcEn() {
      return categoryNamePcEn;
    }

    /**
     * @param categoryNamePcEn
     *          the categoryNamePcEn to set
     */
    public void setCategoryNamePcEn(String categoryNamePcEn) {
      this.categoryNamePcEn = categoryNamePcEn;
    }

    /**
     * @return the categoryNamePcJp
     */
    public String getCategoryNamePcJp() {
      return categoryNamePcJp;
    }

    /**
     * @param categoryNamePcJp
     *          the categoryNamePcJp to set
     */
    public void setCategoryNamePcJp(String categoryNamePcJp) {
      this.categoryNamePcJp = categoryNamePcJp;
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
     * @return the categoryIdJd
     */
    public String getCategoryIdJd() {
      return categoryIdJd;
    }

    /**
     * @param categoryIdJd the categoryIdJd to set
     */
    public void setCategoryIdJd(String categoryIdJd) {
      this.categoryIdJd = categoryIdJd;
    }

  }

  /**
   * isReadonlyを取得します。
   * 
   * @return isReadonly
   */
  public boolean isReadonly() {
    return isReadonly;
  }

  /**
   * isReadonlyを設定します。
   * 
   * @param readonly
   *          読込専用フラグ
   */
  public void setReadOnly(boolean readonly) {
    this.isReadonly = readonly;
  }

  /**
   * editModeを取得します。
   * 
   * @return editMode
   */
  public String getEditMode() {
    return editMode;
  }

  /**
   * editModeを設定します。
   * 
   * @param editMode
   *          editMode
   */
  public void setEditMode(String editMode) {
    this.editMode = editMode;
  }

  /**
   * fileUrlを取得します。
   * 
   * @return fileUrl
   */
  public String getFileUrl() {
    return fileUrl;
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
   * fileUrlを設定します。
   * 
   * @param fileUrl
   *          fileUrl
   */
  public void setFileUrl(String fileUrl) {
    this.fileUrl = fileUrl;
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
   * CategoryDetailBeanを取得します。
   * 
   * @return CategoryDetailBean
   */
  public CategoryDetailBean getEdit() {
    return edit;
  }

  /**
   * Listを取得します。
   * 
   * @return List
   */
  public List<CategoryDetailBean> getList() {
    return list;
  }

  /**
   * CategoryDetailBeanを設定します。
   * 
   * @param edit
   */
  public void setEdit(CategoryDetailBean edit) {
    this.edit = edit;
  }

  /**
   * Listを設定します。
   * 
   * @param list
   *          カテゴリリスト
   */
  public void setList(List<CategoryDetailBean> list) {
    this.list = list;
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

    edit.categoryCode = reqparam.get("categoryCode");
    edit.categoryNamePc = reqparam.get("categoryNamePc");
    // 20120514 tuxinwei add start
    edit.categoryNamePcEn = reqparam.get("categoryNamePcEn");
    edit.categoryNamePcJp = reqparam.get("categoryNamePcJp");
    // 20120514 tuxinwei add end
    edit.categoryNameMobile = reqparam.get("categoryNameMobile");
    edit.displayOrder = reqparam.get("displayOrder");
    edit.parentCategoryCode = reqparam.get("parentCategoryCode");
    edit.categoryIdTmall = reqparam.get("categoryIdTmall");
    //2014/4/28 京东WBS对应 ob_李 add start
    edit.categoryIdJd = reqparam.get("categoryIdJd");
    //2014/4/28 京东WBS对应 ob_李 add end
    edit.metaKeyword = reqparam.get("metaKeyword");
    edit.metaDescription = reqparam.get("metaDescription");
    edit.keywordCn2 = reqparam.get("keywordCn2");
    edit.keywordJp2 = reqparam.get("keywordJp2");
    edit.keywordEn2 = reqparam.get("keywordEn2");
    edit.setTitle(reqparam.get("title"));
    edit.setTitleEn(reqparam.get("titleEn"));
    edit.setTitleJp(reqparam.get("titleJp"));
    edit.setDescription(reqparam.get("description"));
    edit.setDescriptionEn(reqparam.get("descriptionEn"));
    edit.setDescriptionJp(reqparam.get("descriptionJp"));
    edit.setKeyword(reqparam.get("keyword"));
    edit.setKeywordEn(reqparam.get("keywordEn"));
    edit.setKeywordJp(reqparam.get("keywordJp"));
    String[] listKey = new String[] {
        "categoryAttributeNo", "categoryAttributeName", "categoryAttributeNameEn", "categoryAttributeNameJp"
    };
    if ("".equals(edit.getCategoryNameMobile())) {
      edit.setCategoryNameMobile("NameMobile");
    }
    List<CategoryAttributeBean> categoryAttributeList = new ArrayList<CategoryAttributeBean>();
    for (CategoryAttributeBean categoryAttributeBean : edit.getAttributeList()) {
      Map<String, String> map = reqparam.getListDataWithKey(categoryAttributeBean.getCategoryAttributeNo(), listKey);
      categoryAttributeBean.setCategoryAttributeNo(map.get("categoryAttributeNo"));
      categoryAttributeBean.setCategoryAttributeName(map.get("categoryAttributeName"));
      categoryAttributeBean.setCategoryAttributeNameEn(map.get("categoryAttributeNameEn"));
      categoryAttributeBean.setCategoryAttributeNameJp(map.get("categoryAttributeNameJp"));
      categoryAttributeList.add(categoryAttributeBean);
    }
    edit.setAttributeList(categoryAttributeList);

  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U1040210";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.back.catalog.CategoryBean.0");
  }

  /**
   * categoryListを設定します。
   * 
   * @param categoryList
   *          categoryList
   */
  public void setCategoryList(String categoryList) {
    this.categoryList = categoryList;
  }

  /**
   * categoryListを取得します。
   * 
   * @return categoryList
   */
  public String getCategoryList() {
    return categoryList;
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
   * uploadTableDisplayFlgを取得します。
   * 
   * @return uploadTableDisplayFlg
   */
  public boolean isUploadTableDisplayFlg() {
    return uploadTableDisplayFlg;
  }

  /**
   * uploadTableDisplayFlgを設定します。
   * 
   * @param uploadTableDisplayFlg
   *          uploadTableDisplayFlg
   */
  public void setUploadTableDisplayFlg(boolean uploadTableDisplayFlg) {
    this.uploadTableDisplayFlg = uploadTableDisplayFlg;
  }

  /**
   * relateButtonDisplayFlgを取得します。
   * 
   * @return relateButtonDisplayFlg
   */
  public boolean isRelateButtonDisplayFlg() {
    return relateButtonDisplayFlg;
  }

  /**
   * relateButtonDisplayFlgを設定します。
   * 
   * @param relateButtonDisplayFlg
   *          relateButtonDisplayFlg
   */
  public void setRelateButtonDisplayFlg(boolean relateButtonDisplayFlg) {
    this.relateButtonDisplayFlg = relateButtonDisplayFlg;
  }

  /**
   * csvHeaderTypeを取得します。
   * 
   * @return csvHeaderType
   */
  public List<NameValue> getCsvHeaderType() {
    return csvHeaderType;
  }

  /**
   * csvHeaderTypeを設定します。
   * 
   * @param csvHeaderType
   *          csvHeaderType
   */
  public void setCsvHeaderType(List<NameValue> csvHeaderType) {
    this.csvHeaderType = csvHeaderType;
  }

  // 10.1.2 10089 追加 ここから
  /**
   * categoryNodeInfoListを取得します。
   * 
   * @return categoryNodeInfoList categoryNodeInfoList
   */
  public List<CategoryInfo> getCategoryNodeInfoList() {
    return categoryNodeInfoList;
  }

  /**
   * categoryNodeInfoListを設定します。
   * 
   * @param categoryNodeInfoList
   *          categoryNodeInfoList
   */
  public void setCategoryNodeInfoList(List<CategoryInfo> categoryNodeInfoList) {
    this.categoryNodeInfoList = categoryNodeInfoList;
  }
  // 10.1.2 10089 追加 ここまで

}
