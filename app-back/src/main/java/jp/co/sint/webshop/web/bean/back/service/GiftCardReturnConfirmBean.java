package jp.co.sint.webshop.web.bean.back.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.data.attribute.Datetime;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.domain.GiftCardReturnList;
import jp.co.sint.webshop.data.dto.GiftCardReturnApply;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.web.bean.UISearchBean;
import jp.co.sint.webshop.web.bean.back.UIBackBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerValue;
import jp.co.sint.webshop.web.webutility.RequestParameter;

public class GiftCardReturnConfirmBean extends UIBackBean implements UISearchBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private PagerValue pagerValue = new PagerValue();

  private List<String> dateString = new ArrayList<String>();
  
  private List<String> confirmDateString = new ArrayList<String>();
  
  private String searchOrderNo;
  
  /** 礼品卡发行开始日时(From) */
  @Datetime(format = DateUtil.DEFAULT_DATE_FORMAT)
  @Metadata(name = "礼品卡发行开始日时(From)", order = 9)
  private String searchReturnDatetimeFrom;

  /** 礼品卡发行开始日时(To) */
  @Datetime(format = DateUtil.DEFAULT_DATE_FORMAT)
  @Metadata(name = "礼品卡发行开始日时(To)", order = 10)
  private String searchReturnDatetimeTo;
  
  private String searchReturnFlg;
  
  private List<GiftCardReturnApply> list = new ArrayList<GiftCardReturnApply>();
  
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


  public static class CustomerSearchedBean implements Serializable {

    /** serial version uid */
    private static final long serialVersionUID = 1L;


  }

  /**
   * リクエストパラメータから値を取得します。
   * 
   * @param reqparam
   *          リクエストパラメータ
   */
  public void createAttributes(RequestParameter reqparam) {
    reqparam.copy(this);
    if (getList() != null && getList().size() > 0) {
      for (GiftCardReturnApply gcra : getList() ) {
        if (NumUtil.isDecimal(reqparam.get("confirmAmount_" + gcra.getOrderNo()))) {
          gcra.setConfirmAmount(new BigDecimal(reqparam.get("confirmAmount_" + gcra.getOrderNo())));
        }

      }
    }

    setSearchOrderNo(reqparam.get("searchOrderNo"));
    setSearchReturnDatetimeFrom(reqparam.getDateString("searchReturnDatetimeFrom"));
    setSearchReturnDatetimeTo(reqparam.getDateString("searchReturnDatetimeTo"));
    setSearchReturnFlg(reqparam.get("searchReturnFlg"));
  }
  
  public List<CodeAttribute> getReturnFlgList() {
    List<CodeAttribute> returnFlgListList = new ArrayList<CodeAttribute>();
    returnFlgListList.addAll(Arrays.asList(GiftCardReturnList.values()));
    return returnFlgListList;
  }

  /**
   * サブJSPを設定します。
   */
  @Override
  public void setSubJspId() {
  }

  /**
   * モジュールIDを取得します。
   * 
   * @return モジュールID
   */
  public String getModuleId() {
    return "U1090120";
  }

  /**
   * モジュール名を取得します。
   * 
   * @return モジュール名
   */
  public String getModuleName() {
    return Messages.getString("礼品卡退款确认");
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
   * @return the searchReturnDatetimeFrom
   */
  public String getSearchReturnDatetimeFrom() {
    return searchReturnDatetimeFrom;
  }

  
  /**
   * @param searchReturnDatetimeFrom the searchReturnDatetimeFrom to set
   */
  public void setSearchReturnDatetimeFrom(String searchReturnDatetimeFrom) {
    this.searchReturnDatetimeFrom = searchReturnDatetimeFrom;
  }

  
  /**
   * @return the searchReturnDatetimeTo
   */
  public String getSearchReturnDatetimeTo() {
    return searchReturnDatetimeTo;
  }

  
  /**
   * @param searchReturnDatetimeTo the searchReturnDatetimeTo to set
   */
  public void setSearchReturnDatetimeTo(String searchReturnDatetimeTo) {
    this.searchReturnDatetimeTo = searchReturnDatetimeTo;
  }

  
  /**
   * @return the searchReturnFlg
   */
  public String getSearchReturnFlg() {
    return searchReturnFlg;
  }

  
  /**
   * @param searchReturnFlg the searchReturnFlg to set
   */
  public void setSearchReturnFlg(String searchReturnFlg) {
    this.searchReturnFlg = searchReturnFlg;
  }

  
  /**
   * @return the list
   */
  public List<GiftCardReturnApply> getList() {
    return list;
  }

  
  /**
   * @param list the list to set
   */
  public void setList(List<GiftCardReturnApply> list) {
    this.list = list;
  }

  
  /**
   * @return the dateString
   */
  public List<String> getDateString() {
    return dateString;
  }

  
  /**
   * @param dateString the dateString to set
   */
  public void setDateString(List<String> dateString) {
    this.dateString = dateString;
  }

  
  /**
   * @return the confirmDateString
   */
  public List<String> getConfirmDateString() {
    return confirmDateString;
  }

  
  /**
   * @param confirmDateString the confirmDateString to set
   */
  public void setConfirmDateString(List<String> confirmDateString) {
    this.confirmDateString = confirmDateString;
  }
}
