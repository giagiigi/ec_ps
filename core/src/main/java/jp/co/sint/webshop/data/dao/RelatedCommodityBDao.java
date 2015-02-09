//
//Copyright(C) 2007-2008 System Integrator Corp.
//All rights reserved.
//
// このファイルはEDMファイルから自動生成されます。
// 直接編集しないで下さい。
//
package jp.co.sint.webshop.data.dao;

import java.util.List;

import jp.co.sint.webshop.data.GenericDao;
import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.dto.RelatedCommodityB;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 「自動リコメンド(RELATED_COMMODITY_B)」テーブルへのデータアクセスを担当するDAO(Data Access Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public interface RelatedCommodityBDao extends GenericDao<RelatedCommodityB, Long> {

  /**
   * 指定されたorm_rowidを持つ自動リコメンドのインスタンスを取得します。
   *
   * @param id 対象のorm_rowid
   * @return idに対応するRelatedCommodityBのインスタンス
   */
  RelatedCommodityB loadByRowid(Long id);

  /**
   * 主キー列の値を指定して自動リコメンドのインスタンスを取得します。
   *
   * @param shopCode ショップコード
   * @param commodityCode 商品コード
   * @param linkShopCode リンクショップコード
   * @param linkCommodityCode リンク商品コード
   * @return 主キー列の値に対応するRelatedCommodityBのインスタンス
   */
  RelatedCommodityB load(String shopCode, String commodityCode, String linkShopCode, String linkCommodityCode);

  /**
   * 主キー列の値を指定して自動リコメンドが既に存在するかどうかを返します。
   *
   * @param shopCode ショップコード
   * @param commodityCode 商品コード
   * @param linkShopCode リンクショップコード
   * @param linkCommodityCode リンク商品コード
   * @return 主キー列の値に対応するRelatedCommodityBの行が存在すればtrue
   */
  boolean exists(String shopCode, String commodityCode, String linkShopCode, String linkCommodityCode);

  /**
   * 新規RelatedCommodityBをデータベースに追加します。
   *
   * @param obj 追加対象のRelatedCommodityB
   * @param loginInfo ログイン情報
   * @return 追加が成功した場合のorm_rowid値
   */
  Long insert(RelatedCommodityB obj, LoginInfo loginInfo);

  /**
   * 自動リコメンドを更新します。
   *
   * @param obj 更新対象のRelatedCommodityB
   * @param loginInfo ログイン情報
   */
  void update(RelatedCommodityB obj, LoginInfo loginInfo);

  /**
   * 自動リコメンドを削除します。
   *
   * @param obj 削除対象のRelatedCommodityB
   * @param loginInfo ログイン情報
   */
  void delete(RelatedCommodityB obj, LoginInfo loginInfo);

  /**
   * 主キー列の値を指定して自動リコメンドを削除します。
   *
   * @param shopCode ショップコード
   * @param commodityCode 商品コード
   * @param linkShopCode リンクショップコード
   * @param linkCommodityCode リンク商品コード
   */
  void delete(String shopCode, String commodityCode, String linkShopCode, String linkCommodityCode);

  /**
   * Queryオブジェクトを指定して自動リコメンドのリストを取得します。
   *
   * @param query Queryオブジェクト
   * @return 検索結果に相当するRelatedCommodityBのリスト
   */
  List<RelatedCommodityB> findByQuery(Query query);

  /**
   * SQLを指定して自動リコメンドのリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するRelatedCommodityBのリスト
   */
  List<RelatedCommodityB> findByQuery(String sqlString, Object... params);

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブルの全データ分のRelatedCommodityBのリスト
   */
  List<RelatedCommodityB> loadAll();

}
