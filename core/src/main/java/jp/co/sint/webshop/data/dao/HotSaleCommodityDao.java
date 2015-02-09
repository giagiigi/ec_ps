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
import jp.co.sint.webshop.data.dto.HotSaleCommodity;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 「商品詳細(COMMODITY_DETAIL)」テーブルへのデータアクセスを担当するDAO(Data Access Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public interface HotSaleCommodityDao extends GenericDao<HotSaleCommodity, Long> {

  /**
   * 指定されたorm_rowidを持つ商品詳細のインスタンスを取得します。
   *
   * @param id 対象のorm_rowid
   * @return idに対応するCommodityDetailのインスタンス
   */
  HotSaleCommodity loadByRowid(Long id);

  /**
   * 主キー列の値を指定して商品詳細のインスタンスを取得します。
   *
   * @param shopCode ショップコード
   * @param skuCode SKUコード
   * @return 主キー列の値に対応するCommodityDetailのインスタンス
   */
  HotSaleCommodity load(String commodityCode,String languageCode);
  
  List<HotSaleCommodity> loadByLanguageCode(String languageCode);

  /**
   * 主キー列の値を指定して商品詳細が既に存在するかどうかを返します。
   *
   * @param shopCode ショップコード
   * @param skuCode SKUコード
   * @return 主キー列の値に対応するCommodityDetailの行が存在すればtrue
   */
  boolean exists(String commodityCode,String languageCode);

  /**
   * 新規CommodityDetailをデータベースに追加します。
   *
   * @param obj 追加対象のCommodityDetail
   * @param loginInfo ログイン情報
   * @return 追加が成功した場合のorm_rowid値
   */
  Long insert(HotSaleCommodity obj, LoginInfo loginInfo);

  /**
   * 商品詳細を更新します。
   *
   * @param obj 更新対象のCommodityDetail
   * @param loginInfo ログイン情報
   */
  void update(HotSaleCommodity obj, LoginInfo loginInfo);

  /**
   * 商品詳細を削除します。
   *
   * @param obj 削除対象のCommodityDetail
   * @param loginInfo ログイン情報
   */
  void delete(HotSaleCommodity obj, LoginInfo loginInfo);

  /**
   * 主キー列の値を指定して商品詳細を削除します。
   *
   * @param shopCode ショップコード
   * @param skuCode SKUコード
   */
  void delete(String commodityCode,String languageCode);

  /**
   * Queryオブジェクトを指定して商品詳細のリストを取得します。
   *
   * @param query Queryオブジェクト
   * @return 検索結果に相当するCommodityDetailのリスト
   */
  List<HotSaleCommodity> findByQuery(Query query);

  /**
   * SQLを指定して商品詳細のリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するCommodityDetailのリスト
   */
  List<HotSaleCommodity> findByQuery(String sqlString, Object... params);

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブルの全データ分のCommodityDetailのリスト
   */
  List<HotSaleCommodity> loadAll();

}
