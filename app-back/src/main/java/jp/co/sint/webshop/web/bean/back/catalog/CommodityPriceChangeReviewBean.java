package jp.co.sint.webshop.web.bean.back.catalog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.data.attribute.Datetime;
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.EmailForSearch;
import jp.co.sint.webshop.data.attribute.Kana;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.MobileNumber;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.data.domain.ConfirmObjectFlg;
import jp.co.sint.webshop.data.domain.OrderFlg;
import jp.co.sint.webshop.data.domain.OrderType;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.bean.UISearchBean;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerValue;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U1020210:商品改价审核のデータモデルです。
 * 
 * 
 * @author System Integrator Corp.
 */
public class CommodityPriceChangeReviewBean extends UIBackBean implements UISearchBean {

  private PagerValue pagerValue = new PagerValue();

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private List<CommoditySearchedBean> commoditySearchedList = new ArrayList<CommoditySearchedBean>();

  private List<String> checkedCommodityList = new ArrayList<String>();
  
  public static class CommoditySearchedBean implements Serializable {
    private String commodityCode;
    
    private String submitTime;
    
    private String responsiblePerson;
    
    private String oldOfficialPrice;
    
    private String newOfficialPrice;
    
    private String oldOfficialSpecialPrice;

    private String newOfficialSpecialPrice;

    private String oldTmallPrice;

    private String newTmallPrice;
    
    private String oldJdPrice;
    
    private String newJdPrice;

    private String ecGrossProfitRate;
    
    private String ecSpecialGrossProfitRate;
    
    private String tmallGrossProfitRate;
    
    private String jdGrossProfitRate;
    
    /**
     * @return the submitTime
     */
    public String getSubmitTime() {
      return submitTime;
    }
    
    /**
     * @param submitTime the submitTime to set
     */
    public void setSubmitTime(String submitTime) {
      this.submitTime = submitTime;
    }

    
    /**
     * @return the responsiblePerson
     */
    public String getResponsiblePerson() {
      return responsiblePerson;
    }

    
    /**
     * @param responsiblePerson the responsiblePerson to set
     */
    public void setResponsiblePerson(String responsiblePerson) {
      this.responsiblePerson = responsiblePerson;
    }

    
    /**
     * @return the oldOfficialPrice
     */
    public String getOldOfficialPrice() {
      return oldOfficialPrice;
    }

    
    /**
     * @param oldOfficialPrice the oldOfficialPrice to set
     */
    public void setOldOfficialPrice(String oldOfficialPrice) {
      this.oldOfficialPrice = oldOfficialPrice;
    }

    
    /**
     * @return the newOfficialPrice
     */
    public String getNewOfficialPrice() {
      return newOfficialPrice;
    }

    
    /**
     * @param newOfficialPrice the newOfficialPrice to set
     */
    public void setNewOfficialPrice(String newOfficialPrice) {
      this.newOfficialPrice = newOfficialPrice;
    }

    
    /**
     * @return the oldOfficialSpecialPrice
     */
    public String getOldOfficialSpecialPrice() {
      return oldOfficialSpecialPrice;
    }

    
    /**
     * @param oldOfficialSpecialPrice the oldOfficialSpecialPrice to set
     */
    public void setOldOfficialSpecialPrice(String oldOfficialSpecialPrice) {
      this.oldOfficialSpecialPrice = oldOfficialSpecialPrice;
    }

    
    /**
     * @return the newOfficialSpecialPrice
     */
    public String getNewOfficialSpecialPrice() {
      return newOfficialSpecialPrice;
    }

    
    /**
     * @param newOfficialSpecialPrice the newOfficialSpecialPrice to set
     */
    public void setNewOfficialSpecialPrice(String newOfficialSpecialPrice) {
      this.newOfficialSpecialPrice = newOfficialSpecialPrice;
    }

    
    /**
     * @return the oldTmallPrice
     */
    public String getOldTmallPrice() {
      return oldTmallPrice;
    }

    
    /**
     * @param oldTmallPrice the oldTmallPrice to set
     */
    public void setOldTmallPrice(String oldTmallPrice) {
      this.oldTmallPrice = oldTmallPrice;
    }

    
    /**
     * @return the newTmallPrice
     */
    public String getNewTmallPrice() {
      return newTmallPrice;
    }

    
    /**
     * @param newTmallPrice the newTmallPrice to set
     */
    public void setNewTmallPrice(String newTmallPrice) {
      this.newTmallPrice = newTmallPrice;
    }

    
    /**
     * @return the oldJdPrice
     */
    public String getOldJdPrice() {
      return oldJdPrice;
    }

    
    /**
     * @param oldJdPrice the oldJdPrice to set
     */
    public void setOldJdPrice(String oldJdPrice) {
      this.oldJdPrice = oldJdPrice;
    }

    
    /**
     * @return the newJdPrice
     */
    public String getNewJdPrice() {
      return newJdPrice;
    }

    
    /**
     * @param newJdPrice the newJdPrice to set
     */
    public void setNewJdPrice(String newJdPrice) {
      this.newJdPrice = newJdPrice;
    }


    
    /**
     * @return the commodityCode
     */
    public String getCommodityCode() {
      return commodityCode;
    }


    
    /**
     * @param commodityCode the commodityCode to set
     */
    public void setCommodityCode(String commodityCode) {
      this.commodityCode = commodityCode;
    }

    
    /**
     * @return the ecGrossProfitRate
     */
    public String getEcGrossProfitRate() {
      return ecGrossProfitRate;
    }

    
    /**
     * @param ecGrossProfitRate the ecGrossProfitRate to set
     */
    public void setEcGrossProfitRate(String ecGrossProfitRate) {
      this.ecGrossProfitRate = ecGrossProfitRate;
    }

    
    /**
     * @return the ecSpecialGrossProfitRate
     */
    public String getEcSpecialGrossProfitRate() {
      return ecSpecialGrossProfitRate;
    }

    
    /**
     * @param ecSpecialGrossProfitRate the ecSpecialGrossProfitRate to set
     */
    public void setEcSpecialGrossProfitRate(String ecSpecialGrossProfitRate) {
      this.ecSpecialGrossProfitRate = ecSpecialGrossProfitRate;
    }

    
    /**
     * @return the tmallGrossProfitRate
     */
    public String getTmallGrossProfitRate() {
      return tmallGrossProfitRate;
    }

    
    /**
     * @param tmallGrossProfitRate the tmallGrossProfitRate to set
     */
    public void setTmallGrossProfitRate(String tmallGrossProfitRate) {
      this.tmallGrossProfitRate = tmallGrossProfitRate;
    }

    
    /**
     * @return the jdGrossProfitRate
     */
    public String getJdGrossProfitRate() {
      return jdGrossProfitRate;
    }

    
    /**
     * @param jdGrossProfitRate the jdGrossProfitRate to set
     */
    public void setJdGrossProfitRate(String jdGrossProfitRate) {
      this.jdGrossProfitRate = jdGrossProfitRate;
    }
    
  }

