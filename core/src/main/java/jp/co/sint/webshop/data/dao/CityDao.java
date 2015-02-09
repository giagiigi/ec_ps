//
//Copyright(C) 2007-2008 System Integrator Corp.
//All rights reserved.
//
package jp.co.sint.webshop.data.dao;

import java.util.List;

import jp.co.sint.webshop.data.GenericDao;
import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.dto.City;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 「地域(REGION)」テーブルへのデータアクセスを担当するDAO(Data Access Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public interface CityDao extends GenericDao<City, Long> {

  /**
   * 指定されたorm_rowidを持つ地域のインスタンスを取得します。
   *
   * @param id 対象のorm_rowid
   * @return idに対応するRegionのインスタンス
   */
  City loadByRowid(Long id);

  /**
   * 主キー列の値を指定して地域のインスタンスを取得します。
   *
   * @param countryCode 国コード
   * @param regionCode 地域コード
   * @return 主キー列の値に対応するRegionのインスタンス
   */
  City load(String countryCode, String regionCode,String cityCode);

  /**
   * 主キー列の値を指定して地域が既に存在するかどうかを返します。
   *
   * @param countryCode 国コード
   * @param regionCode 地域コード
   * @return 主キー列の値に対応するRegionの行が存在すればtrue
   */
  boolean exists(String countryCode, String regionCode,String cityCode);

  /**
   * 新規Regionをデータベースに追加します。
   *
   * @param obj 追加対象のRegion
   * @param loginInfo ログイン情報
   * @return 追加が成功した場合のorm_rowid値
   */
  Long insert(City obj, LoginInfo loginInfo);

  /**
   * 地域を更新します。
   *
   * @param obj 更新対象のRegion
   * @param loginInfo ログイン情報
   */
  void update(City obj, LoginInfo loginInfo);

  /**
   * 地域を削除します。
   *
   * @param obj 削除対象のRegion
   * @param loginInfo ログイン情報
   */
  void delete(City obj, LoginInfo loginInfo);

  /**
   * 主キー列の値を指定して地域を削除します。
   *
   * @param countryCode 国コード
   * @param regionCode 地域コード
   */
  void delete(String countryCode, String regionCode,String cityCode);

  /**
   * Queryオブジェクトを指定して地域のリストを取得します。
   *
   * @param query Queryオブジェクト
   * @return 検索結果に相当するRegionのリスト
   */
  List<City> findByQuery(Query query);

  /**
   * SQLを指定して地域のリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するRegionのリスト
   */
  List<City> findByQuery(String sqlString, Object... params);

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブルの全データ分のRegionのリスト
   */
  List<City> loadAll();
//2013/04/11 配送公司设定对应 ob add start
  /**
   * 按地域取得城市信息
   * @param regionCode 地域编号
   * @return 城市信息
   */
  List<City> load(String regionCode);
//2013/04/11 配送公司设定对应 ob add end

}
