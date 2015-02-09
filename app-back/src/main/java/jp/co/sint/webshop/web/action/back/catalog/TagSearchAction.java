package jp.co.sint.webshop.web.action.back.catalog;

import java.util.List;

import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.catalog.TagCount;
import jp.co.sint.webshop.service.catalog.TagSearchCondition;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.catalog.TagBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerUtil;

/**
 * U1040410:タグマスタのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class TagSearchAction extends TagBaseAction {

  private TagSearchCondition condition;

  protected TagSearchCondition getCondition() {
    return PagerUtil.createSearchCondition(getRequestParameter(), condition);
  }

  protected TagSearchCondition getSearchCondition() {
    return this.condition;
  }

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
  public boolean authorize() {
    return Permission.COMMODITY_READ.isGranted(getLoginInfo()) || Permission.CATALOG_READ.isGranted(getLoginInfo());
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  public boolean validate() {
    TagBean reqBean = getBean();

    boolean isValid = validateBean(reqBean);

    if (!isValid) {
      reqBean.setPagerValue(new TagBean().getPagerValue());
      reqBean.getList().clear();
    }

    return isValid;
  }

  /**
   * 指定されたショップコードを元に、ショップが持つタグの一覧を取得します<BR>
   * サイト管理者の場合は、画面で選択されたショップコードを元に一覧を取得します<BR>
   * ショップ管理者の場合は、ログイン情報のショップコードを元に一覧を取得します
   */
  @Override
  public WebActionResult callService() {

    TagBean reqBean = getBean();

    reqBean.getList().clear();

    // ショップ管理者、または一店舗版の場合はログイン情報からショップコードを取得
    if (getLoginInfo().isShop() || getConfig().isOne()) {
      reqBean.setSearchShopCode(getLoginInfo().getShopCode());
    }

    // 検索条件の設定
    condition = setSearchCondition(reqBean);

    // ページング情報の追加
    condition = getCondition();

    // タグ一覧の取得
    List<TagCount> tagList = getTagList(reqBean, condition);
    if (tagList.isEmpty()) {
      addWarningMessage(WebMessage.get(ActionErrorMessage.SEARCH_RESULT_NOT_FOUND));
    }

    // nextBeanの生成
    createInitNextBean(reqBean, tagList);

    // nextBeanのセット
    setRequestBean(reqBean);

    setNextUrl(null);

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {
    TagBean reqBean = (TagBean) getRequestBean();
    setEditDisplayControl(reqBean);
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.catalog.TagSearchAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3104041006";
  }

}
