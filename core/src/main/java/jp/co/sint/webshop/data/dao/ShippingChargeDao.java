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
import jp.co.sint.webshop.data.dto.ShippingCharge;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 「送料(SHIPPING_CHARGE)」テーブルへのデータアクセスを担当するDAO(Data Access Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public interface ShippingChargeDao extends GenericDao<ShippingCharge, Long> {

  /**
   * 指定されたorm_rowidを持つ送料のインスタンスを取得します。
   *
   * @param id 対象のorm_rowid
   * @return idに対応するShippingChargeのインスタンス
   */
  ShippingCharge loadByRowid(Long id);

  /**
   * 主キー列の値を指定して送料のインスタンスを取得します。
   *
   * @param shopCode ショップコード
   * @param deliveryTypeNo 配送種別番号
   * @param regionBlockId 地域ブロックID
   * @return 主キー列の値に対応するShippingChargeのインスタンス
   */
  ShippingCharge load(String shopCode, Long deliveryTypeNo, Long regionBlockId);

  /**
   * 主キー列の値を指定して送料が既に存在するかどうかを返します。
   *
   * @param shopCode ショップコード
   * @param deliveryTypeNo 配送種別番号
   * @param regionBlockId 地域ブロックID
   * @return 主キー列の値に対応するShippingChargeの行が存在すればtrue
   */
  boolean exists(String shopCode, Long deliveryTypeNo, Long regionBlockId);

  /**
   * 新規ShippingChargeをデータベースに追加します。
   *
   * @param obj 追加対象のShippingCharge
   * @param loginInfo ログイン情報
   * @return 追加が成功した場合のorm_rowid値
   */
  Long insert(ShippingCharge obj, LoginInfo loginInfo);

  /**
   * 送料を更新します。
   *
   * @param obj 更新対象のShippingCharge
   * @param loginInfo ログイン情報
   */
  void update(ShippingCharge obj, LoginInfo loginInfo);

  /**
   * 送料を削除します。
   *
   * @param obj 削除対象のShippingCharge
   * @param loginInfo ログイン情報
   */
  void delete(ShippingCharge obj, LoginInfo loginInfo);

  /**
   * 主キー列の値を指定して送料を削除します。
   *
   * @param shopCode ショップコード
   * @param deliveryTypeNo 配送種別番号
   * @param regionBlockId 地域ブロックID
   */
  void delete(String shopCode, Long deliveryTypeNo, Long regionBlockId);

  /**
   * Queryオブジェクトを指定して送料のリストを取得します。
   *
   * @param query Queryオブジェクト
   * @return 検索結果に相当するShippingChargeのリスト
   */
  List<ShippingCharge> findByQuery(Query query);

  /**
   * SQLを指定して送料のリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するShippingChargeのリスト
   */
  List<ShippingCharge> findByQuery(String sqlString, Object... params);

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブルの全データ分のShippingChargeのリスト
   */
  List<ShippingCharge> loadAll();

}
