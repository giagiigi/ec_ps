//
//All rights reserved.
//
// このファイルはEDMファイルから自動生成されます。
// 直接編集しないで下さい。
//
package jp.co.sint.webshop.data.dao;

import java.util.List;

import jp.co.sint.webshop.data.GenericDao;
import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.dto.ReturnHeader;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 「退换货header」テーブルへのデータアクセスを担当するDAO(Data Access Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public interface ReturnHeaderDao extends GenericDao<ReturnHeader, Long> {

  /**
   * 指定されたorm_rowidを持つギフトのインスタンスを取得します。
   *
   * @param id 対象のorm_rowid
   * @return idに対応するReturnHeaderのインスタンス
   */
  ReturnHeader loadByRowid(Long id);

  /**
   * 主キー列の値を指定してギフトのインスタンスを取得します。
   *
   * @param returnNo 退换货コード
   * @return 主キー列の値に対応するReturnHeaderのインスタンス
   */
  ReturnHeader load(String returnNo);
  
  /**
   * 获取退换货Header信息集合。
   *
   * @param orderNo, cancelFlg
   * @return 退换货Header信息集合
   */
  List<ReturnHeader> load(String orderNo, String cancelFlg);

  /**
   * 主キー列の値を指定して退换货信息が既に存在するかどうかを返します。
   *
   * @param returnNo 退换货コード
   * @return 主キー列の値に対応するReturnHeaderの行が存在すればtrue
   */
  boolean exists(String returnNo);

  /**
   * 新規ReturnHeaderをデータベースに追加します。
   *
   * @param obj 追加対象のReturnHeader
   * @param loginInfo ログイン情報
   * @return 追加が成功した場合のorm_rowid値
   */
  Long insert(ReturnHeader obj, LoginInfo loginInfo);

  /**
   * ReturnHeaderを更新します。
   *
   * @param obj 更新対象のReturnHeader
   * @param loginInfo ログイン情報
   */
  void update(ReturnHeader obj, LoginInfo loginInfo);

  /**
   * 退换货信息を削除します。
   *
   * @param obj 削除対象のReturnHeader
   * @param loginInfo ログイン情報
   */
  void delete(ReturnHeader obj, LoginInfo loginInfo);

  /**
   * 主キー列の値を指定して退换货信息を削除します。
   *
   * @param returnNo 退换货コード
   */
  void delete(String returnNo);

  /**
   * Queryオブジェクトを指定して退换货信息のリストを取得します。
   *
   * @param query Queryオブジェクト
   * @return 検索結果に相当するReturnHeaderのリスト
   */
  List<ReturnHeader> findByQuery(Query query);

  /**
   * SQLを指定して退换货信息のリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するReturnHeaderのリスト
   */
  List<ReturnHeader> findByQuery(String sqlString, Object... params);

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブルの全データ分のReturnHeaderのリスト
   */
  List<ReturnHeader> loadAll();
  

}
