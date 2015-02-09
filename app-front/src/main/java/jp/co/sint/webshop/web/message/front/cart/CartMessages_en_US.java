package jp.co.sint.webshop.web.message.front.cart;

import java.util.ListResourceBundle;

/**
 * メッセージリソースのクラスです。

 * 
 * @author System Integrator Corp.
 */
public class CartMessages_en_US extends ListResourceBundle {

  @Override
  protected Object[][] getContents() {
    Object[][] obj = {
        // お気に入り登録完了

        {
            "complete_add_favorite", "Added to favorite."
        },
        // お気に入り二重登録エラー
        {
            "duplicated_favorite", "The item is already registered to your favorites."
        },
        // カートに商品が入っていないエラー
        {
            "no_cart_item", "There is no item in your cart now."
        },
        // SKU未存在エラー
        {
            "sku_not_found", "There is no No.{0} item."
        },
        // 商品追加失敗エラー

        {
            "add_sku_failed", "Couldn't added No.{0} item."
        },
        // カートに存在しない商品が存在する
        {
            "false_cart_item", "There is no such item exist."
        },
        // SKU未指定エラー
        {
            "no_input_sku", "Please set the specific items that you want to obtain the name."
        },
        // カートに入れる商品、数量未指定エラー

        {
            "no_item_added", "Please set the specific items and quantity."
        }
    };
    return obj;
  }
}
