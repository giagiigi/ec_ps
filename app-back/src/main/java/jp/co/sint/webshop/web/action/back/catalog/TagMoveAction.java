package jp.co.sint.webshop.web.action.back.catalog;

import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.catalog.RelatedBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.DisplayTransition;

/**
 * U1040410:タグマスタのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class TagMoveAction extends TagBaseAction {

  private String shopCode;

  private String tagCode;

  /**
   * 初期処理を実行します<BR>
   * セッションからログイン情報を取得します<BR>
   * 画面で選択されたタグコードを取得します
   */
  public void init() {
    String[] param = getRequestParameter().getPathArgs();
    if (param.length == 2) {
      setShopCode(param[0]);
      setTagCode(param[1]);
    } else {
      setShopCode("");
      setTagCode("");
    }
  }

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  public boolean authorize() {
    boolean authorization = true;

    if (getLoginInfo().isShop()) {
      authorization &= this.shopCode.equals(getLoginInfo().getShopCode());
    }

    return authorization;

  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  public boolean validate() {
    boolean result = false;

    // URLパラメータにショップコードとタグコードが存在しない場合はエラーとする
    if (StringUtil.isNullOrEmpty(shopCode) || StringUtil.isNullOrEmpty(tagCode)) {
      result = false;
      addWarningMessage(Messages.getString("web.action.back.catalog.TagMoveAction.0"));
    } else {
      result = true;
    }
    return result;
  }

  /**
   * タグ関連付け画面へ遷移します<BR>
   */
  @Override
  public WebActionResult callService() {
    // タグ存在チェック
    CatalogService catalogSv = ServiceLocator.getCatalogService(getLoginInfo());
    if (catalogSv.getTag(getShopCode(), getTagCode()) == null) {
      addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
          Messages.getString("web.action.back.catalog.TagMoveAction.1")));
      setRequestBean(getBean());

      return BackActionResult.RESULT_SUCCESS;
    }

    // 関連付け画面用のBeanに値をセット
    RelatedBean relatedBean = new RelatedBean();
    relatedBean.setSearchShopCode(getShopCode());
    relatedBean.setEffectualCode(getTagCode());
    relatedBean.setPictureMode("tag");

    // 前画面情報設定
    DisplayTransition.add(null, "/app/catalog/tag/search_back", getSessionContainer());

    setNextUrl("/app/catalog/related/init/");
    setRequestBean(relatedBean);

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * @return shopCode
   */
  private String getShopCode() {
    return shopCode;
  }

  /**
   * @param shopCode
   *          設定する shopCode
   */
  private void setShopCode(String shopCode) {
    this.shopCode = shopCode;
  }

  /**
   * @return tagCode
   */
  private String getTagCode() {
    return tagCode;
  }

  /**
   * @param tagCode
   *          設定する tagCode
   */
  private void setTagCode(String tagCode) {
    this.tagCode = tagCode;
  }


  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.catalog.TagMoveAction.2");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3104041004";
  }

}
