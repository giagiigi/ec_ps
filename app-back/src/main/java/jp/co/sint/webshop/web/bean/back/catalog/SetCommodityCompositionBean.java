package jp.co.sint.webshop.web.bean.back.catalog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.Currency;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Precision;
import jp.co.sint.webshop.data.attribute.Quantity;
import jp.co.sint.webshop.data.attribute.Range;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.RequestParameter;

public class SetCommodityCompositionBean extends UIBackBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private List<CompositionDetail> compositionDetailList = new ArrayList<CompositionDetail>();

  private CompositionDetail edit = new CompositionDetail();
  
  // 店铺号
  private String shopCode;

  // 商品编号
  private String commodityCode;

  // 商品名称
  private String commodityName;

  // 销售开始时间
  private String saleStartDatetime;

  // 销售结束时间
  private String saleEndDatetime;

  // 销售价格
  private String retailPrice;
  
  // tmall销售价格
  private String tmallRetailPrice;
  
  // 删除按钮
  private boolean deleteButtonDisplayFlg = false;
  
  private boolean updatePriceFlg = false;

  // 登录按钮
  private boolean registerButtonDisplayFlg = false;

  // 套餐明细件数
  private long size;
  
  // 套装商品在淘宝上分配的可销售数
  private String suitTmallStock = "0";
  
  // 套装商品在淘宝上的引当数
  private String suitTmallAllocate = "0";
  
  //tmall 单品和套装的分配比例
  private String scaleValue = "0";
  
  // 2014/06/05 库存更新对应 ob_李先超 add start
  //套餐明细商品设定部分[登录]、[删除]按钮显示flg
  private boolean buttonDisplayFlg = true;
  
  //套装商品在京东上的分配比例
  private String jdScaleValue;
  
  //套装商品在京东上分配的可销售数
  private String suitJdStock = "0";
  
  //套装商品在京东上的引当数
  private String suitJdAllocate = "0";
  
  @Quantity
  @Range(min = 0, max = 100)
  private String inputJdScaleValue;
  // 2014/06/05 库存更新对应 ob_李先超 add end
  
  @Quantity
  @Range(min = 0, max = 100)
  private String inputScaleValue;

  @Precision(precision = 10, scale = 2)
  @Currency
  @Range(min = 0, max = 99999999)
  @Metadata(name = "套餐明细价格")
  private String inputRetailPrice = "";
  
  @Precision(precision = 10, scale = 2)
  @Currency
  @Range(min = 0, max = 99999999)
  @Metadata(name = "tmall套餐明细价格")
  private String inputTmallRetailPrice = "";

  @Length(16)
  @AlphaNum2
  @Metadata(name = "商品编号")
  private String searchCommodityCode = "";

  public static class CompositionDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    // 店铺编号
    private String shopCode;

    // 商品编号
    private String commodityCode;

    // 商品名称
    private String commodityName;

    // 库存数
    private String stockQuantity;
    
    // tmall可单品销售库存数
    private String stockTmall;

    // 销售开始时间
    private String saleStartDatetime;

    // 销售结束时间
    private String saleEndDatetime;
    
    // 2014/06/17 库存更新对应 ob_李先超 add start
    // jd可单品库存数
    private String jdTmall;
    // 2014/06/17 库存更新对应 ob_李先超 add end

    // 销售价格
    @Required
    @Length(11)
    @Currency
    @Range(min = 0, max = 99999999)
    @Precision(precision = 10, scale = 2)
    private String retailPrice;
    
    // tmall销售价格
    @Required
    @Length(11)
    @Currency
    @Range(min = 0, max = 99999999)
    @Precision(precision = 10, scale = 2)
    private String tmallRetailPrice;

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

    /**
     * commodityCodeを取得します。
     * 
     * @return commodityCode
     */
    public String getCommodityCode() {
      return commodityCode;
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
     * commodityNameを取得します。
     * 
     * @return commodityName
     */
    public String getCommodityName() {
      return commodityName;
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
     * stockQuantityを取得します。
     * 
     * @return stockQuantity
     */
    public String getStockQuantity() {
      return stockQuantity;
    }

    /**
     * stockQuantityを設定します。
     * 
     * @param stockQuantity
     *          stockQuantity
     */
    public void setStockQuantity(String stockQuantity) {
      this.stockQuantity = stockQuantity;
    }

    /**
     * saleStartDatetimeを取得します。
     * 
     * @return saleStartDatetime
     */
    public String getSaleStartDatetime() {
      return saleStartDatetime;
    }

    /**
     * saleStartDatetimeを設定します。
     * 
     * @param saleStartDatetime
     *          saleStartDatetime
     */
    public void setSaleStartDatetime(String saleStartDatetime) {
      this.saleStartDatetime = saleStartDatetime;
    }

    /**
     * saleEndDatetimeを取得します。
     * 
     * @return saleEndDatetime
     */
    public String getSaleEndDatetime() {
      return saleEndDatetime;
    }

    /**
     * saleEndDatetimeを設定します。
     * 
     * @param saleEndDatetime
     *          saleEndDatetime
     */
    public void setSaleEndDatetime(String saleEndDatetime) {
      this.saleEndDatetime = saleEndDatetime;
    }

    public String getRetailPrice() {
      return retailPrice;
    }

    public void setRetailPrice(String retailPrice) {
      this.retailPrice = retailPrice;
    }

    
    /**
     * @return the tmallRetailPrice
     */
    public String getTmallRetailPrice() {
      return tmallRetailPrice;
    }

    
    /**
     * @param tmallRetailPrice the tmallRetailPrice to set
     */
    public void setTmallRetailPrice(String tmallRetailPrice) {
      this.tmallRetailPrice = tmallRetailPrice;
    }

    
    /**
     * @return the stockTmall
     */
    public String getStockTmall() {
      return stockTmall;
    }

    
    /**
     * @param stockTmall the stockTmall to set
     */
    public void setStockTmall(String stockTmall) {
      this.stockTmall = stockTmall;
    }
    
    // 2014/06/17 库存更新对应 ob_李先超 add start
    /**
     * @return the jdTmall
     */
    public String getJdTmall() {
      return jdTmall;
    }

    
    /**
     * @param jdTmall the jdTmall to set
     */
    public void setJdTmall(String jdTmall) {
      this.jdTmall = jdTmall;
    }
    // 2014/06/17 库存更新对应 ob_李先超 add end
  }

  /**
   * searchCommodityCodeを取得します。
   * 
   * @return searchCommodityCode
   */
  public String getSearchCommodityCode() {
    return searchCommodityCode;
  }

  /**
   * searchCommodityCodeを設定します。
   * 
   * @param searchCommodityCode
   *          searchCommodityCode
   */
  public void setSearchCommodityCode(String searchCommodityCode) {
    this.searchCommodityCode = searchCommodityCode;
  }

  /**
   * sizeを取得します。
   * 
   * @return size
   */
  public long getSize() {
    return size;
  }

  /**
   * sizeを設定します。
   * 
   * @param size
   *          size
   */
  public void setSize(long size) {
    this.size = size;
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
    setUpdatePriceFlg(false);
    reqparam.copy(this);
  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U1040190";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.back.catalog.SetCommodityCompositionBean.0");
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
   * @return the edit
   */
  public CompositionDetail getEdit() {
    return edit;
  }

  
  /**
   * @param edit the edit to set
   */
  public void setEdit(CompositionDetail edit) {
    this.edit = edit;
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
   * commodityCodeを取得します。
   * 
   * @return commodityCode
   */
  public String getCommodityCode() {
    return commodityCode;
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
   * commodityNameを取得します。
   * 
   * @return commodityName
   */
  public String getCommodityName() {
    return commodityName;
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
   * saleStartDatetimeを取得します。
   * 
   * @return saleStartDatetime
   */
  public String getSaleStartDatetime() {
    return saleStartDatetime;
  }

  /**
   * saleStartDatetimeを設定します。
   * 
   * @param saleStartDatetime
   *          saleStartDatetime
   */
  public void setSaleStartDatetime(String saleStartDatetime) {
    this.saleStartDatetime = saleStartDatetime;
  }

  /**
   * saleEndDatetimeを取得します。
   * 
   * @return saleEndDatetime
   */
  public String getSaleEndDatetime() {
    return saleEndDatetime;
  }

  /**
   * saleEndDatetimeを設定します。
   * 
   * @param saleEndDatetime
   *          saleEndDatetime
   */
  public void setSaleEndDatetime(String saleEndDatetime) {
    this.saleEndDatetime = saleEndDatetime;
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
   * compositionDetailListを取得します。
   * 
   * @return compositionDetailList
   */
  public List<CompositionDetail> getCompositionDetailList() {
    return compositionDetailList;
  }

  /**
   * compositionDetailListを設定します。
   * 
   * @param compositionDetailList
   *          compositionDetailList
   */
  public void setCompositionDetailList(List<CompositionDetail> compositionDetailList) {
    this.compositionDetailList = compositionDetailList;
  }

  public String getRetailPrice() {
    return retailPrice;
  }

  public void setRetailPrice(String retailPrice) {
    this.retailPrice = retailPrice;
  }

  public String getInputRetailPrice() {
    return inputRetailPrice;
  }

  public void setInputRetailPrice(String inputRetailPrice) {
    this.inputRetailPrice = inputRetailPrice;
  }

  
  /**
   * @return the inputTmallRetailPrice
   */
  public String getInputTmallRetailPrice() {
    return inputTmallRetailPrice;
  }

  
  /**
   * @param inputTmallRetailPrice the inputTmallRetailPrice to set
   */
  public void setInputTmallRetailPrice(String inputTmallRetailPrice) {
    this.inputTmallRetailPrice = inputTmallRetailPrice;
  }

  
  /**
   * @return the tmallRetailPrice
   */
  public String getTmallRetailPrice() {
    return tmallRetailPrice;
  }

  
  /**
   * @param tmallRetailPrice the tmallRetailPrice to set
   */
  public void setTmallRetailPrice(String tmallRetailPrice) {
    this.tmallRetailPrice = tmallRetailPrice;
  }

  
  /**
   * @return the suitTmallStock
   */
  public String getSuitTmallStock() {
    return suitTmallStock;
  }

  
  /**
   * @param suitTmallStock the suitTmallStock to set
   */
  public void setSuitTmallStock(String suitTmallStock) {
    this.suitTmallStock = suitTmallStock;
  }

  
  /**
   * @return the scaleValue
   */
  public String getScaleValue() {
    return scaleValue;
  }

  
  /**
   * @param scaleValue the scaleValue to set
   */
  public void setScaleValue(String scaleValue) {
    this.scaleValue = scaleValue;
  }

  
  /**
   * @return the inputScaleValue
   */
  public String getInputScaleValue() {
    return inputScaleValue;
  }

  
  /**
   * @param inputScaleValue the inputScaleValue to set
   */
  public void setInputScaleValue(String inputScaleValue) {
    this.inputScaleValue = inputScaleValue;
  }

  
  /**
   * @return the suitTmallAllocate
   */
  public String getSuitTmallAllocate() {
    return suitTmallAllocate;
  }

  
  /**
   * @param suitTmallAllocate the suitTmallAllocate to set
   */
  public void setSuitTmallAllocate(String suitTmallAllocate) {
    this.suitTmallAllocate = suitTmallAllocate;
  }

  
  /**
   * @return the updatePriceFlg
   */
  public boolean isUpdatePriceFlg() {
    return updatePriceFlg;
  }

  
  /**
   * @param updatePriceFlg the updatePriceFlg to set
   */
  public void setUpdatePriceFlg(boolean updatePriceFlg) {
    this.updatePriceFlg = updatePriceFlg;
  }
  
  // 2014/06/05 库存更新对应 ob_李先超 add start
  /**
   * buttonDisplayFlgを取得します。
   * 
   * @return buttonDisplayFlg
   */
  public boolean isButtonDisplayFlg() {
    return buttonDisplayFlg;
  }

  /**
   * buttonDisplayFlgを設定します。
   * 
   * @param buttonDisplayFlg
   *          buttonDisplayFlg
   */
  public void setButtonDisplayFlg(boolean buttonDisplayFlg) {
    this.buttonDisplayFlg = buttonDisplayFlg;
  }
  
  /**
   * @return the jdScaleValue
   */
  public String getJdScaleValue() {
    return jdScaleValue;
  }

  
  /**
   * @param jdScaleValue the jdScaleValue to set
   */
  public void setJdScaleValue(String jdScaleValue) {
    this.jdScaleValue = jdScaleValue;
  }
  
  /**
   * @return the suitJdStock
   */
  public String getSuitJdStock() {
    return suitJdStock;
  }

  
  /**
   * @param suitJdStock the suitJdStock to set
   */
  public void setSuitJdStock(String suitJdStock) {
    this.suitJdStock = suitJdStock;
  }
  
  /**
   * @return the suitJdAllocate
   */
  public String getSuitJdAllocate() {
    return suitJdAllocate;
  }

  
  /**
   * @param suitJdAllocate the suitJdAllocate to set
   */
  public void setSuitJdAllocate(String suitJdAllocate) {
    this.suitJdAllocate = suitJdAllocate;
  }
  
  /**
   * @return the inputJdScaleValue
   */
  public String getInputJdScaleValue() {
    return inputJdScaleValue;
  }

  
  /**
   * @param inputJdScaleValue the inputJdScaleValue to set
   */
  public void setInputJdScaleValue(String inputJdScaleValue) {
    this.inputJdScaleValue = inputJdScaleValue;
  }
  // 2014/06/05 库存更新对应 ob_李先超 add end

}
