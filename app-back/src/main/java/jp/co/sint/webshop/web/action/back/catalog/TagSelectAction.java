package jp.co.sint.webshop.web.action.back.catalog;

import java.util.List;

import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.catalog.TagCount;
import jp.co.sint.webshop.service.catalog.TagSearchCondition;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.catalog.TagBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1040410:タグマスタのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class TagSelectAction extends TagBaseAction {

  private String tagCode;

  /**
   * 初期処理を実行します<BR>
   * セッションからログイン情報を取得します<BR>
   * 画面で選択されたタグコードを取得します
   */
  @Override
  public void init() {
    String[] param = getRequestParameter().getPathArgs();
    if (param.length == 1) {
      tagCode = param[0];
    } else {
      tagCode = "";
    }
  }

  /**
   * 認証処理を実行します<BR>
   * 更新権限があることをチェックします
   */
  @Override
  public boolean authorize() {
    return Permission.COMMODITY_UPDATE.isGranted(getLoginInfo());
  }

  /**
   * Validationチェックを実行します<BR>
   */
  @Override
  public boolean validate() {
    if (StringUtil.hasValue(tagCode)) {
      return true;
    }
    addErrorMessage(WebMessage.get(ActionErrorMessage.BAD_URL));
    return false;
  }

  /**
   * 画面で選択されたタグコードに関連付いているタグ情報を取得します<BR>
   * 
   * @return アクションの処理結果
   */
  public WebActionResult callService() {

    TagBean reqBean = getBean();

    // ショップ管理者、または一店舗版の場合はログイン情報からショップコードを取得
    if (getLoginInfo().isShop() || getConfig().isOne()) {
      reqBean.setSearchShopCode(getLoginInfo().getShopCode());
    }

    // 検索条件の設定
    TagSearchCondition condition = new TagSearchCondition();
    condition.setShopCode(reqBean.getSearchShopCode());
    condition.setSearchTagCodeStart(reqBean.getSearchTagCodeStart());
    condition.setSearchTagCodeEnd(reqBean.getSearchTagCodeEnd());
    condition.setSearchTagName(reqBean.getSearchTagName());
    condition.setPageSize(reqBean.getPagerValue().getPageSize());
    condition.setCurrentPage(reqBean.getPagerValue().getCurrentPage());
    condition.setMaxFetchSize(reqBean.getPagerValue().getMaxFetchSize());

    // タグ一覧の取得
    List<TagCount> tagList = getTagList(reqBean, condition);

    // 画面表示用Beanを生成
    createSelectNextBean(reqBean, tagList, tagCode);

    // nextBeanのセット
    setRequestBean(reqBean);

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {
    TagBean reqBean = (TagBean) getRequestBean();
    setHiddenDisplayControl(reqBean);
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.catalog.TagSelectAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3104041008";
  }

}
