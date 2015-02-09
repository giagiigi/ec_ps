package jp.co.sint.webshop.web.bean.back.catalog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.service.catalog.CategoryInfo;
import jp.co.sint.webshop.utility.ArrayUtil;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U1040230:カテゴリ－関連付けのデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class RelatedCategoryTreeBean extends UIBackBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private List<RelatedCategoryTreeDetailBean> list = new ArrayList<RelatedCategoryTreeDetailBean>();

  private String shopCode;

  private String commodityCode;

  private String commodityName;

  private String categoryList;

  private String pathList;

  private String checkCategoryList;

  private String[] checkedList;

  private boolean registerPartsDisplayFlg;
  
  // 10.1.2 10089 追加 ここから
  private List<CategoryInfo> categoryNodeInfoList;
  // 10.1.2 10089 追加 ここまで

  /**
   * U1040230:カテゴリ－関連付けのサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class RelatedCategoryTreeDetailBean implements Serializable {

    /** serial version uid */
    private static final long serialVersionUID = 1L;

    private String check;

    private String categoryCode;

    private String categoryNamePc;

    private String parentCategoryCode;

    private String path;

    private String depth;

    private String displayOrder;

    private String commodityCount;

    /**
     * categoryCodeを取得します。
     * 
     * @return categoryCode
     */
    public String getCategoryCode() {
      return categoryCode;
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
     * checkを取得します。
     * 
     * @return check
     */
    public String getCheck() {
      return check;
    }

    /**
     * commodityCountを取得します。
     * 
     * @return commodityCount
     */
    public String getCommodityCount() {
      return commodityCount;
    }

    /**
     * depthを取得します。
     * 
     * @return depth
     */
    public String getDepth() {
      return depth;
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
     * parentCategoryCodeを取得します。
     * 
     * @return parentCategoryCode
     */
    public String getParentCategoryCode() {
      return parentCategoryCode;
    }

    /**
     * pathを取得します。
     * 
     * @return path
     */
    public String getPath() {
      return path;
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
     * categoryNamePcを設定します。
     * 
     * @param categoryNamePc
     *          categoryNamePc
     */
    public void setCategoryNamePc(String categoryNamePc) {
      this.categoryNamePc = categoryNamePc;
    }

    /**
     * checkを設定します。
     * 
     * @param check
     *          check
     */
    public void setCheck(String check) {
      this.check = check;
    }

    /**
     * commodityCountを設定します。
     * 
     * @param commodityCount
     *          commodityCount
     */
    public void setCommodityCount(String commodityCount) {
      this.commodityCount = commodityCount;
    }

    /**
     * depthを設定します。
     * 
     * @param depth
     *          depth
     */
    public void setDepth(String depth) {
      this.depth = depth;
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
     * parentCategoryCodeを設定します。
     * 
     * @param parentCategoryCode
     *          parentCategoryCode
     */
    public void setParentCategoryCode(String parentCategoryCode) {
      this.parentCategoryCode = parentCategoryCode;
    }

    /**
     * pathを設定します。
     * 
     * @param path
     *          path
     */
    public void setPath(String path) {
      this.path = path;
    }

  }

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
   * Listを取得します。
   * 
   * @return List
   */
  public List<RelatedCategoryTreeDetailBean> getList() {
    return list;
  }

  /**
   * Listを設定します。
   * 
   * @param list
   *          カテゴリツリー情報一覧
   */
  public void setList(List<RelatedCategoryTreeDetailBean> list) {
    this.list = list;
  }

  /**
   * categoryListを設定します。
   * 
   * @param categoryList
   *          設categoryList
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
   * checkCategoryListを設定します。
   * 
   * @param checkCategoryList
   *          checkCategoryList
   */
  public void setCheckCategoryList(String checkCategoryList) {
    this.checkCategoryList = checkCategoryList;
  }

  /**
   * checkCategoryListを取得します。
   * 
   * @return checkCategoryList
   */
  public String getCheckCategoryList() {
    return checkCategoryList;
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
   * shopCodeを取得します。
   * 
   * @return shopCode
   */
  public String getShopCode() {
    return shopCode;
  }

  /**
   * pathListを設定します。
   * 
   * @param pathList
   *          pathList
   */
  public void setPathList(String pathList) {
    this.pathList = pathList;
  }

  /**
   * pathListを取得します。
   * 
   * @return pathList
   */
  public String getPathList() {
    return pathList;
  }

  /**
   * registerPartsDisplayFlgを設定します。
   * 
   * @param registerPartsDisplayFlg
   *          registerPartsDisplayFlg
   */
  public void setRegisterPartsDisplayFlg(boolean registerPartsDisplayFlg) {
    this.registerPartsDisplayFlg = registerPartsDisplayFlg;
  }

  /**
   * registerPartsDisplayFlgを取得します。
   * 
   * @return registerPartsDisplayFlg
   */
  public boolean isRegisterPartsDisplayFlg() {
    return registerPartsDisplayFlg;
  }

  //10.1.2 10089 追加 ここから
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
  //10.1.2 10089 追加 ここまで

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
    checkedList = reqparam.getAll("tree");
  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U1040230";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.back.catalog.RelatedCategoryTreeBean.0");
  }

  /**
   * checkedListを取得します。
   * 
   * @return checkedList
   */
  public String[] getCheckedList() {
    return ArrayUtil.immutableCopy(checkedList);
  }

  /**
   * checkedListを設定します。
   * 
   * @param checkedList
   *          checkedList
   */
  public void setCheckedList(String[] checkedList) {
    this.checkedList = ArrayUtil.immutableCopy(checkedList);
  }

}
