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
import jp.co.sint.webshop.data.dto.FriendCouponExchangeHistory;
import jp.co.sint.webshop.service.LoginInfo;

/**
 * @author Kousen.
 */
public interface FriendCouponExchangeHistoryDao extends GenericDao<FriendCouponExchangeHistory, Long> {

  /**
   * 指定されたorm_rowidを持つキャンペーンのインスタンスを取得します。
   * 
   * @param id
   *          対象のorm_rowid
   * @return idに対応するFriendCouponExchangeHistoryのインスタンス
   */
  FriendCouponExchangeHistory loadByRowid(Long id);

  /**
   * 主キー列の値を指定してキャンペーンが既に存在するかどうかを返します。
   * 
   * @param couponIssueNo
   *          クーポンコード
   * @return 主キー列の値に対応するFriendCouponExchangeHistoryの行が存在すればtrue
   */
  boolean exists(String couponIssueNo);

  /**
   * キャンペーンを削除します。
   * 
   * @param obj
   *          削除対象のFriendCouponExchangeHistory
   * @param loginInfo
   *          ログイン情報
   */
  void delete(FriendCouponExchangeHistory obj, LoginInfo loginInfo);

  /**
   * 主キー列の値を指定してキャンペーンを削除します。
   * 
   * @param couponIssueNo
   *          クーポンコード
   */
  void delete(String couponIssueNo);

  /**
   * Queryオブジェクトを指定してキャンペーンのリストを取得します。
   * 
   * @param query
   *          Queryオブジェクト
   * @return 検索結果に相当するFriendCouponExchangeHistoryのリスト
   */
  List<FriendCouponExchangeHistory> findByQuery(Query query);

  /**
   * SQLを指定してキャンペーンのリストを取得します。
   * 
   * @param sqlString
   *          バインド変数(?)を含むSQL文字列
   * @param params
   *          バインド変数に与える値の配列
   * @return 検索結果に相当するFriendCouponExchangeHistoryのリスト
   */
  List<FriendCouponExchangeHistory> findByQuery(String sqlString, Object... params);

  /**
   * テーブルの全データを一括で取得します。
   * 
   * @return テーブルの全データ分のFriendCouponExchangeHistoryのリスト
   */
  List<FriendCouponExchangeHistory> loadAll();

  /**
   * クーポンコードを指定してFriendCouponExchangeHistoryを取得します。 　　
   * @param　couponIssueNo クーポンコード
   * 
   * @return 検索結果に相当するFriendCouponExchangeHistory
   */
  public FriendCouponExchangeHistory load(String couponIssueNo);

  /**
   * 顧客コードを指定して両替使うポイントを取得します 　　 
   * @param customerCode 顧客コード 
   * @return 両替使うポイント
   */
  Long getAllExchangePoint(String customerCode);

}
