package jp.co.sint.webshop.service.cart;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import jp.co.sint.webshop.data.dto.Customer;
import jp.co.sint.webshop.data.dto.CustomerAddress;
import jp.co.sint.webshop.data.dto.CustomerCoupon;
import jp.co.sint.webshop.data.dto.DeliveryType;
import jp.co.sint.webshop.service.OptionalCommodity;

/**
 * 決済情報作成クラス<BR>
 * 決済情報単位で１インスタンスとする
 * 
 * @author System Integrator Corp.
 */
public interface Cashier extends Serializable {

  /**
   * 決済ショップのショップコードを取得する
   * 
   * @return 決済ショップのショップコード
   */
  String getPaymentShopCode();

  /**
   * 顧客情報を取得する
   * 
   * @return 顧客情報
   */
  Customer getCustomer();

  /**
   * 本人アドレス情報を取得する
   * 
   * @return 本人アドレス情報
   */
  CustomerAddress getSelfAddress();

  /**
   * 使用可能な（カートに入れられた）商品の一覧を取得します
   * 
   * @return 商品一覧
   */
  List<CartCommodityInfo> getUsableCommodity();

  // 2012/11/21 促销对应 ob add start
  /**
   * 已领取多关联促销活动赠品一覧取得
   * 
   * @return 已领取多关联促销活动赠品一覧
   */
  List<CartCommodityInfo> getOtherGiftList();

  // 2012/11/21 促销对应 ob add end

  /**
   * 連絡事項、注意事項（管理側のみ参照）を取得します。
   * 
   * @return 連絡事項、注意事項（管理側のみ参照）
   */
  String getMessage();

  /**
   * 連絡事項・注意事項（管理側のみ参照）を設定します。
   * 
   * @param messeage
   */
  void setMessage(String messeage);

  /**
   * 使用するポイントを取得します。
   * 
   * @return 使用ポイント
   */
  String getUsePoint();

  /**
   * 使用するポイントを設定します。
   * 
   * @param usePoint
   */
  void setUsePoint(String usePoint);

  /**
   * ポイントの使用可否を取得します。
   * 
   * @return ポイントの使用可否
   */
  boolean isUsablePoint();

  /**
   * ポイントの使用可非を設定します。
   */
  void setUsablePoint(boolean usable);

  /**
   * 支払方法情報を取得します。
   * 
   * @return 支払方法情報
   */
  CashierPayment getPayment();

  /**
   * 支払方法情報を設定します。
   * 
   * @param cashierPayment
   */
  void setPayment(CashierPayment cashierPayment);

  /**
   * 注意事項（管理側のみ参照）を取得します。
   * 
   * @return 注意事項（管理側のみ参照）
   */
  String getCaution();

  /**
   * 注意事項（管理側のみ参照）を設定します。
   * 
   * @param caution
   */
  void setCaution(String caution);

  void addCashierItem(CustomerAddress address, DeliveryType deliveryType, List<CartCommodityInfo> commodityInfoList);

  /**
   * CashierItemを追加する
   * 
   * @param address
   * @param deliveryType
   * @param commodityInfo
   */
  void addCashierItem(CustomerAddress address, DeliveryType deliveryType, CartCommodityInfo commodityInfo);

  void removeShipping(CashierShipping shipping);

  /**
   * deliveryCode、addressNoをキーとするCashierItemを一括削除する
   * 
   * @param shopCode
   * @param deliveryCode
   * @param addressNo
   */
  void removeShipping(String shopCode, Long deliveryCode, Long addressNo);

  /**
   * 出荷情報の一覧を削除します。
   * 
   * @param shippingList
   */
  void removeShippingList(List<CashierShipping> shippingList);

  /**
   * CashierItemを削除する
   * 
   * @param shopCode
   * @param deliveryCode
   * @param addressNo
   * @param skuCode
   */
  void removeCashierItem(String shopCode, Long deliveryCode, Long addressNo, String skuCode,String isDiscount);

