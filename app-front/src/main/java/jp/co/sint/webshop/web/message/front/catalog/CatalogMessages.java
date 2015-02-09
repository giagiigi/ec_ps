package jp.co.sint.webshop.web.message.front.catalog;

import java.util.ListResourceBundle;

/**
 * メッセージリソースのクラスです。
 * 
 * @author System Integrator Corp.
 */
public class CatalogMessages extends ListResourceBundle {

  @Override
  protected Object[][] getContents() {
    Object[][] obj = {
        // レビュー複数登録エラーメッセージ
        {
            "duplicated_review_error", "レビューの登録は、1商品につき1回までです。"
        },
        // 商品が未存在メッセージ(商品一覧)
        {
            "no_commodity_list_error", "お探しの商品が見つかりませんでした。"
        },
        // 商品が未存在メッセージ(商品詳細)
        {
            "no_commodity_detail_error", "お探しの商品はお取り扱いしておりません。"
        },
        // 規格組み合わせ非存在メッセージ
        {
            "no_standard_combination", "その組み合わせの商品はありません。"
        },
        // 入荷お知らせ登録エラーメッセージ
        {
            "arrival_goods_error", "入荷お知らせ登録ができない商品、または購入可能な商品のため、入荷お知らせ登録することができません。"
        }
    };
    return obj;
  }
}
