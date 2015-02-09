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
import jp.co.sint.webshop.data.dto.OptionalCampaign;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 「キャンペーン(CAMPAIGN)」テーブルへのデータアクセスを担当するDAO(Data Access Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public interface OptionalCampaignDao extends GenericDao<OptionalCampaign, Long> {

  /**
   * 指定されたorm_rowidを持つキャンペーンのインスタンスを取得します。
   *
   * @param id 対象のorm_rowid
   * @return idに対応するCampaignのインスタンス
   */
  OptionalCampaign loadByRowid(Long id);

  /**
   * 主キー列の値を指定してキャンペーンのインスタンスを取得します。
   *
   * @param shopCode ショップコード
   * @param campaignCode キャンペーンコード
   * @return 主キー列の値に対応するCampaignのインスタンス
   */
  OptionalCampaign load(String shopCode, String campaignCode);

  /**
   * 主キー列の値を指定してキャンペーンが既に存在するかどうかを返します。
   *
   * @param shopCode ショップコード
   * @param campaignCode キャンペーンコード
   * @return 主キー列の値に対応するCampaignの行が存在すればtrue
   */
  boolean exists(String shopCode, String campaignCode);

  /**
   * 新規Campaignをデータベースに追加します。
   *
   * @param obj 追加対象のCampaign
   * @param loginInfo ログイン情報
   * @return 追加が成功した場合のorm_rowid値
   */
  Long insert(OptionalCampaign obj, LoginInfo loginInfo);

  /**
   * キャンペーンを更新します。
   *
   * @param obj 更新対象のCampaign
   * @param loginInfo ログイン情報
   */
  void update(OptionalCampaign obj, LoginInfo loginInfo);

  /**
   * キャンペーンを削除します。
   *
   * @param obj 削除対象のCampaign
   * @param loginInfo ログイン情報
   */
  void delete(OptionalCampaign obj, LoginInfo loginInfo);

  /**
   * 主キー列の値を指定してキャンペーンを削除します。
   *
   * @param shopCode ショップコード
   * @param campaignCode キャンペーンコード
   */
  void delete(String shopCode, String campaignCode);

  /**
   * Queryオブジェクトを指定してキャンペーンのリストを取得します。
   *
   * @param query Queryオブジェクト
   * @return 検索結果に相当するCampaignのリスト
   */
  List<OptionalCampaign> findByQuery(Query query);

  /**
   * SQLを指定してキャンペーンのリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するCampaignのリスト
   */
  List<OptionalCampaign> findByQuery(String sqlString, Object... params);

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブルの全データ分のCampaignのリスト
   */
  List<OptionalCampaign> loadAll();

}
