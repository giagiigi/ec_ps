package jp.co.sint.webshop.validation;

import jp.co.sint.webshop.data.attribute.Pattern;

/**
 * 正規表現によるパターンマッチValidatorです。
 * 
 * @author System Integrator Corp.
 */
public class PatternValidator implements Validator<Pattern> {

  private String pattern;
  
  private String message;

  public PatternValidator() {

  }

  public PatternValidator(String p) {
    setPattern(p);
  }

  public String getPattern() {
    return pattern;
  }

  public void setPattern(String pattern) {
    this.pattern = pattern;
  }

  public String getMessage() {
    return message;
  }


  public void setMessage(String message) {
    this.message = message;
  }

  public void init(Pattern annotation) {
    setPattern(annotation.value());
    setMessage(annotation.message());
  }

  public boolean isValid(Object value) {
    return ValidatorUtil.patternMatches(getPattern(), value);
  }
}
