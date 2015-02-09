package jp.co.sint.webshop.web.action.back.catalog;

import java.util.List;

import jp.co.sint.webshop.data.dto.CommodityDetail;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.catalog.CommodityInfo;
import jp.co.sint.webshop.utility.StringUtil;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.catalog.CommodityEditBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.InformationMessage;
import jp.co.sint.webshop.web.message.back.ServiceErrorMessage;
import jp.co.sint.webshop.web.message.back.catalog.CatalogErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1040120:商品登録のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class CommodityEditConfirmAction extends CommodityEditBaseAction {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return Permission.COMMODITY_UPDATE.isGranted(getLoginInfo());
  }

  private boolean validationResult = false;

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    validationResult = super.validate();
    //add by twh 2012-12-4 17:16:27 start
    //套装商品登录时加上的判断
    if(StringUtil.isNullOrEmptyAnyOf(getBean().getHeader().getCommodityNameEn())){
      addErrorMessage("商品英文名不能为空");
      validationResult &= false;
    }
    if(StringUtil.isNullOrEmptyAnyOf(getBean().getHeader().getCommodityNameJp())){
      addErrorMessage("商品日文名不能为空");      
      validationResult &= false;
    }
    //add by twh 2012-12-4 17:16:27 end
    return validationResult;
  }

  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    CommodityEditBean reqBean = getBean();

    CatalogService catalogService = ServiceLocator.getCatalogService(getLoginInfo());
   CommodityInfo commodity = catalogService.getCommodityInfo(getLoginInfo().getShopCode(), reqBean.getCommodityCode());
    CommodityInfo representSku = catalogService.getSkuInfo(getLoginInfo().getShopCode(), reqBean.getSku().getRepresentSkuCode());

    if (reqBean.getMode().equals(MODE_NEW)) {

      // 重複チェック
      if (commodity != null) {
        addErrorMessage(WebMessage.get(ServiceErrorMessage.DUPLICATED_REGISTER_ERROR, Messages
            .getString("web.action.back.catalog.CommodityEditConfirmAction.0")));
        validationResult &= false;
      }
      // 代表SKUコードの重複チェック
      if (representSku != null) {
        addErrorMessage(WebMessage.get(ServiceErrorMessage.DUPLICATED_REGISTER_ERROR, Messages
            .getString("web.action.back.catalog.CommodityEditConfirmAction.1")));
        validationResult &= false;
      }

    } else if (reqBean.getMode().equals(MODE_UPDATE)) {

      // 存在チェック
      if (commodity == null || commodity.getHeader() == null || commodity.getDetail() == null) {
        setNextUrl("/app/catalog/commodity_list/init/nodata");
        return BackActionResult.RESULT_SUCCESS;
      }

      if (representSku == null) {
        addErrorMessage(WebMessage.get(CatalogErrorMessage.NOT_REPRESENT_SKU_ERROR));
        validationResult &= false;
        return BackActionResult.RESULT_SUCCESS;

      } else {
        // 自分に関連付いている商品SKUの一覧
        List<CommodityDetail> ownSkuList = catalogService.getCommoditySku(getLoginInfo().getShopCode(), reqBean.getCommodityCode());

        boolean isOwn = false;
        for (CommodityDetail cd : ownSkuList) {
          if (representSku.getDetail().getSkuCode().equals(cd.getSkuCode())) {
            isOwn = true;
          }
        }
        // 自分に関連付いている商品SKUのSKUコード以外を代表SKUコードにしようとしたらエラー
        if (!isOwn) {
          validationResult &= false;
          addErrorMessage(WebMessage.get(ServiceErrorMessage.DUPLICATED_REGISTER_ERROR, Messages
              .getString("web.action.back.catalog.CommodityEditConfirmAction.1")));
        }

      }
    }

    setRequestBean(reqBean);

    if (validationResult) { // エラーがひとつも存在しなければ確認メッセージを表示する
      addWarningMessage(WebMessage.get(InformationMessage.REGISTER_CONFIRM));
    }

    return BackActionResult.RESULT_SUCCESS;

  }

  /**
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {

    CommodityEditBean reqBean = (CommodityEditBean) getRequestBean();

    if (validationResult) {
      reqBean.setCommodityCodeDisplayMode(WebConstantCode.DISPLAY_HIDDEN);
      reqBean.setCommodityEditDisplayMode(WebConstantCode.DISPLAY_HIDDEN);
      reqBean.setDisplayNextButton(false);
      reqBean.setDisplayPreviewButton(false);

      if (reqBean.getMode().equals(MODE_NEW)) {
        reqBean.setDisplayRegisterButton(true);
        reqBean.setDisplayUpdateButton(false);
        reqBean.setDisplayCancelButton(true);
      } else if (reqBean.getMode().equals(MODE_UPDATE)) {
        reqBean.setDisplayRegisterButton(false);
        reqBean.setDisplayUpdateButton(true);
        reqBean.setDisplayCancelButton(true);
      } else {
        reqBean.setDisplayNextButton(false);
        reqBean.setDisplayRegisterButton(false);
        reqBean.setDisplayUpdateButton(false);
        reqBean.setDisplayCancelButton(false);
      }
    } else {
      reqBean = getBean();
    }

    reqBean.setDisplayMoveSkuButton(false);

    setRequestBean(reqBean);
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.catalog.CommodityEditConfirmAction.2");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3104012003";
  }

}
