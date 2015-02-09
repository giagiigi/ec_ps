package jp.co.sint.webshop.web.action.back.catalog;

import jp.co.sint.webshop.data.dto.Brand;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult; 
import jp.co.sint.webshop.service.result.CommonServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent; 
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult; 
import jp.co.sint.webshop.web.bean.back.catalog.BrandListBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.CompleteMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages; 
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1040410:タグマスタのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class BrandListRegisterAction extends BrandListBaseAction {

  private static final String PICTURE_NAME = Messages.getString("web.action.back.catalog.BrandListCompleteAction.0");

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
    BrandListBean reqBean = getBean();
    if (reqBean.isSearchTableDisplayFlg() == false) {
      return validateBean(reqBean.getEdit());
    } else {
      return true;
    }
  }

  /**
   * 画面で入力されたタグ情報を登録します<BR>
   * タグ情報の登録は、ショップ管理者のみが行えます<BR>
   */
  @Override
  public WebActionResult callService() {

    BrandListBean reqBean = getBean();
    if (reqBean.isSearchTableDisplayFlg() == false) {
      // ショップ管理者、または一店舗版の場合はログイン情報からショップコードを取得
      if (getLoginInfo().isShop() || getConfig().isOne()) {
        reqBean.setSearchShopCode(getLoginInfo().getShopCode());
      }

      // 登録用データを生成
      Brand brand = new Brand();
      brand.setShopCode(reqBean.getSearchShopCode());
      brand.setBrandCode(reqBean.getEdit().getBrandCode());
      brand.setBrandName(reqBean.getEdit().getBrandName());
      brand.setBrandDescription(reqBean.getEdit().getBrandDescription());
      //20120524 tuxinwei add start
      brand.setBrandDescriptionEn(reqBean.getEdit().getBrandDescriptionEn());
      brand.setBrandDescriptionJp(reqBean.getEdit().getBrandDescriptionJp());
      //20120524 tuxinwei add end
      brand.setBrandEnglishName(reqBean.getEdit().getBrandEnglishName());
      //20120515 tuxinwei add start
      brand.setBrandJapaneseName(reqBean.getEdit().getBrandJapaneseName());
      //20120515 tuxinwei add end
      brand.setBrandNameAbbr(reqBean.getEdit().getBrandNameAbbr());
      brand.setTmallBrandCode(reqBean.getEdit().getTmallBrandCode());
      brand.setTmallBrandName(reqBean.getEdit().getTmallBrandName());
      brand.setBrandType(reqBean.getEdit().getBrandType());
      brand.setKeywordCn2(reqBean.getEdit().getKeywordCn2());
      brand.setKeywordEn2(reqBean.getEdit().getKeywordEn2());
      brand.setKeywordJp2(reqBean.getEdit().getKeywordJp2());
      // 20130703 txw add start
      brand.setTitle(reqBean.getEdit().getTitle());
      brand.setTitleEn(reqBean.getEdit().getTitleEn());
      brand.setTitleJp(reqBean.getEdit().getTitleJp());
      brand.setDescription(reqBean.getEdit().getDescription());
      brand.setDescriptionEn(reqBean.getEdit().getDescriptionEn());
      brand.setDescriptionJp(reqBean.getEdit().getDescriptionJp());
      brand.setKeyword(reqBean.getEdit().getKeyword());
      brand.setKeywordEn(reqBean.getEdit().getKeywordEn());
      brand.setKeywordJp(reqBean.getEdit().getKeywordJp());
      // 20130703 txw add end
      // 登録サービスを実行
      CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
      ServiceResult result = service.insertBrand(brand);
      // service.insertTag(brand);
      if (result.hasError()) {
        for (ServiceErrorContent errorContent : result.getServiceErrorList()) {
          if (errorContent.equals(CommonServiceErrorContent.DUPLICATED_REGISTER_ERROR)) {
            addErrorMessage(WebMessage.get(ServiceErrorMessage.DUPLICATED_REGISTER_ERROR, Messages
                .getString("web.action.back.catalog.BrandListRegisterAction.0")));
          } else if (errorContent.equals(CommonServiceErrorContent.NO_DATA_ERROR)) {
            addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, Messages
                .getString("web.action.back.catalog.BrandListRegisterAction.0")));
          } else if (errorContent.equals(CommonServiceErrorContent.VALIDATION_ERROR)) {
            return BackActionResult.SERVICE_VALIDATION_ERROR;
          } else if (errorContent.equals(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR)) {
            return BackActionResult.SERVICE_ERROR;
          }
        }
        setNextUrl(null);
      } else {
        // 获取操作完成信息
        addInformationMessage(WebMessage.get(CompleteMessage.REGISTER_COMPLETE, PICTURE_NAME));
      }

    }
    setRequestBean(reqBean);
    reqBean.setSearchResultTableDisplayFlg(false);
    reqBean.setSearchTableDisplayFlg(false);
    reqBean.setRegisterTableDisplayFlg(true);
    reqBean.setDisplayNextButton(true);
    reqBean.setRegisterButtonDisplayFlg(false);
    reqBean.setBrandEditDisplayMode(WebConstantCode.DISPLAY_EDIT);
    setNextUrl(null);
    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.catalog.BrandListRegisterAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "0320100105";
  }

}
