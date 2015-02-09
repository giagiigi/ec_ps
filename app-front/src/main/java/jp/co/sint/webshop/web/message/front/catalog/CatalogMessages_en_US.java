package jp.co.sint.webshop.web.message.front.catalog;

import java.util.ListResourceBundle;

/**
 * メッセージリソースのクラスです。

 * 
 * @author System Integrator Corp.
 */
public class CatalogMessages_en_US extends ListResourceBundle {

  @Override
  protected Object[][] getContents() {
    Object[][] obj = {
        // レビュー複数登録エラーメッセージ
        {
            "duplicated_review_error", "You have registered the review ."
        },
        // 商品が未存在メッセージ(商品一覧)
        {
            "no_commodity_list_error", "Couldn't find the item you looking for."
        },
        // 商品が未存在メッセージ(商品詳細)
        {
            "no_commodity_detail_error", "We don't carry the item you looking for."
        },
        // 規格組み合わせ非存在メッセージ

        {
            "no_standard_combination", "There is no item with the combination."
        },
        // 入荷お知らせ登録エラーメッセージ
        {
            "arrival_goods_error", "Because the item is purchasable or unable to register,"
            + " You can not register the arrival goods info."
        }
    };
    return obj;
  }
}
