package jp.co.sint.webshop.web.action.back.shop;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.domain.OperatingMode;
import jp.co.sint.webshop.data.domain.PaymentMethodType;
import jp.co.sint.webshop.data.domain.PointFunctionEnabledFlg;
import jp.co.sint.webshop.data.domain.TaxType;
import jp.co.sint.webshop.data.dto.Commission;
import jp.co.sint.webshop.data.dto.PaymentMethod;
import jp.co.sint.webshop.data.dto.PointRule;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ShopManagementService;
import jp.co.sint.webshop.service.SiteManagementService;
import jp.co.sint.webshop.service.shop.PaymentMethodSuite;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.shop.PaymentmethodListBean;
import jp.co.sint.webshop.web.bean.back.shop.PaymentmethodListBean.PaymentCommissionDetail;
import jp.co.sint.webshop.web.bean.back.shop.PaymentmethodListBean.PaymentMethodDetail;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.CompleteMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1050510:支払方法一覧のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class PaymentmethodListPaymentAction extends PaymentMethodListBaseAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    boolean authorization = false;
    OperatingMode operatingMode = getConfig().getOperatingMode();

    if (operatingMode.equals(OperatingMode.MALL) && Permission.SHOP_MANAGEMENT_READ_SITE.isGranted(getLoginInfo())) {
      authorization = true;
    }

    if (operatingMode.equals(OperatingMode.MALL) && Permission.SHOP_MANAGEMENT_READ_SHOP.isGranted(getLoginInfo())
        && getLoginInfo().getShopCode().equals(getConfig().getSiteShopCode())) {
      authorization = true;
    }
    if (operatingMode.equals(OperatingMode.SHOP) && Permission.SHOP_MANAGEMENT_READ_SHOP.isGranted(getLoginInfo())) {
      authorization = true;
    }
    if (operatingMode.equals(OperatingMode.ONE) && Permission.SHOP_MANAGEMENT_READ_SITE.isGranted(getLoginInfo())) {
      authorization = true;
    }

    if (operatingMode.equals(OperatingMode.ONE) && Permission.SHOP_MANAGEMENT_READ_SHOP.isGranted(getLoginInfo())
        && getLoginInfo().getShopCode().equals(getConfig().getSiteShopCode())) {
      authorization = true;
    }

    if (getLoginInfo().isShop() && getRequestParameter().getPathArgs().length > 0) {

      String shopCode = getRequestParameter().getPathArgs()[0];
      if (StringUtil.isNullOrEmpty(shopCode)) {
        return false;
      }

      authorization &= shopCode.equals(getLoginInfo().getShopCode());
    }

    return authorization;
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    return true;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    // URLパラメータよりショップコードと支払方法コードを取得
    String shopCode = "";
    String paymentMethodNo = "";
    if (getRequestParameter().getPathArgs().length > 1) {
      shopCode = getRequestParameter().getPathArgs()[0];
      paymentMethodNo = getRequestParameter().getPathArgs()[1];
    }

    ShopManagementService service = ServiceLocator.getShopManagementService(getLoginInfo());

    PaymentMethodSuite paymentSuite = service.getPaymentMethod(shopCode, NumUtil.toLong(paymentMethodNo));
    if (paymentSuite.getPaymentMethod() == null) {
      addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
          Messages.getString("web.action.back.shop.PaymentmethodListPaymentAction.0")));
      setRequestBean(getBean());
      return BackActionResult.RESULT_SUCCESS;
    }

    PaymentMethod paymentMethod = paymentSuite.getPaymentMethod();

    List<Commission> commissionList = paymentSuite.getCommissionList();
    List<PaymentCommissionDetail> paymentCommissionList = new ArrayList<PaymentCommissionDetail>();

    String previousFromPrice = "0";
    for (Commission commission : commissionList) {
      PaymentCommissionDetail commissionDetail = new PaymentCommissionDetail();
      commissionDetail.setOrmRowid(commission.getOrmRowid().toString());
      commissionDetail.setPriceFrom(previousFromPrice);
      commissionDetail.setPriceTo(NumUtil.toString(commission.getPaymentPriceThreshold()));
      commissionDetail.setPaymentCommission(NumUtil.toString(commission.getPaymentCommission()));
      commissionDetail.setUpdatedDatetime(commission.getUpdatedDatetime());
      paymentCommissionList.add(commissionDetail);
      //modify by V10-CH start
      //previousFromPrice = NumUtil.toString(commission.getPaymentPriceThreshold() + 1);
      previousFromPrice = NumUtil.toString(commission.getPaymentPriceThreshold().add(BigDecimal.ONE));
      //modify by V10-CH end
    }
    PaymentmethodListBean bean = getBean();
    bean.setPaymentMethodList(getPaymentMethodDetailList(getLoginInfo().getShopCode()));
    bean.setCommissionList(paymentCommissionList);
    bean.setShopCode(shopCode);
    bean.setPaymentMethodNo(paymentMethodNo);
    bean.setCommissionPaymentMethodName(paymentMethod.getPaymentMethodName());
    bean.setCommissionTaxTypeName(TaxType.fromValue(NumUtil.toString(paymentMethod.getPaymentCommissionTaxType())).getName());
    PaymentCommissionDetail commissionRegister = new PaymentCommissionDetail();
    bean.setCommissionRegister(commissionRegister);

    // 処理完了時にメッセージを表示
    if (getRequestParameter().getPathArgs().length > 2) {
      String information = "";

      if (getRequestParameter().getPathArgs()[2].equals(WebConstantCode.COMPLETE_INSERT + "_commission")) {
        information = WebMessage.get(CompleteMessage.REGISTER_COMPLETE,
            Messages.getString("web.action.back.shop.PaymentmethodListPaymentAction.1"));
      }
      if (getRequestParameter().getPathArgs()[2].equals(WebConstantCode.COMPLETE_DELETE + "_commission")) {
        information = WebMessage.get(CompleteMessage.DELETE_COMPLETE,
            Messages.getString("web.action.back.shop.PaymentmethodListPaymentAction.1"));
      }
      if (getRequestParameter().getPathArgs()[2].equals(WebConstantCode.COMPLETE_UPDATE + "_commission")) {
        information = WebMessage.get(CompleteMessage.UPDATE_COMPLETE,
            Messages.getString("web.action.back.shop.PaymentmethodListPaymentAction.1"));
      }
      addInformationMessage(information);
    }

    setRequestBean(bean);

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {
    PaymentmethodListBean requestBean = (PaymentmethodListBean) getRequestBean();

    // エラーが発生していた場合、表示情報設定処理を行わない
    if (getDisplayMessage().getErrors().size() > 0) {
      return;
    }

    requestBean.setPaymentMethodDetailDisplay(WebConstantCode.DISPLAY_BLOCK);

    boolean deleteButtonFlg = false;
    boolean registerFlg = false;
    boolean referFlg = false;
    boolean pointUseFlg = false;

    if (Permission.SHOP_MANAGEMENT_READ_SITE.isGranted(getLoginInfo())
        || Permission.SHOP_MANAGEMENT_READ_SHOP.isGranted(getLoginInfo())) {
      referFlg = true;
    }
    if (Permission.SHOP_MANAGEMENT_DELETE_SITE.isGranted(getLoginInfo())
        || Permission.SHOP_MANAGEMENT_DELETE_SHOP.isGranted(getLoginInfo())) {
      deleteButtonFlg = true;
    }
    if (Permission.SHOP_MANAGEMENT_UPDATE_SITE.isGranted(getLoginInfo())
        || Permission.SHOP_MANAGEMENT_UPDATE_SHOP.isGranted(getLoginInfo())) {
      registerFlg = true;
    }

    for (PaymentMethodDetail bean : requestBean.getPaymentMethodList()) {
      if (bean.getPaymentMethodType().equals(PaymentMethodType.CASH_ON_DELIVERY.getValue())) {
        bean.setCommissionDisplayFlg(true);
      }
    }
    SiteManagementService site = ServiceLocator.getSiteManagementService(getLoginInfo());
    PointRule pointRule = site.getPointRule();
    if (pointRule.getPointFunctionEnabledFlg().equals(NumUtil.toLong(PointFunctionEnabledFlg.ENABLED.getValue()))) {
      pointUseFlg = true;
    }

    requestBean.setPointUseFlg(pointUseFlg);
    requestBean.setDeleteButtonFlg(deleteButtonFlg);
    requestBean.setRegisterFlg(registerFlg);
    requestBean.setReferFlg(referFlg);
    setRequestBean(requestBean);
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.shop.PaymentmethodListPaymentAction.2");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "4105051005";
  }

}
