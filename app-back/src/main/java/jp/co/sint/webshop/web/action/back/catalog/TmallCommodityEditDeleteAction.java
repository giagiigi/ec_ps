package jp.co.sint.webshop.web.action.back.catalog;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import jp.co.sint.webshop.data.dto.CommodityDetail;
import jp.co.sint.webshop.service.CatalogService;
import jp.co.sint.webshop.service.DataIOService;
import jp.co.sint.webshop.service.Permission;
import jp.co.sint.webshop.service.ServiceLocator;
import jp.co.sint.webshop.service.ServiceResult;
import jp.co.sint.webshop.service.data.CommodityImage;
import jp.co.sint.webshop.service.data.SkuImage;
import jp.co.sint.webshop.service.result.CatalogServiceErrorContent;
import jp.co.sint.webshop.service.result.DataIOServiceErrorContent;
import jp.co.sint.webshop.service.result.ServiceErrorContent;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.bean.back.catalog.TmallCommodityEditBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.CompleteMessage;
import jp.co.sint.webshop.web.message.back.catalog.CatalogErrorMessage;
import jp.co.sint.webshop.web.message.back.data.DataIOErrorMessage;
import jp.co.sint.webshop.web.text.back.Messages;

/**
 * U1040120:商品登録のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class TmallCommodityEditDeleteAction extends TmallCommodityEditBaseAction {

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
   * アクションを実行します。
   * 
   * @return アクションの実行結果
   */
  @Override
  public WebActionResult callService() {

    TmallCommodityEditBean reqBean = getBean();
    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    DataIOService ioService = ServiceLocator.getDataIOService(getLoginInfo());
    String shopCode = reqBean.getShopCode();
    String commodityCode = reqBean.getCommodityCode();
    List<String> errorList = new ArrayList<String>();
    List<String> successList = new ArrayList<String>();
    
      List<CommodityDetail> detailList = service.getCommoditySku(shopCode, commodityCode);

      ServiceResult result = service.deleteCCommodity(shopCode, commodityCode);
      if (result.hasError()) {
        for (ServiceErrorContent error : result.getServiceErrorList()) {
          if (error == CatalogServiceErrorContent.DELETE_COMMODITY_ERROR) {
            errorList.add(WebMessage.get(CatalogErrorMessage.DELETE_COMMODITY_ERROR, commodityCode));
          }
        }
      } else {
        // SKU画像の削除
        for (CommodityDetail detail : detailList) {
          SkuImage info = new SkuImage(detail.getShopCode(), detail.getSkuCode());
          ServiceResult ioResult = ioService.deleteImage(info);
          if (ioResult.hasError()) {
            for (ServiceErrorContent error : ioResult.getServiceErrorList()) {
              if (error == DataIOServiceErrorContent.FILE_DELETE_ERROR) {
                addWarningMessage(WebMessage.get(DataIOErrorMessage.FILE_DELETE_FAILED, MessageFormat.format(
                    Messages.getString("web.action.back.catalog.CommodityDeleteDeleteAction.0"),
                        detail.getSkuCode())));
              }
            }
          }
        }

        // 商品画像の削除
        CommodityImage info = new CommodityImage(shopCode, commodityCode);
        ServiceResult ioResult = ioService.deleteImage(info);
        if (ioResult.hasError()) {
          for (ServiceErrorContent error : ioResult.getServiceErrorList()) {
            if (error == DataIOServiceErrorContent.FILE_DELETE_ERROR) {
              addWarningMessage(WebMessage.get(DataIOErrorMessage.FILE_DELETE_FAILED, MessageFormat.format(
                  Messages.getString("web.action.back.catalog.CommodityDeleteDeleteAction.1"),
                  commodityCode)));
            }
          }
        }
        
        successList.add(WebMessage.get(CompleteMessage.DELETE_COMPLETE, MessageFormat.format(
            Messages.getString("web.action.back.catalog.CommodityDeleteDeleteAction.1"), commodityCode)));
      }
    

    if (errorList.isEmpty()) {
      for (String information : successList) {
        addInformationMessage(information);
      }
    } else if (successList.isEmpty()) {
      for (String error : errorList) {
        addErrorMessage(error);
      }
    } else {
      List<String> warnList = new ArrayList<String>();
      warnList.addAll(successList);
      warnList.addAll(errorList);
      for (String warnings : warnList) {
        addWarningMessage(warnings);
      }
    }

    setRequestBean(reqBean);

    return BackActionResult.RESULT_SUCCESS;

  }


  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.catalog.CommodityEditRegisterAction.1");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3104012007";
  }

}
