package jp.co.sint.webshop.web.message.front.cart;

import jp.co.sint.webshop.message.MessageType;

/**
 * メッセージのenumです。
 * 
 * @author System Integrator Corp.
 */
public enum CartDisplayMessage implements MessageType {

  /** カートに商品が入っていないエラーメッセージ */
  NO_CART_ITEM("no_cart_item"),
  /** お気に入り登録完了 */
  COMPLETE_ADD_FAVORITE("complete_add_favorite"),
  /** お気に入り二重登録エラー */
  DUPLICATED_FAVORITE("duplicated_favorite"),
  /** 商品未存在エラー */
  SKU_NOT_FOUND("sku_not_found"),
  /** 商品追加失敗エラー */
  ADD_SKU_FAILED("add_sku_failed"),
  /** カートに存在しない商品が存在する */
  FALSE_CART_ITEM("false_cart_item"),
  /** SKU未指定エラー */
  NO_INPUT_SKU("no_input_sku"),
  /** カートに入れる商品・数量未指定エラー */
  NO_ITEM_ADDED("no_item_added");

  private String messagePropertyId;

  private CartDisplayMessage(String messagePropertyId) {
    this.messagePropertyId = messagePropertyId;
  }

  /**
   * メッセージプロパティIDを取得します。
   * 
   * @return メッセージプロパティID
   */
  public String getMessagePropertyId() {
    return this.messagePropertyId;
  }

  /**
   * メッセージリソースを取得します。
   * 
   * @return メッセージリソース
   */
  public String getMessageResource() {
    return "jp.co.sint.webshop.web.message.front.cart.CartMessages";
  }

}
