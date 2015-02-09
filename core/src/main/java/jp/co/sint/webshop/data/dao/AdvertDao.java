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
import jp.co.sint.webshop.data.dto.Advert;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 「広告(ADVERT)」テーブルへのデータアクセスを担当するDAO(Data Access Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public interface AdvertDao extends GenericDao<Advert, Long> {

  /**
   * 指定されたorm_rowidを持つ広告のインスタンスを取得します。
   *
   * @param id 対象のorm_rowid
   * @return idに対応するAdvertのインスタンス
   */
  Advert loadByRowid(Long id);

  /**
   * 主キー列の値を指定して広告のインスタンスを取得します。
   *
   * @param advertNo 広告番号
   * @return 主キー列の値に対応するAdvertのインスタンス
   */
  Advert load(Long advertNo);

  /**
   * 主キー列の値を指定して広告が既に存在するかどうかを返します。
   *
   * @param advertNo 広告番号
   * @return 主キー列の値に対応するAdvertの行が存在すればtrue
   */
  boolean exists(Long advertNo);

  /**
   * 新規Advertをデータベースに追加します。
   *
   * @param obj 追加対象のAdvert
   * @param loginInfo ログイン情報
   * @return 追加が成功した場合のorm_rowid値
   */
  Long insert(Advert obj, LoginInfo loginInfo);

  /**
   * 広告を更新します。
   *
   * @param obj 更新対象のAdvert
   * @param loginInfo ログイン情報
   */
  void update(Advert obj, LoginInfo loginInfo);

  /**
   * 広告を削除します。
   *
   * @param obj 削除対象のAdvert
   * @param loginInfo ログイン情報
   */
  void delete(Advert obj, LoginInfo loginInfo);

  /**
   * 主キー列の値を指定して広告を削除します。
   *
   * @param advertNo 広告番号
   */
  void delete(Long advertNo);

  /**
   * Queryオブジェクトを指定して広告のリストを取得します。
   *
   * @param query Queryオブジェクト
   * @return 検索結果に相当するAdvertのリスト
   */
  List<Advert> findByQuery(Query query);

  /**
   * SQLを指定して広告のリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するAdvertのリスト
   */
  List<Advert> findByQuery(String sqlString, Object... params);

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブルの全データ分のAdvertのリスト
   */
  List<Advert> loadAll();

}
