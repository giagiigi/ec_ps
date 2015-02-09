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
import jp.co.sint.webshop.data.dto.ShippingHeader;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 「出荷ヘッダ(SHIPPING_HEADER)」テーブルへのデータアクセスを担当するDAO(Data Access Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public interface ShippingHeaderDao extends GenericDao<ShippingHeader, Long> {

  /**
   * 指定されたorm_rowidを持つ出荷ヘッダのインスタンスを取得します。
   *
   * @param id 対象のorm_rowid
   * @return idに対応するShippingHeaderのインスタンス
   */
  ShippingHeader loadByRowid(Long id);

  /**
   * 主キー列の値を指定して出荷ヘッダのインスタンスを取得します。
   *
   * @param shippingNo 出荷番号
   * @return 主キー列の値に対応するShippingHeaderのインスタンス
   */
  ShippingHeader load(String shippingNo);

  /**
   * 主キー列の値を指定して出荷ヘッダが既に存在するかどうかを返します。
   *
   * @param shippingNo 出荷番号
   * @return 主キー列の値に対応するShippingHeaderの行が存在すればtrue
   */
  boolean exists(String shippingNo);

  /**
   * 新規ShippingHeaderをデータベースに追加します。
   *
   * @param obj 追加対象のShippingHeader
   * @param loginInfo ログイン情報
   * @return 追加が成功した場合のorm_rowid値
   */
  Long insert(ShippingHeader obj, LoginInfo loginInfo);

  /**
   * 出荷ヘッダを更新します。
   *
   * @param obj 更新対象のShippingHeader
   * @param loginInfo ログイン情報
   */
  void update(ShippingHeader obj, LoginInfo loginInfo);

  /**
   * 出荷ヘッダを削除します。
   *
   * @param obj 削除対象のShippingHeader
   * @param loginInfo ログイン情報
   */
  void delete(ShippingHeader obj, LoginInfo loginInfo);

  /**
   * 主キー列の値を指定して出荷ヘッダを削除します。
   *
   * @param shippingNo 出荷番号
   */
  void delete(String shippingNo);

  /**
   * Queryオブジェクトを指定して出荷ヘッダのリストを取得します。
   *
   * @param query Queryオブジェクト
   * @return 検索結果に相当するShippingHeaderのリスト
   */
  List<ShippingHeader> findByQuery(Query query);

  /**
   * SQLを指定して出荷ヘッダのリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するShippingHeaderのリスト
   */
  List<ShippingHeader> findByQuery(String sqlString, Object... params);

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブルの全データ分のShippingHeaderのリスト
   */
  List<ShippingHeader> loadAll();
  
  
  /**
   * 主キー列の値を指定して出荷ヘッダのインスタンスを取得します。
   *
   * @param orderNo 受注番号
   * @return 主キー列の値に対応するShippingHeaderのインスタンス
   */
  ShippingHeader loadByOrderNo(String orderNo);

}
