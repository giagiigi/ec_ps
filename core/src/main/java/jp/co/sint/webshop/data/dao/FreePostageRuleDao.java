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
import jp.co.sint.webshop.data.dto.FreePostageRule;
import jp.co.sint.webshop.service.LoginInfo;

/**
 * 「免邮促销(FreePostageRuleDao)」テーブルへのデータアクセスを担当するDAO(Data Access Object)です。
 * 
 * @author Kousen.
 */
public interface FreePostageRuleDao extends GenericDao<FreePostageRule, Long> {

  /**
   * 指定されたorm_rowidを持つ免邮促销のインスタンスを取得します。
   * 
   * @param id
   *          対象のorm_rowid
   * @return idに対応するFreePostageRuleのインスタンス
   */
  FreePostageRule loadByRowid(Long id);

  /**
   * 主キー列の値を指定して免邮促销のインスタンスを取得します。
   * 
   * @param freePostageRuleCode
   *          免邮促销编号
   * @return 主キー列の値に対応するFreePostageRuleのインスタンス
   */
  FreePostageRule load(String freePostageRuleCode);

  /**
   * 主キー列の値を指定して免邮促销が既に存在するかどうかを返します。
   * 
   * @param freePostageRuleCode
   *          免邮促销编号
   * @return 主キー列の値に対応するFreePostageRuleの行が存在すればtrue
   */
  boolean exists(String freePostageRuleCode);

  /**
   * 新規FreePostageRuleをデータベースに追加します。
   * 
   * @param obj
   *          追加対象のFreePostageRule
   * @param loginInfo
   *          ログイン情報
   * @return 追加が成功した場合のorm_rowid値
   */
  Long insert(FreePostageRule obj, LoginInfo loginInfo);

  /**
   * 免邮促销を更新します。
   * 
   * @param obj
   *          更新対象のFreePostageRule
   * @param loginInfo
   *          ログイン情報
   */
  void update(FreePostageRule obj, LoginInfo loginInfo);

  /**
   * 免邮促销を削除します。
   * 
   * @param obj
   *          削除対象のFreePostageRule
   * @param loginInfo
   *          ログイン情報
   */
  void delete(FreePostageRule obj, LoginInfo loginInfo);

  /**
   * 主キー列の値を指定して免邮促销を削除します。
   * 
   * @param freePostageRuleCode
   *          免邮促销编号
   */
  void delete(String freePostageRuleCode);

  /**
   * Queryオブジェクトを指定して免邮促销のリストを取得します。
   * 
   * @param query
   *          Queryオブジェクト
   * @return 検索結果に相当するFreePostageRuleのリスト
   */
  List<FreePostageRule> findByQuery(Query query);

  /**
   * SQLを指定して免邮促销のリストを取得します。
   * 
   * @param sqlString
   *          バインド変数(?)を含むSQL文字列
   * @param params
   *          バインド変数に与える値の配列
   * @return 検索結果に相当するFreePostageRuleのリスト
   */
  List<FreePostageRule> findByQuery(String sqlString, Object... params);

  /**
   * テーブルの全データを一括で取得します。
   * 
   * @return テーブルの全データ分のFreePostageRuleのリスト
   */
  List<FreePostageRule> loadAll();

}
