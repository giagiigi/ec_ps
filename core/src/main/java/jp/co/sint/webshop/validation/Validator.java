package jp.co.sint.webshop.validation;

import java.lang.annotation.Annotation;

/**
 * 共通のValidatorを定義します。
 * 
 * @author System Integrator Corp.
 */
public interface Validator<A extends Annotation> {

  /**
   * 値を検証します。
   * 
   * @param value
   *          検査対象の値
   * @return 検証結果が正しければtrue
   */
  boolean isValid(Object value);

  /**
   * このバリデータを初期化します。
   * 
   * @param annotation 初期化に必要な情報
   */
  void init(A annotation);

  /**
   * エラーメッセージを返します。
   * 
   * @return エラーメッセージ
   */
  String getMessage();
}
