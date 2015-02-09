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
import jp.co.sint.webshop.data.dto.Gift;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 「ギフト(GIFT)」テーブルへのデータアクセスを担当するDAO(Data Access Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public interface GiftDao extends GenericDao<Gift, Long> {

  /**
   * 指定されたorm_rowidを持つギフトのインスタンスを取得します。
   *
   * @param id 対象のorm_rowid
   * @return idに対応するGiftのインスタンス
   */
  Gift loadByRowid(Long id);

  /**
   * 主キー列の値を指定してギフトのインスタンスを取得します。
   *
   * @param shopCode ショップコード
   * @param giftCode ギフトコード
   * @return 主キー列の値に対応するGiftのインスタンス
   */
  Gift load(String shopCode, String giftCode);

  /**
   * 主キー列の値を指定してギフトが既に存在するかどうかを返します。
   *
   * @param shopCode ショップコード
   * @param giftCode ギフトコード
   * @return 主キー列の値に対応するGiftの行が存在すればtrue
   */
  boolean exists(String shopCode, String giftCode);

  /**
   * 新規Giftをデータベースに追加します。
   *
   * @param obj 追加対象のGift
   * @param loginInfo ログイン情報
   * @return 追加が成功した場合のorm_rowid値
   */
  Long insert(Gift obj, LoginInfo loginInfo);

  /**
   * ギフトを更新します。
   *
   * @param obj 更新対象のGift
   * @param loginInfo ログイン情報
   */
  void update(Gift obj, LoginInfo loginInfo);

  /**
   * ギフトを削除します。
   *
   * @param obj 削除対象のGift
   * @param loginInfo ログイン情報
   */
  void delete(Gift obj, LoginInfo loginInfo);

  /**
   * 主キー列の値を指定してギフトを削除します。
   *
   * @param shopCode ショップコード
   * @param giftCode ギフトコード
   */
  void delete(String shopCode, String giftCode);

  /**
   * Queryオブジェクトを指定してギフトのリストを取得します。
   *
   * @param query Queryオブジェクト
   * @return 検索結果に相当するGiftのリスト
   */
  List<Gift> findByQuery(Query query);

  /**
   * SQLを指定してギフトのリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するGiftのリスト
   */
  List<Gift> findByQuery(String sqlString, Object... params);

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブルの全データ分のGiftのリスト
   */
  List<Gift> loadAll();

}
