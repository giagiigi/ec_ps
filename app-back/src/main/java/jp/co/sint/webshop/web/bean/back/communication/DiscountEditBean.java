package jp.co.sint.webshop.web.bean.back.communication;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.Currency;
import jp.co.sint.webshop.data.attribute.Datetime;
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Precision;
import jp.co.sint.webshop.data.attribute.Quantity;
import jp.co.sint.webshop.data.attribute.Range;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.RequestParameter;

public class DiscountEditBean extends UIBackBean implements Serializable {

  /**
	 * 
	 */
  private static final long serialVersionUID = 1L;

  // 折扣编号
  @Required
  @Length(16)
  @AlphaNum2
  @Metadata(name = "折扣编号")
  private String discountCode;

  // 折扣名称(说明)
  @Required
  @Length(50)
  @Metadata(name = "折扣名称")
  private String discountName;

  // 开始日期（年月日 时）
  @Required
  @Datetime(format = DateUtil.DEFAULT_DATETIME_FORMAT)
  @Metadata(name = "活动期间(From)")
  private String discountStartDatetime;

  // 结束日期（年月日 时）
  @Required
  @Datetime(format = DateUtil.DEFAULT_DATETIME_FORMAT)
  @Metadata(name = "活动期间(To)")
  private String discountEndDatetime;

  private List<DiscountDetailBean> list = new ArrayList<DiscountDetailBean>();

  private DiscountDetailBean edit = new DiscountDetailBean();

  @Required
  @Length(16)
  @AlphaNum2
  @Metadata(name = "关联商品编号")
  private String relatedComdtyCode;

  // 区分更新或增加
  private String displayMode;

  private String editMode;

  // 登录按钮
  private boolean displayLoginButtonFlg;

  // 更新按钮
  private boolean displayUpdateButtonFlg;

  // 删除按钮
  private boolean displayDeleteFlg;
  

  @Length(8)
  @Digit
  @Range(min = 1, max = 99999999)
  @Metadata(name = "商品种类数量")
  private String commodityTypeNum;
  
  //SO发送状态
  @Length(1)
  @Digit
  private String soCouponFlg;
  

  public static class DiscountDetailBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String commodityCode;

    private String commodityName;

    private String unitPrice;

    private String stockQuantity;

    private String saleStartDatetime;

    private String saleEndDatetime;

    @Required
    @Length(11)
    @Currency
    @Range(min = 0, max = 99999999)
    @Precision(precision = 10, scale = 2)
    @Metadata(name = "折扣特价")
    private String discountPrice;

    @Required
    @Length(8)
    @Digit
    @Range(min = 0, max = 99999999)
    @Metadata(name = "每个顾客最多购买数")
    private String customerMaxTotalNum;

    @Required
    @Length(8)
    @Digit
    @Range(min = 0, max = 99999999)
    @Metadata(name = "网站最多购买数")
    private String siteMaxTotalNum;
    
    @Length(5)
    @Digit
    @Quantity
    @Range(min = 0, max = 99999)
    @Metadata(name = "中文排序")
    private String rankcn;
    
    @Length(5)
    @Digit
    @Quantity
    @Range(min = 0, max = 99999)
    @Metadata(name = "日文排序")
    private String rankjp;
    
    @Length(5)
    @Digit
    @Quantity
    @Range(min = 0, max = 99999)
    @Metadata(name = "英文排序")
    private String ranken;

    @Required
    @Length(1)
    @Metadata(name = "使用标志")
    private Long useFlg;
    
    @Length(1)
    @Metadata(name = "中文显示标志")
    private Long displayFlgcn;
    
    @Length(1)
    @Metadata(name = "日文显示标志")
    private Long displayFlgjp;
    
    @Length(1)
    @Metadata(name = "英文显示标志")
    private Long displayFlgen;

    @Length(256)
    @Metadata(name = "折扣说明(中文)")
    private String discountDirectionsCn;

    @Length(256)
    @Metadata(name = "折扣说明(日文)")
    private String discountDirectionsJp;

    @Length(256)
    @Metadata(name = "折扣说明(英文)")
    private String discountDirectionsEn;

    /**
     * @return the commodityCode
     */
    public String getCommodityCode() {
      return commodityCode;
    }

    /**
     * @return the commodityName
     */
    public String getCommodityName() {
      return commodityName;
    }

    /**
     * @return the unitPrice
     */
    public String getUnitPrice() {
      return unitPrice;
    }

    /**
     * @return the stockQuantity
     */
    public String getStockQuantity() {
      return stockQuantity;
    }

    /**
     * @return the saleStartDatetime
     */
    public String getSaleStartDatetime() {
      return saleStartDatetime;
    }

    /**
     * @return the saleEndDatetime
     */
    public String getSaleEndDatetime() {
      return saleEndDatetime;
    }

    /**
     * @return the discountPrice
     */
    public String getDiscountPrice() {
      return discountPrice;
    }

    /**
     * @return the customerMaxTotalNum
     */
    public String getCustomerMaxTotalNum() {
      return customerMaxTotalNum;
    }

