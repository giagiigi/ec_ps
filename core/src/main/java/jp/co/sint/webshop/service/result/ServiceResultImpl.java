package jp.co.sint.webshop.service.result;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.text.Messages;

public class ServiceResultImpl implements ServiceResult {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private List<ServiceErrorContent> serviceErrorList;

  public ServiceResultImpl() {
    serviceErrorList = new ArrayList<ServiceErrorContent>();
  }

  public List<ServiceErrorContent> getServiceErrorList() {
    return serviceErrorList;
  }

  public boolean hasError() {
    return serviceErrorList.size() != 0;
  }

  public void addServiceError(ServiceErrorContent content) {
    Logger logger = Logger.getLogger(this.getClass());
    logger.error(MessageFormat.format(Messages.log("service.result.ServiceResultImpl.0"), content));
    serviceErrorList.add(content);
  }

}
