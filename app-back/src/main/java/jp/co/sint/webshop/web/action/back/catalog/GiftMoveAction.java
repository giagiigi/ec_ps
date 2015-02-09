package jp.co.sint.webshop.web.action.back.catalog;

import jp.co.sint.webshop.data.dto.Gift;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.catalog.GiftBean;
import jp.co.sint.webshop.web.bean.back.catalog.RelatedBean;
import jp.co.sint.webshop.web.bean.back.catalog.GiftBean.GiftDetailBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.message.back.catalog.CatalogErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.DisplayTransition;

/**
 * U1040310:ギフトのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class GiftMoveAction extends GiftBaseAction {

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
    if (getRequestParameter().getPathArgs().length < 1) {
      addErrorMessage(WebMessage.get(CatalogErrorMessage.CODE_FAILED,
          Messages.getString("web.action.back.catalog.GiftMoveAction.0")));
      return false;
    }

    String giftCode = getRequestParameter().getPathArgs()[0];
    for (GiftDetailBean detail : getBean().getList()) {
      if (detail.getGiftCode().equals(giftCode)) {
        return true;
      }
    }

    addErrorMessage(WebMessage.get(CatalogErrorMessage.CODE_FAILED,
        Messages.getString("web.action.back.catalog.GiftMoveAction.0")));
    return false;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    String giftCode = getRequestParameter().getPathArgs()[0];
    String shopCode = "";

    for (GiftDetailBean detail : getBean().getList()) {
      if (detail.getGiftCode().equals(giftCode)) {
        shopCode = detail.getShopCode();
      }
    }

    // 存在チェック
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    Gift gift = service.getGift(shopCode, giftCode);
    if (gift == null) {
      addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
          Messages.getString("web.action.back.catalog.GiftMoveAction.0")));

      setRequestBean(getBean());
      return BackActionResult.RESULT_SUCCESS;
    }

    // nextBeanの生成
    RelatedBean nextBean = new RelatedBean();
    nextBean.setSearchShopCode(gift.getShopCode());
    nextBean.setEffectualCode(gift.getGiftCode());
    nextBean.setPictureMode("gift");
    setRequestBean(nextBean);

    // 前画面情報設定
    GiftBean bean = getBean();
    String previousPage = "";
    if (StringUtil.hasValue(bean.getEdit().getGiftCode())) {
      previousPage = "/app/catalog/gift/select/" + bean.getEdit().getGiftCode();
    } else {
      previousPage = "/app/catalog/gift/init";
    }
    DisplayTransition.add(getBean(), previousPage, getSessionContainer());

    // 商品関連付け画面へ遷移
    setNextUrl("/app/catalog/related/init");
    return BackActionResult.RESULT_SUCCESS;
  }


  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.catalog.GiftMoveAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3104031003";
  }

}
