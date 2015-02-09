package jp.co.sint.webshop.web.bean.back.catalog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Quantity;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.bean.UISearchBean;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerValue;
import jp.co.sint.webshop.web.webutility.RequestParameter;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1040710:入荷お知らせのデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class StockListBean extends UIBackBean implements UISearchBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private List<StockListDetailBean> list = new ArrayList<StockListDetailBean>();

  /** SKUコード */
  private String searchSkuCode;

  private List<CodeAttribute> shopList = new ArrayList<CodeAttribute>();

  /** ショップコード */
  private String searchShopCode;

  private boolean displayShopList = false;

  private boolean registerButtonDisplayFlg = true;

  private boolean searchButtonDisplayFlg;

  private boolean registerTableDisplayFlg;

  private boolean updateButtonDisplayFlg;

  private List<String> checkedCode = new ArrayList<String>();

  private List<StockListAddBean> StockListAddBeanList = new ArrayList<StockListAddBean>();

  private PagerValue pagerValue;

  // 20120116 os013 add start
  /** EC在库割合(0-100) */
  @Length(3)
  @Quantity
  @Metadata(name = "EC在库割合(0-100)")
  private Long shareRatio;
  
  //商品关联
  private String commodityLink;
  
  //更新在库品区分Flg
  private String updateFlg;
  
  // 2014/06/17 库存更新对应 ob_卢 add start
  private List<String> errorMsgList = new ArrayList<String>();
  // 2014/06/17 库存更新对应 ob_卢 add end
  
  
  public String getUpdateFlg() {
    return updateFlg;
  }
  
  /**
   * @return the errorMsgList
   */
  public List<String> getErrorMsgList() {
    return errorMsgList;
  }



  /**
   * @param errorMsgList the errorMsgList to set
   */
  public void setErrorMsgList(List<String> errorMsgList) {
    this.errorMsgList = errorMsgList;
  }



  public void setUpdateFlg(String updateFlg) {
    this.updateFlg = updateFlg;
  }
  
  public String getCommodityLink() {
    return commodityLink;
  }
  public void setCommodityLink(String commodityLink) {
    this.commodityLink = commodityLink;
  }
  
  /**
   * U1040710:入荷お知らせのサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class StockListDetailBean implements Serializable {

    /** serial version uid */
    private static final long serialVersionUID = 1L;

    // ショップコード
    private String shopCode;

    // SKUコード
    private String skuCode;

    // SKU名稱
    private String skuname;

    // 商品名稱
    private String commodityName;

    // EC在庫数量
    private Long stockQuantity;

    // EC引当数量
    private Long allocatedQuantity;

    // 予約数量
    private Long reservedQuantity;

    // 予約上限数
    private Long reservationLimit;

    // 注文毎予約上限数
    private Long oneshotReservationLimit;

    // 安全在庫
    private Long stockThreshold;

    // 総在庫
    private Long stockTotal;

    // TMALL在庫数
    private Long stockTmall;

    // TMALL引当数
    private Long allocatedTmall;

    // 在庫リーバランスフラグ
    private Long shareRecalcFlag;

    // 規格1値の文字列
    private String standard1Name;

    // 規格2値の文字列
    private String standard2Name;

    // EC在庫割合(0-100)
    private Long shareRatio;

    //在库品区分
    private String onStockFlag;
    
    //行设置颜色
    private String rowColor;
        
    //商品编号
    private String commodityCode;
    
    //商品编号表示用
    private String commodityCodeShow;
    
    // 2014/06/09 库存更新对应 ob_卢 add start
    // 京东在庫数
    private Long stockJd;

    // 京东引当数
    private Long allocatedJd;
    
    // 京东在庫割合(0-100)
    private Long shareRatioJd;
    
    // 天猫在庫割合(0-100)
    private Long shareRatioTmall;
    // 2014/06/09 库存更新对应 ob_卢 add end
    
    
    
    public String getCommodityCodeShow() {
      return commodityCodeShow;
    }

    /**
     * @return the shareRatioTmall
     */
    public Long getShareRatioTmall() {
      return shareRatioTmall;
    }

    /**
     * @param shareRatioTmall the shareRatioTmall to set
     */
    public void setShareRatioTmall(Long shareRatioTmall) {
      this.shareRatioTmall = shareRatioTmall;
    }

    /**
     * @return the stockJd
     */
    public Long getStockJd() {
      return stockJd;
    }

    /**
     * @param stockJd the stockJd to set
     */
    public void setStockJd(Long stockJd) {
      this.stockJd = stockJd;
    }

    /**
     * @return the allocatedJd
     */
    public Long getAllocatedJd() {
      return allocatedJd;
    }

    /**
     * @param allocatedJd the allocatedJd to set
     */
    public void setAllocatedJd(Long allocatedJd) {
      this.allocatedJd = allocatedJd;
    }

    /**
     * @return the shareRatioJd
     */
    public Long getShareRatioJd() {
      return shareRatioJd;
    }

    /**
     * @param shareRatioJd the shareRatioJd to set
     */
    public void setShareRatioJd(Long shareRatioJd) {
      this.shareRatioJd = shareRatioJd;
    }

    public void setCommodityCodeShow(String commodityCodeShow) {
      this.commodityCodeShow = commodityCodeShow;
    }

    public String getCommodityCode() {
      return commodityCode;
    }

    public void setCommodityCode(String commodityCode) {
      this.commodityCode = commodityCode;
    }


    public String getRowColor() {
      return rowColor;
    }

    
    public void setRowColor(String rowColor) {
      this.rowColor = rowColor;
    }

    public String getOnStockFlag() {
      return onStockFlag;
    }
    
    public void setOnStockFlag(String onStockFlag) {
      this.onStockFlag = onStockFlag;
    }
    
    public String getShopCode() {
      return shopCode;
    }

    public void setShopCode(String shopCode) {
      this.shopCode = shopCode;
    }

    public String getSkuCode() {
      return skuCode;
    }

    public void setSkuCode(String skuCode) {
      this.skuCode = skuCode;
    }

    public String getSkuname() {
      return skuname;
    }

    public void setSkuname(String skuname) {
      this.skuname = skuname;
    }

    public String getCommodityName() {
      return commodityName;
    }

    public void setCommodityName(String commodityName) {
      this.commodityName = commodityName;
    }

    public Long getStockQuantity() {
      return stockQuantity;
    }

    public void setStockQuantity(Long stockQuantity) {
      this.stockQuantity = stockQuantity;
    }

    public Long getAllocatedQuantity() {
      return allocatedQuantity;
    }

    public void setAllocatedQuantity(Long allocatedQuantity) {
      this.allocatedQuantity = allocatedQuantity;
    }

    public Long getReservedQuantity() {
      return reservedQuantity;
    }

    public void setReservedQuantity(Long reservedQuantity) {
      this.reservedQuantity = reservedQuantity;
    }

    public Long getReservationLimit() {
      return reservationLimit;
    }

    public void setReservationLimit(Long reservationLimit) {
      this.reservationLimit = reservationLimit;
    }

    public Long getOneshotReservationLimit() {
      return oneshotReservationLimit;
    }

    public void setOneshotReservationLimit(Long oneshotReservationLimit) {
      this.oneshotReservationLimit = oneshotReservationLimit;
    }

    public Long getStockThreshold() {
      return stockThreshold;
    }

    public void setStockThreshold(Long stockThreshold) {
      this.stockThreshold = stockThreshold;
    }

    public Long getStockTotal() {
      return stockTotal;
    }

    public void setStockTotal(Long stockTotal) {
      this.stockTotal = stockTotal;
    }

    public Long getStockTmall() {
      return stockTmall;
    }

    public void setStockTmall(Long stockTmall) {
      this.stockTmall = stockTmall;
    }

    public Long getAllocatedTmall() {
      return allocatedTmall;
    }

    public void setAllocatedTmall(Long allocatedTmall) {
      this.allocatedTmall = allocatedTmall;
    }

    public Long getShareRecalcFlag() {
      return shareRecalcFlag;
    }

    public void setShareRecalcFlag(Long shareRecalcFlag) {
      this.shareRecalcFlag = shareRecalcFlag;
    }

    public Long getShareRatio() {
      return shareRatio;
    }

    public void setShareRatio(Long shareRatio) {
      this.shareRatio = shareRatio;
    }

    public String getStandard1Name() {
      return standard1Name;
    }

    public void setStandard1Name(String standard1Name) {
      this.standard1Name = standard1Name;
    }

    public String getStandard2Name() {
      return standard2Name;
    }

    public void setStandard2Name(String standard2Name) {
      this.standard2Name = standard2Name;
    }

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
   * shopListを設定します。
   * 
   * @param shopList
   *          shopList
   */
  public void setShopList(List<CodeAttribute> shopList) {
    this.shopList = shopList;
  }

  /**
   * listを取得します。
   * 
   * @return list
   */
  public List<StockListDetailBean> getList() {
    return list;
  }

  /**
   * searchSkuCodeを取得します。
   * 
   * @return searchSkuCode
   */
  public String getSearchSkuCode() {
    return searchSkuCode;
  }

  /**
   * listを設定します。
   * 
   * @param list
   *          list
   */
  public void setList(List<StockListDetailBean> list) {
    this.list = list;
  }

  /**
   * searchSkuCodeを設定します。
   * 
   * @param searchSkuCode
   *          searchSkuCode
   */
  public void setSearchSkuCode(String searchSkuCode) {
    this.searchSkuCode = searchSkuCode;
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
   * searchShopCodeを設定します。
   * 
   * @param searchShopCode
   *          searchShopCode
   */
  public void setSearchShopCode(String searchShopCode) {
    this.searchShopCode = searchShopCode;
  }

  /**
   * displayShopListを取得します。
   * 
   * @return displayShopList
   */
  public boolean isDisplayShopList() {
    return displayShopList;
  }

  /**
   * displayShopListを設定します。
   * 
   * @param displayShopList
   *          displayShopList
   */
  public void setDisplayShopList(boolean displayShopList) {
    this.displayShopList = displayShopList;
  }
  //20120323 os013 add start
  private String mode;
  
  /**
   * @return the mode
   */
  public String getMode() {
    return mode;
  }

  /**
   * @param mode the mode to set
   */
  public void setMode(String mode) {
    this.mode = mode;
  }
  //20120323 os013 add end
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
    this.setSearchShopCode(reqparam.get("searchShopCode"));
    if(StringUtil.isNullOrEmpty(getSearchSkuCode())){
      this.setSearchSkuCode(reqparam.get("searchSkuCode"));
    }
    this.setCheckedCode(Arrays.asList(reqparam.getAll("checkBox")));
    //20120323 os013 add start
    if(WebConstantCode.COMPLETE_UPDATE.equals(this.getMode())){
      this.setCommodityLink(getCommodityLink());
    }else{
    //20120323 os013 add end
      this.setCommodityLink(reqparam.get("commodityLink"));
    }
    
  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U1800003";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.back.catalog.StockListDetailBean.0");
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
   * pagerValueを設定します。
   * 
   * @param pagerValue
   *          pagerValue
   */
  public void setPagerValue(PagerValue pagerValue) {
    this.pagerValue = pagerValue;
  }

  /**
   * checkedCodeを取得します。
   * 
   * @return checkedCode
   */
  public List<String> getCheckedCode() {
    return checkedCode;
  }

  /**
   * checkedCodeを設定します。
   * 
   * @param checkedCode
   *          checkedCode
   */
  public void setCheckedCode(List<String> checkedCode) {
    this.checkedCode = checkedCode;
  }

  public void setRegisterButtonDisplayFlg(boolean registerButtonDisplayFlg) {
    this.registerButtonDisplayFlg = registerButtonDisplayFlg;
  }

  public boolean isRegisterButtonDisplayFlg() {
    return registerButtonDisplayFlg;
  }

  public boolean isSearchButtonDisplayFlg() {
    return searchButtonDisplayFlg;
  }

  public void setSearchButtonDisplayFlg(boolean searchButtonDisplayFlg) {
    this.searchButtonDisplayFlg = searchButtonDisplayFlg;
  }

  public boolean isRegisterTableDisplayFlg() {
    return registerTableDisplayFlg;
  }

  public void setRegisterTableDisplayFlg(boolean registerTableDisplayFlg) {
    this.registerTableDisplayFlg = registerTableDisplayFlg;
  }

  public boolean isUpdateButtonDisplayFlg() {
    return updateButtonDisplayFlg;
  }

  public void setUpdateButtonDisplayFlg(boolean updateButtonDisplayFlg) {
    this.updateButtonDisplayFlg = updateButtonDisplayFlg;
  }

  public static class StockListAddBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    // SKUコード
    private String skuCode;

    // 安全在庫
    private String stockThreshold;

    // EC無限在庫フラグ
    private String infinityFlagEc;

    // TMALL無限在庫フラグ
    private String infinityFlagTmall;

    // 在庫リーバランスフラグ
    private String shareRecalcFlag;

    public String getSkuCode() {
      return skuCode;
    }

    public void setSkuCode(String skuCode) {
      this.skuCode = skuCode;
    }

    public String getStockThreshold() {
      return stockThreshold;
    }

    public void setStockThreshold(String stockThreshold) {
      this.stockThreshold = stockThreshold;
    }

    public String getInfinityFlagEc() {
      return infinityFlagEc;
    }

    public void setInfinityFlagEc(String infinityFlagEc) {
      this.infinityFlagEc = infinityFlagEc;
    }

    public String getInfinityFlagTmall() {
      return infinityFlagTmall;
    }

    public void setInfinityFlagTmall(String infinityFlagTmall) {
      this.infinityFlagTmall = infinityFlagTmall;
    }

    public String getShareRecalcFlag() {
      return shareRecalcFlag;
    }

    public void setShareRecalcFlag(String shareRecalcFlag) {
      this.shareRecalcFlag = shareRecalcFlag;
    }

  }

  public List<StockListAddBean> getStockListAddBeanList() {
    return StockListAddBeanList;
  }

  public void setStockListAddBeanList(List<StockListAddBean> stockListAddBeanList) {
    StockListAddBeanList = stockListAddBeanList;
  }

  public Long getShareRatio() {
    return shareRatio;
  }

  public void setShareRatio(Long shareRatio) {
    this.shareRatio = shareRatio;
  }
  
  

}
