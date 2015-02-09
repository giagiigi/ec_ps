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
import jp.co.sint.webshop.data.dto.TmallCommodityProperty;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 「受注ヘッダ(ORDER_HEADER)」テーブルへのデータアクセスを担当するDAO(Data Access Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public interface TmallCommodityPropertyDao extends GenericDao<TmallCommodityProperty, Long> {

  /**
   * 指定されたorm_rowidを持つ受注ヘッダのインスタンスを取得します。
   *
   * @param id 対象のorm_rowid
   * @return idに対応するTmallCommodityPropertyのインスタンス
   */
  TmallCommodityProperty loadByRowid(Long id);

  /**
   * 主キー列の値を指定して受注ヘッダのインスタンスを取得します。
   *
   * @param orderNo 受注番号
   * @return 主キー列の値に対応するTmallCommodityPropertyのインスタンス
   */
  TmallCommodityProperty load(String commodityCode,String propertyId,String valueId);
  /**
   * 主キー列の値を指定して受注ヘッダが既に存在するかどうかを返します。
   *
   * @param orderNo 受注番号
   * @return 主キー列の値に対応するTmallCommodityPropertyの行が存在すればtrue
   */
  boolean exists(String commodityCode,String propertyId,String valueId);
  /**
   * 新規OrderHeaderをデータベースに追加します。
   *
   * @param obj 追加対象のTmallCommodityProperty
   * @param loginInfo ログイン情報
   * @return 追加が成功した場合のorm_rowid値
   */
  Long insert(TmallCommodityProperty obj, LoginInfo loginInfo);

  /**
   * 受注ヘッダを更新します。
   *
   * @param obj 更新対象のTmallCommodityProperty
   * @param loginInfo ログイン情報
   */
  void update(TmallCommodityProperty obj, LoginInfo loginInfo);

  /**
   * 受注ヘッダを削除します。
   *
   * @param obj 削除対象のTmallCommodityProperty
   * @param loginInfo ログイン情報
   */
  void delete(TmallCommodityProperty obj, LoginInfo loginInfo);

  /**
   * 主キー列の値を指定して受注ヘッダを削除します。
   *
   * @param orderNo 受注番号
   */
  void delete(String commodityCode,String propertyId,String valueId);

  /**
   * Queryオブジェクトを指定して受注ヘッダのリストを取得します。
   *
   * @param query Queryオブジェクト
   * @return 検索結果に相当するTmallCommodityPropertyのリスト
   */
  List<TmallCommodityProperty> findByQuery(Query query);

  /**
   * SQLを指定して受注ヘッダのリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するTmallCommodityPropertyのリスト
   */
  List<TmallCommodityProperty> findByQuery(String sqlString, Object... params);

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブルの全データ分のTmallCommodityPropertyのリスト
   */
  List<TmallCommodityProperty> loadAll();
  /**
   * テーブルの全データを一括で取得します。
   * @return テーブルの全データ分のTmallCommodityPropertyのリスト
   */
  List<TmallCommodityProperty> loadByCommodityCode(String commodityId);
  /**
   * 查询商品自定义的属性
   */
  List<TmallCommodityProperty> loadInputProByCommodityCode(String commodityId);

}
