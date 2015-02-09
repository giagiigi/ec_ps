package jp.co.sint.webshop.service.customer;

import java.io.Serializable;

import jp.co.sint.webshop.data.dto.InquiryDetail;
import jp.co.sint.webshop.data.dto.InquiryHeader;

public class InquiryInfo implements Serializable {

  /** Serial Version UID */
  private static final long serialVersionUID = 1L;

  private InquiryHeader inquiryHeader;

  private InquiryDetail inquiryDetail;
  
  /**
   * @return the inquiryHeader
   */
  public InquiryHeader getInquiryHeader() {
    return inquiryHeader;
  }

  /**
   * @param inquiryHeader
   *          the inquiryHeader to set
   */
  public void setInquiryHeader(InquiryHeader inquiryHeader) {
    this.inquiryHeader = inquiryHeader;
  }

  /**
   * @return the inquiryDetail
   */
  public InquiryDetail getInquiryDetail() {
    return inquiryDetail;
  }

  /**
   * @param inquiryDetail
   *          the inquiryDetail to set
   */
  public void setInquiryDetail(InquiryDetail inquiryDetail) {
    this.inquiryDetail = inquiryDetail;
  }

}