  // 2012-11-29 促销对应 ob add start
  /**
   * 多重关联商品删除
   * 
   * @param multipleCampaignCode
   * @param skuCode
   */
  void removeOtherGift(String multipleCampaignCode, String skuCode);

  // 2012-11-29 促销对应 ob add end
  /**
   * CashierItemを更新する
   * 
   * @param cashierItem
   */
  void updateCashierShipping(CashierShipping cashierItem);

  /**
   * Cashier内のCasshierShippingを取得する
   * 
   * @param shopCode
   * @param deliveryCode
   * @param addressNo
   * @return 配送データ
   */
  CashierShipping getShipping(String shopCode, Long deliveryCode, Long addressNo);

  /**
   * Cashier内のCashierShippingのリストをすべて取得する
   * 
   * @return 全配送データ
   */
  List<CashierShipping> getShippingList();

  /**
   * Cashier内のCashierShippingのリストから、指定のAddressNoのデータのみ取得する
   * 
   * @return 指定したアドレス識別子から得られる配送データ
   */
  List<CashierShipping> getItemListByAddress(Long addressNo);

  /**
   * Cashier内のCashierShippingのリストから、指定のDeliveryCodeのデータのみ取得する。
   * 
   * @return 配送コードから得られる配送データ
   */
  List<CashierShipping> getItemListByDelivery(Long deliveryTypeCode);

  /**
   * Cashier内の商品の販売価格の合計金額を取得します。
   * 
   * @return 販売価格の合計金額
   */
  BigDecimal getTotalCommodityPrice();
  
  BigDecimal getNotOptionalCommodityPrice();

  /**
   * 根据公共优惠券商品，取得Cashier内符合优惠的商品价格
   * @return
   */
  BigDecimal getTotalCouponCommodityPrice(String CouponCommodity);
  
  /**
   * Cashier内の商品のギフト価格の合計金額を取得します。
   * 
   * @return 商品のギフト価格の合計金額
   */
  BigDecimal getTotalGiftPrice();

  /**
   * Cashier内の送料の合計金額を取得します。
   * 
   * @return 送料の合計金額
   */
  BigDecimal getTotalShippingCharge();

  BigDecimal getGrandTotalPrice();

  /**
   * cashier情報を全てクリアします
   */
  void clear();

  boolean isReserve();

  void recomputeShippingCharge();

  List<CustomerCoupon> getCustomerCouponId();

  void setCustomerCouponId(List<CustomerCoupon> customerCoupon);

  String getUseCoupon();

  void setUseCoupon(String useCoupon);

  boolean isUsableCoupon();

  void setUsableCoupon(boolean usable);

  // 20111221 shen add start
  CashierInvoice getInvoice();

  void setInvoice(CashierInvoice invoice);

  CashierDiscount getDiscount();

  void setDiscount(CashierDiscount discount);

  BigDecimal getDiscountPrice();

  BigDecimal getPaymentTotalPrice();

  BigDecimal getTotalDeliveryDateCommssion();

  // 20111221 shen add end

  BigDecimal getTotalWeight();
  
  BigDecimal getOptionalCheapPrice();
  
  void setOptionalCheapPrice(BigDecimal optionalCheapPrice);
  
  Map<String, OptionalCommodity> getOptionalCommodityMap();
  
  void setOptionalCommodityMap(Map<String, OptionalCommodity> optionalCommodityMap);
  
  List<String> getOpCommodityCodeList();
  
  void setOpCommodityCodeList(List<String> opCommodityCodeList);

  CashierDelivery getDelivery();
  
  void setGiftCardUseAmount(BigDecimal giftCardUseAmount);
  
  BigDecimal getGiftCardUseAmount();
  
  void setOuterCardUseAmount(BigDecimal giftOuterUseAmount);
  
  BigDecimal getOuterCardUseAmount();

  void setDelivery(CashierDelivery delivery);

  // 20121224 txw add start
  int getItemCount();

  void setItemCount(int itemCount);

  // 20121224 txw add end
}
