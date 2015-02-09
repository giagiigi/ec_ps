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
import jp.co.sint.webshop.data.dto.OnlineService;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 「(OnlineService)」テーブルへのデータアクセスを担当するDAO(Data Access Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public interface OnlineServiceDao extends GenericDao<OnlineService, Long> {

  /**
   * 指定されたorm_rowidを持つのインスタンスを取得します。
   *
   * @param id 対象のorm_rowid
   * @return idに対応するOnlineServiceのインスタンス
   */
  OnlineService loadByRowid(Long id);

  /**
   * 主キー列の値を指定してポイントルールのインスタンスを取得します。
   *
   * @param OnlineServiceNo 
   * @return 主キー列の値に対応するOnlineServiceのインスタンス
   */
  OnlineService load(Long pointRuleNo);

  /**
   * 主キー列の値を指定してポイントルールが既に存在するかどうかを返します。
   *
   * @param OnlineServiceNo 
   * @return 主キー列の値に対応するOnlineServiceの行が存在すればtrue
   */
  boolean exists(Long pointRuleNo);

  /**
   * 新規PointRuleをデータベースに追加します。
   *
   * @param obj 追加対象のOnlineService
   * @param loginInfo ログイン情報
   * @return 追加が成功した場合のorm_rowid値
   */
  Long insert(OnlineService obj, LoginInfo loginInfo);
  

  /**
   * OnlineServiceを更新します。
   *
   * @param obj 更新対象のOnlineService
   * @param loginInfo ログイン情報
   */
  void update(OnlineService obj, LoginInfo loginInfo);

  /**
   * OnlineServiceを削除します。
   *
   * @param obj 削除対象のOnlineService
   * @param loginInfo ログイン情報
   */
  void delete(OnlineService obj, LoginInfo loginInfo);

  /**
   * 主キー列の値を指定してポイントルールを削除します。
   *
   * @param OnlineServiceNo 
   */
  void delete(Long onlineServiceNo);

  /**
   * Queryオブジェクトを指定してのリストを取得します。
   *
   * @param query Queryオブジェクト
   * @return 検索結果に相当するOnlineServiceのリスト
   */
  List<OnlineService> findByQuery(Query query);

  /**
   * SQLを指定してポイントルールのリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するOnlineServiceのリスト
   */
  List<OnlineService> findByQuery(String sqlString, Object... params);
  
  /**
   * テーブルの全データを一括で取得します。
   * @return テーブルの全データ分のOnlineServiceのリスト
   */
  List<OnlineService> loadAll();
  
  /**
   * 
   * 检索shopCode对应项
   */
  boolean isHaveOnline(String shopCode);
}
