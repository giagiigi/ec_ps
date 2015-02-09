package jp.co.sint.webshop.validation;

public interface CustomValidator<T> {

  ValidationSummary validate(T value);

}
