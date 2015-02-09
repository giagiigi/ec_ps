package jp.co.sint.webshop.data.dao;

import java.util.List;

import jp.co.sint.webshop.data.GenericDao;
import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.dto.JdProperty;
import jp.co.sint.webshop.service.LoginInfo;

/**
 * 「JD属性表(jd_Property)」テーブルへのデータアクセスを担当するDAO(Data Access Object)です。
 * 
 * @author OB
 * 
 */
public interface JdPropertyDao extends GenericDao<JdProperty, Long> {

  /**
   * 指定されたorm_rowidを持つJD属性表のインスタンスを取得します。
   * 
   * @param id
   *          対象のorm_rowid
   * @return idに対応するJdPropertyのインスタンス
   */
  JdProperty loadByRowid(Long id);

  /**
   * 主キー列の値を指定してJD属性表のインスタンスを取得します。
   * 
   * @param property_id
   *          JD属性id
   * @param categoryId
   *          JD类目ID
   * @return 主キー列の値に対応するJdPropertyのインスタンス
   */
  JdProperty load(String propertyId, String categoryId);

  /**
   * 主キー列の値を指定してJD属性表が既に存在するかどうかを返します。
   * 
   * @param property_id
   *          JD属性id
   * @param categoryId
   *          JD类目ID
   * @return 主キー列の値に対応するJdPropertyの行が存在すればtrue
   */
  boolean exists(String propertyId, String categoryId);

  /**
   * 新規JdPropertyをデータベースに追加します。
   * 
   * @param obj
   *          追加対象のJdProperty
   * @param loginInfo
   *          ログイン情報
   * @return 追加が成功した場合のorm_rowid値
   */
  Long insert(JdProperty obj, LoginInfo loginInfo);

  /**
   * JD属性表を更新します。
   * 
   * @param obj
   *          更新対象のJdProperty
   * @param loginInfo
   *          ログイン情報
   */
  void update(JdProperty obj, LoginInfo loginInfo);

  /**
   * JD属性表を削除します。
   * 
   * @param obj
   *          削除対象のJdProperty
   * @param loginInfo
   *          ログイン情報
   */
  void delete(JdProperty obj, LoginInfo loginInfo);

  /**
   * 主キー列の値を指定してJD属性表を削除します。
   * 
   * @param property_id
   *          JD属性id
   * @param categoryId
   *          JD类目ID
   */
  void delete(String propertyId, String categoryId);

  /**
   * Queryオブジェクトを指定してJD属性表のリストを取得します。
   * 
   * @param query
   *          Queryオブジェクト
   * @return 検索結果に相当するJdPropertyのリスト
   */
  List<JdProperty> findByQuery(Query query);

  /**
   * SQLを指定してJD属性表のリストを取得します。
   * 
   * @param sqlString
   *          バインド変数(?)を含むSQL文字列
   * @param params
   *          バインド変数に与える値の配列
   * @return 検索結果に相当するJdPropertyのリスト
   */
  List<JdProperty> findByQuery(String sqlString, Object... params);

  /**
   * テーブルの全データを一括で取得します。
   * 
   * @return テーブルの全データ分のJdPropertyのリスト
   */
  List<JdProperty> loadAll();

}
