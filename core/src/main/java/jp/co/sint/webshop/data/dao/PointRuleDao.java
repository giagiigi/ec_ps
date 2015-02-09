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
import jp.co.sint.webshop.data.dto.PointRule;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 「ポイントルール(POINT_RULE)」テーブルへのデータアクセスを担当するDAO(Data Access Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public interface PointRuleDao extends GenericDao<PointRule, Long> {

  /**
   * 指定されたorm_rowidを持つポイントルールのインスタンスを取得します。
   *
   * @param id 対象のorm_rowid
   * @return idに対応するPointRuleのインスタンス
   */
  PointRule loadByRowid(Long id);

  /**
   * 主キー列の値を指定してポイントルールのインスタンスを取得します。
   *
   * @param pointRuleNo ポイントルール番号
   * @return 主キー列の値に対応するPointRuleのインスタンス
   */
  PointRule load(Long pointRuleNo);

  /**
   * 主キー列の値を指定してポイントルールが既に存在するかどうかを返します。
   *
   * @param pointRuleNo ポイントルール番号
   * @return 主キー列の値に対応するPointRuleの行が存在すればtrue
   */
  boolean exists(Long pointRuleNo);

  /**
   * 新規PointRuleをデータベースに追加します。
   *
   * @param obj 追加対象のPointRule
   * @param loginInfo ログイン情報
   * @return 追加が成功した場合のorm_rowid値
   */
  Long insert(PointRule obj, LoginInfo loginInfo);

  /**
   * ポイントルールを更新します。
   *
   * @param obj 更新対象のPointRule
   * @param loginInfo ログイン情報
   */
  void update(PointRule obj, LoginInfo loginInfo);

  /**
   * ポイントルールを削除します。
   *
   * @param obj 削除対象のPointRule
   * @param loginInfo ログイン情報
   */
  void delete(PointRule obj, LoginInfo loginInfo);

  /**
   * 主キー列の値を指定してポイントルールを削除します。
   *
   * @param pointRuleNo ポイントルール番号
   */
  void delete(Long pointRuleNo);

  /**
   * Queryオブジェクトを指定してポイントルールのリストを取得します。
   *
   * @param query Queryオブジェクト
   * @return 検索結果に相当するPointRuleのリスト
   */
  List<PointRule> findByQuery(Query query);

  /**
   * SQLを指定してポイントルールのリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するPointRuleのリスト
   */
  List<PointRule> findByQuery(String sqlString, Object... params);

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブルの全データ分のPointRuleのリスト
   */
  List<PointRule> loadAll();

}
