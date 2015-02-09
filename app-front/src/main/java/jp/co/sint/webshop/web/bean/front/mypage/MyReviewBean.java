package jp.co.sint.webshop.web.bean.front.mypage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Range;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.web.bean.UISearchBean;
import jp.co.sint.webshop.web.bean.front.UIFrontBean;
import jp.co.sint.webshop.web.text.front.Messages;
import jp.co.sint.webshop.web.webutility.PagerValue;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U2030210:お客様情報登録1のデータモデルです。
 * 
 * @author System Integrator Corp.
 */
public class MyReviewBean extends UIFrontBean implements UISearchBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  /** 氏名(姓) */
  @Digit
  @Range(min = 1, max = 5)
  @Metadata(name = "评分")
  private String point;

  /** 氏名(姓) */
  @Required
  @Length(100)
  @Metadata(name = "主题")
  private String title;

  /** 氏名(名) */
  @Required
  @Length(200)
  @Metadata(name = "评价内容")
  private String content;

  @Required
  @Length(200)
  @Metadata(name = "评价商品编号")
  private String commodityCode;

  @Required
  @Length(200)
  @Metadata(name = "评价商品所属订单编号")
  private String orderNo;

  private PagerValue pagerValue;

  
  private String searchOrderDateType;
  
  
  private List<MyOrderBean> list = new ArrayList<MyOrderBean>();

  public static class MyOrderBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String orderNo;

    private String orderDate;

    private List<MyOrderDetailBean> list = new ArrayList<MyOrderDetailBean>();

    public static class MyOrderDetailBean implements Serializable {

      private static final long serialVersionUID = 1L;

      private String shopCode;
      
      private String commodityCode;

      private String commodityName;

      private String price;

      private String reviewFlag = "false";// 评价标志，默认为未评价
      
      private String isGift = "false"; //是否为赠品
      

      /**
       * @return the commodityName
       */
      public String getCommodityName() {
        return commodityName;
      }

      /**
       * @param commodityName
       *          the commodityName to set
       */
      public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
      }

      /**
       * @return the price
       */
      public String getPrice() {
        return price;
      }

      /**
       * @param price
       *          the price to set
       */
      public void setPrice(String price) {
        this.price = price;
      }

      /**
       * @return the reviewFlag
       */
      public String getReviewFlag() {
        return reviewFlag;
      }

      /**
       * @param reviewFlag
       *          the reviewFlag to set
       */
      public void setReviewFlag(String reviewFlag) {
        this.reviewFlag = reviewFlag;
      }

      /**
       * @return the commodityCode
       */
      public String getCommodityCode() {
        return commodityCode;
      }

      /**
       * @param commodityCode
       *          the commodityCode to set
       */
      public void setCommodityCode(String commodityCode) {
        this.commodityCode = commodityCode;
      }

      
      /**
       * @return the isGift
       */
      public String getIsGift() {
        return isGift;
      }

      
      /**
       * @param isGift the isGift to set
       */
      public void setIsGift(String isGift) {
        this.isGift = isGift;
      }

      
      /**
       * @return the shopCode
       */
      public String getShopCode() {
        return shopCode;
      }

      
      /**
       * @param shopCode the shopCode to set
       */
      public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
      }

    }

    /**
     * @return the orderNo
     */
    public String getOrderNo() {
      return orderNo;
    }

    /**
     * @param orderNo
     *          the orderNo to set
     */
    public void setOrderNo(String orderNo) {
      this.orderNo = orderNo;
    }

    /**
     * @return the orderDate
     */
    public String getOrderDate() {
      return orderDate;
    }

    /**
     * @param orderDate
     *          the orderDate to set
     */
    public void setOrderDate(String orderDate) {
      this.orderDate = orderDate;
    }

    /**
     * @return the list
     */
    public List<MyOrderDetailBean> getList() {
      return list;
    }

    /**
     * @param list
     *          the list to set
     */
    public void setList(List<MyOrderDetailBean> list) {
      this.list = list;
    }

  }

  /**
   * サブJSPを設定します。
   */
  @Override
  public void setSubJspId() {
    addSubJspId("/common/header");
    addSubJspId("/catalog/topic_path");
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
    return "U2030210";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.front.mypage.MyReviewBean.0");
  }

  /**
   * @return the pagerValue
   */
  public PagerValue getPagerValue() {
    return pagerValue;
  }

  /**
   * @param pagerValue
   *          the pagerValue to set
   */
  public void setPagerValue(PagerValue pagerValue) {
    this.pagerValue = pagerValue;
  }

  /**
   * @return the point
   */
  public String getPoint() {
    return point;
  }

  /**
   * @param point
   *          the point to set
   */
  public void setPoint(String point) {
    this.point = point;
  }


  /**
   * @return the content
   */
  public String getContent() {
    return content;
  }

  /**
   * @param content
   *          the content to set
   */
  public void setContent(String content) {
    this.content = content;
  }

  /**
   * @return the list
   */
  public List<MyOrderBean> getList() {
    return list;
  }

  /**
   * @param list
   *          the list to set
   */
  public void setList(List<MyOrderBean> list) {
    this.list = list;
  }

  /**
   * @return the commodityCode
   */
  public String getCommodityCode() {
    return commodityCode;
  }

  /**
   * @param commodityCode
   *          the commodityCode to set
   */
  public void setCommodityCode(String commodityCode) {
    this.commodityCode = commodityCode;
  }

  /**
   * @return the orderNo
   */
  public String getOrderNo() {
    return orderNo;
  }

  /**
   * @param orderNo
   *          the orderNo to set
   */
  public void setOrderNo(String orderNo) {
    this.orderNo = orderNo;
  }

  
  /**
   * @return the title
   */
  public String getTitle() {
    return title;
  }

  
  /**
   * @param title the title to set
   */
  public void setTitle(String title) {
    this.title = title;
  }

  
  /**
   * @return the searchOrderDateType
   */
  public String getSearchOrderDateType() {
    return searchOrderDateType;
  }

  
  /**
   * @param searchOrderDateType the searchOrderDateType to set
   */
  public void setSearchOrderDateType(String searchOrderDateType) {
    this.searchOrderDateType = searchOrderDateType;
  }

}
