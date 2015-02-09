package jp.co.sint.webshop.web.action.back.catalog;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.catalog.ArrivalGoodsSearchCondition;
import jp.co.sint.webshop.service.catalog.ArrivalGoodsSubscritionCount;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.catalog.ArrivalGoodsBean;
import jp.co.sint.webshop.web.bean.back.catalog.ArrivalGoodsBean.ArrivalGoodsDetailBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerUtil;

/**
 * U1040710:入荷お知らせのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class ArrivalGoodsSearchAction extends WebBackAction<ArrivalGoodsBean> {

  /**
   * 初期処理を実行します
   */
  @Override
  public void init() {
    super.init();
    if (getLoginInfo().isShop() || getConfig().isOne()) {
      getBean().setSearchShopCode(getLoginInfo().getShopCode());
    }
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
    ArrivalGoodsBean reqBean = getBean();
    boolean isValid = validateBean(reqBean);

    if (!isValid) {
      reqBean.setPagerValue(new ArrivalGoodsBean().getPagerValue());
      reqBean.getList().clear();

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

    ArrivalGoodsBean reqBean = getBean();

    if (getLoginInfo().isShop()) {
      reqBean.setSearchShopCode(getLoginInfo().getShopCode());
    }

    // 検索条件の生成
    ArrivalGoodsSearchCondition condition = new ArrivalGoodsSearchCondition();
    condition.setSearchShopCode(reqBean.getSearchShopCode());
    condition.setSearchCommodityName(reqBean.getSearchCommodityName());
    condition.setSearchSkuCode(reqBean.getSearchSkuCode());
    condition = PagerUtil.createSearchCondition(getRequestParameter(), condition);

    // 検索結果の取得
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    SearchResult<ArrivalGoodsSubscritionCount> result = service.getArrivalGoodsSubcriptionCountList(condition);
    List<ArrivalGoodsSubscritionCount> goodsList = result.getRows();
    // 件数0件,オーバーフローチェック
    prepareSearchWarnings(result, SearchWarningType.BOTH);

    // 検索結果の初期化
    reqBean.getList().clear();

    // 画面表示用Beanを設定
    List<ArrivalGoodsDetailBean> detailList = new ArrayList<ArrivalGoodsDetailBean>();
    for (ArrivalGoodsSubscritionCount goods : goodsList) {
      ArrivalGoodsDetailBean detail = new ArrivalGoodsDetailBean();
      detail.setShopCode(goods.getShopCode());
      String commodityName = goods.getCommodityName();
      if (StringUtil.hasValueAnyOf(goods.getStandardDetail1Name(), goods.getStandardDetail2Name())) {
        commodityName += "(";
        if (StringUtil.hasValue(goods.getStandardDetail1Name())) {
          commodityName += goods.getStandardDetail1Name();
        }
        if (StringUtil.hasValueAllOf(goods.getStandardDetail1Name(), goods.getStandardDetail2Name())) {
          commodityName += ":";
        }
        if (StringUtil.hasValue(goods.getStandardDetail2Name())) {
          commodityName += goods.getStandardDetail2Name();
        }
        commodityName += ")";
      }
      detail.setCommodityName(commodityName);
      detail.setSkuCode(goods.getSkuCode());
      detail.setSubscriptionCount(String.valueOf(goods.getSubscriptionCount()));
      detailList.add(detail);
    }
    reqBean.setList(detailList);

    reqBean.setPagerValue(PagerUtil.createValue(result));

    // 画面表示用Beanを次画面Beanを設定
    setRequestBean(reqBean);

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {

    // 画面項目の表示/非表示を設定
    ArrivalGoodsBean requestBean = (ArrivalGoodsBean) getRequestBean();
    if (getLoginInfo().isSite()) {
      requestBean.setDisplayShopList(true);
    }
    if (Permission.COMMODITY_DELETE.isGranted(getLoginInfo())) {
      requestBean.setDisplayDeleteButton(true);
    }
    if (Permission.COMMODITY_DATA_IO.isGranted(getLoginInfo())) {
      for (ArrivalGoodsDetailBean detail : requestBean.getList()) {
        if (NumUtil.toLong(detail.getSubscriptionCount()) > 0) {
          detail.setDisplayCsvExportButton(true);
        }
      }
    }

  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.catalog.ArrivalGoodsSearchAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3104071004";
  }

}
