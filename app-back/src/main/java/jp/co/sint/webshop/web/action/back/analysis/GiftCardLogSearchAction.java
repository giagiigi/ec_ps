package jp.co.sint.webshop.web.action.back.analysis;

import java.math.BigDecimal;

import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.service.CommunicationService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.analysis.GiftCardDetailListSummary;
import jp.co.sint.webshop.service.communication.GiftCardDetailListSearchCondition;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.validation.ValidatorUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.analysis.GiftCardLogBean;
import jp.co.sint.webshop.web.bean.back.analysis.GiftCardLogBean.GiftCardListBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.webutility.PagerUtil;

/**
 * U1060610:PRIVATEクーポン管理のアクションクラスです
 * 
 * @author System OB.
 */
public class GiftCardLogSearchAction extends WebBackAction<GiftCardLogBean> {

  private GiftCardDetailListSearchCondition condition;

  /**
   * 初期処理を実行します
   */
  @Override
  public void init() {
    condition = new GiftCardDetailListSearchCondition();
  }

  protected GiftCardDetailListSearchCondition getCondition() {
    return PagerUtil.createSearchCondition(getRequestParameter(), condition);
  }

  protected GiftCardDetailListSearchCondition getSearchCondition() {
    return this.condition;
  }

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    BackLoginInfo login = getLoginInfo();
    if (null == login) {
      return false;
    }
    return Permission.ANALYSIS_READ_SITE.isGranted(login);
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    GiftCardLogBean bean = getBean();

    condition = getCondition();

    condition.setSearchGiftCardCode(bean.getSearchGiftCardCode());
    condition.setSearchGiftCardName(bean.getSearchGiftCardName());
    condition.setSearchMinIssueStartDatetimeFrom(bean.getSearchMinIssueStartDatetimeFrom());
    condition.setSearchMinIssueStartDatetimeTo(bean.getSearchMinIssueStartDatetimeTo());

    CommunicationService service = ServiceLocator.getCommunicationService(getLoginInfo());

    // 根据查询条件取得顾客别优惠券发行规则信息List
    SearchResult<GiftCardDetailListSummary> result = service.searchGiftCardList_analysis(
        condition, true);

    // 件数0件,オーバーフローチェック
    prepareSearchWarnings(result, SearchWarningType.BOTH);

    bean.setPagerValue(PagerUtil.createValue(result));

    bean.getList().clear();
    // 生成画面一览信息
    
    BigDecimal totalSalePrice = BigDecimal.ZERO;
    BigDecimal totalDenomination = BigDecimal.ZERO;
    BigDecimal activateAmount = BigDecimal.ZERO;
    BigDecimal unactAmount = BigDecimal.ZERO;
    BigDecimal useAmount = BigDecimal.ZERO;
    BigDecimal leftAmount = BigDecimal.ZERO;
    for (GiftCardDetailListSummary row : result.getRows()) {
      
      totalSalePrice = totalSalePrice.add(row.getTotalSalePrice());
      totalDenomination = totalDenomination.add(row.getTotalDenomination());
      activateAmount = activateAmount.add(row.getActivateAmount());
      unactAmount = unactAmount.add(row.getUnactAmount());
      useAmount = useAmount.add(row.getUseAmount());
      leftAmount = leftAmount.add(row.getLeftAmount());
      
      GiftCardListBean detail = new GiftCardListBean();
      detail.setGiftCardCode(row.getCardCode());
      detail.setGiftCardName(row.getCardName());
      detail.setIssueTime( DateUtil.toDateTimeString(row.getIssueTime(),"yyyy-MM-dd HH:mm:ss"));
      detail.setIssueNum(row.getIssueNum().toString());
      detail.setTotalSalePrice(row.getTotalSalePrice().toString());
      detail.setTotalDenomination(row.getTotalDenomination().toString());
      detail.setActivateAmount(row.getActivateAmount().toString());
      detail.setUnactAmount(row.getUnactAmount().toString());
      detail.setUseAmount(row.getUseAmount().toString());
      detail.setLeftAmount(row.getLeftAmount().toString());

      bean.getList().add(detail);
    }
    bean.setTotalSalePrice(totalSalePrice);
    bean.setTotalDenomination(totalDenomination);
    bean.setActivateAmount(activateAmount);
    bean.setUnactAmount(unactAmount);
    bean.setUseAmount(useAmount);
    bean.setLeftAmount(leftAmount);

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
    boolean isValid = true;
    GiftCardLogBean bean = getBean();

    // 検索条件のvalidationチェック
    isValid &= validateBean(bean);

    // 日付の大小関係チェック
    if (isValid) {

      if (StringUtil.hasValueAllOf(bean.getSearchMinIssueStartDatetimeFrom(), bean
          .getSearchMinIssueStartDatetimeTo())) {
        if (!ValidatorUtil.isCorrectOrder(bean.getSearchMinIssueStartDatetimeFrom(), bean
            .getSearchMinIssueStartDatetimeTo())) {
          isValid &= false;
          addErrorMessage(WebMessage.get(ActionErrorMessage.COMPARISON_ERROR, "礼品卡发行时间"));
        }
      }
      
    }

    return isValid;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return "礼品卡发行分析一览画面检索处理";
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "6107111002";
  }

}
