package jp.co.sint.webshop.web.bean.back.analysis;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.Datetime;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.web.bean.UISearchBean;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerValue;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U1060310:キャンペーン管理のデータモデルです。
 * 
 * @author OB.
 */
public class GiftCardLogBean extends UIBackBean implements UISearchBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private PagerValue pagerValue = new PagerValue();

  /** 礼品卡编号 */
  @AlphaNum2
  @Length(16)
  @Metadata(name = "礼品卡编号", order = 1)
  private String searchGiftCardCode;

  /** 礼品卡名称 */
  @Length(40)
  @Metadata(name = "礼品卡名称", order = 2)
  private String searchGiftCardName;

  /** 礼品卡发行开始日时(From) */
  @Datetime(format = DateUtil.DEFAULT_DATETIME_FORMAT)
  @Metadata(name = "礼品卡发行开始日时(From)", order = 9)
  private String searchMinIssueStartDatetimeFrom;

  /** 礼品卡发行开始日时(To) */
  @Datetime(format = DateUtil.DEFAULT_DATETIME_FORMAT)
  @Metadata(name = "礼品卡发行开始日时(To)", order = 10)
  private String searchMinIssueStartDatetimeTo;
  
  private List<GiftCardListBean> list = new ArrayList<GiftCardListBean>();
  
  // 销售单价合计
  private BigDecimal totalSalePrice = BigDecimal.ZERO;

  // 面值合计
  private BigDecimal totalDenomination = BigDecimal.ZERO;

  // 激活金额
  private BigDecimal activateAmount = BigDecimal.ZERO;

  // 未激活金额
  private BigDecimal unactAmount = BigDecimal.ZERO;
  
  private BigDecimal useAmount = BigDecimal.ZERO;
  
  private BigDecimal leftAmount = BigDecimal.ZERO;

  /**
   * サブJSPを設定します。
   */
  @Override
  public void setSubJspId() {

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
   *          設定する pagerValue
   */
  public void setPagerValue(PagerValue pagerValue) {
    this.pagerValue = pagerValue;
  }
  
  
  /**
   * U1060310:キャンペーン管理のサブモデルです。
   * 
   * @author OB.
   */
  public static class GiftCardListBean implements Serializable {

    /**
       * 
       */
    private static final long serialVersionUID = 1L;

    /** 礼品卡编号 */
    private String giftCardCode;

    /** 礼品卡名称 */
    private String giftCardName;

    /** 发行时间 */
    private String issueTime;

    /** 发行件数 */
    private String issueNum;

    // 销售单价合计
    private String totalSalePrice;

    // 面值合计
    private String totalDenomination;

    // 激活金额
    private String activateAmount;

    // 未激活金额
    private String unactAmount;
    
    private String useAmount;
    
    private String leftAmount;


    /**
     * @return the issueTime
     */
    public String getIssueTime() {
      return issueTime;
    }

    
    /**
     * @param issueTime the issueTime to set
     */
    public void setIssueTime(String issueTime) {
      this.issueTime = issueTime;
    }

    
    /**
     * @return the issueNum
     */
    public String getIssueNum() {
      return issueNum;
    }

    
    /**
     * @param issueNum the issueNum to set
     */
    public void setIssueNum(String issueNum) {
      this.issueNum = issueNum;
    }

    
    /**
     * @return the totalSalePrice
     */
    public String getTotalSalePrice() {
      return totalSalePrice;
    }

    
    /**
     * @param totalSalePrice the totalSalePrice to set
     */
    public void setTotalSalePrice(String totalSalePrice) {
      this.totalSalePrice = totalSalePrice;
    }

    
    /**
     * @return the totalDenomination
     */
    public String getTotalDenomination() {
      return totalDenomination;
    }

    
    /**
     * @param totalDenomination the totalDenomination to set
     */
    public void setTotalDenomination(String totalDenomination) {
      this.totalDenomination = totalDenomination;
    }

    
    /**
     * @return the activateAmount
     */
    public String getActivateAmount() {
      return activateAmount;
    }

    
    /**
     * @param activateAmount the activateAmount to set
     */
    public void setActivateAmount(String activateAmount) {
      this.activateAmount = activateAmount;
    }

    
    /**
     * @return the unactAmount
     */
    public String getUnactAmount() {
      return unactAmount;
    }

    
    /**
     * @param unactAmount the unactAmount to set
     */
    public void setUnactAmount(String unactAmount) {
      this.unactAmount = unactAmount;
    }

    
    /**
     * @return the giftCardCode
     */
    public String getGiftCardCode() {
      return giftCardCode;
    }

    
    /**
     * @param giftCardCode the giftCardCode to set
     */
    public void setGiftCardCode(String giftCardCode) {
      this.giftCardCode = giftCardCode;
    }

    
    /**
     * @return the giftCardName
     */
    public String getGiftCardName() {
      return giftCardName;
    }

    
    /**
     * @param giftCardName the giftCardName to set
     */
    public void setGiftCardName(String giftCardName) {
      this.giftCardName = giftCardName;
    }


    
    /**
     * @return the useAmount
     */
    public String getUseAmount() {
      return useAmount;
    }


    
    /**
     * @param useAmount the useAmount to set
     */
    public void setUseAmount(String useAmount) {
      this.useAmount = useAmount;
    }


    
    /**
     * @return the leftAmount
     */
    public String getLeftAmount() {
      return leftAmount;
    }


    
    /**
     * @param leftAmount the leftAmount to set
     */
    public void setLeftAmount(String leftAmount) {
      this.leftAmount = leftAmount;
    }

  }
  
  

  /**
   * リクエストパラメータから値を取得します。
   * 
   * @param reqparam
   *          リクエストパラメータ
   */
  public void createAttributes(RequestParameter reqparam) {

    setSearchGiftCardCode(reqparam.get("searchGiftCardCode"));
    setSearchGiftCardName(reqparam.get("searchGiftCardName"));
    setSearchMinIssueStartDatetimeFrom(reqparam.getDateTimeString("searchMinIssueStartDatetimeFrom"));
    setSearchMinIssueStartDatetimeTo(reqparam.getDateTimeString("searchMinIssueStartDatetimeTo"));
    
  }


  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("礼品卡使用状况");
  }

  
  /**
   * @return the searchGiftCardCode
   */
  public String getSearchGiftCardCode() {
    return searchGiftCardCode;
  }

  
  /**
   * @param searchGiftCardCode the searchGiftCardCode to set
   */
  public void setSearchGiftCardCode(String searchGiftCardCode) {
    this.searchGiftCardCode = searchGiftCardCode;
  }

  
  /**
   * @return the searchGiftCardName
   */
  public String getSearchGiftCardName() {
    return searchGiftCardName;
  }

  
  /**
   * @param searchGiftCardName the searchGiftCardName to set
   */
  public void setSearchGiftCardName(String searchGiftCardName) {
    this.searchGiftCardName = searchGiftCardName;
  }

  
  /**
   * @return the searchMinIssueStartDatetimeFrom
   */
  public String getSearchMinIssueStartDatetimeFrom() {
    return searchMinIssueStartDatetimeFrom;
  }

  
  /**
   * @param searchMinIssueStartDatetimeFrom the searchMinIssueStartDatetimeFrom to set
   */
  public void setSearchMinIssueStartDatetimeFrom(String searchMinIssueStartDatetimeFrom) {
    this.searchMinIssueStartDatetimeFrom = searchMinIssueStartDatetimeFrom;
  }

  
  /**
   * @return the searchMinIssueStartDatetimeTo
   */
  public String getSearchMinIssueStartDatetimeTo() {
    return searchMinIssueStartDatetimeTo;
  }

  
  /**
   * @param searchMinIssueStartDatetimeTo the searchMinIssueStartDatetimeTo to set
   */
  public void setSearchMinIssueStartDatetimeTo(String searchMinIssueStartDatetimeTo) {
    this.searchMinIssueStartDatetimeTo = searchMinIssueStartDatetimeTo;
  }

  
  /**
   * @return the list
   */
  public List<GiftCardListBean> getList() {
    return list;
  }

  
  /**
   * @param list the list to set
   */
  public void setList(List<GiftCardListBean> list) {
    this.list = list;
  }

  
  /**
   * @return the totalSalePrice
   */
  public BigDecimal getTotalSalePrice() {
    return totalSalePrice;
  }

  
  /**
   * @param totalSalePrice the totalSalePrice to set
   */
  public void setTotalSalePrice(BigDecimal totalSalePrice) {
    this.totalSalePrice = totalSalePrice;
  }

  
  /**
   * @return the totalDenomination
   */
  public BigDecimal getTotalDenomination() {
    return totalDenomination;
  }

  
  /**
   * @param totalDenomination the totalDenomination to set
   */
  public void setTotalDenomination(BigDecimal totalDenomination) {
    this.totalDenomination = totalDenomination;
  }

  
  /**
   * @return the activateAmount
   */
  public BigDecimal getActivateAmount() {
    return activateAmount;
  }

  
  /**
   * @param activateAmount the activateAmount to set
   */
  public void setActivateAmount(BigDecimal activateAmount) {
    this.activateAmount = activateAmount;
  }

  
  /**
   * @return the unactAmount
   */
  public BigDecimal getUnactAmount() {
    return unactAmount;
  }

  
  /**
   * @param unactAmount the unactAmount to set
   */
  public void setUnactAmount(BigDecimal unactAmount) {
    this.unactAmount = unactAmount;
  }

  
  /**
   * @return the useAmount
   */
  public BigDecimal getUseAmount() {
    return useAmount;
  }

  
  /**
   * @param useAmount the useAmount to set
   */
  public void setUseAmount(BigDecimal useAmount) {
    this.useAmount = useAmount;
  }

  
  /**
   * @return the leftAmount
   */
  public BigDecimal getLeftAmount() {
    return leftAmount;
  }

  
  /**
   * @param leftAmount the leftAmount to set
   */
  public void setLeftAmount(BigDecimal leftAmount) {
    this.leftAmount = leftAmount;
  }
}
