//
//Copyright(C) 2007-2008 System Integrator Corp.
//All rights reserved.
//
package jp.co.sint.webshop.data.dao;

import java.util.List;

import jp.co.sint.webshop.data.GenericDao;
import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.dto.Region;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 「地域(REGION)」テーブルへのデータアクセスを担当するDAO(Data Access Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public interface RegionDao extends GenericDao<Region, Long> {

  /**
   * 指定されたorm_rowidを持つ地域のインスタンスを取得します。
   *
   * @param id 対象のorm_rowid
   * @return idに対応するRegionのインスタンス
   */
  Region loadByRowid(Long id);

  /**
   * 主キー列の値を指定して地域のインスタンスを取得します。
   *
   * @param countryCode 国コード
   * @param regionCode 地域コード
   * @return 主キー列の値に対応するRegionのインスタンス
   */
  Region load(String countryCode, String regionCode);

  /**
   * 主キー列の値を指定して地域が既に存在するかどうかを返します。
   *
   * @param countryCode 国コード
   * @param regionCode 地域コード
   * @return 主キー列の値に対応するRegionの行が存在すればtrue
   */
  boolean exists(String countryCode, String regionCode);

  /**
   * 新規Regionをデータベースに追加します。
   *
   * @param obj 追加対象のRegion
   * @param loginInfo ログイン情報
   * @return 追加が成功した場合のorm_rowid値
   */
  Long insert(Region obj, LoginInfo loginInfo);

  /**
   * 地域を更新します。
   *
   * @param obj 更新対象のRegion
   * @param loginInfo ログイン情報
   */
  void update(Region obj, LoginInfo loginInfo);

  /**
   * 地域を削除します。
   *
   * @param obj 削除対象のRegion
   * @param loginInfo ログイン情報
   */
  void delete(Region obj, LoginInfo loginInfo);

  /**
   * 主キー列の値を指定して地域を削除します。
   *
   * @param countryCode 国コード
   * @param regionCode 地域コード
   */
  void delete(String countryCode, String regionCode);

  /**
   * Queryオブジェクトを指定して地域のリストを取得します。
   *
   * @param query Queryオブジェクト
   * @return 検索結果に相当するRegionのリスト
   */
  List<Region> findByQuery(Query query);

  /**
   * SQLを指定して地域のリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するRegionのリスト
   */
  List<Region> findByQuery(String sqlString, Object... params);

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブルの全データ分のRegionのリスト
   */
  List<Region> loadAll();

}
