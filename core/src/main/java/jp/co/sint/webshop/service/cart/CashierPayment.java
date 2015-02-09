package jp.co.sint.webshop.service.cart;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.data.attribute.Digit;
import jp.co.sint.webshop.data.attribute.Email;
import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.attribute.Metadata;
import jp.co.sint.webshop.data.attribute.Required;
import jp.co.sint.webshop.data.domain.PaymentMethodType;
import jp.co.sint.webshop.data.dto.PaymentMethod;

/**
 * @author System Integrator Corp.
 */
public class CashierPayment implements Serializable {

  private static final long serialVersionUID = 1L;

  private String shopCode;

  private CashierPaymentTypeBase selectPayment;

  public String getPaymentMethodCode() {
    if (selectPayment == null) {
      return "";
    } else {
      return selectPayment.getPaymentMethodCode();
    }
  }

  public static class CashierPaymentTypeBase implements Serializable {

    private static final long serialVersionUID = 1L;

    private PaymentMethod paymentMethod;

    private String paymentMethodCode;

    private String paymentMethodName;

    private String paymentMethodType;

    private BigDecimal paymentCommission;

    // 20120109 shen add start
    private BigDecimal orderPriceThreshold;

    // 20120109 shen add end

    public boolean isCashOnDelivery() {
      return getPaymentMethodType().equals(PaymentMethodType.CASH_ON_DELIVERY.getValue());
    }

    public boolean isBanking() {
      return getPaymentMethodType().equals(PaymentMethodType.BANKING.getValue());
    }

    public boolean isCreditcard() {
      return getPaymentMethodType().equals(PaymentMethodType.CREDITCARD.getValue());
    }

    public boolean isCvsPayment() {
      return getPaymentMethodType().equals(PaymentMethodType.CVS_PAYMENT.getValue());
    }

    public boolean isDigitalCash() {
      return getPaymentMethodType().equals(PaymentMethodType.DIGITAL_CASH.getValue());
    }

    public boolean isNoPayment() {
      return getPaymentMethodType().equals(PaymentMethodType.NO_PAYMENT.getValue());
    }

    public boolean isPointInFull() {
      return getPaymentMethodType().equals(PaymentMethodType.POINT_IN_FULL.getValue());
    }

    /**
     * @return the paymentMethodCode
     */
    public String getPaymentMethodCode() {
      return paymentMethodCode;
    }

    /**
     * @param paymentMethodCode
     *          the paymentMethodCode to set
     */
    public void setPaymentMethodCode(String paymentMethodCode) {
      this.paymentMethodCode = paymentMethodCode;
    }

    /**
     * @return the paymentMethodName
     */
    public String getPaymentMethodName() {
      return paymentMethodName;
    }

    /**
     * @param paymentMethodName
     *          the paymentMethodName to set
     */
    public void setPaymentMethodName(String paymentMethodName) {
      this.paymentMethodName = paymentMethodName;
    }

    /**
     * @return the paymentMethodType
     */
    public String getPaymentMethodType() {
      return paymentMethodType;
    }

    /**
     * @param paymentMethodType
     *          the paymentMethodType to set
     */
    public void setPaymentMethodType(String paymentMethodType) {
      this.paymentMethodType = paymentMethodType;
    }

    /**
     * @return the paymentCommission
     */
    public BigDecimal getPaymentCommission() {
      return paymentCommission;
    }

    /**
     * @param paymentCommission
     *          the paymentCommission to set
     */
    public void setPaymentCommission(BigDecimal paymentCommission) {
      this.paymentCommission = paymentCommission;
    }

    /**
     * @return the paymentMethod
     */
    public PaymentMethod getPaymentMethod() {
      return paymentMethod;
    }

    /**
     * @param paymentMethod
     *          the paymentMethod to set
     */
    public void setPaymentMethod(PaymentMethod paymentMethod) {
      this.paymentMethod = paymentMethod;
    }

    /**
     * @return the orderPriceThreshold
     */
    public BigDecimal getOrderPriceThreshold() {
      return orderPriceThreshold;
    }

    /**
     * @param orderPriceThreshold
     *          the orderPriceThreshold to set
     */
    public void setOrderPriceThreshold(BigDecimal orderPriceThreshold) {
      this.orderPriceThreshold = orderPriceThreshold;
    }

  }

  public static class CashierPaymentTypeCreditCard extends CashierPaymentTypeBase {

    private static final long serialVersionUID = 1L;

    @Digit
    @Required
    @Length(16)
    @Metadata(name = "カード番号")
    private String cardNo;

    @Digit
    @Required
    @Length(2)
    @Metadata(name = "カード有効期限(月)")
    private String cardExpirationMonth;

    @Digit
    @Required
    @Length(2)
    @Metadata(name = "カード有効期限(年)")
    private String cardExpirationYear;

    @Required
    @Metadata(name = "カード名義人")
    private String cardUserName;

    /**
     * @return the cardNo
     */
    public String getCardNo() {
      return cardNo;
    }

    /**
     * @param cardNo
     *          the cardNo to set
     */
    public void setCardNo(String cardNo) {
      this.cardNo = cardNo;
    }

    /**
     * @return the cardExpirationMonth
     */
    public String getCardExpirationMonth() {
      return cardExpirationMonth;
    }

    /**
     * @param cardExpirationMonth
     *          the cardExpirationMonth to set
     */
    public void setCardExpirationMonth(String cardExpirationMonth) {
      this.cardExpirationMonth = cardExpirationMonth;
    }

