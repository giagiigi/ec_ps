package jp.co.sint.webshop.web.message.front.cart;

import java.util.ListResourceBundle;

/**
 * メッセージリソースのクラスです。
 * 
 * @author System Integrator Corp.
 */
public class CartMessages extends ListResourceBundle {

  @Override
  protected Object[][] getContents() {
    Object[][] obj = {
        // お気に入り登録完了
        {
            "complete_add_favorite", "お気に入りに登録しました。"
        },
        // お気に入り二重登録エラー
        {
            "duplicated_favorite", "すでにお気に入りに登録されている商品です。"
        },
        // カートに商品が入っていないエラー
        {
            "no_cart_item", "現在、お客様のカートに品物は入っておりません。"
        },
        // SKU未存在エラー
        {
            "sku_not_found", "No.{0}の商品は存在しません。"
        },
        // 商品追加失敗エラー
        {
            "add_sku_failed", "No.{0}の商品を追加できませんでした。"
        },
        // カートに存在しない商品が存在する
        {
            "false_cart_item", "該当の商品はカートに存在しません。"
        },
        // SKU未指定エラー
        {
            "no_input_sku", "商品名を取得する商品を指定してください。"
        },
        // カートに入れる商品、数量未指定エラー
        {
            "no_item_added", "カートに入れる商品と数量を指定してください。"
        }
    };
    return obj;
  }
}
