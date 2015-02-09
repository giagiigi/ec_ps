package jp.co.sint.webshop.service.impl;

import java.io.Serializable;

import org.apache.log4j.Logger;

import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.WebshopEntity;
import jp.co.sint.webshop.data.cache.CacheContainer;
import jp.co.sint.webshop.data.domain.LoginType;
import jp.co.sint.webshop.service.LoginInfo;
import jp.co.sint.webshop.service.ServiceLoginInfo;
import jp.co.sint.webshop.service.StatefulService;
import jp.co.sint.webshop.service.UserContext;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.UserAgent;

public class AbstractServiceImpl implements StatefulService, Serializable {

  public AbstractServiceImpl() {
    Logger logger = Logger.getLogger(this.getClass());
    logger.debug("Service Implementation Loaded: ");
  }

  /** uid */
  private static final long serialVersionUID = 1L;

  private static UserContext userContext = new UserContextProxy();

  private static UserContext getUserContext() {
    return userContext;
  }

  /**
   * ログイン情報を返します。
   * 
   * @return ログイン情報
   */
  public LoginInfo getLoginInfo() {
    return getUserContext().getLoginInfo();
  }

  /**
   * @param loginInfo
   *          ログイン情報
   */
  public void setLoginInfo(LoginInfo loginInfo) {
    if (loginInfo == null) {
      throw new RuntimeException("LoginInfo is NULL.");
    } else if (loginInfo.getLoginType() == LoginType.FRONT) {
      UserAgent userAgent = loginInfo.getUserAgent();
      loginInfo = ServiceLoginInfo.getInstance();
      ((ServiceLoginInfo) loginInfo).setUserAgent(userAgent);
    }
    getUserContext().setLoginInfo(loginInfo);
  }

  public void setUserStatus(WebshopEntity entity) {
    Logger logger = Logger.getLogger(this.getClass());
    DatabaseUtil.setUserStatus(entity, getLoginInfo());
    logger.debug("UpdatedUser:" + entity.getUpdatedUser());
    logger.debug("UpdatedDatetime:" + entity.getUpdatedDatetime());
  }
  
  public CacheContainer getCacheContainer() {
    return DIContainer.getCacheContainer();
  }

}
