package jp.co.sint.webshop.web.action.back.shop;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.attribute.Precision;
import jp.co.sint.webshop.data.domain.AdvanceLaterFlg;
import jp.co.sint.webshop.data.domain.OperatingMode;
import jp.co.sint.webshop.data.domain.PaymentMethodDisplayType;
import jp.co.sint.webshop.data.domain.PaymentMethodType;
import jp.co.sint.webshop.data.dto.Bank;
import jp.co.sint.webshop.data.dto.Commission;
import jp.co.sint.webshop.data.dto.PaymentMethod;
import jp.co.sint.webshop.data.dto.PostPayment;
import jp.co.sint.webshop.data.dto.Tax;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.ShopManagementService;
import jp.co.sint.webshop.service.SiteManagementService;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.service.shop.PaymentMethodSuite;
import jp.co.sint.webshop.utility.BeanUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.shop.PaymentmethodDetailBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.message.back.ValidationMessage;
import jp.co.sint.webshop.web.message.back.shop.ShopErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

import org.apache.log4j.Logger;

/**
 * U1050520:支払方法詳細のアクションクラスです



 * 
 * @author System Integrator Corp.
 */
public class PaymentmethodDetailRegisterAction extends WebBackAction<PaymentmethodDetailBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。



   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    boolean authorization = false;
    if (getConfig().getOperatingMode().equals(OperatingMode.MALL)
        && Permission.SHOP_MANAGEMENT_UPDATE_SITE.isGranted(getLoginInfo())) {
      authorization = true;
    }
    if (getConfig().getOperatingMode().equals(OperatingMode.MALL)
        && Permission.SHOP_MANAGEMENT_UPDATE_SHOP.isGranted(getLoginInfo())
        && getLoginInfo().getShopCode().equals(getConfig().getSiteShopCode())) {
      authorization = true;
    }
    if (getConfig().getOperatingMode().equals(OperatingMode.SHOP)
        && Permission.SHOP_MANAGEMENT_UPDATE_SITE.isGranted(getLoginInfo())) {
      authorization = true;
    }
    if (getConfig().getOperatingMode().equals(OperatingMode.SHOP)
        && Permission.SHOP_MANAGEMENT_UPDATE_SHOP.isGranted(getLoginInfo())) {
      authorization = true;
    }
    if (getConfig().getOperatingMode().equals(OperatingMode.ONE)
        && Permission.SHOP_MANAGEMENT_UPDATE_SITE.isGranted(getLoginInfo())) {
      authorization = true;
    }

    if (getConfig().getOperatingMode().equals(OperatingMode.ONE)
        && Permission.SHOP_MANAGEMENT_UPDATE_SHOP.isGranted(getLoginInfo())
        && getLoginInfo().getShopCode().equals(getConfig().getSiteShopCode())) {
      authorization = true;
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
    PaymentmethodDetailBean bean = getBean();
    
    boolean isValid = validateBean(bean);
    //Add by V10-CH start
    if (bean.getPaymentType().equals(PaymentMethodType.POSTRAL.getValue())) {
      isValid &= validateBean(bean.getPostOtherInfo());
      if (!bean.getUpdateModeFlg()) {
        isValid &= validateBean(bean.getPostalInfo());
      }   
    } else if (bean.getPaymentType().equals(PaymentMethodType.BANKING.getValue())) {
      isValid &= validateBean(bean.getBankOtherInfo());
      if (!bean.getUpdateModeFlg()) {
        isValid &= validateBean(bean.getBankingInfo());
      }  
    } else if (bean.getPaymentType().equals(PaymentMethodType.CREDITCARD.getValue())
        // v10-ch-pg add start
        || bean.getPaymentType().equals(PaymentMethodType.ALIPAY.getValue())
        || bean.getPaymentType().equals(PaymentMethodType.CHINA_UNIONPAY.getValue())
        // v10-ch-pg add end
        || bean.getPaymentType().equals(PaymentMethodType.CVS_PAYMENT.getValue())
        || bean.getPaymentType().equals(PaymentMethodType.DIGITAL_CASH.getValue())) {
      // 10.1.4 10129 追加 ここから
      if (bean.getPaymentType().equals(PaymentMethodType.CREDITCARD.getValue())) {
        getBean().getOtherInfo().setPaymentLimitDays("");
      }
      // 10.1.4 10129 追加 ここまで
      // v10-ch-pg add start
      if (!bean.getPaymentType().equals(PaymentMethodType.ALIPAY.getValue())) {
        //Alipay以外はサービスIDをクリア
        getBean().getOtherInfo().setServiceId(null);
      } else {
        if (StringUtil.isNullOrEmpty(getBean().getOtherInfo().getServiceId())) {
          addErrorMessage(WebMessage.get(ValidationMessage.REQUIRED_ERROR, Messages
              .getString("web.action.back.shop.PaymentmethodDetailRegisterAction.5")));
          isValid = false;
        }
      }
      // v10-ch-pg add end
      isValid &= validateBean(getBean().getOtherInfo());
    }
    if (bean.getPaymentType().equals(PaymentMethodType.CVS_PAYMENT.getValue())
        || bean.getPaymentType().equals(PaymentMethodType.DIGITAL_CASH.getValue())) {
      if (StringUtil.isNullOrEmpty(bean.getOtherInfo().getPaymentLimitDays())) {
        addErrorMessage(WebMessage.get(ValidationMessage.REQUIRED_ERROR, Messages
            .getString("web.action.back.shop.PaymentmethodDetailRegisterAction.0")));
        isValid = false;
      }
    }
    if (bean.getPaymentType().equals(PaymentMethodType.CVS_PAYMENT.getValue())
        && StringUtil.isNullOrEmpty(bean.getOtherInfo().getCvsEnableType())) {
      addErrorMessage(WebMessage.get(ValidationMessage.REQUIRED_ERROR, Messages
          .getString("web.action.back.shop.PaymentmethodDetailRegisterAction.1")));
      isValid = false;
    }
    if (bean.getPaymentType().equals(PaymentMethodType.DIGITAL_CASH.getValue())
        && StringUtil.isNullOrEmpty(bean.getOtherInfo().getDigitalCashEnableType())) {
      addErrorMessage(WebMessage.get(ValidationMessage.REQUIRED_ERROR, Messages
          .getString("web.action.back.shop.PaymentmethodDetailRegisterAction.2")));
      isValid = false;
    }
    return isValid;
  }

  /**
   * アクションを実行します。



   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    PaymentmethodDetailBean bean = getBean();

    // 登録用共通情報作成



    PaymentMethodSuite paymentMethodSuite = new PaymentMethodSuite();

    PaymentMethod paymentMethod = new PaymentMethod();
    String shopCode = getLoginInfo().getShopCode();

    paymentMethod.setShopCode(shopCode);
    paymentMethod.setPaymentMethodType(bean.getPaymentType());

    if (bean.getPaymentType().equals(PaymentMethodType.CREDITCARD.getValue())
        || bean.getPaymentType().equals(PaymentMethodType.CASH_ON_DELIVERY.getValue())) {
      // クレジットカード・代引のときは後払いにする



      paymentMethod.setAdvanceLaterFlg(NumUtil.toLong(AdvanceLaterFlg.LATER.getValue()));
    } else if (bean.getPaymentType().equals(PaymentMethodType.BANKING.getValue())
        // v10-ch-pg added start
        || bean.getPaymentType().equals(PaymentMethodType.ALIPAY.getValue())
        || bean.getPaymentType().equals(PaymentMethodType.CHINA_UNIONPAY.getValue())
        || bean.getPaymentType().equals(PaymentMethodType.POSTRAL.getValue())
        // v10-ch-pg added end
        || bean.getPaymentType().equals(PaymentMethodType.CVS_PAYMENT.getValue())
        || bean.getPaymentType().equals(PaymentMethodType.DIGITAL_CASH.getValue())) {
      // 銀行振込、コンビニ、電子マネーのときは先払いにする
      paymentMethod.setAdvanceLaterFlg(NumUtil.toLong(AdvanceLaterFlg.ADVANCE.getValue()));
    }
    //Add by V10-CH start
    //邮局支付
    else if(bean.getPaymentType().equals(PaymentMethodType.POSTRAL.getValue())){
      paymentMethod.setAdvanceLaterFlg(NumUtil.toLong(AdvanceLaterFlg.ADVANCE.getValue()));
    }
    //Add by V10-CH end
    else {
      paymentMethod.setAdvanceLaterFlg(NumUtil.toLong(bean.getAdvanceLaterType()));
    }
    paymentMethod.setPaymentMethodNo(NumUtil.toLong(bean.getPaymentMethodNo()));
    paymentMethod.setPaymentMethodName(bean.getPaymentMethodName());
    paymentMethod.setMerchantId(bean.getOtherInfo().getMerchantId());
    paymentMethod.setSecretKey(bean.getOtherInfo().getSecretKey());

    // v10-ch-pg added start
    paymentMethod.setServiceId(bean.getOtherInfo().getServiceId());

    // v10-ch-pg added end
    if (StringUtil.hasValue(bean.getOtherInfo().getPaymentLimitDays())) {
      paymentMethod.setPaymentLimitDays(Long.valueOf(bean.getOtherInfo().getPaymentLimitDays()));
    }
    if (StringUtil.hasValue(bean.getBankOtherInfo().getBankPaymentLimitDays())) {
      paymentMethod.setPaymentLimitDays(Long.valueOf(bean.getBankOtherInfo().getBankPaymentLimitDays()));
    }
    if (StringUtil.hasValue(bean.getPostOtherInfo().getPostPaymentLimitDays())) {
      paymentMethod.setPaymentLimitDays(Long.valueOf(bean.getPostOtherInfo().getPostPaymentLimitDays()));
    }
    if (StringUtil.hasValue(bean.getOtherInfo().getCvsEnableType())
        && bean.getPaymentType().equals(PaymentMethodType.CVS_PAYMENT.getValue())) {
      paymentMethod.setCvsEnableType(Long.valueOf(bean.getOtherInfo().getCvsEnableType()));
    }
    if (StringUtil.hasValue(bean.getOtherInfo().getDigitalCashEnableType())
        && bean.getPaymentType().equals(PaymentMethodType.DIGITAL_CASH.getValue())) {
      paymentMethod.setDigitalCashEnableType(Long.valueOf(bean.getOtherInfo().getDigitalCashEnableType()));
    }
    paymentMethod.setPaymentMethodDisplayType(Long.valueOf(bean.getPaymentMethodDisplayType()));
    paymentMethod.setPaymentCommissionTaxType(Long.valueOf(bean.getPaymentCommissionTaxType()));

    // 消費税率は消費税マスタから取得して自動で入力
    SiteManagementService siteService = ServiceLocator.getSiteManagementService(getLoginInfo());
    Tax tax = siteService.getCurrentTax();
    paymentMethod.setPaymentCommissionTaxRate(tax.getTaxRate());
    paymentMethod.setUpdatedDatetime(bean.getUpdateDate());

    // 订单金额临界值
    paymentMethod.setOrderPriceThreshold(NumUtil.parse(bean.getOrderPriceThreshold()));
    
    paymentMethodSuite.setPaymentMethod(paymentMethod);

    // Commissionデータ1件分作成
    Commission commission = new Commission();

    commission.setShopCode(shopCode);
    commission.setPaymentMethodNo(NumUtil.toLong(bean.getPaymentMethodNo()));

    //String length = WebUtil.buildMaxlength(Commission.class, "paymentPriceThreshold", null);

//    String paymentPriceThreshold = "";
//    for (Long i = 0L; i < NumUtil.toLong(length); i++) {
//      paymentPriceThreshold += "9";
//    }
    Precision precision = BeanUtil.getAnnotation(Commission.class, "paymentPriceThreshold", Precision.class);
    BigDecimal maximum = NumUtil.getActualMaximum(precision.precision(), precision.scale());
    commission.setPaymentPriceThreshold(maximum);

    //modify by V10-CH 170 start
    //commission.setPaymentPriceThreshold(NumUtil.toLong(paymentPriceThreshold));
//    commission.setPaymentPriceThreshold(NumUtil.parse(paymentPriceThreshold));
    //modify by V10-CH 170 end

    if (bean.getPaymentCommission().equals("")) {
      commission.setPaymentCommission(BigDecimal.ZERO);
    } else {
      commission.setPaymentCommission(NumUtil.parse(bean.getPaymentCommission()));
    }

    List<Commission> commissionList = new ArrayList<Commission>();
    commissionList.add(commission);
    paymentMethodSuite.setCommissionList(commissionList);

    ShopManagementService service = ServiceLocator.getShopManagementService(getLoginInfo());

    boolean isUnAvailable = isUnAvailablePayment(paymentMethodSuite, service);
    if (isUnAvailable) {
      addErrorMessage(WebMessage.get(ShopErrorMessage.PAYMENT_UNAVAILABLE));
      setRequestBean(bean);
      return BackActionResult.RESULT_SUCCESS;
    }

    Logger logger = Logger.getLogger(this.getClass());
    if (!bean.getUpdateModeFlg()) {
      Bank bank = new Bank();
      //v10-ch-pg add start
      PostPayment postPayment = new PostPayment();
      //v10-ch-pg add end
      if (bean.getPaymentType().equals(PaymentMethodType.BANKING.getValue())) {
        bank.setShopCode(shopCode);
        bank.setBankCode(bean.getBankingInfo().getBankCode());
        bank.setBankBranchCode(bean.getBankingInfo().getBankBranchCode());
        bank.setAccountNo(bean.getBankingInfo().getAccountNo());
        bank.setBankName(bean.getBankingInfo().getBankName());
        bank.setBankKana(bean.getBankingInfo().getBankKana());
        bank.setBankBranchName(bean.getBankingInfo().getBankBranchName());
        bank.setBankBranchNameKana(bean.getBankingInfo().getBankBranchNameKana());
        bank.setAccountType(Long.valueOf(bean.getBankingInfo().getAccountType()));
        bank.setAccountName(bean.getBankingInfo().getAccountName());
        postPayment = null;
        //v10-ch-pg add start
      } else if (bean.getPaymentType().equals(PaymentMethodType.POSTRAL.getValue())) {
        postPayment.setShopCode(shopCode);
        postPayment.setPostAccountNo(bean.getPostalInfo().getPostAccountNo());
        postPayment.setPostAccountName(bean.getPostalInfo().getPostAccountName());
        //postPayment.setShopCode(bean.getPostalInfo().getShopCode());
        bank = null;
      } else {
        bank = null;
        postPayment = null;
      }
      //v10-ch-pg add end

      clearUnnecessaryValues(paymentMethodSuite.getPaymentMethod()); // 10.1.4
                                                                      // 10129
                                                                      // 追加

      ServiceResult result = service.insertPaymentMethod(paymentMethodSuite, bank, postPayment);
      setRequestBean(bean);
      if (result.hasError()) {
        for (ServiceErrorContent error : result.getServiceErrorList()) {
          if (error.equals(CommonServiceErrorContent.NO_DATA_ERROR)) {
            addWarningMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, Messages
                .getString("web.action.back.shop.PaymentmethodDetailRegisterAction.3")));
            return BackActionResult.RESULT_SUCCESS;
          }
        }
        logger.warn(result.toString());
        return BackActionResult.SERVICE_ERROR;
      }
      setNextUrl("/app/shop/paymentmethod_detail/init/" + shopCode + "/"
          + NumUtil.toString(paymentMethodSuite.getPaymentMethod().getPaymentMethodNo()) + "/new_register");

    } else if (bean.getUpdateModeFlg()) {

      ServiceResult result = service.updatePaymentMethod(paymentMethodSuite);

      if (result.hasError()) {
        setNextUrl(null);
        setRequestBean(bean);
        for (ServiceErrorContent error : result.getServiceErrorList()) {
          if (error.equals(CommonServiceErrorContent.NO_DATA_ERROR)) {
            addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, Messages
                .getString("web.action.back.shop.PaymentmethodDetailRegisterAction.3")));
            return BackActionResult.RESULT_SUCCESS;
          }
        }
        logger.warn(result.toString());
        return BackActionResult.SERVICE_ERROR;
      }
      setRequestBean(bean);
      setNextUrl("/app/shop/paymentmethod_detail/init/" + shopCode + "/" + bean.getPaymentMethodNo() + "/update_register");

    }

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * フロントまたは管理側のそれぞれに、表示可能な支払方法が存在するかをチェックします。



   * 
   * @param paymentMethodSuite
   * @param service
   * @return 入力値にエラーがなければtrue
   */
  private boolean isUnAvailablePayment(PaymentMethodSuite paymentMethodSuite, ShopManagementService service) {
    boolean isUnAvailable = true;
    if (paymentMethodSuite.getPaymentMethod().getPaymentMethodDisplayType().equals(PaymentMethodDisplayType.ALL.longValue())) {
      isUnAvailable = false;
    } else {
      boolean frontDisplay = false;
      boolean backDisplay = false;
      List<PaymentMethod> paymentList = service.getPaymentMethodList(getLoginInfo().getShopCode());
      for (PaymentMethod pm : paymentList) {
        if (pm.getPaymentMethodNo().equals(paymentMethodSuite.getPaymentMethod().getPaymentMethodNo())
            || pm.getPaymentMethodType().equals(PaymentMethodType.NO_PAYMENT.getValue())
            || pm.getPaymentMethodType().equals(PaymentMethodType.POINT_IN_FULL.getValue())) {
          continue;
        }

        if (pm.getPaymentMethodDisplayType().equals(PaymentMethodDisplayType.ALL.longValue())) {
          frontDisplay = true;
          backDisplay = true;
        } else if (pm.getPaymentMethodDisplayType().equals(PaymentMethodDisplayType.FRONT.longValue())) {
          frontDisplay = true;
        } else if (pm.getPaymentMethodDisplayType().equals(PaymentMethodDisplayType.BACK.longValue())) {
          backDisplay = true;
        }
        if (frontDisplay && backDisplay) {
          isUnAvailable = false;
          break;
        }
      }

      if (frontDisplay && !backDisplay) {
        if (paymentMethodSuite.getPaymentMethod().getPaymentMethodDisplayType().equals(PaymentMethodDisplayType.ALL.longValue())) {
          isUnAvailable = false;
        } else if (paymentMethodSuite.getPaymentMethod().getPaymentMethodDisplayType().equals(
            PaymentMethodDisplayType.BACK.longValue())) {
          isUnAvailable = false;
        }
      } else if (!frontDisplay && backDisplay) {
        if (paymentMethodSuite.getPaymentMethod().getPaymentMethodDisplayType().equals(PaymentMethodDisplayType.ALL.longValue())) {
          isUnAvailable = false;
        } else if (paymentMethodSuite.getPaymentMethod().getPaymentMethodDisplayType().equals(
            PaymentMethodDisplayType.FRONT.longValue())) {
          isUnAvailable = false;
        }
      } else if (!frontDisplay && !backDisplay) {
        if (paymentMethodSuite.getPaymentMethod().getPaymentMethodDisplayType().equals(PaymentMethodDisplayType.ALL.longValue())) {
          isUnAvailable = false;
        }
      }
    }
    return isUnAvailable;
  }

  /**
   * Action名の取得
   * 
   * @return Action名



   */
  public String getActionName() {
    return Messages.getString("web.action.back.shop.PaymentmethodDetailRegisterAction.4");
  }

  /**
   * オペレーションコードの取得



   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "4105052003";
  }

  // 10.1.4 10129 追加 ここから
  private void clearUnnecessaryValues(PaymentMethod method) {
    if (method.getPaymentMethodType().equals(PaymentMethodType.BANKING.getValue())
        || method.getPaymentMethodType().equals(PaymentMethodType.CASH_ON_DELIVERY.getValue())) {
      method.setMerchantId(null);
      method.setSecretKey(null);
    }
    if (!method.getPaymentMethodType().equals(PaymentMethodType.CVS_PAYMENT.getValue())
        && !method.getPaymentMethodType().equals(PaymentMethodType.BANKING.getValue()) // 10.1.7 10305 追加
        && !method.getPaymentMethodType().equals(PaymentMethodType.DIGITAL_CASH.getValue())
        && !method.getPaymentMethodType().equals(PaymentMethodType.BANKING.getValue())
        && !method.getPaymentMethodType().equals(PaymentMethodType.POSTRAL.getValue())) {
      method.setPaymentLimitDays(null);
    }
    if (!method.getPaymentMethodType().equals(PaymentMethodType.CVS_PAYMENT.getValue())) {
      method.setCvsEnableType(null);
    }
    if (!method.getPaymentMethodType().equals(PaymentMethodType.DIGITAL_CASH.getValue())) {
      method.setDigitalCashEnableType(null);
    }
  }
  // 10.1.4 10129 追加 ここまで
}
