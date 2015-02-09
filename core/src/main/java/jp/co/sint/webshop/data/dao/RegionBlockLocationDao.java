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
import jp.co.sint.webshop.data.dto.RegionBlockLocation;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 「地域ブロック配置(REGION_BLOCK_LOCATION)」テーブルへのデータアクセスを担当するDAO(Data Access Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public interface RegionBlockLocationDao extends GenericDao<RegionBlockLocation, Long> {

  /**
   * 指定されたorm_rowidを持つ地域ブロック配置のインスタンスを取得します。
   *
   * @param id 対象のorm_rowid
   * @return idに対応するRegionBlockLocationのインスタンス
   */
  RegionBlockLocation loadByRowid(Long id);

  /**
   * 主キー列の値を指定して地域ブロック配置のインスタンスを取得します。
   *
   * @param shopCode ショップコード
   * @param regionBlockId 地域ブロックID
   * @param prefectureCode 都道府県コード
   * @return 主キー列の値に対応するRegionBlockLocationのインスタンス
   */
  RegionBlockLocation load(String shopCode, Long regionBlockId, String prefectureCode);

  /**
   * 主キー列の値を指定して地域ブロック配置が既に存在するかどうかを返します。
   *
   * @param shopCode ショップコード
   * @param regionBlockId 地域ブロックID
   * @param prefectureCode 都道府県コード
   * @return 主キー列の値に対応するRegionBlockLocationの行が存在すればtrue
   */
  boolean exists(String shopCode, Long regionBlockId, String prefectureCode);

  /**
   * 新規RegionBlockLocationをデータベースに追加します。
   *
   * @param obj 追加対象のRegionBlockLocation
   * @param loginInfo ログイン情報
   * @return 追加が成功した場合のorm_rowid値
   */
  Long insert(RegionBlockLocation obj, LoginInfo loginInfo);

  /**
   * 地域ブロック配置を更新します。
   *
   * @param obj 更新対象のRegionBlockLocation
   * @param loginInfo ログイン情報
   */
  void update(RegionBlockLocation obj, LoginInfo loginInfo);

  /**
   * 地域ブロック配置を削除します。
   *
   * @param obj 削除対象のRegionBlockLocation
   * @param loginInfo ログイン情報
   */
  void delete(RegionBlockLocation obj, LoginInfo loginInfo);

  /**
   * 主キー列の値を指定して地域ブロック配置を削除します。
   *
   * @param shopCode ショップコード
   * @param regionBlockId 地域ブロックID
   * @param prefectureCode 都道府県コード
   */
  void delete(String shopCode, Long regionBlockId, String prefectureCode);

  /**
   * Queryオブジェクトを指定して地域ブロック配置のリストを取得します。
   *
   * @param query Queryオブジェクト
   * @return 検索結果に相当するRegionBlockLocationのリスト
   */
  List<RegionBlockLocation> findByQuery(Query query);

  /**
   * SQLを指定して地域ブロック配置のリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するRegionBlockLocationのリスト
   */
  List<RegionBlockLocation> findByQuery(String sqlString, Object... params);

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブルの全データ分のRegionBlockLocationのリスト
   */
  List<RegionBlockLocation> loadAll();

}
