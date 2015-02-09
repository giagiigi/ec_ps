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
import jp.co.sint.webshop.data.dto.ImageUploadHistory;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 「商品ヘッダ(COMMODITY_HEADER)」テーブルへのデータアクセスを担当するDAO(Data Access Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public interface ImageUploadHistoryDao extends GenericDao<ImageUploadHistory, Long> {

  /**
   * 主キー列の値を指定して商品ヘッダのインスタンスを取得します。
   *
   * @param shopCode ショップコード
   * @param commodityCode 商品コード
   * @return 主キー列の値に対応するCommodityHeaderのインスタンス
   */
  ImageUploadHistory load(String shopCode, String skuCode);
  
  /**
   * 主キー列の値を指定して商品ヘッダのインスタンスを取得します。
   *
   * @param shopCode ショップコード
   * @param commodityCode 商品コード
   * @return 主キー列の値に対応するCommodityHeaderのインスタンス
   */
  ImageUploadHistory loadByCommodityCode(String shopCode, String commodityCode);

  /**
   * 主キー列の値を指定して商品ヘッダが既に存在するかどうかを返します。
   *
   * @param shopCode ショップコード
   * @param commodityCode 商品コード
   * @return 主キー列の値に対応するCommodityHeaderの行が存在すればtrue
   */
  boolean exists(String shopCode, String skuCode);

  /**
   * 新規CommodityHeaderをデータベースに追加します。
   *
   * @param obj 追加対象のCommodityHeader
   * @param loginInfo ログイン情報
   * @return 追加が成功した場合のorm_rowid値
   */
  Long insert(ImageUploadHistory obj, LoginInfo loginInfo);

  /**
   * 商品ヘッダを更新します。
   *
   * @param obj 更新対象のCommodityHeader
   * @param loginInfo ログイン情報
   */
  void update(ImageUploadHistory obj, LoginInfo loginInfo);

  /**
   * Queryオブジェクトを指定して商品ヘッダのリストを取得します。
   *
   * @param query Queryオブジェクト
   * @return 検索結果に相当するCommodityHeaderのリスト
   */
  List<ImageUploadHistory> findByQuery(Query query);

  /**
   * SQLを指定して商品ヘッダのリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するCommodityHeaderのリスト
   */
  List<ImageUploadHistory> findByQuery(String sqlString, Object... params);

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブルの全データ分のCommodityHeaderのリスト
   */
  List<ImageUploadHistory> loadAll();
  
  // 2014/05/02 京东WBS对应 ob_姚 add start
  int updateByQuery(String sql, Object... params);
  // 2014/05/02 京东WBS对应 ob_姚 add end
  

}
