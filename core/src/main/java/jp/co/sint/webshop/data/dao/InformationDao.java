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
import jp.co.sint.webshop.data.dto.Information;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 「お知らせ(INFORMATION)」テーブルへのデータアクセスを担当するDAO(Data Access Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public interface InformationDao extends GenericDao<Information, Long> {

  /**
   * 指定されたorm_rowidを持つお知らせのインスタンスを取得します。
   *
   * @param id 対象のorm_rowid
   * @return idに対応するInformationのインスタンス
   */
  Information loadByRowid(Long id);

  /**
   * 主キー列の値を指定してお知らせのインスタンスを取得します。
   *
   * @param informationNo お知らせ番号
   * @return 主キー列の値に対応するInformationのインスタンス
   */
  Information load(Long informationNo);

  /**
   * 主キー列の値を指定してお知らせが既に存在するかどうかを返します。
   *
   * @param informationNo お知らせ番号
   * @return 主キー列の値に対応するInformationの行が存在すればtrue
   */
  boolean exists(Long informationNo);

  /**
   * 新規Informationをデータベースに追加します。
   *
   * @param obj 追加対象のInformation
   * @param loginInfo ログイン情報
   * @return 追加が成功した場合のorm_rowid値
   */
  Long insert(Information obj, LoginInfo loginInfo);

  /**
   * お知らせを更新します。
   *
   * @param obj 更新対象のInformation
   * @param loginInfo ログイン情報
   */
  void update(Information obj, LoginInfo loginInfo);

  /**
   * お知らせを削除します。
   *
   * @param obj 削除対象のInformation
   * @param loginInfo ログイン情報
   */
  void delete(Information obj, LoginInfo loginInfo);

  /**
   * 主キー列の値を指定してお知らせを削除します。
   *
   * @param informationNo お知らせ番号
   */
  void delete(Long informationNo);

  /**
   * Queryオブジェクトを指定してお知らせのリストを取得します。
   *
   * @param query Queryオブジェクト
   * @return 検索結果に相当するInformationのリスト
   */
  List<Information> findByQuery(Query query);

  /**
   * SQLを指定してお知らせのリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するInformationのリスト
   */
  List<Information> findByQuery(String sqlString, Object... params);

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブルの全データ分のInformationのリスト
   */
  List<Information> loadAll();

}
