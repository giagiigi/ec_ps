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
import jp.co.sint.webshop.data.dto.OrderMedia;
import jp.co.sint.webshop.service.LoginInfo;

/**
 * 「订单媒体(OrderMediaDao)」テーブルへのデータアクセスを担当するDAO(Data Access Object)です。
 * 
 * @author Kousen.
 */
public interface OrderMediaDao extends GenericDao<OrderMedia, Long> {

  /**
   * 指定されたorm_rowidを持つ订单媒体のインスタンスを取得します。
   * 
   * @param id
   *          対象のorm_rowid
   * @return idに対応するOrderMediaのインスタンス
   */
  OrderMedia loadByRowid(Long id);

  /**
   * 主キー列の値を指定して订单媒体のインスタンスを取得します。
   * 
   * @param orderNo
   *          订单号
   * @return 主キー列の値に対応するOrderMediaのインスタンス
   */
  OrderMedia load(String orderNo);

  /**
   * 主キー列の値を指定して订单媒体が既に存在するかどうかを返します。
   * 
   * @param orderNo
   *          订单号
   * @return 主キー列の値に対応するOrderMediaの行が存在すればtrue
   */
  boolean exists(String orderNo);

  /**
   * 新規OrderMediaをデータベースに追加します。
   * 
   * @param obj
   *          追加対象のOrderMedia
   * @param loginInfo
   *          ログイン情報
   * @return 追加が成功した場合のorm_rowid値
   */
  Long insert(OrderMedia obj, LoginInfo loginInfo);

  /**
   * 订单媒体を更新します。
   * 
   * @param obj
   *          更新対象のOrderMedia
   * @param loginInfo
   *          ログイン情報
   */
  void update(OrderMedia obj, LoginInfo loginInfo);

  /**
   * 订单媒体を削除します。
   * 
   * @param obj
   *          削除対象のOrderMedia
   * @param loginInfo
   *          ログイン情報
   */
  void delete(OrderMedia obj, LoginInfo loginInfo);

  /**
   * 主キー列の値を指定して订单媒体を削除します。
   * 
   * @param orderNo订单号
   */
  void delete(String orderNo);

  /**
   * Queryオブジェクトを指定して订单媒体のリストを取得します。
   * 
   * @param query
   *          Queryオブジェクト
   * @return 検索結果に相当するOrderMediaのリスト
   */
  List<OrderMedia> findByQuery(Query query);

  /**
   * SQLを指定して订单媒体のリストを取得します。
   * 
   * @param sqlString
   *          バインド変数(?)を含むSQL文字列
   * @param params
   *          バインド変数に与える値の配列
   * @return 検索結果に相当するOrderMediaのリスト
   */
  List<OrderMedia> findByQuery(String sqlString, Object... params);

  /**
   * テーブルの全データを一括で取得します。
   * 
   * @return テーブルの全データ分のOrderMediaのリスト
   */
  List<OrderMedia> loadAll();

}
