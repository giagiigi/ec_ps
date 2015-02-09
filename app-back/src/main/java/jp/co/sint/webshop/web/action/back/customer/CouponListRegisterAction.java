package jp.co.sint.webshop.web.action.back.customer;

import jp.co.sint.webshop.data.domain.CouponUsedFlg;
import jp.co.sint.webshop.data.dto.CouponIssue;
import jp.co.sint.webshop.data.dto.CustomerCoupon;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.ShopManagementService;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.customer.CouponListBean;
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
public class CouponListRegisterAction extends CouponListBaseAction {

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
    CouponListBean bean = (CouponListBean) getBean();
    setNextUrl(null);

    if (!validateBean(bean.getEdit())) {
      return false;
    }
    ShopManagementService ss = ServiceLocator.getShopManagementService(getLoginInfo());
    CouponIssue ci = ss.getCouponIssue(getLoginInfo().getShopCode(), NumUtil.toLong(bean.getEdit().getCouponIssueNo()));
    if (ci == null) {
      addErrorMessage(WebMessage.get(CustomerErrorMessage.COUPON_ISSUE_NOT_EXISTS_REGISTER));
      return false;
    } else if (!DateUtil.isPeriodDate(ci.getBonusCouponStartDate(), ci.getBonusCouponEndDate())) {
      addErrorMessage(WebMessage.get(CustomerErrorMessage.OUT_OF_COUPON_ISSUE_DATE));
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

    CustomerCoupon dto = new CustomerCoupon();
    CouponListBean bean = (CouponListBean) getBean();

    if (cs.isNotFound(bean.getCustomerCode()) || cs.isWithdrawed(bean.getCustomerCode())) {
      addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
          Messages.getString("web.action.back.customer.PointHistoryRegisterAction.0")));
      this.setRequestBean(bean);
      return BackActionResult.RESULT_SUCCESS;
    }

    // ポイントシステム使用チェック
    if (!bean.getUsableCouponSystem()) {
      addErrorMessage(WebMessage.get(CustomerErrorMessage.COUPON_SYSTEM_DISABLED_REGISTER));
      this.setRequestBean(bean);
      return BackActionResult.RESULT_SUCCESS;
    }
    // データベース登録処理
    ShopManagementService sms = ServiceLocator.getShopManagementService(getLoginInfo());
    CouponIssue ci = sms.getCouponIssue(getLoginInfo().getShopCode(), NumUtil.toLong(bean.getEdit().getCouponIssueNo()));
    
    dto.setCustomerCode(bean.getCustomerCode());
    dto.setDescription(bean.getEdit().getDescription());
    dto.setCouponIssueNo(NumUtil.toLong(bean.getEdit().getCouponIssueNo()));
    dto.setUseFlg(CouponUsedFlg.ENABLED.longValue());
    dto.setCouponName(ci.getCouponName());
    dto.setCouponPrice(ci.getCouponPrice());
    dto.setIssueDate(DateUtil.getSysdate());
    dto.setUseCouponEndDate(ci.getUseCouponEndDate());
    dto.setUseCouponStartDate(ci.getUseCouponStartDate());
    

    getRequestParameter().get("searchShopCode");

    ServiceResult sResult = cs.insertCustomerCoupon(dto);

    if (sResult.hasError()) {
      return BackActionResult.SERVICE_ERROR;
    } else {
      setNextUrl("/app/customer/coupon_list/complete/" + bean.getCustomerCode() + "/register");
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
