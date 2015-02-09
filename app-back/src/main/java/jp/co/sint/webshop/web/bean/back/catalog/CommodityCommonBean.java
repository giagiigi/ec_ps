package jp.co.sint.webshop.web.bean.back.catalog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.data.domain.PartsCode;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U1040810:商品詳細レイアウトのデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class CommodityCommonBean extends UIBackBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private List<CommodityCommonDetailBean> displayList = new ArrayList<CommodityCommonDetailBean>();

  private List<CommodityCommonDetailBean> unDisplayList = new ArrayList<CommodityCommonDetailBean>();

  @Required
  @Metadata(name = "ショップ")
  private String searchShopCode;

  private List<CodeAttribute> shopList;

  private boolean registerButtonDisplayFlg;

  private boolean resetButtonDisplayFlg;

  private boolean searchButtonDisplayFlg;

  /**
   * U1040810:商品詳細レイアウトのサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class CommodityCommonDetailBean implements Serializable {

    /** serial version uid */
    private static final long serialVersionUID = 1L;

    /** パーツコード */
    @Length(16)
    @Metadata(name = "パーツコード")
    private String partsCode;

    /** 表示順 */
    @Length(4)
    @Metadata(name = "表示順")
    private String displayOrder;

    /** 表示フラグ */
    @Metadata(name = "表示フラグ")
    private String displayType;

    private Date updatedDatetime;

    /**
     * displayOrderを取得します。
     * 
     * @return displayOrder
     */
    public String getDisplayOrder() {
      return displayOrder;
    }

    /**
     * displayTypeを取得します。
     * 
     * @return displayType
     */
    public String getDisplayType() {
      return displayType;
    }

    /**
     * partsCodeを取得します。
     * 
     * @return partsCode
     */
    public String getPartsCode() {
      return partsCode;
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
     * displayTypeを設定します。
     * 
     * @param displayType
     *          displayType
     */
    public void setDisplayType(String displayType) {
      this.displayType = displayType;
    }

    /**
     * partsCodeを設定します。
     * 
     * @param partsCode
     *          partsCode
     */
    public void setPartsCode(String partsCode) {
      this.partsCode = partsCode;
    }

    /**
     * partsNameを取得します。
     * 
     * @return partsName
     */
    public String getPartsName() {
      return PartsCode.fromValue(getPartsCode()).getName();
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
    String[] display = reqparam.getAll("registerDisplay");
    String[] unDisplay = reqparam.getAll("registerUnDisplay");

    displayList.clear();
    for (int i = 0; i < display.length; i++) {
      if (StringUtil.isNullOrEmpty(display[i])) {
        // 表示エリアを空にした場合見えないliタグが作られることへの対処
        continue;
      }
      CommodityCommonDetailBean detail = new CommodityCommonDetailBean();
      detail.setPartsCode(display[i]);
      detail.setDisplayOrder(Integer.toString(i));
      detail.setDisplayType("1");
      displayList.add(detail);
    }

    unDisplayList.clear();
    for (int i = 0; i < unDisplay.length; i++) {
      if (StringUtil.isNullOrEmpty(unDisplay[i])) {
        // 非表示エリアを空にした場合見えないliタグが作られることへの対処
        continue;
      }
      CommodityCommonDetailBean detail = new CommodityCommonDetailBean();
      detail.setPartsCode(unDisplay[i]);
      detail.setDisplayOrder(Integer.toString(i));
      detail.setDisplayType("0");
      unDisplayList.add(detail);
    }
  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U1040810";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.back.catalog.CommodityCommonBean.0");
  }

  /**
   * displayListを設定します。
   * 
   * @param displayList
   *          displayList
   */
  public void setDisplayList(List<CommodityCommonDetailBean> displayList) {
    this.displayList = displayList;
  }

  /**
   * displayListを取得します。
   * 
   * @return displayList
   */
  public List<CommodityCommonDetailBean> getDisplayList() {
    return displayList;
  }

  /**
   * displayListを設定します。
   * 
   * @param unDisplayList
   *          unDisplayList
   */
  public void setUnDisplayList(List<CommodityCommonDetailBean> unDisplayList) {
    this.unDisplayList = unDisplayList;
  }

  /**
   * displayListを取得します。
   * 
   * @return unDisplayList
   */
  public List<CommodityCommonDetailBean> getUnDisplayList() {
    return unDisplayList;
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
   * registerButtonDisplayFlgを取得します。
   * 
   * @return registerButtonDisplayFlg
   */
  public boolean isRegisterButtonDisplayFlg() {
    return registerButtonDisplayFlg;
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
   * searchShopCodeを取得します。
   * 
   * @return searchShopCode
   */
  public String getSearchShopCode() {
    return searchShopCode;
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
   * shopListを取得します。
   * 
   * @return shopList
   */
  public List<CodeAttribute> getShopList() {
    return shopList;
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
   * searchButtonDisplayFlgを取得します。
   * 
   * @return searchButtonDisplayFlg
   */
  public boolean isSearchButtonDisplayFlg() {
    return searchButtonDisplayFlg;
  }

  /**
   * resetButtonDisplayFlgを設定します。
   * 
   * @param resetButtonDisplayFlg
   *          resetButtonDisplayFlg
   */
  public void setResetButtonDisplayFlg(boolean resetButtonDisplayFlg) {
    this.resetButtonDisplayFlg = resetButtonDisplayFlg;
  }

  /**
   * resetButtonDisplayFlgを取得します。
   * 
   * @return resetButtonDisplayFlg
   */
  public boolean isResetButtonDisplayFlg() {
    return resetButtonDisplayFlg;
  }

}