    /**
     * @return the cardExpirationYear
     */
    public String getCardExpirationYear() {
      return cardExpirationYear;
    }

    /**
     * @param cardExpirationYear
     *          the cardExpirationYear to set
     */
    public void setCardExpirationYear(String cardExpirationYear) {
      this.cardExpirationYear = cardExpirationYear;
    }

    /**
     * @return the cardUserName
     */
    public String getCardUserName() {
      return cardUserName;
    }

    /**
     * @param cardUserName
     *          the cardUserName to set
     */
    public void setCardUserName(String cardUserName) {
      this.cardUserName = cardUserName;
    }
  }

  public static class CashierPaymentTypeCvs extends CashierPaymentTypeBase {

    private static final long serialVersionUID = 1L;

    @Required
    @Metadata(name = "コンビニ種類")
    private String convenienceCode;

    // リストボックスの選択枝
    @Metadata(name = "コンビニ種類")
    private List<CodeAttribute> displayConvenienceCodeList;

    @Required
    @Metadata(name = "受取人（姓）")
    private String lastName;

    @Required
    @Metadata(name = "受取人（名）")
    private String firstName;

    @Required
    @Metadata(name = "受取人 (姓:カナ)")
    private String lastNameKana;

    @Required
    @Metadata(name = "受取人 (名:カナ)")
    private String firstNameKana;

    /**
     * @return the convenienceCode
     */
    public String getConvenienceCode() {
      return convenienceCode;
    }

    /**
     * @param convenienceCode
     *          the convenienceCode to set
     */
    public void setConvenienceCode(String convenienceCode) {
      this.convenienceCode = convenienceCode;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
      return lastName;
    }

    /**
     * @param lastName
     *          the lastName to set
     */
    public void setLastName(String lastName) {
      this.lastName = lastName;
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
      return firstName;
    }

    /**
     * @param firstName
     *          the firstName to set
     */
    public void setFirstName(String firstName) {
      this.firstName = firstName;
    }

    /**
     * @return the displayConvenienceCodeList
     */
    public List<CodeAttribute> getDisplayConvenienceCodeList() {
      return displayConvenienceCodeList;
    }

    /**
     * @param displayConvenienceCodeList
     *          the displayConvenienceCodeList to set
     */
    public void setDisplayConvenienceCodeList(List<CodeAttribute> displayConvenienceCodeList) {
      this.displayConvenienceCodeList = displayConvenienceCodeList;
    }

    /**
     * lastNameKanaを返します。
     * 
     * @return the lastNameKana
     */
    public String getLastNameKana() {
      return lastNameKana;
    }

    /**
     * lastNameKanaを設定します。
     * 
     * @param lastNameKana
     *          設定する lastNameKana
     */
    public void setLastNameKana(String lastNameKana) {
      this.lastNameKana = lastNameKana;
    }

    /**
     * firstNameKanaを返します。
     * 
     * @return the firstNameKana
     */
    public String getFirstNameKana() {
      return firstNameKana;
    }

    /**
     * firstNameKanaを設定します。
     * 
     * @param firstNameKana
     *          設定する firstNameKana
     */
    public void setFirstNameKana(String firstNameKana) {
      this.firstNameKana = firstNameKana;
    }
  }

  public static class CashierPaymentTypeDigitalCash extends CashierPaymentTypeBase {

    private static final long serialVersionUID = 1L;

    @Required
    @Digit
    @Metadata(name = "電子マネー種類")
    private String digitalCashType;

    // リストボックスの選択枝
    @Metadata(name = "電子マネー種類")
    private List<CodeAttribute> displayDigitalCashModeList;

    @Required
    @Email
    @Metadata(name = "支払メールアドレス")
    private String digitalCashPaymentEmail;

    /**
     * @return the digitalCashType
     */
    public String getDigitalCashType() {
      return digitalCashType;
    }

    /**
     * @param digitalCashType
     *          the digitalCashType to set
     */
    public void setDigitalCashType(String digitalCashType) {
      this.digitalCashType = digitalCashType;
    }

    /**
     * @return the digitalCashPaymentEmail
     */
    public String getDigitalCashPaymentEmail() {
      return digitalCashPaymentEmail;
    }

    /**
     * @param digitalCashPaymentEmail
     *          the digitalCashPaymentEmail to set
     */
    public void setDigitalCashPaymentEmail(String digitalCashPaymentEmail) {
      this.digitalCashPaymentEmail = digitalCashPaymentEmail;
    }

    /**
     * @return the displayDigitalCashModeList
     */
    public List<CodeAttribute> getDisplayDigitalCashModeList() {
      return displayDigitalCashModeList;
    }

    /**
     * @param displayDigitalCashModeList
     *          the displayDigitalCashModeList to set
     */
    public void setDisplayDigitalCashModeList(List<CodeAttribute> displayDigitalCashModeList) {
      this.displayDigitalCashModeList = displayDigitalCashModeList;
    }
  }

  /**
   * @return the shopCode
   */
  public String getShopCode() {
    return shopCode;
  }

  /**
   * @param shopCode
   *          the shopCode to set
   */
  public void setShopCode(String shopCode) {
    this.shopCode = shopCode;
  }

  /**
   * @return the selectPayment
   */
  public CashierPaymentTypeBase getSelectPayment() {
    return selectPayment;
  }

  /**
   * @param selectPayment
   *          the selectPayment to set
   */
  public void setSelectPayment(CashierPaymentTypeBase selectPayment) {
    this.selectPayment = selectPayment;
  }
}
