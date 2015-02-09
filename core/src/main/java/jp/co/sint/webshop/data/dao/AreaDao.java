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
import jp.co.sint.webshop.data.dto.Area;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 「区县(AREA)」テーブルへのデータアクセスを担当するDAO(Data Access Object)です。
 *
 * @author Kousen.
 *
 */
public interface AreaDao extends GenericDao<Area, Long> {

  /**
   * 指定されたorm_rowidを持つ区县のインスタンスを取得します。
   *
   * @param id 対象のorm_rowid
   * @return idに対応するAreaのインスタンス
   */
  Area loadByRowid(Long id);

  /**
   * 主キー列の値を指定して区县のインスタンスを取得します。
   *
   * @param areaCode 区县编号
   * @return 主キー列の値に対応するAreaのインスタンス
   */
  Area load(String areaCode);

  /**
   * 主キー列の値を指定して区县が既に存在するかどうかを返します。
   *
   * @param areaCode 区县编号
   * @return 主キー列の値に対応するAreaの行が存在すればtrue
   */
  boolean exists(String areaCode);

  /**
   * 新規Areaをデータベースに追加します。
   *
   * @param obj 追加対象のArea
   * @param loginInfo ログイン情報
   * @return 追加が成功した場合のorm_rowid値
   */
  Long insert(Area obj, LoginInfo loginInfo);

  /**
   * 区县を更新します。
   *
   * @param obj 更新対象のArea
   * @param loginInfo ログイン情報
   */
  void update(Area obj, LoginInfo loginInfo);

  /**
   * 区县を削除します。
   *
   * @param obj 削除対象のArea
   * @param loginInfo ログイン情報
   */
  void delete(Area obj, LoginInfo loginInfo);

  /**
   * 主キー列の値を指定して区县を削除します。
   *
   * @param areaCode 区县编号
   */
  void delete(String areaCode);

  /**
   * Queryオブジェクトを指定して区县のリストを取得します。
   *
   * @param query Queryオブジェクト
   * @return 検索結果に相当するAreaのリスト
   */
  List<Area> findByQuery(Query query);

  /**
   * SQLを指定して区县のリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するAreaのリスト
   */
  List<Area> findByQuery(String sqlString, Object... params);

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブルの全データ分のAreaのリスト
   */
  List<Area> loadAll();
  
  /**
   * 按指定条件，取得区县信息
   *
   * @param prefectureCode 地域编号
   * @param cityCode 城市编号
   * @return 区县信息
   */
  List<Area> load(String prefectureCode, String cityCode);

}
