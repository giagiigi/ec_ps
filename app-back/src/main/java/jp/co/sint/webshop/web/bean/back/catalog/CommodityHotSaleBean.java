package jp.co.sint.webshop.web.bean.back.catalog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Quantity;
import jp.co.sint.webshop.data.attribute.Range;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.RequestParameter;

public class CommodityHotSaleBean extends UIBackBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private List<HotSaleDetail> hotListzh = new ArrayList<HotSaleDetail>();
  
  private List<HotSaleDetail> hotListjp = new ArrayList<HotSaleDetail>();
  
  private List<HotSaleDetail> hotListus = new ArrayList<HotSaleDetail>();
  
  @Length(16)
  @AlphaNum2
  @Metadata(name = "商品编号")
  private String searchCommodityCodezh = "";
  
  @Length(16)
  @AlphaNum2
  @Metadata(name = "商品编号")
  private String searchCommodityCodejp = "";
  
  @Length(16)
  @AlphaNum2
  @Metadata(name = "商品编号")
  private String searchCommodityCodeus = "";
  
  @Quantity
  @Range(min = 0, max = 99999)
  @Metadata(name = "排列顺序")
  private String inputSortRankzh;
  
  @Quantity
  @Range(min = 0, max = 99999)
  @Metadata(name = "排列顺序")
  private String inputSortRankjp;
  
  @Quantity
  @Range(min = 0, max = 99999)
  @Metadata(name = "排列顺序")
  private String inputSortRankus;
  
  
  // 删除按钮
  private boolean deleteButtonDisplayFlg = false;

  // 登录按钮
  private boolean registerButtonDisplayFlg = false;

  // 中文热销明细件数
  private long sizezh;
  
  // 日文热销明细件数
  private long sizejp;
  
  // 英文热销明细件数
  private long sizeus;

  public static class HotSaleDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    // 商品编号
    private String commodityCode;

    // 商品名称
    private String commodityName;
    
    private String languageCode;
    
    @Quantity
    @Range(min = 0, max = 99999)
    @Metadata(name = "排列顺序")
    private String sortRank;

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
     * @return the languageCode
     */
    public String getLanguageCode() {
      return languageCode;
    }

    
    /**
     * @param languageCode the languageCode to set
     */
    public void setLanguageCode(String languageCode) {
      this.languageCode = languageCode;
    }

    
    /**
     * @return the sortRank
     */
    public String getSortRank() {
      return sortRank;
    }

    
    /**
     * @param sortRank the sortRank to set
     */
    public void setSortRank(String sortRank) {
      this.sortRank = sortRank;
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
    reqparam.copy(this);
  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U1040140";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("热卖商品设定");
  }

  
  /**
   * @return the deleteButtonDisplayFlg
   */
  public boolean isDeleteButtonDisplayFlg() {
    return deleteButtonDisplayFlg;
  }

  
  /**
   * @param deleteButtonDisplayFlg the deleteButtonDisplayFlg to set
   */
  public void setDeleteButtonDisplayFlg(boolean deleteButtonDisplayFlg) {
    this.deleteButtonDisplayFlg = deleteButtonDisplayFlg;
  }

  
  /**
   * @return the registerButtonDisplayFlg
   */
  public boolean isRegisterButtonDisplayFlg() {
    return registerButtonDisplayFlg;
  }

  
  /**
   * @param registerButtonDisplayFlg the registerButtonDisplayFlg to set
   */
  public void setRegisterButtonDisplayFlg(boolean registerButtonDisplayFlg) {
    this.registerButtonDisplayFlg = registerButtonDisplayFlg;
  }

  
  /**
   * @return the sizezh
   */
  public long getSizezh() {
    return sizezh;
  }

  
  /**
   * @param sizezh the sizezh to set
   */
  public void setSizezh(long sizezh) {
    this.sizezh = sizezh;
  }

  
  /**
   * @return the sizejp
   */
  public long getSizejp() {
    return sizejp;
  }

  
  /**
   * @param sizejp the sizejp to set
   */
  public void setSizejp(long sizejp) {
    this.sizejp = sizejp;
  }

  
  /**
   * @return the sizeus
   */
  public long getSizeus() {
    return sizeus;
  }

  
  /**
   * @param sizeus the sizeus to set
   */
  public void setSizeus(long sizeus) {
    this.sizeus = sizeus;
  }

  
  /**
   * @return the searchCommodityCodejp
   */
  public String getSearchCommodityCodejp() {
    return searchCommodityCodejp;
  }

  
  /**
   * @param searchCommodityCodejp the searchCommodityCodejp to set
   */
  public void setSearchCommodityCodejp(String searchCommodityCodejp) {
    this.searchCommodityCodejp = searchCommodityCodejp;
  }

  
  /**
   * @return the searchCommodityCodeus
   */
  public String getSearchCommodityCodeus() {
    return searchCommodityCodeus;
  }

  
  /**
   * @param searchCommodityCodeus the searchCommodityCodeus to set
   */
  public void setSearchCommodityCodeus(String searchCommodityCodeus) {
    this.searchCommodityCodeus = searchCommodityCodeus;
  }

  
  /**
   * @return the searchCommodityCodezh
   */
  public String getSearchCommodityCodezh() {
    return searchCommodityCodezh;
  }

  
  /**
   * @param searchCommodityCodezh the searchCommodityCodezh to set
   */
  public void setSearchCommodityCodezh(String searchCommodityCodezh) {
    this.searchCommodityCodezh = searchCommodityCodezh;
  }

  
  /**
   * @return the hotListzh
   */
  public List<HotSaleDetail> getHotListzh() {
    return hotListzh;
  }

  
  /**
   * @param hotListzh the hotListzh to set
   */
  public void setHotListzh(List<HotSaleDetail> hotListzh) {
    this.hotListzh = hotListzh;
  }

  
  /**
   * @return the hotListjp
   */
  public List<HotSaleDetail> getHotListjp() {
    return hotListjp;
  }

  
  /**
   * @param hotListjp the hotListjp to set
   */
  public void setHotListjp(List<HotSaleDetail> hotListjp) {
    this.hotListjp = hotListjp;
  }

  
  /**
   * @return the hotListus
   */
  public List<HotSaleDetail> getHotListus() {
    return hotListus;
  }

  
  /**
   * @param hotListus the hotListus to set
   */
  public void setHotListus(List<HotSaleDetail> hotListus) {
    this.hotListus = hotListus;
  }

  
  /**
   * @return the inputSortRankzh
   */
  public String getInputSortRankzh() {
    return inputSortRankzh;
  }

  
  /**
   * @param inputSortRankzh the inputSortRankzh to set
   */
  public void setInputSortRankzh(String inputSortRankzh) {
    this.inputSortRankzh = inputSortRankzh;
  }

  
  /**
   * @return the inputSortRankjp
   */
  public String getInputSortRankjp() {
    return inputSortRankjp;
  }

  
  /**
   * @param inputSortRankjp the inputSortRankjp to set
   */
  public void setInputSortRankjp(String inputSortRankjp) {
    this.inputSortRankjp = inputSortRankjp;
  }

  
  /**
   * @return the inputSortRankus
   */
  public String getInputSortRankus() {
    return inputSortRankus;
  }

  
  /**
   * @param inputSortRankus the inputSortRankus to set
   */
  public void setInputSortRankus(String inputSortRankus) {
    this.inputSortRankus = inputSortRankus;
  }

}
