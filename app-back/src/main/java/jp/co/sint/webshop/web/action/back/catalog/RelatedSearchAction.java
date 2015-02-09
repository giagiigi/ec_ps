package jp.co.sint.webshop.web.action.back.catalog;

import java.util.List;

import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.catalog.RelatedBaseEvent;
import jp.co.sint.webshop.service.catalog.RelatedSearchConditionBaseEvent;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.catalog.RelatedBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerUtil;

import org.apache.log4j.Logger;

/**
 * U1040160:関連付けのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class RelatedSearchAction extends RelatedBaseAction {

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    RelatedBean reqBean = (RelatedBean) getBean();

    // ショップ管理者の場合はログイン情報からショップコードを取得
    if (getLoginInfo().isShop()) {
      reqBean.setSearchShopCode(getLoginInfo().getShopCode());
    }

    // 検索条件をセット
    RelatedSearchConditionBaseEvent condition = setSearchCondition(reqBean);

    // URLパラメータで取得した関連付けの種類に応じて、インスタンス化するクラスを変更
    RelatedBase related = RelatedBase.createNewInstance(reqBean.getPictureMode(), getLoginInfo());
    related.setEffectualCode(reqBean.getEffectualCode());

    // URLパラメータで取得したコードに関連付いている商品の一覧を取得
    RelatedBean relatedBean = reqBean;
    SearchResult<RelatedBaseEvent> result = related.search(relatedBean, condition);
    relatedBean.getList().clear();

    // ページ情報を追加
    relatedBean.setPagerValue(PagerUtil.createValue(result));
    List<RelatedBaseEvent> relatedList = result.getRows();

    // 件数0件,オーバーフローチェック
    prepareSearchWarnings(result, SearchWarningType.BOTH);

    // nextBeanを作成
    setNextBean(relatedBean, relatedList);

    setNextUrl(null);
    setRequestBean(relatedBean);

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    boolean auth = false;
    // pictureModeがcampaignのときはcampaignの参照権限が、
    // それ以外のときはcommodityの参照権限が必要
    if (getBean().getPictureMode().equals("campaign")) {
      auth = Permission.CAMPAIGN_READ_SHOP.isGranted(getLoginInfo()) || Permission.CAMPAIGN_READ_SITE.isGranted(getLoginInfo());
    } else {
      auth = Permission.COMMODITY_READ.isGranted(getLoginInfo()) || Permission.CATALOG_READ.isGranted(getLoginInfo());
    }

    return auth;
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    boolean result = true;
    RelatedBean reqBean = (RelatedBean) getBean();

    // bean(検索条件)のvalidationチェック
    result = validateBean(reqBean);

    // 検索条件の大小チェック
    RelatedSearchConditionBaseEvent condition = setSearchCondition(reqBean);
    if (!condition.isValid()) {
      addErrorMessage(WebMessage.get(ActionErrorMessage.SEARCHCONDITION_ERROR,
          Messages.getString("web.action.back.catalog.RelatedSearchAction.0")));

      Logger logger = Logger.getLogger(this.getClass());
      logger.debug(WebMessage.get(ActionErrorMessage.SEARCHCONDITION_ERROR,
          Messages.getString("web.action.back.catalog.RelatedSearchAction.0")));

      result = false;
    }

    return result;
  }

  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {
    RelatedBean relatedBean = (RelatedBean) getRequestBean();
    setDisplayControl(relatedBean);
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.catalog.RelatedSearchAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3104016005";
  }

}
