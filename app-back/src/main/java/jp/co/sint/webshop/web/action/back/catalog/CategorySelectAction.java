package jp.co.sint.webshop.web.action.back.catalog;

import java.util.List;

import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.catalog.CategoryInfo;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.catalog.CategoryBean;
import jp.co.sint.webshop.web.bean.back.catalog.CategoryBean.CategoryDetailBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.CompleteMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1040210:カテゴリのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class CategorySelectAction extends CategoryBaseAction {

  private String categoryCode;

  private static final String PICTURE_NAME = Messages.getString("web.action.back.catalog.CategorySelectAction.0");

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    // カテゴリ参照権限、商品管理参照権限、商品参照権限を持つユーザのみカテゴリを選択できる
    return Permission.CATEGORY_READ.isGranted(getLoginInfo()) || Permission.CATALOG_READ.isGranted(getLoginInfo())
        || Permission.COMMODITY_READ.isGranted(getLoginInfo());
  }

  /**
   * 初期処理を実行します。
   */
  @Override
  public void init() {
    // URLパラメータから取得した表示対象のカテゴリコードを設定
    String[] param = getRequestParameter().getPathArgs();
    if (param.length > 0) {
      categoryCode = param[0];
    } else {
      categoryCode = "";
    }
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    return StringUtil.hasValue(categoryCode);
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    CategoryBean reqBean = new CategoryBean();

    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    List<CategoryInfo> categoryList = service.getAllCategory();

    if (categoryList.size() < 1) {
      addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, Messages
          .getString("web.action.back.catalog.CategorySelectAction.1")));
      setRequestBean(reqBean);
      return BackActionResult.RESULT_SUCCESS;
    }

    setCategoryTree(reqBean, categoryList);
    reqBean.getEdit().setCategoryCode(categoryCode);
    CategoryDetailBean edit = createCategoryEdit(reqBean);
    reqBean.setEdit(edit);

    if (StringUtil.isNullOrEmpty(reqBean.getEdit().getCategoryCode())) {
      addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, Messages
          .getString("web.action.back.catalog.CategorySelectAction.2")));
    }

    setRequestBean(reqBean);

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {

    CategoryBean nextBean = (CategoryBean) getRequestBean();
    nextBean.setMode(WebConstantCode.DISPLAY_HIDDEN);
    if (getLoginInfo().isSite() && Permission.CATEGORY_UPDATE.isGranted(getLoginInfo())) {
      nextBean.setEditMode(WebConstantCode.DISPLAY_EDIT);
    } else {
      nextBean.setEditMode(WebConstantCode.DISPLAY_READONLY);
      nextBean.setReadOnly(true);
    }

    boolean hasRelatePermission = false;
    if (getLoginInfo().isShop()) {
      hasRelatePermission = Permission.COMMODITY_READ.isGranted(getLoginInfo());
    } else {
      hasRelatePermission = Permission.CATALOG_READ.isGranted(getLoginInfo()) && Permission.CATEGORY_READ.isGranted(getLoginInfo());
    }

    nextBean.setRelateButtonDisplayFlg(hasRelatePermission);
    
    
    if (getLoginInfo().isSite() && Permission.CATEGORY_UPDATE.isGranted(getLoginInfo())) {
      nextBean.setRegisterButtonDisplayFlg(true);
      nextBean.setReadOnly(false);
    }

    if (getLoginInfo().isSite() && Permission.CATEGORY_DELETE.isGranted(getLoginInfo())) {
      nextBean.setDeleteButtonDisplayFlg(true);
      nextBean.setReadOnly(false);
    }

    if (getLoginInfo().isSite() && Permission.CATEGORY_DATA_IO.isGranted(getLoginInfo())) {
      nextBean.setUploadTableDisplayFlg(true);
      nextBean.setReadOnly(false);
    }

    if (nextBean.getEdit().getParentCategoryCode().equals("/") || Permission.CATEGORY_UPDATE.isDenied(getLoginInfo())) {
      nextBean.setReadOnly(true);
    }

    setCompleteParameter();
    setRequestBean(nextBean);

  }

  /**
   * 処理完了パラメータがあれば、処理完了メッセージをセットします <BR>
   * 処理完了パラメータがない場合は、何もメッセージを設定しません<BR>
   * 登録完了時：insert 更新完了時：update 削除完了時：delete <BR>
   */
  private void setCompleteParameter() {
    String[] param = getRequestParameter().getPathArgs();
    String completeParam = "";
    if (param.length >= 2) {
      completeParam = param[1];
    }

    if (completeParam.equals(WebConstantCode.COMPLETE_INSERT)) {
      addInformationMessage(WebMessage.get(CompleteMessage.REGISTER_COMPLETE, PICTURE_NAME));

    } else if (completeParam.equals(WebConstantCode.COMPLETE_UPDATE)) {
      addInformationMessage(WebMessage.get(CompleteMessage.UPDATE_COMPLETE, PICTURE_NAME));

    } else if (completeParam.equals(WebConstantCode.COMPLETE_DELETE)) {
      addInformationMessage(WebMessage.get(CompleteMessage.DELETE_COMPLETE, PICTURE_NAME));
    }
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.catalog.CategorySelectAction.3");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3104021006";
  }

}
