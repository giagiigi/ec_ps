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
import jp.co.sint.webshop.data.dto.NewCouponHistoryUseInfo;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 「优惠券发行履历」テーブルへのデータアクセスを担当するDAO(Data Access Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public interface NewCouponHistoryUseInfoDao extends GenericDao<NewCouponHistoryUseInfo, Long> {

  /**
   * 指定されたorm_rowidを持つNewCouponHistoryUseInfoのインスタンスを取得します。
   *
   * @param id 対象のorm_rowid
   * @return idに対応するNewCouponHistoryUseInfoのインスタンス
   */
  NewCouponHistoryUseInfo loadByRowid(Long id);

  /**
   * 主キー列の値を指定してNewCouponHistoryUseInfoのインスタンスを取得します。
   *
   * @param couponIssueNo
   * @param couponUseNo 
   * @return 主キー列の値に対応するNewCouponHistoryUseInfoのインスタンス
   */
  NewCouponHistoryUseInfo load(String couponIssueNo, String couponUseNo);

  /**
   * 主キー列の値を指定してNewCouponHistoryUseInfoが既に存在するかどうかを返します。
   *
   * @param couponIssueNo 
   * @param couponUseNo 
   * @return 主キー列の値に対応するNewCouponHistoryUseInfoの行が存在すればtrue
   */
  boolean exists(String couponIssueNo, String couponUseNo);

  /**
   * 新規NewCouponHistoryUseInfoをデータベースに追加します。
   *
   * @param obj 追加対象のNewCouponHistoryUseInfo
   * @param loginInfo ログイン情報
   * @return 追加が成功した場合のorm_rowid値
   */
  Long insert(NewCouponHistoryUseInfo obj, LoginInfo loginInfo);

  /**
   * NewCouponHistoryUseInfoを更新します。
   *
   * @param obj 更新対象のNewCouponHistoryUseInfo
   * @param loginInfo ログイン情報
   */
  void update(NewCouponHistoryUseInfo obj, LoginInfo loginInfo);

  /**
   * NewCouponHistoryUseInfoを削除します。
   *
   * @param obj 削除対象のNewCouponHistoryUseInfo
   * @param loginInfo ログイン情報
   */
  void delete(NewCouponHistoryUseInfo obj, LoginInfo loginInfo);

  /**
   * 主キー列の値を指定してNewCouponHistoryUseInfoを削除します。
   *
   * @param couponIssueNo 
   * @param couponUseNo 
   */
  void delete(String couponIssueNo, String couponUseNo);

  /**
   * Queryオブジェクトを指定してNewCouponHistoryUseInfoのリストを取得します。
   *
   * @param query Queryオブジェクト
   * @return 検索結果に相当するNewCouponHistoryUseInfoのリスト
   */
  List<NewCouponHistoryUseInfo> findByQuery(Query query);

  /**
   * SQLを指定してNewCouponHistoryUseInfoのリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するNewCouponHistoryUseInfoのリスト
   */
  List<NewCouponHistoryUseInfo> findByQuery(String sqlString, Object... params);

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブルの全データ分のNewCouponHistoryUseInfoのリスト
   */
  List<NewCouponHistoryUseInfo> loadAll();

}
