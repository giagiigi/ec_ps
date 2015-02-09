package jp.co.sint.webshop.data.dao;


import java.util.List;

import jp.co.sint.webshop.data.GenericDao;
import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.dto.GoogleAnalysis;
import jp.co.sint.webshop.service.LoginInfo;
/** 
 * 「(GoogleAnalysis)」テーブルへのデータアクセスを担当するDAO(Data Access Object)です。
 *
 * @author System Integrator Corp.
 *
 */
public interface GoogleAnalysisDao  extends GenericDao<GoogleAnalysis, Long> {

  /**
   * 指定されたorm_rowidを持つのインスタンスを取得します。
   *
   * @param id 対象のorm_rowid
   * @return idに対応するGoogleAnalysisのインスタンス
   */
  GoogleAnalysis loadByRowid(Long id);

  /**
   * 主キー列の値を指定してポイントルールのインスタンスを取得します。
   *
   * @param GoogleAnalysisNo 
   * @return 主キー列の値に対応するGoogleAnalysisのインスタンス
   */
  GoogleAnalysis load(Long pointRuleNo);

  /**
   * 主キー列の値を指定してポイントルールが既に存在するかどうかを返します。
   *
   * @param GoogleAnalysisNo 
   * @return 主キー列の値に対応するGoogleAnalysisの行が存在すればtrue
   */
  boolean exists(Long pointRuleNo);

  /**
   * 新規PointRuleをデータベースに追加します。
   *
   * @param obj 追加対象のGoogleAnalysis
   * @param loginInfo ログイン情報
   * @return 追加が成功した場合のorm_rowid値
   */
  Long insert(GoogleAnalysis obj, LoginInfo loginInfo);

  /**
   * GoogleAnalysisを更新します。
   *
   * @param obj 更新対象のGoogleAnalysis
   * @param loginInfo ログイン情報
   */
  void update(GoogleAnalysis obj, LoginInfo loginInfo);

  /**
   * GoogleAnalysisを削除します。
   *
   * @param obj 削除対象のGoogleAnalysis
   * @param loginInfo ログイン情報
   */
  void delete(GoogleAnalysis obj, LoginInfo loginInfo);

  /**
   * 主キー列の値を指定してポイントルールを削除します。
   *
   * @param GoogleAnalysisNo 
   */
  void delete(Long GoogleAnalysisNo);

  /**
   * Queryオブジェクトを指定してのリストを取得します。
   *
   * @param query Queryオブジェクト
   * @return 検索結果に相当するGoogleAnalysisのリスト
   */
  List<GoogleAnalysis> findByQuery(Query query);

  /**
   * SQLを指定してポイントルールのリストを取得します。
   * @param sqlString バインド変数(?)を含むSQL文字列
   * @param params バインド変数に与える値の配列
   * @return 検索結果に相当するGoogleAnalysisのリスト
   */
  List<GoogleAnalysis> findByQuery(String sqlString, Object... params);

  /**
   * テーブルの全データを一括で取得します。
   * @return テーブルの全データ分のGoogleAnalysisのリスト
   */
  List<GoogleAnalysis> loadAll();

}





  
  
  

