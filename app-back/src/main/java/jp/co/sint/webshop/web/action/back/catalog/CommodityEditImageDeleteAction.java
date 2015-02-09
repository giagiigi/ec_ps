package jp.co.sint.webshop.web.action.back.catalog;

import jp.co.sint.webshop.service.DataIOService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.data.CommodityImage;
import jp.co.sint.webshop.service.data.ImageInfo;
import jp.co.sint.webshop.service.data.ImageType;
import jp.co.sint.webshop.service.data.SkuImage;
import jp.co.sint.webshop.service.result.DataIOServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.catalog.CommodityEditBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.ActionErrorMessage;
import jp.co.sint.webshop.web.message.back.data.DataIOErrorMessage;
import jp.co.sint.webshop.web.webutility.WebConstantCode;

/**
 * U1040120:商品登録のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class CommodityEditImageDeleteAction extends CommodityEditBaseAction {

  /** 削除画像指定定数: 商品画像(PC) */
  private static final String IMAGE = "image";

  /** 削除画像指定定数: 商品画像(携帯) */
  private static final String IMAGE_MOBILE = "imageMobile";

  /** 削除画像指定定数: サムネイル画像(PC) */
  private static final String THUMBNAIL = "thumbnail";

  /** 削除画像指定定数: サムネイル画像(携帯) */
  private static final String THUMBNAIL_MOBILE = "thumbnailMobile";

  /** 削除画像指定定数: 代表SKU画像(PC) */
  private static final String REPRESENT_SKU_IMAGE = "representSku";

  /** 削除画像指定定数: 代表SKU画像(携帯) */
  private static final String REPRESENT_SKU_IMAGE_MOBILE = "representSkuMobile";

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
    if (getRequestParameter().getPathArgs().length < 1) {
      addErrorMessage(WebMessage.get(ActionErrorMessage.BAD_URL));
      return false;
    }

    target = getRequestParameter().getPathArgs()[0];
    // URLパラメータが"image" or "thumbnail_pc" or "thumbnail_mobile"以外ならエラー
    if (!IMAGE.equals(target) && !IMAGE_MOBILE.equals(target) && !THUMBNAIL.equals(target) && !THUMBNAIL_MOBILE.equals(target)
        && !REPRESENT_SKU_IMAGE.equals(target) && !REPRESENT_SKU_IMAGE_MOBILE.equals(target)) {
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

    CommodityEditBean reqBean = getBean();

    // 削除対象画像オブジェクトを生成
    ImageInfo info = null;

    String imageName = "";
    if (THUMBNAIL.equals(target)) {
      CommodityImage image = new CommodityImage(reqBean.getShopCode(), reqBean.getCommodityCode());
      image.setImageType(ImageType.COMMODITY_THUMBNAIL_PC);
      info = image;
      imageName = Messages.getString("web.action.back.catalog.CommodityEditImageDeleteAction.0");
    } else if (THUMBNAIL_MOBILE.equals(target)) {
      CommodityImage image = new CommodityImage(reqBean.getShopCode(), reqBean.getCommodityCode());
      image.setImageType(ImageType.COMMODITY_THUMBNAIL_MOBILE);
      info = image;
      imageName = Messages.getString("web.action.back.catalog.CommodityEditImageDeleteAction.1");
    } else if (IMAGE.equals(target)) {
      CommodityImage image = new CommodityImage(reqBean.getShopCode(), reqBean.getCommodityCode());
      image.setImageType(ImageType.COMMODITY_IMAGE_PC);
      info = image;
      imageName = Messages.getString("web.action.back.catalog.CommodityEditImageDeleteAction.2");
    } else if (IMAGE_MOBILE.equals(target)) {
      CommodityImage image = new CommodityImage(reqBean.getShopCode(), reqBean.getCommodityCode());
      image.setImageType(ImageType.COMMODITY_IMAGE_MOBILE);
      info = image;
      imageName = Messages.getString("web.action.back.catalog.CommodityEditImageDeleteAction.3");
    } else if (REPRESENT_SKU_IMAGE.equals(target)) {
      SkuImage image = new SkuImage(reqBean.getShopCode(), reqBean.getSku().getRepresentSkuCode());
      image.setImageType(ImageType.SKU_IMAGE_PC);
      info = image;
      imageName = Messages.getString("web.action.back.catalog.CommodityEditImageDeleteAction.4");
    } else if (REPRESENT_SKU_IMAGE_MOBILE.equals(target)) {
      SkuImage image = new SkuImage(reqBean.getShopCode(), reqBean.getSku().getRepresentSkuCode());
      image.setImageType(ImageType.SKU_IMAGE_MOBILE);
      info = image;
      imageName = Messages.getString("web.action.back.catalog.CommodityEditImageDeleteAction.5");
    }

    if (info == null) {
      addErrorMessage(WebMessage.get(DataIOErrorMessage.FILE_DELETE_FAILED, imageName));
      setRequestBean(reqBean);
      return BackActionResult.RESULT_SUCCESS;
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

    setNextUrl("/app/catalog/commodity_edit/select/" + reqBean.getShopCode() + "/" + reqBean.getCommodityCode() + "/edit" + "/"
        + WebConstantCode.COMPLETE_DELETE);

    return BackActionResult.RESULT_SUCCESS;
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.catalog.CommodityEditImageDeleteAction.6");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3104012010";
  }

}
