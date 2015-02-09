package jp.co.sint.webshop.web.action.back.catalog;
 
import java.util.List;

import jp.co.sint.webshop.data.dto.Brand;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.catalog.BrandSearchCondition;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.catalog.BrandListBean; 
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.message.back.CompleteMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages; 

/**
 * U1040410:タグマスタのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class BrandListDeleteAction extends BrandListBaseAction {

  private static final String PICTURE_NAME = Messages.getString("web.action.back.catalog.BrandListCompleteAction.0");

  private String brandCode;

  /**
   * 初期処理を実行します<BR>
   * セッションからログイン情報を取得します<BR>
   * 画面で選択されたタグコードを取得します
   */
  public void init() {
    String[] param = getRequestParameter().getPathArgs();
    if (param.length == 1) {
      brandCode = param[0];
    } else {
      brandCode = "";
    }
  }

  /**
   * 認証処理を実行します<BR>
   * 削除権限があることをチェックします
   * 
   * @return 削除権限があればtrue
   */
  public boolean authorize() {
    return Permission.COMMODITY_DELETE.isGranted(getLoginInfo());
  }

  /**
   * Validationチェックを実行します<BR>
   * 画面からタグコードを取得できなければエラーとします<BR>
   * 
   * @return タグコードが取得できればtrue
   */
  public boolean validate() {
    if (StringUtil.hasValue(brandCode)) {
      return true;
    }
    addErrorMessage(WebMessage.get(ActionErrorMessage.BAD_URL));
    return false;
  }

  /**
   * 画面で入力されたタグ情報を登録します<BR>
   * タグ情報の削除は、ショップ管理者のみが行えます<BR>
   */
  @Override
  public WebActionResult callService() {
    BrandListBean reqBean = (BrandListBean) getBean();
    reqBean.getList().clear();

    // ショップ管理者、または一店舗版の場合はログイン情報からショップコードを取得
    if (getLoginInfo().isShop() || getConfig().isOne()) {
      reqBean.setSearchShopCode(getLoginInfo().getShopCode());
    }

    // 削除サービスを実行
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    ServiceResult result = service.deleteBrand(reqBean.getSearchShopCode(), brandCode);

    if (result.hasError()) {
      for (ServiceErrorContent errorContent : result.getServiceErrorList()) {
        if (errorContent.equals(CommonServiceErrorContent.DUPLICATED_REGISTER_ERROR)) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.DUPLICATED_REGISTER_ERROR));
        } else if (errorContent.equals(CommonServiceErrorContent.NO_DATA_ERROR)) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, Messages
              .getString("web.action.back.catalog.brandndListDeleteAction.0")));
        } else if (errorContent.equals(CommonServiceErrorContent.VALIDATION_ERROR)) {
          return BackActionResult.SERVICE_VALIDATION_ERROR;
        } else if (errorContent.equals(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR)) {
          return BackActionResult.SERVICE_ERROR;
        }
      }
      setNextUrl(null);
    } else {

      // 検索条件のクリア
      clearSearchCondition(reqBean);

      // 検索条件の設定
      BrandSearchCondition condition = setSearchCondition(reqBean);

      // タグ一覧の取得
      List<Brand> BrandList = getBrandList(reqBean, condition);

      // nextBeanの生成
      createInitNextBean(reqBean, BrandList);

      addInformationMessage(WebMessage.get(CompleteMessage.DELETE_COMPLETE, PICTURE_NAME));
    }
    // 画面表示用Beanを生成
    setRequestBean(reqBean);

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.catalog.BrandListDeleteAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3104091002";
  }

}
