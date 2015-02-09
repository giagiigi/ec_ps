//
//Copyright(C) 2007-2008 System Integrator Corp.
//All rights reserved.
//
package jp.co.sint.webshop.data.dao;

import java.util.List;

import jp.co.sint.webshop.data.GenericDao;
import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.dto.CommodityPreviousDay;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 「前一天临近有效期商品(commodity_previous_day)」テーブルへのデータアクセスを担当するDAO(Data Access Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public interface CommodityPreviousDayDao extends GenericDao<CommodityPreviousDay, Long> {

  /**
   * 指定されたorm_rowidを持つ前一天临近有效期商品のインスタンスを取得します。
   *
   * @param id 対象のorm_rowid
   * @return idに対応するCommodityPreviousDayのインスタンス
   */
  CommodityPreviousDay loadByRowid(Long id);

  /**
   * 主キー列の値を指定して前一天临近有效期商品のインスタンスを取得します。
   *
   * @param commodityCode 商品编号
   * @return 主キー列の値に対応するCommodityPreviousDayのインスタンス
   */
  CommodityPreviousDay load(String commodityCode);

  /**
   * 主キー列の値を指定して前一天临近有效期商品が既に存在するかどうかを返します。
   *
   * @param commodityCode 商品编号
   * @return 主キー列の値に対応するCommodityPreviousDayの行が存在すればtrue
   */
  boolean exists(String commodityCode);

  /**
   * 新規CommodityPreviousDayをデータベースに追加します。
   *
   * @param obj 追加対象のCommodityPreviousDay
   * @param loginInfo ログイン情報
   * @return 追加が成功した場合のorm_rowid値
   */
  Long insert(CommodityPreviousDay obj, LoginInfo loginInfo);

  /**
   * 前一天临近有效期商品を更新します。
   *
   * @param obj 更新対象のCommodityPreviousDay
   * @param loginInfo ログイン情報
   */
  void update(CommodityPreviousDay obj, LoginInfo loginInfo);

  /**
   * 前一天临近有效期商品を削除します。
   *
   * @param obj 削除対象のCommodityPreviousDay
   * @param loginInfo ログイン情報
   */
  void delete(CommodityPreviousDay obj, LoginInfo loginInfo);

  /**
   * 主キー列の値を指定して前一天临近有效期商品を削除します。
   *
   * @param commodityCode 商品编号
   */
  void delete(String commodityCode);

  /**
   * Queryオブジェクトを指定して前一天临近有效期商品のリストを取得します。
   *
   * @param query Queryオブジェクト
   * @return 検索結果に相当するCommodityPreviousDayのリスト
   */
  List<CommodityPreviousDay> findByQuery(Query query);

  /**
   * SQLを指定して前一天临近有效期商品のリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するCommodityPreviousDayのリスト
   */
  List<CommodityPreviousDay> findByQuery(String sqlString, Object... params);

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブルの全データ分のCommodityPreviousDayのリスト
   */
  List<CommodityPreviousDay> loadAll();

  /**
   * テーブルの全データを一括で削除します。
   */
  void deleteAll();
  
}
