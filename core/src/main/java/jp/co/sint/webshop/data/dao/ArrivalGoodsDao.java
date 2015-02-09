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
import jp.co.sint.webshop.data.dto.ArrivalGoods;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 「商品入荷お知らせ(ARRIVAL_GOODS)」テーブルへのデータアクセスを担当するDAO(Data Access Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public interface ArrivalGoodsDao extends GenericDao<ArrivalGoods, Long> {

  /**
   * 指定されたorm_rowidを持つ商品入荷お知らせのインスタンスを取得します。
   *
   * @param id 対象のorm_rowid
   * @return idに対応するArrivalGoodsのインスタンス
   */
  ArrivalGoods loadByRowid(Long id);

  /**
   * 主キー列の値を指定して商品入荷お知らせのインスタンスを取得します。
   *
   * @param shopCode ショップコード
   * @param skuCode SKUコード
   * @param email メールアドレス
   * @return 主キー列の値に対応するArrivalGoodsのインスタンス
   */
  ArrivalGoods load(String shopCode, String skuCode, String email);

  /**
   * 主キー列の値を指定して商品入荷お知らせが既に存在するかどうかを返します。
   *
   * @param shopCode ショップコード
   * @param skuCode SKUコード
   * @param email メールアドレス
   * @return 主キー列の値に対応するArrivalGoodsの行が存在すればtrue
   */
  boolean exists(String shopCode, String skuCode, String email);

  /**
   * 新規ArrivalGoodsをデータベースに追加します。
   *
   * @param obj 追加対象のArrivalGoods
   * @param loginInfo ログイン情報
   * @return 追加が成功した場合のorm_rowid値
   */
  Long insert(ArrivalGoods obj, LoginInfo loginInfo);

  /**
   * 商品入荷お知らせを更新します。
   *
   * @param obj 更新対象のArrivalGoods
   * @param loginInfo ログイン情報
   */
  void update(ArrivalGoods obj, LoginInfo loginInfo);

  /**
   * 商品入荷お知らせを削除します。
   *
   * @param obj 削除対象のArrivalGoods
   * @param loginInfo ログイン情報
   */
  void delete(ArrivalGoods obj, LoginInfo loginInfo);

  /**
   * 主キー列の値を指定して商品入荷お知らせを削除します。
   *
   * @param shopCode ショップコード
   * @param skuCode SKUコード
   * @param email メールアドレス
   */
  void delete(String shopCode, String skuCode, String email);

  /**
   * Queryオブジェクトを指定して商品入荷お知らせのリストを取得します。
   *
   * @param query Queryオブジェクト
   * @return 検索結果に相当するArrivalGoodsのリスト
   */
  List<ArrivalGoods> findByQuery(Query query);

  /**
   * SQLを指定して商品入荷お知らせのリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するArrivalGoodsのリスト
   */
  List<ArrivalGoods> findByQuery(String sqlString, Object... params);

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブルの全データ分のArrivalGoodsのリスト
   */
  List<ArrivalGoods> loadAll();

}
