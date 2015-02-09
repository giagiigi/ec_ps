package jp.co.sint.webshop.web.action.back.analysis;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.data.domain.OperatingMode;
import jp.co.sint.webshop.data.domain.PaymentMethodType;
import jp.co.sint.webshop.data.dto.PaymentMethod;
import jp.co.sint.webshop.data.dto.Shop;
import jp.co.sint.webshop.service.AnalysisService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ShopManagementService;
import jp.co.sint.webshop.service.analysis.CountType;
import jp.co.sint.webshop.service.analysis.SalesAmountByShopSearchCondition;
import jp.co.sint.webshop.service.analysis.SalesAmountByShopSummary;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.utility.UserAgent;
import jp.co.sint.webshop.utility.UserAgentManager;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.analysis.SalesAmountShopBean;
import jp.co.sint.webshop.web.bean.back.analysis.SalesAmountShopBean.SalesAmountShopBeanDetail;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1070820:ショップ別売上集計のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class SalesAmountShopSearchAction extends WebBackAction<SalesAmountShopBean> {

  private List<CodeAttribute> createPaymentMethodList(String shopCode) {
    if (StringUtil.isNullOrEmpty(shopCode)) {
      return new ArrayList<CodeAttribute>();
    }
    ShopManagementService service = ServiceLocator.getShopManagementService(getLoginInfo());

    List<CodeAttribute> paymentMethodList = new ArrayList<CodeAttribute>();
    paymentMethodList.add(new NameValue(Messages.getString(
        "web.action.back.analysis.SalesAmountShopSearchAction.0"), ""));
    // ショップ個別決済かつサイト管理者の場合はショップで使用されている決済方法区分一覧
    if (getConfig().isShop() && shopCode.equals(getConfig().getSiteShopCode())) {
      for (Shop s : service.getShopAll()) {
        for (PaymentMethod p : service.getPaymentMethodList(s.getShopCode())) {
          CodeAttribute payment = PaymentMethodType.fromValue(p.getPaymentMethodType());
          if (!paymentMethodList.contains(payment)) {
            paymentMethodList.add(payment);
          }
        }
      }
    } else {
      for (PaymentMethod p : service.getAllPaymentMethodList(shopCode)) {
        CodeAttribute payment = new NameValue(p.getPaymentMethodName(), NumUtil.toString(p.getPaymentMethodNo()));
        paymentMethodList.add(payment);
      }
    }

    return paymentMethodList;
  }

  private List<CodeAttribute> createClientGroupList() {
    UserAgentManager manager = DIContainer.getUserAgentManager();
    List<CodeAttribute> clientGroupList = new ArrayList<CodeAttribute>();

    clientGroupList.add(new NameValue(Messages.getString(
        "web.action.back.analysis.SalesAmountShopSearchAction.1"), ""));
    for (UserAgent ua : manager.getUserAgentList()) {
      clientGroupList.add(new NameValue(ua.getAgentName(), ua.getClientGroup()));
    }

    return clientGroupList;
  }

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    BackLoginInfo login = getLoginInfo();
    return Permission.ANALYSIS_READ_SITE.isGranted(login) || Permission.ANALYSIS_READ_SHOP.isGranted(login);
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    return validateBean(getBean());
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    BackLoginInfo login = getLoginInfo();
    SalesAmountShopBean bean = getBean();

    String shopCode = bean.getShopCode();

    if (login.isShop()) {
      // 自ショップのショップコードでなかったら
      // 自ショップのショップコードにする
      if (!shopCode.equals(login.getShopCode())) {
        shopCode = login.getShopCode();
      }
    }

    bean.setClientGroupList(createClientGroupList());

    // ショップ個別決済モード以外の場合は支払ショップがサイトのため、サイトに関連付いている支払方法一覧を取得
    String paymentShopCode;
    if (getConfig().isShop()) {
      paymentShopCode = shopCode;
    } else {
      paymentShopCode = getConfig().getSiteShopCode();
    }
    bean.setPaymentMethodList(createPaymentMethodList(paymentShopCode));

    SalesAmountByShopSearchCondition searchCondition = new SalesAmountByShopSearchCondition();
    int year = NumUtil.toLong(bean.getSearchYear()).intValue();
    int month = NumUtil.toLong(bean.getSearchMonth()).intValue();
    searchCondition.setSearchYear(year);
    searchCondition.setSearchMonth(month);
    searchCondition.setClientGroup(bean.getClientGroupCondition());

    if (getConfig().getOperatingMode().equals(OperatingMode.SHOP) && shopCode.equals(getConfig().getSiteShopCode())) {
      searchCondition.setPaymentMethodType(bean.getPaymentMethodCondition());
    } else {
      if (StringUtil.hasValue(bean.getPaymentMethodCondition())) {
        searchCondition.setPaymentMethodNo(NumUtil.toLong(bean.getPaymentMethodCondition()));
      }
    }

    searchCondition.setShopCode(shopCode);

    AnalysisService service = ServiceLocator.getAnalysisService(getLoginInfo());

    CountType type = null;

    if (bean.getDisplayMode().equals(SalesAmountShopBean.DISPLAY_MODE_DAY)) {
      type = CountType.DAILY;
    } else if (bean.getDisplayMode().equals(SalesAmountShopBean.DISPLAY_MODE_MONTH)) {
      type = CountType.MONTHLY;
    } else {
      addErrorMessage(WebMessage.get(ActionErrorMessage.SEARCHCONDITION_ERROR));
      return BackActionResult.RESULT_SUCCESS;
    }
    searchCondition.setType(type);

    List<SalesAmountByShopSummary> result = service.getSalesAmountByShop(searchCondition);

    List<SalesAmountShopBeanDetail> detailList = new ArrayList<SalesAmountShopBeanDetail>();
    for (SalesAmountByShopSummary summary : result) {
      SalesAmountShopBeanDetail detail = new SalesAmountShopBeanDetail();
      detail.setCountedDate(summary.getCountedDate());
      detail.setTotalDiscountAmount(NumUtil.toString(summary.getTotalDiscountAmount()));
      detail.setTotalGiftPrice(NumUtil.toString(summary.getTotalGiftPrice()));
      detail.setTotalGiftTax(NumUtil.toString(summary.getTotalGiftTax()));
      detail.setTotalRefund(NumUtil.toString(summary.getTotalRefund()));
      detail.setTotalReturnItemLossMoney(NumUtil.toString(summary.getTotalReturnItemLossMoney()));
      detail.setTotalSalesPrice(NumUtil.toString(summary.getTotalSalesPrice()));
      detail.setTotalSalesPriceTax(NumUtil.toString(summary.getTotalSalesPriceTax()));
      detail.setTotalShippingCharge(NumUtil.toString(summary.getTotalShippingCharge()));
      detail.setTotalShippingChargeTax(NumUtil.toString(summary.getTotalShippingChargeTax()));

      detailList.add(detail);
    }

    bean.setSearchResultDisplay(true);
    bean.setSearchResult(detailList);
    bean.setExportAuthority(Permission.ANALYSIS_DATA_SITE.isGranted(login) || Permission.ANALYSIS_DATA_SHOP.isGranted(login));

    setRequestBean(bean);

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.analysis.SalesAmountShopSearchAction.2");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "6107082003";
  }

}
