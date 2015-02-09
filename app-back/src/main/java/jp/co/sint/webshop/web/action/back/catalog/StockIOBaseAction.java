package jp.co.sint.webshop.web.action.back.catalog;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.data.dto.StockIODetail;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.service.catalog.CommodityInfo;
import jp.co.sint.webshop.service.catalog.StockIOSearchCondition;
import jp.co.sint.webshop.service.catalog.StockOperationType;
import jp.co.sint.webshop.utility.DateUtil;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.catalog.StockIOBean;
import jp.co.sint.webshop.web.bean.back.catalog.StockIOBean.StockIODetailBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.webutility.PagerUtil;

/**
 * U1040510:入出庫管理のアクションの基底クラスです
 * 
 * @author System Integrator Corp.
 */
public abstract class StockIOBaseAction extends WebBackAction<StockIOBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public abstract boolean authorize();

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public abstract WebActionResult callService();

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
   * @param reqBean
   *          入出庫Bean
   * @return 入出庫検索条件
   */
  public StockIOSearchCondition setSearchCondition(StockIOBean reqBean, StockIOBean nextBean) {

    nextBean.setSearchShopCode(reqBean.getSearchShopCode());
    nextBean.setSearchSkuCode(reqBean.getSearchSkuCode());
    nextBean.setSearchStockIODateStart(reqBean.getSearchStockIODateStart());
    nextBean.setSearchStockIODateEnd(reqBean.getSearchStockIODateEnd());
    nextBean.setSearchStockIOType(reqBean.getSearchStockIOType());

    StockIOSearchCondition condition = new StockIOSearchCondition();
    condition.setSearchShopCode(reqBean.getSearchShopCode());
    condition.setSearchSkuCode(reqBean.getSearchSkuCode());
    condition.setSearchStockIODateStart(reqBean.getSearchStockIODateStart());
    condition.setSearchStockIODateEnd(reqBean.getSearchStockIODateEnd());
    condition.setStockIOType(reqBean.getSearchStockIOType().toArray((new String[2])));
    condition = PagerUtil.createSearchCondition(getRequestParameter(), condition);
    return condition;
  }

  /**
   * @param reqBean
   *          入出庫Bean
   * @param condition
   *          入出庫検索条件
   * @return 入出庫明細リスト
   */
  public List<StockIODetail> getStockIOList(StockIOBean reqBean, StockIOSearchCondition condition) {
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    SearchResult<StockIODetail> searchResult = service.getStockIOList(condition);

    // ページ情報を追加
    reqBean.setPagerValue(PagerUtil.createValue(searchResult));

    if (searchResult.isOverflow()) {
      addWarningMessage(WebMessage.get(ActionErrorMessage.SEARCH_RESULT_OVERFLOW, NumUtil.formatNumber(""
          + searchResult.getRowCount()), "" + NumUtil.formatNumber("" + condition.getMaxFetchSize())));
    }
    List<StockIODetail> stockIOList = searchResult.getRows();
    return stockIOList;
  }

  /**
   * @param nextBean
   * @param stockIOList
   */
  public void createNextBean(StockIOBean nextBean, List<StockIODetail> stockIOList) {

    List<StockIODetailBean> detailListBean = new ArrayList<StockIODetailBean>();
    // 検索結果を画面表示用Beanにセット
    for (StockIODetail si : stockIOList) {
      StockIODetailBean detailBean = new StockIODetailBean();
      detailBean.setStockIODate(DateUtil.toDateString(si.getStockIODate()));
      detailBean.setSkuCode(si.getSkuCode());
      detailBean.setStockIOQuantity(Long.toString(si.getStockIOQuantity()));
      detailBean.setStockIOType(Long.toString(si.getStockIOType()));
      detailBean.setMemo(si.getMemo());
      detailListBean.add(detailBean);
    }

    List<CodeAttribute> stockOperationList = new ArrayList<CodeAttribute>();
    for (StockOperationType so : StockOperationType.values()) {
      stockOperationList.add(so);
    }

    nextBean.setStockOperationList(stockOperationList);

    // 入力項目欄
    StockIODetailBean editDetailBean = new StockIODetailBean();
    editDetailBean.setStockIOType(StockOperationType.ENTRY.getValue());

    nextBean.setEdit(editDetailBean);
    nextBean.setList(detailListBean);

  }

  public String getCommodityName(CommodityInfo commodityInfo) {
    if (StringUtil.hasValue(commodityInfo.getDetail().getStandardDetail1Name())
        && StringUtil.hasValue(commodityInfo.getDetail().getStandardDetail2Name())) {
      return "：" + commodityInfo.getHeader().getCommodityName() + "(" + commodityInfo.getDetail().getStandardDetail1Name() + "/"
          + commodityInfo.getDetail().getStandardDetail2Name() + ")";

    } else if (StringUtil.hasValue(commodityInfo.getDetail().getStandardDetail1Name())
        && StringUtil.isNullOrEmpty(commodityInfo.getDetail().getStandardDetail2Name())) {
      return "：" + commodityInfo.getHeader().getCommodityName() + "(" + commodityInfo.getDetail().getStandardDetail1Name() + ")";

    } else if (StringUtil.isNullOrEmpty(commodityInfo.getDetail().getStandardDetail1Name())
        && StringUtil.hasValue(commodityInfo.getDetail().getStandardDetail2Name())) {
      return "：" + commodityInfo.getHeader().getCommodityName() + "(" + commodityInfo.getDetail().getStandardDetail2Name() + ")";

    } else {
      return "：" + commodityInfo.getHeader().getCommodityName();

    }
  }

  /**
   * @param stockIOBean
   */
  public void setDisplayControl(StockIOBean stockIOBean) {
    // 更新処理に関する表示制御を実施
    if (Permission.STOCK_MANAGEMENT_UPDATE.isGranted(getLoginInfo()) && getDisplayMessage().getErrors().size() == 0
        && StringUtil.hasValue(stockIOBean.getCommodityName())) {
      stockIOBean.setRegisterTableDisplayFlg(true);
    } else {
      stockIOBean.setRegisterTableDisplayFlg(false);
    }

    if (Permission.STOCK_MANAGEMENT_DATA_IO.isGranted(getLoginInfo())) {
      stockIOBean.setUploadTableDisplayFlg(true);
    } else {
      stockIOBean.setUploadTableDisplayFlg(false);
    }

    // ショップ検索の表示制御を実施
    if (getLoginInfo().isSite()) {
      stockIOBean.setSearchTableDisplayFlg(true);
      UtilService utilService = ServiceLocator.getUtilService(getLoginInfo());
      stockIOBean.setShopList(utilService.getShopNames(true));
    } else {
      stockIOBean.setSearchTableDisplayFlg(false);
    }
  }

}
