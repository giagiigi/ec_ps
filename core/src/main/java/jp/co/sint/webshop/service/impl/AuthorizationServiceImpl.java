package jp.co.sint.webshop.service.impl;

import java.util.Collections;
import java.util.List;

import jp.co.sint.webshop.configure.WebshopConfig;
import jp.co.sint.webshop.data.DatabaseUtil;
import jp.co.sint.webshop.data.Query;
import jp.co.sint.webshop.data.SimpleQuery;
import jp.co.sint.webshop.data.dao.CustomerDao;
import jp.co.sint.webshop.data.dao.UserAccountDao;
import jp.co.sint.webshop.data.dao.UserPermissionDao;
import jp.co.sint.webshop.data.domain.CustomerStatus;
import jp.co.sint.webshop.data.domain.LoginLockedFlg;
import jp.co.sint.webshop.data.domain.OperatingMode;
import jp.co.sint.webshop.data.domain.PermissionType;
import jp.co.sint.webshop.data.dto.Customer;
import jp.co.sint.webshop.data.dto.UserAccount;
import jp.co.sint.webshop.data.dto.UserPermission;
import jp.co.sint.webshop.service.AuthorizationService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.authorization.AuthorizationServiceQuery;
import jp.co.sint.webshop.service.result.AuthorizationServiceErrorContent;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceResultImpl;
import jp.co.sint.webshop.text.Messages;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.PasswordUtil;
import jp.co.sint.webshop.validation.BeanValidator;
import jp.co.sint.webshop.validation.ValidationResult;

import org.apache.log4j.Logger;

public class AuthorizationServiceImpl extends AbstractServiceImpl implements AuthorizationService {

  /** serial version uid */
  private static final long serialVersionUID = 1L;

  public ServiceResult authorizeUser(String shopCode, String loginId, String password) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();
    UserAccount account = this.getUserAccountByLoginId(shopCode, loginId);

    if (account == null) {
      serviceResult.addServiceError(AuthorizationServiceErrorContent.USER_ACCOUNT_NOT_FOUND);
      return serviceResult;
    } else if (account.getLoginLockedFlg().equals(Long.valueOf(LoginLockedFlg.LOCKED.getValue()))) {
      serviceResult.addServiceError(AuthorizationServiceErrorContent.USER_ACCOUNT_LOCK);
      return serviceResult;
    }

    PermissionType type = this.getPermissionType(account.getUserCode());

    if (type == null) {
      serviceResult.addServiceError(AuthorizationServiceErrorContent.USER_PERMISSION_NOT_FOUND);
      return serviceResult;
    }

    if (PasswordUtil.getDigest(password).equalsIgnoreCase(account.getPassword())) {
      // 暗号化パスワード認証OK
      account.setLoginErrorCount(0L);
      account.setLoginDatetime(DateUtil.getSysdate());
    } else {
      // 認証NG
      WebshopConfig config = DIContainer.getWebshopConfig();

      account.setLoginErrorCount(account.getLoginErrorCount() + 1L);
      if (account.getLoginErrorCount() >= config.getAuthorizeUserErrorMaxCount()
          && !account.getUserCode().equals(Long.parseLong(config.getSiteUserCode()))) {
        account.setLoginLockedFlg(Long.valueOf(LoginLockedFlg.LOCKED.getValue()));
      }
      serviceResult.addServiceError(AuthorizationServiceErrorContent.USER_ACCOUNT_PASSWORD_UNMATCH);
    }
    // 認証結果をDBに登録する
    ServiceResult result = this.registerUserAccount(account);

