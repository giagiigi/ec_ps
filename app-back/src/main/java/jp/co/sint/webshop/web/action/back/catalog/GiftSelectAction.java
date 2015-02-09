package jp.co.sint.webshop.web.action.back.catalog;

import java.util.List;

import jp.co.sint.webshop.data.domain.OperatingMode;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.UtilService;
import jp.co.sint.webshop.service.catalog.GiftCount;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.catalog.GiftBean;
import jp.co.sint.webshop.web.bean.back.catalog.GiftBean.GiftDetailBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.message.back.catalog.CatalogErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1040310:ギフトのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class GiftSelectAction extends GiftBaseAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    if (getConfig().getOperatingMode().equals(OperatingMode.ONE)) {
      return Permission.CATALOG_READ.isGranted(getLoginInfo());
    } else {
      return Permission.COMMODITY_READ.isGranted(getLoginInfo());
    }
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
          Messages.getString("web.action.back.catalog.GiftSelectAction.0")));
      return false;
    }

    String giftCode = getRequestParameter().getPathArgs()[0];
    for (GiftDetailBean detail : getBean().getList()) {
      if (detail.getGiftCode().equals(giftCode)) {
        return true;
      }

    }

    addErrorMessage(WebMessage.get(CatalogErrorMessage.CODE_FAILED,
        Messages.getString("web.action.back.catalog.GiftSelectAction.0")));
    return false;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    GiftBean nextBean = getBean();

    // ショップリストの取得
    if (getLoginInfo().isSite()) {
      // サイト管理者の場合のみ、ショップリストを取得
      UtilService service = ServiceLocator.getUtilService(getLoginInfo());
      getBean().setShopList(service.getShopNames(true));
    }

    // ショップのギフト一覧を取得
    List<GiftCount> giftList = getGiftList(getBean());

    // URLパラメータから選択ギフトコードを取得
    // URL形式: ～/app/catalog/gift/select/【giftCode】
    String giftCode = "";
    giftCode = getRequestParameter().getPathArgs()[0];

    boolean exists = false;
    // 画面表示用Beanを生成
    for (GiftCount gift : giftList) {
      // 選択ギフトコードと一致すれば編集項目に設定
      if (gift.getGiftCode().equals(giftCode)) {
        GiftDetailBean editBean = new GiftDetailBean();
        setResultGiftList(gift, editBean);
        exists = true;
        nextBean.setEdit(editBean);
      }
    }
    if (!exists) {
      addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
          Messages.getString("web.action.back.catalog.GiftSelectAction.0")));
    }

    // 画面表示用Beanを次画面Beanに設定
    setRequestBean(nextBean);

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {

    // 画面項目の表示/非表示を設定する
    GiftBean bean = (GiftBean) getRequestBean();
    bean.setMode(MODE_UPDATE);
    setDisplayControl(bean);

    // 次画面のBeanを設定する
    setRequestBean(bean);
  }


  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.catalog.GiftSelectAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3104031006";
  }

}
