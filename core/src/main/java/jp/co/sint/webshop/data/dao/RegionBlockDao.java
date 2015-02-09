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
import jp.co.sint.webshop.data.dto.RegionBlock;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 「地域ブロック(REGION_BLOCK)」テーブルへのデータアクセスを担当するDAO(Data Access Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public interface RegionBlockDao extends GenericDao<RegionBlock, Long> {

  /**
   * 指定されたorm_rowidを持つ地域ブロックのインスタンスを取得します。
   *
   * @param id 対象のorm_rowid
   * @return idに対応するRegionBlockのインスタンス
   */
  RegionBlock loadByRowid(Long id);

  /**
   * 主キー列の値を指定して地域ブロックのインスタンスを取得します。
   *
   * @param shopCode ショップコード
   * @param regionBlockId 地域ブロックID
   * @return 主キー列の値に対応するRegionBlockのインスタンス
   */
  RegionBlock load(String shopCode, Long regionBlockId);

  /**
   * 主キー列の値を指定して地域ブロックが既に存在するかどうかを返します。
   *
   * @param shopCode ショップコード
   * @param regionBlockId 地域ブロックID
   * @return 主キー列の値に対応するRegionBlockの行が存在すればtrue
   */
  boolean exists(String shopCode, Long regionBlockId);

  /**
   * 新規RegionBlockをデータベースに追加します。
   *
   * @param obj 追加対象のRegionBlock
   * @param loginInfo ログイン情報
   * @return 追加が成功した場合のorm_rowid値
   */
  Long insert(RegionBlock obj, LoginInfo loginInfo);

  /**
   * 地域ブロックを更新します。
   *
   * @param obj 更新対象のRegionBlock
   * @param loginInfo ログイン情報
   */
  void update(RegionBlock obj, LoginInfo loginInfo);

  /**
   * 地域ブロックを削除します。
   *
   * @param obj 削除対象のRegionBlock
   * @param loginInfo ログイン情報
   */
  void delete(RegionBlock obj, LoginInfo loginInfo);

  /**
   * 主キー列の値を指定して地域ブロックを削除します。
   *
   * @param shopCode ショップコード
   * @param regionBlockId 地域ブロックID
   */
  void delete(String shopCode, Long regionBlockId);

  /**
   * Queryオブジェクトを指定して地域ブロックのリストを取得します。
   *
   * @param query Queryオブジェクト
   * @return 検索結果に相当するRegionBlockのリスト
   */
  List<RegionBlock> findByQuery(Query query);

  /**
   * SQLを指定して地域ブロックのリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するRegionBlockのリスト
   */
  List<RegionBlock> findByQuery(String sqlString, Object... params);

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブルの全データ分のRegionBlockのリスト
   */
  List<RegionBlock> loadAll();

}
