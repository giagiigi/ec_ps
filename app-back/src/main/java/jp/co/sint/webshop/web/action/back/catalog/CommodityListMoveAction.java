package jp.co.sint.webshop.web.action.back.catalog;

import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.catalog.CommodityInfo;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.catalog.CommodityListBean;
import jp.co.sint.webshop.web.bean.back.catalog.RelatedBean;
import jp.co.sint.webshop.web.bean.back.catalog.RelatedListBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.DisplayTransition;

import org.apache.log4j.Logger;

/**
 * U1040110:商品マスタのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class CommodityListMoveAction extends WebBackAction<CommodityListBean> {

  private static final String PICTURE_EDIT = "edit";

  private static final String PICTURE_COPY = "copy";

  private static final String PICTURE_CATEGORY = "category";

  private static final String PICTURE_RECOMMEND_A = "commodity_a";

  private static final String PICTURE_RECOMMEND_B = "commodity_b";
  
  // 2012/11/19 促销对应 ob add start
  //套餐设定
  private static final String SET_COMMODITY_COMPOSITION = "set_commodity_composition";
  // 2012/11/19 促销对应 ob add end

  /**
   * beanのcreateAttributeを実行するかどうかの設定
   * 
   * @return true - 実行する false-実行しない
   */
  @Override
  public boolean isCallCreateAttribute() {
    return false;
  }

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    boolean authorization = true;

    if (getLoginInfo().isShop() && getRequestParameter().getPathArgs().length > 1) {

      String shopCode = getRequestParameter().getPathArgs()[1];
      if (StringUtil.isNullOrEmpty(shopCode)) {
        return false;
      }
      authorization &= shopCode.equals(getLoginInfo().getShopCode());
    }

    return authorization;
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    if (getRequestParameter().getPathArgs().length >= 3) {
      return true;
    }
    addErrorMessage(WebMessage.get(ActionErrorMessage.BAD_URL));
    return false;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    Logger logger = Logger.getLogger(this.getClass());

    // URLパラメータの取得
    // URL形式: ～/catalog/commodity_list/move/【遷移先】/【ショップコード】/【商品コード】
    String nextPicture = getRequestParameter().getPathArgs()[0];
    String shopCode = getRequestParameter().getPathArgs()[1];
    String commodityCode = getRequestParameter().getPathArgs()[2];
    logger.debug("nextPicture: " + nextPicture);
    logger.debug("commodidtyCode: " + commodityCode);

    // URLパラメータに従い、遷移先を制御
    String nextUrl = "/app/catalog";

    // 商品存在チェック
    CatalogService catalogSv = ServiceLocator.getCatalogService(getLoginInfo());
    CommodityInfo commodity = catalogSv.getCommodityInfo(shopCode, commodityCode);
    if (commodity == null || commodity.getHeader() == null || commodity.getDetail() == null) {
      addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, Messages
          .getString("web.action.back.catalog.CommodityListMoveAction.0")));
      setRequestBean(getBean());

      return BackActionResult.RESULT_SUCCESS;
    }

    if (nextPicture.equals(PICTURE_EDIT) || nextPicture.equals(PICTURE_COPY)) {
      nextUrl = nextUrl + "/commodity_edit/select/" + shopCode + "/" + commodityCode + "/" + nextPicture;

    } 
    // 2012/11/19 促销对应 ob add start
    //套餐设定转移
    else if (nextPicture.equals(SET_COMMODITY_COMPOSITION)) {
        nextUrl = nextUrl + "/set_commodity_composition/init/" + shopCode + "/" + commodityCode;
      }
	  // 2012/11/19 促销对应 ob add end
    else if (nextPicture.equals(PICTURE_CATEGORY)) {
      nextUrl = nextUrl + "/related_category_tree/init/" + shopCode + "/" + commodityCode;
    } else {

      // 関連付け画面用Beanに初期値を設定
      if (nextPicture.equals(PICTURE_RECOMMEND_A) || nextPicture.equals(PICTURE_RECOMMEND_B)) {
        RelatedBean related = new RelatedBean();
        related.setSearchShopCode(shopCode);
        related.setEffectualCode(commodityCode);
        related.setPictureMode(nextPicture);
        setRequestBean(related);

        nextUrl = nextUrl + "/related";

      } else {
        RelatedListBean relatedList = new RelatedListBean();
        relatedList.setSearchShopCode(shopCode);
        relatedList.setCommodityCode(commodityCode);
        relatedList.setPictureMode(nextPicture);
        setRequestBean(relatedList);

        nextUrl = nextUrl + "/related_list";
      }

    }

    // 遷移元情報をセッションに保存
    DisplayTransition.add(getBean(), "/app/catalog/commodity_list/search_back", getSessionContainer());

    setNextUrl(nextUrl);
    logger.debug("nextUrl: " + nextUrl);

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.catalog.CommodityListMoveAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3104011004";
  }

}
