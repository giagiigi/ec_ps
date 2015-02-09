package jp.co.sint.webshop.data;

import java.sql.CallableStatement;
import java.sql.SQLException;

/**
 * TransactionManagerインターフェイスを利用してストアドプロシージャを実行するためのインターフェイス
 * 
 * @author System Integrator Corp.
 */
public interface ProcedureDelegate {

  /**
   * CallableStatementオブジェクトを生成するためのSQL文を返します。
   * 
   * @return SQL文
   */
  String getStatement();

  /**
   * CallableStatementを実行します。
   * 例外処理やリソースの解放(closeメソッド呼び出し)はTransactionManager側で対応します。
   * 
   * @param statement
   *          CallableStatementオブジェクト
   * @throws SQLException
   *           SQL例外
   */
  void execute(CallableStatement statement) throws SQLException;
}
