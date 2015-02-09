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
import jp.co.sint.webshop.data.dto.CommodityLayout;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 「商品レイアウト(COMMODITY_LAYOUT)」テーブルへのデータアクセスを担当するDAO(Data Access Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public interface CommodityLayoutDao extends GenericDao<CommodityLayout, Long> {

  /**
   * 指定されたorm_rowidを持つ商品レイアウトのインスタンスを取得します。
   *
   * @param id 対象のorm_rowid
   * @return idに対応するCommodityLayoutのインスタンス
   */
  CommodityLayout loadByRowid(Long id);

  /**
   * 主キー列の値を指定して商品レイアウトのインスタンスを取得します。
   *
   * @param shopCode ショップコード
   * @param partsCode パーツコード
   * @return 主キー列の値に対応するCommodityLayoutのインスタンス
   */
  CommodityLayout load(String shopCode, String partsCode);

  /**
   * 主キー列の値を指定して商品レイアウトが既に存在するかどうかを返します。
   *
   * @param shopCode ショップコード
   * @param partsCode パーツコード
   * @return 主キー列の値に対応するCommodityLayoutの行が存在すればtrue
   */
  boolean exists(String shopCode, String partsCode);

  /**
   * 新規CommodityLayoutをデータベースに追加します。
   *
   * @param obj 追加対象のCommodityLayout
   * @param loginInfo ログイン情報
   * @return 追加が成功した場合のorm_rowid値
   */
  Long insert(CommodityLayout obj, LoginInfo loginInfo);

  /**
   * 商品レイアウトを更新します。
   *
   * @param obj 更新対象のCommodityLayout
   * @param loginInfo ログイン情報
   */
  void update(CommodityLayout obj, LoginInfo loginInfo);

  /**
   * 商品レイアウトを削除します。
   *
   * @param obj 削除対象のCommodityLayout
   * @param loginInfo ログイン情報
   */
  void delete(CommodityLayout obj, LoginInfo loginInfo);

  /**
   * 主キー列の値を指定して商品レイアウトを削除します。
   *
   * @param shopCode ショップコード
   * @param partsCode パーツコード
   */
  void delete(String shopCode, String partsCode);

  /**
   * Queryオブジェクトを指定して商品レイアウトのリストを取得します。
   *
   * @param query Queryオブジェクト
   * @return 検索結果に相当するCommodityLayoutのリスト
   */
  List<CommodityLayout> findByQuery(Query query);

  /**
   * SQLを指定して商品レイアウトのリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するCommodityLayoutのリスト
   */
  List<CommodityLayout> findByQuery(String sqlString, Object... params);

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブルの全データ分のCommodityLayoutのリスト
   */
  List<CommodityLayout> loadAll();

}
