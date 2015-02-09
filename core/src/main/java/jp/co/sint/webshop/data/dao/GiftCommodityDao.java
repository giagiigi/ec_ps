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
import jp.co.sint.webshop.data.dto.GiftCommodity;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 「ギフト対象商品(GIFT_COMMODITY)」テーブルへのデータアクセスを担当するDAO(Data Access Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public interface GiftCommodityDao extends GenericDao<GiftCommodity, Long> {

  /**
   * 指定されたorm_rowidを持つギフト対象商品のインスタンスを取得します。
   *
   * @param id 対象のorm_rowid
   * @return idに対応するGiftCommodityのインスタンス
   */
  GiftCommodity loadByRowid(Long id);

  /**
   * 主キー列の値を指定してギフト対象商品のインスタンスを取得します。
   *
   * @param shopCode ショップコード
   * @param giftCode ギフトコード
   * @param commodityCode 商品コード
   * @return 主キー列の値に対応するGiftCommodityのインスタンス
   */
  GiftCommodity load(String shopCode, String giftCode, String commodityCode);

  /**
   * 主キー列の値を指定してギフト対象商品が既に存在するかどうかを返します。
   *
   * @param shopCode ショップコード
   * @param giftCode ギフトコード
   * @param commodityCode 商品コード
   * @return 主キー列の値に対応するGiftCommodityの行が存在すればtrue
   */
  boolean exists(String shopCode, String giftCode, String commodityCode);

  /**
   * 新規GiftCommodityをデータベースに追加します。
   *
   * @param obj 追加対象のGiftCommodity
   * @param loginInfo ログイン情報
   * @return 追加が成功した場合のorm_rowid値
   */
  Long insert(GiftCommodity obj, LoginInfo loginInfo);

  /**
   * ギフト対象商品を更新します。
   *
   * @param obj 更新対象のGiftCommodity
   * @param loginInfo ログイン情報
   */
  void update(GiftCommodity obj, LoginInfo loginInfo);

  /**
   * ギフト対象商品を削除します。
   *
   * @param obj 削除対象のGiftCommodity
   * @param loginInfo ログイン情報
   */
  void delete(GiftCommodity obj, LoginInfo loginInfo);

  /**
   * 主キー列の値を指定してギフト対象商品を削除します。
   *
   * @param shopCode ショップコード
   * @param giftCode ギフトコード
   * @param commodityCode 商品コード
   */
  void delete(String shopCode, String giftCode, String commodityCode);

  /**
   * Queryオブジェクトを指定してギフト対象商品のリストを取得します。
   *
   * @param query Queryオブジェクト
   * @return 検索結果に相当するGiftCommodityのリスト
   */
  List<GiftCommodity> findByQuery(Query query);

  /**
   * SQLを指定してギフト対象商品のリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するGiftCommodityのリスト
   */
  List<GiftCommodity> findByQuery(String sqlString, Object... params);

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブルの全データ分のGiftCommodityのリスト
   */
  List<GiftCommodity> loadAll();

}
