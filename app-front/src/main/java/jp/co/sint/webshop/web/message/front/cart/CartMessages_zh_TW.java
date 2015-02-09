package jp.co.sint.webshop.web.message.front.cart;

import java.util.ListResourceBundle;

/**
 * メッセージリソースのクラスです。

 * 
 * @author System Integrator Corp.
 */
public class CartMessages_zh_TW extends ListResourceBundle {

  @Override
  protected Object[][] getContents() {
    Object[][] obj = {
        // お気に入り登録完了

        {
            "complete_add_favorite", "加入到收藏夹。"
        },
        // お気に入り二重登録エラー
        {
            "duplicated_favorite", "在您的收藏夹中已经存在了。"
        },
        // カートに商品が入っていないエラー
        {
            "no_cart_item", "购物车内没有商品。"
        },
        // SKU未存在エラー
        {
            "sku_not_found", "No.{0}的商品不存在。"
        },
        // 商品追加失敗エラー
        {
            "add_sku_failed", "No.{0}的商品不能追加。"
        },
        // カートに存在しない商品が存在する
        {
            "false_cart_item", "这个商品购物车里不存在。"
        },
        // SKU未指定エラー
        {
            "no_input_sku", "请指定取得商品名的商品。"
        },
        // カートに入れる商品、数量未指定エラー
        {
            "no_item_added", "请将指定的商品和数量放入购物车。"
        }
    };
    return obj;
  }
}
