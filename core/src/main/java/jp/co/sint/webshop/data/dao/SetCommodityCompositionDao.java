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
import jp.co.sint.webshop.data.dto.SetCommodityComposition;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 「套餐商品明细(SET_COMMODITY_COMPOSITION)」テーブルへのデータアクセスを担当するDAO(Data Access Object)です。
 *
 * @author KS.
 *
 */
public interface SetCommodityCompositionDao extends GenericDao<SetCommodityComposition, Long> {

  /**
   * 指定されたorm_rowidを持つ商品套餐商品明细のインスタンスを取得します。
   *
   * @param id 対象のorm_rowid
   * @return idに対応するchildCommodityCodeのインスタンス
   */
	SetCommodityComposition loadByRowid(Long id);

  /**
   * 主キー列の値を指定して套餐商品明细のインスタンスを取得します。
   *
   * @param shopCode ショップコード
   * @param commodityCode 商品コード
   * @return 主キー列の値に対応するSetCommodityCompositionのインスタンス
   */
	List<SetCommodityComposition> load(String shopCode, String commodityCode);
	
	List<SetCommodityComposition> loadChild(String shopCode, String childCommodityCode);

  /**
   * 主キー列の値を指定して套餐商品明细が既に存在するかどうかを返します。
   *
   * @param shopCode ショップコード
   * @param commodityCode 商品コード
   * @param childCommodityCode 子商品コード
   * @return 主キー列の値に対応するSetCommodityCompositionの行が存在すればtrue
   */
  boolean exists(String shopCode, String commodityCode, String childCommodityCode);

  /**
   * 新規SetCommodityCompositionをデータベースに追加します。
   *
   * @param obj 追加対象のSetCommodityComposition
   * @param loginInfo ログイン情報
   * @return 追加が成功した場合のorm_rowid値
   */
  Long insert(SetCommodityComposition obj, LoginInfo loginInfo);

  /**
   * 套餐商品明细を更新します。
   *
   * @param obj 更新対象のSetCommodityComposition
   * @param loginInfo ログイン情報
   */
  void update(SetCommodityComposition obj, LoginInfo loginInfo);

  /**
   * 套餐商品明细を削除します。
   *
   * @param obj 削除対象のSetCommodityComposition
   * @param loginInfo ログイン情報
   */
  void delete(SetCommodityComposition obj, LoginInfo loginInfo);

  /**
   * 主キー列の値を指定して套餐商品明细を削除します。
   *
   * @param shopCode ショップコード
   * @param commodityCode 商品コード
   * @param childCommodityCode 子商品コード
   */
  void delete(String shopCode, String commodityCode, String childCommodityCode);

  /**
   * Queryオブジェクトを指定して套餐商品明细のリストを取得します。
   *
   * @param query Queryオブジェクト
   * @return 検索結果に相当するSetCommodityCompositionのリスト
   */
  List<SetCommodityComposition> findByQuery(Query query);

  /**
   * SQLを指定して套餐商品明细のリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するSetCommodityCompositionのリスト
   */
  List<SetCommodityComposition> findByQuery(String sqlString, Object... params);

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブルの全データ分のSetCommodityCompositionのリスト
   */
  List<SetCommodityComposition> loadAll();
  
  long updateByQuery(String sql,Object... params);

  /**
   * 主キー列の値を指定して套餐商品明细のインスタンスを取得します。
   *
   * @param shopCode ショップコード
   * @param commodityCode 商品コード
   * @param childCommodityCode 子商品コード
   * @return 主キー列の値に対応するSetCommodityCompositionのインスタンス
   */
	SetCommodityComposition load(String shopCode, String commodityCode, String childCommodityCode);
}
