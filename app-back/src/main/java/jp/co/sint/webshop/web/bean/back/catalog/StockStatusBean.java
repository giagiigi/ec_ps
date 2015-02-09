package jp.co.sint.webshop.web.bean.back.catalog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Quantity;
import jp.co.sint.webshop.data.attribute.Range;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U1040610:在庫状況設定のデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class StockStatusBean extends UIBackBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private StockStatusDetailBean edit = new StockStatusDetailBean();

  private List<StockStatusDetailBean> list = new ArrayList<StockStatusDetailBean>();

  private String mode;

  private List<CodeAttribute> shopList = new ArrayList<CodeAttribute>();

  @Required
  @Metadata(name = "ショップ")
  private String searchShopCode;

  private boolean searchButtonDisplayFlg = false;

  private boolean selectButtonDisplayFlg = false;

  private boolean registerButtonDisplayFlg = false;

  private boolean updateButtonDisplayFlg = false;

  private boolean editTableDisplayFlg = false;

  /**
   * U1040610:在庫状況設定のデータモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class StockStatusDetailBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String shopCode;

    private String stockStatusNo;

    @Required
    @Length(40)
    @Metadata(name = "在庫状況分類名", order = 1)
    private String stockStatusName;

    @Required
    @Length(50)
    @Metadata(name = "在庫無コメント", order = 2)
    private String outOfStockMessage;

    @Required
    @Length(50)
    @Metadata(name = "在庫少コメント", order = 3)
    private String stockLittleMessage;

    @Required
    @Length(8)
    @Quantity
    @Range(min = 1, max = 99999999)
    @Metadata(name = "在庫少 数量", order = 4)
    private String stockSufficientThreshold;

    @Required
    @Length(50)
    @Metadata(name = "在庫多コメント", order = 5)
    private String stockSufficientMessage;

    /**
     * 削除ボタン表示フラグ true:表示 false:非表示 デフォルトfalse
     */
    private boolean deleteButtonDisplayFlg = false;

    private Date updatedDatetime;

    /**
     * outOfStockMessageを取得します。
     * 
     * @return outOfStockMessage
     */
    public String getOutOfStockMessage() {
      return outOfStockMessage;
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
     * stockLittleMessageを取得します。
     * 
     * @return stockLittleMessage
     */
    public String getStockLittleMessage() {
      return stockLittleMessage;
    }

    /**
     * stockStatusNameを取得します。
     * 
     * @return stockStatusName
     */
    public String getStockStatusName() {
      return stockStatusName;
    }

    /**
     * stockStatusNoを取得します。
     * 
     * @return stockStatusNo
     */
    public String getStockStatusNo() {
      return stockStatusNo;
    }

    /**
     * stockSufficientMessageを取得します。
     * 
     * @return stockSufficientMessage
     */
    public String getStockSufficientMessage() {
      return stockSufficientMessage;
    }

    /**
     * stockSufficientThresholdを取得します。
     * 
     * @return stockSufficientThreshold
     */
    public String getStockSufficientThreshold() {
      return stockSufficientThreshold;
    }

    /**
     * deleteButtonDisplayFlgを取得します。 true:表示 false:非表示 デフォルトfalse
     * 
     * @return delButtonDisplayFlg
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
     * outOfStockMessageを設定します。
     * 
     * @param outOfStockMessage
     *          outOfStockMessage
     */
    public void setOutOfStockMessage(String outOfStockMessage) {
      this.outOfStockMessage = outOfStockMessage;
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
     * stockLittleMessageを設定します。
     * 
     * @param stockLittleMessage
     *          stockLittleMessage
     */
    public void setStockLittleMessage(String stockLittleMessage) {
      this.stockLittleMessage = stockLittleMessage;
    }

    /**
     * stockStatusNameを設定します。
     * 
     * @param stockStatusName
     *          stockStatusName
     */
    public void setStockStatusName(String stockStatusName) {
      this.stockStatusName = stockStatusName;
    }

    /**
     * stockStatusNoを設定します。
     * 
     * @param stockStatusNo
     *          stockStatusNo
     */
    public void setStockStatusNo(String stockStatusNo) {
      this.stockStatusNo = stockStatusNo;
    }

    /**
     * stockSufficientMessageを設定します。
     * 
     * @param stockSufficientMessage
     *          stockSufficientMessage
     */
    public void setStockSufficientMessage(String stockSufficientMessage) {
      this.stockSufficientMessage = stockSufficientMessage;
    }

    /**
     * stockSufficientThresholdを設定します。
     * 
     * @param stockSufficientThreshold
     *          stockSufficientThreshold
     */
    public void setStockSufficientThreshold(String stockSufficientThreshold) {
      this.stockSufficientThreshold = stockSufficientThreshold;
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

  }

  /**
   * editを取得します。
   * 
   * @return edit
   */
  public StockStatusDetailBean getEdit() {
    return edit;
  }

  /**
   * listを取得します。
   * 
   * @return list
   */
  public List<StockStatusDetailBean> getList() {
    return list;
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
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U1040610";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.back.catalog.StockStatusBean.0");
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
   * editを設定します。
   * 
   * @param edit
   *          edit
   */
  public void setEdit(StockStatusDetailBean edit) {
    this.edit = edit;
  }

  /**
   * listを設定します。
   * 
   * @param list
   *          list
   */
  public void setList(List<StockStatusDetailBean> list) {
    this.list = list;
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
   * searchShopCodeを設定します。
   * 
   * @param searchShopCode
   *          searchShopCode
   */
  public void setSearchShopCode(String searchShopCode) {
    this.searchShopCode = searchShopCode;
  }

  /**
   * selectButtonDisplayFlgを設定します。
   * 
   * @param selectButtonDisplayFlg
   *          selectButtonDisplayFlg
   */
  public void setSelectButtonDisplayFlg(boolean selectButtonDisplayFlg) {
    this.selectButtonDisplayFlg = selectButtonDisplayFlg;
  }

  /**
   * selectButtonDisplayFlgを取得します。 true:表示 false:非表示 デフォルトfalse
   * 
   * @return selectButtonDisplayFlg
   */
  public boolean isSelectButtonDisplayFlg() {
    return selectButtonDisplayFlg;
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
   * searchButtonDisplayFlgを取得します。
   * 
   * @return searchButtonDisplayFlg
   */
  public boolean isSearchButtonDisplayFlg() {
    return searchButtonDisplayFlg;
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
   * registerButtonDisplayFlgを設定します。
   * 
   * @param registerButtonDisplayFlg
   *          registerButtonDisplayFlg
   */
  public void setRegisterButtonDisplayFlg(boolean registerButtonDisplayFlg) {
    this.registerButtonDisplayFlg = registerButtonDisplayFlg;
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
   * updateButtonDisplayFlgを設定します。
   * 
   * @param updateButtonDisplayFlg
   *          updateButtonDisplayFlg
   */
  public void setUpdateButtonDisplayFlg(boolean updateButtonDisplayFlg) {
    this.updateButtonDisplayFlg = updateButtonDisplayFlg;
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
   * editTableDisplayFlgを取得します。
   * 
   * @return editTableDisplayFlg
   */
  public boolean isEditTableDisplayFlg() {
    return editTableDisplayFlg;
  }

  /**
   * editTableDisplayFlgを設定します。
   * 
   * @param editTableDisplayFlg
   *          editTableDisplayFlg
   */
  public void setEditTableDisplayFlg(boolean editTableDisplayFlg) {
    this.editTableDisplayFlg = editTableDisplayFlg;
  }

  /**
   * リクエストパラメータから値を取得します。
   * 
   * @param reqparam
   *          リクエストパラメータ
   */
  public void createAttributes(RequestParameter reqparam) {
    setSearchShopCode(reqparam.get("searchShopCode"));
    edit.setStockStatusName(reqparam.get("stockStatusName"));
    edit.setStockSufficientMessage(reqparam.get("stockSufficientMessage"));
    edit.setStockLittleMessage(reqparam.get("stockLittleMessage"));
    edit.setOutOfStockMessage(reqparam.get("outOfStockMessage"));
    edit.setStockSufficientThreshold(reqparam.get("stockSufficientThreshold"));
  }

  /**
   * サブJSPを設定します。
   */
  @Override
  public void setSubJspId() {
  }

}
