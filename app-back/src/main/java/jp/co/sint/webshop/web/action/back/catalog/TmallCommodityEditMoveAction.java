package jp.co.sint.webshop.web.action.back.catalog;

import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.catalog.CommodityInfo;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.catalog.TmallCommodityEditBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.DisplayTransition;

/**
 * U1040120:商品登録のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class TmallCommodityEditMoveAction extends WebBackAction<TmallCommodityEditBean> {

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
    return true;
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

    TmallCommodityEditBean reqBean = getBean();

    String[] args = getRequestParameter().getPathArgs();
    if(args!=null&&args.length>0){
      String target = args[0];
      if("list".equals(target)){
        setNextUrl("/app/catalog/tmall_commodity_list");
      }else if("sku".equals(target)){
        setNextUrl("/app/catalog/tmall_commodity_sku/init/"+reqBean.getCommodityCode()+"/"+("".equals(reqBean.getShopCode())?"00000000":reqBean.getShopCode()));
      }
      return BackActionResult.RESULT_SUCCESS;
    }
    String shopCode = "";
    if (getLoginInfo().isSite()) {
      shopCode = reqBean.getShopCode();
    } else {
      shopCode = getLoginInfo().getShopCode();
     
    }

    // 商品存在チェック
    CatalogService catalogSv = ServiceLocator.getCatalogService(getLoginInfo());
    CommodityInfo commodity = catalogSv.getCCommodityInfo(shopCode, reqBean.getCommodityCode());
    if (commodity == null || commodity.getCheader() == null || commodity.getCdetail() == null) {
      addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, Messages
          .getString("web.action.back.catalog.CommodityEditMoveAction.0")));
      setRequestBean(getBean());

      return BackActionResult.RESULT_SUCCESS;
    }

    // 商品SKU画面へ遷移する
    setNextUrl("/app/catalog/tmall_commodity_sku/init" + "/" + shopCode + "/" + reqBean.getCommodityCode());

    // 遷移元情報をセッションに保存
    DisplayTransition.add(reqBean, "/app/catalog/tmall_commodity_edit/select/" + shopCode + "/" + reqBean.getCommodityCode() + "/edit",
        getSessionContainer());

    return BackActionResult.RESULT_SUCCESS;

  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.catalog.CommodityEditMoveAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3104022005";
  }

}
