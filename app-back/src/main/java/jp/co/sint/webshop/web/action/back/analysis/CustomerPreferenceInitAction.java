package jp.co.sint.webshop.web.action.back.analysis;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.data.domain.Sex;
import jp.co.sint.webshop.data.dto.CustomerAttribute;
import jp.co.sint.webshop.service.CustomerService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.service.analysis.RearrangeType;
import jp.co.sint.webshop.service.customer.CustomerGroupCount;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.analysis.CustomerPreferenceBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1070310:顧客嗜好分析のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class CustomerPreferenceInitAction extends WebBackAction<CustomerPreferenceBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    // 分析参照_サイトの権限を持つユーザのみアクセス可能
    return Permission.ANALYSIS_READ_SITE.isGranted(getLoginInfo());
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
    CustomerPreferenceBean bean = new CustomerPreferenceBean();

    Date now = DateUtil.getSysdate();

    CustomerService custService = ServiceLocator.getCustomerService(getLoginInfo());
    if (!NumUtil.isNum(bean.getSearchYearStart())) {
      bean.setSearchYearStart(DateUtil.getYYYY(now));
    }
    if (!NumUtil.isNum(bean.getSearchMonthStart())) {
      bean.setSearchMonthStart(DateUtil.getMM(now));
    }
    if (!NumUtil.isNum(bean.getSearchYearEnd())) {
      bean.setSearchYearEnd(DateUtil.getYYYY(now));
    }
    if (!NumUtil.isNum(bean.getSearchMonthEnd())) {
      bean.setSearchMonthEnd(DateUtil.getMM(now));
    }

    if (StringUtil.isNullOrEmpty(bean.getAdvancedSearchMode())) {
      bean.setAdvancedSearchMode(WebConstantCode.VALUE_FALSE);
    }

    // 並べ替え順タイプリストを作成
    List<CodeAttribute> rearrangeList = new ArrayList<CodeAttribute>();
    for (RearrangeType r : RearrangeType.values()) {
      rearrangeList.add(new NameValue(r.getName(), r.getValue()));
    }

    bean.setRearrangeTypeList(rearrangeList);
    UtilService utilService=ServiceLocator.getUtilService(getLoginInfo()); 
    List<CodeAttribute> groupList = new ArrayList<CodeAttribute>();
    groupList.add(new NameValue(Messages.getString(
    "web.action.back.analysis.CustomerPreferenceInitAction.0"), ""));
    for (CustomerGroupCount c : custService.getCustomerGroup()) {
      groupList.add(new NameValue(utilService.getNameByLanguage(c.getCustomerGroupName(),c.getCustomerGroupNameEn(),c.getCustomerGroupNameJp()), c.getCustomerGroupCode()));
    }
    bean.setCustomerGroupList(groupList);

    // 顧客属性選択プルダウンを作成
    List<CustomerAttribute> attributeList = custService.getAttributeList();

    int selection = 3;
    if (attributeList.size() < 3) {
      selection = attributeList.size();
    }

    List<List<CodeAttribute>> customerAttributeSelectList = new ArrayList<List<CodeAttribute>>();
    for (int i = 0; i < selection; i++) {
      List<CodeAttribute> attributePartsList = new ArrayList<CodeAttribute>();

      attributePartsList.add(new NameValue(Messages.getString(
      "web.action.back.analysis.CustomerPreferenceInitAction.0"), ""));
      for (CustomerAttribute c : attributeList) {
        attributePartsList.add(new NameValue(c.getCustomerAttributeName(), NumUtil.toString(c.getCustomerAttributeNo())));
      }
      customerAttributeSelectList.add(attributePartsList);
    }

    bean.setCustomerAttributeSelectList(customerAttributeSelectList);

    bean.setSexCondition(Sex.MALE.getValue());

    // 分析データ出力_サイトを持つユーザか一店舗モードで分析データ参照_サイトを持つユーザのみCSV出力ボタンを表示
    BackLoginInfo login = getLoginInfo();
    boolean hasExportAuthority = Permission.ANALYSIS_DATA_SITE.isGranted(login)
        || (Permission.ANALYSIS_DATA_SHOP.isGranted(login) && getConfig().isOne());
    bean.setDisplayExportButton(hasExportAuthority);

    setRequestBean(bean);

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.analysis.CustomerPreferenceInitAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "6107031002";
  }

}
