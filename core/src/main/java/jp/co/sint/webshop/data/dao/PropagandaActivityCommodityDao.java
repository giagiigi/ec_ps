//
//Copyright(C) 2007-2008 System Integrator Corp.
//All rights reserved.
//
package jp.co.sint.webshop.data.dao;

import java.util.List;

import jp.co.sint.webshop.data.GenericDao;
import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.dto.PropagandaActivityCommodity;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 「宣传品活动关联商品(propaganda_activity_commodity)」テーブルへのデータアクセスを担当するDAO(Data Access Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public interface PropagandaActivityCommodityDao extends GenericDao<PropagandaActivityCommodity, Long> {

  /**
   * 指定されたorm_rowidを持つ宣传品活动关联商品のインスタンスを取得します。
   *
   * @param id 対象のorm_rowid
   * @return idに対応するPropagandaActivityCommodityのインスタンス
   */
  PropagandaActivityCommodity loadByRowid(Long id);

  /**
   * 主キー列の値を指定して宣传品活动关联商品のインスタンスを取得します。
   *
   * @param activityCode 活动编号
   * @param commodityCode 商品编号
   * @return 主キー列の値に対応するPropagandaActivityCommodityのインスタンス
   */
  PropagandaActivityCommodity load(String activityCode, String commodityCode);

  /**
   * 主キー列の値を指定して宣传品活动关联商品が既に存在するかどうかを返します。
   *
   * @param activityCode 活动编号
   * @param commodityCode 商品编号
   * @return 主キー列の値に対応するPropagandaActivityCommodityの行が存在すればtrue
   */
  boolean exists(String activityCode, String commodityCode);

  /**
   * 新規PropagandaActivityCommodityをデータベースに追加します。
   *
   * @param obj 追加対象のPropagandaActivityCommodity
   * @param loginInfo ログイン情報
   * @return 追加が成功した場合のorm_rowid値
   */
  Long insert(PropagandaActivityCommodity obj, LoginInfo loginInfo);

  /**
   * 宣传品活动关联商品を更新します。
   *
   * @param obj 更新対象のPropagandaActivityCommodity
   * @param loginInfo ログイン情報
   */
  void update(PropagandaActivityCommodity obj, LoginInfo loginInfo);

  /**
   * 宣传品活动关联商品を削除します。
   *
   * @param obj 削除対象のPropagandaActivityCommodity
   * @param loginInfo ログイン情報
   */
  void delete(PropagandaActivityCommodity obj, LoginInfo loginInfo);

  /**
   * 主キー列の値を指定して宣传品活动关联商品を削除します。
   *
   * @param activityCode 活动编号
   * @param commodityCode 商品编号
   */
  void delete(String activityCode, String commodityCode);

  /**
   * Queryオブジェクトを指定して宣传品活动关联商品のリストを取得します。
   *
   * @param query Queryオブジェクト
   * @return 検索結果に相当するPropagandaActivityCommodityのリスト
   */
  List<PropagandaActivityCommodity> findByQuery(Query query);

  /**
   * SQLを指定して宣传品活动关联商品のリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するPropagandaActivityCommodityのリスト
   */
  List<PropagandaActivityCommodity> findByQuery(String sqlString, Object... params);

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブルの全データ分のPropagandaActivityCommodityのリスト
   */
  List<PropagandaActivityCommodity> loadAll();

}
