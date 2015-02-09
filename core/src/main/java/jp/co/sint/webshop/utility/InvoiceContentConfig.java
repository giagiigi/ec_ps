package jp.co.sint.webshop.utility;

import java.io.Serializable;


/**
 * 发票内容信息
 * 
 * @author ks.
 */
public class InvoiceContentConfig implements Serializable {
  /**
   * serial version UID
   */
  private static final long serialVersionUID = 1L;

  /** 发票内容 */
  private String invoiceContent;

  
  /**
   * invoiceContentを取得します。
   * 
   * @return invoiceContent
   */
  public String getInvoiceContent() {
    return invoiceContent;
  }


  
  /**
   * invoiceContentを設定します。
   * 
   * @param invoiceContent
   *          invoiceContent
   */
  public void setInvoiceContent(String invoiceContent) {
    this.invoiceContent = invoiceContent;
  }


}
