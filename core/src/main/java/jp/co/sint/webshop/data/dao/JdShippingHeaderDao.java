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
import jp.co.sint.webshop.data.dto.JdShippingHeader;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 「出荷ヘッダ(Tmall_SHIPPING_HEADER)」テーブルへのデータアクセスを担当するDAO(Data Access Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public interface JdShippingHeaderDao extends GenericDao<JdShippingHeader, Long> {

  /**
   * 指定されたorm_rowidを持つ出荷ヘッダのインスタンスを取得します。
   *
   * @param id 対象のorm_rowid
   * @return idに対応するShippingHeaderのインスタンス
   */
  JdShippingHeader loadByRowid(Long id);

  /**
   * 主キー列の値を指定して出荷ヘッダのインスタンスを取得します。
   *
   * @param shippingNo 出荷番号
   * @return 主キー列の値に対応するTmallShippingHeaderのインスタンス
   */
  JdShippingHeader load(String shippingNo);

  /**
   * 主キー列の値を指定して出荷ヘッダのインスタンスを取得します。
   *
   * @param orderNo 出荷番号
   * @return 主キー列の値に対応するTmallShippingHeaderのインスタンス
   */
  JdShippingHeader loadByOrderNo(String orderNo);
  /**
   * 主キー列の値を指定して出荷ヘッダが既に存在するかどうかを返します。
   *
   * @param shippingNo 出荷番号
   * @return 主キー列の値に対応するTmallShippingHeaderの行が存在すればtrue
   */
  boolean exists(String shippingNo);

  /**
   * 新規TmallShippingHeaderをデータベースに追加します。
   *
   * @param obj 追加対象のTmallShippingHeader
   * @param loginInfo ログイン情報
   * @return 追加が成功した場合のorm_rowid値
   */
  Long insert(JdShippingHeader obj, LoginInfo loginInfo);

  /**
   * 出荷ヘッダを更新します。
   *
   * @param obj 更新対象のTmallShippingHeader
   * @param loginInfo ログイン情報
   */
  void update(JdShippingHeader obj, LoginInfo loginInfo);

  /**
   * 出荷ヘッダを削除します。
   *
   * @param obj 削除対象のTmallShippingHeader
   * @param loginInfo ログイン情報
   */
  void delete(JdShippingHeader obj, LoginInfo loginInfo);

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
   * @return 検索結果に相当するTmallShippingHeaderのリスト
   */
  List<JdShippingHeader> findByQuery(Query query);

  /**
   * SQLを指定して出荷ヘッダのリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するTmallShippingHeaderのリスト
   */
  List<JdShippingHeader> findByQuery(String sqlString, Object... params);

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブルの全データ分のTmallShippingHeaderのリスト
   */
  List<JdShippingHeader> loadAll();

}
