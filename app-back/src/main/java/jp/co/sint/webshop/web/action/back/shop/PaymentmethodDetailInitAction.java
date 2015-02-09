package jp.co.sint.webshop.web.action.back.shop;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.data.domain.AdvanceLaterType;
import jp.co.sint.webshop.data.domain.CvsEnableType;
import jp.co.sint.webshop.data.domain.DigitalCashEnableType;
import jp.co.sint.webshop.data.domain.OperatingMode;
import jp.co.sint.webshop.data.domain.PaymentMethodDisplayType;
import jp.co.sint.webshop.data.domain.PaymentMethodType;
import jp.co.sint.webshop.data.domain.TaxType;
import jp.co.sint.webshop.data.dto.PaymentMethod;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ShopManagementService;
import jp.co.sint.webshop.service.shop.PaymentMethodSuite;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.shop.PaymentmethodDetailBean;
import jp.co.sint.webshop.web.bean.back.shop.PaymentmethodDetailBean.BankOtherInfo;
import jp.co.sint.webshop.web.bean.back.shop.PaymentmethodDetailBean.BankingInfo;
import jp.co.sint.webshop.web.bean.back.shop.PaymentmethodDetailBean.OtherInfo;
import jp.co.sint.webshop.web.bean.back.shop.PaymentmethodDetailBean.PostalInfo;
import jp.co.sint.webshop.web.bean.back.shop.PaymentmethodDetailBean.PostOtherInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.CompleteMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1050520:支払方法詳細のアクションクラスです

 * 
 * @author System Integrator Corp.
 */
public class PaymentmethodDetailInitAction extends WebBackAction<PaymentmethodDetailBean> {

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
    if (operatingMode.equals(OperatingMode.SHOP) && Permission.SHOP_MANAGEMENT_READ_SITE.isGranted(getLoginInfo())) {
      authorization = true;
    }
    if (operatingMode.equals(OperatingMode.SHOP) && Permission.SHOP_MANAGEMENT_READ_SHOP.isGranted(getLoginInfo())) {
      authorization = true;
    }
    if (operatingMode.equals(OperatingMode.ONE) && Permission.SHOP_MANAGEMENT_READ_SITE.isGranted(getLoginInfo())) {
      authorization = true;
    }

    if (getConfig().getOperatingMode().equals(OperatingMode.ONE) && Permission.SHOP_MANAGEMENT_READ_SHOP.isGranted(getLoginInfo())
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
    boolean validation = true;

    if (getRequestParameter().getPathArgs().length > 1) {
      String shopCode = getRequestParameter().getPathArgs()[0];
      Long paymentMethodNo = NumUtil.toLong(getRequestParameter().getPathArgs()[1]);
      ShopManagementService service = ServiceLocator.getShopManagementService(getLoginInfo());
      PaymentMethod paymentMethod = service.getPaymentMethod(shopCode, paymentMethodNo).getPaymentMethod();
      if (paymentMethod.getPaymentMethodType().equals(PaymentMethodType.NO_PAYMENT.getValue())
          || paymentMethod.getPaymentMethodType().equals(PaymentMethodType.POINT_IN_FULL.getValue())) {
        validation = false;
        setNextUrl("/app/common/login");
      }
    }

    return validation;
  }

