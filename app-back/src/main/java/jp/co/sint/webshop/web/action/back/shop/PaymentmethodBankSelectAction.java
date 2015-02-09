package jp.co.sint.webshop.web.action.back.shop;

import jp.co.sint.webshop.data.domain.OperatingMode;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.shop.PaymentmethodBankBean;
import jp.co.sint.webshop.web.bean.back.shop.PaymentmethodBankBean.BankDetail;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1050530:金融機関設定のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class PaymentmethodBankSelectAction extends WebBackAction<PaymentmethodBankBean> {

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

    if (getConfig().getOperatingMode().equals(OperatingMode.MALL) && Permission.SHOP_MANAGEMENT_READ_SHOP.isGranted(getLoginInfo())
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
    PaymentmethodBankBean bean = getBean();
    String bankCode = bean.getRegisterBank().getBankCode();
    String bankBranchCode = bean.getRegisterBank().getBankBranchCode();
    String accountNo = bean.getRegisterBank().getAccountNo();

    BankDetail registerBankDetail = new BankDetail();

    for (BankDetail detail : bean.getBankList()) {
      if (detail.getBankCode().equals(bankCode) && detail.getBankBranchCode().equals(bankBranchCode)
          && detail.getAccountNo().equals(accountNo)) {
        registerBankDetail.setBankCode(detail.getBankCode());
        registerBankDetail.setBankName(detail.getBankName());
        registerBankDetail.setBankKana(detail.getBankKana());
        registerBankDetail.setBankBranchCode(detail.getBankBranchCode());
        registerBankDetail.setBankBranchName(detail.getBankBranchName());
        registerBankDetail.setBankBranchNameKana(detail.getBankBranchNameKana());
        registerBankDetail.setAccountType(detail.getAccountType());
        registerBankDetail.setAccountNo(detail.getAccountNo());
        registerBankDetail.setAccountName(detail.getAccountName());
        registerBankDetail.setUpdatedDatetime(detail.getUpdatedDatetime());
      }
    }

    bean.setRegisterBank(registerBankDetail);
    bean.setProcessMode(WebConstantCode.PROCESS_UPDATE);

    setRequestBean(bean);

    return BackActionResult.RESULT_SUCCESS;
  }


  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.shop.PaymentmethodBankSelectAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "4105053004";
  }

}
