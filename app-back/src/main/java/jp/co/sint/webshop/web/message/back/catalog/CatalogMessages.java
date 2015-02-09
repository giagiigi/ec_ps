package jp.co.sint.webshop.web.message.back.catalog;

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
        {
            "code_failed", "{0}コードが正しくありません。"
        }, {
            "root_category_delete_error", "ルートカテゴリは削除できません。"
        }, {
            "root_category_parent_change_error", "ルートカテゴリの親カテゴリは変更できません。"
        }, {
            "change_category_own_child_error", "自身の子カテゴリへの移動はできません。"
        }, {
            "change_category_own_error", "親カテゴリを自分自身に変更することはできません。"
        }, {
            "delete_commodity_error", "商品コード:{0}の商品は売上確定していない出荷データに含まれるため、削除できません。"
        }, {
            "delete_sku_error", "SKUコード:{0}は売上確定していない出荷データに含まれるため、削除できません。"
        }, {
          // 10.1.7 10327 追加 ここから
          "change_standard_count_error", "売上確定していないSKUが出荷データに含まれるため、規格数を変更できません。"
        }, {
          // 10.1.7 10327 追加 ここまで
            "arrival_goods_delete_error", "SKUコード:{0}の入荷お知らせを削除できませんでした。"
        }, {
            "init_data_error", "必要なデータが登録されていません。リセットボタンをクリックしてデータを初期化してください。"
        }, {
            "set_period_error", "{0}を設定する場合は、{1}を設定する必要があります。"
        }, {
            "date_period_error", "{0}は{1}より前に設定する必要があります。"
        }, {
            "price_revision_date_error", "価格改定日時は販売期間内に設定してください。"
        }, {
            "stock_status_set_error", "在庫管理区分が「在庫管理する(状況表示)」に設定されている場合は、在庫状況表示を選択してください。"
        }, {
            "fault_standard_detail_name_set_error", "規格詳細名称が不正です。"
        }, {
            "fault_standard_name_set_error", "この商品の規格に規格名称「{0}」が設定されています。商品SKU画面で規格詳細名称を設定してください。"
        }, {
            "standard_name2_set_error", "規格名称を1つのみ設定する場合、{0}2名称は設定できません。"
        }, {
            "standard_names_set_error", "規格名称を使用しない場合、{0}1名称、{0}2名称は設定できません。"
        }, {
            "register_sku_error", "SKU商品を新規登録する場合は、規格名称を登録してください。"
        }, {
            "stock_amount_overflow_error", "{0}の合計は{1}から{2}の範囲内にしてください。"
        }, {
            "stock_absolute_overflow_error", "この商品は引当または予約されているため、入出庫数は{0}以上を入力してください。"
        }, {
            "stock_deliver_overflow_error", "この商品は引当または予約されているため、入出庫数は{0}以下を入力してください。"
        }, {
            "stock_no_changeable_error", "有効在庫がないため、在庫数を減らせません。"
        }, {
            "no_available_delivery_type_error", "利用可能な配送種別が登録されていません。配送種別を登録してください。"
        }, {
            "stock_quantity_error", "入出庫数は1以上を入力してください。"
        }, {
            "commodity_layout_top_parts_error", "{0}は表示エリアの一番上に配置してください。"
        }, {
            "not_represent_sku_error", "代表SKUコードには、登録済みのSKUコードを入力してください。"
        }, {
            "update_category_path_error", "カテゴリ登録・更新処理に失敗しました。カテゴリツリーに不整合があります。"
        }, {
            "category_max_depth_over_error", "カテゴリ階層は{0}階層までとしてください。"
        }, {
            "standard_name_duplicated_error", "規格名称に重複があります。"
        }, {
        // 10.1.6 10258 追加 ここから
            "overlapped_values", "{0}と{1}には異なる値を入力してください。"
        }, {
            "fault_standard_count_error", "規格数が不正です。"
        }, {
        // 10.1.6 10258 追加 ここまで
            "stock_io_memo_required_error", "入出庫事由は必ず入力してください。"
        }
    };
    return obj;
  }

}
