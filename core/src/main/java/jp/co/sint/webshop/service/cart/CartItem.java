
package jp.co.sint.webshop.service.cart;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * カート内に登録する１商品（ＳＫＵ単位）の情報
 * 
 * @author System Integrator Corp.
 */
public interface CartItem extends Serializable {

  /**
   * ショップコードの取得
   * 
   * @return ショップコード
   */
  String getShopCode();

  /**
   * ショップ名の取得
   * 
   * @return ショップ名
   */
  String getShopName();

  /**
   * 商品コードの取得
   * 
   * @return 商品コード
   */
  String getCommodityCode();

  /**
   * 商品名の取得
   * 
   * @return 商品名
   */
  String getCommodityName();

  /**
   * SKUコードの取得
   * 
   * @return SKUコード
   */
  String getSkuCode();

  /**
   * 商品税区分の取得
   * 
   * @return 商品税区分
   */
  Long getCommodityTaxType();

  /**
   * 商品税額の取得
   * 
   * @return 商品税額
   */
  BigDecimal getCommodityTaxCharge();

  /**
   * 商品単価の取得
   * 
   * @return 商品単価
   */
  BigDecimal getUnitPrice();

  /**
   * 商品販売価格（税込）の取得
   * 
   * @return 商品販売価格（税込）
   */
  BigDecimal getRetailPrice();

  /**
   * 規格名１の取得
   * 
   * @return 規格名１
   */
  String getStandardDetail1Name();

  /**
   * 規格名２の取得
   * 
   * @return 規格名２
   */
  String getStandardDetail2Name();

  /**
   * ギフトコードの取得
   * 
   * @return ギフトコード
   */
  String getGiftCode();

  /**
   * ギフト名の取得
   * 
   * @return ギフト名
   */
  String getGiftName();

  /**
   * ギフト価格の取得
   * 
   * @return ギフト価格（税込）
   */
  BigDecimal getGiftPrice();

  /**
   * ギフト税区分の取得
   * 
   * @return ギフト税区分
   */
  Long getGiftTaxType();

  /**
   * ギフト税額の取得
   * 
   * @return ギフト税額
   */
  BigDecimal getGiftTaxCharge();

  /**
   * キャンペーンコードの取得
   * 
   * @return キャンペーンコード
   */
  String getCampaignCode();

  /**
   * キャンペーン名の取得
   * 
   * @return キャンペーン名
   */
  String getCampaignName();

  /**
   * 数量の取得
   * 
   * @return 数量
   */
  int getQuantity();

  /**
   * 予約注文かどうかの取得
   * 
   * @return 予約注文(通常注文ではない)かどうか
   */
  boolean isReserve();

  /**
   * 通常注文かどうかの取得
   * 
   * @return 通常の注文(予約注文ではない)かどうか
   */
  boolean isOrder();

  /**
   * 金額 （商品価格＋ギフト価格）×数量 の取得
   * 
   * @return 金額 （商品価格＋ギフト価格）×数量
   */
  BigDecimal getSubTotal();

  /**
   * 数量を設定する
   * 
   * @param quantity
   */
  void setQuantity(int quantity);

  /**
   * ギフトを設定する
   * 
   * @param giftCode
   */
  void setGift(String shopCode, String giftCode);

  CartCommodityInfo getCommodityInfo();

  // 20111230 shen add start
  BigDecimal getWeight();
  // 20111230 shen add end
  //2012/11/23 促销对应 新建订单_商品选择  ob add start
  Long getCommodityType();
  //2012/11/23 促销对应 新建订单_商品选择  ob add end
  
  Long getCombinationAmount();
  
  String getOriginalCommodityCode();
  
  String getIsDiscountCommodity();
  
}