    if (result.hasError()) {
      for (ServiceErrorContent errorContent : serviceResult.getServiceErrorList()) {
        if (errorContent.equals(CommonServiceErrorContent.VALIDATION_ERROR)) {
          serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
          return serviceResult;
        } else if (errorContent.equals(CommonServiceErrorContent.NO_DATA_ERROR)) {
          serviceResult.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
          return serviceResult;
        }
      }
    }
    return serviceResult;
  }

  public PermissionType getPermissionType(Long userCode) {
    if (NumUtil.isNull(userCode)) {
      return null;
    }

    if (DIContainer.getWebshopConfig().getOperatingMode() == OperatingMode.ONE) {
      // 一店舗版の場合常にサイト管理者とする
      return PermissionType.SITE;
    }

    boolean isSite = false;
    boolean isShop = false;

    List<UserPermission> list = getUserPermissionList(userCode);

    for (UserPermission userPermission : list) {
      for (Permission permission : Permission.values()) {
        if (userPermission.getPermissionCode().equals(permission.getCode())
            && permission.getPermissionType().equals(PermissionType.SITE)) {
          isSite = true;
        }
        if (userPermission.getPermissionCode().equals(permission.getCode())
            && permission.getPermissionType().equals(PermissionType.SHOP)) {
          isShop = true;
        }
      }
    }

    if (isSite && !isShop) {
      return PermissionType.SITE;
    }
    if (isShop && !isSite) {
      return PermissionType.SHOP;
    } else {
      return null;
    }

  }

  public List<UserPermission> getUserPermissionList(Long userCode) {
    if (NumUtil.isNull(userCode)) {
      return Collections.emptyList();
    }

    UserPermissionDao dao = DIContainer.getDao(UserPermissionDao.class);
    String sql = AuthorizationServiceQuery.LOAD_USER_PERMISSION;
    Object[] params = {
      userCode.toString()
    };

    return dao.findByQuery(sql, params);
  }

  public ServiceResult authorizeCustomer(String loginId, String password) {
    ServiceResultImpl serviceResult = new ServiceResultImpl();
    Customer customer = this.getCustomerByLoginId(loginId);

    if (customer == null || !customer.getCustomerStatus().equals(CustomerStatus.MEMBER.longValue())) {
      serviceResult.addServiceError(AuthorizationServiceErrorContent.CUSTOMER_NOT_FOUND);
      return serviceResult;
    } else if (customer.getLoginLockedFlg().equals(Long.valueOf(LoginLockedFlg.LOCKED.getValue()))) {
      serviceResult.addServiceError(AuthorizationServiceErrorContent.CUSTOMER_LOCK);
      return serviceResult;
    }

    if (PasswordUtil.getDigest(password).equalsIgnoreCase(customer.getPassword())) {
      // 暗号化パスワード認証OK
      customer.setLoginDatetime(DateUtil.getSysdate());
      customer.setLoginErrorCount(0L);
    } else {
      // 認証NG
      customer.setLoginErrorCount(customer.getLoginErrorCount() + 1L);
      if (customer.getLoginErrorCount() >= DIContainer.getWebshopConfig().getAuthorizeCustomerErrorMaxCount()) {
        customer.setLoginLockedFlg(Long.valueOf(LoginLockedFlg.LOCKED.getValue()));
      }
      serviceResult.addServiceError(AuthorizationServiceErrorContent.CUSTOMER_PASSWORD_UNMATCH);
    }
    // 認証結果をDBに登録する
    ServiceResult result = this.registerCustomer(customer);
    //支付宝登陆会员判断
    if(serviceResult.getServiceErrorList().size()==0&&customer.getCustomerKbn()!=null &&customer.getCustomerKbn()==1){
    	serviceResult.addServiceError(AuthorizationServiceErrorContent.CUSTOMER_KBN);
    }
    if (result.hasError()) {
      for (ServiceErrorContent errorContent : serviceResult.getServiceErrorList()) {
        if (errorContent.equals(CommonServiceErrorContent.VALIDATION_ERROR)) {
          serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
          return serviceResult;
        } else if (errorContent.equals(CommonServiceErrorContent.NO_DATA_ERROR)) {
          serviceResult.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
          return serviceResult;
        }
      }
    }
    return serviceResult;
  }

  private UserAccount getUserAccountByLoginId(String shopcode, String loginid) {
    Query query = new SimpleQuery(AuthorizationServiceQuery.LOAD_USER_ACCOUNT, shopcode, loginid);
    return DatabaseUtil.loadAsBean(query, UserAccount.class);
  }

  private ServiceResult registerUserAccount(UserAccount account) {
    Logger logger = Logger.getLogger(this.getClass());
    ServiceResultImpl serviceResult = new ServiceResultImpl();

    List<ValidationResult> resultList = BeanValidator.validate(account).getErrors();
    if (resultList.size() > 0) {
      serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      for (ValidationResult rs : resultList) {
        logger.debug(rs.getFormedMessage());
      }
      return serviceResult;
    }

    UserAccountDao dao = DIContainer.getDao(UserAccountDao.class);
    UserAccount userAccount = dao.load(account.getUserCode());

    if (userAccount == null) {
      serviceResult.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      logger.debug(Messages.log("service.impl.AuthorizationServiceImpl.0"));
      return serviceResult;
    }
    try {
      dao.update(account, getLoginInfo());
    } catch (RuntimeException e) {
      // ログイン日時の更新に失敗したら、ログイン失敗とする
      serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
    }
    return serviceResult;
  }

  private Customer getCustomerByLoginId(String loginId) {
    Query query = new SimpleQuery(AuthorizationServiceQuery.LOAD_CUSTOMER, loginId.toLowerCase(), loginId.toLowerCase());
    return DatabaseUtil.loadAsBean(query, Customer.class);
  }

  private ServiceResult registerCustomer(Customer customer) {
    Logger logger = Logger.getLogger(this.getClass());

    ServiceResultImpl serviceResult = new ServiceResultImpl();

    List<ValidationResult> resultList = BeanValidator.validate(customer).getErrors();
    if (resultList.size() > 0) {
      serviceResult.addServiceError(CommonServiceErrorContent.VALIDATION_ERROR);
      for (ValidationResult rs : resultList) {
        logger.debug(rs.getFormedMessage());
      }
      return serviceResult;
    }

    CustomerDao dao = DIContainer.getDao(CustomerDao.class);
    Customer result = dao.load(customer.getCustomerCode());

    if (result == null) {
      serviceResult.addServiceError(CommonServiceErrorContent.NO_DATA_ERROR);
      logger.debug(Messages.log("service.impl.AuthorizationServiceImpl.1"));
      return serviceResult;
    }

    dao.update(customer, getLoginInfo());

    return serviceResult;

  }

}
