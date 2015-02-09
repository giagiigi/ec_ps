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

import org.apache.log4j.Logger;

/**
 * U1040410:タグマスタのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class BrandListUpdateAction extends BrandListBaseAction {
 
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
    BrandListBean reqBean = (BrandListBean) getBean();
    return validateBean(reqBean.getEdit());
  }

  /**
   * 画面で入力されたタグ情報を更新します<BR>
   * タグ情報の更新は、ショップ管理者のみが行えます<BR>
   */
  @Override
  public WebActionResult callService() {
    Logger logger = Logger.getLogger(this.getClass());

    BrandListBean reqBean = (BrandListBean) getBean();

    // ショップ管理者、または一店舗版の場合はログイン情報からショップコードを取得
    if (getLoginInfo().isShop() || getConfig().isOne()) {
      reqBean.setSearchShopCode(getLoginInfo().getShopCode());
    }

    // 更新対象のタグ情報を取得
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    Brand brandResult = service.getBrand(reqBean.getSearchShopCode(), reqBean.getEdit().getBrandCode());

    // service.calculateStockByBatch("00000000", "10001", true);
    if (brandResult == null) {
      addWarningMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, Messages
          .getString("web.action.back.catalog.BrandListUpdateAction.0")));
      logger.debug(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, Messages
          .getString("web.action.back.catalog.BrandListUpdateAction.0")));
      return BackActionResult.RESULT_SUCCESS;
    }

    String shopCode = "";
    if (getLoginInfo().isSite()) {
      shopCode = reqBean.getEdit().getShopCode();
    } else {
      shopCode = getLoginInfo().getShopCode();
    }

    // 更新用データを生成
    Brand brand = new Brand();
    brand.setShopCode(shopCode);
    brand.setBrandCode(reqBean.getEdit().getBrandCode());
    brand.setBrandName(reqBean.getEdit().getBrandName());
    brand.setBrandDescription(reqBean.getEdit().getBrandDescription());
    // 20120524 tuxinwei add start
    brand.setBrandDescriptionEn(reqBean.getEdit().getBrandDescriptionEn());
    brand.setBrandDescriptionJp(reqBean.getEdit().getBrandDescriptionJp());
    // 20120524 tuxinwei add end
    brand.setBrandEnglishName(reqBean.getEdit().getBrandEnglishName());
    // 20120515 tuxinwei add start
    brand.setBrandJapaneseName(reqBean.getEdit().getBrandJapaneseName());
    // 20120515 tuxinwei add end
    brand.setBrandNameAbbr(reqBean.getEdit().getBrandNameAbbr());
    brand.setTmallBrandCode(reqBean.getEdit().getTmallBrandCode());
    brand.setTmallBrandName(reqBean.getEdit().getTmallBrandName());
    brand.setBrandType(reqBean.getEdit().getBrandType());
    brand.setOrmRowid(brandResult.getOrmRowid());
    brand.setCreatedDatetime(brandResult.getCreatedDatetime());
    brand.setCreatedUser(brandResult.getCreatedUser());
    brand.setUpdatedDatetime(reqBean.getEdit().getUpdatedDatetime());
    brand.setUpdatedUser(getLoginInfo().getLoginId());
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

    // 更新サービスを実行
    ServiceResult result = service.updateBrand(brand);
    if (result.hasError()) {
      for (ServiceErrorContent errorContent : result.getServiceErrorList()) {
        if (errorContent.equals(CommonServiceErrorContent.DUPLICATED_REGISTER_ERROR)) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.DUPLICATED_REGISTER_ERROR));
        } else if (errorContent.equals(CommonServiceErrorContent.NO_DATA_ERROR)) {
          addErrorMessage(WebMessage.get(ServiceErrorMessage.NO_DATA_ERROR, Messages
              .getString("web.action.back.catalog.BrandListUpdateAction.0")));
        } else if (errorContent.equals(CommonServiceErrorContent.VALIDATION_ERROR)) {
          return BackActionResult.SERVICE_VALIDATION_ERROR;
        } else if (errorContent.equals(CommonServiceErrorContent.DB_OBJECT_EXECUTE_ERROR)) {
          return BackActionResult.SERVICE_ERROR;
        }
      }
      setNextUrl(null);
    } else {
      // 设置编辑属性
      reqBean.setMode(WebConstantCode.DISPLAY_READONLY);
      // 获取操作完成信息
      addInformationMessage(WebMessage.get(CompleteMessage.UPDATE_COMPLETE, PICTURE_NAME));
    }

    reqBean.setDisplayNextButton(true);
    reqBean.setRegisterButtonDisplayFlg(false);
    reqBean.setUpdateButtonDisplayFlg(false);
    reqBean.setBrandEditDisplayMode(WebConstantCode.DISPLAY_EDIT);
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
    return Messages.getString("web.action.back.catalog.BrandListUpdateAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3104091009";
  }

}