  public List<CommoditySearchedBean> getCommoditySearchedList() {
    return commoditySearchedList;
  }



  /**
   * orderSearchedListを設定します。
   * 
   * 
   * @param orderSearchedList
   *            orderSearchedList
   */
  public void setCommoditySearchedList(List<CommoditySearchedBean> commoditySearchedList) {
    this.commoditySearchedList = commoditySearchedList;
  }
  
  
  
  /**
   * @return the checkedOrderList
   */
  public List<String> getCheckedCommodityList() {
    return checkedCommodityList;
  }



  
  /**
   * @param checkedOrderList the checkedOrderList to set
   */
  public void setCheckedCommodityList(List<String> checkedCommodityList) {
    this.checkedCommodityList = checkedCommodityList;
  }



  public void createAttributes(RequestParameter reqparam) {
    reqparam.copy(this);

    setCheckedCommodityList(Arrays.asList(reqparam.getAll("commodityCode")));

  }

  /**
   * モジュールIDを取得します。
   * 
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U1040170";
  }

  /**
   * モジュール名を取得します。
   * 
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.back.catalog.CommodityPriceChangeReview.0");
  }

  /**
   * pagerValueを取得します。
   * 
   * 
   * @return pagerValue
   */
  public PagerValue getPagerValue() {
    return pagerValue;
  }

  /**
   * pagerValueを設定します。
   * 
   * 
   * @param pagerValue
   *            pagerValue
   */
  public void setPagerValue(PagerValue pagerValue) {
    this.pagerValue = pagerValue;
  }

  /**
   * 入金状況を取得します。
   * 
   * 
   * @return 入金状況のNameValueリスト
   */
  public List<NameValue> getPaymentStatusList() {
    return NameValue.asList(":"
        + Messages.getString("web.bean.back.order.OrderListBean.3")
        + "/0:"
        + Messages.getString("web.bean.back.order.OrderListBean.4")
        + "/1:"
        + Messages.getString("web.bean.back.order.OrderListBean.5"));
  }
  
  /**
   * 受注タイプラジオボタン内容を設定する
   */
  public List<CodeAttribute> getOrderTypeList() {
    List<CodeAttribute> orderTypeList = new ArrayList<CodeAttribute>();
    orderTypeList.addAll(Arrays.asList(OrderType.values()));
    return orderTypeList;
  }
  
   public List<CodeAttribute> getConfirmOrderTypeList() {
      List<CodeAttribute> orderTypeList = new ArrayList<CodeAttribute>();
      orderTypeList.addAll(Arrays.asList(ConfirmObjectFlg.values()));
      return orderTypeList;
    }
  
  /**
   * 検査フラグラジオボタン内容を設定する
   */
  public List<NameValue> getOrderFlgList() {
    return NameValue.asList(":"
        + OrderFlg.ALL.getName()
        + "/" + OrderFlg.NOT_CHECKED.getValue() + ":"
        + OrderFlg.NOT_CHECKED.getName()
        + "/" + OrderFlg.CHECKED.getValue() + ":"
        + OrderFlg.CHECKED.getName());
  }
  
  public List<NameValue> getContentList() {
    return NameValue.asList("0:"
        + Messages.getString("web.bean.back.order.OrderConfirmListBean.1")
        + "/1:"
        + Messages.getString("web.bean.back.order.OrderConfirmListBean.2")
        + "/3:"
        + Messages.getString("web.bean.back.order.OrderConfirmListBean.4")
        + "/2:"
        + Messages.getString("web.bean.back.order.OrderConfirmListBean.3"));
  }


  @Override
  public void setSubJspId() {
  }

}
