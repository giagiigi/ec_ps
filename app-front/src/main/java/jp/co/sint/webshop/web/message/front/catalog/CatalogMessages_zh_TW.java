package jp.co.sint.webshop.web.message.front.catalog;

import java.util.ListResourceBundle;

/**
 * メッセージリソースのクラスです。

 * 
 * @author System Integrator Corp.
 */
public class CatalogMessages_zh_TW extends ListResourceBundle {

  @Override
  protected Object[][] getContents() {
    Object[][] obj = {
        // レビュー複数登録エラーメッセージ
        {
            "duplicated_review_error", "评论登录时，1个商品只能登录1次。"
        },
        // 商品が未存在メッセージ(商品一覧)
        {
            "no_commodity_list_error", "没找到您要找的商品。"
        },
        // 商品が未存在メッセージ(商品詳細)
        {
            "no_commodity_detail_error", "无法处理您找的商品。"
        },
        // 規格組み合わせ非存在メッセージ

        {
            "no_standard_combination", "没有那个组合的商品。"
        },
        // 入荷お知らせ登録エラーメッセージ
        {
            "arrival_goods_error", "因为是到货通知无法登录的商品，或者可以购买的商品，所以无法登录到货通知。。"
        }
    };
    return obj;
  }
}
