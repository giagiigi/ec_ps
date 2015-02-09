package jp.co.sint.webshop.api.jd;

import java.io.Serializable;
import com.jd.open.api.sdk.response.AbstractResponse;

/**
 * 京东API返回结果
 *
 * @author OB Corp.
 */
public interface JdApiResult extends Serializable {

  JdApiResultType getResultType();
  
  boolean hasError();

  AbstractResponse getResultBean();
  
  String getErrorMessage();

  String getErrorCode();
}
