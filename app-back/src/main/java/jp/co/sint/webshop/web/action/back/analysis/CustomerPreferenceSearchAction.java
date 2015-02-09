package jp.co.sint.webshop.web.action.back.analysis;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.service.AnalysisService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.analysis.CustomerPreferenceSearchCondition;
import jp.co.sint.webshop.service.analysis.CustomerPreferenceSummary;
import jp.co.sint.webshop.service.analysis.RearrangeType;
import jp.co.sint.webshop.service.analysis.CustomerPreferenceSearchCondition.CustomerAttributeSearchCondition;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.analysis.CustomerPreferenceBean;
import jp.co.sint.webshop.web.bean.back.analysis.CustomerPreferenceBean.CustomerAttributeListBean;
import jp.co.sint.webshop.web.bean.back.analysis.CustomerPreferenceBean.CustomerPreferenceDetailBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.message.back.analysis.AnalysisErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerUtil;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1070310:顧客嗜好分析のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class CustomerPreferenceSearchAction extends WebBackAction<CustomerPreferenceBean> {

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
    CustomerPreferenceBean searchBean = getBean();

    boolean result = validateBean(searchBean);

    if (!result) {
      return result;
    }

    if (!StringUtil.isCorrectRange(searchBean.getSearchYearStart()
        + "/" + searchBean.getSearchMonthStart(), searchBean
        .getSearchYearEnd()
        + "/" + searchBean.getSearchMonthEnd())) {
      result = false;
      addErrorMessage(WebMessage.get(ActionErrorMessage.PERIOD_ERROR));
    }

    if (StringUtil.hasValue(searchBean.getSearchAgeStart()) && StringUtil.hasValue(searchBean.getSearchAgeEnd())) {
      if (!NumUtil.isCorrectRange(searchBean.getSearchAgeStart(), searchBean.getSearchAgeEnd())) {
        result = false;
        addErrorMessage(WebMessage.get(AnalysisErrorMessage.SEARCH_RANGE_ERROR,
            Messages.getString("web.action.back.analysis.CustomerPreferenceSearchAction.0"),
            Messages.getString("web.action.back.analysis.CustomerPreferenceSearchAction.1")));
      }
    }
    for (CustomerAttributeListBean attribute : searchBean.getCustomerAttributeList()) {
      for (String attributeAnswer : attribute.getAttributeAnswerItem()) {
        if (!NumUtil.isNum(attributeAnswer)) {
          result = false;
          addErrorMessage(WebMessage.get(ActionErrorMessage.FORMAT_ERROR,
              Messages.getString("web.action.back.analysis.CustomerPreferenceSearchAction.2")));
        }
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
    AnalysisService analysisService = ServiceLocator.getAnalysisService(getLoginInfo());
    CustomerPreferenceSearchCondition condition = new CustomerPreferenceSearchCondition();
    CustomerPreferenceBean bean = getBean();

    condition.setSexcondition(bean.getSexCondition());
    if (StringUtil.hasValue(bean.getSearchAgeStart())) {
      condition.setSearchAgeStart(NumUtil.toLong(bean.getSearchAgeStart()));
    }
    if (StringUtil.hasValue(bean.getSearchAgeEnd())) {
      condition.setSearchAgeEnd(NumUtil.toLong(bean.getSearchAgeEnd()));
    }
    condition.setSearchYearStart(Integer.parseInt(bean.getSearchYearStart()));
    condition.setSearchYearEnd(Integer.parseInt(bean.getSearchYearEnd()));
    condition.setSearchMonthStart(Integer.parseInt(bean.getSearchMonthStart()));
    condition.setSearchMonthEnd(Integer.parseInt(bean.getSearchMonthEnd()));
    RearrangeType rearrange = RearrangeType.fromValue(bean.getRearrangeTypeCondition());
    if (rearrange == null) {
      // 並べ替え条件不正の場合は受注件数順
      rearrange = RearrangeType.ORDER_BY_ORDER_COUNT;
    }
    condition.setRearrangeTypeCondition(rearrange);
    condition.setCustomerGroupCondition(bean.getCustomerGroupCondition());

    if (bean.getAdvancedSearchMode().equalsIgnoreCase(WebConstantCode.VALUE_TRUE)) {
      List<CustomerAttributeSearchCondition> attributeConditionList = new ArrayList<CustomerAttributeSearchCondition>();
      for (CustomerAttributeListBean ca : bean.getCustomerAttributeList()) {
        if (StringUtil.isNullOrEmpty(ca.getCustomerAttributeNo())) {
          continue;
        }
        CustomerAttributeSearchCondition attribute = new CustomerAttributeSearchCondition();

        attribute.setCustomerAttributeNo(ca.getCustomerAttributeNo());

        List<String> attributeItem = new ArrayList<String>();

        for (String s : ca.getAttributeAnswerItem()) {
          attributeItem.add(s);
        }
        attribute.setAttributeAnswerItem(attributeItem);

        attributeConditionList.add(attribute);
      }
      condition.setCustomerAttributeCondition(attributeConditionList);
    }
    PagerUtil.createSearchCondition(getRequestParameter(), condition);
    SearchResult<CustomerPreferenceSummary> searchResult = analysisService.getCustomerPreference(condition);

    bean.setPagerValue(PagerUtil.createValue(searchResult));

    if (searchResult.getRowCount() == 0) {
      // 検索結果が0件のときは結果をクリア
      bean.setSearchResult(new ArrayList<CustomerPreferenceDetailBean>());
      // 10.1.4 10167 追加 ここから
      bean.setTotalCustomerCount("0");
      bean.setTotalOrderCount("0");
      // 10.1.4 10167 追加 ここまで
      setRequestBean(bean);
      addWarningMessage(WebMessage.get(ActionErrorMessage.SEARCH_RESULT_NOT_FOUND));
      return BackActionResult.RESULT_SUCCESS;
    } else if (searchResult.isOverflow()) {
      addWarningMessage(WebMessage.get(ActionErrorMessage.SEARCH_RESULT_OVERFLOW, NumUtil.formatNumber(""
          + searchResult.getRowCount()), "" + NumUtil.formatNumber("" + condition.getMaxFetchSize())));
    }

    long totalCustomerCount = 0L;
    long totalOrderCount = 0L;

    List<CustomerPreferenceDetailBean> detailList = new ArrayList<CustomerPreferenceDetailBean>();
    for (CustomerPreferenceSummary c : searchResult.getRows()) {
      CustomerPreferenceDetailBean detail = new CustomerPreferenceDetailBean();
      detail.setShopCode(c.getShopCode());
      detail.setShopName(c.getShopName());
      detail.setCommodityCode(c.getCommodityCode());
      detail.setCommodityName(c.getCommodityName());
      detail.setTotalOrderCount(NumUtil.toString(c.getTotalOrderCount()));
      detail.setTotalOrderCountRatio(c.getTotalOrderCountRatio());
      String graph = "" + Math.round(Double.parseDouble(c.getTotalOrderCountRatio()) / 2);
      detail.setGraphCount(graph);
      detail.setPurchasingAmount(c.getPurchasingAmount());

      totalCustomerCount += c.getTotalCustomerCount();
      totalOrderCount += c.getTotalOrderCount();

      detailList.add(detail);
    }
    bean.setTotalCustomerCount(NumUtil.toString(totalCustomerCount));
    bean.setTotalOrderCount(NumUtil.toString(totalOrderCount));

    bean.setSearchResult(detailList);

    setRequestBean(bean);

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.analysis.CustomerPreferenceSearchAction.3");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "6107031003";
  }

}
