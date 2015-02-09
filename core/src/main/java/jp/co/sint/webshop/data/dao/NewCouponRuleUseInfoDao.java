package jp.co.sint.webshop.data.dao;

import java.util.List;

import jp.co.sint.webshop.data.GenericDao;
import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.dto.NewCouponRuleUseInfo;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 「优惠券规则_使用关联信息」テーブルへのデータアクセスを担当するDAO(Data Access Object)です。
 *
 * @author ob
 *
 */
public interface NewCouponRuleUseInfoDao extends GenericDao<NewCouponRuleUseInfo, Long> {

  /**
   * 指定されたorm_rowidを持つ优惠券规则_使用关联信息のインスタンスを取得します。
   *
   * @param id 対象のorm_rowid
   * @return idに対応する优惠券规则_使用关联信息のインスタンス
   */
  NewCouponRuleUseInfo loadByRowid(Long id);

  /**
   * 主キー列の値を指定して优惠券规则_使用关联信息のインスタンスを取得します。
   *
   * @param couponCode 优惠券规则编号
   * @param couponUseNo 明细编号
   * @return 主キー列の値に対応するNewCouponRuleUseInfoのインスタンス
   */
  NewCouponRuleUseInfo load(String couponCode, Long couponUseNo);
  /**
   * 根据优惠券规则编号取得优惠券规则_发行关联信息
   * 
   * @param couponCode 优惠券规则编号
   * @return 优惠券规则_发行关联信息
   * 
   */
  List<NewCouponRuleUseInfo> load(String couponCode, boolean trueOrFalse);
  
  /**
   * 主キー列の値を指定して优惠券规则_使用关联信息のインスタンスを取得します。
   *
   * @param couponCode 优惠券规则编号
   * @return 主キー列の値に対応するNewCouponRuleUseInfoののリスト
   */
  List<NewCouponRuleUseInfo> load(String couponCode);
  
  /**
   * 优惠券编号和商品编号の値を指定して优惠券规则_使用关联信息のインスタンスを取得します。
   *
   * @param couponCode 优惠券规则编号
   * @param couponUseNo 明细编号
   * @return 主キー列の値に対応するNewCouponRuleUseInfoのインスタンス
   */
  List<NewCouponRuleUseInfo> load(String couponCode, String useCommodityCode , String flg);
  List<NewCouponRuleUseInfo> load(String couponCode,String flg);
  

  /**
   * 主キー列の値を指定して优惠券规则_使用关联信息が既に存在するかどうかを返します。
   *
   * @param couponCode 优惠券规则编号
   * @param couponUseNo 明细编号
   * @return 主キー列の値に対応するNewCouponRuleUseInfoの行が存在すればtrue
   */
  boolean exists(String couponCode, Long couponUseNo);

  /**
   * 新規NewCouponRuleUseInfoをデータベースに追加します。
   *
   * @param obj 追加対象のNewCouponRuleUseInfo
   * @param loginInfo ログイン情報
   * @return 追加が成功した場合のorm_rowid値
   */
  Long insert(NewCouponRuleUseInfo obj, LoginInfo loginInfo);

  /**
   * 优惠券规则_使用关联信息を更新します。
   *
   * @param obj 更新対象のNewCouponRuleUseInfo
   * @param loginInfo ログイン情報
   */
  void update(NewCouponRuleUseInfo obj, LoginInfo loginInfo);

  /**
   * 优惠券规则_使用关联信息を削除します。
   *
   * @param obj 削除対象のNewCouponRuleUseInfo
   * @param loginInfo ログイン情報
   */
  void delete(NewCouponRuleUseInfo obj, LoginInfo loginInfo);

  /**
   * 主キー列の値を指定して优惠券规则_使用关联信息を削除します。
   *
   * @param couponCode 优惠券规则编号
   * @param couponUseNo 明细编号
   */
  void delete(String couponCode, Long couponUseNo);

  /**
   * Queryオブジェクトを指定して优惠券规则_使用关联信息のリストを取得します。
   *
   * @param query Queryオブジェクト
   * @return 検索結果に相当するNewCouponRuleUseInfoのリスト
   */
  List<NewCouponRuleUseInfo> findByQuery(Query query);

  /**
   * SQLを指定して优惠券规则_使用关联信息のリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するNewCouponRuleUseInfoのリスト
   */
  List<NewCouponRuleUseInfo> findByQuery(String sqlString, Object... params);

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブルの全データ分のNewCouponRuleUseInfoのリスト
   */
  List<NewCouponRuleUseInfo> loadAll();
  /**
   * 根据优惠券编号和商品编号の値を指定して优惠券规则_使用关联信息を削除します。
   *
   * @param couponCode 优惠券规则编号
   * @param couponUseNo 明细编号
   */
  void delete(String couponCode, String useCommodityCode, String flg);

}
