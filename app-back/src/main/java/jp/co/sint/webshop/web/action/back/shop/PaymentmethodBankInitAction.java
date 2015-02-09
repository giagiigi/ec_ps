package jp.co.sint.webshop.web.action.back.shop;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.domain.OperatingMode;
import jp.co.sint.webshop.data.dto.Bank;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ShopManagementService;
import jp.co.sint.webshop.service.shop.PaymentMethodSuite;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.shop.PaymentmethodBankBean;
import jp.co.sint.webshop.web.bean.back.shop.PaymentmethodBankBean.BankDetail;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.message.back.CompleteMessage;
import jp.co.sint.webshop.web.message.back.shop.ShopErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1050530:金融機関設定のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class PaymentmethodBankInitAction extends WebBackAction<PaymentmethodBankBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    boolean authorization = false;

    OperatingMode operataingMode = getConfig().getOperatingMode();

    if (operataingMode.equals(OperatingMode.MALL) && Permission.SHOP_MANAGEMENT_READ_SITE.isGranted(getLoginInfo())) {
      authorization = true;
    }

    if (operataingMode.equals(OperatingMode.MALL) && Permission.SHOP_MANAGEMENT_READ_SHOP.isGranted(getLoginInfo())
        && getLoginInfo().getShopCode().equals(getConfig().getSiteShopCode())) {
      authorization = true;
    }

    if (operataingMode.equals(OperatingMode.SHOP) && Permission.SHOP_MANAGEMENT_READ_SITE.isGranted(getLoginInfo())) {
      authorization = true;
    }

    if (operataingMode.equals(OperatingMode.SHOP) && Permission.SHOP_MANAGEMENT_READ_SHOP.isGranted(getLoginInfo())) {
      authorization = true;
    }

    if (operataingMode.equals(OperatingMode.ONE) && Permission.SHOP_MANAGEMENT_READ_SITE.isGranted(getLoginInfo())) {
      authorization = true;
    }

    if (operataingMode.equals(OperatingMode.ONE) && Permission.SHOP_MANAGEMENT_READ_SHOP.isGranted(getLoginInfo())
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
      String paymentMethodNo = getRequestParameter().getPathArgs()[1];
      ShopManagementService service = ServiceLocator.getShopManagementService(getLoginInfo());

      PaymentMethodSuite paymentMethodSuite = service.getPaymentMethod(shopCode, NumUtil.toLong(paymentMethodNo));

      if (paymentMethodSuite.getPaymentMethod() == null) {
        validation = false;
        addErrorMessage(WebMessage.get(ShopErrorMessage.NO_PAYMENT_ERROR));
      }

    } else {
      validation = false;
      addErrorMessage(WebMessage.get(ActionErrorMessage.BAD_URL));
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
    String shopCode = "";
    String paymentMethodNo = "";
    String completeInformation = "";

    // URLパラメータには[0]にショップコード、[1]に支払方法コード、処理完了時は[2]に完了情報がセットされる
    if (getRequestParameter().getPathArgs().length > 2) {
      shopCode = getRequestParameter().getPathArgs()[0];
      paymentMethodNo = getRequestParameter().getPathArgs()[1];
      completeInformation = getRequestParameter().getPathArgs()[2];
    } else if (getRequestParameter().getPathArgs().length > 1) {
      shopCode = getRequestParameter().getPathArgs()[0];
      paymentMethodNo = getRequestParameter().getPathArgs()[1];
    }

    ShopManagementService service = ServiceLocator.getShopManagementService(getLoginInfo());
    List<Bank> bankListResult = service.getBankList(shopCode, NumUtil.toLong(paymentMethodNo));

    // 取得した金融機関リストをbean用のリストに1件づつセット
    List<BankDetail> bankList = new ArrayList<BankDetail>();
    for (Bank bank : bankListResult) {
      BankDetail bankDetail = new BankDetail();
      bankDetail.setBankCode(bank.getBankCode());
      bankDetail.setBankName(bank.getBankName());
      bankDetail.setBankKana(bank.getBankKana());
      bankDetail.setBankBranchCode(bank.getBankBranchCode());
      bankDetail.setBankBranchName(bank.getBankBranchName());
      bankDetail.setBankBranchNameKana(bank.getBankBranchNameKana());
      bankDetail.setAccountType(StringUtil.coalesce(bank.getAccountType().toString(), ""));
      bankDetail.setAccountNo(bank.getAccountNo());
      bankDetail.setAccountName(bank.getAccountName());
      bankDetail.setUpdatedDatetime(bank.getUpdatedDatetime());
      bankList.add(bankDetail);
    }

    PaymentmethodBankBean bean = new PaymentmethodBankBean();
    bean.setShopCode(shopCode);
    bean.setPaymentMethodNo(paymentMethodNo);
    bean.setBankList(bankList);

    setRequestBean(bean);

    if (completeInformation.equals(WebConstantCode.COMPLETE_REGISTER)) {
      addInformationMessage(WebMessage.get(CompleteMessage.REGISTER_COMPLETE,
          Messages.getString("web.action.back.shop.PaymentmethodBankInitAction.0")));
    } else if (completeInformation.equals(WebConstantCode.COMPLETE_DELETE)) {
      addInformationMessage(WebMessage.get(CompleteMessage.DELETE_COMPLETE,
          Messages.getString("web.action.back.shop.PaymentmethodBankInitAction.0")));
    }

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {
    PaymentmethodBankBean bean = (PaymentmethodBankBean) getRequestBean();

    boolean buttonDisplayFlg = false;
    String displayMode = WebConstantCode.DISPLAY_HIDDEN;

    OperatingMode mode = getConfig().getOperatingMode();
    if (bean.getBankList().size() <= 1) {
      bean.setDeleteButtonDisplayFlg(false);
    } else if (mode.equals(OperatingMode.MALL) || mode.equals(OperatingMode.ONE)) {
      bean.setDeleteButtonDisplayFlg(Permission.SHOP_MANAGEMENT_DELETE_SITE.isGranted(getLoginInfo()));
    } else {
      bean.setDeleteButtonDisplayFlg(Permission.SHOP_MANAGEMENT_DELETE_SHOP.isGranted(getLoginInfo()));
    }

    if (Permission.SHOP_MANAGEMENT_UPDATE_SITE.isGranted(getLoginInfo())
        || Permission.SHOP_MANAGEMENT_UPDATE_SHOP.isGranted(getLoginInfo())) {
      buttonDisplayFlg = true;
      displayMode = WebConstantCode.DISPLAY_EDIT;
    }

    bean.setButtonDisplayFlg(buttonDisplayFlg);
    bean.setDisplayMode(displayMode);
    bean.setProcessMode(WebConstantCode.PROCESS_INSERT);

    setRequestBean(bean);
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.shop.PaymentmethodBankInitAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "4105053002";
  }

}
