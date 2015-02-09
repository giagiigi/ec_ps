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
import jp.co.sint.webshop.data.dto.CommodityAccessLog;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 「商品別アクセスログ(COMMODITY_ACCESS_LOG)」テーブルへのデータアクセスを担当するDAO(Data Access Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public interface CommodityAccessLogDao extends GenericDao<CommodityAccessLog, Long> {

  /**
   * 指定されたorm_rowidを持つ商品別アクセスログのインスタンスを取得します。
   *
   * @param id 対象のorm_rowid
   * @return idに対応するCommodityAccessLogのインスタンス
   */
  CommodityAccessLog loadByRowid(Long id);

  /**
   * 主キー列の値を指定して商品別アクセスログのインスタンスを取得します。
   *
   * @param commodityAccessLogId 商品別アクセスログID
   * @return 主キー列の値に対応するCommodityAccessLogのインスタンス
   */
  CommodityAccessLog load(Long commodityAccessLogId);

  /**
   * 主キー列の値を指定して商品別アクセスログが既に存在するかどうかを返します。
   *
   * @param commodityAccessLogId 商品別アクセスログID
   * @return 主キー列の値に対応するCommodityAccessLogの行が存在すればtrue
   */
  boolean exists(Long commodityAccessLogId);

  /**
   * 新規CommodityAccessLogをデータベースに追加します。
   *
   * @param obj 追加対象のCommodityAccessLog
   * @param loginInfo ログイン情報
   * @return 追加が成功した場合のorm_rowid値
   */
  Long insert(CommodityAccessLog obj, LoginInfo loginInfo);

  /**
   * 商品別アクセスログを更新します。
   *
   * @param obj 更新対象のCommodityAccessLog
   * @param loginInfo ログイン情報
   */
  void update(CommodityAccessLog obj, LoginInfo loginInfo);

  /**
   * 商品別アクセスログを削除します。
   *
   * @param obj 削除対象のCommodityAccessLog
   * @param loginInfo ログイン情報
   */
  void delete(CommodityAccessLog obj, LoginInfo loginInfo);

  /**
   * 主キー列の値を指定して商品別アクセスログを削除します。
   *
   * @param commodityAccessLogId 商品別アクセスログID
   */
  void delete(Long commodityAccessLogId);

  /**
   * Queryオブジェクトを指定して商品別アクセスログのリストを取得します。
   *
   * @param query Queryオブジェクト
   * @return 検索結果に相当するCommodityAccessLogのリスト
   */
  List<CommodityAccessLog> findByQuery(Query query);

  /**
   * SQLを指定して商品別アクセスログのリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するCommodityAccessLogのリスト
   */
  List<CommodityAccessLog> findByQuery(String sqlString, Object... params);

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブルの全データ分のCommodityAccessLogのリスト
   */
  List<CommodityAccessLog> loadAll();

}
