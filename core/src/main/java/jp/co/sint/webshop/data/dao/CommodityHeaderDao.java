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
import jp.co.sint.webshop.data.dto.CommodityHeader;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 「商品ヘッダ(COMMODITY_HEADER)」テーブルへのデータアクセスを担当するDAO(Data Access Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public interface CommodityHeaderDao extends GenericDao<CommodityHeader, Long> {

  /**
   * 指定されたorm_rowidを持つ商品ヘッダのインスタンスを取得します。
   *
   * @param id 対象のorm_rowid
   * @return idに対応するCommodityHeaderのインスタンス
   */
  CommodityHeader loadByRowid(Long id);

  /**
   * 主キー列の値を指定して商品ヘッダのインスタンスを取得します。
   *
   * @param shopCode ショップコード
   * @param commodityCode 商品コード
   * @return 主キー列の値に対応するCommodityHeaderのインスタンス
   */
  CommodityHeader load(String shopCode, String commodityCode);

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
  Long insert(CommodityHeader obj, LoginInfo loginInfo);

  /**
   * 商品ヘッダを更新します。
   *
   * @param obj 更新対象のCommodityHeader
   * @param loginInfo ログイン情報
   */
  void update(CommodityHeader obj, LoginInfo loginInfo);

  /**
   * 商品ヘッダを削除します。
   *
   * @param obj 削除対象のCommodityHeader
   * @param loginInfo ログイン情報
   */
  void delete(CommodityHeader obj, LoginInfo loginInfo);

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
  List<CommodityHeader> findByQuery(Query query);

  /**
   * SQLを指定して商品ヘッダのリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するCommodityHeaderのリスト
   */
  List<CommodityHeader> findByQuery(String sqlString, Object... params);

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブルの全データ分のCommodityHeaderのリスト
   */
  List<CommodityHeader> loadAll();
  
  long updateByQuery(String sql,Object... params);
  
  //20120204 ob add start
  /*
   * 判断sku编号是不是代表sku
   */
  boolean isRepresentSkuCode(String shopCode, String skuCode);
  
  /*
   * 通过代表SKU取得商品header
   */
  CommodityHeader loadByRepSku(String shopCode, String RepSku);
  //20120204 ob add end
  
//20121120 促销对应 ob add start
  CommodityHeader loadBycommodityCode(String shopCode, String commodityCode,boolean flag,boolean resultFlg);
//20121120 促销对应 ob add end
}
