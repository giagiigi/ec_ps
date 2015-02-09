package jp.co.sint.webshop.web.action.back.catalog;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.data.dto.CCommodityDetail;
import jp.co.sint.webshop.data.dto.CommodityPriceChangeHistory;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.catalog.CommodityPriceChangeHistoryCondition;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.validation.BeanValidator;
import jp.co.sint.webshop.validation.ValidationSummary;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.catalog.CommodityPriceChangeReviewBean;
import jp.co.sint.webshop.web.bean.back.catalog.CommodityPriceChangeReviewBean.CommoditySearchedBean;
import jp.co.sint.webshop.web.bean.back.catalog.TmallCommoditySkuBean.CCommoditySkuDetailBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.message.back.CompleteMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerUtil;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1020510:受注確認管理のアクションクラスです。

 * 
 * @author System Integrator Corp.
 */
public class CommodityPriceChangeReviewSearchAction extends WebBackAction<CommodityPriceChangeReviewBean> {

  private CommodityPriceChangeHistoryCondition condition;

  protected CommodityPriceChangeHistoryCondition getCondition() {
    return PagerUtil.createSearchCondition(getRequestParameter(), condition);
  }

  protected CommodityPriceChangeHistoryCondition getSearchCondition() {
    return this.condition;
  }

  /**
   * 初期処理を実行します。

   */
  @Override
  public void init() {

    CommodityPriceChangeReviewBean bean = getBean();

    setRequestBean(bean);
  }



  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。

   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return getLoginInfo().hasPermission(Permission.COMMODITY_PRICE_CHANGE);
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
      return summary.isValid();
    }

    boolean authorization = true;


    return authorization;
  }

  /**
   * アクションを実行します。

   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    CommodityPriceChangeReviewBean bean = getBean();

    // 検索条件作成
    condition = new CommodityPriceChangeHistoryCondition();
    condition.setReviewOrNotFlg("0");
    condition = getCondition();
    
    // 検索実行
    CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());
    SearchResult<CommodityPriceChangeHistory> result = catalogService.searchCommodityPriceChangeHistory(condition);
    if (result.getRowCount() == 0) {
      addWarningMessage(WebMessage.get(ActionErrorMessage.SEARCH_RESULT_NOT_FOUND));
    } else if (result.isOverflow()) {
      addWarningMessage(WebMessage.get(ActionErrorMessage.SEARCH_RESULT_OVERFLOW, NumUtil.formatNumber("" + result.getRowCount())));
    }
    bean.setPagerValue(PagerUtil.createValue(result));

    // リスト生成

    List<CommoditySearchedBean> detailList = new ArrayList<CommoditySearchedBean>();

    for (CommodityPriceChangeHistory commodityPriceChangeHistory : result.getRows()) {
      CommoditySearchedBean row = new CommoditySearchedBean();
      row.setCommodityCode(commodityPriceChangeHistory.getCommodityCode());
      if(commodityPriceChangeHistory.getSubmitTime() != null) {
        row.setSubmitTime(DateUtil.toDateTimeString(commodityPriceChangeHistory.getSubmitTime()));
      } else {
        row.setSubmitTime(null);
      }
      row.setResponsiblePerson(commodityPriceChangeHistory.getResponsiblePerson());
      if(commodityPriceChangeHistory.getOldOfficialPrice() != null) {
        row.setOldOfficialPrice(commodityPriceChangeHistory.getOldOfficialPrice().toString());
      } else {
        row.setOldOfficialPrice(null);
      }
      if(commodityPriceChangeHistory.getNewOfficialPrice() != null) {
        row.setNewOfficialPrice(commodityPriceChangeHistory.getNewOfficialPrice().toString());
      } else {
        row.setNewOfficialPrice(null);
      }
      if(commodityPriceChangeHistory.getOldOfficialSpecialPrice() != null) {
        row.setOldOfficialSpecialPrice(commodityPriceChangeHistory.getOldOfficialSpecialPrice().toString());
      } else {
        row.setOldOfficialSpecialPrice(null);
      }
      if(commodityPriceChangeHistory.getNewOfficialSpecialPrice() != null) {
        row.setNewOfficialSpecialPrice(commodityPriceChangeHistory.getNewOfficialSpecialPrice().toString());
      } else {
        row.setNewOfficialSpecialPrice(null);
      }
      if(commodityPriceChangeHistory.getOldTmallPrice() != null) {
        row.setOldTmallPrice(commodityPriceChangeHistory.getOldTmallPrice().toString());
      } else {
        row.setOldTmallPrice(null);
      }
      if(commodityPriceChangeHistory.getNewTmallPrice() != null) {
        row.setNewTmallPrice(commodityPriceChangeHistory.getNewTmallPrice().toString());
      } else {
        row.setNewTmallPrice(null);
      }
      if(commodityPriceChangeHistory.getOldJdPrice() != null) {
        row.setOldJdPrice(commodityPriceChangeHistory.getOldJdPrice().toString());
      } else {
        row.setOldJdPrice(null);
      }
      if(commodityPriceChangeHistory.getNewJdPrice() != null) {
        row.setNewJdPrice(commodityPriceChangeHistory.getNewJdPrice().toString());
      } else {
        row.setNewJdPrice(null);
      }
      if(commodityPriceChangeHistory.getEcProfitMargin() != null) {
        row.setEcGrossProfitRate(commodityPriceChangeHistory.getEcProfitMargin().toString());
      } else {
        row.setEcGrossProfitRate(null);
      }
      if(commodityPriceChangeHistory.getEcSpecialProfitMargin() != null) {
        row.setEcSpecialGrossProfitRate(commodityPriceChangeHistory.getEcSpecialProfitMargin().toString());
      } else {
        row.setEcSpecialGrossProfitRate(null);
      }
      if(commodityPriceChangeHistory.getTmallProfitMargin() != null) {
        row.setTmallGrossProfitRate(commodityPriceChangeHistory.getTmallProfitMargin().toString());
      } else {
        row.setTmallGrossProfitRate(null);
      }
      if(commodityPriceChangeHistory.getJdProfitMargin() != null) {
       row.setJdGrossProfitRate(commodityPriceChangeHistory.getJdProfitMargin().toString());
      } else {
        row.setJdGrossProfitRate(null);
      }
      detailList.add(row);
    }
    
    bean.setCommoditySearchedList(detailList);

    setRequestBean(bean);

    return BackActionResult.RESULT_SUCCESS;

  }

  /**
   * 画面表示に必要な項目を設定・初期化します。

   */
  @Override
  public void prerender() {
    CommodityPriceChangeReviewBean bean = (CommodityPriceChangeReviewBean) getRequestBean();

    if (getRequestParameter().getPathArgs().length > 0) {
      String completeParam = getRequestParameter().getPathArgs()[0];
      if (completeParam.equals(WebConstantCode.COMPLETE_UPDATE)) {
        addInformationMessage(WebMessage.get(CompleteMessage.UPDATE_COMPLETE,
            Messages.getString("web.action.back.order.CommodityPriceChangeReviewConfirmAction.2")));
      }
    }

    setRequestBean(bean);
  }

  /**
   * Action名の取得
   * 
   * @return Action名

   */
  public String getActionName() {
    return Messages.getString("web.action.back.order.OrderConfirmListSearchAction.3");
  }

  /**
   * オペレーションコードの取得

   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "1102021009";
  }
  
  public BigDecimal calculateGrossProfitRate(CatalogService catalogService, BigDecimal priceReadyForCheck, CommodityPriceChangeHistory commodityPriceChangeHistory) {
    // 查询平均计算成本和税率，以计算每行的毛利率。
    SearchResult<CCommodityDetail> cCommodityDetailSearchResult = catalogService.searchCCommodityDetail(commodityPriceChangeHistory.getCommodityCode());
    BigDecimal averageCost = cCommodityDetailSearchResult.getRows().get(0).getAverageCost();
    BigDecimal taxClass = new BigDecimal(cCommodityDetailSearchResult.getRows().get(0).getTaxClass());
    
    BigDecimal taxClassPercent = taxClass.multiply(new BigDecimal(0.01));
    // 毛利率 = (售价-平均移动成本*（1+税率））/售价
    BigDecimal grossProfitRate = priceReadyForCheck.subtract(taxClassPercent.add(new BigDecimal(1)).multiply(averageCost)).divide(priceReadyForCheck, 2, RoundingMode.HALF_UP);
    return grossProfitRate;
  }

}

