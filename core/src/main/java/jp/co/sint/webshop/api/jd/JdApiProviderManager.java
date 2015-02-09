package jp.co.sint.webshop.api.jd;

import com.jd.open.api.sdk.DefaultJdClient;
import com.jd.open.api.sdk.JdClient;
import com.jd.open.api.sdk.JdException;
import com.jd.open.api.sdk.request.JdRequest;
import com.jd.open.api.sdk.response.AbstractResponse;

import jp.co.sint.webshop.utility.DIContainer;

import org.apache.log4j.Logger;

/**
 * 京东用API接口<BR>
 * 
 * @author OB Corp.
 */
public class JdApiProviderManager {

  private JdRequest<?> request;

  private JdApiConfig config;

  private JdClient client;

  private String apiType;

  @SuppressWarnings("unchecked")
  public JdApiProviderManager(JdRequest requestParam) {
    request = requestParam;
    if (requestParam != null) {
      apiType = requestParam.getApiMethod();
    }
  }

  public JdApiResult execute() {

    Logger logger = Logger.getLogger(this.getClass());
    logger.info("京东API：" + apiType + " 开始!");

    init();

    JdApiResultImpl result = new JdApiResultImpl();

    if (request == null || config == null || client == null) {
      logger.warn("京东API：" + apiType + " 失败!");
      return result;
    }

    try {
      AbstractResponse response = client.execute(request);
      result.setResultBean(response);
    } catch (JdException e) {
      logger.error(e.getErrMsg());
      result.setErrorMessage(e.getErrMsg());
    }

    result.setResultType(JdApiResultType.SUCCESS);

    if (result.hasError()) {
      logger.warn(result.getErrorMessage());
      logger.warn("京东API：" + apiType + " 失败!");
    } else {
      logger.info("京东API：" + apiType + " 成功!");
    }
    return result;

  }

  /**
   * 京东API初期处理
   */
  private void init() {
    config = DIContainer.getJdApiConfig();
    client = new DefaultJdClient(config.getReqUrl(), config.getSessionKey(), config.getAppKey(), config.getAppSercet());
  }

}
