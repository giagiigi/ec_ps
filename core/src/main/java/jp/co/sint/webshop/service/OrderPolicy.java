package jp.co.sint.webshop.service;

import java.io.Serializable;
import java.math.BigDecimal;

import jp.co.sint.webshop.data.dto.ShippingDetail;
import jp.co.sint.webshop.data.dto.ShippingHeader;
import jp.co.sint.webshop.service.order.OrderContainer;
import jp.co.sint.webshop.service.order.ShippingContainer;
import jp.co.sint.webshop.utility.BigDecimalUtil;
import jp.co.sint.webshop.validation.ValidatorUtil;

/**
 * 単価、合計金額や注文数量の最大値など受注データ仕様の整合性をチェックするクラスです。
 * 
 * @author System Integrator Corp.
 */
public class OrderPolicy implements Serializable {

  private static final long serialVersionUID = 1L;

  private int maxShippingHeaderSize = 999;

  private int maxShippingDetailSize = 99;

  private BigDecimal maxLineUnitPrice = new BigDecimal("99999999.99");

  private long maxLineQuantity = 9999L;

  private BigDecimal maxTotalAmount = new BigDecimal("99999999.99");

  public boolean isCorrect(OrderContainer container) {

    boolean result = true;
    BigDecimal totalAmount = BigDecimal.ZERO;

    // 出荷ヘッダ数のチェック
    result &= ValidatorUtil.lessThanOrEquals(container.getShippings().size(), maxShippingHeaderSize);

    for (ShippingContainer sc : container.getShippings()) {
      ShippingHeader sh = sc.getShippingHeader();
      totalAmount = sh.getShippingCharge();

      // 1出荷あたりの出荷明細のチェック
      result &= ValidatorUtil.lessThanOrEquals(sc.getShippingDetails().size(), maxShippingDetailSize);

      for (ShippingDetail sd : sc.getShippingDetails()) {
        totalAmount = totalAmount.add(BigDecimalUtil.multiply(sd.getRetailPrice(), sd.getPurchasingAmount()));

        // 1明細あたりの単価チェック
        result &= ValidatorUtil.lessThanOrEquals(sd.getRetailPrice(), maxLineUnitPrice);

        // 1明細あたりの数量チェック
        result &= ValidatorUtil.lessThanOrEquals(sd.getPurchasingAmount(), maxLineQuantity);
      }
    }
    result &= ValidatorUtil.lessThanOrEquals(totalAmount, maxTotalAmount);
    return result;
  }
}
