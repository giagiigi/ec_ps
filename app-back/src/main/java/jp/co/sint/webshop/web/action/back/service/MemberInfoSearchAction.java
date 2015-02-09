package jp.co.sint.webshop.web.action.back.service;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.data.domain.CustomerStatus;
import jp.co.sint.webshop.data.domain.Sex;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.customer.MemberSearchCondition;
import jp.co.sint.webshop.service.customer.MemberSearchInfo;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.service.MemberInfoBean;
import jp.co.sint.webshop.web.bean.back.service.MemberInfoBean.CustomerSearchedBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerUtil;

public class MemberInfoSearchAction extends WebBackAction<MemberInfoBean> {

  private MemberSearchCondition condition;

  protected MemberSearchCondition getCondition() {
    return PagerUtil.createSearchCondition(getRequestParameter(), condition);
  }

  protected MemberSearchCondition getSearchCondition() {
    return this.condition;
  }

  /**
   * 初期処理を実行します
   */
  @Override
  public void init() {
    getBean().setPageMode(false);
    getBean().setDisplayHistoryMode(StringUtil.EMPTY);
    getBean().setCustomerInfo(new CustomerSearchedBean());
  }
  
  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return getLoginInfo().hasPermission(Permission.SERVICE_USER_DATA_READ);
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    boolean result = true;
    MemberInfoBean bean = getBean();
    // bean(検索条件)のvalidationチェック
    result = validateBean(bean);
    if (!result) {
      bean.setPagerValue(null);
      if (bean.getCustomerList()!=null) {
        bean.getCustomerList().clear();
      }
    }
    return result;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    MemberInfoBean bean = getBean();

    condition = new MemberSearchCondition();
    condition.setSearchMobile(bean.getSearchMobile());
    condition.setSearchTel(bean.getSearchTel());
    condition.setSearchCustomerCode(bean.getSearchCustomerCode());
    condition.setSearchCustomerName(bean.getSearchCustomerName());
    condition.setSearchOrderNo(bean.getSearchOrderNo());
    condition.setSearchEmail(bean.getSearchEmail());
    // soukai add 2012/01/31 ob start
    condition.setSearchDeliverySlipNo(bean.getSearchDeliverySlipNo());
    // soukai add 2012/01/31 ob end
    condition.setCurrentPage(1);
    condition.setPageSize(20);
    condition.setMaxFetchSize(20);

    // 検索結果リストを取得
    CustomerService service = ServiceLocator.getCustomerService(getLoginInfo());
    SearchResult<MemberSearchInfo> result = service.getMemberList(condition);

    // 検索結果0件チェック
    if (result.getRowCount() == 0) {
      addWarningMessage(WebMessage.get(ActionErrorMessage.SEARCH_RESULT_NOT_FOUND));
    }

    // soukai update 2012/01/31 ob start
    // オーバーフローチェック
//    if (result.isOverflow()) {
//      this.addWarningMessage(WebMessage.get(ActionErrorMessage.SEARCH_RESULT_OVERFLOW, NumUtil.formatNumber(""
//          + result.getRowCount()), "" + NumUtil.formatNumber("" + condition.getMaxFetchSize())));
//    }
    
//    bean.setPagerValue(PagerUtil.createValue(result));
    // soukai update 2012/01/31 ob start

    // 結果一覧を作成
    List<MemberSearchInfo> resultList = result.getRows();
    List<CustomerSearchedBean> customerList = new ArrayList<CustomerSearchedBean>();
    for (MemberSearchInfo memberSearchInfo : resultList) {
      CustomerSearchedBean customerBean = new CustomerSearchedBean();
      customerBean.setCustomerCode(memberSearchInfo.getCustomerCode());
      customerBean.setCustomerName(memberSearchInfo.getLastName());
      customerBean.setEmail(memberSearchInfo.getEmail());
      // soukai delete 2012/02/01 ob start
//      customerBean.setMobile(memberSearchInfo.getMobileNumber());
//      customerBean.setTel(memberSearchInfo.getPhoneNumber());
      // soukai delete 2012/02/01 ob end
      CodeAttribute customerStatus = CustomerStatus.fromValue(memberSearchInfo.getCustomerStatus());
      if (customerStatus != null) {
        customerBean.setCustomerStatus(customerStatus.getName());
      }
      CodeAttribute sex = Sex.fromValue(memberSearchInfo.getSex());
      if (sex != null) {
        customerBean.setSex(sex.getName());
      }
      // soukai delete 2012/02/01 ob start
//      customerBean.setPostCode(memberSearchInfo.getPostalCode());
//      customerBean.setAddress(memberSearchInfo.getAddress1() + memberSearchInfo.getAddress2() + memberSearchInfo.getAddress3());
      // soukai delete 2012/02/01 ob end
      customerBean.setCaution(memberSearchInfo.getCaution());
      customerList.add(customerBean);
    }
    
    // soukai add 2012/01/31 ob start
    // 检索结果超过上限时提示错误信息
    if (result.isOverflow()) {
        this.addWarningMessage(WebMessage.get(ActionErrorMessage.SEARCH_RESULT_BEYOND_CAP, NumUtil.formatNumber(""
            + result.getRowCount()), "" + NumUtil.formatNumber("" + condition.getMaxFetchSize())));
        //customerList.clear();
      }
    result.setRowCount(20);
    bean.setPagerValue(PagerUtil.createValue(result));
    // soukai add 2012/01/31 ob end
    bean.setCustomerList(customerList);
    
    if (resultList.size() == 1) {
      setNextUrl("/app/service/member_info/select/" + resultList.get(0).getCustomerCode());
    }

    setRequestBean(bean);

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.service.MemberInfoSearchAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "8109011002";
  }
}
