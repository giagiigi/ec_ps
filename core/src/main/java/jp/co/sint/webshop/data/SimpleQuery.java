package jp.co.sint.webshop.data;

import jp.co.sint.webshop.utility.ArrayUtil;

/**
 * Queryインタフェースのシンプルな実装クラスです。
 * 
 * @author System Integrator Corp.
 */
public class SimpleQuery implements Query {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private Object[] parameters = new Object[0];

  private String sqlString = "";

  public SimpleQuery() {
  }

  /**
   * クエリ文字列とバインド値を指定して、新しいSimpleQueryオブジェクトを生成します。
   * 
   * @param query
   *          クエリ文字列
   * @param parameters
   *          バインド値の配列
   */
  public SimpleQuery(String query, Object... parameters) {
    this.setSqlString(query);
    this.setParameters(parameters);
  }

  /**
   * SQL文字列を取得します。
   * 
   * @return SQL文字列
   */
  public String getSqlString() {
    return sqlString;
  }

  /**
   * SQL文字列を設定します。
   * 
   * @param sqlString
   *          SQL文字列
   */
  public void setSqlString(String sqlString) {
    this.sqlString = sqlString;
  }

  /**
   * INパラメータとしてバインドされる値の配列を返します。
   * 
   * @return パラメータにバインドされる値の配列
   */
  public Object[] getParameters() {
    return ArrayUtil.immutableCopy(parameters);
  }

  /**
   * INパラメータとしてバインドされる値の配列を設定します。
   * 
   * @param parameters
   *          パラメータにバインドされる値の配列
   */
  public void setParameters(Object... parameters) {
    this.parameters = ArrayUtil.immutableCopy(parameters);
  }

}
