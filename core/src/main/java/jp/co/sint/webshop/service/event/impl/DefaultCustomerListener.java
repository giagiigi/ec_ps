package jp.co.sint.webshop.service.event.impl;

import jp.co.sint.webshop.data.domain.LoginLockedFlg;
import jp.co.sint.webshop.data.dto.Shop;
import jp.co.sint.webshop.service.MailingService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceLoginInfo;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.ShopManagementService;
import jp.co.sint.webshop.service.event.CustomerEvent;
import jp.co.sint.webshop.service.event.CustomerListener;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.text.Messages;
import jp.co.sint.webshop.utility.DIContainer;

import org.apache.log4j.Logger;

public class DefaultCustomerListener implements CustomerListener {

  private static final long serialVersionUID = 1L;

  public DefaultCustomerListener() {
    Logger logger = Logger.getLogger(this.getClass());
    logger.info(Messages.log("service.event.impl.DefaultCustomerListener.0"));
  }

  public void customerAdded(CustomerEvent event) {
    Logger logger = Logger.getLogger(this.getClass());
    logger.info(Messages.log("service.event.impl.DefaultCustomerListener.1"));
    // 10.1.6 10253 追加 ここから
    if (event.getCustomer().getLoginLockedFlg().equals(LoginLockedFlg.LOCKED.longValue())) {
      logger.info(Messages.log("service.event.impl.DefaultCustomerListener.6"));
      return;
    }
    // 10.1.6 10253 追加 ここまで
    MailingService mailService = ServiceLocator.getMailingService(ServiceLoginInfo.getInstance());
    ShopManagementService shopService = ServiceLocator.getShopManagementService(ServiceLoginInfo.getInstance());
    Shop site = shopService.getShop(DIContainer.getWebshopConfig().getSiteShopCode());
    ServiceResult result = mailService.sendCustomerRegisterdMail(event.getCustomer(), site);
    if (result.hasError()) {
      for (ServiceErrorContent error : result.getServiceErrorList()) {
        logger.error(error);
      }
    }
  }

  public void customerWithdrawed(CustomerEvent event) {
    Logger logger = Logger.getLogger(this.getClass());
    logger.info(Messages.log("service.event.impl.DefaultCustomerListener.2"));
    // 10.1.6 10253 追加 ここから
    if (event.getCustomer().getLoginLockedFlg().equals(LoginLockedFlg.LOCKED.longValue())) {
      logger.info(Messages.log("service.event.impl.DefaultCustomerListener.7"));
      return;
    }
    // 10.1.6 10253 追加 ここまで
    ShopManagementService shopService = ServiceLocator.getShopManagementService(ServiceLoginInfo.getInstance());
    Shop site = shopService.getShop(DIContainer.getWebshopConfig().getSiteShopCode());
    MailingService mailingService = ServiceLocator.getMailingService(ServiceLoginInfo.getInstance());
    ServiceResult result = mailingService.sendWithdrawalMail(event.getCustomer(), site);
    if (result.hasError()) {
      for (ServiceErrorContent error : result.getServiceErrorList()) {
        logger.error(error);
      }
    }
  }

  public void customerUpdated(CustomerEvent event) {
    Logger logger = Logger.getLogger(this.getClass());
    logger.info(Messages.log("service.event.impl.DefaultCustomerListener.3"));
    // 10.1.6 10253 追加 ここから
    if (event.getCustomer().getLoginLockedFlg().equals(LoginLockedFlg.LOCKED.longValue())) {
      logger.info(Messages.log("service.event.impl.DefaultCustomerListener.8"));
      return;
    }
    // 10.1.6 10253 追加 ここまで
    ShopManagementService shopService = ServiceLocator.getShopManagementService(ServiceLoginInfo.getInstance());
    Shop site = shopService.getShop(DIContainer.getWebshopConfig().getSiteShopCode());
    MailingService mailingService = ServiceLocator.getMailingService(ServiceLoginInfo.getInstance());
    ServiceResult result = mailingService.sendCustomerRegisterdMail(event.getCustomer(), site);
    if (result.hasError()) {
      for (ServiceErrorContent error : result.getServiceErrorList()) {
        logger.error(error);
      }
    }
  }

  public void customerPasswordUpdated(CustomerEvent event) {
    Logger logger = Logger.getLogger(this.getClass());
    logger.info(Messages.log("service.event.impl.DefaultCustomerListener.4"));
    // 10.1.6 10253 追加 ここから
    if (event.getCustomer().getLoginLockedFlg().equals(LoginLockedFlg.LOCKED.longValue())) {
      logger.info(Messages.log("service.event.impl.DefaultCustomerListener.9"));
      return;
    }
    // 10.1.6 10253 追加 ここまで
    ShopManagementService shopService = ServiceLocator.getShopManagementService(ServiceLoginInfo.getInstance());
    Shop site = shopService.getShop(DIContainer.getWebshopConfig().getSiteShopCode());
    MailingService mailingService = ServiceLocator.getMailingService(ServiceLoginInfo.getInstance());
    ServiceResult result = mailingService.sendPasswordChangeMail(event.getCustomer(), site);
    if (result.hasError()) {
      for (ServiceErrorContent error : result.getServiceErrorList()) {
        logger.error(error);
      }
    }
  }

  public void customerWithdrawalRequested(CustomerEvent event) {
    Logger logger = Logger.getLogger(this.getClass());
    logger.info(Messages.log("service.event.impl.DefaultCustomerListener.5"));
    // 10.1.6 10253 追加 ここから
    if (event.getCustomer().getLoginLockedFlg().equals(LoginLockedFlg.LOCKED.longValue())) {
      logger.info(Messages.log("service.event.impl.DefaultCustomerListener.10"));
      return;
    }
    // 10.1.6 10253 追加 ここまで
    ShopManagementService shopService = ServiceLocator.getShopManagementService(ServiceLoginInfo.getInstance());
    Shop site = shopService.getShop(DIContainer.getWebshopConfig().getSiteShopCode());
    MailingService mailingService = ServiceLocator.getMailingService(ServiceLoginInfo.getInstance());
    mailingService.sendWithdrawalRequestMail(event.getCustomer(), site);
  }

}
