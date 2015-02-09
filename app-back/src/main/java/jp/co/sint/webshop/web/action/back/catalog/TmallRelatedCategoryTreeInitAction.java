package jp.co.sint.webshop.web.action.back.catalog;

import java.util.List;

import jp.co.sint.webshop.data.dto.CCommodityHeader;
import jp.co.sint.webshop.data.dto.CategoryCommodity;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.catalog.CatalogQuery;
import jp.co.sint.webshop.service.catalog.CategoryInfo;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.catalog.TmallRelatedCategoryTreeBean;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.CompleteMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

import org.apache.log4j.Logger;

/**
 * U1040230:カテゴリ－関連付けのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class TmallRelatedCategoryTreeInitAction extends WebBackAction<TmallRelatedCategoryTreeBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    boolean authorization;

    // モードに関わらず、カテゴリの参照権限があれば表示
    BackLoginInfo login = getLoginInfo();
    authorization = Permission.CATEGORY_READ.isGranted(login) || Permission.COMMODITY_READ.isGranted(login);

    if (login.isShop() && getRequestParameter().getPathArgs().length > 0) {

      String shop = getRequestParameter().getPathArgs()[0];
      if (StringUtil.isNullOrEmpty(shop)) {
        return false;
      }

      authorization &= shop.equals(login.getShopCode());
    }

    return authorization;

  }

  private String shopCode = "";

  private String commodityCode = "";

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    if (getRequestParameter().getPathArgs().length >= 2) {
      shopCode = getRequestParameter().getPathArgs()[0];
      commodityCode = getRequestParameter().getPathArgs()[1];
      return true;
    }
    return false;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    Logger logger = Logger.getLogger(this.getClass());

    TmallRelatedCategoryTreeBean reqBean = new TmallRelatedCategoryTreeBean();
    reqBean.setShopCode(shopCode);
    reqBean.setCommodityCode(commodityCode);

    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    
    List<CategoryInfo> categoryList = service.getAllCategory();
    
    if (categoryList == null) {
      setNextUrl(null);
      logger.error(Messages.log("web.action.back.catalog.RelatedCategoryTreeInitAction.0"));
      addWarningMessage(Messages.getString("web.action.back.catalog.RelatedCategoryTreeInitAction.0"));
      return BackActionResult.RESULT_SUCCESS;
    }

    // 商品名の取得
    CCommodityHeader commodityHeader = service.getCCommodityheader(reqBean.getShopCode(), reqBean.getCommodityCode());
    if (commodityHeader == null) {
      setNextUrl("/app/catalog/commodity_list/init/nodata");
      return BackActionResult.RESULT_SUCCESS;
    }
    reqBean.setCommodityName(commodityHeader.getCommodityName());

    // カテゴリツリー構築用データの生成
    // 階層:表示順:名前:PC件数-Mobile件数
    StringBuilder treelist = new StringBuilder();
    StringBuilder pathList = new StringBuilder();
    for (int i = 0; i < categoryList.size(); i++) {
      treelist.append(categoryList.get(i).getCategoryCode() + ":" + categoryList.get(i).getDepth() + ":"
          + categoryList.get(i).getCategoryNamePc() + ":" + categoryList.get(i).getCommodityCountPc()
          + "-" + categoryList.get(i).getCommodityCountMobile() + "/");

      pathList.append(categoryList.get(i).getCategoryCode() + ":"
          + (categoryList.get(i).getPath()).replace(
              "/" + CatalogQuery.CATEGORY_DELIMITER, "") + "/");
    }
    reqBean.setCategoryList(treelist.toString());
    // 10.1.2 10089 追加 ここから
    reqBean.setCategoryNodeInfoList(categoryList);
    // 10.1.2 10089 追加 ここまで
    reqBean.setPathList(pathList.toString());

    // ショップコード+商品コードで検索したカテゴリの一覧
    List<CategoryCommodity> result = service.getCategoryCommodityList(reqBean.getShopCode(), reqBean.getCommodityCode());
    StringBuilder checkList = new StringBuilder();
    for (CategoryCommodity ct : result) {
      checkList.append("/" + ct.getCategoryCode() + ":");
    }

    reqBean.setCheckCategoryList(checkList.toString());

    setCompleteParameter();
    setRequestBean(reqBean);

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * 処理完了パラメータがあれば、処理完了メッセージをセットします <BR>
   * 処理完了パラメータがない場合は、何もメッセージを設定しません<BR>
   * 登録完了時：insert <BR>
   */
  private void setCompleteParameter() {
    String[] param = getRequestParameter().getPathArgs();
    String completeParam = "";
    if (param.length == 3) {
      completeParam = param[2];
    }

    if (completeParam.equals(WebConstantCode.COMPLETE_REGISTER)) {
      addInformationMessage(WebMessage.get(CompleteMessage.REGISTER_COMPLETE,
          Messages.getString("web.action.back.catalog.RelatedCategoryTreeInitAction.1")));
    } else if (completeParam.equals(WebConstantCode.COMPLETE_DELETE)) {
      addInformationMessage(WebMessage.get(CompleteMessage.DELETE_COMPLETE,
          Messages.getString("web.action.back.catalog.RelatedCategoryTreeInitAction.1")));
    }
  }

  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {
    TmallRelatedCategoryTreeBean reqBean = (TmallRelatedCategoryTreeBean) getRequestBean();
    if (Permission.CATEGORY_UPDATE.isGranted(getLoginInfo()) || Permission.COMMODITY_UPDATE.isGranted(getLoginInfo())) {
      reqBean.setRegisterPartsDisplayFlg(true);
    } else {
      reqBean.setRegisterPartsDisplayFlg(false);
    }
    setRequestBean(reqBean);
  }


  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.catalog.RelatedCategoryTreeInitAction.2");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3104030001";
  }

}
