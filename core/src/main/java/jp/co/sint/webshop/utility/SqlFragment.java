package jp.co.sint.webshop.utility;

import java.io.Serializable;
import java.util.Arrays;

/**
 * SQL部分文字列と、SQLにバインドされるパラメータ(群)をまとめたクラスです。
 * 
 * @author System Integrator Corp.
 */
public class SqlFragment implements Serializable {

  /** uid */
  private static final long serialVersionUID = 1L;

  private String fragment = "";

  private Object[] parameters = new Object[0];

  /**
   * 新しいSqlFragmentを生成します。
   */
  public SqlFragment() {
  }

  /**
   * SQL部分文字列とパラメータを指定して新しいSqlFragmentを生成します。
   * 
   * @param fragment
   *          SQL部分文字列
   * @param parameters
   *          パラメータの配列
   */
  public SqlFragment(String fragment, Object... parameters) {
    this.setFragment(fragment);
    this.setParameters(parameters);
  }

  /**
   * SQL部分文字列を取得します。
   * 
   * @return SQL部分文字列
   */
  public String getFragment() {
    return fragment;
  }

  /**
   * パラメータを取得します
   * 
   * @return パラメータの配列
   */
  public Object[] getParameters() {
    return ArrayUtil.immutableCopy(parameters);
  }

  /**
   * SQL部分文字列を設定します。
   * 
   * @param fragment
   *          SQL部分文字列
   */
  public void setFragment(String fragment) {
    this.fragment = fragment;
  }

  /**
   * パラメータを設定します
   * 
   * @param parameters
   *          パラメータの配列
   */
  public void setParameters(Object[] parameters) {
    this.parameters = ArrayUtil.immutableCopy(parameters);
  }

  @Override
  public String toString() {
    return "{fragment:" + getFragment() + ", parameters:" + Arrays.toString(getParameters()) + "}";
  }
}
