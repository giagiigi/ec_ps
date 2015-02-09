package jp.co.sint.webshop.web.message.front.catalog;

import jp.co.sint.webshop.message.MessageType;

/**
 * メッセージリソースのクラスです。
 * 
 * @author System Integrator Corp.
 */
public enum CatalogErrorMessage implements MessageType {
  /** レビュー複数登録エラーメッセージ */
  DUPLICATED_REVIEW_ERROR("duplicated_review_error"),
  /** 商品が未存在メッセージ(商品一覧) */
  NO_COMMODITY_LIST_ERROR("no_commodity_list_error"),
  /** 商品が未存在メッセージ(商品詳細) */
  NO_COMMODITY_DETAIL_ERROR("no_commodity_detail_error"),
  /** 規格組み合わせ非存在メッセージ */
  NO_STANDARD_COMBINATION("no_standard_combination"),
  /** 入荷お知らせ登録エラーメッセージ */
  ARRIVAL_GOODS_ERROR("arrival_goods_error");

  private String messagePropertyId;

  private CatalogErrorMessage(String messagePropertyId) {
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
    return "jp.co.sint.webshop.web.message.front.catalog.CatalogMessages";
  }

}
