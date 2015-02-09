package jp.co.sint.webshop.service.communication;

import jp.co.sint.webshop.data.attribute.Datetime;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.service.SearchCondition;
import jp.co.sint.webshop.utility.DateUtil;

public class GiftCardReturnListSearchCondition extends SearchCondition {

  /** uid */
  private static final long serialVersionUID = 1L;

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

}
