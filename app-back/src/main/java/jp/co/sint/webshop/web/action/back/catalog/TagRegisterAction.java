package jp.co.sint.webshop.web.action.back.catalog;

import jp.co.sint.webshop.data.dto.Tag;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.utility.NumUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.catalog.TagBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1040410:タグマスタのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class TagRegisterAction extends TagBaseAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  public boolean authorize() {
    return Permission.COMMODITY_UPDATE.isGranted(getLoginInfo());
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  public boolean validate() {
    TagBean reqBean = getBean();
    return validateBean(reqBean.getEdit());
  }

  /**
   * 画面で入力されたタグ情報を登録します<BR>
   * タグ情報の登録は、ショップ管理者のみが行えます<BR>
   */
  @Override
  public WebActionResult callService() {

    TagBean reqBean = getBean();

    // ショップ管理者、または一店舗版の場合はログイン情報からショップコードを取得
    if (getLoginInfo().isShop() || getConfig().isOne()) {
      reqBean.setSearchShopCode(getLoginInfo().getShopCode());
    }

    // 登録用データを生成
    Tag tag = new Tag();
    tag.setShopCode(reqBean.getSearchShopCode());
    tag.setTagCode(reqBean.getEdit().getTagCode());
    tag.setTagName(reqBean.getEdit().getTagName());
    //20120514 tuxinwei add start
    tag.setTagNameEn(reqBean.getEdit().getTagNameEn());
    tag.setTagNameJp(reqBean.getEdit().getTagNameJp());
    //20120514 tuxinwei add end
    tag.setDisplayOrder(NumUtil.toLong(reqBean.getEdit().getDisplayOrder(), null));

    // 登録サービスを実行
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    ServiceResult result = service.insertTag(tag);
    if (result.hasError()) {
      for (ServiceErrorContent errorContent : result.getServiceErrorList()) {
        if (errorContent.equals(CommonServiceErrorContent.DUPLICATED_REGISTER_ERROR)) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.DUPLICATED_REGISTER_ERROR,
              Messages.getString("web.action.back.catalog.TagRegisterAction.0")));
        } else if (errorContent.equals(CommonServiceErrorContent.NO_DATA_ERROR)) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR,
              Messages.getString("web.action.back.catalog.TagRegisterAction.0")));
        } else if (errorContent.equals(CommonServiceErrorContent.VALIDATION_ERROR)) {
          return BackActionResult.SERVICE_VALIDATION_ERROR;
        } else if (errorContent.equals(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR)) {
          return BackActionResult.SERVICE_ERROR;
        }
      }
      setNextUrl(null);
    } else {
      setNextUrl("/app/catalog/tag/complete/" + WebConstantCode.COMPLETE_INSERT);
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
    return Messages.getString("web.action.back.catalog.TagRegisterAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3104041005";
  }

}
