package jp.co.sint.webshop.data;

import jp.co.sint.webshop.service.LoginInfo;

/**
 * トランザクション管理インタフェースです。複数テーブルを1トランザクションで更新するための機能を提供します。
 * 
 * @author System Integrator Corp.
 */
public interface TransactionManager extends Disposable {

  /**
   * 新しいトランザクションを開始します。
   * 
   * @param loginInfo
   *          ログイン情報
   */
  void begin(LoginInfo loginInfo);

  /**
   * トランザクション処理の一部として、1件分のデータを追加します。
   * DTOとデータベースレコードは行ID(ORM_ROWID列)の値で連結されている必要があります。
   * 
   * @param entity
   *          テーブルの1レコードを表現するDTO
   */
  void insert(WebshopEntity entity);

  /**
   * トランザクション処理の一部として、1件分のデータを更新します。
   * DTOとデータベースレコードは行ID(ORM_ROWID列)の値で連結されている必要があります。
   * 
   * @param entity
   *          テーブルの1レコードを表現するDTO
   */
  void update(WebshopEntity entity);

  /**
   * トランザクション処理の一部として、1件分のデータを削除します。
   * DTOとデータベースレコードは行ID(ORM_ROWID列)の値で連結されている必要があります。
   * 
   * @param entity
   *          テーブルの1レコードを表現するDTO
   */
  void delete(WebshopEntity entity);

  /**
   * トランザクション処理の一部として、更新SQLを発行します。
   * 
   * @param query
   *          データベースを更新するSQLステートメント。
   *          PreparedStatementの書式に従ったバインドメカニズムを利用することができます。
   * @param parameters
   *          SQLステートメントにバインドするパラメータの配列。
   *          配列内の順序はSQLステートメント内のマーカー「?」の順序と一致している必要があります。
   * @return SQLによって更新された行の数を返します。
   */
  int executeUpdate(String query, Object... parameters);

  /**
   * トランザクション処理の一部として、更新SQLを発行します。
   * 
   * @param query
   *          SQLクラス
   * @return SQLによって更新された行の数を返します。
   */
  int executeUpdate(Query query);

  /**
   * ストアドプロシージャを実行します。
   * 
   * @param delegate
   *          実行するストアドプロシージャ情報を含むProcedureDelegateオブジェクト
   */
  void executeProcedure(ProcedureDelegate delegate);

  /**
   * 現在実行中のトランザクションをコミットします。
   */
  void commit();

  /**
   * 現在実行中のトランザクションをロールバックします。
   */
  void rollback();

  /**
   * トランザクションを閉じ、リソースを開放します。
   * 
   * @deprecated dispose()に置き換えます。
   */
  @Deprecated
void close();

  /**
   * このトランザクションに関連付けられた、在庫管理クラスを取得します。
   * 
   * @return 在庫管理クラス
   */
  StockManager getStockManager();

}
