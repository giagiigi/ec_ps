package jp.co.sint.webshop.validation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 検証結果のコンテナクラスです。
 * 
 * @author System Integrator Corp.
 */
public class ValidationSummary implements Serializable {

  /** uid */
  private static final long serialVersionUID = 1L;

  // private boolean valid;

  private List<ValidationResult> errors = new ArrayList<ValidationResult>();

  public boolean isValid() {
    return errors.size() == 0;
  }

  // public void setValid(boolean valid) {
  // this.valid = valid;
  // }

  public boolean hasError() {
    return !this.isValid();
  }

  public List<ValidationResult> getErrors() {
    return errors;
  }

  public void setErrors(List<ValidationResult> errors) {
    this.errors = errors;
  }

  public List<String> getErrorMessages() {
    List<String> messages = new ArrayList<String>();
    for (ValidationResult r : getErrors()) {
      messages.add(r.getFormedMessage());
    }
    return messages;
  }
}
