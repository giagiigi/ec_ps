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
import jp.co.sint.webshop.data.dto.RecommendedCommodity;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 「個別リコメンド(RECOMMENDED_COMMODITY)」テーブルへのデータアクセスを担当するDAO(Data Access Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public interface RecommendedCommodityDao extends GenericDao<RecommendedCommodity, Long> {

  /**
   * 指定されたorm_rowidを持つ個別リコメンドのインスタンスを取得します。
   *
   * @param id 対象のorm_rowid
   * @return idに対応するRecommendedCommodityのインスタンス
   */
  RecommendedCommodity loadByRowid(Long id);

  /**
   * 主キー列の値を指定して個別リコメンドのインスタンスを取得します。
   *
   * @param shopCode ショップコード
   * @param commodityCode 商品コード
   * @param customerCode 顧客コード
   * @return 主キー列の値に対応するRecommendedCommodityのインスタンス
   */
  RecommendedCommodity load(String shopCode, String commodityCode, String customerCode);

  /**
   * 主キー列の値を指定して個別リコメンドが既に存在するかどうかを返します。
   *
   * @param shopCode ショップコード
   * @param commodityCode 商品コード
   * @param customerCode 顧客コード
   * @return 主キー列の値に対応するRecommendedCommodityの行が存在すればtrue
   */
  boolean exists(String shopCode, String commodityCode, String customerCode);

  /**
   * 新規RecommendedCommodityをデータベースに追加します。
   *
   * @param obj 追加対象のRecommendedCommodity
   * @param loginInfo ログイン情報
   * @return 追加が成功した場合のorm_rowid値
   */
  Long insert(RecommendedCommodity obj, LoginInfo loginInfo);

  /**
   * 個別リコメンドを更新します。
   *
   * @param obj 更新対象のRecommendedCommodity
   * @param loginInfo ログイン情報
   */
  void update(RecommendedCommodity obj, LoginInfo loginInfo);

  /**
   * 個別リコメンドを削除します。
   *
   * @param obj 削除対象のRecommendedCommodity
   * @param loginInfo ログイン情報
   */
  void delete(RecommendedCommodity obj, LoginInfo loginInfo);

  /**
   * 主キー列の値を指定して個別リコメンドを削除します。
   *
   * @param shopCode ショップコード
   * @param commodityCode 商品コード
   * @param customerCode 顧客コード
   */
  void delete(String shopCode, String commodityCode, String customerCode);

  /**
   * Queryオブジェクトを指定して個別リコメンドのリストを取得します。
   *
   * @param query Queryオブジェクト
   * @return 検索結果に相当するRecommendedCommodityのリスト
   */
  List<RecommendedCommodity> findByQuery(Query query);

  /**
   * SQLを指定して個別リコメンドのリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するRecommendedCommodityのリスト
   */
  List<RecommendedCommodity> findByQuery(String sqlString, Object... params);

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブルの全データ分のRecommendedCommodityのリスト
   */
  List<RecommendedCommodity> loadAll();

}
