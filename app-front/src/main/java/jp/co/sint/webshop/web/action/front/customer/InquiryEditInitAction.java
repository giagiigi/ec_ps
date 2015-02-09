package jp.co.sint.webshop.web.action.front.customer;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.data.dto.CommodityHeader;
import jp.co.sint.webshop.data.dto.Customer;
import jp.co.sint.webshop.data.dto.Shop;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.OrderService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ShopManagementService;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.service.customer.CustomerInfo;
import jp.co.sint.webshop.utility.InquiryConfig;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.front.FrontActionResult;
import jp.co.sint.webshop.web.action.front.WebFrontAction;
import jp.co.sint.webshop.web.bean.front.customer.InquiryEditBean;
import jp.co.sint.webshop.web.exception.URLNotFoundException;
import jp.co.sint.webshop.web.login.front.FrontLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.front.ActionErrorMessage;
import jp.co.sint.webshop.web.message.front.CompleteMessage;
import jp.co.sint.webshop.web.message.front.ServiceErrorMessage;
import jp.co.sint.webshop.web.text.front.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U2060410:お問い合わせ入力のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class InquiryEditInitAction extends WebFrontAction<InquiryEditBean> {

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

    String[] path = getRequestParameter().getPathArgs();
    if (path.length > 0 && path[0].equals("complete")) {
      InquiryEditBean nextBean = getBean();

      addInformationMessage(WebMessage.get(CompleteMessage.INQUIRY_COMPLETE));
      nextBean.setReadonlyMode(WebConstantCode.DISPLAY_READONLY);
      nextBean.setConfirmButtonDisplay(false);
      nextBean.setCompleteButtonDisplay(false);
      nextBean.setBackButtonDisplay(false);

      setRequestBean(nextBean);
      return FrontActionResult.RESULT_SUCCESS;
    }
    InquiryEditBean bean = new InquiryEditBean();

    if (path.length > 2) { // 商品or注文に関する問い合わせの場合、対象データの存在チェックを行う
      boolean noData = false;
      String inquiryType = "";
      if (path[0].equals("commodity")) { // 商品についての問い合わせ
        CatalogService catalogSvc = ServiceLocator.getCatalogService(getLoginInfo());
        CommodityHeader commodityHeader = catalogSvc.getCommodityHeader(path[1], path[2]);
        noData = commodityHeader == null;
        inquiryType = Messages.getString("web.action.front.customer.InquiryEditInitAction.0");

        if (!noData) {
          bean.setCommodityInquiryMode(true);
          bean.setCommodityName(commodityHeader.getCommodityName());
          bean.setShopCode(path[1]);
          bean.setCommodityCode(path[2]);
        }
      } else if (path[0].equals("order")) { // 注文・予約についての問い合わせ
        ShopManagementService service = ServiceLocator.getShopManagementService(getLoginInfo());
        Shop shop = service.getShop(path[1]);

        OrderService orderSvc = ServiceLocator.getOrderService(getLoginInfo());
        noData = (orderSvc.getOrder(path[2]).getOrderHeader() == null || shop == null);
        inquiryType = Messages.getString("web.action.front.customer.InquiryEditInitAction.1");

        if (!noData) {
          bean.setOrderInquiryMode(true);
          bean.setShopCode(path[1]);
          bean.setOrderNo(path[2]);
        }
      } else {
        throw new URLNotFoundException(WebMessage.get(ActionErrorMessage.BAD_URL));
      }

      if (noData) {
        addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, inquiryType));
        InquiryEditBean errorBean = getBean();
        errorBean.setConfirmButtonDisplay(false);
        errorBean.setEditMode(WebConstantCode.DISPLAY_READONLY);
        errorBean.setReadonlyMode(WebConstantCode.DISPLAY_READONLY);
        setRequestBean(errorBean);
        return FrontActionResult.RESULT_SUCCESS;
      }
    }

    // セッションから顧客コードを取得
    String customerCode = "";
    FrontLoginInfo login = getLoginInfo();
    customerCode = login.getCustomerCode();

    if (StringUtil.isNullOrEmpty(customerCode)) {
      bean.setEditMode(WebConstantCode.DISPLAY_EDIT);
    } else {
      // 顧客情報を取得
      CustomerService customerSv = ServiceLocator.getCustomerService(getLoginInfo());

      // 顧客存在チェック
      if (customerSv.isNotFound(customerCode) || customerSv.isInactive(customerCode)) {
        setNextUrl("/app/common/index");
        setRequestBean(bean);

        getSessionContainer().logout();
        return FrontActionResult.RESULT_SUCCESS;
      }

      CustomerInfo info = customerSv.getCustomer(customerCode);
      Customer customer = info.getCustomer();

      bean.setCustomerCode(customer.getCustomerCode());
      //modify by V10-CH 170 start
      //bean.setCustomerName(customer.getLastName() + " " + customer.getFirstName());
      bean.setCustomerName(customer.getLastName());
      //modify by V10-CH 170 end
      bean.setEmail(customer.getEmail());
      bean.setEditMode(WebConstantCode.DISPLAY_HIDDEN);
    }

    setRequestBean(bean);
    return FrontActionResult.RESULT_SUCCESS;
  }

  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {
    String[] parameter = getRequestParameter().getPathArgs();
    if (parameter.length > 0 && parameter[0].equals("complete")) {
      return;
    }

    // 問い合わせ件名リストの生成
    UtilService service = ServiceLocator.getUtilService(getLoginInfo());
    InquiryConfig inquiry = service.getInquiryConfig();

    InquiryEditBean bean = (InquiryEditBean) getRequestBean();

    if (bean == null) {
      return;
    }
    bean.getCustomerInquirySubjectList().clear();

    List<String> subjects = new ArrayList<String>();
    String[] path = getRequestParameter().getPathArgs();
    if (path.length > 2 && path[0].equals("commodity")) { // 商品についての問い合わせ
      subjects.add(inquiry.getCommodityInquirySubject());
      bean.setShopName(service.getShopName(path[1]));
    } else if (path.length > 2 && path[0].equals("order")) { // 注文・予約についての問い合わせ
      subjects.add(inquiry.getOrderInquirySubject());
      bean.setShopName(service.getShopName(path[1]));
    } else if (path.length > 0) {
      throw new URLNotFoundException(WebMessage.get(ActionErrorMessage.BAD_URL));
    } else {
      subjects = inquiry.getInquirySubjects();
    }

    List<NameValue> nameValueList = new ArrayList<NameValue>();
    for (int i = 0; i < subjects.size(); i++) {
      NameValue nameValue = new NameValue();
      nameValue.setName(subjects.get(i));
      nameValue.setValue(String.valueOf(i));
      nameValueList.add(nameValue);
    }

    List<CodeAttribute> subjectList = new ArrayList<CodeAttribute>();
    subjectList.addAll(nameValueList);

    // 表示ボタンフラグ設定
    bean.setConfirmButtonDisplay(true);
    bean.setCompleteButtonDisplay(false);
    bean.setBackButtonDisplay(false);
    bean.setReadonlyMode(WebConstantCode.DISPLAY_EDIT);

    bean.setCustomerInquirySubjectList(subjectList);
  }

}
