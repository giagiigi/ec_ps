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
import jp.co.sint.webshop.data.dto.CCommodityHeader;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 「商品ヘッダ(COMMODITY_HEADER)」テーブルへのデータアクセスを担当するDAO(Data Access Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public interface CCommodityHeaderDao extends GenericDao<CCommodityHeader, Long> {

  /**
   * 指定されたorm_rowidを持つ商品ヘッダのインスタンスを取得します。
   *
   * @param id 対象のorm_rowid
   * @return idに対応するCommodityHeaderのインスタンス
   */
  CCommodityHeader loadByRowid(Long id);

  /**
   * 主キー列の値を指定して商品ヘッダのインスタンスを取得します。
   *
   * @param shopCode ショップコード
   * @param commodityCode 商品コード
   * @return 主キー列の値に対応するCommodityHeaderのインスタンス
   */
  CCommodityHeader load(String shopCode, String commodityCode);

  /**
   * 主キー列の値を指定して商品ヘッダが既に存在するかどうかを返します。
   *
   * @param shopCode ショップコード
   * @param commodityCode 商品コード
   * @return 主キー列の値に対応するCommodityHeaderの行が存在すればtrue
   */
  boolean exists(String shopCode, String commodityCode);

  /**
   * 新規CommodityHeaderをデータベースに追加します。
   *
   * @param obj 追加対象のCommodityHeader
   * @param loginInfo ログイン情報
   * @return 追加が成功した場合のorm_rowid値
   */
  Long insert(CCommodityHeader obj, LoginInfo loginInfo);

  /**
   * 商品ヘッダを更新します。
   *
   * @param obj 更新対象のCommodityHeader
   * @param loginInfo ログイン情報
   */
  void update(CCommodityHeader obj, LoginInfo loginInfo);

  /**
   * 商品ヘッダを削除します。
   *
   * @param obj 削除対象のCommodityHeader
   * @param loginInfo ログイン情報
   */
  void delete(CCommodityHeader obj, LoginInfo loginInfo);

  /**
   * 主キー列の値を指定して商品ヘッダを削除します。
   *
   * @param shopCode ショップコード
   * @param commodityCode 商品コード
   */
  void delete(String shopCode, String commodityCode);

  /**
   * Queryオブジェクトを指定して商品ヘッダのリストを取得します。
   *
   * @param query Queryオブジェクト
   * @return 検索結果に相当するCommodityHeaderのリスト
   */
  List<CCommodityHeader> findByQuery(Query query);

  /**
   * SQLを指定して商品ヘッダのリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するCommodityHeaderのリスト
   */
  List<CCommodityHeader> findByQuery(String sqlString, Object... params);

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブルの全データ分のCommodityHeaderのリスト
   */
  List<CCommodityHeader> loadAll();
  
  int updateByQuery(String sql,Object... params);

}
