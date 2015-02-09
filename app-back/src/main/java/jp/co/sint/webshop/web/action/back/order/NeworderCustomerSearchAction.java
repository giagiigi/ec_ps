package jp.co.sint.webshop.web.action.back.order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.data.domain.CustomerStatus;
import jp.co.sint.webshop.data.dto.CustomerGroup;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.customer.CustomerSearchCondition;
import jp.co.sint.webshop.service.customer.CustomerSearchInfo;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.order.NeworderCustomerBean;
import jp.co.sint.webshop.web.bean.back.order.NeworderCustomerBean.SearchedCustomerListBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerUtil;

/**
 * U1020120:新規受注（顧客選択）のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class NeworderCustomerSearchAction extends NeworderCustomerBaseAction {

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    NeworderCustomerBean bean = (NeworderCustomerBean) getBean();
    return validateBean(bean);
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    NeworderCustomerBean bean = getBean();

    // SearchConditionの設定
    CustomerSearchCondition condition = new CustomerSearchCondition();
    condition.setSearchCustomerName(bean.getSearchCustomerName());
    condition.setSearchCustomerNameKana(bean.getSearchCustomerNameKana());
    condition.setSearchEmail(bean.getSearchEmail());
    condition.setSearchTel(bean.getSearchPhoneNumber());
    //Add by V10-CH start
    condition.setSearchMobile(bean.getSearchMobileNumber());
    //Add by V10-CH end
    condition.setSearchCustomerGroupCode(bean.getSearchCustomerGroupCode());
    condition.setSearchCustomerStatus(CustomerStatus.MEMBER.getValue());

    PagerUtil.createSearchCondition(getRequestParameter(), condition);

    // 検索結果リストを取得
    CustomerService service = ServiceLocator.getCustomerService(getLoginInfo());
    SearchResult<CustomerSearchInfo> result = service.findCustomer(condition);

    // ページング設定
    bean.setPagerValue(PagerUtil.createValue(result));

    // リスト生成
    List<SearchedCustomerListBean> detailList = new ArrayList<SearchedCustomerListBean>();
    for (CustomerSearchInfo b : result.getRows()) {
      SearchedCustomerListBean detail = new SearchedCustomerListBean();
      detail.setCustomerCode(b.getCustomerCode());
      detail.setCustomerGrouopCode(b.getCustomerGroupCode());
      detail.setLastName(b.getLastName());
      detail.setFirstName(b.getFirstName());
      detail.setLastNameKana(b.getLastNameKana());
      detail.setFirstNameKana(b.getFirstNameKana());
      detail.setEmail(b.getEmail());
      detail.setPhoneNumber(b.getPhoneNumber());
      //Add by V10-CH start
      detail.setMobileNumber(b.getMobileNumber());
      //Add by V10-CH end
      detail.setCaution(b.getCaution());
      detail.setHasCaution(StringUtil.hasValue(b.getCaution()));

      CustomerGroup group = getCustomerGroup(b.getCustomerGroupCode());
      detail.setCustomerGroupName(group.getCustomerGroupName());
      detail.setCustomerGroupPointRate(group.getCustomerGroupPointRate());
      detailList.add(detail);
    }

    bean.setList(detailList);

    setRequestBean(bean);
    getSessionContainer().setTempBean(bean);

    return BackActionResult.RESULT_SUCCESS;
  }

  private Map<String, CustomerGroup> customerGroupMap = new HashMap<String, CustomerGroup>();

  /**
   * DB連続アクセス防止用メソッド
   * 
   * @param customerGroupCode
   * @return 顧客グループ
   */
  private CustomerGroup getCustomerGroup(String customerGroupCode) {
    CustomerService service = ServiceLocator.getCustomerService(getLoginInfo());
    CustomerGroup group = customerGroupMap.get(customerGroupCode);
    if (group == null) {
      group = service.getCustomerGroup(customerGroupCode);
      customerGroupMap.put(customerGroupCode, group);
    }
    return group;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.order.NeworderCustomerSearchAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "1102012003";
  }

}
