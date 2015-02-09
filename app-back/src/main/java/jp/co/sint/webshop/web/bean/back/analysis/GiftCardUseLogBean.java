package jp.co.sint.webshop.web.bean.back.analysis;

import java.io.Serializable;
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
public class GiftCardUseLogBean extends UIBackBean implements UISearchBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private PagerValue pagerValue = new PagerValue();

  @AlphaNum2
  @Length(16)
  @Metadata(name = "顾客コード(From)", order = 1)
  private String searchCustomerCodeStart;
  
  @AlphaNum2
  @Length(16)
  @Metadata(name = "顾客コード(To)", order = 2)
  private String searchCustomerCodeEnd;
  
  @Length(11)
  @AlphaNum2
  private String searchTelephoneNum;
  
  @Length(40)
  private String searchCustomerName;
  
  @Length(15)
  private String searchCardId;
  
  @Length(20)
  private String searchOrderNo;
  
  @Length(256)
  private String searchEmail;

  /** 礼品卡充值开始日时(From) */
  @Datetime(format = DateUtil.DEFAULT_DATE_FORMAT)
  @Metadata(name = "礼品卡充值开始日时(From)", order = 3)
  private String searchShippingStartDatetimeFrom;

  /** 礼品卡充值开始日时(To) */
  @Datetime(format = DateUtil.DEFAULT_DATE_FORMAT)
  @Metadata(name = "礼品卡充值开始日时(To)", order = 4)
  private String searchShippingStartDatetimeTo;
    
  private List<GiftCardUseListBean> list = new ArrayList<GiftCardUseListBean>();
  
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
  public static class GiftCardUseListBean implements Serializable {

    /**
       * 
       */
    private static final long serialVersionUID = 1L;

    private String customerCode;

    private String cardId;

    private String rechargeTime;

    private String unitPrice;

    private String denomination;

    private String discountRate;

    private String useAmount;
    
    private String leftAmount;
    
    private List<String> linkOrder = new ArrayList<String>();



    
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


    
    /**
     * @return the customerCode
     */
    public String getCustomerCode() {
      return customerCode;
    }


    
    /**
     * @param customerCode the customerCode to set
     */
    public void setCustomerCode(String customerCode) {
      this.customerCode = customerCode;
    }


    
    /**
     * @return the cardId
     */
    public String getCardId() {
      return cardId;
    }


    
    /**
     * @param cardId the cardId to set
     */
    public void setCardId(String cardId) {
      this.cardId = cardId;
    }


    
    /**
     * @return the rechargeTime
     */
    public String getRechargeTime() {
      return rechargeTime;
    }


    
    /**
     * @param rechargeTime the rechargeTime to set
     */
    public void setRechargeTime(String rechargeTime) {
      this.rechargeTime = rechargeTime;
    }


    
    /**
     * @return the unitPrice
     */
    public String getUnitPrice() {
      return unitPrice;
    }


    
    /**
     * @param unitPrice the unitPrice to set
     */
    public void setUnitPrice(String unitPrice) {
      this.unitPrice = unitPrice;
    }


    
    /**
     * @return the denomination
     */
    public String getDenomination() {
      return denomination;
    }


    
    /**
     * @param denomination the denomination to set
     */
    public void setDenomination(String denomination) {
      this.denomination = denomination;
    }


    
    /**
     * @return the discountRate
     */
    public String getDiscountRate() {
      return discountRate;
    }


    
    /**
     * @param discountRate the discountRate to set
     */
    public void setDiscountRate(String discountRate) {
      this.discountRate = discountRate;
    }


    
    /**
     * @return the linkOrder
     */
    public List<String> getLinkOrder() {
      return linkOrder;
    }


    
    /**
     * @param linkOrder the linkOrder to set
     */
    public void setLinkOrder(List<String> linkOrder) {
      this.linkOrder = linkOrder;
    }

  }
  
  

  /**
   * リクエストパラメータから値を取得します。
   * 
   * @param reqparam
   *          リクエストパラメータ
   */
  public void createAttributes(RequestParameter reqparam) {

      setSearchCustomerCodeStart(reqparam.get("searchCustomerCodeStart"));
      setSearchCustomerCodeEnd(reqparam.get("searchCustomerCodeEnd"));
      setSearchCustomerName(reqparam.get("searchCustomerName"));
      setSearchTelephoneNum(reqparam.get("searchTelephoneNum"));
      setSearchEmail(reqparam.get("searchEmail"));
      setSearchCardId(reqparam.get("searchCardId"));
      setSearchOrderNo(reqparam.get("searchOrderNo"));
      setSearchShippingStartDatetimeFrom(reqparam.getDateString("searchShippingStartDatetimeFrom"));
      setSearchShippingStartDatetimeTo(reqparam.getDateString("searchShippingStartDatetimeTo"));
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
   * @return the searchCustomerCodeStart
   */
  public String getSearchCustomerCodeStart() {
    return searchCustomerCodeStart;
  }

  
  /**
   * @param searchCustomerCodeStart the searchCustomerCodeStart to set
   */
  public void setSearchCustomerCodeStart(String searchCustomerCodeStart) {
    this.searchCustomerCodeStart = searchCustomerCodeStart;
  }

  
  /**
   * @return the searchCustomerCodeEnd
   */
  public String getSearchCustomerCodeEnd() {
    return searchCustomerCodeEnd;
  }

  
  /**
   * @param searchCustomerCodeEnd the searchCustomerCodeEnd to set
   */
  public void setSearchCustomerCodeEnd(String searchCustomerCodeEnd) {
    this.searchCustomerCodeEnd = searchCustomerCodeEnd;
  }

  
  /**
   * @return the searchTelephoneNum
   */
  public String getSearchTelephoneNum() {
    return searchTelephoneNum;
  }

  
  /**
   * @param searchTelephoneNum the searchTelephoneNum to set
   */
  public void setSearchTelephoneNum(String searchTelephoneNum) {
    this.searchTelephoneNum = searchTelephoneNum;
  }

  
  /**
   * @return the searchCustomerName
   */
  public String getSearchCustomerName() {
    return searchCustomerName;
  }

  
  /**
   * @param searchCustomerName the searchCustomerName to set
   */
  public void setSearchCustomerName(String searchCustomerName) {
    this.searchCustomerName = searchCustomerName;
  }

  
  /**
   * @return the searchCardId
   */
  public String getSearchCardId() {
    return searchCardId;
  }

  
  /**
   * @param searchCardId the searchCardId to set
   */
  public void setSearchCardId(String searchCardId) {
    this.searchCardId = searchCardId;
  }

  
  /**
   * @return the searchOrderNo
   */
  public String getSearchOrderNo() {
    return searchOrderNo;
  }

  
  /**
   * @param searchOrderNo the searchOrderNo to set
   */
  public void setSearchOrderNo(String searchOrderNo) {
    this.searchOrderNo = searchOrderNo;
  }

  
  /**
   * @return the searchEmail
   */
  public String getSearchEmail() {
    return searchEmail;
  }

  
  /**
   * @param searchEmail the searchEmail to set
   */
  public void setSearchEmail(String searchEmail) {
    this.searchEmail = searchEmail;
  }

  
  /**
   * @return the searchShippingStartDatetimeFrom
   */
  public String getSearchShippingStartDatetimeFrom() {
    return searchShippingStartDatetimeFrom;
  }

  
  /**
   * @param searchShippingStartDatetimeFrom the searchShippingStartDatetimeFrom to set
   */
  public void setSearchShippingStartDatetimeFrom(String searchShippingStartDatetimeFrom) {
    this.searchShippingStartDatetimeFrom = searchShippingStartDatetimeFrom;
  }

  
  /**
   * @return the searchShippingStartDatetimeTo
   */
  public String getSearchShippingStartDatetimeTo() {
    return searchShippingStartDatetimeTo;
  }

  
  /**
   * @param searchShippingStartDatetimeTo the searchShippingStartDatetimeTo to set
   */
  public void setSearchShippingStartDatetimeTo(String searchShippingStartDatetimeTo) {
    this.searchShippingStartDatetimeTo = searchShippingStartDatetimeTo;
  }

  
  /**
   * @return the list
   */
  public List<GiftCardUseListBean> getList() {
    return list;
  }

  
  /**
   * @param list the list to set
   */
  public void setList(List<GiftCardUseListBean> list) {
    this.list = list;
  }

}
