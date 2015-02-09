package jp.co.sint.webshop.validation;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;

import jp.co.sint.webshop.data.attribute.Domain;
import jp.co.sint.webshop.text.Messages;

/**
 * 定義域チェック用のバリデータクラスです。
 * 
 * @author System Integrator Corp.
 */
public class DomainValidator implements Validator<Domain> {

  private Class<?> domainType;

  public String getMessage() {
    //modify by V10-CH 170 start
    return Messages.getString("validation.DomainValidator.0");
    //modify by V10-CH 170 end
  }

  public void init(Domain annotation) {
    domainType = annotation.value();
  }

  public boolean isValid(Object value) {
    Logger logger = Logger.getLogger(this.getClass());
    boolean result = false;
    if (value == null) {
      return true;
    }
    try {
      Method m = domainType.getMethod("isValid", String.class);
      Boolean b = (Boolean) m.invoke(null, value.toString());
      result = b.booleanValue();
    } catch (NoSuchMethodException nsmEx) {
      logger.warn(nsmEx);
      result = false;
    } catch (Exception e) {
      logger.warn(e);
      result = false;
    }

    return result;
  }

}
