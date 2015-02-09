package jp.co.sint.webshop.validation;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.dto.ShippingDetail;
import jp.co.sint.webshop.data.dto.ShippingHeader;
import jp.co.sint.webshop.service.order.OrderContainer;
import jp.co.sint.webshop.service.order.ShippingContainer;
import jp.co.sint.webshop.text.Messages;
import jp.co.sint.webshop.utility.BigDecimalUtil;
import jp.co.sint.webshop.utility.NumUtil;

public class NumberLimitPolicy {

  /** 1受注の出荷数 */
  private Long maxShippingHeaderNum;

  /** 1出荷の商品種別数（出荷明細数） */
  private Long maxShippingDetailNum;

  /** 1受注の最大購入金額数 */
  private BigDecimal maxTotalCurrencyNum;

  /** 1受注の最大購入商品数 */
  private Long maxTotalAmountNum;

  /** 1出荷の最大 */
  private BigDecimal maxShippingCharge;
  
  // 2012/11/24 促销对应 ob add start
  /** 1セットの最小明細数 */
  private Long minSetNum;
  // 2012/11/24 促销对应 ob add end

  /**
   * maxShippingDetailNumを取得します。
   * 
   * @return maxShippingDetailNum
   */
  public Long getMaxShippingDetailNum() {
    return maxShippingDetailNum;
  }

  /**
   * maxShippingDetailNumを設定します。
   * 
   * @param maxShippingDetailNum
   *          maxShippingDetailNum
   */
  public void setMaxShippingDetailNum(Long maxShippingDetailNum) {
    this.maxShippingDetailNum = maxShippingDetailNum;
  }

  /**
   * maxShippingHeaderNumを取得します。
   * 
   * @return maxShippingHeaderNum
   */
  public Long getMaxShippingHeaderNum() {
    return maxShippingHeaderNum;
  }

  /**
   * maxShippingHeaderNumを設定します。
   * 
   * @param maxShippingHeaderNum
   *          maxShippingHeaderNum
   */
  public void setMaxShippingHeaderNum(Long maxShippingHeaderNum) {
    this.maxShippingHeaderNum = maxShippingHeaderNum;
  }

  /**
   * maxTotalCurrencyNumを取得します。
   * 
   * @return maxTotalCurrencyNum
   */
  public BigDecimal getMaxTotalCurrencyNum() {
    return maxTotalCurrencyNum;
  }

  /**
   * maxTotalCurrencyNumを設定します。
   * 
   * @param maxTotalCurrencyNum
   *          maxTotalCurrencyNum
   */
  public void setMaxTotalCurrencyNum(BigDecimal maxTotalCurrencyNum) {
    this.maxTotalCurrencyNum = maxTotalCurrencyNum;
  }

  /**
   * maxTotalAmountNumを取得します。
   * 
   * @return maxTotalAmountNum
   */
  public Long getMaxTotalAmountNum() {
    return maxTotalAmountNum;
  }

  /**
   * maxTotalAmountNumを設定します。
   * 
   * @param maxTotalAmountNum
   *          maxTotalAmountNum
   */
  public void setMaxTotalAmountNum(Long maxTotalAmountNum) {
    this.maxTotalAmountNum = maxTotalAmountNum;
  }

  //add by V10-CH 170 start
//  public ValidationResult validTotalCurrency(Long currency) {
//    ValidationResult result = null;
//    if (currency > getMaxTotalCurrencyNum()) {
//      result = new ValidationResult();
//      //modify by V10-CH 170 start
//      result.setName(Messages.getString("validation.NumberLimitPolicy.0"));
//      result.setMessage(MessageFormat.format(Messages.getString("validation.NumberLimitPolicy.1"),
//        getMaxTotalCurrencyNum()));
//      //modify by V10-CH 170 end
//    } else {
//      result = new ValidationResult(true);
//    }
//    return result;
//  }
  public ValidationResult validTotalCurrency(BigDecimal currency) {
    ValidationResult result = null;
    if (BigDecimalUtil.isAbove(currency, getMaxTotalCurrencyNum())) {
      result = new ValidationResult();
      result.setName(Messages.getString("validation.NumberLimitPolicy.0"));
      result.setMessage(MessageFormat.format(Messages.getString("validation.NumberLimitPolicy.1"), //$NON-NLS-1$
          getMaxTotalCurrencyNum()));
    } else {
      result = new ValidationResult(true);
    }
    return result;
  }
  //add by V10-CH 170 end

  public ValidationResult validTotalAmount(Long amount) {
    ValidationResult result = null;
    if (amount > getMaxTotalAmountNum()) {
      result = new ValidationResult();
      //modify by V10-CH 170 start
      result.setName(Messages.getString("validation.NumberLimitPolicy.2"));
      result.setMessage(MessageFormat.format(Messages.getString("validation.NumberLimitPolicy.3"),
       getMaxTotalAmountNum()));
      //modify by V10-CH 170 end
    } else {
      result = new ValidationResult(true);
    }
    return result;
  }

