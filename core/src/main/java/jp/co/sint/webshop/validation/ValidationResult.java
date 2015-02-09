package jp.co.sint.webshop.validation;

import java.io.Serializable;
import java.text.MessageFormat;

import jp.co.sint.webshop.text.Messages;

/**
 * 値検証の結果(エラーメッセージ)を表すクラスです。
 * 
 * @author System Integrator Corp.
 */
public class ValidationResult implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private String name;

  private Object value;

  private String message;

  private int displayOrder = Integer.MAX_VALUE;

  private int priority;

  private boolean correct;

  public ValidationResult() {
  }

  public ValidationResult(boolean correct) {
    this.correct = correct;
  }

  public ValidationResult(String name, Object value, String message) {
    setName(name);
    setValue(value);
    setMessage(message);
  }

  public ValidationResult(String name, Object value, String message, int priority) {
    this(name, value, message);
    setPriority(priority);
  }

  public boolean isCorrect() {
    return correct;
  }

  public boolean isError() {
    return !correct;
  }

  /**
   * @return priority
   */
  public int getPriority() {
    return priority;
  }

  /**
   * @return value
   */
  public Object getValue() {
    return value;
  }

  /**
   * @param priority
   *          設定する priority
   */
  public void setPriority(int priority) {
    this.priority = priority;
  }

  /**
   * @param value
   *          設定する value
   */
  public void setValue(Object value) {
    this.value = value;
  }

  /**
   * @return message
   */
  public String getMessage() {
    return this.message;
  }

  /**
   * 書式化されたメッセージを返します。
   * 
   * @return 書式化されたメッセージ
   */
  public String getFormedMessage() {
    if (getName() == null) {
      return getMessage();
    }
    //modify by V10-CH 170 start
    return MessageFormat.format(Messages.getString("validation.ValidationResult.0"), getName(), getMessage());
    //modify by V10-CH 170 end
  }

  /**
   * @return name
   */
  public String getName() {
    return name;
  }

  /**
   * @param message
   *          設定する message
   */
  public void setMessage(String message) {
    this.message = message;
  }

  /**
   * @param name
   *          設定する name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * @return displayOrder
   */
  public int getDisplayOrder() {
    return displayOrder;
  }

  /**
   * @param displayOrder
   *          設定する displayOrder
   */
  public void setDisplayOrder(int displayOrder) {
    this.displayOrder = displayOrder;
  }

  /**
   * correctを設定します。
   * 
   * @param correct
   *          設定する correct
   */
  public void setCorrect(boolean correct) {
    this.correct = correct;
  }

}
