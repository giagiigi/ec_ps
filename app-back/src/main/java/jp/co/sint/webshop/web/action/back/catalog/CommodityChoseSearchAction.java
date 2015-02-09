package jp.co.sint.webshop.web.action.back.catalog;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.data.dao.StockDao;
import jp.co.sint.webshop.data.dao.TmallSuitCommodityDao;
import jp.co.sint.webshop.data.domain.SetCommodityFlg;
import jp.co.sint.webshop.data.dto.SetCommodityComposition;
import jp.co.sint.webshop.data.dto.Stock;
import jp.co.sint.webshop.data.dto.TmallSuitCommodity;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.CommodityContainer;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.catalog.CommodityListSearchCondition;
import jp.co.sint.webshop.utility.BigDecimalUtil;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.validation.ValidatorUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.catalog.CommodityChoseBean;
import jp.co.sint.webshop.web.bean.back.catalog.CommodityChoseBean.CommodityListResult;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.message.back.CompleteMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerUtil;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1040110:商品マスタのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class CommodityChoseSearchAction extends CommodityChoseBaseAction {

  private CommodityListSearchCondition condition;

  protected CommodityListSearchCondition getCondition() {
    return PagerUtil.createSearchCondition(getRequestParameter(), condition);
  }

  protected CommodityListSearchCondition getSearchCondition() {
    return this.condition;
  }

  /**
   * beanのcreateAttributeを実行するかどうかの設定
   * 
   * @return true - 実行する false-実行しない
   */
  @Override
  public boolean isCallCreateAttribute() {
    // 戻るボタンで遷移した場合はcallCreateAttributeを呼ばない
    if (getUrlPath().equals("back") || getUrlPath().equals(WebConstantCode.COMPLETE_UPDATE)) {
      return false;
    }
    return true;
  }

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return Permission.COMMODITY_READ.isGranted(getLoginInfo()) || Permission.CATALOG_READ.isGranted(getLoginInfo());
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    boolean isValid = true;

    // 検索条件のvalidationチェック
    isValid &= validateBean(getBean());

    // 商品コードの大小チェック
    if (StringUtil.hasValueAllOf(getBean().getSearchCommodityCodeStart(), getBean().getSearchCommodityCodeEnd())) {
      if (!ValidatorUtil.isCorrectOrder(getBean().getSearchCommodityCodeStart(), getBean().getSearchCommodityCodeEnd())) {
        isValid = false;
        addErrorMessage(WebMessage.get(ActionErrorMessage.COMPARISON_ERROR, Messages
            .getString("web.action.back.catalog.CommodityListSearchAction.0")));
      }
    }

    // 販売開始日時の前後チェック
    if (StringUtil.hasValueAllOf(getBean().getSearchSaleStartDateRangeFrom(), getBean().getSearchSaleStartDateRangeTo())) {
      if (!ValidatorUtil.isCorrectOrder(getBean().getSearchSaleStartDateRangeFrom(), getBean().getSearchSaleStartDateRangeTo())) {
        isValid = false;
        addErrorMessage(WebMessage.get(ActionErrorMessage.COMPARISON_ERROR, Messages
            .getString("web.action.back.catalog.CommodityListSearchAction.1")));
      }
    }

    // 販売終了日時の前後チェック
    if (StringUtil.hasValueAllOf(getBean().getSearchSaleEndDateRangeFrom(), getBean().getSearchSaleEndDateRangeTo())) {
      if (!ValidatorUtil.isCorrectOrder(getBean().getSearchSaleEndDateRangeFrom(), getBean().getSearchSaleEndDateRangeTo())) {
        isValid = false;
        addErrorMessage(WebMessage.get(ActionErrorMessage.COMPARISON_ERROR, Messages
            .getString("web.action.back.catalog.CommodityListSearchAction.2")));
      }
    }

    // 在庫数開始、終了の大小チェック
    if (StringUtil.hasValueAllOf(getBean().getSearchStockQuantityStart(), getBean().getSearchStockQuantityEnd())) {
      if (!ValidatorUtil.isCorrectOrder(NumUtil.toLong(getBean().getSearchStockQuantityStart()), NumUtil.toLong(getBean()
          .getSearchStockQuantityEnd()))) {
        isValid = false;
        addErrorMessage(WebMessage.get(ActionErrorMessage.COMPARISON_ERROR, Messages
            .getString("web.action.back.catalog.CommodityListSearchAction.3")));
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

    // ショップユーザの場合は検索ショップコードを自ショップに設定
    if (getLoginInfo().isShop()) {
      getBean().setSearchShopCode(getLoginInfo().getShopCode());
    }
    TmallSuitCommodityDao suitDao = DIContainer.getDao(TmallSuitCommodityDao.class);
    // 検索条件の作成
    condition = new CommodityListSearchCondition();
    setCondition(condition);

    // ページング情報の追加
    condition = getCondition();

    // 検索条件のバリデーションチェック
    if (!validateBean(condition)) {
      addErrorMessage(WebMessage.get(ActionErrorMessage.SEARCHCONDITION_ERROR, Messages
          .getString("web.action.back.catalog.CommodityListSearchAction.4")));
      return BackActionResult.RESULT_SUCCESS;
    }

    // 検索結果の取得
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    SearchResult<CommodityContainer> resultList = service.getCommoditySearch(condition);

    // 検索結果0件チェック
    if (resultList.getRows().isEmpty()) {
      addWarningMessage(WebMessage.get(ActionErrorMessage.SEARCH_RESULT_NOT_FOUND));
    }

    // オーバーフローチェック
    if (resultList.isOverflow()) {
      this.addWarningMessage(WebMessage.get(ActionErrorMessage.SEARCH_RESULT_OVERFLOW, NumUtil.formatNumber(""
          + resultList.getRowCount()), "" + NumUtil.formatNumber("" + condition.getMaxFetchSize())));
    }

    List<CommodityContainer> commodityList = resultList.getRows();
    CommodityChoseBean nextBean = getBean();
    StockDao dao = DIContainer.getDao(StockDao.class);
    Long statusType;
    Stock stock;
    // nextBeanに検索結果を設定
    List<CommodityListResult> list = new ArrayList<CommodityListResult>();
    for (CommodityContainer result : commodityList) {
      CommodityListResult commodity = new CommodityListResult();
      commodity.setShopCode(result.getCommodityHeader().getShopCode());
      commodity.setShopName(result.getShop().getShopName());
      commodity.setCommodityCode(result.getCommodityHeader().getCommodityCode());
      commodity.setCommodityName(result.getCommodityHeader().getCommodityName());
      commodity.setSaleType(String.valueOf(result.getContainerAddInfo().getSaleType()));
      commodity.setSaleStatus(String.valueOf(result.getContainerAddInfo().getSaleStatus()));
      commodity.setCommodityTaxType(NumUtil.toString(result.getCommodityHeader().getCommodityTaxType()));
      commodity.setUpdatedDatetime(result.getCommodityHeader().getUpdatedDatetime());
      // 2012/11/22  add by tangweihui start
      commodity.setCommodityType(result.getCommodityHeader().getCommodityType());
      // 2012/11/22  add by tangweihui end
      // 2012/11/17 促销对应 ob add start
      // 套餐商品
      if (SetCommodityFlg.OBJECTIN.longValue().equals(result.getCommodityHeader().getSetCommodityFlg())) {
        commodity.setSetCommodityFlg(true);
        // 套餐明细
        if (result.getContainerAddInfo().getRelatedCompositionCount() > 0) {
          commodity.setRelatedCompositionFlg(true);
        }
      }
      // 2012/11/17 促销对应 ob add end
      
      if (StringUtil.hasValue(result.getCommodityHeader().getOriginalCommodityCode())){
        stock = dao.load(result.getCommodityHeader().getShopCode(), result.getCommodityHeader().getOriginalCommodityCode());
        if (stock.getStockQuantity() - stock.getAllocatedQuantity() < result.getCommodityHeader().getCombinationAmount() ){
          statusType = 2L;
        } else {
          statusType = 0L;
        }
        commodity.setUnitPrice(NumUtil.toString(result.getCommodityDetail().getUnitPrice().multiply(new BigDecimal(result.getCommodityHeader().getCombinationAmount()))));
        commodity.setStockStatus(String.valueOf(statusType));
      } else {
        commodity.setUnitPrice(NumUtil.toString(result.getCommodityDetail().getUnitPrice()));
        commodity.setStockStatus(String.valueOf(result.getContainerAddInfo().getStockStatus()));
      }
      
      if (SetCommodityFlg.OBJECTIN.longValue().equals(result.getCommodityHeader().getSetCommodityFlg())) {
        TmallSuitCommodity suit = suitDao.load(result.getCommodityHeader().getCommodityCode());
        if (suit != null ) {
          commodity.setStockStatus(String.valueOf(suit.getStockQuantity()-suit.getAllocatedQuantity() > 0L ? "0" : "2"));
          if (commodity.getStockStatus().equals("0")) {
            List<SetCommodityComposition> compositionList = service.getSetCommodityInfo("00000000", result.getCommodityHeader().getCommodityCode());
            if (compositionList != null) {
              BigDecimal suitPrice = BigDecimal.ZERO;
              for (SetCommodityComposition edit : compositionList) {
                suitPrice = BigDecimalUtil.add(suitPrice,edit.getTmallRetailPrice());
              }  
              commodity.setUnitPrice(NumUtil.toString(suitPrice));
            } else {
              commodity.setUnitPrice("0");
              commodity.setStockStatus("2");
            }
          } 
        } else {
          commodity.setUnitPrice("0");
          commodity.setStockStatus("2");
        }
      }
      
      // 各関連付け情報の有無をチェック
      commodity.setRelatedCommodityA(result.getContainerAddInfo().getRelatedCommodityACount() > 0);
      commodity.setRelatedCommodityB(result.getContainerAddInfo().getRelatedCommodityBCount() > 0);
      commodity.setRelatedTag(result.getContainerAddInfo().getRelatedTagCount() > 0);
      commodity.setRelatedCategory(result.getContainerAddInfo().getRelatedCategoryCount() > 0);
      commodity.setRelatedCampaign(result.getContainerAddInfo().getRelatedCampaignCount() > 0);
      commodity.setRelatedGift(result.getContainerAddInfo().getRelatedGiftCount() > 0);
      commodity.setSortRank(result.getCommodityHeader().getChosenSortRank());
      

      list.add(commodity);
    }
    nextBean.setList(list);

    // ページ情報の追加
    nextBean.setPagerValue(PagerUtil.createValue(resultList));

    setRequestBean(nextBean);

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * ボタンの表示/非表示設定<br>
   * 更新完了メッセージを表示
   */
  @Override
  public void prerender() {
    CommodityChoseBean reqBean = (CommodityChoseBean) getRequestBean();
    setDisplayControl(reqBean);
    setRequestBean(reqBean);

    if (getUrlPath().equals(WebConstantCode.COMPLETE_UPDATE)) {
      addInformationMessage(WebMessage.get(CompleteMessage.UPDATE_COMPLETE, Messages
          .getString("web.action.back.catalog.CommodityListSearchAction.5")));
    }
  }

  /**
   * URLから処理パラメータを取得
   * 
   * @return 処理パラメータ
   */
  private String getUrlPath() {
    String[] args = getRequestParameter().getPathArgs();
    if (args.length > 0) {
      return args[0];
    }
    return "";
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.catalog.CommodityListSearchAction.6");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3104011008";
  }

}
