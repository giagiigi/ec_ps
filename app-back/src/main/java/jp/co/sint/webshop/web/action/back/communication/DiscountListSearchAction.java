package jp.co.sint.webshop.web.action.back.communication;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.communication.DiscountHeadLine;
import jp.co.sint.webshop.service.communication.DiscountListSearchCondition;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.validation.BeanValidator;
import jp.co.sint.webshop.validation.ValidationSummary;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.communication.DiscountListBean;
import jp.co.sint.webshop.web.bean.back.communication.DiscountListBean.DiscountDetailBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerUtil;

/**
 * U1061210:限时限量折扣一览画面检索处理
 * 
 * @author KS.
 */
public class DiscountListSearchAction extends WebBackAction<DiscountListBean> {

  private DiscountListSearchCondition condition;

  protected DiscountListSearchCondition getCondition() {
    return PagerUtil.createSearchCondition(getRequestParameter(), condition);
  }

  protected DiscountListSearchCondition getSearchCondition() {
    return this.condition;
  }

  /**
   * 初期処理を実行します
   */
  @Override
  public void init() {
    condition = new DiscountListSearchCondition();
  }

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return Permission.DISCOUNT_READ_SHOP.isGranted(getLoginInfo());
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
    condition.setSearchDiscountStartDatetimeFrom(getBean().getSearchDiscountStartDatetimeFrom());
    condition.setSearchDiscountEndDatetimeTo(getBean().getSearchDiscountEndDatetimeTo());

    if (condition.isValid()) {
      return true;
    } else {
      if (StringUtil.hasValueAllOf(condition.getSearchDiscountStartDatetimeFrom(), condition.getSearchDiscountEndDatetimeTo())) {
        if (!StringUtil.isCorrectRange(condition.getSearchDiscountStartDatetimeFrom(), condition.getSearchDiscountEndDatetimeTo())) {
          addErrorMessage(WebMessage.get(ActionErrorMessage.PERIOD_ERROR_WITH_PARAM, "活动期间"));
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
    DiscountListBean bean = getBean();
    CommunicationService service = ServiceLocator.getCommunicationService(getLoginInfo());
    List<DiscountDetailBean> detailList = new ArrayList<DiscountDetailBean>();

    // 設置查詢條件
    condition = getCondition();
    condition.setSearchDiscountCode(bean.getSearchDiscountCode());
    condition.setSearchDiscountName(bean.getSearchDiscountName());
    condition.setSearchDiscountStartDatetimeFrom(bean.getSearchDiscountStartDatetimeFrom());
    condition.setSearchDiscountEndDatetimeTo(bean.getSearchDiscountEndDatetimeTo());
    condition.setSearchDiscountStatus(bean.getSearchDiscountStatus());
    
    SearchResult<DiscountHeadLine> result = service.getDiscountList(condition);

    // オーバーフローチェック
    prepareSearchWarnings(result, SearchWarningType.BOTH);

    // 清空List
    bean.getList().clear();

    // 將查詢出的数据保存在BEAN中
    for (DiscountHeadLine dhl : result.getRows()) {
      DiscountDetailBean detail = new DiscountDetailBean();
      detail.setDiscountCode(dhl.getDiscountCode());
      detail.setDiscountName(dhl.getDiscountName());
      detail.setDiscountStartDatetime(DateUtil.toDateTimeString(dhl.getDiscountStartDatetime()));
      detail.setDiscountEndDatetime(DateUtil.toDateTimeString(dhl.getDiscountEndDatetime()));
      detail.setCommodityCount(dhl.getCommodityCount());
      
   

      detailList.add(detail);
    }

    bean.setPagerValue(PagerUtil.createValue(result));
    bean.setList(detailList);

    setRequestBean(bean);

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.bean.back.communication.DiscountListSearchAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "5106121003";
  }

}
