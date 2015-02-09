package jp.co.sint.webshop.validation;

/**
 * BeanValidatorで自己検証可能であることを示すインタフェースです。
 * 
 * @author System Integrator Corp.
 * @param <T>
 */

public interface Validatable<T> {

  /**
   * このオブジェクトを検証します。
   * 
   * @return ValidationResultのリスト
   */
  ValidationSummary validate();

  /**
   * このオブジェクトが検証の結果有効であるかどうかを返します。
   * 
   * @return 検証の結果有効であるならtrue
   */
  boolean isValid();
}
