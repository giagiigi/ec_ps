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
import jp.co.sint.webshop.data.dto.DiscountCommodity;
import jp.co.sint.webshop.service.LoginInfo;

/**
 * 「限时限量折扣关联商品(DiscountCommodity)」テーブルへのデータアクセスを担当するDAO(Data Access Object)です。
 * 
 * @author System Integrator Corp.
 */
public interface DiscountCommodityDao extends GenericDao<DiscountCommodity, Long> {

  /**
   * 指定されたorm_rowidを持つキャンペーン対象商品のインスタンスを取得します。
   * 
   * @param id
   *          対象のorm_rowid
   * @return idに対応するDiscountCommodityのインスタンス
   */
  DiscountCommodity loadByRowid(Long id);

  /**
   * 主キー列の値を指定してキャンペーン対象商品のインスタンスを取得します。
   * 
   * @param discountCode
   *          折扣编号
   * @param commodityCode
   *          商品コード
   * @return 主キー列の値に対応するDiscountCommodityのインスタンス
   */
  DiscountCommodity load(String discountCode, String commodityCode);

  /**
   * 主キー列の値を指定してキャンペーン対象商品が既に存在するかどうかを返します。
   * 
   * @param discountCode
   *          折扣编号
   * @param commodityCode
   *          商品コード
   * @return 主キー列の値に対応するDiscountCommodityの行が存在すればtrue
   */
  boolean exists(String discountCode, String commodityCode);

  /**
   * 新規DiscountCommodityをデータベースに追加します。
   * 
   * @param obj
   *          追加対象のDiscountCommodity
   * @param loginInfo
   *          ログイン情報
   * @return 追加が成功した場合のorm_rowid値
   */
  Long insert(DiscountCommodity obj, LoginInfo loginInfo);

  /**
   * キャンペーン対象商品を更新します。
   * 
   * @param obj
   *          更新対象のDiscountCommodity
   * @param loginInfo
   *          ログイン情報
   */
  void update(DiscountCommodity obj, LoginInfo loginInfo);

  /**
   * キャンペーン対象商品を削除します。
   * 
   * @param obj
   *          削除対象のDiscountCommodity
   * @param loginInfo
   *          ログイン情報
   */
  void delete(DiscountCommodity obj, LoginInfo loginInfo);

  /**
   * 主キー列の値を指定してキャンペーン対象商品を削除します。
   * 
   * @param discountCode
   *          折扣编号
   * @param commodityCode
   *          商品コード
   */
  void delete(String discountCode, String commodityCode);

  /**
   * Queryオブジェクトを指定してキャンペーン対象商品のリストを取得します。
   * 
   * @param query
   *          Queryオブジェクト
   * @return 検索結果に相当するDiscountCommodityのリスト
   */
  List<DiscountCommodity> findByQuery(Query query);

  /**
   * SQLを指定してキャンペーン対象商品のリストを取得します。
   * 
   * @param sqlString
   *          バインド変数(?)を含むSQL文字列
   * @param params
   *          バインド変数に与える値の配列
   * @return 検索結果に相当するDiscountCommodityのリスト
   */
  List<DiscountCommodity> findByQuery(String sqlString, Object... params);

  /**
   * テーブルの全データを一括で取得します。
   * 
   * @return テーブルの全データ分のDiscountCommodityのリスト
   */
  List<DiscountCommodity> loadAll();

}
