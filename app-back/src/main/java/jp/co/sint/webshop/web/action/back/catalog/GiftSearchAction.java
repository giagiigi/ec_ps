package jp.co.sint.webshop.web.action.back.catalog;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.code.CodeUtil;
import jp.co.sint.webshop.data.domain.DisplayFlg;
import jp.co.sint.webshop.data.domain.TaxType;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.catalog.GiftCount;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.catalog.GiftBean;
import jp.co.sint.webshop.web.bean.back.catalog.GiftBean.GiftDetailBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1040310:ギフトのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class GiftSearchAction extends GiftBaseAction {

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
    return validateBean(getBean());
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    GiftBean reqBean = getBean();

    // 検索結果をクリア
    reqBean.getList().clear();

    // 検索ショップコードを設定
    if (getLoginInfo().isShop()) {
      // ショップ管理者なら自ショップコードを設定
      reqBean.setSearchShopCode(getLoginInfo().getShopCode());
    }

    // ショップが持つギフト一覧を取得
    List<GiftCount> giftList = getGiftList(reqBean);

    if (giftList.isEmpty()) {
      addWarningMessage(WebMessage.get(ActionErrorMessage.SEARCH_RESULT_NOT_FOUND));
    }

    // 画面表示用Beanを生成
    List<GiftDetailBean> detailList = new ArrayList<GiftDetailBean>();
    for (GiftCount gift : giftList) {
      GiftDetailBean detail = new GiftDetailBean();
      setResultGiftList(gift, detail);
      detailList.add(detail);
    }
    reqBean.setList(detailList);

    GiftDetailBean edit = new GiftDetailBean();
    edit.setShopCode(reqBean.getSearchShopCode());
    edit.setDisplayFlg(DisplayFlg.HIDDEN.getValue());
    edit.setGiftTaxType(CodeUtil.getDefaultValue(TaxType.class).getValue());
    reqBean.setEdit(edit);

    // 画面表示用Beanを次画面Beanに設定
    setRequestBean(reqBean);

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {

    // 画面項目の表示/表示を設定する
    GiftBean bean = (GiftBean) getRequestBean();
    bean.setMode(MODE_NEW);
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
    return Messages.getString("web.action.back.catalog.GiftSearchAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3104031005";
  }

}
