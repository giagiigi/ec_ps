package jp.co.sint.webshop.web.action.back.catalog;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.domain.SaleStatus;
import jp.co.sint.webshop.data.domain.SkuStatus;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.utility.ArrayUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.catalog.TmallCommodityListBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1040110:商品マスタのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class TmallCommodityListInitAction extends TmallCommodityListBaseAction {

  /**
   * 初期処理を実行します
   */
  public void init() {
    TmallCommodityListBean bean = new TmallCommodityListBean();
    this.setBean(bean);
    super.init();
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
    return true;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    // nextBean生成
    TmallCommodityListBean nextBean = getBean();

    // nextBeanに検索条件の初期値を設定
    if (getLoginInfo().isSite()) {
      UtilService service = ServiceLocator.getUtilService(getLoginInfo());
      nextBean.setShopList(service.getShopNamesDefaultAllShop(false, false));
    }
    if (getLoginInfo().isShop()) {
      nextBean.setSearchShopCode(getLoginInfo().getShopCode());
    }
    nextBean.setSearchCommodityCodeStart("");
    nextBean.setSearchCommodityCodeEnd("");
    nextBean.setSearchSkuCode("");
    nextBean.setSearchCommodityName("");
    nextBean.setSearchCommoditySearchWords("");
    nextBean.setSearchStockQuantityStart("");
    nextBean.setSearchStockQuantityEnd("");

    // 在庫状況検索の初期値:0=全規格あり, 1=一部規格あり
    nextBean.setSearchStockStatus(new String[] {
        SkuStatus.ALL.getValue(), SkuStatus.SOME.getValue(), SkuStatus.NONE.getValue()
    });

    // 適用期間検索の初期値:SaleStatus全て
    List<String> searchSaleStatusList = new ArrayList<String>();
    for (SaleStatus st : SaleStatus.values()) {
      searchSaleStatusList.add(st.getValue());
    }
    nextBean.setSearchSaleStatus(ArrayUtil.toArray(searchSaleStatusList, String.class));

    // 販売状態検索の初期値:2=両方
    nextBean.setSearchSaleType("2");

    // 表示クライアント初期値:PC、携帯両方チェック
    nextBean.setSearchDisplayClientPc("1");
    nextBean.setSearchDisplayClientMobile("1");

    // 入荷お知らせ検索の初期値:2=両方
    nextBean.setSearchArrivalGoods("2");

    // 次画面BeanにnextBeanを設定
    setRequestBean(nextBean);

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {
    TmallCommodityListBean reqBean = (TmallCommodityListBean) getRequestBean();
    setDisplayControl(reqBean);
    setMessage();
    setRequestBean(reqBean);
  }

  private void setMessage() {
    if (getRequestParameter().getPathArgs().length > 0) {
      if (getRequestParameter().getPathArgs()[0].equals("nodata")) {
        addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
            Messages.getString("web.action.back.catalog.CommodityListInitAction.0")));
      }
    }
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.catalog.CommodityListInitAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3104013003";
  }

}
