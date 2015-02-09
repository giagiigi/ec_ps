package jp.co.sint.webshop.web.action.back.catalog;

import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.configure.WebshopConfig;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.catalog.CategoryInfo;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.utility.DIContainer;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.UploadResult;
import jp.co.sint.webshop.web.bean.UploadSubBean;
import jp.co.sint.webshop.web.bean.UploadSubBean.ResultType;
import jp.co.sint.webshop.web.bean.back.catalog.CategoryBean;
import jp.co.sint.webshop.web.bean.back.catalog.CategoryBean.CategoryAttributeBean;
import jp.co.sint.webshop.web.bean.back.catalog.CategoryBean.CategoryDetailBean;
import jp.co.sint.webshop.web.config.WebFrameworkConstants;
import jp.co.sint.webshop.web.login.back.BackLoginInfo;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.message.back.CompleteMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1040210:カテゴリのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class CategoryInitAction extends CategoryBaseAction {

  private static final String PICTURE_NAME = Messages.getString("web.action.back.catalog.CategoryInitAction.0");

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    BackLoginInfo login = getLoginInfo();
    // サイト管理者はカテゴリ参照権限、ショップ管理者は商品参照権限が必要
    return (login.isSite() && Permission.CATEGORY_READ.isGranted(login))
        || (login.isShop() && Permission.COMMODITY_READ.isGranted(login));
  }

  /**
   * beanのcreateAttributeを実行するかどうかの設定
   * 
   * @return true - 実行する false-実行しない
   */
  @Override
  public boolean isCallCreateAttribute() {
    return false;
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    return true;
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
      addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
          Messages.getString("web.action.back.catalog.CategoryInitAction.1")));
      setRequestBean(reqBean);
      return BackActionResult.RESULT_SUCCESS;
    }

    // 初期表示時の親カテゴリコードはカテゴリのルートを設定
    reqBean.setEdit(new CategoryDetailBean());
    setCategoryTree(reqBean, categoryList);

    if (getLoginInfo().isSite() && Permission.CATEGORY_UPDATE.isGranted(getLoginInfo())) {
      reqBean.getEdit().setParentCategoryCode(categoryList.get(0).getParentCategoryCode());

      List<CategoryAttributeBean> categoryAttributeListBean = new ArrayList<CategoryAttributeBean>();

      WebshopConfig config = DIContainer.getWebshopConfig();
      for (int i = 0; i < config.getCategoryAttributeMaxCount(); i++) {
        CategoryAttributeBean categoryAttributeBean = new CategoryAttributeBean();
        categoryAttributeBean.setCategoryAttributeNo(Integer.toString(i));
        categoryAttributeListBean.add(categoryAttributeBean);
      }
      reqBean.getEdit().setAttributeList(categoryAttributeListBean);

    } else {
      CategoryDetailBean edit = createCategoryEdit(reqBean);
      reqBean.setEdit(edit);

    }

    setRequestBean(reqBean);

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {
    BackLoginInfo login = getLoginInfo();

    CategoryBean nextBean = (CategoryBean) getRequestBean();

    if (login.isSite() && Permission.CATEGORY_UPDATE.isGranted(login)) {
      nextBean.setMode(WebConstantCode.DISPLAY_EDIT);
      nextBean.setEditMode(WebConstantCode.DISPLAY_EDIT);
    } else {
      nextBean.setMode(WebConstantCode.DISPLAY_HIDDEN);
      nextBean.setEditMode(WebConstantCode.DISPLAY_READONLY);
      nextBean.setReadOnly(true);
    }

    // 関連付け画面に遷移するためにはショップ管理者は商品参照、サイト管理者は商品参照とカテゴリ参照の両方が必要
    boolean hasRelatePermission = false;
    if (login.isShop()) {
      hasRelatePermission = Permission.COMMODITY_READ.isGranted(login);
    } else {
      hasRelatePermission = Permission.CATALOG_READ.isGranted(login) && Permission.CATEGORY_READ.isGranted(login);
    }

    nextBean.setRelateButtonDisplayFlg(hasRelatePermission);

    if (login.isSite() && Permission.CATEGORY_UPDATE.isGranted(login)) {
      nextBean.setRegisterButtonDisplayFlg(true);
      nextBean.setReadOnly(false);
    }

    if (login.isSite() && Permission.CATEGORY_DELETE.isGranted(login)) {
      nextBean.setReadOnly(false);
    }

    if (login.isSite() && Permission.CATEGORY_DATA_IO.isGranted(login)) {
      nextBean.setUploadTableDisplayFlg(true);
      nextBean.setReadOnly(false);
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
    if (param.length == 1) {
      completeParam = param[0];
    }

    if (completeParam.equals(WebConstantCode.COMPLETE_DELETE)) {
      addInformationMessage(WebMessage.get(CompleteMessage.DELETE_COMPLETE, PICTURE_NAME));
    }

    if (completeParam.equals(WebConstantCode.COMPLETE_UPLOAD)) {
      UploadSubBean messageBean = (UploadSubBean) getBean().getSubBeanMap().get(WebFrameworkConstants.ATTRIBUTE_UPLOAD_BEAN);
      List<UploadResult> resultList = messageBean.getUploadDetailList();

      if (messageBean.getResult().equals(ResultType.SUCCESS)) {
        addInformationMessage(WebMessage.get(CompleteMessage.CSV_IMPORT_COMPLETE));
      } else if (messageBean.getResult().equals(ResultType.FAILED)) {
        addErrorMessage(WebMessage.get(ActionErrorMessage.CSV_IMPORT_FAILED));
      } else {
        addWarningMessage(WebMessage.get(ActionErrorMessage.CSV_IMPORT_PARTIAL, ""));
      }

      for (UploadResult ur : resultList) {

        for (String s : ur.getInformationMessage()) {
          addInformationMessage(s);
        }
        for (String s : ur.getWarningMessage()) {
          addWarningMessage(s);
        }
        for (String s : ur.getErrorMessage()) {
          addErrorMessage(s);
        }
      }
    }

  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.catalog.CategoryInitAction.2");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3104021003";
  }

}