  public ValidationResult validMaxShippingHeader(OrderContainer order) {
    ValidationResult result = null;
    if (order.getShippings().size() > getMaxShippingHeaderNum()) {
      result = new ValidationResult();
      //modify by V10-CH 170 start
      result.setName(Messages.getString("validation.NumberLimitPolicy.4"));
      result.setMessage(MessageFormat.format(Messages.getString("validation.NumberLimitPolicy.5"),
       getMaxShippingHeaderNum()));
      //modify by V10-CH 170 end
    } else {
      result = new ValidationResult(true);
    }
    return result;
  }

  public ValidationResult validMaxShippingDetail(ShippingContainer shipping) {
    ValidationResult result = null;
    if (shipping.getShippingDetails().size() > getMaxShippingDetailNum()) {
      result = new ValidationResult();
      //modify by V10-CH 170 start
      result.setName(Messages.getString("validation.NumberLimitPolicy.6"));
      result.setMessage(MessageFormat.format(Messages.getString("validation.NumberLimitPolicy.7"),
       getMaxShippingDetailNum()));
      //modify by V10-CH 170 end
    } else {
      result = new ValidationResult(true);
    }
    return result;
  }

  public ValidationResult validMaxShippingCharge(ShippingContainer shipping) {
    ValidationResult result = null;

    BigDecimal shippingCharge = shipping.getShippingHeader().getShippingCharge();

    if (BigDecimalUtil.isAbove(shippingCharge, getMaxShippingCharge())) {
      result = new ValidationResult();
      //modify by V10-CH 170 start
      result.setName(Messages.getString("validation.NumberLimitPolicy.8"));
      result.setMessage(MessageFormat.format(Messages.getString("validation.NumberLimitPolicy.9"),
       getMaxShippingCharge()));
      //modify by V10-CH 170 end
    } else {
      result = new ValidationResult(true);
    }
    return result;
  }

  public ValidationSummary isCorrect(OrderContainer order) {
    ValidationSummary summary = new ValidationSummary();
    List<ValidationResult> results = new ArrayList<ValidationResult>();
    ValidationResult result;

    // 1受注の出荷ヘッダ数チェック
    result = validMaxShippingHeader(order);
    if (result.isError()) {
      results.add(result);
    }

    // 1受注の最大購入金額チェック ＋ 購入数量チェック
    BigDecimal currency = BigDecimal.ZERO;
    Long amount = 0L;
    for (ShippingContainer shipping : order.getShippings()) {
      ShippingHeader header = shipping.getShippingHeader();
      currency = currency.add(header.getShippingCharge());
      for (ShippingDetail detail : shipping.getShippingDetails()) {
        currency = currency.add(BigDecimalUtil.multiply(detail.getGiftPrice().add(detail.getRetailPrice()),
            detail.getPurchasingAmount()));
        amount += detail.getPurchasingAmount();
      }

      // 1出荷の出荷明細数チェック
      result = validMaxShippingDetail(shipping);
      if (result.isError()) {
        results.add(result);
      }

      // 1出荷の送料チェック
      result = validMaxShippingCharge(shipping);
      if (result.isError()) {
        results.add(result);
      }
    }
    // 1受注の最大購入金額チェック
    //modify by V10-CH 170 start
    //result = validTotalCurrency(currency + NumUtil.coalesce(order.getOrderHeader().getPaymentCommission(), 0L));
    //soukai update 2012/01/18 ob start
    //result = validTotalCurrency(currency.add(NumUtil.coalesce(order.getOrderHeader().getPaymentCommission(), BigDecimal.ZERO)));
    currency = currency.subtract(NumUtil.coalesce(order.getOrderHeader().getDiscountPrice(), BigDecimal.ZERO));
    result = validTotalCurrency(currency.add(NumUtil.coalesce(order.getOrderHeader().getPaymentCommission(), BigDecimal.ZERO)));
    //soukai udpate 2012/01/18 ob end
    //modify by V10-CH 170 end
    if (result.isError()) {
      results.add(result);
    }
    // 1受注の最大購入数量チェック
    result = validTotalAmount(amount);
    if (result.isError()) {
      results.add(result);
    }

    summary.setErrors(results);
    return summary;
  }

  /**
   * maxShippingChargeを取得します。
   * 
   * @return maxShippingCharge
   */
  public BigDecimal getMaxShippingCharge() {
    return maxShippingCharge;
  }

  /**
   * maxShippingChargeを設定します。
   * 
   * @param maxShippingCharge
   *          maxShippingCharge
   */
  public void setMaxShippingCharge(BigDecimal maxShippingCharge) {
    this.maxShippingCharge = maxShippingCharge;
  }

  /**
   * @return the minSetNum
   */
  public Long getMinSetNum() {
    return minSetNum;
  }

  /**
   * @param minSetNum the minSetNum to set
   */
  public void setMinSetNum(Long minSetNum) {
    this.minSetNum = minSetNum;
  }

}
