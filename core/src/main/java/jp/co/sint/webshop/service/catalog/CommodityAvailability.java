package jp.co.sint.webshop.service.catalog;

public enum CommodityAvailability {

  /** 商品未存在 */
  NOT_EXIST_SKU,

  /** 販売期間外 */
  OUT_OF_PERIOD,

  /** 
   * 在庫切れ<BR>
   * 有効在庫数が0の場合
   */
  OUT_OF_STOCK,

  /** 
   * 在庫不足<BR>
   * 有効在庫数が0以上の時に、指定された数量が確保できない場合<BR>
   * 例. 有効在庫2で、3個購入しようとした
   */
  STOCK_SHORTAGE,

  /**
   * 予約上限数オーバー(注文毎含む)<BR>
   * 有効在庫数が0以上の時に、指定された数量が確保できない場合<BR>
   * 例. 有効在庫2で、3個予約しようとした
   */
  RESERVATION_LIMIT_OVER,
  
  /** 
   * 在庫切れ(予約)<BR>
   * 有効在庫数が0の場合
   */
  OUT_OF_RESERVATION_STOCK,

  /** 引き当て可能 */
  AVAILABLE;

}
