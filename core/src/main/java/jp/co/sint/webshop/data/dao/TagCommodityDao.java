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
import jp.co.sint.webshop.data.dto.TagCommodity;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 「タグ商品(TAG_COMMODITY)」テーブルへのデータアクセスを担当するDAO(Data Access Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public interface TagCommodityDao extends GenericDao<TagCommodity, Long> {

  /**
   * 指定されたorm_rowidを持つタグ商品のインスタンスを取得します。
   *
   * @param id 対象のorm_rowid
   * @return idに対応するTagCommodityのインスタンス
   */
  TagCommodity loadByRowid(Long id);

  /**
   * 主キー列の値を指定してタグ商品のインスタンスを取得します。
   *
   * @param shopCode ショップコード
   * @param tagCode タグコード
   * @param commodityCode 商品コード
   * @return 主キー列の値に対応するTagCommodityのインスタンス
   */
  TagCommodity load(String shopCode, String tagCode, String commodityCode);

  /**
   * 主キー列の値を指定してタグ商品が既に存在するかどうかを返します。
   *
   * @param shopCode ショップコード
   * @param tagCode タグコード
   * @param commodityCode 商品コード
   * @return 主キー列の値に対応するTagCommodityの行が存在すればtrue
   */
  boolean exists(String shopCode, String tagCode, String commodityCode);

  /**
   * 新規TagCommodityをデータベースに追加します。
   *
   * @param obj 追加対象のTagCommodity
   * @param loginInfo ログイン情報
   * @return 追加が成功した場合のorm_rowid値
   */
  Long insert(TagCommodity obj, LoginInfo loginInfo);

  /**
   * タグ商品を更新します。
   *
   * @param obj 更新対象のTagCommodity
   * @param loginInfo ログイン情報
   */
  void update(TagCommodity obj, LoginInfo loginInfo);

  /**
   * タグ商品を削除します。
   *
   * @param obj 削除対象のTagCommodity
   * @param loginInfo ログイン情報
   */
  void delete(TagCommodity obj, LoginInfo loginInfo);

  /**
   * 主キー列の値を指定してタグ商品を削除します。
   *
   * @param shopCode ショップコード
   * @param tagCode タグコード
   * @param commodityCode 商品コード
   */
  void delete(String shopCode, String tagCode, String commodityCode);

  /**
   * Queryオブジェクトを指定してタグ商品のリストを取得します。
   *
   * @param query Queryオブジェクト
   * @return 検索結果に相当するTagCommodityのリスト
   */
  List<TagCommodity> findByQuery(Query query);

  /**
   * SQLを指定してタグ商品のリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するTagCommodityのリスト
   */
  List<TagCommodity> findByQuery(String sqlString, Object... params);

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブルの全データ分のTagCommodityのリスト
   */
  List<TagCommodity> loadAll();

}
