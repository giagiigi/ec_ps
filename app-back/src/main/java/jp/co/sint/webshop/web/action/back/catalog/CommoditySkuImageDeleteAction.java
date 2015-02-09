package jp.co.sint.webshop.web.action.back.catalog;

import jp.co.sint.webshop.service.DataIOService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.data.ImageType;
import jp.co.sint.webshop.service.data.SkuImage;
import jp.co.sint.webshop.service.result.DataIOServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.catalog.CommoditySkuBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.message.back.data.DataIOErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;


/**
 * U1040140:商品SKUのアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class CommoditySkuImageDeleteAction extends CommoditySkuBaseAction {

  private static final String SKU_IMAGE = "image";
  
  private static final String SKU_IMAGE_MOBILE = "imageMobile";

  private String target;
  
  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return Permission.COMMODITY_UPDATE.isGranted(getLoginInfo());
  }

  /**
   * データモデルに格納された入力値の妥当性を検証します。
   * 
   * @return 入力値にエラーがなければtrue
   */
  @Override
  public boolean validate() {
    if (getRequestParameter().getPathArgs().length < 2) {
      addErrorMessage(WebMessage.get(ActionErrorMessage.BAD_URL));
      return false;
    }
    
    target = getRequestParameter().getPathArgs()[0];
    // URLパラメータが"image" or "imageMobile"以外ならエラー
    if (!SKU_IMAGE.equals(target)
        && !SKU_IMAGE_MOBILE.equals(target)) {
      addErrorMessage(WebMessage.get(ActionErrorMessage.BAD_URL));
    }

    return true;
  }


  /**
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {
    
    CommoditySkuBean reqBean = getBean();
    
    String shopCode = reqBean.getShopCode();
    String skuCode = getRequestParameter().getPathArgs()[1];
    
    SkuImage info = new SkuImage(shopCode, skuCode);
    String imageName = "";
    if (SKU_IMAGE.equals(target)) {
      info.setImageType(ImageType.SKU_IMAGE_PC);
      imageName = Messages.getString("web.action.back.catalog.CommoditySkuImageDeleteAction.0");
    } else if (SKU_IMAGE_MOBILE.equals(target)) {
      info.setImageType(ImageType.SKU_IMAGE_MOBILE);
      imageName = Messages.getString("web.action.back.catalog.CommoditySkuImageDeleteAction.1");
    }
    
    DataIOService service = ServiceLocator.getDataIOService(getLoginInfo());
    ServiceResult result = service.deleteImage(info);
    for (ServiceErrorContent error : result.getServiceErrorList()) {
      if (error == DataIOServiceErrorContent.FILE_DELETE_ERROR) {
        addErrorMessage(WebMessage.get(DataIOErrorMessage.FILE_DELETE_FAILED, imageName));
        setRequestBean(reqBean);
        return BackActionResult.RESULT_SUCCESS;
      }
    }
    
    setRequestBean(reqBean);
    
    setNextUrl("/app/catalog/commodity_sku/init/" + getBean().getShopCode() + "/"
        + getBean().getParentCommodityCode() + "/" + COMPLETE_FILE_DELETE);
    
    return BackActionResult.RESULT_SUCCESS;
  }
  
  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.catalog.CommoditySkuImageDeleteAction.2");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3104012011";
  }

}
