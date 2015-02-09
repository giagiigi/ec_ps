package jp.co.sint.webshop.web.action.back.catalog;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.code.CodeAttribute;
import jp.co.sint.webshop.code.NameValue;
import jp.co.sint.webshop.data.SearchResult;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.catalog.RelatedBaseEvent;
import jp.co.sint.webshop.service.catalog.RelatedSearchConditionBaseEvent;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.catalog.RelatedBean;
import jp.co.sint.webshop.web.bean.back.catalog.RelatedBean.RelatedDetailBean;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.PagerUtil;

/**
 * U1040210:カテゴリのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class RelatedInitAction extends RelatedBaseAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    boolean auth = false;
    RelatedBean reqBean = getBean();

    // pictureModeがcampaignのときはcampaignの参照権限が、
    // それ以外のときはcommodityの参照権限が必要
    if (reqBean.getPictureMode().equals("campaign")) {
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
    RelatedBean reqBean = (RelatedBean) getBean();
    return validateItems(reqBean, "effectualCode", "pictureMode", "searchShopCode");
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public WebActionResult callService() {

    RelatedBean reqBean = getBean();

    // ショップ管理者の場合はログイン情報からショップコードを取得
    if (getLoginInfo().isShop()) {
      reqBean.setSearchShopCode(getLoginInfo().getShopCode());
    }
    
    List<CodeAttribute> langCode = new ArrayList<CodeAttribute>();

    // 关联语种下拉框选项设定
    langCode.add(new NameValue("中", "zh-cn"));
    langCode.add(new NameValue("日", "ja-jp"));
    langCode.add(new NameValue("英", "en-us"));
    reqBean.setLangCodes(langCode);

    // 検索条件の作成
    RelatedSearchConditionBaseEvent condition = setSearchCondition(reqBean);

    // URLパラメータで取得した関連付けの種類に応じて、インスタンス化するクラスを変更
    // タグ:tag ギフト:gift キャンペーン:campaign
    RelatedBase related = RelatedBase.createNewInstance(reqBean.getPictureMode(), getLoginInfo());
    related.setEffectualCode(reqBean.getEffectualCode());

    // リクエストパラメータで取得したコードに関連付いている商品の一覧を取得
    SearchResult<RelatedBaseEvent> searchResult = related.search(reqBean, condition);
    List<RelatedBaseEvent> relatedList = searchResult.getRows();

    // オーバーフローチェック
    prepareSearchWarnings(searchResult, SearchWarningType.OVERFLOW);

    if (relatedList != null) {
      reqBean.setPagerValue(PagerUtil.createValue(searchResult));
      reqBean.getList().clear();

      // nextBeanを作成
      setNextBean(reqBean, relatedList);
    }

    RelatedDetailBean edit = new RelatedDetailBean();
    reqBean.setEdit(edit);

    // ショップ名の設定
    reqBean.setShopName(getShopName());
    setNextUrl(null);

    setRequestBean(reqBean);

    return BackActionResult.RESULT_SUCCESS;
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
    return Messages.getString("web.action.back.catalog.RelatedInitAction.0");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3104016003";
  }

}
