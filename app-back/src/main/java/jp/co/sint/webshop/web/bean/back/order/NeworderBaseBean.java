package jp.co.sint.webshop.web.bean.back.order;

import java.io.Serializable;

import jp.co.sint.webshop.data.attribute.AlphaNum2;
import jp.co.sint.webshop.data.attribute.BankCode;
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.data.domain.InvoiceFlg;
import jp.co.sint.webshop.data.domain.InvoiceType;
import jp.co.sint.webshop.service.cart.Cashier;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.bean.back.UIBackBean;

/**
 * 新規受注データモデルの基底クラスです。
 * 
 * @author System Integrator Corp.
 */
public abstract class NeworderBaseBean extends UIBackBean {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  private Cashier cashier;

  /**
   * cashierを取得します。
   * 
   * @return cashier
   */
  public Cashier getCashier() {
    return cashier;
  }

  /**
   * cashierを設定します。
   * 
   * @param cashier
   *          cashier
   */
  public void setCashier(Cashier cashier) {
    this.cashier = cashier;
  }

//soukai add 2012/01/03 ob start
  /**
   * 发票信息。
   * 
   * @author Kousen.
   */
  public static class InvoiceBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String invoiceFlg;

    @Required
    @Length(50)
    @Metadata(name = "商品规格", order = 1)
    private String invoiceCommodityName;

    @Required
    @Metadata(name = "发票类型", order = 2)
    private String invoiceType;

    private String invoiceTypeName;
    
    @Required
    @Length(25)
    @Metadata(name = "发票抬头", order = 3)
    private String invoiceCustomerName;

    @Required
    @Length(60)
    @Metadata(name = "公司名称", order = 4)
    private String invoiceCompanyName;

    @Required
    @Length(20)
    @AlphaNum2
    @Metadata(name = "纳税人识别号", order = 5)
    private String invoiceTaxpayerCode;

    @Required
    @Length(60)
    @Metadata(name = "地址", order = 6)
    private String invoiceAddress;

    @Required
    @Length(20)
    @Digit(allowNegative = false)
    @Metadata(name = "电话号码", order = 7)
    private String invoiceTel;

    @Required
    @Length(50)
    @Metadata(name = "银行名称", order = 8)
    private String invoiceBankName;

    @Required
    @Length(20)
    @BankCode
    @Metadata(name = "银行编号", order = 9)
    private String invoiceBankNo;

    private String invoiceSaveFlg;

    public String getInvoiceFlg() {
      if (StringUtil.isNullOrEmpty(invoiceFlg)) {
    	  return InvoiceFlg.NO_NEED.getValue();
      }
      return invoiceFlg;
    }

    public void setInvoiceFlg(String invoiceFlg) {
      this.invoiceFlg = invoiceFlg;
    }

    public String getInvoiceCommodityName() {
      return invoiceCommodityName;
    }

    public void setInvoiceCommodityName(String invoiceCommodityName) {
      this.invoiceCommodityName = invoiceCommodityName;
    }

    public String getInvoiceType() {
      if (StringUtil.isNullOrEmpty(invoiceType)) {
    	  return InvoiceType.USUAL.getValue();
      }
      return invoiceType;
    }

    public void setInvoiceType(String invoiceType) {
      this.invoiceType = invoiceType;
    }

    public String getInvoiceCustomerName() {
      return invoiceCustomerName;
    }

    public void setInvoiceCustomerName(String invoiceCustomerName) {
      this.invoiceCustomerName = invoiceCustomerName;
    }

    public String getInvoiceCompanyName() {
      return invoiceCompanyName;
    }

    public void setInvoiceCompanyName(String invoiceCompanyName) {
      this.invoiceCompanyName = invoiceCompanyName;
    }

    public String getInvoiceTaxpayerCode() {
      return invoiceTaxpayerCode;
    }

    public void setInvoiceTaxpayerCode(String invoiceTaxpayerCode) {
      this.invoiceTaxpayerCode = invoiceTaxpayerCode;
    }

    public String getInvoiceAddress() {
      return invoiceAddress;
    }

    public void setInvoiceAddress(String invoiceAddress) {
      this.invoiceAddress = invoiceAddress;
    }

    public String getInvoiceTel() {
      return invoiceTel;
    }

    public void setInvoiceTel(String invoiceTel) {
      this.invoiceTel = invoiceTel;
    }

    public String getInvoiceBankName() {
      return invoiceBankName;
    }

    public void setInvoiceBankName(String invoiceBankName) {
      this.invoiceBankName = invoiceBankName;
    }

    public String getInvoiceBankNo() {
      return invoiceBankNo;
    }

    public void setInvoiceBankNo(String invoiceBankNo) {
      this.invoiceBankNo = invoiceBankNo;
    }

    public String getInvoiceSaveFlg() {
      return invoiceSaveFlg;
    }

    public void setInvoiceSaveFlg(String invoiceSaveFlg) {
      this.invoiceSaveFlg = invoiceSaveFlg;
    }

	/**
	 * @return the invoiceTypeName
	 */
	public String getInvoiceTypeName() {
		return invoiceTypeName;
	}

	/**
	 * @param invoiceTypeName the invoiceTypeName to set
	 */
	public void setInvoiceTypeName(String invoiceTypeName) {
		this.invoiceTypeName = invoiceTypeName;
	}

  }
//soukai add 2012/01/03 ob end
}