    /**
     * @return the siteMaxTotalNum
     */
    public String getSiteMaxTotalNum() {
      return siteMaxTotalNum;
    }

    /**
     * @return the useFlg
     */
    public Long getUseFlg() {
      return useFlg;
    }

    /**
     * @return the discountDirectionsCn
     */
    public String getDiscountDirectionsCn() {
      return discountDirectionsCn;
    }

    /**
     * @return the discountDirectionsJp
     */
    public String getDiscountDirectionsJp() {
      return discountDirectionsJp;
    }

    /**
     * @return the discountDirectionsEn
     */
    public String getDiscountDirectionsEn() {
      return discountDirectionsEn;
    }

    /**
     * @param useFlg
     *          the useFlg to set
     */
    public void setUseFlg(Long useFlg) {
      this.useFlg = useFlg;
    }

    /**
     * @param discountDirectionsCn
     *          the discountDirectionsCn to set
     */
    public void setDiscountDirectionsCn(String discountDirectionsCn) {
      this.discountDirectionsCn = discountDirectionsCn;
    }

    /**
     * @param discountDirectionsJp
     *          the discountDirectionsJp to set
     */
    public void setDiscountDirectionsJp(String discountDirectionsJp) {
      this.discountDirectionsJp = discountDirectionsJp;
    }

    /**
     * @param discountDirectionsEn
     *          the discountDirectionsEn to set
     */
    public void setDiscountDirectionsEn(String discountDirectionsEn) {
      this.discountDirectionsEn = discountDirectionsEn;
    }

    /**
     * @param commodityCode
     *          the commodityCode to set
     */
    public void setCommodityCode(String commodityCode) {
      this.commodityCode = commodityCode;
    }

    /**
     * @param commodityName
     *          the commodityName to set
     */
    public void setCommodityName(String commodityName) {
      this.commodityName = commodityName;
    }

    /**
     * @param unitPrice
     *          the unitPrice to set
     */
    public void setUnitPrice(String unitPrice) {
      this.unitPrice = unitPrice;
    }

    /**
     * @param stockQuantity
     *          the stockQuantity to set
     */
    public void setStockQuantity(String stockQuantity) {
      this.stockQuantity = stockQuantity;
    }

    /**
     * @param saleStartDatetime
     *          the saleStartDatetime to set
     */
    public void setSaleStartDatetime(String saleStartDatetime) {
      this.saleStartDatetime = saleStartDatetime;
    }

    /**
     * @param saleEndDatetime
     *          the saleEndDatetime to set
     */
    public void setSaleEndDatetime(String saleEndDatetime) {
      this.saleEndDatetime = saleEndDatetime;
    }

    /**
     * @param discountPrice
     *          the discountPrice to set
     */
    public void setDiscountPrice(String discountPrice) {
      this.discountPrice = discountPrice;
    }

    /**
     * @param customerMaxTotalNum
     *          the customerMaxTotalNum to set
     */
    public void setCustomerMaxTotalNum(String customerMaxTotalNum) {
      this.customerMaxTotalNum = customerMaxTotalNum;
    }

