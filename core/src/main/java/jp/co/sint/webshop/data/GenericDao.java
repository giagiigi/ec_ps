package jp.co.sint.webshop.data;

import java.io.Serializable;
import java.util.List;

/**
 * ジェネリックDAO(Data Access Object)の既定インタフェースです。
 * 
 * @author System Integrator Corp.
 * @param <T>
 *          アクセス先テーブルに対応するDTOクラス
 * @param <PK>
 *          行IDクラス(通常はLongを用います)
 */
public interface GenericDao<T extends WebshopEntity, PK extends Serializable> {

  /**
   * 指定されたidを持つDTOインスタンスを取得します。
   * 
   * @param id
   *          対象のorm_rowid
   * @return idに対応するDTOインスタンス
   */
  T loadByRowid(PK id);

  /**
   * 新しいDTOインスタンスの内容をデータベースに追加します。
   * 
   * @param newInstance
   *          追加対象のDTOインスタンス
   * @return 追加が成功した場合のorm_rowid値
   */
  PK insert(T newInstance);

  /**
   * DTOインスタンスの更新をデータベースへ反映します。
   * 
   * @param transactionObject
   *          更新対象のDTOインスタンス
   */
  void update(T transactionObject);

  /**
   * DTOインスタンスの内容をデータベースから削除します。
   * 
   * @param transactionObject
   *          削除対象のDTOインスタンス
   */
  void delete(T transactionObject);

  /**
   * SQLクエリを指定して、DTOインスタンスのリストを取得します。
   * 
   * @param query
   *          SQLクラス
   * @return DTOインスタンスのリスト。結果がゼロ件の場合は空のリストを返す。
   */
  List<T> findByQuery(Query query);

  /**
   * SQLクエリを指定して、DTOインスタンスのリストを取得します。
   * 
   * @param sqlString
   *          SQL文字列
   * @param parameters
   *          バインド変数に与えるパラメータの配列
   * @return DTOインスタンスのリスト。結果がゼロ件の場合は空のリストを返す。
   */
  List<T> findByQuery(String sqlString, Object... parameters);

  /**
   * テーブルの全データを一括で取得します。
   * 
   * @return テーブルの全データ分のDTOのリスト
   */
  List<T> loadAll();

}
