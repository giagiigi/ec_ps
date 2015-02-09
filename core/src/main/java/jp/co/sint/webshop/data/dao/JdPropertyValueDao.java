package jp.co.sint.webshop.data.dao;

import java.util.List;

import jp.co.sint.webshop.data.GenericDao;
import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.dto.JdPropertyValue;
import jp.co.sint.webshop.service.LoginInfo;

/**
 * 「JD属性值(jd_Property_Value)」テーブルへのデータアクセスを担当するDAO(Data Access Object)です。
 * 
 * @author OB
 * 
 */
public interface JdPropertyValueDao extends GenericDao<JdPropertyValue, Long> {

  /**
   * 指定されたorm_rowidを持つJD属性值のインスタンスを取得します。
   * 
   * @param id
   *          対象のorm_rowid
   * @return idに対応するJdPropertyValueのインスタンス
   */
  JdPropertyValue loadByRowid(Long id);

  /**
   * 主キー列の値を指定してJD属性值のインスタンスを取得します。
   * 
   * @param valueId
   *          属性值id
   * @param propertyId
   *          所属属性id
   * @return 主キー列の値に対応するJdPropertyValueのインスタンス
   */
  JdPropertyValue load(String valueId, String propertyId);

  /**
   * 主キー列の値を指定してJD属性值が既に存在するかどうかを返します。
   * 
   * @param valueId
   *          属性值id
   * @param propertyId
   *          所属属性id
   * @return 主キー列の値に対応するJdPropertyValueの行が存在すればtrue
   */
  boolean exists(String valueId, String propertyId);

  /**
   * 新規JdPropertyValueをデータベースに追加します。
   * 
   * @param obj
   *          追加対象のJdPropertyValue
   * @param loginInfo
   *          ログイン情報
   * @return 追加が成功した場合のorm_rowid値
   */
  Long insert(JdPropertyValue obj, LoginInfo loginInfo);

  /**
   * JD属性值を更新します。
   * 
   * @param obj
   *          更新対象のJdPropertyValue
   * @param loginInfo
   *          ログイン情報
   */
  void update(JdPropertyValue obj, LoginInfo loginInfo);

  /**
   * JD属性值を削除します。
   * 
   * @param obj
   *          削除対象のJdPropertyValue
   * @param loginInfo
   *          ログイン情報
   */
  void delete(JdPropertyValue obj, LoginInfo loginInfo);

  /**
   * 主キー列の値を指定してJD属性值を削除します。
   * 
   * @param valueId
   *          属性值id
   * @param propertyId
   *          所属属性id
   */
  void delete(String valueId, String propertyId);;

  /**
   * Queryオブジェクトを指定してJD属性值のリストを取得します。
   * 
   * @param query
   *          Queryオブジェクト
   * @return 検索結果に相当するJdPropertyValueのリスト
   */
  List<JdPropertyValue> findByQuery(Query query);

  /**
   * SQLを指定してJD属性值のリストを取得します。
   * 
   * @param sqlString
   *          バインド変数(?)を含むSQL文字列
   * @param params
   *          バインド変数に与える値の配列
   * @return 検索結果に相当するJdPropertyValueのリスト
   */
  List<JdPropertyValue> findByQuery(String sqlString, Object... params);

  /**
   * テーブルの全データを一括で取得します。
   * 
   * @return テーブルの全データ分のJdPropertyValueのリスト
   */
  List<JdPropertyValue> loadAll();

}
