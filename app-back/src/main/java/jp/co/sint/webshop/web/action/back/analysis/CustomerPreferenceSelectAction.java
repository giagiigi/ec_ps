package jp.co.sint.webshop.web.action.back.analysis;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.data.dto.CustomerAttribute;
import jp.co.sint.webshop.data.dto.CustomerAttributeChoice;
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
import jp.co.sint.webshop.web.bean.back.analysis.CustomerPreferenceBean.CustomerAttributeListBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1070310:顧客嗜好分析のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class CustomerPreferenceSelectAction extends WebBackAction<CustomerPreferenceBean> {

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
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    CustomerPreferenceBean bean = getBean();

    Date now = DateUtil.getSysdate();
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

    CustomerService custService = ServiceLocator.getCustomerService(getLoginInfo());
    UtilService utilService=ServiceLocator.getUtilService(getLoginInfo()); 
    List<CodeAttribute> groupList = new ArrayList<CodeAttribute>();
    groupList.add(new NameValue(Messages.getString(
    "web.action.back.analysis.CustomerPreferenceSelectAction.0"), ""));
    for (CustomerGroupCount c : custService.getCustomerGroup()) {
    	 groupList.add(new NameValue(utilService.getNameByLanguage(c.getCustomerGroupName(),c.getCustomerGroupNameEn(),c.getCustomerGroupNameJp()), c.getCustomerGroupCode()));
    }
    bean.setCustomerGroupList(groupList);

    // 顧客属性選択プルダウンを作成
    List<CustomerAttribute> attributeList = custService.getAttributeList();

    List<String> attributeNoList = bean.getCustomerAttributeNoList();
    List<List<CodeAttribute>> customerAttributeSelectList = new ArrayList<List<CodeAttribute>>();
    for (String s : attributeNoList) {
      List<CodeAttribute> attributePartsList = new ArrayList<CodeAttribute>();
      attributePartsList.add(new NameValue(Messages.getString(
      "web.action.back.analysis.CustomerPreferenceSelectAction.0"), ""));
      for (CustomerAttribute c : attributeList) {
        String no = NumUtil.toString(c.getCustomerAttributeNo());
        if (no.equals(s)) {
          attributePartsList.add(new NameValue(c.getCustomerAttributeName(), no));
        }
        if (!attributeNoList.contains(no)) {
          attributePartsList.add(new NameValue(c.getCustomerAttributeName(), no));
        }
      }
      customerAttributeSelectList.add(attributePartsList);
    }
    bean.setCustomerAttributeSelectList(customerAttributeSelectList);

    // 顧客属性の条件部分を作成
    List<CustomerAttributeListBean> customerAttributeList = new ArrayList<CustomerAttributeListBean>();
    for (String s : bean.getCustomerAttributeNoList()) {
      CustomerAttributeListBean attributeBean = new CustomerAttributeListBean();
      if (StringUtil.hasValue(s)) {
        for (CustomerAttributeListBean c : bean.getCustomerAttributeList()) {
          if (StringUtil.isNullOrEmpty(c.getCustomerAttributeNo())) {
            continue;
          }
          // 同じ顧客属性番号をもつ属性が既に選択されていた場合は
          // チェックボックスの結果を保存する
          if (c.getCustomerAttributeNo().equals(s)) {
            attributeBean.setAttributeAnswerItem(c.getAttributeAnswerItem());
          }
        }
        CustomerAttribute attribute = custService.getAttribute(NumUtil.toLong(s));
        attributeBean.setCustomerAttributeNo("" + attribute.getCustomerAttributeNo());
        attributeBean.setCustomerAttributeName(attribute.getCustomerAttributeName());

        List<CodeAttribute> attributeChoiceList = new ArrayList<CodeAttribute>();
        for (CustomerAttributeChoice c : custService.getAttributeChoiceList("" + attribute.getCustomerAttributeNo())) {
          attributeChoiceList.add(new NameValue(c.getCustomerAttributeChoices(), "" + c.getCustomerAttributeChoicesNo()));
        }
        attributeBean.setAttributeChoiceList(attributeChoiceList);
      }
      customerAttributeList.add(attributeBean);
    }
    bean.setCustomerAttributeList(customerAttributeList);

    // 分析データ出力_サイトを持つユーザか一店舗モードで分析データ参照_サイトを持つユーザのみCSV出力ボタンを表示
    BackLoginInfo login = getLoginInfo();
    boolean hasExportAuthority = Permission.ANALYSIS_DATA_SITE.isGranted(login)
        || (Permission.ANALYSIS_DATA_SHOP.isGranted(login) && getConfig().isOne());
    bean.setDisplayExportButton(hasExportAuthority);

    setRequestBean(bean);

    return BackActionResult.RESULT_SUCCESS;
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
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.analysis.CustomerPreferenceSelectAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "6107031004";
  }

}
