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
import jp.co.sint.webshop.web.text.back.Messages;
import jp.co.sint.webshop.web.action.WebActionResult;
import jp.co.sint.webshop.web.action.back.BackActionResult;
import jp.co.sint.webshop.web.action.back.WebBackAction;
import jp.co.sint.webshop.web.bean.back.catalog.CommodityDeleteBean;
import jp.co.sint.webshop.web.bean.back.catalog.CommodityDeleteBean.CommodityDeleteListBean;
import jp.co.sint.webshop.web.message.WebMessage;
import jp.co.sint.webshop.web.message.back.CompleteMessage;
import jp.co.sint.webshop.web.message.back.catalog.CatalogErrorMessage;
import jp.co.sint.webshop.web.message.back.data.DataIOErrorMessage;

/**
 * U1040130:商品削除確認のアクションクラスです
 * 
 * @author System Integrator Corp.
 */
public class CommodityDeleteDeleteAction extends WebBackAction<CommodityDeleteBean> {

  /**
   * ログインユーザの権限を確認し、このアクションの実行を認可するかどうかを返します。
   * 
   * @return アクションの実行を認可する場合はtrue
   */
  @Override
  public boolean authorize() {
    return Permission.COMMODITY_DELETE.isGranted(getLoginInfo());
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

    CommodityDeleteBean reqBean = getBean();

    CatalogService service = ServiceLocator.getCatalogService(getLoginInfo());
    DataIOService ioService = ServiceLocator.getDataIOService(getLoginInfo());

    List<String> errorList = new ArrayList<String>();
    List<String> successList = new ArrayList<String>();
    for (CommodityDeleteListBean commodity : reqBean.getList()) {
      List<CommodityDetail> detailList = service.getCommoditySku(commodity.getShopCode(), commodity.getCommodityCode());

      ServiceResult result = service.deleteCommodity(commodity.getShopCode(), commodity.getCommodityCode());
      String commodityCode = commodity.getCommodityCode();
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
        CommodityImage info = new CommodityImage(commodity.getShopCode(), commodity.getCommodityCode());
        ServiceResult ioResult = ioService.deleteImage(info);
        if (ioResult.hasError()) {
          for (ServiceErrorContent error : ioResult.getServiceErrorList()) {
            if (error == DataIOServiceErrorContent.FILE_DELETE_ERROR) {
              addWarningMessage(WebMessage.get(DataIOErrorMessage.FILE_DELETE_FAILED, MessageFormat.format(
                  Messages.getString("web.action.back.catalog.CommodityDeleteDeleteAction.1"),
                          commodity.getCommodityCode())));
            }
          }
        }
        
        successList.add(WebMessage.get(CompleteMessage.DELETE_COMPLETE, MessageFormat.format(
            Messages.getString("web.action.back.catalog.CommodityDeleteDeleteAction.1"), commodityCode)));
      }
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
   * 画面表示に必要な項目を設定・初期化します。
   */
  @Override
  public void prerender() {
    CommodityDeleteBean reqBean = (CommodityDeleteBean) getRequestBean();
    reqBean.setDisplayDeleteButton(false);
    setRequestBean(reqBean);
  }

  /**
   * Action名の取得
   * 
   * @return Action名
   */
  public String getActionName() {
    return Messages.getString("web.action.back.catalog.CommodityDeleteDeleteAction.2");
  }

  /**
   * オペレーションコードの取得
   * 
   * @return オペレーションコード
   */
  public String getOperationCode() {
    return "3104013001";
  }

}
