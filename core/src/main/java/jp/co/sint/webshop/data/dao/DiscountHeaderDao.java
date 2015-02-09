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
import jp.co.sint.webshop.data.dto.DiscountHeader;
import jp.co.sint.webshop.service.LoginInfo;

/**
 * 「限时限量折扣(DiscountHeader)」テーブルへのデータアクセスを担当するDAO(Data Access Object)です。
 * 
 * @author System Integrator Corp.
 */
public interface DiscountHeaderDao extends GenericDao<DiscountHeader, Long> {

  /**
   * 指定されたorm_rowidを持つギフトのインスタンスを取得します。
   * 
   * @param id
   *          対象のorm_rowid
   * @return idに対応するDiscountHeaderのインスタンス
   */
  DiscountHeader loadByRowid(Long id);

  /**
   * 主キー列の値を指定してギフトのインスタンスを取得します。
   * 
   * @param discountCode
   *          折扣编号
   * @return 主キー列の値に対応するDiscountHeaderのインスタンス
   */
  DiscountHeader load(String discountCode);

  /**
   * 主キー列の値を指定してギフトが既に存在するかどうかを返します。
   * 
   * @param discountCode
   *          折扣编号
   * @return 主キー列の値に対応するDiscountHeaderの行が存在すればtrue
   */
  boolean exists(String discountCode);

  /**
   * 新規DiscountHeaderをデータベースに追加します。
   * 
   * @param obj
   *          追加対象のDiscountHeader
   * @param loginInfo
   *          ログイン情報
   * @return 追加が成功した場合のorm_rowid値
   */
  Long insert(DiscountHeader obj, LoginInfo loginInfo);

  /**
   * ギフトを更新します。
   * 
   * @param obj
   *          更新対象のDiscountHeader
   * @param loginInfo
   *          ログイン情報
   */
  void update(DiscountHeader obj, LoginInfo loginInfo);

  /**
   * ギフトを削除します。
   * 
   * @param obj
   *          削除対象のDiscountHeader
   * @param loginInfo
   *          ログイン情報
   */
  void delete(DiscountHeader obj, LoginInfo loginInfo);

  /**
   * 主キー列の値を指定してギフトを削除します。
   * 
   * @param discountCode
   *          ギフトコード
   */
  void delete(String discountCode);

  /**
   * Queryオブジェクトを指定してギフトのリストを取得します。
   * 
   * @param query
   *          Queryオブジェクト
   * @return 検索結果に相当するDiscountHeaderのリスト
   */
  List<DiscountHeader> findByQuery(Query query);

  /**
   * SQLを指定してギフトのリストを取得します。
   * 
   * @param sqlString
   *          バインド変数(?)を含むSQL文字列
   * @param params
   *          バインド変数に与える値の配列
   * @return 検索結果に相当するDiscountHeaderのリスト
   */
  List<DiscountHeader> findByQuery(String sqlString, Object... params);

  /**
   * テーブルの全データを一括で取得します。
   * 
   * @return テーブルの全データ分のDiscountHeaderのリスト
   */
  List<DiscountHeader> loadAll();

}
