package jp.co.sint.webshop.web.action.back.analysis;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.sint.webshop.service.AnalysisService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.analysis.SalesAmountSummary;
import jp.co.sint.webshop.utility.DateRange;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.analysis.SalesAmountBean;
import jp.co.sint.webshop.web.bean.back.analysis.SalesAmountBean.SalesAmountBeanDetail;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1070810:売上集計のアクションクラスです。
 * 
 * @author System Integrator Corp.
 */
public class SalesAmountSearchAction extends WebBackAction<SalesAmountBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    BackLoginInfo login = getLoginInfo();
    if (getConfig().isOne()) {
      return false;
    } else {
      return Permission.ANALYSIS_READ_SITE.isGranted(login) || Permission.ANALYSIS_READ_SHOP.isGranted(login);
    }
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    BackLoginInfo login = getLoginInfo();
    // サイト権限がない場合はcallService()でリダイレクトさせる為
    // validationチェックを行わない。
    if (Permission.ANALYSIS_READ_SITE.isDenied(login)) {
      return true;
    }

    return validateBean(getBean());
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    BackLoginInfo login = getLoginInfo();

    // サイト権限がない場合はショップ別売上集計画面に遷移させる
    if (Permission.ANALYSIS_READ_SITE.isDenied(login)) {
      String year = DateUtil.getYYYY(DateUtil.getSysdate());
      String month = DateUtil.getMM(DateUtil.getSysdate());
      setNextUrl("/app/analysis/sales_amount_shop/init/" + login.getShopCode() + "/" + year + "/" + month);

      setRequestBean(new SalesAmountBean());

      return BackActionResult.RESULT_SUCCESS;
    }

    SalesAmountBean requestBean = getBean();
    requestBean.setSearchSiteResult(new SalesAmountBeanDetail());
    requestBean.getSearchShopResult().clear();

    Date startDate = DateUtil.fromYearMonth(requestBean.getSearchYear(), requestBean.getSearchMonth());
    Date endDate = DateUtil.addDate(DateUtil.addMonth(startDate, 1), -1);

    AnalysisService service = ServiceLocator.getAnalysisService(getLoginInfo());
    DateRange range = new DateRange(startDate, endDate);

    SalesAmountSummary siteResult = service.getSalesAmountSite(range);

    List<SalesAmountSummary> shopResult = service.getSalesAmountShop(range);

    requestBean.setSearchSiteResult(createDetail(siteResult));

    List<SalesAmountBeanDetail> searchShopResult = new ArrayList<SalesAmountBeanDetail>();
    for (SalesAmountSummary summary : shopResult) {
      searchShopResult.add(createDetail(summary));
    }

    requestBean.setSearchShopResult(searchShopResult);

    setRequestBean(requestBean);
    setNextUrl(null);

    return BackActionResult.RESULT_SUCCESS;
  }

  private SalesAmountBeanDetail createDetail(SalesAmountSummary summary) {
    SalesAmountBeanDetail detail = new SalesAmountBeanDetail();
    detail.setShopCode(summary.getShopCode());
    detail.setShopName(summary.getShopName());
    detail.setTotalSalesPrice(NumUtil.toString(summary.getTotalSalesPrice()));
    detail.setTotalSalesPriceTax(NumUtil.toString(summary.getTotalSalesPriceTax()));
    detail.setTotalRefund(NumUtil.toString(summary.getTotalRefund()));
    detail.setTotalReturnItemLossMoney(NumUtil.toString(summary.getTotalReturnItemLossMoney()));
    detail.setTotalDiscountAmount(NumUtil.toString(summary.getTotalDiscountAmount()));
    detail.setTotalShippingCharge(NumUtil.toString(summary.getTotalShippingCharge()));
    detail.setTotalShippingChargeTax(NumUtil.toString(summary.getTotalShippingChargeTax()));
    detail.setTotalGiftPrice(NumUtil.toString(summary.getTotalGiftPrice()));
    detail.setTotalGiftTax(NumUtil.toString(summary.getTotalGiftTax()));

    return detail;
  }

  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {
    BackLoginInfo login = getLoginInfo();
    if (Permission.ANALYSIS_DATA_SITE.isGranted(login)) {
      SalesAmountBean requestBean = (SalesAmountBean) getRequestBean();
      requestBean.setExportAuthority(true);
      setRequestBean(requestBean);
    }
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.analysis.SalesAmountSearchAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "6107081004";
  }

}
