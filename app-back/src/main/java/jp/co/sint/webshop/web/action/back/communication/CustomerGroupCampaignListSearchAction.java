package jp.co.sint.webshop.web.action.back.communication;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.data.domain.CampaignType;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.service.communication.CustomerGroupCampaignHeadLine;
import jp.co.sint.webshop.service.communication.CustomerGroupCampaignSearchCondition;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.validation.BeanValidator;
import jp.co.sint.webshop.validation.ValidationSummary;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.communication.CustomerGroupCampaignListBean;
import jp.co.sint.webshop.web.bean.back.communication.CustomerGroupCampaignListBean.CustomerGroupCampaignDetail;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.webutility.PagerUtil;

/**
 * U1060510:顾客组别优惠规则管理のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class CustomerGroupCampaignListSearchAction extends
    WebBackAction<CustomerGroupCampaignListBean> {

  private CustomerGroupCampaignSearchCondition condition;

  protected CustomerGroupCampaignSearchCondition getCondition() {
    return PagerUtil.createSearchCondition(getRequestParameter(), condition);
  }

  protected CustomerGroupCampaignSearchCondition getSearchCondition() {
    return this.condition;
  }

  /**
   * 初期処理を実行します
   */
  @Override
  public void init() {
    condition = new CustomerGroupCampaignSearchCondition();
  }

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return Permission.CUSTOMER_GROUP_CAMPAIGN_READ_SHOP.isGranted(getLoginInfo());
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    ValidationSummary summary = BeanValidator.validate(getBean());
    if (summary.hasError()) {
      getDisplayMessage().getErrors().addAll(summary.getErrorMessages());
      return false;
    }

    condition = getCondition();
    // 日付の大小関係チェック
    condition.setCampaignStartDateFrom(getBean().getSearchStartDateFrom());
    condition.setCampaignStartDateTo(getBean().getSearchStartDateTo());
    condition.setCampaignEndDateFrom(getBean().getSearchEndDateFrom());
    condition.setCampaignEndDateTo(getBean().getSearchEndDateTo());

    if (condition.isValid()) {
      return true;
    } else {
      if (StringUtil.hasValueAllOf(condition.getCampaignStartDateFrom(), condition
          .getCampaignStartDateTo())) {
        if (!StringUtil.isCorrectRange(condition.getCampaignStartDateFrom(), condition
            .getCampaignStartDateTo())) {
          addErrorMessage(WebMessage.get(ActionErrorMessage.PERIOD_ERROR_WITH_PARAM, "优惠开始时间"));
        }
      }
      if (StringUtil.hasValueAllOf(condition.getCampaignEndDateFrom(), condition
          .getCampaignEndDateTo())) {
        if (!StringUtil.isCorrectRange(condition.getCampaignEndDateFrom(), condition
            .getCampaignEndDateTo())) {
          addErrorMessage(WebMessage.get(ActionErrorMessage.PERIOD_ERROR_WITH_PARAM, "优惠结束时间"));
        }
      }
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
    CustomerGroupCampaignListBean bean = getBean();
    CommunicationService service = ServiceLocator.getCommunicationService(getLoginInfo());
    condition = getCondition();
    condition.setCampaignCode(bean.getSearchCampaignCode());
    condition.setCampaignName(bean.getSearchCampaignName());
    //add by cs_yuli 20120521 start
    condition.setCampaignNameEn(bean.getSearchCampaignNameEn());
    condition.setCampaignNameJp(bean.getSearchCampaignNameJp());
    //add by cs_yuli 20120521 end
    condition.setCampaignStartDateFrom(bean.getSearchStartDateFrom());
    condition.setCampaignStartDateTo(bean.getSearchStartDateTo());
    condition.setCampaignEndDateFrom(bean.getSearchEndDateFrom());
    condition.setCampaignEndDateTo(bean.getSearchEndDateTo());
    condition.setCampaignType(bean.getSearchCampaignType());
    condition.setCustomerGroupCode(bean.getSearchCustomerGroupCode());
    condition.setCampaignStatus(bean.getSearchCampaignStatus());
    SearchResult<CustomerGroupCampaignHeadLine> result = service
        .getCustomerGroupCampaignList(condition);

    // オーバーフローチェック
    prepareSearchWarnings(result, SearchWarningType.BOTH);

    List<CustomerGroupCampaignDetail> detailList = new ArrayList<CustomerGroupCampaignDetail>();
    bean.getList().clear();
    for (CustomerGroupCampaignHeadLine customerGroupCampaignHeadLine : result.getRows()) {
      CustomerGroupCampaignDetail detail = new CustomerGroupCampaignDetail();
      detail.setCampaignCode(customerGroupCampaignHeadLine.getCampaignCode());
      detail.setCampaignName(customerGroupCampaignHeadLine.getCampaignName());
      detail.setCampaignNameEn(customerGroupCampaignHeadLine.getCampaignNameEn());
      detail.setCampaignNameJp(customerGroupCampaignHeadLine.getCampaignNameJp());
      detail.setCampaignType(CampaignType
          .fromValue(customerGroupCampaignHeadLine.getCampaignType()).getName());
      detail.setCustomerGroupName(customerGroupCampaignHeadLine.getCustomerGroupName());
      detail.setCustomerGroupNameEn(customerGroupCampaignHeadLine.getCustomerGroupNameEn());
      detail.setCustomerGroupNameJp(customerGroupCampaignHeadLine.getCustomerGroupNameJp());
      detail.setStartDate(DateUtil.toDateTimeString(customerGroupCampaignHeadLine
          .getCampaignStartDatetime()));
      detail.setEndDate(DateUtil.toDateTimeString(customerGroupCampaignHeadLine
          .getCampaignEndDatetime()));
      detail.setMinOrderAmount(customerGroupCampaignHeadLine.getMinOrderAmount());
      detail.setCampaignAmount(NumUtil.toString(customerGroupCampaignHeadLine.getCampaignAmount()));
      if (CampaignType.FIXED.longValue().equals(customerGroupCampaignHeadLine.getCampaignType())) {
        detail.setFixed(true);
        detail.setCampaignAmount(NumUtil
            .toString(customerGroupCampaignHeadLine.getCampaignAmount()));
      } else {
        detail.setFixed(false);
        detail.setRate(customerGroupCampaignHeadLine.getCampaignProportion());
      }
      // 20140312 hdh add start
      detail.setPersonalUseLimit(NumUtil.toString(customerGroupCampaignHeadLine.getPersonalUseLimit()));
      // 20140312 hdh add end
      detailList.add(detail);
    }
    bean.setPagerValue(PagerUtil.createValue(result));
    bean.setList(detailList);
    // 取得顾客组
    UtilService s = ServiceLocator.getUtilService(getLoginInfo());
    bean.setCustomerGroupList(s.getCustomerGroupNames());
    setRequestBean(bean);

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return "顾客组别优惠规则管理检索处理";
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "5106051003";
  }

}
