package jp.co.sint.webshop.data;

import java.io.Serializable;

/**
 * SQLを取り扱うための基底インタフェースです。
 * 
 * @author System Integrator Corp.
 */
public interface Query extends Serializable {

  /**
   * SQL文字列を取得します。このSQL文字列にはINパラメータのプレースホルダ(?)を含むことがあります。
   * 
   * @return SQL文字列
   */
  String getSqlString();

  /**
   * INパラメータとしてバインドされる値の配列を返します。
   * <p>
   * パラメータの数はgetSqlString()メソッドで戻されるSQL文字列に含まれる プレースホルダ(?の数と一致している必要があります。
   * </p>
   * 
   * @return パラメータにバインドされる値の配列
   */
  Object[] getParameters();

}
