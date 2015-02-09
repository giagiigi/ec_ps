package jp.co.sint.webshop.web.action.back.analysis;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.service.AnalysisService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.analysis.GiftCardUseLogSearchCondition;
import jp.co.sint.webshop.service.analysis.GiftCardUseLogSummary;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.validation.BeanValidator;
import jp.co.sint.webshop.validation.ValidationResult;
import jp.co.sint.webshop.validation.ValidatorUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.analysis.GiftCardUseLogBean;
import jp.co.sint.webshop.web.bean.back.analysis.GiftCardUseLogBean.GiftCardUseListBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerUtil;

import org.apache.log4j.Logger;

/**
 * U1070830:商品別売上集計のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class GiftCardUseLogSearchAction extends WebBackAction<GiftCardUseLogBean> {

  
  private GiftCardUseLogSearchCondition condition;

  protected GiftCardUseLogSearchCondition getCondition() {
    return PagerUtil.createSearchCondition(getRequestParameter(), condition);
  }

  protected GiftCardUseLogSearchCondition getSearchCondition() {
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
    if (Permission.ANALYSIS_READ_SITE.isGranted(login) || Permission.ANALYSIS_READ_SHOP.isGranted(login)) {
      return true;
    }
    return false;
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    boolean isValid = true;
    GiftCardUseLogBean bean = getBean();

    // 検索条件のvalidationチェック
    isValid &= validateBean(bean);

    // 日付の大小関係チェック
    if (isValid) {

      if (StringUtil.hasValueAllOf(bean.getSearchShippingStartDatetimeFrom(), bean
          .getSearchShippingStartDatetimeTo())) {
        if (!ValidatorUtil.isCorrectOrder(bean.getSearchShippingStartDatetimeFrom(), bean
            .getSearchShippingStartDatetimeTo())) {
          isValid &= false;
          addErrorMessage(WebMessage.get(ActionErrorMessage.COMPARISON_ERROR, "发行时间"));
        }
      }
      
    }

    return isValid;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    Logger logger = Logger.getLogger(this.getClass());
    GiftCardUseLogBean requestBean = getBean();

    GiftCardUseLogSearchCondition condition = new GiftCardUseLogSearchCondition();
    
    condition.setSearchCardId(requestBean.getSearchCardId());
    condition.setSearchCustomerCodeEnd(requestBean.getSearchCustomerCodeEnd());
    condition.setSearchCustomerCodeStart(requestBean.getSearchCustomerCodeStart());
    condition.setSearchCustomerName(requestBean.getSearchCustomerName());
    condition.setSearchEmail(requestBean.getSearchEmail());
    condition.setSearchOrderNo(requestBean.getSearchOrderNo());
    condition.setSearchShippingStartDatetimeFrom(requestBean.getSearchShippingStartDatetimeFrom());
    condition.setSearchShippingStartDatetimeTo(requestBean.getSearchShippingStartDatetimeTo());
    condition.setSearchTelephoneNum(requestBean.getSearchTelephoneNum());

    PagerUtil.createSearchCondition(getRequestParameter(), condition);

    List<ValidationResult> beanValidate = BeanValidator.validate(condition).getErrors();
    if (beanValidate.size() > 0) {
      for (ValidationResult r : beanValidate) {
        this.addErrorMessage(r.getFormedMessage());
      }
      requestBean.getList().clear();
      setRequestBean(requestBean);
      return BackActionResult.RESULT_SUCCESS;
    }

    try {
      AnalysisService service = ServiceLocator.getAnalysisService(getLoginInfo());

      SearchResult<GiftCardUseLogSummary> result = service.getGiftCardUseLogList(condition);
      requestBean.setPagerValue(PagerUtil.createValue(result));
      if (result.getRowCount() == 0) {
        this.addWarningMessage(WebMessage.get(ActionErrorMessage.SEARCH_RESULT_NOT_FOUND));
        requestBean.getList().clear();
        setRequestBean(requestBean);
        return BackActionResult.RESULT_SUCCESS;
      }
      if (result.isOverflow()) {
        addWarningMessage(WebMessage.get(ActionErrorMessage.SEARCH_RESULT_OVERFLOW,
            NumUtil.formatNumber("" + result.getRowCount()), "" + NumUtil.formatNumber("" + condition.getMaxFetchSize())));
      }


      List<GiftCardUseListBean> detailList = new ArrayList<GiftCardUseListBean>();
      for (GiftCardUseLogSummary s : result.getRows()) {
        GiftCardUseListBean detail = new GiftCardUseListBean();
        
        detail.setCustomerCode(s.getCustomerCode());
        detail.setCardId(s.getCardId());
        detail.setRechargeTime( DateUtil.toDateTimeString(s.getRechargeDate(),"yyyy-MM-dd HH:mm:ss"));
        detail.setUnitPrice(s.getUnitPrice().toString());
        detail.setDenomination(s.getDenomination().toString());
        detail.setDiscountRate(s.getDiscountRate().toString());
        detail.setUseAmount(s.getUseAmount().toString());
        detail.setLeftAmount(s.getLeftAmount().toString());
        String newStr = s.getLinkOrder().replace(")", "");
        newStr = newStr.replace("(", "");
        String[] links = newStr.split(";");
        for (String str: links) {
          if (StringUtil.hasValue(str)) {
            detail.getLinkOrder().add(str);
          }
        }
        
        detailList.add(detail);
      }

      requestBean.setList(detailList);

      setRequestBean(requestBean);
      setNextUrl(null);

    } catch (RuntimeException e) {
      logger.error(e);
      return BackActionResult.SERVICE_ERROR;
    }

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
    return Messages.getString("礼品卡使用明细分析"); //$NON-NLS-1$
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "6107083003";
  }

}
