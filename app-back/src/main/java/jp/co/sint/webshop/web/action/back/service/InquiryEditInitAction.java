package jp.co.sint.webshop.web.action.back.service;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.data.domain.IbObType;
import jp.co.sint.webshop.data.domain.InquiryStatus;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.customer.CustomerInfo;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.service.InquiryEditBean;
import jp.co.sint.webshop.web.text.back.Messages;

public class InquiryEditInitAction extends WebBackAction<InquiryEditBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return getLoginInfo().hasPermission(Permission.SERVICE_COMPLAINT_DATA_UPDATE);
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    if (StringUtil.hasValue(getCustomerCode())) {
      return true;
    }
    return false;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    InquiryEditBean bean = new InquiryEditBean();

    CustomerService service = ServiceLocator.getCustomerService(getLoginInfo());
    CustomerInfo customerInfo = service.getCustomer(getCustomerCode());
    // 验证会员是否存在
    if (customerInfo == null || customerInfo.getCustomer() == null
        || StringUtil.isNullOrEmpty(customerInfo.getCustomer().getCustomerCode())) {
      setNextUrl("/app/service/member_info/init");
      return BackActionResult.RESULT_SUCCESS;
    }

    bean.setCustomerCode(customerInfo.getCustomer().getCustomerCode());
    bean.setCustomerName(customerInfo.getCustomer().getLastName());
    bean.setInquiryStatus(InquiryStatus.PROCESSING.getValue());
    bean.setIbObType(IbObType.IB.getValue());

    List<CodeAttribute> largeCategoryList = new ArrayList<CodeAttribute>();
    largeCategoryList.add(new NameValue(Messages.getString("web.action.back.service.InquiryEditInitAction.2"), ""));
    for (String largeCategory : DIContainer.getMemberInquiryConfig().getLargeCategory()) {
      largeCategoryList.add(new NameValue(largeCategory, largeCategory));
    }
    bean.setLargeCategoryList(largeCategoryList);
    bean.setCategoryArrayForJs(DIContainer.getMemberInquiryConfig().categoryArray());

    setRequestBean(bean);
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * URLから顧客コードを取得します。<BR>
   * 
   * @return customerCode
   */
  private String getCustomerCode() {
    String[] tmpArgs = getRequestParameter().getPathArgs();
    if (tmpArgs.length > 0) {
      return tmpArgs[0];
    } else {
      return "";
    }
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.service.InquiryEditInitAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "8109041001";
  }
}
