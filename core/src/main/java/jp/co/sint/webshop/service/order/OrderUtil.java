package jp.co.sint.webshop.service.order;

import java.util.Set;

import jp.co.sint.webshop.data.dto.OrderHeader;
import jp.co.sint.webshop.data.dto.ShippingDetail;
import jp.co.sint.webshop.utility.Sku;
import jp.co.sint.webshop.validation.ValidatorUtil;

public final class OrderUtil {

  private OrderUtil() {
  }

  public static boolean isModifiedPaymentMethod(OrderHeader before, OrderHeader after) {
    return !ValidatorUtil.areEqualOrNull(before.getPaymentMethodNo(), after.getPaymentMethodNo());
  }

  public static boolean isModifiedUsedPoints(OrderHeader before, OrderHeader after) {
    return !ValidatorUtil.areEqualOrNull(before.getUsedPoint(), after.getUsedPoint());
  }

  public static boolean isModifiedOrder(OrderContainer orig, OrderContainer mod) {
    // 支払方法が変更されているか
    boolean b1 = isModifiedPaymentMethod(orig.getOrderHeader(), mod.getOrderHeader());
    // 使用ポイント数が変更されているか
    boolean b2 = isModifiedUsedPoints(orig.getOrderHeader(), mod.getOrderHeader());
    // 出荷情報が更新されているかどうか
    boolean b3 = isModifiedShippingList(orig, mod);

    return (b1 || b2 || b3);
  }

  public static boolean isModifiedShippingList(OrderContainer orig, OrderContainer mod) {
    Set<String> shippings1 = orig.getShippingNoSet();
    Set<String> shippings2 = mod.getShippingNoSet();
    boolean b3 = false;
    boolean shippingsAreModified = !shippings1.equals(shippings2);
    if (shippingsAreModified) {
      return true;
    }

    for (ShippingContainer modSc : mod.getShippings()) {
      ShippingContainer orgSc = orig.getShipping(modSc.getShippingNo());
      b3 |= isModifiedShipping(orgSc, modSc);
      if (b3) {
        return true;
      }
    }
    return false;
  }

  public static boolean isModifiedShipping(ShippingContainer org, ShippingContainer mod) {
    // 配送先住所が変わっているか
    boolean b1 = isModifiedAddress(org, mod);
    // 出荷明細のSKUのセットが変わっているか
    boolean b2 = isModifiedSkuSet(org, mod);
    // 出荷明細の単価その他が変わっているか
    boolean b3 = isModifiedDetails(org, mod);
    return b1 || b2 || b3;
  }

  public static boolean isModifiedAddress(ShippingContainer org, ShippingContainer mod) {
    String pc1 = org.getShippingHeader().getPrefectureCode();
    String pc2 = mod.getShippingHeader().getPrefectureCode();
    return !pc1.equals(pc2);
  }

  public static boolean isModifiedSkuSet(ShippingContainer org, ShippingContainer mod) {
    Set<Sku> skus1 = org.getSkuSet();
    Set<Sku> skus2 = mod.getSkuSet();
    return !skus1.equals(skus2);
  }

  public static boolean isModifiedDetails(ShippingContainer org, ShippingContainer mod) {
    boolean result = false;
    for (Sku sku : mod.getSkuSet()) {
      ShippingDetail sd1 = org.getShippingDetail(sku);
      ShippingDetail sd2 = mod.getShippingDetail(sku);
      if (sd1 != null && sd2 != null) {
        result |= isModified(sd1, sd2);
      }
    }
    return result;
  }

  // 出荷明細が変わっているかどうかを調べる
  public static boolean isModified(ShippingDetail org, ShippingDetail mod) {
    boolean result = false;
    result |= !ValidatorUtil.areEqualOrNull(org.getRetailPrice(), mod.getRetailPrice());
    result |= !ValidatorUtil.areEqualOrNull(org.getPurchasingAmount(), mod.getPurchasingAmount());
    result |= !ValidatorUtil.areEqualOrNull(org.getGiftPrice(), mod.getGiftPrice());
    return result;
  }

}