    /**
     * @param siteMaxTotalNum
     *          the siteMaxTotalNum to set
     */
    public void setSiteMaxTotalNum(String siteMaxTotalNum) {
      this.siteMaxTotalNum = siteMaxTotalNum;
    }

    
    /**
     * @return the rankcn
     */
    public String getRankcn() {
      return rankcn;
    }

    
    /**
     * @param rankcn the rankcn to set
     */
    public void setRankcn(String rankcn) {
      this.rankcn = rankcn;
    }

    
    /**
     * @return the rankjp
     */
    public String getRankjp() {
      return rankjp;
    }

    
    /**
     * @param rankjp the rankjp to set
     */
    public void setRankjp(String rankjp) {
      this.rankjp = rankjp;
    }

    
    /**
     * @return the ranken
     */
    public String getRanken() {
      return ranken;
    }

    
    /**
     * @param ranken the ranken to set
     */
    public void setRanken(String ranken) {
      this.ranken = ranken;
    }

    
    /**
     * @return the displayFlgcn
     */
    public Long getDisplayFlgcn() {
      return displayFlgcn;
    }

    
    /**
     * @param displayFlgcn the displayFlgcn to set
     */
    public void setDisplayFlgcn(Long displayFlgcn) {
      this.displayFlgcn = displayFlgcn;
    }

    
    /**
     * @return the displayFlgjp
     */
    public Long getDisplayFlgjp() {
      return displayFlgjp;
    }

    
    /**
     * @param displayFlgjp the displayFlgjp to set
     */
    public void setDisplayFlgjp(Long displayFlgjp) {
      this.displayFlgjp = displayFlgjp;
    }

    
    /**
     * @return the displayFlgen
     */
    public Long getDisplayFlgen() {
      return displayFlgen;
    }

    
    /**
     * @param displayFlgen the displayFlgen to set
     */
    public void setDisplayFlgen(Long displayFlgen) {
      this.displayFlgen = displayFlgen;
    }



  }

  /**
   * @return the discountCode
   */
  public String getDiscountCode() {
    return discountCode;
  }

  /**
   * @return the discountName
   */
  public String getDiscountName() {
    return discountName;
  }

  /**
   * @return the discountStartDate
   */
  public String getDiscountStartDatetime() {
    return discountStartDatetime;
  }

  /**
   * @return the discountEndDate
   */
  public String getDiscountEndDatetime() {
    return discountEndDatetime;
  }

  /**
   * @return the list
   */
  public List<DiscountDetailBean> getList() {
    return list;
  }

  /**
   * @return the relatedComdtyCode
   */
  public String getRelatedComdtyCode() {
    return relatedComdtyCode;
  }

  /**
   * @return the displayMode
   */
  public String getDisplayMode() {
    return displayMode;
  }

  /**
   * @return the displayUpdateButtonFlg
   */
  public boolean isDisplayUpdateButtonFlg() {
    return displayUpdateButtonFlg;
  }

  /**
   * @return the displayDeleteFlg
   */
  public boolean isDisplayDeleteFlg() {
    return displayDeleteFlg;
  }

  /**
   * @return the editMode
   */
  public String getEditMode() {
    return editMode;
  }



  /**
   * @return the displayLoginButtonFlg
   */
  public boolean isDisplayLoginButtonFlg() {
    return displayLoginButtonFlg;
  }

  /**
   * @param displayLoginButtonFlg
   *          the displayLoginButtonFlg to set
   */
  public void setDisplayLoginButtonFlg(boolean displayLoginButtonFlg) {
    this.displayLoginButtonFlg = displayLoginButtonFlg;
  }

  /**
   * @param editMode
   *          the editMode to set
   */
  public void setEditMode(String editMode) {
    this.editMode = editMode;
  }

  /**
   * @param discountCode
   *          the discountCode to set
   */
  public void setDiscountCode(String discountCode) {
    this.discountCode = discountCode;
  }

  /**
   * @param discountName
   *          the discountName to set
   */
  public void setDiscountName(String discountName) {
    this.discountName = discountName;
  }

  /**
   * @param discountStartDate
   *          the discountStartDate to set
   */
  public void setDiscountStartDatetime(String discountStartDatetime) {
    this.discountStartDatetime = discountStartDatetime;
  }

  /**
   * @param discountEndDate
   *          the discountEndDate to set
   */
  public void setDiscountEndDatetime(String discountEndDatetime) {
    this.discountEndDatetime = discountEndDatetime;
  }

  /**
   * @param list
   *          the list to set
   */
  public void setList(List<DiscountDetailBean> list) {
    this.list = list;
  }

  /**
   * @param relatedComdtyCode
   *          the relatedComdtyCode to set
   */
  public void setRelatedComdtyCode(String relatedComdtyCode) {
    this.relatedComdtyCode = relatedComdtyCode;
  }

  /**
   * @param displayMode
   *          the displayMode to set
   */
  public void setDisplayMode(String displayMode) {
    this.displayMode = displayMode;
  }

  /**
   * @param displayUpdateButtonFlg
   *          the displayUpdateButtonFlg to set
   */
  public void setDisplayUpdateButtonFlg(boolean displayUpdateButtonFlg) {
    this.displayUpdateButtonFlg = displayUpdateButtonFlg;
  }

  /**
   * @param displayDeleteFlg
   *          the displayDeleteFlg to set
   */
  public void setDisplayDeleteFlg(boolean displayDeleteFlg) {
    this.displayDeleteFlg = displayDeleteFlg;
  }

  /**
   * @return the edit
   */
  public DiscountDetailBean getEdit() {
    return edit;
  }

  /**
   * @param edit
   *          the edit to set
   */
  public void setEdit(DiscountDetailBean edit) {
    this.edit = edit;
  }

  @Override
  public void setSubJspId() {
  }

  /**
   *取得页面参数this.searchDiscountStatus = searchDiscountStatus;
   */
  public void createAttributes(RequestParameter reqparam) {
    reqparam.copy(this);
  
    this.setDiscountStartDatetime(reqparam.getDateTimeString("discountStartDatetime"));
    this.setDiscountEndDatetime(reqparam.getDateTimeString("discountEndDatetime"));
    this.setSoCouponFlg(reqparam.get("soCouponFlg"));
  }




  public String getModuleId() {
    return "U1061210";
  }

  public String getModuleName() {
    return Messages.getString("web.bean.back.communication.DiscountEditBean.0");
  }

  
  /**
   * @return the commodityTypeNum
   */
  public String getCommodityTypeNum() {
    return commodityTypeNum;
  }

  
  /**
   * @param commodityTypeNum the commodityTypeNum to set
   */
  public void setCommodityTypeNum(String commodityTypeNum) {
    this.commodityTypeNum = commodityTypeNum;
  }

  
  /**
   * @return the soCouponFlg
   */
  public String getSoCouponFlg() {
    return soCouponFlg;
  }
  /**
   * @param soCouponFlg the soCouponFlg to set
   */
  public void setSoCouponFlg(String soCouponFlg) {
    this.soCouponFlg = soCouponFlg;
  }


}
