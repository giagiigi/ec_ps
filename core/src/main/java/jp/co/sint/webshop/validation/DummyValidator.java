package jp.co.sint.webshop.validation;

import java.lang.annotation.Annotation;

/**
 * ダミーのバリデータです。検証結果は常にtrueを返します。
 * 
 * @author System Integrator Corp.
 */
public class DummyValidator implements Validator<Annotation> {

  public String getMessage() {
    return "";
  }

  public void init(Annotation annotation) {
  }

  public boolean isValid(Object value) {
    return true;
  }

}