  /**
   * アクションを実行します。

   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    // 共通情報(支払方法区分)作成
    List<NameValue> paymentTypeList = new ArrayList<NameValue>();
    paymentTypeList.add(new NameValue("-----", ""));
    paymentTypeList.add(new NameValue(PaymentMethodType.CASH_ON_DELIVERY.getName(), PaymentMethodType.CASH_ON_DELIVERY.getValue()));
    paymentTypeList.add(new NameValue(PaymentMethodType.BANKING.getName(), PaymentMethodType.BANKING.getValue()));
    //delete by V10-CH start
    // paymentTypeList.add(new NameValue(PaymentMethodType.CREDITCARD.getName(), PaymentMethodType.CREDITCARD.getValue()));
    //delete by V10-CH end
//  v10-ch-pg delete start
//    paymentTypeList.add(new NameValue(PaymentMethodType.CVS_PAYMENT.getName(), PaymentMethodType.CVS_PAYMENT.getValue()));
//    paymentTypeList.add(new NameValue(PaymentMethodType.DIGITAL_CASH.getName(), PaymentMethodType.DIGITAL_CASH.getValue()));
//  v10-ch-pg delete end
    // v10-ch-pg add start
    paymentTypeList.add(new NameValue(PaymentMethodType.ALIPAY.getName(), PaymentMethodType.ALIPAY.getValue()));
    paymentTypeList.add(new NameValue(PaymentMethodType.CHINA_UNIONPAY.getName(), PaymentMethodType.CHINA_UNIONPAY.getValue()));
    paymentTypeList.add(new NameValue(PaymentMethodType.POSTRAL.getName(), PaymentMethodType.POSTRAL.getValue()));
    // v10-ch-pg add end
    paymentTypeList.add(new NameValue(PaymentMethodType.OUTER_CARD.getName(), PaymentMethodType.OUTER_CARD.getValue()));
    
    paymentTypeList.add(new NameValue(PaymentMethodType.INNER_CARD.getName(), PaymentMethodType.INNER_CARD.getValue()));
    
    paymentTypeList.add(new NameValue(PaymentMethodType.WAP_ALIPAY.getName(), PaymentMethodType.WAP_ALIPAY.getValue()));
    
    paymentTypeList.add(new NameValue(PaymentMethodType.JD_ONLINEPAYMENT.getName(), PaymentMethodType.JD_ONLINEPAYMENT.getValue()));

    PaymentmethodDetailBean bean = new PaymentmethodDetailBean();
    bean.setPaymentTypeList(paymentTypeList);

    BankingInfo bankingInfo = new BankingInfo();
    bean.setBankingInfo(bankingInfo);

    OtherInfo otherInfo = new OtherInfo();
    bean.setOtherInfo(otherInfo);

    BankOtherInfo bankOtherInfo = new BankOtherInfo();
    bean.setBankOtherInfo(bankOtherInfo);
    //Add by V10-CH start
    PostalInfo postalInfo = new PostalInfo();
    bean.setPostalInfo(postalInfo);
    PostOtherInfo postalOtherInfo = new PostOtherInfo();
    bean.setPostOtherInfo(postalOtherInfo);
    //Add by V10-CH end
    // 処理完了メッセージ存在時
    if (getRequestParameter().getPathArgs().length > 2) {
      if (getRequestParameter().getPathArgs()[2].equals("new_register")) {
        addInformationMessage(WebMessage.get(CompleteMessage.REGISTER_COMPLETE, Messages
            .getString("web.action.back.shop.PaymentmethodDetailInitAction.0")));
      } else if (getRequestParameter().getPathArgs()[2].equals("update_register")) {
        addInformationMessage(WebMessage.get(CompleteMessage.UPDATE_COMPLETE, Messages
            .getString("web.action.back.shop.PaymentmethodDetailInitAction.0")));
      }
    }

    // URLパラメータに支払方法コードがあるかどうかで登録か更新か判断する。

    // 配列の[0]にはショップコード、[1]には支払方法コードが入る
    if (getRequestParameter().getPathArgs().length > 1) {
      String shopCode = getRequestParameter().getPathArgs()[0];
      String paymentMethodNo = getRequestParameter().getPathArgs()[1];

      ShopManagementService service = ServiceLocator.getShopManagementService(getLoginInfo());
      PaymentMethodSuite methodSuite = service.getPaymentMethod(shopCode, NumUtil.toLong(paymentMethodNo));

      bean.setUpdateModeFlg(true);
      bean.setPaymentType(String.valueOf(methodSuite.getPaymentMethod().getPaymentMethodType()));
      bean.setAdvanceLaterType(String.valueOf(methodSuite.getPaymentMethod().getAdvanceLaterFlg()));
      bean.setPaymentMethodNo(NumUtil.toString(methodSuite.getPaymentMethod().getPaymentMethodNo()));
      bean.setPaymentMethodName(methodSuite.getPaymentMethod().getPaymentMethodName());
      bean.setUpdateDate(methodSuite.getPaymentMethod().getUpdatedDatetime());
      bean.getOtherInfo().setMerchantId(methodSuite.getPaymentMethod().getMerchantId());
      bean.getOtherInfo().setSecretKey(methodSuite.getPaymentMethod().getSecretKey());
      // v10-ch-pg add start
      bean.getOtherInfo().setServiceId(methodSuite.getPaymentMethod().getServiceId());
      // v10-ch-pg add end
      if (methodSuite.getPaymentMethod().getPaymentLimitDays() != null) {
        if (methodSuite.getPaymentMethod().getPaymentMethodType().equals(PaymentMethodType.BANKING.getValue())) {
          bean.getBankOtherInfo().setBankPaymentLimitDays(String.valueOf(methodSuite.getPaymentMethod().getPaymentLimitDays()));
        }else if(methodSuite.getPaymentMethod().getPaymentMethodType().equals(PaymentMethodType.POSTRAL.getValue())){
          bean.getPostOtherInfo().setPostPaymentLimitDays(String.valueOf(methodSuite.getPaymentMethod().getPaymentLimitDays()));
        } 
        else {
           //        M17N 10361 追加 ここから
          if (methodSuite.getPaymentMethod().getPaymentMethodType().equals(PaymentMethodType.ALIPAY.getValue())
              || methodSuite.getPaymentMethod().getPaymentMethodType().equals(PaymentMethodType.CHINA_UNIONPAY.getValue())) {
            bean.getBankOtherInfo().setBankPaymentLimitDays(String.valueOf(methodSuite.getPaymentMethod().getPaymentLimitDays()));
          }else{
          //          M17N 10361 追加 ここまで 
          bean.getOtherInfo().setPaymentLimitDays(String.valueOf(methodSuite.getPaymentMethod().getPaymentLimitDays()));
          }
        }
      }
      if (methodSuite.getPaymentMethod().getCvsEnableType() != null) {
        bean.getOtherInfo().setCvsEnableType(methodSuite.getPaymentMethod().getCvsEnableType().toString());
      }
      if (methodSuite.getPaymentMethod().getDigitalCashEnableType() != null) {
        bean.getOtherInfo().setDigitalCashEnableType(methodSuite.getPaymentMethod().getDigitalCashEnableType().toString());
      }
      bean.setPaymentCommissionTaxType(StringUtil.coalesce(NumUtil.toString(methodSuite.getPaymentMethod()
          .getPaymentCommissionTaxType())));
      if (methodSuite.getPaymentMethod().getPaymentCommissionTaxRate() != null) {
        bean.setPaymentCommissionTaxRate(String.valueOf(methodSuite.getPaymentMethod().getPaymentCommissionTaxRate()));
      }

      if (methodSuite.getCommissionList().size() > 1) {
        bean.setPaymentCommission("");
      } else if (methodSuite.getCommissionList().size() == 1) {
        bean.setPaymentCommission(NumUtil.toString(methodSuite.getCommissionList().get(0).getPaymentCommission()));
      }

      bean.setOrderPriceThreshold(NumUtil.toString(methodSuite.getPaymentMethod().getOrderPriceThreshold()));
      bean.setPaymentMethodName(methodSuite.getPaymentMethod().getPaymentMethodName());
      bean.setPaymentMethodDisplayType(NumUtil.toString(methodSuite.getPaymentMethod().getPaymentMethodDisplayType()));
    } else {

      bean.setUpdateModeFlg(false);
      bean.setPaymentType("");
      bean.setAdvanceLaterType(AdvanceLaterType.ADVANCE.getValue());
      bean.setPaymentMethodNo("");
      bean.setPaymentMethodName("");
      bean.setPaymentCommission("0");
      bean.setPaymentMethodDisplayType(PaymentMethodDisplayType.ALL.getValue());
      bean.setPaymentCommissionTaxType(TaxType.NO_TAX.getValue());
      bean.setPaymentCommissionTaxRate("");
      bean.getBankingInfo().setBankCode("");
      bean.getBankingInfo().setBankName("");
      bean.getBankingInfo().setBankKana("");
      bean.getBankingInfo().setBankBranchCode("");
      bean.getBankingInfo().setBankBranchName("");
      bean.getBankingInfo().setBankBranchNameKana("");
      bean.getBankingInfo().setAccountType("");
      bean.getBankingInfo().setAccountNo("");
      bean.getBankingInfo().setAccountName("");
      bean.getOtherInfo().setMerchantId("");
      bean.getOtherInfo().setSecretKey("");
      // v10-ch-pg add start
      bean.getOtherInfo().setServiceId("");
      // v10-ch-pg add end
      bean.getOtherInfo().setPaymentLimitDays("");
      bean.getOtherInfo().setCvsEnableType(CvsEnableType.ALL.getValue());
      bean.getOtherInfo().setDigitalCashEnableType(DigitalCashEnableType.ALL.getValue());
      //Add by V10-CH start
      bean.getPostalInfo().setPostAccountName("");
      bean.getPostalInfo().setPostAccountNo("");
      //Add by V10-CH end
      bean.setOrderPriceThreshold("0");

    }

    setRequestBean(bean);

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * 画面表示に必要な項目を設定・初期化します。

   */
  @Override
  public void prerender() {

    PaymentmethodDetailBean bean = (PaymentmethodDetailBean) getRequestBean();

    if (Permission.SHOP_MANAGEMENT_UPDATE_SITE.isGranted(getLoginInfo())
        || Permission.SHOP_MANAGEMENT_UPDATE_SHOP.isGranted(getLoginInfo())) {
      bean.setUpdateFlg(true);
    } else {
      bean.setUpdateFlg(false);
    }

    // 支払方法リスト表示・非表示制御

    if (!bean.getUpdateModeFlg()) {
      bean.setRegisterModeDisplay(WebConstantCode.DISPLAY_EDIT);
      bean.setBankMoveButtonFlg(false);
    } else if (bean.getUpdateModeFlg()) {
      bean.setRegisterModeDisplay(WebConstantCode.DISPLAY_HIDDEN);
      bean.setBankMoveButtonFlg(true);
    }

    setRequestBean(bean);
  }

  /**
   * Action名の取得
   * 
   * @return Action名

   */
  public String getActionName() {
    return Messages.getString("web.action.back.shop.PaymentmethodDetailInitAction.1");
  }

  /**
   * オペレーションコードの取得

   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "4105052001";
  }

}
