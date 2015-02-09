package jp.co.sint.webshop.web.bean.back.catalog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.web.bean.UISearchBean;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerValue;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U1040220:カテゴリ－商品関連付けのデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class RelatedCategoryBean extends UIBackBean implements UISearchBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private RelatedCategoryDetailBean edit = new RelatedCategoryDetailBean();

  private List<RelatedCategoryDetailBean> list = new ArrayList<RelatedCategoryDetailBean>();

  private String mode;

  @Required
  @AlphaNum2
  @Metadata(name = "カテゴリコード")
  private String categoryCode;

  private String categoryName;

  @AlphaNum2
  @Metadata(name = "商品コード(From)")
  private String searchCommodityCodeStart;

  @AlphaNum2
  @Metadata(name = "商品コード(To)")
  private String searchCommodityCodeEnd;

  @Length(50)
  @Metadata(name = "商品名")
  private String searchCommodityName;

  private boolean registerTableDisplayFlg;

  private boolean deletePartsDisplayFlg;

  private boolean shopListDisplayFlg;

  private boolean selectDisplayFlg;

  private List<CodeAttribute> shopList = new ArrayList<CodeAttribute>();

  private List<CodeAttribute> shopListEdit = new ArrayList<CodeAttribute>();

  private String searchShopCode;

  private List<String> checkedCodeList = new ArrayList<String>();

  private List<CategoryAttributeBean> attributeList = new ArrayList<CategoryAttributeBean>();

  private PagerValue pagerValue;

  /**
   * U1040220:カテゴリ－商品関連付けのサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class CategoryAttributeBean implements Serializable {

    /** serial version uid */
    private static final long serialVersionUID = 1L;

    /** カテゴリ属性番号 */
    private String categoryAttributeNo;

    /** カテゴリ属性名称 */
    private String categoryAttributeName;


    /** カテゴリ属性英文名称 */
    private String categoryAttributeNameEn;

    /** カテゴリ属性日文名称 */
    private String categoryAttributeNameJp;
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
	 * @param categoryAttributeNameEn the categoryAttributeNameEn to set
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
	 * @param categoryAttributeNameJp the categoryAttributeNameJp to set
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
   * U1040220:カテゴリ－商品関連付けのサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class CategoryAttributeValueBean implements Serializable {

    /** serial version uid */
    private static final long serialVersionUID = 1L;

    /** カテゴリ属性番号 */
    private String categoryAttributeNo;

    /** カテゴリ属性値 */
    @Length(30)
    @Metadata(name = "カテゴリ属性値")
    private String categoryAttributeValue;
    //add by cs_yuli 20120607 start
    /** カテゴリ属性値 */
    @Length(30)
    @Metadata(name = "カテゴリ属性英文値")
    private String categoryAttributeValueEn;

    /** カテゴリ属性値 */
    @Length(30)
    @Metadata(name = "カテゴリ属性日文値")
    private String categoryAttributeValueJp;
    //add by cs_yuli 20120607 end
    
    /** カテゴリ属性値更新日付 */
    private Date updatedDatetime;

    /**
     * categoryAttributeValueを取得します。
     * 
     * @return categoryAttributeValue
     */
    public String getCategoryAttributeValue() {
      return categoryAttributeValue;
    }

    /**
     * categoryAttributeValueを設定します。
     * 
     * @param categoryAttributeValue
     *          categoryAttributeValue
     */
    public void setCategoryAttributeValue(String categoryAttributeValue) {
      this.categoryAttributeValue = categoryAttributeValue;
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
     * categoryAttributeNoを設定します。
     * 
     * @param categoryAttributeNo
     *          categoryAttributeNo
     */
    public void setCategoryAttributeNo(String categoryAttributeNo) {
      this.categoryAttributeNo = categoryAttributeNo;
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
	 * @param categoryAttributeValueEn the categoryAttributeValueEn to set
	 */
	public void setCategoryAttributeValueEn(String categoryAttributeValueEn) {
		this.categoryAttributeValueEn = categoryAttributeValueEn;
	}

	/**
	 * @return the categoryAttributeValueEn
	 */
	public String getCategoryAttributeValueEn() {
		return categoryAttributeValueEn;
	}

	/**
	 * @param categoryAttributeValueJp the categoryAttributeValueJp to set
	 */
	public void setCategoryAttributeValueJp(String categoryAttributeValueJp) {
		this.categoryAttributeValueJp = categoryAttributeValueJp;
	}

	/**
	 * @return the categoryAttributeValueJp
	 */
	public String getCategoryAttributeValueJp() {
		return categoryAttributeValueJp;
	}

  }

  /**
   * U1040220:カテゴリ－商品関連付けのサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class RelatedCategoryDetailBean implements Serializable {

    /** serial version uid */
    private static final long serialVersionUID = 1L;

    private List<CategoryAttributeValueBean> attributeList = new ArrayList<CategoryAttributeValueBean>();

    /** 商品コード */
    @Required
    @Length(16)
    @AlphaNum2
    @Metadata(name = "商品コード")
    private String commodityCode;

    /** ショップコード */
    @Length(16)
    @AlphaNum2
    @Metadata(name = "ショップコード")
    private String shopCode;

    private String shopName;

    private String commodityName;

    private String checkedCode;

    private boolean updateButtonDisplayFlg;

    /**
     * commodityCodeを取得します。
     * 
     * @return commodityCode
     */
    public String getCommodityCode() {
      return commodityCode;
    }

    /**
     * commodityNameを取得します。
     * 
     * @return commodityName
     */
    public String getCommodityName() {
      return commodityName;
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
     * commodityCodeを設定します。
     * 
     * @param commodityCode
     *          commodityCode
     */
    public void setCommodityCode(String commodityCode) {
      this.commodityCode = commodityCode;
    }

    /**
     * commodityNameを設定します。
     * 
     * @param commodityName
     *          commodityName
     */
    public void setCommodityName(String commodityName) {
      this.commodityName = commodityName;
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
     * updateButtonDisplayFlgを取得します。
     * 
     * @return updateButtonDisplayFlg
     */
    public boolean isUpdateButtonDisplayFlg() {
      return updateButtonDisplayFlg;
    }

    /**
     * attributeListを取得します。
     * 
     * @return attributeList
     */
    public List<CategoryAttributeValueBean> getAttributeList() {
      return attributeList;
    }

    /**
     * attributeListを設定します。
     * 
     * @param attributeList
     *          attributeList
     */
    public void setAttributeList(List<CategoryAttributeValueBean> attributeList) {
      this.attributeList = attributeList;
    }

    /**
     * shopNameを取得します。
     * 
     * @return shopName
     */
    public String getShopName() {
      return shopName;
    }

    /**
     * shopNameを設定します。
     * 
     * @param shopName
     *          shopName
     */
    public void setShopName(String shopName) {
      this.shopName = shopName;
    }

    /**
     * checkedCodeを取得します。
     * 
     * @return checkedCode
     */

    public String getCheckedCode() {
      return checkedCode;
    }

    /**
     * checkedCodeを設定します。
     * 
     * @param checkedCode
     *          checkedCode
     */
    public void setCheckedCode(String checkedCode) {
      this.checkedCode = checkedCode;
    }

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
   * categoryNameを取得します。
   * 
   * @return categoryName
   */
  public String getCategoryName() {
    return categoryName;
  }

  /**
   * searchCommodityCodeEndを取得します。
   * 
   * @return searchCommodityCodeEnd
   */
  public String getSearchCommodityCodeEnd() {
    return searchCommodityCodeEnd;
  }

  /**
   * searchCommodityCodeStartを取得します。
   * 
   * @return searchCommodityCodeStart
   */
  public String getSearchCommodityCodeStart() {
    return searchCommodityCodeStart;
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
   * categoryNameを設定します。
   * 
   * @param categoryName
   *          categoryName
   */
  public void setCategoryName(String categoryName) {
    this.categoryName = categoryName;
  }

  /**
   * searchCommodityCodeEndを設定します。
   * 
   * @param searchCommodityCodeEnd
   *          searchCommodityCodeEnd
   */
  public void setSearchCommodityCodeEnd(String searchCommodityCodeEnd) {
    this.searchCommodityCodeEnd = searchCommodityCodeEnd;
  }

  /**
   * searchCommodityCodeStartを設定します。
   * 
   * @param searchCommodityCodeStart
   *          searchCommodityCodeStart
   */
  public void setSearchCommodityCodeStart(String searchCommodityCodeStart) {
    this.searchCommodityCodeStart = searchCommodityCodeStart;
  }

  /**
   * CategoryDetailBeanを取得します。
   * 
   * @return CategoryDetailBean
   */
  public RelatedCategoryDetailBean getEdit() {
    return edit;
  }

  /**
   * Listを取得します。
   * 
   * @return List
   */
  public List<RelatedCategoryDetailBean> getList() {
    return list;
  }

  /**
   * RelatedCategoryDetailBeanを設定します。
   * 
   * @param edit
   *          関連カテゴリ情報
   */
  public void setEdit(RelatedCategoryDetailBean edit) {
    this.edit = edit;
  }

  /**
   * Listを設定します。
   * 
   * @param list
   *          関連カテゴリ情報一覧
   */
  public void setList(List<RelatedCategoryDetailBean> list) {
    this.list = list;
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

    searchShopCode = reqparam.get("searchShopCode");
    searchCommodityCodeStart = reqparam.get("searchCommodityCodeStart");
    searchCommodityCodeEnd = reqparam.get("searchCommodityCodeEnd");
    searchCommodityName = reqparam.get("searchCommodityName");
    edit.shopCode = reqparam.get("editShopCode");
    edit.commodityCode = reqparam.get("editCommodityCode");

    String[] listKey = new String[] {
        "editCategoryAttributeNo", "editCategoryAttributeValue","editCategoryAttributeValueEn","editCategoryAttributeValueJp"
    };
    for (CategoryAttributeValueBean ca : edit.getAttributeList()) {
      Map<String, String> map = reqparam.getListDataWithKey(ca.getCategoryAttributeNo(), listKey);
      ca.setCategoryAttributeNo(map.get("editCategoryAttributeNo"));
      ca.setCategoryAttributeValue(map.get("editCategoryAttributeValue"));
      ca.setCategoryAttributeValueEn(map.get("editCategoryAttributeValueEn"));
      ca.setCategoryAttributeValueJp(map.get("editCategoryAttributeValueJp"));
    }

    checkedCodeList = Arrays.asList(reqparam.getAll("checkBox"));

  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U1040220";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.back.catalog.RelatedCategoryBean.0");
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
   * shopListDisplayFlgを取得します。
   * 
   * @return shopListDisplayFlg
   */
  public boolean isShopListDisplayFlg() {
    return shopListDisplayFlg;
  }

  /**
   * shopListDisplayFlgを設定します。
   * 
   * @param shopListDisplayFlg
   *          shopListDisplayFlg
   */
  public void setShopListDisplayFlg(boolean shopListDisplayFlg) {
    this.shopListDisplayFlg = shopListDisplayFlg;
  }

  /**
   * deletePartsDisplayFlgを設定します。
   * 
   * @param deletePartsDisplayFlg
   *          deletePartsDisplayFlg
   */
  public void setDeletePartsDisplayFlg(boolean deletePartsDisplayFlg) {
    this.deletePartsDisplayFlg = deletePartsDisplayFlg;
  }

  /**
   * deletePartsDisplayFlgを取得します。
   * 
   * @return deletePartsDisplayFlg
   */
  public boolean isDeletePartsDisplayFlg() {
    return deletePartsDisplayFlg;
  }

  /**
   * selectDisplayFlgを取得します。
   * 
   * @return selectDisplayFlg
   */
  public boolean isSelectDisplayFlg() {
    return selectDisplayFlg;
  }

  /**
   * selectDisplayFlgを設定します。
   * 
   * @param selectDisplayFlg
   *          selectDisplayFlg
   */
  public void setSelectDisplayFlg(boolean selectDisplayFlg) {
    this.selectDisplayFlg = selectDisplayFlg;
  }

  /**
   * searchCommodityNameを設定します。
   * 
   * @param searchCommodityName
   *          searchCommodityName
   */
  public void setSearchCommodityName(String searchCommodityName) {
    this.searchCommodityName = searchCommodityName;
  }

  /**
   * searchCommodityNameを取得します。
   * 
   * @return searchCommodityName
   */
  public String getSearchCommodityName() {
    return searchCommodityName;
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
   * modeを取得します。
   * 
   * @return mode
   */
  public String getMode() {
    return mode;
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
   * checkedCodeListを取得します。
   * 
   * @return checkedCodeList
   */

  public List<String> getCheckedCodeList() {
    return checkedCodeList;
  }

  /**
   * checkedCodeListを設定します。
   * 
   * @param checkedCodeList
   *          checkedCodeList
   */
  public void setCheckedCodeList(List<String> checkedCodeList) {
    this.checkedCodeList = checkedCodeList;
  }

  /**
   * shopListEditを取得します。
   * 
   * @return shopListEdit
   */
  public List<CodeAttribute> getShopListEdit() {
    return shopListEdit;
  }

  /**
   * shopListEditを設定します。
   * 
   * @param shopListEdit
   *          shopListEdit
   */
  public void setShopListEdit(List<CodeAttribute> shopListEdit) {
    this.shopListEdit = shopListEdit;
  }

}
