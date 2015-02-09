package jp.co.sint.webshop.web.bean.back.communication;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.Datetime;
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.web.bean.UISearchBean;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerValue;
import jp.co.sint.webshop.web.webutility.RequestParameter;

/**
 * U1061210:限时限量折扣管理。
 * 
 * @author KS.
 */
public class DiscountListBean extends UIBackBean implements UISearchBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private PagerValue pagerValue = new PagerValue();

  @AlphaNum2
  @Length(16)
  @Metadata(name = "折扣编号")
  private String searchDiscountCode;

  @Length(50)
  @Metadata(name = "折扣名称")
  private String searchDiscountName;

  @Datetime(format = "yyyy/MM/dd HH:mm:ss")
  @Metadata(name = "折扣开始时间(From)")
  private String searchDiscountStartDatetimeFrom;

  @Datetime(format = "yyyy/MM/dd HH:mm:ss")
  @Metadata(name = "折扣结束时间(To)")
  private String searchDiscountEndDatetimeTo;

  private String searchDiscountStatus;

  @Length(1)
  @Digit
  private String soCouponFlg;

  /** 更新权限持有区分 */
  private Boolean updateAuthorizeFlg;

  /** 删除权限持有区分 */
  private Boolean deleteAuthorizeFlg;

  private List<DiscountDetailBean> list = new ArrayList<DiscountDetailBean>();

  /**
   * @return the searchDiscountCode
   */
  public String getSearchDiscountCode() {
    return searchDiscountCode;
  }

  /**
   * @return the searchDiscountName
   */
  public String getSearchDiscountName() {
    return searchDiscountName;
  }

  /**
   * @return the searchDiscountStartDatetimeFrom
   */
  public String getSearchDiscountStartDatetimeFrom() {
    return searchDiscountStartDatetimeFrom;
  }

  /**
   * @return the searchDiscountEndDatetimeTo
   */
  public String getSearchDiscountEndDatetimeTo() {
    return searchDiscountEndDatetimeTo;
  }

  /**
   * @return the searchDiscountStatus
   */
  public String getSearchDiscountStatus() {
    return searchDiscountStatus;
  }

  /**
   * @return the updateAuthorizeFlg
   */
  public Boolean getUpdateAuthorizeFlg() {
    return updateAuthorizeFlg;
  }

  /**
   * @return the deleteAuthorizeFlg
   */
  public Boolean getDeleteAuthorizeFlg() {
    return deleteAuthorizeFlg;
  }

  /**
   * @return the list
   */
  public List<DiscountDetailBean> getList() {
    return list;
  }

  /**
   * @param searchDiscountCode
   *          the searchDiscountCode to set
   */
  public void setSearchDiscountCode(String searchDiscountCode) {
    this.searchDiscountCode = searchDiscountCode;
  }

  /**
   * @param searchDiscountName
   *          the searchDiscountName to set
   */
  public void setSearchDiscountName(String searchDiscountName) {
    this.searchDiscountName = searchDiscountName;
  }

  /**
   * @param searchDiscountStartDatetimeFrom
   *          the searchDiscountStartDatetimeFrom to set
   */
  public void setSearchDiscountStartDatetimeFrom(String searchDiscountStartDatetimeFrom) {
    this.searchDiscountStartDatetimeFrom = searchDiscountStartDatetimeFrom;
  }

  /**
   * @param searchDiscountEndDatetimeTo
   *          the searchDiscountEndDatetimeTo to set
   */
  public void setSearchDiscountEndDatetimeTo(String searchDiscountEndDatetimeTo) {
    this.searchDiscountEndDatetimeTo = searchDiscountEndDatetimeTo;
  }

  /**
   * @param searchDiscountStatus
   *          the searchDiscountStatus to set
   */
  public void setSearchDiscountStatus(String searchDiscountStatus) {
    this.searchDiscountStatus = searchDiscountStatus;
  }

  /**
   * @param updateAuthorizeFlg
   *          the updateAuthorizeFlg to set
   */
  public void setUpdateAuthorizeFlg(Boolean updateAuthorizeFlg) {
    this.updateAuthorizeFlg = updateAuthorizeFlg;
  }

  /**
   * @param deleteAuthorizeFlg
   *          the deleteAuthorizeFlg to set
   */
  public void setDeleteAuthorizeFlg(Boolean deleteAuthorizeFlg) {
    this.deleteAuthorizeFlg = deleteAuthorizeFlg;
  }

  /**
   * @param list
   *          the list to set
   */
  public void setList(List<DiscountDetailBean> list) {
    this.list = list;
  }

  /**
   * U1061010:促销活动のサブモデルです。
   * 
   * @author System Integrator Corp.
   */
  public static class DiscountDetailBean implements Serializable {

    private static final long serialVersionUID = 1L;

    // 折扣编号
    private String discountCode;

    // 折扣名称
    private String discountName;

    // 折扣开始时间
    private String discountStartDatetime;

    // 折扣结束时间
    private String discountEndDatetime;

    // 商品数量
    private Long commodityCount;

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
     * @return the discountStartDatetime
     */
    public String getDiscountStartDatetime() {
      return discountStartDatetime;
    }

    /**
     * @return the discountEndDatetime
     */
    public String getDiscountEndDatetime() {
      return discountEndDatetime;
    }

    /**
     * @return the commodityCount
     */
    public Long getCommodityCount() {
      return commodityCount;
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
     * @param discountStartDatetime
     *          the discountStartDatetime to set
     */
    public void setDiscountStartDatetime(String discountStartDatetime) {
      this.discountStartDatetime = discountStartDatetime;
    }

    /**
     * @param discountEndDatetime
     *          the discountEndDatetime to set
     */
    public void setDiscountEndDatetime(String discountEndDatetime) {
      this.discountEndDatetime = discountEndDatetime;
    }

    /**
     * @param commodityCount
     *          the commodityCount to set
     */
    public void setCommodityCount(Long commodityCount) {
      this.commodityCount = commodityCount;
    }

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

  @Override
  public void createAttributes(RequestParameter reqparam) {
    setSearchDiscountCode(reqparam.get("searchDiscountCode"));
    setSearchDiscountName(reqparam.get("searchDiscountName"));
    setSearchDiscountStartDatetimeFrom(reqparam.getDateTimeString("searchDiscountStartDatetimeFrom"));
    setSearchDiscountEndDatetimeTo(reqparam.getDateTimeString("searchDiscountEndDatetimeTo"));
    setSearchDiscountStatus(reqparam.get("searchDiscountStatus"));
    this.setSoCouponFlg(reqparam.get("soCouponFlg"));


  }

  @Override
  public String getModuleId() {
    return "U1061210";
  }

  @Override
  public void setSubJspId() {
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("web.bean.back.communication.DiscountListBean.0");
  }


  public void setSoCouponFlg(String soCouponFlg) {
    this.soCouponFlg = soCouponFlg;
  }

  public String getSoCouponFlg() {
    return soCouponFlg;
  }
}

