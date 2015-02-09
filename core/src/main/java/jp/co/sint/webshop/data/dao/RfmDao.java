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
import jp.co.sint.webshop.data.dto.Rfm;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 「RFM(RFM)」テーブルへのデータアクセスを担当するDAO(Data Access Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public interface RfmDao extends GenericDao<Rfm, Long> {

  /**
   * 指定されたorm_rowidを持つRFMのインスタンスを取得します。
   *
   * @param id 対象のorm_rowid
   * @return idに対応するRfmのインスタンス
   */
  Rfm loadByRowid(Long id);

  /**
   * 主キー列の値を指定してRFMのインスタンスを取得します。
   *
   * @param orderNo 受注番号
   * @return 主キー列の値に対応するRfmのインスタンス
   */
  Rfm load(String orderNo);

  /**
   * 主キー列の値を指定してRFMが既に存在するかどうかを返します。
   *
   * @param orderNo 受注番号
   * @return 主キー列の値に対応するRfmの行が存在すればtrue
   */
  boolean exists(String orderNo);

  /**
   * 新規Rfmをデータベースに追加します。
   *
   * @param obj 追加対象のRfm
   * @param loginInfo ログイン情報
   * @return 追加が成功した場合のorm_rowid値
   */
  Long insert(Rfm obj, LoginInfo loginInfo);

  /**
   * RFMを更新します。
   *
   * @param obj 更新対象のRfm
   * @param loginInfo ログイン情報
   */
  void update(Rfm obj, LoginInfo loginInfo);

  /**
   * RFMを削除します。
   *
   * @param obj 削除対象のRfm
   * @param loginInfo ログイン情報
   */
  void delete(Rfm obj, LoginInfo loginInfo);

  /**
   * 主キー列の値を指定してRFMを削除します。
   *
   * @param orderNo 受注番号
   */
  void delete(String orderNo);

  /**
   * Queryオブジェクトを指定してRFMのリストを取得します。
   *
   * @param query Queryオブジェクト
   * @return 検索結果に相当するRfmのリスト
   */
  List<Rfm> findByQuery(Query query);

  /**
   * SQLを指定してRFMのリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するRfmのリスト
   */
  List<Rfm> findByQuery(String sqlString, Object... params);

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブルの全データ分のRfmのリスト
   */
  List<Rfm> loadAll();

}
