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
import jp.co.sint.webshop.data.dto.FriendCouponRule;
import jp.co.sint.webshop.service.LoginInfo;

/**
 * 「友達紹介ｸｰﾎﾟﾝの発行ルール(friend_coupon_rule)」テーブルへのデータアクセスを担当するDAO(Data Access Object)です。
 * 
 * @author System Integrator Corp.
 */
public interface FriendCouponRuleDao extends GenericDao<FriendCouponRule, Long> {

  /**
   * 指定されたorm_rowidを持つ友達紹介ｸｰﾎﾟﾝの発行ルールのインスタンスを取得します。
   * 
   * @param id
   *          対象のorm_rowid
   * @return idに対応するFriendCouponRuleのインスタンス
   */
  FriendCouponRule loadByRowid(Long id);

  /**
   * 主キー列の値を指定して友達紹介ｸｰﾎﾟﾝの発行ルールのインスタンスを取得します。
   * 
   * @param friendCouponRuleNo
   * @return 主キー列の値に対応するFriendCouponRuleのインスタンス
   */
  FriendCouponRule load(String friendCouponRuleNo);

  /**
   * 主キー列の値を指定して友達紹介ｸｰﾎﾟﾝの発行ルールが既に存在するかどうかを返します。
   * @param friendCouponRuleNo
   * @return 主キー列の値に対応するFriendCouponRuleの行が存在すればtrue
   */
  boolean exists(String friendCouponRuleNo);

  /**
   * 新規FriendCouponRuleをデータベースに追加します。
   * 
   * @param obj
   *          追加対象のFriendCouponRule
   * @param loginInfo
   *          ログイン情報
   * @return 追加が成功した場合のorm_rowid値
   */
  Long insert(FriendCouponRule obj, LoginInfo loginInfo);

  /**
   * 友達紹介ｸｰﾎﾟﾝの発行ルールを更新します。
   * 
   * @param obj
   *          更新対象のFriendCouponRule
   * @param loginInfo
   *          ログイン情報
   */
  void update(FriendCouponRule obj, LoginInfo loginInfo);

  /**
   * 友達紹介ｸｰﾎﾟﾝの発行ルールを削除します。
   * 
   * @param obj
   *          削除対象のFriendCouponRule
   * @param loginInfo
   *          ログイン情報
   */
  void delete(FriendCouponRule obj, LoginInfo loginInfo);

  /**
   * 主キー列の値を指定して友達紹介ｸｰﾎﾟﾝの発行ルールを削除します。
   * 
   * @param friendCouponRuleNo
   */
  void delete(String friendCouponRuleNo);

  /**
   * Queryオブジェクトを指定して友達紹介ｸｰﾎﾟﾝの発行ルールのリストを取得します。
   * 
   * @param query
   *          Queryオブジェクト
   * @return 検索結果に相当するFriendCouponRuleのリスト
   */
  List<FriendCouponRule> findByQuery(Query query);

  /**
   * SQLを指定して友達紹介ｸｰﾎﾟﾝの発行ルールのリストを取得します。
   * 
   * @param sqlString
   *          バインド変数(?)を含むSQL文字列
   * @param params
   *          バインド変数に与える値の配列
   * @return 検索結果に相当するFriendCouponRuleのリスト
   */
  List<FriendCouponRule> findByQuery(String sqlString, Object... params);

  /**
   * テーブルの全データを一括で取得します。
   * 
   * @return テーブルの全データ分のFriendCouponRuleのリスト
   */
  List<FriendCouponRule> loadAll();

}
