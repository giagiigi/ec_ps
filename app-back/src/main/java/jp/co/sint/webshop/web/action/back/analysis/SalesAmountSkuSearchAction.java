package jp.co.sint.webshop.web.action.back.analysis;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.service.AnalysisService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.analysis.SalesAmountBySkuSearchCondition;
import jp.co.sint.webshop.service.analysis.SalesAmountBySkuSummary;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.validation.BeanValidator;
import jp.co.sint.webshop.validation.ValidationResult;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.analysis.SalesAmountSkuBean;
import jp.co.sint.webshop.web.bean.back.analysis.SalesAmountSkuBean.SalesAmountSkuBeanDetail;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.webutility.PagerUtil;

import org.apache.log4j.Logger;

/**
 * U1070830:商品別売上集計のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class SalesAmountSkuSearchAction extends WebBackAction<SalesAmountSkuBean> {

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
    boolean result = true;

    SalesAmountSkuBean bean = getBean();

    result &= validateBean(bean);

    Date startDate = DateUtil.fromString(bean.getSearchStartDate());
    Date endDate = DateUtil.fromString(bean.getSearchEndDate());
    if (startDate != null && endDate != null) {
      if (startDate.after(endDate)) {
        addErrorMessage(WebMessage.get(ActionErrorMessage.PERIOD_ERROR));
        result &= false;
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
    Logger logger = Logger.getLogger(this.getClass());
    SalesAmountSkuBean requestBean = getBean();

    SalesAmountBySkuSearchCondition condition = new SalesAmountBySkuSearchCondition();

    Date searchStartDate = DateUtil.fromString(requestBean.getSearchStartDate());
    Date searchEndDate = DateUtil.fromString(requestBean.getSearchEndDate());
    condition.setSearchStartDate(searchStartDate);
    condition.setSearchEndDate(searchEndDate);
    if (getLoginInfo().isSite()) {
      condition.setShopCode(requestBean.getShopCodeCondition());
    } else {
      condition.setShopCode(getLoginInfo().getShopCode());
    }
    condition.setCommodityCodeStart(requestBean.getCommodityCodeStart());
    condition.setCommodityCodeEnd(requestBean.getCommodityCodeEnd());
    condition.setCommoditySkuName(requestBean.getSkuName());
    PagerUtil.createSearchCondition(getRequestParameter(), condition);

    List<ValidationResult> beanValidate = BeanValidator.validate(condition).getErrors();
    if (beanValidate.size() > 0) {
      for (ValidationResult r : beanValidate) {
        this.addErrorMessage(r.getFormedMessage());
      }
      requestBean.getSearchResult().clear();
      setRequestBean(requestBean);
      return BackActionResult.RESULT_SUCCESS;
    }

    try {
      AnalysisService service = ServiceLocator.getAnalysisService(getLoginInfo());

      SearchResult<SalesAmountBySkuSummary> result = service.getSalesAmountBySku(condition);
      // 10.1.4 10166 追加 ここから
      requestBean.setPagerValue(PagerUtil.createValue(result));
      // 10.1.4 10166 追加 ここまで
      if (result.getRowCount() == 0) {
        this.addWarningMessage(WebMessage.get(ActionErrorMessage.SEARCH_RESULT_NOT_FOUND));
        requestBean.getSearchResult().clear();
        setRequestBean(requestBean);
        return BackActionResult.RESULT_SUCCESS;
      }
      if (result.isOverflow()) {
        addWarningMessage(WebMessage.get(ActionErrorMessage.SEARCH_RESULT_OVERFLOW,
            NumUtil.formatNumber("" + result.getRowCount()), "" + NumUtil.formatNumber("" + condition.getMaxFetchSize())));
      }

      // 10.1.4 10166 削除 ここから
      // requestBean.setPagerValue(PagerUtil.createValue(result));
      // 10.1.4 10166 削除 ここまで

      List<SalesAmountSkuBeanDetail> detailList = new ArrayList<SalesAmountSkuBeanDetail>();
      for (SalesAmountBySkuSummary s : result.getRows()) {
        SalesAmountSkuBeanDetail detail = new SalesAmountSkuBeanDetail();
        detail.setShopName(s.getShopName());
        detail.setShopCode(s.getShopCode());
        detail.setCommodityCode(s.getCommodityCode());
        detail.setSkuCode(s.getSkuCode());
        detail.setSkuName(s.getCommoditySkuName());
        detail.setTotalSalesPrice(NumUtil.toString(s.getTotalSalesPrice()));
        detail.setTotalSalesPriceTax(NumUtil.toString(s.getTotalSalesPriceTax()));
        detail.setTotalRefund(NumUtil.toString(s.getTotalRefund()));
        detail.setTotalOrderQuantity(NumUtil.toString(s.getTotalOrderQuantity()));
        detail.setTotalReturnItemQuantity(NumUtil.toString(s.getTotalReturnItemQuantity()));
        detail.setTotalGiftPrice(NumUtil.toString(s.getTotalGiftPrice()));
        detail.setTotalGiftTax(NumUtil.toString(s.getTotalGiftTax()));
        detail.setTotalDiscountAmount(NumUtil.toString(s.getTotalDiscountAmount()));
        detailList.add(detail);
      }

      requestBean.setSearchResult(detailList);

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
    BackLoginInfo login = getLoginInfo();
    SalesAmountSkuBean requestBean = (SalesAmountSkuBean) getRequestBean();

    if (Permission.ANALYSIS_DATA_SITE.isGranted(login) || Permission.ANALYSIS_DATA_SHOP.isGranted(login)) {
      requestBean.setExportAuthority(true);
    }

    setRequestBean(requestBean);
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.analysis.SalesAmountSkuSearchAction.0"); //$NON-NLS-1$
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
