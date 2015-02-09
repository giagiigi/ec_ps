package jp.co.sint.webshop.web.action.back.catalog;

import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.catalog.ArrivalGoodsBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.message.back.catalog.CatalogErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

import org.apache.log4j.Logger;

/**
 * U1040710:入荷お知らせのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class ArrivalGoodsDeleteAction extends WebBackAction<ArrivalGoodsBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    if (Permission.COMMODITY_DELETE.isGranted(getLoginInfo())) {
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
    if (getBean().getCheckedCode().size() > 0) {
      for (String param : getBean().getCheckedCode()) {
        if (StringUtil.isNullOrEmpty(param)) {
          addErrorMessage(WebMessage.get(ActionErrorMessage.DELETE_ERROR,
              Messages.getString("web.action.back.catalog.ArrivalGoodsDeleteAction.0")));
          return false;
        }
      }
      return true;
    }


    addErrorMessage(WebMessage.get(ActionErrorMessage.NO_CHECKED,
        Messages.getString("web.action.back.catalog.ArrivalGoodsDeleteAction.0")));
    return false;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    // 削除サービスの呼び出し
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());

    ServiceResult result;
    // 削除対象の入荷お知らせリストを作成
    for (String skuCode : getBean().getCheckedCode()) {
      result = service.deleteCommodityArrivalGoods(getLoginInfo().getShopCode(), skuCode);
      if (result.hasError()) {
        for (ServiceErrorContent error : result.getServiceErrorList()) {
          addErrorMessage(WebMessage.get(CatalogErrorMessage.ARRIVAL_GOODS_DELETE_ERROR, skuCode));
          Logger logger = Logger.getLogger(this.getClass());
          logger.debug(error.toString());
        }
        result.getServiceErrorList().clear();
      }
    }

    setNextUrl("/app/catalog/arrival_goods/init/" + WebConstantCode.COMPLETE_DELETE);

    return BackActionResult.RESULT_SUCCESS;
  }


  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.catalog.ArrivalGoodsDeleteAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3104071001";
  }

}
