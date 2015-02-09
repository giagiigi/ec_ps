package jp.co.sint.webshop.service.cart;

import java.io.Serializable;

/**
 * @author Kousen.
 */
public class CashierInvoice implements Serializable {

  private static final long serialVersionUID = 1L;

  private String invoiceFlg;

  private CashierInvoiceBase invoiceInfo;

  public static class CashierInvoiceBase implements Serializable {

    private static final long serialVersionUID = 1L;

    private String commodityName;

    private String invoiceType;

    private String customerName;

    private String companyName;

    private String taxpayerCode;

    private String address;

    private String tel;

    private String bankName;

    private String bankNo;

    private String invoiceSaveFlg;

    public String getCommodityName() {
      return commodityName;
    }

    public void setCommodityName(String commodityName) {
      this.commodityName = commodityName;
    }

    public String getInvoiceType() {
      return invoiceType;
    }

    public void setInvoiceType(String invoiceType) {
      this.invoiceType = invoiceType;
    }

    public String getCustomerName() {
      return customerName;
    }

    public void setCustomerName(String customerName) {
      this.customerName = customerName;
    }

    public String getCompanyName() {
      return companyName;
    }

    public void setCompanyName(String companyName) {
      this.companyName = companyName;
    }

    public String getTaxpayerCode() {
      return taxpayerCode;
    }

    public void setTaxpayerCode(String taxpayerCode) {
      this.taxpayerCode = taxpayerCode;
    }

    public String getAddress() {
      return address;
    }

    public void setAddress(String address) {
      this.address = address;
    }

    public String getTel() {
      return tel;
    }

    public void setTel(String tel) {
      this.tel = tel;
    }

    public String getBankName() {
      return bankName;
    }

    public void setBankName(String bankName) {
      this.bankName = bankName;
    }

    public String getBankNo() {
      return bankNo;
    }

    public void setBankNo(String bankNo) {
      this.bankNo = bankNo;
    }

    public String getInvoiceSaveFlg() {
      return invoiceSaveFlg;
    }

    public void setInvoiceSaveFlg(String invoiceSaveFlg) {
      this.invoiceSaveFlg = invoiceSaveFlg;
    }

  }

  public String getInvoiceFlg() {
    return invoiceFlg;
  }

  public void setInvoiceFlg(String invoiceFlg) {
    this.invoiceFlg = invoiceFlg;
  }

  public CashierInvoiceBase getInvoiceInfo() {
    return invoiceInfo;
  }

  public void setInvoiceInfo(CashierInvoiceBase invoiceInfo) {
    this.invoiceInfo = invoiceInfo;
  }

}
