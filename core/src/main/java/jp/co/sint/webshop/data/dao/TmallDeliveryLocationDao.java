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
import jp.co.sint.webshop.data.dto.TmallDeliveryLocation;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 「DeliveryLocation」テーブルへのデータアクセスを担当するDAO(Data Access Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public interface TmallDeliveryLocationDao extends GenericDao<TmallDeliveryLocation, Long> {

  /**
   * 指定されたorm_rowidを持つキャンペーンのインスタンスを取得します。
   *
   * @param id 対象のorm_rowid
   * @return idに対応するDeliveryLocationのインスタンス
   */
  TmallDeliveryLocation loadByRowid(Long id);

  /**
   * 主キー列の値を指定してキャンペーンのインスタンスを取得します。
   * @param shopCode ショップコード
   * @param deliveryCompanyNo 配送会社コード
   * @param prefectureCode 地域コード
   * @param cityCode 都市コード
   * @param areaCode 県コード
   * @return 主キー列の値に対応するDeliveryLocationのインスタンス
   */
  TmallDeliveryLocation load(String shopCode, String deliveryCompanyNo, String prefectureCode, String cityCode ,String areaCode);
  /**
   * 主キー列の値を指定してキャンペーンのインスタンスを取得します。
   * @param shopCode ショップコード
   * @param deliveryCompanyNo 配送会社コード
   * @return 主キー列の値に対応するList<DeliveryLocation>のインスタンス
   */
  List<TmallDeliveryLocation> load(String shopCode, String deliveryCompanyNo);
  /**
   * 主キー列の値を指定してキャンペーンが既に存在するかどうかを返します。
   * @param shopCode ショップコード
   * @param deliveryCompanyNo 配送会社コード
   * @param prefectureCode 地域コード
   * @param cityCode 都市コード
   * @param areaCode 県コード
   * @return 主キー列の値に対応するDeliveryLocationの行が存在すればtrue
   */
  boolean exists(String shopCode, String deliveryCompanyNo, String prefectureCode, String cityCode ,String areaCode);

  /**
   * 新規DeliveryLocationをデータベースに追加します。
   *
   * @param obj 追加対象のDeliveryLocation
   * @param loginInfo ログイン情報
   * @return 追加が成功した場合のorm_rowid値
   */
  Long insert(TmallDeliveryLocation obj, LoginInfo loginInfo);

  /**
   * DeliveryLocationを更新します。
   *
   * @param obj 更新対象のDeliveryLocation
   * @param loginInfo ログイン情報
   */
  void update(TmallDeliveryLocation obj, LoginInfo loginInfo);

  /**
   * DeliveryLocationを削除します。
   *
   * @param obj 削除対象のDeliveryLocation
   * @param loginInfo ログイン情報
   */
  void delete(TmallDeliveryLocation obj, LoginInfo loginInfo);

  /**
   * Queryオブジェクトを指定してキャンペーンのリストを取得します。
   *
   * @param query Queryオブジェクト
   * @return 検索結果に相当するDeliveryLocationのリスト
   */
  List<TmallDeliveryLocation> findByQuery(Query query);

  /**
   * SQLを指定してキャンペーンのリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するDeliveryLocationのリスト
   */
  List<TmallDeliveryLocation> findByQuery(String sqlString, Object... params);

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブルの全データ分のDeliveryLocationのリスト
   */
  List<TmallDeliveryLocation> loadAll();

}
