package jp.co.sint.webshop.web.action.back.customer;

import java.math.BigDecimal;

import jp.co.sint.webshop.data.attribute.Length;
import jp.co.sint.webshop.data.domain.PointIssueStatus;
import jp.co.sint.webshop.data.domain.PointIssueType;
import jp.co.sint.webshop.data.dto.Customer;
import jp.co.sint.webshop.data.dto.PointHistory;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.CustomerServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.utility.BeanUtil;
import jp.co.sint.webshop.utility.BigDecimalUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.PointUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.validation.ValidatorUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.customer.PointHistoryBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.message.back.customer.CustomerErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1030140:ポイント履歴のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class PointHistoryRegisterAction extends PointHistoryBaseAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    BackLoginInfo login = getLoginInfo();
    boolean auth = false;
    // 権限チェック
    if (Permission.CUSTOMER_POINT_INVEST.isGranted(login)) {
      auth = true;
    } else {
      auth = false;
    }

    return auth;
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    PointHistoryBean bean = (PointHistoryBean) getBean();
    setNextUrl(null);

    if (!validateBean(bean.getEdit())) {
      return false;
    }
    //modified by zhanghaibin start 2010-05-19
    BigDecimal rest = BigDecimalUtil.divide(NumUtil.parse(bean.getRestPoint(), BigDecimal.ZERO),DIContainer.getWebshopConfig().getPointMultiple());
    //modified by zhanghaibin end   2010-05-19
    BigDecimal issued = NumUtil.parse(getRequestParameter().get("issuedPoint"), BigDecimal.ZERO);
    if (issued.equals(BigDecimal.ZERO)) {
      addErrorMessage(WebMessage.get(CustomerErrorMessage.POINT_INSERT_ZERO_ERROR));
      return false;
    }
    if (ValidatorUtil.moreThan(BigDecimalUtil.add(rest, issued), PointUtil.getCustomerPointLimit())) {
      String maximum = NumUtil.toString(PointUtil.getCustomerPointLimit()); //$NON-NLS-1$
      addErrorMessage(WebMessage.get(CustomerErrorMessage.POINT_OVERFLOW, maximum));
      return false;
    }
    return true;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    CustomerService cs = ServiceLocator.getCustomerService(getLoginInfo());
    BackLoginInfo login = getLoginInfo();

    PointHistory dto = new PointHistory();
    PointHistoryBean bean = (PointHistoryBean) getBean();

    if (cs.isNotFound(bean.getCustomerCode()) || cs.isWithdrawed(bean.getCustomerCode())) {
      addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
          Messages.getString("web.action.back.customer.PointHistoryRegisterAction.0")));
      this.setRequestBean(bean);
      return BackActionResult.RESULT_SUCCESS;
    }

    // ポイントシステム使用チェック
    if (!bean.getUsablePointSystem()) {
      addErrorMessage(WebMessage.get(CustomerErrorMessage.POINT_SYSTEM_DISABLED_REGISTER));
      this.setRequestBean(bean);
      return BackActionResult.RESULT_SUCCESS;
    }
    // データベース登録処理
    dto.setCustomerCode(bean.getCustomerCode());
    dto.setDescription(bean.getEdit().getDescription());
    dto.setPointIssueStatus(PointIssueStatus.ENABLED.longValue());
    dto.setPointIssueType(PointIssueType.ADJUSTMENT.longValue());
    dto.setIssuedPoint(new BigDecimal(bean.getEdit().getIssuedPoint()));
    dto.setShopCode(login.getShopCode());
    dto.setPointIssueDatetime(DateUtil.fromString(DateUtil.getSysdateString()));

    getRequestParameter().get("searchShopCode");

    ServiceResult sResult = cs.insertPointHistory(dto, bean.getUpdatedDatetime());

    if (sResult.hasError()) {
      for (ServiceErrorContent result : sResult.getServiceErrorList()) {
        if (result.equals(CustomerServiceErrorContent.REST_POINT_MINUS_ERROR)) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.REST_POINT_MINUS_ERROR));
          // 10.1.1 10012 削除 ここから
          // return BackActionResult.RESULT_SUCCESS;
          // 10.1.1 10012 削除 ここまで
        } else if (result.equals(CustomerServiceErrorContent.POINT_OVERFLOW_ERROR)) {
          Length len = BeanUtil.getAnnotation(Customer.class, "restPoint", Length.class);
          String maximum = StringUtil.times("9", len.value());
          addErrorMessage(WebMessage.get(CustomerErrorMessage.POINT_OVERFLOW, maximum));
        } else if (result.equals(CustomerServiceErrorContent.POINT_INSERT_FAILURE_ERROR)) {
          addErrorMessage(WebMessage.get(CustomerErrorMessage.POINT_INSERT_FAILURE_ERROR));
        } else if (result.equals(CustomerServiceErrorContent.POINT_SYSTEM_DISABLED_ERROR)) {
          addErrorMessage(WebMessage.get(CustomerErrorMessage.POINT_SYSTEM_DISABLED_REGISTER));
        } else if (result.equals(CommonServiceErrorContent.VALIDATION_ERROR)) {
          return BackActionResult.SERVICE_VALIDATION_ERROR;
        } else {
          return BackActionResult.SERVICE_ERROR;
        }
      }
    } else {
      setNextUrl("/app/customer/point_history/complete/" + bean.getCustomerCode() + "/register");
    }

    this.setRequestBean(bean);

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.customer.PointHistoryRegisterAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "2103014004";
  }

}
