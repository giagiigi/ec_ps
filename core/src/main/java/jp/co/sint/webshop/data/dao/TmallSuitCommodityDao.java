//
// Copyright(C) 2007-2008 System Integrator Corp.
// All rights reserved.
//
// このファイルはEDMファイルから自動生成されます。
// 直接編集しないで下さい。
//
package jp.co.sint.webshop.data.dao;

import java.util.List;

import jp.co.sint.webshop.data.GenericDao;
import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.dto.TmallSuitCommodity;
import jp.co.sint.webshop.service.LoginInfo;

/**
 * 「TMALL库存比率分配(TmallStockAllocation)」テーブルへのデータアクセスを担当するDAO(Data Access
 * Object)です。
 * 
 * @author System Integrator Corp.
 */
public interface TmallSuitCommodityDao extends GenericDao<TmallSuitCommodity, Long> {

  /**
   * 指定されたorm_rowidを持つギフトのインスタンスを取得します。
   * 
   * @param id
   *          対象のorm_rowid
   * @return idに対応するTmallStockAllocationのインスタンス
   */
  TmallSuitCommodity loadByRowid(Long id);

  /**
   * 主キー列の値を指定してギフトのインスタンスを取得します。
   * 
   * @param shopCode
   *          ショップコード
   * @param skuCode
   *          ギフトコード
   * @return 主キー列の値に対応するTmallStockAllocationのインスタンス
   */
  TmallSuitCommodity load(String commodityCode);

  /**
   * 主キー列の値を指定してギフトが既に存在するかどうかを返します。
   * 
   * @param shopCode
   *          ショップコード
   * @param skuCode
   *          ギフトコード
   * @return 主キー列の値に対応するTmallStockAllocationの行が存在すればtrue
   */
  boolean exists(String commodityCode);

  /**
   * 新規TmallStockAllocationをデータベースに追加します。
   * 
   * @param obj
   *          追加対象のTmallStockAllocation
   * @param loginInfo
   *          ログイン情報
   * @return 追加が成功した場合のorm_rowid値
   */
  Long insert(TmallSuitCommodity obj, LoginInfo loginInfo);

  /**
   * ギフトを更新します。
   * 
   * @param obj
   *          更新対象のTmallStockAllocation
   * @param loginInfo
   *          ログイン情報
   */
  void update(TmallSuitCommodity obj, LoginInfo loginInfo);

  /**
   * ギフトを削除します。
   * 
   * @param obj
   *          削除対象のTmallStockAllocation
   * @param loginInfo
   *          ログイン情報
   */
  void delete(TmallSuitCommodity obj, LoginInfo loginInfo);

  /**
   * 主キー列の値を指定してギフトを削除します。
   * 
   * @param shopCode
   *          ショップコード
   * @param skuCode
   *          ギフトコード
   */
  void delete(String commodityCode);

  /**
   * Queryオブジェクトを指定してギフトのリストを取得します。
   * 
   * @param query
   *          Queryオブジェクト
   * @return 検索結果に相当するTmallStockAllocationのリスト
   */
  List<TmallSuitCommodity> findByQuery(Query query);

  /**
   * SQLを指定してギフトのリストを取得します。
   * 
   * @param sqlString
   *          バインド変数(?)を含むSQL文字列
   * @param params
   *          バインド変数に与える値の配列
   * @return 検索結果に相当するTmallStockAllocationのリスト
   */
  List<TmallSuitCommodity> findByQuery(String sqlString, Object... params);

  /**
   * テーブルの全データを一括で取得します。
   * 
   * @return テーブルの全データ分のTmallStockAllocationのリスト
   */
  List<TmallSuitCommodity> loadAll();

}
