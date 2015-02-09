package jp.co.sint.webshop.code;

/**
 * 名前と値のペアを持つコード値の共通基底インタフェースです。
 * @author System Integrator Corp.
 *
 */
public interface CodeAttribute {

  /**
   * コード名称を返します。
   * @return コード名称
   */
  String getName();

  /**
   * コード値を返します。
   * @return コード値
   */
  String getValue();

}
